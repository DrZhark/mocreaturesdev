package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityInsect;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class MoCEntityFirefly extends MoCEntityInsect

{
    private int soundCount;

    public MoCEntityFirefly(World world)
    {
        super(world);
        //setSize(0.2F, 0.2F);
        //health = 2;
        //moveSpeed = 0.3F;
        texture = MoCreatures.proxy.MODEL_TEXTURE + "firefly.png";
    }

    /*@Override
    protected void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(22, Byte.valueOf((byte) 0)); // isFlying - 0 false 1 true
    }
    
    @Override
    public boolean isFlyer()
    {
        return getIsFlying();
    }
    
    @Override
    public boolean isFlyerInsect()
    {
        return getIsFlying();
    }
    
    //private int textCounter;
    
    public boolean getIsFlying()
    {
        return (dataWatcher.getWatchableObjectByte(22) == 1);
    }
    
    public void setIsFlying(boolean flag)
    {
        if (worldObj.isRemote)
            return;
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(22, Byte.valueOf(input));
    }*/

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        /*if (getIsFlying() && !hasPath() && !isMovementCeased() && entityToAttack == null)
        {
            updateWanderPath();
        }*/

        if (MoCreatures.isServer())
        {
            EntityPlayer ep = worldObj.getClosestPlayerToEntity(this, 5D);
            if (ep != null && getIsFlying() && --soundCount == -1)
            {
                MoCTools.playCustomSound(this, "cricketfly", this.worldObj);
                soundCount = 20;
            }

            if (getIsFlying() && rand.nextInt(500) == 0)
            {
                setIsFlying(false);
            }
        }

    }

    @Override
    protected float getFlyingSpeed()
    {
        return 0.3F;
    }

    @Override
    protected float getWalkingSpeed()
    {
        return 0.2F;
    }

    @Override
    public boolean getCanSpawnHere()
    {
        return (MoCreatures.proxy.getFrequency(this.getEntityName()) > 0) && super.getCanSpawnHere();
    }
}
