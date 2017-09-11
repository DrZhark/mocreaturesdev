package drzhark.mocreatures.entity.item;

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
import drzhark.mocreatures.init.MoCItems;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
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
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D); // setMaxHealth
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    @Override
    public boolean handleWaterMovement() {
        if (this.world.handleMaterialAcceleration(this.getEntityBoundingBox(), Material.WATER, this)) {
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
        if ((this.lCounter > 10) && entityplayer.inventory.addItemStackToInventory(new ItemStack(MoCItems.mocegg, 1, i))) {
            this.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.2F, (((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F) + 1.0F) * 2.0F);
            if (!this.world.isRemote) {
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
        travel(this.moveStrafing, this.moveVertical, this.moveForward);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.world.isRemote) {
            if (this.rand.nextInt(20) == 0) {
                this.lCounter++;
            }

            if (this.lCounter > 500) {
                EntityPlayer entityplayer1 = this.world.getClosestPlayerToEntity(this, 24D);
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
                        MoCEntityFishy entityspawn = new MoCEntityFishy(this.world);
                        entityspawn.setPosition(this.posX, this.posY, this.posZ);
                        entityspawn.setType(getEggType());
                        entityspawn.setEdad(30);
                        this.world.spawnEntity(entityspawn);
                        EntityPlayer entityplayer = this.world.getClosestPlayerToEntity(this, 24D);
                        if (entityplayer != null) {
                            MoCTools.tameWithName(entityplayer, entityspawn);
                        }
                    }

                    else if (getEggType() == 11) // shark
                    {
                        MoCEntityShark entityspawn = new MoCEntityShark(this.world);
                        entityspawn.setPosition(this.posX, this.posY, this.posZ);
                        entityspawn.setEdad(30);
                        this.world.spawnEntity(entityspawn);
                        EntityPlayer entityplayer = this.world.getClosestPlayerToEntity(this, 24D);
                        if (entityplayer != null) {
                            MoCTools.tameWithName(entityplayer, entityspawn);
                        }
                    }

                    else if (getEggType() == 90) // piranha
                    {
                        MoCEntityPiranha entityspawn = new MoCEntityPiranha(this.world);
                        entityspawn.setPosition(this.posX, this.posY, this.posZ);
                        this.world.spawnEntity(entityspawn);
                        entityspawn.setEdad(30);
                        EntityPlayer entityplayer = this.world.getClosestPlayerToEntity(this, 24D);
                        if (entityplayer != null) {
                            MoCTools.tameWithName(entityplayer, entityspawn);
                        }
                    }

                    else if (getEggType() >= 80 && getEggType() < (80 + MoCEntitySmallFish.fishNames.length)) // smallfish
                    {
                        final int type = getEggType() - 79;
                        MoCEntitySmallFish entityspawn = MoCEntitySmallFish.createEntity(this.world, type);
                        entityspawn.setPosition(this.posX, this.posY, this.posZ);
                        this.world.spawnEntity(entityspawn);
                        entityspawn.setEdad(30);
                        EntityPlayer entityplayer = this.world.getClosestPlayerToEntity(this, 24D);
                        if (entityplayer != null) {
                            MoCTools.tameWithName(entityplayer, entityspawn);
                        }
                    }

                    else if (getEggType() >= 70 && getEggType() < (70 + MoCEntityMediumFish.fishNames.length)) // mediumfish
                    {
                        final int type = getEggType() - 69;
                        MoCEntityMediumFish entityspawn = MoCEntityMediumFish.createEntity(this.world, type);
                        entityspawn.setPosition(this.posX, this.posY, this.posZ);
                        this.world.spawnEntity(entityspawn);
                        entityspawn.setEdad(30);
                        EntityPlayer entityplayer = this.world.getClosestPlayerToEntity(this, 24D);
                        if (entityplayer != null) {
                            MoCTools.tameWithName(entityplayer, entityspawn);
                        }
                    }
                    MoCTools.playCustomSound(this, SoundEvents.ENTITY_CHICKEN_EGG);
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
                        MoCEntitySnake entityspawn = new MoCEntitySnake(this.world);

                        entityspawn.setPosition(this.posX, this.posY, this.posZ);
                        entityspawn.setType(getEggType() - 20);
                        entityspawn.setEdad(50);
                        this.world.spawnEntity(entityspawn);
                        EntityPlayer entityplayer = this.world.getClosestPlayerToEntity(this, 24D);
                        if (entityplayer != null) {
                            MoCTools.tameWithName(entityplayer, entityspawn);
                        }
                    }

                    if (getEggType() == 30 || getEggType() == 31 || getEggType() == 32) // Ostriches. 30 = wild egg, 31 = stolen egg
                    {
                        MoCEntityOstrich entityspawn = new MoCEntityOstrich(this.world);
                        int typeInt = 1;
                        if (this.world.provider.doesWaterVaporize() || getEggType() == 32) {
                            typeInt = 5;
                        }
                        entityspawn.setPosition(this.posX, this.posY, this.posZ);
                        entityspawn.setType(typeInt);
                        entityspawn.setEdad(35);
                        this.world.spawnEntity(entityspawn);
                        entityspawn.setHealth(entityspawn.getMaxHealth());

                        if (getEggType() == 31)//stolen egg that hatches a tamed ostrich
                        {
                            EntityPlayer entityplayer = this.world.getClosestPlayerToEntity(this, 24D);
                            if (entityplayer != null) {
                                MoCTools.tameWithName(entityplayer, entityspawn);
                            }
                        }
                    }

                    if (getEggType() == 33) // Komodo
                    {
                        MoCEntityKomodo entityspawn = new MoCEntityKomodo(this.world);

                        entityspawn.setPosition(this.posX, this.posY, this.posZ);
                        entityspawn.setEdad(30);
                        this.world.spawnEntity(entityspawn);
                        EntityPlayer entityplayer = this.world.getClosestPlayerToEntity(this, 24D);
                        if (entityplayer != null) {
                            MoCTools.tameWithName(entityplayer, entityspawn);
                        }
                    }

                    if (getEggType() > 40 && getEggType() < 46) //scorpions for now it uses 41 - 45
                    {
                        MoCEntityPetScorpion entityspawn = new MoCEntityPetScorpion(this.world);
                        int typeInt = getEggType() - 40;
                        entityspawn.setPosition(this.posX, this.posY, this.posZ);
                        entityspawn.setType(typeInt);
                        entityspawn.setAdult(false);
                        this.world.spawnEntity(entityspawn);
                        entityspawn.setHealth(entityspawn.getMaxHealth());
                        EntityPlayer entityplayer = this.world.getClosestPlayerToEntity(this, 24D);
                        if (entityplayer != null) {
                            MoCTools.tameWithName(entityplayer, entityspawn);
                        }
                    }

                    if (getEggType() > 49 && getEggType() < 62) //wyverns for now it uses 50 - 61
                    {
                        MoCEntityWyvern entityspawn = new MoCEntityWyvern(this.world);
                        int typeInt = getEggType() - 49;
                        entityspawn.setPosition(this.posX, this.posY, this.posZ);
                        entityspawn.setType(typeInt);
                        entityspawn.setAdult(false);
                        entityspawn.setEdad(30);
                        this.world.spawnEntity(entityspawn);
                        entityspawn.setHealth(entityspawn.getMaxHealth());
                        EntityPlayer entityplayer = this.world.getClosestPlayerToEntity(this, 24D);
                        if (entityplayer != null) {
                            MoCTools.tameWithName(entityplayer, entityspawn);
                        }
                    }
                    if (getEggType() > 61 && getEggType() < 66) //manticorePets for now it uses 62 - 65
                    {
                        MoCEntityManticorePet entityspawn = new MoCEntityManticorePet(this.world);
                        int typeInt = getEggType() - 61;
                        entityspawn.setPosition(this.posX, this.posY, this.posZ);
                        entityspawn.setType(typeInt);
                        entityspawn.setAdult(false);
                        entityspawn.setEdad(30);
                        this.world.spawnEntity(entityspawn);
                        entityspawn.setHealth(entityspawn.getMaxHealth());
                        EntityPlayer entityplayer = this.world.getClosestPlayerToEntity(this, 24D);
                        if (entityplayer != null) {
                            MoCTools.tameWithName(entityplayer, entityspawn);
                        }
                    }
                    MoCTools.playCustomSound(this, SoundEvents.ENTITY_CHICKEN_EGG);
                    setDead();
                }
            }
        }
    }

    private void NotifyEggHatching() {
        EntityPlayer entityplayer = this.world.getClosestPlayerToEntity(this, 24D);
        if (entityplayer != null) {
            entityplayer.sendMessage(new TextComponentTranslation("Egg hatching soon! KEEP WATCH! The hatched creature located @ "
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
