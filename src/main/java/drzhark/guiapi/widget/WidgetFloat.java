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
     * @param setting The backing setting.
     * @param title The text that will show on the WidgetSlider.
     */
    public WidgetFloat(SettingFloat setting, String title) {
        this(setting, title, 2);
    }

    /**
     * This creates a new WidgetInt using the SettingInt and String provided, as
     * well as setting how many decimal places to use.
     *
     * @param setting The backing setting.
     * @param title The text that will show on the WidgetSlider.
     */
    public WidgetFloat(SettingFloat setting, String title, int _decimalPlaces) {
        super(title);
        setTheme("");
        this.decimalPlaces = _decimalPlaces;
        this.settingReference = setting;
        this.settingReference.displayWidget = this;
        SimpleFloatModel smodel =
                new SimpleFloatModel(this.settingReference.minimumValue, this.settingReference.maximumValue, this.settingReference.get());
        smodel.addCallback(this);
        this.slider = new WidgetSlider(smodel);
        if ((this.settingReference.stepValue > 0.0f) && (this.settingReference.stepValue <= this.settingReference.maximumValue)) {
            this.slider.setStepSize(this.settingReference.stepValue);
        }
        this.slider.setFormat(String.format("%s: %%.%df", this.niceName, this.decimalPlaces));
        add(this.slider);
        update();
    }

    @Override
    public void addCallback(Runnable paramRunnable) {
        this.slider.getModel().addCallback(paramRunnable);
    }

    @Override
    public void removeCallback(Runnable paramRunnable) {
        this.slider.getModel().removeCallback(paramRunnable);
    }

    @Override
    public void run() {
        this.settingReference.set(this.slider.getValue(), ModSettingScreen.guiContext);
    }

    @Override
    public void update() {
        this.slider.setValue(this.settingReference.get(ModSettingScreen.guiContext));
        this.slider.setMinMaxValue(this.settingReference.minimumValue, this.settingReference.maximumValue);
        this.slider.setFormat(String.format("%s: %%.%df", this.niceName, this.decimalPlaces));
    }

    @Override
    public String userString() {
        String l = String.format("%02d", this.decimalPlaces);
        return String.format("%s: %." + l + "f", this.niceName, this.settingReference);
    }
}
