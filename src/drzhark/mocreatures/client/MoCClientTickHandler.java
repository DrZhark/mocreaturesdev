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
       /* if (inMenu)
        {
            if (lastTickRun > 10)
            {
                //System.out.println("MoCreatures.proxy.needsUpdate = " + MoCreatures.proxy.needsUpdate);
                if (MoCreatures.proxy.needsUpdate)
                {
                    //System.out.println("need update, updating settings...");
                    MoCreatures.updateSettings();
                    inMenu = false;
                }
            }
        }*/
        
        if (inMenu)
        {
            //if (lastTickRun > 10)
            //{
            if (MoCreatures.proxy.needsUpdate)
            {
                MoCreatures.updateSettings();
                inMenu = false;
                MoCreatures.proxy.needsUpdate = false;
            }
            //}
            //lastTickRun++;
        }
    }

    boolean inMenu;
    int lastTickRun;

    private void onTickInGui(GuiScreen curScreen)
    {
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