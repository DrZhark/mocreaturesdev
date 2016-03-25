package drzhark.guiapi;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Type;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.Field;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiAPI {

    public Object cacheCheck = null;
    public Field controlListField;

    @SuppressWarnings("rawtypes")
    public List getControlList(GuiOptions gui) {
        try {
            return (List) this.controlListField.get(gui);
        } catch (Throwable e) {
            return null; // This should really print something, but it should never (ever) fire.
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public void processGuiOptions(GuiOptions gui) {
        List controlList = getControlList(gui);
        if (controlList == null) {
            return;
        }
        if (controlList.get(0) == this.cacheCheck) {
            // Cached so we don't have to check this every frame
            return;
        }

        ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft());
        int width = scaledresolution.getScaledWidth();
        int height = scaledresolution.getScaledHeight();
        controlList.add(new GuiApiButton(300, width / 2 - 155, height / 6 + 12, 150, 20, "Global Mod Options"));

        // set the cache!
        this.cacheCheck = controlList.get(0);
    }

    @SubscribeEvent
    public void clientTick(ClientTickEvent event) {
        if (!(event.type == Type.CLIENT)) {
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

}
