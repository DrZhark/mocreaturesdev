package drzhark.mocreatures.dimension;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.NoiseGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.fml.common.eventhandler.Event.Result;

import java.util.List;
import java.util.Random;

public class ChunkProviderWyvernLair implements IChunkProvider {

    private Random RNGa;
    private NoiseGeneratorOctaves noiseGen1;
    private NoiseGeneratorOctaves noiseGen2;
    private NoiseGeneratorOctaves noiseGen3;
    public NoiseGeneratorOctaves noiseGen4;
    public NoiseGeneratorOctaves noiseGen5;
    private World worldObj;
    private double[] densities;
    private int chunkX = 0;
    private int chunkZ = 0;

    /** The biomes that are used to generate the chunk */
    private BiomeGenBase[] biomesForGeneration;
    double[] noiseData1;
    double[] noiseData2;
    double[] noiseData3;
    double[] noiseData4;
    double[] noiseData5;
    //int[][] field_73203_h = new int[32][32];
    byte[] metadat = new byte[32768];

    public ChunkProviderWyvernLair(World par1World, long par2) {
        this.worldObj = par1World;
        this.RNGa = new Random(par2);
        this.noiseGen1 = new NoiseGeneratorOctaves(this.RNGa, 16);
        this.noiseGen2 = new NoiseGeneratorOctaves(this.RNGa, 16);
        this.noiseGen3 = new NoiseGeneratorOctaves(this.RNGa, 8);
        this.noiseGen4 = new NoiseGeneratorOctaves(this.RNGa, 10);
        this.noiseGen5 = new NoiseGeneratorOctaves(this.RNGa, 16);

        NoiseGenerator[] noiseGens = {this.noiseGen1, this.noiseGen2, this.noiseGen3, this.noiseGen4, this.noiseGen5};
        noiseGens = TerrainGen.getModdedNoiseGenerators(par1World, this.RNGa, noiseGens);
        this.noiseGen1 = (NoiseGeneratorOctaves) noiseGens[0];
        this.noiseGen2 = (NoiseGeneratorOctaves) noiseGens[1];
        this.noiseGen3 = (NoiseGeneratorOctaves) noiseGens[2];
        this.noiseGen4 = (NoiseGeneratorOctaves) noiseGens[3];
        this.noiseGen5 = (NoiseGeneratorOctaves) noiseGens[4];
    }

    /**
     * Calls ChunkProvider constructor, adding metadata that will be saved to
     * every terrain block generated.
     *
     * @param par1World
     * @param par2
     * @param metadata
     */
    public ChunkProviderWyvernLair(World par1World, long par2, int metadata) {
        this(par1World, par2);

        for (int i = 0; i < 32768; i++) {
            this.metadat[i] = (byte) metadata;
        }
    }

    @Override
    public boolean func_177460_a(IChunkProvider p_177460_1_, Chunk p_177460_2_, int p_177460_3_, int p_177460_4_) {
        return false;
    }

