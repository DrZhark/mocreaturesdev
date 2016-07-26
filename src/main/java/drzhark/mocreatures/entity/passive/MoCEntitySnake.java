package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.EntityAIFleeFromPlayer;
import drzhark.mocreatures.entity.ai.EntityAIHunt;
import drzhark.mocreatures.entity.ai.EntityAINearestAttackableTargetMoC;
import drzhark.mocreatures.entity.ai.EntityAIPanicMoC;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

/**
 * Biome - specific Forest Desert plains Swamp Jungle Tundra Taiga Extreme Hills
 * Ocean
 *
 * swamp: python, bright green, #1 plains: coral, cobra #1, #2, #3, #4 desert:
 * rattlesnake , #2 jungle: all except rattlesnake hills: all except python,
 * bright green, bright orange tundra-taiga: none ocean: leave alone
 *
 */

public class MoCEntitySnake extends MoCEntityTameableAnimal {

    private float fTongue;
    private float fMouth;
    private boolean isBiting;
    private float fRattle;
    private boolean isPissed;
    private int hissCounter;

    private int movInt;
    private boolean isNearPlayer;
    public float bodyswing;

    public static final String snakeNames[] = {"Dark", "Spotted", "Orange", "Green", "Coral", "Cobra", "Rattle", "Python"};

