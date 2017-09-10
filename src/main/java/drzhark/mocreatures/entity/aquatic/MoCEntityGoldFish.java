package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoCEntityGoldFish extends MoCEntitySmallFish{

    public MoCEntityGoldFish(World world) {
        super(world);
        this.setType(5);
    }
    
    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getTexture("smallfish_goldfish.png");
    }

    @Override
    protected int getEggNumber() {
        return 84;
    }
}
