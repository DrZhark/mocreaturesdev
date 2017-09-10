package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoCEntityAnchovy extends MoCEntitySmallFish{

    public MoCEntityAnchovy(World world) {
        super(world);
        this.setType(1);
    }
    
    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getTexture("smallfish_anchovy.png");
    }

    @Override
    protected int getEggNumber() {
        return 80;
    }
}
