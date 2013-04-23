package drzhark.mocreatures.entity.passive;

import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAmbient;

public class MoCEntityMaggot extends MoCEntityAmbient

{
    public MoCEntityMaggot(World world)
    {
        super(world);
        setSize(0.2F, 0.2F);
        health = 2;
        texture = MoCreatures.proxy.MODEL_TEXTURE + "maggot.png";
    }

   
    @Override
    public int getMaxHealth()
    {
        return 2;
    }

    @Override
    public float getMoveSpeed()
    {
        return 0.15F;
    }

     

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (!worldObj.isRemote)
        {

        }
    }

    @Override
    protected void fall(float f)
    {
    }

   

    @Override
    public boolean getCanSpawnHere()
    {
        checkSpawningBiome();
        return (MoCreatures.proxy.getFrequency(this.getEntityName()) > 0) && super.getCanSpawnHere();
    }

    
    @Override
    protected int getDropItemId()
    {
        return Item.slimeBall.itemID;
    }

    @Override
    public boolean isOnLadder()
    {
        return isCollidedHorizontally;
    }

    public boolean climbing()
    {
        return !onGround && isOnLadder();
    }

    @Override
    protected void jump()
    {
    }
    
}
