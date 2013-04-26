package drzhark.mocreatures.entity.passive;


import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAmbient;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import drzhark.mocreatures.network.MoCServerPacketHandler;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class MoCEntityCrab extends MoCEntityAmbient

{
    public MoCEntityCrab(World world)
    {
        super(world);
        setSize(0.3F, 0.3F);
        health = 6;
        setEdad(50 + rand.nextInt(50));
        
    }

    


    @Override
    public int getMaxHealth()
    {
        return 6;
    }

    @Override
    public float getMoveSpeed()
    {
        return 0.15F;
    }

    @Override
    public void selectType()
    {
        if (getType() == 0)
        {
            setType(rand.nextInt(2) + 1);
        }

    }

    @Override
    public String getTexture()
    {

        switch (getType())
        {
        case 1:
            return MoCreatures.proxy.MODEL_TEXTURE + "craba.png";
        case 2:
            return MoCreatures.proxy.MODEL_TEXTURE + "crabb.png";
        
        default:
            return MoCreatures.proxy.MODEL_TEXTURE + "craba.png";
        }
    }

    

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        
        if (MoCreatures.isServer())
        {
            if (fleeingTick == 3)
            {
            	MoCServerPacketHandler.sendAnimationPacket(this.entityId, this.worldObj.provider.dimensionId, 1);
            }
        }
    }

    @Override
    protected void fall(float f)
    {
    }

   

    @Override
    public void performAnimation(int animationType)
    {
        if (animationType == 1) //fleeing animation finishes
        {
            this.fleeingTick = 0;
        }
    }

    @Override
    public boolean getCanSpawnHere()
    {
        return (MoCreatures.proxy.getFrequency(this.getEntityName()) > 0) && super.getCanSpawnHere();
    }

    

    @Override
    protected int getDropItemId()
    {
        return MoCreatures.crabmeat.itemID;
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


    @Override
    public float getSizeFactor() 
    {   
		return 0.7F * (float)getEdad() * 0.01F;
    }
    
    @Override
    public boolean canBreatheUnderwater()
    {
        return true;
    }
    
    public boolean isFleeing()
    {
    	return this.fleeingTick != 0;
    }
}
