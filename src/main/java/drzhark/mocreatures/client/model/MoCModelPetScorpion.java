package drzhark.mocreatures.client.model;

import drzhark.mocreatures.entity.passive.MoCEntityPetScorpion;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCModelPetScorpion extends MoCModelScorpion {

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        MoCEntityPetScorpion scorpy = (MoCEntityPetScorpion) entity;
        poisoning = scorpy.swingingTail();
        isTalking = scorpy.mouthCounter != 0;
        babies = scorpy.getHasBabies();
        attacking = scorpy.armCounter;
        sitting = scorpy.getIsSitting();
        setRotationAngles(f, f1, f2, f3, f4, f5);
        renderParts(f5);
    }
}
