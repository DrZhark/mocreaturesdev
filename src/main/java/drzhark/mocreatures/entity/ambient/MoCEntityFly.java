package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityInsect;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MoCEntityFly extends MoCEntityInsect {

    public MoCEntityFly(World world) {
        super(world);
        this.texture = "fly.png";
    }

    private int soundCount;// = 50;

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (MoCreatures.isServer()) {

            if (getIsFlying() && this.rand.nextInt(200) == 0) {
                setIsFlying(false);
            }
            if (getIsFlying() && --this.soundCount == -1) {
                EntityPlayer ep = this.worldObj.getClosestPlayerToEntity(this, 5D);
                if (ep != null) {
                    MoCTools.playCustomSound(this, "fly", this.worldObj);
                    this.soundCount = 55;
                }
            }

            //TODO
            //not working this causes entities to attack it and creepers to blow!
            /*
             * if (getAttackTarget() == null && worldObj.rand.nextInt(100) == 0) {
             * List list = worldObj.getEntitiesWithinAABBExcludingEntity(this,
             * getEntityBoundingBox().expand(12D, 8D, 12D)); for (int i = 0; i <
             * list.size(); i++) { Entity entity = (Entity) list.get(i); if
             * (!(entity instanceof EntityMob)) { continue; } EntityMob
             * entitymob = (EntityMob) entity; entitymob.setAttackTarget(this);
             * if (entitymob instanceof EntityZombie) { this.getAttackTarget() =
             * entitymob; } if (entitymob instanceof MoCEntityHorseMob) { if
             * (((MoCEntityHorseMob) entitymob).getType() == 23) { ; } {
             * this.getAttackTarget() = entitymob; } //((MoCEntityOgre)
             * entitymob).attackTime = 20; } } }
             */

        }
    }

    @Override
    public boolean isMyFavoriteFood(ItemStack par1ItemStack) {
        return par1ItemStack != null && par1ItemStack.getItem() == Items.rotten_flesh;
    }

    @Override
    public boolean isFlyer() {
        return true;
    }

    @Override
    public float getAIMoveSpeed() {
        if (getIsFlying()) {
            return 0.2F;
        }
        return 0.12F;
    }
}
