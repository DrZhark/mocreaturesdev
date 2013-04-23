package drzhark.mocreatures.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.MoCreatures;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class MoCItem extends Item// implements ITextureProvider
{
    public MoCItem(int i)
    {
        super(i);
        this.setCreativeTab(MoCreatures.tabMoC);
    }

   /* @Override
    public String getTextureFile()
    {
        return "/mocreatures/items.png";
    }*/

   
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("mocreatures"+ this.getUnlocalizedName().replaceFirst("item.", ":"));
    }
}
