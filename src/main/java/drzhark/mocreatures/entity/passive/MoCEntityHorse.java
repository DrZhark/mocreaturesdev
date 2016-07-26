package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.EntityAIFollowAdult;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.inventory.MoCAnimalChest;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import drzhark.mocreatures.network.message.MoCMessageHeart;
import drzhark.mocreatures.network.message.MoCMessageVanish;
import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.BlockJukebox.TileEntityJukebox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;

import java.util.List;

public class MoCEntityHorse extends MoCEntityTameableAnimal {

    private int gestationtime;
    private int countEating;
    private int textCounter;
    private int fCounter;
    public int shuffleCounter;
    public int wingFlapCounter;
    private float transFloat = 0.2F;

    public MoCAnimalChest localchest;
    public MoCAnimalChest emptychest;
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
    protected EntityAIWanderMoC2 wander;

    public MoCEntityHorse(World world) {
        super(world);
        setSize(1.4F, 1.6F);
        this.gestationtime = 0;
        this.eatenpumpkin = false;
        this.nightmareInt = 0;
        this.isImmuneToFire = false;
        setEdad(50);
        setIsChested(false);
        this.stepHeight = 1.0F;

        if (MoCreatures.isServer()) {
            if (this.rand.nextInt(5) == 0) {
                setAdult(false);
            } else {
                setAdult(true);
            }
        }
        ((PathNavigateGround) this.getNavigator()).setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(3, new EntityAIFollowAdult(this, 1.0D));
        this.tasks.addTask(4, this.wander = new EntityAIWanderMoC2(this, 1.0D, 80));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(23, Byte.valueOf((byte) 0)); // isChestedHorse - 0 false 1 true
        this.dataWatcher.addObject(24, Byte.valueOf((byte) 0)); // isSeating - 0 false 1 true
        this.dataWatcher.addObject(25, Integer.valueOf(0)); // armor 0 by default, 1 metal, 2 gold, 3 diamond, 4 crystaline
        this.dataWatcher.addObject(26, Byte.valueOf((byte) 0)); // Bred - 0 false 1 true
        this.dataWatcher.addObject(27, Byte.valueOf((byte) 0)); // isRideable - 0 false 1 true
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        Entity entity = damagesource.getEntity();
        if ((this.riddenByEntity != null) && (entity == this.riddenByEntity)) {
            return false;
        }
        if (entity instanceof EntityWolf) {
            EntityCreature entitycreature = (EntityCreature) entity;
            entitycreature.setAttackTarget(null);
            return false;
        } else {
            i = i - (getArmorType() + 2);
            if (i < 0F) {
                i = 0F;
            }
            return super.attackEntityFrom(damagesource, i);
        }
    }

    @Override
    public boolean canBeCollidedWith() {
        return this.riddenByEntity == null;
    }

    @Override
    public boolean checkSpawningBiome() {
        BlockPos pos = new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(getEntityBoundingBox().minY), this.posZ);

