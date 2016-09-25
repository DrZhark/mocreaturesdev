package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class MoCItem extends Item {

    public MoCItem() {
    }

    public MoCItem(String name) {
        this(name, 0);
    }

    public MoCItem(String name, int meta) {
        GameRegistry.registerItem(this, name);
        this.setCreativeTab(MoCreatures.tabMoC);
        this.setUnlocalizedName(name);
        if (!MoCreatures.isServer())
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
                    .register(this, meta, new ModelResourceLocation("mocreatures:" + name, "inventory"));
    }
}
