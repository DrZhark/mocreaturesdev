package drzhark.mocreatures.entity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import drzhark.mocreatures.entity.item.MoCEntityKittyBed;
import drzhark.mocreatures.entity.item.MoCEntityLitterBox;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.network.MoCServerPacketHandler;

// import net.minecraft.src.forge.ISpawnHandler;

public abstract class MoCEntityAmbient extends EntityAnimal  implements MoCIMoCreature//, IEntityAdditionalSpawnData
{
	public MoCEntityAmbient(World world)
	{
		super(world);
		setTamed(false);
		setAdult(true);
		//selectType();
	}

	/**
	 * Put your code to choose a texture / the mob type in here. Will be called
	 * by default MocEntity constructors.
	 */
	@Override
	public void selectType()
	{
		setType(1);
	}

	@Override
    public void initCreature()
    {
    	selectType();
    	super.initCreature();
    }
	
	@Override
	public EntityAgeable createChild(EntityAgeable var1)
	{
		return null;
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(15, Byte.valueOf((byte) 0)); // isAdult - 0 false 1 true
		dataWatcher.addObject(16, Byte.valueOf((byte) 0)); // isTamed - 0 false 1 true
		dataWatcher.addObject(17, String.valueOf("")); // displayName empty string by default
		dataWatcher.addObject(18, Integer.valueOf(0)); // int ageTicks / "edad"
		dataWatcher.addObject(19, Integer.valueOf(0)); // int type
		dataWatcher.addObject(20, String.valueOf("")); //owners name
		
	}

	public void setType(int i)
	{
		dataWatcher.updateObject(19, Integer.valueOf(i));
	}

	@Override
	public int getType()
	{
		return dataWatcher.getWatchableObjectInt(19);
	}

	public void setDisplayName(boolean flag)
	{

	}

	public boolean getDisplayName()
	{
		return (getName() != null && !getName().equals(""));
	}

	@Override
	public boolean getIsAdult()
	{
		return (dataWatcher.getWatchableObjectByte(15) == 1);
	}

	@Override
	public boolean getIsTamed()
	{
		return (dataWatcher.getWatchableObjectByte(16) == 1);
	}

	@Override
	public String getName()
	{
		return this.dataWatcher.getWatchableObjectString(17);
	}

	/**
	 * @return networked Entity "Age" in integer value, typical values are
	 *         0-100. Old float eDad was typically 0F-1.0F
	 */
	public int getEdad()
	{
		return dataWatcher.getWatchableObjectInt(18);
	}

	

	public void setEdad(int i)
	{
		dataWatcher.updateObject(18, Integer.valueOf(i));
	}

	@Override
	public void setAdult(boolean flag)
	{
		byte input = (byte) (flag ? 1 : 0);
		dataWatcher.updateObject(15, Byte.valueOf(input));
	}

	@Override
	public void setName(String name)
	{
		dataWatcher.updateObject(17, String.valueOf(name));
	}

	@Override
	public void setTamed(boolean flag)
	{
		byte input = (byte) (flag ? 1 : 0);
		dataWatcher.updateObject(16, Byte.valueOf(input));

	}

	

	@Override
	protected boolean canDespawn()
	{
		return !getIsTamed();
	}

	/**
	 * called in getCanSpawnHere to make sure the right type of creature spawns
	 * in the right biome i.e. snakes, rays, bears, BigCats and later wolves,
	 * etc.
	 */
	@Override
	public boolean checkSpawningBiome()
	{
		return true;
	}

	
	/*protected EntityLiving getClosestEntityLiving(Entity entity, double d)
	{
		double d1 = -1D;
		EntityLiving entityliving = null;
		List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(d, d, d));
		for (int i = 0; i < list.size(); i++)
		{
			Entity entity1 = (Entity) list.get(i);

			if (entitiesToIgnore(entity1))
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
	}*/

	

	

	@Override
	public void onLivingUpdate()
	{
		
		if (isSwimming() && swimmerEntity())
		{
			floating();
		}

		

		moveSpeed = getMoveSpeed();

		super.onLivingUpdate();
	}

	
	public boolean swimmerEntity()
	{
		return false;
	}

	public boolean isSwimming()
	{
		return ((isInsideOfMaterial(Material.water)));
	}

	

