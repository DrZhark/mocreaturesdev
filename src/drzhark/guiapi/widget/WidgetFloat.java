package drzhark.guiapi.widget;

import de.matthiasmann.twl.model.SimpleFloatModel;
import drzhark.guiapi.ModSettingScreen;
import drzhark.guiapi.setting.SettingFloat;

/**
 * This is the Widget for Float settings. It uses a WidgetSlider to display to
 * the user.
 * 
 * @author lahwran
 */
public class WidgetFloat extends WidgetSetting implements Runnable {
    /**
     * The number of decimal places to display to the user.
     */
    public int decimalPlaces;
    /**
     * The reference to the SettingInt that this WidgetInt uses.
     */
    public SettingFloat settingReference;
    /**
     * The reference to the underlying WidgetSlider.
     */
    public WidgetSlider slider;

    /**
     * This creates a new WidgetInt using the SettingInt and String provided. It
     * defaults the decimal places to display at 2.
     * 
     * @param setting
     *            The backing setting.
     * @param title
     *            The text that will show on the WidgetSlider.
     */
    public WidgetFloat(SettingFloat setting, String title) {
        this(setting, title, 2);
    }

    /**
     * This creates a new WidgetInt using the SettingInt and String provided, as
     * well as setting how many decimal places to use.
     * 
     * @param setting
     *            The backing setting.
     * @param title
     *            The text that will show on the WidgetSlider.
     */
    public WidgetFloat(SettingFloat setting, String title, int _decimalPlaces) {
        super(title);
        setTheme("");
        decimalPlaces = _decimalPlaces;
        settingReference = setting;
        settingReference.displayWidget = this;
        SimpleFloatModel smodel = new SimpleFloatModel(
                settingReference.minimumValue, settingReference.maximumValue,
                settingReference.get());
        smodel.addCallback(this);
        slider = new WidgetSlider(smodel);
        if ((settingReference.stepValue > 0.0f)
                && (settingReference.stepValue <= settingReference.maximumValue)) {
            slider.setStepSize(settingReference.stepValue);
        }
        slider.setFormat(String.format("%s: %%.%df", niceName, decimalPlaces));
        add(slider);
        update();
    }

    @Override
    public void addCallback(Runnable paramRunnable) {
        slider.getModel().addCallback(paramRunnable);
    }

    @Override
    public void removeCallback(Runnable paramRunnable) {
        slider.getModel().removeCallback(paramRunnable);
    }

    @Override
    public void run() {
        settingReference.set(slider.getValue(), ModSettingScreen.guiContext);
    }

    @Override
    public void update() {
        slider.setValue(settingReference.get(ModSettingScreen.guiContext));
        slider.setMinMaxValue(settingReference.minimumValue, settingReference.maximumValue);
        slider.setFormat(String.format("%s: %%.%df", niceName, decimalPlaces));
    }

    @Override
    public String userString() {
        String l = String.format("%02d", decimalPlaces);
        return String.format("%s: %." + l + "f", niceName, settingReference);
    }
}
