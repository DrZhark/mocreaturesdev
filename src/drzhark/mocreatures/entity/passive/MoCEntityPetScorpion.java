package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import drzhark.mocreatures.entity.monster.MoCEntityScorpion;
import drzhark.mocreatures.network.MoCServerPacketHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class MoCEntityPetScorpion extends MoCEntityAnimal {
    public static final String scorpionNames[] = { "Dirt", "Cave", "Nether", "Frost", "Undead" };
    private boolean isPoisoning;
    private int poisontimer;
    public int mouthCounter;
    public int armCounter;
    private int hideCounter;
    //public boolean cheated;

    public MoCEntityPetScorpion(World world)
    {
        super(world);
        setSize(1.4F, 0.9F);
        health = 15;
        poisontimer = 0;
        setAdult(false);
        setEdad(20);
        roper = null;
        setHasBabies(false);
        //cheated = true;
        this.stepHeight = 1.0F;

    }

    @Override
    public void selectType()
    {
        if (getType() == 0)
        {
            setType(1);

        }

    }

    @Override
    public String getTexture()
    {

        boolean saddle = getIsRideable();
        switch (getType())
        {
        case 1:
            if (!saddle) { return MoCreatures.proxy.MODEL_TEXTURE + "scorpiondirt.png"; }
            return MoCreatures.proxy.MODEL_TEXTURE + "scorpiondirtsaddle.png";
        case 2:
            if (!saddle) { return MoCreatures.proxy.MODEL_TEXTURE + "scorpioncave.png"; }
            return MoCreatures.proxy.MODEL_TEXTURE + "scorpioncavesaddle.png";
        case 3:
            if (!saddle) { return MoCreatures.proxy.MODEL_TEXTURE + "scorpionnether.png"; }
            return MoCreatures.proxy.MODEL_TEXTURE + "scorpionnethersaddle.png";
        case 4:
            if (!saddle) { return MoCreatures.proxy.MODEL_TEXTURE + "scorpionfrost.png"; }
            return MoCreatures.proxy.MODEL_TEXTURE + "scorpionfrostsaddle.png";

        case 5:
            if (!saddle) { return MoCreatures.proxy.MODEL_TEXTURE + "scorpionundead.png"; }
            return MoCreatures.proxy.MODEL_TEXTURE + "scorpionundeadsaddle.png";

        default:
            return MoCreatures.proxy.MODEL_TEXTURE + "scorpiondirt.png";
        }

    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(22, Byte.valueOf((byte) 0)); // isRideable - 0 false 1 true
        dataWatcher.addObject(23, Byte.valueOf((byte) 0)); // has babies - 0 false 1 true
        dataWatcher.addObject(24, Byte.valueOf((byte) 0)); // isPicked - 0 false 1 true

    }

    public boolean getIsRideable()
    {
        return (dataWatcher.getWatchableObjectByte(22) == 1);
    }

    public boolean getHasBabies()
    {
        //return true;
        return getIsAdult() && (dataWatcher.getWatchableObjectByte(23) == 1);
    }

    public boolean getIsPicked()
    {
        return (dataWatcher.getWatchableObjectByte(24) == 1);
    }

    public boolean getIsPoisoning()
    {
        return isPoisoning;
        //return (dataWatcher.getWatchableObjectByte(23) == 1);
    }

    public void setRideable(boolean flag)
    {
        // if (worldObj.isRemote) return;
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(22, Byte.valueOf(input));
    }

    public void setHasBabies(boolean flag)
    {
        //if (worldObj.isRemote) return;
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(23, Byte.valueOf(input));
    }

    public void setPicked(boolean flag)
    {
        //if (worldObj.isRemote) return;
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(24, Byte.valueOf(input));
    }

    public void setPoisoning(boolean flag)
    {
        if (flag && MoCreatures.isServer())
        {
            MoCServerPacketHandler.sendAnimationPacket(this.entityId, this.worldObj.provider.dimensionId, 0);
        }
        isPoisoning = flag;
    }

    @Override
    public void performAnimation(int animationType)
    {
        if (animationType == 0) //tail animation
        {
            setPoisoning(true);
        }
        else if (animationType == 1) //arm swinging
        {
            armCounter = 1;
            //swingArm();
        }
        else if (animationType == 3) //movement of mouth
        {
            mouthCounter = 1;
        }
    }

    @Override
    public int getMaxHealth()
    {
        return 15;
    }

    @Override
    public float getMoveSpeed()
    {
        return 0.8F;
    }

    @Override
    public boolean isOnLadder()
    {
        return isCollidedHorizontally;
    }

    public boolean climbing()
    {
        return !onGround && isOnLadder();
    }

    @Override
    public void onLivingUpdate()
    {
        //if (MoCreatures.isServer())
        //{
        	/*if (cheated)
        	{
        		this.setDead();
        	}*/
            //Riding();
        //}

        if (!onGround && (ridingEntity != null))
        {
            rotationYaw = ridingEntity.rotationYaw;
        }
        if (getIsAdult() && fleeingTick > 0)
        {
            fleeingTick = 0;
        }

        if (mouthCounter != 0 && mouthCounter++ > 50)
        {
            mouthCounter = 0;
        }

        if (MoCreatures.isServer() && (armCounter == 10 || armCounter == 40))
        {
            worldObj.playSoundAtEntity(this, "scorpionclaw", 1.0F, 1.0F + ((rand.nextFloat() - rand.nextFloat()) * 0.2F));
        }

        if (armCounter != 0 && armCounter++ > 24)
        {
            armCounter = 0;
        }

        if (getIsPoisoning())
        {
            poisontimer++;
            if (poisontimer == 1)
            {
                worldObj.playSoundAtEntity(this, "scorpionsting", 1.0F, 1.0F + ((rand.nextFloat() - rand.nextFloat()) * 0.2F));
            }
            if (poisontimer > 50)
            {
                poisontimer = 0;
                setPoisoning(false);
            }
        }

        if (MoCreatures.isServer() && !getIsAdult() && (rand.nextInt(200) == 0))
        {
            setEdad(getEdad() + 1);
            if (getEdad() >= 120)
            {
                setAdult(true);
            }
        }

        super.onLivingUpdate();
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, int i)
    {
        if (super.attackEntityFrom(damagesource, i))
        {
            Entity entity = damagesource.getEntity();
            if ((entity != null) && (entity instanceof EntityPlayer) && getIsTamed()) { return false; }

            if ((entity != null) && (entity != this) && (worldObj.difficultySetting > 0) && getIsAdult())
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
    protected Entity findPlayerToAttack()
    {
        if (worldObj.difficultySetting > 0 && (!worldObj.isDaytime()) && getIsAdult())// only attacks player at night
        {
            if (!getIsTamed())
            {
                EntityPlayer entityplayer = worldObj.getClosestVulnerablePlayerToEntity(this, 12D);
                if ((entityplayer != null) && getIsAdult()) { return entityplayer; }
            }
            else
            {
                if ((rand.nextInt(80) == 0))
                {
                    EntityLiving entityliving = getClosestEntityLiving(this, 10D);
                    return entityliving;
                }

            }
        }
        return null;
    }

    @Override
    public boolean entitiesToIgnore(Entity entity)
    {
        return ((super.entitiesToIgnore(entity)) || (this.getIsTamed() && entity instanceof MoCEntityScorpion && ((MoCEntityScorpion) entity).getIsTamed()));
    }

    @Override
    protected void attackEntity(Entity entity, float f)
    {
        if ((f > 2.0F) && (f < 6F) && (rand.nextInt(50) == 0))
        {
            if (onGround)
            {
                double d = entity.posX - posX;
                double d1 = entity.posZ - posZ;
                float f1 = MathHelper.sqrt_double((d * d) + (d1 * d1));
                motionX = ((d / f1) * 0.5D * 0.8D) + (motionX * 0.2D);
                motionZ = ((d1 / f1) * 0.5D * 0.8D) + (motionZ * 0.2D);
                motionY = 0.4D;
            }
        }
        else if (attackTime <= 0 && (f < 3.0D) && (entity.boundingBox.maxY > boundingBox.minY) && (entity.boundingBox.minY < boundingBox.maxY))
        {
            attackTime = 20;
            boolean flag = (entity instanceof EntityPlayer);
            if (!getIsPoisoning() && rand.nextInt(5) == 0)
            {
                setPoisoning(true);
                if (getType() <= 2)// regular scorpions
                {
                    if (flag)
                    {
                        MoCreatures.poisonPlayer((EntityPlayer) entity);
                    }
                    ((EntityLiving) entity).addPotionEffect(new PotionEffect(Potion.poison.id, 70, 0));
                }
                else if (getType() == 4)// blue scorpions
                {
                    if (flag)
                    {
                        MoCreatures.freezePlayer((EntityPlayer) entity);
                    }
                    ((EntityLiving) entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 70, 0));

                }
                else if (getType() == 3)// red scorpions
                {
                    if (flag && MoCreatures.isServer() && !worldObj.provider.isHellWorld)
                    {
                        MoCreatures.burnPlayer((EntityPlayer) entity);
                        ((EntityLiving) entity).setFire(15);

                    }

                }

            }
            else
            {
                entity.attackEntityFrom(DamageSource.causeMobDamage(this), 1);
                swingArm();
            }
        }
    }

    public void swingArm()
    {
        if (MoCreatures.isServer())
        {
            MoCServerPacketHandler.sendAnimationPacket(this.entityId, this.worldObj.provider.dimensionId, 1);
        }
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();
    }

    public boolean swingingTail()
    {
        return getIsPoisoning() && poisontimer < 15;
    }

    @Override
    public void onDeath(DamageSource damagesource)
    {
        super.onDeath(damagesource);

        if (MoCreatures.isServer() && getIsAdult() && getHasBabies())
        {
            int k = rand.nextInt(5);
            for (int i = 0; i < k; i++)
            {

                MoCEntityScorpion entityscorpy = new MoCEntityScorpion(worldObj);
                entityscorpy.setPosition(posX, posY, posZ);
                entityscorpy.setAdult(false);
                entityscorpy.setType(getType());
                worldObj.spawnEntityInWorld(entityscorpy);
                worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, ((rand.nextFloat() - rand.nextFloat()) * 0.2F) + 1.0F);

            }
        }
    }

    @Override
    protected String getDeathSound()
    {
        return "scorpiondying";
    }

    @Override
    protected String getHurtSound()
    {
        return "scorpionhurt";
    }

    @Override
    protected String getLivingSound()
    {
        if (MoCreatures.isServer())
        {
            MoCServerPacketHandler.sendAnimationPacket(this.entityId, this.worldObj.provider.dimensionId, 3);
        }

        return "scorpiongrunt";
    }

    @Override
    protected int getDropItemId()
    {
        if (!getIsAdult()) { return Item.silk.itemID; }

        boolean flag = (rand.nextInt(2) == 0);

        switch (getType())
        {
        case 1:
            if (flag) { return MoCreatures.stingDirt.itemID; }
            return MoCreatures.chitinDirt.itemID;
        case 2:
            if (flag) { return MoCreatures.stingCave.itemID; }
            return MoCreatures.chitinCave.itemID;
        case 3:
            if (flag) { return MoCreatures.stingNether.itemID; }
            return MoCreatures.chitinNether.itemID;
        case 4:
            if (flag) { return MoCreatures.stingFrost.itemID; }
            return MoCreatures.chitinFrost.itemID;
        case 5:
            return Item.rottenFlesh.itemID;

        default:
            return Item.silk.itemID;
        }
    }

    @Override
    public boolean interact(EntityPlayer entityplayer)
    {
        if (super.interact(entityplayer)) { return false; }

        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if ((itemstack != null) && getIsAdult() && !getIsRideable() && (itemstack.itemID == Item.saddle.itemID || itemstack.itemID == MoCreatures.horsesaddle.itemID))
        {
            entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            setRideable(true);
            return true;
        }

        if ((itemstack != null) && this.getIsTamed() && itemstack.itemID == MoCreatures.vialundead.itemID)
        {
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Item.glassBottle));
            }
            else
            {
                entityplayer.inventory.addItemStackToInventory(new ItemStack(Item.glassBottle));
            }
            setType(5);
            return true;
        }

        if ((itemstack != null) && this.getIsTamed() && itemstack.itemID == MoCreatures.vialdarkness.itemID)
        {
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Item.glassBottle));
            }
            else
            {
                entityplayer.inventory.addItemStackToInventory(new ItemStack(Item.glassBottle));
            }
            health = getMaxHealth();
            if (MoCreatures.isServer())
            {
                int i = getType() + 40;
                MoCEntityEgg entityegg = new MoCEntityEgg(worldObj, i);
                entityegg.setPosition(entityplayer.posX, entityplayer.posY, entityplayer.posZ);
                entityplayer.worldObj.spawnEntityInWorld(entityegg);
                entityegg.motionY += worldObj.rand.nextFloat() * 0.05F;
                entityegg.motionX += (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.3F;
                entityegg.motionZ += (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.3F;
            }
            return true;
        }
        if (this.ridingEntity == null && !getIsAdult())
        {
            rotationYaw = entityplayer.rotationYaw;
            if (MoCreatures.isServer())
            {
                mountEntity(entityplayer);
            }
            setPicked(true);

            if (MoCreatures.isServer() && !getIsTamed())
            {
                MoCTools.tameWithName((EntityPlayerMP) entityplayer, this);
            }
        }
        else if (this.ridingEntity != null && !getIsAdult())
        {
            worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, ((rand.nextFloat() - rand.nextFloat()) * 0.2F) + 1.0F);
            setPicked(false);
            if (MoCreatures.isServer())
            {
                this.mountEntity(null);
            }
            motionX = entityplayer.motionX * 5D;
            motionY = (entityplayer.motionY / 2D) + 0.5D;
            motionZ = entityplayer.motionZ * 5D;
        }

        if (getIsRideable() && getIsTamed() && getIsAdult() && (riddenByEntity == null))
        {
            entityplayer.rotationYaw = rotationYaw;
            entityplayer.rotationPitch = rotationPitch;
            setEating(false);
            if (MoCreatures.isServer())
            {
                entityplayer.mountEntity(this);
            }

            return true;
        }

        return false;
    }

    @Override
    public double getYOffset()
    {
        if (ridingEntity instanceof EntityPlayer && ridingEntity == MoCreatures.proxy.getPlayer() && !MoCreatures.isServer()) { return (yOffset - 1.7F); }

        if ((ridingEntity instanceof EntityPlayer) && !MoCreatures.isServer())
        {
            return (yOffset - 0.1F);
        }
        else
        {
            return yOffset;
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        setHasBabies(nbttagcompound.getBoolean("Babies"));
        setRideable(nbttagcompound.getBoolean("Saddled"));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("Babies", getHasBabies());
        nbttagcompound.setBoolean("Saddled", getIsRideable());
    }

    @Override
    public boolean updateMount()
    {
        return getIsTamed();
    }

    @Override
    public boolean forceUpdates()
    {
        return getIsTamed();
    }

    @Override
    public int nameYOffset()
    {

        int n = (int) (1 - (getEdad() * 0.8));
        if (n < -70)
        {
            n = -70;
        }
        
        return n;//(byte) (1 - (getEdad() * 0.8));

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
        
        return r;//(double) ((150 - getEdad()) * 0.0075D);

    }

    @Override
    protected boolean isMyHealFood(ItemStack itemstack)
    {
        return (itemstack.itemID == MoCreatures.ratRaw.itemID || itemstack.itemID == MoCreatures.ratCooked.itemID);
    }

    @Override
    public int getTalkInterval()
    {
        return 300;
    }

    @Override
    protected void fall(float f)
    {
    }

    @Override
    public boolean renderName()
    {
        return getDisplayName() && (riddenByEntity == null) && (ridingEntity == null);
    }

    @Override
    public boolean canBeCollidedWith()
    {

        return riddenByEntity == null;
    }

    @Override
    public boolean rideableEntity()
    {
        return true;
    }

    @Override
    protected boolean isMovementCeased()
    {
        return (riddenByEntity != null);
    }
    
    @Override
    public void dropMyStuff() 
    {
        MoCTools.dropSaddle(this, worldObj);
    }
}
