package drzhark.guiapi.widget;

import de.matthiasmann.twl.model.SimpleFloatModel;
import drzhark.guiapi.ModSettingScreen;
import drzhark.guiapi.ModSettings;
import drzhark.guiapi.setting.SettingInt;

/**
 * This is the Widget for Integer settings. It uses a WidgetSlider to display to
 * the user.
 *
 * @author lahwran
 */
public class WidgetInt extends WidgetSetting implements Runnable {

    /**
     * The reference to the SettingInt that this WidgetInt uses.
     */
    public SettingInt settingReference;
    /**
     * The reference to the underlying WidgetSlider.
     */
    public WidgetSlider slider;

    /**
     * This creates a new WidgetInt using the SettingInt and String provided.
     *
     * @param setting The backing setting.
     * @param title The text that will show on the WidgetSlider.
     */
    public WidgetInt(SettingInt setting, String title) {
        super(title);
        setTheme("");
        this.settingReference = setting;
        this.settingReference.displayWidget = this;
        SimpleFloatModel smodel =
                new SimpleFloatModel(this.settingReference.minimumValue, this.settingReference.maximumValue, this.settingReference.get());
        this.slider = new WidgetSlider(smodel);
        this.slider.setFormat(String.format("%s: %%.0f", this.niceName));
        if ((this.settingReference.stepValue > 1) && (this.settingReference.stepValue <= this.settingReference.maximumValue)) {
            this.slider.setStepSize(this.settingReference.stepValue);
        }
        smodel.addCallback(this);
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
        ModSettings.dbgout("run " + (int) this.slider.getValue());
        this.settingReference.set((int) this.slider.getValue(), ModSettingScreen.guiContext);
    }

    @Override
    public void update() {
        this.slider.setValue(this.settingReference.get(ModSettingScreen.guiContext));
        this.slider.setMinMaxValue(this.settingReference.minimumValue, this.settingReference.maximumValue);
        this.slider.setFormat(String.format("%s: %%.0f", this.niceName));
        ModSettings.dbgout("update " + this.settingReference.get(ModSettingScreen.guiContext) + " -> " + (int) this.slider.getValue());
    }

    @Override
    public String userString() {
        return String.format("%s: %.0d", this.niceName, this.settingReference.get(ModSettingScreen.guiContext));
    }
}
