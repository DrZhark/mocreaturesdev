package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCModelKittyBed extends ModelBase {

    ModelRenderer TableL;

    ModelRenderer TableR;

    ModelRenderer Table_B;

    ModelRenderer FoodT;

    ModelRenderer FoodTraySide;

    ModelRenderer FoodTraySideB;

    ModelRenderer FoodTraySideC;

    ModelRenderer FoodTraySideD;

    ModelRenderer Milk;

    ModelRenderer PetFood;

    ModelRenderer Bottom;

    public boolean hasMilk;

    public boolean hasFood;

    public boolean pickedUp;

    public float milklevel;

    public MoCModelKittyBed() {
        float f = 0.0F;
        this.TableL = new ModelRenderer(this, 30, 8);
        this.TableL.addBox(-8F, 0.0F, 7F, 16, 6, 1, f);
        this.TableL.setRotationPoint(0.0F, 18F, 0.0F);
        this.TableR = new ModelRenderer(this, 30, 8);
        this.TableR.addBox(-8F, 18F, -8F, 16, 6, 1, f);
        this.TableR.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Table_B = new ModelRenderer(this, 30, 0);
        this.Table_B.addBox(-8F, -3F, 0.0F, 16, 6, 1, f);
        this.Table_B.setRotationPoint(8F, 21F, 0.0F);
        this.Table_B.rotateAngleY = 1.5708F;
        this.FoodT = new ModelRenderer(this, 14, 0);
        this.FoodT.addBox(1.0F, 1.0F, 1.0F, 4, 1, 4, f);
        this.FoodT.setRotationPoint(-16F, 22F, 0.0F);
        this.FoodTraySide = new ModelRenderer(this, 0, 0);
        this.FoodTraySide.addBox(-16F, 21F, 5F, 5, 3, 1, f);
        this.FoodTraySide.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.FoodTraySideB = new ModelRenderer(this, 0, 0);
        this.FoodTraySideB.addBox(-15F, 21F, 0.0F, 5, 3, 1, f);
        this.FoodTraySideB.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.FoodTraySideC = new ModelRenderer(this, 0, 0);
        this.FoodTraySideC.addBox(-3F, -1F, 0.0F, 5, 3, 1, f);
        this.FoodTraySideC.setRotationPoint(-16F, 22F, 2.0F);
        this.FoodTraySideC.rotateAngleY = 1.5708F;
        this.FoodTraySideD = new ModelRenderer(this, 0, 0);
        this.FoodTraySideD.addBox(-3F, -1F, 0.0F, 5, 3, 1, f);
        this.FoodTraySideD.setRotationPoint(-11F, 22F, 3F);
        this.FoodTraySideD.rotateAngleY = 1.5708F;
        this.Milk = new ModelRenderer(this, 14, 9);
        this.Milk.addBox(0.0F, 0.0F, 0.0F, 4, 1, 4, f);
        this.Milk.setRotationPoint(-15F, 21F, 1.0F);
        this.PetFood = new ModelRenderer(this, 0, 9);
        this.PetFood.addBox(0.0F, 0.0F, 0.0F, 4, 1, 4, f);
        this.PetFood.setRotationPoint(-15F, 21F, 1.0F);
        this.Bottom = new ModelRenderer(this, 16, 15);
        this.Bottom.addBox(-10F, 0.0F, -7F, 16, 1, 14, f);
        this.Bottom.setRotationPoint(2.0F, 23F, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.TableL.render(f5);
        this.TableR.render(f5);
        this.Table_B.render(f5);
        this.Bottom.render(f5);
        if (!this.pickedUp) {
            this.FoodT.render(f5);
            this.FoodTraySide.render(f5);
            this.FoodTraySideB.render(f5);
            this.FoodTraySideC.render(f5);
            this.FoodTraySideD.render(f5);
            if (this.hasMilk) {
                this.Milk.rotationPointY = 21F + this.milklevel;
                this.Milk.render(f5);
            }
            if (this.hasFood) {
                this.PetFood.rotationPointY = 21F + this.milklevel;
                this.PetFood.render(f5);
            }
        }
    }
}
