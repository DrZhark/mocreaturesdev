package drzhark.mocreatures.client.model;

import drzhark.mocreatures.entity.ambient.MoCEntityFirefly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

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

    public MoCModelFirefly() {
        this.textureWidth = 32;
        this.textureHeight = 32;

        this.Head = new ModelRenderer(this, 0, 4);
        this.Head.addBox(-1F, 0F, -1F, 2, 1, 2);
        this.Head.setRotationPoint(0F, 22.5F, -2F);
        setRotation(this.Head, -2.171231F, 0F, 0F);

        this.Antenna = new ModelRenderer(this, 0, 7);
        this.Antenna.addBox(-1F, 0F, 0F, 2, 1, 0);
        this.Antenna.setRotationPoint(0F, 22.5F, -3F);
        setRotation(this.Antenna, -1.665602F, 0F, 0F);

        this.Thorax = new ModelRenderer(this, 0, 0);
        this.Thorax.addBox(-1F, 0F, -1F, 2, 2, 2);
        this.Thorax.setRotationPoint(0F, 21F, -1F);
        setRotation(this.Thorax, 0F, 0F, 0F);

        this.Abdomen = new ModelRenderer(this, 8, 0);
        this.Abdomen.addBox(-1F, 0F, -1F, 2, 2, 2);
        this.Abdomen.setRotationPoint(0F, 22F, 0F);
        setRotation(this.Abdomen, 1.427659F, 0F, 0F);

        this.Tail = new ModelRenderer(this, 8, 17);
        this.Tail.addBox(-1F, 0.5F, -1F, 2, 2, 1);
        this.Tail.setRotationPoint(0F, 21.3F, 1.5F);
        setRotation(this.Tail, 1.13023F, 0F, 0F);

        this.FrontLegs = new ModelRenderer(this, 0, 7);
        this.FrontLegs.addBox(-1F, 0F, 0F, 2, 2, 0);
        this.FrontLegs.setRotationPoint(0F, 23F, -1.8F);
        setRotation(this.FrontLegs, -0.8328009F, 0F, 0F);

        this.MidLegs = new ModelRenderer(this, 0, 9);
        this.MidLegs.addBox(-1F, 0F, 0F, 2, 2, 0);
        this.MidLegs.setRotationPoint(0F, 23F, -1.2F);
        setRotation(this.MidLegs, 1.070744F, 0F, 0F);

        this.RearLegs = new ModelRenderer(this, 0, 9);
        this.RearLegs.addBox(-1F, 0F, 0F, 2, 3, 0);
        this.RearLegs.setRotationPoint(0F, 23F, -0.4F);
        setRotation(this.RearLegs, 1.249201F, 0F, 0F);

        this.RightShellOpen = new ModelRenderer(this, 0, 12);
        this.RightShellOpen.addBox(-1F, 0F, 0F, 2, 0, 5);
        this.RightShellOpen.setRotationPoint(-1F, 21F, -2F);
        setRotation(this.RightShellOpen, 1.22F, 0F, -0.6457718F);

        this.LeftShellOpen = new ModelRenderer(this, 0, 12);
        this.LeftShellOpen.addBox(-1F, 0F, 0F, 2, 0, 5);
        this.LeftShellOpen.setRotationPoint(1F, 21F, -2F);
        setRotation(this.LeftShellOpen, 1.22F, 0F, 0.6457718F);

        this.RightShell = new ModelRenderer(this, 0, 12);
        this.RightShell.addBox(-1F, 0F, 0F, 2, 0, 5);
        this.RightShell.setRotationPoint(-1F, 21F, -2F);
        setRotation(this.RightShell, 0.0174533F, 0F, -0.6457718F);

        this.LeftShell = new ModelRenderer(this, 0, 12);
        this.LeftShell.addBox(-1F, 0F, 0F, 2, 0, 5);
        this.LeftShell.setRotationPoint(1F, 21F, -2F);
        setRotation(this.LeftShell, 0.0174533F, 0F, 0.6457718F);

        this.LeftWing = new ModelRenderer(this, 15, 12);
        this.LeftWing.addBox(-1F, 0F, 0F, 2, 0, 5);
        this.LeftWing.setRotationPoint(1F, 21F, -1F);
        setRotation(this.LeftWing, 0F, 1.047198F, 0F);

        this.RightWing = new ModelRenderer(this, 15, 12);
        this.RightWing.addBox(-1F, 0F, 0F, 2, 0, 5);
        this.RightWing.setRotationPoint(-1F, 21F, -1F);
        setRotation(this.RightWing, 0F, -1.047198F, 0F);

    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        MoCEntityFirefly entityfirefly = (MoCEntityFirefly) entity;
        boolean isFlying = (entityfirefly.getIsFlying() || entityfirefly.motionY < -0.1D);

        setRotationAngles(f, f1, f2, f3, f4, f5, isFlying);
        this.Antenna.render(f5);
        this.RearLegs.render(f5);
        this.MidLegs.render(f5);
        this.Head.render(f5);

        this.Abdomen.render(f5);
        this.FrontLegs.render(f5);
        this.Thorax.render(f5);
        this.Tail.render(f5);

        if (!isFlying) {
            this.RightShell.render(f5);
            this.LeftShell.render(f5);
        } else {
            this.RightShellOpen.render(f5);
            this.LeftShellOpen.render(f5);

            GL11.glPushMatrix();
            GL11.glEnable(3042 /* GL_BLEND */);
            float transparency = 0.6F;
            GL11.glBlendFunc(770, 771);
            GL11.glColor4f(0.8F, 0.8F, 0.8F, transparency);
            this.LeftWing.render(f5);
            this.RightWing.render(f5);
            GL11.glDisable(3042/* GL_BLEND */);
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

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, boolean isFlying) {
        float legMov = 0F;
        float legMovB = 0F;

        float frontLegAdj = 0F;
        if (isFlying) {
            float WingRot = MathHelper.cos((f2 * 1.8F)) * 0.8F;
            this.RightWing.rotateAngleZ = WingRot;
            this.LeftWing.rotateAngleZ = -WingRot;
            legMov = (f1 * 1.5F);
            legMovB = legMov;
            frontLegAdj = 1.4F;

        } else {
            legMov = MathHelper.cos((f * 1.5F) + 3.141593F) * 2.0F * f1;
            legMovB = MathHelper.cos(f * 1.5F) * 2.0F * f1;
        }
        this.FrontLegs.rotateAngleX = -0.8328009F + frontLegAdj + legMov;
        this.MidLegs.rotateAngleX = 1.070744F + legMovB;
        this.RearLegs.rotateAngleX = 1.249201F + legMov;
    }

}
