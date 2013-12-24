package drzhark.guiapi;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSmallButton;
import net.minecraft.client.settings.EnumOptions;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

//@Mod(name = "DrZhark's GuiAPI", modid = "DrZhark's GuiAPI", version = "1.0", acceptedMinecraftVersions = "1.6")
@SideOnly(Side.CLIENT)
public class GuiAPI implements IFMLLoadingPlugin, ITickHandler {

    Object cacheCheck = null;
    Field controlListField;

    @Init
    public void init(FMLInitializationEvent event) {
        try {
            Field[] fields = GuiScreen.class.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                if (fields[i].getType() == List.class) {
                    controlListField = fields[i];
                    controlListField.setAccessible(true);
                    break;
                }
            }
            if (controlListField == null) {
                throw new Exception("No fields found on GuiScreen ("
                        + GuiScreen.class.getSimpleName()
                        + ") of type List! This should never happen!");
            }
        } catch (Throwable e) {
            throw new RuntimeException(
                    "Unable to get Field reference for GuiScreen.controlList!",
                    e);
        }
        TickRegistry.registerTickHandler(this, Side.CLIENT);
    }

    public List getControlList(GuiOptions gui) {
        try {
            return (List) controlListField.get(gui);
        } catch (Throwable e) {
            return null; // This should really print something, but it should
                            // never (ever) fire.
        }
    }

    public void processGuiOptions(GuiOptions gui) {
        List controlList = getControlList(gui);
        if (controlList == null) {
            return;
        }
        if (controlList.get(0) == cacheCheck) {
            // Cached so we don't have to check this every frame
            return;
        }
        
        // I hacked this out so it just sticks it between touchscreen mode and difficulty. I'm so sorry.
        
        // First get a list of buttons
        ArrayList<GuiSmallButton> buttonsPreSorted = new ArrayList<GuiSmallButton>();
        for (Object guiButton : controlList) {
            if (guiButton instanceof GuiSmallButton)
                buttonsPreSorted.add((GuiSmallButton) guiButton);
        }
        
        
        
        int xPos = -1; // difficulty
        int yPos = -1; // touchscreen mode
        for (GuiSmallButton guiButton : buttonsPreSorted) {
            if(guiButton.returnEnumOptions() == EnumOptions.DIFFICULTY)
            {
                xPos = guiButton.xPosition;
            }
            
            if(guiButton.returnEnumOptions() == EnumOptions.TOUCHSCREEN)
            {
                yPos = guiButton.yPosition;
            }
        }

        
            controlList.add(new GuiApiButton(300, xPos, yPos, 150, 20,
                    "Global Mod Options"));

            // set the cache!
            cacheCheck = controlList.get(0);
    }

    @Override
    public String[] getLibraryRequestClass() {
        return null;
    }

    @Override
    public String[] getASMTransformerClass() {
        return null;
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
    }

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {
        if (!type.contains(TickType.RENDER)) {
            return;
        }
        if (Minecraft.getMinecraft() == null) {
            return; // what
        }
        if (Minecraft.getMinecraft().currentScreen == null) {
            return;
        }
        if (Minecraft.getMinecraft().currentScreen instanceof GuiOptions) {
            processGuiOptions((GuiOptions) Minecraft.getMinecraft().currentScreen);
        }
    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData) {
        
    }

    @Override
    public EnumSet<TickType> ticks() {
        return EnumSet.of(TickType.RENDER);
    }

    @Override
    public String getLabel() {
        return "GuiAPI main menu checker";
    }
}
