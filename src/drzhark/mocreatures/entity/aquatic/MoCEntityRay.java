package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAquatic;
import drzhark.mocreatures.network.MoCServerPacketHandler;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class MoCEntityRay extends MoCEntityAquatic {

    private int poisoncounter;
    private int tailCounter;

    public MoCEntityRay(World world)
    {
        super(world);
        setSize(1.8F, 0.5F);
        //health = 10;
        setEdad(50 + (rand.nextInt(50)));
        moveSpeed = 0.3F;
    }

    @Override
    public void selectType()
    {
        checkSpawningBiome();

        if (getType() == 0)
        {
            int i = rand.nextInt(100);
            if (i <= 35)
            {
                setType(1);
                setEdad(80 + (rand.nextInt(100)));
            }
            else
            {
                setType(1);
                setEdad(70);
            }
            getMaxHealth();
        }
    }

    @Override
    public ResourceLocation getTexture()
    {
        switch (getType())
        {
        case 1:
            return MoCreatures.proxy.getTexture("mantray.png");
        case 2:
            return MoCreatures.proxy.getTexture("stingray.png");

        default:
            return MoCreatures.proxy.getTexture("stingray.png");
        }
    }

    @Override
    public float getMaxHealth()
    {
        if (getType() == 2)
        {
            return 10;
        }
        return 20;
    }

    @Override
    public float getMoveSpeed()
    {
        return 0.3F;
    }

    public boolean isPoisoning()
    {
        return tailCounter != 0;
    }
    
    @Override
    public boolean interact(EntityPlayer entityplayer)
    {
        if (super.interact(entityplayer)) { return false; }
        
        if (riddenByEntity == null && getType() == 1)
        {
            entityplayer.rotationYaw = rotationYaw;
            entityplayer.rotationPitch = rotationPitch;
            entityplayer.posY = posY;
            if (!worldObj.isRemote)
            {
                entityplayer.mountEntity(this);
            }
            return true;
        }
        return false;
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        if (!worldObj.isRemote)
        {
            if (!getIsAdult() && (rand.nextInt(50) == 0))
            {
                setEdad(getEdad() + 1);
                if ((getType() == 1 && getEdad() >= 180) || (getType() > 1 && getEdad() >= 90))
                {
                    setAdult(true);
                }
            }

            if (!getIsTamed() && getType() > 1 && ++poisoncounter > 250 && (worldObj.difficultySetting > 0) && rand.nextInt(30) == 0)
            {
                if (MoCTools.findNearPlayerAndPoison(this, true))
                {
                    MoCServerPacketHandler.sendAnimationPacket(this.entityId, this.worldObj.provider.dimensionId, 1);
                    poisoncounter = 0;
                }
            }
        }
        else //client stuff
        {
            if (tailCounter > 0 && ++tailCounter > 50)
            {
                tailCounter = 0;
            }
        }
    }

    @Override
    public void performAnimation(int animationType)
    {
        if (animationType == 1) //attacking with tail
        {
            tailCounter = 1;
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i)
    {
        if (super.attackEntityFrom(damagesource, i))
        {
            if (getType() == 1 || (worldObj.difficultySetting == 0)) { return true; }
            Entity entity = damagesource.getEntity();

            if (entity != this)
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
    public boolean checkSpawningBiome()
    {
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(boundingBox.minY);
        int k = MathHelper.floor_double(posZ);
        String s = MoCTools.BiomeName(worldObj, i, j, k);
        if (s != "Ocean")
        {
            setType(2);
        }
        return true;
    }

    @Override
    public float getAdjustedYOffset()
    {
        if (!isSwimming())
        {
            return 0.09F;
        }
        else if (getType() == 1)
        {
            return 0.15F;
        }

        return 0.25F;
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
    public boolean canBeTrappedInNet()
    {
        return ( getType() == 2 || (getType()== 1 && getIsTamed()) );
    }

    @Override
    public double getMountedYOffset()
    {
        return (double)this.height * 0.15D * getSizeFactor();
    }

    @Override
    public float getSizeFactor() 
    {   
        return (float)getEdad() * 0.01F;
    }
}