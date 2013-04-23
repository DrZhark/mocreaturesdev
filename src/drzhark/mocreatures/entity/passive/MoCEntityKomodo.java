package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import drzhark.mocreatures.network.MoCServerPacketHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

//TODO
//move tail also independent of speed (done)
//eggs - tame egg hatchs (done)
//poison(done)
//hunter behavior(done)
//riding? (done)
//saddle (done)
//taming behavior: rope, name (done)
//adjust yOffset of rider (done)
//tongue in/out tongue counter. done)
//lower body when resting (done)

public class MoCEntityKomodo extends MoCEntityAnimal
{
    public int sitCounter;
    public int tailCounter;
    public int tongueCounter;
    public int mouthCounter;

    public MoCEntityKomodo(World world)
    {
        super(world);
        setSize(1.6F, 0.5F);
        health = 20;
        texture = MoCreatures.proxy.MODEL_TEXTURE + "komododragon.png";
        setTamed(false);
        setAdult(false);
        this.stepHeight = 1.0F;
        
        //setEdad(35);
        if(rand.nextInt(6) == 0)
        {
            setEdad(30 + rand.nextInt(40));
            //setAdult(false);
        }
        else
        {
            setEdad(90 + rand.nextInt(30));
            //setAdult(true);
        }
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
        return (MoCreatures.proxy.getFrequency(this.getEntityName()) > 0) && super.getCanSpawnHere();
    }

    @Override
    public int getMaxHealth()
    {
        return 20;
    }

    @Override
    protected String getDeathSound()
    {
        openmouth();
        
        return "snakedying";//"komododying";
    }

    @Override
    protected String getHurtSound()
    {
        openmouth();
        return "snakehurt";//"komodohurt";
    }

    @Override
    protected String getLivingSound()
    {
        openmouth();
        return "snakehiss";//"komodo";
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
        
       /* 
        if (attackCounter > 0 && ++attackCounter > 100)
        {
            attackCounter = 0;
        }*/
        if (MoCreatures.isServer())
        {
            //Riding();
            
            
 
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
        //System.out.println("opening mouth " + MoCreatures.isServer());
        mouthCounter = 1;
    }
    
    private void sit()
    {
        sitCounter = 1;
        if (MoCreatures.isServer())
        {
            MoCServerPacketHandler.sendAnimationPacket(this.entityId, worldObj.provider.dimensionId, 0);
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
                entityDropItem(new ItemStack(MoCreatures.fishyegg, 1, 33), 0.0F);
            }

        }
       else
       {
       
        entityDropItem(new ItemStack(MoCreatures.crochide.itemID, 1, 0), 0.0F);
       }
    }
    
    @Override
    public float getSizeFactor() 
    {   
        if (!getIsAdult())
        {
            return (float)getEdad() * 0.01F;
//            return (float) getAge() * 0.01F;
        }
        return 1.2F;
    }
    
    /*public boolean isResting()
    {
        //TODO CHANGE!
        return (motionX < 0.001D && motionX > -0.001D) && (motionZ < 0.001D && motionZ > -0.001D);
        //(motionX == 0D && motionZ == 0D);
    }*/
    
    @Override
    public boolean isNotScared()
    {
        return true;
    }
    
    @Override
    public boolean interact(EntityPlayer entityplayer)
    {
        if (super.interact(entityplayer)) { return false; }
        
        //TODO remove before release
        //setAdult(true);
        //setTamed(true);
        //setRideable(true);
        //System.out.println("interacted");
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        
        if ((itemstack != null) && getIsTamed() && !getIsRideable() && getEdad() > 90 &&
                (itemstack.itemID == Item.saddle.itemID || itemstack.itemID == MoCreatures.horsesaddle.itemID))
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
                //System.out.println("riding!");
                // setDisplayName(false);
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
        return (int) ((120/getEdad()) * (-50));

    }
    
    @Override
    public double roperYOffset()
    {
        /*if (this.getIsAdult())
        {
            return 0D;
        }
        else
        {
            return (double) ((130 - getEdad()) * 0.01D);
        }*/
        
        double r = (double) ((150 - getEdad()) * 0.012D);
        if (r < 0.55D)
        {
            r = 0.55D;
        }
        if (r > 1.2D)
        {
            r = 1.2D;
        }
        //System.out.println("r =" + r);
        return r;//(double) ((150 - getEdad()) * 0.0075D);

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
            //return (double) (yOff + (this.height) );
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
            ((EntityLiving) entity).addPotionEffect(new PotionEffect(Potion.poison.id, 150, 0));
            entity.attackEntityFrom(DamageSource.causeMobDamage(this), 2);
        }
    }
    
    @Override
    public boolean attackEntityFrom(DamageSource damagesource, int i)
    {
        if (super.attackEntityFrom(damagesource, i))
        {
            Entity entity = damagesource.getEntity();
         
            
            if (entity != null && getIsTamed() && entity instanceof EntityPlayer) { return false; }
            

            if ((riddenByEntity != null) && (entity == riddenByEntity)) { return false; }
            
            if ((entity != this) && (worldObj.difficultySetting > 0))
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
        
        if (worldObj.difficultySetting > 0)
        {
            EntityPlayer entityplayer = worldObj.getClosestVulnerablePlayerToEntity(this, 6D);
            if (!getIsTamed() && (entityplayer != null) && getEdad()>70)
            {
                    return entityplayer;
            }
            if ((rand.nextInt(500) == 0))
            {
                EntityLiving entityliving = getClosestEntityLiving(this, 8D);
                return entityliving;
            }
        }
        return null;
    }
    
    @Override
    public boolean isMyHealFood(ItemStack par1ItemStack)
    {
        return par1ItemStack != null && (par1ItemStack.itemID == MoCreatures.ratRaw.itemID || par1ItemStack.itemID == MoCreatures.rawTurkey.itemID);
    }
    
    @Override
    public boolean canBeCollidedWith()
    {
        return riddenByEntity == null;
    }
    
    @Override
    public boolean entitiesToIgnore(Entity entity)
    {

        return (super.entitiesToIgnore(entity) || (entity instanceof MoCEntityKomodo) 
        );
    }
   
    @Override
    public void dropMyStuff() 
    {
        if (MoCreatures.isServer())
        {
            dropArmor();
            MoCTools.dropSaddle(this, worldObj);
            
            /*if (getBagged())
            {
               MoCTools.dropInventory(this, localchest);
               MoCTools.dropCustomItem(this, this.worldObj, new ItemStack(Block.chest, 1));
            }*/
        }
        
    }
}
