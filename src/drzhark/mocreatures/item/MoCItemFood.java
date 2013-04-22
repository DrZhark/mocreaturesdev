package drzhark.mocreatures.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemFood;

public class MoCItemFood extends ItemFood //implements ITextureProvider
{

    //private int healInt;

    public MoCItemFood(int i, int j)
    {
        super(i, j, 0.6F, false);
        maxStackSize = 32;
        //this.field_35430_a = 32;
        //healInt = j;
    }

    public MoCItemFood(int i, int j, float f, boolean flag)
    {
        super(i, j, f, flag);
    }

    /*@Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        itemstack.stackSize--;
        entityplayer.heal(healInt);
        return itemstack;
    }*/

    /*public String getTextureFile()
    {
        return "/mocreatures/items.png";
    }*/
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("mocreatures"+ this.getUnlocalizedName().replaceFirst("item.", ":"));
    }

    /*public ItemFood(int par1, int par2, float par3, boolean par4)
    {
        super(par1);
        this.field_35430_a = 32;
        this.healAmount = par2;
        this.isWolfsFavoriteMeat = par4;
        this.saturationModifier = par3;
    }*/
}
