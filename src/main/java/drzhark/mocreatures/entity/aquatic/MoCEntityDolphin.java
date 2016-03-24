package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAquatic;
import drzhark.mocreatures.entity.ai.EntityAIPanicMoC;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageHeart;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

import java.util.List;

public class MoCEntityDolphin extends MoCEntityTameableAquatic {

    public int gestationtime;

    public MoCEntityDolphin(World world) {
        super(world);
        setSize(1.5F, 0.8F);
        setEdad(60 + this.rand.nextInt(100));
        this.tasks.addTask(1, new EntityAIPanicMoC(this, 1.3D));
        this.tasks.addTask(5, new EntityAIWanderMoC2(this, 1.0D, 30));

    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5.0D);
    }

    @Override
    public void selectType() {
        if (getType() == 0) {
            int i = this.rand.nextInt(100);
            if (i <= 35) {
                setType(1);
            } else if (i <= 60) {
                setType(2);
            } else if (i <= 85) {
                setType(3);
            } else if (i <= 96) {
                setType(4);
            } else if (i <= 98) {
                setType(5);
            } else {
                setType(6);
            }
        }
    }

    @Override
    public ResourceLocation getTexture() {

        switch (getType()) {
            case 1:
                return MoCreatures.proxy.getTexture("dolphin.png");
            case 2:
                return MoCreatures.proxy.getTexture("dolphin2.png");
            case 3:
                return MoCreatures.proxy.getTexture("dolphin3.png");
            case 4:
                return MoCreatures.proxy.getTexture("dolphin4.png");
            case 5:
                return MoCreatures.proxy.getTexture("dolphin5.png");
            case 6:
                return MoCreatures.proxy.getTexture("dolphin6.png");
            default:
                return MoCreatures.proxy.getTexture("dolphin.png");
        }
    }

    @Override
    public int getMaxTemper() {

        switch (getType()) {
            case 1:
                return 50;
            case 2:
                return 100;
            case 3:
                return 150;
            case 4:
                return 200;
            case 5:
                return 250;
            case 6:
                return 300;
            default:
                return 100;
        }
    }

    public int getInitialTemper() {
        switch (getType()) {
            case 1:
                return 50;
            case 2:
                return 100;
            case 3:
                return 150;
            case 4:
                return 200;
            case 5:
                return 250;
            case 6:
                return 300;
            default:
                return 50;
        }
    }

    @Override
    public double getCustomSpeed() {
        switch (getType()) {
            case 1:
                return 1.5D;
            case 2:
                return 2.0D;
            case 3:
                return 2.5D;
            case 4:
                return 3.D;
            case 5:
                return 3.5D;
            case 6:
                return 4.D;
            default:
                return 1.5D;
        }
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(24, Byte.valueOf((byte) 0)); // byte isHungry 0 = false 1 = true
        this.dataWatcher.addObject(23, Byte.valueOf((byte) 0)); // byte hasEaten 0 = false 1 = true
    }

    public boolean getIsHungry() {
        return (this.dataWatcher.getWatchableObjectByte(24) == 1);
    }

    public boolean getHasEaten() {
        return (this.dataWatcher.getWatchableObjectByte(23) == 1);
    }

    public void setIsHungry(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(24, Byte.valueOf(input));
    }

    public void setHasEaten(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(23, Byte.valueOf(input));
    }

    //TODO
    /*@Override
    protected void attackEntity(Entity entity, float f) {
        if (attackTime <= 0 && (f < 3.5D) && (entity.getEntityBoundingBox().maxY > getEntityBoundingBox().minY)
                && (entity.getEntityBoundingBox().minY < getEntityBoundingBox().maxY) && (getEdad() >= 100)) {
            attackTime = 20;
            entity.attackEntityFrom(DamageSource.causeMobDamage(this), 5);
        }
    }*/

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (super.attackEntityFrom(damagesource, i) && (this.worldObj.getDifficulty().getDifficultyId() > 0)) {
            Entity entity = damagesource.getEntity();
            if (entity instanceof EntityLivingBase) {
                EntityLivingBase entityliving = (EntityLivingBase) entity;
                if ((this.riddenByEntity == entity) || (this.ridingEntity == entity)) {
                    return true;
                }
                if (entity != this && this.getEdad() >= 100) {
                    setAttackTarget(entityliving);
                }
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    @Override
    public boolean canBeCollidedWith() {
        return this.riddenByEntity == null;
    }

    //TODO
    /*public EntityLivingBase FindTarget(Entity entity, double d) {
        double d1 = -1D;
        EntityLivingBase entityliving = null;
        List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(d, d, d));
        for (int i = 0; i < list.size(); i++) {
            Entity entity1 = (Entity) list.get(i);
            if (!(entity1 instanceof MoCEntityShark) || (entity1 instanceof MoCEntityShark && ((MoCEntityShark) entity1).getIsTamed())) {
                continue;
            }
            double d2 = entity1.getDistanceSq(entity.posX, entity.posY, entity.posZ);
            if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1)) && ((EntityLivingBase) entity1).canEntityBeSeen(entity)) {
                d1 = d2;
                entityliving = (EntityLivingBase) entity1;
            }
        }

        return entityliving;
    }*/

    private int Genetics(MoCEntityDolphin entitydolphin, MoCEntityDolphin entitydolphin1) {
        if (entitydolphin.getType() == entitydolphin1.getType()) {
            return entitydolphin.getType();
        }
        int i = entitydolphin.getType() + entitydolphin1.getType();
        boolean flag = this.rand.nextInt(3) == 0;
        boolean flag1 = this.rand.nextInt(10) == 0;
        if ((i < 5) && flag) {
            return i;
        }
        if (((i == 5) || (i == 6)) && flag1) {
            return i;
        } else {
            return 0;
        }
    }

    @Override
    protected String getDeathSound() {
        return "mocreatures:dolphindying";
    }

    @Override
    protected Item getDropItem() {
        return Items.fish;
    }

    @Override
    protected String getHurtSound() {
        return "mocreatures:dolphinhurt";
    }

    @Override
    protected String getLivingSound() {
        return "mocreatures:dolphin";
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    @Override
    protected String getUpsetSound() {
        return "mocreatures:dolphinupset";
    }

    @Override
    public boolean interact(EntityPlayer entityplayer) {
        if (super.interact(entityplayer)) {
            return false;
        }

        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if ((itemstack != null) && (itemstack.getItem() == Items.fish)) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            if (MoCreatures.isServer()) {
                setTemper(getTemper() + 25);
                if (getTemper() > getMaxTemper()) {
                    setTemper(getMaxTemper() - 1);
                }

                if ((getHealth() + 15) > getMaxHealth()) {
                    this.setHealth(getMaxHealth());
                }

                if (!getIsAdult()) {
                    setEdad(getEdad() + 1);
                }
            }

            this.worldObj.playSoundAtEntity(this, "mocreatures:eating", 1.0F, 1.0F + ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F));

            return true;
        }
        if ((itemstack != null) && (itemstack.getItem() == Items.cooked_fish) && getIsTamed() && getIsAdult()) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            if ((getHealth() + 25) > getMaxHealth()) {
                this.setHealth(getMaxHealth());
            }
            setHasEaten(true);
            this.worldObj.playSoundAtEntity(this, "mocreatures:eating", 1.0F, 1.0F + ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F));
            return true;
        }
        if (this.riddenByEntity == null) {
            entityplayer.rotationYaw = this.rotationYaw;
            entityplayer.rotationPitch = this.rotationPitch;
            entityplayer.posY = this.posY;
            if (!this.worldObj.isRemote) {
                entityplayer.mountEntity(this);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (MoCreatures.isServer()) {

            //TODO
            /*if (!getIsHungry() && (this.rand.nextInt(100) == 0)) {
                setIsHungry(true);
            }*/

            //TODO
            /*if ((this.riddenByEntity == null) && (this.deathTime == 0) && (!getIsTamed() || getIsHungry())) {
                EntityItem entityitem = getClosestFish(this, 12D);
                if (entityitem != null) {
                    MoveToNextEntity(entityitem);
                    EntityItem entityitem1 = getClosestFish(this, 2D);
                    if ((this.rand.nextInt(20) == 0) && (entityitem1 != null) && (this.deathTime == 0)) {

                        entityitem1.setDead();
                        setTemper(getTemper() + 25);
                        if (getTemper() > getMaxTemper()) {
                            setTemper(getMaxTemper() - 1);
                        }
                        this.setHealth(getMaxHealth());
                    }
                }
            }*/
            if (!ReadyforParenting(this)) {
                return;
            }
            int i = 0;
            List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(8D, 2D, 8D));
            for (int j = 0; j < list.size(); j++) {
                Entity entity = list.get(j);
                if (entity instanceof MoCEntityDolphin) {
                    i++;
                }
            }

            if (i > 1) {
                return;
            }
            List<Entity> list1 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(4D, 2D, 4D));
            for (int k = 0; k < list1.size(); k++) {
                Entity entity1 = list1.get(k);
                if (!(entity1 instanceof MoCEntityDolphin) || (entity1 == this)) {
                    continue;
                }
                MoCEntityDolphin entitydolphin = (MoCEntityDolphin) entity1;
                if (!ReadyforParenting(this) || !ReadyforParenting(entitydolphin)) {
                    continue;
                }
                if (this.rand.nextInt(100) == 0) {
                    this.gestationtime++;
                }
                if (this.gestationtime % 3 == 0) {
                    MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageHeart(this.getEntityId()),
                            new TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 64));
                }
                if (this.gestationtime <= 50) {
                    continue;
                }
                MoCEntityDolphin babydolphin = new MoCEntityDolphin(this.worldObj);
                babydolphin.setPosition(this.posX, this.posY, this.posZ);
                if (this.worldObj.spawnEntityInWorld(babydolphin)) {
                    this.worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F) + 1.0F);
                    setHasEaten(false);
                    entitydolphin.setHasEaten(false);
                    this.gestationtime = 0;
                    entitydolphin.gestationtime = 0;
                    int l = Genetics(this, entitydolphin);
                    babydolphin.setEdad(35);
                    babydolphin.setAdult(false);
                    babydolphin.setOwner(this.getOwnerName());
                    babydolphin.setTamed(true);
                    EntityPlayer entityplayer = this.worldObj.getPlayerEntityByName(this.getOwnerName());
                    if (entityplayer != null) {
                        MoCTools.tameWithName(entityplayer, babydolphin);
                    }
                    babydolphin.setTypeInt(l);
                    break;
                }
            }
        }
    }

    public boolean ReadyforParenting(MoCEntityDolphin entitydolphin) {
        return (entitydolphin.riddenByEntity == null) && (entitydolphin.ridingEntity == null) && entitydolphin.getIsTamed()
                && entitydolphin.getHasEaten() && entitydolphin.getIsAdult();
    }

    @Override
    public void setDead() {
        if (MoCreatures.isServer() && getIsTamed() && (getHealth() > 0)) {
            return;
        } else {
            super.setDead();
            return;
        }
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 1;
    }

    @Override
    protected boolean usesNewAI() {
        return true;
    }

    @Override
    public float getAIMoveSpeed() {
        return 0.15F;
    }

    @Override
    public boolean isMovementCeased() {
        return !isInWater();
    }

    @Override
    protected double minDivingDepth() {
        return 0.4D;
    }

    @Override
    protected double maxDivingDepth() {
        return 4.0D;
    }

    @Override
    public int getMaxEdad() {
        return 160;
    }

    @Override
    public void updateRiderPosition() {
        double dist = (0.8D);
        double newPosX = this.posX + (dist * Math.sin(this.renderYawOffset / 57.29578F));
        double newPosZ = this.posZ - (dist * Math.cos(this.renderYawOffset / 57.29578F));
        this.riddenByEntity.setPosition(newPosX, this.posY + getMountedYOffset() + this.riddenByEntity.getYOffset(), newPosZ);
    }

    @Override
    public double getMountedYOffset() {
        return this.getEdad() * 0.01F * (this.height * 0.5D);
    }
}
