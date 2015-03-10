package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class MoCItem extends Item {

    public MoCItem(String name) {
        GameRegistry.registerItem(this, name);
        this.setCreativeTab(MoCreatures.tabMoC);
        this.setUnlocalizedName(name);
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
                .register(this, 0, new ModelResourceLocation("mocreatures:" + name, "inventory"));
    }

    // TODO
    /*@SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister par1IconRegister) {
        this.itemIcon = par1IconRegister.registerIcon("mocreatures" + this.getUnlocalizedName().replaceFirst("item.", ":"));
    }*/
}
