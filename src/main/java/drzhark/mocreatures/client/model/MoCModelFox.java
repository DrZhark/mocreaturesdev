package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCModelFox extends ModelBase {

    public ModelRenderer Body;
    public ModelRenderer Leg1;
    public ModelRenderer Leg2;
    public ModelRenderer Leg3;
    public ModelRenderer Leg4;
    public ModelRenderer Snout;
    public ModelRenderer Head;
    public ModelRenderer Tail;
    public ModelRenderer Ears;

    public MoCModelFox() {
        byte byte0 = 8;
        this.Body = new ModelRenderer(this, 0, 0);
        this.Body.addBox(0.0F, 0.0F, 0.0F, 6, 6, 12, 0.0F);
        this.Body.setRotationPoint(-4F, 10F, -6F);
        this.Head = new ModelRenderer(this, 0, 20);
        this.Head.addBox(-3F, -3F, -4F, 6, 6, 4, 0.0F);
        this.Head.setRotationPoint(-1F, 11F, -6F);
        this.Snout = new ModelRenderer(this, 20, 20);
        this.Snout.addBox(-1F, 1.0F, -7F, 2, 2, 4, 0.0F);
        this.Snout.setRotationPoint(-1F, 11F, -6F);
        this.Ears = new ModelRenderer(this, 50, 20);
        this.Ears.addBox(-3F, -6F, -2F, 6, 4, 1, 0.0F);
        this.Ears.setRotationPoint(-1F, 11F, -6F);
        this.Tail = new ModelRenderer(this, 32, 20);
        this.Tail.addBox(-5F, -5F, -2F, 3, 3, 8, 0.0F);
        this.Tail.setRotationPoint(2.5F, 15F, 5F);
        this.Tail.rotateAngleX = -0.5235988F;
        this.Leg1 = new ModelRenderer(this, 0, 0);
        this.Leg1.addBox(-2F, 0.0F, -2F, 3, byte0, 3, 0.0F);
        this.Leg1.setRotationPoint(-2F, 24 - byte0, 5F);
        this.Leg2 = new ModelRenderer(this, 0, 0);
        this.Leg2.addBox(-2F, 0.0F, -2F, 3, byte0, 3, 0.0F);
        this.Leg2.setRotationPoint(1.0F, 24 - byte0, 5F);
        this.Leg3 = new ModelRenderer(this, 0, 0);
        this.Leg3.addBox(-2F, 0.0F, -2F, 3, byte0, 3, 0.0F);
        this.Leg3.setRotationPoint(-2F, 24 - byte0, -4F);
        this.Leg4 = new ModelRenderer(this, 0, 0);
        this.Leg4.addBox(-2F, 0.0F, -2F, 3, byte0, 3, 0.0F);
        this.Leg4.setRotationPoint(1.0F, 24 - byte0, -4F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        setRotationAngles(f, f1, f2, f3, f4, f5);
        this.Body.render(f5);
        this.Leg1.render(f5);
        this.Leg2.render(f5);
        this.Leg3.render(f5);
        this.Leg4.render(f5);
        this.Head.render(f5);
        this.Snout.render(f5);
        this.Tail.render(f5);
        this.Ears.render(f5);
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
        this.Head.rotateAngleY = f3 / 57.29578F;
        this.Head.rotateAngleX = f4 / 57.29578F;
        this.Snout.rotateAngleY = this.Head.rotateAngleY;//f3 / 57.29578F;
        this.Snout.rotateAngleX = this.Head.rotateAngleX;//f4 / 57.29578F;
        //Snout.rotationPointX = 0.0F + ((f3 / 57.29578F) * 0.8F);
        this.Ears.rotateAngleY = this.Head.rotateAngleY;//f3 / 57.29578F;
        this.Ears.rotateAngleX = this.Head.rotateAngleX;//f4 / 57.29578F;
        //Ears.rotationPointX = 0.0F + ((f3 / 57.29578F) * 2.5F);
        this.Leg1.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
        this.Leg2.rotateAngleX = MathHelper.cos((f * 0.6662F) + 3.141593F) * 1.4F * f1;
        this.Leg3.rotateAngleX = MathHelper.cos((f * 0.6662F) + 3.141593F) * 1.4F * f1;
        this.Leg4.rotateAngleX = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
    }
}
