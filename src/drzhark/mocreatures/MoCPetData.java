package drzhark.mocreatures;

import java.awt.List;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Hashtable;
import java.util.Iterator;

import drzhark.mocreatures.entity.IMoCTameable;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.storage.SaveHandler;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.DimensionManager;

public class MoCPetData {

    private NBTTagCompound ownerData;
    private NBTTagList tamedList;
    private static BitSet IDMap = new BitSet(Long.SIZE << 4);
    private final String ownerName;
    private static ArrayList<Integer> usedPetIds = new ArrayList();

    public MoCPetData(IMoCTameable pet)
    {
        ownerData = new NBTTagCompound();
        tamedList = new NBTTagList();
        ownerData.setTag("TamedList", tamedList);
        ownerName = MoCreatures.isServer() ? pet.getOwnerName() : Minecraft.getMinecraft().thePlayer.username;
        ownerData.setName("PetData");
    }

    public MoCPetData(NBTTagCompound nbt)
    {
        ownerData = nbt;
        tamedList = nbt.getTagList("TamedList");
        ownerName = nbt.getName();
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
            System.out.println("id = " + id);
            System.out.println("name = " + petNBT.getString("Name"));
            petData.setInteger("ChunkX", coords.posX);
            petData.setInteger("ChunkY", coords.posY);
            petData.setInteger("ChunkZ", coords.posZ);
            petData.setInteger("Dimension", ((Entity)pet).worldObj.provider.dimensionId);
            tamedList.appendTag(petData);
            ownerData.setCompoundTag("PetIdData", savePetDataMap());
            return id;
        }
        else
        {
            System.out.println("ERROR COULD NOT SAVE PET " + pet);
            return -1;
        }
    }

    public boolean removePet(int id)
    {
        for (int i = 0; i < this.tamedList.tagCount(); i++)
        {
            NBTTagCompound nbt = (NBTTagCompound)this.tamedList.tagAt(i);
            if (nbt.hasKey("PetId") && nbt.getInteger("PetId") == id)
            {
                System.out.println("FOUND MATCH PET " + nbt.getString("Name") + " for owner " + ownerName);
                this.tamedList.removeTag(i);
                System.out.println("REMOVED!!!");
                this.usedPetIds.remove(new Integer(id));
                this.IDMap.clear(id); // clear bit so it can be reused again
                if (this.usedPetIds.size() == 0)
                    this.IDMap.clear(); // fixes bug with ID 0 not able to be used again
                ownerData.setCompoundTag("PetIdData", savePetDataMap());
                return true;
            }
        }
        return false;
    }

    public NBTTagCompound getPetData()
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

    /**
     * Return the next free pet ID.
     * @return the next free pet ID
     */
    public static int getNextFreePetId() {
        int next = 0;
        while (true)
        {
            next = IDMap.nextClearBit(next);
            if (usedPetIds.contains(new Integer(next)))
            {
                System.out.println("getNextFreePetId ID " + next + " already exists in usedPetIds, checking next...");
                IDMap.set(next);
            }
            else
            {
                System.out.println("getNextFreePetId FOUND AVAILABLE ID " + next);
                usedPetIds.add(new Integer(next));
                return next;
            }
        }
    }

    public static NBTTagCompound savePetDataMap()
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

    public static void loadPetDataMap(NBTTagCompound compoundTag)
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
