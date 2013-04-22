// todo freeze for some time if close to flower
// attack player if player attacks hive?
// hive block (honey, bee spawner)

package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityInsect;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class MoCEntityBee extends MoCEntityInsect

{
    private int soundCount;
    private boolean upset;

    public MoCEntityBee(World world)
    {
        super(world);
        //setSize(0.2F, 0.2F);
        health = 4;
        texture = MoCreatures.proxy.MODEL_TEXTURE + "bee.png";
    }

    /*@Override
    public boolean isFlyer()
    {
        return true;
    }
    
    @Override
    public boolean isFlyerInsect()
    {
        return true;
    }*/

    //private int textCounter;

    /**
     * Overridden for the dynamic nightmare texture.
     */
    /*
    public String getTexture()
    {
            //if (rand.nextInt(2)== 0) textCounter++;
            textCounter++;
            if (textCounter < 10) textCounter = 10;
            if (textCounter > 29) textCounter = 10;
            String textNumber = "" + textCounter;
            textNumber = textNumber.substring(0,1);
            return MoCreatures.proxy.MODEL_TEXTURE + "bee" + textNumber + ".png";

    }*/

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        /*EntityPlayer ep2 = worldObj.getClosestPlayerToEntity(this, 3D);
        if (ep2 != null)
        {
            //System.out.println("Am I mounting player? = " + this.ridingEntity != null + " is this server? = " + MoCreatures.isServer());
        }*/
        if (MoCreatures.isServer())
        {
            EntityPlayer ep = worldObj.getClosestPlayerToEntity(this, 5D);
            if (ep != null && getIsFlying() && --soundCount == -1)
            {
                MoCTools.playCustomSound(this, getMySound(), this.worldObj);
                soundCount = 20;
            }

            if (getIsFlying() && rand.nextInt(500) == 0)
            {
                setIsFlying(false);
            }
        }

        /*if (!hasPath() && !isMovementCeased() && entityToAttack == null)
        {
            updateWanderPath();
        }*/
    }

    private String getMySound()
    {
        if (entityToAttack != null) { return "beeupset"; }
        return "bee";
    }

    @Override
    protected float getFlyingSpeed()
    {
        return 0.5F;
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

    @Override
    public int getTalkInterval()
    {
        return 2000;
    }

    @Override
    protected float getSoundVolume()
    {
        return 0.1F;
    }

    @Override
    protected String getDeathSound()
    {
        return null;
    }

    @Override
    protected String getHurtSound()
    {
        return null;
    }

    @Override
    protected String getLivingSound()
    {
        //System.out.println("sound playing");
        return null;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, int i)
    {
        if (super.attackEntityFrom(damagesource, i))
        {
            Entity entity = damagesource.getEntity();
            if ((entity != this) && (worldObj.difficultySetting > 0))
            {
                entityToAttack = entity;
            }
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    protected void attackEntity(Entity entity, float f)
    {

        if (this.attackTime <= 0 && (f < 2.0D) && (entity.boundingBox.maxY > boundingBox.minY) && (entity.boundingBox.minY < boundingBox.maxY))
        {
            attackTime = 20;
            entity.attackEntityFrom(DamageSource.causeMobDamage(this), 2);
        }
    }

    @Override
    public boolean isMyFavoriteFood(ItemStack par1ItemStack)
    {
        return par1ItemStack != null && (par1ItemStack.itemID == Block.plantRed.blockID || par1ItemStack.itemID == Block.plantYellow.blockID);
    }
}
