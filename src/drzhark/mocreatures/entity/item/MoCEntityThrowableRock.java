package drzhark.mocreatures.entity.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.monster.MoCEntityGolem;

public class MoCEntityThrowableRock extends Entity {

    /** How long the fuse is */
    public int fuse;
    private int masterID;
    public int acceleration = 100;
    private int blockMetadata;
    private double oPosX;
    private double oPosY;
    private double oPosZ;

    public MoCEntityThrowableRock(World par1World)
    {
        super(par1World);
        this.preventEntitySpawning = true;
        this.setSize(1F, 1F);
        this.yOffset = this.height / 2.0F;
    }

    public MoCEntityThrowableRock(World par1World, Entity entitythrower, double par2, double par4, double par6)//, int behavior)//, int bMetadata)
    {
        this(par1World);
        this.setPosition(par2, par4, par6);
        this.fuse = 250;
        this.prevPosX = oPosX = par2;
        this.prevPosY = oPosY = par4;
        this.prevPosZ = oPosZ = par6;
        this.setMasterID(entitythrower.getEntityId());
    }

    public void setMetadata(int i)
    {
        dataWatcher.updateObject(20, Integer.valueOf(i));
    }

    public int getMetadata()
    {
        return dataWatcher.getWatchableObjectInt(20);
    }

    public void setMasterID(int i)
    {
        dataWatcher.updateObject(22, Integer.valueOf(i));
    }

    public int getMasterID()
    {
        return dataWatcher.getWatchableObjectInt(22);
    }

    public void setBehavior(int i)
    {
        dataWatcher.updateObject(21, Integer.valueOf(i));
    }

    public int getBehavior()
    {
        return dataWatcher.getWatchableObjectInt(21);
    }

    public int getType()
    {
        return dataWatcher.getWatchableObjectInt(19);
    }

    public void setType(int i)
    {
        dataWatcher.updateObject(19, Integer.valueOf(i));
    }

    @Override
    protected void entityInit()
    {
        dataWatcher.addObject(19, Integer.valueOf(0)); //blockID
        dataWatcher.addObject(20, Integer.valueOf(0)); //metadata
        dataWatcher.addObject(21, Integer.valueOf(0)); //behaviorType
        dataWatcher.addObject(22, Integer.valueOf(0)); //masterID
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setInteger("TypeInt", getType());
        nbttagcompound.setInteger("Metadata", getMetadata());
        nbttagcompound.setInteger("Behavior", getBehavior());
        nbttagcompound.setInteger("MasterID", getMasterID());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        setType(nbttagcompound.getInteger("TypeInt"));
        setMetadata(nbttagcompound.getInteger("Metadata"));
        setBehavior(nbttagcompound.getInteger("Behavior"));
        setMasterID(nbttagcompound.getInteger("MasterID"));
    }

