package drzhark.mocreatures.entity.passive;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;

public class MoCEntityDeer extends MoCEntityTameableAnimal {

    private float myMoveSpeed;

    public MoCEntityDeer(World world)
    {
        super(world);
        setEdad(75);
        setSize(0.9F, 1.3F);
        //health = 10;
        setAdult(true);
        myMoveSpeed = 1.7F;
        setTamed(false);
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
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
    }

    @Override
    public ResourceLocation getTexture()
    {

        switch (getType())
        {
        case 1:
            return MoCreatures.proxy.getTexture("deer.png");
        case 2:
            return MoCreatures.proxy.getTexture("deerf.png");
        case 3:
            setAdult(false);
            return MoCreatures.proxy.getTexture("deerb.png");

        default:
            return MoCreatures.proxy.getTexture("deer.png");
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
        return "mocreatures:deerdying";
    }

    @Override
    protected Item getDropItem()
    {
        return MoCreatures.fur;
    }

    @Override
    protected String getHurtSound()
    {
        return "mocreatures:deerhurt";
    }

    @Override
    protected String getLivingSound()
    {
        if (!getIsAdult())
        {
            return "mocreatures:deerbgrunt";
        }
        else
        {
            return "mocreatures:deerfgrunt";
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
                EntityLivingBase entityliving = getBoogey(10D);
                if (entityliving != null)
                {
                    setMySpeed(true);

                    MoCTools.runLikeHell(this, entityliving);

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
}