package drzhark.mocreatures.entity.monster;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.IMob;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class MoCEntityFlameWraith extends MoCEntityWraith implements IMob {

    protected int burningTime;

    public MoCEntityFlameWraith(World world) {
        super(world);
        this.texture = "flamewraith.png";
        setSize(1.5F, 1.5F);
        this.isImmuneToFire = true;
        this.burningTime = 30;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15.0D);
    }

    @Override
    protected Item getDropItem() {
        return Items.REDSTONE;
    }

    @Override
    public void onLivingUpdate() {
        if (!this.world.isRemote) {
            if (this.world.isDaytime()) {
                float f = getBrightness();
                if ((f > 0.5F)
                        && this.world.canBlockSeeSky(new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.posY),
                                MathHelper.floor(this.posZ))) && ((this.rand.nextFloat() * 30F) < ((f - 0.4F) * 2.0F))) {
                    this.setHealth(getHealth() - 2);
                }
            }
        }
        super.onLivingUpdate();
    }

    //TODO TEST
    /*@Override
    public float getMoveSpeed() {
        return 1.1F;
    }*/

    @Override
    protected void applyEnchantments(EntityLivingBase entityLivingBaseIn, Entity entityIn) {
        if (!this.world.isRemote && !this.world.provider.doesWaterVaporize()) {
            entityLivingBaseIn.setFire(this.burningTime);
        }
        super.applyEnchantments(entityLivingBaseIn, entityIn);
    }

    @Override
    public boolean isBurning() {
        return this.rand.nextInt(100) == 0;
    }

    @Override
    protected boolean isHarmedByDaylight() {
        return false;
    }
}
