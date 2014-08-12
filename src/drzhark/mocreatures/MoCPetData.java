package drzhark.mocreatures;

import java.util.ArrayList;
import java.util.BitSet;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChunkCoordinates;
import drzhark.mocreatures.entity.IMoCTameable;

public class MoCPetData {

    private NBTTagCompound ownerData = new NBTTagCompound();
    private NBTTagList tamedList = new NBTTagList();
    private BitSet IDMap = new BitSet(Long.SIZE << 4);
    private final String ownerName;
    private ArrayList<Integer> usedPetIds = new ArrayList();

    public MoCPetData(IMoCTameable pet)
    {
        ownerData.setTag("TamedList", tamedList);
        ownerName = MoCreatures.isServer() ? pet.getOwnerName() : Minecraft.getMinecraft().thePlayer.getCommandSenderName();
        //ownerData.setName("PetData");
    }

    public MoCPetData(NBTTagCompound nbt, String owner)
    {
        ownerData = nbt;
        tamedList = nbt.getTagList("TamedList", 10);
        ownerName = owner;
        this.loadPetDataMap(nbt.getCompoundTag("PetIdData"));
    }

    public int addPet(IMoCTameable pet, NBTTagCompound petNBT)
    {
        ChunkCoordinates coords = new ChunkCoordinates(((Entity)pet).chunkCoordX, ((Entity)pet).chunkCoordY, ((Entity)pet).chunkCoordZ);
        if (tamedList != null)
        {
            int id = getNextFreePetId();
            petNBT.setInteger("PetId", id);
            NBTTagCompound petData = (NBTTagCompound)petNBT.copy();
            petData.setInteger("ChunkX", coords.posX);
            petData.setInteger("ChunkY", coords.posY);
            petData.setInteger("ChunkZ", coords.posZ);
            petData.setInteger("Dimension", ((Entity)pet).worldObj.provider.dimensionId);
            tamedList.appendTag(petData);
            ownerData.setTag("PetIdData", savePetDataMap());
            return id;
        }
        else
        {
            return -1;
        }
    }

    public boolean removePet(int id)
    {
        for (int i = 0; i < this.tamedList.tagCount(); i++)
        {
            NBTTagCompound nbt = (NBTTagCompound)this.tamedList.getCompoundTagAt(i);
            if (nbt.hasKey("PetId") && nbt.getInteger("PetId") == id)
            {
                this.tamedList.removeTag(i);
                this.usedPetIds.remove(new Integer(id));
                this.IDMap.clear(id); // clear bit so it can be reused again
                if (this.usedPetIds.size() == 0)
                    this.IDMap.clear(); // fixes bug with ID 0 not able to be used again
                ownerData.setTag("PetIdData", savePetDataMap());
                return true;
            }
        }
        return false;
    }

    public NBTTagCompound getPetData(int id)
    {
        if (tamedList != null)
        {
            for (int i = 0; i < this.tamedList.tagCount(); i++)
            {
                NBTTagCompound nbt = (NBTTagCompound)this.tamedList.getCompoundTagAt(i);
                if (nbt.hasKey("PetId") && nbt.getInteger("PetId") == id)
                {
                    return nbt;
                }
            }
        }
        return null;
    }

    public NBTTagCompound getOwnerRootNBT()
    {
        return ownerData;
    }

    public NBTTagList getTamedList()
    {
        return tamedList;
    }

    public String getOwner()
    {
        if (ownerData != null)
        {
            return ownerData.getString("Owner");
        }
        else return null;
    }

    public boolean getInAmulet(int petId)
    {
        NBTTagCompound petData = getPetData(petId);
        if (petData != null)
        {
            return petData.getBoolean("InAmulet");
        }
        return false;
    }

    public void setInAmulet(int petId, boolean flag)
    {
        NBTTagCompound petData = getPetData(petId);
        if (petData != null)
        {
            petData.setBoolean("InAmulet", flag);
        }
    }

    /**
     * Return the next free pet ID.
     * @return the next free pet ID
     */
    public int getNextFreePetId() {
        int next = 0;
        while (true)
        {
            next = IDMap.nextClearBit(next);
            if (usedPetIds.contains(new Integer(next)))
            {
                IDMap.set(next);
            }
            else
            {
                usedPetIds.add(new Integer(next));
                return next;
            }
        }
    }

    public NBTTagCompound savePetDataMap()
    {
        int[] data = new int[(IDMap.length() + Integer.SIZE - 1 )/ Integer.SIZE];
        NBTTagCompound dataMap = new NBTTagCompound();
        for (int i = 0; i < data.length; i++)
        {
            int val = 0;
            for (int j = 0; j < Integer.SIZE; j++)
            {
                val |= IDMap.get(i * Integer.SIZE + j) ? (1 << j) : 0;
            }
            data[i] = val;
        }
        dataMap.setIntArray("PetIdArray", data);
        return dataMap;
    }

    public void loadPetDataMap(NBTTagCompound compoundTag)
    {
        if (compoundTag == null)
        {
            IDMap.clear();
        }
        else
        {
            int[] intArray = compoundTag.getIntArray("PetIdArray");
            for (int i = 0; i < intArray.length; i++)
            {
                for (int j = 0; j < Integer.SIZE; j++)
                {
                    IDMap.set(i * Integer.SIZE + j, (intArray[i] & (1 << j)) != 0);
                }
            }
            // populate our usedPetIds
            int next = 0;
            while (true)
            {
                next = IDMap.nextClearBit(next);
                if (!usedPetIds.contains(new Integer(next)))
                {
                    usedPetIds.add(new Integer(next));
                }
                else break;
            }
        }
    }
}
