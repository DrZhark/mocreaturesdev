package drzhark.mocreatures.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemFood;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class MoCItemFood extends ItemFood
{

    public MoCItemFood(String name, int j)
    {
        super(j, 0.6F, false);
        GameRegistry.registerItem(this, name);
        maxStackSize = 32;
    }

    public MoCItemFood(String name, int j, float f, boolean flag)
    {
        super(j, f, flag);
        GameRegistry.registerItem(this, name);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("mocreatures"+ this.getUnlocalizedName().replaceFirst("item.", ":"));
    }
}