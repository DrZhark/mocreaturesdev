package drzhark.mocreatures.entity.passive;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityInsect;

public class MoCEntityAnt extends MoCEntityInsect{

	public MoCEntityAnt(World world)
	{
		super(world);
		texture = MoCreatures.proxy.MODEL_TEXTURE + "ant.png";
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
				//System.out.println("found food, not taken yet, server = " + MoCreatures.isServer());
				EntityItem entityitem = getClosestFood(this, 2D);
				if (entityitem != null && entityitem.ridingEntity == null)
	            {
					//System.out.println("found food, taking it, server = " + MoCreatures.isServer());
					entityitem.mountEntity(this);
					return;
					
	            }
				
				if (riddenByEntity == null)
	            {
					//System.out.println("couldn't find food, = " + MoCreatures.isServer());
	            	setHasFood(false);
	            }
			}
			/*else
			{
				if (this.riddenByEntity instanceof EntityItem && rand.nextInt(50)==0)
				{
					exchangeItem((EntityItem)this.riddenByEntity);
					
					EntityItem entityitem = (EntityItem) this.riddenByEntity;
					System.out.println("pos = " + entityitem.posX + ", " + entityitem.posY + ", " + entityitem.posZ + " server = "+ MoCreatures.isServer());
					System.out.println("item is mounting = " + entityitem.ridingEntity + " server = "+ MoCreatures.isServer());
					
					
					entityitem.setPosition(this.posX, this.posY, this.posZ);
					entityitem.lastTickPosX = entityitem.prevPosX = entityitem.posX = this.posX;
					entityitem.lastTickPosZ = entityitem.prevPosZ = entityitem.posZ = this.posZ;
					entityitem.lastTickPosY = entityitem.prevPosY = entityitem.posY = this.posY + 0.8D;
				}
				
			}*/
			
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
