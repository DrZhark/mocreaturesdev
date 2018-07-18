package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.MoCTools;
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

        if (!this.world.isRemote) {
            if (!getHasFood()) {
                EntityItem entityitem = MoCTools.getClosestFood(this, 8D);
                if (entityitem == null || entityitem.isDead) {
                    return;
                }
                if (entityitem.getRidingEntity() == null) {
                    float f = entityitem.getDistance(this);
                    if (f > 1.0F) {
                        int i = MathHelper.floor(entityitem.posX);
                        int j = MathHelper.floor(entityitem.posY);
                        int k = MathHelper.floor(entityitem.posZ);
                        faceLocation(i, j, k, 30F);

                        getMyOwnPath(entityitem, f);
                        return;
                    }
                    if (f < 1.0F) {
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
        EntityItem cargo = new EntityItem(this.world, this.posX, this.posY + 0.2D, this.posZ, entityitem.getItem());
        entityitem.setDead();
        if (!this.world.isRemote) {
            this.world.spawnEntity(cargo);
        }
    }

    @Override
    public boolean getIsFlying() {
        return false;
    }

    @Override
    public boolean isMyFavoriteFood(ItemStack stack) {
        return !stack.isEmpty() && MoCTools.isItemEdible(stack.getItem());
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
