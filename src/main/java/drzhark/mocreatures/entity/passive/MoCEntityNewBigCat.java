package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.IMoCTameable;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.EntityAIFollowAdult;
import drzhark.mocreatures.entity.ai.EntityAIFollowOwnerPlayer;
import drzhark.mocreatures.entity.ai.EntityAIHunt;
import drzhark.mocreatures.entity.ai.EntityAINearestAttackableTargetMoC;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.inventory.MoCAnimalChest;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import drzhark.mocreatures.network.message.MoCMessageHeart;
import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

import java.util.List;

public class MoCEntityNewBigCat extends MoCEntityTameableAnimal {

    public int mouthCounter;
    public int tailCounter;
    public int wingFlapCounter;
    private int gestationtime;
    public MoCAnimalChest localchest;
    public MoCAnimalChest emptychest;
    public ItemStack localstack;
    private int tCounter;
    private float fTransparency;
    private boolean hasEaten;

    public MoCEntityNewBigCat(World world) {
        super(world);
        setEdad(45);
        setSize(1.4F, 1.3F);
        if (this.rand.nextInt(4) == 0) {
            setAdult(false);

        } else {
            setAdult(true);
        }
        setTamed(false);
        this.stepHeight = 1.0F;

        ((PathNavigateGround) this.getNavigator()).setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackOnCollide(this, 1.0D, true));
        //this.tasks.addTask(3, new EntityAIFleeFromPlayer(this, 0.8D, 4D));
        this.tasks.addTask(4, new EntityAIFollowAdult(this, 1.0D));
        this.tasks.addTask(5, new EntityAIFollowOwnerPlayer(this, 1D, 2F, 10F));
        this.tasks.addTask(2, new EntityAIWanderMoC2(this, 0.8D, 30));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        //this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this)); //TODO
        //this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this)); //TODO
        this.targetTasks.addTask(3, new EntityAINearestAttackableTargetMoC(this, EntityPlayer.class, true));
        this.targetTasks.addTask(4, new EntityAIHunt(this, EntityAnimal.class, true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(8.0D);
    }

    /**
     * Sets the type and texture of a BigCat if not set already.
     */
    @Override
    public void selectType() {
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(calculateMaxHealth());
        this.setHealth(getMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(calculateAttackDmg());
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(getAttackRange());
        if (getIsAdult()) {
            setEdad(getMaxEdad());
        }
    }

    @Override
    public double getCustomSpeed() {
        return 2D;
    }

    public float getMoveSpeed() {
        return 1.4F;
    }

    public float calculateMaxHealth() {
        return 20F;
    }

    public double calculateAttackDmg() {
        return 5D;
    }

    public double getAttackRange() {
        return 6D;
    }

    /**
     * Initializes datawatchers for entity. Each datawatcher is used to sync
     * server data to client.
     */
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(28, Byte.valueOf((byte) 0)); // hasAmulet  -  0 false 1 true
        this.dataWatcher.addObject(24, Byte.valueOf((byte) 0)); // isSitting -  0 false 1 true
        this.dataWatcher.addObject(25, Byte.valueOf((byte) 0)); // isGhost -    0 false 1 true
        this.dataWatcher.addObject(26, Byte.valueOf((byte) 0)); // isChested -  0 false 1 true
        this.dataWatcher.addObject(27, Byte.valueOf((byte) 0)); // isRideable - 0 false 1 true
    }

    public boolean getHasEaten() {
        return this.hasEaten;
        //return (this.dataWatcher.getWatchableObjectByte(23) == 1);
    }

    public boolean getHasAmulet() {
        return (this.dataWatcher.getWatchableObjectByte(28) == 1);
    }

    @Override
    public boolean getIsSitting() {
        return (this.dataWatcher.getWatchableObjectByte(24) == 1);
    }

    @Override
    public boolean getIsRideable() {
        return (this.dataWatcher.getWatchableObjectByte(27) == 1);
    }

    public boolean getIsChested() {
        return (this.dataWatcher.getWatchableObjectByte(26) == 1);
    }

    public boolean getIsGhost() {
        return (this.dataWatcher.getWatchableObjectByte(25) == 1);
    }

    public void setEaten(boolean flag) {
        this.hasEaten = flag;
        //byte input = (byte) (flag ? 1 : 0);
        //this.dataWatcher.updateObject(23, Byte.valueOf(input));
    }

    public void setHasAmulet(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(28, Byte.valueOf(input));
    }

    public void setSitting(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(24, Byte.valueOf(input));
    }

    public void setIsChested(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(26, Byte.valueOf(input));
    }

    public void setRideable(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(27, Byte.valueOf(input));
    }

    public void setIsGhost(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(25, Byte.valueOf(input));
    }

    // Method used for receiving damage from another source
    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        Entity entity = damagesource.getEntity();
        if ((this.riddenByEntity != null) && (entity == this.riddenByEntity)) {
            return false;
        }

        if (super.attackEntityFrom(damagesource, i)) {
            if (entity != null && getIsTamed() && entity instanceof EntityPlayer) {
                return false;
            }
            if (entity != this && entity instanceof EntityLivingBase && (this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL)) {
                setAttackTarget((EntityLivingBase) entity);
            }
            return true;
        } else {
            return false;
        }
    }

