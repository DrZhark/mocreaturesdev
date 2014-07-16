package drzhark.mocreatures.entity.item;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.IMoCTameable;
import drzhark.mocreatures.entity.aquatic.MoCEntityFishy;
import drzhark.mocreatures.entity.aquatic.MoCEntityMediumFish;
import drzhark.mocreatures.entity.aquatic.MoCEntityPiranha;
import drzhark.mocreatures.entity.aquatic.MoCEntityShark;
import drzhark.mocreatures.entity.aquatic.MoCEntitySmallFish;
import drzhark.mocreatures.entity.passive.MoCEntityKomodo;
import drzhark.mocreatures.entity.passive.MoCEntityOstrich;
import drzhark.mocreatures.entity.passive.MoCEntityPetScorpion;
import drzhark.mocreatures.entity.passive.MoCEntitySnake;
import drzhark.mocreatures.entity.passive.MoCEntityWyvern;

public class MoCEntityEgg extends EntityLiving {
    private int tCounter;
    private int lCounter;
    public int eggType;

    public MoCEntityEgg(World world, int type)
    {
        this(world);
        eggType = type;
    }

    public MoCEntityEgg(World world)
    {
        super(world);
        setSize(0.25F, 0.25F);
        tCounter = 0;
        lCounter = 0;
        //texture = MoCreatures.proxy.MODEL_TEXTURE + "egg.png";
    }

    public MoCEntityEgg(World world, double d, double d1, double d2)
    {
        super(world);

        setSize(0.25F, 0.25F);
        tCounter = 0;
        lCounter = 0;
        //texture = MoCreatures.proxy.MODEL_TEXTURE + "egg.png";
    }

