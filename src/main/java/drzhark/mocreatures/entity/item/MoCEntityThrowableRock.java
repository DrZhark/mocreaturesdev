package drzhark.mocreatures.entity.item;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.monster.MoCEntityGolem;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.List;

public class MoCEntityThrowableRock extends Entity {

    public int rockTimer;
    public int acceleration = 100;
    private double oPosX;
    private double oPosY;
    private double oPosZ;

    public MoCEntityThrowableRock(World par1World) {
        super(par1World);
        this.preventEntitySpawning = true;
        this.setSize(1F, 1F);
        //this.yOffset = this.height / 2.0F;
    }

    public MoCEntityThrowableRock(World par1World, Entity entitythrower, double par2, double par4, double par6)//, int behavior)//, int bMetadata)
    {
        this(par1World);
        this.setPosition(par2, par4, par6);
        this.rockTimer = 250;
        this.prevPosX = this.oPosX = par2;
        this.prevPosY = this.oPosY = par4;
        this.prevPosZ = this.oPosZ = par6;
        this.setMasterID(entitythrower.getEntityId());
    }

    public void setState(IBlockState state) {
        this.dataWatcher.updateObject(23, Short.valueOf((short) (Block.getStateId(state) & 65535)));
    }

    public IBlockState getState() {
        return Block.getStateById(this.dataWatcher.getWatchableObjectShort(23) & 65535);
    }

    public void setMasterID(int i) {
        this.dataWatcher.updateObject(22, Integer.valueOf(i));
    }

    public int getMasterID() {
        return this.dataWatcher.getWatchableObjectInt(22);
    }

    public void setBehavior(int i) {
        this.dataWatcher.updateObject(21, Integer.valueOf(i));
    }

    public int getBehavior() {
        return this.dataWatcher.getWatchableObjectInt(21);
    }

