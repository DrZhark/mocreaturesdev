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
     * @param title
     *            The backend name for this setting.
     * @param defaulttext
     *            The default text for this Setting.
     */
    public SettingText(String title, String defaulttext) {
        values.put("", defaulttext);
        defaultValue = defaulttext;
        backendName = title;
    }

    @Override
    public void fromString(String s, String context) {
        values.put(context, s);
        if (displayWidget != null) {
            displayWidget.update();
        }
    }

    @Override
    public String get(String context) {
        if (values.get(context) != null) {
            return values.get(context);
        } else if (values.get("") != null) {
            return values.get("");
        } else {
            return defaultValue;
        }
    }

    @Override
    public void set(String v, String context) {
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
        return get(context);
    }
}