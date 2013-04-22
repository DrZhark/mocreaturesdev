package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCModelLitterBox extends ModelBase {

    ModelRenderer Table1;

    ModelRenderer Table3;

    ModelRenderer Table2;

    ModelRenderer Litter;

    ModelRenderer Table4;

    ModelRenderer Bottom;

    ModelRenderer LitterUsed;

    public boolean usedlitter;

    public MoCModelLitterBox()
    {
        float f = 0.0F;
        Table1 = new ModelRenderer(this, 30, 0);
        Table1.addBox(-8F, 0.0F, 7F, 16, 6, 1, f);
        Table1.setRotationPoint(0.0F, 18F, 0.0F);
        Table3 = new ModelRenderer(this, 30, 0);
        Table3.addBox(-8F, 18F, -8F, 16, 6, 1, f);
        Table3.setRotationPoint(0.0F, 0.0F, 0.0F);
        Table2 = new ModelRenderer(this, 30, 0);
        Table2.addBox(-8F, -3F, 0.0F, 16, 6, 1, f);
        Table2.setRotationPoint(8F, 21F, 0.0F);
        Table2.rotateAngleY = 1.5708F;
        Litter = new ModelRenderer(this, 0, 15);
        Litter.addBox(0.0F, 0.0F, 0.0F, 16, 2, 14, f);
        Litter.setRotationPoint(-8F, 21F, -7F);
        Table4 = new ModelRenderer(this, 30, 0);
        Table4.addBox(-8F, -3F, 0.0F, 16, 6, 1, f);
        Table4.setRotationPoint(-9F, 21F, 0.0F);
        Table4.rotateAngleY = 1.5708F;
        LitterUsed = new ModelRenderer(this, 16, 15);
        LitterUsed.addBox(0.0F, 0.0F, 0.0F, 16, 2, 14, f);
        LitterUsed.setRotationPoint(-8F, 21F, -7F);
        Bottom = new ModelRenderer(this, 16, 15);
        Bottom.addBox(-10F, 0.0F, -7F, 16, 1, 14, f);
        Bottom.setRotationPoint(2.0F, 23F, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        Table1.render(f5);
        Table3.render(f5);
        Table2.render(f5);
        Table4.render(f5);
        Bottom.render(f5);
        if (usedlitter)
        {
            LitterUsed.render(f5);
        }
        else
        {
            Litter.render(f5);
        }
    }
}
