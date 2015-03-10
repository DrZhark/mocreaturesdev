package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.item.MoCEntityKittyBed;
import drzhark.mocreatures.entity.item.MoCEntityLitterBox;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import java.util.List;

public class MoCEntityBigCat extends MoCEntityTameableAnimal {

    public MoCEntityBigCat(World world) {
        super(world);
        setEdad(35);
        setSize(0.9F, 1.3F);
        //health = 25;
        if (this.rand.nextInt(4) == 0) {
            setAdult(false);

        } else {
            setAdult(true);
        }
        setHungry(true);
        setTamed(false);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(calculateMaxHealth());
    }

    /**
     * Sets the type and texture of a BigCat if not set already.
     */
    @Override
    public void selectType() {
        checkSpawningBiome();
        if (getType() == 0) {
            int i = this.rand.nextInt(100);
            if (i <= 5) {
                setType(1);
            } else if (i <= 25) {
                setType(2);
            } else if (i <= 50) {
                setType(3);
            } else if (i <= 70) {
                setType(4);
            } else if (i <= 75) {
                setType(7);
            } else {
                setType(5);
            }

            this.setHealth(getMaxHealth());
        }

    }

    @Override
    public float getMoveSpeed() {
        switch (getType()) {
            case 1:
                return 1.4F;
            case 2:
                return 1.4F;
            case 3:
                return 1.6F;
            case 4:
                return 1.9F;
            case 5:
                return 1.6F;
            case 6:
                return 1.7F;
            case 7:
                return 1.7F;

            default:
                return 1.4F;
        }
    }

    public float calculateMaxHealth() {
        switch (getType()) {
            case 1:
                return 25;
            case 2:
                return 30;
            case 3:
                return 20;
            case 4:
                return 20;
            case 5:
                return 35;
            case 6:
                return 25;
            case 7:
                return 40;

            default:
                return 20;
        }

    }

    public float getWidthF() {
        switch (getType()) {
            case 1:
                return 1F;
            case 2:
                return 1.1F;
            case 3:
                return 0.9F;
            case 4:
                return 0.8F;
            case 5:
                return 1.1F;
            case 6:
                return 0.8F;
            case 7:
                return 1.2F;

            default:
                return 1F;
        }
    }

    public float getHeightF() {
        switch (getType()) {
            case 1:
                return 1.0F;
            case 2:
                return 1.1F;
            case 3:
                return 0.9F;
            case 4:
                return 0.8F;
            case 5:
                return 1.1F;
            case 6:
                return 0.8F;
            case 7:
                return 1.2F;

            default:
                return 1F;
        }
    }

    public float getLengthF() {
        switch (getType()) {
            case 1:
                return 1.0F;
            case 2:
                return 1.0F;
            case 3:
                return 0.9F;
            case 4:
                return 1.0F;
            case 5:
                return 1.1F;
            case 6:
                return 0.9F;
            case 7:
                return 1.2F;

            default:
                return 1F;
        }
    }

    public int getForce() {
        switch (getType()) {
            case 1:
                return 5;
            case 2:
                return 5;
            case 3:
                return 4;
            case 4:
                return 3;
            case 5:
                return 6;
            case 6:
                return 3;
            case 7:
                return 8;

            default:
                return 5;
        }
    }

