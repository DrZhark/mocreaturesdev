package drzhark.mocreatures;

import drzhark.mocreatures.entity.IMoCTameable;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.UUID;

public class MoCPetData {

    private NBTTagCompound ownerData = new NBTTagCompound();
    private NBTTagList tamedList = new NBTTagList();
    private BitSet IDMap = new BitSet(Long.SIZE << 4);
    @SuppressWarnings("unused")
    private final UUID ownerUniqueId;
    private ArrayList<Integer> usedPetIds = new ArrayList<Integer>();

    public MoCPetData(IMoCTameable pet) {
        this.ownerData.setTag("TamedList", this.tamedList);
        this.ownerUniqueId = MoCreatures.isServer() ? pet.getOwnerId() : Minecraft.getMinecraft().thePlayer.getUniqueID();
    }

    public MoCPetData(NBTTagCompound nbt, UUID owner) {
        this.ownerData = nbt;
        this.tamedList = nbt.getTagList("TamedList", 10);
        this.ownerUniqueId = owner;
        this.loadPetDataMap(nbt.getCompoundTag("PetIdData"));
    }

    public int addPet(IMoCTameable pet) {
        BlockPos coords = new BlockPos(((Entity) pet).chunkCoordX, ((Entity) pet).chunkCoordY, ((Entity) pet).chunkCoordZ);
        NBTTagCompound petNBT = MoCTools.getEntityData((Entity) pet);
        if (this.tamedList != null) {
            int id = getNextFreePetId();
            petNBT.setInteger("PetId", id);
            NBTTagCompound petData = (NBTTagCompound) petNBT.copy();
            petData.setInteger("ChunkX", coords.getX());
            petData.setInteger("ChunkY", coords.getY());
            petData.setInteger("ChunkZ", coords.getZ());
            petData.setInteger("Dimension", ((Entity) pet).worldObj.provider.getDimensionType().getId());
            this.tamedList.appendTag(petData);
            this.ownerData.setTag("PetIdData", savePetDataMap());
            return id;
        } else {
            return -1;
        }
    }

    public boolean removePet(int id) {
        for (int i = 0; i < this.tamedList.tagCount(); i++) {
            NBTTagCompound nbt = this.tamedList.getCompoundTagAt(i);
            if (nbt.hasKey("PetId") && nbt.getInteger("PetId") == id) {
                this.tamedList.removeTag(i);
                this.usedPetIds.remove(new Integer(id));
                this.IDMap.clear(id); // clear bit so it can be reused again
                if (this.usedPetIds.size() == 0) {
                    this.IDMap.clear(); // fixes bug with ID 0 not able to be used again
                }
                this.ownerData.setTag("PetIdData", savePetDataMap());
                return true;
            }
        }
        return false;
    }

    public NBTTagCompound getPetData(int id) {
        if (this.tamedList != null) {
            for (int i = 0; i < this.tamedList.tagCount(); i++) {
                NBTTagCompound nbt = this.tamedList.getCompoundTagAt(i);
                if (nbt.hasKey("PetId") && nbt.getInteger("PetId") == id) {
                    return nbt;
                }
            }
        }
        return null;
    }

    public NBTTagCompound getOwnerRootNBT() {
        return this.ownerData;
    }

    public NBTTagList getTamedList() {
        return this.tamedList;
    }

    public String getOwner() {
        if (this.ownerData != null) {
            return this.ownerData.getString("Owner");
        } else {
            return null;
        }
    }

    public boolean getInAmulet(int petId) {
        NBTTagCompound petData = getPetData(petId);
        if (petData != null) {
            return petData.getBoolean("InAmulet");
        }
        return false;
    }

    public void setInAmulet(int petId, boolean flag) {
        NBTTagCompound petData = getPetData(petId);
        if (petData != null) {
            petData.setBoolean("InAmulet", flag);
        }
    }

    /**
     * Return the next free pet ID.
     *
     * @return the next free pet ID
     */
    public int getNextFreePetId() {
        int next = 0;
        while (true) {
            next = this.IDMap.nextClearBit(next);
            if (this.usedPetIds.contains(new Integer(next))) {
                this.IDMap.set(next);
            } else {
                this.usedPetIds.add(new Integer(next));
                return next;
            }
        }
    }

    public NBTTagCompound savePetDataMap() {
        int[] data = new int[(this.IDMap.length() + Integer.SIZE - 1) / Integer.SIZE];
        NBTTagCompound dataMap = new NBTTagCompound();
        for (int i = 0; i < data.length; i++) {
            int val = 0;
            for (int j = 0; j < Integer.SIZE; j++) {
                val |= this.IDMap.get(i * Integer.SIZE + j) ? (1 << j) : 0;
            }
            data[i] = val;
        }
        dataMap.setIntArray("PetIdArray", data);
        return dataMap;
    }

    public void loadPetDataMap(NBTTagCompound compoundTag) {
        if (compoundTag == null) {
            this.IDMap.clear();
        } else {
            int[] intArray = compoundTag.getIntArray("PetIdArray");
            for (int i = 0; i < intArray.length; i++) {
                for (int j = 0; j < Integer.SIZE; j++) {
                    this.IDMap.set(i * Integer.SIZE + j, (intArray[i] & (1 << j)) != 0);
                }
            }
            // populate our usedPetIds
            int next = 0;
            while (true) {
                next = this.IDMap.nextClearBit(next);
                if (!this.usedPetIds.contains(new Integer(next))) {
                    this.usedPetIds.add(new Integer(next));
                } else {
                    break;
                }
            }
        }
    }
}
