package drzhark.mocreatures.entity.monster;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.entity.ai.EntityAINearestAttackableTargetMoC;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
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
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class MoCEntityWerewolf extends MoCEntityMob {

    private boolean transforming;
    private int tcounter;
    private int textCounter;

    public MoCEntityWerewolf(World world) {
        super(world);
        setSize(0.9F, 1.6F);
        this.transforming = false;
        this.tcounter = 0;
        setHumanForm(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, 1.0D, true));
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTargetMoC(this, EntityPlayer.class, true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(24, Byte.valueOf((byte) 0)); // isHumanForm - 0 false 1 true
        this.dataWatcher.addObject(23, Byte.valueOf((byte) 0)); //hunched
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
        return (this.dataWatcher.getWatchableObjectByte(24) == 1);
    }

    public void setHumanForm(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(24, Byte.valueOf(input));
    }

    public boolean getIsHunched() {
        return (this.dataWatcher.getWatchableObjectByte(23) == 1);
    }

    public void setHunched(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(23, Byte.valueOf(input));
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
        Entity entity = damagesource.getEntity();
        if (!getIsHumanForm() && (entity != null) && (entity instanceof EntityPlayer)) {
            EntityPlayer entityplayer = (EntityPlayer) entity;
            ItemStack itemstack = entityplayer.getCurrentEquippedItem();
            if (itemstack != null) {
                i = 1F;
                if (itemstack.getItem() == MoCreatures.silversword) {
                    i = 10F;
                }
                if (itemstack.getItem() instanceof ItemSword) {
                    String swordMaterial = ((ItemSword) itemstack.getItem()).getToolMaterialName();
                    String swordName = ((ItemSword) itemstack.getItem()).getUnlocalizedName();
                    if (swordMaterial.toLowerCase().contains("silver") || swordName.toLowerCase().contains("silver")) {
                        i = ((ItemSword) itemstack.getItem()).getDamageVsEntity() * 3F;
                    }
                } else if (itemstack.getItem() instanceof ItemTool) {
                    String toolMaterial = ((ItemSword) itemstack.getItem()).getToolMaterialName();
                    String toolName = ((ItemSword) itemstack.getItem()).getUnlocalizedName();
                    if (toolMaterial.toLowerCase().contains("silver") || toolName.toLowerCase().contains("silver")) {
                        i = ((ItemSword) itemstack.getItem()).getDamageVsEntity() * 2F;
                    }
                } else if (itemstack.getItem().getUnlocalizedName().toLowerCase().contains("silver")) {
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
    protected String getDeathSound() {
        if (getIsHumanForm()) {
            return "mocreatures:werehumandying";
        } else {
            return "mocreatures:werewolfdying";
        }
    }

    @Override
    protected Item getDropItem() {
        int i = this.rand.nextInt(12);
        if (getIsHumanForm()) {
            switch (i) {
                case 0: // '\0'
                    return Items.wooden_shovel;

                case 1: // '\001'
                    return Items.wooden_axe;

                case 2: // '\002'
                    return Items.wooden_sword;

                case 3: // '\003'
                    return Items.wooden_hoe;

                case 4: // '\004'
                    return Items.wooden_pickaxe;
            }
            return Items.stick;
        }
        switch (i) {
            case 0: // '\0'
                return Items.iron_hoe;

            case 1: // '\001'
                return Items.iron_shovel;

            case 2: // '\002'
                return Items.iron_axe;

            case 3: // '\003'
                return Items.iron_pickaxe;

            case 4: // '\004'
                return Items.iron_sword;

            case 5: // '\005'
                return Items.stone_hoe;

            case 6: // '\006'
                return Items.stone_shovel;

            case 7: // '\007'
                return Items.stone_axe;

            case 8: // '\b'
                return Items.stone_pickaxe;

            case 9: // '\t'
                return Items.stone_sword;
        }
        return Items.golden_apple;
    }

    @Override
    protected String getHurtSound() {
        if (getIsHumanForm()) {
            if (!this.transforming)
                return "mocreatures:werehumanhurt";
            return null;
        } else {
            return "mocreatures:werewolfhurt";
        }
    }

    @Override
    protected String getLivingSound() {
        if (getIsHumanForm()) {
            return "mocreatures:werehumangrunt";
        } else {
            return "mocreatures:werewolfgrunt";
        }
    }

    public boolean IsNight() {
        return !this.worldObj.isDaytime();
    }

    @Override
    public void onDeath(DamageSource damagesource) {
        Entity entity = damagesource.getEntity();
        if ((this.scoreValue > 0) && (entity != null)) {
            entity.addToPlayerScore(this, this.scoreValue);
        }
        if (entity != null) {
            entity.onKillEntity(this);
        }

        if (!this.worldObj.isRemote) {
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
        if (!this.worldObj.isRemote) {
            if (((IsNight() && getIsHumanForm()) || (!IsNight() && !getIsHumanForm())) && (this.rand.nextInt(250) == 0)) {
                this.transforming = true;
            }
            if (getIsHumanForm() && (this.getAttackTarget() != null)) {
                setAttackTarget(null);
            }
            if (this.getAttackTarget() != null && !getIsHumanForm()) {
                boolean hunch = (this.getDistanceSqToEntity(this.getAttackTarget()) > 12D);
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
                    this.worldObj.playSoundAtEntity(this, "mocreatures:weretransform", 1.0F,
                            ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F) + 1.0F);
                }
                if (this.tcounter > 30) {
                    Transform();
                    this.tcounter = 0;
                    this.transforming = false;
                }
            }
            //so entity doesn't despawn that often
            if (this.rand.nextInt(300) == 0) {
                this.entityAge -= 100 * this.worldObj.getDifficulty().getDifficultyId();
                if (this.entityAge < 0) {
                    this.entityAge = 0;
                }
            }
        }
    }

    private void Transform() {
        if (this.deathTime > 0) {
            return;
        }
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(getEntityBoundingBox().minY) + 1;
        int k = MathHelper.floor_double(this.posZ);
        float f = 0.1F;
        for (int l = 0; l < 30; l++) {
            double d = i + this.worldObj.rand.nextFloat();
            double d1 = j + this.worldObj.rand.nextFloat();
            double d2 = k + this.worldObj.rand.nextFloat();
            double d3 = d - i;
            double d4 = d1 - j;
            double d5 = d2 - k;
            double d6 = MathHelper.sqrt_double((d3 * d3) + (d4 * d4) + (d5 * d5));
            d3 /= d6;
            d4 /= d6;
            d5 /= d6;
            double d7 = 0.5D / ((d6 / f) + 0.1D);
            d7 *= (this.worldObj.rand.nextFloat() * this.worldObj.rand.nextFloat()) + 0.3F;
            d3 *= d7;
            d4 *= d7;
            d5 *= d7;
            this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (d + (i * 1.0D)) / 2D, (d1 + (j * 1.0D)) / 2D, (d2 + (k * 1.0D)) / 2D,
                    d3, d4, d5);
        }

        if (getIsHumanForm()) {
            setHumanForm(false);
            this.setHealth(40);
            this.transforming = false;
            this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
        } else {
            setHumanForm(true);
            this.setHealth(15);
            this.transforming = false;
            this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
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
