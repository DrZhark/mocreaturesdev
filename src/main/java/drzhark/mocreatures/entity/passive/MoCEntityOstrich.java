package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.EntityAIFollowAdult;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import drzhark.mocreatures.inventory.MoCAnimalChest;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import drzhark.mocreatures.util.MoCSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

import java.util.List;

import javax.annotation.Nullable;

public class MoCEntityOstrich extends MoCEntityTameableAnimal {

    private int eggCounter;
    private int hidingCounter;
    public int mouthCounter;
    public int wingCounter;
    public int sprintCounter;
    public int jumpCounter;
    public int transformCounter;
    public int transformType;
    public boolean canLayEggs;

    public MoCAnimalChest localchest;
    public ItemStack localstack;
    private static final DataParameter<Boolean> RIDEABLE = EntityDataManager.<Boolean>createKey(MoCEntityOstrich.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> EGG_WATCH = EntityDataManager.<Boolean>createKey(MoCEntityOstrich.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> CHESTED = EntityDataManager.<Boolean>createKey(MoCEntityOstrich.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_HIDING = EntityDataManager.<Boolean>createKey(MoCEntityOstrich.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> HELMET_TYPE = EntityDataManager.<Integer>createKey(MoCEntityOstrich.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> FLAG_COLOR = EntityDataManager.<Integer>createKey(MoCEntityOstrich.class, DataSerializers.VARINT);
    

    public MoCEntityOstrich(World world) {
        super(world);
        setSize(1.0F, 1.6F);
        setEdad(35);
        this.eggCounter = this.rand.nextInt(1000) + 1000;
        this.stepHeight = 1.0F;
        this.canLayEggs = false;
    }
    
    @Override
    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(4, new EntityAIFollowAdult(this, 1.0D));
        this.tasks.addTask(5, new EntityAIAttackMelee(this, 1.0D, true));
        this.tasks.addTask(6, new EntityAIWanderMoC2(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(EGG_WATCH, Boolean.valueOf(false));
        this.dataManager.register(CHESTED, Boolean.valueOf(false));
        this.dataManager.register(RIDEABLE, Boolean.valueOf(false));
        this.dataManager.register(IS_HIDING, Boolean.valueOf(false));
        this.dataManager.register(HELMET_TYPE, Integer.valueOf(0));
        this.dataManager.register(FLAG_COLOR, Integer.valueOf(0));
    }

    @Override
    public boolean getIsRideable() {
    	return ((Boolean)this.dataManager.get(RIDEABLE)).booleanValue();
    }

    @Override
    public void setRideable(boolean flag) {
    	this.dataManager.set(RIDEABLE, Boolean.valueOf(flag));
    }

    public boolean getEggWatching() {
    	return ((Boolean)this.dataManager.get(EGG_WATCH)).booleanValue();
    }

    public void setEggWatching(boolean flag) {
    	this.dataManager.set(EGG_WATCH, Boolean.valueOf(flag));
    }

    public boolean getHiding() {
    	return ((Boolean)this.dataManager.get(IS_HIDING)).booleanValue();
    }

    public void setHiding(boolean flag) {
        this.dataManager.set(IS_HIDING, Boolean.valueOf(flag));
    }

    public int getHelmet() {
    	return ((Integer)this.dataManager.get(HELMET_TYPE)).intValue();
    }

    public void setHelmet(int i) {
    	this.dataManager.set(HELMET_TYPE, Integer.valueOf(i));
    }

    public int getFlagColor() {
    	return ((Integer)this.dataManager.get(FLAG_COLOR)).intValue();
    }

    public void setFlagColor(int i) {
    	this.dataManager.set(FLAG_COLOR, Integer.valueOf(i));
    }

    public boolean getIsChested() {
    	return ((Boolean)this.dataManager.get(CHESTED)).booleanValue();
    }

    public void setIsChested(boolean flag) {
    	this.dataManager.set(CHESTED, Boolean.valueOf(flag));
    }

    @Override
    public boolean isMovementCeased() {
        return (getHiding() || this.isBeingRidden());
    }

    @Override
    public boolean isNotScared() {
        return (getType() == 2 && getAttackTarget() != null) || (getType() > 2);
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        //dmg reduction
        if (getIsTamed() && getHelmet() != 0) {
            int j = 0;
            switch (getHelmet()) {
                case 1:
                    j = 1;
                    break;
                case 5:
                case 6:
                case 2:
                    j = 2;
                    break;
                case 7:
                case 3:
                    j = 3;
                    break;
                case 4:
                case 9:
                case 10:
                case 11:
                case 12:
                    j = 4;
                    break;
            }
            i -= j;
            if (i <= 0) {
                i = 1;
            }
        }

        if (super.attackEntityFrom(damagesource, i)) {
            Entity entity = damagesource.getEntity();

            if (!(entity instanceof EntityLivingBase) || ((this.isBeingRidden()) && (entity == this.getRidingEntity()))
                    || (entity instanceof EntityPlayer && getIsTamed())) {
                return false;
            }

            if ((entity != this) && (super.shouldAttackPlayers()) && getType() > 2) {
                setAttackTarget((EntityLivingBase) entity);
                flapWings();
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onDeath(DamageSource damagesource) {
        super.onDeath(damagesource);
        dropMyStuff();
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        if (entityIn instanceof EntityPlayer && !shouldAttackPlayers()) {
            return false;
        }
        openMouth();
        flapWings();
        return super.attackEntityAsMob(entityIn);
    }

    public float calculateMaxHealth() {
        switch (getType()) {
            case 1:
                return 10;
            case 2:
                return 20;
            case 3:
                return 25;
            case 4:
                return 25;
            case 5:
                return 35;
            case 6:
                return 35;
            default:
                return 20;
        }
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isBeingRidden();
    }

    @Override
    public void selectType() {
        if (getType() == 0) {
            /**
             * 1 = chick /2 = female /3 = male /4 = albino male /5 = nether ostrich /6 = wyvern
             */
            int j = this.rand.nextInt(100);
            if (j <= (20)) {
                setType(1);
            } else if (j <= (65)) {
                setType(2);
            } else if (j <= (95)) {
                setType(3);
            } else {
                setType(4);
            }
        }
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(calculateMaxHealth());
        this.setHealth(getMaxHealth());
    }

    @Override
    public ResourceLocation getTexture() {
        if (this.transformCounter != 0 && this.transformType > 4) {
            String newText = "ostricha.png";
            if (this.transformType == 5) {
                newText = "ostriche.png";
            }
            if (this.transformType == 6) {
                newText = "ostrichf.png";
            }
            if (this.transformType == 7) {
                newText = "ostrichg.png";
            }
            if (this.transformType == 8) {
                newText = "ostrichh.png";
            }

            if ((this.transformCounter % 5) == 0) {
                return MoCreatures.proxy.getTexture(newText);
            }
            if (this.transformCounter > 50 && (this.transformCounter % 3) == 0) {
                return MoCreatures.proxy.getTexture(newText);
            }

            if (this.transformCounter > 75 && (this.transformCounter % 4) == 0) {
                return MoCreatures.proxy.getTexture(newText);
            }
        }

        switch (getType()) {
            case 1:
                return MoCreatures.proxy.getTexture("ostrichc.png"); //chick
            case 2:
                return MoCreatures.proxy.getTexture("ostrichb.png"); //female
            case 3:
                return MoCreatures.proxy.getTexture("ostricha.png"); //male
            case 4:
                return MoCreatures.proxy.getTexture("ostrichd.png"); //albino
            case 5:
                return MoCreatures.proxy.getTexture("ostriche.png"); //nether
            case 6:
                return MoCreatures.proxy.getTexture("ostrichf.png"); //black wyvern
            case 7:
                return MoCreatures.proxy.getTexture("ostrichg.png"); //undead
            case 8:
                return MoCreatures.proxy.getTexture("ostrichh.png"); //unicorned
            default:
                return MoCreatures.proxy.getTexture("ostricha.png");
        }
    }

    @Override
    public double getCustomSpeed() {
        double OstrichSpeed = 0.8D;
        if (getType() == 1) {
            OstrichSpeed = 0.8;
        } else if (getType() == 2) {
            OstrichSpeed = 0.8D;
        } else if (getType() == 3) {
            OstrichSpeed = 1.1D;
        } else if (getType() == 4) {
            OstrichSpeed = 1.3D;
        } else if (getType() == 5) {
            OstrichSpeed = 1.4D;
            this.isImmuneToFire = true;
        }
        if (this.sprintCounter > 0 && this.sprintCounter < 200) {
            OstrichSpeed *= 1.5D;
        }
        if (this.sprintCounter > 200) {
            OstrichSpeed *= 0.5D;
        }
        return OstrichSpeed;
    }

    @Override
    public boolean rideableEntity() {
        return true;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (getHiding()) {
            this.prevRenderYawOffset = this.renderYawOffset = this.rotationYaw = this.prevRotationYaw;
        }

        if (this.mouthCounter > 0 && ++this.mouthCounter > 20) {
            this.mouthCounter = 0;
        }

        if (this.wingCounter > 0 && ++this.wingCounter > 80) {
            this.wingCounter = 0;
        }

        if (this.jumpCounter > 0 && ++this.jumpCounter > 8) {
            this.jumpCounter = 0;
        }

        if (this.sprintCounter > 0 && ++this.sprintCounter > 300) {
            this.sprintCounter = 0;
        }

        if (this.transformCounter > 0) {
            if (this.transformCounter == 40) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_TRANSFORM);
            }

            if (++this.transformCounter > 100) {
                this.transformCounter = 0;
                if (this.transformType != 0) {
                    dropArmor();
                    setType(this.transformType);
                    selectType();
                }
            }
        }
    }

    public void transform(int tType) {
        if (MoCreatures.isServer()) {
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), tType),
                    new TargetPoint(this.worldObj.provider.getDimensionType().getId(), this.posX, this.posY, this.posZ, 64));
        }
        this.transformType = tType;
        if (!this.isBeingRidden() && this.transformType != 0) {
            dropArmor();
            this.transformCounter = 1;
        }
    }

    @Override
    public void performAnimation(int animationType) {
        if (animationType >= 5 && animationType < 9) //transform 5 - 8
        {
            this.transformType = animationType;
            this.transformCounter = 1;
        }

    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (getIsTamed() && MoCreatures.isServer() && (this.rand.nextInt(300) == 0) && (getHealth() <= getMaxHealth()) && (this.deathTime == 0)) {
            this.setHealth(getHealth() + 1);
        }

        if (MoCreatures.isServer()) {
            //ostrich buckle!
            if (getType() == 8 && (this.sprintCounter > 0 && this.sprintCounter < 150) && (this.isBeingRidden()) && rand.nextInt(15) == 0) {
                MoCTools.buckleMobs(this, 2D, this.worldObj);
            }
            // TODO
            //shy ostriches will run and hide
            /*if (!isNotScared() && fleeingTick > 0 && fleeingTick < 2) {
                fleeingTick = 0;
                setHiding(true);
                this.getNavigator().clearPathEntity();
            }*/

            if (getHiding()) {
                //wild shy ostriches will hide their heads only for a short term
                //tamed ostriches will keep their heads hidden until the whip is used again
                if (++this.hidingCounter > 500 && !getIsTamed()) {
                    setHiding(false);
                    this.hidingCounter = 0;
                }

            }

            if (getType() == 1 && (this.rand.nextInt(200) == 0)) {
                //when is chick and becomes adult, change over to different type
                setEdad(getEdad() + 1);
                if (getEdad() >= 100) {
                    setAdult(true);
                    setType(0);
                    selectType();
                }
            }

            //egg laying
            if (this.canLayEggs && (getType() == 2) && !getEggWatching() && --this.eggCounter <= 0 && this.rand.nextInt(5) == 0)// &&
            {
                EntityPlayer entityplayer1 = this.worldObj.getClosestPlayerToEntity(this, 12D);
                if (entityplayer1 != null) {
                    double distP = MoCTools.getSqDistanceTo(entityplayer1, this.posX, this.posY, this.posZ);
                    if (distP < 10D) {
                        int OstrichEggType = 30;
                        MoCEntityOstrich maleOstrich = getClosestMaleOstrich(this, 8D);
                        if (maleOstrich != null && this.rand.nextInt(100) < MoCreatures.proxy.ostrichEggDropChance) {
                            MoCEntityEgg entityegg = new MoCEntityEgg(this.worldObj, OstrichEggType);
                            entityegg.setPosition(this.posX, this.posY, this.posZ);
                            this.worldObj.spawnEntityInWorld(entityegg);

                            if (!this.getIsTamed()) {
                                setEggWatching(true);
                                if (maleOstrich != null) {
                                    maleOstrich.setEggWatching(true);
                                }
                                openMouth();
                            }

                            //TODO change sound
                            MoCTools.playCustomSound(this, SoundEvents.ENTITY_CHICKEN_EGG);
                            //finds a male and makes it eggWatch as well
                            //MoCEntityOstrich entityOstrich = (MoCEntityOstrich) getClosestSpecificEntity(this, MoCEntityOstrich.class, 12D);
                            this.eggCounter = this.rand.nextInt(2000) + 2000;
                            this.canLayEggs = false;
                        }
                    }
                }
            }

            //egg protection
            if (getEggWatching()) {
                //look for and protect eggs and move close
                MoCEntityEgg myEgg = (MoCEntityEgg) getBoogey(8D);
                if ((myEgg != null) && (MoCTools.getSqDistanceTo(myEgg, this.posX, this.posY, this.posZ) > 4D)) {
                    Path pathentity = this.navigator.getPathToPos(myEgg.getPosition());
                    this.navigator.setPath(pathentity, 16F);
                }
                if (myEgg == null) //didn't find egg
                {
                    setEggWatching(false);

                    EntityPlayer eggStealer = this.worldObj.getClosestPlayerToEntity(this, 10D);
                    if (eggStealer != null) {
                        this.worldObj.getDifficulty();
                        if (!getIsTamed() && this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL) {
                            setAttackTarget(eggStealer);
                            flapWings();
                        }
                    }
                }
            }
        }
    }

    protected MoCEntityOstrich getClosestMaleOstrich(Entity entity, double d) {
        double d1 = -1D;
        MoCEntityOstrich entityliving = null;
        List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(entity, entity.getEntityBoundingBox().expand(d, d, d));
        for (int i = 0; i < list.size(); i++) {
            Entity entity1 = list.get(i);
            if (!(entity1 instanceof MoCEntityOstrich) || ((entity1 instanceof MoCEntityOstrich) && ((MoCEntityOstrich) entity1).getType() < 3)) {
                continue;
            }

            double d2 = entity1.getDistanceSq(entity.posX, entity.posY, entity.posZ);
            if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1))) {
                d1 = d2;
                entityliving = (MoCEntityOstrich) entity1;
            }
        }

        return entityliving;
    }

    @Override
    public boolean entitiesToInclude(Entity entity) {
        return ((entity instanceof MoCEntityEgg) && (((MoCEntityEgg) entity).eggType == 30)

        );
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand, @Nullable ItemStack stack) {
        if (super.processInteract(player, hand, stack)) {
            return true;
        }
        boolean onMainHand = (hand == EnumHand.MAIN_HAND);
        if (getIsTamed() && onMainHand && (getType() > 1) && (stack != null) && !getIsRideable()
                && (stack.getItem() == MoCreatures.horsesaddle || stack.getItem() == Items.SADDLE)) {
            if (--stack.stackSize == 0) {
                player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
            }
            MoCTools.playCustomSound(this, SoundEvents.ENTITY_CHICKEN_EGG);
            setRideable(true);
            return true;
        }

        if (!getIsTamed() && onMainHand && stack != null && getType() == 2 && stack.getItem() == Items.MELON_SEEDS) {
            if (--stack.stackSize == 0) {
                player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
            }

            openMouth();
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_EATING);
            this.canLayEggs = true;
            return true;
        }

        //makes the ostrich stay by hiding their heads
        if ((stack != null) && (stack.getItem() == MoCreatures.whip) && getIsTamed() && (!this.isBeingRidden())) {
            setHiding(!getHiding());
            return true;
        }

        if ((stack != null) && onMainHand && this.getIsTamed() && getType() > 1 && stack.getItem() == MoCreatures.essencedarkness) {
            if (--stack.stackSize == 0) {
                player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.GLASS_BOTTLE));
            } else {
                player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }
            if (getType() == 6) {
                this.setHealth(getMaxHealth());
            } else {
                transform(6);
            }
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_DRINKING);
            return true;
        }

        if ((stack != null) && onMainHand && this.getIsTamed() && getType() > 1 && stack.getItem() == MoCreatures.essenceundead) {
            if (--stack.stackSize == 0) {
                player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.GLASS_BOTTLE));
            } else {
                player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }
            if (getType() == 7) {
                this.setHealth(getMaxHealth());
            } else {
                transform(7);
            }
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_DRINKING);
            return true;
        }

        if ((stack != null) && onMainHand && this.getIsTamed() && getType() > 1 && stack.getItem() == MoCreatures.essencelight) {
            if (--stack.stackSize == 0) {
                player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.GLASS_BOTTLE));
            } else {
                player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }
            if (getType() == 8) {
                this.setHealth(getMaxHealth());
            } else {
                transform(8);
            }
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_DRINKING);
            return true;
        }

        if ((stack != null) && onMainHand && this.getIsTamed() && getType() > 1 && stack.getItem() == MoCreatures.essencefire) {
            if (--stack.stackSize == 0) {
                player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.GLASS_BOTTLE));
            } else {
                player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }
            if (getType() == 5) {
                this.setHealth(getMaxHealth());
            } else {
                transform(5);
            }
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_DRINKING);
            return true;
        }
        if (getIsTamed() && onMainHand && getIsChested() && (getType() > 1) && stack != null && stack.getItem() == Item.getItemFromBlock(Blocks.WOOL)) {
            int colorInt = (stack.getItemDamage());
            if (colorInt == 0) {
                colorInt = 16;
            }
            if (--stack.stackSize == 0) {
                player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
            }
            MoCTools.playCustomSound(this, SoundEvents.ENTITY_CHICKEN_EGG);
            dropFlag();
            setFlagColor((byte) colorInt);
            return true;
        }

        if ((stack != null) && onMainHand && (getType() > 1) && getIsTamed() && !getIsChested() && (stack.getItem() == Item.getItemFromBlock(Blocks.CHEST))) {
            if (--stack.stackSize == 0) {
                player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
            }

            //entityplayer.inventory.addItemStackToInventory(new ItemStack(MoCreatures.key));
            setIsChested(true);
            MoCTools.playCustomSound(this, SoundEvents.ENTITY_CHICKEN_EGG);
            return true;
        }

        if (player.isSneaking() && getIsChested()) {
            // if first time opening a chest, we must initialize it
            if (this.localchest == null) {
                this.localchest = new MoCAnimalChest("OstrichChest", 9);
            }
            if (MoCreatures.isServer()) {
                player.displayGUIChest(this.localchest);
            }
            return true;
        }

        if (getIsTamed() && onMainHand && (getType() > 1) && stack != null) {

            Item item = stack.getItem();
            if (item instanceof ItemArmor) {
                byte helmetType = 0;
                if (stack.getItem() == Items.LEATHER_HELMET) {
                    helmetType = 1;
                } else if (stack.getItem() == Items.IRON_HELMET) {
                    helmetType = 2;
                } else if (stack.getItem() == Items.GOLDEN_HELMET) {
                    helmetType = 3;
                } else if (stack.getItem() == Items.DIAMOND_HELMET) {
                    helmetType = 4;
                } else if (stack.getItem() == MoCreatures.helmetHide) {
                    helmetType = 5;
                } else if (stack.getItem() == MoCreatures.helmetFur) {
                    helmetType = 6;
                } else if (stack.getItem() == MoCreatures.helmetCroc) {
                    helmetType = 7;
                }
                /*
                 * else if (itemstack.getItem() == MoCreatures.helmetGreen) {
                 * helmetType = 8; }
                 */
                else if (stack.getItem() == MoCreatures.scorpHelmetDirt) {
                    helmetType = 9;
                } else if (stack.getItem() == MoCreatures.scorpHelmetFrost) {
                    helmetType = 10;
                } else if (stack.getItem() == MoCreatures.scorpHelmetCave) {
                    helmetType = 11;
                } else if (stack.getItem() == MoCreatures.scorpHelmetNether) {
                    helmetType = 12;
                }

                if (helmetType != 0) {
                    player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
                    dropArmor();
                    MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ARMOR_OFF);
                    setHelmet(helmetType);
                    return true;
                }

            }
        }
        if (getIsRideable() && getIsAdult() && (!this.isBeingRidden())) {
            player.rotationYaw = this.rotationYaw;
            player.rotationPitch = this.rotationPitch;
            setHiding(false);
            if (!this.worldObj.isRemote && (!this.isBeingRidden() || this.getRidingEntity() == player)) {
                player.startRiding(this);
            }
            return true;
        }
        return false;
    }

    /**
     * Drops a block of the color of the flag if carrying one
     */
    private void dropFlag() {
        if (MoCreatures.isServer() && getFlagColor() != 0) {
            int color = getFlagColor();
            if (color == 16) {
                color = 0;
            }
            EntityItem entityitem = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(Blocks.WOOL, 1, color));
            entityitem.setPickupDelay(10);
            this.worldObj.spawnEntityInWorld(entityitem);
            setFlagColor((byte) 0);
        }
    }

    private void openMouth() {
        this.mouthCounter = 1;
    }

    private void flapWings() {
        this.wingCounter = 1;
    }

    @Override
    protected SoundEvent getHurtSound() {
        openMouth();
        return MoCSoundEvents.ENTITY_OSTRICH_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        openMouth();
        if (getType() == 1) {
            return MoCSoundEvents.ENTITY_OSTRICH_AMBIENT_BABY;
        }

        return MoCSoundEvents.ENTITY_OSTRICH_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        openMouth();
        return MoCSoundEvents.ENTITY_OSTRICH_DEATH;
    }

    @Override
    protected Item getDropItem() {
        boolean flag = (this.rand.nextInt(100) < MoCreatures.proxy.rareItemDropChance);
        if (flag && (this.getType() == 8)) // unicorn
        {
            return MoCreatures.unicornhorn;
        }
        if (this.getType() == 5 && flag) {
            return MoCreatures.heartfire;
        }
        if (this.getType() == 6 && flag) // bat horse
        {
            return MoCreatures.heartdarkness;
        }
        if (this.getType() == 7) {
            if (flag) {
                return MoCreatures.heartundead;
            }
            return Items.ROTTEN_FLESH;
        }
        return MoCreatures.ostrichraw;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        setRideable(nbttagcompound.getBoolean("Saddle"));
        setEggWatching(nbttagcompound.getBoolean("EggWatch"));
        setHiding(nbttagcompound.getBoolean("Hiding"));
        setHelmet(nbttagcompound.getInteger("Helmet"));
        setFlagColor(nbttagcompound.getInteger("FlagColor"));
        setIsChested(nbttagcompound.getBoolean("Bagged"));
        if (getIsChested()) {
            NBTTagList nbttaglist = nbttagcompound.getTagList("Items", 10);
            this.localchest = new MoCAnimalChest("OstrichChest", 18);
            for (int i = 0; i < nbttaglist.tagCount(); i++) {
                NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
                int j = nbttagcompound1.getByte("Slot") & 0xff;
                if ((j >= 0) && j < this.localchest.getSizeInventory()) {
                    this.localchest.setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(nbttagcompound1));
                }
            }
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("Saddle", getIsRideable());
        nbttagcompound.setBoolean("EggWatch", getEggWatching());
        nbttagcompound.setBoolean("Hiding", getHiding());
        nbttagcompound.setInteger("Helmet", getHelmet());
        nbttagcompound.setInteger("FlagColor", getFlagColor());
        nbttagcompound.setBoolean("Bagged", getIsChested());

        if (getIsChested() && this.localchest != null) {
            NBTTagList nbttaglist = new NBTTagList();
            for (int i = 0; i < this.localchest.getSizeInventory(); i++) {
                this.localstack = this.localchest.getStackInSlot(i);
                if (this.localstack != null) {
                    NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                    nbttagcompound1.setByte("Slot", (byte) i);
                    this.localstack.writeToNBT(nbttagcompound1);
                    nbttaglist.appendTag(nbttagcompound1);
                }
            }
            nbttagcompound.setTag("Items", nbttaglist);
        }
    }

    @Override
    public boolean getCanSpawnHere() {
        //spawns in deserts and plains
        return getCanSpawnHereCreature() && getCanSpawnHereLiving();
    }

    @Override
    public int nameYOffset() {
        if (getType() > 1) {
            return -105;
        } else {
            return (-5 - getEdad());
        }
    }

    /*@Override
    public boolean updateMount() {
        return getIsTamed();
    }*/

    /* @Override
     public boolean forceUpdates() {
         return getIsTamed();
     }*/

    @Override
    public boolean isMyHealFood(ItemStack par1ItemStack) {
        return MoCTools.isItemEdible(par1ItemStack.getItem());
    }

    @Override
    public void dropMyStuff() {
        if (MoCreatures.isServer()) {
            dropArmor();
            MoCTools.dropSaddle(this, this.worldObj);

            if (getIsChested()) {
                MoCTools.dropInventory(this, this.localchest);
                MoCTools.dropCustomItem(this, this.worldObj, new ItemStack(Blocks.CHEST, 1));
                setIsChested(false);
            }
        }

    }

    /**
     * Drops the helmet
     */
    @Override
    public void dropArmor() {
        if (MoCreatures.isServer()) {
            EntityItem entityitem = null;// = new EntityItem(worldObj, posX, posY, posZ, new ItemStack(Blocks.WOOL, 1, color));

            switch (getHelmet()) {
                case 0:
                case 8:
                    return;
                    //break;
                case 1:
                    entityitem = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(Items.LEATHER_HELMET, 1));
                    break;
                case 2:
                    entityitem = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(Items.IRON_HELMET, 1));
                    break;
                case 3:
                    entityitem = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(Items.GOLDEN_HELMET, 1));
                    break;
                case 4:
                    entityitem = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(Items.DIAMOND_HELMET, 1));
                    break;
                case 5:
                    entityitem = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(MoCreatures.helmetHide, 1));
                    break;
                case 6:
                    entityitem = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(MoCreatures.helmetFur, 1));
                    break;
                case 7:
                    entityitem = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(MoCreatures.helmetCroc, 1));
                    break;
                case 9:
                    entityitem = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(MoCreatures.scorpHelmetDirt, 1));
                    break;
                case 10:
                    entityitem = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(MoCreatures.scorpHelmetFrost, 1));
                    break;
                case 11:
                    entityitem = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(MoCreatures.scorpHelmetCave, 1));
                    break;
                case 12:
                    entityitem = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(MoCreatures.scorpHelmetNether, 1));
                    break;
            }

            if (entityitem != null) {
                entityitem.setPickupDelay(10);
                this.worldObj.spawnEntityInWorld(entityitem);
            }
            setHelmet((byte) 0);
        }
    }

    @Override
    public boolean isFlyer() {
        return this.isBeingRidden() && (getType() == 5 || getType() == 6);
    }

    @Override
    public void fall(float f, float f1) {
        if (isFlyer()) {
            return;
        }
        super.fall(f, f1);
    }

    @Override
    protected double myFallSpeed() {
        return 0.89D;
    }

    @Override
    protected double flyerThrust() {
        return 0.8D;
    }

    @Override
    protected float flyerFriction() {
        return 0.96F;
    }

    @Override
    protected boolean selfPropelledFlyer() {
        return getType() == 6;
    }

    @Override
    public void makeEntityJump() {
        if (this.jumpCounter > 5) {
            this.jumpCounter = 1;
        }
        if (this.jumpCounter == 0) {
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_WINGFLAP);
            this.jumpPending = true;
            this.jumpCounter = 1;
        }

    }

    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        if (getType() == 7) {
            return EnumCreatureAttribute.UNDEAD;
        }
        return super.getCreatureAttribute();
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 1;
    }

    //TODO 
    //improve fall flapping wing animation
    //IMPROVE DIVE CODE
    //ATTACK!
    //EGG LYING

    @Override
    public int getMaxEdad() {
        return 20;
    }
}
