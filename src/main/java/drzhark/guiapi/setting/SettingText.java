package drzhark.guiapi.setting;

/**
 * This is the Setting type for Text.
 *
 * @author lahwran
 */
public class SettingText extends Setting<String> {

    /**
     * A constructor for SettingText.
     *
     * @param title The backend name for this setting.
     * @param defaulttext The default text for this Setting.
     */
    public SettingText(String title, String defaulttext) {
        this.values.put("", defaulttext);
        this.defaultValue = defaulttext;
        this.backendName = title;
    }

    @Override
    public void fromString(String s, String context) {
        this.values.put(context, s);
        if (this.displayWidget != null) {
            this.displayWidget.update();
        }
    }

    @Override
    public String get(String context) {
        if (this.values.get(context) != null) {
            return this.values.get(context);
        } else if (this.values.get("") != null) {
            return this.values.get("");
        } else {
            return this.defaultValue;
        }
    }

    @Override
    public void set(String v, String context) {
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
        return get(context);
    }
}
