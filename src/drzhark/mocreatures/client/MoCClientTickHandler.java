package drzhark.mocreatures.client;

import java.util.EnumSet;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;

public class MoCClientTickHandler implements ITickHandler {

    private void onTickInGame()
    {
        if (MoCreatures.proxy.needsUpdate && !inMenu)
        {
            MoCreatures.proxy.needsUpdate = false;
            MoCreatures.proxy.ConfigInit(MoCreatures.proxy.configPreEvent);
            MoCreatures.proxy.ConfigPostInit(MoCreatures.proxy.configPostEvent);
            MoCreatures.updateSettings();
        }
    }

    boolean inMenu;
    int lastTickRun;

    private void onTickInGui(GuiScreen curScreen)
    {
        // handle GUI tick stuff here
        inMenu = true;
        MoCreatures.proxy.needsUpdate = true;
        lastTickRun = 0;
    }

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData)
    {
    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData)
    {
        if (type.equals(EnumSet.of(TickType.CLIENT)))
        {
            GuiScreen curScreen = Minecraft.getMinecraft().currentScreen;
            if (curScreen != null)
            {
                onTickInGui(curScreen);
            }
            else
            {
                inMenu = false;
                onTickInGame();
            }
        }

    }

    @Override
    public EnumSet<TickType> ticks()
    {
        return EnumSet.of(TickType.CLIENT);
    }

    @Override
    public String getLabel()
    {
        return null;
    }
}