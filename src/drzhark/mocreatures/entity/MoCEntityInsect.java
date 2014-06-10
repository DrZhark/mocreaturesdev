package drzhark.mocreatures.entity;

import java.util.List;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;

public class MoCEntityInsect extends MoCEntityAmbient {

    private int climbCounter;

    public MoCEntityInsect(World world)
    {
        super(world);
        setSize(0.2F, 0.2F);
        //health = 2;
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(6.0D);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(22, Byte.valueOf((byte) 0)); // isFlying - 0 false 1 true
    }

    @Override
    public boolean isFlyer()
    {
        return getIsFlying();
    }

    @Override
    public boolean isFlyingAlone()
    {
        return getIsFlying();
    }

    public boolean getIsFlying()
    {
        return (dataWatcher.getWatchableObjectByte(22) == 1);
    }

    public void setIsFlying(boolean flag)
    {
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(22, Byte.valueOf(input));
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (getIsFlying())
        {
            moveSpeed = getFlyingSpeed();
        }
        else
        {
            moveSpeed = getWalkingSpeed();
        }

        if (MoCreatures.isServer())
        {
            if (isOnLadder() && !onGround)
            {
                MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 1), new TargetPoint(this.worldObj.provider.dimensionId, this.posX, this.posY, this.posZ, 64));
            }
            
            if (!getIsFlying() && rand.nextInt(getFlyingFreq()) == 0)
            {
                List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(4D, 4D, 4D));
                for (int i = 0; i < list.size(); i++)
                {
                    Entity entity1 = (Entity) list.get(i);
                    if (!(entity1 instanceof EntityLivingBase))
                    {
                        continue;
                    }
                    if (((EntityLivingBase) entity1).width >= 0.4F && ((EntityLivingBase) entity1).height >= 0.4F && canEntityBeSeen(entity1))
                    {
                        this.motionY += 0.3D;
                        setIsFlying(true);
                    }
                }
            }

            if (isAttractedToLight() && rand.nextInt(50) == 0)
            {
                int ai[] = MoCTools.ReturnNearestBlockCoord(this, Blocks.torch, 8D);
                if (ai[0] > -1000)
                {
                    PathEntity pathentity = worldObj.getEntityPathToXYZ(this,ai[0], ai[1], ai[2], 24F, true, false, false, true);
                    if (pathentity != null)
                    {
                        this.setPathToEntity(pathentity);
                    }
                }
            }
 
            //this makes the flying insect move all the time in the air
            if (getIsFlying() && !hasPath() && !isMovementCeased() && entityToAttack == null)
            {
                updateWanderPath();
            }

        }
        else // client stuff
        {
            if (climbCounter > 0 && ++climbCounter > 8)
            {
                climbCounter = 0;
            }
        }
    }

    /**
     * Is this insect attracted to light?
     * @return
     */
    public boolean isAttractedToLight() 
    {
        return false;
    }

    @Override
    public void performAnimation(int animationType)
    {
        if (animationType == 1) //climbing animation
        {
            climbCounter = 1;
        }
        
    }

    @Override
    protected void fall(float f)
    {
    }

    @Override
    public boolean getCanSpawnHere()
    {
        return super.getCanSpawnHereAnimal()  && super.getCanSpawnHereCreature();
    }

    @Override
    public float getMoveSpeed()
    {
        if (getIsFlying())
        {
            return getFlyingSpeed();
        }
        else
        {
            return getWalkingSpeed();
        }
    }

    protected float getFlyingSpeed()
    {
        return 0.7F;
    }

    protected float getWalkingSpeed()
    {
        return 0.2F;
    }

    @Override
    public float getSizeFactor()
    {
        return 1.0F;
    }

    @Override
    public int getMaxSpawnedInChunk()
    {
        return 4;
    }

    @Override
    public boolean isOnLadder()
    {
        return isCollidedHorizontally;
    }

    public boolean climbing()
    {
        return (climbCounter != 0);
    }

    @Override
    protected void jump()
    {
    }

    @Override
    protected boolean canTriggerWalking()
    {
        return false;
    }

    protected int getFlyingFreq()
    {
        return 20;
    }

    @Override
    public int rollRotationOffset() 
    {
        return 0;
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.ARTHROPOD;
    }
}