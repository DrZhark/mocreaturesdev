package drzhark.mocreatures;

import java.util.Map;
import java.util.TreeMap;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.WorldSavedData;
import drzhark.mocreatures.entity.IMoCTameable;

public class MoCPetMapData extends WorldSavedData
{
    private Map<String, MoCPetData> petMap = new TreeMap<String, MoCPetData>(String.CASE_INSENSITIVE_ORDER);

    public MoCPetMapData(String par1Str)
    {
        super(par1Str);
        this.markDirty();
    }

    /**
     * Get a list of pets.
     */
    public MoCPetData getPetData(String owner)
    {
        return petMap.get(owner);
    }

    public Map<String, MoCPetData> getPetMap()
    {
        return petMap;
    }

    public boolean removeOwnerPet(IMoCTameable pet, int petId)
    {
        if (this.petMap.get(pet.getOwnerName()) != null) // required since getInteger will always return 0 if no key is found
        {
           if (this.petMap.get(pet.getOwnerName()).removePet(petId))
           {
               this.markDirty();
               pet.setOwnerPetId(-1);
               return true;
           }
        }
        return false;
    }

    public void updateOwnerPet(IMoCTameable pet, NBTTagCompound petNBT)
    {
        if (pet.getOwnerPetId() == -1 || petMap.get(pet.getOwnerName()) == null)
        {
            String owner = MoCreatures.isServer() ? pet.getOwnerName() : Minecraft.getMinecraft().thePlayer.username;
            MoCPetData petData = null;
            int id = -1;
            if (petMap.containsKey(owner))
            {
                petData = petMap.get(owner);
                this.markDirty();
                id = petData.addPet(pet, petNBT);
            }
            else // create new pet data
            {
                petData = new MoCPetData(pet);
                id = petData.addPet(pet, petNBT);
                petMap.put(owner, petData);
                this.markDirty();
            }
            pet.setOwnerPetId(id);
        }
        else
        {
            // update pet data
            String owner = pet.getOwnerName();
            MoCPetData petData = MoCreatures.instance.mapData.getPetData(owner);
            NBTTagList tag = petData.getPetData().getTagList("TamedList");
            int id = -1;
            id = pet.getOwnerPetId();

            for (int i = 0; i < tag.tagCount(); i++)
            {
                NBTTagCompound nbt = (NBTTagCompound)tag.tagAt(i);
                if (nbt.getInteger("PetId") == id)
                {
                    nbt = (NBTTagCompound)petNBT.copy();
                    nbt.setInteger("ChunkX", ((Entity)pet).chunkCoordX);
                    nbt.setInteger("ChunkY", ((Entity)pet).chunkCoordY);
                    nbt.setInteger("ChunkZ", ((Entity)pet).chunkCoordZ);
                    nbt.setInteger("Dimension", ((Entity)pet).worldObj.provider.dimensionId);
                }
            }
        }
    }
    /**
     * reads in data from the NBTTagCompound into this MapDataBase
     */
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        for (Object nbtTag : par1NBTTagCompound.getTags())
        {
            NBTTagCompound nbt = (NBTTagCompound)nbtTag;
            if (!this.petMap.containsKey(nbt.getName()))
            {
                this.petMap.put(nbt.getName(), new MoCPetData(nbt));
            }
        }
    }

    /**
     * write data to NBTTagCompound from this MapDataBase, similar to Entities and TileEntities
     */
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        for (Map.Entry<String, MoCPetData> ownerEntry : petMap.entrySet())
        {
            par1NBTTagCompound.setTag(ownerEntry.getKey(), ownerEntry.getValue().getPetData());
        }
    }
}
