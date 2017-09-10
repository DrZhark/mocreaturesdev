package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoCEntityManderin extends MoCEntitySmallFish{

    public MoCEntityManderin(World world) {
        super(world);
        this.setType(7);
    }
    
    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getTexture("smallfish_manderin.png");
    }

    @Override
    protected int getEggNumber() {
        return 86;
    }
}
