package drzhark.mocreatures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import drzhark.mocreatures.entity.IMoCTameable;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import drzhark.mocreatures.entity.IMoCEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraftforge.common.DimensionManager;

public class MoCPetMapData extends WorldSavedData
{
    private Map<String, MoCPetData> petMap = new HashMap();

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

    public boolean removeOwnerPet(IMoCTameable pet, int petId)
    {
        if (this.petMap.get(pet.getOwnerName()) != null) // required since getInteger will always return 0 if no key is found
        {
            System.out.println("REMOVING PET " + this + " WITH ID " + ((Entity)pet).getEntityData().getInteger("PetId"));
           if (this.petMap.get(pet.getOwnerName()).removePet(petId))
           {
               this.markDirty();
               ((Entity)pet).getEntityData().removeTag("PetId");
               return true;
           }
        }
        return false;
    }

    public void updateOwnerPet(IMoCTameable pet, NBTTagCompound petNBT)
    {
        if (!pet.getEntityData().hasKey("PetId"))
        {
            System.out.println("SAVING OWNER PET DATA FOR " + this + " with name " + pet.getName());
            String owner = MoCreatures.isServer() ? pet.getOwnerName() : Minecraft.getMinecraft().thePlayer.username;
            if (((Entity)pet).worldObj.isRemote)
                System.out.println("savePetForOwner CLIENT USERNAME = " + Minecraft.getMinecraft().thePlayer.username);
            else System.out.println("savePetForOwner SERVER USERNAME = " + pet.getOwnerName());
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
                System.out.println("STORING PET DATA FOR OWNER " + owner);
                petMap.put(owner, petData);
                this.markDirty();
            }
            pet.getEntityData().setInteger("PetId", id);
            System.out.println("customentitydata = " +pet.getEntityData());
            System.out.println("created new pet save data with id " + id + " for entity " + this);
            System.out.println("entityData pet ID = " + pet.getEntityData().getInteger("PetId"));
        }
        else
        {
            // update pet data
            System.out.println("UPDATING PET DATA...");
            String owner = pet.getOwnerName();
            MoCPetData petData = MoCreatures.instance.mapData.getPetData(owner);
            NBTTagList tag = petData.getPetData().getTagList("TamedList");
            System.out.println("writeEntityFromNBT Checking entityData tag map for " + this);
            int id = -1;
            id = pet.getEntityData().getInteger("PetId");
            System.out.println("READNBT PetId = " + id + " for " + this);
            for (int i = 0; i < tag.tagCount(); i++)
            {
                System.out.println("found tag " + tag.tagAt(i));
                NBTTagCompound nbt = (NBTTagCompound)tag.tagAt(i);
                if (nbt.getInteger("PetId") == id)
                {
                    System.out.println("UPDATING " + pet.getName() + " PETDATA with ID " + id);
                    nbt = (NBTTagCompound)petNBT.copy();
                    System.out.println("id = " + id);
                    System.out.println("name = " + petNBT.getString("Name"));
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
            System.out.println("readFromNBT found key " + nbt.getName());
            if (!this.petMap.containsKey(nbt.getName()))
            {
                System.out.println("ADDING OWNER " + nbt.getName() + " PET DATA to mocreatures.dat");
                for (Object obj : nbt.getTags())
                {
                    Map.Entry<String, NBTBase> tagEntry = (Map.Entry<String, NBTBase>)obj;
                    System.out.println("Found " + nbt.getName() + " data " + tagEntry.getKey() + " with value " + tagEntry.getValue());
                }
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
            System.out.println("Found petdata with owner key " + ownerEntry.getKey());
            par1NBTTagCompound.setTag(ownerEntry.getKey(), ownerEntry.getValue().getPetData());
        }
    }
}
