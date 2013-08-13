package drzhark.mocreatures;

import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.NETHER_BRIDGE;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cpw.mods.fml.common.registry.EntityRegistry;

import drzhark.customspawner.CustomSpawner;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.SpawnListEntry;
import net.minecraft.world.gen.structure.MapGenNetherBridge;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingPackSizeEvent;
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
            if (customSpawnList != null)
                MoCreatures.myCustomSpawner.performWorldGenSpawning(event.world, event.world.getBiomeGenForCoords(par1 + 16, par2 + 16), par1 + 8, par2 + 8, 16, 16, event.rand, customSpawnList, MoCreatures.proxy.worldGenCreatureSpawning);
        }
    }

    @ForgeSubscribe // override maxSpawnInChunk values
    public void onLivingPackSize(LivingPackSizeEvent event)
    {
        if (MoCreatures.proxy.useCustomSpawner)
        {
            MoCEntityData entityData = MoCreatures.proxy.classToEntityMapping.get(event.entityLiving.getClass());
            //System.out.println("entityData = " + entityData);
            if (entityData != null)
            {
                //System.out.println("LIVINGPACKSIZE " + event.entityLiving + " setting to " + entityData.getMaxInChunk());
                event.maxPackSize = entityData.getMaxInChunk();
               // System.out.println("new size = " + event.maxPackSize);
                event.setResult(Result.ALLOW); // needed for changes to take effect
            }
        }
    }

   /* @ForgeSubscribe // override maxSpawnInChunk values
    public void onLivingSpawn(LivingSpawnEvent.CheckSpawn event)
    {
        //System.out.println("onLivingSpawn event = " + event.entity);
        if (MoCreatures.proxy.useCustomSpawner)
        {
            MoCEntityData entityData = MoCreatures.proxy.classToEntityMapping.get(event.entityLiving.getClass());
            if (entityData != null && entityData.getUseVanillaSpawner() && entityData.getCanSpawn())
            {
                WorldServer world = DimensionManager.getWorld(event.entityLiving.worldObj.provider.dimensionId);
                if (world != null && (MoCreatures.myCustomSpawner.isValidLightLevel(event.entityLiving, world, MoCreatures.proxy.lightLevel, MoCreatures.proxy.checkAmbientLightLevel) && MoCreatures.myCustomSpawner.getEntityCount(world, entityData.getType()) <= MoCreatures.myCustomSpawner.getMax(entityData.getType()) * MoCreatures.myCustomSpawner.eligibleChunksForSpawning.size() / 256))
                {
                    event.setResult(Result.ALLOW);
                }
                else
                {
                    if (MoCreatures.proxy.debugCMS) 
                        MoCreatures.myCustomSpawner.log.info("Denied vanilla spawner from spawning entity " + entityData.getEntityName()+ ".");
                    //System.out.println("Denied vanilla spawner from spawning entity " + entityData.getEntityName() + ".");
                    event.setResult(Result.DENY);
                }
            }
            else if (entityData != null && !entityData.getCanSpawn())
            {
                if (MoCreatures.proxy.debugCMS) 
                    MoCreatures.myCustomSpawner.log.info("Denied CMS from spawning entity " + entityData.getEntityName() + ". CanSpawn set to false.");
                event.setResult(Result.DENY);
            }
        }
    }*/

    @ForgeSubscribe
    public void structureMapGen(InitMapGenEvent event)
    {
      //  System.out.println("INITMAPGENEVENT " + event.newGen + " , type = " + event.type);
        if (MoCreatures.proxy.useCustomSpawner)
        {
            String structureClass = event.originalGen.getClass().toString();
            MoCreatures.proxy.structureData.addStructure(event.type, event.originalGen);
        }
    }

}