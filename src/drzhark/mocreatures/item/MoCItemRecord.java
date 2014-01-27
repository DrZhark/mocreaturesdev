package drzhark.mocreatures.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemRecord;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.MoCreatures;

public class MoCItemRecord extends ItemRecord
{
    public MoCItemRecord(int par1, String par2Str)
    {
        super(par1, par2Str);
        this.setCreativeTab(CreativeTabs.tabMisc);
        this.setCreativeTab(MoCreatures.tabMoC);
    }

    @SideOnly(Side.CLIENT)

    /**
     * Return the title for this record.
     */
    public String getRecordTitle()
    {
        return "MoC - " + this.recordName;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("mocreatures:recordshuffle");
    }
}