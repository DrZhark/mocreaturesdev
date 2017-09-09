package drzhark.mocreatures.init.recipes;

import drzhark.mocreatures.init.MoCItems;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoCRecipe implements IRecipe {

    @Override
    public IRecipe setRegistryName(ResourceLocation name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ResourceLocation getRegistryName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Class<IRecipe> getRegistryType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean matches(InventoryCrafting inv, World worldIn) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean canFit(int width, int height) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return new ItemStack(MoCItems.fishnet, 1);
    }

}
