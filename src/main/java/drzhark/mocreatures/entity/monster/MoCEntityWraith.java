package drzhark.mocreatures.entity.monster;

import drzhark.mocreatures.entity.MoCEntityMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class MoCEntityWraith extends MoCEntityMob//MoCEntityFlyerMob
{

    public MoCEntityWraith(World world) {
        super(world);
        this.isCollidedVertically = false;
        this.texture = "wraith.png";
        setSize(1.5F, 1.5F);
        this.isImmuneToFire = false;
        //health = 10;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
                .setBaseValue(this.worldObj.getDifficulty().getDifficultyId() == 1 ? 2.0D : 3.0D); // setAttackStrength
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
    }

    public boolean d2() {
        return super.getCanSpawnHere();
    }

    @Override
    protected String getDeathSound() {
        return "mocreatures:wraithdying";
    }

    @Override
    protected Item getDropItem() {
        return Items.gunpowder;
    }

    @Override
    protected String getHurtSound() {
        return "mocreatures:wraithhurt";
    }

    @Override
    protected String getLivingSound() {
        return "mocreatures:wraith";
    }

    @Override
    public void onLivingUpdate() {
        if (!this.worldObj.isRemote) {
            if (this.worldObj.isDaytime()) {
                float f = getBrightness(1.0F);
                if ((f > 0.5F)
                        && this.worldObj.canBlockSeeSky(new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY),
                                MathHelper.floor_double(this.posZ))) && ((this.rand.nextFloat() * 30F) < ((f - 0.4F) * 2.0F))) {
                    //fire = 300;
                    this.setFire(15);
                }
            }
        }
        super.onLivingUpdate();
    }

    @Override
    public boolean isFlyer() {
        return true;
    }

    @Override
    public float getMoveSpeed() {
        return 1.3F;
    }
}
