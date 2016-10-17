package drzhark.mocreatures.client.model;

import drzhark.mocreatures.entity.passive.MoCEntityBear;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCModelBear extends ModelBase {

    public ModelRenderer Saddle;
    public ModelRenderer SaddleBack;
    public ModelRenderer SaddleFront;
    public ModelRenderer Bag;
    public ModelRenderer SaddleSitted;
    public ModelRenderer SaddleBackSitted;
    public ModelRenderer SaddleFrontSitted;
    public ModelRenderer BagSitted;

    ModelRenderer Tail;
    ModelRenderer Mouth;
    ModelRenderer LegFR1;
    ModelRenderer Neck;
    ModelRenderer LEar;
    ModelRenderer Snout;
    ModelRenderer Head;
    ModelRenderer REar;
    ModelRenderer Abdomen;
    ModelRenderer Torso;
    ModelRenderer LegRR3;
    ModelRenderer LegRR1;
    ModelRenderer LegRR2;
    ModelRenderer LegFR2;
    ModelRenderer LegFR3;
    ModelRenderer LegFL1;
    ModelRenderer LegFL3;
    ModelRenderer LegFL2;
    ModelRenderer LegRL1;
    ModelRenderer LegRL2;
    ModelRenderer LegRL3;
    ModelRenderer MouthOpen;

    ModelRenderer BHead;
    ModelRenderer BSnout;
    ModelRenderer BMouth;
    ModelRenderer BMouthOpen;
    ModelRenderer BNeck;
    ModelRenderer BLEar;
    ModelRenderer BREar;
    ModelRenderer BTorso;
    ModelRenderer BAbdomen;
    ModelRenderer BTail;
    ModelRenderer BLegFL1;
    ModelRenderer BLegFL2;
    ModelRenderer BLegFL3;
    ModelRenderer BLegFR1;
    ModelRenderer BLegFR2;
    ModelRenderer BLegFR3;
    ModelRenderer BLegRL1;
    ModelRenderer BLegRL2;
    ModelRenderer BLegRL3;
    ModelRenderer BLegRR1;
    ModelRenderer BLegRR2;
    ModelRenderer BLegRR3;

    ModelRenderer CHead;
    ModelRenderer CSnout;
    ModelRenderer CMouth;
    ModelRenderer CMouthOpen;
    ModelRenderer CLEar;
    ModelRenderer CREar;
    ModelRenderer CNeck;
    ModelRenderer CTorso;
    ModelRenderer CAbdomen;
    ModelRenderer CTail;
    ModelRenderer CLegFL1;
    ModelRenderer CLegFL2;
    ModelRenderer CLegFL3;
    ModelRenderer CLegFR1;
    ModelRenderer CLegFR2;
    ModelRenderer CLegFR3;
    ModelRenderer CLegRL1;
    ModelRenderer CLegRL2;
    ModelRenderer CLegRL3;
    ModelRenderer CLegRR1;
    ModelRenderer CLegRR2;
    ModelRenderer CLegRR3;

    private int bearstate;
    private float attackSwing;

    public MoCModelBear() {
        this.textureWidth = 128;
        this.textureHeight = 128;

        this.Head = new ModelRenderer(this, 19, 0);
        this.Head.addBox(-4F, 0F, -4F, 8, 8, 5);
        this.Head.setRotationPoint(0F, 6F, -10F);
        setRotation(this.Head, 0.1502636F, 0F, 0F);

        this.Mouth = new ModelRenderer(this, 24, 21);
        this.Mouth.addBox(-1.5F, 6F, -6.8F, 3, 2, 5);
        this.Mouth.setRotationPoint(0F, 6F, -10F);
        setRotation(this.Mouth, -0.0068161F, 0F, 0F);

        this.MouthOpen = new ModelRenderer(this, 24, 21);
        this.MouthOpen.addBox(-1.5F, 4F, -9.5F, 3, 2, 5);
        this.MouthOpen.setRotationPoint(0F, 6F, -10F);
        setRotation(this.MouthOpen, 0.534236F, 0F, 0F);

        this.LEar = new ModelRenderer(this, 40, 0);
        this.LEar.addBox(2F, -2F, -2F, 3, 3, 1);
        this.LEar.setRotationPoint(0F, 6F, -10F);
        setRotation(this.LEar, 0.1502636F, -0.3490659F, 0.1396263F);

        this.REar = new ModelRenderer(this, 16, 0);
        this.REar.addBox(-5F, -2F, -2F, 3, 3, 1);
        this.REar.setRotationPoint(0F, 6F, -10F);
        setRotation(this.REar, 0.1502636F, 0.3490659F, -0.1396263F);

        this.Snout = new ModelRenderer(this, 23, 13);
        this.Snout.addBox(-2F, 3F, -8F, 4, 3, 5);
        this.Snout.setRotationPoint(0F, 6F, -10F);
        setRotation(this.Snout, 0.1502636F, 0F, 0F);

        this.Neck = new ModelRenderer(this, 18, 28);
        this.Neck.addBox(-3.5F, 0F, -7F, 7, 7, 7);
        this.Neck.setRotationPoint(0F, 5F, -5F);
        setRotation(this.Neck, 0.2617994F, 0F, 0F);

        this.Abdomen = new ModelRenderer(this, 13, 62);
        this.Abdomen.addBox(-4.5F, 0F, 0F, 9, 11, 10);
        this.Abdomen.setRotationPoint(0F, 5F, 5F);
        setRotation(this.Abdomen, -0.4363323F, 0F, 0F);

        this.Torso = new ModelRenderer(this, 12, 42);
        this.Torso.addBox(-5F, 0F, 0F, 10, 10, 10);
        this.Torso.setRotationPoint(0F, 5F, -5F);

        this.Tail = new ModelRenderer(this, 26, 83);
        this.Tail.addBox(-1.5F, 0F, 0F, 3, 3, 3);
        this.Tail.setRotationPoint(0F, 8.466666F, 12F);
        setRotation(this.Tail, 0.4363323F, 0F, 0F);

        this.LegFL1 = new ModelRenderer(this, 40, 22);
        this.LegFL1.addBox(-2.5F, 0F, -2.5F, 5, 8, 5);
        this.LegFL1.setRotationPoint(4F, 10F, -4F);
        setRotation(this.LegFL1, 0.2617994F, 0F, 0F);

        this.LegFL2 = new ModelRenderer(this, 46, 35);
        this.LegFL2.addBox(-2F, 7F, 0F, 4, 6, 4);
        this.LegFL2.setRotationPoint(4F, 10F, -4F);

        this.LegFL3 = new ModelRenderer(this, 46, 45);
        this.LegFL3.addBox(-2F, 12F, -1F, 4, 2, 5);
        this.LegFL3.setRotationPoint(4F, 10F, -4F);

        this.LegFR1 = new ModelRenderer(this, 4, 22);
        this.LegFR1.addBox(-2.5F, 0F, -2.5F, 5, 8, 5);
        this.LegFR1.setRotationPoint(-4F, 10F, -4F);
        setRotation(this.LegFR1, 0.2617994F, 0F, 0F);

        this.LegFR2 = new ModelRenderer(this, 2, 35);
        this.LegFR2.addBox(-2F, 7F, 0F, 4, 6, 4);
        this.LegFR2.setRotationPoint(-4F, 10F, -4F);

        this.LegFR3 = new ModelRenderer(this, 0, 45);
        this.LegFR3.addBox(-2F, 12F, -1F, 4, 2, 5);
        this.LegFR3.setRotationPoint(-4F, 10F, -4F);

        this.LegRL1 = new ModelRenderer(this, 34, 83);
        this.LegRL1.addBox(-1.5F, 0F, -2.5F, 4, 8, 6);
        this.LegRL1.setRotationPoint(3.5F, 11F, 9F);
        setRotation(this.LegRL1, -0.1745329F, 0F, 0F);

        this.LegRL2 = new ModelRenderer(this, 41, 97);
        this.LegRL2.addBox(-2F, 6F, -1F, 4, 6, 4);
        this.LegRL2.setRotationPoint(3.5F, 11F, 9F);

        this.LegRL3 = new ModelRenderer(this, 44, 107);
        this.LegRL3.addBox(-2F, 11F, -2F, 4, 2, 5);
        this.LegRL3.setRotationPoint(3.5F, 11F, 9F);

        this.LegRR1 = new ModelRenderer(this, 10, 83);
        this.LegRR1.addBox(-2.5F, 0F, -2.5F, 4, 8, 6);
        this.LegRR1.setRotationPoint(-3.5F, 11F, 9F);
        setRotation(this.LegRR1, -0.1745329F, 0F, 0F);

        this.LegRR2 = new ModelRenderer(this, 7, 97);
        this.LegRR2.addBox(-2F, 6F, -1F, 4, 6, 4);
        this.LegRR2.setRotationPoint(-3.5F, 11F, 9F);

        this.LegRR3 = new ModelRenderer(this, 2, 107);
        this.LegRR3.addBox(-2F, 11F, -2F, 4, 2, 5);
        this.LegRR3.setRotationPoint(-3.5F, 11F, 9F);

        //---standing

        this.BHead = new ModelRenderer(this, 19, 0);
        this.BHead.addBox(-4F, 0F, -4F, 8, 8, 5);
        this.BHead.setRotationPoint(0F, -12F, 5F);
        setRotation(this.BHead, -0.0242694F, 0F, 0F);

        this.BSnout = new ModelRenderer(this, 23, 13);
        this.BSnout.addBox(-2F, 2.5F, -8.5F, 4, 3, 5);
        this.BSnout.setRotationPoint(0F, -12F, 5F);
        setRotation(this.BSnout, -0.0242694F, 0F, 0F);

        this.BMouth = new ModelRenderer(this, 24, 21);
        this.BMouth.addBox(-1.5F, 5.5F, -8.0F, 3, 2, 5);
        this.BMouth.setRotationPoint(0F, -12F, 5F);
        setRotation(this.BMouth, -0.08726F, 0F, 0F);

        this.BMouthOpen = new ModelRenderer(this, 24, 21);
        this.BMouthOpen.addBox(-1.5F, 3.5F, -11F, 3, 2, 5);
        this.BMouthOpen.setRotationPoint(0F, -12F, 5F);
        setRotation(this.BMouthOpen, 0.5235988F, 0F, 0F);

        this.BNeck = new ModelRenderer(this, 18, 28);
        this.BNeck.addBox(-3.5F, 0F, -7F, 7, 6, 7);
        this.BNeck.setRotationPoint(0F, -3F, 11F);
        setRotation(this.BNeck, -1.336881F, 0F, 0F);

        this.BLEar = new ModelRenderer(this, 40, 0);
        this.BLEar.addBox(2F, -2F, -2F, 3, 3, 1);
        this.BLEar.setRotationPoint(0F, -12F, 5F);
        setRotation(this.BLEar, -0.0242694F, -0.3490659F, 0.1396263F);

        this.BREar = new ModelRenderer(this, 16, 0);
        this.BREar.addBox(-5F, -2F, -2F, 3, 3, 1);
        this.BREar.setRotationPoint(0F, -12F, 5F);
        setRotation(this.BREar, -0.0242694F, 0.3490659F, -0.1396263F);

        this.BTorso = new ModelRenderer(this, 12, 42);
        this.BTorso.addBox(-5F, 0F, 0F, 10, 10, 10);
        this.BTorso.setRotationPoint(0F, -3.5F, 12.3F);
        setRotation(this.BTorso, -1.396263F, 0F, 0F);

        this.BAbdomen = new ModelRenderer(this, 13, 62);
        this.BAbdomen.addBox(-4.5F, 0F, 0F, 9, 11, 10);
        this.BAbdomen.setRotationPoint(0F, 6F, 14F);
        setRotation(this.BAbdomen, -1.570796F, 0F, 0F);

        this.BTail = new ModelRenderer(this, 26, 83);
        this.BTail.addBox(-1.5F, 0F, 0F, 3, 3, 3);
        this.BTail.setRotationPoint(0F, 12.46667F, 12.6F);
        setRotation(this.BTail, 0.3619751F, 0F, 0F);

        this.BLegFL1 = new ModelRenderer(this, 40, 22);
        this.BLegFL1.addBox(-2.5F, 0F, -2.5F, 5, 8, 5);
        this.BLegFL1.setRotationPoint(5F, -1F, 6F);
        setRotation(this.BLegFL1, 0.2617994F, 0F, -0.2617994F);

        this.BLegFL2 = new ModelRenderer(this, 46, 35);
        this.BLegFL2.addBox(0F, 5F, 3F, 4, 6, 4);
        this.BLegFL2.setRotationPoint(5F, -1F, 6F);
        setRotation(this.BLegFL2, -0.5576792F, 0F, 0F);

        this.BLegFL3 = new ModelRenderer(this, 46, 45);
        this.BLegFL3.addBox(0.1F, -7F, -14F, 4, 2, 5);
        this.BLegFL3.setRotationPoint(5F, -1F, 6F);
        setRotation(this.BLegFL3, 2.007645F, 0F, 0F);

        this.BLegFR1 = new ModelRenderer(this, 4, 22);
        this.BLegFR1.addBox(-2.5F, 0F, -2.5F, 5, 8, 5);
        this.BLegFR1.setRotationPoint(-5F, -1F, 6F);
        setRotation(this.BLegFR1, 0.2617994F, 0F, 0.2617994F);

        this.BLegFR2 = new ModelRenderer(this, 2, 35);
        this.BLegFR2.addBox(-4F, 5F, 3F, 4, 6, 4);
        this.BLegFR2.setRotationPoint(-5F, -1F, 6F);
        setRotation(this.BLegFR2, -0.5576792F, 0F, 0F);

        this.BLegFR3 = new ModelRenderer(this, 0, 45);
        this.BLegFR3.addBox(-4.1F, -7F, -14F, 4, 2, 5);
        this.BLegFR3.setRotationPoint(-5F, -1F, 6F);
        setRotation(this.BLegFR3, 2.007129F, 0F, 0F);

        this.BLegRL1 = new ModelRenderer(this, 34, 83);
        this.BLegRL1.addBox(-1.5F, 0F, -2.5F, 4, 8, 6);
        this.BLegRL1.setRotationPoint(3F, 11F, 9F);
        setRotation(this.BLegRL1, -0.5235988F, -0.2617994F, 0F);

        this.BLegRL2 = new ModelRenderer(this, 41, 97);
        this.BLegRL2.addBox(-1.3F, 6F, -3F, 4, 6, 4);
        this.BLegRL2.setRotationPoint(3F, 11F, 9F);
        setRotation(this.BLegRL2, 0F, -0.2617994F, 0F);

        this.BLegRL3 = new ModelRenderer(this, 44, 107);
        this.BLegRL3.addBox(-1.2F, 11F, -4F, 4, 2, 5);
        this.BLegRL3.setRotationPoint(3F, 11F, 9F);
        setRotation(this.BLegRL3, 0F, -0.2617994F, 0F);

        this.BLegRR1 = new ModelRenderer(this, 10, 83);
        this.BLegRR1.addBox(-2.5F, 0F, -2.5F, 4, 8, 6);
        this.BLegRR1.setRotationPoint(-3F, 11F, 9F);
        setRotation(this.BLegRR1, -0.1745329F, 0.2617994F, 0F);

        this.BLegRR2 = new ModelRenderer(this, 7, 97);
        this.BLegRR2.addBox(-2.4F, 6F, -1F, 4, 6, 4);
        this.BLegRR2.setRotationPoint(-3F, 11F, 9F);
        setRotation(this.BLegRR2, 0F, 0.2617994F, 0F);

        this.BLegRR3 = new ModelRenderer(this, 2, 107);
        this.BLegRR3.addBox(-2.5F, 11F, -2F, 4, 2, 5);
        this.BLegRR3.setRotationPoint(-3F, 11F, 9F);
        setRotation(this.BLegRR3, 0F, 0.2617994F, 0F);

        //---Sitting
        this.CHead = new ModelRenderer(this, 19, 0);
        this.CHead.addBox(-4F, 0F, -4F, 8, 8, 5);
        this.CHead.setRotationPoint(0F, 3F, -3.5F);
        setRotation(this.CHead, 0.1502636F, 0F, 0F);

        this.CSnout = new ModelRenderer(this, 23, 13);
        this.CSnout.addBox(-2F, 3F, -8.5F, 4, 3, 5);
        this.CSnout.setRotationPoint(0F, 3F, -3.5F);
        setRotation(this.CSnout, 0.1502636F, 0F, 0F);

        this.CMouth = new ModelRenderer(this, 24, 21);
        this.CMouth.addBox(-1.5F, 6F, -7F, 3, 2, 5);
        this.CMouth.setRotationPoint(0F, 3F, -3.5F);
        setRotation(this.CMouth, -0.0068161F, 0F, 0F);

        this.CMouthOpen = new ModelRenderer(this, 24, 21);
        this.CMouthOpen.addBox(-1.5F, 5.5F, -9F, 3, 2, 5);
        this.CMouthOpen.setRotationPoint(0F, 3F, -3.5F);
        setRotation(this.CMouthOpen, 0.3665191F, 0F, 0F);

        this.CLEar = new ModelRenderer(this, 40, 0);
        this.CLEar.addBox(2F, -2F, -2F, 3, 3, 1);
        this.CLEar.setRotationPoint(0F, 3F, -3.5F);
        setRotation(this.CLEar, 0.1502636F, -0.3490659F, 0.1396263F);

        this.CREar = new ModelRenderer(this, 16, 0);
        this.CREar.addBox(-5F, -2F, -2F, 3, 3, 1);
        this.CREar.setRotationPoint(0F, 3F, -3.5F);
        setRotation(this.CREar, 0.1502636F, 0.3490659F, -0.1396263F);

        this.CNeck = new ModelRenderer(this, 18, 28);
        this.CNeck.addBox(-3.5F, 0F, -7F, 7, 7, 7);
        this.CNeck.setRotationPoint(0F, 5.8F, 3.4F);
        setRotation(this.CNeck, -0.3316126F, 0F, 0F);

        this.CTorso = new ModelRenderer(this, 12, 42);
        this.CTorso.addBox(-5F, 0F, 0F, 10, 10, 10);
        this.CTorso.setRotationPoint(0F, 5.8F, 3.4F);
        setRotation(this.CTorso, -0.9712912F, 0F, 0F);

        this.CAbdomen = new ModelRenderer(this, 13, 62);
        this.CAbdomen.addBox(-4.5F, 0F, 0F, 9, 11, 10);
        this.CAbdomen.setRotationPoint(0F, 14F, 9F);
        setRotation(this.CAbdomen, -1.570796F, 0F, 0F);

        this.CTail = new ModelRenderer(this, 26, 83);
        this.CTail.addBox(-1.5F, 0F, 0F, 3, 3, 3);
        this.CTail.setRotationPoint(0F, 21.46667F, 8F);
        setRotation(this.CTail, 0.4363323F, 0F, 0F);

        this.CLegFL1 = new ModelRenderer(this, 40, 22);
        this.CLegFL1.addBox(-2.5F, 0F, -1.5F, 5, 8, 5);
        this.CLegFL1.setRotationPoint(4F, 10F, 0F);
        setRotation(this.CLegFL1, -0.2617994F, 0F, 0F);

        this.CLegFL2 = new ModelRenderer(this, 46, 35);
        this.CLegFL2.addBox(-2F, 0F, -1.2F, 4, 6, 4);
        this.CLegFL2.setRotationPoint(4F, 17F, -2F);
        setRotation(this.CLegFL2, -0.3490659F, 0F, 0.2617994F);

        this.CLegFL3 = new ModelRenderer(this, 46, 45);
        this.CLegFL3.addBox(-2F, 0F, -3F, 4, 2, 5);
        this.CLegFL3.setRotationPoint(2.5F, 22F, -4F);
        setRotation(this.CLegFL3, 0F, 0.1745329F, 0F);

        this.CLegFR1 = new ModelRenderer(this, 4, 22);
        this.CLegFR1.addBox(-2.5F, 0F, -1.5F, 5, 8, 5);
        this.CLegFR1.setRotationPoint(-4F, 10F, 0F);
        setRotation(this.CLegFR1, -0.2617994F, 0F, 0F);

        this.CLegFR2 = new ModelRenderer(this, 2, 35);
        this.CLegFR2.addBox(-2F, 0F, -1.2F, 4, 6, 4);
        this.CLegFR2.setRotationPoint(-4F, 17F, -2F);
        setRotation(this.CLegFR2, -0.3490659F, 0F, -0.2617994F);

        this.CLegFR3 = new ModelRenderer(this, 0, 45);
        this.CLegFR3.addBox(-2F, 0F, -3F, 4, 2, 5);
        this.CLegFR3.setRotationPoint(-2.5F, 22F, -4F);
        setRotation(this.CLegFR3, 0F, -0.1745329F, 0F);

        this.CLegRL1 = new ModelRenderer(this, 34, 83);
        this.CLegRL1.addBox(-1.5F, 0F, -2.5F, 4, 8, 6);
        this.CLegRL1.setRotationPoint(3F, 21F, 5F);
        setRotation(this.CLegRL1, -1.396263F, -0.3490659F, 0.3490659F);

        this.CLegRL2 = new ModelRenderer(this, 41, 97);
        this.CLegRL2.addBox(-2F, 0F, -2F, 4, 6, 4);
        this.CLegRL2.setRotationPoint(5.2F, 22.5F, -1F);
        setRotation(this.CLegRL2, -1.570796F, 0F, 0.3490659F);

        this.CLegRL3 = new ModelRenderer(this, 44, 107);
        this.CLegRL3.addBox(-2F, 0F, -3F, 4, 2, 5);
        this.CLegRL3.setRotationPoint(5.5F, 22F, -6F);
        setRotation(this.CLegRL3, -1.375609F, 0F, 0.3490659F);

        this.CLegRR1 = new ModelRenderer(this, 10, 83);
        this.CLegRR1.addBox(-2.5F, 0F, -2.5F, 4, 8, 6);
        this.CLegRR1.setRotationPoint(-3F, 21F, 5F);
        setRotation(this.CLegRR1, -1.396263F, 0.3490659F, -0.3490659F);

        this.CLegRR2 = new ModelRenderer(this, 7, 97);
        this.CLegRR2.addBox(-2F, 0F, -2F, 4, 6, 4);
        this.CLegRR2.setRotationPoint(-5.2F, 22.5F, -1F);
        setRotation(this.CLegRR2, -1.570796F, 0F, -0.3490659F);

        this.CLegRR3 = new ModelRenderer(this, 2, 107);
        this.CLegRR3.addBox(-2F, 0F, -3F, 4, 2, 5);
        this.CLegRR3.setRotationPoint(-5.5F, 22F, -6F);
        setRotation(this.CLegRR3, -1.375609F, 0F, -0.3490659F);
        
        Saddle = new ModelRenderer(this, 36, 114);
		Saddle.addBox(-4F, -0.5F, -3F, 8, 2, 6, 0F);
		Saddle.setRotationPoint(0F, 4F, -2F);
		
		SaddleBack = new ModelRenderer(this, 20, 108);
		SaddleBack.addBox(-4F, -0.2F, 2.9F, 8, 2, 4, 0F);
		SaddleBack.setRotationPoint(0F, 4F, -2F);
		SaddleBack.rotateAngleX = 0.10088F;
		
		SaddleFront = new ModelRenderer(this, 36, 122);
		SaddleFront.addBox(-2.5F, -1F, -3F, 5, 2, 3, 0F);
		SaddleFront.setRotationPoint(0F, 4F, -2F);
		SaddleFront.rotateAngleX = -0.1850049F;
		
		Bag = new ModelRenderer(this, 0, 114);
		Bag.addBox(-5F, -3F, -2.5F, 10, 2, 5, 0F);
		Bag.setRotationPoint(0F, 7F, 7F);
		Bag.rotateAngleX = -0.4363323F;
		
		BagSitted = new ModelRenderer(this, 0, 114);
		BagSitted.addBox(-5F, -3F, -2.5F, 10, 2, 5, 0F);
		BagSitted.setRotationPoint(0F, 17F, 8F);
		BagSitted.rotateAngleX = -1.570796F;
		
		SaddleSitted = new ModelRenderer(this, 36, 114);
		SaddleSitted.addBox(-4F, -0.5F, -3F, 8, 2, 6, 0F);
		SaddleSitted.setRotationPoint(0F, 7.5F, 6.5F);
		SaddleSitted.rotateAngleX = -0.9686577F;
		
		SaddleBackSitted = new ModelRenderer(this, 20, 108);
		SaddleBackSitted.addBox(-4F, -0.3F, 2.9F, 8, 2, 4, 0F);
		SaddleBackSitted.setRotationPoint(0F, 7.5F, 6.5F);
		SaddleBackSitted.rotateAngleX = -0.9162979F;

		SaddleFrontSitted = new ModelRenderer(this, 36, 122);
		SaddleFrontSitted.addBox(-2.5F, -1F, -3F, 5, 2, 3, 0F);
		SaddleFrontSitted.setRotationPoint(0F, 7.5F, 6.5F);
		SaddleFrontSitted.rotateAngleX = -1.151917F;
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        MoCEntityBear entitybear = (MoCEntityBear) entity;
        this.bearstate = entitybear.getBearState();
        boolean openMouth = (entitybear.mouthCounter != 0);
        this.attackSwing = entitybear.getAttackSwing();
        setRotationAngles(f, f1, f2, f3, f4, f5);
        boolean chested = entitybear.getIsChested();
        boolean saddled = entitybear.getIsRideable();
        
        if (bearstate == 0) { //in fours
            if (openMouth) {
                this.MouthOpen.render(f5);
            } else {
                this.Mouth.render(f5);
            }
            if (saddled)
            {
            	Saddle.render(f5);
            	SaddleBack.render(f5);
            	SaddleFront.render(f5);
            }
            if (chested)
            {
            	Bag.render(f5);
            }
            this.LegFR1.render(f5);
            this.Neck.render(f5);
            this.LEar.render(f5);
            this.Snout.render(f5);
            this.Head.render(f5);
            this.REar.render(f5);
            this.Abdomen.render(f5);
            this.Torso.render(f5);
            this.LegRR3.render(f5);
            this.LegRR1.render(f5);
            this.LegRR2.render(f5);
            this.LegFR2.render(f5);
            this.LegFR3.render(f5);
            this.LegFL1.render(f5);
            this.LegFL3.render(f5);
            this.LegFL2.render(f5);
            this.LegRL1.render(f5);
            this.LegRL2.render(f5);
            this.LegRL3.render(f5);
            this.Tail.render(f5);
        } else if (bearstate == 1) {
            this.BHead.render(f5);
            this.BSnout.render(f5);
            if (openMouth) {
                this.BMouthOpen.render(f5);
            } else {
                this.BMouth.render(f5);
            }

            this.BNeck.render(f5);
            this.BLEar.render(f5);
            this.BREar.render(f5);
            this.BTorso.render(f5);
            this.BAbdomen.render(f5);
            this.BTail.render(f5);
            this.BLegFL1.render(f5);
            this.BLegFL2.render(f5);
            this.BLegFL3.render(f5);
            this.BLegFR1.render(f5);
            this.BLegFR2.render(f5);
            this.BLegFR3.render(f5);
            this.BLegRL1.render(f5);
            this.BLegRL2.render(f5);
            this.BLegRL3.render(f5);
            this.BLegRR1.render(f5);
            this.BLegRR2.render(f5);
            this.BLegRR3.render(f5);
        } else if (bearstate == 2) { //sited
            if (openMouth) {
                this.CMouthOpen.render(f5);
            } else {
                this.CMouth.render(f5);
            }
            if (saddled)
            {
            	SaddleSitted.render(f5);
            	SaddleBackSitted.render(f5);
            	SaddleFrontSitted.render(f5);
            }
            if (chested)
            {
            	BagSitted.render(f5);
            }
            this.CHead.render(f5);
            this.CSnout.render(f5);
            this.CLEar.render(f5);
            this.CREar.render(f5);
            this.CNeck.render(f5);
            this.CTorso.render(f5);
            this.CAbdomen.render(f5);
            this.CTail.render(f5);
            this.CLegFL1.render(f5);
            this.CLegFL2.render(f5);
            this.CLegFL3.render(f5);
            this.CLegFR1.render(f5);
            this.CLegFR2.render(f5);
            this.CLegFR3.render(f5);
            this.CLegRL1.render(f5);
            this.CLegRL2.render(f5);
            this.CLegRL3.render(f5);
            this.CLegRR1.render(f5);
            this.CLegRR2.render(f5);
            this.CLegRR3.render(f5);
        }

    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
        float LLegRotX = MathHelper.cos(f * 0.6662F) * 0.8F * f1;
        float RLegRotX = MathHelper.cos((f * 0.6662F) + 3.141593F) * 0.8F * f1;
        float XAngle = (f4 / 57.29578F);
        float YAngle = f3 / 57.29578F;

        if (this.bearstate == 0) {
            this.Head.rotateAngleX = 0.1502636F + XAngle;
            this.Head.rotateAngleY = YAngle;

            this.Snout.rotateAngleX = 0.1502636F + XAngle;
            this.Snout.rotateAngleY = YAngle;

            this.Mouth.rotateAngleX = -0.0068161F + XAngle;
            this.Mouth.rotateAngleY = YAngle;

            this.MouthOpen.rotateAngleX = 0.534236F + XAngle;
            this.MouthOpen.rotateAngleY = YAngle;

            this.LEar.rotateAngleX = 0.1502636F + XAngle;
            this.LEar.rotateAngleY = -0.3490659F + YAngle;

            this.REar.rotateAngleX = 0.1502636F + XAngle;
            this.REar.rotateAngleY = 0.3490659F + YAngle;

            this.LegFL1.rotateAngleX = 0.2617994F + LLegRotX;
            this.LegFL2.rotateAngleX = LLegRotX;
            this.LegFL3.rotateAngleX = LLegRotX;

            this.LegRR1.rotateAngleX = -0.1745329F + LLegRotX;
            this.LegRR2.rotateAngleX = LLegRotX;
            this.LegRR3.rotateAngleX = LLegRotX;

            this.LegFR1.rotateAngleX = 0.2617994F + RLegRotX;
            this.LegFR2.rotateAngleX = RLegRotX;
            this.LegFR3.rotateAngleX = RLegRotX;

            this.LegRL1.rotateAngleX = -0.1745329F + RLegRotX;
            this.LegRL2.rotateAngleX = RLegRotX;
            this.LegRL3.rotateAngleX = RLegRotX;
        } else if (this.bearstate == 1) {

            this.BHead.rotateAngleX = -0.0242694F - XAngle;
            this.BHead.rotateAngleY = YAngle;

            this.BSnout.rotateAngleX = -0.0242694F - XAngle;
            this.BSnout.rotateAngleY = YAngle;

            this.BMouth.rotateAngleX = -0.08726F - XAngle;
            this.BMouth.rotateAngleY = YAngle;

            this.BMouthOpen.rotateAngleX = 0.5235988F - XAngle;
            this.BMouthOpen.rotateAngleY = YAngle;

            this.BLEar.rotateAngleX = -0.0242694F - XAngle;
            this.BLEar.rotateAngleY = -0.3490659F + YAngle;

            this.BREar.rotateAngleX = -0.0242694F - XAngle;
            this.BREar.rotateAngleY = 0.3490659F + YAngle;

            /**
             * Arm breathing movement
             */
            float breathing = MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
            this.BLegFR1.rotateAngleZ = 0.2617994F + breathing;
            this.BLegFR2.rotateAngleZ = breathing;
            this.BLegFR3.rotateAngleZ = breathing;
            this.BLegFL1.rotateAngleZ = -0.2617994F - breathing;
            this.BLegFL2.rotateAngleZ = -breathing;
            this.BLegFL3.rotateAngleZ = -breathing;

            this.BLegFL1.rotateAngleX = 0.2617994F + attackSwing;
            this.BLegFL2.rotateAngleX = -0.5576792F + attackSwing;
            this.BLegFL3.rotateAngleX = 2.007645F + attackSwing;

            this.BLegFR1.rotateAngleX = 0.2617994F + attackSwing;
            this.BLegFR2.rotateAngleX = -0.5576792F + attackSwing;
            this.BLegFR3.rotateAngleX = 2.007645F + attackSwing;

            this.BLegRR1.rotateAngleX = -0.1745329F + LLegRotX;
            this.BLegRR2.rotateAngleX = LLegRotX;
            this.BLegRR3.rotateAngleX = LLegRotX;

            this.BLegRL1.rotateAngleX = -0.5235988F + RLegRotX;
            this.BLegRL2.rotateAngleX = RLegRotX;
            this.BLegRL3.rotateAngleX = RLegRotX;
        } else if (this.bearstate == 2) {
            this.CHead.rotateAngleX = 0.1502636F + XAngle;
            this.CHead.rotateAngleY = YAngle;

            this.CSnout.rotateAngleX = 0.1502636F + XAngle;
            this.CSnout.rotateAngleY = YAngle;

            this.CMouth.rotateAngleX = -0.0068161F + XAngle;
            this.CMouth.rotateAngleY = YAngle;

            this.CMouthOpen.rotateAngleX = 0.3665191F + XAngle;
            this.CMouthOpen.rotateAngleY = YAngle;

            this.CLEar.rotateAngleX = 0.1502636F + XAngle;
            this.CLEar.rotateAngleY = -0.3490659F + YAngle;

            this.CREar.rotateAngleX = 0.1502636F + XAngle;
            this.CREar.rotateAngleY = 0.3490659F + YAngle;
        }

        this.Tail.rotateAngleZ = LLegRotX * 0.2F;
    }
}
