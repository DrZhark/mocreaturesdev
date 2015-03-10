// todo freeze for some time if close to flower
// attack player if player attacks hive?
// hive block (honey, bee spawner)

package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityInsect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class MoCEntityBee extends MoCEntityInsect

{

    private int soundCount;
    private boolean upset;

    public MoCEntityBee(World world) {
        super(world);
        //health = 4;
        this.texture = "bee.png";
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0D);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (MoCreatures.isServer()) {
            EntityPlayer ep = this.worldObj.getClosestPlayerToEntity(this, 5D);
            if (ep != null && getIsFlying() && --this.soundCount == -1) {
                MoCTools.playCustomSound(this, getMySound(), this.worldObj);
                this.soundCount = 20;
            }

            if (getIsFlying() && this.rand.nextInt(500) == 0) {
                setIsFlying(false);
            }
        }
    }

    private String getMySound() {
        if (getAttackTarget() != null) {
            return "beeupset";
        }
        return "bee";
    }

    @Override
    protected float getFlyingSpeed() {
        return 0.5F;
    }

    @Override
    protected float getWalkingSpeed() {
        return 0.2F;
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
    protected String getDeathSound() {
        return null;
    }

    @Override
    protected String getHurtSound() {
        return null;
    }

    @Override
    protected String getLivingSound() {
        return null;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (super.attackEntityFrom(damagesource, i)) {
            Entity entity = damagesource.getEntity();
            if (entity instanceof EntityLivingBase) {
                EntityLivingBase entityliving = (EntityLivingBase) entity;
                if ((entity != this) && (this.worldObj.getDifficulty().getDifficultyId() > 0)) {
                    setAttackTarget(entityliving);
                }
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    /*@Override
    protected void attackEntity(Entity entity, float f) {

        if (this.attackTime <= 0 && (f < 2.0D) && (entity.getEntityBoundingBox().maxY > getEntityBoundingBox().minY)
                && (entity.getEntityBoundingBox().minY < getEntityBoundingBox().maxY)) {
            attackTime = 20;
            entity.attackEntityFrom(DamageSource.causeMobDamage(this), 2);
        }
    }*/

    @Override
    public boolean isMyFavoriteFood(ItemStack par1ItemStack) {
        return par1ItemStack != null
                && (par1ItemStack.getItem() == Item.getItemFromBlock(Blocks.red_flower) || par1ItemStack.getItem() == Item
                        .getItemFromBlock(Blocks.yellow_flower));
    }
}
