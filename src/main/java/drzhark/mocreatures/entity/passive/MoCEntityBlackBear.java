package drzhark.mocreatures.entity.passive;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.IMoCTameable;

public class MoCEntityBlackBear extends MoCEntityBear {

	public MoCEntityBlackBear(World world) {
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
		return MoCreatures.proxy.getTexture("bearblack.png");
    }
	
	@Override
	public float getBearSize() {
		return 0.9F;
    }
	
	@Override
    public int getMaxEdad() {
		return 90;
	}
	
	@Override
	public float calculateMaxHealth() {
		return 30;
    }
	
	public double getAttackRange() {
        int factor = 1;
        if (this.worldObj.getDifficulty().getDifficultyId() > 1) {
            factor = 2;
        }
        return 6D * factor;
    }

	@Override
	public int getAttackStrength() {
        int factor = (this.worldObj.getDifficulty().getDifficultyId());
        return 2 * factor;
    }
	
	@Override
    public boolean shouldAttackPlayers() {
        return false;
    }
	
	@Override
    public boolean processInteract(EntityPlayer player, EnumHand hand, @Nullable ItemStack stack) {
        if (super.processInteract(player, hand, stack)) {
            return true;
        }
        boolean onMainHand = (hand == EnumHand.MAIN_HAND);
        ItemStack itemstack = player.inventory.getCurrentItem();
        if ((itemstack != null) && onMainHand && this.getEdad() < 80 && MoCTools.isItemEdibleforCarnivores(itemstack.getItem())) {
            if (--itemstack.stackSize == 0) {
                player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
            }

            if (!getIsTamed() && MoCreatures.isServer()) {
                MoCTools.tameWithName(player, this);
            }

            this.setHealth(getMaxHealth());
            eatingAnimal();
            if (MoCreatures.isServer() && !getIsAdult() && (getEdad() < 100)) {
                setEdad(getEdad() + 1);
            }

            return true;
        }
        if ((itemstack != null) && getIsTamed() && (itemstack.getItem() == MoCreatures.whip)) {
            if (getBearState() == 0) {
                setBearState(2);
            }else {
                setBearState(0);
            }
            return true;
        }
        if (getIsRideable() && getIsAdult() && (!this.isBeingRidden())) {
            player.rotationYaw = this.rotationYaw;
            player.rotationPitch = this.rotationPitch;
            setBearState(0);
            if (MoCreatures.isServer()) {
                player.startRiding(this);
            }
            return true;
        }
        return false;
    }
	
	@Override
	public String getOffspringClazz(IMoCTameable mate) {
        return "BlackBear";
    }

	@Override
    public int getOffspringTypeInt(IMoCTameable mate) {
        return 1;
    }

	@Override
    public boolean compatibleMate(Entity mate) {
        return mate instanceof MoCEntityPandaBear;
    }
}
