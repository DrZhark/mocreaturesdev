package drzhark.mocreatures.entity.ambient;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityInsect;

public class MoCEntityAnt extends MoCEntityInsect{

    public MoCEntityAnt(World world)
    {
        super(world);
        texture = "ant.png";
    }

     @Override
    protected void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(23, Byte.valueOf((byte) 0)); // foundFood 0 = false, 1 = true
    }

    public boolean getHasFood()
    {
        return (dataWatcher.getWatchableObjectByte(22) == 1);
    }

    public void setHasFood(boolean flag)
    {
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(22, Byte.valueOf(input));
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (MoCreatures.isServer())
        {
            if (!getHasFood())
            {
                EntityItem entityitem = getClosestFood(this, 8D);
                if (entityitem != null && entityitem.ridingEntity == null)
                {
                    float f = entityitem.getDistanceToEntity(this);
                    if (f > 1.0F)
                    {
                        int i = MathHelper.floor_double(entityitem.posX);
                        int j = MathHelper.floor_double(entityitem.posY);
                        int k = MathHelper.floor_double(entityitem.posZ);
                        faceLocation(i, j, k, 30F);

                        getMyOwnPath(entityitem, f);
                        return;
                    }
                    if ((f < 1.0F) && (entityitem != null))
                    {
                        exchangeItem(entityitem);
                        setHasFood(true);
                        return;
                    }
                }
            }

            

        }
        
        if (getHasFood())
        {
            if (this.riddenByEntity == null)
            {
                EntityItem entityitem = getClosestFood(this, 2D);
                if (entityitem != null && entityitem.ridingEntity == null)
                {
                    entityitem.mountEntity(this);
                    return;
                    
                }
                
                if (riddenByEntity == null)
                {
                    setHasFood(false);
                }
            }
        }
    }

    private void exchangeItem(EntityItem entityitem)
    {
        EntityItem cargo = new EntityItem(worldObj, this.posX, this.posY+0.2D, this.posZ, entityitem.getEntityItem());
        entityitem.setDead();
        if (MoCreatures.isServer()) this.worldObj.spawnEntityInWorld(cargo);
    }
    @Override
    public boolean getIsFlying()
    {
        return false;
    }


    @Override
    protected float getWalkingSpeed()
    {
        return 0.8F;
    }

    @Override
    public boolean isMyFavoriteFood(ItemStack par1ItemStack)
    {
        return par1ItemStack != null && isItemEdible(par1ItemStack.getItem());
    }

    
    protected int getFlyingFreq()
    {
        return 5000;
    }
}
