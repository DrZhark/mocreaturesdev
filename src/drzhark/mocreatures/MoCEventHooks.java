package drzhark.mocreatures;

import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.NETHER_BRIDGE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cpw.mods.fml.common.registry.EntityRegistry;

import drzhark.customspawner.CustomSpawner;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.SpawnListEntry;
import net.minecraft.world.gen.structure.MapGenNetherBridge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.event.world.WorldEvent;

public class MoCEventHooks {

    @ForgeSubscribe
    public void peformCustomWorldGenSpawning(PopulateChunkEvent.Populate event)
    {
        if (MoCreatures.proxy.useCustomSpawner)
        {
            int par1 = event.chunkX * 16;
            int par2 = event.chunkZ * 16;
            List customSpawnList = MoCreatures.myCustomSpawner.getCustomBiomeSpawnList(event.world.getBiomeGenForCoords(par1 + 16, par2 + 16));
            CustomSpawner.performWorldGenSpawning(event.world, event.world.getBiomeGenForCoords(par1 + 16, par2 + 16), par1 + 8, par2 + 8, 16, 16, event.rand, customSpawnList, MoCreatures.proxy.worldGenCreatureSpawning);
        }
    }

    @ForgeSubscribe
    public void onLivingSpawn(LivingSpawnEvent.CheckSpawn event)
    {
        System.out.println("event = " + event.getResult());
        
    }

    @ForgeSubscribe // handle world gen spawns
    public void onLivingWorldGenSpawn(EntityJoinWorldEvent event)
    {
        if (EntityLiving.class.isAssignableFrom(event.entity.getClass()) && MoCreatures.proxy.useCustomSpawner && MoCreatures.proxy.modifyVanillaSpawns)
        {
            if (MoCreatures.proxy.vanillaEntityMap.containsKey(event.entity.getEntityName()) || MoCreatures.proxy.vanillaEntityMap.containsKey(event.entity.getEntityName() + ".Vanilla"))
            {
                System.out.println("Killed vanilla world gen entity " + event.entity);
                event.entity.setDead();
            }
        }
    }
    /*@ForgeSubscribe
    public void onWorldLoading(WorldEvent.Load event)
    {
        //String dimensionName = event.world.provider.getDimensionName();
        event.world.getSaveHandler().getWorldDirectoryName()
        if (dimensionName != null)
        {
            MoCConfiguration config = MoCreatures.proxy.MoCconfig;
            System.out.println("Dimension " + dimensionName + " is loading.");
        }
    }*/

    @ForgeSubscribe
    public void structureMapGen(InitMapGenEvent event)
    {
        System.out.println("INITMAPGENEVENT " + event.newGen + " , type = " + event.type);
        if (MoCreatures.proxy.useCustomSpawner)
        {
            String structureClass = event.originalGen.getClass().toString();
            MoCreatures.proxy.structureData.addStructure(event.type, event.originalGen);
        }
    }
}