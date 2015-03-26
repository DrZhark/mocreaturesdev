package drzhark.mocreatures.entity.monster;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
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

    public int frequencyA;
    public int attackCounterLeft;
    public int attackCounterRight;
    private int movingHead;
    public boolean pendingSmashAttack;

    public MoCEntityOgre(World world) {
        super(world);
        setSize(1.9F, 3F);
        this.isImmuneToFire = false;
        this.frequencyA = 30;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(getType() > 4 ? 50.0D : 35.0D);
    }

    @Override
    protected double getAttackStrenght() {
        return 3D;
    }

    @Override
    public void selectType() {
        if (this.worldObj.provider.doesWaterVaporize()) {
            setType(this.rand.nextInt(2) + 3);
            this.setHealth(getMaxHealth());
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

                this.setHealth(getMaxHealth());
            }
        }
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

    /*@Override
    protected void attackEntity(Entity entity, float f) {
        if (attackTime <= 0 && (f < 2.5F) && (entity.getEntityBoundingBox().maxY > getEntityBoundingBox().minY)
                && (entity.getEntityBoundingBox().minY < getEntityBoundingBox().maxY) && (this.worldObj.getDifficulty().getDifficultyId() > 0)) {
            attackTime = 20;
            this.attackEntityAsMob(entity);
        }
    }*/

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

    public void DestroyingOgre() {
        if (this.deathTime > 0) {
            return;
        }
        MoCTools.DestroyBlast(this, this.posX, this.posY + 1.0D, this.posZ, getDestroyForce(), getOgreFire());
    }

    /*protected Entity findPlayerToAttack() {
        float f = getBrightness(1.0F);
        if (f < 0.5F) {
            EntityPlayer entityplayer = this.worldObj.getClosestPlayerToEntity(this, getAttackRange());
            if ((entityplayer != null) && (this.worldObj.getDifficulty().getDifficultyId() > 0)) {
                return entityplayer;
            }
        }
        return null;
    }*/

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

    public boolean getOgreFire() {
        if (getType() == 3 || getType() == 4) {
            this.isImmuneToFire = true;
            return true;
        }
        return false;
    }

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

            if ((this.getAttackTarget() != null) && (this.rand.nextInt(this.frequencyA) == 0) /*&& attackTime == 0*/&& this.attackCounterLeft == 0
                    && this.attackCounterRight == 0) {
                startOgreAttack();
            }

            // TODO
            /*if ((attackTime <= 0) && this.pendingSmashAttack) {
                this.pendingSmashAttack = false;
                DestroyingOgre();
                MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageExplode(this.getEntityId()),
                        new TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 64));
            }*/

            if (getType() > 2) {

                if (this.worldObj.isDaytime()) {
                    float f = getBrightness(1.0F);
                    if ((f > 0.5F)
                            && this.worldObj.canBlockSeeSky(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY),
                                    MathHelper.floor_double(this.posZ))) && ((this.rand.nextFloat() * 30F) < ((f - 0.4F) * 2.0F))) {
                        this.setHealth(getHealth() - 5);
                    }
                }
            }
        }

        if (this.attackCounterLeft > 0 && ++this.attackCounterLeft > 30) {
            this.attackCounterLeft = 0;
        }

        if (this.attackCounterRight > 0 && ++this.attackCounterRight > 30) {
            this.attackCounterRight = 0;
        }
        super.onLivingUpdate();
    }

    /**
     * Starts attack counters and synchronizes animations with clients
     */
    private void startOgreAttack() {
        if (MoCreatures.isServer()) {
            //attackTime = 15;
            this.pendingSmashAttack = true;
            boolean leftArmW = (getType() == 2 || getType() == 4 || getType() == 6) && this.rand.nextInt(2) == 0;

            if (leftArmW) {
                this.attackCounterLeft = 1;
                MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 1),
                        new TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 64));
            } else {
                this.attackCounterRight = 1;
                MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 2),
                        new TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 64));
            }
        }
    }

    @Override
    public void performAnimation(int animationType) {
        if (animationType == 1) //left arm
        {
            this.attackCounterLeft = 1;
        }
        if (animationType == 2) //right arm
        {
            this.attackCounterRight = 1;
        }
    }

    public int getMovingHead() {
        if (getType() == 1 || getType() == 3 || getType() == 5) //single headed ogre
        {
            return 1;
        }

        if (this.rand.nextInt(100) == 0) {
            this.movingHead = this.rand.nextInt(2) + 2; //randomly changes the focus head, returns 2 or 3
        }
        return this.movingHead;
    }

    private boolean canCaveOgreSpawn() {
        return (!this.worldObj.canBlockSeeSky(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper
                .floor_double(this.posZ)))) && (this.posY < 50D);
    }
}
