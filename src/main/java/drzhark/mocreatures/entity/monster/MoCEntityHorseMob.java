package drzhark.mocreatures.entity.monster;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.entity.ai.EntityAINearestAttackableTargetMoC;
import drzhark.mocreatures.init.MoCItems;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class MoCEntityHorseMob extends MoCEntityMob {

    public int mouthCounter;
    public int textCounter;
    public int standCounter;
    public int tailCounter;
    public int eatingCounter;
    public int wingFlapCounter;

    public MoCEntityHorseMob(World world) {
        super(world);
        setSize(1.4F, 1.6F);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, true));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTargetMoC(this, EntityPlayer.class, true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
    }

    @Override
    public void selectType() {
        if (this.world.provider.doesWaterVaporize()) {
            setType(38);
            this.isImmuneToFire = true;
        } else {
            if (getType() == 0) {
                int j = this.rand.nextInt(100);
                if (j <= (40)) {
                    setType(23); //undead
                } else if (j <= (80)) {
                    setType(26); //skeleton horse
                } else {
                    setType(32); //bat
                }
            }
        }
    }

    /**
     * Overridden for the dynamic nightmare texture. * 
     * 23 Undead 
     * 24 Undead Unicorn 
     * 25 Undead Pegasus
     * 26 skeleton 
     * 27 skeleton unicorn 
     * 28 skeleton pegasus
     * 30 bug horse
     * 32 Bat Horse 
     * 38 nightmare
     */
    @Override
    public ResourceLocation getTexture() {

        switch (getType()) {
            case 23://undead horse

                if (!MoCreatures.proxy.getAnimateTextures()) {
                    return MoCreatures.proxy.getTexture("horseundead.png");
                }
                String baseTex = "horseundead";
                int max = 79;

                if (this.rand.nextInt(3) == 0) {
                    this.textCounter++;
                }
                if (this.textCounter < 10) {
                    this.textCounter = 10;
                }
                if (this.textCounter > max) {
                    this.textCounter = 10;
                }

                String iteratorTex = "" + this.textCounter;
                iteratorTex = iteratorTex.substring(0, 1);
                String decayTex = "" + (getEdad() / 100);
                decayTex = decayTex.substring(0, 1);
                return MoCreatures.proxy.getTexture(baseTex + decayTex + iteratorTex + ".png");

            case 26:
                return MoCreatures.proxy.getTexture("horseskeleton.png");

            case 32:
                return MoCreatures.proxy.getTexture("horsebat.png");

            case 38:
                if (!MoCreatures.proxy.getAnimateTextures()) {
                    return MoCreatures.proxy.getTexture("horsenightmare1.png");
                }
                this.textCounter++;
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

            default:
                return MoCreatures.proxy.getTexture("horseundead.png");
        }
    }

    @Override
    protected SoundEvent getDeathSound() {
        openMouth();
        return MoCSoundEvents.ENTITY_HORSE_DEATH_UNDEAD;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        openMouth();
        stand();
        return MoCSoundEvents.ENTITY_HORSE_HURT_UNDEAD;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        openMouth();
        if (this.rand.nextInt(10) == 0) {
            stand();
        }
        return MoCSoundEvents.ENTITY_HORSE_AMBIENT_UNDEAD;
    }

    public boolean isOnAir() {
        return this.world.isAirBlock(new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.posY - 0.2D), MathHelper
                .floor(this.posZ)));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (this.mouthCounter > 0 && ++this.mouthCounter > 30) {
            this.mouthCounter = 0;
        }

        if (this.standCounter > 0 && ++this.standCounter > 20) {
            this.standCounter = 0;
        }

        if (this.tailCounter > 0 && ++this.tailCounter > 8) {
            this.tailCounter = 0;
        }

        if (this.eatingCounter > 0 && ++this.eatingCounter > 50) {
            this.eatingCounter = 0;
        }

        if (this.wingFlapCounter > 0 && ++this.wingFlapCounter > 20) {
            this.wingFlapCounter = 0;
            //TODO flap sound!
        }
    }

    @Override
    public boolean isFlyer() {
        return this.getType() == 25 //undead pegasus
                || this.getType() == 32 // bat horse
                || this.getType() == 28; // skeleton pegasus
    }

    /**
     * Has an unicorn? to render it and buckle entities!
     *
     * @return
     */
    public boolean isUnicorned() {
        return this.getType() == 24 || this.getType() == 27 || this.getType() == 32;
    }

    @Override
    public void onLivingUpdate() {

        super.onLivingUpdate();

        if (isOnAir() && isFlyer() && this.rand.nextInt(5) == 0) {
            this.wingFlapCounter = 1;
        }

        if (this.rand.nextInt(200) == 0) {
            moveTail();
        }

        if (!isOnAir() && (!this.isBeingRidden()) && this.rand.nextInt(250) == 0) {
            stand();
        }

        if (this.world.isRemote && getType() == 38 && this.rand.nextInt(50) == 0) {
            LavaFX();
        }

        if (this.world.isRemote && getType() == 23 && this.rand.nextInt(50) == 0) {
            UndeadFX();
        }

        if (!this.world.isRemote) {
            if (isFlyer() && this.rand.nextInt(500) == 0) {
                wingFlap();
            }

            if (!isOnAir() && (!this.isBeingRidden()) && this.rand.nextInt(300) == 0) {
                setEating();
            }

            if (!this.isBeingRidden() && this.rand.nextInt(100) == 0) {
                MoCTools.findMobRider(this);
                /*List list = this.world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(4D, 4D, 4D));
                for (int i = 0; i < list.size(); i++) {
                    Entity entity = (Entity) list.get(i);
                    if (!(entity instanceof EntityMob)) {
                        continue;
                    }
                    EntityMob entitymob = (EntityMob) entity;
                    if (entitymob.getRidingEntity() == null
                            && (entitymob instanceof EntitySkeleton || entitymob instanceof EntityZombie || entitymob instanceof MoCEntitySilverSkeleton)) {
                        entitymob.mountEntity(this);
                        break;
                    }
                }*/
            }
        }
    }

    private void openMouth() {
        this.mouthCounter = 1;
    }

    private void moveTail() {
        this.tailCounter = 1;
    }

    private void setEating() {
        this.eatingCounter = 1;
    }

    private void stand() {
        this.standCounter = 1;
    }

    public void wingFlap() {
        this.wingFlapCounter = 1;
    }

    @Override
    protected Item getDropItem() {
        boolean flag = (this.rand.nextInt(100) < MoCreatures.proxy.rareItemDropChance);
        if (this.getType() == 32 && MoCreatures.proxy.rareItemDropChance < 25) {
            flag = (this.rand.nextInt(100) < 25);
        }

        if (flag && (this.getType() == 36 || (this.getType() >= 50 && this.getType() < 60))) //unicorn
        {
            return MoCItems.unicornhorn;
        }

        if (this.getType() == 38 && flag && this.world.provider.doesWaterVaporize()) //nightmare
        {
            return MoCItems.heartfire;
        }
        if (this.getType() == 32 && flag) //bat horse
        {
            return MoCItems.heartdarkness;
        }
        if (this.getType() == 26)//skely
        {
            return Items.BONE;
        }
        if ((this.getType() == 23 || this.getType() == 24 || this.getType() == 25)) {
            if (flag) {
                return MoCItems.heartundead;
            }
            return Items.ROTTEN_FLESH;
        }

        if (this.getType() == 21 || this.getType() == 22) {
            return Items.GHAST_TEAR;
        }

        return Items.LEATHER;
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        if (entityIn instanceof EntityPlayer && !shouldAttackPlayers()) {
            return false;
        }
        if (this.onGround && !isOnAir()) {
            stand();
        }
        openMouth();
        MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_HORSE_MAD);
        return super.attackEntityAsMob(entityIn);
    }

    @Override
    public void onDeath(DamageSource damagesource) {
        super.onDeath(damagesource);

        if ((this.getType() == 23) || (this.getType() == 24) || (this.getType() == 25)) {
            MoCTools.spawnSlimes(this.world, this);
        }

    }

    @Override
    public double getMountedYOffset() {
        return (this.height * 0.75D) - 0.1D;
    }

    @Override
    public boolean getCanSpawnHere() {
        if (this.posY < 50D && !this.world.provider.doesWaterVaporize()) {
            setType(32);
        }
        return super.getCanSpawnHere();
    }

    public void UndeadFX() {
        MoCreatures.proxy.UndeadFX(this);
    }

    public void LavaFX() {
        MoCreatures.proxy.LavaFX(this);
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        if (getType() == 23 || getType() == 24 || getType() == 25) {
            return EnumCreatureAttribute.UNDEAD;
        }
        return super.getCreatureAttribute();
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
    public void updatePassenger(Entity passenger) {
        double dist = (0.4D);
        double newPosX = this.posX + (dist * Math.sin(this.renderYawOffset / 57.29578F));
        double newPosZ = this.posZ - (dist * Math.cos(this.renderYawOffset / 57.29578F));
        passenger.setPosition(newPosX, this.posY + getMountedYOffset() + passenger.getYOffset(), newPosZ);
        passenger.rotationYaw = this.rotationYaw;
    }
}
