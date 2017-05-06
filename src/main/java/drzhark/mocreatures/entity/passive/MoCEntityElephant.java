package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.EntityAIFollowAdult;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.inventory.MoCAnimalChest;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import drzhark.mocreatures.util.MoCSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

import java.util.List;

import javax.annotation.Nullable;

public class MoCEntityElephant extends MoCEntityTameableAnimal {

    public int sprintCounter;
    public int sitCounter;
    public MoCAnimalChest localelephantchest;
    public MoCAnimalChest localelephantchest2;
    public MoCAnimalChest localelephantchest3;
    public MoCAnimalChest localelephantchest4;
    public ItemStack localstack;
    boolean hasPlatform;
    public int tailCounter;
    public int trunkCounter;
    public int earCounter;
    private byte tuskUses;
    private byte temper;
    private static final DataParameter<Integer> TUSK_TYPE = EntityDataManager.<Integer>createKey(MoCEntityElephant.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> STORAGE_TYPE = EntityDataManager.<Integer>createKey(MoCEntityElephant.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> HARNESS_TYPE = EntityDataManager.<Integer>createKey(MoCEntityElephant.class, DataSerializers.VARINT);


    public MoCEntityElephant(World world) {
        super(world);
        setAdult(true);
        setTamed(false);
        setEdad(50);
        setSize(1.1F, 3F); //TODO 
        this.stepHeight = 1.0F;

        if (MoCreatures.isServer()) {
            if (this.rand.nextInt(4) == 0) {
                setAdult(false);
            } else {
                setAdult(true);
            }
        }
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(4, new EntityAIFollowAdult(this, 1.0D));
        this.tasks.addTask(5, new EntityAIAttackMelee(this, 1.0D, true));
        this.tasks.addTask(6, new EntityAIWanderMoC2(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
    }

    @Override
    public void selectType() {
        checkSpawningBiome();
        if (getType() == 0) {
            setType(this.rand.nextInt(2) + 1);
        }
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(calculateMaxHealth());
        this.setHealth(getMaxHealth());
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(TUSK_TYPE, Integer.valueOf(0));// tusks: 0 nothing, 1 wood, 2 iron, 3 diamond
        this.dataManager.register(STORAGE_TYPE, Integer.valueOf(0));// storage: 0 nothing, 1 chest, 2 chests....
        this.dataManager.register(HARNESS_TYPE, Integer.valueOf(0));// harness: 0 nothing, 1 harness, 2 cabin
    }

    public int getTusks() {
    	return ((Integer)this.dataManager.get(TUSK_TYPE)).intValue();
    }

    public void setTusks(int i) {
    	this.dataManager.set(TUSK_TYPE, Integer.valueOf(i));
    }

    @Override
    public int getArmorType() {
        return ((Integer)this.dataManager.get(HARNESS_TYPE)).intValue();
    }

    @Override
    public void setArmorType(int i) {
    	this.dataManager.set(HARNESS_TYPE, Integer.valueOf(i));
    }

    public int getStorage() {
    	return ((Integer)this.dataManager.get(STORAGE_TYPE)).intValue();
    }

    public void setStorage(int i) {
    	this.dataManager.set(STORAGE_TYPE, Integer.valueOf(i));
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getType()) {
            case 1:
                return MoCreatures.proxy.getTexture("elephantafrican.png");
            case 2:
                return MoCreatures.proxy.getTexture("elephantindian.png");
            case 3:
                return MoCreatures.proxy.getTexture("mammoth.png");
            case 4:
                return MoCreatures.proxy.getTexture("mammothsonghua.png");
            case 5:
                return MoCreatures.proxy.getTexture("elephantindianpretty.png");
            default:
                return MoCreatures.proxy.getTexture("elephantafrican.png");
        }
    }

    public float calculateMaxHealth() {
        switch (getType()) {
            case 1:
                return 40;
            case 2:
                return 30;
            case 3:
                return 50;
            case 4:
                return 60;
            case 5:
                return 40;

            default:
                return 30;
        }
    }

    @Override
    public double getCustomSpeed() {
        if (this.sitCounter != 0) {
            return 0D;
        }
        double tSpeed = 0.5D;
        if (getType() == 1) {
            tSpeed = 0.55D;
        } else if (getType() == 2) {
            tSpeed = 0.6D;
        } else if (getType() == 3) {
            tSpeed = 0.5D;
        } else if (getType() == 4) {
            tSpeed = 0.55D;
        } else if (getType() == 5) {
            tSpeed = 0.5D;
        }

        if (this.sprintCounter > 0 && this.sprintCounter < 150) {
            tSpeed *= 1.5D;
        }
        if (this.sprintCounter > 150) {
            tSpeed *= 0.5D;
        }

        return tSpeed;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (MoCreatures.isServer()) {
            if ((this.sprintCounter > 0 && this.sprintCounter < 150) && (this.isBeingRidden()) && rand.nextInt(15) == 0) {
                MoCTools.buckleMobsNotPlayers(this, 3D, this.worldObj);
            }

            if (this.sprintCounter > 0 && ++this.sprintCounter > 300) {
                this.sprintCounter = 0;
            }

            if (getIsTamed() && (!this.isBeingRidden()) && getArmorType() >= 1 && this.rand.nextInt(20) == 0) {
                EntityPlayer ep = this.worldObj.getClosestPlayerToEntity(this, 3D);
                if (ep != null && (!MoCreatures.proxy.enableOwnership || ep.getUniqueID().equals(this.getOwnerId())) && ep.isSneaking()) {
                    sit();
                }
            }

            if (MoCreatures.proxy.elephantBulldozer && getIsTamed() && (this.isBeingRidden()) && (getTusks() > 0)) {
                int height = 2;
                if (getType() == 3) {
                    height = 3;
                }
                if (getType() == 4) {
                    height = 3;
                }
                int dmg = MoCTools.destroyBlocksInFront(this, 2D, this.getTusks(), height);
                checkTusks(dmg);

            }

        } else //client only animation counters
        {
            if (this.tailCounter > 0 && ++this.tailCounter > 8) {
                this.tailCounter = 0;
            }

            if (this.rand.nextInt(200) == 0) {
                this.tailCounter = 1;
            }

            if (this.trunkCounter > 0 && ++this.trunkCounter > 38) {
                this.trunkCounter = 0;
            }

            if (this.trunkCounter == 0 && this.rand.nextInt(200) == 0) {
                this.trunkCounter = rand.nextInt(10) + 1;
            }

            if (this.earCounter > 0 && ++this.earCounter > 30) {
                this.earCounter = 0;
            }

            if (this.rand.nextInt(200) == 0) {
                this.earCounter = rand.nextInt(20) + 1;
            }

        }

        if (this.sitCounter != 0) {
            if (++this.sitCounter > 100) {
                this.sitCounter = 0;
            }
        }
    }

    /**
     * Checks if the tusks sets need to break or not (wood = 59, stone = 131,
     * iron = 250, diamond = 1561, gold = 32)
     *
     * @param dmg
     */
    private void checkTusks(int dmg) {
        this.tuskUses += (byte) dmg;
        if ((this.getTusks() == 1 && this.tuskUses > 59) || (this.getTusks() == 2 && this.tuskUses > 250)
                || (this.getTusks() == 3 && this.tuskUses > 1000)) {
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_TURTLE_HURT);
            setTusks((byte) 0);
        }
    }

    private void sit() {
        this.sitCounter = 1;
        if (MoCreatures.isServer()) {
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 0),
                    new TargetPoint(this.worldObj.provider.getDimensionType().getId(), this.posX, this.posY, this.posZ, 64));
        }
        this.getNavigator().clearPathEntity();
    }

