package drzhark.guiapi.setting;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

import drzhark.guiapi.ModSettings;



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
        backendName = title;
        defaultValue = defaultvalue;
        values.put("", defaultvalue);
    }

    @Override
    public void fromString(String s, String context) {
        Properties prop = new Properties();
        try {
            prop.loadFromXML(new ByteArrayInputStream(s.getBytes("UTF-8")));
        } catch (Throwable e) {
            ModSettings.dbgout("Error reading SettingDictionary from context '"
                    + context + "': " + e);
        }
        values.put(context, prop);
        if (displayWidget != null) {
            displayWidget.update();
        }
    }

    @Override
    public Properties get(String context) {
        if (values.get(context) != null) {
            return values.get(context);
        } else if (values.get("") != null) {
            return values.get("");
        } else {
            return defaultValue;
        }
    }

    @Override
    public void set(Properties v, String context) {
        values.put(context, v);
        if (parent != null) {
            parent.save(context);
        }
        if (displayWidget != null) {
            displayWidget.update();
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
            ModSettings.dbgout("Error writing SettingDictionary from context '"
                    + context + "': " + e);
            return "";
        }
    }

}
