package drzhark.mocreatures.client.model;



import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.entity.ambient.MoCEntityFirefly;

@SideOnly(Side.CLIENT)
public class MoCModelFirefly extends ModelBase {
    //fields
    ModelRenderer Antenna;
    ModelRenderer RearLegs;
    ModelRenderer MidLegs;
    ModelRenderer Head;
    ModelRenderer Tail;
    ModelRenderer Abdomen;
    ModelRenderer FrontLegs;
    ModelRenderer RightShellOpen;
    ModelRenderer LeftShellOpen;
    ModelRenderer Thorax;
    ModelRenderer RightShell;
    ModelRenderer LeftShell;
    ModelRenderer LeftWing;
    ModelRenderer RightWing;

    public MoCModelFirefly()
    {
        textureWidth = 32;
        textureHeight = 32;

        Head = new ModelRenderer(this, 0, 4);
        Head.addBox(-1F, 0F, -1F, 2, 1, 2);
        Head.setRotationPoint(0F, 22.5F, -2F);
        setRotation(Head, -2.171231F, 0F, 0F);

        Antenna = new ModelRenderer(this, 0, 7);
        Antenna.addBox(-1F, 0F, 0F, 2, 1, 0);
        Antenna.setRotationPoint(0F, 22.5F, -3F);
        setRotation(Antenna, -1.665602F, 0F, 0F);

        Thorax = new ModelRenderer(this, 0, 0);
        Thorax.addBox(-1F, 0F, -1F, 2, 2, 2);
        Thorax.setRotationPoint(0F, 21F, -1F);
        setRotation(Thorax, 0F, 0F, 0F);

        Abdomen = new ModelRenderer(this, 8, 0);
        Abdomen.addBox(-1F, 0F, -1F, 2, 2, 2);
        Abdomen.setRotationPoint(0F, 22F, 0F);
        setRotation(Abdomen, 1.427659F, 0F, 0F);

        Tail = new ModelRenderer(this, 8, 17);
        Tail.addBox(-1F, 0.5F, -1F, 2, 2, 1);
        Tail.setRotationPoint(0F, 21.3F, 1.5F);
        setRotation(Tail, 1.13023F, 0F, 0F);

        FrontLegs = new ModelRenderer(this, 0, 7);
        FrontLegs.addBox(-1F, 0F, 0F, 2, 2, 0);
        FrontLegs.setRotationPoint(0F, 23F, -1.8F);
        setRotation(FrontLegs, -0.8328009F, 0F, 0F);

        MidLegs = new ModelRenderer(this, 0, 9);
        MidLegs.addBox(-1F, 0F, 0F, 2, 2, 0);
        MidLegs.setRotationPoint(0F, 23F, -1.2F);
        setRotation(MidLegs, 1.070744F, 0F, 0F);

        RearLegs = new ModelRenderer(this, 0, 9);
        RearLegs.addBox(-1F, 0F, 0F, 2, 3, 0);
        RearLegs.setRotationPoint(0F, 23F, -0.4F);
        setRotation(RearLegs, 1.249201F, 0F, 0F);

        RightShellOpen = new ModelRenderer(this, 0, 12);
        RightShellOpen.addBox(-1F, 0F, 0F, 2, 0, 5);
        RightShellOpen.setRotationPoint(-1F, 21F, -2F);
        setRotation(RightShellOpen, 1.22F, 0F, -0.6457718F);

        LeftShellOpen = new ModelRenderer(this, 0, 12);
        LeftShellOpen.addBox(-1F, 0F, 0F, 2, 0, 5);
        LeftShellOpen.setRotationPoint(1F, 21F, -2F);
        setRotation(LeftShellOpen, 1.22F, 0F, 0.6457718F);

        RightShell = new ModelRenderer(this, 0, 12);
        RightShell.addBox(-1F, 0F, 0F, 2, 0, 5);
        RightShell.setRotationPoint(-1F, 21F, -2F);
        setRotation(RightShell, 0.0174533F, 0F, -0.6457718F);

        LeftShell = new ModelRenderer(this, 0, 12);
        LeftShell.addBox(-1F, 0F, 0F, 2, 0, 5);
        LeftShell.setRotationPoint(1F, 21F, -2F);
        setRotation(LeftShell, 0.0174533F, 0F, 0.6457718F);

        LeftWing = new ModelRenderer(this, 15, 12);
        LeftWing.addBox(-1F, 0F, 0F, 2, 0, 5);
        LeftWing.setRotationPoint(1F, 21F, -1F);
        setRotation(LeftWing, 0F, 1.047198F, 0F);

        RightWing = new ModelRenderer(this, 15, 12);
        RightWing.addBox(-1F, 0F, 0F, 2, 0, 5);
        RightWing.setRotationPoint(-1F, 21F, -1F);
        setRotation(RightWing, 0F, -1.047198F, 0F);

    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        //super.render(entity, f, f1, f2, f3, f4, f5);
        MoCEntityFirefly entityfirefly = (MoCEntityFirefly) entity;
        boolean isFlying = (entityfirefly.getIsFlying() || entityfirefly.isOnAir());
        //boolean onGround = entityfirefly.onGround;

        setRotationAngles(f, f1, f2, f3, f4, f5, isFlying);
        Antenna.render(f5);
        RearLegs.render(f5);
        MidLegs.render(f5);
        Head.render(f5);

        Abdomen.render(f5);
        FrontLegs.render(f5);
        Thorax.render(f5);
        Tail.render(f5);

        if (!isFlying)
        {
            RightShell.render(f5);
            LeftShell.render(f5);
        }
        else
        {
            RightShellOpen.render(f5);
            LeftShellOpen.render(f5);

            GL11.glPushMatrix();
            GL11.glEnable(3042 /*GL_BLEND*/);
            float transparency = 0.6F;
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(0.8F, 0.8F, 0.8F, transparency);
            LeftWing.render(f5);
            RightWing.render(f5);
            GL11.glDisable(3042/*GL_BLEND*/);
            GL11.glPopMatrix();
        }

        //flag = glowing

        //    boolean flag = true;
        //    GL11.glPushMatrix();
        //    GL11.glEnable(3042 /*GL_BLEND*/ );
        //    if (!flag)
        //    {
        //        float transparency = 0.4F;
        //        GL11.glBlendFunc(770, 771);
        //        GL11.glColor4f(0.8F, 0.8F, 0.8F, transparency);
        //    }
        //    else
        //    {
        //        GL11.glBlendFunc(770, 1);
        //        //GL11.glBlendFunc(770, GL11.GL_ONE);
        //    }
        //    
        //    GL11.glDisable(3042/*GL_BLEND*/);
        //    
        //    GL11.glPopMatrix();

    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, boolean isFlying)
    {
        //super.setRotationAngles(f, f1, f2, f3, f4, f5);

        //bipedRightArm.rotateAngleX = MathHelper.cos((f * 0.6662F) + 3.141593F) * 2.0F * f1 * 0.5F;
        //bipedLeftArm.rotateAngleX = MathHelper.cos(f * 0.6662F) * 2.0F * f1 * 0.5F;

        float legMov = 0F;
        float legMovB = 0F;

        float frontLegAdj = 0F;
        if (isFlying)
        {
            float WingRot = MathHelper.cos((f2 * 1.8F)) * 0.8F;
            RightWing.rotateAngleZ = WingRot;
            LeftWing.rotateAngleZ = -WingRot;
            legMov = (f1 * 1.5F);
            legMovB = legMov;
            frontLegAdj = 1.4F;

        }
        else
        {
            legMov = MathHelper.cos((f * 1.5F) + 3.141593F) * 2.0F * f1;
            legMovB = MathHelper.cos(f * 1.5F) * 2.0F * f1;
        }
        FrontLegs.rotateAngleX = -0.8328009F + frontLegAdj + legMov;
        MidLegs.rotateAngleX = 1.070744F + legMovB;
        RearLegs.rotateAngleX = 1.249201F + legMov;
    }

}
