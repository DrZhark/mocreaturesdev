package drzhark.mocreatures.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class MoCBlockDirt extends MoCBlock {

    public MoCBlockDirt(String name) {
        super(name, Material.GROUND);
        setTickRandomly(true);
        this.setSoundType(SoundType.GROUND);
    }
}
