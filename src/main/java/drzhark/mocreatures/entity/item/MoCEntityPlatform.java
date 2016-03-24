package drzhark.mocreatures.entity.item;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.List;

public class MoCEntityPlatform extends Entity {

    private int mastersID = 0;
    private double yOffset;
    private double zOffset;
    private int mountCount;

    public MoCEntityPlatform(World world, int ID, double yOff, double zOff) {
        this(world);
        this.isImmuneToFire = true;
        this.mastersID = ID;
        this.yOffset = yOff;
        this.zOffset = zOff;
        this.setSize(0.1F, 0.1F);
    }

    public MoCEntityPlatform(World world) {
        super(world);
    }

    public boolean attackEntityFrom(Entity entity, int i) {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    public void fall(float f, float f1) {
    }

    @Override
    public void handleStatusUpdate(byte byte0) {
    }

    private EntityLivingBase getMaster() {
        List<Entity> entityList = this.worldObj.loadedEntityList;
        for (Entity ent : entityList) {
            if (ent.getEntityId() == this.mastersID && ent instanceof EntityLivingBase) {
                return (EntityLivingBase) ent;
            }
        }

        return null;
    }

    @Override
    public void onUpdate() {
        EntityLivingBase master = getMaster();
        if (master == null || this.riddenByEntity == null) {
            if (MoCreatures.isServer()) {
                this.setDead();
            }
            return;
        }

        if (this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer && ((EntityPlayer) this.riddenByEntity).isSneaking()
                && ++this.mountCount > 10) {
            this.riddenByEntity.mountEntity(null);
            this.riddenByEntity = null;
            this.setDead();

        }
        this.yOffset = master.getMountedYOffset() * 0.7D;//1.8D;
        this.rotationYaw = master.renderYawOffset;
        double newPosX = master.posX + (this.zOffset * Math.cos((MoCTools.realAngle(master.renderYawOffset - 90F)) / 57.29578F));
        double newPosZ = master.posZ + (this.zOffset * Math.sin((MoCTools.realAngle(master.renderYawOffset - 90F)) / 57.29578F));
        setLocationAndAngles(newPosX, master.posY + this.yOffset, newPosZ, master.rotationYaw, master.rotationPitch);
    }

    public void setYOffset(double yOff) {
        this.yOffset = yOff;
    }

    public void setZOffset(double zOff) {
        this.zOffset = zOff;
    }

    @Override
    protected void entityInit() {
    }

    @Override
    public void updateRiderPosition() {
        if (this.riddenByEntity != null) {
            this.riddenByEntity.setPosition(this.posX, this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ);
            this.riddenByEntity.rotationYaw = this.rotationYaw;
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    @Override
    protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    @Override
    protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
    }
}
