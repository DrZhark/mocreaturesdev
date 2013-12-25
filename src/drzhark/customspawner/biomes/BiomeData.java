package drzhark.customspawner.biomes;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class BiomeData {

    private Class<? extends BiomeGenBase> clazz;
    private boolean defined;
    private BiomeGenBase biome;
    private String tag;
    private BiomeDictionary.Type[] types;

    public BiomeData(BiomeGenBase biome)
    {
        this.clazz = biome.getClass();
        this.biome = biome;
        this.defined = false;
    }

    public BiomeData(BiomeGenBase biome, String tag)
    {
        this.clazz = biome.getClass();
        this.biome = biome;
        this.defined = false;
        this.tag = tag;
    }

    public BiomeData(BiomeGenBase biome, boolean flag)
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

    public void setTypes(Type[] types)
    {
        this.types = types;
    }

    public Type[] getTypes()
    {
        return this.types;
    }

    public boolean isDefined()
    {
        return this.defined;
    }
}