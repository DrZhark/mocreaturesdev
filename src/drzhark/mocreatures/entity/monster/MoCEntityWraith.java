package drzhark.mocreatures.entity.monster;



import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityMob;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class MoCEntityWraith extends MoCEntityMob//MoCEntityFlyerMob
{
    public MoCEntityWraith(World world)
    {
        super(world);
        isCollidedVertically = false;
        texture = MoCreatures.proxy.MODEL_TEXTURE + "wraith.png";
        setSize(1.5F, 1.5F);
        isImmuneToFire = false;
        //attackStrength = 2;
        health = 10;
        //moveSpeed = 1.3F;
    }

    public boolean d2()
    {
        return super.getCanSpawnHere();
    }

    @Override
    public boolean getCanSpawnHere()
    {
        return (MoCreatures.proxy.getFrequency(this.getEntityName()) > 0)
        //&& (worldObj.difficultySetting >= (((Integer) MoCreatures.wraithSpawnDifficulty.get()).intValue() + 1)) 
        && super.getCanSpawnHere();
    }

    @Override
    protected String getDeathSound()
    {
        return "wraithdying";
    }

    @Override
    protected int getDropItemId()
    {
        return Item.gunpowder.itemID;
    }

    @Override
    protected String getHurtSound()
    {
        return "wraithhurt";
    }

    @Override
    protected String getLivingSound()
    {
        return "wraith";
    }

    @Override
    public void onLivingUpdate()
    {
        if (!worldObj.isRemote)
        {
            if (worldObj.isDaytime())
            {
                float f = getBrightness(1.0F);
                if ((f > 0.5F) && worldObj.canBlockSeeTheSky(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)) && ((rand.nextFloat() * 30F) < ((f - 0.4F) * 2.0F)))
                {
                    //fire = 300;
                    this.setFire(15);
                }
            }
        }
        super.onLivingUpdate();
    }

    @Override
    public boolean isFlyer()
    {
        return true;
    }

    @Override
    public int getMaxHealth()
    {
        return 10;
    }

    @Override
    public float getMoveSpeed()
    {
        return 1.3F;
    }

    @Override
    public int getAttackStrength(Entity par1Entity)
    {
        if (worldObj.difficultySetting == 1) { return 2; }
        return 3;
    }
}
