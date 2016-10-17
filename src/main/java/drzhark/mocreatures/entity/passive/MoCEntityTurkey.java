package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.util.MoCSoundEvents;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityTurkey extends MoCEntityTameableAnimal {

    public MoCEntityTurkey(World world) {
        super(world);
        setSize(0.8F, 1.0F);
        this.texture = "turkey.png";
    }

    @Override
    protected void initEntityAI() {
    	this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.4D));
        this.tasks.addTask(3, new EntityAITempt(this, 1.0D, Items.MELON_SEEDS, false));
        this.tasks.addTask(5, new EntityAIWanderMoC2(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    @Override
    public void selectType() {
        if (getType() == 0) {
            setType(this.rand.nextInt(2) + 1);
        }
    }

    @Override
    public ResourceLocation getTexture() {
        if (getType() == 1) {
            return MoCreatures.proxy.getTexture("turkey.png");
        } else {
            return MoCreatures.proxy.getTexture("turkeyfemale.png");
        }
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_TURKEY_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound() {
        return MoCSoundEvents.ENTITY_TURKEY_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return MoCSoundEvents.ENTITY_TURKEY_AMBIENT;
    }

    @Override
    protected Item getDropItem() {
        boolean flag = (this.rand.nextInt(2) == 0);
        if (flag) {
            return MoCreatures.rawTurkey;
        }
        return Items.FEATHER;
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand, @Nullable ItemStack stack) {
        if (super.processInteract(player, hand, stack)) {
            return true;
        }
        boolean onMainHand = (hand == EnumHand.MAIN_HAND);
        if (MoCreatures.isServer() && onMainHand && !getIsTamed() && (stack != null) && (stack.getItem() == Items.MELON_SEEDS)) {
            MoCTools.tameWithName(player, this);
        }

        return true;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!this.onGround && this.motionY < 0.0D) {
            this.motionY *= 0.8D;
        }
    }

    @Override
    public boolean isMyHealFood(ItemStack par1ItemStack) {
        return par1ItemStack != null && par1ItemStack.getItem() == Items.PUMPKIN_SEEDS;
    }

    @Override
    public int nameYOffset() {
        return -50;
    }

    @Override
    public int getTalkInterval() {
        return 400;
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 2;
    }
}
