package drzhark.mocreatures.entity.passive;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCloth;
import net.minecraft.block.BlockJukeBox;
import net.minecraft.block.StepSound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.tileentity.TileEntityRecordPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import drzhark.mocreatures.inventory.MoCAnimalChest;
import drzhark.mocreatures.network.MoCServerPacketHandler;

public class MoCEntityHorse extends MoCEntityAnimal {
    private int gestationtime;
    private int countEating;
    private int textCounter;
    private int fCounter;
    public int shuffleCounter;
    public int wingFlapCounter;

    private float transFloat = 0.2F;

    public MoCAnimalChest localhorsechest;
    public boolean eatenpumpkin;

    private boolean hasReproduced;
    private int nightmareInt;

    public ItemStack localstack;

    public int mouthCounter;
    public int standCounter;
    public int tailCounter;
    public int vanishCounter;
    public int sprintCounter;
    public int transformType;
    public int transformCounter;

    public MoCEntityHorse(World world)
    {
        super(world);
        setSize(1.4F, 1.6F);
        health = 20;
        //setInventorySize(9);
        gestationtime = 0;
        eatenpumpkin = false;
        nightmareInt = 0;
        isImmuneToFire = false;
        setEdad(50);
        setChestedHorse(false);
        roper = null;
        this.stepHeight = 1.0F;

        if (MoCreatures.isServer())
        {
            // setMaxHealth(20);

            if (rand.nextInt(5) == 0)
            {
                setAdult(false);

            }
            else
            {
                setAdult(true);
            }
        }
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(22, Byte.valueOf((byte) 0)); // isRideable - 0 false 1 true
        dataWatcher.addObject(23, Byte.valueOf((byte) 0)); // isChestedHorse - 0 false 1 true
        dataWatcher.addObject(24, Byte.valueOf((byte) 0)); // Eating - 0 false 1 true
        dataWatcher.addObject(25, Integer.valueOf(0)); // armor 0 by default, 1 metal, 2 gold, 3 diamond, 4 crystaline
        dataWatcher.addObject(26, Byte.valueOf((byte) 0)); // Bred - 0 false 1 true
        //dataWatcher.addObject(27, Integer.valueOf(9)); //inventory size
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, int i)
    {

        Entity entity = damagesource.getEntity();
        if ((riddenByEntity != null) && (entity == riddenByEntity)) { return false; }
        if (entity instanceof EntityWolf)
        {
            EntityCreature entitycreature = (EntityCreature) entity;
            entitycreature.setAttackTarget(null);
            return false;
        }
        else
        {
            i = i - (getArmorType() + 2);
            if (i < 0)
            {
                i = 0;
            }
            return super.attackEntityFrom(damagesource, i);
        }
    }

    @Override
    public boolean canBeCollidedWith()
    {

        return riddenByEntity == null;
    }

    @Override
    public boolean checkSpawningBiome()
    {
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(boundingBox.minY);
        int k = MathHelper.floor_double(posZ);

        String s = MoCTools.BiomeName(worldObj, i, j, k);

        if (s.equals("Plains"))
        {
            if (rand.nextInt(3) == 0)
            {
                setType(60);// zebra
            }
        }

        selectType();
        return true;
    }

    /**
     * returns one of the RGB color codes
     * 
     * @param sColor
     *            : 1 will return the Red component, 2 will return the Green and
     *            3 the blue
     * @param typeInt
     *            : which set of colors to inquiry about, corresponds with the
     *            horse types.
     * @return
     */
    public float colorFX(int sColor, int typeInt)
    {
        if (typeInt == 48) // yellow
        {
            if (sColor == 1) { return (float) 179 / 256; }
            if (sColor == 2) { return (float) 160 / 256; }
            return (float) 22 / 256;
        }
        
        if (typeInt == 49) // purple
        {
            if (sColor == 1) { return (float) 147 / 256; }
            if (sColor == 2) { return (float) 90 / 256; }
            return (float) 195 / 256;
        }

        if (typeInt == 51) // blue
        {
            if (sColor == 1) { return (float) 30 / 256; }
            if (sColor == 2) { return (float) 144 / 256; }
            return (float) 255 / 256;
        }
        if (typeInt == 52) // pink
        {
            if (sColor == 1) { return (float) 255 / 256; }
            if (sColor == 2) { return (float) 105 / 256; }
            return (float) 180 / 256;
        }

        if (typeInt == 53) // lightgreen
        {
            if (sColor == 1) { return (float) 188 / 256; }
            if (sColor == 2) { return (float) 238 / 256; }
            return (float) 104 / 256;
        }
        
        if (typeInt == 54) // black fairy
        {
            if (sColor == 1) { return (float) 110 / 256; }
            if (sColor == 2) { return (float) 123 / 256; }
            return (float) 139 / 256;
        }
        
        if (typeInt == 55) // red fairy
        {
            if (sColor == 1) { return (float) 194 / 256; }
            if (sColor == 2) { return (float) 29 / 256; }
            return (float) 34 / 256;
        }
        
        if (typeInt == 56) // dark blue fairy
        {
            if (sColor == 1) { return (float) 63 / 256; }
            if (sColor == 2) { return (float) 45 / 256; }
            return (float) 255 / 256;
        }
        
        if (typeInt == 57) // cyan
        {
            if (sColor == 1) { return (float) 69 / 256; }
            if (sColor == 2) { return (float) 146 / 256; }
            return (float) 145 / 256;
        }

        if (typeInt == 58) // green
        {
            if (sColor == 1) { return (float) 90 / 256; }
            if (sColor == 2) { return (float) 136 / 256; }
            return (float) 43 / 256;
        }
        
        if (typeInt == 59) // orange
        {
            if (sColor == 1) { return (float) 218 / 256; }
            if (sColor == 2) { return (float) 40 / 256; }
            return (float) 0 / 256;
        }
        
        if (typeInt > 22 && typeInt < 26) // green for undeads
        {
            if (sColor == 1) { return (float) 60 / 256; }
            if (sColor == 2) { return (float) 179 / 256; }
            return (float) 112 / 256;

        }
        if (typeInt == 40) // dark red for black pegasus
        {
            if (sColor == 1) { return (float) 139 / 256; }
            if (sColor == 2) { return 0F; }
            return 0F;

        }

        // by default will return clear gold
        if (sColor == 1) { return (float) 255 / 256; }
        if (sColor == 2) { return (float) 236 / 256; }
        return (float) 139 / 256;

    }

    /**
     * Called to vanish a Horse without FX
     */
    public void dissapearHorse()
    {
        this.isDead = true;
    }

    private void drinkingHorse()
    {
        openMouth();
        MoCTools.playCustomSound(this, "drinking", worldObj);
        //worldObj.playSoundAtEntity(this, "drinking", 1.0F, 1.0F + ((rand.nextFloat() - rand.nextFloat()) * 0.2F));
    }

    //moved to MoCTools and modified for nbbt amulets
    /*private void dropAmulet()
    {
        if (MoCreatures.isServer())
        {
            if (this.getType() == 26 || this.getType() == 27 || this.getType() == 28)
            {
                EntityItem entityitem = new EntityItem(worldObj, posX, posY, posZ, new ItemStack(MoCreatures.amuletbonefull, 1, this.getType()));
                entityitem.delayBeforeCanPickup = 20;
                
                worldObj.spawnEntityInWorld(entityitem);
            }

            if (this.getType() > 49 && this.getType() < 60)
            {
                EntityItem entityitem = new EntityItem(worldObj, posX, posY, posZ, new ItemStack(MoCreatures.amuletfairyfull, 1, this.getType()));
                entityitem.delayBeforeCanPickup = 20;
                worldObj.spawnEntityInWorld(entityitem);
            }

            if (this.getType() == 39 || this.getType() == 40)
            {
                EntityItem entityitem = new EntityItem(worldObj, posX, posY, posZ, new ItemStack(MoCreatures.amuletpegasusfull, 1, this.getType()));
                entityitem.delayBeforeCanPickup = 20;
                worldObj.spawnEntityInWorld(entityitem);
            }

            if (this.getType() == 21 || this.getType() == 22)
            {
                EntityItem entityitem = new EntityItem(worldObj, posX, posY, posZ, new ItemStack(MoCreatures.amuletghostfull, 1, this.getType()));
                entityitem.delayBeforeCanPickup = 20;
                worldObj.spawnEntityInWorld(entityitem);
            }

        }
    }*/

    /**
     * Drops the current armor if the horse has one
     */
    public void dropArmor()
    {
        if (MoCreatures.isServer())
        {
            int i = getArmorType();
            if (i != 0)
            {
                MoCTools.playCustomSound(this, "armoroff", worldObj);
            }

            if (i == 1)
            {
                EntityItem entityitem = new EntityItem(worldObj, this.posX, this.posY, this.posZ, new ItemStack(MoCreatures.horsearmormetal, 1));
                entityitem.delayBeforeCanPickup = 10;
                worldObj.spawnEntityInWorld(entityitem);
            }
            if (i == 2)
            {
                EntityItem entityitem = new EntityItem(worldObj, this.posX, this.posY, this.posZ, new ItemStack(MoCreatures.horsearmorgold, 1));
                entityitem.delayBeforeCanPickup = 10;
                worldObj.spawnEntityInWorld(entityitem);
            }
            if (i == 3)
            {
                EntityItem entityitem = new EntityItem(worldObj, this.posX, this.posY, this.posZ, new ItemStack(MoCreatures.horsearmordiamond, 1));
                entityitem.delayBeforeCanPickup = 10;
                worldObj.spawnEntityInWorld(entityitem);
            }
            if (i == 4)
            {
                EntityItem entityitem = new EntityItem(worldObj, this.posX, this.posY, this.posZ, new ItemStack(MoCreatures.horsearmorcrystal, 1));
                entityitem.delayBeforeCanPickup = 10;
                worldObj.spawnEntityInWorld(entityitem);
            }
            setArmorType((byte) 0);
        }
    }

