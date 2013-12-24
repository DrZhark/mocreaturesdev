package drzhark.guiapi.widget;

import de.matthiasmann.twl.CallbackWithReason;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.ListBox;
import de.matthiasmann.twl.ListBox.CallbackReason;
import de.matthiasmann.twl.model.SimpleChangableListModel;
import de.matthiasmann.twl.utils.CallbackSupport;
import drzhark.guiapi.setting.SettingList;

public class WidgetList extends WidgetSetting implements
        CallbackWithReason<CallbackReason> {

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
        settingReference = setting;
        settingReference.displayWidget = this;
        if (title != null) {
            displayLabel = new Label();
            displayLabel.setText(niceName);
            add(displayLabel);
        }

        listBoxModel = new SimpleChangableListModel<String>(setting.get());
        listBox = new ListBox<String>(listBoxModel);
        add(listBox);
        listBox.addCallback(this);
        update();
    }

    @Override
    public void addCallback(Runnable callback) {
        callbacks = (CallbackSupport.addCallbackToList(callbacks, callback,
                Runnable.class));
    }

    @Override
    public void callback(CallbackReason paramT) {
        CallbackSupport.fireCallbacks(callbacks);
    }

    @Override
    public void layout() {
        if (displayLabel != null) {
            displayLabel.setPosition(getX(), getY());
            int offset = displayLabel.computeTextHeight();
            displayLabel.setSize(getWidth(), offset);
            listBox.setPosition(getX(), getY() + offset);
            listBox.setSize(getWidth(), getHeight() - offset);
        } else {
            listBox.setPosition(getX(), getY());
            listBox.setSize(getWidth(), getHeight());
        }
    }

    @Override
    public void removeCallback(Runnable callback) {
        callbacks = (CallbackSupport
                .removeCallbackFromList(callbacks, callback));
    }

    @Override
    public void update() {
        listBoxModel.clear();
        listBoxModel.addElements(settingReference.get());
    }

    @Override
    public String userString() {
        String output = "";
        if (niceName != null) {
            output = niceName + ": ";
        }

        int sel = listBox.getSelected();
        String text = sel != -1 ? listBoxModel.getEntry(sel) : "NOTHING";

        output += String.format(
                "%s (Entry %s) currently selected from %s items.", text, sel,
                settingReference.get().size());

        return output;
    }

}
