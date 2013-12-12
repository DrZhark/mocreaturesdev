package drzhark.mocreatures;

import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.WorldEvent;

public class MoCEventHooks {

    @ForgeSubscribe
    public void onWorldUnload(WorldEvent.Unload event)
    {
        // if overworld has been deleted or unloaded, reset our flag
        if (event.world.provider.dimensionId == 0)
        {
            MoCreatures.proxy.worldInitDone = false;
        }
    }

    @ForgeSubscribe
    public void onWorldLoad(WorldEvent.Load event)
    {
        if (DimensionManager.getWorld(0) != null && !MoCreatures.proxy.worldInitDone) // if overworld has loaded, use its mapstorage
        {
            MoCPetMapData data = (MoCPetMapData)DimensionManager.getWorld(0).mapStorage.loadData(MoCPetMapData.class, "mocreatures");
            if (data == null)
            {
                data = new MoCPetMapData("mocreatures");
            }

            DimensionManager.getWorld(0).mapStorage.setData("mocreatures", data);
            DimensionManager.getWorld(0).mapStorage.saveAllData();
            MoCreatures.instance.mapData = data;
            MoCreatures.proxy.worldInitDone = true;
        }
    }
}