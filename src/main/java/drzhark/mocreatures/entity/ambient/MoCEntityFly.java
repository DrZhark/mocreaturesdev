package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.MoCEntityInsect;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MoCEntityFly extends MoCEntityInsect {

    public MoCEntityFly(World world) {
        super(world);
        this.texture = "fly.png";
    }

    private int soundCount;// = 50;

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (!this.world.isRemote) {

            if (getIsFlying() && this.rand.nextInt(200) == 0) {
                setIsFlying(false);
            }
            if (getIsFlying() && --this.soundCount == -1) {
                EntityPlayer ep = this.world.getClosestPlayerToEntity(this, 5D);
                if (ep != null) {
                    MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_FLY_AMBIENT);
                    this.soundCount = 55;
                }
            }
        }
    }

    @Override
    public boolean isMyFavoriteFood(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() == Items.ROTTEN_FLESH;
    }

    @Override
    public boolean isFlyer() {
        return true;
    }

    @Override
    public float getAIMoveSpeed() {
        if (getIsFlying()) {
            return 0.2F;
        }
        return 0.12F;
    }
}
