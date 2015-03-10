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

    /**
     * The method that does the work of actually decorating chunks
     */
    /*protected void func_150513_a(BiomeGenBase biomegenbase) {
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(this.currentWorld, this.randomGenerator, this.field_180294_c));
        this.generateOres();
        int i;
        int j;
        int k;

        boolean doGen = TerrainGen.decorate(this.currentWorld, this.randomGenerator, this.field_180294_c, SAND);
        for (i = 0; doGen && i < this.sandPerChunk2; ++i) {
            j = this.field_180294_c.getX() + this.randomGenerator.nextInt(16) + 8;
            k = this.field_180294_c.getZ() + this.randomGenerator.nextInt(16) + 8;
            this.sandGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getTopSolidOrLiquidBlock(new BlockPos(j, 0, k)));
        }

        doGen = TerrainGen.decorate(this.currentWorld, this.randomGenerator, this.field_180294_c, CLAY);
        for (i = 0; doGen && i < this.clayPerChunk; ++i) {
            j = this.field_180294_c.getX() + this.randomGenerator.nextInt(16) + 8;
            k = this.field_180294_c.getZ() + this.randomGenerator.nextInt(16) + 8;
            this.clayGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getTopSolidOrLiquidBlock(new BlockPos(j, 0, k)));
        }

        doGen = TerrainGen.decorate(this.currentWorld, this.randomGenerator, new BlockPos(this.field_180294_c.getX(), 0, this.field_180294_c.getZ()), SAND_PASS2);
        for (i = 0; doGen && i < this.sandPerChunk; ++i) {
            j = this.field_180294_c.getX() + this.randomGenerator.nextInt(16) + 8;
            k = this.field_180294_c.getZ() + this.randomGenerator.nextInt(16) + 8;
            this.gravelAsSandGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getTopSolidOrLiquidBlock(new BlockPos(j, 0, k)));
        }

        i = this.treesPerChunk;

        if (this.randomGenerator.nextInt(10) == 0) {
            ++i;
        }

        int l;
        int i1;

        doGen = TerrainGen.decorate(this.currentWorld, this.randomGenerator, this.field_180294_c, TREE);
        for (j = 0; doGen && j < i; ++j) {
            k = this.field_180294_c.getX() + this.randomGenerator.nextInt(16) + 8;
            l = this.field_180294_c.getZ() + this.randomGenerator.nextInt(16) + 8;
            i1 = this.currentWorld.getHeightValue(k, l);
            WorldGenAbstractTree worldgenabstracttree = biomegenbase.genBigTreeChance(this.randomGenerator);
            worldgenabstracttree.setScale(1.0D, 1.0D, 1.0D);

            if (worldgenabstracttree.generate(this.currentWorld, this.randomGenerator, k, i1, l)) {
                worldgenabstracttree.func_150524_b(this.currentWorld, this.randomGenerator, k, i1, l);
            }
        }

        doGen = TerrainGen.decorate(this.currentWorld, this.randomGenerator, this.field_180294_c, BIG_SHROOM);
        for (j = 0; doGen && j < this.bigMushroomsPerChunk; ++j) {
            k = this.field_180294_c.getX() + this.randomGenerator.nextInt(16) + 8;
            l = this.field_180294_c.getZ() + this.randomGenerator.nextInt(16) + 8;
            this.bigMushroomGen.generate(this.currentWorld, this.randomGenerator, k, this.currentWorld.getHeightValue(k, l), l);
        }

        doGen = TerrainGen.decorate(this.currentWorld, this.randomGenerator, this.field_180294_c, FLOWERS);
        for (j = 0; doGen && j < this.flowersPerChunk; ++j) {
            k = this.field_180294_c.getX() + this.randomGenerator.nextInt(16) + 8;
            l = this.field_180294_c.getZ() + this.randomGenerator.nextInt(16) + 8;
            if (this.currentWorld.getHeightValue(k, l) == 0) {
                continue;
            }

            i1 = this.randomGenerator.nextInt(this.currentWorld.getHeightValue(k, l) + 32);
            String s = biomegenbase.func_150572_a(this.randomGenerator, k, i1, l);
            BlockFlower blockflower = BlockFlower.func_149857_e(s);

            if (blockflower.getMaterial() != Material.air) {
                this.yellowFlowerGen.func_150550_a(blockflower, BlockFlower.func_149856_f(s));
                this.yellowFlowerGen.generate(this.currentWorld, this.randomGenerator, k, i1, l);
            }
        }

        doGen = TerrainGen.decorate(this.currentWorld, this.randomGenerator, this.field_180294_c.getX(), this.field_180294_c.getZ(), GRASS);
        for (j = 0; doGen && j < this.grassPerChunk; ++j) {
            k = this.field_180294_c.getX() + this.randomGenerator.nextInt(16) + 8;
            l = this.field_180294_c.getZ() + this.randomGenerator.nextInt(16) + 8;
            if (this.currentWorld.getHeightValue(k, l) == 0) {
                continue;
            }

            i1 = this.currentWorld.getHeightValue(k, l);
            WorldGenerator worldgenerator = biomegenbase.getRandomWorldGenForGrass(this.randomGenerator);
            worldgenerator.generate(this.currentWorld, this.randomGenerator, k, i1, l);

        }

        doGen = TerrainGen.decorate(this.currentWorld, this.randomGenerator, this.field_180294_c.getX(), this.field_180294_c.getZ(), DEAD_BUSH);
        for (j = 0; doGen && j < this.deadBushPerChunk; ++j) {
            k = this.field_180294_c.getX() + this.randomGenerator.nextInt(16) + 8;
            l = this.field_180294_c.getZ() + this.randomGenerator.nextInt(16) + 8;
            if (this.currentWorld.getHeightValue(k, l) == 0) {
                continue;
            }

            i1 = this.randomGenerator.nextInt(this.currentWorld.getHeightValue(k, l) * 2);
            (new WorldGenDeadBush(Blocks.deadbush)).generate(this.currentWorld, this.randomGenerator, k, i1, l);
        }

        doGen = TerrainGen.decorate(this.currentWorld, this.randomGenerator, this.field_180294_c.getX(), this.field_180294_c.getZ(), LILYPAD);
        for (j = 0; doGen && j < this.waterlilyPerChunk; ++j) {
            k = this.field_180294_c.getX() + this.randomGenerator.nextInt(16) + 8;
            l = this.field_180294_c.getZ() + this.randomGenerator.nextInt(16) + 8;
            if (this.currentWorld.getHeightValue(k, l) == 0) {
                continue;
            }

            for (i1 = this.randomGenerator.nextInt(this.currentWorld.getHeightValue(k, l) * 2); i1 > 0 && this.currentWorld.isAirBlock(k, i1 - 1, l); --i1) {
                ;
            }

            this.waterlilyGen.generate(this.currentWorld, this.randomGenerator, k, i1, l);
        }

        doGen = TerrainGen.decorate(this.currentWorld, this.randomGenerator, this.field_180294_c.getX(), this.field_180294_c.getZ(), SHROOM);
        for (j = 0; doGen && j < this.mushroomsPerChunk; ++j) {
            if (this.randomGenerator.nextInt(4) == 0) {
                k = this.field_180294_c.getX() + this.randomGenerator.nextInt(16) + 8;
                l = this.field_180294_c.getZ() + this.randomGenerator.nextInt(16) + 8;
                i1 = this.currentWorld.getHeightValue(k, l);
                this.mushroomBrownGen.generate(this.currentWorld, this.randomGenerator, k, i1, l);
            }

            if (this.randomGenerator.nextInt(8) == 0) {
                k = this.field_180294_c.getX() + this.randomGenerator.nextInt(16) + 8;
                l = this.field_180294_c.getZ() + this.randomGenerator.nextInt(16) + 8;
                if (this.currentWorld.getHeightValue(k, l) == 0) {
                    continue;
                }

                i1 = this.randomGenerator.nextInt(this.currentWorld.getHeightValue(k, l) * 2);
                this.mushroomRedGen.generate(this.currentWorld, this.randomGenerator, k, i1, l);
            }
        }

        if (doGen && this.randomGenerator.nextInt(4) == 0) {
            j = this.field_180294_c.getX() + this.randomGenerator.nextInt(16) + 8;
            k = this.field_180294_c.getZ() + this.randomGenerator.nextInt(16) + 8;

            l = this.randomGenerator.nextInt(1 + this.currentWorld.getHeightValue(j, k) * 2);
            this.mushroomBrownGen.generate(this.currentWorld, this.randomGenerator, j, l, k);
        }

        if (doGen && this.randomGenerator.nextInt(8) == 0) {
            j = this.field_180294_c.getX() + this.randomGenerator.nextInt(16) + 8;
            k = this.field_180294_c.getZ() + this.randomGenerator.nextInt(16) + 8;
            l = this.randomGenerator.nextInt(1 + this.currentWorld.getHeightValue(j, k) * 2);
            this.mushroomRedGen.generate(this.currentWorld, this.randomGenerator, j, l, k);
        }

        doGen = TerrainGen.decorate(this.currentWorld, this.randomGenerator, this.field_180294_c.getX(), this.field_180294_c.getZ(), REED);
        for (j = 0; doGen && j < this.reedsPerChunk; ++j) {
            k = this.field_180294_c.getX() + this.randomGenerator.nextInt(16) + 8;
            l = this.field_180294_c.getZ() + this.randomGenerator.nextInt(16) + 8;
            if (this.currentWorld.getHeightValue(k, l) == 0) {
                continue;
            }

            i1 = this.randomGenerator.nextInt(this.currentWorld.getHeightValue(k, l) * 2);
            this.reedGen.generate(this.currentWorld, this.randomGenerator, k, i1, l);
        }

        for (j = 0; doGen && j < 10; ++j) {
            k = this.field_180294_c.getX() + this.randomGenerator.nextInt(16) + 8;
            l = this.field_180294_c.getZ() + this.randomGenerator.nextInt(16) + 8;
            if (this.currentWorld.getHeightValue(k, l) == 0) {
                continue;
            }

            i1 = this.randomGenerator.nextInt(this.currentWorld.getHeightValue(k, l) * 2);
            this.reedGen.generate(this.currentWorld, this.randomGenerator, k, i1, l);
        }

        doGen = TerrainGen.decorate(this.currentWorld, this.randomGenerator, this.field_180294_c.getX(), this.field_180294_c.getZ(), PUMPKIN);
        if (doGen && this.randomGenerator.nextInt(32) == 0) {
            j = this.field_180294_c.getX() + this.randomGenerator.nextInt(16) + 8;
            k = this.field_180294_c.getZ() + this.randomGenerator.nextInt(16) + 8;
            l = this.randomGenerator.nextInt(1 + this.currentWorld.getHeightValue(j, k) * 2);
            (new WorldGenPumpkin()).generate(this.currentWorld, this.randomGenerator, j, l, k);
        }

        doGen = TerrainGen.decorate(this.currentWorld, this.randomGenerator, this.field_180294_c.getX(), this.field_180294_c.getZ(), CACTUS);
        for (j = 0; doGen && j < this.cactiPerChunk; ++j) {
            k = this.field_180294_c.getX() + this.randomGenerator.nextInt(16) + 8;
            l = this.field_180294_c.getZ() + this.randomGenerator.nextInt(16) + 8;
            if (this.currentWorld.getHeightValue(k, l) == 0) {
                continue;
            }

            i1 = this.randomGenerator.nextInt(this.currentWorld.getHeightValue(k, l) * 2);
            this.cactusGen.generate(this.currentWorld, this.randomGenerator, k, i1, l);
        }

        doGen = TerrainGen.decorate(this.currentWorld, this.randomGenerator, this.field_180294_c, LAKE);
        if (doGen && this.generateLakes) {
            for (j = 0; j < 50; ++j) {
                k = this.field_180294_c.getX() + this.randomGenerator.nextInt(16) + 8;
                l = this.randomGenerator.nextInt(this.randomGenerator.nextInt(248) + 8);
                i1 = this.field_180294_c.getZ() + this.randomGenerator.nextInt(16) + 8;
                (new WorldGenLiquids(Blocks.flowing_water)).generate(this.currentWorld, this.randomGenerator, k, l, i1);
            }

            for (j = 0; j < 20; ++j) {
                k = this.field_180294_c.getX() + this.randomGenerator.nextInt(16) + 8;
                l = this.randomGenerator.nextInt(this.randomGenerator.nextInt(this.randomGenerator.nextInt(240) + 8) + 8);
                i1 = this.field_180294_c.getZ() + this.randomGenerator.nextInt(16) + 8;
                (new WorldGenLiquids(Blocks.flowing_lava)).generate(this.currentWorld, this.randomGenerator, k, l, i1);
            }
        }

        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(this.currentWorld, this.randomGenerator, this.field_180294_c));
    }*/
}
