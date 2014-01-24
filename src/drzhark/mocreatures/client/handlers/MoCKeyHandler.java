package drzhark.mocreatures.client.handlers;

import java.util.EnumSet;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import drzhark.guiapi.GuiModScreen;
import drzhark.guiapi.ModSettingScreen;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.network.packet.MoCPacketEntityDive;
import drzhark.mocreatures.network.packet.MoCPacketEntityJump;

public class MoCKeyHandler {
    int keyCount;
    private ModSettingScreen localScreen;
    //static KeyBinding jumpBinding = new KeyBinding("jumpBind", Keyboard.KEY_F);
    //static KeyBinding jumpBinding = new KeyBinding("MoCreatures Jump", MoCClientProxy.mc.gameSettings.keyBindJump.getKeyCode(), "key.categories.movement");
    static KeyBinding diveBinding = new KeyBinding("MoCreatures Dive", Keyboard.KEY_F, "key.categories.movement");
    static KeyBinding guiBinding = new KeyBinding("MoCreatures GUI", Keyboard.KEY_F6, "key.categories.misc");
    //static KeyBinding dismountBinding = new KeyBinding("MoCreatures Dismount", Keyboard.KEY_F);

    public MoCKeyHandler()
    {
        //the first value is an array of KeyBindings, the second is whether or not the call
        //keyDown should repeat as long as the key is down
        //cpw.mods.fml.client.registry.ClientRegistry.registerKeyBinding(jumpBinding);
        cpw.mods.fml.client.registry.ClientRegistry.registerKeyBinding(diveBinding);
        cpw.mods.fml.client.registry.ClientRegistry.registerKeyBinding(guiBinding);
        localScreen = MoCClientProxy.instance.MoCScreen;
    }

    
    @SubscribeEvent
    public void onKeyInput(KeyInputEvent event)
    {
        boolean kbJump = Keyboard.isKeyDown(MoCClientProxy.mc.gameSettings.keyBindJump.getKeyCode());
        boolean kbDive = Keyboard.isKeyDown(diveBinding.getKeyCode());
        boolean kbGui = Keyboard.isKeyDown(guiBinding.getKeyCode());
        //boolean kbDismount = kb.keyDescription.equals("MoCreatures Dismount");
        boolean chatting = FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().func_146241_e();
        if ((kbGui) && (!MoCreatures.isServer()))
        {
            this.localScreen = MoCClientProxy.instance.MoCScreen;
            if ((MoCClientProxy.mc.inGameHasFocus) && (this.localScreen != null))
            {
                GuiModScreen.show(localScreen.theWidget);
            }
            else 
            {
                localScreen = null; // kill our instance
            }
        }

       // ++keyCount;
        /**
         * this avoids double jumping
         */
        if (/*keyCount < 4 ||*/chatting) { return; }

        EntityPlayer ep = MoCClientProxy.mc.thePlayer;

        if (kbJump && ep != null && ep.ridingEntity != null && ep.ridingEntity instanceof IMoCEntity)
        {
           // keyCount = 0;
            // jump code needs to be executed client/server simultaneously to take
            ((IMoCEntity) ep.ridingEntity).makeEntityJump();
            MoCreatures.packetPipeline.sendToServer(new MoCPacketEntityJump());
        }

        if (kbDive && ep != null && ep.ridingEntity != null && ep.ridingEntity instanceof IMoCEntity)
        {
          //  keyCount = 0;
            // jump code needs to be executed client/server simultaneously to take
            ((IMoCEntity) ep.ridingEntity).makeEntityDive();
            MoCreatures.packetPipeline.sendToServer(new MoCPacketEntityDive());
        }
    }
}