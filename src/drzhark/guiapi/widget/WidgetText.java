package drzhark.guiapi.widget;

import de.matthiasmann.twl.EditField;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.model.StringModel;
import de.matthiasmann.twl.utils.CallbackSupport;
import drzhark.guiapi.GuiModScreen;
import drzhark.guiapi.ModSettingScreen;
import drzhark.guiapi.ModSettings;
import drzhark.guiapi.setting.SettingText;

/**
 * This is the Widget for Text settings. It uses an EditField for the user to
 * edit, and a Label for the name.
 * 
 * @author lahwran
 * @author ShaRose
 */
public class WidgetText extends WidgetSetting implements StringModel {
    private Runnable[] callbacks;
    /**
     * The label that displays to the user what the nice name of this setting
     * is.
     */
    public Label displayLabel;
    /**
     * The EditField that the user actually changes the setting with.
     */
    public EditField editField;
    /**
     * This is a control number to who and what can edit this setting. 0 means
     * that both the SettingText and the user can edit. Below 0 means that only
     * the user can edit: So resetting to default will not change the text the
     * user sees. Over 0 means that the user can't edit the field, but if the
     * SettingText updates, it will replace what the user sees.
     */
    public int setmode = 0;
    /**
     * The reference to the SettingText that this WidgetText uses.
     */
    public SettingText settingReference;

    /**
     * This creates a new WidgetText using the SettingText and String provided.
     * 
     * @param setting
     *            The backing setting.
     * @param title
     *            The text that will show on the Label. If null, it will not
     *            have a label at all.
     */
    public WidgetText(SettingText setting, String title) {
        super(title);
        setTheme("");
        settingReference = setting;
        settingReference.displayWidget = this;
        editField = new EditField();
        add(editField);
        if (title != null) {
            displayLabel = new Label();
            displayLabel.setText(String.format("%s: ", niceName));
            add(displayLabel);
        }
        editField.setModel(this);
        update();
    }

    @Override
    public void addCallback(Runnable callback) {
        callbacks = (CallbackSupport.addCallbackToList(callbacks, callback,
                Runnable.class));
    }

    @Override
    public String getValue() {
        return settingReference.get();
    }

    @Override
    public void layout() {
        if (displayLabel != null) {
            displayLabel.setPosition(getX(), (getY() + (getHeight() / 2))
                    - (displayLabel.computeTextHeight() / 2));
            displayLabel.setSize(displayLabel.computeTextWidth(),
                    displayLabel.computeTextHeight());
            editField.setPosition(getX() + displayLabel.computeTextWidth(),
                    getY());
            editField.setSize(getWidth() - displayLabel.computeTextWidth(),
                    getHeight());
        } else {
            editField.setPosition(getX(), getY());
            editField.setSize(getWidth(), getHeight());
        }
    }

    @Override
    public void removeCallback(Runnable callback) {
        callbacks = (CallbackSupport
                .removeCallbackFromList(callbacks, callback));
    }

    @Override
    public void setValue(String _value) {
        GuiModScreen.clicksound();
        ModSettings.dbgout(String.format("setvalue %s", editField.getText()));
        if (setmode <= 0) {
            setmode = -1;
            settingReference.set(editField.getText(),
                    ModSettingScreen.guiContext);
            setmode = 0;
        }
        CallbackSupport.fireCallbacks(callbacks);
    }

    @Override
    public void update() {
        ModSettings.dbgout("update");
        if (displayLabel != null) {
            displayLabel.setText(String.format("%s: ", niceName));
        }
        if (setmode >= 0) {
            setmode = 1;
            editField
                    .setText(settingReference.get(ModSettingScreen.guiContext));
            setmode = 0;
        }
        ModSettings.dbgout(String.format("update %s", editField.getText()));
    }

    @Override
    public String userString() {
        return String.format("%s: %s", niceName,
                settingReference.get(ModSettingScreen.guiContext));
    }
}