    public void generateBaseBlocks(int p_180520_1_, int p_180520_2_, ChunkPrimer p_180520_3_) {
        byte b0 = 2;
        int k = b0 + 1;
        byte b1 = 33;
        int l = b0 + 1;
        this.densities = this.initializeNoiseField(this.densities, p_180520_1_ * b0, 0, p_180520_2_ * b0, k, b1, l);

        for (int i1 = 0; i1 < b0; ++i1) {
            for (int j1 = 0; j1 < b0; ++j1) {
                for (int k1 = 0; k1 < 32; ++k1) {
                    double d0 = 0.25D;
                    double d1 = this.densities[((i1 + 0) * l + j1 + 0) * b1 + k1 + 0];
                    double d2 = this.densities[((i1 + 0) * l + j1 + 1) * b1 + k1 + 0];
                    double d3 = this.densities[((i1 + 1) * l + j1 + 0) * b1 + k1 + 0];
                    double d4 = this.densities[((i1 + 1) * l + j1 + 1) * b1 + k1 + 0];
                    double d5 = (this.densities[((i1 + 0) * l + j1 + 0) * b1 + k1 + 1] - d1) * d0;
                    double d6 = (this.densities[((i1 + 0) * l + j1 + 1) * b1 + k1 + 1] - d2) * d0;
                    double d7 = (this.densities[((i1 + 1) * l + j1 + 0) * b1 + k1 + 1] - d3) * d0;
                    double d8 = (this.densities[((i1 + 1) * l + j1 + 1) * b1 + k1 + 1] - d4) * d0;

                    for (int l1 = 0; l1 < 4; ++l1) {
                        double d9 = 0.125D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;

                        for (int i2 = 0; i2 < 8; ++i2) {
                            double d14 = 0.125D;
                            double d15 = d10;
                            double d16 = (d11 - d10) * d14;

                            for (int j2 = 0; j2 < 8; ++j2) {
                                IBlockState iblockstate = null;

                                if (d15 > 0.0D) {
                                    iblockstate = MoCreatures.mocStone.getDefaultState(); //TODO Add proper metadata
                                }

                                int k2 = i2 + i1 * 8;
                                int l2 = l1 + k1 * 4;
                                int i3 = j2 + j1 * 8;
                                p_180520_3_.setBlockState(k2, l2, i3, iblockstate);
                                d15 += d16;
                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }
    }

    /**
     * Replaces the stone that was placed in with blocks that match the biome
     * @param biomesForGeneration2
     * @param z
     * @param x
     */
    public void insertBiomeBlocks(int x, int z, ChunkPrimer chunkPrimer, BiomeGenBase[] biomesForGeneration2) {
        ChunkProviderEvent.ReplaceBiomeBlocks event = new ChunkProviderEvent.ReplaceBiomeBlocks(this, x, z, chunkPrimer, this.worldObj);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.getResult() == Result.DENY) {
            return;
        }
        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                byte b0 = 5;
                int k = -1;

                BiomeGenBase biome = biomesForGeneration2[j + i * 16];
                IBlockState iblockStateTopBlock = biome.topBlock;//MoCreatures.mocStone.getDefaultState();
                IBlockState iblockStateFillerBlock = biome.fillerBlock;//MoCreatures.mocStone.getDefaultState();

                for (int l = 127; l >= 0; --l) {
                    IBlockState iblockstate2 = chunkPrimer.getBlockState(i, l, j);

                    if (iblockstate2.getBlock().getMaterial() == Material.air) {
                        k = -1;
                    } else if (iblockstate2.getBlock() == MoCreatures.mocStone.getDefaultState().getBlock()) {
                        if (k == -1) {
                            k = b0;
                            if (l >= 0) {
                                chunkPrimer.setBlockState(i, l, j, iblockStateTopBlock);
                            } else {
                                chunkPrimer.setBlockState(i, l, j, iblockStateFillerBlock);
                            }
                        } else if (k > 0) {
                            --k;
                            chunkPrimer.setBlockState(i, l, j, iblockStateFillerBlock);
                        }
                    }
                }
            }
        }
    }

    /**
     * loads or generates the chunk at the chunk location specified
     */
    public Chunk loadChunk(int par1, int par2) {
        return this.provideChunk(par1, par2);
    }

    /**
     * Will return back a chunk, if it doesn't exist and its not a MP client it
     * will generates all the blocks for the specified chunk from the map seed
     * and chunk seed
     */
    @Override
    public Chunk provideChunk(int x, int z) {
        this.chunkX = x;
        this.chunkZ = z;
        this.RNGa.setSeed(x * 341873128712L + z * 132897987541L);
        ChunkPrimer chunkprimer = new ChunkPrimer();
        this.biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, x * 16, z * 16, 16, 16);
        this.generateBaseBlocks(x, z, chunkprimer); //method a
        this.insertBiomeBlocks(x, z, chunkprimer, this.biomesForGeneration); //method b
        Chunk chunk = new Chunk(this.worldObj, chunkprimer, x, z);
        byte[] abyte = chunk.getBiomeArray();

        for (int k = 0; k < abyte.length; ++k) {
            abyte[k] = (byte) this.biomesForGeneration[k].biomeID;
        }

        chunk.generateSkylightMap();
        return chunk;
    }

    /**
     * generates a subset of the level's terrain data. Takes 7 arguments: the
     * [empty] noise array, the position, and the size.
     */
    private double[] initializeNoiseField(double[] par1ArrayOfDouble, int par2, int par3, int par4, int par5, int par6, int par7) {
        ChunkProviderEvent.InitNoiseField event = new ChunkProviderEvent.InitNoiseField(this, par1ArrayOfDouble, par2, par3, par4, par5, par6, par7);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.getResult() == Result.DENY) {
            return event.noisefield;
        }

        if (par1ArrayOfDouble == null) {
            par1ArrayOfDouble = new double[par5 * par6 * par7];
        }

        double var8 = 684.412D;
        double var10 = 684.412D;
        this.noiseData4 = this.noiseGen4.generateNoiseOctaves(this.noiseData4, par2, par4, par5, par7, 1.121D, 1.121D, 0.5D);
        this.noiseData5 = this.noiseGen5.generateNoiseOctaves(this.noiseData5, par2, par4, par5, par7, 200.0D, 200.0D, 0.5D);
        var8 *= 2.0D;
        this.noiseData1 =
                this.noiseGen3.generateNoiseOctaves(this.noiseData1, par2, par3, par4, par5, par6, par7, var8 / 80.0D, var10 / 160.0D, var8 / 80.0D);
        this.noiseData2 = this.noiseGen1.generateNoiseOctaves(this.noiseData2, par2, par3, par4, par5, par6, par7, var8, var10, var8);
        this.noiseData3 = this.noiseGen2.generateNoiseOctaves(this.noiseData3, par2, par3, par4, par5, par6, par7, var8, var10, var8);
        int var12 = 0;
        int var13 = 0;

        for (int var14 = 0; var14 < par5; ++var14) {
            for (int var15 = 0; var15 < par7; ++var15) {
                double var16 = (this.noiseData4[var13] + 256.0D) / 512.0D;

                if (var16 > 1.0D) {
                    var16 = 1.0D;
                }

                double var18 = this.noiseData5[var13] / 8000.0D;

                if (var18 < 0.0D) {
                    var18 = -var18 * 0.3D;
                }

                var18 = var18 * 3.0D - 2.0D;
                float var20 = (var14 + par2 - 0) / 1.0F;
                float var21 = (var15 + par4 - 0) / 1.0F;
                float var22 = 100.0F - MathHelper.sqrt_float(var20 * var20 + var21 * var21) * 8.0F;

                if (var22 > 80.0F) {
                    var22 = 80.0F;
                }

                if (var22 < -100.0F) {
                    var22 = -100.0F;
                }

                if (var18 > 1.0D) {
                    var18 = 1.0D;
                }

                var18 /= 8.0D;
                var18 = 0.0D;

                if (var16 < 0.0D) {
                    var16 = 0.0D;
                }

                var16 += 0.5D;
                var18 = var18 * par6 / 16.0D;
                ++var13;
                double var23 = par6 / 2.0D;

                for (int var25 = 0; var25 < par6; ++var25) {
                    double var26 = 0.0D;
                    double var28 = (var25 - var23) * 8.0D / var16;

                    if (var28 < 0.0D) {
                        var28 *= -1.0D;
                    }

                    double var30 = this.noiseData2[var12] / 512.0D;
                    double var32 = this.noiseData3[var12] / 512.0D;
                    double var34 = (this.noiseData1[var12] / 10.0D + 1.0D) / 2.0D;

                    if (var34 < 0.0D) {
                        var26 = var30;
                    } else if (var34 > 1.0D) {
                        var26 = var32;
                    } else {
                        var26 = var30 + (var32 - var30) * var34;
                    }

                    var26 -= 8.0D;
                    var26 += var22;
                    byte var36 = 2;
                    double var37;

                    if (var25 > par6 / 2 - var36) {
                        var37 = (var25 - (par6 / 2 - var36)) / 64.0F;

                        if (var37 < 0.0D) {
                            var37 = 0.0D;
                        }

                        if (var37 > 1.0D) {
                            var37 = 1.0D;
                        }

                        var26 = var26 * (1.0D - var37) + -3000.0D * var37;
                    }

                    var36 = 8;

                    if (var25 < var36) {
                        var37 = (var36 - var25) / (var36 - 1.0F);
                        var26 = var26 * (1.0D - var37) + -30.0D * var37;
                    }

                    par1ArrayOfDouble[var12] = var26;
                    ++var12;
                }
            }
        }

        return par1ArrayOfDouble;
    }

