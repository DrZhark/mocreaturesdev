package drzhark.mocreatures.entity.monster;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class MoCEntitySilverSkeleton extends MoCEntityMob {

    public int attackCounterLeft;
    public int attackCounterRight;

    public MoCEntitySilverSkeleton(World world) {
        super(world);
        this.texture = "silverskeleton.png";
        setSize(0.9F, 1.4F);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0D);
    }

    @Override
    public void onLivingUpdate() {
        if (MoCreatures.isServer()) {
            if (this.getAttackTarget() == null) {
                setSprinting(false);
            } else {
                setSprinting(true);
            }

            if (this.worldObj.isDaytime()) {
                float var1 = this.getBrightness(1.0F);

                if (var1 > 0.5F
                        && this.worldObj.canBlockSeeSky(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY),
                                MathHelper.floor_double(this.posZ))) && this.rand.nextFloat() * 30.0F < (var1 - 0.4F) * 2.0F) {
                    this.setFire(8);
                }
            }
        }

        if (this.attackCounterLeft > 0 && ++this.attackCounterLeft > 10) {
            this.attackCounterLeft = 0;
        }

        if (this.attackCounterRight > 0 && ++this.attackCounterRight > 10) {
            this.attackCounterRight = 0;
        }

        super.onLivingUpdate();
    }

    @Override
    protected Item getDropItem() {
        if (this.rand.nextInt(10) == 0) {
            return MoCreatures.silversword;
        }
        return Items.bone;

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

    /**
     * Starts attack counters and synchronizes animations with clients
     */
    private void startAttackAnimation() {
        if (MoCreatures.isServer()) {
            boolean leftArmW = this.rand.nextInt(2) == 0;

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

    /*protected void attackEntity(Entity par1Entity, float par2) {
        if (this.attackTime <= 0 && par2 < 2.0F && par1Entity.getEntityBoundingBox().maxY > this.getEntityBoundingBox().minY
                && par1Entity.getEntityBoundingBox().minY < this.getEntityBoundingBox().maxY) {
            this.attackTime = 20;
            startAttackAnimation();
            this.attackEntityAsMob(par1Entity);
        }
    }*/

    @Override
    public float getMoveSpeed() {
        return 1.2F;
        /*
         * if (isSprinting()) return 1.2F; return 0.8F;
         */
    }

    @Override
    protected String getDeathSound() {
        return "mob.skeleton.death";
    }

    @Override
    protected String getHurtSound() {
        return "mob.skeleton.hurt";
    }

    @Override
    protected String getLivingSound() {
        return "mob.skeleton.say";
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block block) {
        this.playSound("mob.skeleton.step", 0.15F, 1.0F);
    }
}