    //TODO
    /*@Override
    protected Entity findPlayerToAttack() {
        if (this.roper != null && this.roper instanceof EntityPlayer) {
            return getMastersEnemy((EntityPlayer) this.roper, 12D);
        }

        this.worldObj.getDifficulty();
        if (this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL && MoCreatures.isHuntingEnabled()) {
            EntityPlayer entityplayer = this.worldObj.getClosestPlayerToEntity(this, getAttackRange());
            if (!getIsTamed() && (entityplayer != null) && getIsAdult() && getIsHungry()) {
                if ((getType() == 1) || (getType() == 5) || (getType() == 7)) {
                    setHungry(false);
                    return entityplayer;
                }
                if (this.rand.nextInt(30) == 0) {
                    setHungry(false);
                    return entityplayer;
                }
            }
            if ((this.rand.nextInt(80) == 0) && getIsHungry()) {
                EntityLivingBase entityliving = getClosestTarget(this, getAttackRange());
                setHungry(false);
                return entityliving;
            }
        }
        return null;
    }*/

    @Override
    protected String getDeathSound() {
        openMouth();
        if (getIsAdult()) {
            return "mocreatures:liondeath";
        } else {
            return "mocreatures:cubdying";
        }
    }

    @Override
    protected Item getDropItem() {
        return MoCreatures.bigcatclaw;
    }

    @Override
    protected String getHurtSound() {
        openMouth();
        if (getIsAdult()) {
            return "mocreatures:lionhurt";
        } else {
            return "mocreatures:cubhurt";
        }
    }

    @Override
    protected String getLivingSound() {
        openMouth();
        if (getIsAdult()) {
            return "mocreatures:liongrunt";
        } else {
            return "mocreatures:cubgrunt";
        }
    }

    /*public EntityCreature getMastersEnemy(EntityPlayer entityplayer, double d) {
        double d1 = -1D;
        EntityCreature entitycreature = null;
        List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(entityplayer, getEntityBoundingBox().expand(d, 4D, d));
        for (int i = 0; i < list.size(); i++) {
            Entity entity = (Entity) list.get(i);
            if (!(entity instanceof EntityCreature) || (entity == this)) {
                continue;
            }
            EntityCreature entitycreature1 = (EntityCreature) entity;
            if ((entitycreature1 != null) && (entitycreature1.getAttackTarget() == entityplayer)) {
                return entitycreature1;
            }
        }

        return entitycreature;
    }*/

    /**
     * Checks if bigcat is sitting.
     */
    @Override
    public boolean isMovementCeased() {
        return getIsSitting();
    }

    @Override
    public void onDeath(DamageSource damagesource) {
        if (MoCreatures.isServer()) {
            if (getHasAmulet()) {
                    MoCTools.dropCustomItem(this, this.worldObj, new ItemStack(MoCreatures.medallion, 1));
                    setHasAmulet(false);
            }

            if (getIsTamed() && !getIsGhost() && this.rand.nextInt(4) == 0) {
                this.spawnGhost();
            }
        }
        super.onDeath(damagesource);

    }

    public void spawnGhost() {
        try {
            EntityLiving templiving = MoCTools.spawnListByNameClass(this.getClazzString(), this.worldObj);
            if (templiving != null && templiving instanceof MoCEntityNewBigCat) {
                MoCEntityNewBigCat ghost = (MoCEntityNewBigCat) templiving;
                ghost.setPosition(this.posX, this.posY, this.posZ);
                this.worldObj.spawnEntityInWorld(ghost);
                MoCTools.playCustomSound(this, "appearmagic", this.worldObj);
                ghost.setOwner(this.getOwnerName());
                ghost.setTamed(true);
                EntityPlayer entityplayer = this.worldObj.getClosestPlayerToEntity(this, 24D);
                if (entityplayer != null) {
                    MoCTools.tameWithName(entityplayer, ghost);
                }

                ghost.setAdult(false);
                ghost.setEdad(1);
                ghost.setType(this.getType());
                ghost.selectType();
                ghost.setIsGhost(true);

            }
        } catch (Exception e) {
        }

    }

