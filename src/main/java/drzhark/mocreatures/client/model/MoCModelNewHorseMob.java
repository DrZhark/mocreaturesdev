package drzhark.mocreatures.client.model;

import drzhark.mocreatures.entity.monster.MoCEntityHorseMob;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCModelNewHorseMob extends MoCModelNewHorse {

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        MoCEntityHorseMob entityhorse = (MoCEntityHorseMob) entity;

        int type = entityhorse.getType();
        boolean wings = (entityhorse.isFlyer());
        boolean eating = (entityhorse.eatingCounter != 0);//entityhorse.getEating();
        boolean standing = (entityhorse.standCounter != 0 && entityhorse.riddenByEntity == null);
        boolean openMouth = (entityhorse.mouthCounter != 0);
        boolean moveTail = (entityhorse.tailCounter != 0);
        boolean flapwings = (entityhorse.wingFlapCounter != 0);
        boolean rider = (entityhorse.riddenByEntity != null);
        boolean floating = (entityhorse.isFlyer() && entityhorse.isOnAir());

        setRotationAngles(f, f1, f2, f3, f4, f5, eating, rider, floating, standing, false, moveTail, wings, flapwings, false, 0);
        this.Ear1.render(f5);
        this.Ear2.render(f5);
        this.Neck.render(f5);
        this.Head.render(f5);
        if (openMouth) {
            this.UMouth2.render(f5);
            this.LMouth2.render(f5);
        } else {
            this.UMouth.render(f5);
            this.LMouth.render(f5);
        }
        this.Mane.render(f5);
        this.Body.render(f5);
        this.TailA.render(f5);
        this.TailB.render(f5);
        this.TailC.render(f5);

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

        if (entityhorse.isUnicorned()) {
            this.Unicorn.render(f5);
        }
        if (entityhorse.isFlyer() && type != 34 && type != 36)//pegasus
        {
            this.MidWing.render(f5);
            this.InnerWing.render(f5);
            this.OuterWing.render(f5);
            this.InnerWingR.render(f5);
            this.MidWingR.render(f5);
            this.OuterWingR.render(f5);
        }
    }
}
