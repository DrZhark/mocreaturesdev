package drzhark.mocreatures.entity.passive;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.IMoCTameable;
import drzhark.mocreatures.entity.ai.EntityAIFollowAdult;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;

public class MoCEntityLiger extends MoCEntityBigCat {

    public MoCEntityLiger(World world) {
        super(world);
    }

    @Override
    public void selectType() {
        if (getType() == 0) {
        	setType(1);
    }
        super.selectType();
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getTexture("BCliger.png");
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand, @Nullable ItemStack stack) {
        if (super.processInteract(player, hand, stack)) {
            return true;
        }
        boolean onMainHand = (hand == EnumHand.MAIN_HAND);
        if ((stack != null) && onMainHand && getIsTamed() && (getType() == 1) && (stack.getItem() == MoCreatures.essencelight)) {
            if (--stack.stackSize == 0) {
                player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.GLASS_BOTTLE));
            } else {
                player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }
            setType(getType() + 1);
            return true;
        }
        
        if (getIsRideable() && getIsAdult() && (!this.isBeingRidden())) {
            player.rotationYaw = this.rotationYaw;
            player.rotationPitch = this.rotationPitch;
            setSitting(false);
            if (MoCreatures.isServer()) {
                player.startRiding(this);
            }
            return true;
        }
        return false;
    }
    @Override
    public String getOffspringClazz(IMoCTameable mate) {
        return "Liger";
    }

    @Override
    public int getOffspringTypeInt(IMoCTameable mate) {
        return 1;
    }

    @Override
    public boolean compatibleMate(Entity mate) {
        return false;
    }

    @Override
    public float calculateMaxHealth() {
        return 35F;
    }

    @Override
    public double calculateAttackDmg() {
        return 8D;
    }

    @Override
    public double getAttackRange() {
        return 10D;
    }

    @Override
    public int getMaxEdad() {
        return 135;
    }

    @Override
    public String getClazzString() {
        return "Liger";
    }

    @Override
    public boolean canAttackTarget(EntityLivingBase entity) {
        if (!this.getIsAdult() && (this.getEdad() < this.getMaxEdad() * 0.8)) {
            return false;
        }
        if (entity instanceof MoCEntityLiger) {
            return false;
        }
        return entity.height < 2F && entity.width < 2F;
    }
    
    @Override
    public boolean isFlyer() {
        return this.getType() == 2;
    }
}