    @Override
    public boolean canBeCollidedWith()
    {
        return !this.isDead;
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onEntityUpdate()
    {
        Entity master = getMaster();
        if (MoCreatures.isServer() && this.fuse-- <= 0)
        {
            transformToItem();
        }

        //held Trocks don't need to adjust its position
        if (getBehavior() == 1)
        {
            return;
        }

        //rock damage code (for all rock behaviors)
        if (!this.onGround) //onground!
        {
            List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));

            for (int i = 0; i < list.size(); i++)
            {
                Entity entity1 = (Entity) list.get(i);
                if (master != null && entity1.getEntityId() == master.getEntityId())
                {
                    continue;
                }
                if (entity1 instanceof MoCEntityGolem)
                {
                    continue;
                }
                if (entity1 != null && !(entity1 instanceof EntityLivingBase))
                {
                    continue;
                }

                if (master != null)
                {
                    entity1.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase) master), 4);
                }
                else
                {
                    entity1.attackEntityFrom(DamageSource.generic, 4);
                }
            }
        }

        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (getBehavior() == 2)
        {
            if (master == null) { return; }

            //moves towards the master entity the bigger the number, the slower
            --acceleration;
            if (acceleration < 10)
            {
                acceleration = 10;
            }

            float tX = (float) this.posX - (float) master.posX;
            float tZ = (float) this.posZ - (float) master.posZ;
            float distXZToMaster = tX * tX + tZ * tZ;

            if (distXZToMaster < 1.0F && master instanceof MoCEntityGolem)
            {
                ((MoCEntityGolem) master).receiveRock(this.getType(), this.getMetadata());
                this.setDead();
            }

            double summonedSpeed = (double) acceleration;//20D;
            motionX = ((master.posX - this.posX) / summonedSpeed);
            motionY = ((master.posY - this.posY) / 20D + 0.15D);
            motionZ = ((master.posZ - this.posZ) / summonedSpeed);
            if (MoCreatures.isServer())
            {
                this.moveEntity(this.motionX, this.motionY, this.motionZ);
            }
            return;
        }

        if (getBehavior() == 4)// imploding / exploding rock
        {
            if (master == null)
            {
                if (MoCreatures.isServer())
                {
                    setBehavior(5);
                }
                return;
            }

            //moves towards the master entity the bigger the number, the slower
            acceleration = 10;

            float tX = (float) this.posX - (float) master.posX;
            float tZ = (float) this.posZ - (float) master.posZ;
            float distXZToMaster = tX * tX + tZ * tZ;

            double summonedSpeed = (double) acceleration;//20D;
            motionX = ((master.posX - this.posX) / summonedSpeed);
            motionY = ((master.posY - this.posY) / 20D + 0.15D);
            motionZ = ((master.posZ - this.posZ) / summonedSpeed);

            if (distXZToMaster < 2.5F && master instanceof MoCEntityGolem)
            {
                motionX = 0D;
                motionY = 0D;
                motionZ = 0D;
            }

            if (MoCreatures.isServer())
            {
                this.moveEntity(this.motionX, this.motionY, this.motionZ);
            }

            return;
        }

        if (getBehavior() == 5)// exploding rock
        {
            acceleration = 5;
            double summonedSpeed = (double) acceleration;//20D;
            motionX = ((oPosX - this.posX) / summonedSpeed);
            motionY = ((oPosY - this.posY) / 20D + 0.15D);
            motionZ = ((oPosZ - this.posZ) / summonedSpeed);
            if (MoCreatures.isServer())
            {
                this.moveEntity(this.motionX, this.motionY, this.motionZ);
            }
            setBehavior(0);
            return;
        }

        this.motionY -= 0.04D;
        if (MoCreatures.isServer())
        {
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
        }
        this.motionX *= 0.98D;
        this.motionY *= 0.98D;
        this.motionZ *= 0.98D;

        if (this.onGround)
        {
            this.motionX *= 0.699D;
            this.motionZ *= 0.699D;
            this.motionY *= -0.5D;
        }

    }

    private void transformToItem()
    {
        if ((MoCTools.mobGriefing(this.worldObj)) && (MoCreatures.proxy.golemDestroyBlocks)) // don't drop rocks if mobgriefing is set to false, prevents duping
        {
            EntityItem entityitem = new EntityItem(worldObj, posX, posY, posZ, new ItemStack(Block.getBlockById(getType()), 1, getMetadata()));
            entityitem.delayBeforeCanPickup = 10;
            entityitem.age = 5500;
            worldObj.spawnEntityInWorld(entityitem);
        }
        this.setDead();
    }

    public Block getMyBlock()
    {
        if (this.getType() != 0)
        {
            return Block.getBlockById(this.getType());
        }
        return Blocks.stone;
    }

    private Entity getMaster()
    {
        List<Entity> entityList = worldObj.loadedEntityList;
        for (Entity ent : entityList)
        {
            if (ent.getEntityId() == getMasterID()) { return ent; }
        }

        return null;
    }
}