package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.EntityAIFollowAdult;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.item.MoCEntityPlatform;
import drzhark.mocreatures.inventory.MoCAnimalChest;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
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
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

import java.util.List;

public class MoCEntityElephant extends MoCEntityTameableAnimal {

    public int sprintCounter;
    public int sitCounter;
    public MoCAnimalChest localelephantchest;
    public MoCAnimalChest localelephantchest2;
    public MoCAnimalChest localelephantchest3;
    public MoCAnimalChest localelephantchest4;
    public MoCAnimalChest emptyelephantchest;
    public ItemStack localstack;
    boolean hasPlatform;
    public int tailCounter;
    public int trunkCounter;
    public int earCounter;
    private byte tuskUses;
    private byte temper;

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
        ((PathNavigateGround) this.getNavigator()).setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(4, new EntityAIFollowAdult(this, 1.0D));
        this.tasks.addTask(5, new EntityAIAttackOnCollide(this, 1.0D, true));
        this.tasks.addTask(6, new EntityAIWanderMoC2(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
    }

    @Override
    public void selectType() {
        checkSpawningBiome();
        if (getType() == 0) {
            setType(this.rand.nextInt(2) + 1);
        }
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(calculateMaxHealth());
        this.setHealth(getMaxHealth());
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(23, Byte.valueOf((byte) 0)); // tusks: 0 nothing, 1 wood, 2 iron, 3 diamond
        this.dataWatcher.addObject(24, Byte.valueOf((byte) 0)); // storage: 0 nothing, 1 chest, 2 chests....
        this.dataWatcher.addObject(25, Byte.valueOf((byte) 0)); // harness: 0 nothing, 1 harness, 2 cabin

    }

    public byte getTusks() {
        return (this.dataWatcher.getWatchableObjectByte(23));
    }

    public void setTusks(byte b) {
        this.dataWatcher.updateObject(23, Byte.valueOf(b));
    }

    @Override
    public byte getArmorType() {
        return (this.dataWatcher.getWatchableObjectByte(25));
    }

    @Override
    public void setArmorType(byte b) {
        this.dataWatcher.updateObject(25, Byte.valueOf(b));
    }

    public byte getStorage() {
        return (this.dataWatcher.getWatchableObjectByte(24));
    }

    public void setStorage(byte b) {
        this.dataWatcher.updateObject(24, Byte.valueOf(b));
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
            if ((this.sprintCounter > 0 && this.sprintCounter < 150) && (this.riddenByEntity != null) && rand.nextInt(15) == 0) {
                MoCTools.buckleMobsNotPlayers(this, 3D, this.worldObj);
            }

            if (this.sprintCounter > 0 && ++this.sprintCounter > 300) {
                this.sprintCounter = 0;
            }

            if (getIsTamed() && (this.riddenByEntity == null) && getArmorType() >= 1 && this.rand.nextInt(20) == 0) {
                EntityPlayer ep = this.worldObj.getClosestPlayerToEntity(this, 3D);
                if (ep != null && (!MoCreatures.proxy.enableOwnership || ep.getName().equals(getOwnerName())) && ep.isSneaking()) {
                    sit();
                }
            }

            if (MoCreatures.proxy.elephantBulldozer && getIsTamed() && (this.riddenByEntity != null) && (getTusks() > 0)) {
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

            if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer) {
                if (this.sitCounter != 0 && getArmorType() >= 3 && !secondRider()) {
                    List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(2D, 2D, 2D));
                    for (int i = 0; i < list.size(); i++) {
                        Entity entity1 = list.get(i);

                        if (!(entity1 instanceof EntityPlayer) || entity1 == this.riddenByEntity) {
                            continue;
                        }

                        if (((EntityPlayer) entity1).isSneaking()) {
                            mountSecondPlayer(entity1);
                        }

                    }
                }

            }

            if (this.riddenByEntity == null && this.rand.nextInt(100) == 0) {
                destroyPlatforms();
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

    private boolean secondRider() {
        List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(3D, 3D, 3D));
        for (int i = 0; i < list.size(); i++) {
            Entity entity1 = list.get(i);
            if ((entity1 instanceof MoCEntityPlatform) && (entity1.riddenByEntity != null)) {
                return true;
            }
        }
        return false;
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
            MoCTools.playCustomSound(this, "turtlehurt", this.worldObj);
            setTusks((byte) 0);
        }
    }

