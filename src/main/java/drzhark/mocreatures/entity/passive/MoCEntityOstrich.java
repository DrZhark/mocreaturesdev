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
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

import java.util.List;

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
    public MoCAnimalChest emptychest;
    public ItemStack localstack;

    public MoCEntityOstrich(World world) {
        super(world);
        setSize(1.0F, 1.6F);
        setEdad(35);
        this.eggCounter = this.rand.nextInt(1000) + 1000;
        this.stepHeight = 1.0F;
        this.canLayEggs = false;
        ((PathNavigateGround) this.getNavigator()).setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(4, new EntityAIFollowAdult(this, 1.0D));
        this.tasks.addTask(5, new EntityAIAttackOnCollide(this, 1.0D, true));
        this.tasks.addTask(6, new EntityAIWanderMoC2(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(23, Byte.valueOf((byte) 0)); // eggWatch - 0 false 1 true
        this.dataWatcher.addObject(24, Byte.valueOf((byte) 0)); // hiding - 0 false 1 true
        this.dataWatcher.addObject(25, Byte.valueOf((byte) 0)); // helmet - 0 none
        this.dataWatcher.addObject(26, Byte.valueOf((byte) 0)); // flagcolor - 0 white
        this.dataWatcher.addObject(27, Byte.valueOf((byte) 0)); // bagged - 0 false 1 true
        this.dataWatcher.addObject(28, Byte.valueOf((byte) 0)); // isRideable - 0 false 1 true
    }

    @Override
    public boolean getIsRideable() {
        return (this.dataWatcher.getWatchableObjectByte(28) == 1);
    }

    @Override
    public void setRideable(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(28, Byte.valueOf(input));
    }

    public boolean getEggWatching() {
        return (this.dataWatcher.getWatchableObjectByte(23) == 1);
    }

    public void setEggWatching(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(23, Byte.valueOf(input));
    }

    public boolean getHiding() {
        return (this.dataWatcher.getWatchableObjectByte(24) == 1);
    }

    public void setHiding(boolean flag) {
        if (this.worldObj.isRemote) {
            return;
        }
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(24, Byte.valueOf(input));
    }

    public byte getHelmet() {
        return (this.dataWatcher.getWatchableObjectByte(25));
    }

    public void setHelmet(byte b) {
        this.dataWatcher.updateObject(25, Byte.valueOf(b));
    }

    public byte getFlagColor() {
        return (this.dataWatcher.getWatchableObjectByte(26));
    }

    public void setFlagColor(byte b) {
        this.dataWatcher.updateObject(26, Byte.valueOf(b));
    }

    public boolean getIsChested() {
        return (this.dataWatcher.getWatchableObjectByte(27) == 1);
    }

    public void setIsChested(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(27, Byte.valueOf(input));
    }

    @Override
    public boolean isMovementCeased() {
        return (getHiding() || this.riddenByEntity != null);
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

            if (!(entity instanceof EntityLivingBase) || ((this.riddenByEntity != null) && (entity == this.riddenByEntity))
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
        return this.riddenByEntity == null;
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
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(calculateMaxHealth());
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
                MoCTools.playCustomSound(this, "transform", this.worldObj);
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
                    new TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 64));
        }
        this.transformType = tType;
        if (this.riddenByEntity == null && this.transformType != 0) {
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
            if (getType() == 8 && (this.sprintCounter > 0 && this.sprintCounter < 150) && (this.riddenByEntity != null) && rand.nextInt(15) == 0) {
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
                            this.worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F,
                                    (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
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
                    PathEntity pathentity = this.navigator.getPathToPos(myEgg.getPosition());
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
    public boolean interact(EntityPlayer entityplayer) {
        if (super.interact(entityplayer)) {
            return false;
        }
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();

        if (getIsTamed() && (getType() > 1) && (itemstack != null) && !getIsRideable()
                && (itemstack.getItem() == MoCreatures.horsesaddle || itemstack.getItem() == Items.saddle)) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            this.worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F) + 1.0F);
            setRideable(true);
            return true;
        }

        if (!getIsTamed() && itemstack != null && getType() == 2 && itemstack.getItem() == Items.melon_seeds) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }

            openMouth();
            MoCTools.playCustomSound(this, "mocreatures:eating", this.worldObj);
            this.canLayEggs = true;
            return true;
        }

        //makes the ostrich stay by hiding their heads
        if ((itemstack != null) && (itemstack.getItem() == MoCreatures.whip) && getIsTamed() && (this.riddenByEntity == null)) {
            setHiding(!getHiding());
            return true;
        }

        if ((itemstack != null) && this.getIsTamed() && getType() > 1 && itemstack.getItem() == MoCreatures.essencedarkness) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Items.glass_bottle));
            } else {
                entityplayer.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
            }
            if (getType() == 6) {
                this.setHealth(getMaxHealth());
            } else {
                transform(6);
            }
            MoCTools.playCustomSound(this, "drinking", this.worldObj);
            return true;
        }

        if ((itemstack != null) && this.getIsTamed() && getType() > 1 && itemstack.getItem() == MoCreatures.essenceundead) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Items.glass_bottle));
            } else {
                entityplayer.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
            }
            if (getType() == 7) {
                this.setHealth(getMaxHealth());
            } else {
                transform(7);
            }
            MoCTools.playCustomSound(this, "drinking", this.worldObj);
            return true;
        }

        if ((itemstack != null) && this.getIsTamed() && getType() > 1 && itemstack.getItem() == MoCreatures.essencelight) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Items.glass_bottle));
            } else {
                entityplayer.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
            }
            if (getType() == 8) {
                this.setHealth(getMaxHealth());
            } else {
                transform(8);
            }
            MoCTools.playCustomSound(this, "drinking", this.worldObj);
            return true;
        }

        if ((itemstack != null) && this.getIsTamed() && getType() > 1 && itemstack.getItem() == MoCreatures.essencefire) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Items.glass_bottle));
            } else {
                entityplayer.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
            }
            if (getType() == 5) {
                this.setHealth(getMaxHealth());
            } else {
                transform(5);
            }
            MoCTools.playCustomSound(this, "drinking", this.worldObj);
            return true;
        }
        if (getIsTamed() && getIsChested() && (getType() > 1) && itemstack != null && itemstack.getItem() == Item.getItemFromBlock(Blocks.wool)) {
            int colorInt = (itemstack.getItemDamage());
            if (colorInt == 0) {
                colorInt = 16;
            }
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            MoCTools.playCustomSound(this, "mob.chickenplop", this.worldObj);
            dropFlag();
            setFlagColor((byte) colorInt);
            return true;
        }

        if ((itemstack != null) && (getType() > 1) && getIsTamed() && !getIsChested() && (itemstack.getItem() == Item.getItemFromBlock(Blocks.chest))) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }

            //entityplayer.inventory.addItemStackToInventory(new ItemStack(MoCreatures.key));
            setIsChested(true);
            this.worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F) + 1.0F);
            return true;
        }

        if (entityplayer.isSneaking() && getIsChested()) {
            // if first time opening a chest, we must initialize it
            if (this.localchest == null) {
                this.localchest = new MoCAnimalChest("OstrichChest", 9);
            }
            if (this.emptychest == null) {
                this.emptychest = new MoCAnimalChest("OstrichChest", 0);
            }
            if (MoCreatures.isServer()) {

                InventoryLargeChest singleChest = new InventoryLargeChest("OstrichChest", this.localchest, this.emptychest);
                entityplayer.displayGUIChest(singleChest);
            }
            return true;
        }

        if (getIsTamed() && (getType() > 1) && itemstack != null) {

            Item item = itemstack.getItem();
            if (item instanceof ItemArmor) {
                byte helmetType = 0;
                if (itemstack.getItem() == Items.leather_helmet) {
                    helmetType = 1;
                } else if (itemstack.getItem() == Items.iron_helmet) {
                    helmetType = 2;
                } else if (itemstack.getItem() == Items.golden_helmet) {
                    helmetType = 3;
                } else if (itemstack.getItem() == Items.diamond_helmet) {
                    helmetType = 4;
                } else if (itemstack.getItem() == MoCreatures.helmetHide) {
                    helmetType = 5;
                } else if (itemstack.getItem() == MoCreatures.helmetFur) {
                    helmetType = 6;
                } else if (itemstack.getItem() == MoCreatures.helmetCroc) {
                    helmetType = 7;
                }
                /*
                 * else if (itemstack.getItem() == MoCreatures.helmetGreen) {
                 * helmetType = 8; }
                 */
                else if (itemstack.getItem() == MoCreatures.scorpHelmetDirt) {
                    helmetType = 9;
                } else if (itemstack.getItem() == MoCreatures.scorpHelmetFrost) {
                    helmetType = 10;
                } else if (itemstack.getItem() == MoCreatures.scorpHelmetCave) {
                    helmetType = 11;
                } else if (itemstack.getItem() == MoCreatures.scorpHelmetNether) {
                    helmetType = 12;
                }

                if (helmetType != 0) {
                    entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                    dropArmor();
                    MoCTools.playCustomSound(this, "armoroff", this.worldObj);
                    setHelmet(helmetType);
                    return true;
                }

            }
        }
        if (getIsRideable() && getIsAdult() && (this.riddenByEntity == null)) {
            entityplayer.rotationYaw = this.rotationYaw;
            entityplayer.rotationPitch = this.rotationPitch;
            setHiding(false);
            if (!this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity == entityplayer)) {
                entityplayer.mountEntity(this);
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
            EntityItem entityitem = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(Blocks.wool, 1, color));
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
    protected String getHurtSound() {
        openMouth();
        return "mocreatures:ostrichhurt";
    }

    @Override
    protected String getLivingSound() {
        openMouth();
        if (getType() == 1) {
            return "mocreatures:ostrichchick";
        }

        return "mocreatures:ostrichgrunt";
    }

    @Override
    protected String getDeathSound() {
        openMouth();
        return "mocreatures:ostrichdying";
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
            return Items.rotten_flesh;
        }
        return MoCreatures.ostrichraw;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        setRideable(nbttagcompound.getBoolean("Saddle"));
        setEggWatching(nbttagcompound.getBoolean("EggWatch"));
        setHiding(nbttagcompound.getBoolean("Hiding"));
        setHelmet(nbttagcompound.getByte("Helmet"));
        setFlagColor(nbttagcompound.getByte("FlagColor"));
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
        nbttagcompound.setByte("Helmet", getHelmet());
        nbttagcompound.setByte("FlagColor", getFlagColor());
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
                MoCTools.dropCustomItem(this, this.worldObj, new ItemStack(Blocks.chest, 1));
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
            EntityItem entityitem = null;// = new EntityItem(worldObj, posX, posY, posZ, new ItemStack(Blocks.wool, 1, color));

            switch (getHelmet()) {
                case 0:
                case 8:
                    return;
                    //break;
                case 1:
                    entityitem = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(Items.leather_helmet, 1));
                    break;
                case 2:
                    entityitem = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(Items.iron_helmet, 1));
                    break;
                case 3:
                    entityitem = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(Items.golden_helmet, 1));
                    break;
                case 4:
                    entityitem = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(Items.diamond_helmet, 1));
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
        return this.riddenByEntity != null && (getType() == 5 || getType() == 6);
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
            MoCTools.playCustomSound(this, "wingflap", this.worldObj);
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
