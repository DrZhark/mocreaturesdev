package drzhark.mocreatures.block;




import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;

import drzhark.mocreatures.MoCreatures;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;


public class MoCBlockPlanks extends MoCBlock
{
	@SideOnly(Side.CLIENT)
    private Icon[] icons;
	 
    public MoCBlockPlanks(int ID)
    {
        super(ID, Material.wood);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }


   
   
    @Override
    public int damageDropped(int i)
    {
        return i;
    }
    
	//func_94332_a = registerIcons(IconRegister)
    //func_94245_a = registerIcon(String)
	@SideOnly(Side.CLIENT)
	@Override
    public void registerIcons(IconRegister par1IconRegister)
    {
		icons = new Icon[MoCreatures.multiBlockNames.size()];
        
		for (int x = 0; x < MoCreatures.multiBlockNames.size(); x++)
		{
			icons[x] = par1IconRegister.registerIcon("mocreatures:" + "woodPlanks_" + MoCreatures.multiBlockNames.get(x));
		}
    }
   
    
	
    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
	@SideOnly(Side.CLIENT)
    @Override
    public Icon getIcon(int par1, int par2)
    {
        return icons[par2];
    }
    


    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(int par1, CreativeTabs tab, List subItems) 
    {
        for (int ix = 0; ix < MoCreatures.multiBlockNames.size(); ix++) 
        {
            subItems.add(new ItemStack(this, 1, ix));
        }
    }
    
    @SideOnly(Side.CLIENT)
    public Icon getIconFromDamage(int i)
    {
    return icons[i]; 
    }
}
