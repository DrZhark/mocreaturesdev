package drzhark.mocreatures.client.gui;

import static drzhark.mocreatures.MoCProperty.Type.INTEGER;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.minecraft.entity.EnumCreatureType;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import sharose.mods.guiapi.GuiModScreen;
import sharose.mods.guiapi.GuiWidgetScreen;
import sharose.mods.guiapi.ModSettingScreen;
import sharose.mods.guiapi.ModSettings;
import sharose.mods.guiapi.Setting;
import sharose.mods.guiapi.SettingList;
import sharose.mods.guiapi.WidgetInt;
import sharose.mods.guiapi.WidgetList;
import sharose.mods.guiapi.WidgetMulti;
import sharose.mods.guiapi.WidgetSimplewindow;
//import net.minecraft.src.ModSettings;
//import net.minecraft.src.Setting;

import de.matthiasmann.twl.Widget;
import drzhark.mocreatures.MoCConfigCategory;
import drzhark.mocreatures.MoCConfiguration;
import drzhark.mocreatures.MoCEntityData;
import drzhark.mocreatures.MoCEntityModData;
import drzhark.mocreatures.MoCProperty;
import drzhark.mocreatures.MoCProxy;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.MoCClientProxy;

@SideOnly(Side.CLIENT)
public class MoCSettings extends ModSettings {

    public MoCSettings(String modbackendname) {
        super(modbackendname);
    }

    /**
     * convenience int setting adder
     */
    public MoCSettingInt addSetting(Widget w2, String nicename,
            String backendname, int value, int min, int max) {
        MoCSettingInt s = new MoCSettingInt(backendname, value, min, 1, max);
        WidgetInt w = new WidgetInt(s, nicename);
        w2.add(w);
        append(s);
        return s;
    }

    /**
     * convenience multi setting adder
     */
    public MoCSettingMulti addSetting(Widget w2, String nicename,
            String backendname, int value, String... labels) {
        MoCSettingMulti s = new MoCSettingMulti(backendname, value, labels);
        WidgetMulti w = new WidgetMulti(s, nicename);
        w2.add(w);
        append(s);
        return s;
    }

    /**
     * convenience list setting adder
     */
    public MoCSettingList addSetting(Widget w2, String nicename,
            String backendname, String... options) {

        ArrayList<String> arrayList = new ArrayList<String>();

        for (int i = 0; i < options.length; i++) {
            arrayList.add(options[i]);
        }

        MoCSettingList s = new MoCSettingList(backendname, arrayList);
        WidgetList w = new WidgetList(s, nicename);
        w2.add(w);
        append(s);
        return s;
    }

    /**
     * convenience list setting adder
     */
    public MoCSettingList addSetting(Widget w2, String nicename,
            String backendname, ArrayList options) {

        MoCSettingList s = new MoCSettingList(backendname, options);
        WidgetList w = new WidgetList(s, nicename);
        w2.add(w);
        append(s);
        return s;
    }

    /**
     * convenience list setting adder
     */
    public MoCSettingList addSetting(Widget w2, String nicename,
            String backendname, ArrayList options, MoCConfiguration config, String category) {

        MoCSettingList s = new MoCSettingList(config, category, backendname, options);
        WidgetList w = new WidgetList(s, nicename);
        w2.add(w);
        append(s);
        return s;
    }

    /**
     * must be called after all settings are added for loading/saving to work.
     * loads from .minecraft/mods/$backendname/guiconfig.properties if it
     * exists. coming soon: set name of config file
     * 
     * @param context
     *            The context to load from.
     */
    @SuppressWarnings("rawtypes")
    public void load(String context) {
        // DO NOTHING
    }

