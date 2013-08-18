package drzhark.mocreatures.entity.aquatic;

import java.util.List;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAquatic;
import drzhark.mocreatures.entity.MoCEntityTameableAquatic;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class MoCEntityFishy extends MoCEntityTameableAquatic {

    public int gestationtime;
    private boolean hasEaten;

    public static final String fishNames[] = { "Blue", "Orange", "Cyan", "Greeny", "Green", "Purple", "Yellow", "Striped", "Yellowy" };

    public MoCEntityFishy(World world)
    {
        super(world);
        setSize(0.3F, 0.3F);
        //health = getMaxHealth();
        setEdad(50 + rand.nextInt(50));
    }

    @Override
    public void selectType()
    {
        if (getType() == 0)
        {
            setType(rand.nextInt(fishNames.length) + 1);
        }

    }

    @Override
    public float getMaxHealth()
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
    protected void dropFewItems(boolean flag, int x)
    {
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
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        
        if (!this.isInsideOfMaterial(Material.water))
        {
            prevRenderYawOffset = renderYawOffset = rotationYaw = prevRotationYaw;
            rotationPitch = prevRotationPitch;
        }

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

            if (rand.nextInt(5) == 0 && !getIsTamed())
            {
                EntityLiving entityliving = getBoogey(8D);
                if (entityliving != null && entityliving.isInsideOfMaterial(Material.water))
                {
                   MoCTools.runLikeHell(this, entityliving);
                }
            }

            if (getIsTamed() && rand.nextInt(100) == 0 && func_110143_aJ() < getMaxHealth())
            {
                this.setEntityHealth(getMaxHealth());
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
        return false; //TOOD pending overhaul of breeding
    }
    
    @Override
    protected boolean canBeTrappedInNet() 
    {
        return true;
    }

    @Override
    public boolean renderName()
    {
        return getDisplayName() && (riddenByEntity == null);
    }
    
    @Override
    public int nameYOffset()
    {
        return -25;
    }
    
    @Override
    public int rollRotationOffset()
    {
        if (!this.isInsideOfMaterial(Material.water))
        {
            return -90;
        }
        return 0;
    }
    
    @Override
    public float getAdjustedYOffset()
    {
        if (!this.isInsideOfMaterial(Material.water))
        {
            return -0.1F;
        }
        return 0.0F;
    }

    @Override
    public float getAdjustedXOffset()
    {
        if (!this.isInsideOfMaterial(Material.water))
        {
            return -0.2F;
        }
        return 0F;
    }

    @Override
    protected boolean isFisheable()
    {
        return !getIsTamed();
    }
}