    /**
     * Checks to see if a chunk exists at x, y
     */
    @Override
    public boolean chunkExists(int par1, int par2) {
        return true;
    }

    /**
     * Populates chunk with ores etc etc
     */
    @Override
    public void populate(IChunkProvider par1IChunkProvider, int par2, int par3) {
        BlockFalling.fallInstantly = true;

        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(par1IChunkProvider, this.worldObj, this.worldObj.rand, par2, par3, false));

        int var4 = par2 * 16;
        int var5 = par3 * 16;
        BiomeGenBase var6 = this.worldObj.getBiomeGenForCoords(new BlockPos(var4 + 16, 0, var5 + 16));

        long var7 = this.RNGa.nextLong() / 2L * 2L + 1L;
        long var9 = this.RNGa.nextLong() / 2L * 2L + 1L;
        boolean var11 = false;

        int var12;
        int var13;
        int var14;

        if (!var11 && this.RNGa.nextInt(2) == 0) {
            var12 = var4 + this.RNGa.nextInt(16) + 8;
            var13 = this.RNGa.nextInt(128);
            var14 = var5 + this.RNGa.nextInt(16) + 8;
            (new WorldGenLakes(Blocks.water)).generate(this.worldObj, this.RNGa, new BlockPos(var12, var13, var14));
        }

