package drzhark.mocreatures.entity.item;

import java.util.List;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.monster.MoCEntityGolem;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;



public class MoCEntityThrowableRock extends Entity {

    /** How long the fuse is */
    public int fuse;
    //private int myBlockID;
    //private Entity master;
    private int masterID;
    //private boolean held;
    //private boolean summoned;
    //private int rockType;
    //    private int behaviorType;
    public int acceleration = 100;
    private int blockMetadata;
    private double oPosX;
    private double oPosY;
    private double oPosZ;

    public MoCEntityThrowableRock(World par1World)
    {
        super(par1World);
        //this.fuse = 0;
        this.preventEntitySpawning = true;
        this.setSize(1F, 1F);
        this.yOffset = this.height / 2.0F;
    }

    //public MoCEntityThrowableRock(World par1World, Entity entitythrower, double par2, double par4, double par6, boolean flag, boolean isSummoned)//, int bMetadata)
    public MoCEntityThrowableRock(World par1World, Entity entitythrower, double par2, double par4, double par6)//, int behavior)//, int bMetadata)

    {
        this(par1World);

        this.setPosition(par2, par4, par6);
        //this.held = flag;
        //this.summoned = isSummoned;
        //this.blockMetadata = bMetadata;

        /*if (!held)// && !summoned)
        {
            float var8 = (float) (Math.random() * Math.PI * 2.0D);
            this.motionX = (double) (-((float) Math.sin((double) var8)) * 0.02F);
            this.motionY = 0.2D;
            this.motionZ = (double) (-((float) Math.cos((double) var8)) * 0.02F);
        }*/
        this.fuse = 250;
        this.prevPosX = oPosX = par2;
        this.prevPosY = oPosY = par4;
        this.prevPosZ = oPosZ = par6;
        //this.rockType = 0;
        this.setMasterID(entitythrower.entityId);
        //this.master = entitythrower;

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
        //return rockType;
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
        //System.out.println("fuse= " + fuse + "server? " + MoCreatures.isServer());
        if (MoCreatures.isServer() && this.fuse-- <= 0)
        {
            this.transformToItem();
            //this.setDead();
        }

        //held Trocks don't need to adjust its position
        if (getBehavior() == 1)//|| !MoCreatures.isServer())
        {
            //System.out.println("held");
            return;
        }

        //rock damage code (for all rock behaviors)
        if (!this.onGround) //onground!
        {
            List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));

            //List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(1D, 1D, 1D));
            for (int i = 0; i < list.size(); i++)
            {
                Entity entity1 = (Entity) list.get(i);
                if (master != null && entity1.entityId == master.entityId)
                {
                    continue;
                }
                if (entity1 instanceof MoCEntityGolem)
                {
                    continue;
                }
                if (entity1 != null && !(entity1 instanceof EntityLiving))
                {
                    continue;
                }

                if (master != null)
                {
                    entity1.attackEntityFrom(DamageSource.causeMobDamage((EntityLiving) master), 4);
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

        if (getBehavior() == 2)// && getMaster() != null)
        {

            if (master == null) { return; }

            //moves towards the master entity the bigger the number, the slower
            --acceleration;
            if (acceleration < 10)
            {
                acceleration = 10;
            }

            float tX = (float) this.posX - (float) master.posX;
            //float tY = (float) this.posY - (float) master.posY;
            float tZ = (float) this.posZ - (float) master.posZ;
            //float distToMaster = tX * tX + tY * tY + tZ * tZ;
            float distXZToMaster = tX * tX + tZ * tZ;

            //this.rotationPitch = distXZToMaster;
            //prevRenderYawOffset = renderYawOffset = distXZToMaster;//rotationYaw = prevRotationYaw;
            //System.out.println("dist XZ to master  = " + distXZToMaster);
            if (distXZToMaster < 1.0F && master instanceof MoCEntityGolem)
            {
                ((MoCEntityGolem) master).receiveRock(this.getType(), this.getMetadata());
                this.setDead();
            }

            double summonedSpeed = (double) acceleration;//20D;
            motionX = ((master.posX - this.posX) / summonedSpeed);
            motionY = ((master.posY - this.posY) / 20D + 0.15D);

            //System.out.println("motion Y after adjust " + motionY);

            /*if (motionY <= 0.1)
            {
                motionY = 0.1;
            }*/
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
                    //System.out.println("master has died");
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

    /*@Override
    public void onUpdate()
    {
        if (getBehavior() == 0)// &&) !summoned)
        {
            super.onUpdate();
        }
    }*/

    private void transformToItem()
    {
        EntityItem entityitem = new EntityItem(worldObj, posX, posY, posZ, new ItemStack(getType(), 1, getMetadata()));
        entityitem.delayBeforeCanPickup = 10;
        entityitem.age = 5500;
        worldObj.spawnEntityInWorld(entityitem);
        this.setDead();
    }

    public Block getMyBLock()
    {

        //Block block = Block.blocksList[this.getType()];
        if (this.getType() != 0)
        {
            //Block mytempBlock =  Block.blocksList[this.getType()];
            //mytempBlock.
            return Block.blocksList[this.getType()];
        }
        //System.out.println("returning null");
        return Block.blocksList[1];
        //Block rBlock = Block.dirt; 
        //return rBlock;

        //Block var11 = Block.blocksList[this.getBlockId(var8, var9, var10)];

    }

    private Entity getMaster()
    {
        List<Entity> entityList = worldObj.loadedEntityList;
        for (Entity ent : entityList)
        {
            if (ent.entityId == getMasterID()) { return ent; }
        }

        return null;
    }

}
