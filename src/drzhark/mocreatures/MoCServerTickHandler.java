package drzhark.mocreatures;

import java.util.EnumSet;
import java.util.List;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.TickType;
import drzhark.mocreatures.item.MoCItemArmor;

public class MoCServerTickHandler implements IScheduledTickHandler//ITickHandler
{

    private void onTickInGame()
    {
        if (MoCreatures.proxy.useCustomSpawner)
        {
            for (int dimension : DimensionManager.getIDs())
            {
                WorldServer worldObj = DimensionManager.getWorld(dimension);

                // ambient spawn tick
                if (worldObj != null && (worldObj.getWorldInfo().getWorldTime() % MoCreatures.proxy.ambientSpawnTickRate == 0L) && MoCreatures.proxy.spawnAmbients && MoCreatures.proxy.maxAmbients > 0) 
                {

                    int ambientSpawns = 0;

                    boolean spawnPassive = true;
                    if (dimension == 0)
                    {
                        spawnPassive = worldObj.isDaytime();
                    }
                    
                    if (worldObj.playerEntities.size() > 0 && spawnPassive) //&& worldObj.getGameRules().getGameRuleBooleanValue("doMobSpawning")
                    {
                        ambientSpawns = MoCreatures.myCustomSpawner.doCustomSpawning(worldObj, EnumCreatureType.ambient, MoCreatures.proxy.monsterSpawnRange, MoCreatures.proxy.lightLevel, MoCreatures.proxy.checkAmbientLightLevel);
                        if (MoCreatures.proxy.debugLogging) MoCreatures.log.info("Mo'Creatures Spawned " + ambientSpawns + " Ambients");
                    }
                }

                // water creature spawn tick
                if (worldObj != null && (worldObj.getWorldInfo().getWorldTime() % MoCreatures.proxy.waterSpawnTickRate == 0L) && MoCreatures.proxy.spawnWaterCreatures && MoCreatures.proxy.maxWaterCreatures > 0) 
                {

                    int waterSpawns = 0;
    
                    boolean spawnPassive = true;
                    if (dimension == 0)
                    {
                        spawnPassive = worldObj.isDaytime();
                    }
                    
                    if (worldObj.playerEntities.size() > 0 && spawnPassive) //&& worldObj.getGameRules().getGameRuleBooleanValue("doMobSpawning")
                    {
                        waterSpawns = MoCreatures.myCustomSpawner.doCustomSpawning(worldObj, EnumCreatureType.waterCreature, MoCreatures.proxy.monsterSpawnRange, MoCreatures.proxy.lightLevel, MoCreatures.proxy.checkAmbientLightLevel);
                        if (MoCreatures.proxy.debugLogging) MoCreatures.log.info("Mo'Creatures Spawned " + waterSpawns + " Water Creatures");// + ",  players on dimension " + dimension + " = " + playersInDimension );
                    }
                }

                // creature spawn tick
                if (worldObj != null && (worldObj.getWorldInfo().getWorldTime() % MoCreatures.proxy.creatureSpawnTickRate == 0L) && MoCreatures.proxy.spawnCreatures && MoCreatures.proxy.maxCreatures > 0) 
                {

                    int animalSpawns = 0;
    
                    boolean spawnPassive = true;
                    if (dimension == 0)
                    {
                        spawnPassive = worldObj.isDaytime();
                    }
                    
                    if (worldObj.playerEntities.size() > 0 && spawnPassive) //&& worldObj.getGameRules().getGameRuleBooleanValue("doMobSpawning")
                    {
                        animalSpawns = MoCreatures.myCustomSpawner.doCustomSpawning(worldObj, EnumCreatureType.creature, MoCreatures.proxy.monsterSpawnRange, MoCreatures.proxy.lightLevel, MoCreatures.proxy.checkAmbientLightLevel);
                        if (MoCreatures.proxy.debugLogging) MoCreatures.log.info("Mo'Creatures Spawned " + animalSpawns + " Creatures");// + ",  players on dimension " + dimension + " = " + playersInDimension );
                    }
                }
                // monster spawn tick
                if (worldObj != null && (worldObj.getWorldInfo().getWorldTime() % MoCreatures.proxy.monsterSpawnTickRate == 0L) && MoCreatures.proxy.spawnMonsters && worldObj.difficultySetting > 0 && MoCreatures.proxy.maxMonsters > 0) 
                {

                    int mobSpawns = 0;

                    if (worldObj.playerEntities.size() > 0 && !MoCreatures.proxy.disallowMonsterSpawningDuringDay) //&& worldObj.getGameRules().getGameRuleBooleanValue("doMobSpawning")
                    {
                        mobSpawns = MoCreatures.myCustomSpawner.doCustomSpawning(worldObj, EnumCreatureType.monster, MoCreatures.proxy.monsterSpawnRange, MoCreatures.proxy.lightLevel, MoCreatures.proxy.checkAmbientLightLevel);
                        if (MoCreatures.proxy.debugLogging) MoCreatures.log.info("Mo'Creatures Spawned " + mobSpawns + " Mobs");// + ",  players on dimension " + dimension + " = " + playersInDimension );
                    }
                }

                // despawn tick
                if (MoCreatures.proxy.despawnVanilla && worldObj != null && (worldObj.getWorldInfo().getWorldTime() % MoCreatures.proxy.despawnTickRate == 0L))
                {
                    if (worldObj.playerEntities.size() > 0)
                    {
                        int numDespawns = MoCreatures.myCustomSpawner.despawnVanillaAnimals(worldObj, MoCreatures.proxy.despawnLightLevel);
                        if (MoCreatures.proxy.debugLogging)
                        {
                            MoCreatures.log.info("Mo'Creatures DeSpawned " + numDespawns + " Vanilla Creatures");// + ",  players on dimension " + dimension + " = " + playersInDimension);
                        }
                    }
                    
                }

                worldObj = null;
            }
        }
    }

   
    

    @Override
    public String getLabel()
    {
        return null;
    }

    @Override
    public int nextTickSpacing()
    {

        return 20000;
    }
    
    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) 
    {
    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData) 
    {
        onTickInGame();
    }

    @Override
    public EnumSet<TickType> ticks() 
    {
        return EnumSet.of(TickType.SERVER);
    }
}