package drzhark.mocreatures.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;

public class MultiItemBlock extends ItemMultiTexture {

    public MultiItemBlock(Block block) {
        super(block, block, new ItemMultiTexture.Mapper() {

            public String apply(ItemStack stack) {
                return MoCBlock.EnumType.byMetadata(stack.getMetadata()).getUnlocalizedName();
            }
        });
        setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int damageValue) {
        return damageValue;
    }

}
