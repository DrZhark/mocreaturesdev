package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.IMoCTameable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityTiger extends MoCEntityNewBigCat {

    public MoCEntityTiger(World world) {
        super(world);
        // TODO Auto-generated constructor stub
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
                return MoCreatures.proxy.getTexture("BCtiger.png");
            case 2:
                return MoCreatures.proxy.getTexture("BCwhiteTiger.png");
            case 3:
                return MoCreatures.proxy.getTexture("BCwhiteTiger.png"); //winged tiger
            default:
                return MoCreatures.proxy.getTexture("BCtiger.png");
        }
    }

    @Override
    public boolean isFlyer() {
        return this.getType() == 3;
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand, @Nullable ItemStack stack) {
        if (super.processInteract(player, hand, stack)) {
            return false;
        }

        if ((stack != null) && getIsTamed() && getType() == 2 && (stack.getItem() == MoCreatures.essencelight)) {
            if (--stack.stackSize == 0) {
                player.inventory.setInventorySlotContents(player.inventory.currentItem, new ItemStack(Items.GLASS_BOTTLE));
            } else {
                player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }
            setType(3);
            return true;
        }
        //TODO ERASE! TESTING ONLY
        /*if (itemstack != null && (itemstack.getItem() == MoCreatures.essencefire))
        {
            setType(getType()+1);
            if (getType() >9) setType(1);
            setEdad(getMaxEdad());
            return true;
        }
        if (itemstack != null && (itemstack.getItem() == MoCreatures.essenceundead))
        {
            this.setIsGhost(!getIsGhost());
            return true;
        }*/

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
        if (mate instanceof MoCEntityLion && ((MoCEntityLion) mate).getType() == 2) {
            return "Lion";
        }
        return "Tiger";
    }

    @Override
    public String getClazzString() {
        return "Tiger";
    }

    @Override
    public int getOffspringTypeInt(IMoCTameable mate) {
        if (mate instanceof MoCEntityLion && ((MoCEntityLion) mate).getType() == 2) {
            return 4; //liger
        }
        return this.getType();
    }

    @Override
    public boolean compatibleMate(Entity mate) {
        return (mate instanceof MoCEntityTiger && ((MoCEntityTiger) mate).getType() < 3)
                || (mate instanceof MoCEntityLion && ((MoCEntityLion) mate).getType() == 2);
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
            return 1.6F;
    }
}
