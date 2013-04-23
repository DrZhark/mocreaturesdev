package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class MoCModelCrab extends ModelBase
{

	ModelRenderer Shell;
	ModelRenderer ShellRight;
	ModelRenderer ShellLeft;
	ModelRenderer ShellBack;
	ModelRenderer LeftEye;
	ModelRenderer LeftEyeBase;
	ModelRenderer RightEyeBase;
	ModelRenderer RightEye;
	ModelRenderer RightArmA;
	ModelRenderer RightArmB;
	ModelRenderer RightArmC;
	ModelRenderer RightArmD;
	ModelRenderer LeftArmA;
	ModelRenderer LeftArmB;
	ModelRenderer LeftArmC;
	ModelRenderer LeftArmD;
	ModelRenderer LeftLeg1A;
	ModelRenderer LeftLeg1B;
	ModelRenderer LeftLeg2A;
	ModelRenderer LeftLeg2B;
	ModelRenderer LeftLeg3A;
	ModelRenderer LeftLeg3B;
	ModelRenderer LeftLeg4A;
	ModelRenderer LeftLeg4B;
	ModelRenderer LeftLeg4C;
	ModelRenderer RightLeg1A;
	ModelRenderer RightLeg1B;
	ModelRenderer RightLeg2A;
	ModelRenderer RightLeg2B;
	ModelRenderer RightLeg3A;
	ModelRenderer RightLeg3B;
	ModelRenderer RightLeg4A;
	ModelRenderer RightLeg4B;
	ModelRenderer RightLeg4C;

	public MoCModelCrab()
	{
		textureWidth = 64;
		textureHeight = 64;

		Shell = new ModelRenderer(this, 0, 0);
		Shell.addBox(-5F, 0F, -4F, 10, 4, 8);
		Shell.setRotationPoint(0F, 16F, 0F);

ShellRight = new ModelRenderer(this, 0, 23);
		ShellRight.addBox(4.6F, -2F, -4F, 3, 3, 8);
		ShellRight.setRotationPoint(0F, 16F, 0F);
		setRotation(ShellRight, 0F, 0F, 0.418879F);
		
		ShellLeft = new ModelRenderer(this, 0, 12);
		ShellLeft.addBox(-7.6F, -2F, -4F, 3, 3, 8);
		ShellLeft.setRotationPoint(0F, 16F, 0F);
		setRotation(ShellLeft, 0F, 0F, -0.418879F);
		
		ShellBack = new ModelRenderer(this, 10, 42);
		ShellBack.addBox(-5F, -1.6F, 3.6F, 10, 3, 3);
		ShellBack.setRotationPoint(0F, 16F, 0F);
		setRotation(ShellBack, -0.418879F, 0F, 0F);
		
		LeftEye = new ModelRenderer(this, 0, 4);
		LeftEye.addBox(1F, -2F, -4.5F, 1, 3, 1);
		LeftEye.setRotationPoint(0F, 16F, 0F);
		setRotation(LeftEye, 0F, 0F, 0.1745329F);
		
		LeftEyeBase = new ModelRenderer(this, 0, 16);
		LeftEyeBase.addBox(1F, 1F, -5F, 2, 3, 1);
		LeftEyeBase.setRotationPoint(0F, 16F, 0F);
		setRotation(LeftEyeBase, 0F, 0F, 0.2094395F);
		
		RightEyeBase = new ModelRenderer(this, 0, 12);
		RightEyeBase.addBox(-3F, 1F, -5F, 2, 3, 1);
		RightEyeBase.setRotationPoint(0F, 16F, 0F);
		setRotation(RightEyeBase, 0F, 0F, -0.2094395F);
		
		RightEye = new ModelRenderer(this, 0, 0);
		RightEye.addBox(-2F, -2F, -4.5F, 1, 3, 1);
		RightEye.setRotationPoint(0F, 16F, 0F);
		setRotation(RightEye, 0F, 0F, -0.1745329F);
		
		RightArmA = new ModelRenderer(this, 0, 34);
		RightArmA.addBox(-4F, -1F, -1F, 4, 2, 2);
		RightArmA.setRotationPoint(-4F, 19F, -4F);
		setRotation(RightArmA, 0F, -0.5235988F, 0F);
		
		RightArmB = new ModelRenderer(this, 22, 12);
		RightArmB.addBox(-3F, -1.5F, -4F, 4, 3, 2);
		RightArmB.setRotationPoint(-4F, 19F, -4F);
		setRotation(RightArmB, 0F, 0.1745329F, 0F);
		
		RightArmC = new ModelRenderer(this, 22, 17);
		RightArmC.addBox(1F, -1.5F, -4F, 3, 1, 2);
		RightArmC.setRotationPoint(-4F, 19F, -4F);
		setRotation(RightArmC, 0F, 0.1745329F, 0F);
		
		RightArmD = new ModelRenderer(this, 16, 12);
		RightArmD.addBox(1F, 0.5F, -3.5F, 2, 1, 1);
		RightArmD.setRotationPoint(-4F, 19F, -4F);
		setRotation(RightArmD, 0F, 0.1745329F, 0F);
		
		LeftArmA = new ModelRenderer(this, 0, 38);
		LeftArmA.addBox(0F, -1F, -1F, 4, 2, 2);
		LeftArmA.setRotationPoint(4F, 19F, -4F);
		setRotation(LeftArmA, 0F, 0.5235988F, 0F);
		
		LeftArmB = new ModelRenderer(this, 22, 20);
		LeftArmB.addBox(-1F, -1.5F, -4F, 4, 3, 2);
		LeftArmB.setRotationPoint(4F, 19F, -4F);
		setRotation(LeftArmB, 0F, -0.1745329F, 0F);
		
		LeftArmC = new ModelRenderer(this, 22, 25);
		LeftArmC.addBox(-4F, -1.5F, -4F, 3, 1, 2);
		LeftArmC.setRotationPoint(4F, 19F, -4F);
		setRotation(LeftArmC, 0F, -0.1745329F, 0F);
		
		LeftArmD = new ModelRenderer(this, 16, 23);
		LeftArmD.addBox(-3F, 0.5F, -3.5F, 2, 1, 1);
		LeftArmD.setRotationPoint(4F, 19F, -4F);
		setRotation(LeftArmD, 0F, -0.1745329F, 0F);
		
		LeftLeg1A = new ModelRenderer(this, 0, 54);
		LeftLeg1A.addBox(-0.4F, -0.5F, -0.5F, 4, 1, 1);
		LeftLeg1A.setRotationPoint(5F, 19.5F, -2.5F);
		setRotation(LeftLeg1A, 0F, 0.1745329F, 0.418879F);
		
		LeftLeg1B = new ModelRenderer(this, 0, 56);
		LeftLeg1B.addBox(3F, -2.5F, -0.5F, 4, 1, 1);
		LeftLeg1B.setRotationPoint(5F, 19.5F, -2.5F);
		setRotation(LeftLeg1B, 0F, 0.1745329F, 0.992464F);
		
		LeftLeg2A = new ModelRenderer(this, 0, 62);
		LeftLeg2A.addBox(-0.4F, -0.5F, -0.5F, 4, 1, 1);
		LeftLeg2A.setRotationPoint(5F, 19.5F, 0.5F);
		setRotation(LeftLeg2A, 0F, -0.0872665F, 0.418879F);
		
		LeftLeg2B = new ModelRenderer(this, 10, 62);
		LeftLeg2B.addBox(3F, -2.5F, -0.5F, 4, 1, 1);
		LeftLeg2B.setRotationPoint(5F, 19.5F, 0.5F);
		setRotation(LeftLeg2B, 0F, -0.0872665F, 0.992464F);
		
		LeftLeg3A = new ModelRenderer(this, 0, 58);
		LeftLeg3A.addBox(-0.4F, -0.5F, -0.5F, 4, 1, 1);
		LeftLeg3A.setRotationPoint(5F, 19.5F, 2.5F);
		setRotation(LeftLeg3A, 0F, -0.6981317F, 0.418879F);
		
		LeftLeg3B = new ModelRenderer(this, 0, 60);
		LeftLeg3B.addBox(3F, -2.5F, -0.5F, 4, 1, 1);
		LeftLeg3B.setRotationPoint(5F, 19.5F, 2.5F);
		setRotation(LeftLeg3B, 0F, -0.6981317F, 0.992464F);
		
		LeftLeg4A = new ModelRenderer(this, 22, 34);
		LeftLeg4A.addBox(-0.6F, -0.5F, -0.5F, 4, 1, 1);
		LeftLeg4A.setRotationPoint(2F, 19.5F, 3.5F);
		setRotation(LeftLeg4A, 0F, -0.6108652F, 0.418879F);
		
		LeftLeg4B = new ModelRenderer(this, 22, 36);
		LeftLeg4B.addBox(1.4F, 0.5F, -3.5F, 3, 1, 2);
		LeftLeg4B.setRotationPoint(2F, 19.5F, 3.5F);
		setRotation(LeftLeg4B, 0F, -1.919862F, 0.418879F);
		
		LeftLeg4C = new ModelRenderer(this, 22, 39);
		LeftLeg4C.addBox(1.4F, 2F, -5.5F, 3, 1, 2);
		LeftLeg4C.setRotationPoint(2F, 19.5F, 3.5F);
		setRotation(LeftLeg4C, 0F, -2.84932F, 0.418879F);
		
		RightLeg1A = new ModelRenderer(this, 0, 42);
		RightLeg1A.addBox(-3.6F, -0.5F, -0.5F, 4, 1, 1);
		RightLeg1A.setRotationPoint(-5F, 19.5F, -2.5F);
		setRotation(RightLeg1A, 0F, -0.1745329F, -0.418879F);
		
		RightLeg1B = new ModelRenderer(this, 0, 48);
		RightLeg1B.addBox(-7F, -2.5F, -0.5F, 4, 1, 1);
		RightLeg1B.setRotationPoint(-5F, 19.5F, -2.5F);
		setRotation(RightLeg1B, 0F, -0.1745329F, -0.9924624F);
		
		RightLeg2A = new ModelRenderer(this, 0, 44);
		RightLeg2A.addBox(-3.6F, -0.5F, -0.5F, 4, 1, 1);
		RightLeg2A.setRotationPoint(-5F, 19.5F, 0.5F);
		setRotation(RightLeg2A, 0F, 0.0872665F, -0.418879F);
		
		RightLeg2B = new ModelRenderer(this, 0, 50);
		RightLeg2B.addBox(-7F, -2.5F, -0.5F, 4, 1, 1);
		RightLeg2B.setRotationPoint(-5F, 19.5F, 0.5F);
		setRotation(RightLeg2B, 0F, 0.0872665F, -0.9924624F);
		
		RightLeg3A = new ModelRenderer(this, 0, 46);
		RightLeg3A.addBox(-3.6F, -0.5F, -0.5F, 4, 1, 1);
		RightLeg3A.setRotationPoint(-5F, 19.5F, 2.5F);
		setRotation(RightLeg3A, 0F, 0.6981317F, -0.418879F);
		
		RightLeg3B = new ModelRenderer(this, 0, 52);
		RightLeg3B.addBox(-7F, -2.5F, -0.5F, 4, 1, 1);
		RightLeg3B.setRotationPoint(-5F, 19.5F, 2.5F);
		setRotation(RightLeg3B, 0F, 0.6981317F, -0.9924624F);
		
		RightLeg4A = new ModelRenderer(this, 12, 34);
		RightLeg4A.addBox(-3.6F, -0.5F, -0.5F, 4, 1, 1);
		RightLeg4A.setRotationPoint(-3F, 19.5F, 3.5F);
		setRotation(RightLeg4A, 0F, 0.6108652F, -0.418879F);
		
		RightLeg4B = new ModelRenderer(this, 12, 36);
		RightLeg4B.addBox(-4.6F, 0.5F, -3.5F, 3, 1, 2);
		RightLeg4B.setRotationPoint(-3F, 19.5F, 3.5F);
		setRotation(RightLeg4B, 0F, 1.919862F, -0.418879F);
		
		RightLeg4C = new ModelRenderer(this, 12, 39);
		RightLeg4C.addBox(-4.6F, 2F, -5.5F, 3, 1, 2);
		RightLeg4C.setRotationPoint(-3F, 19.5F, 3.5F);
		setRotation(RightLeg4C, 0F, 2.849328F, -0.418879F);
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5);
		Shell.render(f5);
		ShellRight.render(f5);
		ShellLeft.render(f5);
		ShellBack.render(f5);
		LeftEye.render(f5);
		LeftEyeBase.render(f5);
		RightEyeBase.render(f5);
		RightEye.render(f5);
		RightArmA.render(f5);
		RightArmB.render(f5);
		RightArmC.render(f5);
		RightArmD.render(f5);
		LeftArmA.render(f5);
		LeftArmB.render(f5);
		LeftArmC.render(f5);
		LeftArmD.render(f5);
		LeftLeg1A.render(f5);
		LeftLeg1B.render(f5);
		LeftLeg2A.render(f5);
		LeftLeg2B.render(f5);
		LeftLeg3A.render(f5);
		LeftLeg3B.render(f5);
		LeftLeg4A.render(f5);
		LeftLeg4B.render(f5);
		LeftLeg4C.render(f5);
		RightLeg1A.render(f5);
		RightLeg1B.render(f5);
		RightLeg2A.render(f5);
		RightLeg2B.render(f5);
		RightLeg3A.render(f5);
		RightLeg3B.render(f5);
		RightLeg4A.render(f5);
		RightLeg4B.render(f5);
		RightLeg4C.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
	{
		
	}

}
