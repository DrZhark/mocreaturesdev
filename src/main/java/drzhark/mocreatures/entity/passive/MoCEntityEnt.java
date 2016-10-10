package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.util.MoCSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.world.BlockEvent;

import java.util.List;

public class MoCEntityEnt extends MoCEntityAnimal {

    public MoCEntityEnt(World world) {
        super(world);
        setSize(1.4F, 7F);
        this.stepHeight = 2F;
    }
    
    @Override
    protected void initEntityAI() {
    	this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(5, new EntityAIAttackMelee(this, 1.0D, true));
        this.tasks.addTask(6, new EntityAIWanderMoC2(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
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
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (damagesource.getEntity() != null && damagesource.getEntity() instanceof EntityPlayer) {
            EntityPlayer ep = (EntityPlayer) damagesource.getEntity();
            ItemStack currentItem = ep.inventory.getCurrentItem();
            if (currentItem != null) {
                Item itemheld = currentItem.getItem();
                if (itemheld != null && itemheld instanceof ItemAxe) {
                    this.worldObj.getDifficulty();
                    if (super.shouldAttackPlayers()) {
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
            entityDropItem(new ItemStack(Blocks.LOG, qty, typ), 0.0F);
            return;
        }
        if (i == 1) {
            entityDropItem(new ItemStack(Items.STICK, qty, 0), 0.0F);
            return;

        }
        entityDropItem(new ItemStack(Blocks.SAPLING, qty, typ), 0.0F);
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_ENT_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound() {
        return MoCSoundEvents.ENTITY_ENT_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return MoCSoundEvents.ENTITY_ENT_AMBIENT;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (MoCreatures.isServer()) {

            if (this.getAttackTarget() == null && this.rand.nextInt(500) == 0) {
                plantOnFertileGround();
            }

            if (this.rand.nextInt(100) == 0) {
                atractCritter();
            }
        }
    }

    /**
     * Makes small creatures follow the Ent
     */
    private void atractCritter() {
        List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(8D, 3D, 8D));
        int n = this.rand.nextInt(3) + 1;
        int j = 0;
        for (int k = 0; k < list.size(); k++) {
            Entity entity = list.get(k);
            if (entity instanceof EntityAnimal && entity.width < 0.6F && entity.height < 0.6F) {
                EntityAnimal entityanimal = (EntityAnimal) entity;
                if (entityanimal.getAttackTarget() == null && !MoCTools.isTamed(entityanimal)) {
                    Path pathentity = entityanimal.getNavigator().getPathToEntityLiving(this);
                    entityanimal.setAttackTarget(this);
                    entityanimal.getNavigator().setPath(pathentity, 1D);
                    j++;
                    if (j > n) {
                        return;
                    }
                }

            }
        }
    }

    private boolean plantOnFertileGround() {
        BlockPos pos = new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ));
        Block blockUnderFeet = this.worldObj.getBlockState(pos.down()).getBlock();
        Block blockOnFeet = this.worldObj.getBlockState(pos).getBlock();

        if (blockUnderFeet == Blocks.DIRT) {
            Block block = Blocks.GRASS;
            BlockEvent.BreakEvent event = null;
            if (!this.worldObj.isRemote) {
                event =
                        new BlockEvent.BreakEvent(this.worldObj, pos, block.getDefaultState(), FakePlayerFactory.get((WorldServer) this.worldObj,
                                MoCreatures.MOCFAKEPLAYER));
            }
            if (event != null && !event.isCanceled()) {
                this.worldObj.setBlockState(pos.down(), block.getDefaultState(), 3);
                return true;
            }
            return false;
        }

        if (blockUnderFeet == Blocks.GRASS && blockOnFeet == Blocks.AIR) {
            IBlockState iblockstate = getBlockStateToBePlanted();
            int plantChance = 3;
            if (iblockstate.getBlock() == Blocks.SAPLING) {
                plantChance = 10;
            }
            //boolean cantPlant = false;
            // check perms first
            for (int x = -1; x < 2; x++) {
                for (int z = -1; z < 2; z++) {
                    int xCoord = MathHelper.floor_double(this.posX + x);
                    int yCoord = MathHelper.floor_double(this.posY);
                    int zCoord = MathHelper.floor_double(this.posZ + z);
                    BlockPos pos1 = new BlockPos(xCoord, yCoord, zCoord);
                    //BlockEvent.BreakEvent event = null;
                    //if (!this.worldObj.isRemote) {
                    //    event =
                    //            new BlockEvent.BreakEvent(this.worldObj, pos1, iblockstate, FakePlayerFactory.get((WorldServer) this.worldObj,
                    //                    MoCreatures.MOCFAKEPLAYER));
                    //}
                    //cantPlant = (event != null && event.isCanceled());
                    Block blockToPlant = this.worldObj.getBlockState(pos1).getBlock();
                    //if (!cantPlant && this.rand.nextInt(plantChance) == 0 && blockToPlant == Blocks.AIR) {
                    if (this.rand.nextInt(plantChance) == 0 && blockToPlant == Blocks.AIR) {
                        this.worldObj.setBlockState(pos1, iblockstate, 3);
                    }
                }
            }
            return true;
        }

        return false;
    }

    /**
     * Returns a random blockState
     *
     * @return Any of the flowers, mushrooms, grass and saplings
     */
    private IBlockState getBlockStateToBePlanted() {
        int blockID = 0;
        int metaData = 0;
        switch (this.rand.nextInt(20)) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                blockID = 31;
                metaData = rand.nextInt(2) + 1;
                break;
            case 8:
            case 9:
            case 10:
                blockID = 175; //other flowers
                metaData = rand.nextInt(6);
                break;
            case 11:
            case 12:
            case 13:
                blockID = 37; //dandelion
                break;
            case 14:
            case 15:
            case 16:
                blockID = 38; //flowers
                metaData = rand.nextInt(9);
                break;
            case 17:
                blockID = 39; //brown mushroom
                break;
            case 18:
                blockID = 40; //red mushroom
                break;
            case 19:
                blockID = 6; //sapling
                if (getType() == 2) {
                    metaData = 2; //to place the right sapling
                }
                break;

            default:
                blockID = 31;
        }
        IBlockState iblockstate;
        iblockstate = Block.getBlockById(blockID).getStateFromMeta(metaData);
        return iblockstate;

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
    protected void applyEnchantments(EntityLivingBase entityLivingBaseIn, Entity entityIn) {
        MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GOAT_SMACK);
        MoCTools.bigsmack(this, entityIn, 1F);
        super.applyEnchantments(entityLivingBaseIn, entityIn);
    }

    @Override
    public boolean isNotScared() {
        return true;
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }
}
