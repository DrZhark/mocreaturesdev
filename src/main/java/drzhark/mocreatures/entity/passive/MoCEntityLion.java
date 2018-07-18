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

public class MoCEntityLion extends MoCEntityBigCat {

    public MoCEntityLion(World world) {
        super(world);
    }

    @Override
    public void selectType() {

        if (getType() == 0) {
            if (rand.nextInt(20) == 0)
            {
                setType(rand.nextInt(2)+6);//white lions
            }else
            {
            setType(this.rand.nextInt(2) + 1);
        }
        }
        super.selectType();
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getType()) {
            case 1:
                return MoCreatures.proxy.getTexture("bcfemalelion.png");//lioness
            case 2:
                return MoCreatures.proxy.getTexture("bcmalelion.png");//lion
            case 3:
                return MoCreatures.proxy.getTexture("bcmalelion.png");//winged lion
            /*case 4:
                return MoCreatures.proxy.getTexture("bcliger.png");//liger
            case 5:
                return MoCreatures.proxy.getTexture("bcliger.png");//winged liger
            */case 6:
                return MoCreatures.proxy.getTexture("bcwhitelion.png");//female white
            case 7:
                return MoCreatures.proxy.getTexture("bcwhitelion.png");//male white
            case 8:
                return MoCreatures.proxy.getTexture("bcwhitelion.png");//winged male white
            /*case 9:
                return MoCreatures.proxy.getTexture("bcliard.png");// Male Lion X leopard
            */default:
                return MoCreatures.proxy.getTexture("bcfemalelion.png");
        }
    }

    @Override
    public boolean hasMane() {
        return getIsAdult() && ((this.getType() >= 2 && getType() < 4) || this.getType() == 7);
    }

    @Override
    public boolean isFlyer() {
        return this.getType() == 3 || this.getType() == 5 || this.getType() == 8;
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        final Boolean tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        final ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && getIsTamed() && (getType() == 2 || getType() == 7)
                && (stack.getItem() == MoCItems.essencelight)) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
            } else {
                player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }
            setType(getType() + 1);
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
        if (mate instanceof MoCEntityTiger && ((MoCEntityTiger) mate).getType() < 3) {
            return "Liger";//return 4; //liger"
        }
        if (getType() == 2 && mate instanceof MoCEntityLeopard && ((MoCEntityLeopard) mate).getType() == 1) {
            return "Liard";//return 9; //liard
        }
        if (getType() == 2 && mate instanceof MoCEntityPanther && ((MoCEntityPanther) mate).getType() == 1) {
            return "Lither";//return 5; //lither
        }
        return "Lion";
    }

    @Override
    public int getOffspringTypeInt(IMoCTameable mate) {
        int x = 0;
        if (mate instanceof MoCEntityTiger && ((MoCEntityTiger) mate).getType() < 3) {
            return 1;//4; //liger
        }
        if (getType() == 2 && mate instanceof MoCEntityLeopard && ((MoCEntityLeopard) mate).getType() == 1) {
            return 1;//9; //liard
        }
        if (getType() == 2 && mate instanceof MoCEntityPanther && ((MoCEntityPanther) mate).getType() == 1) {
            return 1;//5; //lither
        }
        if (mate instanceof MoCEntityLion) {
            int lionMateType = ((MoCEntityLion) mate).getType();
            if (this.getType() == 1 && lionMateType == 2) {
                x = this.rand.nextInt(2) + 1;
            }
            if (this.getType() == 2 && lionMateType == 1) {
                x = this.rand.nextInt(2) + 1;
            }
            if (this.getType() == 6 && lionMateType == 7) {
                x = this.rand.nextInt(2) + 6;
            }
            if (this.getType() == 7 && lionMateType == 6) {
                x = this.rand.nextInt(2) + 6;
            }
            if (this.getType() == 7 && lionMateType == 1) {
                x = this.rand.nextInt(2) + 1;
            }
            if (this.getType() == 6 && lionMateType == 2) {
                x = this.rand.nextInt(2) + 1;
            }
            if (this.getType() == 1 && lionMateType == 7) {
                x = this.rand.nextInt(2) + 1;
            }
            if (this.getType() == 2 && lionMateType == 6) {
                x = this.rand.nextInt(2) + 1;
            }
        }
        return x;
    }

    @Override
    public boolean compatibleMate(Entity mate) {
        if (this.getType() == 2 && mate instanceof MoCEntityTiger && ((MoCEntityTiger) mate).getType() < 3) {
            return true;
        }
        if (this.getType() == 2 && mate instanceof MoCEntityLeopard && ((MoCEntityLeopard) mate).getType() == 1) {
            return true;
        }
        if (this.getType() == 2 && mate instanceof MoCEntityPanther && ((MoCEntityPanther) mate).getType() == 1) {
            return true;
        }
        if (mate instanceof MoCEntityLion) {
            return (getOffspringTypeInt((MoCEntityLion) mate) != 0);
        }
        return false;
    }

    @Override
    public boolean readytoBreed() {
        return (this.getType() < 3 || this.getType() == 6 || this.getType() == 7) && super.readytoBreed();
    }

    @Override
    public float calculateMaxHealth() {
        if (this.getType() == 2 || this.getType() == 7) {
            return 35F;
        }
        if (this.getType() == 4) {
            return 40F;
        }
        return 30F;
    }

    @Override
    public double calculateAttackDmg() {
        return 7D;
    }

    @Override
    public double getAttackRange() {
        if (this.getType() == 1 || this.getType() == 6) {
            return 12D;
        }
        return 8D;
    }

    @Override
    public int getMaxEdad() {
        if (getType() == 1 || getType() == 6) {
            return 110;
        }
        if (getType() == 9) {
            return 100;
        }
        return 120;
    }

    @Override
    public boolean canAttackTarget(EntityLivingBase entity) {
        if (!this.getIsAdult() && (this.getEdad() < this.getMaxEdad() * 0.8)) {
            return false;
        }
        if (entity instanceof MoCEntityLion) {
            return false;
        }
        return entity.height < 2F && entity.width < 2F;
    }
}