    public MoCEntitySnake(World world) {
        super(world);
        setSize(1.4F, 0.5F);
        this.bodyswing = 2F;
        this.movInt = this.rand.nextInt(10);
        setEdad(50 + this.rand.nextInt(50));
        this.tasks.addTask(2, new EntityAIPanicMoC(this, 0.8D));
        this.tasks.addTask(3, new EntityAIFleeFromPlayer(this, 0.8D, 4D));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0D, true));
        this.tasks.addTask(5, new EntityAIWanderMoC2(this, 0.8D, 30));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.targetTasks.addTask(1, new EntityAIHunt(this, EntityAnimal.class, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTargetMoC(this, EntityPlayer.class, true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
    }

    @Override
    public void selectType() {
        checkSpawningBiome();
        // snake types:
        // 1 small blackish/dark snake (passive)
        // 2 dark green /brown snake (passive)
        // 3 bright orangy snake aggressive venomous swamp, jungle, forest
        // 4 bright green snake aggressive venomous swamp, jungle, forest
        // 5 coral (aggressive - venomous) small / plains, forest
        // 6 cobra (aggressive - venomous - spitting) plains, forest
        // 7 rattlesnake (aggressive - venomous) desert
        // 8 python (aggressive - non venomous) big - swamp
        // 9 sea snake (aggressive - venomous)
        if (getType() == 0) {
            setType(this.rand.nextInt(8) + 1);
        }
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getType()) {
            case 1:
                return MoCreatures.proxy.getTexture("snake1.png");
            case 2:
                return MoCreatures.proxy.getTexture("snake2.png");
            case 3:
                return MoCreatures.proxy.getTexture("snake3.png");
            case 4:
                return MoCreatures.proxy.getTexture("snake4.png");
            case 5:
                return MoCreatures.proxy.getTexture("snake5.png");
            case 6:
                return MoCreatures.proxy.getTexture("snake6.png");
            case 7:
                return MoCreatures.proxy.getTexture("snake7.png");
            case 8:
                return MoCreatures.proxy.getTexture("snake8.png");
            default:
                return MoCreatures.proxy.getTexture("snake1.png");
        }
    }

    @Override
    public void fall(float f, float f1) {
    }

    @Override
    public boolean isOnLadder() {
        return this.isCollidedHorizontally;
    }

    @Override
    // snakes can't jump
            protected
            void jump() {
        if (this.isInWater()) {
            super.jump();
        }
    }

    @Override
    protected boolean canDespawn() {
        if (MoCreatures.proxy.forceDespawns) {
            return !getIsTamed();
        } else {
            return false;
        }
    }

    public boolean pickedUp() {
        return (this.getRidingEntity() != null);
    }

    @Override
    public boolean interact(EntityPlayer entityplayer) {
        if (super.interact(entityplayer)) {
            return false;
        }
        if (!getIsTamed()) {
            return false;
        }

        //ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        //TODO
        //this doesn't work yet, make the player feed the mouse to this snake
        /*
         * if (entityplayer.riddenByEntity != null &&
         * entityplayer.riddenByEntity instanceof MoCEntityMouse) {
         * //System.out.println("player has a mouse"); }
         */

        this.rotationYaw = entityplayer.rotationYaw;
        if (this.getRidingEntity() == null) {
            if (MoCreatures.isServer()) {
                mountEntity(entityplayer);
            }
        } else {
            this.worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F) + 1.0F);
            if (MoCreatures.isServer()) {
                this.mountEntity(null);
            }
        }
        this.motionX = entityplayer.motionX * 5D;
        this.motionY = (entityplayer.motionY / 2D) + 0.5D;
        this.motionZ = entityplayer.motionZ * 5D;

        return true;
    }

    @Override
    public boolean isNotScared() {
        if (getType() > 2 && getEdad() > 50) {
            return true;
        }
        return false;
    }

    /**
     * returns true when is climbing up
     *
     * @return
     */
    public boolean isClimbing() {
        return isOnLadder() && this.motionY > 0.01F;
    }

    public boolean isResting() {
        return (!getNearPlayer() && this.onGround && (this.motionX < 0.01D && this.motionX > -0.01D) && (this.motionZ < 0.01D && this.motionZ > -0.01D));
    }

    public boolean getNearPlayer() {
        return (this.isNearPlayer || this.isBiting());
    }

    public int getMovInt() {
        return this.movInt;
    }

    @Override
    public boolean swimmerEntity() {
        return false;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    public void setNearPlayer(boolean flag) {
        this.isNearPlayer = flag;
    }

    @Override
    public double getYOffset() {
        if (this.getRidingEntity() instanceof EntityPlayer && this.getRidingEntity() == MoCreatures.proxy.getPlayer() && !MoCreatures.isServer()) {
            return 0.1F;
        }

        if ((this.getRidingEntity() instanceof EntityPlayer) && !MoCreatures.isServer()) {
            return (super.getYOffset() + 0.1F);
        } else {
            return super.getYOffset();
        }
    }

    public float getSizeF() {
        float factor = 1.0F;
        if (getType() == 1 || getType() == 2)// small shy snakes
        {
            factor = 0.8F;
        } else if (getType() == 5)// coral
        {
            factor = 0.6F;
        }
        if (getType() == 6)// cobra 1.1
        {
            factor = 1.1F;
        }
        if (getType() == 7)// rattlesnake
        {
            factor = 0.9F;
        }
        if (getType() == 8)// python
        {
            factor = 1.5F;
        }
        return this.getEdad() * 0.01F * factor;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (!MoCreatures.isServer()) {
            if (getfTongue() != 0.0F) {
                setfTongue(getfTongue() + 0.2F);
                if (getfTongue() > 8.0F) {
                    setfTongue(0.0F);
                }
            }

            if (getfMouth() != 0.0F && this.hissCounter == 0) //biting
            {
                setfMouth(getfMouth() + 0.1F);
                if (getfMouth() > 0.5F) {
                    setfMouth(0.0F);
                }
            }

            if (getType() == 7 && getfRattle() != 0.0F) // rattling
            {
                setfRattle(getfRattle() + 0.2F);
                if (getfRattle() == 1.0F) {
                    // TODO synchronize
                    this.worldObj.playSoundAtEntity(this, "mocreatures:snakerattle", 1.0F,
                            1.0F + ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F));
                }
                if (getfRattle() > 8.0F) {
                    setfRattle(0.0F);
                }
            }

            /**
             * stick tongue
             */
            if (this.rand.nextInt(50) == 0 && getfTongue() == 0.0F) {
                setfTongue(0.1F);
            }

            /**
             * Open mouth
             */
            if (this.rand.nextInt(100) == 0 && getfMouth() == 0.0F) {
                setfMouth(0.1F);
            }
            if (getType() == 7) {
                int chance = 0;
                if (getNearPlayer()) {
                    chance = 30;
                } else {
                    chance = 100;
                }

                if (this.rand.nextInt(chance) == 0) {
                    setfRattle(0.1F);
                }
            }
            /**
             * change in movement pattern
             */
            if (!isResting() && !pickedUp() && this.rand.nextInt(50) == 0) {
                this.movInt = this.rand.nextInt(10);
            }

            /**
             * Biting animation
             */
            if (isBiting()) {
                this.bodyswing -= 0.5F;
                setfMouth(0.3F);

                if (this.bodyswing < 0F) {
                    this.worldObj.playSoundAtEntity(this, "mocreatures:snakesnap", 1.0F,
                            1.0F + ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F));
                    this.bodyswing = 2.5F;
                    setfMouth(0.0F);
                    setBiting(false);
                }
            }

        }
        if (pickedUp()) {
            this.movInt = 0;
        }

        if (isResting()) {

            this.prevRenderYawOffset = this.renderYawOffset = this.rotationYaw = this.prevRotationYaw;

        }

        if (!this.onGround && (this.getRidingEntity() != null)) {
            this.rotationYaw = this.getRidingEntity().rotationYaw;// -90F;
        }

        if (this.worldObj.getDifficulty().getDifficultyId() > 0 && getNearPlayer() && !getIsTamed() && isNotScared()) {

            this.hissCounter++;

            // TODO synchronize and get sound
            // hiss
            if (this.hissCounter % 25 == 0) {
                setfMouth(0.3F);
                this.worldObj
                        .playSoundAtEntity(this, "mocreatures:snakeupset", 1.0F, 1.0F + ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F));
            }
            if (this.hissCounter % 35 == 0) {
                setfMouth(0.0F);
            }

            if (this.hissCounter > 100 && this.rand.nextInt(50) == 0) {
                // then randomly get pissed
                setPissed(true);
                this.hissCounter = 0;
            }
        }
        if (this.hissCounter > 500) {
            this.hissCounter = 0;
        }

    }

    /**
     * from 0.0 to 4.0F 0.0 = inside mouth 2.0 = completely stuck out 3.0 =
     * returning 4.0 = in.
     *
     * @return
     */
    public float getfTongue() {
        return this.fTongue;
    }

    public void setfTongue(float fTongue) {
        this.fTongue = fTongue;
    }

    public float getfMouth() {
        return this.fMouth;
    }

    public void setfMouth(float fMouth) {
        this.fMouth = fMouth;
    }

    public float getfRattle() {
        return this.fRattle;
    }

    public void setfRattle(float fRattle) {
        this.fRattle = fRattle;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        /**
         * this stops chasing the target randomly
         */
        if (getAttackTarget() != null && this.rand.nextInt(300) == 0) {
            setAttackTarget(null);
        }

        EntityPlayer entityplayer1 = this.worldObj.getClosestPlayerToEntity(this, 12D);
        if (entityplayer1 != null) {
            double distP = MoCTools.getSqDistanceTo(entityplayer1, this.posX, this.posY, this.posZ);
            if (isNotScared()) {
                if (distP < 5D) {
                    setNearPlayer(true);
                } else {
                    setNearPlayer(false);
                }

                /*if (entityplayer1.riddenByEntity != null
                        && (entityplayer1.riddenByEntity instanceof MoCEntityMouse || entityplayer1.riddenByEntity instanceof MoCEntityBird)) {
                    PathEntity pathentity = this.navigator.getPathToEntityLiving(entityplayer1);
                    this.navigator.setPath(pathentity, 16F);
                    setPissed(false);
                    this.hissCounter = 0;
                }*/
            } else {
                setNearPlayer(false);
            }

        } else {
            setNearPlayer(false);
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        if ((getType() < 3 || getIsTamed()) && entityIn instanceof EntityPlayer) {
            return false;
        }

        if (entityIn instanceof EntityPlayer && !shouldAttackPlayers()) {
            return false;
        }
        setBiting(true);
        return super.attackEntityAsMob(entityIn);
    }

    @Override
    public void performAnimation(int i) {
        setBiting(true);
    }

    public boolean isBiting() {
        return this.isBiting;
    }

    public void setBiting(boolean flag) {
        if (flag && MoCreatures.isServer()) {
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 0),
                    new TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 64));
        }
        this.isBiting = flag;
    }

    public boolean isPissed() {
        return this.isPissed;
    }

    public void setPissed(boolean isPissed) {
        this.isPissed = isPissed;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {

        if (getType() < 3) {
            return super.attackEntityFrom(damagesource, i);
        }

        if (super.attackEntityFrom(damagesource, i)) {
            Entity entity = damagesource.getEntity();

            if ((this.riddenByEntity == entity) || (this.getRidingEntity() == entity)) {
                return true;
            }
            if ((entity != this) && entity instanceof EntityLivingBase && (super.shouldAttackPlayers())) {
                setPissed(true);
                setAttackTarget((EntityLivingBase) entity);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void dropFewItems(boolean flag, int x) {
        if (getEdad() > 60) {
            int j = this.rand.nextInt(3);
            for (int l = 0; l < j; l++) {

                entityDropItem(new ItemStack(MoCreatures.mocegg, 1, getType() + 20), 0.0F);
            }
        }
    }

    @Override
    public boolean canAttackTarget(EntityLivingBase entity) {
        return !(entity instanceof MoCEntitySnake) && entity.height < 0.5D && entity.width < 0.5D;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block par4) {
        if (isInsideOfMaterial(Material.water)) {
            this.worldObj.playSoundAtEntity(this, "mocreatures:snakeswim", 1.0F, 1.0F);
        }
        // TODO - add sound for slither
        /*
         * else { worldObj.playSoundAtEntity(this, "snakeslither", 1.0F, 1.0F);
         * }
         */
    }

    @Override
    protected String getDeathSound() {
        return "mocreatures:snakedying";
    }

    @Override
    protected String getHurtSound() {
        return "mocreatures:snakehurt";
    }

    @Override
    protected String getLivingSound() {
        return "mocreatures:snakehiss";
    }

    @Override
    public boolean getCanSpawnHere() {
        return getCanSpawnHereCreature() && getCanSpawnHereLiving(); //&& checkSpawningBiome()
    }

    @Override
    public boolean checkSpawningBiome() {
        BlockPos pos = new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(getEntityBoundingBox().minY), this.posZ);
        /**
         * swamp: python, bright green, #1 (done) plains: coral, cobra #1, #2,
         * #3, #4 (everyone but 7) desert: rattlesnake , #2 jungle: all except
         * rattlesnake forest: all except rattlesnake hills: all except python,
         * bright green, bright orange, rattlesnake tundra-taiga: none ocean:
         * leave alone
         */

        /**
         * Biome lists: Ocean Plains Desert Extreme Hills Forest Taiga Swampland
         * River Frozen Ocean Frozen River Ice Plains Ice Mountains Mushroom
         * Island Mushroom Island Shore Beach DesertHills ForestHills TaigaHills
         * Extreme Hills Edge Jungle JungleHills
         *
         */
        try {
            Biome currentbiome = MoCTools.Biomekind(this.worldObj, pos);
            int l = this.rand.nextInt(10);

            if (BiomeDictionary.isBiomeOfType(currentbiome, Type.SNOWY)) {
                return false;
            }

            if (BiomeDictionary.isBiomeOfType(currentbiome, Type.SANDY)) {
                if (l < 5) {
                    setType(7); // rattlesnake or spotted brownish ?
                } else {
                    setType(2);
                }
            }

            if (getType() == 7 && !(BiomeDictionary.isBiomeOfType(currentbiome, Type.SANDY))) {
                setType(2);
            }
            if (BiomeDictionary.isBiomeOfType(currentbiome, Type.HILLS)) {
                if (l < 4) {
                    setType(1);
                } else if (l < 7) {
                    setType(5);
                } else {
                    setType(6);
                }
            }
            if (BiomeDictionary.isBiomeOfType(currentbiome, Type.SWAMP)) {
                // python or bright green bright orange
                if (l < 4) {
                    setType(8);
                } else if (l < 8) {
                    setType(4);
                } else {
                    setType(1);
                }
            }
        } catch (Exception e) {
        }
        return true;
    }

    @Override
    public int nameYOffset() {
        return -30;
    }

    @Override
    public boolean isMyHealFood(ItemStack par1ItemStack) {
        return par1ItemStack != null && (par1ItemStack.getItem() == MoCreatures.ratRaw);
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 2;
    }

    @Override
    public boolean isReadyToHunt() {
        return this.getIsAdult() && !this.isMovementCeased();
    }

    @Override
    protected void applyEnchantments(EntityLivingBase entityLivingBaseIn, Entity entityIn) {
        if (isVenomous()) {
            if (entityIn instanceof EntityPlayer) {
                MoCreatures.poisonPlayer((EntityPlayer) entityIn);
            }
            ((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(Potion.poison.id, 150, 2));
        }
        super.applyEnchantments(entityLivingBaseIn, entityIn);
    }

    private boolean isVenomous() {
        return getType() == 3 || getType() == 4 || getType() == 5 || getType() == 6 || getType() == 7 || getType() == 9;
    }

    @Override
    public boolean shouldAttackPlayers() {
        return this.isPissed() && super.shouldAttackPlayers();
    }

    @Override
    public int getTalkInterval() {
        return 400;
    }

    @Override
    public boolean isAmphibian() {
        return true;
    }

    /*@Override
    public float getAIMoveSpeed()
    {
        if (isInWater())
        {
            return 0.08F;
        }
        return 0.12F;
    }*/

    /* @Override
     protected double minDivingDepth()
     {
         return ((double)getEdad() + 8D)/340D;
     }

     @Override
     protected double maxDivingDepth()
     {
         return 1D * (this.getEdad()/100D);
     }*/
}
