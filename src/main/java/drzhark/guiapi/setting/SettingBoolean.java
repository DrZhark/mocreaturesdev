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
     * @param name The backend name for this setting.
     */
    public SettingBoolean(String name) {
        this(name, false);
    }

    /**
     * This is the constructor for SettingBoolean.
     *
     * @param name The backend name for this setting.
     * @param defValue The default value.
     */
    public SettingBoolean(String name, Boolean defValue) {
        this.defaultValue = defValue;
        this.values.put("", this.defaultValue);
        this.backendName = name;
    }

    @Override
    public void fromString(String s, String context) {
        this.values.put(context, s.equals("true"));
        if (this.displayWidget != null) {
            this.displayWidget.update();
        }
    }

    @Override
    public Boolean get(String context) {
        if (this.values.get(context) != null) {
            return this.values.get(context);
        } else if (this.values.get("") != null) {
            return this.values.get("");
        } else {
            return this.defaultValue;
        }
    }

    @Override
    public void set(Boolean v, String context) {
        this.values.put(context, v);
        if (this.parent != null) {
            this.parent.save(context);
        }
        if (this.displayWidget != null) {
            this.displayWidget.update();
        }
    }

    @Override
    public String toString(String context) {
        return (get(context) ? "true" : "false");
    }
}
