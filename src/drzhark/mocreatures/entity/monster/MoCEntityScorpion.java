package drzhark.mocreatures.entity.monster;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.entity.passive.MoCEntityPetScorpion;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;

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
        poisontimer = 0;
        setAdult(true);
        setEdad(20);

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

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15.0D);
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
    public ResourceLocation getTexture()
    {
        switch (getType())
        {
        case 1:
            return MoCreatures.proxy.getTexture("scorpiondirt.png");
        case 2:
            return MoCreatures.proxy.getTexture("scorpioncave.png");
        case 3:
            return MoCreatures.proxy.getTexture("scorpionnether.png");
        case 4:
            return MoCreatures.proxy.getTexture("scorpionfrost.png");
        default:
            return MoCreatures.proxy.getTexture("scorpiondirt.png");
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
        return getIsAdult() && (dataWatcher.getWatchableObjectByte(23) == 1);
    }

    public boolean getIsPicked()
    {
        return (dataWatcher.getWatchableObjectByte(22) == 1);
    }

    public boolean getIsPoisoning()
    {
        return isPoisoning;
    }

    public void setHasBabies(boolean flag)
    {
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(23, Byte.valueOf(input));
    }

    public void setPicked(boolean flag)
    {
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(22, Byte.valueOf(input));
    }

    public void setPoisoning(boolean flag)
    {
        if (flag && MoCreatures.isServer())
        {
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 0), new TargetPoint(this.worldObj.provider.dimensionId, this.posX, this.posY, this.posZ, 64));
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
            return;// false;
        }
        else
        {
            this.getNavigator().tryMoveToXYZ(var1.xCoord, var1.yCoord, var1.zCoord, this.getMoveSpeed() / 2F);
        }
    }

    /**
     * Does it want to hide?
     * 
     * @return
     */
    private boolean wantsToHide()
    {
        return (worldObj.isDaytime()); //&& worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), (int) this.boundingBox.minY, MathHelper.floor_double(this.posZ)));
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
            worldObj.playSoundAtEntity(this, "mocreatures:scorpionclaw", 1.0F, 1.0F + ((rand.nextFloat() - rand.nextFloat()) * 0.2F));
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
                worldObj.playSoundAtEntity(this, "mocreatures:scorpionsting", 1.0F, 1.0F + ((rand.nextFloat() - rand.nextFloat()) * 0.2F));
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
    public boolean attackEntityFrom(DamageSource damagesource, float i)
    {
        if (super.attackEntityFrom(damagesource, i))
        {
            Entity entity = damagesource.getEntity();

            if ((entity != null) && (entity != this) && (worldObj.difficultySetting.getDifficultyId() > 0) && getIsAdult())
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
        if (worldObj.difficultySetting.getDifficultyId() > 0 && (!worldObj.isDaytime()) && getIsAdult())// only attacks player at night
        {
            EntityPlayer entityplayer = worldObj.getClosestVulnerablePlayerToEntity(this, 12D);
            if ((entityplayer != null)) { return entityplayer; }
            {
                if ((rand.nextInt(80) == 0))
                {
                    EntityLivingBase entityliving = getClosestEntityLiving(this, 10D);
                if (entityliving != null && !(entityliving instanceof EntityPlayer) && this.canEntityBeSeen(entityliving)) // blood - add LoS requirement
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
            if (!getIsPoisoning() && rand.nextInt(5) == 0 && entity instanceof EntityLivingBase)
            {
                setPoisoning(true);
                if (getType() <= 2)// regular scorpions
                {
                    if (flag)
                    {
                        MoCreatures.poisonPlayer((EntityPlayer) entity);
                    }
                    ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.poison.id, 70, 0));
                }
                else if (getType() == 4)// blue scorpions
                {
                    if (flag)
                    {
                        MoCreatures.freezePlayer((EntityPlayer) entity);
                    }
                    ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 70, 0));

                }
                else if (getType() == 3)// red scorpions
                {
                    if (flag && MoCreatures.isServer() && !worldObj.provider.isHellWorld)
                    {
                        MoCreatures.burnPlayer((EntityPlayer) entity);
                        ((EntityLivingBase) entity).setFire(15);
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
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 1), new TargetPoint(this.worldObj.provider.dimensionId, this.posX, this.posY, this.posZ, 64));
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
                MoCEntityPetScorpion entityscorpy = new MoCEntityPetScorpion(worldObj);
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
        return "mocreatures:scorpiondying";
    }

    @Override
    protected String getHurtSound()
    {
        return "mocreatures:scorpionhurt";
    }

    @Override
    protected String getLivingSound()
    {
        if (MoCreatures.isServer())
        {
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 3), new TargetPoint(this.worldObj.provider.dimensionId, this.posX, this.posY, this.posZ, 64));
        }
        return "mocreatures:scorpiongrunt";
    }

    @Override
    protected Item getDropItem()
    {
        if (!getIsAdult()) { return Items.string; }

        boolean flag = (rand.nextInt(100) < MoCreatures.proxy.rareItemDropChance);

        switch (getType())
        {
        case 1:
            if (flag) { return MoCreatures.scorpStingDirt; }
            return MoCreatures.chitin;
        case 2:
            if (flag) { return MoCreatures.scorpStingCave; }
            return MoCreatures.chitinCave;
        case 3:
            if (flag) { return MoCreatures.scorpStingNether; }
            return MoCreatures.chitinNether;
        case 4:
            if (flag) { return MoCreatures.scorpStingFrost; }
            return MoCreatures.chitinFrost;
        default:
            return Items.string;
        }
    }

    @Override
    protected void dropFewItems(boolean flag, int x)
    {
        if (!flag) return;
        Item item = this.getDropItem();

        if (item != null)
        {
            if (rand.nextInt(3) == 0)
            {
                this.dropItem(item, 1);
            }
        }

    }

    @Override
    public boolean getCanSpawnHere()
    {
        return (isValidLightLevel() && MoCreatures.entityMap.get(this.getClass()).getFrequency() > 0) && getCanSpawnHereLiving() && getCanSpawnHereCreature();
    }

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

        BiomeGenBase currentbiome = MoCTools.Biomekind(worldObj, i, j, k);

        if (BiomeDictionary.isBiomeOfType(currentbiome, Type.FROZEN))
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

    @Override
    public boolean isAIEnabled()
    {
        return wantsToHide() && (entityToAttack == null) && (hideCounter < 50);
    }

    @Override
    public int getTalkInterval()
    {
        return 300;
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.ARTHROPOD;
    }
    
    @Override
    public float getAdjustedYOffset()
    {
        return 30F;
    }
}