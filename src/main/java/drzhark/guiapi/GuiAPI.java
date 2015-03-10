package drzhark.guiapi;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.GameSettings;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Type;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class GuiAPI implements IFMLLoadingPlugin {

    Object cacheCheck = null;
    Field controlListField;

    @EventHandler
    public void init(FMLInitializationEvent event) {
        try {
            Field[] fields = GuiScreen.class.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                if (fields[i].getType() == List.class) {
                    this.controlListField = fields[i];
                    this.controlListField.setAccessible(true);
                    break;
                }
            }
            if (this.controlListField == null) {
                throw new Exception("No fields found on GuiScreen (" + GuiScreen.class.getSimpleName() + ") of type List! This should never happen!");
            }
        } catch (Throwable e) {
            throw new RuntimeException("Unable to get Field reference for GuiScreen.controlList!", e);
        }
        FMLCommonHandler.instance().bus().register(this);
    }

    public List getControlList(GuiOptions gui) {
        try {
            return (List) this.controlListField.get(gui);
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
        if (controlList.get(0) == this.cacheCheck) {
            // Cached so we don't have to check this every frame
            return;
        }

        // I hacked this out so it just sticks it between touchscreen mode and difficulty. I'm so sorry.

        // First get a list of buttons
        ArrayList<GuiOptionButton> buttonsPreSorted = new ArrayList<GuiOptionButton>();
        for (Object guiButton : controlList) {
            if (guiButton instanceof GuiOptionButton) {
                buttonsPreSorted.add((GuiOptionButton) guiButton);
            }
        }

        int xPos = -1; // difficulty
        int yPos = -1; // touchscreen mode
        for (GuiOptionButton guiButton : buttonsPreSorted) {
            /*
             * if(guiButton.returnEnumOptions() ==
             * GameSettings.Options.DIFFICULTY) { xPos = guiButton.xPosition; }
             */

            if (guiButton.returnEnumOptions() == GameSettings.Options.TOUCHSCREEN) {
                yPos = guiButton.yPosition;
            }
        }

        controlList.add(new GuiApiButton(300, xPos, yPos, 150, 20, "Global Mod Options"));

        // set the cache!
        this.cacheCheck = controlList.get(0);
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

    @SubscribeEvent
    public void clientTick(ClientTickEvent event) {
        if (!(event.type == Type.RENDER)) {
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
    public String getAccessTransformerClass() {
        // TODO Auto-generated method stub
        return null;
    }
}
