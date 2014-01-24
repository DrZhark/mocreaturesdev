package drzhark.mocreatures.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemRecord;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MoCItemRecord extends ItemRecord
{
    public MoCItemRecord(String par2Str)
    {
        super(par2Str);
    }

    @SideOnly(Side.CLIENT)

    /**
     * Return the title for this record.
     */
    public String getRecordTitle()
    {
        return "MoC - " + this.field_150929_a;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("mocreatures:recordshuffle");
    }
}