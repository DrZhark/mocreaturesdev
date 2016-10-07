package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import drzhark.mocreatures.entity.ai.EntityAIFleeFromPlayer;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.util.MoCSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import javax.annotation.Nullable;

public class MoCEntityMouse extends MoCEntityAnimal {

    public MoCEntityMouse(World world) {
        super(world);
        setSize(0.3F, 0.3F);
    }

    @Override
    protected void initEntityAI() {
    	this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIFleeFromPlayer(this, 1.2D, 4D));
        this.tasks.addTask(2, new EntityAIPanic(this, 1.4D));
        this.tasks.addTask(5, new EntityAIWanderMoC2(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
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
        Biome currentbiome = MoCTools.Biomekind(this.worldObj, pos);

        try {
            if (BiomeDictionary.isBiomeOfType(currentbiome, Type.SNOWY)) {
                setType(3); //white mice!
            }
        } catch (Exception e) {
        }
        return true;
    }

    public boolean getIsPicked() {
        return this.getRidingEntity() != null;
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
                && (this.worldObj.getCollisionBoxes(this, this.getEntityBoundingBox()).size() == 0)
                && !this.worldObj.containsAnyLiquid(this.getEntityBoundingBox()) && ((block == Blocks.COBBLESTONE) || (block == Blocks.PLANKS)
                || (block == Blocks.DIRT) || (block == Blocks.STONE) || (block == Blocks.GRASS)));
    }

    @Override
    protected Item getDropItem() {
        return Items.WHEAT_SEEDS;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_MOUSE_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound() {
        return MoCSoundEvents.ENTITY_MOUSE_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return MoCSoundEvents.ENTITY_MOUSE_AMBIENT;
    }

    @Override
    public void fall(float f, float f1) {
    }

    @Override
    public double getYOffset() {
        if (this.getRidingEntity() instanceof EntityPlayer && this.getRidingEntity() == MoCreatures.proxy.getPlayer() && !MoCreatures.isServer()) {
            return (super.getYOffset() - 0.7F);
        }

        if ((this.getRidingEntity() instanceof EntityPlayer) && !MoCreatures.isServer()) {
            return (super.getYOffset() - 0.1F);
        } else {
            return super.getYOffset();
        }
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand, @Nullable ItemStack stack) {
        if (this.getRidingEntity() == null) {
                this.startRiding(player);
            this.rotationYaw = player.rotationYaw;
            }
        return true;
    }

    @Override
    public boolean isOnLadder() {
        return this.isCollidedHorizontally;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!this.onGround && (this.getRidingEntity() != null)) {
            this.rotationYaw = this.getRidingEntity().rotationYaw;
        }
        
    }

    public boolean upsideDown() {
        return getIsPicked();
    }
    
    @Override
    public boolean canRidePlayer()
    {
    	return true;
    }
}
