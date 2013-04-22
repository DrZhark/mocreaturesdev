package drzhark.mocreatures.entity.passive;

import java.util.List;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAnimal;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class MoCEntityBoar extends MoCEntityAnimal {
    protected int force;
    protected double attackRange;

    public MoCEntityBoar(World world)
    {
        super(world);
        texture = MoCreatures.proxy.MODEL_TEXTURE + "boara.png";
        setSize(0.9F, 0.9F);
        health = 10;
        force = 1;
        attackRange = 1.0D;
        setEdad(50);
        // setTamed(false);
        if (rand.nextInt(4) == 0)
        {
            setAdult(false);

        }
        else
        {
            setAdult(true);
        }
    }

    @Override
    protected void attackEntity(Entity entity, float f)
    {
        if (attackTime <= 0 && (f < 2.5D) && (entity.boundingBox.maxY > boundingBox.minY) && (entity.boundingBox.minY < boundingBox.maxY))
        {
            attackTime = 20;
            entity.attackEntityFrom(DamageSource.causeMobDamage(this), force);
            if (!(entity instanceof EntityPlayer))
            {
                MoCTools.destroyDrops(this, 3D);
            }
        }
    }

    @Override
    public String getTexture()
    {
        if (getIsAdult()) { return MoCreatures.proxy.MODEL_TEXTURE + "boara.png"; }
        return MoCreatures.proxy.MODEL_TEXTURE + "boarb.png";

    }

    @Override
    protected boolean canDespawn()
    {
        return true;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, int i)
    {
        if (super.attackEntityFrom(damagesource, i))
        {
            Entity entity = damagesource.getEntity();
            if ((riddenByEntity == entity) || (ridingEntity == entity)) { return true; }
            if ((entity != this) && (worldObj.difficultySetting > 0) && getIsAdult())
            {
                entityToAttack = entity;
            }
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public boolean isNotScared()
    {
        return getIsAdult();
    }

    @Override
    protected Entity findPlayerToAttack()
    {
        if (worldObj.difficultySetting > 0)
        {
            EntityPlayer entityplayer = worldObj.getClosestVulnerablePlayerToEntity(this, attackRange);
            if ((entityplayer != null) && (rand.nextInt(50) == 0)) { return entityplayer; }
            if (rand.nextInt(80) == 0)
            {
                EntityLiving entityliving = getClosestTarget(this, 10D);
                return entityliving;
            }
        }
        return null;
    }

    @Override
    public boolean getCanSpawnHere()
    {
        if (MoCTools.isNearTorch(this)) { return false; }

        return (MoCreatures.proxy.getFrequency(this.getEntityName()) > 0) && super.getCanSpawnHere();
    }

    // TODO find a way to move this
    public EntityLiving getClosestTarget(Entity entity, double d)
    {
        double d1 = -1D;
        EntityLiving entityliving = null;
        List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(d, d, d));
        for (int i = 0; i < list.size(); i++)
        {
            Entity entity1 = (Entity) list.get(i);
            if (!(entity1 instanceof EntityLiving) || (entity1 == entity) || (entity1 == entity.riddenByEntity) || (entity1 == entity.ridingEntity) || (entity1 instanceof EntityPlayer) || (entity1 instanceof EntityMob) || (height <= entity1.height) || (width <= entity1.width))
            {
                continue;
            }
            double d2 = entity1.getDistanceSq(entity.posY, entity.posZ, entity.motionX);
            if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1)) && ((EntityLiving) entity1).canEntityBeSeen(entity))
            {
                d1 = d2;
                entityliving = (EntityLiving) entity1;
            }
        }

        return entityliving;
    }

    @Override
    public void onLivingUpdate()
    {
        if (worldObj.difficultySetting == 1)
        {
            attackRange = 2D;
            force = 1;
        }
        else if (worldObj.difficultySetting > 1)
        {
            attackRange = 3D;
            force = 2;
        }
        super.onLivingUpdate();

        if ((MoCreatures.isServer()) && !getIsAdult() && (rand.nextInt(250) == 0))
        {
            setEdad(getEdad() + 1);
            if (getEdad() >= 100)
            {
                setAdult(true);
            }
        }
    }

    @Override
    protected int getDropItemId()
    {

        if (rand.nextInt(2) == 0) { return Item.porkRaw.itemID; }

        return MoCreatures.animalHide.itemID;
    }

    @Override
    protected String getLivingSound()
    {
        return "mob.pig.say";
    }

    @Override
    protected String getHurtSound()
    {
        return "mob.pig.say";
    }

    @Override
    protected String getDeathSound()
    {
        return "mob.pig.death";
    }

}
