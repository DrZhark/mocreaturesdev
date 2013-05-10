package drzhark.mocreatures.entity.monster;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.network.MoCServerPacketHandler;

public class MoCEntitySilverSkeleton extends MoCEntityMob{

	public MoCEntitySilverSkeleton(World world)
	{
		super(world);
		texture = MoCreatures.proxy.MODEL_TEXTURE + "silverskeleton.png";
		setSize(0.9F, 1.4F);
		health = getMaxHealth();
	}
	
	public int attackCounterLeft;
    public int attackCounterRight;
    
    
	@Override
	public void onLivingUpdate()
	{
		if (MoCreatures.isServer())
		{
			if (entityToAttack == null )
        	{
				setSprinting(false);
        	}
        	else
        	{
        		setSprinting(true);
        	}
			
			if (this.worldObj.isDaytime())
            {
                float var1 = this.getBrightness(1.0F);

                if (var1 > 0.5F && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) && this.rand.nextFloat() * 30.0F < (var1 - 0.4F) * 2.0F)
                {
                    this.setFire(8);
                }
            }
			
			
		}

		if (attackCounterLeft > 0 && ++attackCounterLeft > 10)
        {
        	attackCounterLeft = 0;
        }
        
        if (attackCounterRight > 0 && ++attackCounterRight > 10)
        {
        	attackCounterRight = 0;
        }
		
		super.onLivingUpdate();
	}

	@Override
	protected int getDropItemId()
	{
		if (rand.nextInt(10) == 0)
		{
			return MoCreatures.swordsilver.itemID;
		}
		return Item.bone.itemID;

	}
	
	
	 @Override
	    public void performAnimation(int animationType)
	    {
	        
	        if (animationType == 1) //left arm
	        {
	        	attackCounterLeft = 1;
	        }
	        if (animationType == 2) //right arm
	        {
	        	attackCounterRight = 1;
	        }
	    }

	 
	    /**
	     * Starts attack counters and synchronizes animations with clients
	     */
	    private void startAttackAnimation() 
	    {
	    	if (MoCreatures.isServer())
	    	{
	    		boolean leftArmW = rand.nextInt(2) == 0;
	    		
	    		if (leftArmW)
	    		{
	    			//System.out.println("left arm attack");
	    			attackCounterLeft = 1;
	    			MoCServerPacketHandler.sendAnimationPacket(this.entityId, this.worldObj.provider.dimensionId, 1);
	    		}else
	    		{
	    			//System.out.println("right arm attack");
	    			attackCounterRight = 1;
	    			MoCServerPacketHandler.sendAnimationPacket(this.entityId, this.worldObj.provider.dimensionId, 2);
	    		}
	    	}
		}
	    
	   
	    
	    protected void attackEntity(Entity par1Entity, float par2)
	    {
	        if (this.attackTime <= 0 && par2 < 2.0F && par1Entity.boundingBox.maxY > this.boundingBox.minY && par1Entity.boundingBox.minY < this.boundingBox.maxY)
	        {
	            this.attackTime = 20;
	            startAttackAnimation();
	            this.attackEntityAsMob(par1Entity);
	        }
	    }
	public float getMoveSpeed()
	{
		if (isSprinting()) return 1.2F;
		return 0.8F;
	}

	@Override
	public int getMaxHealth()
	{
		return 25;
	}

	@Override
	protected String getDeathSound()
	{
		return "silverskeletondying";
	}

	@Override
	protected String getHurtSound()
	{
		return "silverskeletonhurt";
	}

	@Override
	protected String getLivingSound()
	{
		return "silverskeleton";
	}
	
	/**
     * Get this Entity's EnumCreatureAttribute
     */
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }
}
