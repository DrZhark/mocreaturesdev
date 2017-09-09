package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import net.minecraft.item.ItemFood;

public class MoCItemFood extends ItemFood {

    public MoCItemFood(String name, int j) {
        super(j, 0.6F, false);
        this.setCreativeTab(MoCreatures.tabMoC);
        this.setRegistryName(MoCConstants.MOD_ID, name);
        this.setUnlocalizedName(name);
        this.maxStackSize = 32;
    }

    public MoCItemFood(String name, int j, float f, boolean flag) {
        super(j, f, flag);
        this.setCreativeTab(MoCreatures.tabMoC);
        this.setRegistryName(MoCConstants.MOD_ID, name);
        this.setUnlocalizedName(name);
    }
}
