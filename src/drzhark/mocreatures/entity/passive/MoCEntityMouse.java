
package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;


public class MoCEntityMouse extends MoCEntityAnimal
{
    //private boolean fertile;
    //private int     micetimer;

    public MoCEntityMouse(World world)
    {
        super(world);
        setSize(0.3F, 0.3F);
        health = 4;
        //forceUpdates = true;
    }
    
    public void selectType()
    {
        if (getType() == 0)
        {
            int i = rand.nextInt(100);
            if (i <= 50)
            {
                setType(1);
            } else if (i <= 80)
            {
                setType(2);
            } else
            {
                setType(3);
            }
        }
        
        
    }
    
    
    @Override
    public String getTexture()
    {
        
        switch (getType())
        {
            case 1:
                return MoCreatures.proxy.MODEL_TEXTURE + "miceg.png";
            case 2:
                return MoCreatures.proxy.MODEL_TEXTURE + "miceb.png";
            case 3:
                return MoCreatures.proxy.MODEL_TEXTURE + "micew.png";
            
            default:
                return MoCreatures.proxy.MODEL_TEXTURE + "miceg.png";
        }
    }
    
    @Override
    public int getMaxHealth()
    {
        return 4;
    }
    
    @Override
    public float getMoveSpeed()
    {
        return 0.8F;
    }
    
    @Override
    protected void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(22, Byte.valueOf((byte) 0)); // byte IsPicked, 0 = false 1 = true
    }
    
    public boolean getIsPicked()
    {
        return (dataWatcher.getWatchableObjectByte(22) == 1);
    }
    
    public void setPicked(boolean flag)
    {
        //if (worldObj.isRemote) return;
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(22, Byte.valueOf(input));
    }

    /*private void checkFertility()
    {
        int i = 0;
        List list = worldObj.getEntitiesWithinAABB(MoCEntityMouse.class, AxisAlignedBB.getBoundingBoxFromPool(posX, posY, posZ, posX + 1.0D, posY + 1.0D, posZ + 1.0D).expand(16D, 4D, 16D));
        for(int j = 0; j < list.size(); j++)
        {
            i++;
        }

        if(i > 10)
        {
            fertile = false;
        }
    }*/

    private boolean checkNearCats()
    {
        return true;
    }

    private boolean checkNearRock()
    {
        return true;
    }

    public boolean climbing()
    {
        return !onGround && isOnLadder();
    }


    public boolean entitiesToInclude(Entity entity)
    {
        return !(entity instanceof MoCEntityMouse)
        && super.entitiesToInclude(entity);
    }
    
    @Override
    public boolean getCanSpawnHere()
    {
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(boundingBox.minY);
        int k = MathHelper.floor_double(posZ);
        return ( 
                (MoCreatures.proxy.getFrequency(this.getEntityName()) > 0) &&
                worldObj.checkNoEntityCollision(boundingBox) 
                && (worldObj.getCollidingBoundingBoxes(this, boundingBox).size() == 0) 
                && !worldObj.isAnyLiquid(boundingBox) 
                && ((worldObj.getBlockId(i, j - 1, k) == Block.cobblestone.blockID) 
                || (worldObj.getBlockId(i, j - 1, k) == Block.planks.blockID) 
                || (worldObj.getBlockId(i, j - 1, k) == Block.dirt.blockID) 
                || (worldObj.getBlockId(i, j - 1, k) == Block.stone.blockID) 
                || (worldObj.getBlockId(i, j - 1, k) == Block.grass.blockID)));
    }

    @Override
    protected String getDeathSound()
    {
        return "micedying";
    }

    @Override
    protected int getDropItemId()
    {
        return Item.seeds.itemID;
    }

    @Override
    protected String getHurtSound()
    {
        return "micehurt";
    }

    @Override
    protected String getLivingSound()
    {
        return "micegrunt";
    }

    @Override
    public int getMaxSpawnedInChunk()
    {
        return 6;
    }

   
    
    
    @Override
    public double getYOffset()
    {
        if (ridingEntity instanceof EntityPlayer && ridingEntity == MoCreatures.proxy.getPlayer() && !MoCreatures.isServer())
        {
            return (yOffset - 1.7F);
        }
            
        if ((ridingEntity instanceof EntityPlayer) && !MoCreatures.isServer())
        {
            return (yOffset - 0.1F);
        }
        else 
            return yOffset;
    }
    
    

    @Override
    public boolean interact(EntityPlayer entityplayer)
    {
        rotationYaw = entityplayer.rotationYaw;
        if (this.ridingEntity == null)
        {
            if (MoCreatures.isServer()) mountEntity(entityplayer);
            setPicked(true);
        }
        
        else
        {
            worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, ((rand.nextFloat() - rand.nextFloat()) * 0.2F) + 1.0F);
            setPicked(false);
            if (MoCreatures.isServer()) this.mountEntity(null);
        }
        motionX = entityplayer.motionX * 5D;
        motionY = (entityplayer.motionY / 2D) + 0.5D;
        motionZ = entityplayer.motionZ * 5D;

        return true;
    }

    @Override
    public boolean isOnLadder()
    {
        return isCollidedHorizontally;
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        if(!worldObj.isRemote)
        {
            if(rand.nextInt(15) == 0)
            {
                EntityLiving entityliving = getBoogey(6D);
                if(entityliving != null)
                {
                    runLikeHell(entityliving);

                }
            }
            if(!onGround && (ridingEntity != null))
            {
                rotationYaw = ridingEntity.rotationYaw;
            }
        }
    }

    private void reproduce()
    {
    }

    public boolean upsideDown()
    {
        return getIsPicked();
    }

    @Override
    public boolean updateMount() 
    {
        return true;
    }
    
    @Override
    public boolean forceUpdates() 
    {
        return true;
    }
    
}
