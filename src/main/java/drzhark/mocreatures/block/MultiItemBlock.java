package drzhark.mocreatures.block;

import com.google.common.base.Function;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;

public class MultiItemBlock extends ItemMultiTexture {

    @SuppressWarnings("deprecation")
    public MultiItemBlock(Block block) {
        super(block, block, new Function<ItemStack, String>() {

            public String apply(ItemStack stack) {
                return MoCBlock.EnumType.byMetadata(stack.getMetadata()).getUnlocalizedName();
            }
        });
        setHasSubtypes(true);
        //this.setUnlocalizedName("multiBlock");
        String name = block.getUnlocalizedName().replace("tile.", "").replace("MoC", "").toLowerCase();
        if (net.minecraftforge.fml.common.FMLCommonHandler.instance().getEffectiveSide() == net.minecraftforge.fml.relauncher.Side.CLIENT) {
            ModelBakery.addVariantName(this, "mocreatures:wyvern_" + name);
            ModelBakery.addVariantName(this, "mocreatures:ogre_" + name);
    
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
