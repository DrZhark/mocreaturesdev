package drzhark.guiapi.setting;

import java.util.HashMap;

import de.matthiasmann.twl.Widget;
import drzhark.guiapi.ModSettings;
import drzhark.guiapi.widget.WidgetSetting;

/**
 * This is the base class for Settings.
 * 
 * @author lahwran
 * @param <T>
 *            The type that this Setting will use.
 */
public abstract class Setting<T> extends Widget {
    /**
     * The name used by ModSettings to save and load the setting.
     */
    public String backendName;
    /**
     * The default value for this setting.
     */
    public T defaultValue;
    /**
     * A reference to the Widget that displays this setting.
     */
    public WidgetSetting displayWidget = null;
    /**
     * Reference to the ModSettings this Setting is a child of.
     */
    public ModSettings parent = null;
    /**
     * value. do not write directly if you want things to update!
     */
    public HashMap<String, T> values = new HashMap<String, T>();

    /**
     * This is the basic constructor for Setting. Internal use only.
     */
    public Setting() {
    }

    /**
     * Copies a setting from the source context to the destination context.
     * 
     * @param srccontext
     *            The source context to copy data from.
     * @param destcontext
     *            The destination context you would like to save the data to.
     */
    public void copyContext(String srccontext, String destcontext) {
        values.put(destcontext, values.get(srccontext));
    }

    /**
     * load back a string from toString()
     */
    public abstract void fromString(String s, String context);

    /**
     * Returns the setting for the current context.
     * 
     * @return The setting.
     */
    public T get() {
        return get(ModSettings.currentContext);
    }

    /**
     * Returns the setting for the specified context.
     * 
     * @param context
     *            The context to retrieve from.
     * @return The setting.
     */
    public abstract T get(String context);

    /**
     * Resets this setting for the current context, saving the default and
     * updating the display.
     */
    public void reset() {
        reset(ModSettings.currentContext);
    }

    /**
     * Resets this setting for the specified context, saving the default and
     * updating the display.
     * 
     * @param context
     *            The context to reset.
     */
    public void reset(String context) {
        set(defaultValue, context);
    }

    /**
     * Sets the value for this setting to the current context.
     * 
     * @param v
     *            The value.
     */
    public void set(T v) {
        set(v, ModSettings.currentContext);
    }

    /**
     * Sets the value for this setting to the specified context.
     * 
     * @param v
     *            The value.
     * @param context
     *            The context to set.
     */
    public abstract void set(T v, String context);

    /**
     * return string to save, called from ModSettings.save()
     */
    public abstract String toString(String context);
}