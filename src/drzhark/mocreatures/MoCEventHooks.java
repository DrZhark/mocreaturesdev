package drzhark.mocreatures;

import java.util.List;

import drzhark.customspawner.CustomSpawner;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;

public class MoCEventHooks {

    @ForgeSubscribe
    public void peformCustomWorldGenSpawning(PopulateChunkEvent.Populate event)
    {
        int par1 = event.chunkX * 16;
        int par2 = event.chunkZ * 16;
        List customSpawnList = MoCreatures.myCustomSpawner.getCustomBiomeSpawnList(event.world.getBiomeGenForCoords(par1 + 16, par2 + 16));
        CustomSpawner.performWorldGenSpawning(event.world, event.world.getBiomeGenForCoords(par1 + 16, par2 + 16), par1 + 8, par2 + 8, 16, 16, event.rand, customSpawnList);
    }
}