    /**
     * Drops a chest block if the horse is bagged
     */
    public void dropBags()
    {
        if (!isBagger() || !getChestedHorse() || !MoCreatures.isServer()) { return; }

        EntityItem entityitem = new EntityItem(worldObj, posX, posY, posZ, new ItemStack(Block.chest, 1));
        float f3 = 0.05F;
        entityitem.motionX = (float) worldObj.rand.nextGaussian() * f3;
        entityitem.motionY = ((float) worldObj.rand.nextGaussian() * f3) + 0.2F;
        entityitem.motionZ = (float) worldObj.rand.nextGaussian() * f3;
        worldObj.spawnEntityInWorld(entityitem);
        setChestedHorse(false);
    }

    private void eatingHorse()
    {
        openMouth();
        MoCTools.playCustomSound(this, "eating", worldObj);
        // worldObj.playSoundAtEntity(this, "eating", 1.0F, 1.0F +
        // ((rand.nextFloat() - rand.nextFloat()) * 0.2F));
    }

    @Override
    protected void fall(float f)
    {
        if (isFlyer() || isFloater()) { return; }

        int i = (int) Math.ceil(f - 3F);
        if ((i > 0)) // && (type != 8))
        {
            if (getType() >= 10)
            {
                i /= 3;
            }
            if (i > 0)
            {
                attackEntityFrom(DamageSource.fall, i);
            }
            if ((riddenByEntity != null) && (i > 0))
            {
                riddenByEntity.attackEntityFrom(DamageSource.fall, i);
            }

            int j = worldObj.getBlockId(MathHelper.floor_double(posX), MathHelper.floor_double(posY - 0.20000000298023221D - prevRotationPitch), MathHelper.floor_double(posZ));
            if (j > 0)
            {
                StepSound stepsound = Block.blocksList[j].stepSound;
                worldObj.playSoundAtEntity(this, stepsound.getStepSound(), stepsound.getVolume() * 0.5F, stepsound.getPitch() * 0.75F);
            }
        }
    }

    @Override
    public byte getArmorType()
    {
        return (byte) dataWatcher.getWatchableObjectInt(25);
    }

    public int getInventorySize()
    {
        if (getType() == 40)
        {
            return 18;
        }
        else if (getType() > 64) { return 27; }
        return 9;
    }

    @Override
    public boolean getCanSpawnHere()
    {
        checkSpawningBiome();
        return (MoCreatures.proxy.getFrequency(this.getEntityName()) > 0) && super.getCanSpawnHere();
    }

    public boolean getChestedHorse()
    {
        return (dataWatcher.getWatchableObjectByte(23) == 1);
    }

