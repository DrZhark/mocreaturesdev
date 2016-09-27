package drzhark.customspawner.biomes;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class BiomeData {

    private Class<? extends Biome> clazz;
    private boolean defined;
    private Biome biome;
    private String tag;
    private BiomeDictionary.Type[] types;

    public BiomeData(Biome biome) {
        this.clazz = biome.getClass();
        this.biome = biome;
        this.defined = false;
    }

    public BiomeData(Biome biome, String tag) {
        this.clazz = biome.getClass();
        this.biome = biome;
        this.defined = false;
        this.tag = tag;
    }

    public BiomeData(Biome biome, boolean flag) {
        this.clazz = biome.getClass();
        this.biome = biome;
        this.defined = flag;
    }

    public Class<? extends Biome> getBiomeClass() {
        return this.clazz;
    }

    public String getBiomeName() {
        return this.biome.getBiomeName();
    }

    public Biome getBiome() {
        return this.biome;
    }

    public int getBiomeID() {
        return Biome.getIdForBiome(this.biome);
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setDefined(boolean flag) {
        this.defined = flag;
    }

    public void setTypes(Type[] types) {
        this.types = types;
    }

    public Type[] getTypes() {
        return this.types;
    }

    public boolean isDefined() {
        return this.defined;
    }
}
