package drzhark.mocreatures.entity.passive;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.IMoCTameable;

public class MoCEntityPandaBear extends MoCEntityBear{

	public MoCEntityPandaBear(World world) {
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
		return MoCreatures.proxy.getTexture("bearpanda.png");
    }
	
	@Override
	public float getBearSize() {
		return 0.8F;
    }
	
	@Override
    public int getMaxEdad() {
		return 80;
	}
		
	@Override
	public float calculateMaxHealth() {
		return 20;
    }
	
	@Override
    public boolean isReadyToHunt() {
        return false;
    }

	@Override
	public int getAttackStrength() {
		return 1;
	}
	
	@Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (super.attackEntityFrom(damagesource, i)) {
            return true;
        } else {
            return false;
        }
    }
	
	@Override
    public boolean shouldAttackPlayers() {
        return false;
    }
	
	@Override
    public boolean isMyFavoriteFood(ItemStack par1ItemStack) {
        return this.getType() == 3 && par1ItemStack != null && par1ItemStack.getItem() == Items.REEDS;
    }

    @Override
    public boolean isMyHealFood(ItemStack par1ItemStack) {
        return this.getType() == 3 && par1ItemStack != null && par1ItemStack.getItem() == Items.REEDS;
    }
    
    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand, @Nullable ItemStack stack) {
        if (super.processInteract(player, hand, stack)) {
            return true;
        }
        boolean onMainHand = (hand == EnumHand.MAIN_HAND);
        ItemStack itemstack = player.inventory.getCurrentItem();
        if ((itemstack != null) && onMainHand && ((itemstack.getItem() == MoCreatures.sugarlump) || (itemstack.getItem() == Items.REEDS))) {
            if (--itemstack.stackSize == 0) {
                player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
            }

            if (MoCreatures.isServer()) {
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
    public void onLivingUpdate() {
        super.onLivingUpdate();
        /**
         * panda bears and cubs will sit down every now and then
         */
        if ((MoCreatures.isServer()) && !getIsTamed() && (this.rand.nextInt(300) == 0)) {
            setBearState(2);
        }
    }

    @Override
	public String getOffspringClazz(IMoCTameable mate) {
        return "PandaBear";
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
