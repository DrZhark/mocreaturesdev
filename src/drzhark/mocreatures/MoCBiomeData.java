package drzhark.mocreatures;

import net.minecraft.world.biome.BiomeGenBase;

public class MoCBiomeData {

    private Class<? extends BiomeGenBase> clazz;
    private boolean defined;
    private BiomeGenBase biome;

    public MoCBiomeData(Class<? extends BiomeGenBase> biomeClass, BiomeGenBase biome)
    {
        this.clazz = biomeClass;
        this.biome = biome;
        this.defined = false;
    }

    public MoCBiomeData(Class<? extends BiomeGenBase> biomeClass, BiomeGenBase biome, boolean flag)
    {
        this.clazz = biomeClass;
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

    public void setDefined(boolean flag)
    {
        this.defined = flag;
    }

    public boolean isDefined()
    {
        return this.defined;
    }
}