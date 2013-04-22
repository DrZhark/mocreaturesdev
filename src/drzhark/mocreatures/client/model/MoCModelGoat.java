package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCModelGoat extends ModelBase {
    ModelRenderer Leg1;
    ModelRenderer Leg2;
    ModelRenderer Leg3;
    ModelRenderer Leg4;
    ModelRenderer Body;
    ModelRenderer Tail;
    ModelRenderer LEar;
    ModelRenderer REar;
    ModelRenderer Head;
    ModelRenderer Nose;
    ModelRenderer Tongue;
    ModelRenderer Mouth;
    ModelRenderer RHorn1;
    ModelRenderer RHorn2;
    ModelRenderer RHorn3;
    ModelRenderer RHorn4;
    ModelRenderer RHorn5;
    ModelRenderer LHorn1;
    ModelRenderer LHorn2;
    ModelRenderer LHorn3;
    ModelRenderer LHorn4;
    ModelRenderer LHorn5;
    ModelRenderer Goatie;
    ModelRenderer Neck;
    ModelRenderer Tits;

    public int typeInt;
    public int attacking;
    public float edad;
    public boolean bleat;
    public int legMov;
    public int earMov;
    public int tailMov;
    public int eatMov;

    public MoCModelGoat()
    {
        textureWidth = 64;
        textureHeight = 32;

        Leg1 = new ModelRenderer(this, 0, 23);
        Leg1.addBox(-1F, 0F, -1F, 2, 7, 2);
        Leg1.setRotationPoint(2F, 17F, -6F);

        // Leg1.mirror = true;

        Leg2 = new ModelRenderer(this, 0, 23);
        Leg2.addBox(-1F, 0F, -1F, 2, 7, 2);
        Leg2.setRotationPoint(-2F, 17F, -6F);

        // Leg2.mirror = true;

        Leg3 = new ModelRenderer(this, 0, 23);
        Leg3.addBox(-1F, 0F, -1F, 2, 7, 2);
        Leg3.setRotationPoint(-2F, 17F, 6F);

        // Leg3.mirror = true;

        Leg4 = new ModelRenderer(this, 0, 23);
        Leg4.addBox(-1F, 0F, -1F, 2, 7, 2);
        Leg4.setRotationPoint(2F, 17F, 6F);

        // Leg4.mirror = true;

        Body = new ModelRenderer(this, 20, 8);
        Body.addBox(-3F, -4F, -8F, 6, 8, 16);
        Body.setRotationPoint(0F, 13F, 0F);

        Tail = new ModelRenderer(this, 22, 8);
        Tail.addBox(-1.5F, -1F, 0F, 3, 2, 4);
        Tail.setRotationPoint(0F, 10F, 8F);

        LEar = new ModelRenderer(this, 52, 8);
        LEar.addBox(1.5F, -2F, 0F, 2, 1, 1);
        LEar.setRotationPoint(0F, 8F, -12F);

        // LEar.mirror = true;

        REar = new ModelRenderer(this, 52, 8);
        REar.addBox(-3.5F, -2F, 0F, 2, 1, 1);
        REar.setRotationPoint(0F, 8F, -12F);

        // REar.mirror = true;

        Head = new ModelRenderer(this, 52, 16);
        Head.addBox(-1.5F, -2F, -2F, 3, 5, 3);
        Head.setRotationPoint(0F, 8F, -12F);

        Nose = new ModelRenderer(this, 52, 10);
        Nose.addBox(-1.5F, -1F, -5F, 3, 3, 3);
        Nose.setRotationPoint(0F, 8F, -12F);

        Tongue = new ModelRenderer(this, 56, 5);
        Tongue.addBox(-0.5F, 2F, -5F, 1, 0, 3);
        Tongue.setRotationPoint(0F, 8F, -12F);

        Mouth = new ModelRenderer(this, 54, 0);
        Mouth.addBox(-1F, 2F, -5F, 2, 1, 3);
        Mouth.setRotationPoint(0F, 8F, -12F);

        RHorn1 = new ModelRenderer(this, 0, 0);
        RHorn1.addBox(-1.5F, -3F, -0.7F, 1, 1, 1, 0.1F);
        RHorn1.setRotationPoint(0F, 8F, -12F);

        RHorn2 = new ModelRenderer(this, 0, 0);
        RHorn2.addBox(-1.9F, -4F, -0.2F, 1, 1, 1);
        RHorn2.setRotationPoint(0F, 8F, -12F);

        RHorn3 = new ModelRenderer(this, 0, 0);
        RHorn3.addBox(-2.1F, -4.8F, 0.5F, 1, 1, 1, -0.05F);
        RHorn3.setRotationPoint(0F, 8F, -12F);

        RHorn4 = new ModelRenderer(this, 0, 0);
        RHorn4.addBox(-2.3F, -5.2F, 1.4F, 1, 1, 1, -0.1F);
        RHorn4.setRotationPoint(0F, 8F, -12F);

        RHorn5 = new ModelRenderer(this, 0, 0);
        RHorn5.addBox(-2.6F, -4.9F, 2.0F, 1, 1, 1, -0.15F);
        RHorn5.setRotationPoint(0F, 8F, -12F);

        LHorn1 = new ModelRenderer(this, 0, 0);
        LHorn1.addBox(0.5F, -3F, -0.7F, 1, 1, 1, 0.1F);
        LHorn1.setRotationPoint(0F, 8F, -12F);

        LHorn2 = new ModelRenderer(this, 0, 0);
        LHorn2.addBox(0.9F, -4F, -0.2F, 1, 1, 1);
        LHorn2.setRotationPoint(0F, 8F, -12F);

        LHorn3 = new ModelRenderer(this, 0, 0);
        LHorn3.addBox(1.2F, -4.9F, 0.5F, 1, 1, 1, -0.05F);
        LHorn3.setRotationPoint(0F, 8F, -12F);

        LHorn4 = new ModelRenderer(this, 0, 0);
        LHorn4.addBox(1.4F, -5.3F, 1.4F, 1, 1, 1, -0.1F);
        LHorn4.setRotationPoint(0F, 8F, -12F);

        LHorn5 = new ModelRenderer(this, 0, 0);
        LHorn5.addBox(1.7F, -4.9F, 2.1F, 1, 1, 1, -0.15F);
        LHorn5.setRotationPoint(0F, 8F, -12F);

        Goatie = new ModelRenderer(this, 52, 5);
        Goatie.addBox(-0.5F, 3F, -4F, 1, 2, 1);
        Goatie.setRotationPoint(0F, 8F, -12F);

        Neck = new ModelRenderer(this, 18, 14);
        Neck.addBox(-1.5F, -2.0F, -5F, 3, 4, 6, -0.2F);
        Neck.setRotationPoint(0F, 11F, -8F);
        Neck.rotateAngleX = -24 / 57.29578F;

        Tits = new ModelRenderer(this, 18, 0);
        Tits.addBox(-2.5F, 0F, -2F, 5, 1, 4);
        Tits.setRotationPoint(0F, 17F, 3F);

    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5);
        Leg1.render(f5);
        Leg2.render(f5);
        Leg3.render(f5);
        Leg4.render(f5);
        Body.render(f5);
        Tail.render(f5);
        Neck.render(f5);
        if (typeInt > 1 && typeInt < 5)
        {
            Tits.render(f5);
        }
        GL11.glPushMatrix();

        if (attacking != 0)
        {
            // float yOff = f5*((2.0F*attacking)/45F)-1.33333F;
            // float zOff = f5*(attacking/9F)-3.33333F;

            // 0.3! yOff = (3F*attacking/450F) - (3F*3F/45F);
            // float yOff = 0.3F;
            // yOff = (3F*attacking/450F) - (3F*3F/45F);
            float yOff = (attacking / 150F) - (1F / 5F);
            // float zOff = 0.1F;
            float zOff = (attacking / 450F) - (1F / 15F);
            GL11.glTranslatef(0.0F, yOff, -zOff);
        }
        LEar.render(f5);
        REar.render(f5);
        Head.render(f5);
        Nose.render(f5);

        if (typeInt > 1)
        {
            if (edad > 0.7)
            {
                RHorn1.render(f5);
                LHorn1.render(f5);
            }
            if (edad > 0.8)
            {
                RHorn2.render(f5);
                LHorn2.render(f5);
            }
        }
        if (typeInt > 4)
        {
            if (edad > 0.8)
            {
                RHorn3.render(f5);
                LHorn3.render(f5);
            }
            if (edad > 0.85)
            {
                RHorn4.render(f5);
                LHorn4.render(f5);
            }
            if (edad > 0.9)
            {
                RHorn5.render(f5);
                LHorn5.render(f5);

                // Goatie.render(f5);
            }
        }
        // mouth movement
        // GL11.glTranslatef(eatMov, 0.0F, 0.0F);
        if (eatMov != 0 && !bleat)
        {
            GL11.glTranslatef((float) (eatMov / 100F), 0.0F, 0.0F);
        }
        if (typeInt > 4 && edad > 0.9)
        {
            Goatie.render(f5);
        }
        Tongue.render(f5);
        Mouth.render(f5);

        GL11.glPopMatrix();

    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        //super.setRotationAngles(f, f1, f2, f3, f4, f5);
        Leg1.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        Leg2.rotateAngleX = MathHelper.cos((f * 0.6662F) + 3.141593F) * 1.4F * f1;
        Leg3.rotateAngleX = MathHelper.cos((f * 0.6662F) + 3.141593F) * 1.4F * f1;
        Leg4.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        float baseAngle = (30 / 57.29578F) + (f4 / 57.29578F);

        // neck = -30 to 30
        // head 30 to 75

        if (f3 > 20F)
        {
            f3 = 20F;
        }
        if (f3 < -20F)
        {
            f3 = -20F;
        }
        Head.rotateAngleY = (f3 / 57.29578F);// * 0.6F; //fixes SMP bug
        Neck.rotateAngleX = -30 / 57.29578F;
        Tail.rotateAngleX = tailMov / 57.29578F;

        Head.rotateAngleX = baseAngle;
        if (bleat)
        {
            Head.rotateAngleX = -15 / 57.29578F;
        }
        if (attacking != 0)
        {
            Head.rotateAngleX = attacking / 57.29578F;
            Neck.rotateAngleX = ((1.33F * attacking) - 70) / 57.29578F;
            if (legMov != 0)
            {
                Leg1.rotateAngleX = legMov / 57.29578F;
                // leg -20 to + 30
                // 0 to -20 normal speed
                // -20 to 30 fast x2 or x3
            }
        }

        LEar.rotateAngleX = Head.rotateAngleX;
        REar.rotateAngleX = Head.rotateAngleX;
        if (!bleat && attacking == 0)
        {
            LEar.rotateAngleX = baseAngle + (earMov / 57.29578F);
            REar.rotateAngleX = baseAngle + (earMov / 57.29578F);
        }

        Nose.rotateAngleX = Head.rotateAngleX;
        Mouth.rotateAngleX = Head.rotateAngleX;
        Tongue.rotateAngleX = Head.rotateAngleX;
        Goatie.rotateAngleX = Head.rotateAngleX;
        RHorn1.rotateAngleX = Head.rotateAngleX;
        LHorn1.rotateAngleX = Head.rotateAngleX;
        RHorn2.rotateAngleX = Head.rotateAngleX;
        LHorn2.rotateAngleX = Head.rotateAngleX;
        RHorn3.rotateAngleX = Head.rotateAngleX;
        LHorn3.rotateAngleX = Head.rotateAngleX;
        RHorn4.rotateAngleX = Head.rotateAngleX;
        LHorn4.rotateAngleX = Head.rotateAngleX;
        RHorn5.rotateAngleX = Head.rotateAngleX;
        LHorn5.rotateAngleX = Head.rotateAngleX;
        if (bleat)// && !attacking)
        {
            Mouth.rotateAngleX = 0.0F;
            Tongue.rotateAngleX = -5 / 57.29578F;
            Goatie.rotateAngleX = 0.0F;
        }

        Nose.rotateAngleY = Head.rotateAngleY;
        Mouth.rotateAngleY = Head.rotateAngleY;
        Tongue.rotateAngleY = Head.rotateAngleY;
        LEar.rotateAngleY = Head.rotateAngleY;
        REar.rotateAngleY = Head.rotateAngleY;
        Goatie.rotateAngleY = Head.rotateAngleY;
        RHorn1.rotateAngleY = Head.rotateAngleY;
        LHorn1.rotateAngleY = Head.rotateAngleY;
        RHorn2.rotateAngleY = Head.rotateAngleY;
        LHorn2.rotateAngleY = Head.rotateAngleY;
        RHorn3.rotateAngleY = Head.rotateAngleY;
        LHorn3.rotateAngleY = Head.rotateAngleY;
        RHorn4.rotateAngleY = Head.rotateAngleY;
        LHorn4.rotateAngleY = Head.rotateAngleY;
        RHorn5.rotateAngleY = Head.rotateAngleY;
        LHorn5.rotateAngleY = Head.rotateAngleY;

    }
}