    @Override
    protected void entityInit() {
        this.dataWatcher.addObject(21, Integer.valueOf(0)); //behaviorType
        this.dataWatcher.addObject(22, Integer.valueOf(0)); //masterID
        this.dataWatcher.addObject(23, new Short((short) 0)); //tRock State

    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        IBlockState iblockstate = this.getState();
        nbttagcompound.setInteger("Behavior", getBehavior());
        nbttagcompound.setInteger("MasterID", getMasterID());
        nbttagcompound.setShort("BlockID", (short) Block.getIdFromBlock(iblockstate.getBlock()));
        nbttagcompound.setShort("BlockMetadata", (short) iblockstate.getBlock().getMetaFromState(iblockstate));
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        setBehavior(nbttagcompound.getInteger("Behavior"));
        setMasterID(nbttagcompound.getInteger("MasterID"));
        IBlockState iblockstate;
        iblockstate = Block.getBlockById(nbttagcompound.getShort("BlockID")).getStateFromMeta(nbttagcompound.getShort("BlockMetadata") & 65535);
        this.setState(iblockstate);
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onEntityUpdate() {
        Entity master = getMaster();
        if (this.rockTimer-- <= -50 && getBehavior() == 0) {
            transformToItem();
        }

        /*if (getBehavior() == 0) {
            System.out.println("Zero Rock, Server? =" + MoCreatures.isServer() + " age " + rockTimer + " at " + this);
        }*/
        //held Trocks don't need to adjust its position
        if (getBehavior() == 1) {
            return;
        }

        //rock damage code (for all rock behaviors)
        if (!this.onGround) {
            List<Entity> list =
                    this.worldObj.getEntitiesWithinAABBExcludingEntity(this,
                            this.getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));

            for (int i = 0; i < list.size(); i++) {
                Entity entity1 = (Entity) list.get(i);
                if (master != null && entity1.getEntityId() == master.getEntityId()) {
                    continue;
                }
                if (entity1 instanceof MoCEntityGolem) {
                    continue;
                }
                if (entity1 != null && !(entity1 instanceof EntityLivingBase)) {
                    continue;
                }

                if (master != null) {
                    entity1.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase) master), 4);
                } else {
                    entity1.attackEntityFrom(DamageSource.generic, 4);
                }
            }
        }

        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (getBehavior() == 2) {
            if (master == null) {
                setBehavior(0);
                this.rockTimer = -50;
                return;
            }

            //moves towards the master entity the bigger the number, the slower
            --this.acceleration;
            if (this.acceleration < 10) {
                this.acceleration = 10;
            }

            float tX = (float) this.posX - (float) master.posX;
            float tZ = (float) this.posZ - (float) master.posZ;
            float distXZToMaster = tX * tX + tZ * tZ;

            if (distXZToMaster < 1.5F && master instanceof MoCEntityGolem) {
                ((MoCEntityGolem) master).receiveRock(this.getState());
                this.setBehavior(0);
                this.setDead();
            }

            double summonedSpeed = this.acceleration;//20D;
            this.motionX = ((master.posX - this.posX) / summonedSpeed);
            this.motionY = ((master.posY - this.posY) / 20D + 0.15D);
            this.motionZ = ((master.posZ - this.posZ) / summonedSpeed);
            if (MoCreatures.isServer()) {
                this.moveEntity(this.motionX, this.motionY, this.motionZ);
            }
            return;
        }

        if (getBehavior() == 4)// imploding / exploding rock
        {
            if (master == null) {
                if (MoCreatures.isServer()) {
                    setBehavior(5);
                }
                return;
            }

            //moves towards the master entity the bigger the number, the slower
            this.acceleration = 10;

            float tX = (float) this.posX - (float) master.posX;
            float tZ = (float) this.posZ - (float) master.posZ;
            float distXZToMaster = tX * tX + tZ * tZ;

            double summonedSpeed = this.acceleration;//20D;
            this.motionX = ((master.posX - this.posX) / summonedSpeed);
            this.motionY = ((master.posY - this.posY) / 20D + 0.15D);
            this.motionZ = ((master.posZ - this.posZ) / summonedSpeed);

            if (distXZToMaster < 2.5F && master instanceof MoCEntityGolem) {
                this.motionX = 0D;
                this.motionY = 0D;
                this.motionZ = 0D;
            }

            if (MoCreatures.isServer()) {
                this.moveEntity(this.motionX, this.motionY, this.motionZ);
            }

            return;
        }

        if (getBehavior() == 5)// exploding rock
        {
            this.acceleration = 5;
            double summonedSpeed = this.acceleration;//20D;
            this.motionX = ((this.oPosX - this.posX) / summonedSpeed);
            this.motionY = ((this.oPosY - this.posY) / 20D + 0.15D);
            this.motionZ = ((this.oPosZ - this.posZ) / summonedSpeed);
            if (MoCreatures.isServer()) {
                this.moveEntity(this.motionX, this.motionY, this.motionZ);
            }
            setBehavior(0);
            return;
        }

        this.motionY -= 0.04D;
        if (MoCreatures.isServer()) {
            this.moveEntity(this.motionX, this.motionY, this.motionZ);
        }
        this.motionX *= 0.98D;
        this.motionY *= 0.98D;
        this.motionZ *= 0.98D;

        if (this.onGround) {
            this.motionX *= 0.699D;
            this.motionZ *= 0.699D;
            this.motionY *= -0.5D;
        }

    }

    private void transformToItem() {
        if ((MoCTools.mobGriefing(this.worldObj)) && (MoCreatures.proxy.golemDestroyBlocks) && MoCreatures.isServer()) // don't drop rocks if mobgriefing is set to false, prevents duping
        {
            EntityItem entityitem =
                    new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(this.getState().getBlock(), 1, this.getState()
                            .getBlock().getMetaFromState(this.getState())));
            entityitem.setPickupDelay(10);
            entityitem.setAgeToCreativeDespawnTime();
            this.worldObj.spawnEntityInWorld(entityitem);
        }
        this.setDead();
    }

    public Block getMyBlock() {
        if (this.getState() != null) {
            return this.getState().getBlock();
        }
        return Blocks.stone;
    }

    private Entity getMaster() {
        List<Entity> entityList = this.worldObj.loadedEntityList;
        for (Entity ent : entityList) {
            if (ent.getEntityId() == getMasterID()) {
                return ent;
            }
        }

        return null;
    }
}
