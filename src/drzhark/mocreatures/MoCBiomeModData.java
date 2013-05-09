package drzhark.mocreatures;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;

public class MoCBiomeModData {

    private MoCConfiguration config;
    private Map<String, MoCBiomeData> biomeMap = new TreeMap<String, MoCBiomeData>();
    private List<String> biomes = new ArrayList<String>();
    private String modClassID;
    private String tag;

    public MoCBiomeModData(String key, MoCConfiguration config)
    {
        this.config = config;
        this.modClassID = key;
    }
    public MoCBiomeModData(String key, String tag, MoCConfiguration config)
    {
        this.config = config;
        this.modClassID = key;
        this.tag = tag;
    }

    public MoCConfiguration getModConfig()
    {
        return this.config;
    }

    public List<String> getBiomes()
    {
        return this.biomes;
    }

    public BiomeGenBase getBiome(String biomeName)
    {
        if (this.biomeMap.get(biomeName) != null)
            return this.biomeMap.get(biomeName).getBiome();
        else return null;
    }

    public void addBiome(MoCBiomeData biomeData)
    {
        this.biomeMap.put(this.tag + "|" + biomeData.getBiomeName(), biomeData);
        this.biomes.add(this.tag + "|" + biomeData.getBiomeName());
    }

    public MoCBiomeData removeBiome(MoCBiomeData biomeData)
    {
        if (biomeData != null)
        {
            this.biomes.remove(biomeData.getBiomeName());
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