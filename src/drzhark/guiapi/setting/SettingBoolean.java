package drzhark.guiapi.setting;

/**
 * This is the Setting type for Booleans.
 * 
 * @author lahwran
 */
public class SettingBoolean extends Setting<Boolean> {
    /**
     * This is the constructor for SettingBoolean. It sets the default value as
     * false.
     * 
     * @param name
     *            The backend name for this setting.
     */
    public SettingBoolean(String name) {
        this(name, false);
    }

    /**
     * This is the constructor for SettingBoolean.
     * 
     * @param name
     *            The backend name for this setting.
     * @param defValue
     *            The default value.
     */
    public SettingBoolean(String name, Boolean defValue) {
        defaultValue = defValue;
        values.put("", defaultValue);
        backendName = name;
    }

    @Override
    public void fromString(String s, String context) {
        values.put(context, s.equals("true"));
        if (displayWidget != null) {
            displayWidget.update();
        }
    }

    @Override
    public Boolean get(String context) {
        if (values.get(context) != null) {
            return values.get(context);
        } else if (values.get("") != null) {
            return values.get("");
        } else {
            return defaultValue;
        }
    }

    @Override
    public void set(Boolean v, String context) {
        values.put(context, v);
        if (parent != null) {
            parent.save(context);
        }
        if (displayWidget != null) {
            displayWidget.update();
        }
    }

    @Override
    public String toString(String context) {
        return (get(context) ? "true" : "false");
    }
}