package drzhark.mocreatures.client.model;



import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCModelBird extends ModelBase {

    public ModelRenderer head;
    public ModelRenderer body;
    public ModelRenderer leftleg;
    public ModelRenderer rightleg;
    public ModelRenderer rwing;
    public ModelRenderer lwing;
    public ModelRenderer beak;
    public ModelRenderer tail;

    public MoCModelBird()
    {
        byte byte0 = 16;
        head = new ModelRenderer(this, 0, 0);
        head.addBox(-1.5F, -3F, -2F, 3, 3, 3, 0.0F);
        head.setRotationPoint(0.0F, -1 + byte0, -4F);
        beak = new ModelRenderer(this, 14, 0);
        beak.addBox(-0.5F, -1.5F, -3F, 1, 1, 2, 0.0F);
        beak.setRotationPoint(0.0F, -1 + byte0, -4F);
        body = new ModelRenderer(this, 0, 9);
        body.addBox(-2F, -4F, -3F, 4, 8, 4, 0.0F);
        body.setRotationPoint(0.0F, 0 + byte0, 0.0F);
        body.rotateAngleX = 1.047198F;
        leftleg = new ModelRenderer(this, 26, 0);
        leftleg.addBox(-1F, 0.0F, -4F, 3, 4, 3);
        leftleg.setRotationPoint(-2F, 3 + byte0, 1.0F);
        rightleg = new ModelRenderer(this, 26, 0);
        rightleg.addBox(-1F, 0.0F, -4F, 3, 4, 3);
        rightleg.setRotationPoint(1.0F, 3 + byte0, 1.0F);
        rwing = new ModelRenderer(this, 24, 13);
        rwing.addBox(-1F, 0.0F, -3F, 1, 5, 5);
        rwing.setRotationPoint(-2F, -2 + byte0, 0.0F);
        lwing = new ModelRenderer(this, 24, 13);
        lwing.addBox(0.0F, 0.0F, -3F, 1, 5, 5);
        lwing.setRotationPoint(2.0F, -2 + byte0, 0.0F);
        tail = new ModelRenderer(this, 0, 23);
        tail.addBox(-6F, 5F, 2.0F, 4, 1, 4, 0.0F);
        tail.setRotationPoint(4F, -3 + byte0, 0.0F);
        tail.rotateAngleX = 0.261799F;
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        head.render(f5);
        beak.render(f5);
        body.render(f5);
        leftleg.render(f5);
        rightleg.render(f5);
        rwing.render(f5);
        lwing.render(f5);
        tail.render(f5);
    }

    @Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)
    {
        head.rotateAngleX = -(f4 / 2.0F / 57.29578F);
        //head.rotateAngleY = f3 / 2.0F / 57.29578F; //fixed SMP bug
        head.rotateAngleY = f3 / 57.29578F;
        beak.rotateAngleY = head.rotateAngleY;
        leftleg.rotateAngleX = MathHelper.cos(f * 0.6662F) * f1;
        rightleg.rotateAngleX = MathHelper.cos((f * 0.6662F) + 3.141593F) * f1;
        rwing.rotateAngleZ = f2;
        lwing.rotateAngleZ = -f2;
    }

    //public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {}
    //public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity) {}

}
