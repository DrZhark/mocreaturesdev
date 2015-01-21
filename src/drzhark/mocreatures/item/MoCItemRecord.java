package drzhark.mocreatures.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemRecord;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.MoCreatures;

public class MoCItemRecord extends ItemRecord
{
    public static ResourceLocation RECORD_SHUFFLE_RESOURCE = new ResourceLocation("mocreatures", "shuffling");

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

    public ResourceLocation getRecordResource(String name)
    {
        return RECORD_SHUFFLE_RESOURCE;
    }
}