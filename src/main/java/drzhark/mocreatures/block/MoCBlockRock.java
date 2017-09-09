package drzhark.mocreatures.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class MoCBlockRock extends MoCBlock {

    public MoCBlockRock(String name) {
        super(name, Material.ROCK);
        setTickRandomly(true);
        this.setSoundType(SoundType.STONE);
    }
}
