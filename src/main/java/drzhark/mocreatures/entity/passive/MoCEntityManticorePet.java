package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.IMoCTameable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityManticorePet extends MoCEntityBigCat {

    public MoCEntityManticorePet(World world) {
        super(world);
        this.chestName = "ManticoreChest";
    }

    @Override
    public void selectType() {

        if (getType() == 0) {
            setType(this.rand.nextInt(4) + 1);
        }
        super.selectType();
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getType()) {
            case 1:
                return MoCreatures.proxy.getTexture("BCmanticore.png");
            case 2:
                return MoCreatures.proxy.getTexture("BCmanticoreDark.png");
            case 3:
                return MoCreatures.proxy.getTexture("BCmanticoreBlue.png");
            case 4:
                return MoCreatures.proxy.getTexture("BCmanticoreGreen.png");
            default:
                return MoCreatures.proxy.getTexture("BCmanticore.png");
        }
    }

    @Override
    public boolean hasMane() {
        return true;
    }

    @Override
    public boolean isFlyer() {
        return true;
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand, @Nullable ItemStack stack) {
        if (super.processInteract(player, hand, stack)) {
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
        return "";
    }

    @Override
    public String getClazzString() {
        return "";
    }

    @Override
    public int getOffspringTypeInt(IMoCTameable mate) {
        return 0;
    }

    @Override
    public boolean compatibleMate(Entity mate) {
        return false;
    }

    @Override
    public boolean readytoBreed() {
        return false;
    }

    @Override
    public float calculateMaxHealth() {
        return 40F;
    }

    @Override
    public double calculateAttackDmg() {
        return 7D;
    }

    @Override
    public double getAttackRange() {
        return 8D;
    }

    @Override
    public int getMaxEdad() {
        return 130;
    }

    @Override
    public boolean getHasStinger() {
        return true;
    }

    @Override
    public boolean hasSaberTeeth() {
        return true;
    }

}
