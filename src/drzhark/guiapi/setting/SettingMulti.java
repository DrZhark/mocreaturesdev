package drzhark.guiapi.setting;

import drzhark.guiapi.ModSettings;

/**
 * This is the Setting type for Multis.
 * 
 * @author lahwran
 */
public class SettingMulti extends Setting<Integer> {
    /**
     * A string array of labels for the button.
     */
    public String[] labelValues;

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
    public SettingMulti(String title, int defValue, String... labelValues) {
        if (labelValues.length == 0) {
            return;
        }
        values.put("", defValue);
        defaultValue = defValue;
        this.labelValues = labelValues;
        backendName = title;
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
    public SettingMulti(String title, String... labelValues) {
        this(title, 0, labelValues);
    }

    @Override
    public void fromString(String s, String context) {
        int x = -1;
        for (int i = 0; i < labelValues.length; i++) {
            if (labelValues[i].equals(s)) {
                x = i;
            }
        }
        if (x != -1) {
            values.put(context, x);
        } else {
            values.put(context, new Float(s).intValue());
        }
        ModSettings.dbgout("fromstring multi " + s);
        if (displayWidget != null) {
            displayWidget.update();
        }
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

    /**
     * Helper to get the text label for the current context and value.
     * 
     * @return The label.
     */
    public String getLabel() {
        return labelValues[get()];
    }

    /**
     * Helper to get the text label for the specified context and value.
     * 
     * @param context
     *            The context to get the value from.
     * @return The label.
     */
    public String getLabel(String context) {
        return labelValues[get(context)];
    }

    /**
     * Shifts the value forward for the current context.
     */
    public void next() {
        next(ModSettings.currentContext);
    }

    /**
     * Shifts the value forward for the specified context.
     * 
     * @param context
     *            The context to change.
     */
    public void next(String context) {
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
            parent.save(context);
        }
        if (displayWidget != null) {
            displayWidget.update();
        }
    }

    /**
     * Sets the value for this setting to the current context.
     * 
     * @param v
     *            The value, in the label representation.
     */
    public void set(String v) {
        set(v, ModSettings.currentContext);
    }

    /**
     * Sets the value for this setting to the specified context.
     * 
     * @param v
     *            The value, in the label representation.
     * @param context
     *            The context to set.
     */
    public void set(String v, String context) {
        int x = -1;
        for (int i = 0; i < labelValues.length; i++) {
            if (labelValues[i].equals(v)) {
                x = i;
            }
        }
        if (x != -1) {
            set(x, context);
        }
    }

    @Override
    public String toString(String context) {
        return labelValues[get(context)];
    }
}