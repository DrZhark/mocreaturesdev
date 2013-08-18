package drzhark.mocreatures.entity;

import net.minecraft.nbt.NBTTagCompound;

public interface IMoCTameable {

    public void playTameEffect(boolean par1);

    public String getOwnerName();

    public String getEntityName();

    public String getName();

    public void setName(String name);

    public void setTamed(boolean par1);

    public void setDead();

    public int getType();

    public void writeEntityToNBT(NBTTagCompound nbttagcompound);

    public void readEntityFromNBT(NBTTagCompound nbttagcompound);

    public int getEdad();

    public int getOwnerPetId();

    public boolean getIsAdult();

    public void setType(int creatureType);

    public void setOwner(String username);

    public void setEdad(int edad);

    public void setAdult(boolean adult);

    public void setOwnerPetId(int petId);
}