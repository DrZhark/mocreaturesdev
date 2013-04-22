package drzhark.mocreatures;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import sharose.mods.guiapi.WidgetSimplewindow;
import sharose.mods.guiapi.WidgetSingleRow;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;

public class MoCEntityData {

    private Class<? extends EntityLiving> clazz;
    private EnumCreatureType typeOfCreature;
    @SideOnly(Side.CLIENT)
    private WidgetSimplewindow entityWindow;

    private ArrayList<String> biomeGroups;
    public int frequency = 10;
    public int minGroup = 4;
    public int maxGroup = 4;


    public MoCEntityData(Class<? extends EntityLiving> entityClass, EnumCreatureType type)
    {
        this.clazz = entityClass;
        this.typeOfCreature = type;
    }

    public MoCEntityData(Class<? extends EntityLiving> entityClass, EnumCreatureType type, short freq, short min, short max)
    {
        this.clazz = entityClass;
        this.typeOfCreature = type;
        this.frequency = freq;
        this.minGroup = min;
        this.maxGroup = max;
    }

    public Class<? extends EntityLiving> getEntityClass()
    {
        return this.clazz;
    }

    public EnumCreatureType getType()
    {
        return this.typeOfCreature;
    }

    @SideOnly(Side.CLIENT)
    public WidgetSimplewindow getEntityWindow()
    {
        return this.entityWindow;
    }

    @SideOnly(Side.CLIENT)
    public void setEntityWindow(WidgetSimplewindow window)
    {
        this.entityWindow = window;
    }

    public ArrayList<String> getBiomeGroups()
    {
        return this.biomeGroups;
    }

    public void setBiomeGroups(ArrayList<String> biomeGroups)
    {
        this.biomeGroups = biomeGroups;
    }
}
