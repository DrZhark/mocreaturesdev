package drzhark.mocreatures.client.model;

import drzhark.mocreatures.entity.passive.MoCEntityPetScorpion;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCModelPetScorpion extends MoCModelScorpion {

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        //super.render(entity, f, f1, f2, f3, f4, f5);

        MoCEntityPetScorpion scorpy = (MoCEntityPetScorpion) entity;
        boolean poisoning = scorpy.swingingTail();
        boolean isTalking = scorpy.mouthCounter != 0;
        boolean babies = scorpy.getHasBabies();
        int attacking = scorpy.armCounter;
        setRotationAngles(f, f1, f2, f3, f4, f5, poisoning, isTalking, attacking, babies);
        this.Head.render(f5);
        this.MouthL.render(f5);
        this.MouthR.render(f5);
        this.Body.render(f5);
        this.Tail1.render(f5);
        this.Tail2.render(f5);
        this.Tail3.render(f5);
        this.Tail4.render(f5);
        this.Tail5.render(f5);
        this.Sting1.render(f5);
        this.Sting2.render(f5);
        this.LArm1.render(f5);
        this.LArm2.render(f5);
        this.LArm3.render(f5);
        this.LArm4.render(f5);
        this.RArm1.render(f5);
        this.RArm2.render(f5);
        this.RArm3.render(f5);
        this.RArm4.render(f5);
        this.Leg1A.render(f5);
        this.Leg1B.render(f5);
        this.Leg1C.render(f5);
        this.Leg2A.render(f5);
        this.Leg2B.render(f5);
        this.Leg2C.render(f5);
        this.Leg3A.render(f5);
        this.Leg3B.render(f5);
        this.Leg3C.render(f5);
        this.Leg4A.render(f5);
        this.Leg4B.render(f5);
        this.Leg4C.render(f5);
        this.Leg5A.render(f5);
        this.Leg5B.render(f5);
        this.Leg5C.render(f5);
        this.Leg6A.render(f5);
        this.Leg6B.render(f5);
        this.Leg6C.render(f5);
        this.Leg7A.render(f5);
        this.Leg7B.render(f5);
        this.Leg7C.render(f5);
        this.Leg8A.render(f5);
        this.Leg8B.render(f5);
        this.Leg8C.render(f5);
        if (babies) {
            this.baby1.render(f5);
            this.baby2.render(f5);
            this.baby3.render(f5);
            this.baby4.render(f5);
            this.baby5.render(f5);
        }
    }
}
