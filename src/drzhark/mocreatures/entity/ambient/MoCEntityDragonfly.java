package drzhark.mocreatures.entity.ambient;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityInsect;

public class MoCEntityDragonfly extends MoCEntityInsect
{
    private int soundCount;

    public MoCEntityDragonfly(World world)
    {
        super(world);
        texture = "dragonflya.png";
    }

    @Override
    public void selectType()
    {
        if (getType() == 0)
        {
            setType(rand.nextInt(4)+1);
        }
    }

    @Override
    public ResourceLocation getTexture()
    {
        switch (getType())
        {
        case 1:
            return MoCreatures.proxy.getTexture("dragonflya.png");
        case 2:
            return MoCreatures.proxy.getTexture("dragonflyb.png");
        case 3:
            return MoCreatures.proxy.getTexture("dragonflyc.png");
        case 4:
            return MoCreatures.proxy.getTexture("dragonflyd.png");

        default:
            return MoCreatures.proxy.getTexture("dragonflyd.png");
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