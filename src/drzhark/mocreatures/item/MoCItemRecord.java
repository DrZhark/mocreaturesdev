package drzhark.mocreatures.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemRecord;

public class MoCItemRecord extends ItemRecord //implements ITextureProvider
{
    public MoCItemRecord(int par1, String par2Str)
    {
        super(par1, par2Str);
    }

   /* public String getTextureFile()
    {
        return "/mocreatures/items.png";
    }*/
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("mocreatures"+ this.getUnlocalizedName().replaceFirst("item.", ":"));
    }
}
