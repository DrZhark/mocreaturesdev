package drzhark.mocreatures;

import java.util.List;

import sharose.mods.guiapi.WidgetSimplewindow;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MoCBiomeGroupData {

    private String biomeGroupName;
    private List<String> biomes;
    @SideOnly(Side.CLIENT)
    private WidgetSimplewindow window;

    public MoCBiomeGroupData(String biomeGroup, List<String> biomeList)
    {
        this.biomeGroupName = biomeGroup;
        this.biomes = biomeList;
    }
 
    public String getBiomeGroupName()
    {
        return this.biomeGroupName;
    }

    public List<String> getBiomeList()
    {
        return biomes;
    }

    @SideOnly(Side.CLIENT)
    public void setBiomeGroupWindow(WidgetSimplewindow biomeWindow)
    {
        this.window = biomeWindow;
    }

    @SideOnly(Side.CLIENT)
    public WidgetSimplewindow getBiomeGroupWindow()
    {
        return this.window;
    }
}