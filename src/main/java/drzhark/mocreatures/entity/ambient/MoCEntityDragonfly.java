package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityInsect;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoCEntityDragonfly extends MoCEntityInsect {

    private int soundCount;

    public MoCEntityDragonfly(World world) {
        super(world);
        this.texture = "dragonflya.png";
    }

    @Override
    public void selectType() {
        if (getType() == 0) {
            setType(this.rand.nextInt(4) + 1);
        }
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getType()) {
            case 1:
                return MoCreatures.proxy.getTexture("dragonflya.png");
            case 2:
                return MoCreatures.proxy.getTexture("dragonflyb.png");
            case 3:
                return MoCreatures.proxy.getTexture("dragonflyc.png");
            case 4:
                return MoCreatures.proxy.getTexture("dragonflyd.png");

            default:
                return MoCreatures.proxy.getTexture("dragonflyd.png");
        }
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (!this.world.isRemote) {
            EntityPlayer ep = this.world.getClosestPlayerToEntity(this, 5D);
            if (ep != null && getIsFlying() && --this.soundCount == -1) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_DRAGONFLY_AMBIENT);
                this.soundCount = 20;
            }

            if (getIsFlying() && this.rand.nextInt(200) == 0) {
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
            return 0.25F;
        }
        return 0.12F;
    }

    public int maxFlyingHeight() {
        return 4;
    }
}
