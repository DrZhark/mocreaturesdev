package drzhark.mocreatures.entity.monster;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.entity.ai.EntityAINearestAttackableTargetMoC;
import drzhark.mocreatures.init.MoCItems;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class MoCEntityWerewolf extends MoCEntityMob {

    private boolean transforming;
    private int tcounter;
    private int textCounter;
    private static final DataParameter<Boolean> IS_HUMAN = EntityDataManager.<Boolean>createKey(MoCEntityWerewolf.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_HUNCHED = EntityDataManager.<Boolean>createKey(MoCEntityWerewolf.class, DataSerializers.BOOLEAN);
    
    public MoCEntityWerewolf(World world) {
        super(world);
        setSize(0.9F, 1.6F);
        this.transforming = false;
        this.tcounter = 0;
        setHumanForm(true);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, true));
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTargetMoC(this, EntityPlayer.class, true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(IS_HUMAN, Boolean.valueOf(false));
        this.dataManager.register(IS_HUNCHED, Boolean.valueOf(false));
    }

    @Override
    public void setHealth(float par1) {
        if (this.getIsHumanForm() && par1 > 15F) {
            par1 = 15F;
        }
        super.setHealth(par1);
    }

    @Override
    public void selectType() {
        if (getType() == 0) {
            int k = this.rand.nextInt(100);
            if (k <= 28) {
                setType(1);
            } else if (k <= 56) {
                setType(2);
            } else if (k <= 85) {
                setType(3);
            } else {
                setType(4);
                this.isImmuneToFire = true;
            }
        }
    }

    @Override
    public ResourceLocation getTexture() {
        if (this.getIsHumanForm()) {
            return MoCreatures.proxy.getTexture("wereblank.png");
        }

        switch (getType()) {
            case 1:
                return MoCreatures.proxy.getTexture("wolfblack.png");
            case 2:
                return MoCreatures.proxy.getTexture("wolfbrown.png");
            case 3:
                return MoCreatures.proxy.getTexture("wolftimber.png");
            case 4:
                if (!MoCreatures.proxy.getAnimateTextures()) {
                    return MoCreatures.proxy.getTexture("wolffire1.png");
                }
                this.textCounter++;
                if (this.textCounter < 10) {
                    this.textCounter = 10;
                }
                if (this.textCounter > 39) {
                    this.textCounter = 10;
                }
                String NTA = "wolffire";
                String NTB = "" + this.textCounter;
                NTB = NTB.substring(0, 1);
                String NTC = ".png";

                return MoCreatures.proxy.getTexture(NTA + NTB + NTC);
            default:
                return MoCreatures.proxy.getTexture("wolfbrown.png");
        }
    }

    public boolean getIsHumanForm() {
        return ((Boolean)this.dataManager.get(IS_HUMAN)).booleanValue();
    }

    public void setHumanForm(boolean flag) {
        this.dataManager.set(IS_HUMAN, Boolean.valueOf(flag));
    }

    public boolean getIsHunched() {
        return ((Boolean)this.dataManager.get(IS_HUNCHED)).booleanValue();
    }

    public void setHunched(boolean flag) {
        this.dataManager.set(IS_HUNCHED, Boolean.valueOf(flag));
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        if (getIsHumanForm()) {
            setAttackTarget(null);
            return false;
        }
        if (this.getType() == 4 && entityIn instanceof EntityLivingBase) {
            ((EntityLivingBase) entityIn).setFire(10);
        }
        return super.attackEntityAsMob(entityIn);
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        Entity entity = damagesource.getTrueSource();
        if (!getIsHumanForm() && (entity != null) && (entity instanceof EntityPlayer)) {
            EntityPlayer entityplayer = (EntityPlayer) entity;
            ItemStack stack = entityplayer.getHeldItemMainhand();
            if (!stack.isEmpty()) {
                i = 1F;
                if (stack.getItem() == MoCItems.silversword) {
                    i = 10F;
                }
                if (stack.getItem() instanceof ItemSword) {
                    String swordMaterial = ((ItemSword) stack.getItem()).getToolMaterialName();
                    String swordName = ((ItemSword) stack.getItem()).getUnlocalizedName();
                    if (swordMaterial.toLowerCase().contains("silver") || swordName.toLowerCase().contains("silver")) {
                        i = ((ItemSword) stack.getItem()).getAttackDamage() * 3F;
                    }
                } else if (stack.getItem() instanceof ItemTool) {
                    String toolMaterial = ((ItemTool) stack.getItem()).getToolMaterialName();
                    String toolName = ((ItemTool) stack.getItem()).getUnlocalizedName();
                    if (toolMaterial.toLowerCase().contains("silver") || toolName.toLowerCase().contains("silver")) {
                        i = ((ItemSword) stack.getItem()).getAttackDamage() * 2F;
                    }
                } else if (stack.getItem().getUnlocalizedName().toLowerCase().contains("silver")) {
                    i = 6F;
                }
            }
        }
        return super.attackEntityFrom(damagesource, i);
    }

    @Override
    public boolean shouldAttackPlayers() {
        return !getIsHumanForm() && super.shouldAttackPlayers();
    }

    @Override
    protected Item getDropItem() {
        int i = this.rand.nextInt(12);
        if (getIsHumanForm()) {
            switch (i) {
                case 0: // '\0'
                    return Items.WOODEN_SHOVEL;

                case 1: // '\001'
                    return Items.WOODEN_AXE;

                case 2: // '\002'
                    return Items.WOODEN_SWORD;

                case 3: // '\003'
                    return Items.WOODEN_HOE;

                case 4: // '\004'
                    return Items.WOODEN_PICKAXE;
            }
            return Items.STICK;
        }
        switch (i) {
            case 0: // '\0'
                return Items.IRON_HOE;

            case 1: // '\001'
                return Items.IRON_SHOVEL;

            case 2: // '\002'
                return Items.IRON_AXE;

            case 3: // '\003'
                return Items.IRON_PICKAXE;

            case 4: // '\004'
                return Items.IRON_SWORD;

            case 5: // '\005'
                return Items.STONE_HOE;

            case 6: // '\006'
                return Items.STONE_SHOVEL;

            case 7: // '\007'
                return Items.STONE_AXE;

            case 8: // '\b'
                return Items.STONE_PICKAXE;

            case 9: // '\t'
                return Items.STONE_SWORD;
        }
        return Items.GOLDEN_APPLE;
    }

    @Override
    protected SoundEvent getDeathSound() {
        if (getIsHumanForm()) {
            return MoCSoundEvents.ENTITY_WEREWOLF_DEATH_HUMAN;
        } else {
            return MoCSoundEvents.ENTITY_WEREWOLF_DEATH;
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        if (getIsHumanForm()) {
            if (!this.transforming)
                return MoCSoundEvents.ENTITY_WEREWOLF_HURT_HUMAN;
            return null;
        } else {
            return MoCSoundEvents.ENTITY_WEREWOLF_HURT;
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (getIsHumanForm()) {
            return MoCSoundEvents.ENTITY_WEREWOLF_AMBIENT_HUMAN;
        } else {
            return MoCSoundEvents.ENTITY_WEREWOLF_AMBIENT;
        }
    }

    public boolean IsNight() {
        return !this.world.isDaytime();
    }

    @Override
    public void onDeath(DamageSource damagesource) {
        Entity entity = damagesource.getTrueSource();
        if ((this.scoreValue > 0) && (entity != null)) {
            entity.awardKillScore(this, this.scoreValue, damagesource);
        }
        if (entity != null) {
            entity.onKillEntity(this);
        }

        if (!this.world.isRemote) {
            for (int i = 0; i < 2; i++) {
                Item item = getDropItem();
                if (item != null) {
                    dropItem(item, 1);
                }
            }

        }
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!this.world.isRemote) {
            if (((IsNight() && getIsHumanForm()) || (!IsNight() && !getIsHumanForm())) && (this.rand.nextInt(250) == 0)) {
                this.transforming = true;
            }
            if (getIsHumanForm() && (this.getAttackTarget() != null)) {
                setAttackTarget(null);
            }
            if (this.getAttackTarget() != null && !getIsHumanForm()) {
                boolean hunch = (this.getDistanceSq(this.getAttackTarget()) > 12D);
                setHunched(hunch);
            }

            if (this.transforming && (this.rand.nextInt(3) == 0)) {
                this.tcounter++;
                if ((this.tcounter % 2) == 0) {
                    this.posX += 0.3D;
                    this.posY += this.tcounter / 30;
                    attackEntityFrom(DamageSource.causeMobDamage(this), 1);
                }
                if ((this.tcounter % 2) != 0) {
                    this.posX -= 0.3D;
                }
                if (this.tcounter == 10) {
                    MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_WEREWOLF_TRANSFORM);
                }
                if (this.tcounter > 30) {
                    Transform();
                    this.tcounter = 0;
                    this.transforming = false;
                }
            }
            //so entity doesn't despawn that often
            if (this.rand.nextInt(300) == 0) {
                this.idleTime -= 100 * this.world.getDifficulty().getDifficultyId();
                if (this.idleTime  < 0) {
                    this.idleTime  = 0;
                }
            }
        }
    }

    private void Transform() {
        if (this.deathTime > 0) {
            return;
        }
        int i = MathHelper.floor(this.posX);
        int j = MathHelper.floor(getEntityBoundingBox().minY) + 1;
        int k = MathHelper.floor(this.posZ);
        float f = 0.1F;
        for (int l = 0; l < 30; l++) {
            double d = i + this.world.rand.nextFloat();
            double d1 = j + this.world.rand.nextFloat();
            double d2 = k + this.world.rand.nextFloat();
            double d3 = d - i;
            double d4 = d1 - j;
            double d5 = d2 - k;
            double d6 = MathHelper.sqrt((d3 * d3) + (d4 * d4) + (d5 * d5));
            d3 /= d6;
            d4 /= d6;
            d5 /= d6;
            double d7 = 0.5D / ((d6 / f) + 0.1D);
            d7 *= (this.world.rand.nextFloat() * this.world.rand.nextFloat()) + 0.3F;
            d3 *= d7;
            d4 *= d7;
            d5 *= d7;
            this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (d + (i * 1.0D)) / 2D, (d1 + (j * 1.0D)) / 2D, (d2 + (k * 1.0D)) / 2D,
                    d3, d4, d5);
        }

        if (getIsHumanForm()) {
            setHumanForm(false);
            this.setHealth(40);
            this.transforming = false;
            this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
        } else {
            setHumanForm(true);
            this.setHealth(15);
            this.transforming = false;
            this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        setHumanForm(nbttagcompound.getBoolean("HumanForm"));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("HumanForm", getIsHumanForm());
    }

    @Override
    public float getAIMoveSpeed() {
        if (getIsHumanForm()) {
            return 0.1F;
        }
        if (getIsHunched()) {
            return 0.35F;
        }
        return 0.2F;
    }

    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
        if (getType() == 4) {
            this.isImmuneToFire = true;
        }
        return super.onInitialSpawn(difficulty, livingdata);
    }
}
