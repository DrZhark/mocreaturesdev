package drzhark.mocreatures.client.gui;

import java.util.ArrayList;

import sharose.mods.guiapi.SettingList;

public class MoCSettingList extends SettingList {

    public String category; // reference to category this setting is linked to

    public MoCSettingList(String title) {
        super(title, new ArrayList<String>());
    }

    public MoCSettingList(String title, ArrayList<String> defaultvalue) {
        super(title, defaultvalue);
    }

    public MoCSettingList(String cat, String title, ArrayList<String> defaultvalue) {
        super(title, defaultvalue);
        this.category = cat;
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
            ((MoCSettings)parent).save(context, this.backendName, this.category); // blood - pass backendName
        }
        if (displayWidget != null) {
            displayWidget.update();
        }
    }
}