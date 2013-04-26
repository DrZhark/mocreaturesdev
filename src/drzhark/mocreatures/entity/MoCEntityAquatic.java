package drzhark.mocreatures.entity;

import java.util.List;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.network.MoCServerPacketHandler;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class MoCEntityAquatic extends EntityWaterMob implements MoCIMoCreature//, IEntityAdditionalSpawnData
{
    protected boolean divePending;
    protected boolean jumpPending;
    protected boolean isEntityJumping;
    private PathEntity pathEntity;
    private int outOfWater;
    private int maxHealth;
    private boolean diving;
    private int divingCount;
    private int mountCount;
    public EntityLiving roper;

    public MoCEntityAquatic(World world)
    {
        super(world);
        outOfWater = 0;
        setTamed(false);
        setTemper(50);
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
    /**
     * Forge methods to send the type int to client from server
     */
    /*
    @Override
    public void writeSpawnData(ByteArrayDataOutput data)
    {
        data.writeInt(type);
        
    }

    @Override
    public void readSpawnData(ByteArrayDataInput data)
    {
        type = data.readInt();
        selectType();
    }
     */

    @Override
    protected void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(15, Integer.valueOf(50)); // int temper
        dataWatcher.addObject(16, Byte.valueOf((byte) 0)); // byte IsTamed, 0 = false 1 = true
        dataWatcher.addObject(17, String.valueOf("")); // displayName empty string by default
        dataWatcher.addObject(18, Byte.valueOf((byte) 0)); // byte IsAdult, 0 = false 1 = true
        dataWatcher.addObject(19, Integer.valueOf(0)); // int ageTicks / "edad"
        dataWatcher.addObject(20, Integer.valueOf(0)); // integer type - will be automatically checked and networked in onUpdate-EntityLiving
        dataWatcher.addObject(21, String.valueOf("")); //owners name
    }

    public int getTemper()
    {
        return dataWatcher.getWatchableObjectInt(15);
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

    public boolean getDisplayName()
    {
        return (getName() != null && !getName().equals(""));
    }

    @Override
    public boolean getIsAdult()
    {
        return (dataWatcher.getWatchableObjectByte(18) == 1);
    }

    /**
     * @return networked Entity "Age" in integer value, typical values are
     *         0-100. Old float eDad was typically 0F-1.0F
     */
    public int getEdad()
    {
        return dataWatcher.getWatchableObjectInt(19);
    }

    @Override
    public int getType()
    {
        return dataWatcher.getWatchableObjectInt(20);
    }

    public void setTemper(int i)
    {
        if (!MoCreatures.isServer()) { return; }
        dataWatcher.updateObject(15, Integer.valueOf(i));
    }

    @Override
    public void setTamed(boolean flag)
    {
        if (!MoCreatures.isServer()) { return; }
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(16, Byte.valueOf(input));
    }

    @Override
    public void setName(String name)
    {
        if (!MoCreatures.isServer()) { return; }
        dataWatcher.updateObject(17, String.valueOf(name));
    }

    @Override
    public void setAdult(boolean flag)
    {
        //if (!MoCreatures.isServer()) return;
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(18, Byte.valueOf(input));
    }

    public void setEdad(int i)
    {
        //if (!MoCreatures.isServer()) return;
        dataWatcher.updateObject(19, Integer.valueOf(i));
    }

    public void setType(int i)
    {
        if (!MoCreatures.isServer()) { return; }
        dataWatcher.updateObject(20, Integer.valueOf(i));
    }

    /**
     * How difficult is the creature to be tamed? the Higher the number, the
     * more difficult
     */
    public int getMaxTemper()
    {
        return 100;
    }

    public float b(float f, float f1, float f2)
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

    public void faceItem(int i, int j, int k, float f)
    {
        //System.out.println("faceItem " + this.getName());
        double d = i - posX;
        double d1 = k - posZ;
        double d2 = j - posY;
        double d3 = MathHelper.sqrt_double((d * d) + (d1 * d1));
        float f1 = (float) ((Math.atan2(d1, d) * 180D) / 3.1415927410125728D) - 90F;
        float f2 = (float) ((Math.atan2(d2, d3) * 180D) / 3.1415927410125728D);
        rotationPitch = -b(rotationPitch, f2, f);
        rotationYaw = b(rotationYaw, f1, f);
    }

    @Override
    protected boolean canDespawn()
    {
        return !getIsTamed();
    }

    @Override
    public boolean checkSpawningBiome()
    {
        return true;
    }

    @Override
    protected void playStepSound(int par1, int par2, int par3, int par4)
    {
        // TODO make the sounds

    }

    /*@Override
    protected void fall(float f)
    {
        if (!isInWater())
        {
            super.fall(f);
        }
    }*/

    @Override
    protected void fall(float f)
    {
    }

    public EntityItem getClosestFish(Entity entity, double d)
    {
        double d1 = -1D;
        EntityItem entityitem = null;
        List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(d, d, d));
        for (int i = 0; i < list.size(); i++)
        {
            Entity entity1 = (Entity) list.get(i);
            if (!(entity1 instanceof EntityItem))
            {
                continue;
            }
            EntityItem entityitem1 = (EntityItem) entity1;
            if ((entityitem1.getEntityItem().itemID != Item.fishRaw.itemID) || !entityitem1.isInWater())
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

    @Override
    public int getMaxHealth()
    {
        return maxHealth;
    }

    public void setMaxHealth(int i)
    {
        maxHealth = i;
    }

    @Override
    protected String getDeathSound()
    {
        return null;
    }

    @Override
    protected String getHurtSound()
    {
        return null;
    }

    @Override
    protected String getLivingSound()
    {
        return null;
    }

    @Override
    protected float getSoundVolume()
    {
        return 0.4F;
    }

    public boolean gettingOutOfWater()
    {
        //System.out.println("gettingOutOfWater() " + this.getName());
        int i = (int) posX;
        int j = (int) posY;
        int k = (int) posZ;
        int l = 1;
        l = worldObj.getBlockId(i, j + 1, k);
        return l == 0;
    }

    protected String getUpsetSound()
    {
        return null;
    }

    /**
     * mount jumping power
     */
    public double getCustomJump()
    {
        return 0.4D;
    }

    public void setIsJumping(boolean flag)
    {
        isEntityJumping = flag;
    }

    public boolean getIsJumping()
    {
        return isEntityJumping;
    }

    /**
     * Sets a flag that will make the Entity "jump" in the next onGround
     * moveEntity update
     */
    @Override
    public void makeEntityJump()
    {
        /*if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
        {
            //System.out.println("Server jumping entity = " + this.entityId);
        }else
        {
            //System.out.println("Client jumping entity = " + this.entityId);
        }*/
        //System.out.println("makeEntityJump() " + this.toString() + " getIsJumping() = " + getIsJumping());
        this.jumpPending = true;
    }

    @Override
    public boolean handleWaterMovement()
    {
        return worldObj.handleMaterialAcceleration(boundingBox, Material.water, this);
    }

    @Override
    public void moveEntityWithHeading(float f, float f1)
    {
        if (riddenByEntity == null)
        {
            //System.out.println(this.getName() + " riddenByEntity is NULL");
            super.moveEntityWithHeading(f, f1);
        }
        if ((riddenByEntity != null) && !getIsTamed() && !isSwimming())
        {
            //System.out.println(this.getName() + " SETTING MOUNT TO NULL!!");
            riddenByEntity.mountEntity(null);
            return;
        }

        if ((riddenByEntity != null) && !getIsTamed())
        {
            //if ((rand.nextInt(5) == 0) && !maJump)
            if ((rand.nextInt(5) == 0) && !getIsJumping() && jumpPending)
            {
                //motionY += 0.4D;
                //maJump = true;
                //System.out.println("moveEntitywithHeading " + this.getName() + " is jumping!!");
                motionY += getCustomJump();
                //System.out.println(this.toString() + " setIsJumping = true");
                setIsJumping(true);
                jumpPending = false;
            }
            if (rand.nextInt(10) == 0)
            {
                motionX += rand.nextDouble() / 30D;
                motionZ += rand.nextDouble() / 10D;
            }
            if (MoCreatures.isServer())
            {
                //System.out.println("moveEntitywithHeading " + this.getName() + " moveEntity, IS NOT TAMED!");
                moveEntity(motionX, motionY, motionZ);
            }
            if (MoCreatures.isServer() && rand.nextInt(50) == 0)
            {
                worldObj.playSoundAtEntity(this, getUpsetSound(), 1.0F, 1.0F + ((rand.nextFloat() - rand.nextFloat()) * 0.2F));
                riddenByEntity.motionY += 0.9D;
                riddenByEntity.motionZ -= 0.3D;
                riddenByEntity.mountEntity(null);
                this.ridingEntity = null;
            }
            if (onGround)
            {
                //maJump = false;
                setIsJumping(false);
            }
            if (MoCreatures.isServer())
            {
                int chance = (getMaxTemper() - getTemper());
                if (chance <= 0)
                {
                    chance = 1;
                }
                if (rand.nextInt(chance * 8) == 0)
                {
                    //System.out.println("moveEntitywithHeading SETTING TAMED TO TRUE!!");
                    //MoCreatures.proxy.setName((EntityPlayer)riddenByEntity, this);
                    //TODO NAMER
                    //setTamed(true);
                    //MoCServerPacketHandler.sendNameGUI((EntityPlayerMP) riddenByEntity, this.entityId);
                    MoCTools.tameWithName((EntityPlayerMP) riddenByEntity, this);
                }

            }

        }
        else if ((riddenByEntity != null) && getIsTamed())// && isSwimming())
        {
            //System.out.println("motx = " + motionX + ", motY = " + motionY + ", motZ = " + motionZ);
            //boundingBox.maxY = riddenByEntity.boundingBox.maxY;
            motionX += riddenByEntity.motionX * (getCustomSpeed() / 5D);
            motionZ += riddenByEntity.motionZ * (getCustomSpeed() / 5D);

            if (jumpPending)
            {
                motionY += getCustomJump();
                jumpPending = false;
            }

            if (divePending)
            {
                divePending = false;
                motionY -= 0.3D;
            }
            // not needed ?
            if (onGround)
            {
                //System.out.println(this.getName() + " is on Ground");
            }
            if (motionY > 0.01D && !isSwimming())
            {
                motionY = -0.01D;
            }
            rotationPitch = riddenByEntity.rotationPitch * 0.5F;
            prevRotationYaw = rotationYaw = riddenByEntity.rotationYaw;
            setRotation(rotationYaw, rotationPitch);
            //moveEntity(motionX, motionY, motionZ);
            if (MoCreatures.isServer())
            {
                //    System.out.println(this.getName() + " SERVER moveEntity, IS TAMED!");
                moveEntity(motionX, motionY, motionZ);
            }

            motionX *= 0.95D;
            motionZ *= 0.95D;
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

    protected boolean MoveToNextEntity(Entity entity)
    {
        if (entity != null)
        {
            int i = MathHelper.floor_double(entity.posX);
            int j = MathHelper.floor_double(entity.posY);
            int k = MathHelper.floor_double(entity.posZ);
            faceItem(i, j, k, 30F);
            if (posX < i)
            {
                double d = entity.posX - posX;
                if (d > 0.5D)
                {
                    motionX += 0.050000000000000003D;
                }
            }
            else
            {
                double d1 = posX - entity.posX;
                if (d1 > 0.5D)
                {
                    motionX -= 0.050000000000000003D;
                }
            }
            if (posZ < k)
            {
                double d2 = entity.posZ - posZ;
                if (d2 > 0.5D)
                {
                    motionZ += 0.050000000000000003D;
                }
            }
            else
            {
                double d3 = posZ - entity.posZ;
                if (d3 > 0.5D)
                {
                    motionZ -= 0.050000000000000003D;
                }
            }
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Speed used to move the mob around
     * 
     * @return
     */
    public double getCustomSpeed()
    {
        return 1.5D;
    }

    @Override
    public boolean isInWater()
    {
        return false;
    }

    @Override
    public boolean canBreatheUnderwater()
    {
        return true;
    }

    public boolean isDiving()
    {
        return diving;
    }

    @Override
    protected void jump()
    {

    }

    /*
     * public boolean stuckInWater() { float distY =
     * MoCTools.distanceToSurface(this); //System.out.println("DistY = " + distY);
     * return ((isInsideOfMaterial(Material.water)) && onGround && (distY >2));
     * }
     */

    public void floating()
    {

        float distY = MoCTools.distanceToSurface(this);

        if (riddenByEntity != null)
        {
            EntityPlayer ep = (EntityPlayer) riddenByEntity;
            if (ep.isJumping)
            {
                motionY += 0.09D;
            }
            else
            {
                motionY = -0.008D;
                // motionY *= 0.6D;
            }
            return;
        }

        if ((entityToAttack != null && ((entityToAttack.posY < (posY - 0.5D)) && getDistanceToEntity(entityToAttack) < 10F))) // ||
                                                                                                                                // caughtPrey)
        {
            if (motionY < -0.1)
            {
                motionY = -0.1;
            }
            return;
        }

        if (distY < 1 || isDiving())
        {
            if (motionY < -0.05)
            {
                motionY = -0.05;
            }

        }
        else
        {
            if (motionY < 0)
            {
                motionY = 0;
            }
            motionY += 0.001D;// 0.001

            if (distY > 1)
            {
                motionY += (distY * 0.02);
                if (motionY > 0.2D)
                {
                    motionY = 0.2D;
                }
            }
        }
    }

    // used to pick up objects while riding an entity
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
                        //attackEntityFrom(DamageSource.causeMobDamage((EntityLiving) entity),((EntityMob) entity).attackStrength);
                    	attackEntityFrom(DamageSource.causeMobDamage((EntityLiving) entity), ((EntityMob)entity).getAttackStrength(this));
                    }
                }

            }
            if (entityplayer.isSneaking())
            {
                //if (!worldObj.isRemote)
                //{
            	this.makeEntityDive();
                //entityplayer.mountEntity(null);
                //}
            }
        }
    }

    @Override
    protected boolean isMovementCeased()
    {
        return ((!isSwimming() && riddenByEntity == null) || riddenByEntity != null);
    }

    @Override
    public void onLivingUpdate()
    {
    	if (MoCreatures.isServer())
    	{
    		if (this.riddenByEntity != null)
    		{
    			Riding();
    			mountCount = 1;
    		}
    		
    		if (mountCount > 0)
    		{
    			if (++mountCount > 50)
    			{
    				mountCount = 0;
    			}
    		}
    		
    		if (getIsTamed() && rand.nextInt(100) == 0)
            {
                MoCServerPacketHandler.sendHealth(this.entityId, this.worldObj.provider.dimensionId, this.getHealth());
            }

            if (forceUpdates() && rand.nextInt(500) == 0)
            {
                MoCTools.forceDataSync(this);
            }
    	}
    	
		
        

        moveSpeed = getMoveSpeed();

        if (isSwimming())
        {
            floating();
            //Riding();
            outOfWater = 0;
        }
        else
        {
            //    System.out.println("out of water");
            // since Riding() is not being called, we must watch for player dismounts
            if (this.riddenByEntity != null)
            {
                if (this.riddenByEntity.isSneaking())
                {
                    this.riddenByEntity.mountEntity(null);
                }
            }

            outOfWater++;
            if (outOfWater > 10)
            {
                setPathToEntity(null);
            }
            if (outOfWater > 200 && (outOfWater % 30) == 0)

            // if((rand.nextInt(20) == 0) && (riddenByEntity == null) &&
            // !worldObj.multiplayerWorld)
            {
                // outOfWater++;
                motionY += 0.3D;// + (outOfWater / 30);
                motionX = (float) (Math.random() * 0.2D - 0.1D);
                motionZ = (float) (Math.random() * 0.2D - 0.1D);
                attackEntityFrom(DamageSource.drown, 1);
            }
            /*
             * if((health <= 0) || (!isInWater() && (riddenByEntity == null))) {
             * isJumping = false; moveStrafing = 0.0F; moveForward = 0.0F;
             * randomYawVelocity = 0.0F; }
             */
        }

        if (!hasPath() && riddenByEntity == null && !isMovementCeased() && entityToAttack == null)
        {
            updateWanderPath();
        }

        if (!diving)
        {
            if (riddenByEntity == null && entityToAttack == null && hasPath() && rand.nextInt(500) == 0)
            {
                diving = true;
            }
        }
        else
        {

            divingCount++;
            if (divingCount > 100 || riddenByEntity != null)// || !hasPath())
            {
                diving = false;
                divingCount = 0;
            }
        }
        super.onLivingUpdate();
    }

    public boolean isSwimming()
    {
        return ((isInsideOfMaterial(Material.water)));
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
        //selectType();
    }

    /**
     * fixes dewspawning tamed creatures
     */
    @Override
    public boolean isEntityInsideOpaqueBlock()
    {
        if (getIsTamed()) { return false; }

        return super.isEntityInsideOpaqueBlock();
    }

    public void setDisplayName(boolean flag)
    {

    }

    public void setTypeInt(int i)
    {
        setType(i);
        selectType();
    }

    /**
     * Used to synchronize the attack animation between server and client
     * 
     * @param attackType
     */
    @Override
    public void performAnimation(int attackType)
    {
    }

    /**
     * Makes the entity despawn if requirements are reached changed to the
     * entities now last longer
     */
    @Override
    protected void despawnEntity()
    {
        EntityPlayer var1 = this.worldObj.getClosestPlayerToEntity(this, -1.0D);
        if (var1 != null)
        {
            double var2 = var1.posX - this.posX;
            double var4 = var1.posY - this.posY;
            double var6 = var1.posZ - this.posZ;
            double var8 = var2 * var2 + var4 * var4 + var6 * var6;

            if (this.canDespawn() && var8 > 16384.0D)
            {
                this.setDead();
            }
            //changed from 600
            if (this.entityAge > 1800 && this.rand.nextInt(800) == 0 && var8 > 1024.0D && this.canDespawn())
            {
                this.setDead();
            }
            else if (var8 < 1024.0D)
            {
                this.entityAge = 0;
            }
        }

    }

    public float getMoveSpeed()
    {
        return 0.7F;
    }

    @Override
    public int nameYOffset()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double roperYOffset()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean renderName()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Entity getRoper()
    {
        return roper;
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

    @Override
    public void makeEntityDive()
    {
        this.divePending = true;
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

    /**
     * Checks if the entity's current position is a valid location to spawn this
     * entity.
     */
    @Override
    public boolean getCanSpawnHere()
    {
        return this.worldObj.checkNoEntityCollision(this.boundingBox);
    }

    @Override
    public String getOwnerName()
    {
        return this.dataWatcher.getWatchableObjectString(21);
    }

    @Override
    public void setOwner(String par1Str)
    {
        this.dataWatcher.updateObject(21, par1Str);
    }

    /*public EntityLiving getOwner()
    {
        return this.worldObj.getPlayerEntityByName(this.getOwnerName());
    }*/

    @Override
    public void onDeath(DamageSource damagesource)
    {
        if (this.getIsTamed() && (this.getOwnerName() != null) && MoCreatures.isServer())
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

        return super.attackEntityFrom(damagesource, i);

    }

    @Override
    public boolean interact(EntityPlayer entityplayer)
    {
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();

      //before ownership check 
      		if ((itemstack != null) && getIsTamed() && ((itemstack.itemID == MoCreatures.scrollOfOwner.itemID)) 
      				&& MoCreatures.proxy.enableResetOwnership && MoCTools.isThisPlayerAnOP(entityplayer))
      		{
      			if (--itemstack.stackSize == 0)
      			{
      				entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
      			}
      			if (MoCreatures.isServer())
      			{
      				if (MoCreatures.proxy.enableOwnership) 
      				{
      					EntityPlayer epOwner = this.worldObj.getPlayerEntityByName(this.getOwnerName());
      					if (epOwner != null)
      					{
      						MoCTools.reduceTamedByPlayer(epOwner);
      					}
      					else
      					{
      						MoCTools.reduceTamedByOfflinePlayer(this.getOwnerName());
      					}
      				}
      				this.setOwner("");
      			}
      			return true;
      		}
      		
        //if the player interacting is not the owner, do nothing!
        if (MoCreatures.proxy.enableOwnership && getOwnerName() != null && !getOwnerName().equals("") && !entityplayer.username.equals(getOwnerName())) { return true; }

        //changes name
        if ((itemstack != null) && getIsTamed() //&& MoCreatures.isServer()
                && ((itemstack.itemID == MoCreatures.medallion.itemID) || (itemstack.itemID == Item.book.itemID)))
        {
            if (MoCreatures.isServer())
            {
                MoCTools.tameWithName((EntityPlayerMP) entityplayer, this);
            }
            //TODO NAMER
            /*if (!MoCreatures.isServer())
            {
                MoCreatures.proxy.setName(entityplayer, this);
            }*/
            return true;
        }

        //sets it free, untamed
        if ((itemstack != null) && getIsTamed() 
                && ((itemstack.itemID == MoCreatures.scrollFreedom.itemID)))
        {
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            if (MoCreatures.isServer())
            {
                        
                if (MoCreatures.proxy.enableOwnership) MoCTools.reduceTamedByPlayer(entityplayer);
                this.setOwner("");
                this.setName("");
                this.dropMyStuff();
                this.setTamed(false);
            }
                    
            return true;
        }

        //removes owner, any other player can claim it by renaming it
        if ((itemstack != null) && getIsTamed() 
                        && ((itemstack.itemID == MoCreatures.scrollOfSale.itemID)))
        {
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            if (MoCreatures.isServer())
            {
                        
                if (MoCreatures.proxy.enableOwnership) MoCTools.reduceTamedByPlayer(entityplayer);
                this.setOwner("");
            }
                        
            return true;
        }
                
        if ((itemstack != null) && getIsTamed() && isMyHealFood(itemstack))
        {
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            worldObj.playSoundAtEntity(this, "eating", 1.0F, 1.0F + ((rand.nextFloat() - rand.nextFloat()) * 0.2F));
            if (MoCreatures.isServer())
            {
                health = getMaxHealth();
            }
            return true;
        }

        return false;
    }

    private void dropMyStuff() {
        // TODO Auto-generated method stub
        
    }

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
    public void setArmorType(byte i) {}
    
 // Fixes despawn issue when chunks unload and duplicated mounts when disconnecting on servers
    @Override
    public void setDead()
    {
        if (MoCreatures.isServer() && getIsTamed() && this.health > 0 && mountCount == 0)//&& (this.riddenByEntity == null))
        {
        		return;
        }
        super.setDead();
    }
    
    @Override
    public void dismountEntity() 
	{
		if (MoCreatures.isServer() && this.riddenByEntity != null)
		{
			this.riddenByEntity.mountEntity(null);
			this.riddenByEntity = null;
		}
	}
}