        if (!var11 && this.RNGa.nextInt(8) == 0) {
            var12 = var4 + this.RNGa.nextInt(16) + 8;
            var13 = this.RNGa.nextInt(this.RNGa.nextInt(120) + 8);
            var14 = var5 + this.RNGa.nextInt(16) + 8;

            if (var13 < 63 || this.RNGa.nextInt(10) == 0) {
                (new WorldGenLakes(Blocks.lava)).generate(this.worldObj, this.RNGa, new BlockPos(var12, var13, var14));
            }
        }

        var6.decorate(this.worldObj, this.RNGa, new BlockPos(var4, 0, var5));

        if (par2 == 0 && par3 == 0 && !this.portalDone) {
            createPortal(this.worldObj, this.RNGa);
        }

        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(par1IChunkProvider, this.worldObj, this.worldObj.rand, par2, par3, false));
        BlockFalling.fallInstantly = false;
    }

    private boolean towerDone = false;
    private boolean portalDone = false;

    public void generateTower(World par1World, Random par2Random, int par3, int par4) {
        WorldGenTower myTower = new WorldGenTower(Blocks.grass, Blocks.double_stone_slab, Blocks.lapis_ore);
        if (!this.towerDone) {
            int randPosX = par3 + par2Random.nextInt(16) + 8;
            int randPosZ = par4 + par2Random.nextInt(16) + 8;
            this.towerDone = myTower.generate(par1World, par2Random, new BlockPos(randPosX, 61, randPosZ));
        }
    }

    public void createPortal(World par1World, Random par2Random) {
        MoCWorldGenPortal myPortal =
                new MoCWorldGenPortal(Blocks.quartz_block, 2, Blocks.quartz_stairs, 0, Blocks.quartz_block, 1, Blocks.quartz_block, 0);
        for (int i = 0; i < 16; i++) {
            if (!this.portalDone) {
                int randPosY = 56 + i;
                this.portalDone = myPortal.generate(par1World, par2Random, new BlockPos(0, randPosY, 0));
            }
        }
    }

    /**
     * Two modes of operation: if passed true, save all Chunks in one go. If
     * passed false, save up to two chunks. Return true if all chunks have been
     * saved.
     */
    @Override
    public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate) {
        return true;
    }

    /**
     * Returns if the IChunkProvider supports saving.
     */
    @Override
    public boolean canSave() {
        return true;
    }

    /**
     * Converts the instance data to a readable string.
     */
    @Override
    public String makeString() {
        return "RandomLevelSource";
    }

    /**
     * Returns a list of creatures of the specified type that can spawn at the
     * given location.
     */
    @Override
    public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, BlockPos pos) {
        BiomeGenBase var5 = this.worldObj.getBiomeGenForCoords(pos);
        return var5 == null ? null : var5.getSpawnableList(par1EnumCreatureType);
    }

    @Override
    public int getLoadedChunkCount() {
        return 0;
    }

    /**
     * Unloads the 100 oldest chunks from memory, due to a bug with
     * chunkSet.add() never being called it thinks the list is always empty and
     * will not remove any chunks.
     */
    @Override
    public boolean unloadQueuedChunks() {
        return true;
    }

    @Override
    public void saveExtraData() {
    }

    @Override
    public BlockPos getStrongholdGen(World worldIn, String p_180513_2_, BlockPos p_180513_3_) {
        return null;
    }

    @Override
    public void recreateStructures(Chunk p_180514_1_, int p_180514_2_, int p_180514_3_) {
    }

    @Override
    public Chunk provideChunk(BlockPos blockPosIn) {
        return this.provideChunk(blockPosIn.getX() >> 4, blockPosIn.getZ() >> 4);
    }

}
