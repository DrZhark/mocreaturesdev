package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.IMoCTameable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoCEntityPanthger extends MoCEntityBigCat {

    public MoCEntityPanthger(World world) {
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
        return MoCreatures.proxy.getTexture("bcpanthger.png");
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        final Boolean tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
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
        return "Panthgerd";
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
        return 7D;
    }

    @Override
    public double getAttackRange() {
        return 10D;
    }

    @Override
    public int getMaxEdad() {
        return 115;
    }

    @Override
    public boolean canAttackTarget(EntityLivingBase entity) {
        if (!this.getIsAdult() && (this.getEdad() < this.getMaxEdad() * 0.8)) {
            return false;
        }
        if (entity instanceof MoCEntityPanthger) {
            return false;
        }
        return entity.height < 1.5F && entity.width < 1.5F;
    }
    
    @Override
    public float getMoveSpeed() {
            return 1.6F;
    }
}
