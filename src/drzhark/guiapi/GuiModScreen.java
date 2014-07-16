package drzhark.guiapi;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.renderer.lwjgl.RenderScale;

/**
 * GuiModScreen is the minecraft screen subclass that controls and renders TWL.
 * normally you will want to call it's static methods to use it, though
 * subclassing it and/or instantiating it are also possible. however, to do so
 * would use unsafe api (I still might change things.)
 * 
 * @author lahwran
 * 
 * @see GuiModScreen#show(GuiModScreen)
 * @see GuiModScreen#show(Widget)
 */
public class GuiModScreen extends GuiScreen {
    /**
     * Used by static methods. Is the currently displayed screen.
     */
    public static GuiModScreen currentScreen;

    /**
     * Hide current screen and show parent screen.
     */
    public static void back() {
        if (GuiModScreen.currentScreen != null) {
            Minecraft m = ModSettings.getMcinst();
            m.displayGuiScreen(GuiModScreen.currentScreen.parentScreen);
            if (GuiModScreen.currentScreen.parentScreen instanceof GuiModScreen) {
                GuiModScreen.currentScreen = (GuiModScreen) GuiModScreen.currentScreen.parentScreen;
                GuiModScreen.currentScreen.setActive();
            } else {
                GuiModScreen.currentScreen = null;
            }
        }
    }

    /**
     * Play a click sound. Call after the user performs an action. Already
     * called from setting widgets.
     */
    public static void clicksound() {
        Minecraft m = ModSettings.getMcinst();
        m.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    }

    /**
     * Show a screen - GuiModScreen version. Show an instance of GuiModScreen -
     * Does not set the parent screen, you have to deal with that yourself!
     * 
     * @param screen
     *            GuiModScreen instance or subclass to show with parent screen
     *            already set to current screen.
     */
    public static void show(GuiModScreen screen) {
        Minecraft m = ModSettings.getMcinst();
        m.displayGuiScreen(screen);
        screen.setActive();
    }

    /**
     * Show a screen - TWL widget version. This is the recommended way to show a
     * TWL widget as a screen.
     * 
     * @param screen
     *            widget to show - will be sized to size of screen when twl was
     *            started.
     */
    public static void show(Widget screen) {
        GuiModScreen.show(new GuiModScreen(GuiModScreen.currentScreen, screen));
    }

    /**
     * The type of background to draw. 0 is the default background. If you are
     * in game, it will shade the screen grey before it draws your widget, like
     * when you pause the game normally. If you are not in game, it will show
     * the dirt background, like on the main menu. 1 is force the main menu
     * (dirt) background. Anything else is no background, completely clear.
     */
    public int backgroundType = 0;
    /**
     * Actual main widget of this GuiModScreen
     */
    public Widget mainwidget;
    /**
     * Reference to parent screen, is used by GuiModScreen#back()
     * 
     * @see GuiModScreen#back()
     */
    public GuiScreen parentScreen;

    /**
     * Only use this constructor from subclasses. does not take a widget, so
     * that the subclass can build the widget before storing it. put your main
     * widget in mainwidget, of course.
     * 
     * @param screen
     *            parent screen
     * 
     * @see GuiModScreen#back()
     */
    protected GuiModScreen(GuiScreen screen) {
        parentScreen = screen;
        GuiModScreen.currentScreen = this;
        allowUserInput = false;
    }

    /**
     * main constructor, to be used if you are instantiating this class.
     * 
     * @param screen
     *            parent screen - make sure this is right!
     * @param widget
     *            main widget to display
     */
    public GuiModScreen(GuiScreen screen, Widget widget) {
        mainwidget = widget;
        parentScreen = screen;
        GuiModScreen.currentScreen = this;
        allowUserInput = false;
    }

    @Override
    public void drawScreen(int var1, int var2, float var3) {
        switch (backgroundType) {
        case 0: {
            drawDefaultBackground();
            break;
        }
        case 1: {
            //drawBackground(0);
            drawDefaultBackground();
            break;
        }
        default: {
            break;
        }
        }
        LWJGLRenderer var4 = (LWJGLRenderer) GuiWidgetScreen.getInstance().gui
                .getRenderer();
        ScaledResolution var5 = new ScaledResolution(
                GuiWidgetScreen.getInstance().minecraftInstance,
                GuiWidgetScreen.getInstance().minecraftInstance.displayWidth,
                GuiWidgetScreen.getInstance().minecraftInstance.displayHeight);
        RenderScale.scale = var5.getScaleFactor();
        var4.syncViewportSize();
        GuiWidgetScreen.getInstance().gui.update();
    }

    @Override
    public void handleInput() {
    }

    private void setActive() {
        GuiWidgetScreen.getInstance().setScreen(mainwidget);
    }
}
