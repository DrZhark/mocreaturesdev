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
     * @param title
     *            The backend name for this setting.
     */
    public SettingFloat(String title) {
        this(title, 0.0f, 0.0f, 0.1f, 1.0f);
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
    public SettingFloat(String title, float defValue) {
        this(title, defValue, 0.0f, 0.1f, 1.0f);
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
    public SettingFloat(String title, float defValue, float minValue,
            float maxValue) {
        this(title, defValue, minValue, 0.1f, maxValue);
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
    public SettingFloat(String title, float defValue, float minValue,
            float stepValue, float maxValue) {
        values.put("", defValue);
        defaultValue = defValue;
        minimumValue = minValue;
        this.stepValue = stepValue;
        maximumValue = maxValue;
        backendName = title;
        if (minimumValue > maximumValue) {
            float t = minimumValue;
            minimumValue = maximumValue;
            maximumValue = t;
        }
    }

    @Override
    public void fromString(String s, String context) {
        values.put(context, new Float(s));
        if (displayWidget != null) {
            displayWidget.update();
        }
    }

    @Override
    public Float get(String context) {
        if (values.get(context) != null) {
            return values.get(context);
        } else if (values.get("") != null) {
            return values.get("");
        } else {
            return defaultValue;
        }
    }

    @Override
    public void set(Float v, String context) {
        if (stepValue > 0) {
            values.put(context, Math.round(v / stepValue) * stepValue);
        } else {
            values.put(context, v);
        }
        if (parent != null) {
            parent.save(context);
        }
        if (displayWidget != null) {
            displayWidget.update();
        }
    }

    @Override
    public String toString(String context) {
        return "" + get(context);
    }
}