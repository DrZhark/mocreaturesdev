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
     * @param setting The backing setting.
     * @param title The text that will show on the Label. If null, it will not
     *        have a label at all.
     */
    public WidgetText(SettingText setting, String title) {
        super(title);
        setTheme("");
        this.settingReference = setting;
        this.settingReference.displayWidget = this;
        this.editField = new EditField();
        add(this.editField);
        if (title != null) {
            this.displayLabel = new Label();
            this.displayLabel.setText(String.format("%s: ", this.niceName));
            add(this.displayLabel);
        }
        this.editField.setModel(this);
        update();
    }

    @Override
    public void addCallback(Runnable callback) {
        this.callbacks = (CallbackSupport.addCallbackToList(this.callbacks, callback, Runnable.class));
    }

    @Override
    public String getValue() {
        return this.settingReference.get();
    }

    @Override
    public void layout() {
        if (this.displayLabel != null) {
            this.displayLabel.setPosition(getX(), (getY() + (getHeight() / 2)) - (this.displayLabel.computeTextHeight() / 2));
            this.displayLabel.setSize(this.displayLabel.computeTextWidth(), this.displayLabel.computeTextHeight());
            this.editField.setPosition(getX() + this.displayLabel.computeTextWidth(), getY());
            this.editField.setSize(getWidth() - this.displayLabel.computeTextWidth(), getHeight());
        } else {
            this.editField.setPosition(getX(), getY());
            this.editField.setSize(getWidth(), getHeight());
        }
    }

    @Override
    public void removeCallback(Runnable callback) {
        this.callbacks = (CallbackSupport.removeCallbackFromList(this.callbacks, callback));
    }

    @Override
    public void setValue(String _value) {
        GuiModScreen.clicksound();
        ModSettings.dbgout(String.format("setvalue %s", this.editField.getText()));
        if (this.setmode <= 0) {
            this.setmode = -1;
            this.settingReference.set(this.editField.getText(), ModSettingScreen.guiContext);
            this.setmode = 0;
        }
        CallbackSupport.fireCallbacks(this.callbacks);
    }

    @Override
    public void update() {
        ModSettings.dbgout("update");
        if (this.displayLabel != null) {
            this.displayLabel.setText(String.format("%s: ", this.niceName));
        }
        if (this.setmode >= 0) {
            this.setmode = 1;
            this.editField.setText(this.settingReference.get(ModSettingScreen.guiContext));
            this.setmode = 0;
        }
        ModSettings.dbgout(String.format("update %s", this.editField.getText()));
    }

    @Override
    public String userString() {
        return String.format("%s: %s", this.niceName, this.settingReference.get(ModSettingScreen.guiContext));
    }
}
