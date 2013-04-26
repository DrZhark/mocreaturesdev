package drzhark.mocreatures.entity.monster;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.network.MoCServerPacketHandler;

public class MoCEntityOgre extends MoCEntityMob{

    public int frequencyA;
    public int attackCounterLeft;
    public int attackCounterRight;
    private int movingHead;
    public boolean pendingSmashAttack;
    

    public MoCEntityOgre(World world)
    {
        super(world);
        setSize(1.9F, 4F);
        health = 35;
        isImmuneToFire = false;
        frequencyA = 30;
    }

    @Override
    public void selectType()
    {
    	if (worldObj.provider.isHellWorld)
        {
            setType(rand.nextInt(2)+3);
            health = getMaxHealth();
            this.isImmuneToFire = true;

        }else
        {
        	if (getType() == 0)
        	{
        		//setType(rand.nextInt(2)+1);
        		//health = getMaxHealth();
        		
        		int fOgreChance = MoCreatures.proxy.fireOgreChance;
                int cOgreChance = MoCreatures.proxy.caveOgreChance;
                int j = rand.nextInt(100);
                
                if (canCaveOgreSpawn() && (j >= (100 - cOgreChance)))
                {	//System.out.println("can spawn cave o");
                	setType(rand.nextInt(2)+5);
                }
                else if (j >= (100 - fOgreChance))
                {
                	setType(rand.nextInt(2)+3);
                    this.isImmuneToFire = true;
                }
                else
                {
                	setType(rand.nextInt(2)+1);
                }
	            //setType(rand.nextInt(6)+1);
        		health = getMaxHealth();
        	}
        }
    	
    }
    
   
    @Override
    public String getTexture()
    {
    	//return MoCreatures.proxy.MODEL_TEXTURE + "ogrered.png";
    	
        switch (getType())
        {
        case 1: 
        case 2:
            return MoCreatures.proxy.MODEL_TEXTURE + "ogregreen.png";
        case 3: 
        case 4:
            return MoCreatures.proxy.MODEL_TEXTURE + "ogrered.png";
        case 5: 
        case 6: 
            return MoCreatures.proxy.MODEL_TEXTURE + "ogreblue.png";
       

        default:
            return MoCreatures.proxy.MODEL_TEXTURE + "ogregreen.png";
        }

    }
    
   
    @Override
    public int getMaxHealth()
    {
    	if (getType() > 4)
    	{
        return 50;
    	}
    	return 35;
    }

