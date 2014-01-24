package drzhark.mocreatures.entity.ambient;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityInsect;

public class MoCEntityRoach extends MoCEntityInsect

{
    public MoCEntityRoach(World world)
    {
        super(world);
        texture = "roach.png";
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (MoCreatures.isServer())
        {

            if (getIsFlying() && rand.nextInt(50) == 0)
            {
                setIsFlying(false);
            }

            if (!getIsFlying() && rand.nextInt(10) == 0)
            {
                EntityLivingBase entityliving = getBoogey(3D);
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
    public boolean isMyFavoriteFood(ItemStack par1ItemStack)
    {
        return par1ItemStack != null && par1ItemStack.getItem() == Items.rotten_flesh;
    }

    @Override
    public boolean getIsFlying()
    {    
        return (dataWatcher.getWatchableObjectByte(22) == 1);
    }

    @Override
    protected int getFlyingFreq()
    {
        return 300;
    }
}