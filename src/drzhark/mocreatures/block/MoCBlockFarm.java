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
    //private int textureINT;
    private int farmRange = 10;
    
    public MoCBlockFarm(int par1)
    {
        super(par1, Material.wood);
        //textureINT = par2;
        setTickRandomly(true);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    /*public String getTextureFile()
    {
       return MoCreatures.proxy.BLOCK_PNG;//"/mocreatures/blocks.png";
    }*/
    
    @Override
    public int damageDropped(int i)
    {
        return i;
        //int var4 = this.worldObj.getEntitiesWithinAABB(var13.getClass(), AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)this.xCoord, (double)this.yCoord, (double)this.zCoord, (double)(this.xCoord + 1), (double)(this.yCoord + 1), (double)(this.zCoord + 1)).expand((double)(this.spawnRange * 2), 4.0D, (double)(this.spawnRange * 2))).size();

    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        // TODO Auto-generated method stub
        return null;
    }
    
//    @Override
//    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
//    {
//        
//        if (!MoCreatures.isServer())
//        {
//            return;
//        }
//
//        List nearbyEntities = par1World.getEntitiesWithinAABB(EntityAnimal.class, AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)this.minX, (double)this.minY, (double)this.minZ, (double)(this.maxX), (double)(this.maxY), (double)(this.maxZ)).expand((double)(this.farmRange * 2), 4.0D, (double)(this.farmRange * 2)));
//        //int var4 = this.par1World.getEntitiesWithinAABB(var13.getClass(), AxisAlignedBB.getAABBPool().addOrModifyAABBInPool((double)this.xCoord, (double)this.yCoord, (double)this.zCoord, (double)(this.xCoord + 1), (double)(this.yCoord + 1), (double)(this.zCoord + 1)).expand((double)(this.spawnRange * 2), 4.0D, (double)(this.spawnRange * 2))).size();
//        System.out.println("running farm block code");
//        for (int x = 0; x < nearbyEntities.size(); x++)
//        {
//            EntityAnimal entity = (EntityAnimal) nearbyEntities.get(x);
//            System.out.println("resetting age: "+ entity.getAge() + " of " + entity);
//            entity.attackEntityFrom(DamageSource.generic, 0);
//            System.out.println("to: "+ entity.getAge() + entity);
//            
//        }    
//    }
   
   /* public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        super.randomDisplayTick(par1World, par2, par3, par4, par5Random);

        if (par5Random.nextInt(75) == 0)
        {
            par1World.spawnParticle("depthsuspend", (float)par2 + par5Random.nextFloat(), (float)par3 + 1.1F, (float)par4 + par5Random.nextFloat(), 20.0D, 20.0D, 20.0D);
        }
    }*/

//    public int getIcon (int i, int j)
//    {
//        if (i == 0) return textureINT + (j*3); //Bottom
//        if (i == 1) return textureINT + 1 + (j*3);//Top
//        return textureINT + 2 + (j*3);//Side
//    }
    
//    @SideOnly(Side.CLIENT)
//    public void getSubBlocks(int par1, CreativeTabs tab, List subItems) 
//    {
//        for (int ix = 0; ix < MoCreatures.multiBlockNames.size(); ix++) {
//            subItems.add(new ItemStack(this, 1, ix));
//        }
//    }

    /*@Override
    public TileEntity createNewTileEntity(World var1) 
    {
        return new TileEntityMoCFarm();
    }*/
    

}




