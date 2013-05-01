package drzhark.mocreatures.entity.passive;

import java.util.List;

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
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAnimal;

public class MoCEntityRaccoon extends MoCEntityAnimal{

	
	
	
	public MoCEntityRaccoon(World world)
    {
        super(world);
        setSize(0.8F, 0.8F);
        health = getMaxHealth();
        texture = MoCreatures.proxy.MODEL_TEXTURE + "raccoon.png";
        
        setEdad(70 + rand.nextInt(30));
    }

    @Override
    protected void attackEntity(Entity entity, float f)
    {
        if (attackTime <= 0 && (f < 2.0D) && (entity.boundingBox.maxY > boundingBox.minY) && (entity.boundingBox.minY < boundingBox.maxY))
        {
            attackTime = 20;
            entity.attackEntityFrom(DamageSource.causeMobDamage(this), 2);
            if (!(entity instanceof EntityPlayer))
            {
                MoCTools.destroyDrops(this, 3D);
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
        return 8;
    }

    @Override
    public boolean interact(EntityPlayer entityplayer)
    {
        if (super.interact(entityplayer)) { return false; }
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if ((itemstack != null) && (isItemEdible(itemstack.getItem()))) //((itemstack.itemID == MoCreatures.rawTurkey.itemID)))
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
    protected Entity findPlayerToAttack()
    {
        if ((rand.nextInt(80) == 0) && (worldObj.difficultySetting > 0))
        {
            EntityLiving entityliving = getClosestEntityLiving(this, 8D);
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
        return (MoCreatures.proxy.getFrequency(this.getEntityName()) > 0) && super.getCanSpawnHere();
    }

    
    @Override
    protected String getDeathSound()
    {
        return "raccoondying";
    }

    @Override
    protected int getDropItemId()
    {
        return MoCreatures.fur.itemID;
    }

    @Override
    protected String getHurtSound()
    {
        return "raccoonhurt";
    }

    @Override
    protected String getLivingSound()
    {
        return "raccoongrunt";
    }

    @Override
    public int getMaxSpawnedInChunk()
    {
        return 2;
    }

   
    
    @Override
    public int nameYOffset()
    {
        return -30;

    }

    @Override
    public double roperYOffset()
    {
        return 1.15D;
    }

    @Override
    public float getSizeFactor() 
    {   
		return 0.8F * (float)getEdad() * 0.01F;
    }
    
    
    
    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (MoCreatures.isServer())
        {
        	if (!getIsAdult() && rand.nextInt(300) == 0)
            {
                setEdad(getEdad() + 1);
                if (getEdad() >= 100)
                {
                    setAdult(true);
                }
            }
        }
    }
        
}
