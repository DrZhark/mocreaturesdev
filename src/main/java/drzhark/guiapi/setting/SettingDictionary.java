package drzhark.guiapi.setting;

import drzhark.guiapi.ModSettings;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * This is the Setting type for the Properties class. That is, a Dictionary of
 * strings addressable by strings.
 *
 * @author ShaRose
 */
public class SettingDictionary extends Setting<Properties> {

    public SettingDictionary(String title) {
        this(title, new Properties());
    }

    public SettingDictionary(String title, Properties defaultvalue) {
        this.backendName = title;
        this.defaultValue = defaultvalue;
        this.values.put("", defaultvalue);
    }

    @Override
    public void fromString(String s, String context) {
        Properties prop = new Properties();
        try {
            prop.loadFromXML(new ByteArrayInputStream(s.getBytes("UTF-8")));
        } catch (Throwable e) {
            ModSettings.dbgout("Error reading SettingDictionary from context '" + context + "': " + e);
        }
        this.values.put(context, prop);
        if (this.displayWidget != null) {
            this.displayWidget.update();
        }
    }

    @Override
    public Properties get(String context) {
        if (this.values.get(context) != null) {
            return this.values.get(context);
        } else if (this.values.get("") != null) {
            return this.values.get("");
        } else {
            return this.defaultValue;
        }
    }

    @Override
    public void set(Properties v, String context) {
        this.values.put(context, v);
        if (this.parent != null) {
            this.parent.save(context);
        }
        if (this.displayWidget != null) {
            this.displayWidget.update();
        }
    }

    @Override
    public String toString(String context) {
        try {
            Properties prop = get(context);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            prop.storeToXML(output, "GuiAPI SettingDictionary: DO NOT EDIT.");
            return output.toString("UTF-8");
        } catch (IOException e) {
            ModSettings.dbgout("Error writing SettingDictionary from context '" + context + "': " + e);
            return "";
        }
    }

}
