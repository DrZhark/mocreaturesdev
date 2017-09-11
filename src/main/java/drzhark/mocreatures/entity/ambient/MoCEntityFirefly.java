package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.MoCEntityInsect;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class MoCEntityFirefly extends MoCEntityInsect {

    private int soundCount;

    public MoCEntityFirefly(World world) {
        super(world);
        this.texture = "firefly.png";
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (!this.world.isRemote) {
            EntityPlayer ep = this.world.getClosestPlayerToEntity(this, 5D);
            if (ep != null && getIsFlying() && --this.soundCount == -1) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_CRICKET_FLY);
                this.soundCount = 20;
            }

            if (getIsFlying() && this.rand.nextInt(500) == 0) {
                setIsFlying(false);
            }
        }
    }

    @Override
    public boolean isFlyer() {
        return true;
    }

    @Override
    public float getAIMoveSpeed() {
        if (getIsFlying()) {
            return 0.12F;
        }
        return 0.10F;
    }

    /* @Override
     protected float getFlyingSpeed() {
         return 0.3F;
     }

     @Override
     protected float getWalkingSpeed() {
         return 0.2F;
     }*/
}
