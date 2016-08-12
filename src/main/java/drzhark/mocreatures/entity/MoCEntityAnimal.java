package drzhark.mocreatures.entity;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ai.EntityAIMoverHelperMoC;
import drzhark.mocreatures.entity.ai.PathNavigateFlyer;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import drzhark.mocreatures.entity.item.MoCEntityKittyBed;
import drzhark.mocreatures.entity.item.MoCEntityLitterBox;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import java.util.List;

public abstract class MoCEntityAnimal extends EntityAnimal implements IMoCEntity {

    protected boolean divePending;
    protected boolean jumpPending;
    protected int temper;
    protected boolean isEntityJumping;
    private PathEntity entitypath;
    // used by MoCPlayerTracker to prevent dupes when a player disconnects on animal from server
    protected boolean riderIsDisconnecting;
    private int petDataId = -1;
    public float moveSpeed;
    protected String texture;
    private int huntingCounter;
    protected PathNavigate navigatorWater;
    protected PathNavigate navigatorFlyer;

    private double divingDepth;
    private boolean randomAttributesUpdated; //used to update divingDepth on world load

    public MoCEntityAnimal(World world) {
        super(world);
        setTamed(false);
        setAdult(true);
        this.riderIsDisconnecting = false;
        this.texture = "blank.jpg";
        this.navigatorWater = new PathNavigateSwimmer(this, world);
        this.moveHelper = new EntityAIMoverHelperMoC(this);
        this.navigatorFlyer = new PathNavigateFlyer(this, world);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getTexture(this.texture);
    }

    @Override
    public IEntityLivingData onSpawnFirstTime(DifficultyInstance difficulty, IEntityLivingData par1EntityLivingData) {
        selectType();
        return super.onSpawnFirstTime(difficulty, par1EntityLivingData);
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
        this.dataWatcher.addObject(16, Byte.valueOf((byte) 0)); // isTamed - 0 false 1 true
        this.dataWatcher.addObject(17, String.valueOf("")); // displayName empty string by default
        this.dataWatcher.addObject(18, Integer.valueOf(0)); // int ageTicks / "edad"
        this.dataWatcher.addObject(19, Integer.valueOf(0)); // int type
        this.dataWatcher.addObject(20, String.valueOf("")); //owners name
        this.dataWatcher.addObject(21, Byte.valueOf((byte) 0)); // isAdult - 0 false 1 true
    }

    @Override
    public void setType(int i) {
        this.dataWatcher.updateObject(19, Integer.valueOf(i));
    }

    @Override
    public int getType() {
        return this.dataWatcher.getWatchableObjectInt(19);
    }

    public void setDisplayName(boolean flag) {
    }

    @Override
    public boolean renderName() {
        return MoCreatures.proxy.getDisplayPetName()
                && (getName() != null && !getName().equals("") && (this.riddenByEntity == null) && (this.ridingEntity == null));
    }

    @Override
    public boolean getIsAdult() {
        return (this.dataWatcher.getWatchableObjectByte(21) == 1);
    }

    @Override
    public boolean getIsTamed() {
        return (this.dataWatcher.getWatchableObjectByte(16) == 1);
    }

    @Override
    public String getName() {
        return this.dataWatcher.getWatchableObjectString(17);
    }

    /**
     * @return networked Entity "Age" in integer value, typical values are
     *         0-100. Old float eDad was typically 0F-1.0F
     */
    @Override
    public int getEdad() {
        return this.dataWatcher.getWatchableObjectInt(18);
    }

    public boolean getIsJumping() {
        return this.isEntityJumping;

    }

    @Override
    public void setEdad(int i) {
        this.dataWatcher.updateObject(18, Integer.valueOf(i));
    }

