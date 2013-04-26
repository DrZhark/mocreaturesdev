package drzhark.mocreatures.entity.passive;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import drzhark.mocreatures.inventory.MoCAnimalChest;
import drzhark.mocreatures.network.MoCServerPacketHandler;


public class MoCEntityWyvern extends MoCEntityAnimal{

	
	public MoCAnimalChest localchest;
	public ItemStack localstack;
	public int mouthCounter;
	public int wingFlapCounter;
	public int diveCounter;
	public static final String wyvernNames[] = { "Jungle", "Swamp", "Sand", "Savanna", "Mother", "Undead", "Light", "Dark" };
	
	public MoCEntityWyvern(World world)
	{
		super(world);
		setSize(1.9F, 1.7F);
		health = getMaxHealth();
		setAdult(false);
		this.stepHeight = 1.0F;
        
        //setEdad(35);
        if(rand.nextInt(6) == 0)
        {
            setEdad(50 + rand.nextInt(50));
            //setAdult(false);
        }
        else
        {
            setEdad(80 + rand.nextInt(20));
            //setAdult(true);
        }
        
        //setTamed(true);
        //setSitting(true);
        //setAdult(false);
		
	}

	
	@Override
	protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(22, Byte.valueOf((byte) 0)); // isRideable - 0 false 1 true
		dataWatcher.addObject(23, Byte.valueOf((byte) 0)); // isChested - 0 false 1 true
		dataWatcher.addObject(24, Byte.valueOf((byte) 0)); // armor 0 by default, 1 metal, 2 gold, 3 diamond, 4 crystaline
		dataWatcher.addObject(25, Byte.valueOf((byte) 0)); // isFlying 0 false 1 true
		dataWatcher.addObject(26, Byte.valueOf((byte) 0)); // isSitting - 0 false 1 true
		
	}
	
	public boolean getIsFlying()
	{
		return (dataWatcher.getWatchableObjectByte(25) == 1);
	}

	
	public void setIsFlying(boolean flag)
	{
		byte input = (byte) (flag ? 1 : 0);
		dataWatcher.updateObject(25, Byte.valueOf(input));
	}
	
	public byte getArmorType()
	{
		return dataWatcher.getWatchableObjectByte(24);
	}
	
	@Override
	public void setArmorType(byte i)
	{
		dataWatcher.updateObject(24, Byte.valueOf(i));
	}
	
	public boolean getIsRideable()
	{
		return (dataWatcher.getWatchableObjectByte(22) == 1);
	}
	
	public void setRideable(boolean flag)
	{
		byte input = (byte) (flag ? 1 : 0);
		dataWatcher.updateObject(22, Byte.valueOf(input));
	}
	
	public boolean getIsChested()
	{
		return (dataWatcher.getWatchableObjectByte(23) == 1);
	}
	
	public void setIsChested(boolean flag)
	{
		byte input = (byte) (flag ? 1 : 0);
		dataWatcher.updateObject(23, Byte.valueOf(input));
	}
	
	public boolean getIsSitting()
    {
        return (dataWatcher.getWatchableObjectByte(26) == 1);
    }
	
	public void setSitting(boolean flag)
    {
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(26, Byte.valueOf(input));
    }
	
	@Override
	public void selectType()
	{
		if (getType() == 0)
		{
			//setType(rand.nextInt(8)+1);
			int i = rand.nextInt(100);
			if (i <= 25)
			{
				setType(1);
			}
			else if (i <= 50)
			{
				setType(2);
			}
			else if (i <= 75)
			{
				setType(3);
			}
			else if (i <= 98)
			{
				setType(4);
			}
			else
			{
				setType(5);
			}
		}

	}
	
	 @Override
	    public boolean isNotScared()
	    {
	        return true;
	    }
	
	@Override
	public String getTexture()
	{
	    switch (getType())
        {
        case 1:
            return MoCreatures.proxy.MODEL_TEXTURE + "wyvernjungle.png";
        case 2:
            return  MoCreatures.proxy.MODEL_TEXTURE +"wyvernmix.png";
        case 3:
            return  MoCreatures.proxy.MODEL_TEXTURE +"wyvernsand.png";
        case 4:
            return  MoCreatures.proxy.MODEL_TEXTURE +"wyvernsun.png";
        case 5:
            return  MoCreatures.proxy.MODEL_TEXTURE +"wyvernmother.png";
        case 6:
            return  MoCreatures.proxy.MODEL_TEXTURE +"wyvernundead.png";
        case 7:
            return  MoCreatures.proxy.MODEL_TEXTURE +"wyvernlight.png";
        case 8:
            return  MoCreatures.proxy.MODEL_TEXTURE +"wyverndark.png";
        /*case 9:
            return  MoCreatures.proxy.MODEL_TEXTURE +"wyvernnether.png";*/
        
        default:
            return  MoCreatures.proxy.MODEL_TEXTURE +"wyvernsun.png";
        }

	    	
	}
	
	@Override
	public void onLivingUpdate()
	{
		/**
		 * slow falling
		 */
		
		if (!getIsFlying() && isOnAir() && (motionY < 0.0D))
		{
			motionY *= 0.6D;
		}
		
		if (mouthCounter > 0 && ++mouthCounter > 30)
        {
            mouthCounter = 0;
        }
		
		if (diveCounter > 0 && ++diveCounter > 5)
        {
			diveCounter = 0;
        }
		
		if ((this.jumpPending))
		{
			if (wingFlapCounter == 0)
			{
				MoCTools.playCustomSound(this, "wingflap", worldObj);
			}
			wingFlapCounter = 1;
		}

		
		if (isOnAir() && rand.nextInt(30) == 0)
		{
			wingFlapCounter = 1;
		}
		
				
		if (wingFlapCounter > 0 && ++wingFlapCounter > 20)
		{
			wingFlapCounter = 0;
		}
		
		if (isFlyingAlone())
		{
			wingFlapCounter = 1;
		}
		
		if (MoCreatures.isServer())
		{
			if (!getIsAdult() && (rand.nextInt(500) == 0))
	        {
	            setEdad(getEdad() + 1);
	            if (getEdad() >= getMaxAge())
	            {
	                setAdult(true);
	            }
	        }
			
			if (isFlyingAlone() &&  rand.nextInt(100) == 0 && !isMovementCeased())
			{
				wingFlap();
			}
			
			
			if (isFlyingAlone() && !hasPath() && !isMovementCeased() && entityToAttack == null && rand.nextInt(20)==0)
			{
				updateWanderPath();
			}
			
			if (riddenByEntity != null)
			{
				setIsFlying(false);
			}
			else if (entityToAttack != null && rand.nextInt(20)==0)
			{
				setIsFlying(true);
			}
			else if (!getIsTamed() && rand.nextInt(300)==0)
			{
				setIsFlying(!getIsFlying());
			}
			
			
		}
		
		

		super.onLivingUpdate();
	}
	
	
	//using it?
	public void wingFlap()
	{

		if (wingFlapCounter == 0)
		{
			MoCTools.playCustomSound(this, "wyvernwingflap", worldObj);
		}
		wingFlapCounter = 1;

		motionY = 0.5D;
	}
	
	@Override
    public float getSizeFactor() 
    {   
		return (float)getEdad() * 0.01F;
		
        /*if (!getIsAdult())
        {
            return (float)getEdad() * 0.01F;
        }
        if (getType() >= 5)
        {
        	return 1.5F;
        }
        return 1.0F;*/
    }
	
	@Override
	public boolean isFlyingAlone()
	{
		return getIsFlying() && riddenByEntity == null;
	}
	
	@Override
	public int flyingHeight()
	{
		
		return 18;
	}
	
	protected float getFlyingSpeed()
	{
		return 0.7F;
	}

	
	
	@Override
	public boolean interact(EntityPlayer entityplayer)
	{
		/*
		
	    setEdad(getEdad()+1);
	    if (getEdad() > 110)
	    {
	    	setEdad(30);
	    }
	     //System.out.println("Edad = " + getEdad());
	    */
	   
		//setTamed(true);
		//setType(5);
		if (super.interact(entityplayer)) { return false; }
		ItemStack itemstack = entityplayer.inventory.getCurrentItem();

		if ((itemstack != null) && (itemstack.itemID == MoCreatures.whip.itemID) && getIsTamed() && (riddenByEntity == null))
        {
            setSitting(!getIsSitting());
            return true;
        }
		
		if ((itemstack != null) && !getIsRideable() && getEdad() > 90 && (itemstack.itemID == Item.saddle.itemID || itemstack.itemID == MoCreatures.horsesaddle.itemID) )
		{
			if (--itemstack.stackSize == 0)
			{
				entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
			}
			setRideable(true);
			return true;
		}

		if ((itemstack != null) && this.getIsTamed() && getEdad() > 90 && itemstack.itemID == MoCreatures.horsearmormetal.itemID)
		{
			if (getArmorType() == 0)
			{
				MoCTools.playCustomSound(this, "armorput", worldObj);
			}
			dropArmor();
			setArmorType((byte) 1);
			if (--itemstack.stackSize == 0)
			{
				entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
			}

			return true;
		}

		if ((itemstack != null) && this.getIsTamed() && getEdad() > 90 && itemstack.itemID == MoCreatures.horsearmorgold.itemID)
		{
			if (getArmorType() == 0)
			{
				MoCTools.playCustomSound(this, "armorput", worldObj);
			}
			dropArmor();
			setArmorType((byte) 2);
			if (--itemstack.stackSize == 0)
			{
				entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
			}
			return true;
		}

		if ((itemstack != null) && this.getIsTamed() && getEdad() > 90 && itemstack.itemID == MoCreatures.horsearmordiamond.itemID)
		{
			if (getArmorType() == 0)
			{
				MoCTools.playCustomSound(this, "armorput", worldObj);
			}
			dropArmor();
			setArmorType((byte) 3);
			if (--itemstack.stackSize == 0)
			{
				entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
			}
			return true;
		}

		
		if ((itemstack != null) && getIsTamed() && getEdad() > 90 && !getIsChested() && (itemstack.itemID == Block.chest.blockID))
		{
			if (--itemstack.stackSize == 0)
			{
				entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
			}

			entityplayer.inventory.addItemStackToInventory(new ItemStack(MoCreatures.key));
			setIsChested(true);
			worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, ((rand.nextFloat() - rand.nextFloat()) * 0.2F) + 1.0F);
			return true;
		}
		
		if ((itemstack != null) && (itemstack.itemID == MoCreatures.key.itemID) && getIsChested())
		{
			// if first time opening horse chest, we must initialize it
			if (localchest == null)
			{
				localchest = new MoCAnimalChest("WyvernChest", 14);// 
			}
			// only open this chest on server side
			if (MoCreatures.isServer())
			{
				entityplayer.displayGUIChest(localchest);
			}
			return true;

		}
		
		if ((itemstack != null) && (itemstack.itemID == MoCreatures.viallight.itemID) && getIsTamed() && getEdad() > 90 && getType() < 5)
		{
			if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Item.glassBottle));
            }
            else
            {
                entityplayer.inventory.addItemStackToInventory(new ItemStack(Item.glassBottle));
            }
			
			if (MoCreatures.isServer())
			{
				int i = getType() + 49;
                MoCEntityEgg entityegg = new MoCEntityEgg(worldObj, i);
                entityegg.setPosition(entityplayer.posX, entityplayer.posY, entityplayer.posZ);
                entityplayer.worldObj.spawnEntityInWorld(entityegg);
                entityegg.motionY += worldObj.rand.nextFloat() * 0.05F;
                entityegg.motionX += (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.3F;
                entityegg.motionZ += (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.3F;
			}
			return true;

		}
		
		if ((itemstack != null) && getType() == 5 && (itemstack.itemID == MoCreatures.vialundead.itemID) && getIsTamed())
		{
			if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Item.glassBottle));
            }
            else
            {
                entityplayer.inventory.addItemStackToInventory(new ItemStack(Item.glassBottle));
            }
			
			if (MoCreatures.isServer())
			{
				setType(6);
			}
			return true;

		}
		
		if ((itemstack != null) && getType() == 5 && (itemstack.itemID == MoCreatures.viallight.itemID) && getIsTamed())
		{
			if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Item.glassBottle));
            }
            else
            {
                entityplayer.inventory.addItemStackToInventory(new ItemStack(Item.glassBottle));
            }
			
			if (MoCreatures.isServer())
			{
				setType(7);
			}
			return true;

		}
		
		if ((itemstack != null) && getType() == 5 && (itemstack.itemID == MoCreatures.vialdarkness.itemID) && getIsTamed())
		{
			if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Item.glassBottle));
            }
            else
            {
                entityplayer.inventory.addItemStackToInventory(new ItemStack(Item.glassBottle));
            }
			
			if (MoCreatures.isServer())
			{
				setType(8);
			}
			return true;

		}
		
		/*if ((itemstack != null) && getType() == 5 && (itemstack.itemID == MoCreatures.vialnightmare.itemID) && getIsTamed())
		{
			if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Item.glassBottle));
            }
            else
            {
                entityplayer.inventory.addItemStackToInventory(new ItemStack(Item.glassBottle));
            }
			
			if (MoCreatures.isServer())
			{
				setType(9);
			}
			return true;

		}*/

		if (getIsRideable() && getEdad() > 90 && (riddenByEntity == null))
		{
			entityplayer.rotationYaw = rotationYaw;
			entityplayer.rotationPitch = rotationPitch;
			
			if (MoCreatures.isServer())
			{
				entityplayer.mountEntity(this);
				setSitting(false);
			}
			
			return true;
		}
		else
		{
			return false;
		}

	}
	
	
	/**
	 * Drops the current armor
	 */
	public void dropArmor()
	{
		if (MoCreatures.isServer())
		{
			int i = getArmorType();
			if (i != 0)
			{
				MoCTools.playCustomSound(this, "armoroff", worldObj);
			}

			if (i == 1)
			{
				EntityItem entityitem = new EntityItem(worldObj, this.posX, this.posY, this.posZ, new ItemStack(MoCreatures.horsearmormetal, 1));
				entityitem.delayBeforeCanPickup = 10;
				worldObj.spawnEntityInWorld(entityitem);
			}
			if (i == 2)
			{
				EntityItem entityitem = new EntityItem(worldObj, this.posX, this.posY, this.posZ, new ItemStack(MoCreatures.horsearmorgold, 1));
				entityitem.delayBeforeCanPickup = 10;
				worldObj.spawnEntityInWorld(entityitem);
			}
			if (i == 3)
			{
				EntityItem entityitem = new EntityItem(worldObj, this.posX, this.posY, this.posZ, new ItemStack(MoCreatures.horsearmordiamond, 1));
				entityitem.delayBeforeCanPickup = 10;
				worldObj.spawnEntityInWorld(entityitem);
			}
			
			setArmorType((byte) 0);
		}
	}
	
	
	@Override
    public boolean rideableEntity()
    {
        return true;
    }
	
	@Override
    public int getMaxHealth()
    {
		if (getType() >= 5)
		{
			return 80;
		}
        return 40;
    }

    @Override
    protected String getDeathSound()
    {
        //openmouth();
        
        return "wyverndying";
    }

    @Override
    protected String getHurtSound()
    {
        openMouth();
        return "wyvernhurt";
    }

    @Override
    protected String getLivingSound()
    {
        openMouth();
        //System.out.println("Wyvern hiss, server? " + MoCreatures.isServer());
        return "wyverngrunt";
    }
    
    @Override
    public int getTalkInterval()
    {
        return 400;
    }

   
    
    @Override
	protected boolean isMovementCeased()
	{
    	return (riddenByEntity != null) || getIsSitting();
	}
    
    @Override
	public boolean isFlyer()
	{
		return true;
	}
    
    
    @Override
	protected void fall(float f)
	{
	}
    
    @Override
	public double getMountedYOffset()
	{
    	return (double)this.height * 0.90D * getSizeFactor();
    	
    	/*if (getType() >= 5)
    	{
    		return (double)this.height * 0.9D * getSizeFactor();
    	}
    	return (double)this.height * 0.9D;*/
	}
    
    @Override
	public void updateRiderPosition()
	{
		
		double dist = getSizeFactor() * (0.3D);
		double newPosX = posX - (dist * Math.cos((MoCTools.realAngle(renderYawOffset - 90F)) / 57.29578F));
		double newPosZ = posZ - (dist * Math.sin((MoCTools.realAngle(renderYawOffset - 90F)) / 57.29578F));
		riddenByEntity.setPosition(newPosX, posY + getMountedYOffset() + riddenByEntity.getYOffset(), newPosZ);
	}
    
    @Override
    protected void attackEntity(Entity entity, float f)
    {
        
        if (attackTime <= 0 && (f < 3.0D) && (entity.boundingBox.maxY > boundingBox.minY) && (entity.boundingBox.minY < boundingBox.maxY))
        {
            attackTime = 20;
            boolean flag = (rand.nextInt(3) == 0);
            if (flag)
            {
                if (entity instanceof EntityPlayer) 
                {
                	MoCreatures.poisonPlayer((EntityPlayer) entity);
                }
                ((EntityLiving) entity).addPotionEffect(new PotionEffect(Potion.poison.id, 200, 0));
                MoCTools.playCustomSound(this, "wyvernpoisoning", worldObj);
            }
            
            int dmg = 5;
            if (getType() >= 5) dmg = 10;
            entity.attackEntityFrom(DamageSource.causeMobDamage(this), dmg);
            openMouth();
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
        
        if (worldObj.difficultySetting > 0 && !getIsTamed())
        {
            EntityPlayer entityplayer = worldObj.getClosestVulnerablePlayerToEntity(this, 10D);
            if ((entityplayer != null))
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
    public boolean entitiesToIgnore(Entity entity)
    {

        return (super.entitiesToIgnore(entity) || (entity instanceof MoCEntityWyvern) || (entity instanceof EntityPlayer)
        );
    }
    
    @Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeEntityToNBT(nbttagcompound);
		nbttagcompound.setBoolean("Saddle", getIsRideable());
		nbttagcompound.setBoolean("Chested", getIsChested());
		nbttagcompound.setByte("ArmorType", getArmorType());
		nbttagcompound.setBoolean("isSitting", getIsSitting());
		if (getIsChested() && localchest != null)
		{
			NBTTagList nbttaglist = new NBTTagList();
			for (int i = 0; i < localchest.getSizeInventory(); i++)
			{
				localstack = localchest.getStackInSlot(i);
				if (localstack != null)
				{
					NBTTagCompound nbttagcompound1 = new NBTTagCompound();
					nbttagcompound1.setByte("Slot", (byte) i);
					localstack.writeToNBT(nbttagcompound1);
					nbttaglist.appendTag(nbttagcompound1);
				}
			}
			nbttagcompound.setTag("Items", nbttaglist);
		}

	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readEntityFromNBT(nbttagcompound);
		setRideable(nbttagcompound.getBoolean("Saddle"));
		setIsChested(nbttagcompound.getBoolean("Chested"));
		setArmorType(nbttagcompound.getByte("ArmorType"));
		setSitting(nbttagcompound.getBoolean("isSitting"));
		if (getIsChested())
		{
			NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
			localchest = new MoCAnimalChest("WyvernChest", 14);
			for (int i = 0; i < nbttaglist.tagCount(); i++)
			{
				NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.tagAt(i);
				int j = nbttagcompound1.getByte("Slot") & 0xff;
				if ((j >= 0) && j < localchest.getSizeInventory())
				{
					localchest.setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(nbttagcompound1));
				}
			}
		}
	}
	
	@Override
    public double roperYOffset()
    {
        if (this.getIsAdult())
        {
            return 0D;
        }
        else
        {
            return (130 - getEdad()) * 0.01D;
        }

    }
	
	 @Override
	    public int nameYOffset()
	    {
		 int yOff = getEdad() * -1;
		 if (yOff < -120) yOff = -120;
		 return yOff;

	    }
	 
	 @Override
	    public boolean isMyHealFood(ItemStack par1ItemStack)
	    {
	        return par1ItemStack != null && (par1ItemStack.itemID == MoCreatures.ratRaw.itemID || par1ItemStack.itemID == MoCreatures.rawTurkey.itemID);
	    }
	 
	 @Override
	    public boolean getCanSpawnHere()
	    {
		 	return (MoCreatures.proxy.getFrequency(this.getEntityName()) > 0) && super.getCanSpawnHere();
	    }
	 
	
	 private void openMouth()
	    {
		 	if (MoCreatures.isServer())
		 	{
		 		mouthCounter = 1;
		 		MoCServerPacketHandler.sendAnimationPacket(this.entityId, this.worldObj.provider.dimensionId, 1);
		 	}
	        
	    }
	 
	 @Override
	    public void performAnimation(int animationType)
	    {
	        /*if (animationType == 0) //tail animation
	        {
	            //setPoisoning(true);
	        }*/
	        if (animationType == 1) //opening mouth
	        {
	            mouthCounter = 1;
	        }
	        if (animationType == 2) //diving mount
	        {
	            diveCounter = 1;
	        }
	    }
	 
	 @Override
		public void makeEntityDive()
		{
			//diveCounter = 1;
			MoCServerPacketHandler.sendAnimationPacket(this.entityId, this.worldObj.provider.dimensionId, 2);
			super.makeEntityDive();
		}
	 
	 

	 
	 
	 @Override
	    protected void dropFewItems(boolean flag, int x)
	    {
		 	if (!flag) return;
		 	
		 	int i = MathHelper.floor_double(posX);
			int j = MathHelper.floor_double(boundingBox.minY);
			int k = MathHelper.floor_double(posZ);
			int l = 10;
			if (getType() == 5) //mother wyverns drop eggs more frequently
			{
				l = 3;
			}
			String s = MoCTools.BiomeName(worldObj, i, j, k);
			if (s.equals("WyvernBiome") && rand.nextInt(l) == 0) 
			{
				//int m = rand.nextInt(2)+1;
                //for (int l = 0; l < m; l++)
                //{
                    entityDropItem(new ItemStack(MoCreatures.fishyegg, 1, getType() + 49), 0.0F);
                //}
			}
			
	       
	    }
	 
	 @Override
	    public boolean canBeCollidedWith()
	    {
	        return riddenByEntity == null;
	    }
	 
	 
	 @Override
	    public void dropMyStuff() 
	    {
	        if (MoCreatures.isServer())
	        {
	            dropArmor();
	            MoCTools.dropSaddle(this, worldObj);
	            
	            if (getIsChested())
	            {
	               MoCTools.dropInventory(this, localchest);
	               MoCTools.dropCustomItem(this, this.worldObj, new ItemStack(Block.chest, 1));
	               setIsChested(false);
	            }
	        }
	        
	    }
	 
	 @Override
		public float getAdjustedYOffset()
		{
		 	if (getIsSitting())
		 	{
		 		return 0.4F;
		 	}
			return 0F;
		}

	 	@Override
		public double getCustomSpeed()
		{
	 		if (riddenByEntity != null)
	 		{
	 			if (getType() < 5)
	 			{
	 				return 2.0D;
	 			}
	 			return 3.0D;
	 		}
			return 0.8D;
		}
	 	
	 	private int getMaxAge()
	 	{
	 		if (getType() >= 5)
	 		{
	 			//if (getType() == 5) return 230;
	 			
	 			return 180;
	 		}
	 		return 100;
	 	}
}
