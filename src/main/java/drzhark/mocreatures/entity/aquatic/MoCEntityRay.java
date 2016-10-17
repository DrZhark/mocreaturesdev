package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAquatic;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityRay extends MoCEntityTameableAquatic {

    public MoCEntityRay(World world) {
        super(world);
    }

    @Override
    protected void initEntityAI() {
    	this.tasks.addTask(2, new EntityAIWanderMoC2(this, 1.0D, 80));
    }
    
    public boolean isPoisoning() {
        return false;
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand, @Nullable ItemStack stack) {
        if (super.processInteract(player, hand, stack)) {
            return true;
        }

        if (!this.isBeingRidden() && getType() == 1) {
            player.rotationYaw = this.rotationYaw;
            player.rotationPitch = this.rotationPitch;
            player.posY = this.posY;
            if (MoCreatures.isServer()) {
                player.startRiding(this);
            }
            return true;
        }
        return false;
    }

    @Override
    public float getAdjustedYOffset() {
        if (!this.isInWater()) {
            return 0.09F;
        }
        return 0.15F;
    }

    @Override
    public int nameYOffset() {
        return -25;
    }

    @Override
    public boolean canBeTrappedInNet() {
        return true;
    }

    @Override
    public double getMountedYOffset() {
        return this.height * 0.15D * getSizeFactor();
    }

    @Override
    public float getSizeFactor() {
        float f = getEdad() * 0.01F;
        if (f > 1.5F) {
            f = 1.5F;
        }
        return f;
    }

    @Override
    protected boolean usesNewAI() {
        return true;
    }

    @Override
    public float getAIMoveSpeed() {
        return 0.06F;
    }

    @Override
    public boolean isMovementCeased() {
        return !isInWater();
    }

    @Override
    protected double minDivingDepth() {
        return 3D;
    }

    @Override
    protected double maxDivingDepth() {
        return 6.0D;
    }

    @Override
    public int getMaxEdad() {
        return 90;
    }

    public boolean isMantaRay() {
        return false;
    }
}
