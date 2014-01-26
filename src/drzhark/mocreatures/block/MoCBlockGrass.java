package drzhark.mocreatures.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.MoCreatures;

public class MoCBlockGrass extends MoCBlock
{
    @SideOnly(Side.CLIENT)
    private IIcon[][] icons;

    public MoCBlockGrass(String name)
    {
        super(name, Material.grass);
        setTickRandomly(true);
    }

    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if (!MoCreatures.isServer())
        {
            return;
        }

        if (par1World.getBlockLightValue(par2, par3 + 1, par4) < 4 && par1World.getBlock(par2, par3 + 1, par4).getLightOpacity() > 2)
        {
            par1World.setBlock(par2, par3, par4, MoCreatures.mocDirt, this.getDamageValue(par1World, par2, par3, par4), 3);
        }
        else if (par1World.getBlockLightValue(par2, par3 + 1, par4) >= 9)
        {
            for (int i = 0; i < 45; i++)
            {
                int j = (par2 + par5Random.nextInt(3)) - 1;
                int k = (par3 + par5Random.nextInt(5)) - 3;
                int l = (par4 + par5Random.nextInt(3)) - 1;
                Block block = par1World.getBlock(j, k + 1, l);

                if (par1World.getBlock(j, k, l) == MoCreatures.mocDirt && par1World.getBlockLightValue(j, k + 1, l) >= 4 && block.getLightOpacity() <= 2)
                {
                    par1World.setBlock(j, k, l, MoCreatures.mocGrass, this.getDamageValue(par1World, j, k, l), 3);
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List subItems) 
    {
        for (int ix = 0; ix < MoCreatures.multiBlockNames.size(); ix++) {
            subItems.add(new ItemStack(item, 1, ix));
        }
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
        icons = new IIcon[MoCreatures.multiBlockNames.size()][3];
        
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
    public IIcon getIcon(int par1Side, int Metadata)
    {
        if (par1Side < 0 || par1Side > 2) par1Side = 2;
        if (Metadata < 0 || Metadata >= MoCreatures.multiBlockNames.size()) Metadata = 0;
        return icons[Metadata][par1Side];
    }
}