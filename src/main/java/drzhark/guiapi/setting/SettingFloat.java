package drzhark.guiapi.setting;

/**
 * This is the Setting type for Floats.
 *
 * @author lahwran
 */
public class SettingFloat extends Setting<Float> {

    /**
     * The maximum value.
     */
    public float maximumValue;
    /**
     * The minimum value.
     */
    public float minimumValue;
    /**
     * The step value.
     */
    public float stepValue;

    /**
     * A constructor for SettingFloat. Defaults settings to default value of 0,
     * range of 0.0-1.0, and a step of 0.1.
     *
     * @param title The backend name for this setting.
     */
    public SettingFloat(String title) {
        this(title, 0.0f, 0.0f, 0.1f, 1.0f);
    }

    /**
     * A constructor for SettingFloat. Defaults settings to range of 0.0-1.0 and
     * a step of 0.1.
     *
     * @param title The backend name for this setting.
     * @param defValue The default value.
     */
    public SettingFloat(String title, float defValue) {
        this(title, defValue, 0.0f, 0.1f, 1.0f);
    }

    /**
     * A constructor for SettingFloat. Defaults settings to a step of 0.1.
     *
     * @param title The backend name for this setting.
     * @param defValue The default value.
     * @param minValue The minimum value.
     * @param maxValue The maximum value.
     */
    public SettingFloat(String title, float defValue, float minValue, float maxValue) {
        this(title, defValue, minValue, 0.1f, maxValue);
    }

    /**
     * A constructor for SettingFloat.
     *
     * @param title The backend name for this setting.
     * @param defValue The default value.
     * @param minValue The minimum value.
     * @param stepValue The step value.
     * @param maxValue The maximum value.
     */
    public SettingFloat(String title, float defValue, float minValue, float stepValue, float maxValue) {
        this.values.put("", defValue);
        this.defaultValue = defValue;
        this.minimumValue = minValue;
        this.stepValue = stepValue;
        this.maximumValue = maxValue;
        this.backendName = title;
        if (this.minimumValue > this.maximumValue) {
            float t = this.minimumValue;
            this.minimumValue = this.maximumValue;
            this.maximumValue = t;
        }
    }

    @Override
    public void fromString(String s, String context) {
        this.values.put(context, new Float(s));
        if (this.displayWidget != null) {
            this.displayWidget.update();
        }
    }

    @Override
    public Float get(String context) {
        if (this.values.get(context) != null) {
            return this.values.get(context);
        } else if (this.values.get("") != null) {
            return this.values.get("");
        } else {
            return this.defaultValue;
        }
    }

    @Override
    public void set(Float v, String context) {
        if (this.stepValue > 0) {
            this.values.put(context, Math.round(v / this.stepValue) * this.stepValue);
        } else {
            this.values.put(context, v);
        }
        if (this.parent != null) {
            this.parent.save(context);
        }
        if (this.displayWidget != null) {
            this.displayWidget.update();
        }
    }

    @Override
    public String toString(String context) {
        return "" + get(context);
    }
}
