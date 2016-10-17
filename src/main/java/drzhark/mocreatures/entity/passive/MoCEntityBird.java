package drzhark.mocreatures.entity.passive;

import com.google.common.base.Predicate;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.EntityAIFleeFromEntityMoC;
import drzhark.mocreatures.entity.ai.EntityAIFollowOwnerPlayer;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.util.MoCSoundEvents;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;

import javax.annotation.Nullable;

public class MoCEntityBird extends MoCEntityTameableAnimal {

    private boolean fleeing;
    public float wingb;
    public float wingc;
    public float wingd;
    public float winge;
    public float wingh;
    public boolean textureSet;
    private int jumpTimer;
    protected EntityAIWanderMoC2 wander;
    public static final String birdNames[] = {"Dove", "Crow", "Parrot", "Blue", "Canary", "Red"};
    private static final DataParameter<Boolean> PRE_TAMED = EntityDataManager.<Boolean>createKey(MoCEntityBird.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_FLYING = EntityDataManager.<Boolean>createKey(MoCEntityBird.class, DataSerializers.BOOLEAN);
    
    public MoCEntityBird(World world) {
        super(world);
        setSize(0.4F, 0.3F);
        this.isCollidedVertically = true;
        this.wingb = 0.0F;
        this.wingc = 0.0F;
        this.wingh = 1.0F;
        this.fleeing = false;
        this.textureSet = false;
        setTamed(false);
        this.stepHeight = 1.0F;
    }
    
    @Override
    protected void initEntityAI() {
    	this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIFleeFromEntityMoC(this, new Predicate<Entity>() {

            public boolean apply(Entity entity) {
                return !(entity instanceof MoCEntityBird) && (entity.height > 0.4F || entity.width > 0.4F);
            }
        }, 6.0F, 1.D, 1.3D));
        this.tasks.addTask(3, new EntityAIFollowOwnerPlayer(this, 0.8D, 2F, 10F));
        this.tasks.addTask(4, this.wander = new EntityAIWanderMoC2(this, 1.0D, 80));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
    }

    @Override
    public void selectType() {
        if (getType() == 0) {
            setType(this.rand.nextInt(6) + 1);
        }
    }

    @Override
    public ResourceLocation getTexture() {

        switch (getType()) {
            case 1:
                return MoCreatures.proxy.getTexture("birdwhite.png");
            case 2:
                return MoCreatures.proxy.getTexture("birdblack.png");
            case 3:
                return MoCreatures.proxy.getTexture("birdgreen.png");
            case 4:
                return MoCreatures.proxy.getTexture("birdblue.png");
            case 5:
                return MoCreatures.proxy.getTexture("birdyellow.png");
            case 6:
                return MoCreatures.proxy.getTexture("birdred.png");

            default:
                return MoCreatures.proxy.getTexture("birdblue.png");
        }
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(PRE_TAMED, Boolean.valueOf(false));
        this.dataManager.register(IS_FLYING, Boolean.valueOf(false));
    }

    public boolean getPreTamed() {
    	return ((Boolean)this.dataManager.get(PRE_TAMED)).booleanValue();
    }

    public void setPreTamed(boolean flag) {
    	this.dataManager.set(PRE_TAMED, Boolean.valueOf(flag));
    }

    public boolean getIsFlying() {
    	return ((Boolean)this.dataManager.get(IS_FLYING)).booleanValue();
    }

    public void setIsFlying(boolean flag) {
    	this.dataManager.set(IS_FLYING, Boolean.valueOf(flag));
    }

    @Override
    public void fall(float f, float f1) {
    }

    private int[] FindTreeTop(int i, int j, int k) {
        int l = i - 5;
        int i1 = k - 5;
        int j1 = i + 5;
        int k1 = j + 7;
        int l1 = k + 5;
        for (int i2 = l; i2 < j1; i2++) {
            label0: for (int j2 = i1; j2 < l1; j2++) {
                BlockPos pos = new BlockPos(i2, j, j2);
                IBlockState blockstate = this.worldObj.getBlockState(pos);
                if (blockstate.getBlock().isAir(blockstate, this.worldObj, pos) || (blockstate.getMaterial() != Material.WOOD)) {
                    continue;
                }
                int l2 = j;
                do {
                    if (l2 >= k1) {
                        continue label0;
                    }
                    BlockPos pos1 = new BlockPos(i2, l2, j2);
                    IBlockState blockstate1 = this.worldObj.getBlockState(pos1);
                    if (blockstate1.getBlock().isAir(blockstate1, this.worldObj, pos1)) {
                        return (new int[] {i2, l2 + 2, j2});
                    }
                    l2++;
                } while (true);
            }

        }

        return (new int[] {0, 0, 0});
    }

