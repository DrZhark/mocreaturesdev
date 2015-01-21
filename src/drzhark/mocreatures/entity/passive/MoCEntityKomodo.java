package drzhark.mocreatures.entity.passive;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;

public class MoCEntityKomodo extends MoCEntityTameableAnimal
{
    public int sitCounter;
    public int tailCounter;
    public int tongueCounter;
    public int mouthCounter;

    public MoCEntityKomodo(World world)
    {
        super(world);
        setSize(1.6F, 0.5F);
        //health = 20;
        texture = "komododragon.png";
        setTamed(false);
        setAdult(false);
        this.stepHeight = 1.0F;

        if(rand.nextInt(6) == 0)
        {
            setEdad(30 + rand.nextInt(40));
        }
        else
        {
            setEdad(90 + rand.nextInt(30));
        }
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(22, Byte.valueOf((byte) 0)); // rideable: 0 nothing, 1 saddle
    }

   
    public void setRideable(boolean flag)
    {
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(22, Byte.valueOf(input));
    }

    public boolean getIsRideable()
    {
        return (dataWatcher.getWatchableObjectByte(22) == 1);
    }

    @Override
    public boolean getCanSpawnHere()
    {
        return getCanSpawnHereCreature() && getCanSpawnHereLiving();
    }

    @Override
    protected String getDeathSound()
    {
        openmouth();
        
        return "mocreatures:snakedying";//"komododying";
    }

    @Override
    protected String getHurtSound()
    {
        openmouth();
        return "mocreatures:snakehurt";//"komodohurt";
    }

    @Override
    protected String getLivingSound()
    {
        openmouth();
        return "mocreatures:snakehiss";//"komodo";
    }

    @Override
    public int getTalkInterval()
    {
        return 500;
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (tailCounter > 0 && ++tailCounter > 30)
        {
            tailCounter = 0;
        }

        if (rand.nextInt(100) == 0)
        {
            tailCounter = 1;
        }
        
        
        if (sitCounter > 0 && (riddenByEntity != null || ++sitCounter > 150))
        {
             sitCounter = 0;
        }
        
        if (rand.nextInt(100) == 0)
        {
            tongueCounter = 1;
        }
        
        if (mouthCounter > 0 && ++mouthCounter > 30)
        {
            mouthCounter = 0;
        }
        
        if (tongueCounter > 0 && ++tongueCounter > 20)
        {
            tongueCounter = 0;
        }

        if (MoCreatures.isServer())
        {
            if (riddenByEntity == null && sitCounter == 0 && rand.nextInt(500) == 0)
            {
               sit();
            }
        }
        if ((MoCreatures.isServer()) && !getIsAdult() && (rand.nextInt(500) == 0))
        {
            setEdad(getEdad() + 1);
            if (getEdad() >= 120)
            {
                setAdult(true);
            }
        }
    }

    private void openmouth()
    {
        mouthCounter = 1;
    }

