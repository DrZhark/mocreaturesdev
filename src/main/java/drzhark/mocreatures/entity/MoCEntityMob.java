package drzhark.mocreatures.entity;

import net.minecraft.world.EnumDifficulty;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import drzhark.mocreatures.entity.item.MoCEntityKittyBed;
import drzhark.mocreatures.entity.item.MoCEntityLitterBox;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageHealth;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

import java.util.List;
import java.util.Random;

public abstract class MoCEntityMob extends EntityMob implements IMoCEntity//, IEntityAdditionalSpawnData
{

    protected boolean divePending;
    protected int maxHealth;
    private PathEntity entitypath;
    private boolean riderIsDisconnecting;
    protected float moveSpeed;
    protected String texture;

    public MoCEntityMob(World world) {
        super(world);
        setTamed(false);
        this.riderIsDisconnecting = false;
        this.texture = "blank.jpg";
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(getMoveSpeed());
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(getAttackStrenght());
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
    }

    @Override
    public IEntityLivingData onSpawnFirstTime(DifficultyInstance difficulty, IEntityLivingData par1EntityLivingData) {
        selectType();
        return super.onSpawnFirstTime(difficulty, par1EntityLivingData);
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
    public String getName() {
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
    public void setName(String name) {
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
    // ?? implements?
    protected EntityLivingBase getClosestEntityLiving(Entity entity, double d) {
        double d1 = -1D;
        EntityLivingBase entityliving = null;
        List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(d, d, d));
        for (int i = 0; i < list.size(); i++) {
            Entity entity1 = (Entity) list.get(i);

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

    public boolean entitiesToIgnore(Entity entity) {
        return ((!(entity instanceof EntityLiving)) || (entity instanceof EntityMob) || (entity instanceof MoCEntityEgg)
                || (entity instanceof EntityPlayer && this.getIsTamed()) || (entity instanceof MoCEntityKittyBed)
                || (entity instanceof MoCEntityLitterBox)
                || (this.getIsTamed() && (entity instanceof MoCEntityAnimal && ((MoCEntityAnimal) entity).getIsTamed()))
                || ((entity instanceof EntityWolf) && !(MoCreatures.proxy.attackWolves))
                || ((entity instanceof MoCEntityHorse) && !(MoCreatures.proxy.attackHorses)) || ((entity instanceof MoCEntityAnimal
                || entity instanceof MoCEntityAmbient || entity instanceof MoCEntityAquatic) && !MoCreatures.isHuntingEnabled()));
    }

    @Override
    public boolean checkSpawningBiome() {
        return true;
    }

    @Override
    public void onLivingUpdate() {
        if (MoCreatures.isServer() && forceUpdates() && this.rand.nextInt(1000) == 0) {
            MoCTools.forceDataSync(this);
        }

        if (MoCreatures.isServer() && getIsTamed() && this.rand.nextInt(200) == 0) {
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageHealth(this.getEntityId(), this.getHealth()), new TargetPoint(
                    this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 64));
        }
        this.moveSpeed = getMoveSpeed();
        super.onLivingUpdate();
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
        nbttagcompound.setBoolean("Tamed", getIsTamed());
        nbttagcompound.setBoolean("Adult", getIsAdult());
        nbttagcompound.setInteger("Edad", getEdad());
        nbttagcompound.setString("Name", getName());
        nbttagcompound.setInteger("TypeInt", getType());

    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        setTamed(nbttagcompound.getBoolean("Tamed"));
        setAdult(nbttagcompound.getBoolean("Adult"));
        setEdad(nbttagcompound.getInteger("Edad"));
        setName(nbttagcompound.getString("Name"));
        setType(nbttagcompound.getInteger("TypeInt"));

    }

    @Override
    public void fall(float f, float f1) {
        if (!isFlyer()) {
            super.fall(f, f1);
        }
    }

    protected Entity findPlayerToAttack() {
        if (isFlyer()) {
            EntityPlayer entityplayer = this.worldObj.getClosestPlayerToEntity(this, 20D);
            if ((entityplayer != null) && canEntityBeSeen(entityplayer)) {
                return entityplayer;
            } else {
                return null;
            }
        }
        return null;
        /*else {
            return super.findPlayerToAttack();
        }*/
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
    public void moveEntityWithHeading(float f, float f1) {
        if (!isFlyer()) {
            super.moveEntityWithHeading(f, f1);
            return;
        }

        if (handleWaterMovement()) {
            double d = this.posY;
            moveFlying(f, f1, 0.02F);
            moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.80000001192092896D;
            this.motionY *= 0.80000001192092896D;
            this.motionZ *= 0.80000001192092896D;
        } else if (handleLavaMovement()) {
            double d1 = this.posY;
            moveFlying(f, f1, 0.02F);
            moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.5D;
            this.motionY *= 0.5D;
            this.motionZ *= 0.5D;
        } else {
            float f2 = 0.91F;
            if (this.onGround) {
                f2 = 0.5460001F;
                Block block =
                        this.worldObj.getBlockState(
                                new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(getEntityBoundingBox().minY) - 1, MathHelper
                                        .floor_double(this.posZ))).getBlock();
                if (block != Blocks.air) {
                    f2 = block.slipperiness * 0.91F;
                }
            }
            float f3 = 0.162771F / (f2 * f2 * f2);
            moveFlying(f, f1, this.onGround ? 0.1F * f3 : 0.02F);
            f2 = 0.91F;
            if (this.onGround) {
                f2 = 0.5460001F;
                Block block =
                        this.worldObj.getBlockState(
                                new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(getEntityBoundingBox().minY) - 1, MathHelper
                                        .floor_double(this.posZ))).getBlock();
                if (block != Blocks.air) {
                    f2 = block.slipperiness * 0.91F;
                }
            }
            moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= f2;
            this.motionY *= f2;
            this.motionZ *= f2;
            if (this.isCollidedHorizontally) {
                this.motionY = 0.20000000000000001D;
            }
            if (getAttackTarget() != null && getAttackTarget().posY < this.posY && this.rand.nextInt(30) == 0) {
                this.motionY = -0.25D;
            }
        }
        this.prevLimbSwingAmount = this.limbSwingAmount;
        double d2 = this.posX - this.prevPosX;
        double d3 = this.posZ - this.prevPosZ;
        float f4 = MathHelper.sqrt_double((d2 * d2) + (d3 * d3)) * 4.0F;
        if (f4 > 1.0F) {
            f4 = 1.0F;
        }

        this.limbSwingAmount += (f4 - this.limbSwingAmount) * 0.4F;
        this.limbSwing += this.limbSwingAmount;
    }

    /*@Override
    protected void updateEntityActionState() {
        if (!isFlyer()) {
            super.updateEntityActionState();
            return;
        }

        hasAttacked = false;
        float f = 16F;
        if (this.entityLivingToAttack == null) {
            this.entityLivingToAttack = findPlayerToAttack();
            if (this.entityLivingToAttack != null) {
                this.entitypath = this.worldObj.getPathEntityToEntity(this, this.entityLivingToAttack, f, true, false, false, true);
            }
        } else if (!this.entityLivingToAttack.isEntityAlive()) {
            this.entityLivingToAttack = null;
        } else {
            float f1 = this.entityLivingToAttack.getDistanceToEntity(this);
            if (canEntityBeSeen(this.entityLivingToAttack)) {
                attackEntity(this.entityLivingToAttack, f1);
            }
        }
        if (!hasAttacked && (this.entityLivingToAttack != null) && ((this.entitypath == null) || (this.rand.nextInt(10) == 0))) {
            this.entitypath = this.worldObj.getPathEntityToEntity(this, this.entityLivingToAttack, f, true, false, false, true);
        } else if (((this.entitypath == null) && (this.rand.nextInt(80) == 0)) || (this.rand.nextInt(80) == 0)) {
            boolean flag = false;
            int j = -1;
            int k = -1;
            int l = -1;
            float f2 = -99999F;
            for (int i1 = 0; i1 < 10; i1++) {
                int j1 = MathHelper.floor_double((this.posX + this.rand.nextInt(13)) - 6D);
                int k1 = MathHelper.floor_double((this.posY + this.rand.nextInt(7)) - 3D);
                int l1 = MathHelper.floor_double((this.posZ + this.rand.nextInt(13)) - 6D);
                float f3 = getBlockPathWeight(j1, k1, l1);
                if (f3 > f2) {
                    f2 = f3;
                    j = j1;
                    k = k1;
                    l = l1;
                    flag = true;
                }
            }

            if (flag) {
                this.entitypath = this.worldObj.getEntityPathToXYZ(this, j, k, l, 10F, true, false, false, true);
            }
        }
        int i = MathHelper.floor_double(getEntityBoundingBox().minY);
        boolean flag1 = handleWaterMovement();
        boolean flag2 = handleLavaMovement();
        this.rotationPitch = 0.0F;
        if ((this.entitypath == null) || (this.rand.nextInt(100) == 0)) {
            super.updateEntityActionState();
            this.entitypath = null;
            return;
        }
        //TODO 4FIX test!
        Vec3 vec3d = this.entitypath.getPosition(this); //client

        //Vec3D vec3d = entitypath.getPosition(this); //server
        for (double d = this.width * 2.0F; (vec3d != null) && (vec3d.squareDistanceTo(this.posX, vec3d.yCoord, this.posZ) < (d * d));) {
            this.entitypath.incrementPathIndex();
            if (this.entitypath.isFinished()) {
                vec3d = null;
                this.entitypath = null;
            } else {
                //vec3d = entitypath.getPosition(this); //server
                //TODO 4FIX test!
                vec3d = this.entitypath.getPosition(this);
            }
        }

        this.isJumping = false;
        if (vec3d != null) {
            double d1 = vec3d.xCoord - this.posX;
            double d2 = vec3d.zCoord - this.posZ;
            double d3 = vec3d.yCoord - i;
            float f4 = (float) ((Math.atan2(d2, d1) * 180D) / 3.1415927410125728D) - 90F;
            float f5 = f4 - this.rotationYaw;
            this.moveForward = (float) this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
            for (; f5 < -180F; f5 += 360F) {
            }
            for (; f5 >= 180F; f5 -= 360F) {
            }
            if (f5 > 30F) {
                f5 = 30F;
            }
            if (f5 < -30F) {
                f5 = -30F;
            }
            this.rotationYaw += f5;
            if (hasAttacked && (this.entityLivingToAttack != null)) {
                double d4 = entityLivingToAttack.posX - this.posX;
                double d5 = entityLivingToAttack.posZ - this.posZ;
                float f6 = this.rotationYaw;
                this.rotationYaw = (float) ((Math.atan2(d5, d4) * 180D) / 3.1415927410125728D) - 90F;
                float f7 = (((f6 - this.rotationYaw) + 90F) * 3.141593F) / 180F;
                this.moveStrafing = -MathHelper.sin(f7) * this.moveForward * 1.0F;
                this.moveForward = MathHelper.cos(f7) * this.moveForward * 1.0F;
            }
            if (d3 > 0.0D) {
                this.isJumping = true;
            }
        }
        if (this.entityLivingToAttack != null) {
            faceEntity(this.entityLivingToAttack, 30F, 30F);
        }
        if (this.isCollidedHorizontally) {
            this.isJumping = true;
        }
        if ((this.rand.nextFloat() < 0.8F) && (flag1 || flag2)) {
            this.isJumping = true;
        }
    }*/

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
                && (getName() != null && !getName().equals("") && (this.riddenByEntity == null) && (this.ridingEntity == null));
    }

    @Override
    public boolean updateMount() {
        return false;
    }

    @Override
    public boolean forceUpdates() {
        return false;
    }

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
    public int pitchRotationOffset() {
        return 0;
    }

    @Override
    public int rollRotationOffset() {
        return 0;
    }

    @Override
    public int yawRotationOffset() {
        return 0;
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
    public void riderIsDisconnecting(boolean flag) {
        this.riderIsDisconnecting = true;
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
}