        Biome currentbiome = MoCTools.Biomekind(this.worldObj, pos);
        String s = MoCTools.BiomeName(this.worldObj, pos);
        try {
            if (BiomeDictionary.isBiomeOfType(currentbiome, Type.PLAINS)) {
                if (this.rand.nextInt(3) == 0) {
                    setType(60);// zebra
                }
            }
            if (BiomeDictionary.isBiomeOfType(currentbiome, Type.SANDY)) {
                setType(60);// zebra
            }

            if (s.toLowerCase().contains("prairie"))//prairies spawn only regular horses, no zebras there
            {
                setType(this.rand.nextInt(5) + 1);
            }
        } catch (Exception e) {
        }
        return true;
    }

    /**
     * returns one of the RGB color codes
     *
     * @param sColor : 1 will return the Red component, 2 will return the Green
     *        and 3 the blue
     * @param typeInt : which set of colors to inquiry about, corresponds with
     *        the horse types.
     * @return
     */
    public float colorFX(int sColor, int typeInt) {
        if (typeInt == 48) // yellow
        {
            if (sColor == 1) {
                return (float) 179 / 256;
            }
            if (sColor == 2) {
                return (float) 160 / 256;
            }
            return (float) 22 / 256;
        }

        if (typeInt == 49) // purple
        {
            if (sColor == 1) {
                return (float) 147 / 256;
            }
            if (sColor == 2) {
                return (float) 90 / 256;
            }
            return (float) 195 / 256;
        }

        if (typeInt == 51) // blue
        {
            if (sColor == 1) {
                return (float) 30 / 256;
            }
            if (sColor == 2) {
                return (float) 144 / 256;
            }
            return (float) 255 / 256;
        }
        if (typeInt == 52) // pink
        {
            if (sColor == 1) {
                return (float) 255 / 256;
            }
            if (sColor == 2) {
                return (float) 105 / 256;
            }
            return (float) 180 / 256;
        }

        if (typeInt == 53) // lightgreen
        {
            if (sColor == 1) {
                return (float) 188 / 256;
            }
            if (sColor == 2) {
                return (float) 238 / 256;
            }
            return (float) 104 / 256;
        }

        if (typeInt == 54) // black fairy
        {
            if (sColor == 1) {
                return (float) 110 / 256;
            }
            if (sColor == 2) {
                return (float) 123 / 256;
            }
            return (float) 139 / 256;
        }

        if (typeInt == 55) // red fairy
        {
            if (sColor == 1) {
                return (float) 194 / 256;
            }
            if (sColor == 2) {
                return (float) 29 / 256;
            }
            return (float) 34 / 256;
        }

        if (typeInt == 56) // dark blue fairy
        {
            if (sColor == 1) {
                return (float) 63 / 256;
            }
            if (sColor == 2) {
                return (float) 45 / 256;
            }
            return (float) 255 / 256;
        }

        if (typeInt == 57) // cyan
        {
            if (sColor == 1) {
                return (float) 69 / 256;
            }
            if (sColor == 2) {
                return (float) 146 / 256;
            }
            return (float) 145 / 256;
        }

        if (typeInt == 58) // green
        {
            if (sColor == 1) {
                return (float) 90 / 256;
            }
            if (sColor == 2) {
                return (float) 136 / 256;
            }
            return (float) 43 / 256;
        }

        if (typeInt == 59) // orange
        {
            if (sColor == 1) {
                return (float) 218 / 256;
            }
            if (sColor == 2) {
                return (float) 40 / 256;
            }
            return (float) 0 / 256;
        }

        if (typeInt > 22 && typeInt < 26) // green for undeads
        {
            if (sColor == 1) {
                return (float) 60 / 256;
            }
            if (sColor == 2) {
                return (float) 179 / 256;
            }
            return (float) 112 / 256;

        }
        if (typeInt == 40) // dark red for black pegasus
        {
            if (sColor == 1) {
                return (float) 139 / 256;
            }
            if (sColor == 2) {
                return 0F;
            }
            return 0F;

        }

        // by default will return clear gold
        if (sColor == 1) {
            return (float) 255 / 256;
        }
        if (sColor == 2) {
            return (float) 236 / 256;
        }
        return (float) 139 / 256;
    }

    /**
     * Called to vanish a Horse without FX
     */
    public void dissapearHorse() {
        this.isDead = true;
    }

    private void drinkingHorse() {
        openMouth();
        MoCTools.playCustomSound(this, "drinking", this.worldObj);
    }

    /**
     * Drops the current armor if the horse has one
     */
    @Override
    public void dropArmor() {
        if (MoCreatures.isServer()) {
            int i = getArmorType();
            if (i != 0) {
                MoCTools.playCustomSound(this, "armoroff", this.worldObj);
            }

            if (i == 1) {
                EntityItem entityitem = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(Items.iron_horse_armor, 1));
                entityitem.setPickupDelay(10);
                this.worldObj.spawnEntityInWorld(entityitem);
            }
            if (i == 2) {
                EntityItem entityitem = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(Items.golden_horse_armor, 1));
                entityitem.setPickupDelay(10);
                this.worldObj.spawnEntityInWorld(entityitem);
            }
            if (i == 3) {
                EntityItem entityitem = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(Items.diamond_horse_armor, 1));
                entityitem.setPickupDelay(10);
                this.worldObj.spawnEntityInWorld(entityitem);
            }
            if (i == 4) {
                EntityItem entityitem =
                        new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(MoCreatures.horsearmorcrystal, 1));
                entityitem.setPickupDelay(10);
                this.worldObj.spawnEntityInWorld(entityitem);
            }
            setArmorType((byte) 0);
        }
    }

    /**
     * Drops a chest block if the horse is bagged
     */
    public void dropBags() {
        if (!isBagger() || !getIsChested() || !MoCreatures.isServer()) {
            return;
        }

        EntityItem entityitem = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(Blocks.chest, 1));
        float f3 = 0.05F;
        entityitem.motionX = (float) this.worldObj.rand.nextGaussian() * f3;
        entityitem.motionY = ((float) this.worldObj.rand.nextGaussian() * f3) + 0.2F;
        entityitem.motionZ = (float) this.worldObj.rand.nextGaussian() * f3;
        this.worldObj.spawnEntityInWorld(entityitem);
        setIsChested(false);
    }

    private void eatingHorse() {
        openMouth();
        MoCTools.playCustomSound(this, "mocreatures:eating", this.worldObj);
    }

    @Override
    public void fall(float f, float f1) {
        if (isFlyer() || isFloater()) {
            return;
        }

        float i = (float) (Math.ceil(f - 3F) / 2F);
        if (MoCreatures.isServer() && (i > 0)) {
            if (getType() >= 10) {
                i /= 2;
            }
            if (i > 1F) {
                attackEntityFrom(DamageSource.fall, i);
            }
            if ((this.riddenByEntity != null) && (i > 1F)) {
                this.riddenByEntity.attackEntityFrom(DamageSource.fall, i);
            }

            Block block =
                    this.worldObj.getBlockState(
                            new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY - 0.20000000298023221D
                                    - this.prevRotationPitch), MathHelper.floor_double(this.posZ))).getBlock();
            if (block != Blocks.AIR) {
                SoundType stepsound = block.stepSound;
                this.worldObj.playSoundAtEntity(this, stepsound.getStepSound(), stepsound.getVolume() * 0.5F, stepsound.getFrequency() * 0.75F);
            }
        }
    }

    @Override
    public byte getArmorType() {
        return (byte) this.dataWatcher.getWatchableObjectInt(25);
    }

    public int getInventorySize() {
        if (getType() == 40) {
            return 18;
        } else if (getType() > 64) {
            return 27;
        }
        return 9;
    }

    public boolean getIsChested() {
        return (this.dataWatcher.getWatchableObjectByte(23) == 1);
    }

    @Override
    public double getCustomJump() {
        double HorseJump = 0.35D;
        if (getType() < 6) // tier 1
        {
            HorseJump = 0.35;
        } else if (getType() > 5 && getType() < 11) // tier 2
        {
            HorseJump = 0.40D;
        } else if (getType() > 10 && getType() < 16) // tier 3
        {
            HorseJump = 0.45D;
        } else if (getType() > 15 && getType() < 21) // tier 4
        {
            HorseJump = 0.50D;
        }

        else if (getType() > 20 && getType() < 26) // ghost and undead
        {
            HorseJump = 0.45D;
        } else if (getType() > 25 && getType() < 30) // skely
        {
            HorseJump = 0.5D;
        } else if (getType() >= 30 && getType() < 40) // magics
        {
            HorseJump = 0.55D;
        } else if (getType() >= 40 && getType() < 60) // black pegasus and fairies
        {
            HorseJump = 0.6D;
        } else if (getType() >= 60) // donkeys - zebras and the like
        {
            HorseJump = 0.4D;
        }
        return HorseJump;
    }

    @Override
    public double getCustomSpeed() {
        double HorseSpeed = 0.8D;
        if (getType() < 6) // tier 1
        {
            HorseSpeed = 0.9;
        } else if (getType() > 5 && getType() < 11) // tier 2
        {
            HorseSpeed = 1.0D;
        } else if (getType() > 10 && getType() < 16) // tier 3
        {
            HorseSpeed = 1.1D;
        } else if (getType() > 15 && getType() < 21) // tier 4
        {
            HorseSpeed = 1.2D;
        }

        else if (getType() > 20 && getType() < 26) // ghost and undead
        {
            HorseSpeed = 0.8D;
        } else if (getType() > 25 && getType() < 30) // skely
        {
            HorseSpeed = 1.0D;
        } else if (getType() > 30 && getType() < 40) // magics
        {
            HorseSpeed = 1.2D;
        } else if (getType() >= 40 && getType() < 60) // black pegasus and
                                                      // fairies
        {
            HorseSpeed = 1.3D;
        } else if (getType() == 60 || getType() == 61) // zebras and zorse
        {
            HorseSpeed = 1.1D;
        } else if (getType() == 65) // donkeys
        {
            HorseSpeed = 0.7D;
        } else if (getType() > 65) // mule and zorky
        {
            HorseSpeed = 0.9D;
        }
        if (this.sprintCounter > 0 && this.sprintCounter < 150) {
            HorseSpeed *= 1.5D;
        }
        if (this.sprintCounter > 150) {
            HorseSpeed *= 0.5D;
        }
        return HorseSpeed;
    }

    @Override
    protected String getDeathSound() {
        openMouth();
        if (this.isUndead()) {
            return "horsedyingundead";
        }
        if (this.isGhost()) {
            return "horsedyingghost";
        }
        if (this.getType() == 60 || this.getType() == 61) {
            return "zebrahurt";
        }
        if (this.getType() >= 65 && this.getType() <= 67) {
            return "donkeydying";
        }
        return "horsedying";
    }

    @Override
    public boolean renderName() {
        if (isGhost() && getEdad() < 10) {
            return false;
        }

        return super.renderName();
    }

    @Override
    protected Item getDropItem() {
        boolean flag = (this.rand.nextInt(100) < MoCreatures.proxy.rareItemDropChance);

        if (flag && (this.getType() == 36 || (this.getType() >= 50 && this.getType() < 60))) // unicorn
        {
            return MoCreatures.unicornhorn;
        }
        if (this.getType() == 39) // pegasus
        {
            return Items.feather;
        }
        if (this.getType() == 40) // dark pegasus
        {
            return Items.feather;
        }
        if (this.getType() == 38 && flag && this.worldObj.provider.doesWaterVaporize()) // nightmare
        {
            return MoCreatures.heartfire;
        }
        if (this.getType() == 32 && flag) // bat horse
        {
            return MoCreatures.heartdarkness;
        }
        if (this.getType() == 26)// skely
        {
            return Items.bone;
        }
        if ((this.getType() == 23 || this.getType() == 24 || this.getType() == 25)) {
            if (flag) {
                return MoCreatures.heartundead;
            }
            return Items.rotten_flesh;
        }
        if (this.getType() == 21 || this.getType() == 22) {
            return Items.ghast_tear;
        }

        return Items.leather;
    }

    @Override
    public boolean getIsSitting() {
        return (this.dataWatcher.getWatchableObjectByte(24) == 1);
    }

    /*public boolean getHasBred() {
        return (this.dataWatcher.getWatchableObjectByte(26) == 1);
    }*/

    public boolean getHasReproduced() {
        return this.hasReproduced;
    }

    @Override
    protected String getHurtSound() {
        openMouth();
        if (isFlyer() && this.riddenByEntity == null) {
            wingFlap();
        } else {
            if (this.rand.nextInt(3) == 0) {
                stand();
            }
        }
        if (this.isUndead()) {
            return "mocreatures:horsehurtundead";
        }
        if (this.isGhost()) {
            return "mocreatures:horsehurtghost";
        }
        if (this.getType() == 60 || this.getType() == 61) {
            return "mocreatures:zebrahurt";
        }
        if (this.getType() >= 65 && this.getType() <= 67) {
            return "mocreatures:donkeyhurt";
        }
        return "mocreatures:horsehurt";
    }

    @Override
    public boolean getIsRideable() {
        return (this.dataWatcher.getWatchableObjectByte(27) == 1);
    }

    @Override
    protected String getLivingSound() {
        openMouth();
        if (this.rand.nextInt(10) == 0 && !isMovementCeased()) {
            stand();
        }
        if (this.isUndead()) {
            return "mocreatures:horsegruntundead";
        }
        if (this.isGhost()) {
            return "mocreatures:horsegruntghost";
        }
        if (this.getType() == 60 || this.getType() == 61) {
            return "mocreatures:zebragrunt";
        }
        if (this.getType() >= 65 && this.getType() <= 67) {
            return "mocreatures:donkeygrunt";
        }
        return "mocreatures:horsegrunt";
    }

    /**
     * sound played when an untamed mount buckles rider
     */
    @Override
    protected String getMadSound() {
        openMouth();
        stand();
        if (this.isUndead()) {
            return "horsemadundead";
        }
        if (this.isGhost()) {
            return "horsemadghost";
        }
        if (this.getType() == 60 || this.getType() == 61) {
            return "zebrahurt";
        }
        if (this.getType() >= 65 && this.getType() <= 67) {
            return "donkeyhurt";
        }
        return "horsemad";
    }

    public float calculateMaxHealth() {
        int maximumHealth = 30;
        if (getType() < 6) // tier 1
        {
            maximumHealth = 25;
        } else if (getType() > 5 && getType() < 11) // tier 2
        {
            maximumHealth = 30;
        } else if (getType() > 10 && getType() < 16) // tier 3
        {
            maximumHealth = 35;
        } else if (getType() > 15 && getType() < 21) // tier 4
        {
            maximumHealth = 40;
        }

        else if (getType() > 20 && getType() < 26) // ghost and undead
        {
            maximumHealth = 35;
        } else if (getType() > 25 && getType() < 30) // skely
        {
            maximumHealth = 35;
        } else if (getType() >= 30 && getType() < 40) // magics
        {
            maximumHealth = 50;
        } else if (getType() == 40) // black pegasus
        {
            maximumHealth = 50;
        } else if (getType() > 40 && getType() < 60) // fairies
        {
            maximumHealth = 40;
        } else if (getType() >= 60) // donkeys - zebras and the like
        {
            maximumHealth = 30;
        }

        return maximumHealth;
    }

    /**
     * How difficult is the creature to be tamed? the Higher the number, the
     * more difficult
     */
    @Override
    public int getMaxTemper() {

        if (getType() == 60) {
            return 200; // zebras are harder to tame
        }
        return 100;
    }

    public int getNightmareInt() {
        return this.nightmareInt;
    }

    @Override
    protected float getSoundVolume() {
        return 0.8F;
    }

    @Override
    public int getTalkInterval() {
        return 400;
    }

    /**
     * Overridden for the dynamic nightmare texture.
     */
    @Override
    public ResourceLocation getTexture() {
        String tempTexture;

        switch (getType()) {
            case 1:
                tempTexture = "horsewhite.png";
                break;
            case 2:
                tempTexture = "horsecreamy.png";
                break;
            case 3:
                tempTexture = "horsebrown.png";
                break;
            case 4:
                tempTexture = "horsedarkbrown.png";
                break;
            case 5:
                tempTexture = "horseblack.png";
                break;
            case 6:
                tempTexture = "horsebrightcreamy.png";
                break;
            case 7:
                tempTexture = "horsespeckled.png";
                break;
            case 8:
                tempTexture = "horsepalebrown.png";
                break;
            case 9:
                tempTexture = "horsegrey.png";
                break;
            case 11:
                tempTexture = "horsepinto.png";
                break;
            case 12:
                tempTexture = "horsebrightpinto.png";
                break;
            case 13:
                tempTexture = "horsepalespeckles.png";
                break;
            case 16:
                tempTexture = "horsespotted.png";
                break;
            case 17:
                tempTexture = "horsecow.png";
                break;

            case 21:
                tempTexture = "horseghost.png";
                break;
            case 22:
                tempTexture = "horseghostb.png";
                break;
            case 23:
                tempTexture = "horseundead.png";
                break;
            case 24:
                tempTexture = "horseundeadunicorn.png";
                break;
            case 25:
                tempTexture = "horseundeadpegasus.png";
                break;
            case 26:
                tempTexture = "horseskeleton.png";
                break;
            case 27:
                tempTexture = "horseunicornskeleton.png";
                break;
            case 28:
                tempTexture = "horsepegasusskeleton.png";
                break;
            case 30:
                tempTexture = "horsebug.png";
                break;
            case 32:
                tempTexture = "horsebat.png";
                break;
            case 36:
                tempTexture = "horseunicorn.png";
                break;
            case 38:
                //this.isImmuneToFire = true;
                tempTexture = "horsenightmare.png";
                break;
            case 39:
                tempTexture = "horsepegasus.png";
                break;
            case 40:
                //this.isImmuneToFire = true;
                tempTexture = "horsedarkpegasus.png";
                break;
            /*
             * case 44: tempTexture = "horsefairydarkblue.png"; break; case 45:
             * tempTexture = "horsefairydarkblue.png"; break; case 46:
             * tempTexture = "horsefairydarkblue.png"; break; case 47:
             * tempTexture = "horsefairydarkblue.png"; break;
             */
            case 48:
                tempTexture = "horsefairyyellow.png";
                break;
            case 49:
                tempTexture = "horsefairypurple.png";
                break;
            case 50:
                tempTexture = "horsefairywhite.png";
                break;
            case 51:
                tempTexture = "horsefairyblue.png";
                break;
            case 52:
                tempTexture = "horsefairypink.png";
                break;
            case 53:
                tempTexture = "horsefairylightgreen.png";
                break;
            case 54:
                tempTexture = "horsefairyblack.png";
                break;
            case 55:
                tempTexture = "horsefairyred.png";
                break;
            case 56:
                tempTexture = "horsefairydarkblue.png";
                break;
            case 57:
                tempTexture = "horsefairycyan.png";
                break;
            case 58:
                tempTexture = "horsefairygreen.png";
                break;
            case 59:
                tempTexture = "horsefairyorange.png";
                break;

            case 60:
                tempTexture = "horsezebra.png";
                break;
            case 61:
                tempTexture = "horsezorse.png";
                break;
            case 65:
                tempTexture = "horsedonkey.png";
                break;
            case 66:
                tempTexture = "horsemule.png";
                break;
            case 67:
                tempTexture = "horsezonky.png";
                break;

            default:
                tempTexture = "horsebug.png";
        }

        if ((isArmored() || isMagicHorse()) && getArmorType() > 0) {
            String armorTex = "";
            if (getArmorType() == 1) {
                armorTex = "metal.png";
            }
            if (getArmorType() == 2) {
                armorTex = "gold.png";
            }
            if (getArmorType() == 3) {
                armorTex = "diamond.png";
            }
            if (getArmorType() == 4) {
                armorTex = "crystaline.png";
            }
            return MoCreatures.proxy.getTexture(tempTexture.replace(".png", armorTex));
        }

        if (this.isUndead() && this.getType() < 26) {
            String baseTex = "horseundead";
            int max = 79;
            if (this.getType() == 25) // undead pegasus
            {
                baseTex = "horseundeadpegasus";
                // max = 79; //undead pegasus have an extra animation

            }
            if (this.getType() == 24)// undead unicorn
            {
                baseTex = "horseundeadunicorn";
                max = 69; // undead unicorn have an animation less
            }

            String iteratorTex = "1";
            if (MoCreatures.proxy.getAnimateTextures()) {
                if (this.rand.nextInt(3) == 0) {
                    this.textCounter++;
                }
                if (this.textCounter < 10) {
                    this.textCounter = 10;
                }
                if (this.textCounter > max) {
                    this.textCounter = 10;
                }
                iteratorTex = "" + this.textCounter;
                iteratorTex = iteratorTex.substring(0, 1);
            }

            String decayTex = "" + (getEdad() / 100);
            decayTex = decayTex.substring(0, 1);
            return MoCreatures.proxy.getTexture(baseTex + decayTex + iteratorTex + ".png");
        }

        // if animate textures is off, return plain textures
        if (!MoCreatures.proxy.getAnimateTextures()) {
            return MoCreatures.proxy.getTexture(tempTexture);
        }

        if (this.isNightmare()) {
            if (this.rand.nextInt(1) == 0) {
                this.textCounter++;
            }
            if (this.textCounter < 10) {
                this.textCounter = 10;
            }
            if (this.textCounter > 59) {
                this.textCounter = 10;
            }
            String NTA = "horsenightmare";
            String NTB = "" + this.textCounter;
            NTB = NTB.substring(0, 1);
            String NTC = ".png";

            return MoCreatures.proxy.getTexture(NTA + NTB + NTC);
        }

        if (this.transformCounter != 0 && this.transformType != 0) {
            String newText = "horseundead.png";
            if (this.transformType == 23) {
                newText = "horseundead.png";
            }
            if (this.transformType == 24) {
                newText = "horseundeadunicorn.png";
            }
            if (this.transformType == 25) {
                newText = "horseundeadpegasus.png";
            }
            if (this.transformType == 36) {
                newText = "horseunicorn.png";
            }
            if (this.transformType == 39) {
                newText = "horsepegasus.png";
            }
            if (this.transformType == 40) {
                newText = "horseblackpegasus.png";
            }

            if (this.transformType == 48) {
                newText = "horsefairyyellow.png";
            }
            if (this.transformType == 49) {
                newText = "horsefairypurple.png";
            }
            if (this.transformType == 50) {
                newText = "horsefairywhite.png";
            }
            if (this.transformType == 51) {
                newText = "horsefairyblue.png";
            }
            if (this.transformType == 52) {
                newText = "horsefairypink.png";
            }
            if (this.transformType == 53) {
                newText = "horsefairylightgreen.png";
            }
            if (this.transformType == 54) {
                newText = "horsefairyblack.png";
            }
            if (this.transformType == 55) {
                newText = "horsefairyred.png";
            }
            if (this.transformType == 56) {
                newText = "horsefairydarkblue.png";
            }

            if (this.transformType == 57) {
                newText = "horsefairycyan.png";
            }

            if (this.transformType == 58) {
                newText = "horsefairygreen.png";
            }

            if (this.transformType == 59) {
                newText = "horsefairyorange.png";
            }

            if (this.transformType == 32) {
                newText = "horsebat.png";
            }
            if (this.transformType == 38) {
                newText = "horsenightmare1.png";
            }
            if ((this.transformCounter % 5) == 0) {
                return MoCreatures.proxy.getTexture(newText);
            }
            if (this.transformCounter > 50 && (this.transformCounter % 3) == 0) {
                return MoCreatures.proxy.getTexture(newText);
            }

            if (this.transformCounter > 75 && (this.transformCounter % 4) == 0) {
                return MoCreatures.proxy.getTexture(newText);
            }
        }

        return MoCreatures.proxy.getTexture(tempTexture);

    }

    /**
     * New networked to fix SMP issues
     *
     * @return
     */
    public byte getVanishC() {
        return (byte) this.vanishCounter;
    }

    /**
     * Breeding rules for the horses
     *
     * @param entityhorse
     * @param entityhorse1
     * @return
     */
    //private int HorseGenetics(MoCEntityHorse entityhorse, MoCEntityHorse entityhorse1)
    private int HorseGenetics(int typeA, int typeB) {
        boolean flag = MoCreatures.proxy.easyBreeding;
        //int typeA = entityhorse.getType();
        //int typeB = entityhorse1.getType();

        // identical horses have so spring
        if (typeA == typeB) {
            return typeA;
        }

        // zebras plus any horse
        if (typeA == 60 && typeB < 21 || typeB == 60 && typeA < 21) {
            return 61; // zorse
        }

        // dokey plus any horse
        if (typeA == 65 && typeB < 21 || typeB == 65 && typeA < 21) {
            return 66; // mule
        }

        // zebra plus donkey
        if (typeA == 60 && typeB == 65 || typeB == 60 && typeA == 65) {
            return 67; // zonky
        }

        if (typeA > 20 && typeB < 21 || typeB > 20 && typeA < 21) // rare horses plus  ordinary horse always returns ordinary horse
        {
            if (typeA < typeB) {
                return typeA;
            }
            return typeB;
        }

        // unicorn plus white pegasus (they will both vanish!)
        if (typeA == 36 && typeB == 39 || typeB == 36 && typeA == 39) {
            return 50; // white fairy
        }

        // unicorn plus black pegasus (they will both vanish!)
        if (typeA == 36 && typeB == 40 || typeB == 36 && typeA == 40) {
            return 54; // black fairy
        }

        // rare horse mixture: produces a regular horse 1-5
        if (typeA > 20 && typeB > 20 && (typeA != typeB)) {
            return (this.rand.nextInt(5)) + 1;
        }

        // rest of cases will return either typeA, typeB or new mix
        int chanceInt = (this.rand.nextInt(4)) + 1;
        if (!flag) {
            if (chanceInt == 1) // 25%
            {
                return typeA;
            } else if (chanceInt == 2) // 25%
            {
                return typeB;
            }
        }

        if ((typeA == 1 && typeB == 2) || (typeA == 2 && typeB == 1)) {
            return 6;
        }

        if ((typeA == 1 && typeB == 3) || (typeA == 3 && typeB == 1)) {
            return 2;
        }

        if ((typeA == 1 && typeB == 4) || (typeA == 4 && typeB == 1)) {
            return 7;
        }

        if ((typeA == 1 && typeB == 5) || (typeA == 5 && typeB == 1)) {
            return 9;
        }

        if ((typeA == 1 && typeB == 7) || (typeA == 7 && typeB == 1)) {
            return 12;
        }

        if ((typeA == 1 && typeB == 8) || (typeA == 8 && typeB == 1)) {
            return 7;
        }

        if ((typeA == 1 && typeB == 9) || (typeA == 9 && typeB == 1)) {
            return 13;
        }

        if ((typeA == 1 && typeB == 11) || (typeA == 11 && typeB == 1)) {
            return 12;
        }

        if ((typeA == 1 && typeB == 12) || (typeA == 12 && typeB == 1)) {
            return 13;
        }

        if ((typeA == 1 && typeB == 17) || (typeA == 17 && typeB == 1)) {
            return 16;
        }

        if ((typeA == 2 && typeB == 4) || (typeA == 4 && typeB == 2)) {
            return 3;
        }

        if ((typeA == 2 && typeB == 5) || (typeA == 5 && typeB == 2)) {
            return 4;
        }

        if ((typeA == 2 && typeB == 7) || (typeA == 7 && typeB == 2)) {
            return 8;
        }

        if ((typeA == 2 && typeB == 8) || (typeA == 8 && typeB == 2)) {
            return 3;
        }

        if ((typeA == 2 && typeB == 12) || (typeA == 12 && typeB == 2)) {
            return 6;
        }

        if ((typeA == 2 && typeB == 16) || (typeA == 16 && typeB == 2)) {
            return 13;
        }

        if ((typeA == 2 && typeB == 17) || (typeA == 17 && typeB == 2)) {
            return 12;
        }

        if ((typeA == 3 && typeB == 4) || (typeA == 4 && typeB == 3)) {
            return 8;
        }

        if ((typeA == 3 && typeB == 5) || (typeA == 5 && typeB == 3)) {
            return 8;
        }

        if ((typeA == 3 && typeB == 6) || (typeA == 6 && typeB == 3)) {
            return 2;
        }

        if ((typeA == 3 && typeB == 7) || (typeA == 7 && typeB == 3)) {
            return 11;
        }

        if ((typeA == 3 && typeB == 9) || (typeA == 9 && typeB == 3)) {
            return 8;
        }

        if ((typeA == 3 && typeB == 12) || (typeA == 12 && typeB == 3)) {
            return 11;
        }

        if ((typeA == 3 && typeB == 16) || (typeA == 16 && typeB == 3)) {
            return 11;
        }

        if ((typeA == 3 && typeB == 17) || (typeA == 17 && typeB == 3)) {
            return 11;
        }

        if ((typeA == 4 && typeB == 6) || (typeA == 6 && typeB == 4)) {
            return 3;
        }

        if ((typeA == 4 && typeB == 7) || (typeA == 7 && typeB == 4)) {
            return 8;
        }

        if ((typeA == 4 && typeB == 9) || (typeA == 9 && typeB == 4)) {
            return 7;
        }

        if ((typeA == 4 && typeB == 11) || (typeA == 11 && typeB == 4)) {
            return 7;
        }

        if ((typeA == 4 && typeB == 12) || (typeA == 12 && typeB == 4)) {
            return 7;
        }

        if ((typeA == 4 && typeB == 13) || (typeA == 13 && typeB == 4)) {
            return 7;
        }

        if ((typeA == 4 && typeB == 16) || (typeA == 16 && typeB == 4)) {
            return 13;
        }

        if ((typeA == 4 && typeB == 17) || (typeA == 17 && typeB == 4)) {
            return 5;
        }

        if ((typeA == 5 && typeB == 6) || (typeA == 6 && typeB == 5)) {
            return 4;
        }

        if ((typeA == 5 && typeB == 7) || (typeA == 7 && typeB == 5)) {
            return 4;
        }

        if ((typeA == 5 && typeB == 8) || (typeA == 8 && typeB == 5)) {
            return 4;
        }

        if ((typeA == 5 && typeB == 11) || (typeA == 11 && typeB == 5)) {
            return 17;
        }

        if ((typeA == 5 && typeB == 12) || (typeA == 12 && typeB == 5)) {
            return 13;
        }

        if ((typeA == 5 && typeB == 13) || (typeA == 13 && typeB == 5)) {
            return 16;
        }

        if ((typeA == 5 && typeB == 16) || (typeA == 16 && typeB == 5)) {
            return 17;
        }

        if ((typeA == 6 && typeB == 8) || (typeA == 8 && typeB == 6)) {
            return 2;
        }

        if ((typeA == 6 && typeB == 17) || (typeA == 17 && typeB == 6)) {
            return 7;
        }

        if ((typeA == 7 && typeB == 16) || (typeA == 16 && typeB == 7)) {
            return 13;
        }

        if ((typeA == 8 && typeB == 11) || (typeA == 11 && typeB == 8)) {
            return 7;
        }

        if ((typeA == 8 && typeB == 12) || (typeA == 12 && typeB == 8)) {
            return 7;
        }

        if ((typeA == 8 && typeB == 13) || (typeA == 13 && typeB == 8)) {
            return 7;
        }

        if ((typeA == 8 && typeB == 16) || (typeA == 16 && typeB == 8)) {
            return 7;
        }

        if ((typeA == 8 && typeB == 17) || (typeA == 17 && typeB == 8)) {
            return 7;
        }

        if ((typeA == 9 && typeB == 16) || (typeA == 16 && typeB == 9)) {
            return 13;
        }

        if ((typeA == 11 && typeB == 16) || (typeA == 16 && typeB == 11)) {
            return 13;
        }

        if ((typeA == 11 && typeB == 17) || (typeA == 17 && typeB == 11)) {
            return 7;
        }

        if ((typeA == 12 && typeB == 16) || (typeA == 16 && typeB == 12)) {
            return 13;
        }

        if ((typeA == 13 && typeB == 17) || (typeA == 17 && typeB == 13)) {
            return 9;
        }

        return typeA; // breed is not in the table so it will return the first
                      // parent type
    }

    @Override
    public boolean interact(EntityPlayer entityplayer) {
        if (super.interact(entityplayer)) {
            return false;
        }
        if (this.getType() == 60 && !getIsTamed() && isZebraRunning()) // zebra
        {
            return false;
        }

        ItemStack itemstack = entityplayer.inventory.getCurrentItem();

        if ((itemstack != null) && !getIsRideable() && itemstack.getItem() == Items.saddle) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            setRideable(true);
            return true;
        }

        if ((itemstack != null) && this.getIsTamed() && itemstack.getItem() == Items.iron_horse_armor && isArmored()) {
            if (getArmorType() == 0) {
                MoCTools.playCustomSound(this, "armorput", this.worldObj);
            }
            dropArmor();
            setArmorType((byte) 1);
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }

            return true;
        }

        if ((itemstack != null) && this.getIsTamed() && itemstack.getItem() == Items.golden_horse_armor && isArmored()) {
            if (getArmorType() == 0) {
                MoCTools.playCustomSound(this, "armorput", this.worldObj);
            }
            dropArmor();
            setArmorType((byte) 2);
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            return true;
        }

        if ((itemstack != null) && this.getIsTamed() && itemstack.getItem() == Items.diamond_horse_armor && isArmored()) {
            if (getArmorType() == 0) {
                MoCTools.playCustomSound(this, "armorput", this.worldObj);
            }
            dropArmor();
            setArmorType((byte) 3);
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            return true;
        }

        if ((itemstack != null) && this.getIsTamed() && itemstack.getItem() == MoCreatures.horsearmorcrystal && isMagicHorse()) {
            if (getArmorType() == 0) {
                MoCTools.playCustomSound(this, "armorput", this.worldObj);
            }
            dropArmor();
            setArmorType((byte) 4);
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            return true;
        }

        // transform to undead, or heal undead horse
        if ((itemstack != null) && this.getIsTamed() && itemstack.getItem() == MoCreatures.essenceundead) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Items.glass_bottle));
            } else {
                entityplayer.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
            }

            if (this.isUndead() || isGhost()) {
                this.setHealth(getMaxHealth());

            }

            // pegasus, dark pegasus, or bat horse
            if (this.getType() == 39 || this.getType() == 32 || this.getType() == 40) {

                // transformType = 25; //undead pegasus
                transform(25);

            } else if (this.getType() == 36 || (this.getType() > 47 && this.getType() < 60)) // unicorn or fairies
            {

                // transformType = 24; //undead unicorn
                transform(24);
            } else if (this.getType() < 21 || this.getType() == 60 || this.getType() == 61) // regular horses or zebras
            {

                // transformType = 23; //undead
                transform(23);
            }

            drinkingHorse();
            return true;
        }

        // to transform to nightmares: only pure breeds
        if ((itemstack != null) && this.getIsTamed() && itemstack.getItem() == MoCreatures.essencefire) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Items.glass_bottle));
            } else {
                entityplayer.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
            }

            if (this.isNightmare()) {
                if (getIsAdult() && getHealth() == getMaxHealth()) {
                    this.eatenpumpkin = true;
                }
                this.setHealth(getMaxHealth());

            }
            if (this.getType() == 61) {
                //nightmare
                transform(38);
            }
            drinkingHorse();
            return true;
        }

        // transform to dark pegasus
        if ((itemstack != null) && this.getIsTamed() && itemstack.getItem() == MoCreatures.essencedarkness) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Items.glass_bottle));
            } else {
                entityplayer.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
            }

            if (this.getType() == 32) {
                if (getIsAdult() && getHealth() == getMaxHealth()) {
                    this.eatenpumpkin = true;
                }
                this.setHealth(getMaxHealth());
            }

            if (this.getType() == 61) {
                transform(32); //horsezorse to bat horse
            }

            if (this.getType() == 39) // pegasus to darkpegasus
            {
                //darkpegasus
                transform(40);
            }
            drinkingHorse();
            return true;
        }

        if ((itemstack != null) && this.getIsTamed() && itemstack.getItem() == MoCreatures.essencelight) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Items.glass_bottle));
            } else {
                entityplayer.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
            }

            if (this.isMagicHorse()) {
                if (getIsAdult() && getHealth() == getMaxHealth()) {
                    this.eatenpumpkin = true;
                }
                this.setHealth(getMaxHealth());
            }

            if (this.isNightmare()) {
                // unicorn
                transform(36);
            }
            if (this.getType() == 32 && this.posY > 128D) // bathorse to pegasus
            {
                // pegasus
                transform(39);
            }
            // to return undead horses to pristine conditions
            if (this.isUndead() && this.getIsAdult() && MoCreatures.isServer()) {
                setEdad(10);
                if (this.getType() >= 26) {
                    setType(getType() - 3);
                }
            }
            drinkingHorse();
            return true;
        }

        if ((itemstack != null) && this.isAmuletHorse() && this.getIsTamed()) {
            if ((this.getType() == 26 || this.getType() == 27 || this.getType() == 28) && itemstack.getItem() == MoCreatures.amuletbone) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                vanishHorse();
                return true;
            }

            if ((this.getType() > 47 && this.getType() < 60) && itemstack.getItem() == MoCreatures.amuletfairy) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                vanishHorse();
                return true;
            }

            if ((this.getType() == 39 || this.getType() == 40) && (itemstack.getItem() == MoCreatures.amuletpegasus)) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                vanishHorse();
                return true;
            }

            if ((this.getType() == 21 || this.getType() == 22) && (itemstack.getItem() == MoCreatures.amuletghost)) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                vanishHorse();
                return true;
            }

        }

        if ((itemstack != null) && (itemstack.getItem() == Items.dye) && this.getType() == 50) {

            int colorInt = itemstack.getItemDamage();
            switch (colorInt) {
                case 14: //orange
                    transform(59);
                    break;
                case 13: //magenta TODO
                    //transform(46);
                    break;
                case 12: //light blue
                    transform(51);
                    break;
                case 11: //yellow
                    transform(48);
                    break;
                case 10: //light green
                    transform(53);
                    break;
                case 9: //pink
                    transform(52);
                    break;
                case 8: //gray TODO
                    //transform(50);
                    break;
                case 7: //light gray TODO
                    //transform(50);
                    break;
                case 6: //cyan
                    transform(57);
                    break;
                case 5: //purple
                    transform(49);
                    break;
                case 4: //dark blue
                    transform(56);
                    break;
                case 3: //brown TODO
                    //transform(50);
                    break;
                case 2: //green
                    transform(58);
                    break;
                case 1: //red
                    transform(55);
                    break;
                case 0: //black
                    transform(54);
                    break;

            }

            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            eatingHorse();
            return true;
        }

        // zebra easter egg
        if ((itemstack != null)
                && (this.getType() == 60)
                && ((itemstack.getItem() == Items.record_11) || (itemstack.getItem() == Items.record_13) || (itemstack.getItem() == Items.record_cat)
                        || (itemstack.getItem() == Items.record_chirp) || (itemstack.getItem() == Items.record_far)
                        || (itemstack.getItem() == Items.record_mall) || (itemstack.getItem() == Items.record_mellohi)
                        || (itemstack.getItem() == Items.record_stal) || (itemstack.getItem() == Items.record_strad) || (itemstack.getItem() == Items.record_ward))) {
            entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            if (MoCreatures.isServer()) {
                EntityItem entityitem1 = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(MoCreatures.recordshuffle, 1));
                entityitem1.setPickupDelay(20);
                this.worldObj.spawnEntityInWorld(entityitem1);
            }
            eatingHorse();
            return true;
        }
        if ((itemstack != null) && (itemstack.getItem() == Items.wheat) && !isMagicHorse() && !isUndead()) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            if (MoCreatures.isServer()) {
                setTemper(getTemper() + 25);
                if (getTemper() > getMaxTemper()) {
                    setTemper(getMaxTemper() - 5);
                }
            }
            if ((getHealth() + 5) > getMaxHealth()) {
                this.setHealth(getMaxHealth());
            }
            eatingHorse();
            if (!getIsAdult() && (getEdad() < getMaxEdad())) {
                setEdad(getEdad() + 1);
            }
            return true;
        }

        if ((itemstack != null) && (itemstack.getItem() == MoCreatures.sugarlump) && !isMagicHorse() && !isUndead()) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            if (MoCreatures.isServer()) {
                setTemper(getTemper() + 25);
                if (getTemper() > getMaxTemper()) {
                    setTemper(getMaxTemper() - 5);
                }
            }
            if ((getHealth() + 10) > getMaxHealth()) {
                this.setHealth(getMaxHealth());
            }
            eatingHorse();
            if (!getIsAdult() && (getEdad() < getMaxEdad())) {
                setEdad(getEdad() + 2);
            }
            return true;
        }

        if ((itemstack != null) && (itemstack.getItem() == Items.bread) && !isMagicHorse() && !isUndead()) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            if (MoCreatures.isServer()) {
                setTemper(getTemper() + 100);
                if (getTemper() > getMaxTemper()) {
                    setTemper(getMaxTemper() - 5);
                }
            }
            if ((getHealth() + 20) > getMaxHealth()) {
                this.setHealth(getMaxHealth());
            }
            eatingHorse();
            if (!getIsAdult() && (getEdad() < getMaxEdad())) {
                setEdad(getEdad() + 3);
            }
            return true;
        }

        if ((itemstack != null) && ((itemstack.getItem() == Items.APPLE) || (itemstack.getItem() == Items.golden_apple)) && !isMagicHorse()
                && !isUndead()) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }

            if (MoCreatures.isServer()) {
                MoCTools.tameWithName(entityplayer, this);
            }

            this.setHealth(getMaxHealth());
            eatingHorse();
            if (!getIsAdult() && (getEdad() < getMaxEdad()) && MoCreatures.isServer()) {
                setEdad(getEdad() + 1);
            }

            return true;
        }

        if ((itemstack != null) && getIsTamed() && (itemstack.getItem() == Item.getItemFromBlock(Blocks.chest)) && (isBagger())) {
            if (getIsChested()) {
                return false;
            }
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }

            //entityplayer.inventory.addItemStackToInventory(new ItemStack(MoCreatures.key));
            setIsChested(true);
            this.worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F) + 1.0F);
            return true;
        }
        if ((itemstack != null) && getIsTamed() && (itemstack.getItem() == MoCreatures.haystack)) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            // eatinghaystack = true;
            setSitting(true);
            eatingHorse();
            if (!isMagicHorse() && !isUndead()) {
                this.setHealth(getMaxHealth());
            }

            return true;
        }
        if (getIsChested() && entityplayer.isSneaking()) {
            // if first time opening horse chest, we must initialize it
            if (this.localchest == null) {
                this.localchest = new MoCAnimalChest("HorseChest", getInventorySize());// , new
            }
            if (this.emptychest == null) {
                this.emptychest = new MoCAnimalChest("HorseChest", 0);
            }
            // only open this chest on server side
            if (MoCreatures.isServer()) {
                InventoryLargeChest singleChest = new InventoryLargeChest("HorseChest", this.localchest, this.emptychest);
                entityplayer.displayGUIChest(singleChest);
            }
            return true;

        }

        if ((itemstack != null)
                && ((itemstack.getItem() == Item.getItemFromBlock(Blocks.pumpkin)) || (itemstack.getItem() == Items.mushroom_stew)
                        || (itemstack.getItem() == Items.cake) || (itemstack.getItem() == Items.golden_carrot))) {
            if (!getIsAdult() || isMagicHorse() || isUndead()) {
                return false;
            }
            if (itemstack.getItem() == Items.mushroom_stew) {
                if (--itemstack.stackSize == 0) {
                    entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Items.bowl));
                } else {
                    entityplayer.inventory.addItemStackToInventory(new ItemStack(Items.bowl));
                }
            } else if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            this.eatenpumpkin = true;
            this.setHealth(getMaxHealth());
            eatingHorse();
            return true;
        }

        if ((itemstack != null) && (itemstack.getItem() == MoCreatures.whip) && getIsTamed() && (this.riddenByEntity == null)) {
            setSitting(!getIsSitting());// eatinghaystack = !eatinghaystack;
            return true;
        }

        if (getIsRideable() && getIsAdult() && (this.riddenByEntity == null)) {
            entityplayer.rotationYaw = this.rotationYaw;
            entityplayer.rotationPitch = this.rotationPitch;
            setSitting(false);
            if (MoCreatures.isServer()) {
                entityplayer.mountEntity(this);
            }
            this.gestationtime = 0;
            return true;
        } else {
            return false;
        }

    }

    /**
     * Can this horse be trapped in a special amulet?
     */
    public boolean isAmuletHorse() {

        return (this.getType() >= 48 && this.getType() < 60) || this.getType() == 40 || this.getType() == 39 || this.getType() == 21
                || this.getType() == 22 || this.getType() == 26 || this.getType() == 27 || this.getType() == 28;
    }

    /**
     * Can wear regular armor
     */
    public boolean isArmored() {
        return (this.getType() < 21);
    }

    /**
     * able to carry bags
     *
     * @return
     */
    public boolean isBagger() {
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
    public boolean isFloater() {
        return this.getType() == 36 // unicorn
                || this.getType() == 27 // skely unicorn
                || this.getType() == 24 // undead unicorn
                || this.getType() == 22; // not winged ghost

    }

    @Override
    public boolean isFlyer() {
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
    public boolean isGhost() {

        return this.getType() == 21 || this.getType() == 22;
    }

    /**
     * Can wear magic armor
     */
    public boolean isMagicHorse() {
        return

        this.getType() == 39 || this.getType() == 36 || this.getType() == 32 || this.getType() == 40 || (this.getType() >= 45 && this.getType() < 60) //fairy
                || this.getType() == 21 || this.getType() == 22;
    }

    @Override
    public boolean isMovementCeased() {
        return getIsSitting() || (this.riddenByEntity != null) || this.standCounter != 0 || this.shuffleCounter != 0 || this.getVanishC() != 0;
    }

    /**
     * Is this a Nightmare horse?
     */
    public boolean isNightmare() {

        return this.getType() == 38;
    }

    /**
     * Rare horse that can be transformed into Nightmares or Bathorses or give
     * ghost horses on dead
     */
    public boolean isPureBreed() {

        return (this.getType() > 10 && this.getType() < 21);
    }

    /**
     * Mobs don't attack you if you're riding one of these they won't reproduce
     * either
     *
     * @return
     */
    public boolean isUndead() {
        return (this.getType() == 23) || (this.getType() == 24) || (this.getType() == 25) || (this.getType() == 26) // skely
                || (this.getType() == 27) // skely unicorn
                || (this.getType() == 28); // skely pegasus
    }

    /**
     * Has an unicorn? to render it and buckle entities!
     *
     * @return
     */
    public boolean isUnicorned() {

        return this.getType() == 36 || (this.getType() >= 45 && this.getType() < 60) || this.getType() == 27 || this.getType() == 24;
    }

    public boolean isZebraRunning() {
        boolean flag = false;
        EntityPlayer ep1 = this.worldObj.getClosestPlayerToEntity(this, 8D);
        if (ep1 != null) {
            flag = true;
            if (ep1.getRidingEntity() != null && ep1.getRidingEntity() instanceof MoCEntityHorse) {
                MoCEntityHorse playerHorse = (MoCEntityHorse) ep1.getRidingEntity();
                if (playerHorse.getType() == 16 || playerHorse.getType() == 17 || playerHorse.getType() == 60 || playerHorse.getType() == 61) {
                    flag = false;
                }
            }

        }
        if (flag) {
            MoCTools.runLikeHell(this, ep1);
        }
        return flag;
    }

    public void LavaFX() {
        MoCreatures.proxy.LavaFX(this);
    }

    public void MaterializeFX() {
        MoCreatures.proxy.MaterializeFX(this);
    }

    private void moveTail() {
        this.tailCounter = 1;
    }

    @Override
    public int nameYOffset() {
        if (this.getIsAdult()) {
            return -80;
        } else {
            return (-5 - getEdad());
        }
    }

    private boolean nearMusicBox() {
        // only works server side
        if (!MoCreatures.isServer()) {
            return false;
        }

        boolean flag = false;
        TileEntityJukebox jukebox = MoCTools.nearJukeBoxRecord(this, 6D);
        if (jukebox != null && jukebox.getRecord() != null) {
            Item record = jukebox.getRecord().getItem();
            Item shuffleRecord = MoCreatures.recordshuffle;
            if (record == shuffleRecord) {
                flag = true;
                if (this.shuffleCounter > 1000) {
                    this.shuffleCounter = 0;
                    MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 102), new TargetPoint(
                            this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 64));
                    flag = false;
                }
            }
        }
        return flag;
    }

    // changed to public since we need to send this info to server
    public void NightmareEffect() {
        if (!MoCTools.mobGriefing(this.worldObj)) {
            setNightmareInt(getNightmareInt() - 1);
            return;
        }
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(getEntityBoundingBox().minY);
        int k = MathHelper.floor_double(this.posZ);
        BlockPos pos = new BlockPos(i, j, k);
        IBlockState blockstate = this.worldObj.getBlockState(pos.add(-1, 0, -1));

        BlockEvent.BreakEvent event = null;
        if (!this.worldObj.isRemote) {
            try {
                event =
                        new BlockEvent.BreakEvent(this.worldObj, pos, blockstate, FakePlayerFactory.get(
                                DimensionManager.getWorld(this.worldObj.provider.getDimensionId()), MoCreatures.MOCFAKEPLAYER));
            } catch (Throwable t) {
            }

        }
        if (event != null && !event.isCanceled()) {
            this.worldObj.setBlockState(pos, Blocks.fire.getDefaultState(), 3);//MC1.5
            EntityPlayer entityplayer = (EntityPlayer) this.riddenByEntity;
            if ((entityplayer != null) && (entityplayer.isBurning())) {
                entityplayer.extinguish();
            }
            setNightmareInt(getNightmareInt() - 1);
        }
    }

    @Override
    public void onDeath(DamageSource damagesource) {
        super.onDeath(damagesource);
        if (MoCreatures.isServer()) {
            if ((this.rand.nextInt(10) == 0) && (this.getType() == 23) || (this.getType() == 24) || (this.getType() == 25)) {
                MoCTools.spawnMaggots(this.worldObj, this);
            }

            if (getIsTamed() && (isMagicHorse() || isPureBreed()) && !isGhost() && this.rand.nextInt(4) == 0) {
                MoCEntityHorse entityhorse1 = new MoCEntityHorse(this.worldObj);
                entityhorse1.setPosition(this.posX, this.posY, this.posZ);
                this.worldObj.spawnEntityInWorld(entityhorse1);
                MoCTools.playCustomSound(this, "appearmagic", this.worldObj);

                entityhorse1.setOwner(this.getOwnerName());
                entityhorse1.setTamed(true);
                EntityPlayer entityplayer = this.worldObj.getClosestPlayerToEntity(this, 24D);
                if (entityplayer != null) {
                    MoCTools.tameWithName(entityplayer, entityhorse1);
                }

                entityhorse1.setAdult(false);
                entityhorse1.setEdad(1);
                int l = 22;
                if (this.isFlyer()) {
                    l = 21;
                }
                entityhorse1.setType(l);
            }

        }
    }

    @Override
    public void onLivingUpdate() {
        /**
         * slow falling
         */
        if (isFlyer() || isFloater()) {
            if (!this.onGround && (this.motionY < 0.0D)) {
                this.motionY *= 0.6D;
            }
        }

        if (this.rand.nextInt(200) == 0) {
            moveTail();
        }

        if ((getType() == 38) && (this.rand.nextInt(50) == 0) && !MoCreatures.isServer()) {
            LavaFX();
        }

        if ((getType() == 36) && isOnAir() && !MoCreatures.isServer()) {
            StarFX();
        }

        if (MoCreatures.isServer() && isFlyer() && isOnAir()) {
            float myFlyingSpeed = MoCTools.getMyMovementSpeed(this);
            int wingFlapFreq = (int) (25 - (myFlyingSpeed * 10));
            if (this.riddenByEntity == null || wingFlapFreq < 5) {
                wingFlapFreq = 5;
            }
            if (this.rand.nextInt(wingFlapFreq) == 0) {
                wingFlap();
            }
        }

        if (isFlyer()) {
            if (this.wingFlapCounter > 0 && ++this.wingFlapCounter > 20) {
                this.wingFlapCounter = 0;
            }
            if (this.wingFlapCounter != 0 && this.wingFlapCounter % 5 == 0 && !MoCreatures.isServer()) {
                StarFX();
            }
            if (this.wingFlapCounter == 5 && MoCreatures.isServer()) {
                MoCTools.playCustomSound(this, "wingflap", this.worldObj);
            }
        }

        if (isUndead() && (this.getType() < 26) && getIsAdult() && (this.rand.nextInt(20) == 0)) {
            if (MoCreatures.isServer()) {
                if (this.rand.nextInt(16) == 0) {
                    setEdad(getEdad() + 1);
                }
                if (getEdad() >= 399) {
                    setType(this.getType() + 3);
                }
            } else {
                UndeadFX();
            }

        }

        super.onLivingUpdate();

        if (MoCreatures.isServer()) {
            /**
             * Shuffling LMFAO!
             */
            if (this.getType() == 60 && getIsTamed() && this.rand.nextInt(50) == 0 && nearMusicBox() && shuffleCounter == 0) {
                shuffleCounter = 1;
                MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 101),
                        new TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 64));
            }

            if ((this.rand.nextInt(300) == 0) && (this.deathTime == 0)) {
                this.setHealth(getHealth() + 1);
                if (getHealth() > getMaxHealth()) {
                    this.setHealth(getMaxHealth());
                }
            }

            if (!getIsSitting() && !getIsTamed() && this.rand.nextInt(300) == 0) {
                setSitting(true);
            }

            if (getIsSitting() && ++this.countEating > 50 && !getIsTamed()) {
                this.countEating = 0;
                setSitting(false);
            }

            if ((getType() == 38) && (this.riddenByEntity != null) && (getNightmareInt() > 0) && (this.rand.nextInt(2) == 0)) {
                NightmareEffect();
            }

            /**
             * zebras on the run!
             */
            /*
            if (this.getType() == 60 && !getIsTamed()) {
                boolean flag = isZebraRunning();
            }*/

            /**
             * foal following mommy!
             */
            /*if (!getIsAdult() && (this.rand.nextInt(200) == 0)) {
                setEdad(getEdad() + 1);
                if (getEdad() >= 100) {
                    setAdult(true);
                    setBred(false);
                    MoCEntityHorse mommy = getClosestMommy(this, 16D);
                    if (mommy != null) {
                        mommy.setBred(false);
                    }
                }
            }*/

            /**
             * Buckling
             */
            if ((this.sprintCounter > 0 && this.sprintCounter < 150) && isUnicorned() && (this.riddenByEntity != null)) {
                MoCTools.buckleMobs(this, 2D, this.worldObj);
            }

            if (isFlyer() && !getIsTamed() && this.rand.nextInt(100) == 0 && !isMovementCeased() && !getIsSitting()) {
                wingFlap();
            }

            if (!ReadyforParenting(this)) {
                return;
            }

            int i = 0;

            List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(8D, 3D, 8D));
            for (int j = 0; j < list.size(); j++) {
                Entity entity = list.get(j);
                if (entity instanceof MoCEntityHorse || entity instanceof EntityHorse) {
                    i++;
                }
            }

            if (i > 1) {
                return;
            }
            List<Entity> list1 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(4D, 2D, 4D));
            for (int k = 0; k < list1.size(); k++) {
                Entity horsemate = list1.get(k);
                boolean flag = (horsemate instanceof EntityHorse);
                if (!(horsemate instanceof MoCEntityHorse || flag) || (horsemate == this)) {
                    continue;
                }

                if (!flag) {
                    if (!ReadyforParenting((MoCEntityHorse) horsemate)) {
                        return;
                    }
                }

                if (this.rand.nextInt(100) == 0) {
                    this.gestationtime++;
                }

                if (this.gestationtime % 3 == 0) {
                    MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageHeart(this.getEntityId()),
                            new TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 64));
                }

                if (this.gestationtime <= 50) {
                    continue;
                }
                MoCEntityHorse baby = new MoCEntityHorse(this.worldObj);
                baby.setPosition(this.posX, this.posY, this.posZ);
                this.worldObj.spawnEntityInWorld(baby);
                this.worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F) + 1.0F);
                this.eatenpumpkin = false;
                this.gestationtime = 0;
                //this.setBred(true);

                int horsemateType;// = 0;
                if (flag) {
                    horsemateType = TranslateVanillaHorseType((EntityHorse) horsemate);
                    if (horsemateType == -1) {
                        return;
                    }
                } else {
                    horsemateType = ((MoCEntityHorse) horsemate).getType();
                    ((MoCEntityHorse) horsemate).eatenpumpkin = false;
                    ((MoCEntityHorse) horsemate).gestationtime = 0;
                }
                int l = HorseGenetics(this.getType(), horsemateType);

                if (l == 50 || l == 54) // fairy horse!
                {
                    MoCTools.playCustomSound(this, "appearmagic", this.worldObj);
                    if (!flag) {
                        ((MoCEntityHorse) horsemate).dissapearHorse();
                    }
                    this.dissapearHorse();
                }
                baby.setOwner(this.getOwnerName());
                baby.setTamed(true);
                //baby.setBred(true);
                baby.setAdult(false);
                EntityPlayer entityplayer = this.worldObj.getPlayerEntityByName(this.getOwnerName());
                if (entityplayer != null) {
                    MoCTools.tameWithName(entityplayer, baby);
                }
                baby.setType(l);
                break;
            }
        }

    }

    /**
     * Obtains the 'Type' of vanilla horse for inbreeding with MoC Horses
     *
     * @param horse
     * @return
     */
    private int TranslateVanillaHorseType(EntityHorse horse) {
        if (horse.getHorseType() == 1) {
            return 65; // donkey
        }
        if (horse.getHorseType() == 0) {
            switch ((byte) horse.getHorseVariant()) {
                case 0: //white
                    return 1;
                case 1: //creamy
                    return 2;
                case 3: //brown
                    return 3;
                case 4: //black
                    return 5;
                case 5: //gray
                    return 9;
                case 6: //dark brown
                    return 4;
                default:
                    return 3;
            }

        }
        return -1;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (this.shuffleCounter > 0) {
            ++this.shuffleCounter;
            if (!MoCreatures.isServer() && this.shuffleCounter % 20 == 0) {
                double var2 = this.rand.nextGaussian() * 0.5D;
                double var4 = this.rand.nextGaussian() * -0.1D;
                double var6 = this.rand.nextGaussian() * 0.02D;
                this.worldObj.spawnParticle(EnumParticleTypes.NOTE, this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY
                        + 0.5D + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, var2, var4,
                        var6);
            }

            if ((MoCreatures.isServer() && !nearMusicBox())) {
                this.shuffleCounter = 0;
                MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 102),
                        new TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 64));
            }
        }

        if (this.mouthCounter > 0 && ++this.mouthCounter > 30) {
            this.mouthCounter = 0;
        }

        if (this.standCounter > 0 && ++this.standCounter > 20) {
            this.standCounter = 0;
        }

        if (this.tailCounter > 0 && ++this.tailCounter > 8) {
            this.tailCounter = 0;
        }

        if (getVanishC() > 0) {

            setVanishC((byte) (getVanishC() + 1));

            if (getVanishC() < 15 && !MoCreatures.isServer()) {
                VanishFX();

            }

            if (getVanishC() > 100) {
                setVanishC((byte) 101);
                MoCTools.dropHorseAmulet(this);
                dissapearHorse();
            }

            if (getVanishC() == 1) {
                MoCTools.playCustomSound(this, "vanish", this.worldObj);
            }

            if (getVanishC() == 70) {
                stand();
            }
        }

        if (this.sprintCounter > 0) {
            ++this.sprintCounter;
            if (this.sprintCounter < 150 && this.sprintCounter % 2 == 0 && !MoCreatures.isServer()) {
                StarFX();
            }

            if (this.sprintCounter > 300) {
                this.sprintCounter = 0;
            }
        }

        /*if (this.wingFlapCounter > 0) {
            ++this.wingFlapCounter;
            if (this.wingFlapCounter % 5 == 0 && !MoCreatures.isServer()) {
                StarFX();
            }
            if (this.wingFlapCounter > 20) {
                this.wingFlapCounter = 0;

            }
        }*/

        if (this.transformCounter > 0) {
            if (this.transformCounter == 40) {
                MoCTools.playCustomSound(this, "transform", this.worldObj);
            }

            if (++this.transformCounter > 100) {
                this.transformCounter = 0;
                if (this.transformType != 0) {
                    dropArmor();
                    setType(this.transformType);
                }
            }
        }

        if (isGhost() && getEdad() < 10 && this.rand.nextInt(7) == 0) {
            setEdad(getEdad() + 1);
        }

        if (isGhost() && getEdad() == 9) {
            setEdad(100);
            setAdult(true);
        }
    }

    private void openMouth() {
        this.mouthCounter = 1;
    }

    public boolean ReadyforParenting(MoCEntityHorse entityhorse) {
        int i = entityhorse.getType();
        return (entityhorse.riddenByEntity == null) && (entityhorse.getRidingEntity() == null) && entityhorse.getIsTamed() && entityhorse.eatenpumpkin
                && entityhorse.getIsAdult() && !entityhorse.isUndead() && !entityhorse.isGhost() && (i != 61) && (i < 66);
    }

    @Override
    public boolean rideableEntity() {
        return true;
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
    public void selectType() {
        checkSpawningBiome();
        if (getType() == 0) {
            if (this.rand.nextInt(5) == 0) {
                setAdult(false);
            }
            int j = this.rand.nextInt(100);
            int i = MoCreatures.proxy.zebraChance;
            if (j <= (33 - i)) {
                setType(6);
            } else if (j <= (66 - i)) {
                setType(7);
            } else if (j <= (99 - i)) {
                setType(8);
            } else {
                setType(60);// zebra
            }
        }
    }

    @Override
    public void setArmorType(byte i) {
        this.dataWatcher.updateObject(25, Integer.valueOf(i));
    }

    /*public void setBred(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(26, Byte.valueOf(input));
    }*/

    public void setIsChested(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(23, Byte.valueOf(input));
    }

    public void setSitting(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(24, Byte.valueOf(input));
    }

    public void setNightmareInt(int i) {
        this.nightmareInt = i;
    }

    public void setReproduced(boolean var1) {
        this.hasReproduced = var1;
    }

    @Override
    public void setRideable(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(27, Byte.valueOf(input));
    }

    /*public void setRoped(boolean var1) {
    }*/

    /**
     * New networked to fix SMP issues
     *
     * @return
     */
    public void setVanishC(byte i) {
        this.vanishCounter = i;
    }

    private void stand() {
        if (this.riddenByEntity == null && !this.isOnAir()) {
            this.standCounter = 1;
        }
    }

    public void StarFX() {
        MoCreatures.proxy.StarFX(this);
    }

    /**
     * Used to flicker ghosts
     *
     * @return
     */
    public float tFloat() {
        if (++this.fCounter > 60) {
            this.fCounter = 0;
            this.transFloat = (this.rand.nextFloat() * (0.6F - 0.3F) + 0.3F);
        }

        if (isGhost() && getEdad() < 10) {
            this.transFloat = 0;
        }
        return this.transFloat;
    }

    public void transform(int tType) {
        if (MoCreatures.isServer()) {
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), tType),
                    new TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 64));
        }

        this.transformType = tType;
        if (this.riddenByEntity == null && this.transformType != 0) {
            dropArmor();
            this.transformCounter = 1;
        }
    }

    public void UndeadFX() {
        MoCreatures.proxy.UndeadFX(this);
    }

    public void VanishFX() {
        MoCreatures.proxy.VanishFX(this);
    }

    /**
     * Called to vanish Horse
     */

    public void vanishHorse() {
        this.getNavigator().clearPathEntity();
        this.motionX = 0D;
        this.motionZ = 0D;

        if (this.isBagger()) {
            MoCTools.dropInventory(this, this.localchest);
            dropBags();
        }
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageVanish(this.getEntityId()),
                    new TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 64));
            setVanishC((byte) 1);
        }
        MoCTools.playCustomSound(this, "vanish", this.worldObj);
    }

    @Override
    public void dropMyStuff() {
        dropArmor();
        MoCTools.dropSaddle(this, this.worldObj);
        if (this.isBagger()) {
            MoCTools.dropInventory(this, this.localchest);
            dropBags();
        }
    }

    public void wingFlap() {
        if (this.isFlyer() && this.wingFlapCounter == 0) {
            this.wingFlapCounter = 1;
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 3),
                    new TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 64));
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("Saddle", getIsRideable());
        nbttagcompound.setBoolean("EatingHaystack", getIsSitting());
        nbttagcompound.setBoolean("ChestedHorse", getIsChested());
        nbttagcompound.setBoolean("HasReproduced", getHasReproduced());
        //nbttagcompound.setBoolean("Bred", getHasBred());
        nbttagcompound.setInteger("ArmorType", getArmorType());

        if (getIsChested() && this.localchest != null) {
            NBTTagList nbttaglist = new NBTTagList();
            for (int i = 0; i < this.localchest.getSizeInventory(); i++) {
                // grab the current item stack
                this.localstack = this.localchest.getStackInSlot(i);
                if (this.localstack != null) {
                    NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                    nbttagcompound1.setByte("Slot", (byte) i);
                    this.localstack.writeToNBT(nbttagcompound1);
                    nbttaglist.appendTag(nbttagcompound1);
                }
            }
            nbttagcompound.setTag("Items", nbttaglist);
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        setRideable(nbttagcompound.getBoolean("Saddle"));
        setSitting(nbttagcompound.getBoolean("EatingHaystack"));
        //setBred(nbttagcompound.getBoolean("Bred"));
        setIsChested(nbttagcompound.getBoolean("ChestedHorse"));
        setReproduced(nbttagcompound.getBoolean("HasReproduced"));
        setArmorType((byte) nbttagcompound.getInteger("ArmorType"));
        if (getIsChested()) {
            NBTTagList nbttaglist = nbttagcompound.getTagList("Items", 10);
            this.localchest = new MoCAnimalChest("HorseChest", getInventorySize());

            for (int i = 0; i < nbttaglist.tagCount(); i++) {
                ItemStack itemstack = this.localchest.getStackInSlot(i);

                if (itemstack != null) {
                    this.localchest.setInventorySlotContents(i, itemstack.copy());
                }
            }
        }
    }

    @Override
    public void performAnimation(int animationType) {
        //23,24,25,32,36,38,39,40,51,52,53
        if (animationType >= 23 && animationType < 60) //transform
        {
            this.transformType = animationType;
            this.transformCounter = 1;
        }
        if (animationType == 3) //wing flap 
        {
            this.wingFlapCounter = 1;
        }
        if (animationType == 101) //zebra Shuffle starts
        {
            this.shuffleCounter = 1;
        }
        if (animationType == 102) //zebra Shuffle ends
        {
            this.shuffleCounter = 0;
        }
    }

    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        if (isUndead()) {
            return EnumCreatureAttribute.UNDEAD;
        }
        return super.getCreatureAttribute();
    }

    @Override
    protected boolean canBeTrappedInNet() {
        return getIsTamed() && !isAmuletHorse();
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 4;
    }

    @Override
    public void setType(int i) {
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(calculateMaxHealth());
        this.setHealth(getMaxHealth());
        if (getType() == 38 || getType() == 40) {
            this.isImmuneToFire = true;
        }
        super.setType(i);
    }

    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
        if (getType() == 38 || getType() == 40) {
            this.isImmuneToFire = true;
        }
        return super.onInitialSpawn(difficulty, livingdata);
    }

    @Override
    public void updateRiderPosition() {
        double dist = getSizeFactor() * (0.25D);
        double newPosX = this.posX + (dist * Math.sin(this.renderYawOffset / 57.29578F));
        double newPosZ = this.posZ - (dist * Math.cos(this.renderYawOffset / 57.29578F));
        this.riddenByEntity.setPosition(newPosX, this.posY + getMountedYOffset() + this.riddenByEntity.getYOffset(), newPosZ);
    }

    @Override
    public void makeEntityJump() {
        wingFlap();
        super.makeEntityJump();
    }
}
