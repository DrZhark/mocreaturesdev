package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.IMoCTameable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoCEntityPanther extends MoCEntityNewBigCat {

    public MoCEntityPanther(World world) {
        super(world);
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getType()) {
            case 1:
                return MoCreatures.proxy.getTexture("BCpuma.png");
            case 2:
                return MoCreatures.proxy.getTexture("BCpuma.png"); //winged panther
            case 3:
                return MoCreatures.proxy.getTexture("BCpanthard.png"); //panther X leopard
            case 4:
                return MoCreatures.proxy.getTexture("BCpanthger.png"); //panther X tiger
            case 5:
                return MoCreatures.proxy.getTexture("BClither.png"); //panther X lion
            default:
                return MoCreatures.proxy.getTexture("BCpuma.png");
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
    public boolean interact(EntityPlayer entityplayer) {

        if (super.interact(entityplayer)) {
            return false;
        }
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if ((itemstack != null) && getIsTamed() && getType() == 1 && (itemstack.getItem() == MoCreatures.essencedarkness)) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Items.glass_bottle));
            } else {
                entityplayer.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
            }
            setType(2);
            return true;
        }
        if (getIsRideable() && getIsAdult() && (this.riddenByEntity == null)) {
            entityplayer.rotationYaw = this.rotationYaw;
            entityplayer.rotationPitch = this.rotationPitch;
            setSitting(false);
            if (MoCreatures.isServer()) {
                entityplayer.mountEntity(this);
            }
            return true;
        }
        return false;
    }

    @Override
    public String getOffspringClazz(IMoCTameable mate) {
        return "Panther";
    }

    @Override
    public int getOffspringTypeInt(IMoCTameable mate) {
        if (mate instanceof MoCEntityLeopard && ((MoCEntityLeopard) mate).getType() == 1) {
            return 3; //panthard
        }
        if (mate instanceof MoCEntityTiger && ((MoCEntityTiger) mate).getType() == 1) {
            return 4; //panthger
        }
        if (mate instanceof MoCEntityLion && ((MoCEntityLion) mate).getType() == 2) {
            return 5; //lither
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
        return 6D;
    }

    @Override
    public int getMaxEdad() {
        if (getType() >= 4) return 110;
        return 100;
    }

    @Override
    public String getClazzString() {
        return "Panther";
    }

    @Override
    public boolean canAttackTarget(EntityLivingBase entity) {
        if (!this.getIsAdult() && (this.getEdad() < this.getMaxEdad() * 0.8)) {
            return false;
        }
        if (entity instanceof MoCEntityPanther) {
            return false;
        }
        return entity.height < 1.3F && entity.width < 1.3F;
    }
    
    @Override
    public float getMoveSpeed() {
            return 1.6F;
    }
}
