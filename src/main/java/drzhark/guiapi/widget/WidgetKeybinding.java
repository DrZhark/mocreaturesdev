package drzhark.guiapi.widget;

import de.matthiasmann.twl.Event;
import de.matthiasmann.twl.ToggleButton;
import de.matthiasmann.twl.model.SimpleBooleanModel;
import drzhark.guiapi.GuiModScreen;
import drzhark.guiapi.ModSettingScreen;
import drzhark.guiapi.setting.SettingKey;
import org.lwjgl.input.Keyboard;

/**
 * This is the Widget for Key binding settings. It uses a ToggleButton to
 * display to the user.
 *
 * @author lahwran
 */
public class WidgetKeybinding extends WidgetSetting implements Runnable {

    /**
     * The reference to the underlying SimpleBooleanModel.
     */
    public SimpleBooleanModel booleanModel;
    /**
     * The constant for clearing the existing key.
     */
    public int CLEARKEY = Keyboard.KEY_DELETE;
    /**
     * The constant for exiting and keeping the existing key.
     */
    public int NEVERMINDKEY = Keyboard.KEY_ESCAPE;
    /**
     * The reference to the SettingKey that this WidgetKeybinding uses.
     */
    public SettingKey settingReference;
    /**
     * The reference to the underlying ToggleButton.
     */
    public ToggleButton toggleButton;

    /**
     * This creates a new WidgetKeybinding using the WidgetKeybinding and String
     * provided.
     *
     * @param setting The backing setting.
     * @param title The text that will show on the WidgetSlider.
     */
    public WidgetKeybinding(SettingKey setting, String title) {
        super(title);
        setTheme("");
        this.settingReference = setting;
        this.settingReference.displayWidget = this;
        this.booleanModel = new SimpleBooleanModel(false);
        this.toggleButton = new ToggleButton(this.booleanModel);
        add(this.toggleButton);
        update();
    }

    @Override
    public void addCallback(Runnable paramRunnable) {
        this.booleanModel.addCallback(paramRunnable);
    }

    @Override
    public boolean handleEvent(Event evt) {
        if ((evt.isKeyEvent() && !evt.isKeyPressedEvent()) && this.booleanModel.getValue()) {
            System.out.println(Keyboard.getKeyName(evt.getKeyCode()));
            int tmpvalue = evt.getKeyCode();
            if (tmpvalue == this.CLEARKEY) {
                this.settingReference.set(Keyboard.KEY_NONE, ModSettingScreen.guiContext);
            } else if (tmpvalue != this.NEVERMINDKEY) {
                this.settingReference.set(tmpvalue, ModSettingScreen.guiContext);
            }
            this.booleanModel.setValue(false);
            update();
            GuiModScreen.clicksound();
            return true;
        }
        return false;
    }

    @Override
    public void keyboardFocusLost() {
        GuiModScreen.clicksound();
        this.booleanModel.setValue(false);
    }

    @Override
    public void removeCallback(Runnable paramRunnable) {
        this.booleanModel.removeCallback(paramRunnable);
    }

    @Override
    public void run() {
        GuiModScreen.clicksound();
    }

    @Override
    public void update() {
        this.toggleButton.setText(userString());
    }

    @Override
    public String userString() {
        return String.format("%s: %s", this.niceName, Keyboard.getKeyName(this.settingReference.get(ModSettingScreen.guiContext)));
    }
}
