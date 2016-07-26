package drzhark.mocreatures.entity.monster;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.entity.ai.EntityAIFleeFromPlayer;
import drzhark.mocreatures.entity.ai.EntityAINearestAttackableTargetMoC;
import drzhark.mocreatures.entity.passive.MoCEntityPetScorpion;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
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

public class MoCEntityScorpion extends MoCEntityMob {

    private boolean isPoisoning;
    private int poisontimer;
    public int mouthCounter;
    public int armCounter;

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
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAttackOnCollide(this, 1.0D, true));
        this.tasks.addTask(2, new EntityAIRestrictSun(this));
        this.tasks.addTask(7, new EntityAIFleeSun(this, 1.0D));
        this.tasks.addTask(5, new EntityAIFleeFromPlayer(this, 1.2D, 4D));
        this.tasks.addTask(6, new EntityAILeapAtTarget(this, 0.4F));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTargetMoC(this, EntityPlayer.class, true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(18.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0D);
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

    @Override
    public void onLivingUpdate() {

        if (!this.onGround && (this.ridingEntity != null)) {
            this.rotationYaw = this.ridingEntity.rotationYaw;
        }

        if (this.mouthCounter != 0 && this.mouthCounter++ > 50) {
            this.mouthCounter = 0;
        }

        if (MoCreatures.isServer() && (this.armCounter == 10 || this.armCounter == 40)) {
            this.worldObj.playSoundAtEntity(this, "mocreatures:scorpionclaw", 1.0F, 1.0F + ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F));
        }

        if (this.armCounter != 0 && this.armCounter++ > 24) {
            this.armCounter = 0;
        }

        if (MoCreatures.isServer() && this.riddenByEntity == null && this.getIsAdult() && !this.getHasBabies() && this.rand.nextInt(100) == 0) {
            MoCTools.findMobRider(this);
            /*List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(4D, 2D, 4D));
            for (int i = 0; i < list.size(); i++) {
                Entity entity = (Entity) list.get(i);
                if (!(entity instanceof EntityMob)) {
                    continue;
                }
                EntityMob entitymob = (EntityMob) entity;
                if (entitymob.ridingEntity == null
                        && (entitymob instanceof EntitySkeleton || entitymob instanceof EntityZombie || entitymob instanceof MoCEntitySilverSkeleton)) {
                    entitymob.mountEntity(this);
                    break;
                }
            }*/
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
        super.onLivingUpdate();
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
    public boolean entitiesToIgnore(Entity entity) {
        return ((super.entitiesToIgnore(entity)) || (this.getIsTamed() && entity instanceof MoCEntityScorpion && ((MoCEntityScorpion) entity)
                .getIsTamed()));
    }

    @Override
    protected void applyEnchantments(EntityLivingBase entityLivingBaseIn, Entity entityIn) {
        boolean flag = (entityIn instanceof EntityPlayer);
        if (!getIsPoisoning() && this.rand.nextInt(5) == 0 && entityIn instanceof EntityLivingBase) {
            setPoisoning(true);
            if (getType() <= 2)// regular scorpions
            {
                if (flag) {
                    MoCreatures.poisonPlayer((EntityPlayer) entityIn);
                }
                ((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(Potion.poison.id, 70, 0));
            } else if (getType() == 4)// blue scorpions
            {
                if (flag) {
                    MoCreatures.freezePlayer((EntityPlayer) entityIn);
                }
                ((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 70, 0));

            } else if (getType() == 3)// red scorpions
            {
                if (flag && MoCreatures.isServer() && !this.worldObj.provider.doesWaterVaporize()) {
                    MoCreatures.burnPlayer((EntityPlayer) entityIn);
                    ((EntityLivingBase) entityIn).setFire(15);
                }
            }
        } else {
            swingArm();
        }
        super.applyEnchantments(entityLivingBaseIn, entityIn);
    }

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
                MoCEntityScorpion entityscorpy = new MoCEntityScorpion(this.worldObj);
                entityscorpy.setPosition(this.posX, this.posY, this.posZ);
                entityscorpy.setAdult(false);
                entityscorpy.setEdad(20);
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

        if (BiomeDictionary.isBiomeOfType(currentbiome, Type.SNOWY)) {
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

    @Override
    protected int getMaxEdad() {
        return 120;
    }

    @Override
    public boolean isNotScared() {
        return getIsAdult() || this.getEdad() > 70;
    }

    @Override
    public boolean interact(EntityPlayer entityplayer) {
        if (super.interact(entityplayer)) {
            return false;
        }

        if (MoCreatures.isServer() && this.ridingEntity == null && !getIsAdult() && getEdad() < 60) {
            MoCEntityPetScorpion entitypetscorpy = new MoCEntityPetScorpion(this.worldObj);
            entitypetscorpy.setPosition(this.posX, this.posY, this.posZ);
            entitypetscorpy.setAdult(false);
            entitypetscorpy.setEdad(this.getEdad());
            entitypetscorpy.setType(getType());
            this.worldObj.spawnEntityInWorld(entitypetscorpy);
            entitypetscorpy.rotationYaw = entityplayer.rotationYaw;
            entitypetscorpy.mountEntity(entityplayer);
            MoCTools.tameWithName(entityplayer, entitypetscorpy);
            //entitypetscorpy.setPicked(true);
            this.setDead();
            return true;

        }
        return false;
    }

    @Override
    public double getMountedYOffset() {
        return (this.height * 0.75D) - 0.15D;
    }

    @Override
    public void updateRiderPosition() {
        double dist = (0.2D);
        double newPosX = this.posX + (dist * Math.sin(this.renderYawOffset / 57.29578F));
        double newPosZ = this.posZ - (dist * Math.cos(this.renderYawOffset / 57.29578F));
        this.riddenByEntity.setPosition(newPosX, this.posY + getMountedYOffset() + this.riddenByEntity.getYOffset(), newPosZ);
        this.riddenByEntity.rotationYaw = this.rotationYaw;
    }
}
