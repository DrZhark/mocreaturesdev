package drzhark.mocreatures.client.model;

import net.minecraft.entity.Entity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.entity.passive.MoCEntityPetScorpion;

@SideOnly(Side.CLIENT)
public class MoCModelPetScorpion extends MoCModelScorpion {

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        //super.render(entity, f, f1, f2, f3, f4, f5);

        MoCEntityPetScorpion scorpy = (MoCEntityPetScorpion) entity;
        boolean poisoning = scorpy.swingingTail();
        boolean isTalking = scorpy.mouthCounter != 0;
        boolean babies = scorpy.getHasBabies();
        int attacking = scorpy.armCounter;
        setRotationAngles(f, f1, f2, f3, f4, f5, poisoning, isTalking, attacking, babies);
        Head.render(f5);
        MouthL.render(f5);
        MouthR.render(f5);
        Body.render(f5);
        Tail1.render(f5);
        Tail2.render(f5);
        Tail3.render(f5);
        Tail4.render(f5);
        Tail5.render(f5);
        Sting1.render(f5);
        Sting2.render(f5);
        LArm1.render(f5);
        LArm2.render(f5);
        LArm3.render(f5);
        LArm4.render(f5);
        RArm1.render(f5);
        RArm2.render(f5);
        RArm3.render(f5);
        RArm4.render(f5);
        Leg1A.render(f5);
        Leg1B.render(f5);
        Leg1C.render(f5);
        Leg2A.render(f5);
        Leg2B.render(f5);
        Leg2C.render(f5);
        Leg3A.render(f5);
        Leg3B.render(f5);
        Leg3C.render(f5);
        Leg4A.render(f5);
        Leg4B.render(f5);
        Leg4C.render(f5);
        Leg5A.render(f5);
        Leg5B.render(f5);
        Leg5C.render(f5);
        Leg6A.render(f5);
        Leg6B.render(f5);
        Leg6C.render(f5);
        Leg7A.render(f5);
        Leg7B.render(f5);
        Leg7C.render(f5);
        Leg8A.render(f5);
        Leg8B.render(f5);
        Leg8C.render(f5);
        if (babies)
        {
            baby1.render(f5);
            baby2.render(f5);
            baby3.render(f5);
            baby4.render(f5);
            baby5.render(f5);
        }
    }
}
