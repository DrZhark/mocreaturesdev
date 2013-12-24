package drzhark.mocreatures.client.gui.helpers;

import java.util.ArrayList;

import drzhark.guiapi.setting.SettingList;
import drzhark.mocreatures.configuration.MoCConfiguration;

public class MoCSettingList extends SettingList {

    public String category; // reference to category this setting is linked to
    public MoCConfiguration config;

    public MoCSettingList(String title) {
        super(title, new ArrayList<String>());
    }

    public MoCSettingList(String title, ArrayList<String> defaultvalue) {
        super(title, defaultvalue);
    }

    public MoCSettingList(MoCConfiguration config, String cat, String title, ArrayList<String> defaultvalue) {
        super(title, defaultvalue);
        this.category = cat;
        this.config = config;
    }

    @Override
    public ArrayList<String> get(String context) {
        if (values.get(context) != null) {
            return values.get(context);
        } else if (values.get("") != null) {
            return values.get("");
        } else {
            return defaultValue;
        }
    }

    @Override
    public void set(ArrayList<String> v, String context) {
        values.put(context, v);
        if (parent != null) {
            ((MoCSettings)parent).save(context, this.backendName, this.category, this.config); // blood - pass backendName
        }
        if (displayWidget != null) {
            displayWidget.update();
        }
    }
}