package drzhark.mocreatures.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemFood;

public class MoCItemFood extends ItemFood
{

    public MoCItemFood(int i, int j)
    {
        super(i, j, 0.6F, false);
        maxStackSize = 32;
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