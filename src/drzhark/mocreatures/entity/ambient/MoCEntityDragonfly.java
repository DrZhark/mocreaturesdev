package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityInsect;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class MoCEntityDragonfly extends MoCEntityInsect
{
    private int soundCount;

    public MoCEntityDragonfly(World world)
    {
        super(world);
        //texture = MoCreatures.proxy.MODEL_TEXTURE + "dragonflya.png";
    }

    @Override
    public void selectType()
    {
        if (getType() == 0)
        {
            int i = rand.nextInt(100);
            if (i <= 25)
            {
                setType(1);
            }
            else if (i <= 50)
            {
                setType(2);
            }
            else if (i <= 75)
            {
                setType(3);
            }
            else
            {
                setType(4);
            }
        }
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (MoCreatures.isServer())
        {
            EntityPlayer ep = worldObj.getClosestPlayerToEntity(this, 5D);
            if (ep != null && getIsFlying() && --soundCount == -1)
            {
                MoCTools.playCustomSound(this, "dragonfly", this.worldObj);
                soundCount = 20;
            }

            if (getIsFlying() && rand.nextInt(200) == 0)
            {
                setIsFlying(false);
            }
        }
    }

    @Override
    protected float getFlyingSpeed()
    {
        return 0.8F;
    }

    @Override
    protected float getWalkingSpeed()
    {
        return 0.2F;
    }
}