package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoCEntityPiranha extends MoCEntitySmallFish {

    public static final String fishNames[] = {"Piranha"};

    public MoCEntityPiranha(World world) {
        super(world);
        setSize(0.3F, 0.3F);
        setEdad(30 + this.rand.nextInt(70));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(6.0D);
    }

    @Override
    public void selectType() {
        setType(1);
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getTexture("smallfish_piranha.png");
    }

    protected Entity findPlayerToAttack() {
        if ((this.worldObj.getDifficulty().getDifficultyId() > 0)) {
            EntityPlayer entityplayer = this.worldObj.getClosestPlayerToEntity(this, 12D);
            if ((entityplayer != null) && entityplayer.isInWater() && !getIsTamed()) {
                return entityplayer;
            }
        }
        return null;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (super.attackEntityFrom(damagesource, i) && (this.worldObj.getDifficulty().getDifficultyId() > 0)) {
            Entity entity = damagesource.getEntity();
            if (entity instanceof EntityLivingBase) {
                if ((this.riddenByEntity == entity) || (this.ridingEntity == entity)) {
                    return true;
                }
                if (entity != this) {
                    this.setAttackTarget((EntityLivingBase) entity);
                }
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    @Override
    public boolean isNotScared() {
        return true;
    }

    /*@Override
    protected void attackEntity(Entity entity, float f) {
        if (entity.isInWater() && (f < 0.8D)) {
            if (entity instanceof EntityPlayer && ((EntityPlayer) entity).ridingEntity != null) {
                Entity playerMount = ((EntityPlayer) entity).ridingEntity;
                if (playerMount instanceof EntityBoat) {
                    return;
                }
            }
            attackTime = 20;
            entity.attackEntityFrom(DamageSource.causeMobDamage(this), 2);
            if (!(entity instanceof EntityPlayer)) {
                MoCTools.destroyDrops(this, 3D);
            }
        }
    }*/

    @Override
    protected void dropFewItems(boolean flag, int x) {
        int i = this.rand.nextInt(100);
        if (i < 70) {
            entityDropItem(new ItemStack(Items.fish, 1, 0), 0.0F);
        } else {
            int j = this.rand.nextInt(2);
            for (int k = 0; k < j; k++) {
                entityDropItem(new ItemStack(MoCreatures.mocegg, 1, 90), 0.0F);
            }
        }
    }
}
