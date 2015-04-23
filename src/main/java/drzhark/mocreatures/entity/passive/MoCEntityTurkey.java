package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoCEntityTurkey extends MoCEntityTameableAnimal {

    public MoCEntityTurkey(World world) {
        super(world);
        setSize(0.8F, 1.0F);
        this.texture = "turkey.png";
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.4D));
        this.tasks.addTask(3, new EntityAITempt(this, 1.0D, Items.melon_seeds, false));
        this.tasks.addTask(5, new EntityAIWanderMoC2(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(6.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
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
    protected String getDeathSound() {
        return "mocreatures:turkeyhurt";
    }

    @Override
    protected String getHurtSound() {
        return "mocreatures:turkeyhurt";
    }

    @Override
    protected String getLivingSound() {
        return "mocreatures:turkey";
    }

    @Override
    protected Item getDropItem() {
        boolean flag = (this.rand.nextInt(2) == 0);
        if (flag) {
            return MoCreatures.rawTurkey;
        }
        return Items.feather;
    }

    @Override
    public boolean interact(EntityPlayer entityplayer) {
        if (super.interact(entityplayer)) {
            return false;
        }

        ItemStack itemstack = entityplayer.inventory.getCurrentItem();

        if (MoCreatures.isServer() && !getIsTamed() && (itemstack != null) && (itemstack.getItem() == Items.melon_seeds)) {
            MoCTools.tameWithName(entityplayer, this);
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
        return par1ItemStack != null && par1ItemStack.getItem() == Items.pumpkin_seeds;
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
