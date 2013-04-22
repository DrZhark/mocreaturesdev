package drzhark.mocreatures.block;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.MoCreatures;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class MoCBlockLog extends MoCBlock
{
	@SideOnly(Side.CLIENT)
    private Icon[][] icons;
	
    public MoCBlockLog(int par1)
    {
        super(par1, Material.wood);
        //textInt = par2;
        this.setCreativeTab(CreativeTabs.tabBlock);
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
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return MoCreatures.mocLog.blockID;
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
     * Called whenever the block is removed.
     */
    public void onBlockRemoval(World par1World, int par2, int par3, int par4)
    {
        byte byte0 = 4;
        int i = byte0 + 1;

        if (par1World.checkChunksExist(par2 - i, par3 - i, par4 - i, par2 + i, par3 + i, par4 + i))
        {
            for (int j = -byte0; j <= byte0; j++)
            {
                for (int k = -byte0; k <= byte0; k++)
                {
                    for (int l = -byte0; l <= byte0; l++)
                    {
                        int i1 = par1World.getBlockId(par2 + j, par3 + k, par4 + l);

                        if (i1 != MoCreatures.mocLeaf.blockID)
                        {
                            continue;
                        }

                        int j1 = par1World.getBlockMetadata(par2 + j, par3 + k, par4 + l);

                        if ((j1 & 8) == 0)
                        {
                            par1World.setBlockMetadataWithNotify(par2 + j, par3 + k, par4 + l, j1 | 8, 3);
                        }
                    }
                }
            }
        }
    }

        
    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
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
                        int var12 = par1World.getBlockId(par2 + var9, par3 + var10, par4 + var11);

                        if (Block.blocksList[var12] != null)
                        {
                            Block.blocksList[var12].beginLeavesDecay(par1World, par2 + var9, par3 + var10, par4 + var11);
                        }
                    }
                }
            }
        }
    }
    
    
    @Override
    public boolean canSustainLeaves(World world, int x, int y, int z)
    {
        return true;
    }

    @Override
    public boolean isWood(World world, int x, int y, int z)
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

    


    /**
     * Called when a block is placed using its ItemBlock. Args: World, X, Y, Z, side, hitX, hitY, hitZ, block metadata
     */
    /*public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
    {
        int var10 = par9 & 3;
        byte var11 = 0;

        switch (par5)
        {
            case 0:
            case 1:
                var11 = 0;
                break;
            case 2:
            case 3:
                var11 = 8;
                break;
            case 4:
            case 5:
                var11 = 4;
        }

        return var10 | var11;
    }*/


/*    *//**
     * returns a number between 0 and 3
     *//*
    public static int limitToValidMetadata(int par0)
    {
        return par0 & 3;
    }

    
    *//**
     * Returns an item stack containing a single instance of the current block type. 'i' is the block's subtype/damage
     * and is ignored for blocks which do not support subtypes. Blocks which cannot be harvested should return null.
     *//*
    @Override
    protected ItemStack createStackedBlock(int par1)
    {
        return new ItemStack(this.blockID, 1, limitToValidMetadata(par1));
    }
*/
    @SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs tab, List subItems) 
    {
		for (int ix = 0; ix < MoCreatures.multiBlockNames.size(); ix++) {
			subItems.add(new ItemStack(this, 1, ix));
		}
	}

	//func_94332_a = registerIcons(IconRegister)
    //func_94245_a = registerIcon(String)
	@SideOnly(Side.CLIENT)
	@Override
    public void registerIcons(IconRegister par1IconRegister)
    {
		icons = new Icon[MoCreatures.multiBlockNames.size()][2];
        
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
    public Icon getIcon(int par1Side, int Metadata)
    {
		if (par1Side < 2)
		{
			return icons[Metadata][0];
		}
		return icons[Metadata][1];
		
    }
}
