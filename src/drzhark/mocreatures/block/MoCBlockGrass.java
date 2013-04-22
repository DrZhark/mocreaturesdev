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
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class MoCBlockGrass extends MoCBlock
{
	 
	@SideOnly(Side.CLIENT)
    private Icon[][] icons;
	
	
	public MoCBlockGrass(int par1)
    {
        super(par1, Material.grass);
        setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
	
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
		
        if (!MoCreatures.isServer())
        {
            return;
        }

        
        if (par1World.getBlockLightValue(par2, par3 + 1, par4) < 4 && Block.lightOpacity[par1World.getBlockId(par2, par3 + 1, par4)] > 2)
        {
            //par1World.setBlockWithNotify(par2, par3, par4, MoCreatures.mocDirt.blockID);
            par1World.setBlock(par2, par3, par4, MoCreatures.mocDirt.blockID, this.getDamageValue(par1World, par2, par3, par4), 3);
        }
        else if (par1World.getBlockLightValue(par2, par3 + 1, par4) >= 9)
        {
            for (int i = 0; i < 45; i++)
            {
                int j = (par2 + par5Random.nextInt(3)) - 1;
                int k = (par3 + par5Random.nextInt(5)) - 3;
                int l = (par4 + par5Random.nextInt(3)) - 1;
                int i1 = par1World.getBlockId(j, k + 1, l);

                if (par1World.getBlockId(j, k, l) == MoCreatures.mocDirt.blockID && par1World.getBlockLightValue(j, k + 1, l) >= 4 && Block.lightOpacity[i1] <= 2)
                {
                    par1World.setBlock(j, k, l, MoCreatures.mocGrass.blockID, this.getDamageValue(par1World, j, k, l), 3);
                }
            }
        }
    }
	
	
	//old one without metadata
    /*public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (!MoCreatures.isServer())
        {
            return;
        }

        if (par1World.getBlockLightValue(par2, par3 + 1, par4) < 4 && Block.lightOpacity[par1World.getBlockId(par2, par3 + 1, par4)] > 2)
        {
            par1World.setBlockWithNotify(par2, par3, par4, MoCreatures.mocDirt.blockID);
        }
        else if (par1World.getBlockLightValue(par2, par3 + 1, par4) >= 9)
        {
            for (int i = 0; i < 45; i++)
            {
                int j = (par2 + par5Random.nextInt(3)) - 1;
                int k = (par3 + par5Random.nextInt(5)) - 3;
                int l = (par4 + par5Random.nextInt(3)) - 1;
                int i1 = par1World.getBlockId(j, k + 1, l);

                if (par1World.getBlockId(j, k, l) == MoCreatures.mocDirt.blockID && par1World.getBlockLightValue(j, k + 1, l) >= 4 && Block.lightOpacity[i1] <= 2)
                {
                    par1World.setBlockWithNotify(j, k, l, MoCreatures.mocGrass.blockID);
                }
            }
        }
    }*/
    
    
   
    public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        super.randomDisplayTick(par1World, par2, par3, par4, par5Random);

        if (par5Random.nextInt(75) == 0)
        {
            par1World.spawnParticle("depthsuspend", (float)par2 + par5Random.nextFloat(), (float)par3 + 1.1F, (float)par4 + par5Random.nextFloat(), 20.0D, 20.0D, 20.0D);
        }
    }


    
    @SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs tab, List subItems) 
    {
		for (int ix = 0; ix < MoCreatures.multiBlockNames.size(); ix++) {
			subItems.add(new ItemStack(this, 1, ix));
		}
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
		icons = new Icon[MoCreatures.multiBlockNames.size()][3];
        
		for (int x = 0; x < MoCreatures.multiBlockNames.size(); x++)
		{
			icons[x][0] = par1IconRegister.registerIcon("mocreatures:" + "dirt_" + MoCreatures.multiBlockNames.get(x));
			icons[x][1] = par1IconRegister.registerIcon("mocreatures:" + "grassTop_" + MoCreatures.multiBlockNames.get(x));
			icons[x][2] = par1IconRegister.registerIcon("mocreatures:" + "grassSide_" + MoCreatures.multiBlockNames.get(x));
		}
    }
   
    
	/**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
	
	
	@SideOnly(Side.CLIENT)
	@Override
    public Icon getIcon(int par1Side, int Metadata)
    {
		if (par1Side > 2) par1Side = 2;
        return icons[Metadata][par1Side];
    }
    
	
}


