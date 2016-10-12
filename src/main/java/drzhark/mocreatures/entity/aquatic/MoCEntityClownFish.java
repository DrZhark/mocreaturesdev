package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoCEntityClownFish extends MoCEntitySmallFish{

	public MoCEntityClownFish(World world) {
		super(world);
	}
	
	@Override
    public ResourceLocation getTexture() {
		return MoCreatures.proxy.getTexture("smallfish_clownfish.png");
    }

	@Override
	protected int getEggNumber() {
		return 83;
	}
}
