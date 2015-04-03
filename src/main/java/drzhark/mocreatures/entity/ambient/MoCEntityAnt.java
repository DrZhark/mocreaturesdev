package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityInsect;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class MoCEntityAnt extends MoCEntityInsect {

    public MoCEntityAnt(World world) {
        super(world);
        this.texture = "ant.png";
        this.tasks.addTask(1, new EntityAIWanderMoC2(this, 1.2D));
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(23, Byte.valueOf((byte) 0)); // foundFood 0 = false, 1 = true
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.28D);
    }

    public boolean getHasFood() {
        return (this.dataWatcher.getWatchableObjectByte(23) == 1);
    }

    public void setHasFood(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(23, Byte.valueOf(input));
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (MoCreatures.isServer()) {
            if (!getHasFood()) {
                EntityItem entityitem = MoCTools.getClosestFood(this, 8D);
                if (entityitem != null && entityitem.ridingEntity == null) {
                    float f = entityitem.getDistanceToEntity(this);
                    if (f > 1.0F) {
                        int i = MathHelper.floor_double(entityitem.posX);
                        int j = MathHelper.floor_double(entityitem.posY);
                        int k = MathHelper.floor_double(entityitem.posZ);
                        faceLocation(i, j, k, 30F);

                        getMyOwnPath(entityitem, f);
                        return;
                    }
                    if ((f < 1.0F) && (entityitem != null)) {
                        exchangeItem(entityitem);
                        setHasFood(true);
                        return;
                    }
                }
            }

        }

        if (getHasFood()) {
            if (this.riddenByEntity == null) {
                EntityItem entityitem = MoCTools.getClosestFood(this, 2D);
                if (entityitem != null && entityitem.ridingEntity == null) {
                    entityitem.mountEntity(this);
                    return;

                }

                if (this.riddenByEntity == null) {
                    setHasFood(false);
                }
            }
        }
    }

    private void exchangeItem(EntityItem entityitem) {
        EntityItem cargo = new EntityItem(this.worldObj, this.posX, this.posY + 0.2D, this.posZ, entityitem.getEntityItem());
        entityitem.setDead();
        if (MoCreatures.isServer()) {
            this.worldObj.spawnEntityInWorld(cargo);
        }
    }

    @Override
    public boolean getIsFlying() {
        return false;
    }

    @Override
    public boolean isMyFavoriteFood(ItemStack par1ItemStack) {
        return par1ItemStack != null && MoCTools.isItemEdible(par1ItemStack.getItem());
    }

    @Override
    public boolean isFlyer() {
        return false;
    }

    @Override
    public float getAIMoveSpeed() {
        if (getHasFood()) {
            return 0.05F;
        }
        return 0.15F;
    }
}