    private boolean FlyToNextEntity(Entity entity) {
        if (entity != null) {
            int i = MathHelper.floor_double(entity.posX);
            int j = MathHelper.floor_double(entity.posY);
            int k = MathHelper.floor_double(entity.posZ);
            faceLocation(i, j, k, 30F);
            if (MathHelper.floor_double(this.posY) < j) {
                this.motionY += 0.14999999999999999D;
            }
            if (this.posX < entity.posX) {
                double d = entity.posX - this.posX;
                if (d > 0.5D) {
                    this.motionX += 0.050000000000000003D;
                }
            } else {
                double d1 = this.posX - entity.posX;
                if (d1 > 0.5D) {
                    this.motionX -= 0.050000000000000003D;
                }
            }
            if (this.posZ < entity.posZ) {
                double d2 = entity.posZ - this.posZ;
                if (d2 > 0.5D) {
                    this.motionZ += 0.050000000000000003D;
                }
            } else {
                double d3 = this.posZ - entity.posZ;
                if (d3 > 0.5D) {
                    this.motionZ -= 0.050000000000000003D;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @SuppressWarnings("unused")
    private boolean FlyToNextTree() {
        int ai[] = ReturnNearestMaterialCoord(this, Material.LEAVES, Double.valueOf(20D));
        int ai1[] = FindTreeTop(ai[0], ai[1], ai[2]);
        if (ai1[1] != 0) {
            int i = ai1[0];
            int j = ai1[1];
            int k = ai1[2];
            faceLocation(i, j, k, 30F);
            if ((j - MathHelper.floor_double(this.posY)) > 2) {
                this.motionY += 0.14999999999999999D;
            }
            int l = 0;
            int i1 = 0;
            if (this.posX < i) {
                l = i - MathHelper.floor_double(this.posX);
                this.motionX += 0.050000000000000003D;
            } else {
                l = MathHelper.floor_double(this.posX) - i;
                this.motionX -= 0.050000000000000003D;
            }
            if (this.posZ < k) {
                i1 = k - MathHelper.floor_double(this.posZ);
                this.motionZ += 0.050000000000000003D;
            } else {
                i1 = MathHelper.floor_double(this.posX) - k;
                this.motionZ -= 0.050000000000000003D;
            }
            double d = l + i1;
            if (d < 3D) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected Item getDropItem() {
        return Items.FEATHER;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_BIRD_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound() {
        return MoCSoundEvents.ENTITY_BIRD_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (getType() == 1) {
            return MoCSoundEvents.ENTITY_BIRD_AMBIENT_WHITE;
        }
        if (getType() == 2) {
            return MoCSoundEvents.ENTITY_BIRD_AMBIENT_BLACK;
        }
        if (getType() == 3) {
            return MoCSoundEvents.ENTITY_BIRD_AMBIENT_GREEN;
        }
        if (getType() == 4) {
            return MoCSoundEvents.ENTITY_BIRD_AMBIENT_BLUE;
        }
        if (getType() == 5) {
            return MoCSoundEvents.ENTITY_BIRD_AMBIENT_YELLOW;
        } else {
            return MoCSoundEvents.ENTITY_BIRD_AMBIENT_RED;
        }
    }

    @Override
    public double getYOffset() {
        if (this.getRidingEntity() instanceof EntityPlayer && this.getRidingEntity() == MoCreatures.proxy.getPlayer() && !MoCreatures.isServer()) {
            return ((EntityPlayer) this.getRidingEntity()).isSneaking() ? 0.2 : 0.45F;
        }

        if ((this.getRidingEntity() instanceof EntityPlayer) && !MoCreatures.isServer()) {
            return (super.getYOffset() + 0.45F);
        } else {
            return super.getYOffset();
        }
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand, @Nullable ItemStack stack) {
        if (super.processInteract(player, hand, stack)) {
            return true;
        }
        boolean onMainHand = (hand == EnumHand.MAIN_HAND);
        if (stack != null && onMainHand && getPreTamed() && !getIsTamed() && stack.getItem() == Items.WHEAT_SEEDS) {
            if (--stack.stackSize == 0) {
                player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
            }
            if (MoCreatures.isServer()) {
                MoCTools.tameWithName(player, this);
            }
            return true;
        }

        if (!getIsTamed()) {
            return false;
        }
        if (this.getRidingEntity() == null) {
        	this.rotationYaw = player.rotationYaw;
            this.startRiding(player);
        }
        return true;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        this.winge = this.wingb;
        this.wingd = this.wingc;
        this.wingc = (float) (this.wingc + ((this.onGround ? -1 : 4) * 0.3D));
        if (this.wingc < 0.0F) {
            this.wingc = 0.0F;
        }
        if (this.wingc > 1.0F) {
            this.wingc = 1.0F;
        }
        if (!this.onGround && (this.wingh < 1.0F)) {
            this.wingh = 1.0F;
        }
        this.wingh = (float) (this.wingh * 0.9D);
        if (!this.onGround && (this.motionY < 0.0D)) {
            this.motionY *= 0.8D;
        }
        this.wingb += this.wingh * 2.0F;

        //check added to avoid duplicating behavior on client / server
        if (MoCreatures.isServer()) {

            if (isMovementCeased() && getIsFlying()) {
                setIsFlying(false);
            }

            if (getIsFlying() && this.getNavigator().noPath() && !isMovementCeased() && this.getAttackTarget() == null && rand.nextInt(30) == 0) {
                this.wander.makeUpdate();
            }

            if (!getIsFlying() && !getIsTamed() && this.rand.nextInt(10) == 0) {
                List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(4D, 4D, 4D));
                for (int i = 0; i < list.size(); i++) {
                    Entity entity1 = list.get(i);
                    if (!(entity1 instanceof EntityLivingBase) || entity1 instanceof MoCEntityBird) {
                        continue;
                    }
                    if (((EntityLivingBase) entity1).width >= 0.4F && ((EntityLivingBase) entity1).height >= 0.4F && canEntityBeSeen(entity1)) {
                        setIsFlying(true);
                        this.fleeing = true;
                        this.wander.makeUpdate();
                    }
                }
            }

            if (!isMovementCeased() && !getIsFlying() && this.rand.nextInt(getIsTamed() ? 1000 : 400) == 0) {
                setIsFlying(true);
                this.wander.makeUpdate();
            }

            if (getIsFlying() && rand.nextInt(200) == 0) {
                setIsFlying(false);
            }

            if (this.fleeing && rand.nextInt(50) == 0) {
                this.fleeing = false;
            }

            //TODO move to new AI
            if (!this.fleeing) {
                EntityItem entityitem = getClosestItem(this, 12D, Items.WHEAT_SEEDS, Items.MELON_SEEDS);
                if (entityitem != null) {
                    FlyToNextEntity(entityitem);
                    EntityItem entityitem1 = getClosestItem(this, 1.0D, Items.WHEAT_SEEDS, Items.MELON_SEEDS);
                    if ((this.rand.nextInt(50) == 0) && (entityitem1 != null)) {
                        entityitem1.setDead();
                        setPreTamed(true);
                    }
                }
            }
            if (this.rand.nextInt(10) == 0 && isInsideOfMaterial(Material.WATER)) {
                WingFlap();
            }
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (this.getRidingEntity() != null) {
            this.rotationYaw = this.getRidingEntity().rotationYaw;
        }

        if ((this.getRidingEntity() != null) && (this.getRidingEntity() instanceof EntityPlayer)) {
            EntityPlayer entityplayer = (EntityPlayer) this.getRidingEntity();
            this.rotationYaw = entityplayer.rotationYaw;
            entityplayer.fallDistance = 0.0F;
            if (entityplayer.motionY < -0.1D)
                entityplayer.motionY *= 0.60;
        }

        if (--this.jumpTimer <= 0 && this.onGround
                && ((this.motionX > 0.05D) || (this.motionZ > 0.05D) || (this.motionX < -0.05D) || (this.motionZ < -0.05D))) {
            this.motionY = 0.25D;
            float velX = MathHelper.sin(this.rotationYaw * (float) Math.PI / 180.0F);
            float velZ = MathHelper.cos(this.rotationYaw * (float) Math.PI / 180.0F);

            this.motionX += (-0.2F * velX);
            this.motionZ += (0.2F * velZ);
            this.jumpTimer = 15;
        }
    }

    public int[] ReturnNearestMaterialCoord(Entity entity, Material material, Double double1) {
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
                    BlockPos pos = new BlockPos(k1, l1, i2);
                    IBlockState blockstate = this.worldObj.getBlockState(pos);
                    if (blockstate.getBlock() != null && !blockstate.getBlock().isAir(blockstate, this.worldObj, pos)
                            && blockstate.getMaterial() == material) {
                        return (new int[] {k1, l1, i2});
                    }
                }

            }

        }

        return (new int[] {-1, 0, 0});
    }

    @Override
    public void setDead() {
        if (MoCreatures.isServer() && getIsTamed() && (this.getHealth() > 0)) {
            return;
        } else {
            super.setDead();
            return;
        }
    }

    private void WingFlap() {
        this.motionY += 0.05D;
        if (this.rand.nextInt(30) == 0) {
            this.motionX += 0.2D;
        }
        if (this.rand.nextInt(30) == 0) {
            this.motionX -= 0.2D;
        }
        if (this.rand.nextInt(30) == 0) {
            this.motionZ += 0.2D;
        }
        if (this.rand.nextInt(30) == 0) {
            this.motionZ -= 0.2D;
        }
    }

    @Override
    public int nameYOffset() {
        return -40;
    }

    @Override
    public boolean isMyHealFood(ItemStack par1ItemStack) {
        return par1ItemStack != null && (par1ItemStack.getItem() == Items.WHEAT_SEEDS || par1ItemStack.getItem() == Items.MELON_SEEDS);
    }

    @Override
    public boolean isNotScared() {
        return getIsTamed();
    }

    @Override
    public boolean isFlyer() {
        return true;
    }

    @Override
    public int maxFlyingHeight() {
        if (getIsTamed())
            return 4;
        return 6;
    }

    @Override
    public int minFlyingHeight() {
        return 2;
    }
    
    @Override
    public boolean canRidePlayer()
    {
    	return true;
    }
}
