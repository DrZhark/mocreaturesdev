package drzhark.customspawner;

import java.util.List;

import net.minecraft.util.MathHelper;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingPackSizeEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import drzhark.customspawner.entity.EntityData;
import drzhark.customspawner.utils.CMSLog;

public class EventHooks {

    @ForgeSubscribe
    public void peformCustomWorldGenSpawning(PopulateChunkEvent.Populate event)
    {
       // System.out.println("performCustomWorldGenSpawning " + event.chunkProvider);
        int par1 = event.chunkX * 16;
        int par2 = event.chunkZ * 16;
        int x = event.chunkX * 16 + 8 + event.world.rand.nextInt(16);
        int z = event.chunkZ * 16 + 8 + event.world.rand.nextInt(16);
        /*for (LivingSpawnType entitySpawnType : CustomSpawner.INSTANCE.entitySpawnTypes.values())
        {
            System.out.println("found livingspawntype " + entitySpawnType.getLivingSpawnTypeName());
        }*/
        List customSpawnList = CustomSpawner.INSTANCE.getCustomBiomeSpawnList(event.world.getBiomeGenForCoords(x, z));
       // System.out.println("customSpawnList = " + customSpawnList);
        if (customSpawnList != null)
        {
            //System.out.println("size = " + customSpawnList.size());
            CustomSpawner.INSTANCE.performWorldGenSpawning(event.world, event.world.getBiomeGenForCoords(x, z), par1 + 8, par2 + 8, 16, 16, event.rand, customSpawnList, CustomSpawner.INSTANCE.worldGenCreatureSpawning);
        }
        
        /*List customSpawnList = CustomSpawner.getCustomBiomeSpawnList(event.world.getBiomeGenForCoords(par1 + 16, par2 + 16));
        if (customSpawnList != null)
            CustomSpawner.performWorldGenSpawning(event.world, event.world.getBiomeGenForCoords(par1 + 16, par2 + 16), par1 + 8, par2 + 8, 16, 16, event.rand, customSpawnList, CustomSpawner.INSTANCE.worldGenCreatureSpawning);
    */
    }

    @ForgeSubscribe
    public void onLivingPackSize(LivingPackSizeEvent event)
    {
        EntityData entityData = CustomSpawner.classToEntityMapping.get(event.entityLiving.getClass());
        if (entityData != null)
        {
            event.maxPackSize = entityData.getMaxInChunk();
            event.setResult(Result.ALLOW); // needed for changes to take effect
        }
    }

    @ForgeSubscribe
    public void onLivingSpawn(LivingSpawnEvent.CheckSpawn event)
    {
        EntityData entityData = CustomSpawner.classToEntityMapping.get(event.entityLiving.getClass());
        int x = MathHelper.floor_double(event.x);
        int y = MathHelper.floor_double(event.y);
        int z = MathHelper.floor_double(event.z);
        if (entityData != null && !entityData.getCanSpawn())
        {
            if (CustomSpawner.debug) CMSLog.logger.info("Denied spawn for entity " + entityData.getEntityClass() + ". CanSpawn set to false or frequency set to 0!");
                event.setResult(Result.DENY);
        }
        else if ((entityData.getMinY() != -1 && y < entityData.getMinY()) || (entityData.getMaxY() != -1 && y > entityData.getMaxY()))
        {
            if (CustomSpawner.debug) CMSLog.logger.info("Denied spawn for entity " + entityData.getEntityClass() + ". MinY or MaxY exceeded allowed value!");
                event.setResult(Result.DENY);
        }
        BiomeGenBase biome = event.world.getBiomeGenForCoords(x, z);
        if (biome != null)
        {
            // handle biome specific spawn settings
        }
    }

    @ForgeSubscribe
    public void structureMapGen(InitMapGenEvent event)
    {
        String structureClass = event.originalGen.getClass().toString();
        CustomSpawner.structureData.registerStructure(event.type, event.originalGen);
    }

   /* @ForgeSubscribe
    public void worldLoad(WorldEvent.Load event) {
        // create world provider configs
        String environment = event.world.provider.getClass().getSimpleName().toLowerCase();
        environment = environment.replace("worldprovider", "");
        environment = environment.replace("provider", "");
    }*/
}