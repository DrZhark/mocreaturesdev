package drzhark.mocreatures.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.MoCreatures;

public class MoCBlockLog extends MoCBlock
{
    @SideOnly(Side.CLIENT)
    private IIcon[][] icons;

    public MoCBlockLog(String name)
    {
        super(name, Material.wood);
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random par1Random)
    {
        return 1;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public Item getItemDropped(int par1, Random par2Random, int par3)
    {
        return Item.getItemFromBlock(MoCreatures.mocLog);
    }

    @Override
    public int damageDropped(int i)
    {
        return i;
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
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6)
    {
        byte var7 = 4;
        int var8 = var7 + 1;

        if (par1World.checkChunksExist(par2 - var8, par3 - var8, par4 - var8, par2 + var8, par3 + var8, par4 + var8))
        {
            for (int var9 = -var7; var9 <= var7; ++var9)
            {
                for (int var10 = -var7; var10 <= var7; ++var10)
                {
                    for (int var11 = -var7; var11 <= var7; ++var11)
                    {
                        Block block = par1World.getBlock(par2 + var9, par3 + var10, par4 + var11);

                        if (block != null)
                        {
                            block.beginLeavesDecay(par1World, par2 + var9, par3 + var10, par4 + var11);
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean canSustainLeaves(IBlockAccess world, int x, int y, int z)
    {
        return true;
    }

    @Override
    public boolean isWood(IBlockAccess world, int x, int y, int z)
    {
        return true;
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return 31;
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List subItems)
    {
        for (int ix = 0; ix < MoCreatures.multiBlockNames.size(); ix++) {
            subItems.add(new ItemStack(item, 1, ix));
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        icons = new IIcon[MoCreatures.multiBlockNames.size()][2];
        
        for (int x = 0; x < MoCreatures.multiBlockNames.size(); x++)
        {
            icons[x][0] = par1IconRegister.registerIcon("mocreatures:" + "logTop_" + MoCreatures.multiBlockNames.get(x));
            icons[x][1] = par1IconRegister.registerIcon("mocreatures:" + "logSide_" + MoCreatures.multiBlockNames.get(x));
        }
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int par1Side, int Metadata)
    {
        if (Metadata < 0 || Metadata >= MoCreatures.multiBlockNames.size()) Metadata = 0;
        if (par1Side < 2)
        {
            return icons[Metadata][0];
        }
        return icons[Metadata][1];
    }
}