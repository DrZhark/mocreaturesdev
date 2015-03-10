package drzhark.mocreatures.entity.monster;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityMob;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class MoCEntityRat extends MoCEntityMob {

    public MoCEntityRat(World world) {
        super(world);
        setSize(0.5F, 0.5F);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
    }

    @Override
    public void selectType() {
        if (getType() == 0) {
            int i = this.rand.nextInt(100);
            if (i <= 65) {
                setType(1);
            } else if (i <= 98) {
                setType(2);
            } else {
                setType(3);
            }
        }
    }

    @Override
    protected double getAttackStrenght() {
        return 1D;
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getType()) {
            case 1:
                return MoCreatures.proxy.getTexture("ratb.png");
            case 2:
                return MoCreatures.proxy.getTexture("ratbl.png");
            case 3:
                return MoCreatures.proxy.getTexture("ratw.png");

            default:
                return MoCreatures.proxy.getTexture("ratb.png");
        }
    }

    /*@Override
    protected void attackEntity(Entity entity, float f) {
        float f1 = getBrightness(1.0F);
        if ((f1 > 0.5F) && (this.rand.nextInt(100) == 0)) {
            setAttackTarget(null);
            return;
        } else {
            super.attackEntity(entity, f);
            return;
        }
    }*/

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (super.attackEntityFrom(damagesource, i)) {
            Entity entity = damagesource.getEntity();
            if (entity instanceof EntityPlayer) {
                setAttackTarget((EntityLivingBase) entity);
            }
            if ((entity instanceof EntityArrow) && (((EntityArrow) entity).shootingEntity != null)) {
                entity = ((EntityArrow) entity).shootingEntity;
            }
            if (entity instanceof EntityLiving) {
                //TODO 4FIX TEST
                List list =
                        this.worldObj.getEntitiesWithinAABB(MoCEntityRat.class,
                                AxisAlignedBB.fromBounds(this.posX, this.posY, this.posZ, this.posX + 1.0D, this.posY + 1.0D, this.posZ + 1.0D)
                                        .expand(16D, 4D, 16D));
                Iterator iterator = list.iterator();
                do {
                    if (!iterator.hasNext()) {
                        break;
                    }
                    Entity entity1 = (Entity) iterator.next();
                    MoCEntityRat entityrat = (MoCEntityRat) entity1;
                    if ((entityrat != null) && (entityrat.getAttackTarget() == null) && entity instanceof EntityLivingBase) {
                        entityrat.setAttackTarget((EntityLivingBase) entity);
                    }
                } while (true);
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean climbing() {
        return !this.onGround && isOnLadder();
    }

    @Override
    protected Entity findPlayerToAttack() {
        float f = getBrightness(1.0F);
        if (f < 0.5F) {
            EntityPlayer entityplayer = this.worldObj.getClosestPlayerToEntity(this, 16D);
            return entityplayer != null && this.canEntityBeSeen(entityplayer) ? entityplayer : null;
        }
        return null;
    }

    @Override
    protected String getDeathSound() {
        return "mocreatures:ratdying";
    }

    @Override
    protected Item getDropItem() {
        return MoCreatures.ratRaw;
    }

    @Override
    protected String getHurtSound() {
        return "mocreatures:rathurt";
    }

    @Override
    protected String getLivingSound() {
        return "mocreatures:ratgrunt";
    }

    @Override
    public boolean isOnLadder() {
        return this.isCollidedHorizontally;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
    }
}
