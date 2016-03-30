package drzhark.mocreatures.entity;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ai.EntityAIMoverHelperMoC;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.ai.PathNavigateFlyer;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import drzhark.mocreatures.entity.item.MoCEntityKittyBed;
import drzhark.mocreatures.entity.item.MoCEntityLitterBox;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageHealth;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

import java.util.List;
import java.util.Random;

public abstract class MoCEntityMob extends EntityMob implements IMoCEntity//, IEntityAdditionalSpawnData
{

    protected boolean divePending;
    protected int maxHealth;
    protected float moveSpeed;
    protected String texture;
    protected PathNavigate navigatorWater;
    protected PathNavigate navigatorFlyer;
    protected EntityAIWanderMoC2 wander;

    public MoCEntityMob(World world) {
        super(world);
        setTamed(false);
        this.texture = "blank.jpg";
        this.moveHelper = new EntityAIMoverHelperMoC(this);
        this.navigatorWater = new PathNavigateSwimmer(this, world);
        this.navigatorFlyer = new PathNavigateFlyer(this, world);
        this.tasks.addTask(4, this.wander = new EntityAIWanderMoC2(this, 1.0D, 80));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(getMoveSpeed());
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(getAttackStrenght());
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
    }

    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData par1EntityLivingData) {
        selectType();
        return super.onInitialSpawn(difficulty, par1EntityLivingData);
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getTexture(this.texture);
    }

    protected double getAttackStrenght() {
        return 2D;
    }

    /**
     * Put your code to choose a texture / the mob type in here. Will be called
     * by default MocEntity constructors.
     */
    @Override
    public void selectType() {
        setType(1);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, Byte.valueOf((byte) 0)); // isTamed - 0 false 1 true
        this.dataWatcher.addObject(17, String.valueOf("")); // displayName empty string by default
        this.dataWatcher.addObject(18, Integer.valueOf(0)); // int ageTicks / "edad"
        this.dataWatcher.addObject(19, Integer.valueOf(0)); // int type
        this.dataWatcher.addObject(20, Byte.valueOf((byte) 0)); // isAdult - 0 false 1 true
    }

    @Override
    public void setType(int i) {
        this.dataWatcher.updateObject(19, Integer.valueOf(i));
    }

    @Override
    public int getType() {
        return this.dataWatcher.getWatchableObjectInt(19);
    }

    @Override
    public boolean getIsAdult() {
        return (this.dataWatcher.getWatchableObjectByte(20) == 1);
    }

    @Override
    public boolean getIsTamed() {
        return (this.dataWatcher.getWatchableObjectByte(16) == 1);
    }

    @Override
    public String getPetName() {
        return this.dataWatcher.getWatchableObjectString(17);
    }

    @Override
    public int getEdad() {
        return this.dataWatcher.getWatchableObjectInt(18);
    }

    @Override
    public void setEdad(int i) {
        this.dataWatcher.updateObject(18, Integer.valueOf(i));
    }

    @Override
    public void setAdult(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(20, Byte.valueOf(input));
    }

    @Override
    public void setPetName(String name) {
        this.dataWatcher.updateObject(17, String.valueOf(name));
    }

    @Override
    public void setTamed(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(16, Byte.valueOf(input));
    }

    public boolean getCanSpawnHereLiving() {
        return this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox())
                && this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).size() == 0
                && !this.worldObj.isAnyLiquid(this.getEntityBoundingBox());
    }

    public boolean getCanSpawnHereCreature() {
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(getEntityBoundingBox().minY);
        int k = MathHelper.floor_double(this.posZ);
        return getBlockPathWeight(new BlockPos(i, j, k)) >= 0.0F;
    }

    @Override
    public boolean getCanSpawnHere() {
        return (MoCreatures.entityMap.get(this.getClass()).getFrequency() > 0 && super.getCanSpawnHere());
    }

    public boolean getCanSpawnHereMob() {
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(getEntityBoundingBox().minY);
        int k = MathHelper.floor_double(this.posZ);
        BlockPos pos = new BlockPos(i, j, k);
        if (this.worldObj.getLightFor(EnumSkyBlock.SKY, pos) > this.rand.nextInt(32)) {
            return false;
        }
        int l = this.worldObj.getLightFromNeighbors(pos);
        if (this.worldObj.isThundering()) {
            int i1 = this.worldObj.getSkylightSubtracted();
            this.worldObj.setSkylightSubtracted(10);
            l = this.worldObj.getLightFromNeighbors(pos);
            this.worldObj.setSkylightSubtracted(i1);
        }
        return l <= this.rand.nextInt(8);
    }

    // TODO move this to a class accessible by MocEntityMob and MoCentityAnimals
    protected EntityLivingBase getClosestEntityLiving(Entity entity, double d) {
        double d1 = -1D;
        EntityLivingBase entityliving = null;
        List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(d, d, d));
        for (int i = 0; i < list.size(); i++) {
            Entity entity1 = list.get(i);

            if (entitiesToIgnore(entity1)) {
                continue;
            }
            double d2 = entity1.getDistanceSq(entity.posX, entity.posY, entity.posZ);
            if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1)) && ((EntityLivingBase) entity1).canEntityBeSeen(entity)) {
                d1 = d2;
                entityliving = (EntityLivingBase) entity1;
            }
        }

        return entityliving;
    }

    //TODO REMOVE
    public boolean entitiesToIgnore(Entity entity) {
        return ((!(entity instanceof EntityLiving)) || (entity instanceof EntityMob) || (entity instanceof MoCEntityEgg)
                || (entity instanceof EntityPlayer && this.getIsTamed()) || (entity instanceof MoCEntityKittyBed)
                || (entity instanceof MoCEntityLitterBox)
                || (this.getIsTamed() && (entity instanceof MoCEntityAnimal && ((MoCEntityAnimal) entity).getIsTamed()))
                || ((entity instanceof EntityWolf) && !(MoCreatures.proxy.attackWolves)) || ((entity instanceof MoCEntityHorse) && !(MoCreatures.proxy.attackHorses)));
    }

    @Override
    public boolean checkSpawningBiome() {
        return true;
    }

    @Override
    public void onLivingUpdate() {
        if (MoCreatures.isServer()) {

            /*if (forceUpdates() && this.rand.nextInt(1000) == 0) {
                MoCTools.forceDataSync(this);
            }*/

            if (getIsTamed() && this.rand.nextInt(200) == 0) {
                MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageHealth(this.getEntityId(), this.getHealth()), new TargetPoint(
                        this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 64));
            }

            if (this.isHarmedByDaylight()) {
                if (this.worldObj.isDaytime()) {
                    float var1 = this.getBrightness(1.0F);
                    if (var1 > 0.5F
                            && this.worldObj.canBlockSeeSky(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY),
                                    MathHelper.floor_double(this.posZ))) && this.rand.nextFloat() * 30.0F < (var1 - 0.4F) * 2.0F) {
                        this.setFire(8);
                    }
                }
            }

            if (!getIsAdult() && (this.rand.nextInt(300) == 0)) {
                setEdad(getEdad() + 1);
                if (getEdad() >= getMaxEdad()) {
                    setAdult(true);
                }
            }

            if (getIsFlying() && this.getNavigator().noPath() && !isMovementCeased() && this.getAttackTarget() == null && this.rand.nextInt(20) == 0) {
                this.wander.makeUpdate();
            }
        }

        this.getNavigator().onUpdateNavigation();
        super.onLivingUpdate();
    }

    protected int getMaxEdad() {
        return 100;
    }

    protected boolean isHarmedByDaylight() {
        return false;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (MoCreatures.isServer() && getIsTamed()) {
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageHealth(this.getEntityId(), this.getHealth()), new TargetPoint(
                    this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 64));
        }
        return super.attackEntityFrom(damagesource, i);
    }

    /**
     * Boolean used to select pathfinding behavior
     *
     * @return
     */
    public boolean isFlyer() {
        return false;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound = MoCTools.getEntityData(this);
        nbttagcompound.setBoolean("Tamed", getIsTamed());
        nbttagcompound.setBoolean("Adult", getIsAdult());
        nbttagcompound.setInteger("Edad", getEdad());
        nbttagcompound.setString("Name", getPetName());
        nbttagcompound.setInteger("TypeInt", getType());

    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        nbttagcompound = MoCTools.getEntityData(this);
        setTamed(nbttagcompound.getBoolean("Tamed"));
        setAdult(nbttagcompound.getBoolean("Adult"));
        setEdad(nbttagcompound.getInteger("Edad"));
        setPetName(nbttagcompound.getString("Name"));
        setType(nbttagcompound.getInteger("TypeInt"));

    }

    @Override
    public void fall(float f, float f1) {
        if (!isFlyer()) {
            super.fall(f, f1);
        }
    }

    @Override
    public boolean isOnLadder() {
        if (isFlyer()) {
            return false;
        } else {
            return super.isOnLadder();
        }
    }

    @Override
    public void moveEntityWithHeading(float strafe, float forward) {
        if (!isFlyer()) {
            super.moveEntityWithHeading(strafe, forward);
            return;
        }
        this.moveEntityWithHeadingFlyer(strafe, forward);
    }

    public void moveEntityWithHeadingFlyer(float strafe, float forward) {
        if (this.isServerWorld()) {

            this.moveFlying(strafe, forward, 0.1F);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.8999999761581421D;
            this.motionY *= 0.8999999761581421D;
            this.motionZ *= 0.8999999761581421D;
        } else {
            super.moveEntityWithHeading(strafe, forward);
        }
    }

    public void moveEntityWithHeadingAquatic(float strafe, float forward) {
        if (this.isServerWorld()) {
            if (this.isInWater()) {
                if (this.riddenByEntity instanceof EntityLivingBase) {
                    //this.moveEntityWithRider(strafe, forward);
                    return;
                }

                this.moveFlying(strafe, forward, 0.1F);
                this.moveEntity(this.motionX, this.motionY, this.motionZ);
                this.motionX *= 0.8999999761581421D;
                this.motionY *= 0.8999999761581421D;
                this.motionZ *= 0.8999999761581421D;

                if (this.getAttackTarget() == null) {
                    this.motionY -= 0.005D;
                }
            } else {
                super.moveEntityWithHeading(strafe, forward);
            }
        } else {
            super.moveEntityWithHeading(strafe, forward);
        }

    }

    /**
     * Used to synchronize the attack animation between server and client
     *
     * @param attackType
     */
    @Override
    public void performAnimation(int attackType) {
    }

    public float getMoveSpeed() {
        return 0.7F;
    }

    @Override
    public int nameYOffset() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean renderName() {
        return MoCreatures.proxy.getDisplayPetName()
                && (getPetName() != null && !getPetName().equals("") && (this.riddenByEntity == null) && (this.ridingEntity == null));
    }

    /*@Override
    public boolean updateMount() {
        return false;
    }*/

    /*@Override
    public boolean forceUpdates() {
        return false;
    }*/

    protected Vec3 findPossibleShelter() {
        Random var1 = this.getRNG();

        for (int var2 = 0; var2 < 10; ++var2) {
            int var3 = MathHelper.floor_double(this.posX + var1.nextInt(20) - 10.0D);
            int var4 = MathHelper.floor_double(this.getEntityBoundingBox().minY + var1.nextInt(6) - 3.0D);
            int var5 = MathHelper.floor_double(this.posZ + var1.nextInt(20) - 10.0D);
            BlockPos pos = new BlockPos(var3, var4, var5);

            if (!this.worldObj.canBlockSeeSky(pos) && this.getBlockPathWeight(pos) < 0.0F) {
                return new Vec3(var3, var4, var5);
            }
        }

        return null;
    }

    @Override
    public void makeEntityJump() {
        //TODO
    }

    @Override
    public void makeEntityDive() {
        this.divePending = true;
    }

    @Override
    protected boolean canDespawn() {
        return !getIsTamed();
    }

    @Override
    public void setDead() {
        // Server check required to prevent tamed entities from being duplicated on client-side
        if (MoCreatures.isServer() && (getIsTamed()) && (getHealth() > 0)) {
            return;
        }
        super.setDead();
    }

    @Override
    public float getSizeFactor() {
        return 1.0F;
    }

    @Override
    public float getAdjustedYOffset() {
        return 0F;
    }

    protected void getPathOrWalkableBlock(Entity entity, float f) {
        PathEntity pathentity = this.navigator.getPathToPos(entity.getPosition());
        if ((pathentity == null) && (f > 12F)) {
            int i = MathHelper.floor_double(entity.posX) - 2;
            int j = MathHelper.floor_double(entity.posZ) - 2;
            int k = MathHelper.floor_double(entity.getEntityBoundingBox().minY);
            for (int l = 0; l <= 4; l++) {
                for (int i1 = 0; i1 <= 4; i1++) {
                    if (((l < 1) || (i1 < 1) || (l > 3) || (i1 > 3))
                            && this.worldObj.getBlockState(new BlockPos(i + l, k - 1, j + i1)).getBlock().isNormalCube()
                            && !this.worldObj.getBlockState(new BlockPos(i + l, k, j + i1)).getBlock().isNormalCube()
                            && !this.worldObj.getBlockState(new BlockPos(i + l, k + 1, j + i1)).getBlock().isNormalCube()) {
                        setLocationAndAngles((i + l) + 0.5F, k, (j + i1) + 0.5F, this.rotationYaw, this.rotationPitch);
                        return;
                    }
                }

            }
        } else {
            this.navigator.setPath(pathentity, 16F);
        }
    }

    @Override
    public String getOwnerName() {
        return "";
    }

    @Override
    public void setOwner(String par1Str) {
    }

    @Override
    public void setArmorType(byte i) {
    }

    public byte getArmorType() {
        return 0;
    }

    @Override
    public void dismountEntity() {
    }

    @Override
    public float pitchRotationOffset() {
        return 0F;
    }

    @Override
    public float rollRotationOffset() {
        return 0F;
    }

    @Override
    public float yawRotationOffset() {
        return 0F;
    }

    @Override
    public float getAdjustedZOffset() {
        return 0F;
    }

    @Override
    public float getAdjustedXOffset() {
        return 0F;
    }

    @Override
    public boolean canAttackTarget(EntityLivingBase entity) {
        return false;
    }

    @Override
    public boolean getIsSitting() {
        return false;
    }

    @Override
    public boolean isNotScared() {
        return true;
    }

    @Override
    public boolean isMovementCeased() {
        return false;
    }

    @Override
    public boolean shouldAttackPlayers() {
        return this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL;// && this.worldObj.getWorldInfo().isCreative(); //TODO also creative
    }

    @Override
    public double getDivingDepth() {
        return 0;
    }

    @Override
    public boolean isDiving() {
        return false;
    }

    @Override
    public void forceEntityJump() {
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag =
                entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), ((int) this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
                        .getAttributeValue()));
        if (flag) {
            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }

    @Override
    public int maxFlyingHeight() {
        return 5;
    }

    @Override
    public int minFlyingHeight() {
        return 1;
    }

    @Override
    public PathNavigate getNavigator() {
        if (this.isInWater() && this.isAmphibian()) {
            return this.navigatorWater;
        }
        if (this.isFlyer()) {
            return this.navigatorFlyer;
        }
        return this.navigator;
    }

    public boolean isAmphibian() {
        return false;
    }

    //TODO add a timer / deactivate flying behaviour 
    @Override
    public boolean getIsFlying() {
        return isFlyer();
    }

    /**
     * Returns true if the entity is of the @link{EnumCreatureType} provided
     *
     * @param type The EnumCreatureType type this entity is evaluating
     * @param forSpawnCount If this is being invoked to check spawn count caps.
     * @return If the creature is of the type provided
     */
    @Override
    public boolean isCreatureType(EnumCreatureType type, boolean forSpawnCount) {
        if (type == EnumCreatureType.MONSTER) {
            return true;
        } else {
            return false;
        }
    }
}
