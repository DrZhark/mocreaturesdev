package drzhark.mocreatures.client;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class MoCClientTickHandler implements ITickHandler {

    private void onTickInGame()
    {
    }

    boolean inMenu;
    int lastTickRun;

    private void onTickInGui(GuiScreen curScreen)
    {
        // handle GUI tick stuff here
        inMenu = true;
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