package drzhark.mocreatures;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraftforge.common.DimensionManager;
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
        this.markDirty();
        if (pet.getOwnerPetId() == -1 || petMap.get(pet.getOwnerName()) == null)
        {
            String owner = MoCreatures.isServer() ? pet.getOwnerName() : Minecraft.getMinecraft().thePlayer.getCommandSenderName();
            MoCPetData petData = null;
            int id = -1;
            if (petMap.containsKey(owner))
            {
                petData = petMap.get(owner);
                id = petData.addPet(pet, petNBT);
            }
            else // create new pet data
            {
                petData = new MoCPetData(pet);
                id = petData.addPet(pet, petNBT);
                petMap.put(owner, petData);
            }
            pet.setOwnerPetId(id);
        }
        else
        {
            // update pet data
            String owner = pet.getOwnerName();
            MoCPetData petData = this.getPetData(owner);
            NBTTagCompound rootNBT = petData.getOwnerRootNBT();
            NBTTagList tag = rootNBT.getTagList("TamedList", 10);
            int id = -1;
            id = pet.getOwnerPetId();

            for (int i = 0; i < tag.tagCount(); i++)
            {
                NBTTagCompound nbt = (NBTTagCompound)tag.getCompoundTagAt(i);
                if (nbt.getInteger("PetId") == id)
                {
                    //nbt = (NBTTagCompound)petNBT.copy(); //this breaks updates
                    double posX = Math.round(petNBT.getTagList("Pos", 6).func_150309_d(0));
                    double posY = Math.round(petNBT.getTagList("Pos", 6).func_150309_d(1));
                    double posZ = Math.round(petNBT.getTagList("Pos", 6).func_150309_d(2));
                    // Update what we need for commands
                    nbt.setTag("Pos", this.newDoubleNBTList(new double[] {posX, posY + (double)((Entity)pet).ySize, posZ}));
                    nbt.setInteger("ChunkX", ((Entity)pet).chunkCoordX);
                    nbt.setInteger("ChunkY", ((Entity)pet).chunkCoordY);
                    nbt.setInteger("ChunkZ", ((Entity)pet).chunkCoordZ);
                    nbt.setInteger("Dimension", ((Entity)pet).worldObj.provider.dimensionId);
                    nbt.setInteger("PetId", pet.getOwnerPetId());
                }
            }
        }
    }

    protected NBTTagList newDoubleNBTList(double ... par1ArrayOfDouble)
    {
        NBTTagList nbttaglist = new NBTTagList();
        double[] adouble = par1ArrayOfDouble;
        int i = par1ArrayOfDouble.length;

        for (int j = 0; j < i; ++j)
        {
            double d1 = adouble[j];
            nbttaglist.appendTag(new NBTTagDouble(d1));
        }

        return nbttaglist;
    }

    public boolean isExistingPet(String owner, IMoCTameable pet)
    {
        MoCPetData petData = MoCreatures.instance.mapData.getPetData(owner);
        if (petData != null)
        {
            NBTTagList tag = petData.getTamedList();
            for (int i = 0; i < tag.tagCount(); i++)
            {
                NBTTagCompound nbt = (NBTTagCompound)tag.getCompoundTagAt(i);
                if (nbt.getInteger("PetId") == pet.getOwnerPetId())
                {
                    // found existing pet
                    return true;
                }
            }
        }
        return false;
    }

    public void forceSave()
    {
        if (DimensionManager.getWorld(0) != null)
        {
            ISaveHandler saveHandler = DimensionManager.getWorld(0).getSaveHandler();
            if (saveHandler != null)
            {
                try
                {
                    File file1 = saveHandler.getMapFileFromName("mocreatures");

                    if (file1 != null)
                    {
                        NBTTagCompound nbttagcompound = new NBTTagCompound();
                        this.writeToNBT(nbttagcompound);
                        NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                        nbttagcompound1.setTag("data", nbttagcompound);
                        FileOutputStream fileoutputstream = new FileOutputStream(file1);
                        CompressedStreamTools.writeCompressed(nbttagcompound1, fileoutputstream);
                        fileoutputstream.close();
                    }
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                }
            }
        }
    }

    /**
     * reads in data from the NBTTagCompound into this MapDataBase
     */
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        Iterator iterator = par1NBTTagCompound.func_150296_c().iterator();
        while (iterator.hasNext())
        {
            String s = (String)iterator.next();
            NBTTagCompound nbt = (NBTTagCompound)par1NBTTagCompound.getTag(s);

            if (!this.petMap.containsKey(s))
            {
                this.petMap.put(s, new MoCPetData(nbt, s));
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
            par1NBTTagCompound.setTag(ownerEntry.getKey(), ownerEntry.getValue().getOwnerRootNBT());
        }
    }
}
