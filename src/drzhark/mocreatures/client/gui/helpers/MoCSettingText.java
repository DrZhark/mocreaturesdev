package drzhark.mocreatures.client.gui.helpers;

import drzhark.mocreatures.client.gui.SettingText;
import drzhark.mocreatures.configuration.MoCConfiguration;

public class MoCSettingText extends SettingText {

    public String category; // reference to category this setting is linked to
    private MoCConfiguration config;
    /**
     * A constructor for SettingText.
     * 
     * @param title
     *            The backend name for this setting.
     * @param defaulttext
     *            The default text for this Setting.
     */
    public MoCSettingText(String title, String defaulttext) {
        super(title, defaulttext);
    }

    public MoCSettingText(MoCConfiguration config, String title, String defaulttext) {
        super(title, defaulttext);
        this.config = config;
    }

    public MoCSettingText(MoCConfiguration config, String cat, String title, String defaulttext) {
        super(title, defaulttext);
        this.category = cat;
        this.config = config;
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
            ((MoCSettings)parent).save(context, this.backendName, this.category, this.config); // MoC - include setting name so we can avoid saving all settings
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