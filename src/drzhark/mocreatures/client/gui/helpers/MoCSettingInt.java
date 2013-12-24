package drzhark.mocreatures.client.gui.helpers;

import drzhark.guiapi.setting.SettingInt;
import drzhark.mocreatures.configuration.MoCConfiguration;

public class MoCSettingInt extends SettingInt {

    public String category;
    private MoCConfiguration config;
    /**
     * A constructor for SettingInt. Defaults settings to default value of 0,
     * range of 1-100, and a step of 1.
     * 
     * @param title
     *            The backend name for this setting.
     */
    public MoCSettingInt(String title) {
        super(title, 0, 0, 1, 100);
    }

    /**
     * A constructor for SettingInt. Defaults settings to range of 1-100, and a
     * step of 1.
     * 
     * @param title
     *            The backend name for this setting.
     * @param defValue
     *            The default value.
     */
    public MoCSettingInt(String title, int defValue) {
        super(title, defValue, 0, 1, 100);
    }

    /**
     * A constructor for SettingInt. Defaults settings to a step of 1.
     * 
     * @param title
     *            The backend name for this setting.
     * @param defValue
     *            The default value.
     * @param minValue
     *            The minimum value.
     * @param maxValue
     *            The maximum value.
     */
    public MoCSettingInt(String title, int defValue, int minValue, int maxValue) {
        super(title, defValue, minValue, 1, maxValue);
    }

    /**
     * A constructor for SettingInt.
     * 
     * @param title
     *            The backend name for this setting.
     * @param defValue
     *            The default value.
     * @param minValue
     *            The minimum value.
     * @param stepValue
     *            The step value.
     * @param maxValue
     *            The maximum value.
     */
    public MoCSettingInt(String title, int defValue, int minValue, int stepValue, int maxValue) {
        super(title, defValue, minValue, stepValue, maxValue);
    }

    public MoCSettingInt(MoCConfiguration config, String cat, String title, int defValue, int minValue, int stepValue, int maxValue) {
        super(title, defValue, minValue, stepValue, maxValue);
        this.category = cat;
        this.config = config;
    }

    @Override
    public void set(Integer v, String context) {
        if (stepValue > 1) {
            values.put(
                    context,
                    (int) (Math.round((float) v / (float) stepValue) * (float) stepValue));
        } else {
            values.put(context, v);
        }

        if (parent != null) {
            ((MoCSettings)parent).save(context, this.backendName, this.category, this.config);
        }

        if (displayWidget != null) {
            displayWidget.update();
        }
    }
}