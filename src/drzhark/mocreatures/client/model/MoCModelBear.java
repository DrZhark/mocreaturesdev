package drzhark.mocreatures.client.model;



import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.entity.passive.MoCEntityBear;

@SideOnly(Side.CLIENT)
public class MoCModelBear extends ModelBase {
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

    public MoCModelBear()
    {
        textureWidth = 64;
        textureHeight = 128;

        Head = new ModelRenderer(this, 19, 0);
        Head.addBox(-4F, 0F, -4F, 8, 8, 5);
        Head.setRotationPoint(0F, 6F, -10F);
        setRotation(Head, 0.1502636F, 0F, 0F);

        Mouth = new ModelRenderer(this, 24, 21);
        Mouth.addBox(-1.5F, 6F, -6.8F, 3, 2, 5);
        Mouth.setRotationPoint(0F, 6F, -10F);
        setRotation(Mouth, -0.0068161F, 0F, 0F);

        MouthOpen = new ModelRenderer(this, 24, 21);
        MouthOpen.addBox(-1.5F, 4F, -9.5F, 3, 2, 5);
        MouthOpen.setRotationPoint(0F, 6F, -10F);
        setRotation(MouthOpen, 0.534236F, 0F, 0F);

        LEar = new ModelRenderer(this, 40, 0);
        LEar.addBox(2F, -2F, -2F, 3, 3, 1);
        LEar.setRotationPoint(0F, 6F, -10F);
        setRotation(LEar, 0.1502636F, -0.3490659F, 0.1396263F);

        REar = new ModelRenderer(this, 16, 0);
        REar.addBox(-5F, -2F, -2F, 3, 3, 1);
        REar.setRotationPoint(0F, 6F, -10F);
        setRotation(REar, 0.1502636F, 0.3490659F, -0.1396263F);

        Snout = new ModelRenderer(this, 23, 13);
        Snout.addBox(-2F, 3F, -8F, 4, 3, 5);
        Snout.setRotationPoint(0F, 6F, -10F);
        setRotation(Snout, 0.1502636F, 0F, 0F);

        Neck = new ModelRenderer(this, 18, 28);
        Neck.addBox(-3.5F, 0F, -7F, 7, 7, 7);
        Neck.setRotationPoint(0F, 5F, -5F);
        setRotation(Neck, 0.2617994F, 0F, 0F);

        Abdomen = new ModelRenderer(this, 13, 62);
        Abdomen.addBox(-4.5F, 0F, 0F, 9, 11, 10);
        Abdomen.setRotationPoint(0F, 5F, 5F);
        setRotation(Abdomen, -0.4363323F, 0F, 0F);

        Torso = new ModelRenderer(this, 12, 42);
        Torso.addBox(-5F, 0F, 0F, 10, 10, 10);
        Torso.setRotationPoint(0F, 5F, -5F);

        Tail = new ModelRenderer(this, 26, 83);
        Tail.addBox(-1.5F, 0F, 0F, 3, 3, 3);
        Tail.setRotationPoint(0F, 8.466666F, 12F);
        setRotation(Tail, 0.4363323F, 0F, 0F);

        LegFL1 = new ModelRenderer(this, 40, 22);
        LegFL1.addBox(-2.5F, 0F, -2.5F, 5, 8, 5);
        LegFL1.setRotationPoint(4F, 10F, -4F);
        setRotation(LegFL1, 0.2617994F, 0F, 0F);

        LegFL2 = new ModelRenderer(this, 46, 35);
        LegFL2.addBox(-2F, 7F, 0F, 4, 6, 4);
        LegFL2.setRotationPoint(4F, 10F, -4F);

        LegFL3 = new ModelRenderer(this, 46, 45);
        LegFL3.addBox(-2F, 12F, -1F, 4, 2, 5);
        LegFL3.setRotationPoint(4F, 10F, -4F);

        LegFR1 = new ModelRenderer(this, 4, 22);
        LegFR1.addBox(-2.5F, 0F, -2.5F, 5, 8, 5);
        LegFR1.setRotationPoint(-4F, 10F, -4F);
        setRotation(LegFR1, 0.2617994F, 0F, 0F);

        LegFR2 = new ModelRenderer(this, 2, 35);
        LegFR2.addBox(-2F, 7F, 0F, 4, 6, 4);
        LegFR2.setRotationPoint(-4F, 10F, -4F);

        LegFR3 = new ModelRenderer(this, 0, 45);
        LegFR3.addBox(-2F, 12F, -1F, 4, 2, 5);
        LegFR3.setRotationPoint(-4F, 10F, -4F);

        LegRL1 = new ModelRenderer(this, 34, 83);
        LegRL1.addBox(-1.5F, 0F, -2.5F, 4, 8, 6);
        LegRL1.setRotationPoint(3.5F, 11F, 9F);
        setRotation(LegRL1, -0.1745329F, 0F, 0F);

        LegRL2 = new ModelRenderer(this, 41, 97);
        LegRL2.addBox(-2F, 6F, -1F, 4, 6, 4);
        LegRL2.setRotationPoint(3.5F, 11F, 9F);

        LegRL3 = new ModelRenderer(this, 44, 107);
        LegRL3.addBox(-2F, 11F, -2F, 4, 2, 5);
        LegRL3.setRotationPoint(3.5F, 11F, 9F);

        LegRR1 = new ModelRenderer(this, 10, 83);
        LegRR1.addBox(-2.5F, 0F, -2.5F, 4, 8, 6);
        LegRR1.setRotationPoint(-3.5F, 11F, 9F);
        setRotation(LegRR1, -0.1745329F, 0F, 0F);

        LegRR2 = new ModelRenderer(this, 7, 97);
        LegRR2.addBox(-2F, 6F, -1F, 4, 6, 4);
        LegRR2.setRotationPoint(-3.5F, 11F, 9F);

        LegRR3 = new ModelRenderer(this, 2, 107);
        LegRR3.addBox(-2F, 11F, -2F, 4, 2, 5);
        LegRR3.setRotationPoint(-3.5F, 11F, 9F);

        //---standing

        BHead = new ModelRenderer(this, 19, 0);
        BHead.addBox(-4F, 0F, -4F, 8, 8, 5);
        BHead.setRotationPoint(0F, -12F, 5F);
        setRotation(BHead, -0.0242694F, 0F, 0F);

        BSnout = new ModelRenderer(this, 23, 13);
        BSnout.addBox(-2F, 2.5F, -8.5F, 4, 3, 5);
        BSnout.setRotationPoint(0F, -12F, 5F);
        setRotation(BSnout, -0.0242694F, 0F, 0F);

        BMouth = new ModelRenderer(this, 24, 21);
        BMouth.addBox(-1.5F, 5.5F, -8.0F, 3, 2, 5);
        BMouth.setRotationPoint(0F, -12F, 5F);
        setRotation(BMouth, -0.08726F, 0F, 0F);

        BMouthOpen = new ModelRenderer(this, 24, 21);
        BMouthOpen.addBox(-1.5F, 3.5F, -11F, 3, 2, 5);
        BMouthOpen.setRotationPoint(0F, -12F, 5F);
        setRotation(BMouthOpen, 0.5235988F, 0F, 0F);

        BNeck = new ModelRenderer(this, 18, 28);
        BNeck.addBox(-3.5F, 0F, -7F, 7, 6, 7);
        BNeck.setRotationPoint(0F, -3F, 11F);
        setRotation(BNeck, -1.336881F, 0F, 0F);

        BLEar = new ModelRenderer(this, 40, 0);
        BLEar.addBox(2F, -2F, -2F, 3, 3, 1);
        BLEar.setRotationPoint(0F, -12F, 5F);
        setRotation(BLEar, -0.0242694F, -0.3490659F, 0.1396263F);

        BREar = new ModelRenderer(this, 16, 0);
        BREar.addBox(-5F, -2F, -2F, 3, 3, 1);
        BREar.setRotationPoint(0F, -12F, 5F);
        setRotation(BREar, -0.0242694F, 0.3490659F, -0.1396263F);

        BTorso = new ModelRenderer(this, 12, 42);
        BTorso.addBox(-5F, 0F, 0F, 10, 10, 10);
        BTorso.setRotationPoint(0F, -3.5F, 12.3F);
        setRotation(BTorso, -1.396263F, 0F, 0F);

        BAbdomen = new ModelRenderer(this, 13, 62);
        BAbdomen.addBox(-4.5F, 0F, 0F, 9, 11, 10);
        BAbdomen.setRotationPoint(0F, 6F, 14F);
        setRotation(BAbdomen, -1.570796F, 0F, 0F);

        BTail = new ModelRenderer(this, 26, 83);
        BTail.addBox(-1.5F, 0F, 0F, 3, 3, 3);
        BTail.setRotationPoint(0F, 12.46667F, 12.6F);
        setRotation(BTail, 0.3619751F, 0F, 0F);

        BLegFL1 = new ModelRenderer(this, 40, 22);
        BLegFL1.addBox(-2.5F, 0F, -2.5F, 5, 8, 5);
        BLegFL1.setRotationPoint(5F, -1F, 6F);
        setRotation(BLegFL1, 0.2617994F, 0F, -0.2617994F);

        BLegFL2 = new ModelRenderer(this, 46, 35);
        BLegFL2.addBox(0F, 5F, 3F, 4, 6, 4);
        BLegFL2.setRotationPoint(5F, -1F, 6F);
        setRotation(BLegFL2, -0.5576792F, 0F, 0F);

        BLegFL3 = new ModelRenderer(this, 46, 45);
        BLegFL3.addBox(0.1F, -7F, -14F, 4, 2, 5);
        BLegFL3.setRotationPoint(5F, -1F, 6F);
        setRotation(BLegFL3, 2.007645F, 0F, 0F);

        BLegFR1 = new ModelRenderer(this, 4, 22);
        BLegFR1.addBox(-2.5F, 0F, -2.5F, 5, 8, 5);
        BLegFR1.setRotationPoint(-5F, -1F, 6F);
        setRotation(BLegFR1, 0.2617994F, 0F, 0.2617994F);

        BLegFR2 = new ModelRenderer(this, 2, 35);
        BLegFR2.addBox(-4F, 5F, 3F, 4, 6, 4);
        BLegFR2.setRotationPoint(-5F, -1F, 6F);
        setRotation(BLegFR2, -0.5576792F, 0F, 0F);

        BLegFR3 = new ModelRenderer(this, 0, 45);
        BLegFR3.addBox(-4.1F, -7F, -14F, 4, 2, 5);
        BLegFR3.setRotationPoint(-5F, -1F, 6F);
        setRotation(BLegFR3, 2.007129F, 0F, 0F);

        BLegRL1 = new ModelRenderer(this, 34, 83);
        BLegRL1.addBox(-1.5F, 0F, -2.5F, 4, 8, 6);
        BLegRL1.setRotationPoint(3F, 11F, 9F);
        setRotation(BLegRL1, -0.5235988F, -0.2617994F, 0F);

        BLegRL2 = new ModelRenderer(this, 41, 97);
        BLegRL2.addBox(-1.3F, 6F, -3F, 4, 6, 4);
        BLegRL2.setRotationPoint(3F, 11F, 9F);
        setRotation(BLegRL2, 0F, -0.2617994F, 0F);

        BLegRL3 = new ModelRenderer(this, 44, 107);
        BLegRL3.addBox(-1.2F, 11F, -4F, 4, 2, 5);
        BLegRL3.setRotationPoint(3F, 11F, 9F);
        setRotation(BLegRL3, 0F, -0.2617994F, 0F);

        BLegRR1 = new ModelRenderer(this, 10, 83);
        BLegRR1.addBox(-2.5F, 0F, -2.5F, 4, 8, 6);
        BLegRR1.setRotationPoint(-3F, 11F, 9F);
        setRotation(BLegRR1, -0.1745329F, 0.2617994F, 0F);

        BLegRR2 = new ModelRenderer(this, 7, 97);
        BLegRR2.addBox(-2.4F, 6F, -1F, 4, 6, 4);
        BLegRR2.setRotationPoint(-3F, 11F, 9F);
        setRotation(BLegRR2, 0F, 0.2617994F, 0F);

        BLegRR3 = new ModelRenderer(this, 2, 107);
        BLegRR3.addBox(-2.5F, 11F, -2F, 4, 2, 5);
        BLegRR3.setRotationPoint(-3F, 11F, 9F);
        setRotation(BLegRR3, 0F, 0.2617994F, 0F);

        //---Sitting
        CHead = new ModelRenderer(this, 19, 0);
        CHead.addBox(-4F, 0F, -4F, 8, 8, 5);
        CHead.setRotationPoint(0F, 3F, -3.5F);
        setRotation(CHead, 0.1502636F, 0F, 0F);

        CSnout = new ModelRenderer(this, 23, 13);
        CSnout.addBox(-2F, 3F, -8.5F, 4, 3, 5);
        CSnout.setRotationPoint(0F, 3F, -3.5F);
        setRotation(CSnout, 0.1502636F, 0F, 0F);

        CMouth = new ModelRenderer(this, 24, 21);
        CMouth.addBox(-1.5F, 6F, -7F, 3, 2, 5);
        CMouth.setRotationPoint(0F, 3F, -3.5F);
        setRotation(CMouth, -0.0068161F, 0F, 0F);

        CMouthOpen = new ModelRenderer(this, 24, 21);
        CMouthOpen.addBox(-1.5F, 5.5F, -9F, 3, 2, 5);
        CMouthOpen.setRotationPoint(0F, 3F, -3.5F);
        setRotation(CMouthOpen, 0.3665191F, 0F, 0F);

        CLEar = new ModelRenderer(this, 40, 0);
        CLEar.addBox(2F, -2F, -2F, 3, 3, 1);
        CLEar.setRotationPoint(0F, 3F, -3.5F);
        setRotation(CLEar, 0.1502636F, -0.3490659F, 0.1396263F);

        CREar = new ModelRenderer(this, 16, 0);
        CREar.addBox(-5F, -2F, -2F, 3, 3, 1);
        CREar.setRotationPoint(0F, 3F, -3.5F);
        setRotation(CREar, 0.1502636F, 0.3490659F, -0.1396263F);

        CNeck = new ModelRenderer(this, 18, 28);
        CNeck.addBox(-3.5F, 0F, -7F, 7, 7, 7);
        CNeck.setRotationPoint(0F, 5.8F, 3.4F);
        setRotation(CNeck, -0.3316126F, 0F, 0F);

        CTorso = new ModelRenderer(this, 12, 42);
        CTorso.addBox(-5F, 0F, 0F, 10, 10, 10);
        CTorso.setRotationPoint(0F, 5.8F, 3.4F);
        setRotation(CTorso, -0.9712912F, 0F, 0F);

        CAbdomen = new ModelRenderer(this, 13, 62);
        CAbdomen.addBox(-4.5F, 0F, 0F, 9, 11, 10);
        CAbdomen.setRotationPoint(0F, 14F, 9F);
        setRotation(CAbdomen, -1.570796F, 0F, 0F);

        CTail = new ModelRenderer(this, 26, 83);
        CTail.addBox(-1.5F, 0F, 0F, 3, 3, 3);
        CTail.setRotationPoint(0F, 21.46667F, 8F);
        setRotation(CTail, 0.4363323F, 0F, 0F);

        CLegFL1 = new ModelRenderer(this, 40, 22);
        CLegFL1.addBox(-2.5F, 0F, -1.5F, 5, 8, 5);
        CLegFL1.setRotationPoint(4F, 10F, 0F);
        setRotation(CLegFL1, -0.2617994F, 0F, 0F);

        CLegFL2 = new ModelRenderer(this, 46, 35);
        CLegFL2.addBox(-2F, 0F, -1.2F, 4, 6, 4);
        CLegFL2.setRotationPoint(4F, 17F, -2F);
        setRotation(CLegFL2, -0.3490659F, 0F, 0.2617994F);

        CLegFL3 = new ModelRenderer(this, 46, 45);
        CLegFL3.addBox(-2F, 0F, -3F, 4, 2, 5);
        CLegFL3.setRotationPoint(2.5F, 22F, -4F);
        setRotation(CLegFL3, 0F, 0.1745329F, 0F);

        CLegFR1 = new ModelRenderer(this, 4, 22);
        CLegFR1.addBox(-2.5F, 0F, -1.5F, 5, 8, 5);
        CLegFR1.setRotationPoint(-4F, 10F, 0F);
        setRotation(CLegFR1, -0.2617994F, 0F, 0F);

        CLegFR2 = new ModelRenderer(this, 2, 35);
        CLegFR2.addBox(-2F, 0F, -1.2F, 4, 6, 4);
        CLegFR2.setRotationPoint(-4F, 17F, -2F);
        setRotation(CLegFR2, -0.3490659F, 0F, -0.2617994F);

        CLegFR3 = new ModelRenderer(this, 0, 45);
        CLegFR3.addBox(-2F, 0F, -3F, 4, 2, 5);
        CLegFR3.setRotationPoint(-2.5F, 22F, -4F);
        setRotation(CLegFR3, 0F, -0.1745329F, 0F);

        CLegRL1 = new ModelRenderer(this, 34, 83);
        CLegRL1.addBox(-1.5F, 0F, -2.5F, 4, 8, 6);
        CLegRL1.setRotationPoint(3F, 21F, 5F);
        setRotation(CLegRL1, -1.396263F, -0.3490659F, 0.3490659F);

        CLegRL2 = new ModelRenderer(this, 41, 97);
        CLegRL2.addBox(-2F, 0F, -2F, 4, 6, 4);
        CLegRL2.setRotationPoint(5.2F, 22.5F, -1F);
        setRotation(CLegRL2, -1.570796F, 0F, 0.3490659F);

        CLegRL3 = new ModelRenderer(this, 44, 107);
        CLegRL3.addBox(-2F, 0F, -3F, 4, 2, 5);
        CLegRL3.setRotationPoint(5.5F, 22F, -6F);
        setRotation(CLegRL3, -1.375609F, 0F, 0.3490659F);

        CLegRR1 = new ModelRenderer(this, 10, 83);
        CLegRR1.addBox(-2.5F, 0F, -2.5F, 4, 8, 6);
        CLegRR1.setRotationPoint(-3F, 21F, 5F);
        setRotation(CLegRR1, -1.396263F, 0.3490659F, -0.3490659F);

        CLegRR2 = new ModelRenderer(this, 7, 97);
        CLegRR2.addBox(-2F, 0F, -2F, 4, 6, 4);
        CLegRR2.setRotationPoint(-5.2F, 22.5F, -1F);
        setRotation(CLegRR2, -1.570796F, 0F, -0.3490659F);

        CLegRR3 = new ModelRenderer(this, 2, 107);
        CLegRR3.addBox(-2F, 0F, -3F, 4, 2, 5);
        CLegRR3.setRotationPoint(-5.5F, 22F, -6F);
        setRotation(CLegRR3, -1.375609F, 0F, -0.3490659F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        MoCEntityBear entitybear = (MoCEntityBear) entity;
        int bearState = entitybear.getBearState();
        boolean openMouth = (entitybear.mouthCounter != 0);
        float attackSwing = entitybear.getAttackSwing();
        setRotationAngles(f, f1, f2, f3, f4, f5, bearState, attackSwing);

        if (bearState == 0)
        {
            if (openMouth)
            {
                MouthOpen.render(f5);
            }
            else
            {
                Mouth.render(f5);
            }

            LegFR1.render(f5);
            Neck.render(f5);
            LEar.render(f5);
            Snout.render(f5);
            Head.render(f5);
            REar.render(f5);
            Abdomen.render(f5);
            Torso.render(f5);
            LegRR3.render(f5);
            LegRR1.render(f5);
            LegRR2.render(f5);
            LegFR2.render(f5);
            LegFR3.render(f5);
            LegFL1.render(f5);
            LegFL3.render(f5);
            LegFL2.render(f5);
            LegRL1.render(f5);
            LegRL2.render(f5);
            LegRL3.render(f5);
            Tail.render(f5);
        }
        else if (bearState == 1)
        {
            BHead.render(f5);
            BSnout.render(f5);
            if (openMouth)
            {
                BMouthOpen.render(f5);
            }
            else
            {
                BMouth.render(f5);
            }

            BNeck.render(f5);
            BLEar.render(f5);
            BREar.render(f5);
            BTorso.render(f5);
            BAbdomen.render(f5);
            BTail.render(f5);
            BLegFL1.render(f5);
            BLegFL2.render(f5);
            BLegFL3.render(f5);
            BLegFR1.render(f5);
            BLegFR2.render(f5);
            BLegFR3.render(f5);
            BLegRL1.render(f5);
            BLegRL2.render(f5);
            BLegRL3.render(f5);
            BLegRR1.render(f5);
            BLegRR2.render(f5);
            BLegRR3.render(f5);
        }
        else if (bearState == 2)
        {
            if (openMouth)
            {
                CMouthOpen.render(f5);
            }
            else
            {
                CMouth.render(f5);
            }

            CHead.render(f5);
            CSnout.render(f5);
            CLEar.render(f5);
            CREar.render(f5);
            CNeck.render(f5);
            CTorso.render(f5);
            CAbdomen.render(f5);
            CTail.render(f5);
            CLegFL1.render(f5);
            CLegFL2.render(f5);
            CLegFL3.render(f5);
            CLegFR1.render(f5);
            CLegFR2.render(f5);
            CLegFR3.render(f5);
            CLegRL1.render(f5);
            CLegRL2.render(f5);
            CLegRL3.render(f5);
            CLegRR1.render(f5);
            CLegRR2.render(f5);
            CLegRR3.render(f5);
        }

    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, int bearState, float AttackRotX)
    {
        //float RLegXRot = MathHelper.cos((f * 0.6662F) + 3.141593F) * 0.8F * f1;
        //float LLegXRot = MathHelper.cos(f * 0.6662F) * 0.8F * f1;

        float LLegRotX = MathHelper.cos(f * 0.6662F) * 0.8F * f1;
        float RLegRotX = MathHelper.cos((f * 0.6662F) + 3.141593F) * 0.8F * f1;
        float XAngle = (f4 / 57.29578F);
        float YAngle = f3 / 57.29578F;

        if (bearState == 0)
        {
            Head.rotateAngleX = 0.1502636F + XAngle;
            Head.rotateAngleY = YAngle;

            Snout.rotateAngleX = 0.1502636F + XAngle;
            Snout.rotateAngleY = YAngle;

            Mouth.rotateAngleX = -0.0068161F + XAngle;
            Mouth.rotateAngleY = YAngle;

            MouthOpen.rotateAngleX = 0.534236F + XAngle;
            MouthOpen.rotateAngleY = YAngle;

            LEar.rotateAngleX = 0.1502636F + XAngle;
            LEar.rotateAngleY = -0.3490659F + YAngle;

            REar.rotateAngleX = 0.1502636F + XAngle;
            REar.rotateAngleY = 0.3490659F + YAngle;

            LegFL1.rotateAngleX = 0.2617994F + LLegRotX;
            LegFL2.rotateAngleX = LLegRotX;
            LegFL3.rotateAngleX = LLegRotX;

            LegRR1.rotateAngleX = -0.1745329F + LLegRotX;
            LegRR2.rotateAngleX = LLegRotX;
            LegRR3.rotateAngleX = LLegRotX;

            LegFR1.rotateAngleX = 0.2617994F + RLegRotX;
            LegFR2.rotateAngleX = RLegRotX;
            LegFR3.rotateAngleX = RLegRotX;

            LegRL1.rotateAngleX = -0.1745329F + RLegRotX;
            LegRL2.rotateAngleX = RLegRotX;
            LegRL3.rotateAngleX = RLegRotX;
        }
        else if (bearState == 1)
        {

            BHead.rotateAngleX = -0.0242694F - XAngle;
            BHead.rotateAngleY = YAngle;

            BSnout.rotateAngleX = -0.0242694F - XAngle;
            BSnout.rotateAngleY = YAngle;

            BMouth.rotateAngleX = -0.08726F - XAngle;
            BMouth.rotateAngleY = YAngle;

            BMouthOpen.rotateAngleX = 0.5235988F - XAngle;
            BMouthOpen.rotateAngleY = YAngle;

            BLEar.rotateAngleX = -0.0242694F - XAngle;
            BLEar.rotateAngleY = -0.3490659F + YAngle;

            BREar.rotateAngleX = -0.0242694F - XAngle;
            BREar.rotateAngleY = 0.3490659F + YAngle;

            /**
             * Arm breathing movement
             */
            float breathing = MathHelper.cos(f2 * 0.09F) * 0.05F + 0.05F;
            BLegFR1.rotateAngleZ = 0.2617994F + breathing;
            BLegFR2.rotateAngleZ = breathing;
            BLegFR3.rotateAngleZ = breathing;
            BLegFL1.rotateAngleZ = -0.2617994F - breathing;
            BLegFL2.rotateAngleZ = -breathing;
            BLegFL3.rotateAngleZ = -breathing;

            BLegFL1.rotateAngleX = 0.2617994F + AttackRotX;
            BLegFL2.rotateAngleX = -0.5576792F + AttackRotX;
            BLegFL3.rotateAngleX = 2.007645F + AttackRotX;

            BLegFR1.rotateAngleX = 0.2617994F + AttackRotX;
            BLegFR2.rotateAngleX = -0.5576792F + AttackRotX;
            BLegFR3.rotateAngleX = 2.007645F + AttackRotX;

            BLegRR1.rotateAngleX = -0.1745329F + LLegRotX;
            BLegRR2.rotateAngleX = LLegRotX;
            BLegRR3.rotateAngleX = LLegRotX;

            BLegRL1.rotateAngleX = -0.5235988F + RLegRotX;
            BLegRL2.rotateAngleX = RLegRotX;
            BLegRL3.rotateAngleX = RLegRotX;
        }
        else if (bearState == 2)
        {
            CHead.rotateAngleX = 0.1502636F + XAngle;
            CHead.rotateAngleY = YAngle;

            CSnout.rotateAngleX = 0.1502636F + XAngle;
            CSnout.rotateAngleY = YAngle;

            CMouth.rotateAngleX = -0.0068161F + XAngle;
            CMouth.rotateAngleY = YAngle;

            CMouthOpen.rotateAngleX = 0.3665191F + XAngle;
            CMouthOpen.rotateAngleY = YAngle;

            CLEar.rotateAngleX = 0.1502636F + XAngle;
            CLEar.rotateAngleY = -0.3490659F + YAngle;

            CREar.rotateAngleX = 0.1502636F + XAngle;
            CREar.rotateAngleY = 0.3490659F + YAngle;
        }

        Tail.rotateAngleZ = LLegRotX * 0.2F;
    }
}
