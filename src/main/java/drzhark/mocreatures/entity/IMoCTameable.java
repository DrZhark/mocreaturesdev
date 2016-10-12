package drzhark.mocreatures.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;

import java.util.UUID;

import javax.annotation.Nullable;

public interface IMoCTameable {

    boolean isRiderDisconnecting();

    void setRiderDisconnecting(boolean flag);

    void playTameEffect(boolean par1);

    UUID getOwnerId();

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

    void setOwnerId(@Nullable UUID uuid);

    void setEdad(int edad);

    void setAdult(boolean adult);

    void setOwnerPetId(int petId);

    boolean getIsTamed();

    float getPetHealth();

    void spawnHeart();

    boolean readytoBreed();
    
    String getOffspringClazz(IMoCTameable mate);

    int getOffspringTypeInt(IMoCTameable mate); 

    boolean compatibleMate(Entity mate);
    
    void setHasEaten(boolean flag);
    
    boolean getHasEaten();
    
    void setGestationTime(int time);
    
    int getGestationTime();
}
