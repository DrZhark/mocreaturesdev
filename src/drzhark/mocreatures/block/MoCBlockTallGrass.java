package drzhark.mocreatures.block;



import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import drzhark.mocreatures.MoCreatures;

import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.Icon;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IShearable;

public class MoCBlockTallGrass extends BlockFlower implements IShearable
{
    @SideOnly(Side.CLIENT)
    private Icon[] icons;
    
    public MoCBlockTallGrass(int par1)
    {
        super(par1, Material.vine);
        float var3 = 0.4F;
        this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, 0.8F, 0.5F + var3);
    }

    public MoCBlockTallGrass(int par1, boolean lighted)
    {
    	this(par1);
    	if (lighted)
        {
        	this.setLightValue(0.8F);
        }
    }
    
    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return -1;
    }

    
    /**
     * Returns the usual quantity dropped by the block plus a bonus of 1 to 'i' (inclusive).
     */
    public int quantityDroppedWithBonus(int par1, Random par2Random)
    {
        return 1 + par2Random.nextInt(par1 * 2 + 1);
    }

    /**
     * Called when the player destroys a block with an item that can harvest it. (i, j, k) are the coordinates of the
     * block and l is the block's subtype/damage.
     */
    public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6)
    {
        super.harvestBlock(par1World, par2EntityPlayer, par3, par4, par5, par6);
    }



    /**
     * Get the block's damage value (for use with pick block).
     */
    public int getDamageValue(World par1World, int par2, int par3, int par4)
    {
        return par1World.getBlockMetadata(par2, par3, par4);
    }



    @Override
    public boolean isShearable(ItemStack item, World world, int x, int y, int z)
    {
        return false;
    }

    @Override
    public ArrayList<ItemStack> onSheared(ItemStack item, World world, int x,
            int y, int z, int fortune) {
        
        return null;
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
            icons[x] = par1IconRegister.registerIcon("mocreatures:" + "tallGrass_" + MoCreatures.multiBlockNames.get(x));
        }
    }
   
    
    
    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    @SideOnly(Side.CLIENT)
    @Override
    public Icon getIcon(int par1, int par2)
    {
    	//fixes? game crashing bug
    	if (par2 > icons.length)
    	{
    		par2 = icons.length;
    	}
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
