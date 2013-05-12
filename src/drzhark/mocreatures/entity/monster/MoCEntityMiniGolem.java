package drzhark.mocreatures.entity.monster;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.entity.item.MoCEntityThrowableRock;
import drzhark.mocreatures.network.MoCServerPacketHandler;

public class MoCEntityMiniGolem extends MoCEntityMob {

    public int tcounter;
    public MoCEntityThrowableRock tempRock;
    
    public MoCEntityMiniGolem(World world)
    {
        super(world);
        texture = MoCreatures.proxy.MODEL_TEXTURE + "minigolem.png";
        setSize(1.0F, 1.0F);
        health = getMaxHealth();
        

    }

   

    @Override
    protected void entityInit()
    {
        super.entityInit();
        
        dataWatcher.addObject(22, Byte.valueOf((byte) 0)); // angry 0 = false, 1 = true
        dataWatcher.addObject(23, Byte.valueOf((byte) 0)); // hasRock 0 = false, 1 = true

    }

    public boolean getIsAngry()
    {
        return (dataWatcher.getWatchableObjectByte(22) == 1);
    }

    public void setIsAngry(boolean flag)
    {
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(22, Byte.valueOf(input));
    }

    public boolean getHasRock()
    {
        return (dataWatcher.getWatchableObjectByte(23) == 1);
    }

    public void setHasRock(boolean flag)
    {
    	byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(23, Byte.valueOf(input));
    }
    

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (MoCreatures.isServer())
        {
        	if (entityToAttack == null )
        	{
        		if (getIsAngry()) setIsAngry(false);
        	}
        	else
        	{
        		if (!getIsAngry()) setIsAngry(true);
        	}
        	
        	if (this.worldObj.isDaytime())
            {
                float var1 = this.getBrightness(1.0F);
        	
                if (var1 > 0.5F && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) && this.rand.nextFloat() * 30.0F < (var1 - 0.4F) * 2.0F)
                {
                    this.setFire(8);
                }
            }
        	
        	if (getIsAngry() && entityToAttack != null)
        	{
        		if (!getHasRock() && rand.nextInt(30) == 0)
        		{
        			acquireTRock();
        		}
        		
        		if (getHasRock()) 
        		{
        			attackWithTRock();
        		}
        	}
          
        }

        
    }

    
    protected void acquireTRock()
    {
    	int[] tRockInfo = MoCTools.destroyRandomBlockWithMetadata(this, 3D);
        if (tRockInfo[0] == -1)
        {
        	tcounter = 1;
        	setHasRock(false);
        	return;
        }
        //creates a dummy Trock on top of it
        MoCEntityThrowableRock trock = new MoCEntityThrowableRock(this.worldObj, this, this.posX, this.posY + 2.0D, this.posZ);//, true, false);
        this.worldObj.spawnEntityInWorld(trock);
        
        trock.setType(tRockInfo[0]);
        trock.setMetadata(tRockInfo[1]);
        trock.setBehavior(1);
        this.tempRock = trock;
        setHasRock(true);
    }
   
    @Override
    protected boolean isMovementCeased()
    {
        return getHasRock() && entityToAttack != null;
    }

    /**
     * 
     */
    protected void attackWithTRock()
    {
        this.tcounter++;
       
        if (this.tcounter < 50)
        {
            //maintains position of Trock above head
            this.tempRock.posX = this.posX;
            this.tempRock.posY = (this.posY + 1.0D);
            this.tempRock.posZ = this.posZ;
        }

        if (this.tcounter >= 50)
        {
            //throws a newly spawned Trock and destroys the held Trock
            if (entityToAttack != null && this.getDistanceToEntity(entityToAttack) < 48F)
            {
            	//System.out.println("distance = " + this.getDistanceToEntity(entityToAttack));
                ThrowStone(entityToAttack, this.tempRock.getType(), this.tempRock.getMetadata());
            }

            this.tempRock.setDead();
            setHasRock(false);
            this.tcounter = 0;
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
    protected Entity findPlayerToAttack()
    {
        EntityPlayer var1 = this.worldObj.getClosestVulnerablePlayerToEntity(this, 16.0D);
       /* if (var1 != null && this.canEntityBeSeen(var1) )
        {
        	setIsAngry(true);
        	return var1;
        }

        setIsAngry(false);
        return null;*/
        return var1 != null && this.canEntityBeSeen(var1) ? var1 : null;
    }

    

    /**
     * Stretches the model to that size
     */
    @Override
    public float getSizeFactor()
    {
        return 1.0F;
    }

    /**
     * Throws stone at entity
     * 
     * @param targetEntity
     * @param rocktype
     * @param metadata
     */
    protected void ThrowStone(Entity targetEntity, int rocktype, int metadata)
    {
        ThrowStone((int) targetEntity.posX, (int) targetEntity.posY, (int) targetEntity.posZ, rocktype, metadata);
    }

    /**
     * Throws stone at X,Y,Z coordinates
     * 
     * @param X
     * @param Y
     * @param Z
     * @param rocktype
     * @param metadata
     */
    protected void ThrowStone(int X, int Y, int Z, int rocktype, int metadata)
    {
        MoCEntityThrowableRock etrock = new MoCEntityThrowableRock(this.worldObj, this, this.posX, this.posY + 3.0D, this.posZ);//, false, false);
        this.worldObj.spawnEntityInWorld(etrock);
        etrock.setType(rocktype);
        etrock.setMetadata(metadata);
        etrock.setBehavior(0);
        etrock.motionX = ((X - this.posX) / 20.0D);
        etrock.motionY = ((Y - this.posY) / 20.0D + 0.5D);
        etrock.motionZ = ((Z - this.posZ) / 20.0D);
    }

    /**
     * Plays step sound at given x, y, z for the entity
     */
    @Override
    protected void playStepSound(int par1, int par2, int par3, int par4)
    {
        this.playSound("minigolemwalk", 1.0F, 1.0F);
    }

    @Override
    protected String getDeathSound()
    {
        return "golemgrunt";
    }

    /*@Override
    protected int getDropItemId()
    {
        return MoCreatures.fur.itemID;
    }
    */
    @Override
    protected String getHurtSound()
    {
        return "golemgrunt";
    }

    @Override
    protected String getLivingSound()
    {
        return null;
    }

    @Override
    public int getMaxHealth()
    {
        return 15;
    }
}
