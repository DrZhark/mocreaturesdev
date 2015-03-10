package drzhark.guiapi.widget;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.model.SimpleButtonModel;
import drzhark.guiapi.GuiModScreen;
import drzhark.guiapi.ModSettingScreen;
import drzhark.guiapi.ModSettings;
import drzhark.guiapi.setting.SettingMulti;

/**
 * This is the Widget for Multi settings. It uses a button to display to the
 * user, and cycles the values.
 *
 * @author lahwran
 */
public class WidgetMulti extends WidgetSetting implements Runnable {

    /**
     * The reference to the underlying Button.
     */
    public Button button;
    /**
     * The reference to the SettingMulti that this WidgetMulti uses.
     */
    public SettingMulti value;

    /**
     * This creates a new WidgetMulti using the SettingMulti and String
     * provided.
     *
     * @param setting The backing setting.
     * @param title The title for this Widget. It is what will show on the
     *        button, asides from it's current value.
     */
    public WidgetMulti(SettingMulti setting, String title) {
        super(title);
        setTheme("");
        this.value = setting;
        this.value.displayWidget = this;
        SimpleButtonModel model = new SimpleButtonModel();
        this.button = new Button(model);
        model.addActionCallback(this);
        add(this.button);
        update();
    }

    @Override
    public void addCallback(Runnable paramRunnable) {
        this.button.getModel().addActionCallback(paramRunnable);
    }

    @Override
    public void removeCallback(Runnable paramRunnable) {
        this.button.getModel().removeActionCallback(paramRunnable);
    }

    @Override
    public void run() {
        this.value.next(ModSettingScreen.guiContext);
        update();
        GuiModScreen.clicksound();
    }

    @Override
    public void update() {
        this.button.setText(userString());
        ModSettings.dbgout("multi update " + userString());
    }

    @Override
    public String userString() {
        if (this.niceName.length() > 0) {
            return String.format("%s: %s", this.niceName, this.value.getLabel(ModSettingScreen.guiContext));
        } else {
            return this.value.getLabel(ModSettingScreen.guiContext);
        }
    }
}