	public void floating()
	{
		if (motionY < 0)
		{
			motionY = 0;
		}
		motionY += 0.001D;// 0.001

		int distY = (int) MoCTools.distanceToSurface(this);
		if (distY > 1)
		{
			motionY += (distY * 0.07);
		}

		if (hasPath() && isCollidedHorizontally)
		{
			jump();
		}
	}

	/*
	 * public boolean stuckInWater() { return
	 * ((isInsideOfMaterial(Material.water)) && !inWater && !hasPath()); }
	 */

	/**
	 * List of edible foods
	 * 
	 * @param item1
	 * @return
	 */
	public boolean isItemEdible(Item item1)
	{
		return (item1 instanceof ItemFood) || (item1 instanceof ItemSeeds) || item1.itemID == Item.wheat.itemID || item1.itemID == Item.sugar.itemID || item1.itemID == Item.cake.itemID || item1.itemID == Item.egg.itemID;
	}

	/**
	 * Used to breed
	 * 
	 * @param item1
	 * @return
	 */
	public boolean isMyAphrodisiac(Item item1)
	{
		return false;//(item1 instanceof ItemFood);
	}

	

	//used to drop armor, inventory, saddles, etc.
	public void dropMyStuff() {}

	/**
	 * Used to heal the animal
	 * 
	 * @param itemstack
	 * @return
	 */
	protected boolean isMyHealFood(ItemStack itemstack)
	{
		return false;
	}

	@Override
	public boolean isInWater()
	{
		if (swimmerEntity()) { return false; }
		return super.isInWater();
	}

	@Override
	public boolean canBreatheUnderwater()
	{
		return swimmerEntity();
	}

