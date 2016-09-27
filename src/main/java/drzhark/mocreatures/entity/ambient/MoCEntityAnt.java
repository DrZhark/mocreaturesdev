package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityInsect;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class MoCEntityAnt extends MoCEntityInsect {

	private static final DataParameter<Boolean> FOUND_FOOD = EntityDataManager.<Boolean>createKey(MoCEntityAnt.class, DataSerializers.BOOLEAN);
    
    public MoCEntityAnt(World world) {
        super(world);
        this.texture = "ant.png";
    }

    @Override
    protected void initEntityAI() {
    	this.tasks.addTask(1, new EntityAIWanderMoC2(this, 1.2D));
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(FOUND_FOOD, Boolean.valueOf(false));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.28D);
    }

    public boolean getHasFood() {
    	return ((Boolean)this.dataManager.get(FOUND_FOOD)).booleanValue();
    }

    public void setHasFood(boolean flag) {
    	this.dataManager.set(FOUND_FOOD, Boolean.valueOf(flag));
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (MoCreatures.isServer()) {
            if (!getHasFood()) {
                EntityItem entityitem = MoCTools.getClosestFood(this, 8D);
                if (entityitem != null && entityitem.getRidingEntity() == null) {
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
            if (!this.isBeingRidden()) {
                EntityItem entityitem = MoCTools.getClosestFood(this, 2D);
                if (entityitem != null && entityitem.getRidingEntity() == null) {
                    entityitem.startRiding(this);
                    return;

                }

                if (!this.isBeingRidden()) {
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
