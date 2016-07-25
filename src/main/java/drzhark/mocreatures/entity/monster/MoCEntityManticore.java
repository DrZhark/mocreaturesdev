package drzhark.mocreatures.entity.monster;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.entity.ai.EntityAINearestAttackableTargetMoC;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class MoCEntityManticore extends MoCEntityMob {

    public int mouthCounter;
    public int tailCounter;
    public int wingFlapCounter;
    private boolean isPoisoning;
    private int poisontimer;

    public MoCEntityManticore(World world) {
        super(world);
        setSize(1.4F, 1.6F);
        this.isImmuneToFire = true;
        //this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, 1.0D, true));
        //this.tasks.addTask(2, this.aiAvoidExplodingCreepers);
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTargetMoC(this, EntityPlayer.class, true));

    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.4D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0D);
    }

    @Override
    public void selectType() {
        checkSpawningBiome();

        if (getType() == 0) {
            setType((this.rand.nextInt(2) * 2) + 2);
        }
    }

    @Override
    public boolean checkSpawningBiome() {
        if (this.worldObj.provider.doesWaterVaporize()) {
            setType(1);
            this.isImmuneToFire = true;
            return true;
        }

        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(getEntityBoundingBox().minY);
        int k = MathHelper.floor_double(this.posZ);
        BlockPos pos = new BlockPos(i, j, k);

        BiomeGenBase currentbiome = MoCTools.Biomekind(this.worldObj, pos);

        if (BiomeDictionary.isBiomeOfType(currentbiome, Type.SNOWY)) {
            setType(3);
        }

        return true;
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getType()) {
            case 1:
                return MoCreatures.proxy.getTexture("BCmanticore.png");
            case 2:
                return MoCreatures.proxy.getTexture("BCmanticoreDark.png");
            case 3:
                return MoCreatures.proxy.getTexture("BCmanticoreBlue.png");
            case 4:
                return MoCreatures.proxy.getTexture("BCmanticoreGreen.png");
            default:
                return MoCreatures.proxy.getTexture("BCmanticoreGreen.png");
        }
    }

    @Override
    public boolean isFlyer() {
        return true;
    }

    @Override
    public float getMoveSpeed() {
        return 0.9F;
    }

    @Override
    public void fall(float f, float f1) {
    }

    /*protected void updateFallState(double y, boolean onGroundIn, Block blockIn, BlockPos pos) {
    }*/

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        //startArmSwingAttack();
        return super.attackEntityAsMob(entityIn);
    }

    public boolean getIsRideable() {
        return false;
    }

    @Override
    protected boolean isHarmedByDaylight() {
        return true;
    }

    @Override
    public int maxFlyingHeight() {
        return 10;
    }

    @Override
    public int minFlyingHeight() {
        return 1;
    }

    @Override
    public void updateRiderPosition() {
        double dist = (-0.1D);
        double newPosX = this.posX + (dist * Math.sin(this.renderYawOffset / 57.29578F));
        double newPosZ = this.posZ - (dist * Math.cos(this.renderYawOffset / 57.29578F));
        this.riddenByEntity.setPosition(newPosX, this.posY + getMountedYOffset() + this.riddenByEntity.getYOffset(), newPosZ);
        this.riddenByEntity.rotationYaw = this.rotationYaw;
    }

    @Override
    public double getMountedYOffset() {
        return (this.height * 0.75D) - 0.1D;
    }

    /*@Override
    public boolean getCanSpawnHere() {
        if (this.posY < 50D && !this.worldObj.provider.doesWaterVaporize()) {
            setType(32);
        }
        return super.getCanSpawnHere();
    }*/

    /*@Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEFINED;
    }*/

    private void openMouth() {
        this.mouthCounter = 1;
    }

    private void moveTail() {
        this.tailCounter = 1;
    }

    public boolean isOnAir() {
        return this.worldObj.isAirBlock(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY - 0.2D), MathHelper
                .floor_double(this.posZ)));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (this.mouthCounter > 0 && ++this.mouthCounter > 30) {
            this.mouthCounter = 0;
        }

        if (this.tailCounter > 0 && ++this.tailCounter > 8) {
            this.tailCounter = 0;
        }

        if (this.wingFlapCounter > 0 && ++this.wingFlapCounter > 20) {
            this.wingFlapCounter = 0;
        }
    }

    @Override
    public void onLivingUpdate() {
        //if (true) return;
        super.onLivingUpdate();

        /**
         * slow falling
         */
        /*if (!this.onGround && (this.motionY < 0.0D)) {
            this.motionY *= 0.6D;
        }*/

        if (isOnAir() && isFlyer() && this.rand.nextInt(5) == 0) {
            this.wingFlapCounter = 1;
        }

        if (this.rand.nextInt(200) == 0) {
            moveTail();
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
            /*if (this.wingFlapCounter != 0 && this.wingFlapCounter % 5 == 0 && !MoCreatures.isServer()) {
                StarFX();
            }*/
            if (this.wingFlapCounter == 5 && MoCreatures.isServer()) {
                //System.out.println("playing sound");
                MoCTools.playCustomSound(this, "wingflap", this.worldObj);
            }
        }

        if (getIsPoisoning()) {
            this.poisontimer++;
            if (this.poisontimer == 1) {
                this.worldObj.playSoundAtEntity(this, "mocreatures:scorpionsting", 1.0F,
                        1.0F + ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F));
            }
            if (this.poisontimer > 50) {
                this.poisontimer = 0;
                setPoisoning(false);
            }
        }
        if (MoCreatures.isServer()) {
            if (isFlyer() && this.rand.nextInt(500) == 0) {
                wingFlap();
            }

            if (this.riddenByEntity == null && this.rand.nextInt(200) == 0) {
                MoCTools.findMobRider(this);
            }
        }
    }

    @Override
    public void makeEntityJump() {
        wingFlap();
        super.makeEntityJump();
    }

    public void wingFlap() {
        if (this.isFlyer() && this.wingFlapCounter == 0) {
            this.wingFlapCounter = 1;
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 3),
                    new TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 64));
        }
    }

    @Override
    public void performAnimation(int animationType) {
        /*if (animationType >= 23 && animationType < 60) //transform
        {
            this.transformType = animationType;
            this.transformCounter = 1;
        }*/
        if (animationType == 0) //tail animation
        {
            setPoisoning(true);
        } else if (animationType == 3) //wing flap
        {
            this.wingFlapCounter = 1;
        }
    }

    public boolean getIsPoisoning() {
        return this.isPoisoning;
    }

    public void setPoisoning(boolean flag) {
        if (flag && MoCreatures.isServer()) {
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 0),
                    new TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 64));
        }
        this.isPoisoning = flag;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (super.attackEntityFrom(damagesource, i)) {
            Entity entity = damagesource.getEntity();

            if (entity != null && entity != this && entity instanceof EntityLivingBase && this.shouldAttackPlayers() && getIsAdult()) {
                setAttackTarget((EntityLivingBase) entity);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void applyEnchantments(EntityLivingBase entityLivingBaseIn, Entity entityIn) {
        boolean flag = (entityIn instanceof EntityPlayer);
        if (!getIsPoisoning() && this.rand.nextInt(5) == 0 && entityIn instanceof EntityLivingBase) {
            setPoisoning(true);
            if (getType() == 4 || getType() == 2)// regular
            {
                if (flag) {
                    MoCreatures.poisonPlayer((EntityPlayer) entityIn);
                }
                ((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(Potion.poison.id, 70, 0));
            } else if (getType() == 3)// blue
            {
                if (flag) {
                    MoCreatures.freezePlayer((EntityPlayer) entityIn);
                }
                ((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 70, 0));

            } else if (getType() == 1)// red
            {
                if (flag && MoCreatures.isServer() && !this.worldObj.provider.doesWaterVaporize()) {
                    MoCreatures.burnPlayer((EntityPlayer) entityIn);
                    ((EntityLivingBase) entityIn).setFire(15);
                }
            }
        } else {
            openMouth();
        }
        super.applyEnchantments(entityLivingBaseIn, entityIn);
    }

    public boolean swingingTail() {
        return getIsPoisoning() && this.poisontimer < 15;
    }

    @Override
    protected String getDeathSound() {
        openMouth();
        return "mocreatures:liondeath";
    }

    @Override
    protected String getHurtSound() {
        openMouth();
        return "mocreatures:lionhurt";
    }

    @Override
    protected String getLivingSound() {
        openMouth();
        return "mocreatures:liongrunt";
    }

    /*@Override
    protected String getDeathSound() {
        return "mocreatures:manticoredying";
    }

    @Override
    protected String getHurtSound() {
        return "mocreatures:manticorehurt";
    }

    @Override
    protected String getLivingSound() {
        return "mocreatures:manticore";
    }*/

    @Override
    public float getSizeFactor() {
        return 1.4F;
    }

    @Override
    protected Item getDropItem() {
        boolean flag = (this.rand.nextInt(100) < MoCreatures.proxy.rareItemDropChance);

        switch (getType()) {
            case 1:
                if (flag) {
                    return MoCreatures.scorpStingNether;
                }
                return MoCreatures.chitinNether;
            case 2:
                if (flag) {
                    return MoCreatures.scorpStingCave;
                }
                return MoCreatures.chitinCave;

            case 3:
                if (flag) {
                    return MoCreatures.scorpStingFrost;
                }
                return MoCreatures.chitinFrost;
            case 4:
                if (flag) {
                    return MoCreatures.scorpStingDirt;
                }
                return MoCreatures.chitin;

            default:
                return MoCreatures.chitin;
        }
    }

    @Override
    protected void dropFewItems(boolean flag, int x) {
        BlockPos pos = new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(getEntityBoundingBox().minY), this.posZ);
        int chance = MoCreatures.proxy.rareItemDropChance;
        if (this.rand.nextInt(100) < chance) {
            entityDropItem(new ItemStack(MoCreatures.mocegg, 1, getType() + 61), 0.0F);
        } else {
            super.dropFewItems(flag, x);
        }
    }
}
