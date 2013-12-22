package drzhark.mocreatures.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemRecord;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MoCItemRecord extends ItemRecord
{
    public MoCItemRecord(int par1, String par2Str)
    {
        super(par1, par2Str);
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