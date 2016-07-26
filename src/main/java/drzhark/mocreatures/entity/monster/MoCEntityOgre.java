package drzhark.mocreatures.entity.monster;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.entity.ai.EntityAINearestAttackableTargetMoC;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import drzhark.mocreatures.network.message.MoCMessageExplode;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class MoCEntityOgre extends MoCEntityMob {

    public int attackCounterLeft;
    public int attackCounterRight;
    private int movingHead;
    public int smashCounter;
    public int armToAnimate; // 1 = left, 2 = right, 3 = both
    public int attackCounter;

    public MoCEntityOgre(World world) {
        super(world);
        setSize(1.9F, 3F);
        this.isImmuneToFire = false;
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, 1.0D, true));
        this.tasks.addTask(2, this.aiAvoidExplodingCreepers);
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTargetMoC(this, EntityPlayer.class, true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0D);
    }

    @Override
    public void selectType() {
        if (this.worldObj.provider.doesWaterVaporize()) {
            setType(this.rand.nextInt(2) + 3);
            this.isImmuneToFire = true;

        } else {
            if (getType() == 0) {
                int fOgreChance = MoCreatures.proxy.fireOgreChance;
                int cOgreChance = MoCreatures.proxy.caveOgreChance;
                int j = this.rand.nextInt(100);

                if (canCaveOgreSpawn() && (j >= (100 - cOgreChance))) { //System.out.println("can spawn cave o");
                    setType(this.rand.nextInt(2) + 5);
                } else if (j >= (100 - fOgreChance)) {
                    setType(this.rand.nextInt(2) + 3);
                    this.isImmuneToFire = true;
                } else {
                    setType(this.rand.nextInt(2) + 1);
                }
            }
        }
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(getType() > 4 ? 50.0D : 35.0D);
        this.setHealth(getMaxHealth());
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getType()) {
            case 1:
            case 2:
                return MoCreatures.proxy.getTexture("ogregreen.png");
            case 3:
            case 4:
                return MoCreatures.proxy.getTexture("ogrered.png");
            case 5:
            case 6:
                return MoCreatures.proxy.getTexture("ogreblue.png");
            default:
                return MoCreatures.proxy.getTexture("ogregreen.png");
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (super.attackEntityFrom(damagesource, i)) {
            Entity entity = damagesource.getEntity();
            if ((this.riddenByEntity == entity) || (this.ridingEntity == entity)) {
                return true;
            }
            if ((entity != this) && (this.worldObj.getDifficulty().getDifficultyId() > 0) && entity instanceof EntityLivingBase) {
                setAttackTarget((EntityLivingBase) entity);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean shouldAttackPlayers() {
        return (this.getBrightness(1.0F) < 0.5F) && super.shouldAttackPlayers();
    }

    @Override
    protected String getDeathSound() {
        return "mocreatures:ogredying";
    }

    @Override
    protected Item getDropItem() {
        if (getType() < 3) {
            return Item.getItemFromBlock(Blocks.obsidian);
        } else if (getType() < 5) {
            boolean flag = (this.rand.nextInt(100) < MoCreatures.proxy.rareItemDropChance);
            if (!flag) {
                return Item.getItemFromBlock(Blocks.fire);
            }
            return MoCreatures.heartfire;
        }
        return Items.diamond;
    }

    @Override
    protected String getHurtSound() {
        return "mocreatures:ogrehurt";
    }

    @Override
    protected String getLivingSound() {
        return "mocreatures:ogre";
    }

    public boolean isFireStarter() {
        if (getType() == 3 || getType() == 4) {
            this.isImmuneToFire = true;
            return true;
        }
        return false;
    }

    /**
     * Returns the strength of the blasting power
     * @return
     */
    public float getDestroyForce() {
        int t = getType();
        if (t < 3) //green
        {
            return MoCreatures.proxy.ogreStrength;
        } else if (t < 5) //red
        {
            return MoCreatures.proxy.fireOgreStrength;
        }
        return MoCreatures.proxy.caveOgreStrength;
    }

    public int getAttackRange() {
        return MoCreatures.proxy.ogreAttackRange;
    }

    @Override
    public void onLivingUpdate() {
        if (MoCreatures.isServer()) {
            if (this.smashCounter > 0 && ++this.smashCounter > 10) {
                this.smashCounter = 0;
                performDestroyBlastAttack();
                MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageExplode(this.getEntityId()),
                        new TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 64));
            }

            if ((this.getAttackTarget() != null) && (this.rand.nextInt(40) == 0) && this.smashCounter == 0 && this.attackCounter == 0) {
                startDestroyBlast();
            }
        }

        if (this.attackCounter > 0) {
            if (armToAnimate == 3) {
                this.attackCounter++;
            } else {
                this.attackCounter += 2;
            }

            if (this.attackCounter > 10) {
                this.attackCounter = 0;
                this.armToAnimate = 0;
            }
        }
        super.onLivingUpdate();
    }

    /**
     * Starts counter to perform the DestroyBlast and synchronizes animations with clients
     */
    private void startDestroyBlast() {
        this.smashCounter = 1;
        MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 3),
                new TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 64));
    }

    /**
     * Performs the destroy Blast Attack
     */
    public void performDestroyBlastAttack() {
        if (this.deathTime > 0) {
            return;
        }
        MoCTools.DestroyBlast(this, this.posX, this.posY + 1.0D, this.posZ, getDestroyForce(), isFireStarter());
    }

    @Override
    protected boolean isHarmedByDaylight() {
        return this.getType() > 2;
    }

    /**
     * Starts attack counters and synchronizes animations with clients
     */
    private void startArmSwingAttack() {
        if (MoCreatures.isServer()) {
            if (this.smashCounter != 0)
                return;

            boolean leftArmW = (getType() == 2 || getType() == 4 || getType() == 6) && this.rand.nextInt(2) == 0;

            if (leftArmW) {
                this.attackCounter = 1;
                this.armToAnimate = 1;
                MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 1),
                        new TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 64));
            } else {
                this.attackCounter = 1;
                this.armToAnimate = 2;
                MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 2),
                        new TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 64));
            }
        }
    }

    @Override
    public void performAnimation(int animationType) {
        if (animationType != 0) {
            this.attackCounter = 1;
            this.armToAnimate = animationType;
        }

    }

    public int getMovingHead() {
        if (getType() == 1 || getType() == 3 || getType() == 5) //single headed ogre
        {
            return 1;
        }

        if (this.rand.nextInt(60) == 0) {
            this.movingHead = this.rand.nextInt(2) + 2; //randomly changes the focus head, returns 2 or 3
        }
        return this.movingHead;
    }

    private boolean canCaveOgreSpawn() {
        return (!this.worldObj.canBlockSeeSky(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper
                .floor_double(this.posZ)))) && (this.posY < 50D);
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        startArmSwingAttack();
        return super.attackEntityAsMob(entityIn);
    }
}
