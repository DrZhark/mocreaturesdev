package drzhark.guiapi.setting;

import drzhark.guiapi.ModSettings;
import org.lwjgl.input.Keyboard;

/**
 * This is the Setting type for Keys.
 *
 * @author lahwran
 */
public class SettingKey extends Setting<Integer> {

    /**
     * Constructor for SettingKey.
     *
     * @param title The backend name for this setting.
     * @param key The key you want as default, as a int keycode.
     */
    public SettingKey(String title, int key) {
        this.defaultValue = key;
        this.values.put("", key);
        this.backendName = title;
    }

    /**
     * Constructor for SettingKey.
     *
     * @param title The backend name for this setting.
     * @param key key The key you want as default, as a string.
     */
    public SettingKey(String title, String key) {
        this(title, Keyboard.getKeyIndex(key));
    }

    @Override
    public void fromString(String s, String context) {
        if (s.equals("UNBOUND")) {
            this.values.put(context, Keyboard.KEY_NONE);
        } else {
            this.values.put(context, Keyboard.getKeyIndex(s));
        }
        if (this.displayWidget != null) {
            this.displayWidget.update();
        }
    }

    @Override
    public Integer get(String context) {
        if (this.values.get(context) != null) {
            return this.values.get(context);
        } else if (this.values.get("") != null) {
            return this.values.get("");
        } else {
            return this.defaultValue;
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
     * @param context The context to get the key from.
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
        this.values.put(context, v);
        if (this.parent != null) {
            this.parent.save(context);
        }
        if (this.displayWidget != null) {
            this.displayWidget.update();
        }
    }

    /**
     * Sets the value for this setting to the current context.
     *
     * @param v The value, as a string.
     */
    public void set(String v) {
        set(v, ModSettings.currentContext);
    }

    /**
     * Sets the value for this setting to the specified context.
     *
     * @param v The value, as a string.
     * @param context The context to set.
     */
    public void set(String v, String context) {
        set(Keyboard.getKeyIndex(v), context);
    }

    @Override
    public String toString(String context) {
        return Keyboard.getKeyName(get(context));
    }
}
