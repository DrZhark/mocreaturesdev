package drzhark.mocreatures.client.gui.helpers;

import drzhark.guiapi.setting.SettingList;
import drzhark.mocreatures.configuration.MoCConfiguration;

import java.util.ArrayList;

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
        if (this.values.get(context) != null) {
            return this.values.get(context);
        } else if (this.values.get("") != null) {
            return this.values.get("");
        } else {
            return this.defaultValue;
        }
    }

    @Override
    public void set(ArrayList<String> v, String context) {
        this.values.put(context, v);
        if (this.parent != null) {
            ((MoCSettings) this.parent).save(context, this.backendName, this.category, this.config); // blood - pass backendName
        }
        if (this.displayWidget != null) {
            this.displayWidget.update();
        }
    }
}
