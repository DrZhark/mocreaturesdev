package drzhark.mocreatures.entity.passive;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.IMoCTameable;

public class MoCEntityLiard extends MoCEntityBigCat {

    public MoCEntityLiard(World world) {
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
        return MoCreatures.proxy.getTexture("BCliard.png");
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
        return "Liard";
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
        return 30F;
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
        return 100;
    }

    @Override
    public String getClazzString() {
        return "Liard";
    }

    @Override
    public boolean canAttackTarget(EntityLivingBase entity) {
        if (!this.getIsAdult() && (this.getEdad() < this.getMaxEdad() * 0.8)) {
            return false;
        }
        if (entity instanceof MoCEntityLiard) {
            return false;
        }
        return entity.height < 2F && entity.width < 2F;
    }
}
