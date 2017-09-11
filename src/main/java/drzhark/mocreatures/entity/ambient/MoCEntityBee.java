// todo freeze for some time if close to flower
// attack player if player attacks hive?
// hive block (honey, bee spawner)

package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.MoCEntityInsect;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class MoCEntityBee extends MoCEntityInsect
{

    private int soundCount;

    public MoCEntityBee(World world) {
        super(world);
        this.texture = "bee.png";
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (!this.world.isRemote) {
            if (getIsFlying() && --this.soundCount == -1) {
                EntityPlayer ep = this.world.getClosestPlayerToEntity(this, 5D);
                if (ep != null) {
                    MoCTools.playCustomSound(this, getMySound());
                    this.soundCount = 20;
                }
            }

            if (getIsFlying() && this.rand.nextInt(500) == 0) {
                setIsFlying(false);
            }
        }
    }

    private SoundEvent getMySound() {
        if (getAttackTarget() != null) {
            return MoCSoundEvents.ENTITY_BEE_UPSET;
        }
        return MoCSoundEvents.ENTITY_BEE_AMBIENT;
    }

    @Override
    public int getTalkInterval() {
        return 2000;
    }

    @Override
    protected float getSoundVolume() {
        return 0.1F;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (super.attackEntityFrom(damagesource, i)) {
            Entity entity = damagesource.getTrueSource();
            if (entity instanceof EntityLivingBase) {
                EntityLivingBase entityliving = (EntityLivingBase) entity;
                if ((entity != this) && (this.world.getDifficulty().getDifficultyId() > 0)) {
                    setAttackTarget(entityliving);
                }
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    @Override
    public boolean isMyFavoriteFood(ItemStack stack) {
        return !stack.isEmpty()
                && (stack.getItem() == Item.getItemFromBlock(Blocks.RED_FLOWER) || stack.getItem() == Item
                        .getItemFromBlock(Blocks.YELLOW_FLOWER));
    }

    @Override
    public float getAIMoveSpeed() {
        if (getIsFlying()) {
            return 0.15F;
        }
        return 0.12F;
    }

    @Override
    public boolean isFlyer() {
        return true;
    }
}
