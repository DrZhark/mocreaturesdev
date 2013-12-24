package drzhark.guiapi.widget;

import java.util.ArrayList;

import de.matthiasmann.twl.Widget;

/**
 * This is the base class for Widgets that are supposed to be front ends for
 * Settings.
 * 
 * @author lahwran
 */
public abstract class WidgetSetting extends Widget {
    /**
     * This is a list of all WidgetSetting instances.
     */
    public static ArrayList<WidgetSetting> all = new ArrayList<WidgetSetting>();

    /**
     * This updates all Widgets with the backing setting's current values.
     */
    public static void updateAll() {
        for (int i = 0; i < WidgetSetting.all.size(); i++) {
            WidgetSetting.all.get(i).update();
        }
    }

    /**
     * The name that will be shown on the widget.
     */
    public String niceName;

    /**
     * This sets the Nice Name and adds itself to the list of instances. Note
     * this class is abstract, so you will not be using this constructor.
     * 
     * @param nicename
     *            The nice name for this WidgetSetting.
     */
    public WidgetSetting(String nicename) {
        niceName = nicename;
        WidgetSetting.all.add(this);
    }

    @Override
    public void add(Widget child) {
        String T = child.getTheme();
        if (T.length() == 0) {
            child.setTheme("/-defaults");
        } else if (!T.substring(0, 1).equals("/")) {
            child.setTheme("/" + T);
        }
        super.add(child);
    }

    /**
     * This adds a callback to the displayed widget (Button, Slider, etc)
     * 
     * @param paramRunnable
     *            The Runnable callback you wish to call if the value changes.
     */
    public abstract void addCallback(Runnable paramRunnable);

    @Override
    public void layout() {
        for (int i = 0; i < getNumChildren(); i++) {
            Widget w = getChild(i);
            w.setPosition(getX(), getY());
            w.setSize(getWidth(), getHeight());
        }
    }

    /**
     * This removes a callback to the displayed widget (Button, Slider, etc) if
     * you previously set one up.
     * 
     * @param paramRunnable
     *            The Runnable callback you wish to remove.
     */
    public abstract void removeCallback(Runnable paramRunnable);

    /**
     * This method updates the widget with the backing setting store.
     */
    public abstract void update();

    /**
     * This returns a clean string that shows the Nice Name and the current
     * value.
     * 
     * @return A descriptor string.
     */
    public abstract String userString();
}
