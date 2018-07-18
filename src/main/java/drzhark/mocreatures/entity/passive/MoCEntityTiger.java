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

public class MoCEntityTiger extends MoCEntityBigCat {

    public MoCEntityTiger(World world) {
        super(world);
    }

    @Override
    public void selectType() {

        if (getType() == 0) {
            if (this.rand.nextInt(20) == 0) {
                setType(2);
            } else {
                setType(1);
            }
        }
        super.selectType();
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getType()) {
            case 1:
                return MoCreatures.proxy.getTexture("bctiger.png");
            case 2:
                return MoCreatures.proxy.getTexture("bcwhitetiger.png");
            case 3:
                return MoCreatures.proxy.getTexture("bcwhitetiger.png"); //winged tiger
            /*case 4:
                return MoCreatures.proxy.getTexture("bcleoger.png"); // Tiger x Leopard
            */default:
                return MoCreatures.proxy.getTexture("bctiger.png");
        }
    }

    @Override
    public boolean isFlyer() {
        return this.getType() == 3;
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        final Boolean tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        final ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && getIsTamed() && getType() == 2 && (stack.getItem() == MoCItems.essencelight)) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
            } else {
                player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }
            setType(3);
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
        if (mate instanceof MoCEntityLion && ((MoCEntityLion) mate).getType() == 2) {
            return "Liger";
        }
        if (mate instanceof MoCEntityPanther && ((MoCEntityPanther) mate).getType() == 1) {
            return "Panthger";
        }
        if (mate instanceof MoCEntityLeopard && ((MoCEntityPanther) mate).getType() == 1) {
            return "Leoger";
        }
        return "Tiger";
    }

    @Override
    public int getOffspringTypeInt(IMoCTameable mate) {
        if (mate instanceof MoCEntityLion && ((MoCEntityLion) mate).getType() == 2) {
            return 1;//4; //liger
        }
        if (mate instanceof MoCEntityLeopard && ((MoCEntityLeopard) mate).getType() == 1) {
            return 1;//4; //leoger
        }
        if (mate instanceof MoCEntityPanther && ((MoCEntityPanther) mate).getType() == 1) {
            return 1;//4; //panthger
        }
        return this.getType();
    }

    @Override
    public boolean compatibleMate(Entity mate) {
        return (mate instanceof MoCEntityTiger && ((MoCEntityTiger) mate).getType() < 3)
                || (mate instanceof MoCEntityLion && ((MoCEntityLion) mate).getType() == 2)
                || (mate instanceof MoCEntityLeopard && ((MoCEntityLeopard) mate).getType() == 1)
                || (mate instanceof MoCEntityPanther && ((MoCEntityPanther) mate).getType() == 1);
    }

    @Override
    public boolean readytoBreed() {
        return this.getType() < 3 && super.readytoBreed();
    }

    @Override
    public float calculateMaxHealth() {
        if (this.getType() == 2) {
            return 40F;
        }
        return 35F;
    }

    @Override
    public double calculateAttackDmg() {
        if (this.getType() == 2) {
            return 8D;
        }
        return 7D;
    }

    @Override
    public double getAttackRange() {
        return 8D;
    }

    @Override
    public int getMaxEdad() {
        if (getType() > 2) {
            return 130;
        }
        return 120;
    }

    @Override
    public boolean canAttackTarget(EntityLivingBase entity) {
        if (!this.getIsAdult() && (this.getEdad() < this.getMaxEdad() * 0.8)) {
            return false;
        }
        if (entity instanceof MoCEntityTiger) {
            return false;
        }
        return entity.height < 2F && entity.width < 2F;
    }
    
    @Override
    public float getMoveSpeed() {
            return 1.5F;
    }
}
