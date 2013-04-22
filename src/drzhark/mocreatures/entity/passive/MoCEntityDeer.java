package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;

public class MoCEntityDeer extends MoCEntityAnimal {
    //public boolean textureSet;
    private float myMoveSpeed;

    public MoCEntityDeer(World world)
    {
        super(world);
        setEdad(75);
        setSize(0.9F, 1.3F);
        health = 10;
        //textureSet = false;
        setAdult(true);
        myMoveSpeed = 1.7F;
        setTamed(false);
    }

    @Override
    public void selectType()
    {
        if (getType() == 0)
        {
            int i = rand.nextInt(100);
            if (i <= 20)
            {
                setType(1);
            }
            else if (i <= 70)
            {
                setType(2);
            }
            else
            {
                setType(3);
            }
        }
        /*if (getType() == 1)
        {
            texture = MoCreatures.proxy.MODEL_TEXTURE + "deer.png";
            //health = 15;
        } else if (getType() == 2)
        {
            texture = MoCreatures.proxy.MODEL_TEXTURE + "deerf.png";
            //health = 15;
        } else
        {
            texture = MoCreatures.proxy.MODEL_TEXTURE + "deerb.png";
            //health = 5;
            setAdult(false);
        }
        
        setMySpeed(false);*/
    }

    @Override
    public String getTexture()
    {

        switch (getType())
        {
        case 1:
            return MoCreatures.proxy.MODEL_TEXTURE + "deer.png";
        case 2:
            return MoCreatures.proxy.MODEL_TEXTURE + "deerf.png";
        case 3:
            setAdult(false);
            return MoCreatures.proxy.MODEL_TEXTURE + "deerb.png";

        default:
            return MoCreatures.proxy.MODEL_TEXTURE + "deer.png";
        }
    }

    @Override
    protected void fall(float f)
    {
    }

    @Override
    public boolean entitiesToInclude(Entity entity)
    {
        return !(entity instanceof MoCEntityDeer) && super.entitiesToInclude(entity);
    }

    @Override
    protected String getDeathSound()
    {
        return "deerdying";
    }

    /*
     * public int type { return(dataWatcher.getWatchableObjectInt(17)); }
     */

    @Override
    protected int getDropItemId()
    {
        return MoCreatures.fur.itemID;
    }

    @Override
    protected String getHurtSound()
    {
        return "deerhurt";
    }

    @Override
    protected String getLivingSound()
    {
        if (!getIsAdult())
        {
            return "deerbgrunt";
        }
        else
        {
            return "deerfgrunt";
        }
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        if (!worldObj.isRemote)
        {
            if ((getType() == 3) && !getIsAdult() && (rand.nextInt(250) == 0))
            {
                setEdad(getEdad() + 1);
                if (getEdad() >= 130)
                {
                    setAdult(true);
                    int i = rand.nextInt(1);
                    setType(i);// = i;
                }
            }
            if (rand.nextInt(5) == 0)
            {
                EntityLiving entityliving = getBoogey(10D);
                if (entityliving != null)
                {
                    setMySpeed(true);

                    runLikeHell(entityliving);

                }
                else
                {
                    setMySpeed(false);
                }
            }
        }
    }

    public void setMySpeed(boolean flag)
    {
        float f = 1.0F;
        if (getType() == 1)
        {
            f = 1.7F;
        }
        else if (getType() == 2)
        {
            f = 1.9F;
        }
        else
        {
            f = 1.3F;
        }
        if (flag)
        {
            f *= 2.0F;
        }
        myMoveSpeed = f;
    }

    /*public void setTypeInt(int i)
    {
        type = i;
        selectType();
    }
    */
    @Override
    public float getMoveSpeed()
    {
        return myMoveSpeed;
    }

    @Override
    protected void updateEntityActionState()
    {
        if ((myMoveSpeed > 2.0F) && onGround && (rand.nextInt(30) == 0) && ((motionX > 0.1D) || (motionZ > 0.1D) || (motionX < -0.1D) || (motionZ < -0.1D)))
        {
            motionY = 0.6D;
        }
        super.updateEntityActionState();
    }

    @Override
    public boolean getCanSpawnHere()
    {
        return (MoCreatures.proxy.getFrequency(this.getEntityName()) > 0) && super.getCanSpawnHere();

    }
}
