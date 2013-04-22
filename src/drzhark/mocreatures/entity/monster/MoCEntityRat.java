package drzhark.mocreatures.entity.monster;

import java.util.Iterator;
import java.util.List;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityMob;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class MoCEntityRat extends MoCEntityMob {
    public MoCEntityRat(World world)
    {
        super(world);
        setSize(0.5F, 0.5F);
        health = 10;
        //attackStrength = 1;
    }

    @Override
    public void selectType()
    {
        if (getType() == 0)
        {
            int i = rand.nextInt(100);
            if (i <= 65)
            {
                setType(1);
            }
            else if (i <= 98)
            {
                setType(2);
            }
            else
            {
                setType(3);
            }
        }

        /*if (type == 1)
        {
            texture = MoCreatures.proxy.MODEL_TEXTURE + "ratb.png";
        } else if (type == 2)
        {
            texture = MoCreatures.proxy.MODEL_TEXTURE + "ratbl.png";
        } else if (type == 3)
        {
            texture = MoCreatures.proxy.MODEL_TEXTURE + "ratw.png";
        }*/
    }

    @Override
    public String getTexture()
    {

        switch (getType())
        {
        case 1:
            return MoCreatures.proxy.MODEL_TEXTURE + "ratb.png";
        case 2:
            return MoCreatures.proxy.MODEL_TEXTURE + "ratbl.png";
        case 3:
            return MoCreatures.proxy.MODEL_TEXTURE + "ratw.png";

        default:
            return MoCreatures.proxy.MODEL_TEXTURE + "ratb.png";
        }
    }

    @Override
    protected void attackEntity(Entity entity, float f)
    {
        float f1 = getBrightness(1.0F);
        if ((f1 > 0.5F) && (rand.nextInt(100) == 0))
        {
            entityToAttack = null;
            return;
        }
        else
        {
            super.attackEntity(entity, f);
            return;
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, int i)
    {
        if (super.attackEntityFrom(damagesource, i))
        {
            Entity entity = damagesource.getEntity();
            if (entity instanceof EntityPlayer)
            {
                entityToAttack = entity;
            }
            if ((entity instanceof EntityArrow) && (((EntityArrow) entity).shootingEntity != null))
            {
                entity = ((EntityArrow) entity).shootingEntity;
            }
            if (entity instanceof EntityLiving)
            {
                //TODO 4FIX TEST
                List list = worldObj.getEntitiesWithinAABB(MoCEntityRat.class, AxisAlignedBB.getBoundingBox(posX, posY, posZ, posX + 1.0D, posY + 1.0D, posZ + 1.0D).expand(16D, 4D, 16D));
                Iterator iterator = list.iterator();
                do
                {
                    if (!iterator.hasNext())
                    {
                        break;
                    }
                    Entity entity1 = (Entity) iterator.next();
                    MoCEntityRat entityrat = (MoCEntityRat) entity1;
                    if ((entityrat != null) && (entityrat.entityToAttack == null))
                    {
                        entityrat.entityToAttack = entity;
                    }
                } while (true);
            }
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean climbing()
    {
        return !onGround && isOnLadder();
    }

    @Override
    protected Entity findPlayerToAttack()
    {
        float f = getBrightness(1.0F);
        if (f < 0.5F) {
            EntityPlayer entityplayer = worldObj.getClosestVulnerablePlayerToEntity(this, 16D);
            return entityplayer != null && this.canEntityBeSeen(entityplayer) ? entityplayer : null;
        }
            return null;
        
    }

    @Override
    public boolean getCanSpawnHere()
    {
        return (MoCreatures.proxy.getFrequency(this.getEntityName()) > 0) && super.getCanSpawnHere();
    }

    @Override
    protected String getDeathSound()
    {
        return "ratdying";
    }

    @Override
    protected int getDropItemId()
    {
        return MoCreatures.ratRaw.itemID;
        //return Item.coal.itemID;
    }

    @Override
    protected String getHurtSound()
    {
        return "rathurt";
    }

    @Override
    protected String getLivingSound()
    {
        return "ratgrunt";
    }

    @Override
    public int getMaxSpawnedInChunk()
    {
        return 5;
    }

    @Override
    public int getMaxHealth()
    {
        return 10;
    }

    

    @Override
    public boolean isOnLadder()
    {
        return isCollidedHorizontally;
    }

    

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
    }

    @Override
    public int getAttackStrength(Entity par1Entity)
    {
        return 1;
    }
}
