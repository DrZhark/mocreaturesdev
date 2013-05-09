package drzhark.mocreatures.client.model;



import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCModelFishy extends ModelBase {

    public ModelRenderer Body;
    public ModelRenderer UpperFin;
    public ModelRenderer LowerFin;
    public ModelRenderer RightFin;
    public ModelRenderer LeftFin;
    public ModelRenderer Tail;

    public MoCModelFishy()
    {
        Body = new ModelRenderer(this, 0, 0);
        Body.addBox(0.0F, 0.0F, -3.5F, 1, 5, 5, 0.0F);
        Body.setRotationPoint(0.0F, 18F, -1F);
        Body.rotateAngleX = 0.7853981F;
        Tail = new ModelRenderer(this, 12, 0);
        Tail.addBox(0.0F, 0.0F, 0.0F, 1, 3, 3, 0.0F);
        Tail.setRotationPoint(0.0F, 20.5F, 3F);
        Tail.rotateAngleX = 0.7853981F;
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        setRotationAngles(f, f1, f2, f3, f4, f5);
        Body.render(f5);
        Tail.render(f5);
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
    {
        Tail.rotateAngleY = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
    }
}
