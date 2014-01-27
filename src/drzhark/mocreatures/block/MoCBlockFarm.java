package drzhark.mocreatures.block;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MoCBlockFarm extends BlockContainer
{
    private int farmRange = 10;
    
    public MoCBlockFarm(int par1)
    {
        super(par1, Material.wood);
        setTickRandomly(true);
        this.setCreativeTab(MoCreatures.tabMoC);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    @Override
    public int damageDropped(int i)
    {
        return i;
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        // TODO Auto-generated method stub
        return null;
    }
}