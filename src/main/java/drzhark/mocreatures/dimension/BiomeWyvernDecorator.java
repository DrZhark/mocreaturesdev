package drzhark.mocreatures.dimension;

import net.minecraft.world.biome.BiomeDecorator;

public class BiomeWyvernDecorator extends BiomeDecorator {

    public BiomeWyvernDecorator() {
        this.generateLakes = true;
        this.grassPerChunk = 1;
        this.flowersPerChunk = -999;
        this.mushroomsPerChunk = 20;
        this.treesPerChunk = 4;
    }

}
