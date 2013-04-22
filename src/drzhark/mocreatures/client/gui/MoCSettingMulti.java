package drzhark.mocreatures.client.gui;

import java.util.List;

import sharose.mods.guiapi.ModSettings;
import sharose.mods.guiapi.SettingMulti;

public class MoCSettingMulti extends SettingMulti {

    public String category; // reference to category this setting is linked to
    public String previousValue;
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

    public MoCSettingMulti(String cat, String title, String... labelValues) {
        super(title, 0, labelValues);
        this.category = cat;
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
            ((MoCSettings)parent).save(context, this.backendName, this.category);
        }
        if (displayWidget != null) {
            displayWidget.update();
        }
    }
}