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
     * @param setting
     *            The backing setting.
     * @param title
     *            The text that will show on the WidgetSlider.
     */
    public WidgetInt(SettingInt setting, String title) {
        super(title);
        setTheme("");
        settingReference = setting;
        settingReference.displayWidget = this;
        SimpleFloatModel smodel = new SimpleFloatModel(
                settingReference.minimumValue, settingReference.maximumValue,
                settingReference.get());
        slider = new WidgetSlider(smodel);
        slider.setFormat(String.format("%s: %%.0f", niceName));
        if ((settingReference.stepValue > 1)
                && (settingReference.stepValue <= settingReference.maximumValue)) {
            slider.setStepSize(settingReference.stepValue);
        }
        smodel.addCallback(this);
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
        ModSettings.dbgout("run " + (int) slider.getValue());
        settingReference.set((int) slider.getValue(),
                ModSettingScreen.guiContext);
    }

    @Override
    public void update() {
        slider.setValue(settingReference.get(ModSettingScreen.guiContext));
        slider.setMinMaxValue(settingReference.minimumValue, settingReference.maximumValue);
        slider.setFormat(String.format("%s: %%.0f", niceName));
        ModSettings.dbgout("update "
                + settingReference.get(ModSettingScreen.guiContext) + " -> "
                + (int) slider.getValue());
    }

    @Override
    public String userString() {
        return String.format("%s: %.0d", niceName,
                settingReference.get(ModSettingScreen.guiContext));
    }
}