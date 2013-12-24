package drzhark.mocreatures.client.gui.helpers;

import drzhark.guiapi.setting.SettingMulti;
import drzhark.mocreatures.configuration.MoCConfiguration;

public class MoCSettingMulti extends SettingMulti {

    public String category; // reference to category this setting is linked to
    public String previousValue;
    public MoCConfiguration config;
    /**
     * A constructor for SettingMulti.
     * 
     * @param title
     *            The backend name for this setting.
     * @param defValue
     *            The default value for this Multi.
     * @param labelValues
     *            The text labels you would like this multi to have. Must have
     *            at least one.
     */
    public MoCSettingMulti(String title, int defValue, String... labelValues) {
        super(title, defValue, labelValues);
    }

    public MoCSettingMulti(MoCConfiguration config, String cat, String title, int defValue, String... labelValues) {
        super(title, defValue, labelValues);
        this.category = cat;
        this.config = config;
    }

    /**
     * A constructor for SettingMulti. Default value is 0, or the first label to
     * be defined.
     * 
     * @param title
     *            The backend name for this setting.
     * @param labelValues
     *            The text labels you would like this multi to have. Must have
     *            at least one.
     */
    public MoCSettingMulti(String title, String... labelValues) {
        super(title, 0, labelValues);
    }

    public MoCSettingMulti(MoCConfiguration config, String cat, String title, String... labelValues) {
        super(title, 0, labelValues);
        this.category = cat;
        this.config = config;
    }

    /**
     * Shifts the value forward for the specified context.
     * 
     * @param context
     *            The context to change.
     */
    public void next(String context) {
        this.previousValue = labelValues[get(context)];
        int tempvalue = get(context) + 1;
        while (tempvalue >= labelValues.length) {
            tempvalue -= labelValues.length;
        }
        set(tempvalue, context);
    }

    @Override
    public void set(Integer v, String context) {
        values.put(context, v);
        if (parent != null) {
            ((MoCSettings)parent).save(context, this.backendName, this.category, this.config);
        }
        if (displayWidget != null) {
            displayWidget.update();
        }
    }
}