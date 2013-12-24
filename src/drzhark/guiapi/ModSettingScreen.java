package drzhark.guiapi;

import java.util.ArrayList;

import de.matthiasmann.twl.Widget;
import drzhark.guiapi.widget.WidgetClassicTwocolumn;
import drzhark.guiapi.widget.WidgetSimplewindow;
import drzhark.guiapi.widget.WidgetSinglecolumn;

/**
 * This is the class that GuiModSelect uses to show Subscreens. Create one of
 * these to create your own subscreen, from which you can add widgets to. This
 * automatically registers the button on the Mod Setting Screen.
 * 
 * @author lahwran
 */
public class ModSettingScreen {
    /**
     * The current context.
     */
    public static String guiContext = "";
    /**
     * The list of currently registered ModScreens.
     */
    public static ArrayList<ModSettingScreen> modScreens = new ArrayList<ModSettingScreen>();
    /**
     * title to show on button to this modscreen
     */
    public String buttonTitle;
    /**
     * name to show at top of screen
     */
    public String niceName;
    /**
     * the main widget to pass into GuiModScreen.show()
     */
    public Widget theWidget;
    /**
     * the column widget to show the child widgets in
     */
    public WidgetClassicTwocolumn widgetColumn;

    /**
     * convenience constructor for when you want to show the same name on the
     * button and screen title
     * 
     * @param name
     *            mod nice name
     */
    public ModSettingScreen(String name) {
        this(name, name);
    }

    /**
     * The main Constructor for ModSettingScreen. Creates a WidgetSimplewindow
     * as the main Widget, sets the title for said WidgetSimplewindow, and
     * registers this ModSettingScreen on the settings screen.
     * 
     * @param nicename
     *            The title that will be on the WidgetSimplewindow.
     * @param buttontitle
     *            button-to-screen title
     */
    public ModSettingScreen(String nicename, String buttontitle) {
        ModSettingScreen.modScreens.add(this);
        buttonTitle = buttontitle;
        niceName = nicename;
        widgetColumn = new WidgetClassicTwocolumn();
        theWidget = new WidgetSimplewindow(widgetColumn, niceName);
    }

    /**
     * An alternate Constructor for ModSettingScreen. Instead of creating a
     * WidgetSimplewindow, this simply lets you pass a widget of your choosing.
     * 
     * @param widget
     *            The main widget you want to use for this ModSettingScreen.
     * @param buttontitle
     *            button-to-screen title
     */
    public ModSettingScreen(Widget widget, String buttontitle) {
        ModSettingScreen.modScreens.add(this);
        buttonTitle = buttontitle;
        theWidget = widget;
    }

    /**
     * Add a widget
     * 
     * @param newwidget
     *            the widget to add
     */
    public void append(Widget newwidget) {
        if (widgetColumn != null) {
            widgetColumn.add(newwidget);
        } else {
            theWidget.add(newwidget);
        }
    }

    /**
     * Remove a widget
     * 
     * @param child
     *            widget to remove
     */
    public void remove(Widget child) {
        if (widgetColumn != null) {
            widgetColumn.removeChild(child);
        } else {
            theWidget.removeChild(child);
        }
    }

    /**
     * Changes the widgetColumn to or from WidgetClassicTwocolumn or
     * WidgetSinglecolumn.
     * 
     * @param value
     *            What to change it to. True for WidgetSinglecolumn, False for
     *            WidgetClassicTwocolumn.
     */
    public void setSingleColumn(Boolean value) {
        Boolean isSingle = WidgetSinglecolumn.class.isInstance(widgetColumn);
        if (isSingle == value) {
            return;
        }
        WidgetClassicTwocolumn w2 = (value ? new WidgetSinglecolumn()
                : new WidgetClassicTwocolumn());
        for (int i = 0; i < widgetColumn.getNumChildren(); i++) {
            w2.add(widgetColumn.getChild(i));
        }
        widgetColumn = w2;
        theWidget = new WidgetSimplewindow(widgetColumn,
                ((WidgetSimplewindow) theWidget).titleWidget.getText());
    }
}
