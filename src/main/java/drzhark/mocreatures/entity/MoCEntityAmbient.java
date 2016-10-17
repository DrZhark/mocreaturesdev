package drzhark.mocreatures.entity;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ai.PathNavigateFlyer;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import drzhark.mocreatures.entity.item.MoCEntityKittyBed;
import drzhark.mocreatures.entity.item.MoCEntityLitterBox;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

public abstract class MoCEntityAmbient extends EntityAnimal implements IMoCEntity {

    protected static final DataParameter<Boolean> ADULT = EntityDataManager.<Boolean>createKey(MoCEntityAmbient.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Integer> TYPE = EntityDataManager.<Integer>createKey(MoCEntityAmbient.class, DataSerializers.VARINT);
    protected static final DataParameter<Integer> AGE = EntityDataManager.<Integer>createKey(MoCEntityAmbient.class, DataSerializers.VARINT);
    protected static final DataParameter<String> NAME_STR = EntityDataManager.<String>createKey(MoCEntityAmbient.class, DataSerializers.STRING);

    protected String texture;
    protected boolean riderIsDisconnecting;
    protected PathNavigate navigatorFlyer;

    public MoCEntityAmbient(World world) {
        super(world);
        this.navigatorFlyer = new PathNavigateFlyer(this, world);
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getTexture(this.texture);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
    }

    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData par1EntityLivingData) {
        selectType();
        return super.onInitialSpawn(difficulty, par1EntityLivingData);
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
    public EntityAgeable createChild(EntityAgeable var1) {
        return null;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(ADULT, false);
        this.dataManager.register(TYPE, 0);
        this.dataManager.register(AGE, 45);
        this.dataManager.register(NAME_STR, "");
    }

    @Override
    public void setType(int i) {
        this.dataManager.set(TYPE, i);
    }

    @Override
    public int getType() {
    	return this.dataManager.get(TYPE);
    }

    public void setDisplayName(boolean flag) {
    }

    @Override
    public boolean renderName() {
        return MoCreatures.proxy.getDisplayPetName()
                && (getPetName() != null && !getPetName().equals("") && (!this.isBeingRidden()) && (this.getRidingEntity() == null));
    }

    @Override
    public boolean getIsAdult() {
    	return this.dataManager.get(ADULT);
    }

    @Override
    public void setAdult(boolean flag) {
    	this.dataManager.set(ADULT, flag);
    }

    @Override
    public String getPetName() {
        return this.dataManager.get(NAME_STR);
    }

    @Override
    public void setPetName(String name) {
    	this.dataManager.set(NAME_STR, name);
    }

    @Override
    public int getEdad() {
        return this.dataManager.get(AGE);
    }

    @Override
    public void setEdad(int i) {
    	this.dataManager.set(AGE, i);
    }

    @Override
    public boolean getIsTamed() {
        return false;
    }

    @Override
    public int getOwnerPetId() {
        return 0;
    }

    @Override
    public void setOwnerPetId(int petId) {
    }

    @Override
    public UUID getOwnerId() {
        return null;
    }

    @Override
    protected boolean canDespawn() {
        if (MoCreatures.proxy.forceDespawns) {
            return !getIsTamed();
        } else {
            return false;
        }
    }

    /**
     * called in getCanSpawnHere to make sure the right type of creature spawns
     * in the right biome i.e. snakes, rays, bears, BigCats and later wolves,
     * etc.
     */
    @Override
    public boolean checkSpawningBiome() {
        return true;
    }

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

    @Override
    public void onLivingUpdate() {
        if (MoCreatures.isServer()) {
            if (isMovementCeased()) {
                this.getNavigator().clearPathEntity();
            }
            this.getNavigator().onUpdateNavigation();
        }
        super.onLivingUpdate();
    }

    public boolean swimmerEntity() {
        return false;
    }

    public boolean isSwimming() {
        return ((isInsideOfMaterial(Material.WATER)));
    }

    /**
     * Used to breed
     *
     * @param item1
     * @return
     */
    public boolean isMyAphrodisiac(Item item1) {
        return false;
    }

    //used to drop armor, inventory, saddles, etc.
    public void dropMyStuff() {
    }

    /**
     * Used to heal the animal
     *
     * @param itemstack
     * @return
     */
    protected boolean isMyHealFood(ItemStack itemstack) {
        return false;
    }

    @Override
    public boolean isInWater() {
        if (swimmerEntity()) {
            return false;
        }
        return super.isInWater();
    }

    @Override
    public boolean canBreatheUnderwater() {
        return swimmerEntity();
    }

    public EntityItem getClosestItem(Entity entity, double d, ItemStack item, ItemStack item1) {
        double d1 = -1D;
        EntityItem entityitem = null;
        List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(d, d, d));
        for (int k = 0; k < list.size(); k++) {
            Entity entity1 = list.get(k);
            if (!(entity1 instanceof EntityItem)) {
                continue;
            }
            EntityItem entityitem1 = (EntityItem) entity1;
            if ((entityitem1.getEntityItem() != item) && (entityitem1.getEntityItem() != item1)) {
                continue;
            }
            double d2 = entityitem1.getDistanceSq(entity.posX, entity.posY, entity.posZ);
            if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1))) {
                d1 = d2;
                entityitem = entityitem1;
            }
        }

        return entityitem;
    }

    public EntityItem getClosestEntityItem(Entity entity, double d) {
        double d1 = -1D;
        EntityItem entityitem = null;
        List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(d, d, d));
        for (int k = 0; k < list.size(); k++) {
            Entity entity1 = list.get(k);
            if (!(entity1 instanceof EntityItem)) {
                continue;
            }
            EntityItem entityitem1 = (EntityItem) entity1;
            double d2 = entityitem1.getDistanceSq(entity.posX, entity.posY, entity.posZ);
            if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1))) {
                d1 = d2;
                entityitem = entityitem1;
            }
        }

        return entityitem;
    }

    public void faceLocation(int i, int j, int k, float f) {
        double var4 = i + 0.5D - this.posX;
        double var8 = k + 0.5D - this.posZ;
        double var6 = j + 0.5D - this.posY;
        double var14 = MathHelper.sqrt_double(var4 * var4 + var8 * var8);
        float var12 = (float) (Math.atan2(var8, var4) * 180.0D / Math.PI) - 90.0F;
        float var13 = (float) (-(Math.atan2(var6, var14) * 180.0D / Math.PI));
        this.rotationPitch = -this.updateRotation(this.rotationPitch, var13, f);
        this.rotationYaw = this.updateRotation(this.rotationYaw, var12, f);
    }

    /**
     * Arguments: current rotation, intended rotation, max increment.
     */
    private float updateRotation(float par1, float par2, float par3) {
        float var4;

        for (var4 = par2 - par1; var4 < -180.0F; var4 += 360.0F) {
            ;
        }

        while (var4 >= 180.0F) {
            var4 -= 360.0F;
        }

        if (var4 > par3) {
            var4 = par3;
        }

        if (var4 < -par3) {
            var4 = -par3;
        }

        return par1 + var4;
    }

    public void getMyOwnPath(Entity entity, float f) {
        Path pathentity = this.getNavigator().getPathToEntityLiving(entity);
        if (pathentity != null) {
            this.getNavigator().setPath(pathentity, 1D);
        }
    }

    /**
     * Called to make ridden entities pass on collision to rider
     */
    public void Riding() {
        if ((this.isBeingRidden()) && (this.getRidingEntity() instanceof EntityPlayer)) {
            EntityPlayer entityplayer = (EntityPlayer) this.getRidingEntity();
            List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(1.0D, 0.0D, 1.0D));
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    Entity entity = list.get(i);
                    if (entity.isDead) {
                        continue;
                    }
                    entity.onCollideWithPlayer(entityplayer);
                    if (!(entity instanceof EntityMob)) {
                        continue;
                    }
                    float f = getDistanceToEntity(entity);
                    if ((f < 2.0F) && (this.rand.nextInt(10) == 0)) {
                        
                    }
                }
            }
            if (entityplayer.isSneaking()) {
                if (!this.worldObj.isRemote) {
                    entityplayer.dismountRidingEntity();
                }
            }
        }
    }

    protected void getPathOrWalkableBlock(Entity entity, float f) {
        Path pathentity = this.navigator.getPathToPos(entity.getPosition());
        if ((pathentity == null) && (f > 8F)) {
            int i = MathHelper.floor_double(entity.posX) - 2;
            int j = MathHelper.floor_double(entity.posZ) - 2;
            int k = MathHelper.floor_double(entity.getEntityBoundingBox().minY);
            for (int l = 0; l <= 4; l++) {
                for (int i1 = 0; i1 <= 4; i1++) {
                    BlockPos pos = new BlockPos(i, j, k);
                    if (((l < 1) || (i1 < 1) || (l > 3) || (i1 > 3)) && this.worldObj.getBlockState(pos.add(l, -1, i1)).isNormalCube()
                            && !this.worldObj.getBlockState(pos.add(l, 0, i1)).isNormalCube()
                            && !this.worldObj.getBlockState(pos.add(l, 1, i1)).isNormalCube()) {
                        setLocationAndAngles((i + l) + 0.5F, k, (j + i1) + 0.5F, this.rotationYaw, this.rotationPitch);
                        return;
                    }
                }
            }
        } else {
            this.navigator.setPath(pathentity, 16F);
        }
    }

    public boolean getCanSpawnHereAnimal() {
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(getEntityBoundingBox().minY);
        int k = MathHelper.floor_double(this.posZ);
        BlockPos pos = new BlockPos(i, j, k);
        return this.worldObj.getBlockState(pos.down()).getBlock() == Blocks.GRASS && this.worldObj.getLight(pos) > 8;
    }

    public boolean getCanSpawnHereCreature() {
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(getEntityBoundingBox().minY);
        int k = MathHelper.floor_double(this.posZ);
        return getBlockPathWeight(new BlockPos(i, j, k)) >= 0.0F;
    }

    public boolean getCanSpawnHereLiving() {
        return this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox())
                && this.worldObj.getCollisionBoxes(this, this.getEntityBoundingBox()).size() == 0
                && !this.worldObj.containsAnyLiquid(this.getEntityBoundingBox());
    }

    public boolean getCanSpawnHereAquatic() {
        return this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox());
    }

    @Override
    public boolean getCanSpawnHere() {
        if (MoCreatures.entityMap.get(this.getClass()).getFrequency() <= 0) {
            return false;
        }
        BlockPos pos = new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(getEntityBoundingBox().minY), this.posZ);

        String s = MoCTools.biomeName(this.worldObj, pos);

        if (s.equals("Jungle") || s.equals("JungleHills")) {
            return getCanSpawnHereJungle();
        }

        return super.getCanSpawnHere();
    }

    public boolean getCanSpawnHereJungle() {
        if (this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox())
                && this.worldObj.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty()
                && !this.worldObj.containsAnyLiquid(this.getEntityBoundingBox())) {
            int var1 = MathHelper.floor_double(this.posX);
            int var2 = MathHelper.floor_double(this.getEntityBoundingBox().minY);
            int var3 = MathHelper.floor_double(this.posZ);

            if (var2 < 63) {
                return false;
            }

            BlockPos pos = new BlockPos(var1, var2, var3);
            IBlockState blockstate = this.worldObj.getBlockState(pos.down());
            final Block block = blockstate.getBlock();

            if (block == Blocks.GRASS || block == Blocks.LEAVES || block.isLeaves(blockstate, this.worldObj, pos.down())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound = MoCTools.getEntityData(this);
        nbttagcompound.setBoolean("Adult", getIsAdult());
        nbttagcompound.setInteger("Edad", getEdad());
        nbttagcompound.setString("Name", getPetName());
        nbttagcompound.setInteger("TypeInt", getType());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        nbttagcompound = MoCTools.getEntityData(this);
        setAdult(nbttagcompound.getBoolean("Adult"));
        setEdad(nbttagcompound.getInteger("Edad"));
        setPetName(nbttagcompound.getString("Name"));
        setType(nbttagcompound.getInteger("TypeInt"));
    }

    public void moveEntityWithHeadingOld(float f, float f1) {
        //If the entity is not ridden by entityplayer, then execute the normal Entityliving code
        if (!isFlyer() && (!rideableEntity() || !this.isBeingRidden())) {
            super.moveEntityWithHeading(f, f1);
            return;
        }

        if (handleWaterMovement()) {
            if (this.isBeingRidden()) {
                this.motionX += this.getRidingEntity().motionX * (getCustomSpeed() / 2.0D);
                this.motionZ += this.getRidingEntity().motionZ * (getCustomSpeed() / 2.0D);

                if (!this.worldObj.isRemote) {
                    moveEntity(this.motionX, this.motionY, this.motionZ);
                }

                this.rotationPitch = this.getRidingEntity().rotationPitch * 0.5F;
                if (this.rand.nextInt(20) == 0) {
                    this.rotationYaw = this.getRidingEntity().rotationYaw;
                }
                setRotation(this.rotationYaw, this.rotationPitch);

                if (MoCreatures.isServer() && !getIsTamed()) {
                    MoCTools.playCustomSound(this, this.getAngrySound());
                    this.getRidingEntity().motionY += 0.3D;
                    this.getRidingEntity().motionZ -= 0.3D;
                    this.getRidingEntity().dismountRidingEntity();
                }
            }
            double d = this.posY;
            if (!this.worldObj.isRemote) {
                moveRelative(f, f1, 0.02F);
                moveEntity(this.motionX, this.motionY, this.motionZ);
            }
            this.motionX *= 0.800000011920929D;
            this.motionY *= 0.800000011920929D;
            this.motionZ *= 0.800000011920929D;
            this.motionY -= 0.02D;
            if (this.isCollidedHorizontally
                    && isOffsetPositionInLiquid(this.motionX, ((this.motionY + 0.60000002384185791D) - this.posY) + d, this.motionZ)) {
                this.motionY = 0.30000001192092901D;
            }
        } else if (isNotColliding()) {
            if (this.isBeingRidden()) {
                this.motionX += this.getRidingEntity().motionX * (getCustomSpeed() / 2.0D);
                this.motionZ += this.getRidingEntity().motionZ * (getCustomSpeed() / 2.0D);

                moveEntity(this.motionX, this.motionY, this.motionZ);

                this.rotationPitch = this.getRidingEntity().rotationPitch * 0.5F;
                if (this.rand.nextInt(20) == 0) {
                    this.rotationYaw = this.getRidingEntity().rotationYaw;
                }
                setRotation(this.rotationYaw, this.rotationPitch);
                if (MoCreatures.isServer() && !getIsTamed()) {
                    MoCTools.playCustomSound(this, this.getAngrySound());
                    this.getRidingEntity().motionY += 0.3D;
                    this.getRidingEntity().motionZ -= 0.3D;
                    this.getRidingEntity().dismountRidingEntity();
                }
            }
            double d1 = this.posY;

            moveRelative(f, f1, 0.02F);
            moveEntity(this.motionX, this.motionY, this.motionZ);

            this.motionX *= 0.5D;
            this.motionY *= 0.5D;
            this.motionZ *= 0.5D;
            this.motionY -= 0.02D;
            if (this.isCollidedHorizontally
                    && isOffsetPositionInLiquid(this.motionX, ((this.motionY + 0.60000002384185791D) - this.posY) + d1, this.motionZ)) {
                this.motionY = 0.30000001192092901D;
            }
        } else {
            float f2 = 0.91F;
            if (this.onGround) {
                f2 = 0.5460001F;
                Block block =
                        this.worldObj.getBlockState(
                                new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(getEntityBoundingBox().minY) - 1, MathHelper
                                        .floor_double(this.posZ))).getBlock();
                if (block != Blocks.AIR) {
                    f2 = block.slipperiness * 0.91F;
                }
            }

            float f3 = 0.162771F / (f2 * f2 * f2);
            moveRelative(f, f1, this.onGround ? 0.1F * f3 : 0.02F);

            if (isOnLadder()) {
                this.fallDistance = 0.0F;
                if (this.motionY < -0.15D) {
                    this.motionY = -0.15D;
                }
            }
            if ((this.isBeingRidden()) && !getIsTamed()) {

                if (this.rand.nextInt(10) == 0) {
                    this.motionX += this.rand.nextDouble() / 30D;
                    this.motionZ += this.rand.nextDouble() / 10D;
                }
                // blood - This must be run on server side only since it causes glitch/twitch if run on both sides.
                if (!this.worldObj.isRemote) {
                    moveEntity(this.motionX, this.motionY, this.motionZ);
                }
                if (MoCreatures.isServer() && this.rand.nextInt(50) == 0) {
                    MoCTools.playCustomSound(this, this.getAngrySound());
                    this.getRidingEntity().motionY += 0.9D;
                    this.getRidingEntity().motionZ -= 0.3D;
                    this.getRidingEntity().dismountRidingEntity();
                }
            }
            if ((this.isBeingRidden()) && getIsTamed()) {
                if (!selfPropelledFlyer() || (selfPropelledFlyer() && !isOnAir())) {
                    this.motionX += this.getRidingEntity().motionX * getCustomSpeed();
                    this.motionZ += this.getRidingEntity().motionZ * getCustomSpeed();
                }

                // blood - This must be run on server side only since it causes glitch/twitch if run on both sides.
                if (MoCreatures.isServer()) {
                    moveEntity(this.motionX, this.motionY, this.motionZ);
                }

                this.prevRotationYaw = this.rotationYaw = this.getRidingEntity().rotationYaw;
                this.rotationPitch = this.getRidingEntity().rotationPitch * 0.5F;
                setRotation(this.rotationYaw, this.rotationPitch);
            }
            // blood - This must be run on server side only since it causes glitch/twitch if run on both sides.
            if (!this.worldObj.isRemote) {
                //needs to be left in so flying mounts can be controlled
                moveEntity(this.motionX, this.motionY, this.motionZ);
            }
            if (isFlyingAlone()) {
                int distY = MoCTools.distanceToFloor(this);
                if (distY <= maxFlyingHeight()) {
                    this.motionY *= f2;
                }
                if (distY <= maxFlyingHeight() && (this.isCollidedHorizontally || this.rand.nextInt(100) == 0)) {
                    this.motionY += 0.1D;
                }
                if (distY > maxFlyingHeight() || this.rand.nextInt(150) == 0) {
                    this.motionY -= 0.10D;
                }

                if (isOnAir()) {
                    double velX = 0.05F * Math.cos((MoCTools.realAngle(this.rotationYaw - 90F)) / 57.29578F);
                    double velZ = 0.05F * Math.sin((MoCTools.realAngle(this.rotationYaw - 90F)) / 57.29578F);
                    this.motionX -= velX;
                    this.motionZ -= velZ;
                }
            }

            if (isFlyer() && !this.isBeingRidden() && getAttackTarget() != null && getAttackTarget().posY < this.posY
                    && this.rand.nextInt(30) == 0) {
                this.motionY = -0.25D;
            }

            if (isFlyer() && (this.isBeingRidden()) && getIsTamed()) {
                this.motionY -= 0.08D;
                this.motionY *= myFallSpeed();//0.6D;
            } else if (!isFlyingAlone()) {
                this.motionY -= 0.08D;
                this.motionY *= 0.98000001907348633D;
            }

            if (this.isBeingRidden() && isOnAir()) {
                f2 = flyerFriction();

            }
            this.motionX *= f2;
            this.motionZ *= f2;
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

    /**
    * Used for flyer mounts, to calculate fall speed
    *
    * @return
    */
    protected double myFallSpeed() {
        return 0.6D;
    }

    /**
     * flyer mounts Y thrust
     *
     * @return
     */
    protected double flyerThrust() {
        return 0.3D;
    }

    /**
     * flyer deceleration on Z and X axis
     *
     * @return
     */
    protected float flyerFriction() {
        return 0.91F;
    }

    /**
     * Alternative flyer mount movement, when true, the player only controls
     * frequency of wing flaps
     *
     * @return
     */
    protected boolean selfPropelledFlyer() {
        return false;
    }

    /**
     * Sets a flag that will make the Entity "jump" in the next onGround
     * moveEntity update
     */
    @Override
    public void makeEntityJump() {
    }

    /**
     * Boolean used for flying mounts
     */
    public boolean isFlyer() {
        return false;
    }

    /**
     * How difficult is the creature to be tamed? the Higher the number, the
     * more difficult
     */
    public int getMaxTemper() {
        return 100;
    }

    /**
     * mount speed
     */
    public double getCustomSpeed() {
        return 0.8D;
    }

    /**
     * mount jumping power
     */
    public double getCustomJump() {
        return 0.4D;
    }

    /**
     * sound played when an untamed mount buckles rider
     */
    protected SoundEvent getAngrySound() {
        return null;
    }

    @Override
    public void makeEntityDive() {
    }

    /**
     * Is this a rideable entity?
     */
    public boolean rideableEntity() {
        return false;
    }

    @Override
    public int nameYOffset() {
        return -80;
    }

    /**
     * fixes bug with entities following a player carrying wheat
     */
    protected Entity findPlayerToAttack() {
        return null;
    }

    public void repelMobs(Entity entity1, Double dist, World worldObj) {
        List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(entity1, entity1.getEntityBoundingBox().expand(dist, 4D, dist));
        for (int i = 0; i < list.size(); i++) {
            Entity entity = list.get(i);
            if (!(entity instanceof EntityMob)) {
                continue;
            }
            EntityMob entitymob = (EntityMob) entity;
            entitymob.setAttackTarget(null);
            entitymob.getNavigator().clearPathEntity();
        }
    }

    public void faceItem(int i, int j, int k, float f) {
        double d = i - this.posX;
        double d1 = k - this.posZ;
        double d2 = j - this.posY;
        double d3 = MathHelper.sqrt_double((d * d) + (d1 * d1));
        float f1 = (float) ((Math.atan2(d1, d) * 180D) / 3.1415927410125728D) - 90F;
        float f2 = (float) ((Math.atan2(d2, d3) * 180D) / 3.1415927410125728D);
        this.rotationPitch = -adjustRotation(this.rotationPitch, f2, f);
        this.rotationYaw = adjustRotation(this.rotationYaw, f1, f);
    }

    public float adjustRotation(float f, float f1, float f2) {
        float f3 = f1;
        for (f3 = f1 - f; f3 < -180F; f3 += 360F) {
        }
        for (; f3 >= 180F; f3 -= 360F) {
        }
        if (f3 > f2) {
            f3 = f2;
        }
        if (f3 < -f2) {
            f3 = -f2;
        }
        return f + f3;
    }

    public boolean isFlyingAlone() {
        return false;
    }

    public float getMoveSpeed() {
        return 0.7F;
    }

    /**
     * Used to synchronize animations between server and client
     *
     * @param attackType
     */
    @Override
    public void performAnimation(int attackType) {
    }

    /**
     * Used to follow the player carrying the item
     *
     * @param par1ItemStack
     * @return
     */
    public boolean isMyFavoriteFood(ItemStack par1ItemStack) {
        return false;
    }

    @SuppressWarnings("unused")
    private void followPlayer() {
        EntityPlayer entityplayer1 = this.worldObj.getClosestPlayerToEntity(this, 24D);
        if (entityplayer1 == null) {
            return;
        }

        ItemStack itemstack1 = entityplayer1.inventory.getCurrentItem();
        if (itemstack1 != null && isMyFavoriteFood(itemstack1)) {
            this.getNavigator().tryMoveToEntityLiving(entityplayer1, 1D); 
            }
    }

    public boolean isOnAir() {
        return (this.worldObj.isAirBlock(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY - 0.2D), MathHelper
                .floor_double(this.posZ))) && this.worldObj.isAirBlock(new BlockPos(MathHelper.floor_double(this.posX), MathHelper
                .floor_double(this.posY - 1.2D), MathHelper.floor_double(this.posZ))));
    }

    @Override
    public float getSizeFactor() {
        return 1.0F;
    }

    @Override
    public float getAdjustedYOffset() {
        return 0F;
    }

    public boolean getIsRideable() {
        return false;
    }

    public void setRideable(boolean b) {
    }

    public boolean entitiesToIgnore(Entity entity) {
        return ((!(entity instanceof EntityLiving)) || (entity instanceof EntityMob) || (entity instanceof EntityPlayer && this.getIsTamed())
                || (entity instanceof MoCEntityKittyBed) || (entity instanceof MoCEntityLitterBox)
                || (this.getIsTamed() && (entity instanceof MoCEntityAnimal && ((MoCEntityAnimal) entity).getIsTamed()))
                || ((entity instanceof EntityWolf) && !(MoCreatures.proxy.attackWolves))
                || ((entity instanceof MoCEntityHorse) && !(MoCreatures.proxy.attackHorses))
                || (entity.width > this.width && entity.height > this.height) || (entity instanceof MoCEntityEgg));
    }

    @Override
    public void setArmorType(int i) {
    }

    /**
     * Finds and entity described in entitiesToInclude at d distance
     *
     * @param d
     * @return
     */
    protected EntityLivingBase getBoogey(double d) {
        EntityLivingBase entityliving = null;
        List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(d, 4D, d));
        for (int i = 0; i < list.size(); i++) {
            Entity entity = list.get(i);
            if (entitiesToInclude(entity)) {
                entityliving = (EntityLivingBase) entity;
            }
        }
        return entityliving;
    }

    /**
     * Used in getBoogey to specify what kind of entity to look for
     *
     * @param entity
     * @return
     */
    public boolean entitiesToInclude(Entity entity) {
        return ((entity instanceof EntityLivingBase) && ((entity.width >= 0.5D) || (entity.height >= 0.5D)));
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

    protected boolean canBeTrappedInNet() {
        return (this instanceof IMoCTameable) && getIsTamed();
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
        if (type == EnumCreatureType.AMBIENT) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean canAttackTarget(EntityLivingBase entity) {
        return false;
    }

    @Override
    public boolean canBeLeashedTo(EntityPlayer player) {
        return false;
    }

    @Override
    public boolean getIsSitting() {
        return false;
    }

    @Override
    public boolean isNotScared() {
        return false;
    }

    @Override
    public boolean isMovementCeased() {
        return getIsSitting();
    }

    @Override
    public boolean shouldAttackPlayers() {
        return this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL;
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    /**
     * The distance the entity will float under the surface. 0F = surface 1.0F = 1 block under
     * @return
     */
    @Override
    public double getDivingDepth() {
        return 0.5D;
    }

    @Override
    public boolean isDiving() {
        return false;
    }

    @Override
    public void forceEntityJump() {
        this.jump();
    }

    @Override
    public void fall(float f, float f1) {
    }

    @Override
    public int minFlyingHeight() {
        return 2;
    }

    /**
     * Maximum flyer height when moving autonomously
     *
     * @return
     */
    public int maxFlyingHeight() {
        return 4;
    }

    @Override
    public void moveEntityWithHeading(float strafe, float forward) {
        if (!getIsFlying()) {
            super.moveEntityWithHeading(strafe, forward);
            return;
        }
        this.moveEntityWithHeadingFlying(strafe, forward);
    }

    public void moveEntityWithHeadingFlying(float strafe, float forward) {
        if (this.isServerWorld()) {

            this.moveRelative(strafe, forward, 0.1F);
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 0.8999999761581421D;
            this.motionY *= 0.8999999761581421D;
            this.motionZ *= 0.8999999761581421D;
        } else {
            super.moveEntityWithHeading(strafe, forward);
        }
    }

    @Override
    public boolean getIsFlying() {
        return false;
    }
}
