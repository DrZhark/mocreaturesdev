package drzhark.mocreatures.block;

import com.google.common.base.Function;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class MultiItemBlock extends ItemMultiTexture {

    public MultiItemBlock(Block block) {
        super(block, block, new Function<ItemStack, String>() {

            public String apply(ItemStack stack) {
                return MoCBlock.EnumType.byMetadata(stack.getMetadata()).getUnlocalizedName();
            }
        });
        setHasSubtypes(true);
        String name = block.getUnlocalizedName().replace("tile.", "").replace("MoC", "").toLowerCase();
        if (net.minecraftforge.fml.common.FMLCommonHandler.instance().getEffectiveSide() == net.minecraftforge.fml.relauncher.Side.CLIENT) {
            ModelBakery.registerItemVariants(this, new ResourceLocation("mocreatures:wyvern_" + name));
            ModelBakery.registerItemVariants(this, new ResourceLocation("mocreatures:ogre_" + name));
    
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
                    .register(this, 0, new ModelResourceLocation("mocreatures:wyvern_" + name, "inventory"));
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
                    .register(this, 1, new ModelResourceLocation("mocreatures:ogre_" + name, "inventory"));
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
                    .register(this, 2, new ModelResourceLocation("mocreatures:" + name, "variant=wyvern_lair"));
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
                    .register(this, 3, new ModelResourceLocation("mocreatures:" + name, "variant=ogre_lair"));
        }
    }

    @Override
    public int getMetadata(int damageValue) {
        return damageValue;
    }

}
