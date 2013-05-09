package drzhark.mocreatures;

import net.minecraft.world.biome.BiomeGenBase;

public class MoCBiomeData {

    private Class<? extends BiomeGenBase> clazz;
    private boolean defined;
    private BiomeGenBase biome;
    private String tag;

    public MoCBiomeData(BiomeGenBase biome)
    {
        this.clazz = biome.getClass();
        this.biome = biome;
        this.defined = false;
    }

    public MoCBiomeData(BiomeGenBase biome, String tag)
    {
        this.clazz = biome.getClass();
        this.biome = biome;
        this.defined = false;
        this.tag = tag;
    }

    public MoCBiomeData(BiomeGenBase biome, boolean flag)
    {
        this.clazz = biome.getClass();
        this.biome = biome;
        this.defined = flag;
    }

    public Class<? extends BiomeGenBase> getBiomeClass()
    {
        return this.clazz;
    }
 
    public String getBiomeName()
    {
        return this.biome.biomeName;
    }

    public BiomeGenBase getBiome()
    {
        return biome;
    }

    public int getBiomeID()
    {
        return this.biome.biomeID;
    }

    public String getTag()
    {
        return tag;
    }

    public void setTag(String tag)
    {
        this.tag = tag;
    }

    public void setDefined(boolean flag)
    {
        this.defined = flag;
    }

    public boolean isDefined()
    {
        return this.defined;
    }
}