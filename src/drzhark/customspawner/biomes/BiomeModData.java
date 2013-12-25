package drzhark.customspawner.biomes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary.Type;
import drzhark.customspawner.configuration.CMSConfiguration;

public class BiomeModData {

    private CMSConfiguration config;
    private Map<String, BiomeData> biomeMap = new TreeMap<String, BiomeData>(String.CASE_INSENSITIVE_ORDER);
    private String modClassID;
    private String tag;

    public BiomeModData(String key, CMSConfiguration config)
    {
        this.config = config;
        this.modClassID = key;
    }
    public BiomeModData(String key, String tag, CMSConfiguration config)
    {
        this.config = config;
        this.modClassID = key;
        this.tag = tag;
    }

    public CMSConfiguration getModConfig()
    {
        return this.config;
    }

    public List<String> getBiomes()
    {
        List<String> biomeList = new ArrayList<String>();
        biomeList.addAll(this.biomeMap.keySet());
        return biomeList;
    }

    public List<String> getBiomesForType(Type type)
    {
        ArrayList<String> biomes = new ArrayList<String>();
        for (BiomeData biome : biomeMap.values())
        {
            for (Type typeEntry : biome.getTypes())
            {
                if (type == typeEntry)
                    biomes.add(biome.getBiomeName());
            }
        }
        return biomes;
    }

    public boolean hasBiome(BiomeGenBase biome)
    {
        for (BiomeData biomeData : biomeMap.values())
        {
            if (biomeData.getBiome() == biome)
                return true;
        }
        return false;
    }

    public BiomeGenBase getBiome(String biomeName)
    {
        if (this.biomeMap.get(biomeName) != null)
            return this.biomeMap.get(biomeName).getBiome();
        else return null;
    }

    public void addBiome(BiomeData biomeData)
    {
        if (!this.biomeMap.containsKey(this.tag + "|" + biomeData.getBiomeName()))
        {
            this.biomeMap.put(this.tag + "|" + biomeData.getBiomeName(), biomeData);
        }
    }

    public BiomeData removeBiome(BiomeData biomeData)
    {
        if (biomeData != null)
        {
            return this.biomeMap.remove(biomeData.getBiomeID());
        }
        return null;
    }

    public String getBiomeModKey()
    {
        return this.modClassID;
    }

    public String getModTag()
    {
        return this.tag;
    }

    public void setModTag(String tag)
    {
        this.tag = tag;
    }
}