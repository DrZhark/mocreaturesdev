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
     * @param title The backend name for this setting.
     */
    public SettingInt(String title) {
        this(title, 0, 0, 1, 100);
    }

    /**
     * A constructor for SettingInt. Defaults settings to range of 1-100, and a
     * step of 1.
     *
     * @param title The backend name for this setting.
     * @param defValue The default value.
     */
    public SettingInt(String title, int defValue) {
        this(title, defValue, 0, 1, 100);
    }

    /**
     * A constructor for SettingInt. Defaults settings to a step of 1.
     *
     * @param title The backend name for this setting.
     * @param defValue The default value.
     * @param minValue The minimum value.
     * @param maxValue The maximum value.
     */
    public SettingInt(String title, int defValue, int minValue, int maxValue) {
        this(title, defValue, minValue, 1, maxValue);
    }

    /**
     * A constructor for SettingInt.
     *
     * @param title The backend name for this setting.
     * @param defValue The default value.
     * @param minValue The minimum value.
     * @param stepValue The step value.
     * @param maxValue The maximum value.
     */
    public SettingInt(String title, int defValue, int minValue, int stepValue, int maxValue) {
        this.values.put("", defValue);
        this.defaultValue = defValue;
        this.minimumValue = minValue;
        this.stepValue = stepValue;
        this.maximumValue = maxValue;
        this.backendName = title;
        if (this.minimumValue > this.maximumValue) {
            int t = this.minimumValue;
            this.minimumValue = this.maximumValue;
            this.maximumValue = t;
        }
    }

    @Override
    public void fromString(String s, String context) {
        this.values.put(context, new Integer(s));
        if (this.displayWidget != null) {
            this.displayWidget.update();
        }
        ModSettings.dbgout("fromstring " + s);
    }

    @Override
    public Integer get(String context) {
        if (this.values.get(context) != null) {
            return this.values.get(context);
        } else if (this.values.get("") != null) {
            return this.values.get("");
        } else {
            return this.defaultValue;
        }
    }

    @Override
    public void set(Integer v, String context) {
        ModSettings.dbgout("set " + v);
        if (this.stepValue > 1) {
            this.values.put(context, (int) (Math.round((float) v / (float) this.stepValue) * (float) this.stepValue));
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
