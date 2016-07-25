package drzhark.mocreatures.entity.item;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.aquatic.MoCEntityFishy;
import drzhark.mocreatures.entity.aquatic.MoCEntityMediumFish;
import drzhark.mocreatures.entity.aquatic.MoCEntityPiranha;
import drzhark.mocreatures.entity.aquatic.MoCEntityShark;
import drzhark.mocreatures.entity.aquatic.MoCEntitySmallFish;
import drzhark.mocreatures.entity.passive.MoCEntityKomodo;
import drzhark.mocreatures.entity.passive.MoCEntityManticorePet;
import drzhark.mocreatures.entity.passive.MoCEntityOstrich;
import drzhark.mocreatures.entity.passive.MoCEntityPetScorpion;
import drzhark.mocreatures.entity.passive.MoCEntitySnake;
import drzhark.mocreatures.entity.passive.MoCEntityWyvern;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoCEntityEgg extends EntityLiving {

    private int tCounter;
    private int lCounter;
    public int eggType;

    public MoCEntityEgg(World world, int type) {
        this(world);
        this.eggType = type;
    }

    public MoCEntityEgg(World world) {
        super(world);
        setSize(0.25F, 0.25F);
        this.tCounter = 0;
        this.lCounter = 0;
    }

    public MoCEntityEgg(World world, double d, double d1, double d2) {
        super(world);

        setSize(0.25F, 0.25F);
        this.tCounter = 0;
        this.lCounter = 0;
    }

    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getTexture("egg.png");
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D); // setMaxHealth
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    protected String getDeathSound() {
        return null;
    }

    @Override
    protected String getHurtSound() {
        return null;
    }

    @Override
    protected String getLivingSound() {
        return null;
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    @Override
    public boolean handleWaterMovement() {
        if (this.worldObj.handleMaterialAcceleration(this.getEntityBoundingBox(), Material.water, this)) {
            this.inWater = true;
            return true;
        } else {
            this.inWater = false;
            return false;
        }
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer entityplayer) {
        int i = this.eggType;
        if (i == 30) {
            i = 31;
        }
        if ((this.lCounter > 10) && entityplayer.inventory.addItemStackToInventory(new ItemStack(MoCreatures.mocegg, 1, i))) {
            this.worldObj.playSoundAtEntity(this, "random.pop", 0.2F, (((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F) + 1.0F) * 2.0F);
            if (!this.worldObj.isRemote) {
                entityplayer.onItemPickup(this, 1);

            }
            setDead();
        }
    }

    @Override
    public void onLivingUpdate() {
        this.moveStrafing = 0.0F;
        this.moveForward = 0.0F;
        this.randomYawVelocity = 0.0F;
        moveEntityWithHeading(this.moveStrafing, this.moveForward);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (MoCreatures.isServer()) {
            if (this.rand.nextInt(20) == 0) {
                this.lCounter++;
            }

            if (this.lCounter > 500) {
                EntityPlayer entityplayer1 = this.worldObj.getClosestPlayerToEntity(this, 24D);
                if (entityplayer1 == null) {
                    this.setDead();
                }
            }

            if (isInWater() && (getEggType() < 12 || getEggType() > 69) && (this.rand.nextInt(20) == 0)) {
                this.tCounter++;
                if (this.tCounter % 5 == 0) {
                    this.motionY += 0.2D;
                }

                if (this.tCounter == 5) {
                    NotifyEggHatching();
                }

                if (this.tCounter >= 30) {
                    if (getEggType() <= 10) // fishy
                    {
                        MoCEntityFishy entityspawn = new MoCEntityFishy(this.worldObj);
                        entityspawn.setPosition(this.posX, this.posY, this.posZ);
                        entityspawn.setType(getEggType());
                        entityspawn.setEdad(30);
                        this.worldObj.spawnEntityInWorld(entityspawn);
                        EntityPlayer entityplayer = this.worldObj.getClosestPlayerToEntity(this, 24D);
                        if (entityplayer != null) {
                            MoCTools.tameWithName(entityplayer, entityspawn);
                        }
                    }

                    else if (getEggType() == 11) // shark
                    {
                        MoCEntityShark entityspawn = new MoCEntityShark(this.worldObj);
                        entityspawn.setPosition(this.posX, this.posY, this.posZ);
                        entityspawn.setEdad(30);
                        this.worldObj.spawnEntityInWorld(entityspawn);
                        EntityPlayer entityplayer = this.worldObj.getClosestPlayerToEntity(this, 24D);
                        if (entityplayer != null) {
                            MoCTools.tameWithName(entityplayer, entityspawn);
                        }
                    }

                    else if (getEggType() == 90) // piranha
                    {
                        MoCEntityPiranha entityspawn = new MoCEntityPiranha(this.worldObj);
                        entityspawn.setPosition(this.posX, this.posY, this.posZ);
                        this.worldObj.spawnEntityInWorld(entityspawn);
                        entityspawn.setEdad(30);
                        EntityPlayer entityplayer = this.worldObj.getClosestPlayerToEntity(this, 24D);
                        if (entityplayer != null) {
                            MoCTools.tameWithName(entityplayer, entityspawn);
                        }
                    }

                    else if (getEggType() >= 80 && getEggType() < (80 + MoCEntitySmallFish.fishNames.length)) // smallfish
                    {
                        MoCEntitySmallFish entityspawn = new MoCEntitySmallFish(this.worldObj);

                        entityspawn.setPosition(this.posX, this.posY, this.posZ);
                        entityspawn.setType(getEggType() - 79);
                        this.worldObj.spawnEntityInWorld(entityspawn);
                        entityspawn.setEdad(30);
                        EntityPlayer entityplayer = this.worldObj.getClosestPlayerToEntity(this, 24D);
                        if (entityplayer != null) {
                            MoCTools.tameWithName(entityplayer, entityspawn);
                        }
                    }

                    else if (getEggType() >= 70 && getEggType() < (80 + MoCEntityMediumFish.fishNames.length)) // mediumfish
                    {
                        MoCEntityMediumFish entityspawn = new MoCEntityMediumFish(this.worldObj);
                        entityspawn.setPosition(this.posX, this.posY, this.posZ);
                        entityspawn.setType(getEggType() - 69);
                        this.worldObj.spawnEntityInWorld(entityspawn);
                        entityspawn.setEdad(30);
                        EntityPlayer entityplayer = this.worldObj.getClosestPlayerToEntity(this, 24D);
                        if (entityplayer != null) {
                            MoCTools.tameWithName(entityplayer, entityspawn);
                        }
                    }
                    this.worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F) + 1.0F);
                    setDead();
                }
            }

            else if (!isInWater() && getEggType() > 20 && (this.rand.nextInt(20) == 0)) // non aquatic creatures
            {
                this.tCounter++;
                //if (getEggType() == 30) tCounter = 0; //with this, wild ostriches won't spawn eggs.

                if (this.tCounter % 5 == 0) {
                    this.motionY += 0.2D;
                }

                if (this.tCounter == 5) {
                    NotifyEggHatching();
                }

                if (this.tCounter >= 30) {
                    if (getEggType() > 20 && getEggType() < 29) // snakes
                    {
                        MoCEntitySnake entityspawn = new MoCEntitySnake(this.worldObj);

                        entityspawn.setPosition(this.posX, this.posY, this.posZ);
                        entityspawn.setType(getEggType() - 20);
                        entityspawn.setEdad(50);
                        this.worldObj.spawnEntityInWorld(entityspawn);
                        EntityPlayer entityplayer = this.worldObj.getClosestPlayerToEntity(this, 24D);
                        if (entityplayer != null) {
                            MoCTools.tameWithName(entityplayer, entityspawn);
                        }
                    }

                    if (getEggType() == 30 || getEggType() == 31 || getEggType() == 32) // Ostriches. 30 = wild egg, 31 = stolen egg
                    {
                        MoCEntityOstrich entityspawn = new MoCEntityOstrich(this.worldObj);
                        int typeInt = 1;
                        if (this.worldObj.provider.doesWaterVaporize() || getEggType() == 32) {
                            typeInt = 5;
                        }
                        entityspawn.setPosition(this.posX, this.posY, this.posZ);
                        entityspawn.setType(typeInt);
                        entityspawn.setEdad(35);
                        this.worldObj.spawnEntityInWorld(entityspawn);
                        entityspawn.setHealth(entityspawn.getMaxHealth());

                        if (getEggType() == 31)//stolen egg that hatches a tamed ostrich
                        {
                            EntityPlayer entityplayer = this.worldObj.getClosestPlayerToEntity(this, 24D);
                            if (entityplayer != null) {
                                MoCTools.tameWithName(entityplayer, entityspawn);
                            }
                        }
                    }

                    if (getEggType() == 33) // Komodo
                    {
                        MoCEntityKomodo entityspawn = new MoCEntityKomodo(this.worldObj);

                        entityspawn.setPosition(this.posX, this.posY, this.posZ);
                        entityspawn.setEdad(30);
                        this.worldObj.spawnEntityInWorld(entityspawn);
                        EntityPlayer entityplayer = this.worldObj.getClosestPlayerToEntity(this, 24D);
                        if (entityplayer != null) {
                            MoCTools.tameWithName(entityplayer, entityspawn);
                        }
                    }

                    if (getEggType() > 40 && getEggType() < 46) //scorpions for now it uses 41 - 45
                    {
                        MoCEntityPetScorpion entityspawn = new MoCEntityPetScorpion(this.worldObj);
                        int typeInt = getEggType() - 40;
                        entityspawn.setPosition(this.posX, this.posY, this.posZ);
                        entityspawn.setType(typeInt);
                        entityspawn.setAdult(false);
                        this.worldObj.spawnEntityInWorld(entityspawn);
                        entityspawn.setHealth(entityspawn.getMaxHealth());
                        EntityPlayer entityplayer = this.worldObj.getClosestPlayerToEntity(this, 24D);
                        if (entityplayer != null) {
                            MoCTools.tameWithName(entityplayer, entityspawn);
                        }
                    }

                    if (getEggType() > 49 && getEggType() < 62) //wyverns for now it uses 50 - 61
                    {
                        MoCEntityWyvern entityspawn = new MoCEntityWyvern(this.worldObj);
                        int typeInt = getEggType() - 49;
                        entityspawn.setPosition(this.posX, this.posY, this.posZ);
                        entityspawn.setType(typeInt);
                        entityspawn.setAdult(false);
                        entityspawn.setEdad(30);
                        this.worldObj.spawnEntityInWorld(entityspawn);
                        entityspawn.setHealth(entityspawn.getMaxHealth());
                        EntityPlayer entityplayer = this.worldObj.getClosestPlayerToEntity(this, 24D);
                        if (entityplayer != null) {
                            MoCTools.tameWithName(entityplayer, entityspawn);
                        }
                    }
                    if (getEggType() > 61 && getEggType() < 66) //manticorePets for now it uses 62 - 65
                    {
                        MoCEntityManticorePet entityspawn = new MoCEntityManticorePet(this.worldObj);
                        int typeInt = getEggType() - 61;
                        entityspawn.setPosition(this.posX, this.posY, this.posZ);
                        entityspawn.setType(typeInt);
                        entityspawn.setAdult(false);
                        entityspawn.setEdad(30);
                        this.worldObj.spawnEntityInWorld(entityspawn);
                        entityspawn.setHealth(entityspawn.getMaxHealth());
                        EntityPlayer entityplayer = this.worldObj.getClosestPlayerToEntity(this, 24D);
                        if (entityplayer != null) {
                            MoCTools.tameWithName(entityplayer, entityspawn);
                        }
                    }
                    this.worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F) + 1.0F);
                    setDead();
                }
            }
        }
    }

    private void NotifyEggHatching() {
        EntityPlayer entityplayer = this.worldObj.getClosestPlayerToEntity(this, 24D);
        if (entityplayer != null) {
            entityplayer.addChatMessage(new ChatComponentTranslation("Egg hatching soon! KEEP WATCH! The hatched creature located @ "
                    + (int) this.posX + ", " + (int) this.posY + ", " + (int) this.posZ + " will be lost if you leave area"));
        }
    }

    public int getSize() {
        if (getEggType() == 30 || getEggType() == 31) {
            return 170;
        }
        return 100;
    }

    public int getEggType() {
        return this.eggType;
    }

    public void setEggType(int eggType) {
        this.eggType = eggType;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        nbttagcompound = MoCTools.getEntityData(this);
        setEggType(nbttagcompound.getInteger("EggType"));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound = MoCTools.getEntityData(this);
        nbttagcompound.setInteger("EggType", getEggType());
    }

    @Override
    public boolean isEntityInsideOpaqueBlock() {
        return false;
    }
}
