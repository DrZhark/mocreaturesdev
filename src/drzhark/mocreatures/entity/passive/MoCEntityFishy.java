package drzhark.mocreatures.entity.passive;

import java.util.List;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAquatic;
import drzhark.mocreatures.entity.item.MoCEntityEgg;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class MoCEntityFishy extends MoCEntityAquatic {
    // public float edad;
    public int gestationtime;
    private boolean hasEaten;

    public static final String fishNames[] = { "Blue", "Orange", "Cyan", "Greeny", "Green", "Purple", "Yellow", "Striped", "Yellowy", "Piranha" };

    public MoCEntityFishy(World world)
    {
        super(world);
        setSize(0.3F, 0.3F);
        health = 6;
        setEdad(100);
        // unused_flag = true;
    }

    @Override
    public void selectType()
    {
        if (getType() == 0)
        {
            int i = rand.nextInt(100);
            if (i <= 9)
            {
                setType(1);
            }
            else if (i <= 19)
            {
                setType(2);
            }
            else if (i <= 29)
            {
                setType(3);
            }
            else if (i <= 39)
            {
                setType(4);
            }
            else if (i <= 49)
            {
                setType(5);
            }
            else if (i <= 59)
            {
                setType(6);
            }
            else if (i <= 69)
            {
                setType(7);
            }
            else if (i <= 79)
            {
                setType(8);
            }
            else if (i <= 89)
            {
                setType(9);
            }
            else
            {
                setType(10);
            }
            if (!MoCreatures.proxy.spawnPiranhas && (getType() == 10))
            {
                setType(1);
            }
        }

    }

    @Override
    public String getTexture()
    {

        switch (getType())
        {
        case 1:
            return MoCreatures.proxy.MODEL_TEXTURE + "fishy1.png";
        case 2:
            return MoCreatures.proxy.MODEL_TEXTURE + "fishy2.png";
        case 3:
            return MoCreatures.proxy.MODEL_TEXTURE + "fishy3.png";
        case 4:
            return MoCreatures.proxy.MODEL_TEXTURE + "fishy4.png";
        case 5:
            return MoCreatures.proxy.MODEL_TEXTURE + "fishy5.png";
        case 6:
            return MoCreatures.proxy.MODEL_TEXTURE + "fishy6.png";
        case 7:
            return MoCreatures.proxy.MODEL_TEXTURE + "fishy7.png";
        case 8:
            return MoCreatures.proxy.MODEL_TEXTURE + "fishy8.png";
        case 9:
            return MoCreatures.proxy.MODEL_TEXTURE + "fishy9.png";
        case 10:
            return MoCreatures.proxy.MODEL_TEXTURE + "fishy10.png";
        default:
            return MoCreatures.proxy.MODEL_TEXTURE + "fishy1.png";
        }
    }

    @Override
    public int getMaxHealth()
    {
        return 6;
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(22, Byte.valueOf((byte) 0)); // byte hasEaten 0 = false 1 = true
    }

    public boolean getHasEaten()
    {
        return (dataWatcher.getWatchableObjectByte(22) == 1);
    }

    public void setHasEaten(boolean flag)
    {
        if (worldObj.isRemote) { return; }
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(22, Byte.valueOf(input));
    }

    @Override
    protected void attackEntity(Entity entity, float f)
    {
        if (attackTime <= 0 && (f < 2D) && (entity.boundingBox.maxY > boundingBox.minY) && (entity.boundingBox.minY < boundingBox.maxY))
        {
        	if (entity.isInWater() || (entity instanceof EntityPlayer && ((EntityPlayer)entity).ridingEntity == null))
        	{
        		attackTime = 20;
                entity.attackEntityFrom(DamageSource.causeMobDamage(this), 1);
        	} 
            
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, int i)
    {
        if (super.attackEntityFrom(damagesource, i))
        {
            Entity entity = damagesource.getEntity();
            if ((riddenByEntity == entity) || (ridingEntity == entity)) { return true; }
            if (entity != this && getType() == 10 && worldObj.difficultySetting > 0)
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
    protected void dropFewItems(boolean flag, int x)
    {
        /*
         * if (!getIsAdult()) { return; }
         */
        int i = rand.nextInt(100);
        if (i < 70)// TODO change! 70
        {
            entityDropItem(new ItemStack(Item.fishRaw.itemID, 1, 0), 0.0F);
        }
        else
        {
            int j = rand.nextInt(2);
            for (int k = 0; k < j; k++)
            {
                entityDropItem(new ItemStack(MoCreatures.fishyegg, 1, getType()), 0.0F);
            }

        }
    }

    @Override
    protected Entity findPlayerToAttack()
    {
        if ((worldObj.difficultySetting > 0) && (getEdad() >= 100) && (getType() == 10))
        {
            EntityPlayer entityplayer = worldObj.getClosestVulnerablePlayerToEntity(this, 16D);
            if ((entityplayer != null) && entityplayer.isInWater() && !getIsTamed()) { return entityplayer; }
            if (rand.nextInt(30) == 0)
            {
                EntityLiving entityliving = FindTarget(this, 16D);
                if ((entityliving != null) && entityliving.isInWater()) { return entityliving; }
            }
        }
        return null;
    }

    // TODO move this
    public EntityLiving FindTarget(Entity entity, double d)
    {
        double d1 = -1D;
        EntityLiving entityliving = null;
        List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(d, d, d));
        for (int i = 0; i < list.size(); i++)
        {
            Entity entity1 = (Entity) list.get(i);
            if (!(entity1 instanceof EntityLiving) || (entity1 instanceof MoCEntityAquatic) || (entity1 instanceof MoCEntityEgg) || (entity1 instanceof MoCEntityEgg) || (entity1 instanceof EntityPlayer) || ((entity1 instanceof MoCEntityHorse) && !(MoCreatures.proxy.attackHorses)) || ((entity1 instanceof EntityWolf) && !(MoCreatures.proxy.attackWolves)))
            {
                continue;
            }
            double d2 = entity1.getDistanceSq(entity.posX, entity.posY, entity.posZ);
            if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1)) && ((EntityLiving) entity1).canEntityBeSeen(entity))
            {
                d1 = d2;
                entityliving = (EntityLiving) entity1;
            }
        }

        return entityliving;
    }

    @Override
    public boolean getCanSpawnHere()
    {
        return (MoCreatures.proxy.getFrequency(this.getEntityName()) > 0) && super.getCanSpawnHere();
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        if (MoCreatures.isServer())
        {
            if (!getIsAdult() && (rand.nextInt(100) == 0))
            {
                setEdad(getEdad() + 2);
                if (getEdad() >= 100)
                {
                    setAdult(true);
                }
            }
            if (!ReadyforParenting(this)) { return; }
            int i = 0;
            List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(4D, 3D, 4D));
            for (int j = 0; j < list.size(); j++)
            {
                Entity entity = (Entity) list.get(j);
                if (entity instanceof MoCEntityFishy)
                {
                    i++;
                }
            }

            if (i > 1) { return; }
            List list1 = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(4D, 2D, 4D));
            for (int k = 0; k < list.size(); k++)
            {
                Entity entity1 = (Entity) list1.get(k);
                if (!(entity1 instanceof MoCEntityFishy) || (entity1 == this))
                {
                    continue;
                }
                MoCEntityFishy entityfishy = (MoCEntityFishy) entity1;
                if (!ReadyforParenting(this) || !ReadyforParenting(entityfishy) || (this.getType() != entityfishy.getType()))
                {
                    continue;
                }
                if (rand.nextInt(100) == 0)
                {
                    gestationtime++;
                }
                if (gestationtime <= 50)
                {
                    continue;
                }
                int l = rand.nextInt(3) + 1;
                for (int i1 = 0; i1 < l; i1++)
                {
                    MoCEntityFishy entityfishy1 = new MoCEntityFishy(worldObj);
                    entityfishy1.setPosition(posX, posY, posZ);
                    worldObj.spawnEntityInWorld(entityfishy1);
                    worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, ((rand.nextFloat() - rand.nextFloat()) * 0.2F) + 1.0F);
                    setHasEaten(false);
                    entityfishy.setHasEaten(false);
                    gestationtime = 0;
                    entityfishy.gestationtime = 0;
                    //TODO NAMER
                    //entityfishy1.setTamed(true);
                    EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, 24D);
                    if (entityplayer != null && (MoCreatures.isServer()))
                    {
                        MoCTools.tameWithName((EntityPlayerMP) entityplayer, entityfishy1);
                    }

                    entityfishy1.setEdad(20);
                    entityfishy1.setAdult(false);
                    entityfishy1.setTypeInt(getType());
                }

                break;
            }
        }

    }

    public boolean ReadyforParenting(MoCEntityFishy entityfishy)
    {
        return entityfishy.getIsTamed() && entityfishy.getHasEaten() && entityfishy.getIsAdult();
    }
}