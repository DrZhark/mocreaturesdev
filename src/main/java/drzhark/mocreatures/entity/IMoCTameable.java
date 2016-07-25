package drzhark.mocreatures.entity;

import net.minecraft.nbt.NBTTagCompound;

public interface IMoCTameable {

    boolean isRiderDisconnecting();

    void setRiderDisconnecting(boolean flag);

    void playTameEffect(boolean par1);

    String getOwnerName();

    String getPetName();

    void setPetName(String name);

    void setTamed(boolean par1);

    void setDead();

    int getType();

    void writeEntityToNBT(NBTTagCompound nbttagcompound);

    void readEntityFromNBT(NBTTagCompound nbttagcompound);

    int getEdad();

    int getOwnerPetId();

    boolean getIsAdult();

    void setType(int creatureType);

    void setOwner(String username);

    void setEdad(int edad);

    void setAdult(boolean adult);

    void setOwnerPetId(int petId);

    boolean getIsTamed();

    float getPetHealth();

    void spawnHeart();

    boolean readytoBreed();
}
