package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.EntityAIFollowAdult;
import drzhark.mocreatures.entity.ai.EntityAIFollowOwnerPlayer;
import drzhark.mocreatures.entity.ai.EntityAIHunt;
import drzhark.mocreatures.entity.ai.EntityAINearestAttackableTargetMoC;
import drzhark.mocreatures.entity.ai.EntityAIPanicMoC;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class MoCEntityBear extends MoCEntityTameableAnimal {

    public int mouthCounter;
    private int attackCounter;
    private int standingCounter;

    public MoCEntityBear(World world) {
        super(world);
        setSize(1.2F, 1.5F);
        setEdad(55);
        if (this.rand.nextInt(4) == 0) {
            setAdult(false);
        } else {
            setAdult(true);
        }
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIPanicMoC(this, 1.0D));
        this.tasks.addTask(3, new EntityAIFollowOwnerPlayer(this, 1D, 2F, 10F));
        this.tasks.addTask(4, new EntityAIFollowAdult(this, 1.0D));
        this.tasks.addTask(5, new EntityAIAttackOnCollide(this, 1.0D, true));
        this.tasks.addTask(6, new EntityAIWanderMoC2(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.targetTasks.addTask(1, new EntityAIHunt(this, EntityAnimal.class, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTargetMoC(this, EntityPlayer.class, true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
    }

    /**
     * Initializes datawatchers for entity. Each datawatcher is used to sync
     * server data to client.
     */
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(23, Byte.valueOf((byte) 0));
    }

    /**
     * 0 - bear is on fours 1 - standing 2 - sitting
     *
     * @return
     */
    public int getBearState() {
        return this.dataWatcher.getWatchableObjectByte(23);
    }

    public void setBearState(int i) {
        this.dataWatcher.updateObject(23, Byte.valueOf((byte) i));
    }

    @Override
    public void selectType() {
        checkSpawningBiome();

        if (getType() == 0) {

            int i = this.rand.nextInt(100);
            if (i <= 40) {
                setType(1);
            } else if (i <= 80) {
                setType(2);
            } else {
                setType(3);
            }
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(calculateMaxHealth());
            this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(getAttackStrength());
            this.setHealth(getMaxHealth());
        }
    }

    @Override
    public ResourceLocation getTexture() {

        switch (getType()) {
            case 1:
                return MoCreatures.proxy.getTexture("bearbrowm.png");
            case 2:
                return MoCreatures.proxy.getTexture("bearblack.png");
            case 3:
                return MoCreatures.proxy.getTexture("bearpanda.png");
            case 4:
                return MoCreatures.proxy.getTexture("bearpolar.png");

            default:
                return MoCreatures.proxy.getTexture("bearbrowm.png");
        }
    }

    /**
     * Returns the factor size for the bear, polars are bigger and pandas
     * smaller
     *
     * @return
     */
    public float getBearSize() {
        switch (getType()) {
            case 1:
                return 1.2F;
            case 2:
                return 0.9F;
            case 3:
                return 0.8F;
            case 4:
                return 1.4F;

            default:
                return 1.0F;
        }
    }

    public float calculateMaxHealth() {
        switch (getType()) {
            case 1:
                return 30;
            case 2:
                return 25;
            case 3:
                return 25;
            case 4:
                return 35;

            default:
                return 30;
        }
    }

    /**
     * Returns the distance at which the bear attacks prey
     *
     * @return
     */
    public double getAttackRange() {
        int factor = 1;
        if (this.worldObj.getDifficulty().getDifficultyId() > 1) {
            factor = 2;
        }

        switch (getType()) {
            case 1:
                return 6D * factor;
            case 2:
                return 6D * factor;
            case 3:
                return 1D;
            case 4:
                return 8D * factor;

            default:
                return 8D;
        }
    }

    /**
     * The damage the bear does
     *
     * @return
     */
    public int getAttackStrength() {
        int factor = (this.worldObj.getDifficulty().getDifficultyId());

        switch (getType()) {
            case 1:
                return 2 * factor;
            case 2:
                return 1 * factor;
            case 3:
                return 1;
            case 4:
                return 3 * factor;

            default:
                return 2;
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        startAttack();
        return super.attackEntityAsMob(entityIn);
    }

    /**
     * Checks if entity is sitting.
     */
    @Override
    public boolean isMovementCeased() {
        return getBearState() == 2;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (super.attackEntityFrom(damagesource, i)) {
            Entity entity = damagesource.getEntity();
            if ((this.riddenByEntity == entity) || (this.getRidingEntity() == entity)) {
                return true;
            }
            if (entity != this && entity instanceof EntityLivingBase && super.shouldAttackPlayers() && this.getType() != 3) {
                setAttackTarget((EntityLivingBase) entity);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isNotScared() {
        return getIsAdult();
    }

    @Override
    public boolean shouldAttackPlayers() {
        return ((this.getBrightness(1.0F) < 0.4F && this.getType() == 1) || (this.getType() == 4)) && super.shouldAttackPlayers();
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (this.mouthCounter > 0 && ++this.mouthCounter > 20) {
            this.mouthCounter = 0;
        }

        if (this.attackCounter > 0 && ++this.attackCounter > 9) {
            this.attackCounter = 0;
        }

        /**
         * panda bears and cubs will sit down every now and then
         */
        if ((MoCreatures.isServer()) && (getType() == 3 || (!getIsAdult() && getEdad() < 60)) && (this.rand.nextInt(300) == 0)) {
            setBearState(2);
        }

        /**
         * Sitting bears will resume on fours stance every now and then
         */
        if ((MoCreatures.isServer()) && (getBearState() == 2) && (this.rand.nextInt(800) == 0)) {
            setBearState(0);
        }

        if ((MoCreatures.isServer()) && (getBearState() == 2) && !this.getNavigator().noPath()) {
            setBearState(0);
        }

        /**
         * Adult non panda bears will stand on hind legs if close to player
         */

        if ((MoCreatures.isServer()) && this.standingCounter == 0 && getBearState() != 2 && getIsAdult() && getType() != 3) {
            EntityPlayer entityplayer1 = this.worldObj.getClosestPlayerToEntity(this, 4D);
            if ((entityplayer1 != null && this.canEntityBeSeen(entityplayer1)) || (this.rand.nextInt(2000) == 0)) {
                this.standingCounter = 1;
                setBearState(1);
            }
        }

        if ((MoCreatures.isServer()) && this.standingCounter > 0 && ++this.standingCounter > 100) {
            this.standingCounter = 0;
            setBearState(0);
        }

        //TODO move to AI
        if (MoCreatures.isServer() && getType() == 3 && (this.deathTime == 0) && getBearState() != 2) {
            EntityItem entityitem = getClosestItem(this, 12D, Items.reeds, Items.sugar);
            if (entityitem != null) {

                float f = entityitem.getDistanceToEntity(this);
                if (f > 2.0F) {
                    getMyOwnPath(entityitem, f);
                }
                if ((f < 2.0F) && (entityitem != null) && (this.deathTime == 0)) {
                    entityitem.setDead();
                    this.worldObj
                            .playSoundAtEntity(this, "mocreatures:eating", 1.0F, 1.0F + ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F));
                    this.setHealth(getMaxHealth());
                }

            }
        }
    }

    @Override
    public boolean interact(EntityPlayer entityplayer) {
        if (super.interact(entityplayer)) {
            return false;
        }
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if ((itemstack != null) && (getType() == 3) && ((itemstack.getItem() == MoCreatures.sugarlump) || (itemstack.getItem() == Items.reeds))) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }

            if (MoCreatures.isServer()) {
                MoCTools.tameWithName(entityplayer, this);
            }

            this.setHealth(getMaxHealth());
            eatingAnimal();
            if (MoCreatures.isServer() && !getIsAdult() && (getEdad() < 100)) {
                setEdad(getEdad() + 1);
            }

            return true;
        }
        return false;
    }

    @Override
    public boolean canAttackTarget(EntityLivingBase entity) {
        return !(entity instanceof MoCEntityBear) && entity.height <= 1D && entity.width <= 1D;
    }

    @Override
    protected String getDeathSound() {
        return "mocreatures:beardying";
    }

    @Override
    protected Item getDropItem() {
        return MoCreatures.animalHide;
    }

    @Override
    protected String getHurtSound() {
        openMouth();
        return "mocreatures:bearhurt";
    }

    @Override
    protected String getLivingSound() {
        openMouth();
        return "mocreatures:beargrunt";
    }

    @Override
    public boolean checkSpawningBiome() {
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(getEntityBoundingBox().minY);
        int k = MathHelper.floor_double(this.posZ);
        BlockPos pos = new BlockPos(i, j, k);

        Biome currentbiome = MoCTools.Biomekind(this.worldObj, pos);
        try {
            if (BiomeDictionary.isBiomeOfType(currentbiome, Type.SNOWY)) {
                setType(4);
                return true;
            }
        } catch (Exception e) {
        }
        if (currentbiome.biomeName.toLowerCase().contains("bamboo") || MoCTools.isNearBlockName(this, 12D, "tile.reeds")) {
            setType(3);//panda
            return true;
        }
        return true;
    }

    private void openMouth() {
        this.mouthCounter = 1;
    }

    public float getAttackSwing() {
        //TODO FIX!
        //return 0;
        if (attackCounter == 0)
            return 0;
        return 1.5F + ((float) (attackCounter / 10F) - 10F) * 5F;
    }

    private void startAttack() {

        if (MoCreatures.isServer() && this.attackCounter == 0 && getBearState() == 1) {
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 0),
                    new TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 64));
            this.attackCounter = 1;
        }
    }

    @Override
    public void performAnimation(int i) {
        this.attackCounter = 1;
    }

    private void eatingAnimal() {
        openMouth();
        MoCTools.playCustomSound(this, "mocreatures:eating", this.worldObj);
    }

    @Override
    public boolean isMyFavoriteFood(ItemStack par1ItemStack) {
        return this.getType() == 3 && par1ItemStack != null && par1ItemStack.getItem() == Items.reeds;
    }

    @Override
    public boolean isMyHealFood(ItemStack par1ItemStack) {
        return this.getType() == 3 && par1ItemStack != null && par1ItemStack.getItem() == Items.reeds;
    }

    @Override
    public int nameYOffset() {
        if (getIsAdult()) {
            return (-55);
        }
        return (100 / getEdad()) * (-40);

    }

    @Override
    public double getCustomSpeed() {
        if (getBearState() == 2) {
            return 0D;
        }
        return super.getCustomSpeed();
    }

    @Override
    public boolean isReadyToHunt() {
        return this.getType() != 3 && this.getIsAdult() && !this.isMovementCeased();
    }
}
