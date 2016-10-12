package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoCEntityAngelFish extends MoCEntitySmallFish{

	public MoCEntityAngelFish(World world) {
		super(world);
	}
	
	@Override
    public ResourceLocation getTexture() {
		return MoCreatures.proxy.getTexture("smallfish_angelfish.png");
    }

	@Override
	protected int getEggNumber() {
		return 81;
	}
}