    protected MoCEntityHorse getClosestMommy(Entity entity, double d)
    {
        double d1 = -1D;
        MoCEntityHorse entityliving = null;
        List list = worldObj.getEntitiesWithinAABBExcludingEntity(entity, entity.boundingBox.expand(d, d, d));
        for (int i = 0; i < list.size(); i++)
        {
            Entity entity1 = (Entity) list.get(i);
            if ((!(entity1 instanceof MoCEntityHorse)) || ((entity1 instanceof MoCEntityHorse) && !((MoCEntityHorse) entity1).getHasBred()))
            {
                continue;
            }

            double d2 = entity1.getDistanceSq(entity.posX, entity.posY, entity.posZ);
            if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1)))
            {
                d1 = d2;
                entityliving = (MoCEntityHorse) entity1;
            }
        }

        return entityliving;
    }

    @Override
    public double getCustomJump()
    {
        double HorseJump = 0.4D;
        if (getType() < 6) // tier 1
        {
            HorseJump = 0.4;
        }
        else if (getType() > 5 && getType() < 11) // tier 2
        {
            HorseJump = 0.45D;
        }
        else if (getType() > 10 && getType() < 16) // tier 3
        {
            HorseJump = 0.5D;
        }
        else if (getType() > 15 && getType() < 21) // tier 4
        {
            HorseJump = 0.55D;
        }

        else if (getType() > 20 && getType() < 26) // ghost and undead
        {
            HorseJump = 0.45D;
        }
        else if (getType() > 25 && getType() < 30) // skely
        {
            HorseJump = 0.5D;
        }
        else if (getType() >= 30 && getType() < 40) // magics
        {
            HorseJump = 0.55D;
        }
        else if (getType() >= 40 && getType() < 60) // black pegasus and
                                                    // fairies
        {
            HorseJump = 0.6D;
        }
        else if (getType() >= 60) // donkeys - zebras and the like
        {
            HorseJump = 0.45D;
        }
        return HorseJump;
    }

    @Override
    public double getCustomSpeed()
    {
        double HorseSpeed = 0.8D;
        if (getType() < 6) // tier 1
        {
            HorseSpeed = 0.9;
        }
        else if (getType() > 5 && getType() < 11) // tier 2
        {
            HorseSpeed = 1.0D;
        }
        else if (getType() > 10 && getType() < 16) // tier 3
        {
            HorseSpeed = 1.1D;
        }
        else if (getType() > 15 && getType() < 21) // tier 4
        {
            HorseSpeed = 1.2D;
        }

        else if (getType() > 20 && getType() < 26) // ghost and undead
        {
            HorseSpeed = 0.8D;
        }
        else if (getType() > 25 && getType() < 30) // skely
        {
            HorseSpeed = 1.0D;
        }
        else if (getType() > 30 && getType() < 40) // magics
        {
            HorseSpeed = 1.2D;
        }
        else if (getType() > 40 && getType() < 60) // black pegasus and
                                                    // fairies
        {
            HorseSpeed = 1.3D;
        }
        else if (getType() == 60 || getType() == 61) // zebras and zorse
        {
            HorseSpeed = 1.1D;
        }
        else if (getType() == 65) // donkeys
        {
            HorseSpeed = 0.7D;
        }
        else if (getType() > 65) // mule and zorky
        {
            HorseSpeed = 0.9D;
        }
        if (sprintCounter > 0 && sprintCounter < 150)
        {
            HorseSpeed *= 1.5D;
        }
        if (sprintCounter > 150)
        {
            HorseSpeed *= 0.5D;
        }
        return HorseSpeed;
    }

    @Override
    protected String getDeathSound()
    {
        openMouth();
        if (this.isUndead()) { return "horsedyingundead"; }
        if (this.isGhost()) { return "horsedyingghost"; }
        if (this.getType() == 60 || this.getType() == 61) { return "zebrahurt"; }
        if (this.getType() >= 65 && this.getType() <= 67) { return "donkeydying"; }
        return "horsedying";
    }

    @Override
    public boolean getDisplayName()
    {
        if (isGhost() && getEdad() < 10) { return false; }

        return (getName() != null && !getName().equals(""));
    }

    @Override
    protected int getDropItemId()
    {
        boolean flag = (rand.nextInt(4) == 0);

        if (flag && (this.getType() == 36 || (this.getType() >= 50 && this.getType() < 60))) // unicorn
        { return MoCreatures.unicorn.itemID; }
        if (this.getType() == 39) // pegasus
        { return Item.feather.itemID; }
        if (this.getType() == 40) // dark pegasus
        { return Item.feather.itemID; }
        if (this.getType() == 38 && flag && worldObj.provider.isHellWorld) // nightmare
        { return MoCreatures.heartfire.itemID; }
        if (this.getType() == 32 && flag) // bat horse
        { return MoCreatures.heartdarkness.itemID; }
        if (this.getType() == 26)// skely
        { return Item.bone.itemID; }
        if ((this.getType() == 23 || this.getType() == 24 || this.getType() == 25))
        {
            if (flag) { return MoCreatures.heartundead.itemID; }
            return Item.rottenFlesh.itemID;
        }
        if (this.getType() == 21 || this.getType() == 22) { return Item.ghastTear.itemID; }

        return Item.leather.itemID;
    }

    public boolean getEating()
    {
        return (dataWatcher.getWatchableObjectByte(24) == 1);
    }

    public boolean getHasBred()
    {
        return (dataWatcher.getWatchableObjectByte(26) == 1);
    }

    public boolean getHasReproduced()
    {
        return hasReproduced;
    }

    @Override
    protected String getHurtSound()
    {
        openMouth();
        if (isFlyer() && riddenByEntity == null)
        {
            wingFlap();
        }
        else
        {
            if (rand.nextInt(3) == 0)
            {
                stand();
            }
        }
        if (this.isUndead()) { return "horsehurtundead"; }
        if (this.isGhost()) { return "horsehurtghost"; }
        if (this.getType() == 60 || this.getType() == 61) { return "zebrahurt"; }
        if (this.getType() >= 65 && this.getType() <= 67) { return "donkeyhurt"; }
        return "horsehurt";
    }

    public boolean getIsRideable()
    {
        return (dataWatcher.getWatchableObjectByte(22) == 1);
    }

    @Override
    protected String getLivingSound()
    {
        openMouth();
        if (rand.nextInt(10) == 0 && !isMovementCeased())
        {
            stand();
        }
        if (this.isUndead()) { return "horsegruntundead"; }
        if (this.isGhost()) { return "horsegruntghost"; }
        if (this.getType() == 60 || this.getType() == 61) { return "zebragrunt"; }
        if (this.getType() >= 65 && this.getType() <= 67) { return "donkeygrunt"; }
        return "horsegrunt";
    }

    /**
     * sound played when an untamed mount buckles rider
     */
    @Override
    protected String getMadSound()
    {
        openMouth();
        stand();
        if (this.isUndead()) { return "horsemadundead"; }
        if (this.isGhost()) { return "horsemadghost"; }
        if (this.getType() == 60 || this.getType() == 61) { return "zebrahurt"; }
        if (this.getType() >= 65 && this.getType() <= 67) { return "donkeyhurt"; }
        return "horsemad";
    }

    @Override
    public int getMaxHealth()
    {
        int maximumHealth = 10;
        if (getType() < 6) // tier 1
        {
            maximumHealth = 15;
        }
        else if (getType() > 5 && getType() < 11) // tier 2
        {
            maximumHealth = 20;
        }
        else if (getType() > 10 && getType() < 16) // tier 3
        {
            maximumHealth = 25;
        }
        else if (getType() > 15 && getType() < 21) // tier 4
        {
            maximumHealth = 25;
        }

        else if (getType() > 20 && getType() < 26) // ghost and undead
        {
            maximumHealth = 25;
        }
        else if (getType() > 25 && getType() < 30) // skely
        {
            maximumHealth = 15;
        }
        else if (getType() >= 30 && getType() < 40) // magics
        {
            maximumHealth = 30;
        }
        else if (getType() == 40) // black pegasus
        {
            maximumHealth = 40;
        }
        else if (getType() > 40 && getType() < 60) // fairies
        {
            maximumHealth = 20;
        }
        else if (getType() >= 60) // donkeys - zebras and the like
        {
            maximumHealth = 20;
        }

        return maximumHealth;
    }

    @Override
    public int getMaxSpawnedInChunk()
    {
        return 6;
    }

    /**
     * How difficult is the creature to be tamed? the Higher the number, the
     * more difficult
     */
    @Override
    public int getMaxTemper()
    {

        if (getType() == 60) { return 200; // zebras are harder to tame
        }
        return 100;
    }

    public int getNightmareInt()
    {
        return nightmareInt;
    }

    @Override
    protected float getSoundVolume()
    {
        return 0.8F;
    }

    @Override
    public int getTalkInterval()
    {
        return 400;
    }

    /**
     * Overridden for the dynamic nightmare texture.
     */
    @Override
    public String getTexture()
    {
        String tempTexture;

        switch (getType())
        {
        case 1:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horsewhite.png";
            break;
        case 2:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horsecreamy.png";
            break;
        case 3:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horsebrown.png";
            break;
        case 4:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horsedarkbrown.png";
            break;
        case 5:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horseblack.png";
            break;
        case 6:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horsebrightcreamy.png";
            break;
        case 7:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horsespeckled.png";
            break;
        case 8:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horsepalebrown.png";
            break;
        case 9:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horsegrey.png";
            break;
        case 11:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horsepinto.png";
            break;
        case 12:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horsebrightpinto.png";
            break;
        case 13:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horsepalespeckles.png";
            break;
        case 16:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horsespotted.png";
            break;
        case 17:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horsecow.png";
            break;

        case 21:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horseghost.png";
            break;
        case 22:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horseghostb.png";
            break;
        case 23:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horseundead.png";
            break;
        case 24:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horseundeadunicorn.png";
            break;
        case 25:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horseundeadpegasus.png";
            break;
        case 26:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horseskeleton.png";
            break;
        case 27:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horseunicornskeleton.png";
            break;
        case 28:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horsepegasusskeleton.png";
            break;
        case 30:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horsebug.png";
            break;
        case 32:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horsebat.png";
            break;
        case 36:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horseunicorn.png";
            break;
        case 38:
            isImmuneToFire = true;
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horsenightmare.png";
            break;
        case 39:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horsepegasus.png";
            break;
        case 40:
            isImmuneToFire = true;
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horsedarkpegasus.png";
            break;
            /*
        case 44:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horsefairydarkblue.png";
            break;
        case 45:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horsefairydarkblue.png";
            break;
        case 46:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horsefairydarkblue.png";
            break;
            
        case 47:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horsefairydarkblue.png";
            break;*/
        case 48:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horsefairyyellow.png";
            break;
        case 49:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horsefairypurple.png";
            break;
        case 50:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horsefairywhite.png";
            break;
        case 51:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horsefairyblue.png";
            break;
        case 52:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horsefairypink.png";
            break;
        case 53:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horsefairylightgreen.png";
            break;
        case 54:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horsefairyblack.png";
            break;
        case 55:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horsefairyred.png";
            break;
        case 56:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horsefairydarkblue.png";
            break;
        case 57:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horsefairycyan.png";
            break;
        case 58:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horsefairygreen.png";
            break;
        case 59:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horsefairyorange.png";
            break;
        
        case 60:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horsezebra.png";
            break;
        case 61:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horsezorse.png";
            break;
        case 65:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horsedonkey.png";
            break;
        case 66:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horsemule.png";
            break;
        case 67:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horsezonky.png";
            break;

        default:
            tempTexture = MoCreatures.proxy.MODEL_TEXTURE + "horsebug.png";
        }

        if ((isArmored() || isMagicHorse()) && getArmorType() > 0)
        {
            String armorTex = "";
            if (getArmorType() == 1)
            {
                armorTex = "metal.png";
            }
            if (getArmorType() == 2)
            {
                armorTex = "gold.png";
            }
            if (getArmorType() == 3)
            {
                armorTex = "diamond.png";
            }
            if (getArmorType() == 4)
            {
                armorTex = "crystaline.png";
            }
            return tempTexture.replace(".png", armorTex);
        }

        
        if (this.isUndead() && this.getType() < 26)
        {
            String baseTex = MoCreatures.proxy.MODEL_TEXTURE + "horseundead";
            int max = 79;
            if (this.getType() == 25) // undead pegasus
            {
                baseTex = MoCreatures.proxy.MODEL_TEXTURE + "horseundeadpegasus";
                // max = 79; //undead pegasus have an extra animation

            }
            if (this.getType() == 24)// undead unicorn
            {
                baseTex = MoCreatures.proxy.MODEL_TEXTURE + "horseundeadunicorn";
                max = 69; // undead unicorn have an animation less
            }
            
            String iteratorTex = "1";
            if (MoCreatures.proxy.getAnimateTextures())
            {
            	if (rand.nextInt(3) == 0)
                {
                    textCounter++;
                }
                if (textCounter < 10)
                {
                    textCounter = 10;
                }
                if (textCounter > max)
                {
                    textCounter = 10;
                }
                iteratorTex = "" + textCounter;
                iteratorTex = iteratorTex.substring(0, 1);
            }
           
            String decayTex = "" + (getEdad() / 100);
            decayTex = decayTex.substring(0, 1);
            return baseTex + decayTex + iteratorTex + ".png";
        }
        
        // if animate textures is off, return plain textures
        if (!MoCreatures.proxy.getAnimateTextures()) { return tempTexture; }

        
        if (this.isNightmare())
        {
            if (rand.nextInt(1) == 0)
            {
                textCounter++;
            }
            if (textCounter < 10)
            {
                textCounter = 10;
            }
            if (textCounter > 59)
            {
                textCounter = 10;
            }
            String NTA = MoCreatures.proxy.MODEL_TEXTURE + "horsenightmare";
            String NTB = "" + textCounter;
            NTB = NTB.substring(0, 1);
            String NTC = ".png";

            return NTA + NTB + NTC;
        }

        

        if (transformCounter != 0 && transformType != 0)
        {
            String newText = MoCreatures.proxy.MODEL_TEXTURE + "horseundead.png";
            if (transformType == 23)
            {
                newText = MoCreatures.proxy.MODEL_TEXTURE + "horseundead.png";
            }
            if (transformType == 24)
            {
                newText = MoCreatures.proxy.MODEL_TEXTURE + "horseundeadunicorn.png";
            }
            if (transformType == 25)
            {
                newText = MoCreatures.proxy.MODEL_TEXTURE + "horseundeadpegasus.png";
            }
            if (transformType == 36)
            {
                newText = MoCreatures.proxy.MODEL_TEXTURE + "horseunicorn.png";
            }
            if (transformType == 39)
            {
                newText = MoCreatures.proxy.MODEL_TEXTURE + "horsepegasus.png";
            }
            if (transformType == 40)
            {
                newText = MoCreatures.proxy.MODEL_TEXTURE + "horseblackpegasus.png";
            }
            
            if (transformType == 48)
            {
                newText = MoCreatures.proxy.MODEL_TEXTURE + "horsefairyyellow.png";
            }
            if (transformType == 49)
            {
                newText = MoCreatures.proxy.MODEL_TEXTURE + "horsefairypurple.png";
            }
            if (transformType == 50)
            {
                newText = MoCreatures.proxy.MODEL_TEXTURE + "horsefairywhite.png";
            }
            if (transformType == 51)
            {
                newText = MoCreatures.proxy.MODEL_TEXTURE + "horsefairyblue.png";
            }
            if (transformType == 52)
            {
                newText = MoCreatures.proxy.MODEL_TEXTURE + "horsefairypink.png";
            }
            if (transformType == 53)
            {
                newText = MoCreatures.proxy.MODEL_TEXTURE + "horsefairylightgreen.png";
            }
            if (transformType == 54)
            {
                newText = MoCreatures.proxy.MODEL_TEXTURE + "horsefairyblack.png";
            }
            if (transformType == 55)
            {
                newText = MoCreatures.proxy.MODEL_TEXTURE + "horsefairyred.png";
            }
            if (transformType == 56)
            {
                newText = MoCreatures.proxy.MODEL_TEXTURE + "horsefairydarkblue.png";
            }
            
            if (transformType == 57)
            {
                newText = MoCreatures.proxy.MODEL_TEXTURE + "horsefairycyan.png";
            }
            
            if (transformType == 58)
            {
                newText = MoCreatures.proxy.MODEL_TEXTURE + "horsefairygreen.png";
            }
            
            if (transformType == 59)
            {
                newText = MoCreatures.proxy.MODEL_TEXTURE + "horsefairyorange.png";
            }
            
            if (transformType == 32)
            {
                newText = MoCreatures.proxy.MODEL_TEXTURE + "horsebat.png";
            }
            if (transformType == 38)
            {
                newText = MoCreatures.proxy.MODEL_TEXTURE + "horsenightmare1.png";
            }
            if ((transformCounter % 5) == 0) { return newText; }
            if (transformCounter > 50 && (transformCounter % 3) == 0) { return newText; }

            if (transformCounter > 75 && (transformCounter % 4) == 0) { return newText; }
        }

        return tempTexture;

    }

    /**
     * New networked to fix SMP issues
     * 
     * @return
     */
    public byte getVanishC()
    {
        return (byte) vanishCounter;
    }

    /**
     * Breeding rules for the horses
     * 
     * @param entityhorse
     * @param entityhorse1
     * @return
     */
    private int HorseGenetics(MoCEntityHorse entityhorse, MoCEntityHorse entityhorse1)
    {
        boolean flag = MoCreatures.proxy.easyBreeding;
        int typeA = entityhorse.getType();
        int typeB = entityhorse1.getType();

        // identical horses have so spring
        if (typeA == typeB) { return typeA; }

        // zebras plus any horse
        if (typeA == 60 && typeB < 21 || typeB == 60 && typeA < 21) { return 61; // zorse
        }

        // dokey plus any horse
        if (typeA == 65 && typeB < 21 || typeB == 65 && typeA < 21) { return 66; // mule
        }

        // zebra plus donkey
        if (typeA == 60 && typeB == 65 || typeB == 60 && typeA == 65) { return 67; // zonky
        }

        if (typeA > 20 && typeB < 21 || typeB > 20 && typeA < 21) // rare horses plus  ordinary horse always returns ordinary horse
        {
            if (typeA < typeB) { return typeA; }
            return typeB;
        }

        // unicorn plus white pegasus (they will both vanish!)
        if (typeA == 36 && typeB == 39 || typeB == 36 && typeA == 39)
        {
            entityhorse.vanishHorse();
            entityhorse1.vanishHorse();
            return 50; // white fairy
        }

        // rare horse mixture: produces a regular horse 1-5
        if (typeA > 20 && typeB > 20 && (typeA != typeB)) { return (rand.nextInt(5)) + 1; }

        // rest of cases will return either typeA, typeB or new mix
        int chanceInt = (rand.nextInt(4)) + 1;
        if (!flag)
        {
            if (chanceInt == 1) // 25%
            {
                return typeA;
            }
            else if (chanceInt == 2) // 25%
            { return typeB; }
        }

        if ((typeA == 1 && typeB == 2) || (typeA == 2 && typeB == 1)) { return 6; }

        if ((typeA == 1 && typeB == 3) || (typeA == 3 && typeB == 1)) { return 2; }

        if ((typeA == 1 && typeB == 4) || (typeA == 4 && typeB == 1)) { return 7; }

        if ((typeA == 1 && typeB == 5) || (typeA == 5 && typeB == 1)) { return 9; }

        if ((typeA == 1 && typeB == 7) || (typeA == 7 && typeB == 1)) { return 12; }

        if ((typeA == 1 && typeB == 8) || (typeA == 8 && typeB == 1)) { return 7; }

        if ((typeA == 1 && typeB == 9) || (typeA == 9 && typeB == 1)) { return 13; }

        if ((typeA == 1 && typeB == 11) || (typeA == 11 && typeB == 1)) { return 12; }

        if ((typeA == 1 && typeB == 12) || (typeA == 12 && typeB == 1)) { return 13; }

        if ((typeA == 1 && typeB == 17) || (typeA == 17 && typeB == 1)) { return 16; }

        if ((typeA == 2 && typeB == 4) || (typeA == 4 && typeB == 2)) { return 3; }

        if ((typeA == 2 && typeB == 5) || (typeA == 5 && typeB == 2)) { return 4; }

        if ((typeA == 2 && typeB == 7) || (typeA == 7 && typeB == 2)) { return 8; }

        if ((typeA == 2 && typeB == 8) || (typeA == 8 && typeB == 2)) { return 3; }

        if ((typeA == 2 && typeB == 12) || (typeA == 12 && typeB == 2)) { return 6; }

        if ((typeA == 2 && typeB == 16) || (typeA == 16 && typeB == 2)) { return 13; }

        if ((typeA == 2 && typeB == 17) || (typeA == 17 && typeB == 2)) { return 12; }

        if ((typeA == 3 && typeB == 4) || (typeA == 4 && typeB == 3)) { return 8; }

        if ((typeA == 3 && typeB == 5) || (typeA == 5 && typeB == 3)) { return 8; }

        if ((typeA == 3 && typeB == 6) || (typeA == 6 && typeB == 3)) { return 2; }

        if ((typeA == 3 && typeB == 7) || (typeA == 7 && typeB == 3)) { return 11; }

        if ((typeA == 3 && typeB == 9) || (typeA == 9 && typeB == 3)) { return 8; }

        if ((typeA == 3 && typeB == 12) || (typeA == 12 && typeB == 3)) { return 11; }

        if ((typeA == 3 && typeB == 16) || (typeA == 16 && typeB == 3)) { return 11; }

        if ((typeA == 3 && typeB == 17) || (typeA == 17 && typeB == 3)) { return 11; }

        if ((typeA == 4 && typeB == 6) || (typeA == 6 && typeB == 4)) { return 3; }

        if ((typeA == 4 && typeB == 7) || (typeA == 7 && typeB == 4)) { return 8; }

        if ((typeA == 4 && typeB == 9) || (typeA == 9 && typeB == 4)) { return 7; }

        if ((typeA == 4 && typeB == 11) || (typeA == 11 && typeB == 4)) { return 7; }

        if ((typeA == 4 && typeB == 12) || (typeA == 12 && typeB == 4)) { return 7; }

        if ((typeA == 4 && typeB == 13) || (typeA == 13 && typeB == 4)) { return 7; }

        if ((typeA == 4 && typeB == 16) || (typeA == 16 && typeB == 4)) { return 13; }

        if ((typeA == 4 && typeB == 17) || (typeA == 17 && typeB == 4)) { return 5; }

        if ((typeA == 5 && typeB == 6) || (typeA == 6 && typeB == 5)) { return 4; }

        if ((typeA == 5 && typeB == 7) || (typeA == 7 && typeB == 5)) { return 4; }

        if ((typeA == 5 && typeB == 8) || (typeA == 8 && typeB == 5)) { return 4; }

        if ((typeA == 5 && typeB == 11) || (typeA == 11 && typeB == 5)) { return 17; }

        if ((typeA == 5 && typeB == 12) || (typeA == 12 && typeB == 5)) { return 13; }

        if ((typeA == 5 && typeB == 13) || (typeA == 13 && typeB == 5)) { return 16; }

        if ((typeA == 5 && typeB == 16) || (typeA == 16 && typeB == 5)) { return 17; }

        if ((typeA == 6 && typeB == 8) || (typeA == 8 && typeB == 6)) { return 2; }

        if ((typeA == 6 && typeB == 17) || (typeA == 17 && typeB == 6)) { return 7; }

        if ((typeA == 7 && typeB == 16) || (typeA == 16 && typeB == 7)) { return 13; }

        if ((typeA == 8 && typeB == 11) || (typeA == 11 && typeB == 8)) { return 7; }

        if ((typeA == 8 && typeB == 12) || (typeA == 12 && typeB == 8)) { return 7; }

        if ((typeA == 8 && typeB == 13) || (typeA == 13 && typeB == 8)) { return 7; }

        if ((typeA == 8 && typeB == 16) || (typeA == 16 && typeB == 8)) { return 7; }

        if ((typeA == 8 && typeB == 17) || (typeA == 17 && typeB == 8)) { return 7; }

        if ((typeA == 9 && typeB == 16) || (typeA == 16 && typeB == 9)) { return 13; }

        if ((typeA == 11 && typeB == 16) || (typeA == 16 && typeB == 11)) { return 13; }

        if ((typeA == 11 && typeB == 17) || (typeA == 17 && typeB == 11)) { return 7; }

        if ((typeA == 12 && typeB == 16) || (typeA == 16 && typeB == 12)) { return 13; }

        if ((typeA == 13 && typeB == 17) || (typeA == 17 && typeB == 13)) { return 9; }

        return typeA; // breed is not in the table so it will return the first
                        // parent type
    }

      

    @Override
    public boolean interact(EntityPlayer entityplayer)
    {
        
        
        
        if (super.interact(entityplayer)) { return false; }
        if (this.getType() == 60 && !getIsTamed() && isZebraRunning()) // zebra
        { return false; }

        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
          
        //Debugging only, not for release
        //setType(54);
        //setTamed(true);
        /*if ((itemstack != null) && itemstack.itemID == Block.dirt.blockID)
        {
        	setType(getType() + 1);
        	return true;
        }*/
        
        if ((itemstack != null) && !getIsRideable() && itemstack.itemID == Item.saddle.itemID)
        {
            entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            setRideable(true);
            return true;
        }

        if ((itemstack != null) && this.getIsTamed() && itemstack.itemID == MoCreatures.horsearmormetal.itemID && isArmored())
        {
            if (getArmorType() == 0)
            {
                MoCTools.playCustomSound(this, "armorput", worldObj);
            }
            dropArmor();
            setArmorType((byte) 1);
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }

            return true;
        }

        if ((itemstack != null) && this.getIsTamed() && itemstack.itemID == MoCreatures.horsearmorgold.itemID && isArmored())
        {
            if (getArmorType() == 0)
            {
                MoCTools.playCustomSound(this, "armorput", worldObj);
            }
            dropArmor();
            setArmorType((byte) 2);
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            return true;
        }

        if ((itemstack != null) && this.getIsTamed() && itemstack.itemID == MoCreatures.horsearmordiamond.itemID && isArmored())
        {
            if (getArmorType() == 0)
            {
                MoCTools.playCustomSound(this, "armorput", worldObj);
            }
            dropArmor();
            setArmorType((byte) 3);
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            return true;
        }

        if ((itemstack != null) && this.getIsTamed() && itemstack.itemID == MoCreatures.horsearmorcrystal.itemID && isMagicHorse())
        {
            if (getArmorType() == 0)
            {
                MoCTools.playCustomSound(this, "armorput", worldObj);
            }
            dropArmor();
            setArmorType((byte) 4);
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            return true;
        }

        // transform to undead, or heal undead horse
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

            if (this.isUndead() || isGhost())
            {
                health = getMaxHealth();

            }

            // pegasus, dark pegasus, or bat horse
            if (this.getType() == 39 || this.getType() == 32 || this.getType() == 40)
            {

                // transformType = 25; //undead pegasus
                transform(25);

            }
            else if (this.getType() == 36 || (this.getType() > 47 && this.getType() < 60)) // unicorn or fairies
            {

                // transformType = 24; //undead unicorn
                transform(24);
            }
            else if (this.getType() < 21 || this.getType() == 60 || this.getType() == 61) // regular horses or zebras
            {

                // transformType = 23; //undead
                transform(23);
            }

            drinkingHorse();
            return true;
        }

        // to transform to nightmares: only pure breeds
        if ((itemstack != null) && this.getIsTamed() && itemstack.itemID == MoCreatures.vialnightmare.itemID)
        {
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Item.glassBottle));
            }
            else
            {
                entityplayer.inventory.addItemStackToInventory(new ItemStack(Item.glassBottle));
            }

            if (this.isNightmare())
            {
                if (getIsAdult() && health == getMaxHealth())
                {
                    this.eatenpumpkin = true;
                }
                health = getMaxHealth();

            }
            if (this.getType() == 61)
            {
                //nightmare
                transform(38);
            }
            drinkingHorse();
            return true;
        }

        // transform to dark pegasus
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

            if (this.getType() == 32)
            {
                if (getIsAdult() && health == getMaxHealth())
                {
                    this.eatenpumpkin = true;
                }
                health = getMaxHealth();
            }

            if (this.getType() == 61)
            {
                //bat horse
                transform(32);
            }

            if (this.getType() == 39) // pegasus to darkpegasus
            {
                //darkpegasus
                transform(40);
            }
            drinkingHorse();
            return true;
        }

        if ((itemstack != null) && this.getIsTamed() && itemstack.itemID == MoCreatures.viallight.itemID)
        {
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Item.glassBottle));
            }
            else
            {
                entityplayer.inventory.addItemStackToInventory(new ItemStack(Item.glassBottle));
            }

            if (this.isMagicHorse())
            {
                if (getIsAdult() && health == getMaxHealth())
                {
                    this.eatenpumpkin = true;
                }
                health = getMaxHealth();
            }

            if (this.isNightmare())
            {
                // unicorn
                transform(36);
            }
            if (this.getType() == 32 && this.posY > 128D) // bathorse to pegasus
            {
                // pegasus
                transform(39);
            }
            // to return undead horses to pristine conditions
            if (this.isUndead() && this.getIsAdult() && MoCreatures.isServer())
            {
                setEdad(10);
                if (this.getType() > 26)
                {
                    setType(getType() - 3);
                }
            }
            drinkingHorse();
            return true;
        }

        if ((itemstack != null) && this.isAmuletHorse() && this.getIsTamed())
        {
            if ((this.getType() == 26 || this.getType() == 27 || this.getType() == 28) && itemstack.itemID == MoCreatures.amuletbone.itemID)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                vanishHorse();
                return true;
            }

            if ((this.getType() > 47 && this.getType() < 60) && itemstack.itemID == MoCreatures.amuletfairy.itemID)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                vanishHorse();
                return true;
            }

            if ((this.getType() == 39 || this.getType() == 40) && (itemstack.itemID == MoCreatures.amuletpegasus.itemID))
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                vanishHorse();
                return true;
            }

            if ((this.getType() == 21 || this.getType() == 22) && (itemstack.itemID == MoCreatures.amuletghost.itemID))
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                vanishHorse();
                return true;
            }

        }

        if ((itemstack != null) && (itemstack.itemID == Item.dyePowder.itemID) && this.getType() == 50)
        {

            int colorInt = BlockCloth.getBlockFromDye(itemstack.getItemDamage());
            switch (colorInt)
            {
            case 1: //orange
                transform(59);
                break;
            case 2: //magenta TODO
                //transform(46);
                break;
            case 3: //light blue
                transform(51);
                break;
            case 4: //yellow
                transform(48);
                break;
            case 5: //light green
                transform(53);
                break;
            case 6: //pink
                transform(52);
                break;
            case 7: //gray TODO
                //transform(50);
                break;
            case 8: //light gray TODO
                //transform(50);
                break;
            case 9: //cyan
                transform(57);
                break;
            case 10: //purple
                transform(49);
                break;
            case 11: //dark blue
                transform(56);
                break;
            case 12: //brown TODO
                //transform(50);
                break;
            case 13: //green
                transform(58);
                break;
            case 14: //red
                transform(55);
                break;
            case 15: //black
                transform(54);
                break;
            
            }
            
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            eatingHorse();
            return true;
        }

        // zebra easter egg
        if ((itemstack != null) && (this.getType() == 60) && ((itemstack.itemID == Item.record11.itemID) || (itemstack.itemID == Item.record13.itemID) || (itemstack.itemID == Item.recordCat.itemID) || (itemstack.itemID == Item.recordChirp.itemID) || (itemstack.itemID == Item.recordFar.itemID) || (itemstack.itemID == Item.recordMall.itemID) || (itemstack.itemID == Item.recordMellohi.itemID) || (itemstack.itemID == Item.recordStal.itemID) || (itemstack.itemID == Item.recordStrad.itemID) || (itemstack.itemID == Item.recordWard.itemID)))
        {
            entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            if (MoCreatures.isServer())
            {
                EntityItem entityitem1 = new EntityItem(worldObj, this.posX, this.posY, this.posZ, new ItemStack(MoCreatures.recordshuffle, 1));
                entityitem1.delayBeforeCanPickup = 20;
                worldObj.spawnEntityInWorld(entityitem1);
            }
            eatingHorse();
            return true;
        }
        if ((itemstack != null) && (itemstack.itemID == Item.wheat.itemID) && !isMagicHorse() && !isUndead())
        {
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            if (MoCreatures.isServer())
            {
                setTemper(getTemper() + 25);
                if (getTemper() > getMaxTemper())
                {
                    setTemper(getMaxTemper() - 5);
                }
            }
            if ((health += 5) > getMaxHealth())
            {
                health = getMaxHealth();
            }
            eatingHorse();
            if (!getIsAdult() && (getEdad() < 100))
            {
                setEdad(getEdad() + 1);
            }
            return true;
        }

        if ((itemstack != null) && (itemstack.itemID == MoCreatures.sugarlump.itemID) && !isMagicHorse() && !isUndead())
        {
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            if (MoCreatures.isServer())
            {
                setTemper(getTemper() + 25);
                if (getTemper() > getMaxTemper())
                {
                    setTemper(getMaxTemper() - 5);
                }
            }
            if ((health += 10) > getMaxHealth())
            {
                health = getMaxHealth();
            }
            eatingHorse();
            if (!getIsAdult() && (getEdad() < 100))
            {
                setEdad(getEdad() + 2);
            }
            return true;
        }

        if ((itemstack != null) && (itemstack.itemID == Item.bread.itemID) && !isMagicHorse() && !isUndead())
        {
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            if (MoCreatures.isServer())
            {
                setTemper(getTemper() + 100);
                if (getTemper() > getMaxTemper())
                {
                    setTemper(getMaxTemper() - 5);
                }
            }
            if ((health += 20) > getMaxHealth())
            {
                health = getMaxHealth();
            }
            eatingHorse();
            if (!getIsAdult() && (getEdad() < 100))
            {
                setEdad(getEdad() + 3);
            }
            return true;
        }

        if ((itemstack != null) && ((itemstack.itemID == Item.appleRed.itemID) || (itemstack.itemID == Item.appleGold.itemID)) && !isMagicHorse() && !isUndead())
        {
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            if (MoCreatures.isServer())
            {
                MoCTools.tameWithName((EntityPlayerMP) entityplayer, this);
                //TODO NAMER
                //setTamed(true);
                //if (!MoCreatures.isServer())
                //{
                //    MoCreatures.proxy.setName(entityplayer, this);
                //}
            }

            health = getMaxHealth();
            eatingHorse();
            if (!getIsAdult() && (getEdad() < 100) && MoCreatures.isServer())
            {
                setEdad(getEdad() + 1);
            }

            return true;
        }

        if ((itemstack != null) && getIsTamed() && (itemstack.itemID == Block.chest.blockID) && (isBagger()))
        {
            //System.out.println("SETTING CHESTED HORSE");
            if (getChestedHorse()) { return false; }
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }

            entityplayer.inventory.addItemStackToInventory(new ItemStack(MoCreatures.key));
            setChestedHorse(true);
            worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, ((rand.nextFloat() - rand.nextFloat()) * 0.2F) + 1.0F);
            return true;
        }
        if ((itemstack != null) && getIsTamed() && (itemstack.itemID == MoCreatures.haystack.itemID))
        {
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            // eatinghaystack = true;
            setEating(true);
            eatingHorse();
            if (!isMagicHorse() && !isUndead())
            {
                health = getMaxHealth();
            }

            return true;
        }
        if ((itemstack != null) && (itemstack.itemID == MoCreatures.key.itemID) && getChestedHorse())
        {
            /*if (getType() == 40)
            {
                setInventorySize(18);
            }
            else if (getType() > 64)
            {
                setInventorySize(27);
            }*/

            // if first time opening horse chest, we must initialize it
            if (localhorsechest == null)
            {
                localhorsechest = new MoCAnimalChest("HorseChest", getInventorySize());// , new
            }
            // only open this chest on server side
            if (!worldObj.isRemote)
            {
                entityplayer.displayGUIChest(localhorsechest);
            }
            return true;

        }
        if ((itemstack != null) && ((itemstack.itemID == Block.pumpkin.blockID) || (itemstack.itemID == Item.bowlSoup.itemID) || (itemstack.itemID == Item.cake.itemID)))
        {
            if (!getIsAdult() || isMagicHorse() || isUndead()) { return false; }
            if (itemstack.itemID == Item.bowlSoup.itemID)
            {
                if (--itemstack.stackSize == 0)
                {
                    entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Item.bowlEmpty));
                }
                else
                {
                    entityplayer.inventory.addItemStackToInventory(new ItemStack(Item.bowlEmpty));
                }
            }
            else if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            eatenpumpkin = true;
            health = getMaxHealth();
            eatingHorse();
            return true;
        }

        // TODO test nightmare whiping!
        /*
         * if ((itemstack != null) && getIsTamed() && (itemstack.itemID ==
         * Item.redstone.itemID) && (type == 7)) { if (getNightmareInt() >
         * 500) { return false; } if (--itemstack.stackSize == 0) {
         * entityplayer.
         * inventory.setInventorySlotContents(entityplayer.inventory
         * .currentItem, null); } setNightmareInt(500); eatingHorse(); return
         * true; }
         */
        if ((itemstack != null) && (itemstack.itemID == MoCreatures.whip.itemID) && getIsTamed() && (riddenByEntity == null))
        {
            setEating(!getEating());// eatinghaystack = !eatinghaystack;
            return true;
        }
        /*if ((itemstack != null) && (riddenByEntity == null) && (roper == null) && getIsTamed() && (itemstack.itemID == MoCreatures.rope.itemID))
        {
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            worldObj.playSoundAtEntity(this, "roping", 1.0F, 1.0F + ((rand.nextFloat() - rand.nextFloat()) * 0.2F));
            roper = entityplayer;
            setEating(false);
            return true;
        }

        if ((roper != null) && getIsTamed())
        {
            entityplayer.inventory.addItemStackToInventory(new ItemStack(MoCreatures.rope));
            worldObj.playSoundAtEntity(this, "roping", 1.0F, 1.0F + ((rand.nextFloat() - rand.nextFloat()) * 0.2F));
            roper = null;
            return true;
        }

        if ((itemstack != null) && getIsTamed() && ((itemstack.itemID == MoCreatures.medallion.itemID) || (itemstack.itemID == Item.book.itemID)))
        {
            if (!MoCreatures.isServer())
            {
                MoCreatures.proxy.setName(entityplayer, this);
            }
            return true;
        }*/

        if (getIsRideable() && getIsAdult() && (riddenByEntity == null))
        {
            entityplayer.rotationYaw = rotationYaw;
            entityplayer.rotationPitch = rotationPitch;
            setEating(false);
            if (MoCreatures.isServer())
            {
                entityplayer.mountEntity(this);
            }
            gestationtime = 0;
            return true;
        }
        else
        {
            return false;
        }

    }

    /**
     * Can this horse be trapped in an amulet?
     */
    public boolean isAmuletHorse()
    {

        return (this.getType() >= 48 && this.getType() < 60) || this.getType() == 40 || this.getType() == 39 || this.getType() == 21 || this.getType() == 22 || this.getType() == 26 || this.getType() == 27 || this.getType() == 28;
    }

    /**
     * Can wear regular armor
     */
    public boolean isArmored()
    {
        return (this.getType() < 21);
    }

    /**
     * able to carry bags
     * 
     * @return
     */
    public boolean isBagger()
    {
        return (this.getType() == 66) // mule
                || (this.getType() == 65) // donkey
                || (this.getType() == 67) // zonkey
                || (this.getType() == 39) // pegasi
                || (this.getType() == 40) // black pegasi
                || (this.getType() == 25) // undead pegasi
                || (this.getType() == 28) // skely pegasi
                || (this.getType() >= 45 && this.getType() < 60) // fairy
        ;
    }

    /**
     * Falls slowly
     */
    public boolean isFloater()
    {
        return this.getType() == 36 // unicorn
                || this.getType() == 27 // skely unicorn
                || this.getType() == 24 // undead unicorn
                || this.getType() == 22; // not winged ghost

    }

    @Override
    public boolean isFlyer()
    {
        return this.getType() == 39 // pegasus
                || this.getType() == 40 // dark pegasus
                || (this.getType() >= 45 && this.getType() < 60) //fairy
                || this.getType() == 32 // bat horse
                || this.getType() == 21 // ghost winged
                || this.getType() == 25 // undead pegasus
                || this.getType() == 28;// skely pegasus
    }

    /**
     * Is this a ghost horse?
     * 
     * @return
     */
    public boolean isGhost()
    {

        return this.getType() == 21 || this.getType() == 22;
    }

    /**
     * Can wear magic armor
     */
    public boolean isMagicHorse()
    {
        return

        this.getType() == 39 || this.getType() == 36 || this.getType() == 32 || this.getType() == 40 || (this.getType() >= 45 && this.getType() < 60) //fairy
                || this.getType() == 21 || this.getType() == 22;
    }

    @Override
    protected boolean isMovementCeased()
    {
        return getEating() || (riddenByEntity != null) || this.standCounter != 0 || this.shuffleCounter != 0 || this.getVanishC() != 0;
    }

    /**
     * Is this a Nightmare horse?
     */
    public boolean isNightmare()
    {

        return this.getType() == 38;
    }

    /**
     * Rare horse that can be transformed into Nightmares or Bathorses or give
     * ghost horses on dead
     */
    public boolean isPureBreed()
    {

        return (this.getType() > 10 && this.getType() < 21);
    }

    /**
     * Mobs don't attack you if you're riding one of these they won't reproduce
     * either
     * 
     * @return
     */
    public boolean isUndead()
    {
        return (this.getType() == 23) || (this.getType() == 24) || (this.getType() == 25) || (this.getType() == 26) // skely
                || (this.getType() == 27) // skely unicorn
                || (this.getType() == 28); // skely pegasus
    }

    /**
     * Has an unicorn? to render it and buckle entities!
     * 
     * @return
     */
    public boolean isUnicorned()
    {

        return this.getType() == 36 || (this.getType() >= 45 && this.getType() < 60) || this.getType() == 27 || this.getType() == 24;
    }

    public boolean isZebraRunning()
    {
        boolean flag = false;
        EntityPlayer ep1 = worldObj.getClosestPlayerToEntity(this, 8D);
        if (ep1 != null)
        {
            flag = true;
            if (ep1.ridingEntity != null && ep1.ridingEntity instanceof MoCEntityHorse)
            {
                MoCEntityHorse playerHorse = (MoCEntityHorse) ep1.ridingEntity;
                if (playerHorse.getType() == 16 || playerHorse.getType() == 17 || playerHorse.getType() == 60 || playerHorse.getType() == 61)
                {
                    flag = false;
                }
            }

        }
        if (flag)
        {
            runLikeHell(ep1);
        }
        return flag;
    }

    public void LavaFX()
    {
        MoCreatures.proxy.LavaFX(this);
    }

    public void MaterializeFX()
    {
        MoCreatures.proxy.MaterializeFX(this);
    }

    private void moveTail()
    {
        tailCounter = 1;
    }

    @Override
    public int nameYOffset()
    {
        if (this.getIsAdult())
        {
            return -80;
        }
        else
        {
            return (-5 - getEdad());
        }
    }

    private boolean nearMusicBox()
    {
        // only works server side
        if (!MoCreatures.isServer()) { return false; }

        boolean flag = false;
        TileEntityRecordPlayer jukebox = MoCTools.nearJukeBoxRecord(this, 6D);
        if (jukebox != null && jukebox.func_96097_a() != null)
        {
            int i = jukebox.func_96097_a().itemID;

            int j = MoCreatures.recordshuffle.itemID;
            if (i == j)
            {
                flag = true;
                if (shuffleCounter > 1000)
                {
                    shuffleCounter = 0;
                    MoCServerPacketHandler.sendStopShuffle(this.entityId, this.worldObj.provider.dimensionId);
                    BlockJukeBox blockjukebox = (BlockJukeBox) Block.blocksList[worldObj.getBlockId(jukebox.xCoord, jukebox.yCoord, jukebox.zCoord)];// Block.blocksList[j2].blockID;
                    if (blockjukebox != null)
                    {
                        blockjukebox.ejectRecord(worldObj, jukebox.xCoord, jukebox.yCoord, jukebox.zCoord);
                    }
                    flag = false;
                }
            }
            else
            {
                // System.out.println("Client playing r# " + i);
            }
        }
        return flag;
    }

    // changed to public since we need to send this info to server
    public void NightmareEffect()
    {
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(boundingBox.minY);
        int k = MathHelper.floor_double(posZ);
        worldObj.setBlock(i - 1, j, k - 1, Block.fire.blockID, 0, 3);//MC1.5
        EntityPlayer entityplayer = (EntityPlayer) riddenByEntity;
        if ((entityplayer != null) && (entityplayer.isBurning()))
        {
            // entityplayer.fire = 0;
            entityplayer.extinguish();
        }
        setNightmareInt(getNightmareInt() - 1);
    }

    @Override
    public void onDeath(DamageSource damagesource)
    {
        super.onDeath(damagesource);
        if (MoCreatures.isServer())
        {
            //dropMyStuff(); //moved to MoCEntityAnimal

            if ((rand.nextInt(10) == 0) && (this.getType() == 23) || (this.getType() == 24) || (this.getType() == 25))
            {
                MoCTools.spawnMaggots(worldObj, this);
            }

            if (getIsTamed() && (isMagicHorse() || isPureBreed()) && !isGhost() && rand.nextInt(4) == 0)
            {
                MoCEntityHorse entityhorse1 = new MoCEntityHorse(worldObj);
                entityhorse1.setPosition(posX, posY, posZ);
                worldObj.spawnEntityInWorld(entityhorse1);
                MoCTools.playCustomSound(this, "appearmagic", worldObj);
                // worldObj.playSoundAtEntity(this, "appearmagic", 1.0F,
                // ((rand.nextFloat() - rand.nextFloat()) * 0.2F) + 1.0F);
                //TODO NAMER
                entityhorse1.setOwner(this.getOwnerName());
                entityhorse1.setTamed(true);
                EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, 24D);
                if (entityplayer != null && (MoCreatures.isServer()))
                {
                    MoCTools.tameWithName((EntityPlayerMP) entityplayer, entityhorse1);
                }
                //entityhorse1.setTamed(true);
                entityhorse1.setAdult(false);
                entityhorse1.setEdad(1);
                //entityhorse1.setRideable(this.getIsRideable());
                //entityhorse1.setName(this.getName());
                int l = 22;
                if (this.isFlyer())
                {
                    l = 21;
                }
                entityhorse1.setType(l);
            }
            
        }
    }

    @Override
    public void onLivingUpdate()
    {
        /**
         * slow falling
         */
        if (isFlyer() || isFloater())
        {
            if (!onGround && (motionY < 0.0D))
            {
                motionY *= 0.6D;
            }
        }

        if ((this.jumpPending))
        {
            if (isFlyer() && wingFlapCounter == 0)
            {
                MoCTools.playCustomSound(this, "wingflap", worldObj);
            }
            wingFlapCounter = 1;
        }

        if (rand.nextInt(200) == 0)
        {
            moveTail();
        }

        if ((getType() == 38) && (rand.nextInt(50) == 0) && !MoCreatures.isServer())
        {
            LavaFX();
        }

        if ((getType() == 36) && isOnAir() && !MoCreatures.isServer())
        {
            StarFX();
        }

        if (isOnAir() && isFlyer() && rand.nextInt(30) == 0)
        {
            wingFlapCounter = 1;
        }

        if (isUndead() && (this.getType() < 26) && getIsAdult() && (rand.nextInt(20) == 0))
        {
            if (MoCreatures.isServer())
            {
                if (rand.nextInt(16) == 0)
                {
                    setEdad(getEdad() + 1);
                }
                if (getEdad() >= 399)
                {
                    setType(this.getType() + 3);
                }
            }
            else
            {
                UndeadFX();
            }

        }

        super.onLivingUpdate();

        if (MoCreatures.isServer())
        {

            //Riding();

            /**
             * Shuffling LMFAO!
             */
            if (this.getType() == 60 && getIsTamed() && rand.nextInt(50) == 0 && nearMusicBox())
            {
                shuffle();
                MoCServerPacketHandler.sendShufflePacket(this.entityId, this.worldObj.provider.dimensionId);
            }

            if ((rand.nextInt(300) == 0) && (deathTime == 0))
            {
                health++;
                if (health > getMaxHealth())
                {
                    health = getMaxHealth();
                }
            }

            if (!getEating() && !getIsTamed() && rand.nextInt(300) == 0)
            {
                setEating(true);
            }

            if (getEating() && ++countEating > 50 && !getIsTamed())
            {
                countEating = 0;
                setEating(false);
            }

            if ((getType() == 38) && (riddenByEntity != null) && (getNightmareInt() > 0) && (rand.nextInt(2) == 0))
            {
                NightmareEffect();
            }

            /**
             * zebras on the run!
             */
            if (this.getType() == 60 && !getIsTamed())
            {
                boolean flag = isZebraRunning();

            }

            /**
             * foal following mommy!
             */
            if (!getIsAdult() && (rand.nextInt(200) == 0))
            {
                setEdad(getEdad() + 1);
                if (getEdad() >= 100)
                {
                    setAdult(true);
                    setBred(false);
                    MoCEntityHorse mommy = getClosestMommy(this, 16D);
                    if (mommy != null)
                    {
                        mommy.setBred(false);
                    }
                }
            }

            // TODO test in MP or move out of this !isRemote
            /**
             * Buckling
             */
            if ((sprintCounter > 0 && sprintCounter < 150) && isUnicorned() && (riddenByEntity != null))
            {
                MoCTools.buckleMobs(this, 2D, worldObj);
            }

            if (isFlyer() && rand.nextInt(100) == 0 && !isMovementCeased() && !getEating())
            {
                wingFlap();
            }

            if (getHasBred() && !getIsAdult() && (roper == null) && !getEating())
            {

                MoCEntityHorse mommy = getClosestMommy(this, 16D);
                if ((mommy != null) && (MoCTools.getSqDistanceTo(mommy, this.posX, this.posY, this.posZ) > 4D))
                {
                    // System.out.println("following mommy!");
                    PathEntity pathentity = worldObj.getPathEntityToEntity(this, mommy, 16F, true, false, false, true);
                    setPathToEntity(pathentity);
                }

            }

            if (!ReadyforParenting(this)) { return; }

            int i = 0;

            List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(8D, 3D, 8D));
            for (int j = 0; j < list.size(); j++)
            {
                Entity entity = (Entity) list.get(j);
                if (entity instanceof MoCEntityHorse)
                {
                    i++;
                }
            }

            if (i > 1) { return; }
            List list1 = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(4D, 2D, 4D));
            for (int k = 0; k < list1.size(); k++)
            {
                Entity entity1 = (Entity) list1.get(k);
                if (!(entity1 instanceof MoCEntityHorse) || (entity1 == this))
                {
                    continue;
                }
                MoCEntityHorse entityhorse = (MoCEntityHorse) entity1;
                if (!ReadyforParenting(this) || !ReadyforParenting(entityhorse))
                {
                    return;
                }
                if (rand.nextInt(100) == 0)
                {
                    gestationtime++;
                }

                if (this.gestationtime % 3 == 0)
                {
                    MoCServerPacketHandler.sendHeartPacket(this.entityId, this.worldObj.provider.dimensionId);
                    // double var2 = this.rand.nextGaussian() * 0.02D;
                    // double var4 = this.rand.nextGaussian() * 0.02D;
                    // double var6 = this.rand.nextGaussian() * 0.02D;
                    // this.worldObj.spawnParticle("heart", this.posX +
                    // (double)(this.rand.nextFloat() * this.width * 2.0F) -
                    // (double)this.width, this.posY + 0.5D +
                    // (double)(this.rand.nextFloat() * this.height), this.posZ
                    // + (double)(this.rand.nextFloat() * this.width * 2.0F) -
                    // (double)this.width, var2, var4, var6);
                }

                if (gestationtime <= 50)
                {
                    continue;
                }
                MoCEntityHorse entityhorse1 = new MoCEntityHorse(worldObj);
                entityhorse1.setPosition(posX, posY, posZ);
                worldObj.spawnEntityInWorld(entityhorse1);
                worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, ((rand.nextFloat() - rand.nextFloat()) * 0.2F) + 1.0F);
                eatenpumpkin = false;

                entityhorse.eatenpumpkin = false;
                gestationtime = 0;
                this.setBred(true);
                entityhorse.gestationtime = 0;
                int l = HorseGenetics(this, entityhorse);

                if (l == 51) // fairy horse!
                {
                    // TODO TEST IF THIS SOUNDS IN CLIENT!
                    MoCTools.playCustomSound(this, "appearmagic", worldObj);
                    entityhorse.dissapearHorse();
                    this.dissapearHorse();
                }
                entityhorse1.setOwner(this.getOwnerName());
                entityhorse1.setTamed(true);
                entityhorse1.setBred(true);
                entityhorse1.setAdult(false);
                EntityPlayer entityplayer = worldObj.getPlayerEntityByName(this.getOwnerName());
                if (entityplayer != null && (MoCreatures.isServer()))
                {
                    MoCTools.tameWithName((EntityPlayerMP) entityplayer, entityhorse1);
                }
                entityhorse1.setType(l);
                break;
            }
        }

    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        /*
          if (getHiding()) { prevRenderYawOffset = renderYawOffset =
          rotationYaw = prevRotationYaw; }
         */

        
        if (shuffleCounter > 0) // && ++mouthCounter > 30)
        {
            ++shuffleCounter;
            if (!MoCreatures.isServer() && this.shuffleCounter % 20 == 0)
            {
                double var2 = this.rand.nextGaussian() * 0.5D;
                double var4 = this.rand.nextGaussian() * -0.1D;
                double var6 = this.rand.nextGaussian() * 0.02D;
                this.worldObj.spawnParticle("note", this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY + 0.5D + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, var2, var4, var6);
            }

            if ((MoCreatures.isServer() && !nearMusicBox()))
            {
                shuffleCounter = 0;
                MoCServerPacketHandler.sendStopShuffle(this.entityId, this.worldObj.provider.dimensionId);
            }
        }

        if (mouthCounter > 0 && ++mouthCounter > 30)
        {
            mouthCounter = 0;
        }

        /*
          if (shuffleCounter > 0 && ++shuffleCounter > 30) { shuffleCounter =
          0; }
         */

        if (standCounter > 0 && ++standCounter > 20)
        {
            standCounter = 0;
        }

        if (tailCounter > 0 && ++tailCounter > 8)
        {
            tailCounter = 0;
        }

        if (getVanishC() > 0)// && ++vanishCounter > 20)
        {

            setVanishC((byte) (getVanishC() + 1));

            if (getVanishC() < 15 && !MoCreatures.isServer())
            {
                VanishFX();

            }

            if (getVanishC() > 100)
            {
                setVanishC((byte) 101);
                MoCTools.dropAmulet(this);
                dissapearHorse();
            }

            if (getVanishC() == 1)
            {
                MoCTools.playCustomSound(this, "vanish", worldObj);
            }

            if (getVanishC() == 70)
            {
                stand();
            }
        }

        if (sprintCounter > 0)// && ++sprintCounter > 300)
        {
            ++sprintCounter;
            if (sprintCounter < 150 && sprintCounter % 2 == 0 && !MoCreatures.isServer())
            {
                StarFX();
            }

            if (sprintCounter > 300)
            {
                sprintCounter = 0;
            }

        }

        if (wingFlapCounter > 0)// && ++wingFlapCounter > 20)
        {
            ++wingFlapCounter;
            if (wingFlapCounter % 5 == 0 && !MoCreatures.isServer())
            {
                StarFX();
            }
            if (wingFlapCounter > 20)
            {
                wingFlapCounter = 0;

            }
        }

        if (transformCounter > 0)// && ++transformCounter > 100)
        {
            if (transformCounter == 40)
            {
                MoCTools.playCustomSound(this, "transform", worldObj);
            }

            if (++transformCounter > 100)
            {
                transformCounter = 0;
                if (transformType != 0)
                {
                    dropArmor();
                    setType(transformType);
                }
            }

        }

        if (isGhost() && getEdad() < 10 && rand.nextInt(7) == 0)
        {
            setEdad(getEdad() + 1);
        }

        if (isGhost() && getEdad() == 9)
        {
            setEdad(100);
            setAdult(true);
        }
        /*
          if (wingCounter > 0 && ++wingCounter > 80) { wingCounter = 0; }
         */

    }

    private void openMouth()
    {
        mouthCounter = 1;
    }

    public boolean ReadyforParenting(MoCEntityHorse entityhorse)
    {
        int i = entityhorse.getType();
        return (entityhorse.riddenByEntity == null) && (entityhorse.ridingEntity == null) && entityhorse.getIsTamed() && entityhorse.eatenpumpkin
        // && !entityhorse.getHasReproduced()
        && entityhorse.getIsAdult() && !entityhorse.isUndead() && !entityhorse.isGhost() && (i != 61) && (i < 66);
    }

    @Override
    public boolean renderName()
    {
        return getDisplayName() && (riddenByEntity == null);
    }

    @Override
    public boolean rideableEntity()
    {
        return true;
    }

    @Override
    public double roperYOffset()
    {
        if (this.getIsAdult())
        {
            return 0D;
        }
        else
        {
            return (130 - getEdad()) * 0.01D;
        }

    }

    /**
     * Horse Types
     * 
     * 1 White . 2 Creamy. 3 Brown. 4 Dark Brown. 5 Black.
     * 
     * 6 Bright Creamy. 7 Speckled. 8 Pale Brown. 9 Grey. 10 11 Pinto . 12
     * Bright Pinto . 13 Pale Speckles.
     * 
     * 16 Spotted 17 Cow.
     * 
     * 
     * 
     * 
     * 21 Ghost (winged) 22 Ghost B
     * 
     * 23 Undead 24 Undead Unicorn 25 Undead Pegasus
     * 
     * 26 skeleton 27 skeleton unicorn 28 skeleton pegasus
     * 
     * 30 bug horse
     * 
     * 32 Bat Horse
     * 
     * 36 Unicorn
     * 
     * 38 Nightmare? 39 White Pegasus 40 Black Pegasus
     * 
     * 50 fairy white 51 fairy blue 52 fairy pink 53 fairy light green
     * 
     * 60 Zebra 61 Zorse
     * 
     * 65 Donkey 66 Mule 67 Zonky
     */

    @Override
    public void selectType()
    {
        if (getType() == 0)
        {
            if (rand.nextInt(5) == 0)
            {
                setAdult(false);
            }
            int j = rand.nextInt(100);
            int i = MoCreatures.proxy.zebraChance;
            if (j <= (18 - i))
            {
                setType(1);
            }
            else if (j <= (36 - i))
            {
                setType(2);
            }
            else if (j <= (54 - i))
            {
                setType(3);
            }
            else if (j <= (72 - i))
            {
                setType(4);
            }
            else if (j <= (90 - i))
            {
                setType(5);
            }
            else if (j <= (99 - i))
            {
                setType(65); // donkey
            }
            else
            {
                setType(60);// zebra
            }

            health = getMaxHealth();
        }

    }
    
    @Override
    public void setArmorType(byte i)
    {
        dataWatcher.updateObject(25, Integer.valueOf(i));
    }

    /*public void setInventorySize(int i)
    {
        dataWatcher.updateObject(27, Integer.valueOf(i));
    }*/

    public void setBred(boolean flag)
    {
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(26, Byte.valueOf(input));

        // wasBred = var1;
    }

    public void setChestedHorse(boolean flag)
    {
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(23, Byte.valueOf(input));
    }

   /* @Override
    public void setDead()
    {
        if (MoCreatures.isServer() && (getIsTamed() || getHasBred()) && (health > 0)) { return; }
        super.setDead();
    }*/

    @Override
    public void setEating(boolean flag)
    {
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(24, Byte.valueOf(input));
    }

    public void setNightmareInt(int i)
    {
        nightmareInt = i;
    }

    public void setReproduced(boolean var1)
    {
        hasReproduced = var1;
    }

    public void setRideable(boolean flag)
    {
        // if (worldObj.isRemote) return;
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(22, Byte.valueOf(input));
    }

    public void setRoped(boolean var1)
    {
    }

    /**
     * New networked to fix SMP issues
     * 
     * @return
     */
    public void setVanishC(byte i)
    {
        vanishCounter = i;
        // if (!MoCreatures.isServer()) return;
        // dataWatcher.updateObject(27, Byte.valueOf(i));
    }

    public void shuffle()
    {
        if (shuffleCounter == 0)
        {
            shuffleCounter = 1;
        }

        EntityPlayer ep1 = worldObj.getClosestPlayerToEntity(this, 8D);
        if (ep1 != null)
        {
            this.faceEntity(ep1, 30F, 30F);
            // stare at player
        }
    }

    private void stand()
    {
        if (this.riddenByEntity == null && !this.isOnAir())
        {
            standCounter = 1;
        }
    }

    public void StarFX()
    {
        MoCreatures.proxy.StarFX(this);
    }

    /**
     * Used to flicker ghosts
     * 
     * @return
     */
    public float tFloat()
    {
        if (++this.fCounter > 60)
        {
            this.fCounter = 0;
            this.transFloat = (rand.nextFloat() * (0.6F - 0.3F) + 0.3F);
        }

        if (isGhost() && getEdad() < 10)
        {
            transFloat = 0;
        }
        return this.transFloat;
    }

    public void transform(int tType)
    {
        if (MoCreatures.isServer())
        {
            MoCServerPacketHandler.sendAnimationPacket(this.entityId, this.worldObj.provider.dimensionId, tType);
        }
        //MoCreatures.sendTransformPacket(this.entityId, tType);
        transformType = tType;
        if (this.riddenByEntity == null && transformType != 0)
        {
            dropArmor();
            transformCounter = 1;
        }
    }

    public void UndeadFX()
    {
        MoCreatures.proxy.UndeadFX(this);
    }

    public void VanishFX()
    {
        MoCreatures.proxy.VanishFX(this);
    }

    /**
     * Called to vanish Horse
     */

    public void vanishHorse()
    {
        setPathToEntity(null);
        this.motionX = 0D;
        this.motionZ = 0D;
        //dropMyStuff();
        if (this.isBagger())
        {
        	MoCTools.dropInventory(this, this.localhorsechest);
        	dropBags();
        }
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
        {
            MoCServerPacketHandler.sendVanishPacket(this.entityId, worldObj.provider.dimensionId);
            setVanishC((byte) 1);
        }
        MoCTools.playCustomSound(this, "vanish", worldObj);
    }

    @Override
    public void dropMyStuff() 
    {
        dropArmor(); 
        MoCTools.dropSaddle(this, worldObj); 
        if (this.isBagger())
        {
            MoCTools.dropInventory(this, this.localhorsechest);
            //HorseRemoval(worldObj,  MathHelper.floor_double(posX), MathHelper.floor_double(boundingBox.minY), MathHelper.floor_double(posZ));
            dropBags();
        }
    }
    
    public void wingFlap()
    {

        if (isFlyer() && wingFlapCounter == 0)
        {
            MoCTools.playCustomSound(this, "wingflap", worldObj);
        }
        wingFlapCounter = 1;

        motionY = 0.5D;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("Saddle", getIsRideable());
        nbttagcompound.setBoolean("EatingHaystack", getEating());
        nbttagcompound.setBoolean("ChestedHorse", getChestedHorse());
        nbttagcompound.setBoolean("HasReproduced", getHasReproduced());
        nbttagcompound.setBoolean("Bred", getHasBred());
        nbttagcompound.setBoolean("DisplayName", getDisplayName());
        nbttagcompound.setInteger("ArmorType", getArmorType());
        //nbttagcompound.setInteger("InventorySize", getInventorySize());
        //System.out.println("Writing NBT for horse " + getName() + ", getChestedHorse = " + getChestedHorse());
        if (getChestedHorse() && localhorsechest != null)
        {
            NBTTagList nbttaglist = new NBTTagList();
            for (int i = 0; i < localhorsechest.getSizeInventory(); i++)
            {
                // grab the current item stack
                localstack = localhorsechest.getStackInSlot(i);
                if (localstack != null)
                {
                    //System.out.println("localstack = " + localstack.toString());
                    NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                    nbttagcompound1.setByte("Slot", (byte) i);
                    localstack.writeToNBT(nbttagcompound1);
                    nbttaglist.appendTag(nbttagcompound1);
                }
            }
            nbttagcompound.setTag("Items", nbttaglist);
        }

    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        setRideable(nbttagcompound.getBoolean("Saddle"));
        setEating(nbttagcompound.getBoolean("EatingHaystack"));
        setBred(nbttagcompound.getBoolean("Bred"));
        setChestedHorse(nbttagcompound.getBoolean("ChestedHorse"));
        setReproduced(nbttagcompound.getBoolean("HasReproduced"));
        setDisplayName(nbttagcompound.getBoolean("DisplayName"));
        setArmorType((byte) nbttagcompound.getInteger("ArmorType"));
        //setInventorySize(nbttagcompound.getInteger("InventorySize"));
        //System.out.println("Reading NBT for horse " + getName() + ", getChestedHorse = " + getChestedHorse());
        if (getChestedHorse())
        {
            NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
            localhorsechest = new MoCAnimalChest("HorseChest", getInventorySize());
            //System.out.println("tagCount = " + nbttaglist.tagCount());
            for (int i = 0; i < nbttaglist.tagCount(); i++)
            {
                NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.tagAt(i);
                //System.out.println("nbttagcompound1 = " + nbttagcompound1.toString());
                int j = nbttagcompound1.getByte("Slot") & 0xff;
                if ((j >= 0) && j < localhorsechest.getSizeInventory())
                {
                    //System.out.println("Slot j = " + j);
                    localhorsechest.setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(nbttagcompound1));
                }
            }
        }
    }

    // this one is not working well due to rider having its legs rendered twice
    /*
      @Override public void updateRiderPosition() { double dist = (-0.2D);
      //System.out.println("wide" + riddenByEntity.width); double newPosX = posX
      -(dist * Math.cos((MoCTools.realAngle(rotationYaw - 90F)) / 57.29578F));
      double newPosZ = posZ -(dist * Math.sin((MoCTools.realAngle(rotationYaw -
      90F)) / 57.29578F)); riddenByEntity.setPosition(newPosX, posY +
      getMountedYOffset()+ riddenByEntity.getYOffset(), newPosZ);
      
      //this.riddenByEntity.setPosition(this.posX, this.posY +
      this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ);
      }
     */
    
    //moved to MoCTools
    /*
    public void HorseRemoval(World world, int i, int j, int k)
    {
        if (localstack == null) { return; }

        for (int l = 0; l < localhorsechest.getSizeInventory(); l++)
        {
            ItemStack itemstack = localhorsechest.getStackInSlot(l);
            if (itemstack == null)
            {
                continue;
            }
            float f = (rand.nextFloat() * 0.8F) + 0.1F;
            float f1 = (rand.nextFloat() * 0.8F) + 0.1F;
            float f2 = (rand.nextFloat() * 0.8F) + 0.1F;

            while (itemstack.stackSize > 0)
            {
                int i1 = rand.nextInt(21) + 10;
                if (i1 > itemstack.stackSize)
                {
                    i1 = itemstack.stackSize;
                }
                itemstack.stackSize -= i1;
                EntityItem entityitem = new EntityItem(world, i + f, j + f1, k + f2, new ItemStack(itemstack.itemID, i1, itemstack.getItemDamage()));
                float f3 = 0.05F;
                entityitem.motionX = ((float) rand.nextGaussian() * f3);
                entityitem.motionY = (((float) rand.nextGaussian() * f3) + 0.2F);
                entityitem.motionZ = ((float) rand.nextGaussian() * f3);
                worldObj.spawnEntityInWorld(entityitem);
            }
        }

    }
    */

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
    public void performAnimation(int animationType)
    {
        //23,24,25,32,36,38,39,40,51,52,53
        if (animationType >= 23 && animationType < 60) //transform
        {
            transformType = animationType;
            transformCounter = 1;
        }
        
    }
}
