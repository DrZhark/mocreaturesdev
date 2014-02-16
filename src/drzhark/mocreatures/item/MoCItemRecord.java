package drzhark.mocreatures.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemRecord;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.MoCreatures;

public class MoCItemRecord extends ItemRecord
{
    public MoCItemRecord(String par2Str)
    {
        super(par2Str);
        this.setCreativeTab(MoCreatures.tabMoC);
        this.setUnlocalizedName(par2Str);
        GameRegistry.registerItem(this, par2Str);
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
    public void registerIcons(IIconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("mocreatures:recordshuffle");
    }
}