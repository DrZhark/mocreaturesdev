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
                if (worldObj != null && (worldObj.getWorldInfo().getWorldTime() % MoCreatures.proxy.ambientSpawnTickRate == 0L) && MoCreatures.proxy.spawnAmbients && MoCreatures.proxy.maxAmbient > 0) 
                {

                    int ambientSpawns = 0;

                    boolean spawnPassive = true;
                    if (dimension == 0)
                    {
                        spawnPassive = worldObj.isDaytime();
                    }
                    
                    if (worldObj.playerEntities.size() > 0 && spawnPassive) //&& worldObj.getGameRules().getGameRuleBooleanValue("doMobSpawning")
                    {
                        ambientSpawns = MoCreatures.myCustomSpawner.doCustomSpawning(worldObj, EnumCreatureType.ambient, MoCreatures.proxy.mobSpawnRange, MoCreatures.proxy.lightLevel);
                        if (MoCreatures.proxy.debugLogging) MoCreatures.log.info("Mo'Creatures Spawned " + ambientSpawns + " Ambients");
                    }
                }

                // water creature spawn tick
                if (worldObj != null && (worldObj.getWorldInfo().getWorldTime() % MoCreatures.proxy.waterSpawnTickRate == 0L) && MoCreatures.proxy.spawnWaterCreatures && MoCreatures.proxy.maxWaterMobs > 0) 
                {

                    int waterSpawns = 0;
    
                    boolean spawnPassive = true;
                    if (dimension == 0)
                    {
                        spawnPassive = worldObj.isDaytime();
                    }
                    
                    if (worldObj.playerEntities.size() > 0 && spawnPassive) //&& worldObj.getGameRules().getGameRuleBooleanValue("doMobSpawning")
                    {
                        waterSpawns = MoCreatures.myCustomSpawner.doCustomSpawning(worldObj, EnumCreatureType.waterCreature, MoCreatures.proxy.mobSpawnRange, MoCreatures.proxy.lightLevel);
                        if (MoCreatures.proxy.debugLogging) MoCreatures.log.info("Mo'Creatures Spawned " + waterSpawns + " Water Creatures");// + ",  players on dimension " + dimension + " = " + playersInDimension );
                    }
                }

                // creature spawn tick
                if (worldObj != null && (worldObj.getWorldInfo().getWorldTime() % MoCreatures.proxy.creatureSpawnTickRate == 0L) && MoCreatures.proxy.spawnCreatures && MoCreatures.proxy.maxAnimals > 0) 
                {

                    int animalSpawns = 0;
    
                    boolean spawnPassive = true;
                    if (dimension == 0)
                    {
                    	spawnPassive = worldObj.isDaytime();
                    }
                    
                    if (worldObj.playerEntities.size() > 0 && spawnPassive) //&& worldObj.getGameRules().getGameRuleBooleanValue("doMobSpawning")
                    {
                        animalSpawns = MoCreatures.myCustomSpawner.doCustomSpawning(worldObj, EnumCreatureType.creature, MoCreatures.proxy.mobSpawnRange, MoCreatures.proxy.lightLevel);
                        if (MoCreatures.proxy.debugLogging) MoCreatures.log.info("Mo'Creatures Spawned " + animalSpawns + " Creatures");// + ",  players on dimension " + dimension + " = " + playersInDimension );
                    }
                }
                // monster spawn tick
                if (worldObj != null && (worldObj.getWorldInfo().getWorldTime() % MoCreatures.proxy.mobSpawnTickRate == 0L) && MoCreatures.proxy.spawnMonsters && worldObj.difficultySetting > 0 && MoCreatures.proxy.maxMobs > 0) 
                {

                    int mobSpawns = 0;

                    if (worldObj.playerEntities.size() > 0) //&& worldObj.getGameRules().getGameRuleBooleanValue("doMobSpawning")
                    {
                        mobSpawns = MoCreatures.myCustomSpawner.doCustomSpawning(worldObj, EnumCreatureType.monster, MoCreatures.proxy.mobSpawnRange, MoCreatures.proxy.lightLevel);
                        if (MoCreatures.proxy.debugLogging) MoCreatures.log.info("Mo'Creatures Spawned " + mobSpawns + " Mobs");// + ",  players on dimension " + dimension + " = " + playersInDimension );
                    }
                }

                // despawn tick
                if (MoCreatures.proxy.despawnVanilla && worldObj != null && (worldObj.getWorldInfo().getWorldTime() % MoCreatures.proxy.despawnTickRate == 0L))
                {
                	 
                	if (worldObj.playerEntities.size() > 0)
                	{
                		int numDespawns = MoCreatures.myCustomSpawner.despawnVanillaAnimals(worldObj, MoCreatures.proxy.lightLevel);
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
	
	
	/*@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData)
	{
		if(type.equals(EnumSet.of(TickType.PLAYER)))
		{
			onPlayerTick((EntityPlayer) tickData[0]);
		}
	}*/
    
    /*@Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData)
    {
    	if(type.equals(EnumSet.of(TickType.PLAYER)))
		{
        onTickInGame();
		}
    }*/

   
    /*@Override
	public EnumSet<TickType> ticks()
	{
		return EnumSet.of(TickType.SERVER, TickType.PLAYER);
	}*/

    private void onPlayerTick(EntityPlayer player)
	{
    	
		/*if(player.getCurrentArmor(3) != null && player.worldObj.rand.nextInt(50)==0)
			{
			//System.out.println("player has a helmet");
			ItemStack myStack = player.getCurrentArmor(3);
			if (myStack != null && myStack.getItem() instanceof MoCItemArmor)
			{
				MoCTools.updatePlayerArmorEffects(player);
				//System.out.println("player has equipped a MoC helmet");
			}
			}*/
	}
}