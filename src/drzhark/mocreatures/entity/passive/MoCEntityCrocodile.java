package drzhark.mocreatures.entity.passive;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;

public class MoCEntityCrocodile extends MoCEntityTameableAnimal {
    // TODO
    // fix floating so it moves faster if it's deep and caughts prey underneath

    // for later?
    // birds to clean their mouths?
    // implement taming? (pick up small crocs to insta tame them)

    public float biteProgress;
    public float spin;
    public int spinInt;
    private float myMoveSpeed;
    private boolean waterbound;
    private int hunting;

    public MoCEntityCrocodile(World world)
    {
        super(world);
        texture = "crocodile.png";
        setSize(2F, 0.6F);
        myMoveSpeed = 0.5F;
        //health = 25;
        setEdad(50 + rand.nextInt(50));
        setTamed(false);
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0D);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(22, Byte.valueOf((byte) 0)); // isBiting - 0 false 1 true
        dataWatcher.addObject(23, Byte.valueOf((byte) 0)); // isResting - 0 false 1 true
        dataWatcher.addObject(24, Byte.valueOf((byte) 0)); // caughtPrey - 0 false 1 true
    }

    public boolean getIsBiting()
    {
        return (dataWatcher.getWatchableObjectByte(22) == 1);
    }

    public boolean getIsResting()
    {
        return (dataWatcher.getWatchableObjectByte(23) == 1);
    }

    public boolean getHasCaughtPrey()
    {
        return (dataWatcher.getWatchableObjectByte(24) == 1);
    }

    public void setBiting(boolean flag)
    {
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(22, Byte.valueOf(input));
    }

    public void setIsResting(boolean flag)
    {
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(23, Byte.valueOf(input));
    }

    public void setHasCaughtPrey(boolean flag)
    {
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(24, Byte.valueOf(input));
    }

    @Override
    protected void jump()
    {

        if (isInsideOfMaterial(Material.water))
        {
            if (getHasCaughtPrey() || (entityToAttack == null && rand.nextInt(20) != 0)) 
            {
                return;
            }

            motionY = 0.3D;
            if (isSprinting())
            {
                float f = rotationYaw * 0.01745329F;
                motionX -= MathHelper.sin(f) * 0.2F;
                motionZ += MathHelper.cos(f) * 0.2F;
            }
            isAirBorne = true;

        }
        else if (entityToAttack != null || getHasCaughtPrey())
        {
            super.jump();
        }
    }

    @Override
    protected boolean isMovementCeased()
    {
        return getIsResting();
    }

    @Override
    protected void updateEntityActionState()
    {
        if (!getIsResting())
        {
            super.updateEntityActionState();
        }
    }

    @Override
    public boolean swimmerEntity()
    {
        return true;
    }

    @Override
    public float getMoveSpeed()
    {
        return myMoveSpeed;
    }

    @Override
    public void onLivingUpdate()
    {
        if (getIsResting())
        {
            rotationPitch = -5F;
            if (!isInsideOfMaterial(Material.water) && biteProgress < 0.3F && rand.nextInt(5) == 0)
            {
                biteProgress += 0.005F;
            }
            entityToAttack = findPlayerToAttack();
            if (entityToAttack != null)
            {
                setIsResting(false);
                getMyOwnPath(entityToAttack, 16F);
            }
            if (MoCreatures.isServer() && entityToAttack != null || getHasCaughtPrey() || rand.nextInt(500) == 0)// isInsideOfMaterial(Material.water)
            {
                setIsResting(false);
                biteProgress = 0;
                hunting = 1;
            }

        }
        else
        {
            if (MoCreatures.isServer() && (rand.nextInt(500) == 0) && entityToAttack == null && !getHasCaughtPrey())// && !isInsideOfMaterial(Material.water))
            {
                setIsResting(true);
                setPathToEntity(null);
            }

        }

        if (isInsideOfMaterial(Material.water))
        {
            myMoveSpeed = 0.8F;
        }
        else
        {
            myMoveSpeed = 0.4F;

        }
        if (hunting > 0)
        {
            hunting++;
            if (hunting > 120)
            {
                hunting = 0;
                myMoveSpeed = 0.5F;
            }
            else
            {
                myMoveSpeed = 1.0F;
            }

            if (entityToAttack == null)
            {
                hunting = 0;
                myMoveSpeed = 0.5F;
            }

        }

        if (rand.nextInt(80) == 0 && !getHasCaughtPrey() && !getIsResting())
        {
            crocBite();
        }

        if (MoCreatures.isServer() && rand.nextInt(500) == 0 && !waterbound && !getIsResting() && !isInsideOfMaterial(Material.water))
        {
            MoCTools.MoveToWater(this);
        }

        if (MoCreatures.isServer() && getEdad() < 150 && (rand.nextInt(200) == 0))
        {
            setEdad(getEdad() + 1);
            if (getEdad() >= 90)
            {
                setAdult(true);
            }
        }

        if (waterbound)
        {
            if (!isInsideOfMaterial(Material.water))
            {
                MoCTools.MoveToWater(this);
            }
            else
            {
                waterbound = false;
            }
        }

        if (getHasCaughtPrey())
        {
            if (riddenByEntity != null)
            {
                entityToAttack = null;

                biteProgress = 0.4F;
                setIsResting(false);

                if (!isInsideOfMaterial(Material.water))
                {
                    waterbound = true;
                    if (riddenByEntity instanceof EntityLiving && ((EntityLivingBase) riddenByEntity).getHealth() > 0)
                    {
                        ((EntityLivingBase) riddenByEntity).deathTime = 0;
                    }

                    if (MoCreatures.isServer() && rand.nextInt(50) == 0)
                    {
                        riddenByEntity.attackEntityFrom(DamageSource.causeMobDamage(this), 2);
                        if (!(riddenByEntity instanceof EntityPlayer))
                        {
                            MoCTools.destroyDrops(this, 3D);
                        }
                    }
                }
            }
            else
            {
                setHasCaughtPrey(false);
                biteProgress = 0F;
                waterbound = false;
            }

            if (isSpinning())
            {
                spinInt += 3;
                if ((spinInt % 20) == 0)
                {
                    worldObj.playSoundAtEntity(this, "mocreatures:crocroll", 1.0F, 1.0F + ((rand.nextFloat() - rand.nextFloat()) * 0.2F));
                }
                if (spinInt > 80)
                {
                    spinInt = 0;
                    riddenByEntity.attackEntityFrom(DamageSource.causeMobDamage(this), 4);
                    if (!(riddenByEntity instanceof EntityPlayer))
                    {
                        MoCTools.destroyDrops(this, 3D);
                    }
                }

                //the following if to be removed from SMP

                if (!worldObj.isRemote && riddenByEntity != null && riddenByEntity instanceof EntityPlayer)
                {
                    //TODO 4FIX
                    //MoCreatures.mc.gameSettings.thirdPersonView = 1; 
                }
            }
        }

        super.onLivingUpdate();
    }

    @Override
    public boolean isNotScared()
    {
        return true;
    }

    public boolean getIsSitting()
    {
        double d1 = 0.01D;
        return (getIsResting() || ((motionX < d1 && motionX > -d1) && (motionZ < d1 && motionZ > -d1)));
    }

    public void crocBite()
    {
        if (!getIsBiting())
        {
            setBiting(true);
            biteProgress = 0.0F;
        }
    }

    @Override
    public void onUpdate()
    {
        if (getIsBiting() && !getHasCaughtPrey())// && biteProgress <0.3)
        {
            biteProgress += 0.1F;
            if (biteProgress == 0.4F)
            {
                worldObj.playSoundAtEntity(this, "mocreatures:crocjawsnap", 1.0F, 1.0F + ((rand.nextFloat() - rand.nextFloat()) * 0.2F));
            }
            if (biteProgress > 0.6F)
            {

                setBiting(false);
                biteProgress = 0.0F;
            }
        }

        super.onUpdate();
    }

    @Override
    protected void attackEntity(Entity entity, float f)
    {
        if (entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer)entity;
            if (player.capabilities.isCreativeMode)
                return;
        }
        if (getHasCaughtPrey()) { return; }

        if (attackTime <= 0 && (f < 3F) && (entity.boundingBox.maxY > boundingBox.minY) && (entity.boundingBox.minY < boundingBox.maxY))
        {
            attackTime = 20;
            if (entity.ridingEntity == null && rand.nextInt(3) == 0)
            {
                entity.mountEntity(this);
                setHasCaughtPrey(true);
            }
            else
            {
                entity.attackEntityFrom(DamageSource.causeMobDamage(this), 2);
                if (!(entity instanceof EntityPlayer))
                {
                    MoCTools.destroyDrops(this, 3D);
                }
                crocBite();
                setHasCaughtPrey(false);
            }

        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i)
    {
        if (riddenByEntity != null)
        {

            Entity entity = damagesource.getEntity();
            if (entity != null && riddenByEntity == entity)
            {
                if (rand.nextInt(2) != 0)
                {
                    return false;
                }
                else
                {
                    unMount();
                }
            }

        }
        if (super.attackEntityFrom(damagesource, i))
        {
            Entity entity = damagesource.getEntity();

            if (riddenByEntity != null && riddenByEntity == entity)
            {
                if ((entity != this) && (worldObj.difficultySetting.getDifficultyId() > 0))
                {
                    entityToAttack = entity;
                }
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
        if (getHasCaughtPrey()) { return null; }

        if (worldObj.difficultySetting.getDifficultyId() > 0)
        {
            double attackD = 12D;
             
            if (getIsResting())
            {
                attackD = 6D;
            }
            
            EntityPlayer entityplayer = worldObj.getClosestVulnerablePlayerToEntity(this, attackD); 
            if((entityplayer != null) && getIsAdult()) 
            {
                 return entityplayer; 
            }
            
            EntityLivingBase entityliving = getClosestEntityLiving(this, attackD);
            return entityliving;
        }
        return null;
    }

    @Override
    public boolean entitiesToIgnore(Entity entity)
    {
        return ((super.entitiesToIgnore(entity)) || (entity instanceof MoCEntityCrocodile) || (entity.height < this.height && entity.width < this.width)

        );
    }

    @Override
    public void updateRiderPosition()
    {
        if (riddenByEntity == null) { return; }
        int direction = 1;

        double dist = getEdad() * 0.01F + riddenByEntity.width - 0.4D;
        double newPosX = posX - (dist * Math.cos((MoCTools.realAngle(rotationYaw - 90F)) / 57.29578F));
        double newPosZ = posZ - (dist * Math.sin((MoCTools.realAngle(rotationYaw - 90F)) / 57.29578F));
        riddenByEntity.setPosition(newPosX, posY + getMountedYOffset() + riddenByEntity.getYOffset(), newPosZ);

        if (spinInt > 40)
        {
            direction = -1;
        }
        else
        {
            direction = 1;
        }

        ((EntityLivingBase) riddenByEntity).renderYawOffset = this.rotationYaw * direction;
        ((EntityLivingBase) riddenByEntity).prevRenderYawOffset = this.rotationYaw * direction;
    }

    @Override
    public double getMountedYOffset()
    {
        return (double) height * 0.35D;
    }

    @Override
    public void floating()
    {
        if ((entityToAttack != null && ((entityToAttack.posY < (posY - 0.5D)) && getDistanceToEntity(entityToAttack) < 10F))) // || caughtPrey)
        {
            if (motionY < -0.1)
            {
                motionY = -0.1;
            }
        }
        else
        {
            super.floating();
        }
    }

    @Override
    protected String getDeathSound()
    {
        return "mocreatures:crocdying";
    }

    @Override
    protected String getHurtSound()
    {
        return "mocreatures:crochurt";
    }

    @Override
    protected String getLivingSound()
    {
        if (getIsResting()) { return "mocreatures:crocresting"; }
        return "mocreatures:crocgrunt";
    }

    @Override
    protected Item getDropItem()
    {
        return MoCreatures.hideCroc;
    }

    public boolean isSpinning()
    {
        return getHasCaughtPrey() && (riddenByEntity != null) && (isInsideOfMaterial(Material.water));
    }

    @Override
    public void onDeath(DamageSource damagesource)
    {

        unMount();
        MoCTools.checkForTwistedEntities(worldObj);
        super.onDeath(damagesource);
    }

    public void unMount()
    {

        if (riddenByEntity != null)
        {
            if (riddenByEntity instanceof EntityLiving && ((EntityLivingBase) riddenByEntity).getHealth() > 0)
            {
                ((EntityLivingBase) riddenByEntity).deathTime = 0;
            }

            riddenByEntity.mountEntity(null);
            setHasCaughtPrey(false);
        }
    }

    @Override
    public int getTalkInterval()
    {
        return 120;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
    }
}