    public ResourceLocation getTexture()
    {
        return MoCreatures.proxy.getTexture("egg.png");
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D); // setMaxHealth
    }

    @Override
    public boolean canBreatheUnderwater()
    {
        return true;
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

    @Override
    public boolean handleWaterMovement()
    {
        if (worldObj.handleMaterialAcceleration(boundingBox, Material.water, this))
        {
            this.inWater = true;
            return true;
        }
        else
        {
            this.inWater = false;
            return false;
        }
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer entityplayer)
    {
        int i = eggType;
        if (i == 30)
        {
            i = 31;
        }
        if ((lCounter > 10) && entityplayer.inventory.addItemStackToInventory(new ItemStack(MoCreatures.mocegg, 1, i)))
        {
            worldObj.playSoundAtEntity(this, "random.pop", 0.2F, (((rand.nextFloat() - rand.nextFloat()) * 0.7F) + 1.0F) * 2.0F);
            if (!worldObj.isRemote)
            {
                entityplayer.onItemPickup(this, 1);

            }
            setDead();
        }
    }

    @Override
    public void onLivingUpdate()
    {
        moveStrafing = 0.0F;
        moveForward = 0.0F;
        randomYawVelocity = 0.0F;
        moveEntityWithHeading(moveStrafing, moveForward);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();
        if (MoCreatures.isServer())
        {
            if (rand.nextInt(20) == 0)
            {
                lCounter++;
            }

            if (lCounter > 500)
            {
                EntityPlayer entityplayer1 = worldObj.getClosestPlayerToEntity(this, 24D);
                if (entityplayer1 == null)
                {
                    this.setDead();
                }
            }

            if (isInWater() && (getEggType() < 12 || getEggType() > 69)&& (rand.nextInt(20) == 0))
            {
                tCounter++;
                if (tCounter % 5 == 0)
                {
                    motionY += 0.2D;
                }

                if (tCounter == 5)
                {
                    NotifyEggHatching();                    
                }
                
                if (tCounter >= 30)
                {
                    if (getEggType() < 10) // fishy
                    {
                        MoCEntityFishy entityspawn = new MoCEntityFishy(worldObj);

                        entityspawn.setPosition(posX, posY, posZ);
                        entityspawn.setType(getEggType());
                        entityspawn.setEdad(30);
                        worldObj.spawnEntityInWorld(entityspawn);
                        EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, 24D);
                        if (entityplayer != null)
                        {
                            MoCTools.tameWithName(entityplayer, (IMoCTameable) entityspawn);
                        }
                    }

                    else if (getEggType() == 11) // shark
                    {
                        MoCEntityShark entityspawn = new MoCEntityShark(worldObj);

                        entityspawn.setPosition(posX, posY, posZ);
                        entityspawn.setEdad(30);
                        worldObj.spawnEntityInWorld(entityspawn);
                        EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, 24D);
                        if (entityplayer != null)
                        {
                            MoCTools.tameWithName((EntityPlayerMP) entityplayer, (IMoCTameable) entityspawn);
                        }
                    }

                    else if (getEggType()  == 90) // piranha
                    {
                        MoCEntityPiranha entityspawn = new MoCEntityPiranha(worldObj);

                        entityspawn.setPosition(posX, posY, posZ);
                        worldObj.spawnEntityInWorld(entityspawn);
                        entityspawn.setEdad(30);
                        EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, 24D);
                        if (entityplayer != null)
                        {
                            MoCTools.tameWithName((EntityPlayerMP) entityplayer, (IMoCTameable) entityspawn);
                        }
                    }
                    
                    else if (getEggType() > 79 && getEggType() < (80 + MoCEntitySmallFish.fishNames.length)) // smallfish
                    {
                        MoCEntitySmallFish entityspawn = new MoCEntitySmallFish(worldObj);

                        entityspawn.setPosition(posX, posY, posZ);
                        entityspawn.setType(getEggType() - 79);
                        worldObj.spawnEntityInWorld(entityspawn);
                        entityspawn.setEdad(30);
                        EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, 24D);
                        if (entityplayer != null)
                        {
                            MoCTools.tameWithName((EntityPlayerMP) entityplayer, (IMoCTameable) entityspawn);
                        }
                    }
                    
                    else if (getEggType() > 69 && getEggType() < (80 + MoCEntityMediumFish.fishNames.length)) // mediumfish
                    {
                        MoCEntityMediumFish entityspawn = new MoCEntityMediumFish(worldObj);

                        entityspawn.setPosition(posX, posY, posZ);
                        entityspawn.setType(getEggType() - 69);
                        worldObj.spawnEntityInWorld(entityspawn);
                        entityspawn.setEdad(30);
                        EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, 24D);
                        if (entityplayer != null)
                        {
                            MoCTools.tameWithName((EntityPlayerMP) entityplayer, (IMoCTameable) entityspawn);
                        }
                    }
                    worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, ((rand.nextFloat() - rand.nextFloat()) * 0.2F) + 1.0F);
                    setDead();
                }
            }

            else if (getEggType() > 20 && (rand.nextInt(20) == 0)) // non aquatic creatures
            {
                tCounter++;
                //if (getEggType() == 30) tCounter = 0; //with this, wild ostriches won't spawn eggs.

                if (tCounter % 5 == 0)
                {
                    motionY += 0.2D;
                }

                if (tCounter == 5)
                {
                    NotifyEggHatching();                    
                }

                if (tCounter >= 30)
                {
                    if (getEggType() > 20 && getEggType() < 29) // snakes
                    {
                        MoCEntitySnake entityspawn = new MoCEntitySnake(worldObj);

                        entityspawn.setPosition(posX, posY, posZ);
                        entityspawn.setType(getEggType() - 20);
                        entityspawn.setEdad(50);
                        worldObj.spawnEntityInWorld(entityspawn);
                        EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, 24D);
                        if (entityplayer != null)
                        {
                            MoCTools.tameWithName((EntityPlayerMP) entityplayer, entityspawn);
                        }
                    }

                    if (getEggType() == 30 || getEggType() == 31 || getEggType() == 32) // Ostriches. 30 = wild egg, 31 = stolen egg
                    {
                        MoCEntityOstrich entityspawn = new MoCEntityOstrich(worldObj);
                        int typeInt = 1;
                        if (worldObj.provider.isHellWorld || getEggType() == 32)
                        {
                            typeInt = 5;
                        }
                        entityspawn.setPosition(posX, posY, posZ);
                        entityspawn.setType(typeInt);
                        entityspawn.setEdad(35);
                        worldObj.spawnEntityInWorld(entityspawn);
                        entityspawn.setHealth(entityspawn.getMaxHealth());

                        if (getEggType() == 31)//stolen egg that hatches a tamed ostrich
                        {
                            EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, 24D);
                            if (entityplayer != null)
                            {
                                MoCTools.tameWithName((EntityPlayerMP) entityplayer, entityspawn);
                            }
                        }
                    }

                    if (getEggType() == 33) // Komodo
                    {
                        MoCEntityKomodo entityspawn = new MoCEntityKomodo(worldObj);

                        entityspawn.setPosition(posX, posY, posZ);
                        entityspawn.setEdad(30);
                        worldObj.spawnEntityInWorld(entityspawn);
                        EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, 24D);
                        if (entityplayer != null)
                        {
                            MoCTools.tameWithName((EntityPlayerMP) entityplayer, entityspawn);
                        }
                    }
                    
                    if (getEggType() > 40 && getEggType() < 46) //scorpions for now it uses 41 - 45
                    {
                        MoCEntityPetScorpion entityspawn = new MoCEntityPetScorpion(worldObj);
                        int typeInt = getEggType() - 40;
                        entityspawn.setPosition(posX, posY, posZ);
                        entityspawn.setType(typeInt);
                        entityspawn.setAdult(false);
                        worldObj.spawnEntityInWorld(entityspawn);
                        entityspawn.setHealth(entityspawn.getMaxHealth());
                        EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, 24D);
                        if (entityplayer != null)
                        {
                            MoCTools.tameWithName((EntityPlayerMP) entityplayer, entityspawn);
                        }
                    }
                    
                    if (getEggType() > 49 && getEggType() < 62) //wyverns for now it uses 50 - 61
                    {
                        MoCEntityWyvern entityspawn = new MoCEntityWyvern(worldObj);
                        int typeInt = getEggType() - 49;
                        entityspawn.setPosition(posX, posY, posZ);
                        entityspawn.setType(typeInt);
                        entityspawn.setAdult(false);
                        entityspawn.setEdad(30);
                        worldObj.spawnEntityInWorld(entityspawn);
                        entityspawn.setHealth(entityspawn.getMaxHealth());
                        EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, 24D);
                        if (entityplayer != null)
                        {
                            MoCTools.tameWithName((EntityPlayerMP) entityplayer, entityspawn);
                        }
                    }
                    worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, ((rand.nextFloat() - rand.nextFloat()) * 0.2F) + 1.0F);
                    setDead();
                }
            }
        }
    }

    private void NotifyEggHatching()
    {
        EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, 24D);
        if (entityplayer != null)
        {
            entityplayer.addChatMessage(new ChatComponentTranslation("Egg hatching soon! KEEP WATCH! The hatched creature located @ " + (int)this.posX + ", " + (int)this.posY + ", " + (int)this.posZ + " will be lost if you leave area"));
        }
    }
    public int getSize()
    {
        if (getEggType() == 30 || getEggType() == 31) { return 170; }
        return 100;
    }

    public int getEggType()
    {
        return eggType;
    }

    public void setEggType(int eggType)
    {
        this.eggType = eggType;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        setEggType(nbttagcompound.getInteger("EggType"));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setInteger("EggType", getEggType());
    }
    
    @Override
    public boolean isEntityInsideOpaqueBlock()
    {
        return false;
    }
}