    public double getAttackRange() {
        switch (getType()) {
            case 1:
                return 8D;
            case 2:
                return 4D;
            case 3:
                return 6D;
            case 4:
                return 6D;
            case 5:
                return 8D;
            case 6:
                return 4D;
            case 7:
                return 10D;

            default:
                return 6D;
        }
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getType()) {
            case 1:
                return MoCreatures.proxy.getTexture("lionf.png");
            case 2:
                return MoCreatures.proxy.getTexture("lionf.png");
            case 3:
                return MoCreatures.proxy.getTexture("panther.png");
            case 4:
                return MoCreatures.proxy.getTexture("cheetah.png");
            case 5:
                return MoCreatures.proxy.getTexture("tiger.png");
            case 6:
                return MoCreatures.proxy.getTexture("leopard.png");
            case 7:
                return MoCreatures.proxy.getTexture("tigerw.png");
            default:
                return MoCreatures.proxy.getTexture("lionf.png");
        }
    }

    /**
     * Initializes datawatchers for entity. Each datawatcher is used to sync
     * server data to client.
     */
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(23, Byte.valueOf((byte) 0)); // hasEaten - 0 false 1 true
        this.dataWatcher.addObject(24, Byte.valueOf((byte) 0)); // isSitting - 0 false 1 true
        this.dataWatcher.addObject(25, Byte.valueOf((byte) 0)); // isHungry - 0 false 1 true
    }

    public boolean getIsHungry() {
        return (this.dataWatcher.getWatchableObjectByte(25) == 1);
    }

    public boolean getHasEaten() {
        return (this.dataWatcher.getWatchableObjectByte(23) == 1);
    }

    @Override
    public boolean getIsSitting() {
        return (this.dataWatcher.getWatchableObjectByte(24) == 1);
    }

    public void setHungry(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(25, Byte.valueOf(input));
    }

    public void setEaten(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(23, Byte.valueOf(input));
    }

    public void setSitting(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(24, Byte.valueOf(input));
    }

    /*@Override
    protected void attackEntity(Entity entity, float f) {
        if (attackTime <= 0 && (f > 2.0F) && (f < 6F) && (this.rand.nextInt(50) == 0)) {
            if (this.onGround) {
                double d = entity.posX - this.posX;
                double d1 = entity.posZ - this.posZ;
                float f1 = MathHelper.sqrt_double((d * d) + (d1 * d1));
                this.motionX = ((d / f1) * 0.5D * 0.8D) + (this.motionX * 0.2D);
                this.motionZ = ((d1 / f1) * 0.5D * 0.8D) + (this.motionZ * 0.2D);
                this.motionY = 0.4D;
            }
            return;

        }

        if (this.attackTime <= 0 && (f < 2.5D) && (entity.getEntityBoundingBox().maxY > getEntityBoundingBox().minY)
                && (entity.getEntityBoundingBox().minY < getEntityBoundingBox().maxY)) {
            attackTime = 20;
            entity.attackEntityFrom(DamageSource.causeMobDamage(this), getForce());
            if (!(entity instanceof EntityPlayer)) {
                MoCTools.destroyDrops(this, 3D);
            }
        }
    }*/

    // Method used for receiving damage from another source
    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (super.attackEntityFrom(damagesource, i)) {
            Entity entity = damagesource.getEntity();
            if (entity != null && getIsTamed() && entity instanceof EntityPlayer) {
                return false;
            }
            if ((this.riddenByEntity == entity) || (this.ridingEntity == entity)) {
                return true;
            }
            this.worldObj.getDifficulty();
            if (entity != this && entity instanceof EntityLivingBase && (this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL)) {
                setAttackTarget((EntityLivingBase) entity);
            }
            return true;
        } else {
            return false;
        }
    }

    public int checkNearBigKitties(double d) {
        boolean flag = false;
        List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(d, d, d));
        for (int j = 0; j < list.size(); j++) {
            Entity entity = (Entity) list.get(j);
            if ((entity != this) && (entity instanceof MoCEntityBigCat)) {
                MoCEntityBigCat entitybigcat = (MoCEntityBigCat) entity;
                return entitybigcat.getType();
            }
        }

        return 0;
    }

    @Override
    protected Entity findPlayerToAttack() {
        /*if (this.roper != null && this.roper instanceof EntityPlayer) {
            return getMastersEnemy((EntityPlayer) this.roper, 12D);
        }*/

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
    }

    @Override
    public boolean checkSpawningBiome() {
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(getEntityBoundingBox().minY);
        int k = MathHelper.floor_double(this.posZ);
        BlockPos pos = new BlockPos(i, j, k);

        BiomeGenBase currentbiome = MoCTools.Biomekind(this.worldObj, pos);

        if (BiomeDictionary.isBiomeOfType(currentbiome, Type.FROZEN)) {
            setType(6); //snow leopard
            return true;
        }

        int l = 0;
        {
            l = checkNearBigKitties(12D);

            if (l == 2) {
                l = 1;
            } else if (l == 1 && this.rand.nextInt(3) == 0) {
                l = 2;
            } else if (l == 7) {
                l = 5;
            }
        }
        setType(l);
        return true;
    }

    // TODO move somewhere else
    public boolean NearSnowWithDistance(Entity entity, Double double1) {
        AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().expand(double1.doubleValue(), double1.doubleValue(), double1.doubleValue());
        int i = MathHelper.floor_double(axisalignedbb.minX);
        int j = MathHelper.floor_double(axisalignedbb.maxX + 1.0D);
        int k = MathHelper.floor_double(axisalignedbb.minY);
        int l = MathHelper.floor_double(axisalignedbb.maxY + 1.0D);
        int i1 = MathHelper.floor_double(axisalignedbb.minZ);
        int j1 = MathHelper.floor_double(axisalignedbb.maxZ + 1.0D);
        for (int k1 = i; k1 < j; k1++) {
            for (int l1 = k; l1 < l; l1++) {
                for (int i2 = i1; i2 < j1; i2++) {
                    IBlockState blockstate = this.worldObj.getBlockState(new BlockPos(k1, l1, i2));
                    if ((blockstate.getBlock() != Blocks.air) && (blockstate.getBlock().getMaterial() == Material.snow)) {
                        return true;
                    }
                }

            }

        }

        return false;
    }

    @Override
    public EntityLivingBase getClosestTarget(Entity entity, double d) {
        double d1 = -1D;
        EntityLivingBase entityliving = null;
        List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(d, d, d));
        for (int i = 0; i < list.size(); i++) {
            Entity entity1 = (Entity) list.get(i);
            if (!(entity1 instanceof EntityLivingBase) || (entity1 == entity) || (entity1 == entity.riddenByEntity)
                    || (entity1 == entity.ridingEntity) || (entity1 instanceof EntityPlayer) || (entity1 instanceof MoCEntityElephant)
                    || (!getIsAdult() && ((entity1.width > 0.5D) || (entity1.height > 0.5D))) || (entity1 instanceof MoCEntityKittyBed)
                    || (entity1 instanceof MoCEntityLitterBox) || ((entity1 instanceof EntityMob) && (!getIsTamed() || !getIsAdult()))
                    || (getIsTamed() && (entity1 instanceof IMoCEntity) && ((IMoCEntity) entity1).getIsTamed())
                    || ((entity1 instanceof MoCEntityHorse) && !(MoCreatures.proxy.attackHorses))
                    || ((entity1 instanceof EntityWolf) && !(MoCreatures.proxy.attackWolves))
                    || ((entity instanceof IMoCEntity) && !MoCreatures.isHuntingEnabled()) // don't attack if hunting is disabled
            ) {
                continue;
            }
            if (entity1 instanceof MoCEntityBigCat) {
                if (!getIsAdult()) {
                    continue;
                }
                MoCEntityBigCat entitybigcat = (MoCEntityBigCat) entity1;
                if ((getIsTamed() && entitybigcat.getIsTamed()) || (entitybigcat.getType() == 7)
                        || ((getType() != 2) && (getType() == entitybigcat.getType())) || ((getType() == 2) && (entitybigcat.getType() == 1))
                        || (this.getHealth() < entitybigcat.getHealth())) {
                    continue;
                }
            }
            double d2 = entity1.getDistanceSq(entity.posX, entity.posY, entity.posZ);
            if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1)) && ((EntityLivingBase) entity1).canEntityBeSeen(entity)) {
                d1 = d2;
                entityliving = (EntityLivingBase) entity1;
            }
        }

        return entityliving;
    }

    @Override
    protected String getDeathSound() {
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
        if (getIsAdult()) {
            return "mocreatures:lionhurt";
        } else {
            return "mocreatures:cubhurt";
        }
    }

    @Override
    protected String getLivingSound() {
        if (getIsAdult()) {
            return "mocreatures:liongrunt";
        } else {
            return "mocreatures:cubgrunt";
        }
    }

    public EntityCreature getMastersEnemy(EntityPlayer entityplayer, double d) {
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
    }

    @Override
    public boolean interact(EntityPlayer entityplayer) {

        if (super.interact(entityplayer)) {
            return false;
        }
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if ((itemstack != null) && !getIsTamed() && getHasEaten() && (itemstack.getItem() == MoCreatures.medallion)) {
            if (MoCreatures.isServer()) {
                MoCTools.tameWithName(entityplayer, this);
            }
            if (getIsTamed() && --itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                return true;
            }

            return false;
        }
        if ((itemstack != null) && getIsTamed() && (itemstack.getItem() == MoCreatures.whip)) {
            setSitting(!getIsSitting());
            return true;
        }
        if ((itemstack != null) && getIsTamed() && (itemstack.getItem() == Items.porkchop || itemstack.getItem() == Items.fish)) {
            this.setHealth(getMaxHealth());
            this.worldObj.playSoundAtEntity(this, "mocreatures:eating", 1.0F, 1.0F + ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F));
            setHungry(false);
        }
        return false;

    }

    /**
     * Checks if bigcat is sitting.
     */
    /* @Override
     protected boolean isMovementCeased() {
         return getIsSitting();
     }*/

    //drops medallion on death
    @Override
    public void onDeath(DamageSource damagesource) {
        if (MoCreatures.isServer()) {
            if (getIsTamed()) {
                MoCTools.dropCustomItem(this, this.worldObj, new ItemStack(MoCreatures.medallion, 1));
            }
        }
        super.onDeath(damagesource);
    }

    @Override
    public void onLivingUpdate() {

        super.onLivingUpdate();

        if ((this.rand.nextInt(300) == 0) && (this.getHealth() <= getMaxHealth()) && (this.deathTime == 0) && !this.worldObj.isRemote) {
            //health++;
            this.setHealth(getHealth() + 1);
        }
        if (!getIsAdult() && (this.rand.nextInt(250) == 0)) {
            setEdad(getEdad() + 1);
            if (getEdad() >= 100) {
                setAdult(true);
                // unused_flag = false;
            }
        }
        if (!getIsHungry() && !getIsSitting() && (this.rand.nextInt(200) == 0)) {
            setHungry(true);
        }

        if ((this.deathTime == 0) && getIsHungry() && !getIsSitting()) {
            EntityItem entityitem = getClosestItem(this, 12D, Items.porkchop, Items.fish);
            if (entityitem != null) {
                float f = entityitem.getDistanceToEntity(this);
                if (f > 2.0F) {
                    getMyOwnPath(entityitem, f);
                }
                if ((f < 2.0F) && (entityitem != null) && (this.deathTime == 0)) {
                    entityitem.setDead();
                    this.setHealth(getMaxHealth());
                    if (!getIsAdult() && (getEdad() < 80)) {
                        setEaten(true);
                    }
                    this.worldObj
                            .playSoundAtEntity(this, "mocreatures:eating", 1.0F, 1.0F + ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F));
                    setHungry(false);
                }
            }
        }
    }

    @Override
    public boolean isNotScared() {
        return true;
    }

    @Override
    public boolean renderName() {
        return !getName().isEmpty() && getRenderName() && MoCreatures.proxy.getDisplayPetName();
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        setSitting(nbttagcompound.getBoolean("Sitting"));
        setDisplayName(nbttagcompound.getBoolean("DisplayName"));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("Sitting", getIsSitting());
        nbttagcompound.setBoolean("DisplayName", getRenderName());
    }

    @Override
    public void dropMyStuff() {
    }
}