    private void sit()
    {
        sitCounter = 1;
        if (MoCreatures.isServer())
        {
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 0), new TargetPoint(this.worldObj.provider.dimensionId, this.posX, this.posY, this.posZ, 64));
        }
        setPathToEntity(null);
    }

    @Override
    public void performAnimation(int animationType)
    {
        if (animationType == 0) //sitting animation
        {
            sitCounter = 1;
            setPathToEntity(null);
        }
    }

    @Override
    protected void dropFewItems(boolean flag, int x)
    {
        boolean flag2 = (getEdad() > 90 && rand.nextInt(5) == 0);
        
       if (flag2)
        {
            int j = rand.nextInt(2)+1;
            for (int k = 0; k < j; k++)
            {
                entityDropItem(new ItemStack(MoCreatures.mocegg, 1, 33), 0.0F);
            }
        }
       else
       {
       
        entityDropItem(new ItemStack(MoCreatures.hideCroc, 1, 0), 0.0F);
       }
    }

    @Override
    public float getSizeFactor() 
    {   
        if (!getIsAdult())
        {
            return (float)getEdad() * 0.01F;
        }
        return 1.2F;
    }

    @Override
    public boolean isNotScared()
    {
        return true;
    }

    @Override
    public boolean interact(EntityPlayer entityplayer)
    {
        if (super.interact(entityplayer)) { return false; }

        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        
        if ((itemstack != null) && getIsTamed() && !getIsRideable() && getEdad() > 90 &&
                (itemstack.getItem() == Items.saddle || itemstack.getItem() == MoCreatures.horsesaddle))
        {
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            setRideable(true);
            MoCTools.playCustomSound(this, "roping", worldObj);
            return true;
        }
        
        if (getIsRideable() && getIsTamed() && getEdad() > 90 && (riddenByEntity == null))
        {
            entityplayer.rotationYaw = rotationYaw;
            entityplayer.rotationPitch = rotationPitch;
            
            if (MoCreatures.isServer() && (this.riddenByEntity == null))
            {
                entityplayer.mountEntity(this);
            }

            return true;
        }
        return false;
    }

    @Override
    protected boolean isMovementCeased()
    {
        return sitCounter != 0 || (riddenByEntity != null);
    }

    @Override
    public boolean rideableEntity()
    {
        return true;
    }

    @Override
    public int nameYOffset()
    {
        if (getIsAdult())
        {
            return (-55);
        }
        return (int) ((60/getEdad()) * (-50));
    }

    @Override
    public double roperYOffset()
    {
        double r = (double) ((150 - getEdad()) * 0.012D);
        if (r < 0.55D)
        {
            r = 0.55D;
        }
        if (r > 1.2D)
        {
            r = 1.2D;
        }
        return r;
    }

    @Override
    public boolean swimmerEntity()
    {
        return true;
    }

    @Override
    public boolean canBreatheUnderwater()
    {
        return true;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("Saddle", getIsRideable());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        setRideable(nbttagcompound.getBoolean("Saddle"));
    }

    @Override
    public double getMountedYOffset()
    {
        double yOff = 0.15F;
        boolean sit = (sitCounter != 0);
        if (sit)
        {
            //yOff = -0.5F;
        }
        if (getIsAdult())
        {
            return (double) (yOff + (this.height) );
        }
        return (double) (this.height * (120/getEdad()) );
    }

    @Override
    protected void attackEntity(Entity entity, float f)
    {
        
        if (attackTime <= 0 && (f < 3.0D) && (entity.boundingBox.maxY > boundingBox.minY) && (entity.boundingBox.minY < boundingBox.maxY))
        {
            attackTime = 20;
            boolean flag = (entity instanceof EntityPlayer);
            if (flag)
            {
                MoCreatures.poisonPlayer((EntityPlayer) entity);
            }
            ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.poison.id, 150, 0));
            entity.attackEntityFrom(DamageSource.causeMobDamage(this), 2);
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i)
    {
        if (super.attackEntityFrom(damagesource, i))
        {
            Entity entity = damagesource.getEntity();
         
            
            if (entity != null && getIsTamed() && entity instanceof EntityPlayer) 
            { 
                return false; 
            }

            if ((riddenByEntity != null) && (entity == riddenByEntity)) 
            { 
                return false; 
            }

            if ((entity != this) && (worldObj.difficultySetting.getDifficultyId() > 0))
            {
                entityToAttack = entity;
            }
            return true;
        }
        return false;
    }

    @Override
    protected Entity findPlayerToAttack()
    {
        if (worldObj.difficultySetting.getDifficultyId() > 0)
        {
            EntityPlayer entityplayer = worldObj.getClosestVulnerablePlayerToEntity(this, 6D);
            if (!getIsTamed() && (entityplayer != null) && getEdad()>70)
            {
                    return entityplayer;
            }
            if ((rand.nextInt(500) == 0))
            {
                EntityLivingBase entityliving = getClosestEntityLiving(this, 8D);
                return entityliving;
            }
        }
        return null;
    }

    @Override
    public boolean isMyHealFood(ItemStack par1ItemStack)
    {
        return par1ItemStack != null && (par1ItemStack.getItem() == MoCreatures.ratRaw || par1ItemStack.getItem() == MoCreatures.rawTurkey);
    }

    @Override
    public boolean canBeCollidedWith()
    {
        return riddenByEntity == null;
    }

    @Override
    public boolean entitiesToIgnore(Entity entity)
    {
        return (super.entitiesToIgnore(entity) || (entity instanceof MoCEntityKomodo));
    }

    @Override
    public void dropMyStuff() 
    {
        if (MoCreatures.isServer())
        {
            dropArmor();
            MoCTools.dropSaddle(this, worldObj);
        }
    }

    @Override
    public int getMaxSpawnedInChunk()
    {
        return 2;
    }
}