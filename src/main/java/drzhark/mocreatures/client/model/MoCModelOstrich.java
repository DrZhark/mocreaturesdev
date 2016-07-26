// TODO
// move model to the front (done)
// fix mirror issues (done)
// fix eye (done)
// neck point of rotation (done)
// legs (done)
// wings (done basic)
// tail movement
// flipping wings (done)

package drzhark.mocreatures.client.model;

import drzhark.mocreatures.entity.passive.MoCEntityOstrich;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCModelOstrich extends ModelBase {

    public MoCModelOstrich() {
        this.textureWidth = 128;
        this.textureHeight = 128; //64

        this.UBeak = new ModelRenderer(this, 12, 16);
        this.UBeak.addBox(-1.5F, -15F, -5.5F, 3, 1, 1);
        this.UBeak.setRotationPoint(0F, 3F, -6F);

        this.UBeak2 = new ModelRenderer(this, 20, 16);
        this.UBeak2.addBox(-1F, -15F, -7.5F, 2, 1, 2);
        this.UBeak2.setRotationPoint(0F, 3F, -6F);

        this.UBeakb = new ModelRenderer(this, 12, 16);
        this.UBeakb.addBox(-1.5F, -15F, -6.5F, 3, 1, 1);
        this.UBeakb.setRotationPoint(0F, 3F, -6F);
        setRotation(this.UBeakb, -0.0698132F, 0F, 0F);

        this.UBeak2b = new ModelRenderer(this, 20, 16);
        this.UBeak2b.addBox(-1F, -15F, -8.5F, 2, 1, 2);
        this.UBeak2b.setRotationPoint(0F, 3F, -6F);
        setRotation(this.UBeak2b, -0.0698132F, 0F, 0F);

        this.LBeak = new ModelRenderer(this, 12, 22);
        this.LBeak.addBox(-1.5F, -14F, -5.5F, 3, 1, 1);
        this.LBeak.setRotationPoint(0F, 3F, -6F);

        this.LBeakb = new ModelRenderer(this, 12, 22);
        this.LBeakb.addBox(-1.5F, -14F, -3.9F, 3, 1, 1);
        this.LBeakb.setRotationPoint(0F, 3F, -6F);
        setRotation(this.LBeakb, 0.122173F, 0F, 0F);

        this.LBeak2 = new ModelRenderer(this, 20, 22);
        this.LBeak2.addBox(-1F, -14F, -7.5F, 2, 1, 2);
        this.LBeak2.setRotationPoint(0F, 3F, -6F);

        this.LBeak2b = new ModelRenderer(this, 20, 22);
        this.LBeak2b.addBox(-1F, -14F, -5.9F, 2, 1, 2);
        this.LBeak2b.setRotationPoint(0F, 3F, -6F);
        setRotation(this.LBeak2b, 0.122173F, 0F, 0F);

        this.Body = new ModelRenderer(this, 0, 38);
        this.Body.addBox(-4F, 1F, 0F, 8, 10, 16);
        this.Body.setRotationPoint(0F, 0F, -6F);

        this.LLegA = new ModelRenderer(this, 50, 28);
        this.LLegA.addBox(-2F, -1F, -2.5F, 4, 6, 5);
        this.LLegA.setRotationPoint(4F, 5F, 4F);
        setRotation(this.LLegA, 0.1745329F, 0F, 0F);

        this.LLegB = new ModelRenderer(this, 50, 39);
        this.LLegB.addBox(-1.5F, 5F, -1.5F, 3, 4, 3);
        this.LLegB.setRotationPoint(4F, 5F, 4F);
        setRotation(this.LLegB, 0.1745329F, 0F, 0F);

        this.LLegC = new ModelRenderer(this, 8, 38);
        this.LLegC.addBox(-1F, 8F, 2.5F, 2, 10, 2);
        this.LLegC.setRotationPoint(4F, 5F, 4F);
        setRotation(this.LLegC, -0.2617994F, 0F, 0F);

        this.LFoot = new ModelRenderer(this, 32, 42);
        this.LFoot.addBox(-1F, 17F, -9F, 2, 1, 5);
        this.LFoot.setRotationPoint(4F, 5F, 4F);
        setRotation(this.LFoot, 0.1745329F, 0F, 0F);

        this.RLegA = new ModelRenderer(this, 0, 27);
        this.RLegA.addBox(-2F, -1F, -2.5F, 4, 6, 5);
        this.RLegA.setRotationPoint(-4F, 5F, 4F);
        setRotation(this.RLegA, 0.1745329F, 0F, 0F);

        this.RLegB = new ModelRenderer(this, 18, 27);
        this.RLegB.addBox(-1.5F, 5F, -1.5F, 3, 4, 3);
        this.RLegB.setRotationPoint(-4F, 5F, 4F);
        setRotation(this.RLegB, 0.1745329F, 0F, 0F);

        this.RLegC = new ModelRenderer(this, 0, 38);
        this.RLegC.addBox(-1F, 8F, 2.5F, 2, 10, 2);
        this.RLegC.setRotationPoint(-4F, 5F, 4F);
        setRotation(this.RLegC, -0.2617994F, 0F, 0F);

        this.RFoot = new ModelRenderer(this, 32, 48);
        this.RFoot.addBox(-1F, 17F, -9F, 2, 1, 5);
        this.RFoot.setRotationPoint(-4F, 5F, 4F);
        setRotation(this.RFoot, 0.1745329F, 0F, 0F);

        this.Tail1 = new ModelRenderer(this, 44, 18);
        this.Tail1.addBox(-0.5F, -2F, -2F, 1, 4, 6);
        this.Tail1.setRotationPoint(0F, 4F, 15F);
        setRotation(this.Tail1, 0.3490659F, 0F, 0F);

        this.Tail2 = new ModelRenderer(this, 58, 18);
        this.Tail2.addBox(-2.6F, -2F, -2F, 1, 4, 6);
        this.Tail2.setRotationPoint(0F, 4F, 15F);
        setRotation(this.Tail2, 0.3490659F, -0.2617994F, 0F);

        this.Tail3 = new ModelRenderer(this, 30, 18);
        this.Tail3.addBox(1.6F, -2F, -2F, 1, 4, 6);
        this.Tail3.setRotationPoint(0F, 4F, 15F);
        setRotation(this.Tail3, 0.3490659F, 0.2617994F, 0F);

        this.LWingB = new ModelRenderer(this, 68, 46);
        this.LWingB.addBox(-0.5F, -3F, 0F, 1, 4, 14);
        this.LWingB.setRotationPoint(4F, 4F, -3F);
        setRotation(this.LWingB, 0.0872665F, 0.0872665F, 0F);

        this.LWingC = new ModelRenderer(this, 98, 46);
        this.LWingC.addBox(-1F, 0F, 0F, 1, 4, 14);
        this.LWingC.setRotationPoint(4F, 4F, -3F);
        setRotation(this.LWingC, 0F, 0.0872665F, 0F);

        this.LWingD = new ModelRenderer(this, 26, 84);
        this.LWingD.addBox(0F, -1F, -1F, 15, 2, 2);
        this.LWingD.setRotationPoint(4F, 3F, -3F);
        setRotation(this.LWingD, 0F, 0F, -0.3490659F);

        this.LWingE = new ModelRenderer(this, 0, 103);
        this.LWingE.addBox(0F, 0F, 1F, 15, 0, 15);
        this.LWingE.setRotationPoint(4F, 3F, -3F);
        setRotation(this.LWingE, 0F, 0F, -0.3490659F);

        this.RWingB = new ModelRenderer(this, 68, 0);
        this.RWingB.addBox(-0.5F, -3F, 0F, 1, 4, 14);
        this.RWingB.setRotationPoint(-4F, 4F, -3F);
        setRotation(this.RWingB, 0.0872665F, -0.0872665F, 0F);

        this.RWingC = new ModelRenderer(this, 98, 0);
        this.RWingC.addBox(0F, 0F, 0F, 1, 4, 14);
        this.RWingC.setRotationPoint(-4F, 4F, -3F);
        setRotation(this.RWingC, 0F, -0.0872665F, 0F);

        this.RWingD = new ModelRenderer(this, 26, 80);
        this.RWingD.addBox(-15F, -1F, -1F, 15, 2, 2);
        this.RWingD.setRotationPoint(-4F, 3F, -3F);
        setRotation(this.RWingD, 0F, 0F, 0.3490659F);

        this.RWingE = new ModelRenderer(this, 0, 88);
        this.RWingE.addBox(-15F, 0F, 1F, 15, 0, 15);
        this.RWingE.setRotationPoint(-4F, 3F, -3F);
        setRotation(this.RWingE, 0F, 0F, 0.3490659F);

        this.SaddleA = new ModelRenderer(this, 72, 18);
        this.SaddleA.addBox(-4F, 0.5F, -3F, 8, 1, 8);
        this.SaddleA.setRotationPoint(0F, 0F, 0F);
        this.SaddleB = new ModelRenderer(this, 72, 27);

        this.SaddleB.addBox(-1.5F, 0F, -3F, 3, 1, 2);
        this.SaddleB.setRotationPoint(0F, 0F, 0F);

        this.SaddleL = new ModelRenderer(this, 72, 30);
        this.SaddleL.addBox(-0.5F, 0F, -0.5F, 1, 6, 1);
        this.SaddleL.setRotationPoint(4F, 1F, 0F);

        this.SaddleR = new ModelRenderer(this, 84, 30);
        this.SaddleR.addBox(-0.5F, 0F, -0.5F, 1, 6, 1);
        this.SaddleR.setRotationPoint(-4F, 1F, 0F);

        this.SaddleL2 = new ModelRenderer(this, 76, 30);
        this.SaddleL2.addBox(-0.5F, 6F, -1F, 1, 2, 2);
        this.SaddleL2.setRotationPoint(4F, 1F, 0F);

        this.SaddleR2 = new ModelRenderer(this, 88, 30);
        this.SaddleR2.addBox(-0.5F, 6F, -1F, 1, 2, 2);
        this.SaddleR2.setRotationPoint(-4F, 1F, 0F);

        this.SaddleC = new ModelRenderer(this, 84, 27);
        this.SaddleC.addBox(-4F, 0F, 3F, 8, 1, 2);
        this.SaddleC.setRotationPoint(0F, 0F, 0F);

        this.NeckLFeather = new ModelRenderer(this, 8, 73);
        this.NeckLFeather.addBox(0F, -8F, -0.5F, 0, 7, 4);
        this.NeckLFeather.setRotationPoint(0F, 3F, -6F);
        setRotation(this.NeckLFeather, 0.2007129F, 0F, 0F);

        this.NeckUFeather = new ModelRenderer(this, 0, 73);
        this.NeckUFeather.addBox(0F, -16F, -2F, 0, 9, 4);
        this.NeckUFeather.setRotationPoint(0F, 3F, -6F);

        this.NeckD = new ModelRenderer(this, 0, 16);
        this.NeckD.addBox(-1.5F, -4F, -2F, 3, 8, 3);
        this.NeckD.setRotationPoint(0F, 3F, -6F);
        setRotation(this.NeckD, 0.4363323F, 0F, 0F);

        this.Saddlebag = new ModelRenderer(this, 32, 7);
        this.Saddlebag.addBox(-4.5F, -3F, 5F, 9, 4, 7);
        this.Saddlebag.setRotationPoint(0F, 0F, 0F);
        setRotation(this.Saddlebag, -0.2602503F, 0F, 0F);

        this.Flagpole = new ModelRenderer(this, 28, 0);
        this.Flagpole.addBox(-0.5F, -15F, -0.5F, 1, 17, 1);
        this.Flagpole.setRotationPoint(0F, 0F, 5F);
        setRotation(this.Flagpole, -0.2602503F, 0F, 0F);

        this.FlagBlack = new ModelRenderer(this, 108, 8);
        this.FlagBlack.addBox(0F, -2.1F, 0F, 0, 4, 10);
        this.FlagBlack.setRotationPoint(0F, -12F, 8F);
        setRotation(this.FlagBlack, -0.2602503F, 0F, 0F);

        this.FlagDarkGrey = new ModelRenderer(this, 108, 12);
        this.FlagDarkGrey.addBox(0F, -2.1F, 0F, 0, 4, 10);
        this.FlagDarkGrey.setRotationPoint(0F, -12F, 8F);
        setRotation(this.FlagDarkGrey, -0.2602503F, 0F, 0F);

        this.FlagYellow = new ModelRenderer(this, 48, 46);
        this.FlagYellow.addBox(0F, -2.1F, 0F, 0, 4, 10);
        this.FlagYellow.setRotationPoint(0F, -12F, 8F);
        setRotation(this.FlagYellow, -0.2602503F, 0F, 0F);

        this.FlagBrown = new ModelRenderer(this, 48, 42);
        this.FlagBrown.addBox(0F, -2.1F, 0F, 0, 4, 10);
        this.FlagBrown.setRotationPoint(0F, -12F, 8F);
        setRotation(this.FlagBrown, -0.2602503F, 0F, 0F);

        this.FlagGreen = new ModelRenderer(this, 48, 38);
        this.FlagGreen.addBox(0F, -2.1F, 0F, 0, 4, 10);
        this.FlagGreen.setRotationPoint(0F, -12F, 8F);
        setRotation(this.FlagGreen, -0.2602503F, 0F, 0F);

        /*
         * FlagCyan = new ModelRenderer(this, 48, 50); FlagCyan.addBox(0F,
         * -16.5F, 5F, 0, 4, 10); FlagCyan.setRotationPoint(0F, 0F, 0F);
         * setRotation(FlagCyan, -0.2602503F, 0F, 0F);
         */

        this.FlagCyan = new ModelRenderer(this, 48, 50);
        this.FlagCyan.addBox(0F, -2.1F, 0F, 0, 4, 10);
        this.FlagCyan.setRotationPoint(0F, -12F, 8F);
        setRotation(this.FlagCyan, -0.2602503F, 0F, 0F);

        this.FlagLightBlue = new ModelRenderer(this, 68, 32);
        this.FlagLightBlue.addBox(0F, -2.1F, 0F, 0, 4, 10);
        this.FlagLightBlue.setRotationPoint(0F, -12F, 8F);
        setRotation(this.FlagLightBlue, -0.2602503F, 0F, 0F);

        this.FlagDarkBlue = new ModelRenderer(this, 68, 28);
        this.FlagDarkBlue.addBox(0F, -2.1F, 0F, 0, 4, 10);
        this.FlagDarkBlue.setRotationPoint(0F, -12F, 8F);
        setRotation(this.FlagDarkBlue, -0.2602503F, 0F, 0F);

        this.FlagPurple = new ModelRenderer(this, 88, 32);
        this.FlagPurple.addBox(0F, -2.1F, 0F, 0, 4, 10);
        this.FlagPurple.setRotationPoint(0F, -12F, 8F);
        setRotation(this.FlagPurple, -0.2602503F, 0F, 0F);

        this.FlagDarkPurple = new ModelRenderer(this, 88, 28);
        this.FlagDarkPurple.addBox(0F, -2.1F, 0F, 0, 4, 10);
        this.FlagDarkPurple.setRotationPoint(0F, -12F, 8F);
        setRotation(this.FlagDarkPurple, -0.2602503F, 0F, 0F);

        this.FlagDarkGreen = new ModelRenderer(this, 108, 32);
        this.FlagDarkGreen.addBox(0F, -2.1F, 0F, 0, 4, 10);
        this.FlagDarkGreen.setRotationPoint(0F, -12F, 8F);
        setRotation(this.FlagDarkGreen, -0.2602503F, 0F, 0F);

        this.FlagLightRed = new ModelRenderer(this, 108, 28);
        this.FlagLightRed.addBox(0F, -2.1F, 0F, 0, 4, 10);
        this.FlagLightRed.setRotationPoint(0F, -12F, 8F);
        setRotation(this.FlagLightRed, -0.2602503F, 0F, 0F);

        this.FlagRed = new ModelRenderer(this, 108, 24);
        this.FlagRed.addBox(0F, -2.1F, 0F, 0, 4, 10);
        this.FlagRed.setRotationPoint(0F, -12F, 8F);
        setRotation(this.FlagRed, -0.2602503F, 0F, 0F);

        this.FlagWhite = new ModelRenderer(this, 108, 20);
        this.FlagWhite.addBox(0F, -2.1F, 0F, 0, 4, 10);
        this.FlagWhite.setRotationPoint(0F, -12F, 8F);
        setRotation(this.FlagWhite, -0.2602503F, 0F, 0F);

        this.FlagGrey = new ModelRenderer(this, 108, 16);
        this.FlagGrey.addBox(0F, -2.1F, 0F, 0, 4, 10);
        this.FlagGrey.setRotationPoint(0F, -12F, 8F);
        setRotation(this.FlagGrey, -0.2602503F, 0F, 0F);

        this.FlagOrange = new ModelRenderer(this, 88, 24);
        this.FlagOrange.addBox(0F, -2.1F, 0F, 0, 4, 10);
        this.FlagOrange.setRotationPoint(0F, -12F, 8F);
        setRotation(this.FlagOrange, -0.2602503F, 0F, 0F);

        this.NeckU = new ModelRenderer(this, 20, 0);
        this.NeckU.addBox(-1F, -12F, -4F, 2, 5, 2);
        this.NeckU.setRotationPoint(0F, 3F, -6F);

        this.NeckL = new ModelRenderer(this, 20, 7);
        this.NeckL.addBox(-1F, -8F, -2.5F, 2, 5, 2);
        this.NeckL.setRotationPoint(0F, 3F, -6F);
        setRotation(this.NeckL, 0.2007129F, 0F, 0F);

        this.NeckHarness = new ModelRenderer(this, 0, 11);
        this.NeckHarness.addBox(-2F, -3F, -2.5F, 4, 1, 4);
        this.NeckHarness.setRotationPoint(0F, 3F, -6F);
        setRotation(this.NeckHarness, 0.4363323F, 0F, 0F);

        this.NeckHarness2 = new ModelRenderer(this, 84, 55);
        this.NeckHarness2.addBox(-3F, -2.5F, -2F, 6, 1, 1);
        this.NeckHarness2.setRotationPoint(0F, 3F, -6F);

        this.NeckHarnessRight = new ModelRenderer(this, 84, 45);
        this.NeckHarnessRight.addBox(-2.3F, -3.5F, -0.5F, 0, 3, 12);
        this.NeckHarnessRight.setRotationPoint(0F, 3F, -6F);
        setRotation(this.NeckHarnessRight, 0.8983798F, 0F, 0F);

        this.NeckHarnessLeft = new ModelRenderer(this, 84, 45);
        this.NeckHarnessLeft.addBox(2.3F, -3.5F, -0.5F, 0, 3, 12);
        this.NeckHarnessLeft.setRotationPoint(0F, 3F, -6F);
        setRotation(this.NeckHarnessLeft, 0.8983798F, 0F, 0F);

        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.addBox(-1.5F, -16F, -4.5F, 3, 4, 3);
        this.Head.setRotationPoint(0F, 3F, -6F);

        this.UniHorn = new ModelRenderer(this, 0, 8);
        this.UniHorn.addBox(-0.5F, -21F, 0.5F, 1, 6, 1);
        this.UniHorn.setRotationPoint(0F, 3F, -6F);
        setRotation(this.UniHorn, 0.3171542F, 0F, 0F);

        this.HelmetLeather = new ModelRenderer(this, 66, 0);
        this.HelmetLeather.addBox(-2F, -16.5F, -5F, 4, 5, 4);
        this.HelmetLeather.setRotationPoint(0F, 3F, -6F);

        this.HelmetIron = new ModelRenderer(this, 84, 46);
        this.HelmetIron.addBox(-2F, -16.5F, -5F, 4, 5, 4);
        this.HelmetIron.setRotationPoint(0F, 3F, -6F);

        this.HelmetGold = new ModelRenderer(this, 112, 64);
        this.HelmetGold.addBox(-2F, -16.5F, -5F, 4, 5, 4);
        this.HelmetGold.setRotationPoint(0F, 3F, -6F);

        this.HelmetDiamond = new ModelRenderer(this, 96, 64);
        this.HelmetDiamond.addBox(-2F, -16.5F, -5F, 4, 5, 4);
        this.HelmetDiamond.setRotationPoint(0F, 3F, -6F);

        this.HelmetHide = new ModelRenderer(this, 96, 5);
        this.HelmetHide.addBox(-2F, -16.5F, -5F, 4, 5, 4);
        this.HelmetHide.setRotationPoint(0F, 3F, -6F);

        this.HelmetNeckHide = new ModelRenderer(this, 58, 0);
        this.HelmetNeckHide.addBox(-1.5F, -12F, -4.5F, 3, 1, 3);
        this.HelmetNeckHide.setRotationPoint(0F, 3F, -6F);

        this.HelmetHideEar1 = new ModelRenderer(this, 84, 9);
        this.HelmetHideEar1.addBox(-2.5F, -18F, -3F, 2, 2, 1);
        this.HelmetHideEar1.setRotationPoint(0F, 3F, -6F);
        //setRotation(HelmetHideEar1, 0F, 0F, 0.4363323F);

        this.HelmetHideEar2 = new ModelRenderer(this, 90, 9);
        this.HelmetHideEar2.addBox(0.5F, -18F, -3F, 2, 2, 1);
        this.HelmetHideEar2.setRotationPoint(0F, 3F, -6F);
        //setRotation(HelmetHideEar2, 0F, 0F, -0.4363323F);

        this.HelmetFur = new ModelRenderer(this, 84, 0);
        this.HelmetFur.addBox(-2F, -16.5F, -5F, 4, 5, 4);
        this.HelmetFur.setRotationPoint(0F, 3F, -6F);

        this.HelmetNeckFur = new ModelRenderer(this, 96, 0);
        this.HelmetNeckFur.addBox(-1.5F, -12F, -4.5F, 3, 1, 3);
        this.HelmetNeckFur.setRotationPoint(0F, 3F, -6F);

        this.HelmetFurEar1 = new ModelRenderer(this, 66, 9);
        this.HelmetFurEar1.addBox(-2.5F, -18F, -3F, 2, 2, 1);
        this.HelmetFurEar1.setRotationPoint(0F, 3F, -6F);

        this.HelmetFurEar2 = new ModelRenderer(this, 76, 9);
        this.HelmetFurEar2.addBox(0.5F, -18F, -3F, 2, 2, 1);
        this.HelmetFurEar2.setRotationPoint(0F, 3F, -6F);

        this.HelmetReptile = new ModelRenderer(this, 64, 64);
        this.HelmetReptile.addBox(-2F, -16.5F, -5F, 4, 5, 4);
        this.HelmetReptile.setRotationPoint(0F, 3F, -6F);

        this.HelmetReptileEar2 = new ModelRenderer(this, 114, 45);
        this.HelmetReptileEar2.addBox(2.5F, -16.5F, -2F, 0, 5, 5);
        this.HelmetReptileEar2.setRotationPoint(0F, 3F, -6F);
        setRotation(this.HelmetReptileEar2, 0F, 0.6108652F, 0F);

        this.HelmetReptileEar1 = new ModelRenderer(this, 114, 50);
        this.HelmetReptileEar1.addBox(-2.5F, -16.5F, -2F, 0, 5, 5);
        this.HelmetReptileEar1.setRotationPoint(0F, 3F, -6F);
        setRotation(this.HelmetReptileEar1, 0F, -0.6108652F, 0F);

        this.HelmetGreenChitin = new ModelRenderer(this, 80, 64);
        this.HelmetGreenChitin.addBox(-2F, -16.5F, -5F, 4, 5, 4);
        this.HelmetGreenChitin.setRotationPoint(0F, 3F, -6F);

        this.HelmetYellowChitin = new ModelRenderer(this, 0, 64);
        this.HelmetYellowChitin.addBox(-2F, -16.5F, -5F, 4, 5, 4);
        this.HelmetYellowChitin.setRotationPoint(0F, 3F, -6F);

        this.HelmetBlueChitin = new ModelRenderer(this, 16, 64);
        this.HelmetBlueChitin.addBox(-2F, -16.5F, -5F, 4, 5, 4);
        this.HelmetBlueChitin.setRotationPoint(0F, 3F, -6F);

        this.HelmetBlackChitin = new ModelRenderer(this, 32, 64);
        this.HelmetBlackChitin.addBox(-2F, -16.5F, -5F, 4, 5, 4);
        this.HelmetBlackChitin.setRotationPoint(0F, 3F, -6F);

        this.HelmetRedChitin = new ModelRenderer(this, 48, 64);
        this.HelmetRedChitin.addBox(-2F, -16.5F, -5F, 4, 5, 4);
        this.HelmetRedChitin.setRotationPoint(0F, 3F, -6F);

        /*
         * Tail = new ModelRenderer(this, 30, 28); Tail.addBox(-2.5F, 3F, 16F,
         * 5, 5, 5); Tail.setRotationPoint(0F, 0F, -6F); Tailpart1 = new
         * ModelRenderer(this, 30, 28); Tailpart1.addBox(-2.5F, -3.2F, 21F, 5,
         * 5, 5); Tailpart1.setRotationPoint(0F, 0F, -6F);
         * setRotation(Tailpart1, -0.2974289F, 0F, 0F); Tailpart2 = new
         * ModelRenderer(this, 60, 73); Tailpart2.addBox(-2.5F, -8.8F, 24.6F, 5,
         * 5, 8); Tailpart2.setRotationPoint(0F, 0F, -6F);
         * setRotation(Tailpart2, -0.5205006F, 0F, 0F); Tailpart3 = new
         * ModelRenderer(this, 60, 86); Tailpart3.addBox(-2F, 1.5F, 32.6F, 4, 4,
         * 7); Tailpart3.setRotationPoint(0F, 0F, -6F); setRotation(Tailpart3,
         * -0.2230717F, 0F, 0F); Tailpart4 = new ModelRenderer(this, 60, 97);
         * Tailpart4.addBox(-1.5F, 13F, 36.6F, 3, 3, 7);
         * Tailpart4.setRotationPoint(0F, 0F, -6F); setRotation(Tailpart4,
         * 0.0743572F, 0F, 0F); Tailpart5 = new ModelRenderer(this, 60, 107);
         * Tailpart5.addBox(-1F, 26.5F, 35.9F, 2, 2, 5);
         * Tailpart5.setRotationPoint(0F, 0F, -6F); setRotation(Tailpart5,
         * 0.4089647F, 0F, 0F);
         */

        this.Tail = new ModelRenderer(this, 30, 28);
        this.Tail.addBox(-2.5F, -1F, 0F, 5, 5, 5);
        this.Tail.setRotationPoint(0F, 4F, 10F);

        this.Tailpart1 = new ModelRenderer(this, 30, 28);
        this.Tailpart1.addBox(-2.5F, -2.2F, 5F, 5, 5, 5);
        this.Tailpart1.setRotationPoint(0F, 4F, 10F);
        setRotation(this.Tailpart1, -0.2974289F, 0F, 0F);

        this.Tailpart2 = new ModelRenderer(this, 60, 73);
        this.Tailpart2.addBox(-2.5F, -4.3F, 9F, 5, 5, 8);
        this.Tailpart2.setRotationPoint(0F, 4F, 10F);
        setRotation(this.Tailpart2, -0.5205006F, 0F, 0F);

        this.Tailpart3 = new ModelRenderer(this, 60, 86);
        this.Tailpart3.addBox(-2F, 1F, 16F, 4, 4, 7);
        this.Tailpart3.setRotationPoint(0F, 4F, 10F);
        setRotation(this.Tailpart3, -0.2230717F, 0F, 0F);

        this.Tailpart4 = new ModelRenderer(this, 60, 97);
        this.Tailpart4.addBox(-1.5F, 8F, 20.6F, 3, 3, 7);
        this.Tailpart4.setRotationPoint(0F, 4F, 10F);
        setRotation(this.Tailpart4, 0.0743572F, 0F, 0F);

        this.Tailpart5 = new ModelRenderer(this, 60, 107);
        this.Tailpart5.addBox(-1F, 16.5F, 22.9F, 2, 2, 5);
        this.Tailpart5.setRotationPoint(0F, 4F, 10F);
        setRotation(this.Tailpart5, 0.4089647F, 0F, 0F);
    }

    /**
     * Used for easily adding entity-dependent animations. The second and third
     * float params here are the same second and third as in the
     * setRotationAngles method.
     */
    /*
     * public void setLivingAnimations(EntityLiving entityliving, float par2,
     * float par3, float par4) { super.setLivingAnimations(entityliving, par2,
     * par3, par4); this.Head.rotationPointY = 6.0F +
     * ((EntitySheep)entityliving).func_44003_c(par4) * 9.0F; this.field_44016_o
     * = ((EntitySheep)entityliving).func_44002_d(par4); }
     */

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {

        MoCEntityOstrich entityostrich = (MoCEntityOstrich) entity;
        this.typeI = (byte) entityostrich.getType();
        boolean openMouth = (entityostrich.mouthCounter != 0);
        boolean isSaddled = entityostrich.getIsRideable();
        boolean isHiding = entityostrich.getHiding();
        boolean wingFlap = (entityostrich.wingCounter != 0);
        boolean bagged = entityostrich.getIsChested();
        boolean rider = (entityostrich.riddenByEntity != null);
        int jumpCounter = entityostrich.jumpCounter;
        boolean floating = (entityostrich.isFlyer() && entityostrich.isOnAir());

        this.helmet = entityostrich.getHelmet();
        this.flagColor = entityostrich.getFlagColor();
        //super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, isHiding, wingFlap, rider, jumpCounter, floating);

        this.Head.render(f5);

        this.NeckU.render(f5);
        this.NeckD.render(f5);
        this.NeckL.render(f5);
        this.Body.render(f5);
        this.Tail.render(f5);
        this.LLegA.render(f5);
        this.LLegB.render(f5);
        this.LLegC.render(f5);
        this.LFoot.render(f5);
        this.RLegA.render(f5);
        this.RLegB.render(f5);
        this.RLegC.render(f5);
        this.RFoot.render(f5);

        if (this.typeI == 8) {
            this.UniHorn.render(f5);
        }

        if (this.typeI == 5 || this.typeI == 6) //demon and darkness ostriches
        {
            this.LWingD.render(f5);
            this.LWingE.render(f5);
            this.RWingD.render(f5);
            this.RWingE.render(f5);
            this.NeckUFeather.render(f5);
            this.NeckLFeather.render(f5);
        } else {
            this.LWingB.render(f5);
            this.LWingC.render(f5);
            this.RWingB.render(f5);
            this.RWingC.render(f5);
        }

        if (this.typeI == 6) //darkness ostrich
        {

            this.Tailpart1.render(f5);
            this.Tailpart2.render(f5);
            this.Tailpart3.render(f5);
            this.Tailpart4.render(f5);
            this.Tailpart5.render(f5);
        } else {
            this.Tail1.render(f5);
            this.Tail2.render(f5);
            this.Tail3.render(f5);
        }

        if (openMouth) {
            this.UBeakb.render(f5);
            this.UBeak2b.render(f5);
            this.LBeakb.render(f5);
            this.LBeak2b.render(f5);
        } else {
            this.UBeak.render(f5);
            this.UBeak2.render(f5);
            this.LBeak.render(f5);
            this.LBeak2.render(f5);
        }

        if (isSaddled) {
            this.SaddleA.render(f5);
            this.SaddleB.render(f5);
            this.SaddleC.render(f5);
            this.SaddleL.render(f5);
            this.SaddleR.render(f5);
            this.SaddleL2.render(f5);
            this.SaddleR2.render(f5);
            this.NeckHarness.render(f5);
            this.NeckHarness2.render(f5);
            if (rider) {
                this.NeckHarnessLeft.render(f5);
                this.NeckHarnessRight.render(f5);
            }

        }

        if (bagged) {
            this.Saddlebag.render(f5);
            this.Flagpole.render(f5);
            switch (this.flagColor) {
            //case 0:
            //    FlagWhite.render(f5);
            //   break;
                case 1:
                    this.FlagOrange.render(f5);
                    break;
                case 2:
                    this.FlagPurple.render(f5);
                    break;
                case 3:
                    this.FlagLightBlue.render(f5);
                    break;
                case 4:
                    this.FlagYellow.render(f5);
                    break;
                case 5:
                    this.FlagGreen.render(f5);
                    break;
                case 6:
                    this.FlagLightRed.render(f5);
                    break;
                case 7:
                    this.FlagDarkGrey.render(f5);
                    break;
                case 8:
                    this.FlagGrey.render(f5);
                    break;
                case 9:
                    this.FlagCyan.render(f5);
                    break;
                case 10:
                    this.FlagDarkPurple.render(f5);
                    break;
                case 11:
                    this.FlagDarkBlue.render(f5);
                    break;
                case 12:
                    this.FlagBrown.render(f5);
                    break;
                case 13:
                    this.FlagDarkGreen.render(f5);
                    break;
                case 14:
                    this.FlagRed.render(f5);
                    break;
                case 15:
                    this.FlagBlack.render(f5);
                    break;
                case 16:
                    this.FlagWhite.render(f5);
                    break;
            }
        }

        switch (this.helmet) {
            case 1:
                this.HelmetLeather.render(f5);
                break;
            case 2:
                this.HelmetIron.render(f5);
                break;
            case 3:
                this.HelmetGold.render(f5);
                break;
            case 4:
                this.HelmetDiamond.render(f5);
                break;
            case 5:
                this.HelmetHide.render(f5);
                this.HelmetNeckHide.render(f5);
                this.HelmetHideEar1.render(f5);
                this.HelmetHideEar2.render(f5);
                break;
            case 6:
                this.HelmetFur.render(f5);
                this.HelmetNeckFur.render(f5);
                this.HelmetFurEar1.render(f5);
                this.HelmetFurEar2.render(f5);
                break;
            case 7:
                this.HelmetReptile.render(f5);
                this.HelmetReptileEar1.render(f5);
                this.HelmetReptileEar2.render(f5);
                break;
            case 8:
                this.HelmetGreenChitin.render(f5);
                break;
            case 9:
                this.HelmetYellowChitin.render(f5);
                break;
            case 10:
                this.HelmetBlueChitin.render(f5);
                break;
            case 11:
                this.HelmetBlackChitin.render(f5);
                break;
            case 12:
                this.HelmetRedChitin.render(f5);
                break;

        }

    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, boolean hiding, boolean wing, boolean rider,
            int jumpCounter, boolean floating) {
        float LLegXRot = MathHelper.cos(f * 0.4F) * 1.1F * f1;
        float RLegXRot = MathHelper.cos((f * 0.4F) + 3.141593F) * 1.1F * f1;

        if (hiding) {
            this.Head.rotationPointY = 9.0F;
            this.Head.rotateAngleX = 2.61799F;
            this.Head.rotateAngleY = 0.0F;

        } else {
            this.Head.rotationPointY = 3.0F;
            this.Head.rotateAngleX = (RLegXRot / 20F) + (-f4 / (180F / (float) Math.PI));
            this.Head.rotateAngleY = f3 / (180F / (float) Math.PI);
        }

        if (rider) {
            if (floating) {
                this.Head.rotateAngleX = 0F;
            } else {
                this.Head.rotateAngleX = (RLegXRot / 20F);
            }
        }

        this.UBeak.rotationPointY = this.Head.rotationPointY;
        this.UBeakb.rotationPointY = this.Head.rotationPointY;
        this.UBeak2.rotationPointY = this.Head.rotationPointY;
        this.UBeak2b.rotationPointY = this.Head.rotationPointY;
        this.LBeak.rotationPointY = this.Head.rotationPointY;
        this.LBeakb.rotationPointY = this.Head.rotationPointY;
        this.LBeak2.rotationPointY = this.Head.rotationPointY;
        this.LBeak2b.rotationPointY = this.Head.rotationPointY;
        this.NeckU.rotationPointY = this.Head.rotationPointY;
        this.NeckD.rotationPointY = this.Head.rotationPointY;
        this.NeckL.rotationPointY = this.Head.rotationPointY;

        this.UBeak.rotateAngleX = this.Head.rotateAngleX;
        this.UBeak.rotateAngleY = this.Head.rotateAngleY;
        this.UBeak2.rotateAngleX = this.Head.rotateAngleX;
        this.UBeak2.rotateAngleY = this.Head.rotateAngleY;
        this.LBeak.rotateAngleX = this.Head.rotateAngleX;
        this.LBeak.rotateAngleY = this.Head.rotateAngleY;
        this.LBeak2.rotateAngleX = this.Head.rotateAngleX;
        this.LBeak2.rotateAngleY = this.Head.rotateAngleY;
        this.NeckU.rotateAngleX = this.Head.rotateAngleX;
        this.NeckU.rotateAngleY = this.Head.rotateAngleY;
        this.NeckD.rotateAngleX = 0.4363323F + this.Head.rotateAngleX;
        this.NeckD.rotateAngleY = this.Head.rotateAngleY;
        this.NeckL.rotateAngleX = (11.5F / this.radianF) + this.Head.rotateAngleX;
        this.NeckL.rotateAngleY = this.Head.rotateAngleY;

        this.UBeakb.rotateAngleX = -0.0698132F + this.Head.rotateAngleX;
        this.UBeakb.rotateAngleY = this.Head.rotateAngleY;
        this.UBeak2b.rotateAngleX = -0.0698132F + this.Head.rotateAngleX;
        this.UBeak2b.rotateAngleY = this.Head.rotateAngleY;

        this.LBeakb.rotateAngleX = (7F / this.radianF) + this.Head.rotateAngleX;
        this.LBeakb.rotateAngleY = this.Head.rotateAngleY;
        this.LBeak2b.rotateAngleX = (7F / this.radianF) + this.Head.rotateAngleX;
        this.LBeak2b.rotateAngleY = this.Head.rotateAngleY;

        this.NeckUFeather.rotationPointY = this.Head.rotationPointY;
        this.NeckLFeather.rotationPointY = this.Head.rotationPointY;
        this.UniHorn.rotationPointY = this.Head.rotationPointY;

        this.NeckUFeather.rotateAngleX = this.Head.rotateAngleX;
        this.NeckUFeather.rotateAngleY = this.Head.rotateAngleY;
        this.NeckLFeather.rotateAngleX = (11.5F / this.radianF) + this.Head.rotateAngleX;
        this.NeckLFeather.rotateAngleY = this.Head.rotateAngleY;
        this.UniHorn.rotateAngleX = (18F / this.radianF) + this.Head.rotateAngleX;
        this.UniHorn.rotateAngleY = this.Head.rotateAngleY;

        this.NeckHarness.rotationPointY = this.Head.rotationPointY;
        this.NeckHarness2.rotationPointY = this.Head.rotationPointY;
        this.NeckHarnessLeft.rotationPointY = this.Head.rotationPointY;
        this.NeckHarnessRight.rotationPointY = this.Head.rotationPointY;

        this.NeckHarness.rotateAngleX = (25F / this.radianF) + this.Head.rotateAngleX;
        this.NeckHarness.rotateAngleY = this.Head.rotateAngleY;
        this.NeckHarness2.rotateAngleX = this.Head.rotateAngleX;
        this.NeckHarness2.rotateAngleY = this.Head.rotateAngleY;
        this.NeckHarnessLeft.rotateAngleX = (50F / this.radianF) + this.Head.rotateAngleX;
        this.NeckHarnessLeft.rotateAngleY = this.Head.rotateAngleY;
        this.NeckHarnessRight.rotateAngleX = (50F / this.radianF) + this.Head.rotateAngleX;
        this.NeckHarnessRight.rotateAngleY = this.Head.rotateAngleY;

        //helmets

        switch (this.helmet) {
            case 1:

                this.HelmetLeather.rotationPointY = this.Head.rotationPointY;
                this.HelmetLeather.rotateAngleX = this.Head.rotateAngleX;
                this.HelmetLeather.rotateAngleY = this.Head.rotateAngleY;
                break;
            case 2:

                this.HelmetIron.rotationPointY = this.Head.rotationPointY;
                this.HelmetIron.rotateAngleX = this.Head.rotateAngleX;
                this.HelmetIron.rotateAngleY = this.Head.rotateAngleY;
                break;
            case 3:

                this.HelmetGold.rotationPointY = this.Head.rotationPointY;
                this.HelmetGold.rotateAngleX = this.Head.rotateAngleX;
                this.HelmetGold.rotateAngleY = this.Head.rotateAngleY;
                break;
            case 4:
                this.HelmetDiamond.rotationPointY = this.Head.rotationPointY;
                this.HelmetDiamond.rotateAngleX = this.Head.rotateAngleX;
                this.HelmetDiamond.rotateAngleY = this.Head.rotateAngleY;
                break;
            case 5:
                this.HelmetHide.rotationPointY = this.Head.rotationPointY;
                this.HelmetHide.rotateAngleX = this.Head.rotateAngleX;
                this.HelmetHide.rotateAngleY = this.Head.rotateAngleY;
                this.HelmetNeckHide.rotationPointY = this.Head.rotationPointY;
                this.HelmetNeckHide.rotateAngleX = this.Head.rotateAngleX;
                this.HelmetNeckHide.rotateAngleY = this.Head.rotateAngleY;
                this.HelmetHideEar1.rotationPointY = this.Head.rotationPointY;
                this.HelmetHideEar1.rotateAngleX = this.Head.rotateAngleX;
                this.HelmetHideEar1.rotateAngleY = this.Head.rotateAngleY;
                this.HelmetHideEar2.rotationPointY = this.Head.rotationPointY;
                this.HelmetHideEar2.rotateAngleX = this.Head.rotateAngleX;
                this.HelmetHideEar2.rotateAngleY = this.Head.rotateAngleY;
                break;
            case 6:
                this.HelmetFur.rotationPointY = this.Head.rotationPointY;
                this.HelmetFur.rotateAngleX = this.Head.rotateAngleX;
                this.HelmetFur.rotateAngleY = this.Head.rotateAngleY;
                this.HelmetNeckFur.rotationPointY = this.Head.rotationPointY;
                this.HelmetNeckFur.rotateAngleX = this.Head.rotateAngleX;
                this.HelmetNeckFur.rotateAngleY = this.Head.rotateAngleY;
                this.HelmetFurEar1.rotationPointY = this.Head.rotationPointY;
                this.HelmetFurEar1.rotateAngleX = this.Head.rotateAngleX;
                this.HelmetFurEar1.rotateAngleY = this.Head.rotateAngleY;
                this.HelmetFurEar2.rotationPointY = this.Head.rotationPointY;
                this.HelmetFurEar2.rotateAngleX = this.Head.rotateAngleX;
                this.HelmetFurEar2.rotateAngleY = this.Head.rotateAngleY;
                break;
            case 7:
                this.HelmetReptile.rotationPointY = this.Head.rotationPointY;
                this.HelmetReptile.rotateAngleX = this.Head.rotateAngleX;
                this.HelmetReptile.rotateAngleY = this.Head.rotateAngleY;
                this.HelmetReptileEar1.rotationPointY = this.Head.rotationPointY;
                this.HelmetReptileEar1.rotateAngleX = this.Head.rotateAngleX;
                this.HelmetReptileEar1.rotateAngleY = (-35F / this.radianF) + this.Head.rotateAngleY;
                this.HelmetReptileEar2.rotationPointY = this.Head.rotationPointY;
                this.HelmetReptileEar2.rotateAngleX = this.Head.rotateAngleX;
                this.HelmetReptileEar2.rotateAngleY = (35F / this.radianF) + this.Head.rotateAngleY;
                break;
            case 8:
                this.HelmetGreenChitin.rotationPointY = this.Head.rotationPointY;
                this.HelmetGreenChitin.rotateAngleX = this.Head.rotateAngleX;
                this.HelmetGreenChitin.rotateAngleY = this.Head.rotateAngleY;
                break;
            case 9:
                this.HelmetYellowChitin.rotationPointY = this.Head.rotationPointY;
                this.HelmetYellowChitin.rotateAngleX = this.Head.rotateAngleX;
                this.HelmetYellowChitin.rotateAngleY = this.Head.rotateAngleY;
                break;
            case 10:
                this.HelmetBlueChitin.rotationPointY = this.Head.rotationPointY;
                this.HelmetBlueChitin.rotateAngleX = this.Head.rotateAngleX;
                this.HelmetBlueChitin.rotateAngleY = this.Head.rotateAngleY;
                break;
            case 11:
                this.HelmetBlackChitin.rotationPointY = this.Head.rotationPointY;
                this.HelmetBlackChitin.rotateAngleX = this.Head.rotateAngleX;
                this.HelmetBlackChitin.rotateAngleY = this.Head.rotateAngleY;
                break;
            case 12:
                this.HelmetRedChitin.rotationPointY = this.Head.rotationPointY;
                this.HelmetRedChitin.rotateAngleX = this.Head.rotateAngleX;
                this.HelmetRedChitin.rotateAngleY = this.Head.rotateAngleY;
                break;

        }

        //flag
        float flagF = MathHelper.cos(f * 0.8F) * 0.1F * f1;

        switch (this.flagColor) {
            case 1:
                this.FlagOrange.rotateAngleY = flagF;
                break;
            case 2:
                this.FlagPurple.rotateAngleY = flagF;
                break;
            case 3:
                this.FlagLightBlue.rotateAngleY = flagF;
                break;
            case 4:
                this.FlagYellow.rotateAngleY = flagF;
                break;
            case 5:
                this.FlagGreen.rotateAngleY = flagF;
                break;
            case 6:
                this.FlagLightRed.rotateAngleY = flagF;
                break;
            case 7:
                this.FlagDarkGrey.rotateAngleY = flagF;
                break;
            case 8:
                this.FlagGrey.rotateAngleY = flagF;
                break;
            case 9:
                this.FlagCyan.rotateAngleY = flagF;
                break;
            case 10:
                this.FlagDarkPurple.rotateAngleY = flagF;
                break;
            case 11:
                this.FlagDarkBlue.rotateAngleY = flagF;
                break;
            case 12:
                this.FlagBrown.rotateAngleY = flagF;
                break;
            case 13:
                this.FlagDarkGreen.rotateAngleY = flagF;
                break;
            case 14:
                this.FlagRed.rotateAngleY = flagF;
                break;
            case 15:
                this.FlagBlack.rotateAngleY = flagF;
                break;
            case 16:
                this.FlagWhite.rotateAngleY = flagF;
                break;
        }

        //legs

        if ((this.typeI == 5 || this.typeI == 6) && floating) {
            this.LLegC.rotationPointY = 8F;
            this.LLegC.rotationPointZ = 17F;
            this.RLegC.rotationPointY = 8F;
            this.RLegC.rotationPointZ = 17F;
            this.LFoot.rotationPointY = -5F;
            this.LFoot.rotationPointZ = -3F;
            this.RFoot.rotationPointY = -5F;
            this.RFoot.rotationPointZ = -3F;

            this.LLegA.rotateAngleX = 40F / this.radianF;
            this.LLegB.rotateAngleX = this.LLegA.rotateAngleX;
            this.LLegC.rotateAngleX = -85F / this.radianF;
            this.LFoot.rotateAngleX = 25F / this.radianF;

            this.RLegA.rotateAngleX = 40F / this.radianF;
            this.RLegB.rotateAngleX = this.RLegA.rotateAngleX;
            this.RLegC.rotateAngleX = -85F / this.radianF;
            this.RFoot.rotateAngleX = 25F / this.radianF;
        } else {

            this.LLegC.rotationPointY = 5F;
            this.LLegC.rotationPointZ = 4F;
            this.RLegC.rotationPointY = 5F;
            this.RLegC.rotationPointZ = 4F;
            this.LFoot.rotationPointY = 5F;
            this.LFoot.rotationPointZ = 4F;
            this.RFoot.rotationPointY = 5F;
            this.RFoot.rotationPointZ = 4F;

            this.LLegA.rotateAngleX = 0.1745329F + LLegXRot;
            this.LLegB.rotateAngleX = this.LLegA.rotateAngleX;
            this.LLegC.rotateAngleX = -0.2617994F + +LLegXRot;
            this.LFoot.rotateAngleX = this.LLegA.rotateAngleX;
            this.RLegA.rotateAngleX = 0.1745329F + RLegXRot;
            this.RLegB.rotateAngleX = this.RLegA.rotateAngleX;
            this.RLegC.rotateAngleX = -0.2617994F + +RLegXRot;
            this.RFoot.rotateAngleX = this.RLegA.rotateAngleX;
        }

        //wings
        float wingF = 0F;
        /**
         * f = distance walked f1 = speed 0 - 1 f2 = timer
         */
        if (this.typeI == 5 || this.typeI == 6) {

            if (jumpCounter != 0) {
                wingF = (-40F / this.radianF) + MathHelper.cos(jumpCounter * 0.3F) * 1.3F;
            } else if (rider && floating) {
                wingF = MathHelper.cos(f2 * 0.8F) * 0.2F;
            } else {
                wingF = MathHelper.cos(f * 0.3F) * f1;
            }

            this.LWingD.rotateAngleZ = (-20F / this.radianF) - wingF;
            this.LWingE.rotateAngleZ = (-20F / this.radianF) - wingF;

            this.RWingD.rotateAngleZ = (20F / this.radianF) + wingF;
            this.RWingE.rotateAngleZ = (20F / this.radianF) + wingF;

        } else {
            wingF = (10F / this.radianF) + MathHelper.cos(f * 0.6F) * 0.2F * f1;
            if (wing) {
                wingF += (50 / 57.29578F);
            }
            this.LWingB.rotateAngleY = 0.0872665F + wingF;
            this.LWingC.rotateAngleY = 0.0872665F + wingF;

            this.RWingB.rotateAngleY = -0.0872665F - wingF;
            this.RWingC.rotateAngleY = -0.0872665F - wingF;

            this.LWingB.rotateAngleX = 0.0872665F + (RLegXRot / 10F);
            this.LWingC.rotateAngleX = (RLegXRot / 10F);

            this.RWingB.rotateAngleX = 0.0872665F + (RLegXRot / 10F);
            this.RWingC.rotateAngleX = (RLegXRot / 10F);

        }

        if (rider) {
            this.SaddleL.rotateAngleX = -60F / this.radianF;
            this.SaddleL2.rotateAngleX = this.SaddleL.rotateAngleX;
            this.SaddleR.rotateAngleX = -60F / this.radianF;
            ;
            this.SaddleR2.rotateAngleX = this.SaddleR.rotateAngleX;
            ;

            this.SaddleL.rotateAngleZ = -40F / this.radianF;
            this.SaddleL2.rotateAngleZ = this.SaddleL.rotateAngleZ;
            this.SaddleR.rotateAngleZ = 40F / this.radianF;
            this.SaddleR2.rotateAngleZ = this.SaddleR.rotateAngleZ;
        } else {
            this.SaddleL.rotateAngleX = RLegXRot / 3F;
            this.SaddleL2.rotateAngleX = RLegXRot / 3F;
            this.SaddleR.rotateAngleX = RLegXRot / 3F;
            this.SaddleR2.rotateAngleX = RLegXRot / 3F;

            this.SaddleL.rotateAngleZ = RLegXRot / 5F;
            this.SaddleL2.rotateAngleZ = RLegXRot / 5F;
            this.SaddleR.rotateAngleZ = -RLegXRot / 5F;
            this.SaddleR2.rotateAngleZ = -RLegXRot / 5F;
        }

        if (this.typeI == 6) {
            float f6 = 15F;
            float rotF = MathHelper.cos(f * 0.5F) * 0.3F * f1;
            this.Tail.rotateAngleY = rotF;
            rotF += (rotF / f6);
            this.Tailpart1.rotateAngleY = rotF;//MathHelper.cos(f * 0.6662F) * 0.7F * f1;
            rotF += (rotF / f6);
            this.Tailpart1.rotateAngleY = rotF;
            rotF += (rotF / f6);
            this.Tailpart2.rotateAngleY = rotF;
            rotF += (rotF / f6);
            this.Tailpart3.rotateAngleY = rotF;
            rotF += (rotF / f6);
            this.Tailpart4.rotateAngleY = rotF;
            rotF += (rotF / f6);
            this.Tailpart5.rotateAngleY = rotF;
        }

    }

    ModelRenderer UBeak;
    ModelRenderer UBeak2;
    ModelRenderer UBeakb;
    ModelRenderer UBeak2b;
    ModelRenderer LBeak;
    ModelRenderer LBeakb;
    ModelRenderer LBeak2;
    ModelRenderer LBeak2b;
    ModelRenderer Body;
    ModelRenderer Tail;
    ModelRenderer LLegA;
    ModelRenderer LLegB;
    ModelRenderer LLegC;
    ModelRenderer LFoot;
    ModelRenderer RLegA;
    ModelRenderer RLegB;
    ModelRenderer RLegC;
    ModelRenderer RFoot;
    ModelRenderer Tail1;
    ModelRenderer Tail2;
    ModelRenderer Tail3;
    ModelRenderer LWingB;
    ModelRenderer LWingC;
    ModelRenderer LWingD;
    ModelRenderer LWingE;
    ModelRenderer RWingB;
    ModelRenderer RWingC;
    ModelRenderer RWingD;
    ModelRenderer RWingE;
    ModelRenderer SaddleA;
    ModelRenderer SaddleB;
    ModelRenderer SaddleL;
    ModelRenderer SaddleR;
    ModelRenderer SaddleL2;
    ModelRenderer SaddleR2;
    ModelRenderer SaddleC;
    ModelRenderer NeckLFeather;
    ModelRenderer NeckUFeather;
    ModelRenderer NeckD;
    ModelRenderer Saddlebag;
    ModelRenderer Flagpole;
    ModelRenderer FlagBlack;
    ModelRenderer FlagDarkGrey;
    ModelRenderer FlagYellow;
    ModelRenderer FlagBrown;
    ModelRenderer FlagGreen;
    ModelRenderer FlagCyan;
    ModelRenderer FlagLightBlue;
    ModelRenderer FlagDarkBlue;
    ModelRenderer FlagPurple;
    ModelRenderer FlagDarkPurple;
    ModelRenderer FlagDarkGreen;
    ModelRenderer FlagLightRed;
    ModelRenderer FlagRed;
    ModelRenderer FlagWhite;
    ModelRenderer FlagGrey;
    ModelRenderer FlagOrange;
    ModelRenderer NeckU;
    ModelRenderer NeckL;
    ModelRenderer NeckHarness;
    ModelRenderer NeckHarness2;
    ModelRenderer NeckHarnessRight;
    ModelRenderer NeckHarnessLeft;
    ModelRenderer Head;
    ModelRenderer UniHorn;
    ModelRenderer HelmetLeather;
    ModelRenderer HelmetIron;
    ModelRenderer HelmetGold;
    ModelRenderer HelmetDiamond;
    ModelRenderer HelmetHide;
    ModelRenderer HelmetNeckHide;
    ModelRenderer HelmetHideEar1;
    ModelRenderer HelmetHideEar2;
    ModelRenderer HelmetFur;
    ModelRenderer HelmetNeckFur;
    ModelRenderer HelmetFurEar1;
    ModelRenderer HelmetFurEar2;
    ModelRenderer HelmetReptile;
    ModelRenderer HelmetReptileEar2;
    ModelRenderer HelmetReptileEar1;
    ModelRenderer HelmetGreenChitin;
    ModelRenderer HelmetYellowChitin;
    ModelRenderer HelmetBlueChitin;
    ModelRenderer HelmetBlackChitin;
    ModelRenderer HelmetRedChitin;
    ModelRenderer Tailpart1;
    ModelRenderer Tailpart2;
    ModelRenderer Tailpart3;
    ModelRenderer Tailpart4;
    ModelRenderer Tailpart5;

    private byte helmet;
    private byte typeI;
    private byte flagColor;
    private float radianF = 57.29578F;
}
