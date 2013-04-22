package drzhark.mocreatures.block;

import static net.minecraftforge.common.ForgeDirection.UP;

import java.util.Random;

import drzhark.mocreatures.MoCreatures;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;

public class MoCBlock extends Block
{
    public MoCBlock(int par1, Material material)
    {
        super(par1, material);
        
    }
    
        
    @Override
    public boolean canSustainPlant(World world, int x, int y, int z, ForgeDirection direction, IPlantable plant)
    {
        int plantID = plant.getPlantID(world, x, y + 1, z);
        EnumPlantType plantType = plant.getPlantType(world, x, y + 1, z);

        if (plantID == cactus.blockID && blockID == cactus.blockID)
        {
            return true;
        }

        if (plantID == reed.blockID && blockID == reed.blockID)
        {
            return true;
        }
        
        
        if (plant instanceof BlockFlower)
        {
            return true;
        }

        switch (plantType)
        {
            case Desert: return blockID == sand.blockID;
            case Nether: return blockID == slowSand.blockID;
            case Crop:   return blockID == tilledField.blockID;
            case Cave:   return isBlockSolidOnSide(world, x, y, z, UP);
            case Plains: return blockID == grass.blockID || blockID == dirt.blockID;
            case Water:  return world.getBlockMaterial(x, y, z) == Material.water && world.getBlockMetadata(x, y, z) == 0;
            case Beach:
                boolean isBeach = (blockID == Block.grass.blockID || blockID == Block.dirt.blockID || blockID == Block.sand.blockID);
                boolean hasWater = (world.getBlockMaterial(x - 1, y, z    ) == Material.water ||
                                    world.getBlockMaterial(x + 1, y, z    ) == Material.water ||
                                    world.getBlockMaterial(x,     y, z - 1) == Material.water ||
                                    world.getBlockMaterial(x,     y, z + 1) == Material.water);
                return isBeach && hasWater;
        }

        return false;
    }
}
