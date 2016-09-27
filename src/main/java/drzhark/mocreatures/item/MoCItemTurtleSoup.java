package drzhark.mocreatures.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCItemTurtleSoup extends MoCItemFood {

    public MoCItemTurtleSoup(String name, int j) {
        super(name, j);
        this.maxStackSize = 1;
    }

    public MoCItemTurtleSoup(String name, int j, float f, boolean flag) {
        super(name, j, f, flag);
        this.maxStackSize = 1;
    }

    @Override
    @Nullable
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        super.onItemUseFinish(stack, worldIn, entityLiving);
        return new ItemStack(Items.BOWL);
    }
}
