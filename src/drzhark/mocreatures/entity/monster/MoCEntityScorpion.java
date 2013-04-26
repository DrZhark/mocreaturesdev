package drzhark.mocreatures.entity.monster;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.entity.passive.MoCEntityPetScorpion;
import drzhark.mocreatures.network.MoCServerPacketHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class MoCEntityScorpion extends MoCEntityMob {
    private boolean isPoisoning;
    private int poisontimer;
    public int mouthCounter;
    public int armCounter;
    private int hideCounter;

    public MoCEntityScorpion(World world)
    {
        super(world);
        setSize(1.4F, 0.9F);
        health = 15;
        poisontimer = 0;
        setAdult(true);
        setEdad(20);
        //roper = null;

        if (MoCreatures.isServer())
        {
            if (rand.nextInt(4) == 0)
            {
                setHasBabies(true);
            }
            else
            {
                setHasBabies(false);
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
            return MoCreatures.proxy.MODEL_TEXTURE + "scorpiondirt.png";
        case 2:
            return MoCreatures.proxy.MODEL_TEXTURE + "scorpioncave.png";
        case 3:
            return MoCreatures.proxy.MODEL_TEXTURE + "scorpionnether.png";
        case 4:
            return MoCreatures.proxy.MODEL_TEXTURE + "scorpionfrost.png";

        default:
            return MoCreatures.proxy.MODEL_TEXTURE + "scorpiondirt.png";
        }

    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(22, Byte.valueOf((byte) 0)); // isPicked - 0 false 1 true
        dataWatcher.addObject(23, Byte.valueOf((byte) 0)); // has babies - 0 false 1 true
    }

    public boolean getHasBabies()
    {
        //return true;
        return getIsAdult() && (dataWatcher.getWatchableObjectByte(23) == 1);
    }

    public boolean getIsPicked()
    {
        return (dataWatcher.getWatchableObjectByte(22) == 1);
    }

    public boolean getIsPoisoning()
    {
        return isPoisoning;
        //return (dataWatcher.getWatchableObjectByte(23) == 1);
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
        dataWatcher.updateObject(22, Byte.valueOf(input));
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

    /**
     * finds shelter from sunlight
     */
    protected void findSunLightShelter()
    {
        Vec3 var1 = this.findPossibleShelter();
        if (var1 == null)
        {
            hideCounter++;
            if (hideCounter > 200)
            {
                hideCounter = 0;
            }
            this.updateWanderPath();
            //System.out.println("didn't find shelter");
            return;// false;
        }
        else
        {
            this.getNavigator().tryMoveToXYZ(var1.xCoord, var1.yCoord, var1.zCoord, this.getMoveSpeed() / 2F);
            //System.out.println("moving to shelter");
        }
    }

    /**
     * Does it want to hide?
     * 
     * @return
     */
    private boolean wantsToHide()
    {
        return (worldObj.isDaytime()

        && worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), (int) this.boundingBox.minY, MathHelper.floor_double(this.posZ)));
    }

    @Override
    public void onLivingUpdate()
    {

        if (MoCreatures.isServer() && wantsToHide())
        {
            findSunLightShelter();
        }

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

        //if (rand.nextInt(50) == 0) swingArm(); //this will be done automatically on the model

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
            //if ((entity != null) && (entity instanceof EntityPlayer) && getIsTamed()) { return false; }

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

    // @Override
    @Override
    protected Entity findPlayerToAttack()
    {
        if (worldObj.difficultySetting > 0 && (!worldObj.isDaytime()) && getIsAdult())// only attacks player at night
        {
            /*if (!getIsTamed())
            {
                EntityPlayer entityplayer = worldObj.getClosestVulnerablePlayerToEntity(this, 12D);
                if ((entityplayer != null) && getIsAdult()) { return entityplayer; }
            }
            else*/
            {
                if ((rand.nextInt(80) == 0))
                {
                    EntityLiving entityliving = getClosestEntityLiving(this, 10D);
                if (entityliving != null && this.canEntityBeSeen(entityliving)) // blood - add LoS requirement
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

        if (MoCreatures.isServer() && getIsAdult() && getHasBabies())//rand.nextInt(5) == 0)
        {
            int k = rand.nextInt(5);
            for (int i = 0; i < k; i++)
            {

                MoCEntityPetScorpion entityscorpy = new MoCEntityPetScorpion(worldObj);
                entityscorpy.setPosition(posX, posY, posZ);
                entityscorpy.setAdult(false);
                entityscorpy.setType(getType());
                //entityscorpy.cheated = false;
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

        default:
            return Item.silk.itemID;
        }
    }
    
    
    @Override
    protected void dropFewItems(boolean flag, int x)
    {
	 	if (!flag) return;
	 	int j = this.getDropItemId();

        if (j > 0)
        {
            if (rand.nextInt(3) == 0)
            {
            	this.dropItem(j, 1);
            }
        }
	 	
       
    }

    /*@Override
    public boolean interact(EntityPlayer entityplayer)
    {
        if (super.interact(entityplayer)) { return false; }
        

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

        return false;
    }*/

    @Override
    public boolean getCanSpawnHere()
    {

        if (MoCTools.isNearTorch(this)) { return false; }
        
        return (MoCreatures.proxy.getFrequency(this.getEntityName()) > 0) && getCanSpawnHereLiving() && getCanSpawnHereCreature();
    }

    /*@Override
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
    }*/

    @Override
    public boolean checkSpawningBiome()
    {
    	if (worldObj.provider.isHellWorld)
        {
            setType(3);
            isImmuneToFire = true;
            return true;
        }
    	
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(boundingBox.minY);
        int k = MathHelper.floor_double(posZ);

        String s = MoCTools.BiomeName(worldObj, i, j, k);
        BiomeGenBase currentbiome = MoCTools.Biomekind(worldObj, i, j, k);
        
        //if (s.equals("Taiga") || s.equals("Frozen Ocean") || s.equals("Frozen River") || s.equals("Ice Plains") || s.equals("Ice Mountains") || s.equals("TaigaHills"))
        if (currentbiome.temperature <= 0.05F)
        {
            setType(4);
        }
        else if (!worldObj.canBlockSeeTheSky(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)) && (posY < 50D))
        {
        	setType(2);
            return true;
        }

        return true;

    }

    
    
    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        setHasBabies(nbttagcompound.getBoolean("Babies"));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("Babies", getHasBabies());
    }

    /*@Override
    public boolean updateMount()
    {
        return getIsTamed();
    }*/

    /*@Override
    public boolean forceUpdates()
    {
        return getIsTamed();
    }*/

    @Override
    public boolean isAIEnabled()
    {
        return wantsToHide() && (entityToAttack == null) && (hideCounter < 50);
    }

    /*@Override
    public byte nameYOffset()
    {

        byte n = (byte) (1 - (getEdad() * 0.8));
        if (n < -70)
        {
            n = -70;
        }
        //System.out.println("nameY =" + n);
        return n;//(byte) (1 - (getEdad() * 0.8));

    }*/

    /*@Override
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
        //System.out.println("r =" + r);
        return r;//(double) ((150 - getEdad()) * 0.0075D);

    }*/

    @Override
    public int getTalkInterval()
    {
        return 300;
    }
}