	public EntityItem getClosestItem(Entity entity, double d, int i, int j)
	{
		double d1 = -1D;
		EntityItem entityitem = null;
		List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(d, d, d));
		for (int k = 0; k < list.size(); k++)
		{
			Entity entity1 = (Entity) list.get(k);
			if (!(entity1 instanceof EntityItem))
			{
				continue;
			}
			EntityItem entityitem1 = (EntityItem) entity1;
			if ((entityitem1.getEntityItem().itemID != i) && (entityitem1.getEntityItem().itemID != j))
			{
				continue;
			}
			double d2 = entityitem1.getDistanceSq(entity.posX, entity.posY, entity.posZ);
			if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1)))
			{
				d1 = d2;
				entityitem = entityitem1;
			}
		}

		return entityitem;
	}

	public EntityItem getClosestEntityItem(Entity entity, double d)
	{
		double d1 = -1D;
		EntityItem entityitem = null;
		List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(d, d, d));
		for (int k = 0; k < list.size(); k++)
		{
			Entity entity1 = (Entity) list.get(k);
			if (!(entity1 instanceof EntityItem))
			{
				continue;
			}
			EntityItem entityitem1 = (EntityItem) entity1;
			double d2 = entityitem1.getDistanceSq(entity.posX, entity.posY, entity.posZ);
			if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1)))
			{
				d1 = d2;
				entityitem = entityitem1;
			}
		}

		return entityitem;
	}

	public EntityItem getClosestFood(Entity entity, double d)
	{
		double d1 = -1D;
		EntityItem entityitem = null;
		List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(d, d, d));
		for (int k = 0; k < list.size(); k++)
		{
			Entity entity1 = (Entity) list.get(k);
			if (!(entity1 instanceof EntityItem))
			{
				continue;
			}
			EntityItem entityitem1 = (EntityItem) entity1;
			if (!isItemEdible(entityitem1.getEntityItem().getItem()))
			{
				continue;
			}
			double d2 = entityitem1.getDistanceSq(entity.posX, entity.posY, entity.posZ);
			if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1)))
			{
				d1 = d2;
				entityitem = entityitem1;
			}
		}

		return entityitem;
	}

	public void faceLocation(int i, int j, int k, float f)
	{
		double var4 = i + 0.5D - posX;
		double var8 = k + 0.5D - posZ;
		double var6 = j + 0.5D - posY;
		double var14 = (double) MathHelper.sqrt_double(var4 * var4 + var8 * var8);
		float var12 = (float) (Math.atan2(var8, var4) * 180.0D / Math.PI) - 90.0F;
		float var13 = (float) (-(Math.atan2(var6, var14) * 180.0D / Math.PI));
		this.rotationPitch = -this.updateRotation(this.rotationPitch, var13, f);
		this.rotationYaw = this.updateRotation(this.rotationYaw, var12, f);
	}

	/**
	 * Arguments: current rotation, intended rotation, max increment.
	 */
	private float updateRotation(float par1, float par2, float par3)
	{
		float var4;

		for (var4 = par2 - par1; var4 < -180.0F; var4 += 360.0F)
		{
			;
		}

		while (var4 >= 180.0F)
		{
			var4 -= 360.0F;
		}

		if (var4 > par3)
		{
			var4 = par3;
		}

		if (var4 < -par3)
		{
			var4 = -par3;
		}

		return par1 + var4;
	}

	public void getMyOwnPath(Entity entity, float f)
	{
		PathEntity pathentity = worldObj.getPathEntityToEntity(this, entity, 16F, true, false, false, true);
		if (pathentity != null)
		{
			setPathToEntity(pathentity);
		}
	}

	/**
	 * Called to make ridden entities pass on collision to rider
	 */
	public void Riding()
	{
		if ((riddenByEntity != null) && (riddenByEntity instanceof EntityPlayer))
		{
			EntityPlayer entityplayer = (EntityPlayer) riddenByEntity;
			List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(1.0D, 0.0D, 1.0D));
			if (list != null)
			{
				for (int i = 0; i < list.size(); i++)
				{
					Entity entity = (Entity) list.get(i);
					if (entity.isDead)
					{
						continue;
					}
					entity.onCollideWithPlayer(entityplayer);
					if (!(entity instanceof EntityMob))
					{
						continue;
					}
					float f = getDistanceToEntity(entity);
					if ((f < 2.0F) && (rand.nextInt(10) == 0))
					{
						//TODO 4FIX
						//attackEntityFrom(DamageSource.causeMobDamage((EntityLiving) entity),((EntityMob) entity).attackStrength);
					}
				}

			}
			if (entityplayer.isSneaking())
			{
				if (!worldObj.isRemote)
				{
					entityplayer.mountEntity(null);
				}
			}

		}
	}

	protected void getPathOrWalkableBlock(Entity entity, float f)
	{
		PathEntity pathentity = worldObj.getPathEntityToEntity(this, entity, 16F, true, false, false, true);
		if ((pathentity == null) && (f > 8F))
		{
			int i = MathHelper.floor_double(entity.posX) - 2;
			int j = MathHelper.floor_double(entity.posZ) - 2;
			int k = MathHelper.floor_double(entity.boundingBox.minY);
			for (int l = 0; l <= 4; l++)
			{
				for (int i1 = 0; i1 <= 4; i1++)
				{
					if (((l < 1) || (i1 < 1) || (l > 3) || (i1 > 3)) && worldObj.isBlockNormalCube(i + l, k - 1, j + i1) && !worldObj.isBlockNormalCube(i + l, k, j + i1) && !worldObj.isBlockNormalCube(i + l, k + 1, j + i1))
					{
						setLocationAndAngles((i + l) + 0.5F, k, (j + i1) + 0.5F, rotationYaw, rotationPitch);
						return;
					}
				}

			}

		}
		else
		{
			setPathToEntity(pathentity);
		}
	}

	@Override
	public int getMaxHealth()
	{
		return 20;
	}

	

	public boolean getCanSpawnHereAnimal()
	{
		int i = MathHelper.floor_double(posX);
		int j = MathHelper.floor_double(boundingBox.minY);
		int k = MathHelper.floor_double(posZ);
		return worldObj.getBlockId(i, j - 1, k) == Block.grass.blockID && worldObj.getFullBlockLightValue(i, j, k) > 8;
	}

	public boolean getCanSpawnHereCreature()
	{
		int i = MathHelper.floor_double(posX);
		int j = MathHelper.floor_double(boundingBox.minY);
		int k = MathHelper.floor_double(posZ);
		return getBlockPathWeight(i, j, k) >= 0.0F;
	}

	public boolean getCanSpawnHereLiving()
	{
		return worldObj.checkNoEntityCollision(boundingBox) && worldObj.getCollidingBoundingBoxes(this, boundingBox).size() == 0 && !worldObj.isAnyLiquid(boundingBox);
	}

	public boolean getCanSpawnHereAquatic()
	{
		return worldObj.checkNoEntityCollision(boundingBox);
	}

	@Override
	public boolean getCanSpawnHere()
	{
		int i = MathHelper.floor_double(posX);
		int j = MathHelper.floor_double(boundingBox.minY);
		int k = MathHelper.floor_double(posZ);

		String s = MoCTools.BiomeName(worldObj, i, j, k);
		//System.out.println("checkin animal getcanspawnhere and biome = " + s);
		if (s.equals("Jungle") || s.equals("JungleHills")) { return getCanSpawnHereJungle(); }

		return super.getCanSpawnHere();
	}

	public boolean getCanSpawnHereJungle()
	{

		//System.out.println("checking jungle!");
		if (this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox))
		{
			int var1 = MathHelper.floor_double(this.posX);
			int var2 = MathHelper.floor_double(this.boundingBox.minY);
			int var3 = MathHelper.floor_double(this.posZ);

			if (var2 < 63) { return false; }

			int var4 = this.worldObj.getBlockId(var1, var2 - 1, var3);
			Block block = Block.blocksList[var4];

			if (var4 == Block.grass.blockID || var4 == Block.leaves.blockID || (block != null && block.isLeaves(worldObj, var1, var2 - 1, var3))) { return true; }
		}

		return false;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeEntityToNBT(nbttagcompound);
		nbttagcompound.setBoolean("Tamed", getIsTamed());
		nbttagcompound.setBoolean("Adult", getIsAdult());
		nbttagcompound.setInteger("Edad", getEdad());
		nbttagcompound.setString("Name", getName());
		nbttagcompound.setInteger("TypeInt", getType());
		nbttagcompound.setString("Owner", getOwnerName());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readEntityFromNBT(nbttagcompound);
		setTamed(nbttagcompound.getBoolean("Tamed"));
		setAdult(nbttagcompound.getBoolean("Adult"));
		setEdad(nbttagcompound.getInteger("Edad"));
		setName(nbttagcompound.getString("Name"));
		setType(nbttagcompound.getInteger("TypeInt"));
		setOwner(nbttagcompound.getString("Owner"));
		//type = (nbttagcompound.getInteger("TypeInt"));
		//selectType();
	}

	@Override
	public void moveEntityWithHeading(float f, float f1)
	{
		//If the entity is not ridden by entityplayer, then execute the normal Entityliving code
		if (!isFlyer() && (!rideableEntity() || this.riddenByEntity == null))// || (this.ridingEntity != null && !(this.ridingEntity instanceof EntityPlayer))) 
		{
			super.moveEntityWithHeading(f, f1);
			return;
		}

		if (handleWaterMovement())
		{
			if (riddenByEntity != null)
			{
				motionX += riddenByEntity.motionX * (getCustomSpeed() / 2.0D);
				motionZ += riddenByEntity.motionZ * (getCustomSpeed() / 2.0D);
				
				if (!worldObj.isRemote)
				{
					moveEntity(motionX, motionY, motionZ);
				}
				//moveEntity(motionX, motionY, motionZ);
				rotationPitch = riddenByEntity.rotationPitch * 0.5F;
				if (rand.nextInt(20) == 0)
				{
					rotationYaw = riddenByEntity.rotationYaw;
				}
				setRotation(rotationYaw, rotationPitch);

				if (MoCreatures.isServer() && !getIsTamed())
				{
					worldObj.playSoundAtEntity(this, getMadSound(), 1.0F, 1.0F + ((rand.nextFloat() - rand.nextFloat()) * 0.2F));
					riddenByEntity.motionY += 0.3D;
					riddenByEntity.motionZ -= 0.3D;
					riddenByEntity.mountEntity(null);
					this.riddenByEntity = null;
				}

				
			}
			double d = posY;
			if (!worldObj.isRemote)
			{
				moveFlying(f, f1, 0.02F);
				moveEntity(motionX, motionY, motionZ);
			}
			motionX *= 0.800000011920929D;
			motionY *= 0.800000011920929D;
			motionZ *= 0.800000011920929D;
			motionY -= 0.02D;
			if (isCollidedHorizontally && isOffsetPositionInLiquid(motionX, ((motionY + 0.60000002384185791D) - posY) + d, motionZ))
			{
				motionY = 0.30000001192092901D;
			}
		}
		else if (handleLavaMovement())
		{
			if (riddenByEntity != null)
			{
				motionX += riddenByEntity.motionX * (getCustomSpeed() / 2.0D);
				motionZ += riddenByEntity.motionZ * (getCustomSpeed() / 2.0D);
				
				moveEntity(motionX, motionY, motionZ);

				//moveEntity(motionX, motionY, motionZ);
				rotationPitch = riddenByEntity.rotationPitch * 0.5F;
				if (rand.nextInt(20) == 0)
				{
					rotationYaw = riddenByEntity.rotationYaw;
				}
				setRotation(rotationYaw, rotationPitch);
				if (MoCreatures.isServer() && !getIsTamed())
				{
					worldObj.playSoundAtEntity(this, getMadSound(), 1.0F, 1.0F + ((rand.nextFloat() - rand.nextFloat()) * 0.2F));
					riddenByEntity.motionY += 0.3D;
					riddenByEntity.motionZ -= 0.3D;
					riddenByEntity.mountEntity(null);
					this.riddenByEntity = null;
				}
			}
			double d1 = posY;

			moveFlying(f, f1, 0.02F);
			moveEntity(motionX, motionY, motionZ);

			motionX *= 0.5D;
			motionY *= 0.5D;
			motionZ *= 0.5D;
			motionY -= 0.02D;
			if (isCollidedHorizontally && isOffsetPositionInLiquid(motionX, ((motionY + 0.60000002384185791D) - posY) + d1, motionZ))
			{
				motionY = 0.30000001192092901D;
			}
		}
		else
		{
			float f2 = 0.91F;
			if (onGround)
			{
			    //this.isAirBorne = false;
				f2 = 0.5460001F;
				int i = worldObj.getBlockId(MathHelper.floor_double(posX), MathHelper.floor_double(boundingBox.minY) - 1, MathHelper.floor_double(posZ));
				if (i > 0)
				{
					f2 = Block.blocksList[i].slipperiness * 0.91F;
				}
			}

			
			float f3 = 0.162771F / (f2 * f2 * f2);
			moveFlying(f, f1, onGround ? 0.1F * f3 : 0.02F);
			

			if (isOnLadder())
			{
				fallDistance = 0.0F;
				if (motionY < -0.15D)
				{
					motionY = -0.15D;
				}
			}
			if ((riddenByEntity != null) && !getIsTamed())
			{
				
				if (rand.nextInt(10) == 0)
				{
					motionX += rand.nextDouble() / 30D;
					motionZ += rand.nextDouble() / 10D;
				}
				// blood - This must be run on server side only since it causes glitch/twitch if run on both sides.
				if (!worldObj.isRemote)
				{
					moveEntity(motionX, motionY, motionZ);
				}
				if (MoCreatures.isServer() && rand.nextInt(50) == 0)
				{
					worldObj.playSoundAtEntity(this, getMadSound(), 1.0F, 1.0F + ((rand.nextFloat() - rand.nextFloat()) * 0.2F));
					riddenByEntity.motionY += 0.9D;
					riddenByEntity.motionZ -= 0.3D;
					riddenByEntity.mountEntity(null);
					this.riddenByEntity = null;
				}
			}
			if ((riddenByEntity != null) && getIsTamed())
			{
				boundingBox.maxY = riddenByEntity.boundingBox.maxY;
				if (!selfPropelledFlyer() || (selfPropelledFlyer() && !isOnAir()))
				{
				    motionX += riddenByEntity.motionX * getCustomSpeed();
	                motionZ += riddenByEntity.motionZ * getCustomSpeed();
				}
				

				

				// blood - This must be run on server side only since it causes glitch/twitch if run on both sides.
				if (MoCreatures.isServer())
				{
					moveEntity(motionX, motionY, motionZ);
				}
				
				prevRotationYaw = rotationYaw = riddenByEntity.rotationYaw;
				rotationPitch = riddenByEntity.rotationPitch * 0.5F;
				setRotation(rotationYaw, rotationPitch);
			}
			// blood - This must be run on server side only since it causes glitch/twitch if run on both sides.
			if (!worldObj.isRemote)
			{
				//needs to be left in so flying mounts can be controlled
				moveEntity(motionX, motionY, motionZ);
			}
			if (isFlyingAlone())
			{
				int distY = MoCTools.distanceToFloor(this);
				if (distY <= flyingHeight())
				{
					motionY *= f2;
				}
				if (distY <= flyingHeight() && (isCollidedHorizontally || rand.nextInt(100) == 0))
				{
					motionY += 0.1D;
				}
				if (distY > flyingHeight() || rand.nextInt(150) == 0)
				{
					motionY -= 0.10D;
				}
				
				if (isOnAir())
				{
				    double velX = 0.05F * Math.cos((MoCTools.realAngle(this.rotationYaw - 90F)) / 57.29578F);
                    double velZ = 0.05F * Math.sin((MoCTools.realAngle(this.rotationYaw - 90F)) / 57.29578F);
                    this.motionX -= velX;
                    this.motionZ -= velZ;
				}
			}

			//float f = MoCTools.distanceToFloor(this);
			/*if (isFlyerInsect() && isCollidedHorizontally && MoCTools.distanceToFloor(this) <= 5)
			{
				motionY = 0.2D;
			}
			
			if (isFlyerInsect() && MoCTools.distanceToFloor(this) >= 5)
			{
				motionY = -0.5D;
			}
			
			if (!isFlyerInsect() && isCollidedHorizontally && isOnLadder())
			{
				motionY = 0.2D;
			}*/

			if (isFlyer() && riddenByEntity == null && entityToAttack != null && entityToAttack.posY < this.posY && rand.nextInt(30) == 0)
			{
				motionY = -0.25D;
			}

			if (isFlyer() && (riddenByEntity != null) && getIsTamed())
			{
				motionY -= 0.08D;
				motionY *= myFallSpeed();//0.6D;
			}
			else if (!isFlyingAlone())
			{
				motionY -= 0.08D;
				motionY *= 0.98000001907348633D;
			}
			
			if (this.riddenByEntity != null && isOnAir())
			{
			    f2 = flyerFriction();
			    
			}
			//f2 = 0.95F;
			motionX *= f2;
			motionZ *= f2;
		}
        this.prevLimbYaw = this.limbYaw;
        double d2 = posX - prevPosX;
        double d3 = posZ - prevPosZ;
        float f4 = MathHelper.sqrt_double((d2 * d2) + (d3 * d3)) * 4.0F;
        if (f4 > 1.0F)
        {
            f4 = 1.0F;
        }

        this.limbYaw += (f4 - this.limbYaw) * 0.4F;
        this.limbSwing += this.limbYaw;
	}

	/**
	 * Maximum flyer height when moving autonomously
	 * @return
	 */
	public int flyingHeight()
	{
		return 5;
	}
	
	/**
	 * Used for flyer mounts, to calculate fall speed
	 * @return
	 */
	protected double myFallSpeed()
	{
	    return 0.6D;
	}
	
	/**
     * flyer mounts Y thrust
     * @return
     */
	protected double flyerThrust()
    {
        return 0.3D;
    }
	
	/**
     * flyer deceleration on Z and X axis
     * @return
     */
	protected float flyerFriction()
    {
        return 0.91F;
    }

    /**
     * Alternative flyer mount movement, when true, the player only controls frequency of wing flaps
     * @return
     */
    protected boolean selfPropelledFlyer()
    {
        return false;
    }
	/**
	 * Sets a flag that will make the Entity "jump" in the next onGround
	 * moveEntity update
	 */
	@Override
	public void makeEntityJump()
	{
	}

	/**
	 * Boolean used for flying mounts
	 */
	public boolean isFlyer()
	{
		return false;
	}

	

	

	/**
	 * How difficult is the creature to be tamed? the Higher the number, the
	 * more difficult
	 */
	public int getMaxTemper()
	{
		return 100;
	}

	/**
	 * mount speed
	 */
	public double getCustomSpeed()
	{
		return 0.8D;
	}

	/**
	 * mount jumping power
	 */
	public double getCustomJump()
	{
		return 0.4D;
	}

	/**
	 * sound played when an untamed mount buckles rider
	 */
	protected String getMadSound()
	{
		return null;
	}

	
	@Override
	public void makeEntityDive() {}
	
	
	/**
	 * Is this a rideable entity?
	 */
	public boolean rideableEntity()
	{
		return false;
	}

	/**
	 * the type can be forced and the selecType() method is called to adjust
	 * parameters
	 * 
	 */
	/*public void setTypeInt(int i)
	{
		setType(i);// = i;
		selectType();
	}*/

	@Override
	public boolean renderName()
	{
		return getDisplayName() && (riddenByEntity == null);
	}

	@Override
	public int nameYOffset()
	{
		return -80;
	}

	@Override
	public double roperYOffset()
	{
		return 0D;
	}

	
	
	/**
	 * fixes bug with entities following a player carrying wheat
	 */
	@Override
	protected Entity findPlayerToAttack()
	{
		return null;
	}

	public void repelMobs(Entity entity1, Double dist, World worldObj)
	{
		List list = worldObj.getEntitiesWithinAABBExcludingEntity(entity1, entity1.boundingBox.expand(dist, 4D, dist));
		for (int i = 0; i < list.size(); i++)
		{
			Entity entity = (Entity) list.get(i);
			if (!(entity instanceof EntityMob))
			{
				continue;
			}
			EntityMob entitymob = (EntityMob) entity;
			entitymob.setAttackTarget(null);
			entitymob.setPathToEntity(null);
		}
	}

	public void faceItem(int i, int j, int k, float f)
	{
		double d = i - posX;
		double d1 = k - posZ;
		double d2 = j - posY;
		double d3 = MathHelper.sqrt_double((d * d) + (d1 * d1));
		float f1 = (float) ((Math.atan2(d1, d) * 180D) / 3.1415927410125728D) - 90F;
		float f2 = (float) ((Math.atan2(d2, d3) * 180D) / 3.1415927410125728D);
		rotationPitch = -adjustRotation(rotationPitch, f2, f);
		rotationYaw = adjustRotation(rotationYaw, f1, f);
	}

	public float adjustRotation(float f, float f1, float f2)
	{
		float f3 = f1;
		for (f3 = f1 - f; f3 < -180F; f3 += 360F)
		{
		}
		for (; f3 >= 180F; f3 -= 360F)
		{
		}
		if (f3 > f2)
		{
			f3 = f2;
		}
		if (f3 < -f2)
		{
			f3 = -f2;
		}
		return f + f3;
	}

	
	public boolean isFlyingAlone()
	{
		return false;
	}

	public float getMoveSpeed()
	{
		return 0.7F;
	}

	

	/**
	 * Used to synchronize animations between server and client
	 * 
	 * @param attackType
	 */
	@Override
	public void performAnimation(int attackType)
	{
	}

	

	/**
	 * Used to follow the player carrying the item
	 * 
	 * @param par1ItemStack
	 * @return
	 */
	public boolean isMyFavoriteFood(ItemStack par1ItemStack)
	{
		return false;
		//return par1ItemStack.itemID == Item.wheat.itemID;
	}

	private void followPlayer()
	{
		EntityPlayer entityplayer1 = worldObj.getClosestPlayerToEntity(this, 24D);
		if (entityplayer1 == null) { return; }

		ItemStack itemstack1 = entityplayer1.inventory.getCurrentItem();
		if (itemstack1 != null && isMyFavoriteFood(itemstack1))
		{
			PathEntity pathentity = worldObj.getPathEntityToEntity(this, entityplayer1, 16F, true, false, false, true);
			setPathToEntity(pathentity);
		}
	}

	

	@Override
	public Entity getRoper()
	{
		return null;
	}

	@Override
	public boolean updateMount()
	{
		return false;
	}

	@Override
	public boolean forceUpdates()
	{
		return false;
	}

	
	public boolean isOnAir()
	{
		int j = worldObj.getBlockId(MathHelper.floor_double(posX), MathHelper.floor_double(posY - 0.2D), MathHelper.floor_double(posZ));
		int k = worldObj.getBlockId(MathHelper.floor_double(posX), MathHelper.floor_double(posY - 1.2D), MathHelper.floor_double(posZ));
		return (j == 0) && (k == 0);

	}

	@Override
	public float getSizeFactor()
	{
		return 1.0F;
	}

	@Override
	public float getAdjustedYOffset()
	{
		return 0F;
	}

	@Override
	public String getOwnerName()
	{
		return this.dataWatcher.getWatchableObjectString(20);
	}

	@Override
	public void setOwner(String par1Str)
	{
		this.dataWatcher.updateObject(20, par1Str);
	}

	/*public EntityLiving getOwner()
	{
		return this.worldObj.getPlayerEntityByName(this.getOwnerName());
	}*/

	@Override
	public void onDeath(DamageSource damagesource)
	{
		if (MoCreatures.proxy.enableOwnership && this.getIsTamed() && (this.getOwnerName() != null) && MoCreatures.isServer())
		{
			EntityPlayer ep = worldObj.getPlayerEntityByName(this.getOwnerName());
			if (ep != null)
			{
				MoCTools.reduceTamedByPlayer(ep);
			}
			else
			{
				MoCTools.reduceTamedByOfflinePlayer(getOwnerName());
			}
		}

		super.onDeath(damagesource);
	}

	@Override
	public boolean attackEntityFrom(DamageSource damagesource, int i)
	{
		Entity entity = damagesource.getEntity();
		//this avoids damage done by Players to a tamed creature that is not theirs
		if (MoCreatures.proxy.enableOwnership && getOwnerName() != null && !getOwnerName().equals("") && entity != null && entity instanceof EntityPlayer && !((EntityPlayer) entity).username.equals(getOwnerName())) { return false; }

		if (MoCreatures.isServer() && getIsTamed())
		{
			MoCServerPacketHandler.sendHealth(this.entityId, this.worldObj.provider.dimensionId, this.getHealth());
		}
		/*if (isNotScared())
		{
			Entity tempEntity = entityToAttack;
			boolean flag = super.attackEntityFrom(damagesource, i);
			fleeingTick = 0;
			entityToAttack = tempEntity;
			return flag;
		}
		else*/
		{
			return super.attackEntityFrom(damagesource, i);
		}
	}

    public boolean getIsRideable() 
    {    
        return false;
    }

    public void setRideable(boolean b) {}

    protected EntityLiving getClosestEntityLiving(Entity entity, double d)
	{
		double d1 = -1D;
		EntityLiving entityliving = null;
		List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(d, d, d));
		for (int i = 0; i < list.size(); i++)
		{
			Entity entity1 = (Entity) list.get(i);

			if (entitiesToIgnore(entity1))
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
    
    public boolean entitiesToIgnore(Entity entity)
	{

		return ((!(entity instanceof EntityLiving)) 
		        || (entity instanceof EntityMob) 
		        || (entity instanceof EntityPlayer && this.getIsTamed()) 
		        || (entity instanceof MoCEntityKittyBed) 
		        || (entity instanceof MoCEntityLitterBox) 
		        || (this.getIsTamed() && (entity instanceof MoCEntityAnimal && ((MoCEntityAnimal) entity).getIsTamed())) 
		        || ((entity instanceof EntityWolf) && !(MoCreatures.proxy.attackWolves)) 
		        || ((entity instanceof MoCEntityHorse) && !(MoCreatures.proxy.attackHorses)) 
		        || (entity.width > this.width && entity.height > this.height)
		        || (entity instanceof MoCEntityEgg)

		);
	}
    
    @Override
    public void setArmorType(byte i) {}
    
    @Override
    public void dismountEntity() {}
    
    /**
	 * Finds and entity described in entitiesToInclude at d distance
	 * 
	 * @param d
	 * @return
	 */
	protected EntityLiving getBoogey(double d)
	{
		double d1 = -1D;
		EntityLiving entityliving = null;
		List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(d, 4D, d));
		for (int i = 0; i < list.size(); i++)
		{
			Entity entity = (Entity) list.get(i);
			if (entitiesToInclude(entity))
			{
				entityliving = (EntityLiving) entity;
			}
		}
		return entityliving;
	}
	
	/**
	 * Used in getBoogey to specify what kind of entity to look for
	 * 
	 * @param entity
	 * @return
	 */
	public boolean entitiesToInclude(Entity entity)
	{
		return ((entity instanceof EntityLiving) && ((entity.width >= 0.5D) || (entity.height >= 0.5D))

		);
	}
	
	public void runLikeHell(Entity entity)
	{
		double d = posX - entity.posX;
		double d1 = posZ - entity.posZ;
		double d2 = Math.atan2(d, d1);
		d2 += (rand.nextFloat() - rand.nextFloat()) * 0.75D;
		double d3 = posX + (Math.sin(d2) * 8D);
		double d4 = posZ + (Math.cos(d2) * 8D);
		int i = MathHelper.floor_double(d3);
		int j = MathHelper.floor_double(boundingBox.minY);
		int k = MathHelper.floor_double(d4);
		int l = 0;
		do
		{
			if (l >= 16)
			{
				break;
			}
			int i1 = (i + rand.nextInt(4)) - rand.nextInt(4);
			int j1 = (j + rand.nextInt(3)) - rand.nextInt(3);
			int k1 = (k + rand.nextInt(4)) - rand.nextInt(4);
			if ((j1 > 4) && ((worldObj.getBlockId(i1, j1, k1) == 0) || (worldObj.getBlockId(i1, j1, k1) == Block.snow.blockID)) && (worldObj.getBlockId(i1, j1 - 1, k1) != 0))
			{
				PathEntity pathentity = worldObj.getEntityPathToXYZ(this, i1, j1, k1, 16F, true, false, false, true);
				setPathToEntity(pathentity);
				break;
			}
			l++;
		} while (true);
	}
}
