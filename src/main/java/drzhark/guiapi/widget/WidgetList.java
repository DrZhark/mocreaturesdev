package drzhark.guiapi.widget;

import de.matthiasmann.twl.CallbackWithReason;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.ListBox;
import de.matthiasmann.twl.ListBox.CallbackReason;
import de.matthiasmann.twl.model.SimpleChangableListModel;
import de.matthiasmann.twl.utils.CallbackSupport;
import drzhark.guiapi.setting.SettingList;

public class WidgetList extends WidgetSetting implements CallbackWithReason<CallbackReason> {

    private Runnable[] callbacks;
    public Label displayLabel;

    /**
     * The reference to the underlying WidgetSlider.
     */
    public ListBox<String> listBox;

    public SimpleChangableListModel<String> listBoxModel;

    /**
     * The reference to the SettingInt that this WidgetInt uses.
     */
    public SettingList settingReference;

    public WidgetList(SettingList setting, String title) {
        super(title);
        setTheme("");
        this.settingReference = setting;
        this.settingReference.displayWidget = this;
        if (title != null) {
            this.displayLabel = new Label();
            this.displayLabel.setText(this.niceName);
            add(this.displayLabel);
        }

        this.listBoxModel = new SimpleChangableListModel<String>(setting.get());
        this.listBox = new ListBox<String>(this.listBoxModel);
        add(this.listBox);
        this.listBox.addCallback(this);
        update();
    }

    @Override
    public void addCallback(Runnable callback) {
        this.callbacks = (CallbackSupport.addCallbackToList(this.callbacks, callback, Runnable.class));
    }

    @Override
    public void callback(CallbackReason paramT) {
        CallbackSupport.fireCallbacks(this.callbacks);
    }

    @Override
    public void layout() {
        if (this.displayLabel != null) {
            this.displayLabel.setPosition(getX(), getY());
            int offset = this.displayLabel.computeTextHeight();
            this.displayLabel.setSize(getWidth(), offset);
            this.listBox.setPosition(getX(), getY() + offset);
            this.listBox.setSize(getWidth(), getHeight() - offset);
        } else {
            this.listBox.setPosition(getX(), getY());
            this.listBox.setSize(getWidth(), getHeight());
        }
    }

    @Override
    public void removeCallback(Runnable callback) {
        this.callbacks = (CallbackSupport.removeCallbackFromList(this.callbacks, callback));
    }

    @Override
    public void update() {
        this.listBoxModel.clear();
        this.listBoxModel.addElements(this.settingReference.get());
    }

    @Override
    public String userString() {
        String output = "";
        if (this.niceName != null) {
            output = this.niceName + ": ";
        }

        int sel = this.listBox.getSelected();
        String text = sel != -1 ? this.listBoxModel.getEntry(sel) : "NOTHING";

        output += String.format("%s (Entry %s) currently selected from %s items.", text, sel, this.settingReference.get().size());

        return output;
    }

}
