package drzhark.mocreatures.block;

import cpw.mods.fml.common.registry.GameRegistry;
import drzhark.mocreatures.MoCreatures;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MoCBlockFarm extends BlockContainer
{
    private int farmRange = 10;
    
    public MoCBlockFarm(String name)
    {
        super(Material.wood);
        setTickRandomly(true);
        this.setBlockName(name);
        this.setCreativeTab(MoCreatures.tabMoC);
        GameRegistry.registerBlock(this, MultiItemBlock.class, name);
    }

    @Override
    public int damageDropped(int i)
    {
        return i;
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2)
    {
        // TODO Auto-generated method stub
        return null;
    }
}