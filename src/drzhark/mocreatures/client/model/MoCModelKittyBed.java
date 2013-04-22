package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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

    public MoCModelKittyBed()
    {
        float f = 0.0F;
        TableL = new ModelRenderer(this, 30, 8);
        TableL.addBox(-8F, 0.0F, 7F, 16, 6, 1, f);
        TableL.setRotationPoint(0.0F, 18F, 0.0F);
        TableR = new ModelRenderer(this, 30, 8);
        TableR.addBox(-8F, 18F, -8F, 16, 6, 1, f);
        TableR.setRotationPoint(0.0F, 0.0F, 0.0F);
        Table_B = new ModelRenderer(this, 30, 0);
        Table_B.addBox(-8F, -3F, 0.0F, 16, 6, 1, f);
        Table_B.setRotationPoint(8F, 21F, 0.0F);
        Table_B.rotateAngleY = 1.5708F;
        FoodT = new ModelRenderer(this, 14, 0);
        FoodT.addBox(1.0F, 1.0F, 1.0F, 4, 1, 4, f);
        FoodT.setRotationPoint(-16F, 22F, 0.0F);
        FoodTraySide = new ModelRenderer(this, 0, 0);
        FoodTraySide.addBox(-16F, 21F, 5F, 5, 3, 1, f);
        FoodTraySide.setRotationPoint(0.0F, 0.0F, 0.0F);
        FoodTraySideB = new ModelRenderer(this, 0, 0);
        FoodTraySideB.addBox(-15F, 21F, 0.0F, 5, 3, 1, f);
        FoodTraySideB.setRotationPoint(0.0F, 0.0F, 0.0F);
        FoodTraySideC = new ModelRenderer(this, 0, 0);
        FoodTraySideC.addBox(-3F, -1F, 0.0F, 5, 3, 1, f);
        FoodTraySideC.setRotationPoint(-16F, 22F, 2.0F);
        FoodTraySideC.rotateAngleY = 1.5708F;
        FoodTraySideD = new ModelRenderer(this, 0, 0);
        FoodTraySideD.addBox(-3F, -1F, 0.0F, 5, 3, 1, f);
        FoodTraySideD.setRotationPoint(-11F, 22F, 3F);
        FoodTraySideD.rotateAngleY = 1.5708F;
        Milk = new ModelRenderer(this, 14, 9);
        Milk.addBox(0.0F, 0.0F, 0.0F, 4, 1, 4, f);
        Milk.setRotationPoint(-15F, 21F, 1.0F);
        PetFood = new ModelRenderer(this, 0, 9);
        PetFood.addBox(0.0F, 0.0F, 0.0F, 4, 1, 4, f);
        PetFood.setRotationPoint(-15F, 21F, 1.0F);
        Bottom = new ModelRenderer(this, 16, 15);
        Bottom.addBox(-10F, 0.0F, -7F, 16, 1, 14, f);
        Bottom.setRotationPoint(2.0F, 23F, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        TableL.render(f5);
        TableR.render(f5);
        Table_B.render(f5);
        Bottom.render(f5);
        if (!pickedUp)
        {
            FoodT.render(f5);
            FoodTraySide.render(f5);
            FoodTraySideB.render(f5);
            FoodTraySideC.render(f5);
            FoodTraySideD.render(f5);
            if (hasMilk)
            {
                Milk.rotationPointY = 21F + milklevel;
                Milk.render(f5);
            }
            if (hasFood)
            {
                PetFood.rotationPointY = 21F + milklevel;
                PetFood.render(f5);
            }
        }
    }
}
