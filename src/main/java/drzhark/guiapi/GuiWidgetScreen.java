package drzhark.guiapi;

import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.input.lwjgl.LWJGLInput;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.theme.ThemeManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

/**
 * TWL Widget that switches out child widgets. the Minecraft gui end and twl
 * rendering is managed from GuiModScreen.
 *
 * @author lahwran
 */
public class GuiWidgetScreen extends Widget {

    /**
     * The initialized instance of GuiWidgetScreen.
     */
    public static GuiWidgetScreen instance;
    /**
     * The height of the screen that the widget will render on.
     */
    public static int screenheight;
    /**
     * The width of the screen that the widget will render on.
     */
    public static int screenwidth;

    public static URL themeURL;

    /**
     * get the instance of GuiWidget, creating it if needed
     *
     * @return GuiWidgetScreen singleton
     */
    public static GuiWidgetScreen getInstance() {
        if (GuiWidgetScreen.instance != null) {
            return GuiWidgetScreen.instance;
        }

        try {
            GuiWidgetScreen.instance = new GuiWidgetScreen();
            GuiWidgetScreen.instance.renderer = new LWJGLRenderer();
            String themename = "gui/twlGuiTheme.xml";
            GuiWidgetScreen.instance.gui = new GUI(GuiWidgetScreen.instance, GuiWidgetScreen.instance.renderer, new LWJGLInput());

            GuiWidgetScreen.themeURL = new URL("classloader", "", -1, themename, new URLStreamHandler() {

                @Override
                protected URLConnection openConnection(URL paramURL) throws IOException {
                    String file = paramURL.getFile();
                    if (file.startsWith("/")) {
                        file = file.substring(1);
                    }
                    // System.out.println("file path = " + file);
                    return GuiWidgetScreen.class.getClassLoader().getResource(file).openConnection();
                }
            });

            //GuiWidgetScreen.themeURL = new URL("file:\\G:\\MineCraft\\GitHub\\GuiAPI\\theme\\twlGuiTheme.xml");

            GuiWidgetScreen.instance.theme = ThemeManager.createThemeManager(GuiWidgetScreen.themeURL, GuiWidgetScreen.instance.renderer);
            if (GuiWidgetScreen.instance.theme == null) {
                throw new RuntimeException("I don't think you installed the theme correctly ...");
            }
            GuiWidgetScreen.instance.setTheme("");
            GuiWidgetScreen.instance.gui.applyTheme(GuiWidgetScreen.instance.theme);
            GuiWidgetScreen.instance.minecraftInstance = ModSettings.getMcinst();
            GuiWidgetScreen.instance.screenSize =
                    new ScaledResolution(GuiWidgetScreen.instance.minecraftInstance);
        } catch (Throwable e) {
            e.printStackTrace();
            RuntimeException e2 = new RuntimeException("error loading theme");
            e2.initCause(e);
            throw e2;
        }
        return GuiWidgetScreen.instance;
    }

    /**
     * The widget that is currently displayed.
     */
    public Widget currentWidget = null;
    /**
     * This is a reference to a TWL class that is used to render the widgets.
     */
    public GUI gui = null;
    /**
     * This is a reference to Minecraft.
     */
    public Minecraft minecraftInstance;
    /**
     * This is the rendered used by TWL.
     */
    public LWJGLRenderer renderer = null;
    /**
     * This is the ScaledResolution class that is used to scale all of the
     * widgets.
     */
    public ScaledResolution screenSize = null;
    /**
     * This the the ThemeManager for GuiAPI.
     */
    public ThemeManager theme = null;

    /**
     * This creates a new instance of GuiWidgetScreen. It should only be used
     * internally. Please use the static method getInstance() instead.
     */
    public GuiWidgetScreen() {
    }

    @Override
    public void layout() {
        this.screenSize = new ScaledResolution(this.minecraftInstance);
        if (this.currentWidget != null) {
            GuiWidgetScreen.screenwidth = this.screenSize.getScaledWidth();
            GuiWidgetScreen.screenheight = this.screenSize.getScaledHeight();
            this.currentWidget.setSize(GuiWidgetScreen.screenwidth, GuiWidgetScreen.screenheight);
            this.currentWidget.setPosition(0, 0);
        }
    }

    /**
     * Removes all children and clears the current widget.
     */
    public void resetScreen() {
        removeAllChildren();
        this.currentWidget = null;
    }

    /**
     * to be called only from GuiModScreen, sets the widget to display.
     * GuiModScreen manages this.
     *
     * @param widget widget to display
     */
    public void setScreen(Widget widget) {
        this.gui.resyncTimerAfterPause();
        this.gui.clearKeyboardState();
        this.gui.clearMouseState();
        removeAllChildren();
        add(widget);
        GuiApiFontHelper.resyncCustomFonts();

        this.currentWidget = widget;
    }
}
