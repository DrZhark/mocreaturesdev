package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoCEntityMantaRay extends MoCEntityRay {

    public MoCEntityMantaRay(World world) {
        super(world);
        setSize(1.8F, 1F);
        setEdad(80 + (this.rand.nextInt(100)));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20D);
    }

    @Override
    public int getMaxEdad() {
        return 180;
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getTexture("mantray.png");
    }

    @Override
    public boolean isMantaRay() {
        return true;
    }
}
