package drzhark.mocreatures.entity.passive;

import java.util.List;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAnimal;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class MoCEntityFox extends MoCEntityAnimal {
    protected double attackRange;
    protected int force;

    public MoCEntityFox(World world)
    {
        super(world);
        //foxboolean = false;
        //texture = MoCreatures.proxy.MODEL_TEXTURE + "fox.png";
        setSize(0.9F, 1.3F);
        health = 15;
        force = 2;
        attackRange = 4D;
    }

    @Override
    protected void attackEntity(Entity entity, float f)
    {
        if (attackTime <= 0 && (f < 2.0D) && (entity.boundingBox.maxY > boundingBox.minY) && (entity.boundingBox.minY < boundingBox.maxY))
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
    public void selectType()
    {
    	checkSpawningBiome();
    	
        if (getType() == 0)
        {
        	setType(1);
        }
    }   
    
    @Override
    public String getTexture()
    {

        switch (getType())
        {
        case 1:
            return MoCreatures.proxy.MODEL_TEXTURE + "fox.png";
        case 2:
            return MoCreatures.proxy.MODEL_TEXTURE + "foxsnow.png";

        default:
            return MoCreatures.proxy.MODEL_TEXTURE + "fox.png";
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, int i)
    {
        if (super.attackEntityFrom(damagesource, i))
        {
            Entity entity = damagesource.getEntity();
            if ((riddenByEntity == entity) || (ridingEntity == entity)) { return true; }
            if ((entity != this) && (worldObj.difficultySetting > 0))
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
    public int getMaxHealth()
    {
        return 15;
    }

    @Override
    public boolean interact(EntityPlayer entityplayer)
    {
        if (super.interact(entityplayer)) { return false; }
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if ((itemstack != null) && ((itemstack.itemID == MoCreatures.rawTurkey.itemID)))
        {
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }

            if (MoCreatures.isServer())
            {
                MoCTools.tameWithName((EntityPlayerMP) entityplayer, this);
            }
            health = getMaxHealth();

            if (MoCreatures.isServer() && !getIsAdult() && (getEdad() < 100))
            {
                setEdad(getEdad() + 1);
            }

            return true;
        }
        return false;
    }

    @Override
    public boolean isNotScared()
    {
        return true;
    }

    @Override
    protected Entity findPlayerToAttack()
    {
        if ((rand.nextInt(80) == 0) && (worldObj.difficultySetting > 0))
        {
            EntityLiving entityliving = getClosestTarget(this, 8D);
            return entityliving;
        }
        else
        {
            return null;
        }
    }

    @Override
    public boolean getCanSpawnHere()
    {
        if (MoCTools.isNearTorch(this)) { return false; }
        
        return (MoCreatures.proxy.getFrequency(this.getEntityName()) > 0) && super.getCanSpawnHere();
    }

    @Override
    public boolean checkSpawningBiome()
    {
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(boundingBox.minY);
        int k = MathHelper.floor_double(posZ);
        BiomeGenBase currentbiome = MoCTools.Biomekind(worldObj, i, j, k);
        
        String s = MoCTools.BiomeName(worldObj, i, j, k);
        if (currentbiome.temperature <= 0.05F)
        {
            setType(2);
        }
        return true;
    }

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
    protected String getDeathSound()
    {
        return "foxdying";
    }

    @Override
    protected int getDropItemId()
    {
        return MoCreatures.fur.itemID;
    }

    @Override
    protected String getHurtSound()
    {
        return "foxhurt";
    }

    @Override
    protected String getLivingSound()
    {
        return "foxcall";
    }

    @Override
    public int getMaxSpawnedInChunk()
    {
        return 1;
    }

    @Override
    protected float getSoundVolume()
    {
        return 0.3F;
    }

    @Override
    public boolean isMyHealFood(ItemStack par1ItemStack)
    {
        return par1ItemStack != null && par1ItemStack.itemID == MoCreatures.ratRaw.itemID;
    }

    @Override
    public int nameYOffset()
    {
        return -50;

    }

    @Override
    public double roperYOffset()
    {
        return 0.8D;
    }
}
