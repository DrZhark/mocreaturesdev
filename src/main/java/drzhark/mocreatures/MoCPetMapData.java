package drzhark.mocreatures;

import com.google.common.collect.Maps;
import drzhark.mocreatures.entity.IMoCTameable;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.DimensionManager;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class MoCPetMapData extends WorldSavedData {

    private Map<UUID, MoCPetData> petMap = Maps.newHashMap();

    public MoCPetMapData(String par1Str) {
        super(par1Str);
        this.markDirty();
    }

    /**
     * Get a list of pets.
     */
    public MoCPetData getPetData(UUID ownerUniqueId) {
        return this.petMap.get(ownerUniqueId);
    }

    public Map<UUID, MoCPetData> getPetMap() {
        return this.petMap;
    }

    public boolean removeOwnerPet(IMoCTameable pet, int petId) {
        if (this.petMap.get(pet.getOwnerId()) != null) // required since getInteger will always return 0 if no key is found
        {
            if (this.petMap.get(pet.getOwnerId()).removePet(petId)) {
                this.markDirty();
                pet.setOwnerPetId(-1);
                return true;
            }
        }
        return false;
    }

    public void updateOwnerPet(IMoCTameable pet) {
        this.markDirty();
        if (pet.getOwnerPetId() == -1 || this.petMap.get(pet.getOwnerId()) == null) {
            UUID owner = MoCreatures.isServer() ? pet.getOwnerId() : Minecraft.getMinecraft().player.getUniqueID();
            MoCPetData petData = null;
            int id = -1;
            if (this.petMap.containsKey(owner)) {
                petData = this.petMap.get(owner);
                id = petData.addPet(pet);
            } else // create new pet data
            {
                petData = new MoCPetData(pet);
                id = petData.addPet(pet);
                this.petMap.put(owner, petData);
            }
            pet.setOwnerPetId(id);
        } else {
            // update pet data
            UUID owner = pet.getOwnerId();
            MoCPetData petData = this.getPetData(owner);
            NBTTagCompound rootNBT = petData.getOwnerRootNBT();
            NBTTagList tag = rootNBT.getTagList("TamedList", 10);
            int id = -1;
            id = pet.getOwnerPetId();

            for (int i = 0; i < tag.tagCount(); i++) {
                NBTTagCompound nbt = tag.getCompoundTagAt(i);
                if (nbt.getInteger("PetId") == id) {
                    // Update what we need for commands
                    nbt.setTag("Pos", this.newDoubleNBTList(new double[] {((Entity) pet).posX, ((Entity) pet).posY, ((Entity) pet).posZ}));
                    nbt.setInteger("ChunkX", ((Entity) pet).chunkCoordX);
                    nbt.setInteger("ChunkY", ((Entity) pet).chunkCoordY);
                    nbt.setInteger("ChunkZ", ((Entity) pet).chunkCoordZ);
                    nbt.setInteger("Dimension", ((Entity) pet).world.provider.getDimensionType().getId());
                    nbt.setInteger("PetId", pet.getOwnerPetId());
                }
            }
        }
    }

    protected NBTTagList newDoubleNBTList(double... par1ArrayOfDouble) {
        NBTTagList nbttaglist = new NBTTagList();
        double[] adouble = par1ArrayOfDouble;
        int i = par1ArrayOfDouble.length;

        for (int j = 0; j < i; ++j) {
            double d1 = adouble[j];
            nbttaglist.appendTag(new NBTTagDouble(d1));
        }

        return nbttaglist;
    }

    public boolean isExistingPet(UUID owner, IMoCTameable pet) {
        MoCPetData petData = MoCreatures.instance.mapData.getPetData(owner);
        if (petData != null) {
            NBTTagList tag = petData.getTamedList();
            for (int i = 0; i < tag.tagCount(); i++) {
                NBTTagCompound nbt = tag.getCompoundTagAt(i);
                if (nbt.getInteger("PetId") == pet.getOwnerPetId()) {
                    // found existing pet
                    return true;
                }
            }
        }
        return false;
    }

    public void forceSave() {
        if (DimensionManager.getWorld(0) != null) {
            ISaveHandler saveHandler = DimensionManager.getWorld(0).getSaveHandler();
            if (saveHandler != null) {
                try {
                    File file1 = saveHandler.getMapFileFromName("mocreatures");

                    if (file1 != null) {
                        NBTTagCompound nbttagcompound = new NBTTagCompound();
                        this.writeToNBT(nbttagcompound);
                        NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                        nbttagcompound1.setTag("data", nbttagcompound);
                        FileOutputStream fileoutputstream = new FileOutputStream(file1);
                        CompressedStreamTools.writeCompressed(nbttagcompound1, fileoutputstream);
                        fileoutputstream.close();
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    /**
     * reads in data from the NBTTagCompound into this MapDataBase
     */
    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
        Iterator<String> iterator = par1NBTTagCompound.getKeySet().iterator();
        while (iterator.hasNext()) {
            String s = (String) iterator.next();
            NBTTagCompound nbt = (NBTTagCompound) par1NBTTagCompound.getTag(s);
            UUID ownerUniqueId = UUID.fromString(s);

            if (!this.petMap.containsKey(ownerUniqueId)) {
                this.petMap.put(ownerUniqueId, new MoCPetData(nbt, ownerUniqueId));
            }
        }
    }

    /**
     * write data to NBTTagCompound from this MapDataBase, similar to Entities
     * and TileEntities
     * @return 
     */
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound par1NBTTagCompound) {
        for (Map.Entry<UUID, MoCPetData> ownerEntry : this.petMap.entrySet()) {
            try {
            if (this.petMap.entrySet() != null && ownerEntry.getKey()!= null) 
            {
            par1NBTTagCompound.setTag(ownerEntry.getKey().toString(), ownerEntry.getValue().getOwnerRootNBT());
        }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return par1NBTTagCompound;
    }
}
