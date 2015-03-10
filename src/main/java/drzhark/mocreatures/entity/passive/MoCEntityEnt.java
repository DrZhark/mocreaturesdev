package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.world.BlockEvent;

public class MoCEntityEnt extends MoCEntityAnimal {

    public MoCEntityEnt(World world) {
        super(world);
        setSize(1.4F, 7F);
        this.stepHeight = 2F;
    }

    @Override
    public void selectType() {
        if (getType() == 0) {
            setType(this.rand.nextInt(2) + 1);
        }
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getType()) {
            case 1:
                return MoCreatures.proxy.getTexture("ent_oak.png");
            case 2:
                return MoCreatures.proxy.getTexture("ent_birch.png");
            default:
                return MoCreatures.proxy.getTexture("ent_oak.png");
        }
    }

    @Override
    public float getMoveSpeed() {
        return 0.5F;
    }

    public float calculateMaxHealth() {
        return 40F;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (damagesource.getEntity() != null && damagesource.getEntity() instanceof EntityPlayer) {
            EntityPlayer ep = (EntityPlayer) damagesource.getEntity();
            ItemStack currentItem = ep.inventory.getCurrentItem();
            if (currentItem != null) {
                Item itemheld = currentItem.getItem();
                if (itemheld != null && itemheld instanceof ItemAxe) {
                    this.worldObj.getDifficulty();
                    if ((this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL)) {
                        setAttackTarget(ep);

                    }
                    return super.attackEntityFrom(damagesource, i);
                }
            }
        }
        if (damagesource.isFireDamage()) {
            return super.attackEntityFrom(damagesource, i);
        }
        return false;
    }

    @Override
    protected void dropFewItems(boolean flag, int x) {
        int i = this.rand.nextInt(3);
        int qty = this.rand.nextInt(12) + 4;
        int typ = 0;
        if (getType() == 2) {
            typ = 2;
        }
        if (i == 0) {
            entityDropItem(new ItemStack(Blocks.log, qty, typ), 0.0F);
            return;
        }
        if (i == 1) {
            entityDropItem(new ItemStack(Items.stick, qty, 0), 0.0F);
            return;

        }
        entityDropItem(new ItemStack(Blocks.sapling, qty, typ), 0.0F);
    }

    @Override
    protected String getDeathSound() {
        return "mocreatures:entdeath";
    }

    @Override
    protected String getHurtSound() {
        return "mocreatures:enthurt";
    }

    @Override
    protected String getLivingSound() {
        return "mocreatures:entgrunt";
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (this.getAttackTarget() == null && this.rand.nextInt(300) == 0) {
            plantOnFertileGround();
        }

        if (this.rand.nextInt(100) == 0 && MoCreatures.proxy.enableHunters) {
            //attackCritter();
        }
    }

    /*private void attackCritter() {
        List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(8D, 3D, 8D));
        int n = this.rand.nextInt(5) + 1;
        int j = 0;
        for (int k = 0; k < list.size(); k++) {
            Entity entity = (Entity) list.get(k);
            if (entity instanceof EntityAnimal && entity.width < 0.6F && entity.height < 0.6F) {
                EntityAnimal entityanimal = (EntityAnimal) entity;
                if (entityanimal.getEntityToAttack() == null && !MoCTools.isTamed(entityanimal)) {
                    PathEntity pathentity = this.worldObj.getPathEntityToEntity(this, entityanimal, 16.0F, true, false, false, true);
                    //entityanimal.setPathToEntity(pathentity);
                    entityanimal.setAttackTarget(this);
                    entityanimal.setPathToEntity(pathentity);
                    j++;
                    //System.out.println("attracting " + entityanimal);
                    if (j > n) {
                        return;
                    }
                }

            }
        }
    }*/

    private boolean plantOnFertileGround() {
        BlockPos pos = new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ));
        Block blockUnderFeet = this.worldObj.getBlockState(pos.down()).getBlock();
        Block blockOnFeet = this.worldObj.getBlockState(pos).getBlock();

        if (blockUnderFeet == Blocks.dirt) {
            int xCoord = MathHelper.floor_double(this.posX);
            int yCoord = MathHelper.floor_double(this.posY - 1);
            int zCoord = MathHelper.floor_double(this.posZ);
            Block block = Blocks.grass;
            BlockEvent.BreakEvent event = null;
            if (!this.worldObj.isRemote) {
                event =
                        new BlockEvent.BreakEvent(this.worldObj, pos, block.getDefaultState(), FakePlayerFactory.get((WorldServer) this.worldObj,
                                MoCreatures.MOCFAKEPLAYER));
            }
            if (event != null && !event.isCanceled()) {
                this.worldObj.setBlockState(pos.down(), block.getDefaultState(), 3);
            }
            return false;
        }

        if (blockUnderFeet == Blocks.grass && blockOnFeet == Blocks.air) {
            int metaD = 0;
            Block fertileB = Block.getBlockById(getBlockToPlant());

            if (fertileB == Blocks.sapling) {
                if (getType() == 2) {
                    metaD = 2; //to place the right sapling
                }
            }
            if (fertileB == Blocks.tallgrass) {
                metaD = this.rand.nextInt(2) + 1; //to place grass or fern
            }

            boolean canPlant = true;
            // check perms first
            for (int x = -1; x < 2; x++) {
                for (int z = -1; z < 2; z++) {
                    int xCoord = MathHelper.floor_double(this.posX);
                    int yCoord = MathHelper.floor_double(this.posY);
                    int zCoord = MathHelper.floor_double(this.posZ);
                    BlockPos pos1 = new BlockPos(xCoord, yCoord, zCoord);
                    BlockEvent.BreakEvent event = null;
                    if (!this.worldObj.isRemote) {
                        event =
                                new BlockEvent.BreakEvent(this.worldObj, pos1, fertileB.getDefaultState(), FakePlayerFactory.get(
                                        (WorldServer) this.worldObj, MoCreatures.MOCFAKEPLAYER));
                    }
                    if (event != null && event.isCanceled()) {
                        canPlant = false;
                        break;
                    }
                }
            }
            // plant if perm check passed
            if (canPlant) {
                for (int x = -1; x < 2; x++) {
                    for (int z = -1; z < 2; z++) {
                        this.worldObj.setBlockState(new BlockPos(MathHelper.floor_double(this.posX) + x, MathHelper.floor_double(this.posY),
                                MathHelper.floor_double(this.posZ) + z), fertileB.getDefaultState(), 3);
                    }
                }
                return true;
            }
            return false;
        }

        return false;
    }

    /**
     * Returns a random BlockID to plant on fertile ground
     *
     * @return
     */
    private int getBlockToPlant() {
        switch (this.rand.nextInt(15)) {
            case 0:
                return 31; //shrub
            case 1:
                return 37; //dandelion
            case 2:
                return 38; //rose
            case 3:
                return 39; //brown mushroom
            case 4:
                return 40; //red mushroom
            case 5:
                return 6; //sapling
            default:
                return 31;
        }
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    /*@Override
    protected void attackEntity(Entity entity, float f) {
        if (this.attackTime <= 0 && (f < 2.5D) && (entity.getEntityBoundingBox().maxY > getEntityBoundingBox().minY)
                && (entity.getEntityBoundingBox().minY < getEntityBoundingBox().maxY)) {
            attackTime = 200;
            this.worldObj.playSoundAtEntity(this, "mocreatures:goatsmack", 1.0F, 1.0F + ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F));
            entity.attackEntityFrom(DamageSource.causeMobDamage(this), 3);
            MoCTools.bigsmack(this, entity, 2F);
        }
    }*/

    @Override
    public boolean isNotScared() {
        return true;
    }
}
