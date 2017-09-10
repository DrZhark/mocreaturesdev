package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoCEntityBass extends MoCEntityMediumFish{

    public MoCEntityBass(World world) {
        super(world);
        this.setType(3);
    }
    
    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getTexture("mediumfish_bass.png");
    }
    
    @Override
    protected int getEggNumber() {
        return 72;
    }

}
