package drzhark.mocreatures.entity;

import net.minecraft.nbt.NBTTagCompound;

public interface IMoCTameable {

    public void playTameEffect(boolean par1);

    public String getOwnerName();

    public String getEntityName();

    public String getName();

    public void setTamed(boolean par1);

    public void setDead();

    public int getType();

    public NBTTagCompound getEntityData();

    public void writeEntityToNBT(NBTTagCompound nbttagcompound);

    public void readEntityFromNBT(NBTTagCompound nbttagcompound);
}