package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoCEntitySalmon extends MoCEntityMediumFish{

    public MoCEntitySalmon(World world) {
        super(world);
        this.setType(1);
    }
    
    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getTexture("mediumfish_salmon.png");
    }
    
    @Override
    protected int getEggNumber() {
        return 70;
    }

}
