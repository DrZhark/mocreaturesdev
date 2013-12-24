package drzhark.mocreatures.client.gui.helpers;

import drzhark.guiapi.setting.SettingBoolean;
import drzhark.mocreatures.configuration.MoCConfiguration;

public class MoCSettingBoolean extends SettingBoolean {

    public String category; // reference to category this setting is linked to
    private MoCConfiguration config;
    /**
     * This is the constructor for SettingBoolean. It sets the default value as
     * false.
     * 
     * @param name
     *            The backend name for this setting.
     */
    public MoCSettingBoolean(String name) {
        super(name, false);
    }

    /**
     * This is the constructor for SettingBoolean.
     * 
     * @param name
     *            The backend name for this setting.
     * @param defValue
     *            The default value.
     */
    public MoCSettingBoolean(String name, Boolean defValue) {
        super(name, defValue);
    }

    public MoCSettingBoolean(MoCConfiguration config, String cat, String name, Boolean defValue) {
        super(name, defValue);
        this.category = cat;
        this.config = config;
    }

    @Override
    public void set(Boolean v, String context) {
        values.put(context, v);
        if (parent != null) {
            ((MoCSettings)parent).save(context, this.backendName, this.category, this.config);
        }
        if (displayWidget != null) {
            displayWidget.update();
        }
    }
}