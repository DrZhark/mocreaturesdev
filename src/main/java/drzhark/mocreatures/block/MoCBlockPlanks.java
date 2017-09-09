package drzhark.mocreatures.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class MoCBlockPlanks extends MoCBlock {

    public MoCBlockPlanks(String name) {
        super(name, Material.WOOD);
        this.setSoundType(SoundType.WOOD);
    }
}
