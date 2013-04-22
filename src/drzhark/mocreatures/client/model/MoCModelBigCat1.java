package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.client.model.ModelRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCModelBigCat1 extends ModelQuadruped {

    public MoCModelBigCat1()
    {
        super(12, 0.0F);
        head = new ModelRenderer(this, 20, 0);
        head.addBox(-7F, -8F, -2F, 14, 14, 8, 0.0F);
        head.setRotationPoint(0.0F, 4F, -8F);
        body = new ModelRenderer(this, 20, 0);
        body.addBox(-6F, -11F, -8F, 12, 10, 10, 0.0F);
        body.setRotationPoint(0.0F, 5F, 2.0F);
    }

}