    @Override
    public void performAnimation(int animationType) {
        if (animationType == 0) //sitting animation
        {
            this.sitCounter = 1;
            this.getNavigator().clearPathEntity();
        }
    }

    @Override
    public boolean processInteract(EntityPlayer entityplayer, EnumHand hand, @Nullable ItemStack itemstack) {
        if (super.processInteract(entityplayer, hand, itemstack)) {
            return false;
        }
        boolean onMainHand = (hand == EnumHand.MAIN_HAND);
        if ((itemstack != null) && onMainHand && !getIsTamed() && !getIsAdult() && itemstack.getItem() == Items.CAKE) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_EATING);
            this.temper += 2;
            this.setHealth(getMaxHealth());
            if (MoCreatures.isServer() && !getIsAdult() && !getIsTamed() && this.temper >= 10) {
                MoCTools.tameWithName(entityplayer, this);
            }
            return true;
        }

        if ((itemstack != null) && onMainHand &&!getIsTamed() && !getIsAdult() && itemstack.getItem() == MoCreatures.sugarlump) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_EATING);
            this.temper += 1;
            this.setHealth(getMaxHealth());
            if (MoCreatures.isServer() && !getIsAdult() && !getIsTamed() && this.temper >= 10) {
                setTamed(true);
                MoCTools.tameWithName(entityplayer, this);
            }
            return true;
        }

        if ((itemstack != null) && onMainHand && getIsTamed() && getIsAdult() && getArmorType() == 0 && itemstack.getItem() == MoCreatures.elephantHarness) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ROPING);
            setArmorType((byte) 1);
            return true;
        }

        if ((itemstack != null) && onMainHand && getIsTamed() && getIsAdult() && getArmorType() >= 1 && getStorage() == 0
                && itemstack.getItem() == MoCreatures.elephantChest) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ROPING);
            //entityplayer.inventory.addItemStackToInventory(new ItemStack(MoCreatures.key));
            setStorage((byte) 1);
            return true;
        }
        // second storage unit
        if ((itemstack != null) && onMainHand && getIsTamed() && getIsAdult() && getArmorType() >= 1 && getStorage() == 1
                && itemstack.getItem() == MoCreatures.elephantChest) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ROPING);
            setStorage((byte) 2);
            return true;
        }
        // third storage unit for small mammoths
        if ((itemstack != null) && onMainHand && getIsTamed() && getIsAdult() && (getType() == 3) && getArmorType() >= 1 && getStorage() == 2
                && itemstack.getItem() == Item.getItemFromBlock(Blocks.CHEST)) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ROPING);
            setStorage((byte) 3);
            return true;
        }
        // fourth storage unit for small mammoths
        if ((itemstack != null) && onMainHand && getIsTamed() && getIsAdult() && (getType() == 3) && getArmorType() >= 1 && getStorage() == 3
                && itemstack.getItem() == Item.getItemFromBlock(Blocks.CHEST)) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ROPING);
            setStorage((byte) 4);
            return true;
        }

        //giving a garment to an indian elephant with an harness will make it pretty
        if ((itemstack != null) && onMainHand && getIsTamed() && getIsAdult() && getArmorType() == 1 && getType() == 2
                && itemstack.getItem() == MoCreatures.elephantGarment) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ROPING);
            setArmorType((byte) 2);
            setType(5);
            return true;
        }

        //giving a howdah to a pretty indian elephant with a garment will attach the howdah
        if ((itemstack != null) && onMainHand && getIsTamed() && getIsAdult() && getArmorType() == 2 && getType() == 5
                && itemstack.getItem() == MoCreatures.elephantHowdah) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ROPING);
            setArmorType((byte) 3);
            return true;
        }

        //giving a platform to a ? mammoth with harness will attach the platform
        if ((itemstack != null) && onMainHand && getIsTamed() && getIsAdult() && getArmorType() == 1 && getType() == 4
                && itemstack.getItem() == MoCreatures.mammothPlatform) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ARMOR_OFF);
            setArmorType((byte) 3);
            return true;
        }

        if ((itemstack != null) && onMainHand && getIsTamed() && getIsAdult() && itemstack.getItem() == MoCreatures.tusksWood) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ARMOR_OFF);
            dropTusks();
            this.tuskUses = (byte) itemstack.getItemDamage();
            setTusks((byte) 1);
            return true;
        }

        if ((itemstack != null) && onMainHand && getIsTamed() && getIsAdult() && itemstack.getItem() == MoCreatures.tusksIron) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ARMOR_OFF);
            dropTusks();
            this.tuskUses = (byte) itemstack.getItemDamage();
            setTusks((byte) 2);
            return true;
        }

        if ((itemstack != null) && onMainHand && getIsTamed() && getIsAdult() && itemstack.getItem() == MoCreatures.tusksDiamond) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ARMOR_OFF);
            dropTusks();
            this.tuskUses = (byte) itemstack.getItemDamage();
            setTusks((byte) 3);
            return true;
        }

        if (entityplayer.isSneaking()) {
            initChest();
            if (getStorage() == 1) {
                if (MoCreatures.isServer()) {
                    if (MoCreatures.isServer()) {
                        entityplayer.displayGUIChest(this.localelephantchest);
                    }
                }
                return true;
            }
            if (getStorage() == 2) {
                InventoryLargeChest doubleChest = new InventoryLargeChest("ElephantChest", this.localelephantchest, this.localelephantchest2);
                if (MoCreatures.isServer()) {
                    entityplayer.displayGUIChest(doubleChest);
                }
                return true;
            }
            if (getStorage() == 3) {
                InventoryLargeChest doubleChest = new InventoryLargeChest("ElephantChest", this.localelephantchest, this.localelephantchest2);
                InventoryLargeChest tripleChest = new InventoryLargeChest("ElephantChest", doubleChest, this.localelephantchest3);
                if (MoCreatures.isServer()) {
                    entityplayer.displayGUIChest(tripleChest);
                }
                return true;
            }
            if (getStorage() == 4) {
                InventoryLargeChest doubleChest = new InventoryLargeChest("ElephantChest", this.localelephantchest, this.localelephantchest2);
                InventoryLargeChest doubleChestb = new InventoryLargeChest("ElephantChest", this.localelephantchest3, this.localelephantchest4);
                InventoryLargeChest fourChest = new InventoryLargeChest("ElephantChest", doubleChest, doubleChestb);
                if (MoCreatures.isServer()) {
                    entityplayer.displayGUIChest(fourChest);
                }
                return true;
            }

        }
        if ((itemstack != null)
                && onMainHand && getTusks() > 0 
                && ((itemstack.getItem() == Items.DIAMOND_PICKAXE) || (itemstack.getItem() == Items.WOODEN_PICKAXE)
                        || (itemstack.getItem() == Items.STONE_PICKAXE) || (itemstack.getItem() == Items.IRON_PICKAXE) || (itemstack.getItem() == Items.GOLDEN_PICKAXE))) {
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ARMOR_OFF);
            dropTusks();
            return true;
        }

        if (getIsTamed() && getIsAdult() && getArmorType() >= 1 && this.sitCounter != 0) {
            entityplayer.rotationYaw = this.rotationYaw;
            entityplayer.rotationPitch = this.rotationPitch;
            this.sitCounter = 0;
            if (MoCreatures.isServer()) {
                entityplayer.startRiding(this);
            }
            return true;
        }
        return false;
    }

    private void initChest() {
        if (getStorage() > 0 && this.localelephantchest == null) {
            this.localelephantchest = new MoCAnimalChest("ElephantChest", 18);
        }

        if (getStorage() > 1 && this.localelephantchest2 == null) {
            this.localelephantchest2 = new MoCAnimalChest("ElephantChest", 18);
        }

        if (getStorage() > 2 && this.localelephantchest3 == null) {
            this.localelephantchest3 = new MoCAnimalChest("ElephantChest", 9);
        }

        if (getStorage() > 3 && this.localelephantchest4 == null) {
            this.localelephantchest4 = new MoCAnimalChest("ElephantChest", 9);
        }
    }

    /**
     * Drops tusks, makes sound
     */
    private void dropTusks() {
        if (!MoCreatures.isServer()) {
            return;
        }
        int i = getTusks();

        if (i == 1) {
            EntityItem entityitem =
                    new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(MoCreatures.tusksWood, 1, this.tuskUses));
            entityitem.setPickupDelay(10);
            this.worldObj.spawnEntityInWorld(entityitem);
        }
        if (i == 2) {
            EntityItem entityitem =
                    new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(MoCreatures.tusksIron, 1, this.tuskUses));
            entityitem.setPickupDelay(10);
            this.worldObj.spawnEntityInWorld(entityitem);
        }
        if (i == 3) {
            EntityItem entityitem =
                    new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(MoCreatures.tusksDiamond, 1, this.tuskUses));
            entityitem.setPickupDelay(10);
            this.worldObj.spawnEntityInWorld(entityitem);
        }
        setTusks((byte) 0);
        this.tuskUses = 0;
    }

    @Override
    public boolean rideableEntity() {
        return true;
    }

    /*@Override
    public boolean updateMount() {
        return getIsTamed();
    }
    */
    /*@Override
    public boolean forceUpdates() {
        return getIsTamed();
    }*/

    @Override
    public boolean checkSpawningBiome() {
        BlockPos pos = new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(getEntityBoundingBox().minY), this.posZ);
        Biome currentbiome = MoCTools.Biomekind(this.worldObj, pos);

        if (BiomeDictionary.isBiomeOfType(currentbiome, Type.SNOWY)) {
            setType(3 + this.rand.nextInt(2));
            return true;
        }
        if (BiomeDictionary.isBiomeOfType(currentbiome, Type.SANDY)) {
            setType(1);
            return true;
        }

        if (BiomeDictionary.isBiomeOfType(currentbiome, Type.JUNGLE)) {
            setType(2);
            return true;
        }

        if (BiomeDictionary.isBiomeOfType(currentbiome, Type.PLAINS) || BiomeDictionary.isBiomeOfType(currentbiome, Type.FOREST)) {
            setType(1 + this.rand.nextInt(2));
            return true;
        }

        return false;
    }

    @Override
    public float getSizeFactor() {
        float sizeF = 1.25F;

        switch (getType()) {
            case 4:
                sizeF *= 1.2F;
                break;
            case 2:
            case 5:
                sizeF *= 0.80F;
                break;
        }

        if (!getIsAdult()) {
            sizeF = sizeF * (getEdad() * 0.01F);
        }
        return sizeF;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        setTusks(nbttagcompound.getInteger("Tusks"));
        setArmorType(nbttagcompound.getInteger("Harness"));
        setStorage(nbttagcompound.getInteger("Storage"));
        this.tuskUses = nbttagcompound.getByte("TuskUses");
        if (getStorage() > 0) {
            NBTTagList nbttaglist = nbttagcompound.getTagList("Items", 10);
            this.localelephantchest = new MoCAnimalChest("ElephantChest", 18);
            for (int i = 0; i < nbttaglist.tagCount(); i++) {
                NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
                int j = nbttagcompound1.getByte("Slot") & 0xff;
                if ((j >= 0) && j < this.localelephantchest.getSizeInventory()) {
                    this.localelephantchest.setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(nbttagcompound1));
                }
            }
        }
        if (getStorage() >= 2) {
            NBTTagList nbttaglist = nbttagcompound.getTagList("Items2", 10);
            this.localelephantchest2 = new MoCAnimalChest("ElephantChest", 18);
            for (int i = 0; i < nbttaglist.tagCount(); i++) {
                NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
                int j = nbttagcompound1.getByte("Slot") & 0xff;
                if ((j >= 0) && j < this.localelephantchest2.getSizeInventory()) {
                    this.localelephantchest2.setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(nbttagcompound1));
                }
            }
        }

        if (getStorage() >= 3) {
            NBTTagList nbttaglist = nbttagcompound.getTagList("Items3", 10);
            this.localelephantchest3 = new MoCAnimalChest("ElephantChest", 9);
            for (int i = 0; i < nbttaglist.tagCount(); i++) {
                NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
                int j = nbttagcompound1.getByte("Slot") & 0xff;
                if ((j >= 0) && j < this.localelephantchest3.getSizeInventory()) {
                    this.localelephantchest3.setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(nbttagcompound1));
                }
            }
        }
        if (getStorage() >= 4) {
            NBTTagList nbttaglist = nbttagcompound.getTagList("Items4", 10);
            this.localelephantchest4 = new MoCAnimalChest("ElephantChest", 9);
            for (int i = 0; i < nbttaglist.tagCount(); i++) {
                NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
                int j = nbttagcompound1.getByte("Slot") & 0xff;
                if ((j >= 0) && j < this.localelephantchest4.getSizeInventory()) {
                    this.localelephantchest4.setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(nbttagcompound1));
                }
            }
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setInteger("Tusks", getTusks());
        nbttagcompound.setInteger("Harness", getArmorType());
        nbttagcompound.setInteger("Storage", getStorage());
        nbttagcompound.setByte("TuskUses", this.tuskUses);

        if (getStorage() > 0 && this.localelephantchest != null) {
            NBTTagList nbttaglist = new NBTTagList();
            for (int i = 0; i < this.localelephantchest.getSizeInventory(); i++) {
                this.localstack = this.localelephantchest.getStackInSlot(i);
                if (this.localstack != null) {
                    NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                    nbttagcompound1.setByte("Slot", (byte) i);
                    this.localstack.writeToNBT(nbttagcompound1);
                    nbttaglist.appendTag(nbttagcompound1);
                }
            }
            nbttagcompound.setTag("Items", nbttaglist);
        }

        if (getStorage() >= 2 && this.localelephantchest2 != null) {
            NBTTagList nbttaglist = new NBTTagList();
            for (int i = 0; i < this.localelephantchest2.getSizeInventory(); i++) {
                this.localstack = this.localelephantchest2.getStackInSlot(i);
                if (this.localstack != null) {
                    NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                    nbttagcompound1.setByte("Slot", (byte) i);
                    this.localstack.writeToNBT(nbttagcompound1);
                    nbttaglist.appendTag(nbttagcompound1);
                }
            }
            nbttagcompound.setTag("Items2", nbttaglist);
        }

        if (getStorage() >= 3 && this.localelephantchest3 != null) {
            NBTTagList nbttaglist = new NBTTagList();
            for (int i = 0; i < this.localelephantchest3.getSizeInventory(); i++) {
                this.localstack = this.localelephantchest3.getStackInSlot(i);
                if (this.localstack != null) {
                    NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                    nbttagcompound1.setByte("Slot", (byte) i);
                    this.localstack.writeToNBT(nbttagcompound1);
                    nbttaglist.appendTag(nbttagcompound1);
                }
            }
            nbttagcompound.setTag("Items3", nbttaglist);
        }

        if (getStorage() >= 4 && this.localelephantchest4 != null) {
            NBTTagList nbttaglist = new NBTTagList();
            for (int i = 0; i < this.localelephantchest4.getSizeInventory(); i++) {
                this.localstack = this.localelephantchest4.getStackInSlot(i);
                if (this.localstack != null) {
                    NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                    nbttagcompound1.setByte("Slot", (byte) i);
                    this.localstack.writeToNBT(nbttagcompound1);
                    nbttaglist.appendTag(nbttagcompound1);
                }
            }
            nbttagcompound.setTag("Items4", nbttaglist);
        }
    }

    @Override
    public boolean isMyHealFood(ItemStack par1ItemStack) {
        return par1ItemStack != null
                && (par1ItemStack.getItem() == Items.BAKED_POTATO || par1ItemStack.getItem() == Items.BREAD || par1ItemStack.getItem() == MoCreatures.haystack);
    }

    @Override
    public boolean isMovementCeased() {
        return (this.isBeingRidden()) || this.sitCounter != 0;
    }

    @Override
    public void Riding() {
        if ((this.isBeingRidden()) && (this.getRidingEntity() instanceof EntityPlayer)) {
            EntityPlayer entityplayer = (EntityPlayer) this.getRidingEntity();
            List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(1.0D, 0.0D, 1.0D));
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    Entity entity = list.get(i);
                    if (entity.isDead) {
                        continue;
                    }
                    entity.onCollideWithPlayer(entityplayer);
                }

            }
            if (entityplayer.isSneaking()) {
                if (MoCreatures.isServer()) {
                    if (this.sitCounter == 0) {
                        sit();
                    }
                    if (this.sitCounter >= 50) {
                        entityplayer.dismountRidingEntity();
                    }

                }
            }
        }
    }

    @Override
    public boolean canBePushed() {
        return !this.isBeingRidden();
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isBeingRidden();
    }

    @Override
    public void updatePassenger(Entity passenger) {

        double dist = (1.0D);
        switch (getType()) {
            case 1:
            case 3:

                dist = 0.8D;
                break;
            case 2:
            case 5:

                dist = 0.1D;
                break;
            case 4:
                dist = 1.2D;

                break;
        }

        double newPosX = this.posX - (dist * Math.cos((MoCTools.realAngle(this.renderYawOffset - 90F)) / 57.29578F));
        double newPosZ = this.posZ - (dist * Math.sin((MoCTools.realAngle(this.renderYawOffset - 90F)) / 57.29578F));
        passenger.setPosition(newPosX, this.posY + getMountedYOffset() + passenger.getYOffset(), newPosZ);
    }

    @Override
    public double getMountedYOffset() {
        double yOff = 0F;
        boolean sit = (this.sitCounter != 0);

        switch (getType()) {
            case 1:
                yOff = 0.55D;
                if (sit) {
                    yOff = -0.05D;
                }
                break;
            case 3:
                yOff = 0.55D;
                if (sit) {
                    yOff = -0.05D;
                }
                break;
            case 2:
            case 5:
                yOff = -0.17D;
                if (sit) {
                    yOff = -0.5D;
                }
                break;
            case 4:
                yOff = 1.2D;
                if (sit) {
                    yOff = 0.45D;
                }
                break;
        }
        return yOff + (this.height * 0.75D);
    }

    /**
     * Had to set to false to avoid damage due to the collision boxes
     */
    @Override
    public boolean isEntityInsideOpaqueBlock() {
        return false;
    }

    @Override
    public int getTalkInterval() {
        return 300;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_ELEPHANT_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound() {
        return MoCSoundEvents.ENTITY_ELEPHANT_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (!getIsAdult() && getEdad() < 80) {
            return MoCSoundEvents.ENTITY_ELEPHANT_AMBIENT_BABY;
        }
        return MoCSoundEvents.ENTITY_ELEPHANT_AMBIENT;
    }

    @Override
    protected Item getDropItem() {
        return MoCreatures.animalHide;
    }

    @Override
    public boolean getCanSpawnHere() {
        return (MoCreatures.entityMap.get(this.getClass()).getFrequency() > 0) && getCanSpawnHereCreature() && getCanSpawnHereLiving();
    }

    @Override
    public void dropMyStuff() {
        if (MoCreatures.isServer()) {
            dropTusks();
            //dropSaddle(this, worldObj);
            if (getStorage() > 0) {
                if (getStorage() > 0) {
                    MoCTools.dropCustomItem(this, this.worldObj, new ItemStack(MoCreatures.elephantChest, 1));
                    if (this.localelephantchest != null) {
                        MoCTools.dropInventory(this, this.localelephantchest);
                    }

                }
                if (getStorage() >= 2) {
                    if (this.localelephantchest2 != null) {
                        MoCTools.dropInventory(this, this.localelephantchest2);
                    }
                    MoCTools.dropCustomItem(this, this.worldObj, new ItemStack(MoCreatures.elephantChest, 1));
                }
                if (getStorage() >= 3) {
                    if (this.localelephantchest3 != null) {
                        MoCTools.dropInventory(this, this.localelephantchest3);
                    }
                    MoCTools.dropCustomItem(this, this.worldObj, new ItemStack(Blocks.CHEST, 1));
                }
                if (getStorage() >= 4) {
                    if (this.localelephantchest4 != null) {
                        MoCTools.dropInventory(this, this.localelephantchest4);
                    }
                    MoCTools.dropCustomItem(this, this.worldObj, new ItemStack(Blocks.CHEST, 1));
                }
                setStorage((byte) 0);
            }
            dropArmor();
        }
    }

    @Override
    public void dropArmor() {
        if (!MoCreatures.isServer()) {
            return;
        }
        if (getArmorType() >= 1) {
            MoCTools.dropCustomItem(this, this.worldObj, new ItemStack(MoCreatures.elephantHarness, 1));
        }
        if (getType() == 5 && getArmorType() >= 2) {

            MoCTools.dropCustomItem(this, this.worldObj, new ItemStack(MoCreatures.elephantGarment, 1));
            if (getArmorType() == 3) {
                MoCTools.dropCustomItem(this, this.worldObj, new ItemStack(MoCreatures.elephantHowdah, 1));
            }
            setType(2);
        }
        if (getType() == 4 && getArmorType() == 3) {
            MoCTools.dropCustomItem(this, this.worldObj, new ItemStack(MoCreatures.mammothPlatform, 1));
        }
        setArmorType((byte) 0);

    }

    @Override
    public int nameYOffset() {
        int yOff = (int) ((100 / getEdad()) * (getSizeFactor() * -110));
        if (getIsAdult()) {
            yOff = (int) (getSizeFactor() * -110);
        }
        if (sitCounter != 0)
            yOff += 25;
        return yOff;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (super.attackEntityFrom(damagesource, i)) {
            Entity entity = damagesource.getEntity();
            if ((entity != null && getIsTamed() && entity instanceof EntityPlayer) || !(entity instanceof EntityLivingBase)) {
                return false;
            }
            if (this.isRidingOrBeingRiddenBy(entity)) {
                return true;
            }
            if (entity != this && super.shouldAttackPlayers()) {
                setAttackTarget((EntityLivingBase) entity);
            }
            return true;
        }
        return false;
    }

    @Override
    public void fall(float f, float f1) {
        int i = (int) Math.ceil(f - 3F);
        if ((i > 0)) {
            i /= 2;
            if (i > 0) {
                attackEntityFrom(DamageSource.fall, i);
            }
            if ((this.isBeingRidden()) && (i > 0)) {
            	for (Entity entity : this.getRecursivePassengers())
                {
                    entity.attackEntityFrom(DamageSource.fall, (float)i);
            }
            }
            IBlockState iblockstate = this.worldObj.getBlockState(new BlockPos(this.posX, this.posY - 0.2D - (double)this.prevRotationYaw, this.posZ));
            Block block = iblockstate.getBlock();

            if (iblockstate.getMaterial() != Material.AIR && !this.isSilent())
            {
                SoundType soundtype = block.getSoundType(iblockstate, worldObj, new BlockPos(this.posX, this.posY - 0.2D - (double)this.prevRotationYaw, this.posZ), this);
                this.worldObj.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, soundtype.getStepSound(), this.getSoundCategory(), soundtype.getVolume() * 0.5F, soundtype.getPitch() * 0.75F);
            }
        }
    }

    @Override
    public boolean isNotScared() {
        return getIsAdult() || getEdad() > 80 || getIsTamed();
    }
}
