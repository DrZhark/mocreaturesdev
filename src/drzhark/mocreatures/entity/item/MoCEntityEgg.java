package drzhark.mocreatures.entity.item;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.passive.MoCEntityFishy;
import drzhark.mocreatures.entity.passive.MoCEntityKomodo;
import drzhark.mocreatures.entity.passive.MoCEntityOstrich;
import drzhark.mocreatures.entity.passive.MoCEntityPetScorpion;
import drzhark.mocreatures.entity.passive.MoCEntityShark;
import drzhark.mocreatures.entity.passive.MoCEntitySnake;
import drzhark.mocreatures.entity.passive.MoCEntityWyvern;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

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
        texture = MoCreatures.proxy.MODEL_TEXTURE + "egg.png";
    }

    public MoCEntityEgg(World world, double d, double d1, double d2)
    {
        super(world);

        setSize(0.25F, 0.25F);
        tCounter = 0;
        lCounter = 0;
        texture = MoCreatures.proxy.MODEL_TEXTURE + "egg.png";
    }

    @Override
    public boolean canBreatheUnderwater()
    {
        return true;
    }

    /*
     * @Override protected void entityInit() { }
     */

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
        if ((lCounter > 10) && entityplayer.inventory.addItemStackToInventory(new ItemStack(MoCreatures.fishyegg, 1, i)))
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
                //System.out.println("counter = " + lCounter);
            }

            if (lCounter > 500)
            {
                EntityPlayer entityplayer1 = worldObj.getClosestPlayerToEntity(this, 24D);
                if (entityplayer1 == null)
                {
                    this.setDead();
                }
            }

            if (isInWater() && getEggType() < 21 && (rand.nextInt(20) == 0))
            {
                tCounter++;
                if (tCounter % 5 == 0)
                {
                    motionY += 0.2D;

                }
                if (tCounter >= 50)
                {

                    // EntityLiving entityspawn = spawnLiving(creatureName,
                    // MoCreatures.mc.theWorld);

                    // if (entityliving != null)

                    if (getEggType() < 11) // fishy
                    {
                        MoCEntityFishy entityspawn = new MoCEntityFishy(worldObj);

                        entityspawn.setPosition(posX, posY, posZ);
                        entityspawn.setType(getEggType());
                        entityspawn.selectType();
                        entityspawn.setEdad(30);
                        worldObj.spawnEntityInWorld(entityspawn);
                        EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, 24D);
                        if (entityplayer != null)
                        {
                            if (MoCreatures.isServer())
                            {
                                MoCTools.tameWithName((EntityPlayerMP) entityplayer, entityspawn);
                                //TODO NAMER
                                //entityspawn.setTamed(true);
                            }
                        }
                    }

                    else if (getEggType() == 11) // shark
                    {
                        MoCEntityShark entityspawn = new MoCEntityShark(worldObj);

                        entityspawn.setPosition(posX, posY, posZ);
                        // entityspawn.type = getEggType());
                        entityspawn.setEdad(30);
                        worldObj.spawnEntityInWorld(entityspawn);
                        EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, 24D);
                        if (entityplayer != null)
                        {
                            if (MoCreatures.isServer())
                            {
                                MoCTools.tameWithName((EntityPlayerMP) entityplayer, entityspawn);
                                //TODO NAMER
                                //entityspawn.setTamed(true);
                            }
                        }
                    }

                    worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, ((rand.nextFloat() - rand.nextFloat()) * 0.2F) + 1.0F);

                    setDead();
                }

            }

            else if (getEggType() > 20 && MoCTools.isNearTorch(this, 4D) && (rand.nextInt(20) == 0)) // non aquatic creatures
            {
                tCounter++;
                //if (getEggType() == 30) tCounter = 0; //with this, wild ostriches won't spawn eggs.

                if (tCounter % 5 == 0)
                {
                    motionY += 0.2D;

                }
                if (tCounter >= 50)
                {

                    if (getEggType() > 20 && getEggType() < 29) // snakes
                    {
                        MoCEntitySnake entityspawn = new MoCEntitySnake(worldObj);

                        entityspawn.setPosition(posX, posY, posZ);
                        entityspawn.setType(getEggType() - 20);
                        //entityspawn.selectType();
                        entityspawn.setEdad(50);
                        worldObj.spawnEntityInWorld(entityspawn);
                        EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, 24D);
                        if (entityplayer != null)
                        {
                            if (MoCreatures.isServer())
                            {
                                MoCTools.tameWithName((EntityPlayerMP) entityplayer, entityspawn);
                                //TODO NAMER
                                //entityspawn.setTamed(true);
                            }
                        }
                    }

                    if (getEggType() == 30 || getEggType() == 31 || getEggType() == 32) // Ostriches. 30 = wild egg, 31 = stolen egg
                    {
                        if ((worldObj.countEntities(MoCEntityOstrich.class) > 20))
                        {
                            tCounter = 0;
                            lCounter = 500;
                            return;
                        }

                        MoCEntityOstrich entityspawn = new MoCEntityOstrich(worldObj);
                        int typeInt = 1;
                        if (worldObj.provider.isHellWorld || getEggType() == 32)
                        {
                            typeInt = 5;
                        }
                        entityspawn.setPosition(posX, posY, posZ);
                        entityspawn.setType(typeInt);
                        //entityspawn.selectType();
                        entityspawn.setEdad(35);
                        worldObj.spawnEntityInWorld(entityspawn);
                        entityspawn.setEntityHealth(entityspawn.getMaxHealth());
                        //System.out.println("setting health of hatch to " + entityspawn.getMaxHealth());

                        if (getEggType() == 31)//stolen egg that hatches a tamed ostrich
                        {
                            EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, 24D);
                            if (entityplayer != null)
                            {
                                if (MoCreatures.isServer())
                                {
                                    MoCTools.tameWithName((EntityPlayerMP) entityplayer, entityspawn);
                                    //TODO NAMER
                                    //entityspawn.setTamed(true);
                                }
                            }

                            //if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
                            //{
                            //    EntityPlayer player = worldObj.getClosestPlayerToEntity(this, 24D);
                            //MoCreatures.proxy.setName(player, entityspawn);
                            //    MoCServerPacketHandler.sendNameGUI((EntityPlayerMP) player, entityspawn.entityId);
                            //}

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
                            if (MoCreatures.isServer())
                            {
                                MoCTools.tameWithName((EntityPlayerMP) entityplayer, entityspawn);
                            }
                        }
                    }
                    
                    if (getEggType() > 40 && getEggType() < 50) //scorpions for now it uses 41 - 45
                    {
                        MoCEntityPetScorpion entityspawn = new MoCEntityPetScorpion(worldObj);
                        int typeInt = getEggType() - 40;
                        //entityspawn.cheated = false;
                        entityspawn.setPosition(posX, posY, posZ);
                        entityspawn.setType(typeInt);
                        entityspawn.setAdult(false);
                        worldObj.spawnEntityInWorld(entityspawn);
                        entityspawn.setEntityHealth(entityspawn.getMaxHealth());
                        EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, 24D);
                        if (entityplayer != null)
                        {
                            if (MoCreatures.isServer())
                            {
                                MoCTools.tameWithName((EntityPlayerMP) entityplayer, entityspawn);
                            }
                        }
                    }
                    
                    if (getEggType() > 49 && getEggType() < 55) //wyverns for now it uses 50 - 54
                    {
                        MoCEntityWyvern entityspawn = new MoCEntityWyvern(worldObj);
                        int typeInt = getEggType() - 49;
                        entityspawn.setPosition(posX, posY, posZ);
                        entityspawn.setType(typeInt);
                        entityspawn.setAdult(false);
                        entityspawn.setEdad(30);
                        worldObj.spawnEntityInWorld(entityspawn);
                        entityspawn.setEntityHealth(entityspawn.getMaxHealth());
                        EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, 24D);
                        if (entityplayer != null)
                        {
                            if (MoCreatures.isServer())
                            {
                                MoCTools.tameWithName((EntityPlayerMP) entityplayer, entityspawn);
                            }
                        }
                    }
                    worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, ((rand.nextFloat() - rand.nextFloat()) * 0.2F) + 1.0F);
                    setDead();
                }
            }
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
    public int getMaxHealth()
    {
        return 10;
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
}
