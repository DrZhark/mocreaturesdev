package drzhark.mocreatures.client.model;



import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.entity.ambient.MoCEntityBee;

@SideOnly(Side.CLIENT)
public class MoCModelBee extends ModelBase {

    ModelRenderer Abdomen;
    ModelRenderer FrontLegs;
    ModelRenderer RAntenna;
    ModelRenderer LAntenna;
    ModelRenderer RightWing;
    ModelRenderer RearLegs;
    ModelRenderer MidLegs;
    ModelRenderer Head;
    ModelRenderer Mouth;
    ModelRenderer Tail;
    ModelRenderer FoldedWings;
    ModelRenderer LeftWing;
    ModelRenderer Thorax;

    public MoCModelBee()
    {
        textureWidth = 32;
        textureHeight = 32;

        Head = new ModelRenderer(this, 0, 9);
        Head.addBox(-1F, 0F, -1F, 2, 1, 2);
        Head.setRotationPoint(0F, 21.5F, -2F);
        setRotation(Head, -2.171231F, 0F, 0F);

        RAntenna = new ModelRenderer(this, 0, 17);
        RAntenna.addBox(-0.5F, 0F, -1F, 1, 0, 1);
        RAntenna.setRotationPoint(-0.5F, 20.2F, -2.3F);
        setRotation(RAntenna, -1.041001F, 0.7853982F, 0F);

        LAntenna = new ModelRenderer(this, 0, 12);
        LAntenna.addBox(-0.5F, 0F, -1F, 1, 0, 1);
        LAntenna.setRotationPoint(0.5F, 20.2F, -2.3F);
        setRotation(LAntenna, -1.041001F, -0.7853982F, 0F);

        Mouth = new ModelRenderer(this, 0, 13);
        Mouth.addBox(0F, 0F, -1F, 1, 1, 1);
        Mouth.setRotationPoint(0F, 21.5F, -2F);
        setRotation(Mouth, -0.4461433F, 0.3569147F, 0.7853982F);

        Thorax = new ModelRenderer(this, 0, 5);
        Thorax.addBox(-1F, 0F, -1F, 2, 2, 2);
        Thorax.setRotationPoint(0F, 20.5F, -1F);

        Abdomen = new ModelRenderer(this, 0, 0);
        Abdomen.addBox(-1F, 0F, -1F, 2, 3, 2);
        Abdomen.setRotationPoint(0F, 21.5F, 0F);
        setRotation(Abdomen, 1.249201F, 0F, 0F);

        Tail = new ModelRenderer(this, 0, 15);
        Tail.addBox(-0.5F, 0F, 0F, 1, 1, 1);
        Tail.setRotationPoint(0F, 22F, 2F);
        setRotation(Tail, 0.2379431F, 0F, 0F);

        FrontLegs = new ModelRenderer(this, 4, 14);
        FrontLegs.addBox(-1F, 0F, 0F, 2, 2, 0);
        FrontLegs.setRotationPoint(0F, 22F, -1.8F);
        setRotation(FrontLegs, 0.1487144F, 0F, 0F);

        RearLegs = new ModelRenderer(this, 8, 1);
        RearLegs.addBox(-1.5F, 0F, 0F, 3, 3, 0);
        RearLegs.setRotationPoint(0F, 22.5F, -0.4F);
        setRotation(RearLegs, 0.8922867F, 0F, 0F);

        MidLegs = new ModelRenderer(this, 4, 12);
        MidLegs.addBox(-1F, 0F, 0F, 2, 2, 0);
        MidLegs.setRotationPoint(0F, 22.5F, -1.2F);
        setRotation(MidLegs, 0.5948578F, 0F, 0F);

        /*RightWing = new ModelRenderer(this, 8, 11);
        RightWing.addBox(-4F, -3F, 0F, 4, 6, 0);
        RightWing.setRotationPoint(-0.5F, 14F, -1F);
        setRotation(RightWing, 0F, 0F, 0.0872665F);
        
        LeftWing = new ModelRenderer(this, 8, 5);
        LeftWing.addBox(0F, -3F, 0F, 4, 6, 0);
        LeftWing.setRotationPoint(0.5F, 14F, -1F);
        setRotation(LeftWing, 0F, 0F, -0.0872665F);*/

        LeftWing = new ModelRenderer(this, 0, 17);
        LeftWing.addBox(-1F, 0F, 0.5F, 2, 0, 4);
        LeftWing.setRotationPoint(0F, 20.4F, -1F);
        setRotation(LeftWing, 0F, 1.047198F, 0F);

        RightWing = new ModelRenderer(this, 0, 17);
        RightWing.addBox(-1F, 0F, 0.5F, 2, 0, 4);
        RightWing.setRotationPoint(0F, 20.4F, -1F);
        setRotation(RightWing, 0F, -1.047198F, 0F);

        FoldedWings = new ModelRenderer(this, 0, 17);
        FoldedWings.addBox(-1F, 0F, 0F, 2, 0, 4);
        FoldedWings.setRotationPoint(0F, 20.5F, -1F);
        setRotation(FoldedWings, 0.0001745F, 0F, 0F);

    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        MoCEntityBee entitybee = (MoCEntityBee) entity;
        boolean isFlying = (entitybee.getIsFlying() || entitybee.isOnAir());
        //boolean onGround = entitybee.onGround;

        setRotationAngles(f, f1, f2, f3, f4, f5, !isFlying);
        Abdomen.render(f5);
        FrontLegs.render(f5);
        RAntenna.render(f5);
        LAntenna.render(f5);

        RearLegs.render(f5);
        MidLegs.render(f5);
        Head.render(f5);
        Mouth.render(f5);
        Tail.render(f5);
        //FoldedWings.render(f5);
        //LeftWing.render(f5);
        //RightWing.render(f5);
        Thorax.render(f5);

        if (!isFlying)
        {
            FoldedWings.render(f5);
        }
        else
        {
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
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, boolean onGround)
    {
        //super.setRotationAngles(f, f1, f2, f3, f4, f5);
        float WingRot = MathHelper.cos((f2 * 3.0F)) * 0.7F;
        RightWing.rotateAngleZ = WingRot;
        LeftWing.rotateAngleZ = -WingRot;
        float legMov = 0F;
        float legMovB = 0F;

        if (!onGround)
        {
            legMov = (f1 * 1.5F);
            legMovB = legMov;
        }
        else
        {
            legMov = MathHelper.cos((f * 1.5F) + 3.141593F) * 2.0F * f1;
            legMovB = MathHelper.cos(f * 1.5F) * 2.0F * f1;
        }

        FrontLegs.rotateAngleX = 0.1487144F + legMov;
        MidLegs.rotateAngleX = 0.5948578F + legMovB;
        RearLegs.rotateAngleX = 1.070744F + legMov;
    }
}