    @Override
    public void onLivingUpdate() {

        super.onLivingUpdate();

        if (!MoCreatures.isServer()) //animation counters
        {
            if (this.mouthCounter > 0 && ++this.mouthCounter > 30) {
                this.mouthCounter = 0;
            }

            if (this.rand.nextInt(250) == 0) {
                moveTail();
            }

            if (this.tailCounter > 0 && ++this.tailCounter > 10 && this.rand.nextInt(15) == 0) {
                this.tailCounter = 0;
            }
        } else //server stuff
        {
            if (getIsGhost() && getEdad() < 10 && this.rand.nextInt(5) == 0) {
                setEdad(getEdad() + 1);
                if (getEdad() == 9) {
                    setEdad(getMaxEdad());
                    setAdult(true);
                }
            }

            /*if (getHasEaten() && rand.nextInt(300) == 0)
            {
                setEaten(false);
            }*/
        }

        if (MoCreatures.isServer() && isFlyer() && isOnAir()) {
            float myFlyingSpeed = MoCTools.getMyMovementSpeed(this);
            int wingFlapFreq = (int) (25 - (myFlyingSpeed * 10));
            if (this.riddenByEntity == null || wingFlapFreq < 5) {
                wingFlapFreq = 5;
            }
            if (this.rand.nextInt(wingFlapFreq) == 0) {
                wingFlap();
            }
        }

        if (isFlyer()) {
            if (this.wingFlapCounter > 0 && ++this.wingFlapCounter > 20) {
                this.wingFlapCounter = 0;
            }
            if (this.wingFlapCounter == 5 && MoCreatures.isServer()) {
                MoCTools.playCustomSound(this, "wingflap", this.worldObj);
            }
        }

        if ((this.rand.nextInt(300) == 0) && (this.getHealth() <= getMaxHealth()) && (this.deathTime == 0) && !this.worldObj.isRemote) {
            this.setHealth(getHealth() + 1);
        }

        //TODO move to New AI
        if ((this.deathTime == 0) && !isMovementCeased()) {
            EntityItem entityitem = getClosestItem(this, 12D, Items.porkchop, Items.fish);
            if (entityitem != null) {
                float f = entityitem.getDistanceToEntity(this);
                if (f > 2.0F) {
                    getMyOwnPath(entityitem, f);
                }
                if ((f < 2.0F) && (entityitem != null) && (this.deathTime == 0)) {
                    entityitem.setDead();
                    this.setHealth(getMaxHealth());
                    setEaten(true);
                    this.worldObj
                            .playSoundAtEntity(this, "mocreatures:eating", 1.0F, 1.0F + ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F));
                }
            }
        }

