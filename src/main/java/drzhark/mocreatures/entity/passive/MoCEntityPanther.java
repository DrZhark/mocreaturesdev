package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.IMoCTameable;
import drzhark.mocreatures.init.MoCItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoCEntityPanther extends MoCEntityBigCat {

    public MoCEntityPanther(World world) {
        super(world);
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getType()) {
            case 1:
                return MoCreatures.proxy.getTexture("bcpuma.png");
            case 2:
                return MoCreatures.proxy.getTexture("bcpuma.png"); //winged panther
            /*case 3:
                return MoCreatures.proxy.getTexture("bcpanthard.png"); //panther X leopard
            case 4:
                return MoCreatures.proxy.getTexture("bcpanthger.png"); //panther X tiger
            case 5:
                return MoCreatures.proxy.getTexture("bclither.png"); //panther X lion
            */default:
                return MoCreatures.proxy.getTexture("bcpuma.png");
        }
    }

    @Override
    public void selectType() {

        if (getType() == 0) {
            setType(1);
        }
        super.selectType();
    }

    @Override
    public boolean isFlyer() {
        return this.getType() == 2;
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        final Boolean tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        final ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && getIsTamed() && getType() == 1 && (stack.getItem() == MoCItems.essencedarkness)) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
            } else {
                player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }
            setType(2);
            return true;
        }
        if (this.getIsRideable() && this.getIsAdult() && (!this.getIsChested() || !player.isSneaking()) && !this.isBeingRidden()) {
            if (!this.world.isRemote && player.startRiding(this)) {
                player.rotationYaw = this.rotationYaw;
                player.rotationPitch = this.rotationPitch;
                setSitting(false);
            }

            return true;
        }

        return super.processInteract(player, hand);
    }

    @Override
    public String getOffspringClazz(IMoCTameable mate) {
        if (mate instanceof MoCEntityLeopard && ((MoCEntityLeopard) mate).getType() == 1) {
            return "Panthard";//3; //panthard
        }
        if (mate instanceof MoCEntityTiger && ((MoCEntityTiger) mate).getType() == 1) {
            return "Panthger";//4; //panthger
        }
        if (mate instanceof MoCEntityLion && ((MoCEntityLion) mate).getType() == 2) {
            return "Lither";//5; //lither
        }
        
        return "Panther";
        
    }

    @Override
    public int getOffspringTypeInt(IMoCTameable mate) {
        if (mate instanceof MoCEntityLeopard && ((MoCEntityLeopard) mate).getType() == 1) {
            return 1;//3; //panthard
        }
        if (mate instanceof MoCEntityTiger && ((MoCEntityTiger) mate).getType() == 1) {
            return 1;//4; //panthger
        }
        if (mate instanceof MoCEntityLion && ((MoCEntityLion) mate).getType() == 2) {
            return 1;//5; //lither
        }
        return 1;
    }

    @Override
    public boolean compatibleMate(Entity mate) {
        return (mate instanceof MoCEntityLeopard && ((MoCEntityLeopard) mate).getType() == 1)
                || (mate instanceof MoCEntityPanther && ((MoCEntityPanther) mate).getType() == 1)
                || (mate instanceof MoCEntityTiger && ((MoCEntityTiger) mate).getType() == 1)
                || (mate instanceof MoCEntityLion && ((MoCEntityLion) mate).getType() == 2);
    }

    @Override
    public float calculateMaxHealth() {
        return 25F;
    }

    @Override
    public double calculateAttackDmg() {
        return 6D;
    }

    @Override
    public double getAttackRange() {
        return 8D;
    }

    @Override
    public int getMaxEdad() {
        if (getType() >= 4) return 110;
        return 100;
    }

    @Override
    public boolean canAttackTarget(EntityLivingBase entity) {
        if (!this.getIsAdult() && (this.getEdad() < this.getMaxEdad() * 0.8)) {
            return false;
        }
        if (entity instanceof MoCEntityPanther) {
            return false;
        }
        return entity.height < 1.5F && entity.width < 1.5F;
    }
    
    @Override
    public float getMoveSpeed() {
            return 1.6F;
    }
}
