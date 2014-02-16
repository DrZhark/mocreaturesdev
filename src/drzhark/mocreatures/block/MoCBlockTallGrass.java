package drzhark.mocreatures.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.MoCreatures;

public class MoCBlockTallGrass extends BlockBush implements IShearable
{
    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    public MoCBlockTallGrass(String name)
    {
        super(Material.vine);
        float var3 = 0.4F;
        this.setBlockBounds(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, 0.8F, 0.5F + var3);
        this.setCreativeTab(MoCreatures.tabMoC);
        this.setBlockName(name);
        GameRegistry.registerBlock(this, MultiItemBlock.class, name);
    }

    public MoCBlockTallGrass(String name, boolean lighted)
    {
        this(name);
        if (lighted)
        {
            this.setLightLevel(0.8F);
        }
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public Item getItemDropped(int par1, Random par2Random, int par3)
    {
        return null;
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
    public boolean isShearable(ItemStack item, IBlockAccess world, int x, int y, int z)
    {
        return false;
    }

    @Override
    public ArrayList<ItemStack> onSheared(ItemStack item,IBlockAccess world, int x,
            int y, int z, int fortune) {
        
        return null;
    }

    @Override
    public int damageDropped(int i)
    {
        return i;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        icons = new IIcon[MoCreatures.multiBlockNames.size()];
        
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
    public IIcon getIcon(int par1, int par2)
    {
        if (par2 < 0 || par2 >= MoCreatures.multiBlockNames.size()) par2 = 0;
        return icons[par2];
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(Item par1, CreativeTabs tab, List subItems) 
    {
        for (int ix = 0; ix < MoCreatures.multiBlockNames.size(); ix++) 
        {
            subItems.add(new ItemStack(this, 1, ix));
        }
    }
    
    @Override
    protected boolean canPlaceBlockOn(Block block)
    {
        return block == Blocks.grass || block == Blocks.dirt || block == Blocks.farmland || block instanceof MoCBlockDirt || block instanceof MoCBlockGrass;
    }
}