    @Override
    public void setAdult(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(21, Byte.valueOf(input));
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

    public void setIsJumping(boolean flag) {
        this.isEntityJumping = flag;
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

    public EntityLivingBase getClosestTarget(Entity entity, double d) {
        double d1 = -1D;
        EntityLivingBase entityliving = null;
        List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(d, d, d));
        for (int i = 0; i < list.size(); i++) {
            Entity entity1 = (Entity) list.get(i);
            if (!(entity1 instanceof EntityLivingBase) || (entity1 == entity) || (entity1 == entity.riddenByEntity)
                    || (entity1 == entity.ridingEntity) || (entity1 instanceof EntityPlayer) || (entity1 instanceof EntityMob)
                    || (this.height <= entity1.height) || (this.width <= entity1.width)) {
                continue;
            }
            double d2 = entity1.getDistanceSq(entity.posY, entity.posZ, entity.motionX);
            if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1)) && ((EntityLivingBase) entity1).canEntityBeSeen(entity)) {
                d1 = d2;
                entityliving = (EntityLivingBase) entity1;
            }
        }

        return entityliving;
    }

    protected EntityLivingBase getClosestSpecificEntity(Entity entity, Class myClass, double d) {
        double d1 = -1D;
        EntityLivingBase entityliving = null;
        List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(entity, entity.getEntityBoundingBox().expand(d, d, d));
        for (int i = 0; i < list.size(); i++) {
            Entity entity1 = (Entity) list.get(i);
            if (!myClass.isAssignableFrom(entity1.getClass())) {
                continue;
            }

            double d2 = entity1.getDistanceSq(entity.posX, entity.posY, entity.posZ);
            if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1)))// && ((EntityLiving) entity1).canEntityBeSeen(entity))
            {
                d1 = d2;
                entityliving = (EntityLivingBase) entity1;
            }
        }

        return entityliving;
    }

    //TODO
    public boolean entitiesToIgnore(Entity entity) {
        return ((!(entity instanceof EntityLiving)) || (entity instanceof EntityMob) || (entity instanceof EntityPlayer)
                || (entity instanceof MoCEntityKittyBed) || (entity instanceof MoCEntityLitterBox)
                || (this.getIsTamed() && (entity instanceof IMoCEntity && ((IMoCEntity) entity).getIsTamed()))
                || ((entity instanceof EntityWolf) && !(MoCreatures.proxy.attackWolves))
                || ((entity instanceof MoCEntityHorse) && !(MoCreatures.proxy.attackHorses))
                || (entity.width >= this.width || entity.height >= this.height) || (entity instanceof MoCEntityEgg) || ((entity instanceof IMoCEntity) && !MoCreatures.proxy.enableHunters));
    }

    /**
     * Finds and entity described in entitiesToInclude at d distance
     *
     * @param d
     * @return
     */
    protected EntityLivingBase getBoogey(double d) {
        double d1 = -1D;
        EntityLivingBase entityliving = null;
        List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(d, 4D, d));
        for (int i = 0; i < list.size(); i++) {
            Entity entity = (Entity) list.get(i);
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
        return ((entity.getClass() != this.getClass()) && (entity instanceof EntityLivingBase) && ((entity.width >= 0.5D) || (entity.height >= 0.5D)));
    }

    @Override
    public void onLivingUpdate() {
        if (MoCreatures.isServer()) {
            if (rideableEntity() && this.riddenByEntity != null) {
                Riding();
            }

            /*if (forceUpdates() && this.rand.nextInt(500) == 0) {
                MoCTools.forceDataSync(this);
            }*/

            if (isMovementCeased()) {
                this.getNavigator().clearPathEntity();
            }

            if (!getIsAdult() && (this.rand.nextInt(300) == 0) && getEdad() < getMaxEdad()) {
                setEdad(getEdad() + 1);
                if (getEdad() >= getMaxEdad()) {
                    setAdult(true);
                }
            }

            if (MoCreatures.proxy.enableHunters && this.isReadyToHunt() && !this.getIsHunting() && this.rand.nextInt(500) == 0) {
                setIsHunting(true);
            }

            if (getIsHunting() && ++this.huntingCounter > 50) {
                setIsHunting(false);
            }

            this.getNavigator().onUpdateNavigation();
        }

        if (this.isInWater() && this.isAmphibian()) {
            if (this.rand.nextInt(500) == 0 || !this.randomAttributesUpdated) {
                this.setNewDivingDepth();
                this.randomAttributesUpdated = true;
            }

        }

        this.resetInLove(); //TODO Test!
        super.onLivingUpdate();
    }

    public int getMaxEdad() {
        return 100;
    }

    @Override
    public boolean isNotScared() {
        return false;
    }

    public boolean swimmerEntity() {
        return false;
    }

    public boolean isSwimming() {
        return ((isInsideOfMaterial(Material.water)));
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
        if (isAmphibian()) {
            return this.worldObj.handleMaterialAcceleration(this.getEntityBoundingBox().expand(0.0D, -0.2D, 0.0D), Material.water, this);
        }
        return super.isInWater();
    }

    @Override
    public boolean canBreatheUnderwater() {
        return isAmphibian();
    }

    public EntityItem getClosestItem(Entity entity, double d, Item item, Item item1) {
        double d1 = -1D;
        EntityItem entityitem = null;
        List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(d, d, d));
        for (int k = 0; k < list.size(); k++) {
            Entity entity1 = (Entity) list.get(k);
            if (!(entity1 instanceof EntityItem)) {
                continue;
            }
            EntityItem entityitem1 = (EntityItem) entity1;
            if ((entityitem1.getEntityItem().getItem() != item) && (entityitem1.getEntityItem().getItem() != item1)) {
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
        List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(d, d, d));
        for (int k = 0; k < list.size(); k++) {
            Entity entity1 = (Entity) list.get(k);
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

    public EntityItem getClosestFood(Entity entity, double d) {
        double d1 = -1D;
        EntityItem entityitem = null;
        List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(d, d, d));
        for (int k = 0; k < list.size(); k++) {
            Entity entity1 = (Entity) list.get(k);
            if (!(entity1 instanceof EntityItem)) {
                continue;
            }
            EntityItem entityitem1 = (EntityItem) entity1;
            if (!MoCTools.isItemEdible(entityitem1.getEntityItem().getItem())) {
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
        PathEntity pathentity = this.getNavigator().getPathToEntityLiving(entity);
        if (pathentity != null) {
            this.getNavigator().setPath(pathentity, 1D);
        }
    }

    /**
     * Called to make ridden entities pass on collision to rider
     */
    public void Riding() {
        if ((this.riddenByEntity != null) && (this.riddenByEntity instanceof EntityPlayer)) {
            EntityPlayer entityplayer = (EntityPlayer) this.riddenByEntity;
            List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(1.0D, 0.0D, 1.0D));
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    Entity entity = (Entity) list.get(i);
                    if (entity.isDead) {
                        continue;
                    }
                    entity.onCollideWithPlayer(entityplayer);
                    if (!(entity instanceof EntityMob)) {
                        continue;
                    }
                    float f = getDistanceToEntity(entity);
                    if ((f < 2.0F) && entity instanceof EntityMob && (this.rand.nextInt(10) == 0)) {
                        attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase) entity),
                                (float) ((EntityMob) entity).getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue());
                    }
                }
            }
            if (entityplayer.isSneaking()) {
                this.makeEntityDive();
            }
        }
    }

    protected void getPathOrWalkableBlock(Entity entity, float f) {
        PathEntity pathentity = this.navigator.getPathToPos(entity.getPosition());
        if ((pathentity == null) && (f > 8F)) {
            int i = MathHelper.floor_double(entity.posX) - 2;
            int j = MathHelper.floor_double(entity.posZ) - 2;
            int k = MathHelper.floor_double(entity.getEntityBoundingBox().minY);
            for (int l = 0; l <= 4; l++) {
                for (int i1 = 0; i1 <= 4; i1++) {
                    BlockPos pos = new BlockPos(i, k, j);
                    if (((l < 1) || (i1 < 1) || (l > 3) || (i1 > 3)) && this.worldObj.getBlockState(pos.add(l, -1, i1)).getBlock().isNormalCube()
                            && !this.worldObj.getBlockState(pos.add(l, 0, i1)).getBlock().isNormalCube()
                            && !this.worldObj.getBlockState(pos.add(l, 1, i1)).getBlock().isNormalCube()) {
                        setLocationAndAngles((i + l) + 0.5F, k, (j + i1) + 0.5F, this.rotationYaw, this.rotationPitch);
                        return;
                    }
                }
            }
        } else {
            this.navigator.setPath(pathentity, 16F);
        }
    }

    public MoCEntityAnimal spawnBabyAnimal(EntityAgeable par1EntityAgeable) {
        return null;
    }

    public boolean getCanSpawnHereCreature() {
        BlockPos pos =
                new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.getEntityBoundingBox().minY),
                        MathHelper.floor_double(this.posZ));
        return this.getBlockPathWeight(pos) >= 0.0F;
    }

    public boolean getCanSpawnHereLiving() {
        return this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox())
                && this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty()
                && !this.worldObj.isAnyLiquid(this.getEntityBoundingBox());
    }

    public boolean getCanSpawnHereAquatic() {
        return this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox());
    }

    public boolean getCanSpawnHere2() {
        return getCanSpawnHereCreature() && getCanSpawnHereLiving();
    }

    @Override
    public boolean getCanSpawnHere() {
        if (MoCreatures.entityMap.get(this.getClass()).getFrequency() <= 0) {
            return false;
        }
        if (this.worldObj.provider.getDimensionId() != 0) {
            return getCanSpawnHereCreature() && getCanSpawnHereLiving();
        }
        BlockPos pos = new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(getEntityBoundingBox().minY), this.posZ);

        String s = MoCTools.BiomeName(this.worldObj, pos);

        if (s.toLowerCase().contains("jungle")) {
            return getCanSpawnHereJungle();
        }
        if (s.equals("WyvernBiome")) {
            return getCanSpawnHereMoCBiome();
        }
        return super.getCanSpawnHere();
    }

    private boolean getCanSpawnHereMoCBiome() {
        if (this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox())
                && this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty()
                && !this.worldObj.isAnyLiquid(this.getEntityBoundingBox())) {
            BlockPos pos =
                    new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.getEntityBoundingBox().minY),
                            MathHelper.floor_double(this.posZ));

            if (pos.getY() < 50) {
                return false;
            }

            IBlockState blockstate = this.worldObj.getBlockState(pos.down());

            if (blockstate.getBlock() == MoCreatures.mocDirt || blockstate.getBlock() == MoCreatures.mocGrass
                    || (blockstate.getBlock() != null && blockstate.getBlock().isLeaves(this.worldObj, pos.down()))) {
                return true;
            }
        }
        return false;
    }

    public boolean getCanSpawnHereJungle() {
        if (this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox())
                && this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty()) {
            return true;
        }
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
        nbttagcompound.setString("Owner", getOwnerName());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        setTamed(nbttagcompound.getBoolean("Tamed"));
        setAdult(nbttagcompound.getBoolean("Adult"));
        setEdad(nbttagcompound.getInteger("Edad"));
        setName(nbttagcompound.getString("Name"));
        setType(nbttagcompound.getInteger("TypeInt"));
        setOwner(nbttagcompound.getString("Owner"));
    }

    /**
     * Moves the entity based on the specified heading.  Args: strafe, forward
     */
    @Override
    public void moveEntityWithHeading(float strafe, float forward) {
        if (this.isServerWorld()) {
            if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityLivingBase) {
                this.moveEntityWithRider(strafe, forward); //riding movement
                return;
            }
            if ((this.isAmphibian() && isInWater()) || (this.isFlyer() && getIsFlying())) { //amphibian in water movement
                this.moveFlying(strafe, forward, 0.1F);
                this.moveEntity(this.motionX, this.motionY, this.motionZ);
                this.motionX *= 0.8999999761581421D;
                this.motionY *= 0.8999999761581421D;
                this.motionZ *= 0.8999999761581421D;
                if (this.getAttackTarget() == null) {
                    this.motionY -= 0.005D;
                }
            } else // regular movement
            {
                super.moveEntityWithHeading(strafe, forward);
            }
        } else {
            super.moveEntityWithHeading(strafe, forward);
        }
    }

    public void moveEntityWithHeadingBypassRider(float strafe, float forward) {
        super.moveEntityWithHeading(strafe, forward);
    }

    /**
     ** Riding Code
     * @param strafe
     * @param forward
     */
    public void moveEntityWithRider(float strafe, float forward) {
        if (this.riddenByEntity != null && !getIsTamed() && this.riddenByEntity instanceof EntityLivingBase) {
            this.moveEntityWithRiderUntamed(strafe, forward);
            return;
        }

        boolean flySelfPropelled = selfPropelledFlyer() && isOnAir(); //like the black ostrich
        boolean flyingMount = isFlyer() && (this.riddenByEntity != null) && getIsTamed() && !this.onGround && isOnAir();
        this.prevRotationYaw = this.rotationYaw = this.riddenByEntity.rotationYaw;
        this.rotationPitch = this.riddenByEntity.rotationPitch * 0.5F;
        this.setRotation(this.rotationYaw, this.rotationPitch);
        this.rotationYawHead = this.renderYawOffset = this.rotationYaw;

        if (!selfPropelledFlyer() || (selfPropelledFlyer() && !isOnAir())) {
            strafe = (float) (((EntityLivingBase) this.riddenByEntity).moveStrafing * 0.5F * this.getCustomSpeed());
            forward = (float) (((EntityLivingBase) this.riddenByEntity).moveForward * this.getCustomSpeed());
        }

        if (this.jumpPending && (isFlyer())) {
            this.motionY += flyerThrust();//0.3D;
            this.jumpPending = false;

            if (flySelfPropelled) {
                float velX = MathHelper.sin(this.rotationYaw * (float) Math.PI / 180.0F);
                float velZ = MathHelper.cos(this.rotationYaw * (float) Math.PI / 180.0F);

                this.motionX += (-0.5F * velX);
                this.motionZ += (0.5F * velZ);
            }
        } else if (this.jumpPending && !getIsJumping()) {
            this.motionY = getCustomJump() * 2;
            setIsJumping(true);
            this.jumpPending = false;
        }

        if (this.divePending) {
            this.divePending = false;
            this.motionY -= 0.3D;
        }

        if (MoCreatures.isServer()) {
            if (flyingMount) {
                this.moveEntity(this.motionX, this.motionY, this.motionZ);
                this.moveFlying(strafe, forward, this.flyerFriction() / 10F);
                this.motionY *= this.myFallSpeed();
                this.motionY -= 0.055D;
                this.motionZ *= this.flyerFriction();
                this.motionX *= this.flyerFriction();
            } else {
                this.setAIMoveSpeed((float) this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());
                super.moveEntityWithHeading(strafe, forward);
            }
        }

        if (this.onGround) {
            setIsJumping(false);
            this.divePending = false;
            this.jumpPending = false;
        }

    }

    public void moveEntityWithRiderUntamed(float strafe, float forward) {
        //Riding behaviour if untamed
        if ((this.riddenByEntity != null) && !getIsTamed()) {
            if ((this.rand.nextInt(5) == 0) && !getIsJumping() && this.jumpPending) {
                this.motionY += getCustomJump();
                setIsJumping(true);
                this.jumpPending = false;
            }
            if (this.rand.nextInt(10) == 0) {
                this.motionX += this.rand.nextDouble() / 30D;
                this.motionZ += this.rand.nextDouble() / 10D;
            }
            if (MoCreatures.isServer()) {
                moveEntity(this.motionX, this.motionY, this.motionZ);
            }
            if (MoCreatures.isServer() && this.rand.nextInt(50) == 0) {
                this.riddenByEntity.motionY += 0.9D;
                this.riddenByEntity.motionZ -= 0.3D;
                this.riddenByEntity.mountEntity(null);
                this.ridingEntity = null;
            }
            if (this.onGround) {
                setIsJumping(false);
            }
            if (MoCreatures.isServer() && this instanceof IMoCTameable) {
                int chance = (getMaxTemper() - getTemper());
                if (chance <= 0) {
                    chance = 1;
                }
                if (this.rand.nextInt(chance * 8) == 0) {
                    MoCTools.tameWithName((EntityPlayer) this.riddenByEntity, (IMoCTameable) this);
                }

            }
        }
    }

    /**
     * Maximum flyer height when moving autonomously
     *
     * @return
     */
    @Override
    public int maxFlyingHeight() {
        return 5;
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
        this.jumpPending = true;
    }

    /**
     * Boolean used for flying mounts
     */
    @Override
    public boolean isFlyer() {
        return false;
    }

    public int getTemper() {
        return this.temper;
    }

    public void setTemper(int i) {
        this.temper = i;
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
        return 0.6D;
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
    protected String getMadSound() {
        return null;
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
    * adds a following behavior to animals with a rope
    */
    /*@Override
    protected void updateEntityActionState() {
        if (getIsTamed() && (this.riddenByEntity == null) && (this.roper != null)) {
            float f = this.roper.getDistanceToEntity(this);
            if (f > 3F) {
                getPathOrWalkableBlock(this.roper, f);
            }
        }

        if (!isFlyingAlone()) {
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
        }
        // prevent tamed animals attacking other tamed animals
        else if (!this.entityLivingToAttack.isEntityAlive() || MoCTools.isTamed(this.entityLivingToAttack) || !MoCreatures.proxy.enableHunters) //((entityLivingToAttack instanceof IMoCTameable) && ((IMoCTameable)entityLivingToAttack).getIsTamed() && this.getIsTamed()))
        {
            this.entityLivingToAttack = null;
        } else {
            float f1 = this.entityLivingToAttack.getDistanceToEntity(this);
            if (canEntityBeSeen(this.entityLivingToAttack)) {
                attackEntity(this.entityLivingToAttack, f1);
            }
        }
        if (!hasAttacked && (this.entityLivingToAttack != null) && ((this.entitypath == null) || (this.rand.nextInt(10) == 0))) {
            // entitypath = worldObj.getPathToEntity(this, entityLivingToAttack, f);
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
     * fixes bug with entities following a player carrying wheat
     */
    protected Entity findPlayerToAttack() {
        return null;
    }

    public void repelMobs(Entity entity1, Double dist, World worldObj) {
        List list = worldObj.getEntitiesWithinAABBExcludingEntity(entity1, entity1.getEntityBoundingBox().expand(dist, 4D, dist));
        for (int i = 0; i < list.size(); i++) {
            Entity entity = (Entity) list.get(i);
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

    /**
     * Used to spawn hearts at this location
     */
    public void SpawnHeart() {
        double var2 = this.rand.nextGaussian() * 0.02D;
        double var4 = this.rand.nextGaussian() * 0.02D;
        double var6 = this.rand.nextGaussian() * 0.02D;

        this.worldObj.spawnParticle(EnumParticleTypes.HEART, this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY + 0.5D
                + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, var2, var4, var6);

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

    //TODO MOVE TO NEW AI
    private void followPlayer() {
        EntityPlayer entityplayer1 = this.worldObj.getClosestPlayerToEntity(this, 24D);
        if (entityplayer1 == null) {
            return;
        }

        ItemStack itemstack1 = entityplayer1.inventory.getCurrentItem();
        if (itemstack1 != null && isMyFavoriteFood(itemstack1)) {
            this.getNavigator().tryMoveToEntityLiving(entityplayer1, 1D); //TODO check if 1D is adequate
        }
    }

    /**
     * This method must be overrided to work in conjunction with our
     * onLivingUpdate update packets. It is currently used to fix mount bug when
     * players reconnect.
     */
    /*@Override
    public void mountEntity(Entity par1Entity) {
        if (updateMount()) {
            if (par1Entity == null) {
                if (this.ridingEntity != null) {
                    this.setLocationAndAngles(this.ridingEntity.posX, this.ridingEntity.getEntityBoundingBox().minY + this.ridingEntity.height,
                            this.ridingEntity.posZ, this.rotationYaw, this.rotationPitch);
                    this.ridingEntity.riddenByEntity = null;
                }
                this.ridingEntity = null;
            } else {
                this.ridingEntity = par1Entity;
                par1Entity.riddenByEntity = this;
            }
        } else {
            super.mountEntity(par1Entity);
        }
    }*/

    /*@Override
    public boolean updateMount() {
        return false;
    }*/

    /*@Override
    public boolean forceUpdates() {
        return false;
    }*/

    @Override
    public void makeEntityDive() {
        this.divePending = true;
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

    @Override
    public String getOwnerName() {
        return this.dataWatcher.getWatchableObjectString(20);
    }

    @Override
    public void setOwner(String par1Str) {
        this.dataWatcher.updateObject(20, par1Str);
    }

    @Override
    public void onDeath(DamageSource damagesource) {
        if (MoCreatures.isServer()) {
            dropMyStuff();
        }

        super.onDeath(damagesource);
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        Entity entity = damagesource.getEntity();
        if ((this.riddenByEntity != null && entity == this.riddenByEntity) || (this.ridingEntity != null && entity == this.ridingEntity)) {
            return false;
        }

        //this avoids damage done by Players to a tamed creature that is not theirs
        if (MoCreatures.proxy.enableOwnership && getOwnerName() != null && !getOwnerName().equals("") && entity != null
                && entity instanceof EntityPlayer && !((EntityPlayer) entity).getCommandSenderName().equals(getOwnerName())
                && !MoCTools.isThisPlayerAnOP((EntityPlayer) entity)) {
            return false;
        }

        /*
         if (MoCreatures.isServer() && getIsTamed()) {
             MoCServerPacketHandler.sendHealth(this.getEntityId(),
             this.worldObj.provider.getDimensionId(), this.getHealth());
         }
         */
        if (isNotScared()) {
            Entity tempEntity = this.getAttackTarget();
            boolean flag = super.attackEntityFrom(damagesource, i);
            setAttackTarget((EntityLivingBase) tempEntity);
            return flag;
        } else {
            return super.attackEntityFrom(damagesource, i);
        }
    }

    public boolean getIsRideable() {
        return false;
    }

    public void setRideable(boolean b) {
    }

    @Override
    public void setArmorType(byte i) {
    }

    public byte getArmorType() {
        return 0;
    }

    @Override
    public void dismountEntity() {
        if (MoCreatures.isServer() && this.riddenByEntity != null) {
            this.riddenByEntity.mountEntity(null);
            this.riddenByEntity = null;
        }
    }

    /**
     * Drops armor if the animal has one
     */
    public void dropArmor() {
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

    @Override
    public void riderIsDisconnecting(boolean flag) {
        this.riderIsDisconnecting = true;
    }

    /**
     * used to select which entities run the new AI
     * @return
     */
    /*protected boolean usesNewAI() {
        return false;
    }*/

    @Override
    public boolean canAttackTarget(EntityLivingBase entity) {
        return this.height >= entity.height && this.width >= entity.width;
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag =
                entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), ((int) this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
                        .getAttributeValue()));
        if (flag) {
            this.func_174815_a(this, entityIn);
        }

        return flag;
    }

    public boolean isReadyToHunt() {
        return false;
    }

    @Override
    public boolean allowLeashing() {
        return false;
    }

    @Override
    public boolean getIsSitting() {
        return false;
    }

    @Override
    public boolean isMovementCeased() {
        return getIsSitting() || this.riddenByEntity != null;
    }

    public boolean getIsHunting() {
        return this.huntingCounter != 0;
    }

    public void setIsHunting(boolean flag) {
        if (flag) {
            this.huntingCounter = this.rand.nextInt(30) + 1;
        } else {
            this.huntingCounter = 0;
        }
    }

    @Override
    public boolean shouldAttackPlayers() {
        return !getIsTamed() && this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL;// && this.worldObj.getWorldInfo().isCreative(); //TODO also creative
    }

    @Override
    public void onKillEntity(EntityLivingBase entityLivingIn) {
        if (!(entityLivingIn instanceof EntityPlayer)) {
            MoCTools.destroyDrops(this, 3D);
        }
    }

    @Override
    public PathNavigate getNavigator() {
        if (this.isInWater() && this.isAmphibian()) {
            return this.navigatorWater;
        }
        if (this.isFlyer() && getIsFlying()) {
            return this.navigatorFlyer;
        }
        return this.navigator;
    }

    public boolean isAmphibian() {
        return false;
    }

    @Override
    public boolean isDiving() {
        return false;
    }

    /**
     * The distance the entity will float under the surface. 0F = surface 1.0F = 1 block under
     * @return
     */
    @Override
    public double getDivingDepth() {
        return (float) this.divingDepth;
    }

    /**
     * Sets diving depth. if setDepth given = 0.0D, will then choose a random value within proper range
     * @param setDepth
     */
    protected void setNewDivingDepth(double setDepth) {
        if (setDepth != 0.0D) {
            if (setDepth > maxDivingDepth()) {
                setDepth = maxDivingDepth();
            }
            if (setDepth < minDivingDepth()) {
                setDepth = minDivingDepth();
            }
            this.divingDepth = setDepth;
        } else {

            this.divingDepth = (float) ((this.rand.nextDouble() * (maxDivingDepth() - minDivingDepth())) + minDivingDepth());
        }

    }

    protected void setNewDivingDepth() {
        setNewDivingDepth(0.0D);
    }

    protected double minDivingDepth() {
        return 0.2D;
    }

    protected double maxDivingDepth() {
        return 1.0D;
    }

    @Override
    public void forceEntityJump() {
        this.jump();
    }

    @Override
    public int minFlyingHeight() {
        return 1;
    }

    @Override
    public boolean getIsFlying() {
        return false;
    }
}