    @Override
    protected void attackEntity(Entity entity, float f)
    {
        if (attackTime <= 0 && (f < 2.5F) && (entity.boundingBox.maxY > boundingBox.minY) && (entity.boundingBox.minY < boundingBox.maxY) && (worldObj.difficultySetting > 0))
        {
            attackTime = 20;
            this.attackEntityAsMob(entity);
        }
    }

    
    @Override
    public boolean attackEntityFrom(DamageSource damagesource, int i)
    {
        if (super.attackEntityFrom(damagesource, i))
        {
            Entity entity = damagesource.getEntity();
            if ((riddenByEntity == entity) || (ridingEntity == entity)) { return true; }
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

   

    public void DestroyingOgre()
    {
        if (this.deathTime > 0) { return; }
        MoCTools.DestroyBlast(this, posX, posY + 1.0D, posZ, getDestroyForce(), getOgreFire());
    }

    /*protected void dropFewItems()
    {
        int i = rand.nextInt(3) + 1;
        for(int j = 0; j < i; j++)
        {
            entityDropItem(new ItemStack(getDropItemId(), 1, 0), 0.0F);
        }

    }*/

    @Override
    protected Entity findPlayerToAttack()
    {
        float f = getBrightness(1.0F);
        if (f < 0.5F)
        {
            EntityPlayer entityplayer = worldObj.getClosestVulnerablePlayerToEntity(this, getAttackRange());
            if ((entityplayer != null) && (worldObj.difficultySetting > 0)) { return entityplayer; }
        }
        return null;
    }

    @Override
    public boolean getCanSpawnHere()
    {
    	checkSpawningBiome();
        return (MoCreatures.proxy.getFrequency(this.getEntityName()) > 0)
        && super.getCanSpawnHere();
    }

    @Override
    protected String getDeathSound()
    {
        return "ogredying";
    }

    @Override
    protected int getDropItemId()
    {
    	if (getType() < 3)
    	{
        return Block.obsidian.blockID;
    	}
    	else if (getType() < 5)
    	{
    		 boolean flag = (rand.nextInt(4)==0);
    	     if (!flag) 
    	     	{
    	        	return Block.fire.blockID;
    	        }
    	     return MoCreatures.heartfire.itemID;
    	}
    	return Item.diamond.itemID;
        
    }

    @Override
    protected String getHurtSound()
    {
        return "ogrehurt";
    }

    @Override
    protected String getLivingSound()
    {
        return "ogre";
    }

    @Override
    public int getMaxSpawnedInChunk()
    {
        return 2;
    }


    public boolean getOgreFire()
    {
    	if(getType() == 3 || getType() == 4)
    	{
    		this.isImmuneToFire = true;
    		return true;
    	}
        return false;
    }

    public float getDestroyForce()
    {
    	int t = getType();
    	if (t < 3) //green
    	{
    		return MoCreatures.proxy.ogreStrength;
    	}else if (t < 5) //red
    	{
    		return MoCreatures.proxy.fireOgreStrength;
    	}
    	return MoCreatures.proxy.caveOgreStrength;

    }

    public int getAttackRange()
    {
    	return MoCreatures.proxy.ogreAttackRange;
    }

    @Override
    public void onLivingUpdate()
    {
    	//System.out.println("ticking!");
        if (MoCreatures.isServer())
        {
/*            if ((entityToAttack != null) && (rand.nextInt(frequencyA) == 0))
            {
                setOgreAttack(true);
                attackTime = 15;
            }
            
            if ((attackTime <= 0) && (getOgreAttack()))
            {
                setOgreAttack(false);
                DestroyingOgre();
                MoCServerPacketHandler.sendExplodePacket(this.entityId, (this.worldObj.provider.dimensionId));
            }*/
            
            if ((entityToAttack != null) && (rand.nextInt(frequencyA) == 0) && attackTime == 0 && attackCounterLeft == 0 && attackCounterRight == 0)
            {
                startOgreAttack();
            }
            
            if ((attackTime <= 0) && pendingSmashAttack)
            {
            	pendingSmashAttack = false;
                DestroyingOgre();
                MoCServerPacketHandler.sendExplodePacket(this.entityId, (this.worldObj.provider.dimensionId));
            }
            
           
            if (getType() > 2)
            {
            	
            
                if (worldObj.isDaytime())
                {
                    float f = getBrightness(1.0F);
                    if ((f > 0.5F) && worldObj.canBlockSeeTheSky(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)) && ((rand.nextFloat() * 30F) < ((f - 0.4F) * 2.0F)))
                    {
                        health -= 5;
                    }
                }
            }
        }
        
        if (attackCounterLeft > 0 && ++attackCounterLeft > 30)
        {
        	attackCounterLeft = 0;
        }
        
        if (attackCounterRight > 0 && ++attackCounterRight > 30)
        {
        	attackCounterRight = 0;
        }
        
        /*if (attackCounterRight > 0 && ++attackCounterRight > 30)
        {
        	//System.out.println("attack right = " + attackCounterRight);
        	if (++attackCounterRight > 30) attackCounterRight = 0;
        }*/
        super.onLivingUpdate();
    }


    /**
     * Starts attack counters and synchronizes animations with clients
     */
    private void startOgreAttack() 
    {
    	//System.out.println("start ogre attack");
    	if (MoCreatures.isServer())
    	{
    		attackTime = 15;
    		pendingSmashAttack = true;
    		boolean leftArmW = (getType() == 2 || getType() == 4 || getType() == 6) && rand.nextInt(2) == 0;
    		
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

    @Override
    public int getAttackStrength(Entity par1Entity)
    {
        return 3;
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
    
    
    public int getMovingHead()
    {
    	if (getType() == 1 || getType() == 3 || getType() == 5) //single headed ogre
    	{
    		return 1;
    	}
    	
    	if (rand.nextInt(100) == 0)
    	{
    		movingHead = rand.nextInt(2)+2;  //randomly changes the focus head, returns 2 or 3
    	}
    	return movingHead;
    }
    
    private boolean canCaveOgreSpawn()
    {
    	//System.out.println("posY = " + posY);
    	return (!worldObj.canBlockSeeTheSky(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ))) && (posY < 50D);
    }
    
    @Override
    public boolean checkSpawningBiome()
    {

    	int fOgreChance = 50;//MoCreatures.proxy.fireOgreChance;
        int cOgreChance = 50;//MoCreatures.proxy.caveOgreChance;
        int j = rand.nextInt(100);
        if (canCaveOgreSpawn() && (j >= (100 - cOgreChance)))
        {
            setType(rand.nextInt(2)+5);
        }
        else if (j >= (100 - fOgreChance))
        {
        	setType(rand.nextInt(2)+3);
            this.isImmuneToFire = true;
        }
        else
        {
        	setType(rand.nextInt(2)+1);
        }
        //setType(rand.nextInt(6)+1);
		health = getMaxHealth();
        return true;
    }
}
