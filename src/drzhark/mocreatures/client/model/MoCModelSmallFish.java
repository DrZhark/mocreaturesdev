package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;



public class MoCModelSmallFish extends ModelBase
{

    ModelRenderer BodyFlat;
    ModelRenderer BodyRomboid;
    ModelRenderer MidBodyFin;
    ModelRenderer UpperFinA;
    ModelRenderer UpperFinB;
    ModelRenderer UpperFinC;
    ModelRenderer LowerFinA;
    ModelRenderer LowerFinB;
    ModelRenderer LowerFinC;
    ModelRenderer Tail;

    public MoCModelSmallFish()
    {
        textureWidth = 32;
        textureHeight = 32;

        BodyFlat = new ModelRenderer(this, 0, 2);
        BodyFlat.addBox(0F, -1.5F, -1F, 5, 3, 2);
        BodyFlat.setRotationPoint(-3F, 15F, 0F);
        
        BodyRomboid = new ModelRenderer(this, 0, 7);
        BodyRomboid.addBox(0F, 0F, -0.5F, 4, 4, 1);
        BodyRomboid.setRotationPoint(-4F, 15F, 0F);
        setRotation(BodyRomboid, 0F, 0F, -0.7853982F);
        
        MidBodyFin = new ModelRenderer(this, 0, 12);
        MidBodyFin.addBox(0F, -0.5F, 0F, 4, 2, 4);
        MidBodyFin.setRotationPoint(-3F, 15F, 0F);
        setRotation(MidBodyFin, 0F, 0.7853982F, 0F);
        
        UpperFinA = new ModelRenderer(this, 10, 0);
        UpperFinA.addBox(-0.5F, -1.3F, -0.5F, 2, 1, 1);
        UpperFinA.setRotationPoint(-0.65F, 13.5F, 0F);
        
        UpperFinB = new ModelRenderer(this, 0, 0);
        UpperFinB.addBox(-2.5F, -1F, -0.5F, 4, 1, 1);
        UpperFinB.setRotationPoint(0F, 13.5F, 0F);
        UpperFinB.setTextureSize(32, 32);
        
        UpperFinC = new ModelRenderer(this, 0, 18);
        UpperFinC.addBox(-5F, -2F, 0F, 8, 3, 0);
        UpperFinC.setRotationPoint(0F, 13.5F, 0F);
        
        LowerFinA = new ModelRenderer(this, 16, 0);
        LowerFinA.addBox(-0.5F, -0.3F, -0.5F, 2, 1, 1);
        LowerFinA.setRotationPoint(-0.65F, 17.2F, 0F);
        
        LowerFinB = new ModelRenderer(this, 0, 21);
        LowerFinB.addBox(0F, 0F, -3F, 5, 0, 6);
        LowerFinB.setRotationPoint(-3F, 16F, 0F);
        
        LowerFinC = new ModelRenderer(this, 16, 18);
        LowerFinC.addBox(-5F, 0F, 0F, 8, 3, 0);
        LowerFinC.setRotationPoint(0F, 15.5F, 0F);
          
        Tail = new ModelRenderer(this, 10, 7);
        Tail.addBox(0F, 0F, -0.5F, 3, 3, 1);
        Tail.setRotationPoint(1.3F, 15F, 0F);
        setRotation(Tail, 0F, 0F, -0.7853982F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5);
        BodyFlat.render(f5);
        BodyRomboid.render(f5);
        MidBodyFin.render(f5);
        UpperFinA.render(f5);
        UpperFinB.render(f5);
        UpperFinC.render(f5);
        LowerFinA.render(f5);
        LowerFinB.render(f5);
        LowerFinC.render(f5);
        Tail.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        float tailMov = MathHelper.cos(f * 0.8F) * f1 * 0.6F;
        float finMov = MathHelper.cos(f2 * 0.4F) * 0.2F;
        //float mouthMov = MathHelper.cos(f2 * 0.3F) * 0.2F;
        
        Tail.rotateAngleY = tailMov;
        //setRotation(MidBodyFin, 0F, 0.7853982F, 0F);
        MidBodyFin.rotateAngleY = 0.7853982F + finMov;
        LowerFinB.rotateAngleZ = finMov;
        
    }

}
