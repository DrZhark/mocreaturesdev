package drzhark.mocreatures.entity.ambient;

import com.google.common.base.Predicate;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityInsect;
import drzhark.mocreatures.entity.ai.EntityAIFleeFromEntityMoC;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MoCEntityRoach extends MoCEntityInsect

{

    public MoCEntityRoach(World world) {
        super(world);
        this.texture = "roach.png";
    }

    @Override
    protected void initEntityAI() {
    	this.tasks.addTask(3, new EntityAIFleeFromEntityMoC(this, new Predicate<Entity>() {

            public boolean apply(Entity entity) {
                return !(entity instanceof MoCEntityCrab) && (entity.height > 0.3F || entity.width > 0.3F);
            }
        }, 6.0F, 0.8D, 1.3D));
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (MoCreatures.isServer()) {

            if (getIsFlying() && this.rand.nextInt(50) == 0) {
                setIsFlying(false);
            }

            /*if (!getIsFlying() && this.rand.nextInt(10) == 0) {
                EntityLivingBase entityliving = getBoogey(3D);
                if (entityliving != null) {
                    MoCTools.runLikeHell(this, entityliving);
                }
            }*/
        }
    }

    @Override
    public boolean entitiesToInclude(Entity entity) {
        return !(entity instanceof MoCEntityInsect) && super.entitiesToInclude(entity);
    }

    @Override
    public boolean isFlyer() {
        return true;
    }

    @Override
    public boolean isMyFavoriteFood(ItemStack par1ItemStack) {
        return par1ItemStack != null && par1ItemStack.getItem() == Items.ROTTEN_FLESH;
    }

    @Override
    protected int getFlyingFreq() {
        return 500;
    }

    @Override
    public float getAIMoveSpeed() {
        if (getIsFlying()) {
            return 0.1F;
        }
        return 0.25F;
    }

    @Override
    public boolean isNotScared() {
        return getIsFlying();
    }
}
