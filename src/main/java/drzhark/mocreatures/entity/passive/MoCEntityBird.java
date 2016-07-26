package drzhark.mocreatures.entity.passive;

import com.google.common.base.Predicate;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.EntityAIFleeFromEntityMoC;
import drzhark.mocreatures.entity.ai.EntityAIFollowOwnerPlayer;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.List;

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
        ((PathNavigateGround) this.getNavigator()).setAvoidsWater(true);
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
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
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
        this.dataWatcher.addObject(23, Byte.valueOf((byte) 0)); // preTamed - 0 false 1 true
        this.dataWatcher.addObject(24, Byte.valueOf((byte) 0)); // isFlying 0 false 1 true
    }

    public boolean getPreTamed() {
        return (this.dataWatcher.getWatchableObjectByte(23) == 1);
    }

    public void setPreTamed(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(23, Byte.valueOf(input));
    }

    public boolean getIsFlying() {
        return (this.dataWatcher.getWatchableObjectByte(24) == 1);
    }

    public void setIsFlying(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(24, Byte.valueOf(input));
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
                if (blockstate.getBlock().isAir(this.worldObj, pos) || (blockstate.getBlock().getMaterial() != Material.wood)) {
                    continue;
                }
                int l2 = j;
                do {
                    if (l2 >= k1) {
                        continue label0;
                    }
                    BlockPos pos1 = new BlockPos(i2, l2, j2);
                    IBlockState blockstate1 = this.worldObj.getBlockState(pos1);
                    if (blockstate1.getBlock().isAir(this.worldObj, pos1)) {
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
        int ai[] = ReturnNearestMaterialCoord(this, Material.leaves, Double.valueOf(20D));
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
    protected String getDeathSound() {
        return "mocreatures:birddying";
    }

    @Override
    protected Item getDropItem() {
        return Items.feather;
    }

    @Override
    protected String getHurtSound() {
        return "mocreatures:birdhurt";
    }

    @Override
    protected String getLivingSound() {
        if (getType() == 1) {
            return "mocreatures:birdwhite";
        }
        if (getType() == 2) {
            return "mocreatures:birdblack";
        }
        if (getType() == 3) {
            return "mocreatures:birdgreen";
        }
        if (getType() == 4) {
            return "mocreatures:birdblue";
        }
        if (getType() == 5) {
            return "mocreatures:birdyellow";
        } else {
            return "mocreatures:birdred";
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
    public boolean interact(EntityPlayer entityplayer) {

        if (super.interact(entityplayer)) {
            return false;
        }
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();

        if (itemstack != null && getPreTamed() && !getIsTamed() && itemstack.getItem() == Items.wheat_seeds) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            if (MoCreatures.isServer()) {
                MoCTools.tameWithName(entityplayer, this);
            }
            return true;
        }

        if (!getIsTamed()) {
            return false;
        }

        this.rotationYaw = entityplayer.rotationYaw;
        if (this.getRidingEntity() == null) {
            if (MoCreatures.isServer()) {
                mountEntity(entityplayer);
            }
            //setPicked(true);
        } else {
            this.worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F) + 1.0F);
            if (MoCreatures.isServer()) {
                this.mountEntity(null);
            }
        }
        this.motionX = entityplayer.motionX * 5D;
        this.motionY = (entityplayer.motionY / 2D) + 0.5D;
        this.motionZ = entityplayer.motionZ * 5D;
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
                EntityItem entityitem = getClosestItem(this, 12D, Items.wheat_seeds, Items.melon_seeds);
                if (entityitem != null) {
                    FlyToNextEntity(entityitem);
                    EntityItem entityitem1 = getClosestItem(this, 1.0D, Items.wheat_seeds, Items.melon_seeds);
                    if ((this.rand.nextInt(50) == 0) && (entityitem1 != null)) {
                        entityitem1.setDead();
                        setPreTamed(true);
                    }
                }
            }
            if (this.rand.nextInt(10) == 0 && isInsideOfMaterial(Material.water)) {
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
                    if (blockstate.getBlock() != null && !blockstate.getBlock().isAir(this.worldObj, pos)
                            && blockstate.getBlock().getMaterial() == material) {
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

    /*@Override
    public boolean updateMount() {
        return getIsTamed();
    }*/

    /*@Override
    public boolean forceUpdates() {
        return getIsTamed();
    }*/

    @Override
    public int nameYOffset() {
        return -40;

    }

    @Override
    public boolean isMyHealFood(ItemStack par1ItemStack) {
        return par1ItemStack != null && (par1ItemStack.getItem() == Items.wheat_seeds || par1ItemStack.getItem() == Items.melon_seeds);
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
}
