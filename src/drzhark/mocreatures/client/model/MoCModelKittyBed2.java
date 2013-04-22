package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCModelKittyBed2 extends ModelBase {

    ModelRenderer Sheet;

    public MoCModelKittyBed2()
    {
        float f = 0.0F;
        Sheet = new ModelRenderer(this, 0, 15);
        Sheet.addBox(0.0F, 0.0F, 0.0F, 16, 3, 14, f);
        Sheet.setRotationPoint(-8F, 21F, -7F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        Sheet.render(f5);
    }
}
