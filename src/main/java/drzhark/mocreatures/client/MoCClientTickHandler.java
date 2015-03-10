package drzhark.mocreatures.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Type;

public class MoCClientTickHandler {

    private void onTickInGame() {
    }

    boolean inMenu;
    int lastTickRun;

    private void onTickInGui(GuiScreen curScreen) {
        // handle GUI tick stuff here
        this.inMenu = true;
        this.lastTickRun = 0;
    }

    @SubscribeEvent
    public void tickEnd(ClientTickEvent event) {
        if (event.type.equals(Type.CLIENT)) {
            GuiScreen curScreen = Minecraft.getMinecraft().currentScreen;
            if (curScreen != null) {
                onTickInGui(curScreen);
            } else {
                this.inMenu = false;
                onTickInGame();
            }
        }
    }
}
