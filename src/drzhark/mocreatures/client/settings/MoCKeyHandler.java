package drzhark.mocreatures.client.settings;

import java.util.EnumSet;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.input.Keyboard;

import sharose.mods.guiapi.GuiModScreen;
import sharose.mods.guiapi.ModSettingScreen;

import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.client.network.MoCClientPacketHandler;
import drzhark.mocreatures.entity.MoCIMoCreature;

public class MoCKeyHandler extends KeyHandler {
    int keyCount;
    private ModSettingScreen localScreen;
    //static KeyBinding jumpBinding = new KeyBinding("jumpBind", Keyboard.KEY_F);
    static KeyBinding jumpBinding = new KeyBinding("MoCreatures Jump", MoCClientProxy.mc.gameSettings.keyBindJump.keyCode);
    //static KeyBinding diveBinding = new KeyBinding("MoCreatures Dive", Keyboard.KEY_F);
    static KeyBinding guiBinding = new KeyBinding("MoCreatures GUI", Keyboard.KEY_F9);
    static KeyBinding dismountBinding = new KeyBinding("MoCreatures Dismount", Keyboard.KEY_F);

    public MoCKeyHandler()
    {
        //the first value is an array of KeyBindings, the second is whether or not the call
        //keyDown should repeat as long as the key is down
        super(new KeyBinding[] { jumpBinding, dismountBinding, guiBinding }, new boolean[] { true, true, false });
        localScreen = MoCClientProxy.instance.MoCScreen;
    }

    @Override
    public String getLabel()
    {
        return "MoCKeyBindings";
    }

    @Override
    public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat)
    {
        boolean kbJump = kb.keyDescription.equals("MoCreatures Jump");
        boolean kbDive = kb.keyDescription.equals("MoCreatures Dive");
        boolean kbGui = kb.keyDescription.equals("MoCreatures GUI");
        boolean kbDismount = kb.keyDescription.equals("MoCreatures Dismount");

        if (MoCClientProxy.mc.inGameHasFocus && kbJump)
        {
            MoCClientProxy.mc.gameSettings.keyBindJump.pressed = true;
        }

        ++keyCount;
        // handle GUI
        if (kbGui)
        {
            localScreen = MoCClientProxy.instance.MoCScreen;
            if (MoCClientProxy.mc.inGameHasFocus)
            {
                GuiModScreen.show(localScreen.theWidget);
            }
            else {
                localScreen = null; // kill our instance
            }
        }
        /**
         * this avoids double jumping
         */
        if (keyCount < 4) { return; }

        EntityPlayer ep = MoCClientProxy.mc.thePlayer;

        if (kbJump && ep != null && ep.ridingEntity != null && ep.ridingEntity instanceof MoCIMoCreature)
        {
            keyCount = 0;
            // jump code needs to be executed client/server simultaneously to take
            ((MoCIMoCreature) ep.ridingEntity).makeEntityJump();
            MoCClientPacketHandler.sendEntityJumpPacket();
        }

        if (kbDive && ep != null && ep.ridingEntity != null && ep.ridingEntity instanceof MoCIMoCreature)
        {
            keyCount = 0;
            // jump code needs to be executed client/server simultaneously to take
            ((MoCIMoCreature) ep.ridingEntity).makeEntityDive();
            MoCClientPacketHandler.sendEntityDivePacket();
        }

        if (kbDismount && ep != null && ep.ridingEntity != null && ep.ridingEntity instanceof MoCIMoCreature)
        {
            keyCount = 0;
            // dismount code needs executed client/server simultaneously
            ((MoCIMoCreature) ep.ridingEntity).dismountEntity();
            MoCClientPacketHandler.sendEntityDismountPacket();
        }
    }

    @Override
    public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd)
    {
        MoCClientProxy.mc.gameSettings.keyBindJump.pressed = false;
    }

    @Override
    public EnumSet<TickType> ticks()
    {
        return EnumSet.of(TickType.CLIENT);
    }
}