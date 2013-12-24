package drzhark.customspawner.biomes;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.guiapi.widget.WidgetSimplewindow;

public class BiomeGroupData {

    private String biomeGroupName;
    private List<String> biomes;
    @SideOnly(Side.CLIENT)
    private WidgetSimplewindow window;

    public BiomeGroupData(String biomeGroup, List<String> biomeList)
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

    public void addBiome(String biome)
    {
        this.biomes.add(biome);
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