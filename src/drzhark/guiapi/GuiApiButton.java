package drzhark.guiapi;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import drzhark.guiapi.widget.WidgetSetting;

public class GuiApiButton extends GuiButton {

    public GuiApiButton(int par1, int par2, int par3, int par4, int par5,
            String par6Str) {
        super(par1, par2, par3, par4, par5, par6Str);
    }
    
    @Override
    public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3)
    {
        if(super.mousePressed(par1Minecraft, par2, par3))
        {
            par1Minecraft.gameSettings.saveOptions();
            ModSettingScreen.guiContext = "";
            WidgetSetting.updateAll();
            GuiModScreen.show(new GuiModSelect(par1Minecraft.currentScreen));
            return true;
        }
        return false;
    }
}