        //breeding code
        if (MoCreatures.isServer() && readytoBreed() && this.rand.nextInt(100) == 0) {
            doBreeding();
        }

    }

    private void doBreeding() {
        int i = 0;

        List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(8D, 3D, 8D));
        for (int j = 0; j < list.size(); j++) {
            Entity entity = (Entity) list.get(j);
            if (compatibleMate(entity)) {
                i++;
            }
        }

        if (i > 1) {
            return;
        }

        List list1 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(4D, 2D, 4D));
        for (int k = 0; k < list1.size(); k++) {
            Entity mate = (Entity) list1.get(k);
            if (!(compatibleMate(mate)) || (mate == this)) {
                continue;
            }

            if (!this.readytoBreed()) {
                return;
            }

            if (!((IMoCTameable) mate).readytoBreed()) {
                return;
            }

            this.gestationtime++;
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageHeart(this.getEntityId()),
                    new TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 64));

            if (this.gestationtime <= 50) {
                continue;
            }

            try {

                String offspringClass = this.getOffspringClazz((IMoCTameable) mate);

                EntityLiving offspring = MoCTools.spawnListByNameClass(offspringClass, this.worldObj);
                if (offspring != null && offspring instanceof IMoCTameable) {
                    IMoCTameable baby = (IMoCTameable) offspring;
                    ((EntityLiving) baby).setPosition(this.posX, this.posY, this.posZ);
                    this.worldObj.spawnEntityInWorld((EntityLiving) baby);
                    baby.setAdult(false);
                    baby.setEdad(35);
                    baby.setTamed(true);
                    baby.setOwner(this.getOwnerName());
                    baby.setType(getOffspringTypeInt((IMoCTameable) mate));

                    EntityPlayer entityplayer = this.worldObj.getPlayerEntityByName(this.getOwnerName());
                    if (entityplayer != null) {
                        MoCTools.tameWithName(entityplayer, baby);
                    }
                }
                this.worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F) + 1.0F);

            } catch (Exception e) {
            }

            this.setEaten(false);
            this.gestationtime = 0;
            ((MoCEntityNewBigCat) mate).setEaten(false);
            ((MoCEntityNewBigCat) mate).gestationtime = 0;
            break;
        }
    }

    public String getOffspringClazz(IMoCTameable mate) {
        return "";
    }

    public int getOffspringTypeInt(IMoCTameable mate) {
        return 0;
    }

    public boolean compatibleMate(Entity mate) {
        return mate instanceof IMoCTameable;
    }

    @Override
    public boolean readytoBreed() {
        return this.riddenByEntity == null && this.getRidingEntity() == null && this.getIsTamed() && this.getHasEaten() && this.getIsAdult()
                && !this.getIsGhost();
    }

    public void wingFlap() {
        if (this.wingFlapCounter == 0) {
            this.wingFlapCounter = 1;
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 3),
                    new TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 64));
        }
    }

    @Override
    public boolean isNotScared() {
        return getIsAdult() || getEdad() > 80;
    }

    @Override
    public boolean isReadyToHunt() {
        return getIsAdult() && !this.isMovementCeased();
    }

    /*@Override
    public boolean canAttackTarget(EntityLivingBase entity) {
        if (entity instanceof MoCEntityNewBigCat) {
            if (!this.getIsAdult()) {
                return false;
            }

            MoCEntityNewBigCat entitybigcat = (MoCEntityNewBigCat) entity;
            if ((this.getIsTamed() && entitybigcat.getIsTamed()) || (entitybigcat.getType() == 7)
                    || ((this.getType() != 2) && (this.getType() == entitybigcat.getType()))
                    || ((this.getType() == 2) && (entitybigcat.getType() == 1))) {
                return false;
            }
            return true;
        }
        return entity.height <= 1.5D && entity.width <= 1.5D;
    }*/

    @Override
    public void updateRiderPosition() {
        double dist = getSizeFactor() * (0.1D);
        double newPosX = this.posX + (dist * Math.sin(this.renderYawOffset / 57.29578F));
        double newPosZ = this.posZ - (dist * Math.cos(this.renderYawOffset / 57.29578F));
        this.riddenByEntity.setPosition(newPosX, this.posY + getMountedYOffset() + this.riddenByEntity.getYOffset(), newPosZ);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("Saddle", getIsRideable());
        nbttagcompound.setBoolean("Sitting", getIsSitting());
        nbttagcompound.setBoolean("Chested", getIsChested());

        if (getIsChested() && this.localchest != null) {
            NBTTagList nbttaglist = new NBTTagList();
            for (int i = 0; i < this.localchest.getSizeInventory(); i++) {
                // grab the current item stack
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
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        setRideable(nbttagcompound.getBoolean("Saddle"));
        setSitting(nbttagcompound.getBoolean("Sitting"));
        setIsChested(nbttagcompound.getBoolean("Chested"));

        if (getIsChested()) {
            NBTTagList nbttaglist = nbttagcompound.getTagList("Items", 10);
            this.localchest = new MoCAnimalChest("BigCatChest", 18);

            for (int i = 0; i < nbttaglist.tagCount(); i++) {
                ItemStack itemstack = this.localchest.getStackInSlot(i);

                if (itemstack != null) {
                    this.localchest.setInventorySlotContents(i, itemstack.copy());
                }
            }
        }

    }

    @Override
    public boolean interact(EntityPlayer entityplayer) {
        if (super.interact(entityplayer)) {
            return false;
        }

        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if ((itemstack != null) && !getIsTamed() && getHasEaten() && !getIsAdult() && (itemstack.getItem() == MoCreatures.medallion)) {
            if (MoCreatures.isServer()) {
                setHasAmulet(true);
                MoCTools.tameWithName(entityplayer, this);
            }
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                return true;
            }
            return true;
        }

        if ((itemstack != null) && getIsTamed() && !getHasAmulet() && (itemstack.getItem() == MoCreatures.medallion)) {
            if (MoCreatures.isServer()) {
                setHasAmulet(true);
            }
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                return true;
        }
            return true;
        }
        
        if ((itemstack != null) && getIsTamed() && (itemstack.getItem() == MoCreatures.whip)) {
            setSitting(!getIsSitting());
            return true;
        }
        if ((itemstack != null) && getIsTamed() && (itemstack.getItem() == Items.porkchop || itemstack.getItem() == Items.fish)) {
            this.setHealth(getMaxHealth());
            this.worldObj.playSoundAtEntity(this, "mocreatures:eating", 1.0F, 1.0F + ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F));
            setIsHunting(false);
            setEaten(true);
            return true;
        }

        if ((itemstack != null) && getIsTamed() && !getIsRideable() && (getEdad() > 80)
                && (itemstack.getItem() == Items.saddle || itemstack.getItem() == MoCreatures.horsesaddle)) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            setRideable(true);
            return true;
        }

        if ((itemstack != null) && getIsTamed() && getIsAdult() && !getIsChested() && (itemstack.getItem() == Item.getItemFromBlock(Blocks.chest))) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            setIsChested(true);
            this.worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F) + 1.0F);
            return true;
        }

        if (getIsChested() && entityplayer.isSneaking()) {
            if (this.localchest == null) {
                this.localchest = new MoCAnimalChest("BigCatChest", 18);
            }
            if (this.emptychest == null) {
                this.emptychest = new MoCAnimalChest("BigCatChest", 0);
            }
            if (MoCreatures.isServer()) {

                InventoryLargeChest singleChest = new InventoryLargeChest("BigCatChest", this.localchest, this.emptychest);
                entityplayer.displayGUIChest(singleChest);
            }
            return true;
        }

        return false;
    }

    @Override
    public float getSizeFactor() {
        return getEdad() * 0.01F;
    }

    @Override
    public void fall(float f, float f1) {
        if (isFlyer()) {
            return;
        }

        float i = (float) (Math.ceil(f - 3F) / 2F);
        if (MoCreatures.isServer() && (i > 0)) {
            i /= 2;
            if (i > 1F) {
                attackEntityFrom(DamageSource.fall, i);
            }
            if ((this.riddenByEntity != null) && (i > 1F)) {
                this.riddenByEntity.attackEntityFrom(DamageSource.fall, i);
            }

            Block block =
                    this.worldObj.getBlockState(
                            new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY - 0.20000000298023221D
                                    - this.prevRotationPitch), MathHelper.floor_double(this.posZ))).getBlock();
            if (block != Blocks.AIR) {
                SoundType stepsound = block.stepSound;
                this.worldObj.playSoundAtEntity(this, stepsound.getStepSound(), stepsound.getVolume() * 0.5F, stepsound.getFrequency() * 0.75F);
            }
        }
    }

    private void openMouth() {
        this.mouthCounter = 1;
    }

    public boolean hasMane() {
        return false;
    }

    @Override
    public int getTalkInterval() {
        return 400;
    }

    private void moveTail() {
        this.tailCounter = 1;
    }

    public boolean hasSaberTeeth() {
        return false;
    }

    @Override
    public void performAnimation(int animationType) {
        if (animationType == 0) //tail animation
        {
            //setPoisoning(true);
        } else if (animationType == 3) //wing flap
        {
            this.wingFlapCounter = 1;
        }
    }

    @Override
    public void makeEntityJump() {
        if (this.isFlyer()) {
            wingFlap();
        }
        super.makeEntityJump();
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

    public boolean getHasStinger() {
        return false;
    }

    @Override
    public double getMountedYOffset() {
        double Yfactor = ((0.0833D * this.getEdad()) - 2.5D) / 10D;
        return this.height * Yfactor;
    }

    public float tFloat() {

        if (++this.tCounter > 30) {
            this.tCounter = 0;
            this.fTransparency = (this.rand.nextFloat() * (0.4F - 0.2F) + 0.15F);
        }

        if (this.getEdad() < 10) {
            return 0F;
        }
        return this.fTransparency;
    }

    public String getClazzString() {
        return "";
    }

    @Override
    public int nameYOffset() {
        return (int) (((0.445D * this.getEdad()) + 15D) * -1);
    }
}

//TODO

//would be nice
//lying down
//manticore sounds, drops
//cheetahs
//hand swing when attacking
//more hybrids
//jaguars
//lynx / bobcats