    /**
     * called every time a setting is changed saves settings file to
     * .minecraft/mods/$backendname/guiconfig.properties coming soon: set name
     * of config file
     * 
     * @param context
     *            The context to save.
     */
    @SuppressWarnings("rawtypes")
    public void save(String context, String backendName, String category, MoCConfiguration config) {
        if (!settingsLoaded) {
            return;
        }
        try {
            MoCConfiguration entityConfig = config;
            // Find setting
            Setting z = null;
            for (int i = 0; i < Settings.size(); i++) {
                z = (Setting) Settings.get(i);
                if (z.backendName.equals(backendName)) break;
            }

            if (config != null && config != MoCreatures.proxy.mocBiomeConfig && config != MoCreatures.proxy.mocGlobalConfig)
            {
                MoCEntityModData modData = null;
                for (Map.Entry<String, String> modEntry : MoCreatures.proxy.entityModKeyMap.entrySet())
                {
                    if (modEntry.getValue().contains(config.getFileName()))
                    {
                        modData = MoCreatures.proxy.entityModMap.get(modEntry.getKey());
                    }
                }
                if (modData != null)
                {
                    int catType = -1;

                    if (z.backendName.contains("Type"))
                        catType = 0;
                    else if (z.backendName.contains("Frequency"))
                        catType = 1;
                    else if (z.backendName.contains("Min"))
                        catType = 2;
                    else if (z.backendName.contains("Max"))
                        catType = 3;
                    else if (z.backendName.contains("Chunk"))
                        catType = 4;
                    for (Map.Entry<String, MoCProperty> propEntry : config.getCategory(category).getValues().entrySet())
                    {
                        if (propEntry.getKey().equalsIgnoreCase(z.backendName.substring(0, z.backendName.indexOf(" "))))
                        {
                            // handle entity config
                            MoCEntityData entityData = modData.getCreature(propEntry.getKey());
                            if (entityData != null)
                            {
                                MoCProperty property = propEntry.getValue();
                                switch (catType)
                                {
                                    case 0 :
                                        if (MoCreatures.proxy.debugLogging) MoCreatures.log.info("setting type to " + z.toString(context));

                                        if (entityData.getType() != null)
                                        {
                                            if (z.toString(context).equalsIgnoreCase("CREATURE"))
                                            {
                                                entityData.setType(EnumCreatureType.creature);
                                            }
                                            else if (z.toString(context).equalsIgnoreCase("MONSTER"))
                                            {
                                                entityData.setType(EnumCreatureType.monster);
                                            }
                                            else if (z.toString(context).equalsIgnoreCase("WATERCREATURE"))
                                            {
                                                entityData.setType(EnumCreatureType.waterCreature);
                                            }
                                            else if (z.toString(context).equalsIgnoreCase("AMBIENT"))
                                            {
                                                entityData.setType(EnumCreatureType.ambient);
                                            }
                                        }
                                        property.valueList.set(catType, z.toString(context));
                                        break;
                                    case 1 :
                                        if (MoCreatures.proxy.debugLogging) MoCreatures.log.info("setting frequency to " + z.toString(context));
                                        entityData.setFrequency(Integer.parseInt(z.toString(context)));
                                        property.valueList.set(catType, z.toString(context));
                                        break;

                                    case 2 :
                                        if (MoCreatures.proxy.debugLogging) MoCreatures.log.info("setting min to " + z.toString(context));
                                        entityData.setMinSpawn(Integer.parseInt(z.toString(context)));
                                        property.valueList.set(catType, z.toString(context));
                                        break;

                                    case 3 :
                                        if (MoCreatures.proxy.debugLogging) MoCreatures.log.info("setting max to " + z.toString(context));
                                        entityData.setMaxSpawn(Integer.parseInt(z.toString(context)));
                                        property.valueList.set(catType, z.toString(context));
                                        break;
                                    case 4 :
                                        if (MoCreatures.proxy.debugLogging) MoCreatures.log.info("setting chunk to " + z.toString(context));
                                        entityData.setMaxInChunk(Integer.parseInt(z.toString(context)));
                                        property.valueList.set(catType, z.toString(context));
                                        break;
                                    default :
                                }
                                if (MoCreatures.myCustomSpawner != null)
                                {
                                   MoCreatures.myCustomSpawner.updateSpawnListEntry(entityData.getEntityClass(), entityData.getType(), entityData.getFrequency(), entityData.getMinSpawn(), entityData.getMaxSpawn());
                                }
                            }
                        }
                     }
                }
            }
            else if (config != null){ // handle rest of our categories the same
                for (Map.Entry<String, MoCProperty> propEntry : config.getCategory(category).getValues().entrySet())
                {
                    if (propEntry.getKey().equalsIgnoreCase(z.backendName))
                    {
                        MoCProperty property = propEntry.getValue();
                        property.value = z.toString(context);
                        if (MoCreatures.proxy.debugLogging) MoCreatures.log.info("set config value to " + property.value);
                        break;
                    }
                }
            }
            if (config != null)
                config.save(); // save config
            MoCreatures.proxy.mocGlobalConfig.save();
            MoCreatures.proxy.mocBiomeConfig.save();
            MoCreatures.proxy.readConfigValues();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}