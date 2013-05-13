package drzhark.mocreatures.block;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


import drzhark.mocreatures.MoCreatures;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class MoCBlockFarm extends BlockContainer
{
    private int farmRange = 10;
    
    public MoCBlockFarm(int par1)
    {
        super(par1, Material.wood);
        setTickRandomly(true);
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