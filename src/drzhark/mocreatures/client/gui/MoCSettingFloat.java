package drzhark.mocreatures.client.gui;

import sharose.mods.guiapi.SettingFloat;

public class MoCSettingFloat extends SettingFloat {

    public String category; // reference to category this setting is linked to
    /**
     * A constructor for SettingFloat. Defaults settings to default value of 0,
     * range of 0.0-1.0, and a step of 0.1.
     * 
     * @param title
     *            The backend name for this setting.
     */
    public MoCSettingFloat(String title) {
        super(title, 0.0f, 0.0f, 0.1f, 1.0f);
    }

    /**
     * A constructor for SettingFloat. Defaults settings to range of 0.0-1.0 and
     * a step of 0.1.
     * 
     * @param title
     *            The backend name for this setting.
     * @param defValue
     *            The default value.
     */
    public MoCSettingFloat(String title, float defValue) {
        super(title, defValue, 0.0f, 0.1f, 1.0f);
    }

    /**
     * A constructor for SettingFloat. Defaults settings to a step of 0.1.
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
    public MoCSettingFloat(String title, float defValue, float minValue,
            float maxValue) {
        super(title, defValue, minValue, 0.1f, maxValue);
    }

    /**
     * A constructor for SettingFloat.
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
    public MoCSettingFloat(String title, float defValue, float minValue,
            float stepValue, float maxValue) {
        super(title, defValue, minValue, stepValue, maxValue);
    }

    public MoCSettingFloat(String cat, String title, float defValue, float minValue,
            float stepValue, float maxValue) {
        super(title, defValue, minValue, stepValue, maxValue);
        this.category = cat;
    }

    @Override
    public void set(Float v, String context) {
        if (stepValue > 0) {
            //values.put(context, Math.round(v / stepValue) * stepValue);
            values.put(context, (float)(Math.round(v * 100.0) / 100.0)); // blood - force result to be 2 decimals
        } else {
            values.put(context, v);
        }
        if (parent != null) {
            ((MoCSettings)parent).save(context, this.backendName, this.category);
        }
        if (displayWidget != null) {
            displayWidget.update();
        }
    }
}