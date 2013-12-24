package drzhark.guiapi.setting;

import org.lwjgl.input.Keyboard;

import drzhark.guiapi.ModSettings;



/**
 * This is the Setting type for Keys.
 * 
 * @author lahwran
 */
public class SettingKey extends Setting<Integer> {
    /**
     * Constructor for SettingKey.
     * 
     * @param title
     *            The backend name for this setting.
     * @param key
     *            The key you want as default, as a int keycode.
     */
    public SettingKey(String title, int key) {
        defaultValue = key;
        values.put("", key);
        backendName = title;
    }

    /**
     * Constructor for SettingKey.
     * 
     * @param title
     *            The backend name for this setting.
     * @param key
     *            key The key you want as default, as a string.
     */
    public SettingKey(String title, String key) {
        this(title, Keyboard.getKeyIndex(key));
    }

    @Override
    public void fromString(String s, String context) {
        if (s.equals("UNBOUND")) {
            values.put(context, Keyboard.KEY_NONE);
        } else {
            values.put(context, Keyboard.getKeyIndex(s));
        }
        if (displayWidget != null) {
            displayWidget.update();
        }
    }

    @Override
    public Integer get(String context) {
        if (values.get(context) != null) {
            return values.get(context);
        } else if (values.get("") != null) {
            return values.get("");
        } else {
            return defaultValue;
        }
    }

    /**
     * An easy helper to see if the current key is down.
     * 
     * @return true if the key specified to this setting (In the current
     *         context) is down.
     */
    public boolean isKeyDown() {
        return isKeyDown(ModSettings.currentContext);
    }

    /**
     * An easy helper to see if the current key is down.
     * 
     * @param context
     *            The context to get the key from.
     * @return true if the key specified to this setting is down.
     */
    public boolean isKeyDown(String context) {
        if (get(context) != -1) {
            return Keyboard.isKeyDown(get(context));
        }
        return false;
    }

    @Override
    public void set(Integer v, String context) {
        values.put(context, v);
        if (parent != null) {
            parent.save(context);
        }
        if (displayWidget != null) {
            displayWidget.update();
        }
    }

    /**
     * Sets the value for this setting to the current context.
     * 
     * @param v
     *            The value, as a string.
     */
    public void set(String v) {
        set(v, ModSettings.currentContext);
    }

    /**
     * Sets the value for this setting to the specified context.
     * 
     * @param v
     *            The value, as a string.
     * @param context
     *            The context to set.
     */
    public void set(String v, String context) {
        set(Keyboard.getKeyIndex(v), context);
    }

    @Override
    public String toString(String context) {
        return Keyboard.getKeyName(get(context));
    }
}