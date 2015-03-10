package drzhark.mocreatures.entity.monster;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.entity.passive.MoCEntityPetScorpion;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class MoCEntityScorpion extends MoCEntityMob {

    private boolean isPoisoning;
    private int poisontimer;
    public int mouthCounter;
    public int armCounter;
    private int hideCounter;

    public MoCEntityScorpion(World world) {
        super(world);
        setSize(1.4F, 0.9F);
        this.poisontimer = 0;
        setAdult(true);
        setEdad(20);

        if (MoCreatures.isServer()) {
            if (this.rand.nextInt(4) == 0) {
                setHasBabies(true);
            } else {
                setHasBabies(false);
            }
        }
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15.0D);
    }

    @Override
    public void selectType() {
        checkSpawningBiome();

        if (getType() == 0) {
            setType(1);
        }
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getType()) {
            case 1:
                return MoCreatures.proxy.getTexture("scorpiondirt.png");
            case 2:
                return MoCreatures.proxy.getTexture("scorpioncave.png");
            case 3:
                return MoCreatures.proxy.getTexture("scorpionnether.png");
            case 4:
                return MoCreatures.proxy.getTexture("scorpionfrost.png");
            default:
                return MoCreatures.proxy.getTexture("scorpiondirt.png");
        }
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(24, Byte.valueOf((byte) 0)); // isPicked - 0 false 1 true
        this.dataWatcher.addObject(23, Byte.valueOf((byte) 0)); // has babies - 0 false 1 true
    }

    public boolean getHasBabies() {
        return getIsAdult() && (this.dataWatcher.getWatchableObjectByte(23) == 1);
    }

    public boolean getIsPicked() {
        return (this.dataWatcher.getWatchableObjectByte(24) == 1);
    }

    public boolean getIsPoisoning() {
        return this.isPoisoning;
    }

    public void setHasBabies(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(23, Byte.valueOf(input));
    }

    public void setPicked(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(24, Byte.valueOf(input));
    }

    public void setPoisoning(boolean flag) {
        if (flag && MoCreatures.isServer()) {
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 0),
                    new TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 64));
        }
        this.isPoisoning = flag;
    }

    @Override
    public void performAnimation(int animationType) {
        if (animationType == 0) //tail animation
        {
            setPoisoning(true);
        } else if (animationType == 1) //arm swinging
        {
            this.armCounter = 1;
            //swingArm();
        } else if (animationType == 3) //movement of mouth
        {
            this.mouthCounter = 1;
        }
    }

    @Override
    public float getMoveSpeed() {
        return 0.8F;
    }

    @Override
    public boolean isOnLadder() {
        return this.isCollidedHorizontally;
    }

    public boolean climbing() {
        return !this.onGround && isOnLadder();
    }

    /**
     * finds shelter from sunlight
     */
    protected void findSunLightShelter() {
        Vec3 var1 = this.findPossibleShelter();
        if (var1 == null) {
            this.hideCounter++;
            if (this.hideCounter > 200) {
                this.hideCounter = 0;
            }
            // TODO
            //this.updateWanderPath();
            return;// false;
        } else {
            this.getNavigator().tryMoveToXYZ(var1.xCoord, var1.yCoord, var1.zCoord, this.getMoveSpeed() / 2F);
        }
    }

    /**
     * Does it want to hide?
     *
     * @return
     */
    private boolean wantsToHide() {
        return (this.worldObj.isDaytime()); //&& worldObj.canBlockSeeSky(MathHelper.floor_double(this.posX), (int) this.getEntityBoundingBox().minY, MathHelper.floor_double(this.posZ)));
    }

    @Override
    public void onLivingUpdate() {
        if (MoCreatures.isServer() && wantsToHide()) {
            findSunLightShelter();
        }

        if (!this.onGround && (this.ridingEntity != null)) {
            this.rotationYaw = this.ridingEntity.rotationYaw;
        }
        // TODO
        /*
        if (getIsAdult() && fleeingTick > 0) {
            fleeingTick = 0;
        }*/

        if (this.mouthCounter != 0 && this.mouthCounter++ > 50) {
            this.mouthCounter = 0;
        }

        if (MoCreatures.isServer() && (this.armCounter == 10 || this.armCounter == 40)) {
            this.worldObj.playSoundAtEntity(this, "mocreatures:scorpionclaw", 1.0F, 1.0F + ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F));
        }

        if (this.armCounter != 0 && this.armCounter++ > 24) {
            this.armCounter = 0;
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

        if (MoCreatures.isServer() && !getIsAdult() && (this.rand.nextInt(200) == 0)) {
            setEdad(getEdad() + 1);
            if (getEdad() >= 120) {
                setAdult(true);
            }
        }

        super.onLivingUpdate();
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (super.attackEntityFrom(damagesource, i)) {
            Entity entity = damagesource.getEntity();

            if (entity != null && entity != this && entity instanceof EntityLivingBase && (this.worldObj.getDifficulty().getDifficultyId() > 0)
                    && getIsAdult()) {
                setAttackTarget((EntityLivingBase) entity);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected Entity findPlayerToAttack() {
        if (this.worldObj.getDifficulty().getDifficultyId() > 0 && (!this.worldObj.isDaytime()) && getIsAdult())// only attacks player at night
        {
            EntityPlayer entityplayer = this.worldObj.getClosestPlayerToEntity(this, 12D);
            if ((entityplayer != null)) {
                return entityplayer;
            }
            {
                if ((this.rand.nextInt(80) == 0)) {
                    EntityLivingBase entityliving = getClosestEntityLiving(this, 10D);
                    if (entityliving != null && !(entityliving instanceof EntityPlayer) && this.canEntityBeSeen(entityliving)) {
                        return entityliving;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public boolean entitiesToIgnore(Entity entity) {
        return ((super.entitiesToIgnore(entity)) || (this.getIsTamed() && entity instanceof MoCEntityScorpion && ((MoCEntityScorpion) entity)
                .getIsTamed()));
    }

    /*@Override
    protected void attackEntity(Entity entity, float f) {
        if ((f > 2.0F) && (f < 6F) && (this.rand.nextInt(50) == 0)) {
            if (this.onGround) {
                double d = entity.posX - this.posX;
                double d1 = entity.posZ - this.posZ;
                float f1 = MathHelper.sqrt_double((d * d) + (d1 * d1));
                this.motionX = ((d / f1) * 0.5D * 0.8D) + (this.motionX * 0.2D);
                this.motionZ = ((d1 / f1) * 0.5D * 0.8D) + (this.motionZ * 0.2D);
                this.motionY = 0.4D;
            }
        } else if (attackTime <= 0 && (f < 3.0D) && (entity.getEntityBoundingBox().maxY > getEntityBoundingBox().minY)
                && (entity.getEntityBoundingBox().minY < getEntityBoundingBox().maxY)) {
            attackTime = 20;
            boolean flag = (entity instanceof EntityPlayer);
            if (!getIsPoisoning() && this.rand.nextInt(5) == 0 && entity instanceof EntityLivingBase) {
                setPoisoning(true);
                if (getType() <= 2)// regular scorpions
                {
                    if (flag) {
                        MoCreatures.poisonPlayer((EntityPlayer) entity);
                    }
                    ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.poison.id, 70, 0));
                } else if (getType() == 4)// blue scorpions
                {
                    if (flag) {
                        MoCreatures.freezePlayer((EntityPlayer) entity);
                    }
                    ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 70, 0));

                } else if (getType() == 3)// red scorpions
                {
                    if (flag && MoCreatures.isServer() && !this.worldObj.provider.doesWaterVaporize()) {
                        MoCreatures.burnPlayer((EntityPlayer) entity);
                        ((EntityLivingBase) entity).setFire(15);
                    }
                }
            } else {
                entity.attackEntityFrom(DamageSource.causeMobDamage(this), 1);
                swingArm();
            }
        }
    }*/

    public void swingArm() {
        if (MoCreatures.isServer()) {
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 1),
                    new TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 64));
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
    }

    public boolean swingingTail() {
        return getIsPoisoning() && this.poisontimer < 15;
    }

    @Override
    public void onDeath(DamageSource damagesource) {
        super.onDeath(damagesource);

        if (MoCreatures.isServer() && getIsAdult() && getHasBabies()) {
            int k = this.rand.nextInt(5);
            for (int i = 0; i < k; i++) {
                MoCEntityPetScorpion entityscorpy = new MoCEntityPetScorpion(this.worldObj);
                entityscorpy.setPosition(this.posX, this.posY, this.posZ);
                entityscorpy.setAdult(false);
                entityscorpy.setType(getType());
                this.worldObj.spawnEntityInWorld(entityscorpy);
                this.worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F) + 1.0F);

            }
        }
    }

    @Override
    protected String getDeathSound() {
        return "mocreatures:scorpiondying";
    }

    @Override
    protected String getHurtSound() {
        return "mocreatures:scorpionhurt";
    }

    @Override
    protected String getLivingSound() {
        if (MoCreatures.isServer()) {
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 3),
                    new TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 64));
        }
        return "mocreatures:scorpiongrunt";
    }

    @Override
    protected Item getDropItem() {
        if (!getIsAdult()) {
            return Items.string;
        }

        boolean flag = (this.rand.nextInt(100) < MoCreatures.proxy.rareItemDropChance);

        switch (getType()) {
            case 1:
                if (flag) {
                    return MoCreatures.scorpStingDirt;
                }
                return MoCreatures.chitin;
            case 2:
                if (flag) {
                    return MoCreatures.scorpStingCave;
                }
                return MoCreatures.chitinCave;
            case 3:
                if (flag) {
                    return MoCreatures.scorpStingNether;
                }
                return MoCreatures.chitinNether;
            case 4:
                if (flag) {
                    return MoCreatures.scorpStingFrost;
                }
                return MoCreatures.chitinFrost;
            default:
                return Items.string;
        }
    }

    @Override
    protected void dropFewItems(boolean flag, int x) {
        if (!flag) {
            return;
        }
        Item item = this.getDropItem();

        if (item != null) {
            if (this.rand.nextInt(3) == 0) {
                this.dropItem(item, 1);
            }
        }

    }

    @Override
    public boolean getCanSpawnHere() {
        return (isValidLightLevel() && MoCreatures.entityMap.get(this.getClass()).getFrequency() > 0) && getCanSpawnHereLiving()
                && getCanSpawnHereCreature();
    }

    @Override
    public boolean checkSpawningBiome() {
        if (this.worldObj.provider.doesWaterVaporize()) {
            setType(3);
            this.isImmuneToFire = true;
            return true;
        }

        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(getEntityBoundingBox().minY);
        int k = MathHelper.floor_double(this.posZ);
        BlockPos pos = new BlockPos(i, j, k);

        BiomeGenBase currentbiome = MoCTools.Biomekind(this.worldObj, pos);

        if (BiomeDictionary.isBiomeOfType(currentbiome, Type.FROZEN)) {
            setType(4);
        } else if (!this.worldObj.canBlockSeeSky(pos) && (this.posY < 50D)) {
            setType(2);
            return true;
        }

        return true;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        setHasBabies(nbttagcompound.getBoolean("Babies"));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("Babies", getHasBabies());
    }

    /*@Override
    public boolean isAIEnabled() {
        return wantsToHide() && (this.getAttackTarget() == null) && (this.hideCounter < 50);
    }*/

    @Override
    public int getTalkInterval() {
        return 300;
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }

    @Override
    public float getAdjustedYOffset() {
        return 30F;
    }
}
