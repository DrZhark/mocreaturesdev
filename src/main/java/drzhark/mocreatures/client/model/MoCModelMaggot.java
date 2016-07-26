package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class MoCModelMaggot extends ModelBase {

    ModelRenderer Head;
    ModelRenderer Body;
    ModelRenderer Tail;
    ModelRenderer Tailtip;

    public MoCModelMaggot() {
        this.textureWidth = 32;
        this.textureHeight = 32;

        this.Head = new ModelRenderer(this, 0, 11);
        this.Head.addBox(-1F, -1F, -2F, 2, 2, 2);
        this.Head.setRotationPoint(0F, 23F, -2F);

        this.Body = new ModelRenderer(this, 0, 0);
        this.Body.addBox(-1.5F, -2F, 0F, 3, 3, 4);
        this.Body.setRotationPoint(0F, 23F, -2F);

        this.Tail = new ModelRenderer(this, 0, 7);
        this.Tail.addBox(-1F, -1F, 0F, 2, 2, 2);
        this.Tail.setRotationPoint(0F, 23F, 2F);

        this.Tailtip = new ModelRenderer(this, 8, 7);
        this.Tailtip.addBox(-0.5F, 0F, 0F, 1, 1, 1);
        this.Tailtip.setRotationPoint(0F, 23F, 4F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        //super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5);

        //f1 = movement speed!
        //f2 = timer!
        //System.out.println("f2 = " + f2);

        GL11.glPushMatrix();
        GL11.glEnable(3042 /* GL_BLEND */);
        //float transparency = 0.9F;
        GL11.glBlendFunc(770, 771);
        //GL11.glColor4f(1.2F, 1.2F, 1.2F, transparency);
        float f9 = -(MathHelper.cos(f * 3F)) * f1 * 2F;
        //GL11.glScalef(1.0F, 1.0F, 1.0F + (f1 * 3F));
        GL11.glScalef(1.0F, 1.0F, 1.0F + (f9));

        this.Head.render(f5);
        this.Body.render(f5);
        this.Tail.render(f5);
        this.Tailtip.render(f5);
        GL11.glDisable(3042/* GL_BLEND */);
        GL11.glPopMatrix();

    }

    @SuppressWarnings("unused")
    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {

    }

}
