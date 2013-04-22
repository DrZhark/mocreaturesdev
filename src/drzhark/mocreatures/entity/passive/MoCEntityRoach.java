package drzhark.mocreatures.entity.passive;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityInsect;


public class MoCEntityRoach extends MoCEntityInsect

{
    public MoCEntityRoach(World world)
    {
        super(world);
        texture = MoCreatures.proxy.MODEL_TEXTURE + "roach.png";
    }

    
    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        //System.out.println("onLadder = " + isOnLadder() + " , server = "  + MoCreatures.isServer());
        if (MoCreatures.isServer())
        {

            if (getIsFlying() && rand.nextInt(50) == 0)
            {
                setIsFlying(false);
            }

            if (!getIsFlying() && rand.nextInt(10) == 0)
            {
                EntityLiving entityliving = getBoogey(3D);
                if (entityliving != null)
                {
                    runLikeHell(entityliving);
                }
            }

            

        }
    }
    
    @Override
    public boolean entitiesToInclude(Entity entity)
    {
        return !(entity instanceof MoCEntityInsect) && super.entitiesToInclude(entity);
    }

    @Override
    protected float getFlyingSpeed()
    {
        return 0.2F;
    }

    @Override
    protected float getWalkingSpeed()
    {
        return 0.8F;
    }

    @Override
    public boolean getCanSpawnHere()
    {
        return (MoCreatures.proxy.getFrequency(this.getEntityName()) > 0) && super.getCanSpawnHere();
    }

    @Override
    public boolean isMyFavoriteFood(ItemStack par1ItemStack)
    {
        return par1ItemStack != null && par1ItemStack.itemID == Item.rottenFlesh.itemID;
    }
    
    @Override
    public boolean getIsFlying()
    {	
    	//return false;
        return (dataWatcher.getWatchableObjectByte(22) == 1);
    }
    
    @Override
    protected int getFlyingFreq()
    {
    	return 300;
    }
    
    /*public boolean climbing()
    {
    	if (isOnLadder())
    	{
    		System.out.println("roach on ladder , server = " + MoCreatures.isServer());
    		return true;
    	}
        return false;
    }*/

    /*@Override
    public boolean isMovementCeased()
    {
    	return true;
    }*/
}