    /**
     * Destroys dummy entity platforms used for second rider
     */
    private void destroyPlatforms() {
        List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(3D, 3D, 3D));
        for (int i = 0; i < list.size(); i++) {
            Entity entity1 = (Entity) list.get(i);
            if ((entity1 instanceof MoCEntityPlatform)) {
                entity1.setDead();
            }
        }
    }

    private void sit() {
        this.sitCounter = 1;
        if (MoCreatures.isServer()) {
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 0),
                    new TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 64));
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
    public boolean interact(EntityPlayer entityplayer) {
        if (super.interact(entityplayer)) {
            return false;
        }
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();

        if ((itemstack != null) && !getIsTamed() && !getIsAdult() && itemstack.getItem() == Items.cake) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            MoCTools.playCustomSound(this, "mocreatures:eating", this.worldObj);
            this.temper += 2;
            this.setHealth(getMaxHealth());
            if (MoCreatures.isServer() && !getIsAdult() && !getIsTamed() && this.temper >= 10) {
                MoCTools.tameWithName(entityplayer, this);
            }
            return true;
        }

        if ((itemstack != null) && !getIsTamed() && !getIsAdult() && itemstack.getItem() == MoCreatures.sugarlump) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            MoCTools.playCustomSound(this, "mocreatures:eating", this.worldObj);
            this.temper += 1;
            this.setHealth(getMaxHealth());
            if (MoCreatures.isServer() && !getIsAdult() && !getIsTamed() && this.temper >= 10) {
                setTamed(true);
                MoCTools.tameWithName(entityplayer, this);
            }
            return true;
        }

        if ((itemstack != null) && getIsTamed() && getIsAdult() && getArmorType() == 0 && itemstack.getItem() == MoCreatures.elephantHarness) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            MoCTools.playCustomSound(this, "roping", this.worldObj);
            setArmorType((byte) 1);
            return true;
        }

        if ((itemstack != null) && getIsTamed() && getIsAdult() && getArmorType() >= 1 && getStorage() == 0
                && itemstack.getItem() == MoCreatures.elephantChest) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            MoCTools.playCustomSound(this, "roping", this.worldObj);
            //entityplayer.inventory.addItemStackToInventory(new ItemStack(MoCreatures.key));
            setStorage((byte) 1);
            return true;
        }
        // second storage unit
        if ((itemstack != null) && getIsTamed() && getIsAdult() && getArmorType() >= 1 && getStorage() == 1
                && itemstack.getItem() == MoCreatures.elephantChest) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            MoCTools.playCustomSound(this, "roping", this.worldObj);
            setStorage((byte) 2);
            return true;
        }
        // third storage unit for small mammoths
        if ((itemstack != null) && getIsTamed() && getIsAdult() && (getType() == 3) && getArmorType() >= 1 && getStorage() == 2
                && itemstack.getItem() == Item.getItemFromBlock(Blocks.chest)) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            MoCTools.playCustomSound(this, "roping", this.worldObj);
            setStorage((byte) 3);
            return true;
        }
        // fourth storage unit for small mammoths
        if ((itemstack != null) && getIsTamed() && getIsAdult() && (getType() == 3) && getArmorType() >= 1 && getStorage() == 3
                && itemstack.getItem() == Item.getItemFromBlock(Blocks.chest)) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            MoCTools.playCustomSound(this, "roping", this.worldObj);
            setStorage((byte) 4);
            return true;
        }

        //giving a garment to an indian elephant with an harness will make it pretty
        if ((itemstack != null) && getIsTamed() && getIsAdult() && getArmorType() == 1 && getType() == 2
                && itemstack.getItem() == MoCreatures.elephantGarment) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            MoCTools.playCustomSound(this, "roping", this.worldObj);
            setArmorType((byte) 2);
            setType(5);
            return true;
        }

        //giving a howdah to a pretty indian elephant with a garment will attach the howdah
        if ((itemstack != null) && getIsTamed() && getIsAdult() && getArmorType() == 2 && getType() == 5
                && itemstack.getItem() == MoCreatures.elephantHowdah) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            MoCTools.playCustomSound(this, "roping", this.worldObj);
            setArmorType((byte) 3);
            return true;
        }

        //giving a platform to a ? mammoth with harness will attach the platform
        if ((itemstack != null) && getIsTamed() && getIsAdult() && getArmorType() == 1 && getType() == 4
                && itemstack.getItem() == MoCreatures.mammothPlatform) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            MoCTools.playCustomSound(this, "armoroff", this.worldObj);
            setArmorType((byte) 3);
            return true;
        }

        if ((itemstack != null) && getIsTamed() && getIsAdult() && itemstack.getItem() == MoCreatures.tusksWood) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            MoCTools.playCustomSound(this, "armoroff", this.worldObj);
            dropTusks();
            this.tuskUses = (byte) itemstack.getItemDamage();
            setTusks((byte) 1);
            return true;
        }

        if ((itemstack != null) && getIsTamed() && getIsAdult() && itemstack.getItem() == MoCreatures.tusksIron) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            MoCTools.playCustomSound(this, "armoroff", this.worldObj);
            dropTusks();
            this.tuskUses = (byte) itemstack.getItemDamage();
            setTusks((byte) 2);
            return true;
        }

        if ((itemstack != null) && getIsTamed() && getIsAdult() && itemstack.getItem() == MoCreatures.tusksDiamond) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            MoCTools.playCustomSound(this, "armoroff", this.worldObj);
            dropTusks();
            this.tuskUses = (byte) itemstack.getItemDamage();
            setTusks((byte) 3);
            return true;
        }

        if (entityplayer.isSneaking()) {
            initChest();
            if (getStorage() == 1) {
                if (MoCreatures.isServer()) {
                    InventoryLargeChest singleChest = new InventoryLargeChest("ElephantChest", this.localelephantchest, this.emptyelephantchest);
                    entityplayer.displayGUIChest(singleChest);
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
                && getTusks() > 0
                && ((itemstack.getItem() == Items.diamond_pickaxe) || (itemstack.getItem() == Items.wooden_pickaxe)
                        || (itemstack.getItem() == Items.stone_pickaxe) || (itemstack.getItem() == Items.iron_pickaxe) || (itemstack.getItem() == Items.golden_pickaxe))) {
            MoCTools.playCustomSound(this, "armoroff", this.worldObj);
            dropTusks();
            return true;
        }

        if (getIsTamed() && getIsAdult() && getArmorType() >= 1 && this.sitCounter != 0) {
            entityplayer.rotationYaw = this.rotationYaw;
            entityplayer.rotationPitch = this.rotationPitch;
            this.sitCounter = 0;
            if (MoCreatures.isServer()) {
                entityplayer.mountEntity(this);
            }
            return true;
        }
        return false;
    }

    private void initChest() {
        if (getStorage() > 0 && this.emptyelephantchest == null) {
            this.emptyelephantchest = new MoCAnimalChest("ElephantChest", 0);
        }

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
     * Used to mount a second player on the elephant
     */
    private void mountSecondPlayer(Entity entity) {
        double yOff = 2.0D;
        MoCEntityPlatform platform = new MoCEntityPlatform(this.worldObj, this.getEntityId(), yOff, 1.25D);
        platform.setPosition(this.posX, this.posY + yOff, this.posZ);
        this.worldObj.spawnEntityInWorld(platform);
        entity.mountEntity(platform);
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
        setTusks(nbttagcompound.getByte("Tusks"));
        setArmorType(nbttagcompound.getByte("Harness"));
        setStorage(nbttagcompound.getByte("Storage"));
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
        nbttagcompound.setByte("Tusks", getTusks());
        nbttagcompound.setByte("Harness", getArmorType());
        nbttagcompound.setByte("Storage", getStorage());
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
                && (par1ItemStack.getItem() == Items.baked_potato || par1ItemStack.getItem() == Items.bread || par1ItemStack.getItem() == MoCreatures.haystack);
    }

    @Override
    public boolean isMovementCeased() {
        return (this.riddenByEntity != null) || this.sitCounter != 0;
    }

    @Override
    public void setType(int i) {
        this.dataWatcher.updateObject(19, Integer.valueOf(i));
    }

    @Override
    public void Riding() {
        if ((this.riddenByEntity != null) && (this.riddenByEntity instanceof EntityPlayer)) {
            EntityPlayer entityplayer = (EntityPlayer) this.riddenByEntity;
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
                        entityplayer.mountEntity(null);
                    }

                }
            }
        }
    }

    @Override
    public boolean canBePushed() {
        return this.riddenByEntity == null;
    }

    @Override
    public boolean canBeCollidedWith() {
        return this.riddenByEntity == null;
    }

    @Override
    public void updateRiderPosition() {

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
        this.riddenByEntity.setPosition(newPosX, this.posY + getMountedYOffset() + this.riddenByEntity.getYOffset(), newPosZ);
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
    protected String getDeathSound() {
        return "mocreatures:elephantdying";
    }

    @Override
    protected String getHurtSound() {
        return "mocreatures:elephanthurt";
    }

    @Override
    protected String getLivingSound() {
        if (!getIsAdult() && getEdad() < 80) {
            return "mocreatures:elephantcalf";
        }
        return "mocreatures:elephantgrunt";
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
            destroyPlatforms();
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
                    MoCTools.dropCustomItem(this, this.worldObj, new ItemStack(Blocks.chest, 1));
                }
                if (getStorage() >= 4) {
                    if (this.localelephantchest4 != null) {
                        MoCTools.dropInventory(this, this.localelephantchest4);
                    }
                    MoCTools.dropCustomItem(this, this.worldObj, new ItemStack(Blocks.chest, 1));
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
            if ((this.riddenByEntity == entity) || (this.getRidingEntity() == entity)) {
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
            if ((this.riddenByEntity != null) && (i > 0)) {
                this.riddenByEntity.attackEntityFrom(DamageSource.fall, i);
            }

            BlockPos pos =
                    new BlockPos(this.posX, MathHelper.floor_double(this.posY - 0.20000000298023221D - this.prevRotationPitch),
                            MathHelper.floor_double(this.posZ));
            IBlockState blockstate = this.worldObj.getBlockState(pos);
            if (!blockstate.getBlock().isAir(this.worldObj, pos)) {
                Block.SoundType stepsound = blockstate.getBlock().stepSound;
                this.worldObj.playSoundAtEntity(this, stepsound.getStepSound(), stepsound.getVolume() * 0.5F, stepsound.getFrequency() * 0.75F);
            }
        }
    }

    @Override
    public boolean isNotScared() {
        return getIsAdult() || getEdad() > 80 || getIsTamed();
    }
}
