package drzhark.guiapi.setting;

import drzhark.guiapi.ModSettings;

/**
 * This is the Setting type for Ints.
 * 
 * @author lahwran
 */
public class SettingInt extends Setting<Integer> {
    /**
     * The maximum value.
     */
    public int maximumValue;
    /**
     * The minimum value.
     */
    public int minimumValue;
    /**
     * The step value.
     */
    public int stepValue;

    /**
     * A constructor for SettingInt. Defaults settings to default value of 0,
     * range of 1-100, and a step of 1.
     * 
     * @param title
     *            The backend name for this setting.
     */
    public SettingInt(String title) {
        this(title, 0, 0, 1, 100);
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
    public SettingInt(String title, int defValue) {
        this(title, defValue, 0, 1, 100);
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
    public SettingInt(String title, int defValue, int minValue, int maxValue) {
        this(title, defValue, minValue, 1, maxValue);
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
    public SettingInt(String title, int defValue, int minValue, int stepValue,
            int maxValue) {
        values.put("", defValue);
        defaultValue = defValue;
        minimumValue = minValue;
        this.stepValue = stepValue;
        maximumValue = maxValue;
        backendName = title;
        if (minimumValue > maximumValue) {
            int t = minimumValue;
            minimumValue = maximumValue;
            maximumValue = t;
        }
    }

    @Override
    public void fromString(String s, String context) {
        values.put(context, new Integer(s));
        if (displayWidget != null) {
            displayWidget.update();
        }
        ModSettings.dbgout("fromstring " + s);
    }

    @Override
    public Integer get(String context) {
        if (values.get(context) != null) {
            return values.get(context);
        } else if (values.get("") != null) {
            return values.get("");
        } else {
            return defaultValue;
        }
    }

    @Override
    public void set(Integer v, String context) {
        ModSettings.dbgout("set " + v);
        if (stepValue > 1) {
            values.put(
                    context,
                    (int) (Math.round((float) v / (float) stepValue) * (float) stepValue));
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