package drzhark.mocreatures.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.MoCreatures;

public class MoCItemFood extends ItemFood
{

    public MoCItemFood(int i, int j)
    {
        super(i, j, 0.6F, false);
        maxStackSize = 32;
        this.setCreativeTab(CreativeTabs.tabFood);
        this.setCreativeTab(MoCreatures.tabMoC);
    }

    public MoCItemFood(int i, int j, float f, boolean flag)
    {
        super(i, j, f, flag);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("mocreatures"+ this.getUnlocalizedName().replaceFirst("item.", ":"));
    }
}