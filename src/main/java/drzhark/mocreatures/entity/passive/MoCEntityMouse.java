package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import drzhark.mocreatures.entity.ai.EntityAIFleeFromPlayer;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class MoCEntityMouse extends MoCEntityAnimal {

    public MoCEntityMouse(World world) {
        super(world);
        setSize(0.3F, 0.3F);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIFleeFromPlayer(this, 1.2D, 4D));
        this.tasks.addTask(2, new EntityAIPanic(this, 1.4D));
        this.tasks.addTask(5, new EntityAIWanderMoC2(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.35D);
    }

    @Override
    public void selectType() {
        checkSpawningBiome();

        if (getType() == 0) {
            setType(this.rand.nextInt(3) + 1);
        }
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getType()) {
            case 1:
                return MoCreatures.proxy.getTexture("miceg.png");
            case 2:
                return MoCreatures.proxy.getTexture("miceb.png");
            case 3:
                return MoCreatures.proxy.getTexture("micew.png");

            default:
                return MoCreatures.proxy.getTexture("miceg.png");
        }
    }

    @Override
    public boolean checkSpawningBiome() {
        BlockPos pos = new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(getEntityBoundingBox().minY), this.posZ);
        BiomeGenBase currentbiome = MoCTools.Biomekind(this.worldObj, pos);

        String s = MoCTools.BiomeName(this.worldObj, pos);
        try {
            if (BiomeDictionary.isBiomeOfType(currentbiome, Type.SNOWY)) {
                setType(3); //white mice!
            }
        } catch (Exception e) {
        }
        return true;
    }

    /*@Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(23, Byte.valueOf((byte) 0)); // byte IsPicked, 0 = false 1 = true
    }*/

    public boolean getIsPicked() {
        return this.ridingEntity != null;
        //return (this.dataWatcher.getWatchableObjectByte(23) == 1);
    }

    /*public void setPicked(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(23, Byte.valueOf(input));
    }*/

    private boolean checkNearCats() {
        return true;
    }

    private boolean checkNearRock() {
        return true;
    }

    public boolean climbing() {
        return !this.onGround && isOnLadder();
    }

    @Override
    public boolean getCanSpawnHere() {
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(getEntityBoundingBox().minY);
        int k = MathHelper.floor_double(this.posZ);
        BlockPos pos = new BlockPos(i, j, k);
        Block block = this.worldObj.getBlockState(pos.down()).getBlock();
        return ((MoCreatures.entityMap.get(this.getClass()).getFrequency() > 0) && this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox())
                && (this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).size() == 0)
                && !this.worldObj.isAnyLiquid(this.getEntityBoundingBox()) && ((block == Blocks.cobblestone) || (block == Blocks.planks)
                || (block == Blocks.dirt) || (block == Blocks.stone) || (block == Blocks.grass)));
    }

    @Override
    protected String getDeathSound() {
        return "mocreatures:micedying";
    }

    @Override
    protected Item getDropItem() {
        return Items.wheat_seeds;
    }

    @Override
    protected String getHurtSound() {
        return "mocreatures:micehurt";
    }

    @Override
    protected String getLivingSound() {
        return "mocreatures:micegrunt";
    }

    @Override
    public void fall(float f, float f1) {
    }

    @Override
    public double getYOffset() {
        if (this.ridingEntity instanceof EntityPlayer && this.ridingEntity == MoCreatures.proxy.getPlayer() && !MoCreatures.isServer()) {
            return (super.getYOffset() - 0.7F);
        }

        if ((this.ridingEntity instanceof EntityPlayer) && !MoCreatures.isServer()) {
            return (super.getYOffset() - 0.1F);
        } else {
            return super.getYOffset();
        }
    }

    @Override
    public boolean interact(EntityPlayer entityplayer) {
        this.rotationYaw = entityplayer.rotationYaw;
        if (this.ridingEntity == null) {
            if (MoCreatures.isServer()) {
                mountEntity(entityplayer);
            }
            //setPicked(true);
        } else {
            this.worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F) + 1.0F);
            //setPicked(false);
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
    public boolean isOnLadder() {
        return this.isCollidedHorizontally;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!this.onGround && (this.ridingEntity != null)) {
            this.rotationYaw = this.ridingEntity.rotationYaw;
        }
    }

    private void reproduce() {
    }

    public boolean upsideDown() {
        return getIsPicked();
    }

    /*@Override
    public boolean updateMount() {
        return true;
    }*/

    /*@Override
    public boolean forceUpdates() {
        return true;
    }*/

    /*@Override
    public boolean swimmerEntity() {
        return true;
    }*/
}
