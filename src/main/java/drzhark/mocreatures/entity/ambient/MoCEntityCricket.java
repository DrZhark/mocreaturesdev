package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityInsect;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class MoCEntityCricket extends MoCEntityInsect

{

    private int jumpCounter;
    private int soundCounter;

    public MoCEntityCricket(World world) {
        super(world);
        this.texture = "cricketa.png";
    }

    @Override
    public void selectType() {
        if (getType() == 0) {
            int i = this.rand.nextInt(100);
            if (i <= 50) {
                setType(1);
            } else {
                setType(2);
            }
        }
    }

    @Override
    public ResourceLocation getTexture() {
        if (getType() == 1) {
            return MoCreatures.proxy.getTexture("cricketa.png");
        } else {
            return MoCreatures.proxy.getTexture("cricketb.png");
        }
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (MoCreatures.isServer()) {
            if (getIsFlying() && this.rand.nextInt(50) == 0) {
                setIsFlying(false);
            }

            if (getIsFlying() || !this.onGround) {
                EntityPlayer ep = this.worldObj.getClosestPlayerToEntity(this, 5D);
                if (ep != null && --this.soundCounter == -1) {
                    MoCTools.playCustomSound(this, "cricketfly", this.worldObj);
                    this.soundCounter = 10;
                }
            } else if (!DimensionManager.getWorld(0).isDaytime()) {
                EntityPlayer ep = this.worldObj.getClosestPlayerToEntity(this, 12D);
                if (ep != null && --this.soundCounter == -1) {
                    MoCTools.playCustomSound(this, "cricket", this.worldObj);
                    this.soundCounter = 20;
                }
            }

            if (this.jumpCounter > 0 && ++this.jumpCounter > 30) {
                this.jumpCounter = 0;
            }
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (MoCreatures.isServer()) {
            if (onGround && ((motionX > 0.05D) || (motionZ > 0.05D) || (motionX < -0.05D) || (motionZ < -0.05D)))
                if (this.jumpCounter == 0 && this.onGround
                        && ((this.motionX > 0.05D) || (this.motionZ > 0.05D) || (this.motionX < -0.05D) || (this.motionZ < -0.05D))) {
                    this.motionY = 0.45D;
                    this.motionX *= 5D;
                    this.motionZ *= 5D;
                    this.jumpCounter = 1;
                }
        }
    }

    @Override
    public float getAIMoveSpeed() {
        if (getIsFlying()) {
            return 0.12F;
        }
        return 0.15F;
    }

    @Override
    public boolean isFlyer() {
        return true;
    }
}
