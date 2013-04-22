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

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.entity.passive.MoCEntityOstrich;

@SideOnly(Side.CLIENT)
public class MoCModelOstrich extends ModelBase {

    public MoCModelOstrich()
    {
        textureWidth = 128;
        textureHeight = 128; //64

        

        
        UBeak = new ModelRenderer(this, 12, 16);
          UBeak.addBox(-1.5F, -15F, -5.5F, 3, 1, 1);
          UBeak.setRotationPoint(0F, 3F, -6F);
          
          UBeak2 = new ModelRenderer(this, 20, 16);
          UBeak2.addBox(-1F, -15F, -7.5F, 2, 1, 2);
          UBeak2.setRotationPoint(0F, 3F, -6F);

          UBeakb = new ModelRenderer(this, 12, 16);
          UBeakb.addBox(-1.5F, -15F, -6.5F, 3, 1, 1);
          UBeakb.setRotationPoint(0F, 3F, -6F);
          setRotation(UBeakb, -0.0698132F, 0F, 0F);
          
          UBeak2b = new ModelRenderer(this, 20, 16);
          UBeak2b.addBox(-1F, -15F, -8.5F, 2, 1, 2);
          UBeak2b.setRotationPoint(0F, 3F, -6F);
          setRotation(UBeak2b, -0.0698132F, 0F, 0F);
          
          LBeak = new ModelRenderer(this, 12, 22);
          LBeak.addBox(-1.5F, -14F, -5.5F, 3, 1, 1);
          LBeak.setRotationPoint(0F, 3F, -6F);

          LBeakb = new ModelRenderer(this, 12, 22);
          LBeakb.addBox(-1.5F, -14F, -3.9F, 3, 1, 1);
          LBeakb.setRotationPoint(0F, 3F, -6F);
          setRotation(LBeakb, 0.122173F, 0F, 0F);
          
          LBeak2 = new ModelRenderer(this, 20, 22);
          LBeak2.addBox(-1F, -14F, -7.5F, 2, 1, 2);
          LBeak2.setRotationPoint(0F, 3F, -6F);
          
          LBeak2b = new ModelRenderer(this, 20, 22);
          LBeak2b.addBox(-1F, -14F, -5.9F, 2, 1, 2);
          LBeak2b.setRotationPoint(0F, 3F, -6F);
          setRotation(LBeak2b, 0.122173F, 0F, 0F);
          
          Body = new ModelRenderer(this, 0, 38);
          Body.addBox(-4F, 1F, 0F, 8, 10, 16);
          Body.setRotationPoint(0F, 0F, -6F);
          
          LLegA = new ModelRenderer(this, 50, 28);
          LLegA.addBox(-2F, -1F, -2.5F, 4, 6, 5);
          LLegA.setRotationPoint(4F, 5F, 4F);
          setRotation(LLegA, 0.1745329F, 0F, 0F);
          
          LLegB = new ModelRenderer(this, 50, 39);
          LLegB.addBox(-1.5F, 5F, -1.5F, 3, 4, 3);
          LLegB.setRotationPoint(4F, 5F, 4F);
          setRotation(LLegB, 0.1745329F, 0F, 0F);
          
          LLegC = new ModelRenderer(this, 8, 38);
          LLegC.addBox(-1F, 8F, 2.5F, 2, 10, 2);
          LLegC.setRotationPoint(4F, 5F, 4F);
          setRotation(LLegC, -0.2617994F, 0F, 0F);
          
          LFoot = new ModelRenderer(this, 32, 42);
          LFoot.addBox(-1F, 17F, -9F, 2, 1, 5);
          LFoot.setRotationPoint(4F, 5F, 4F);
          setRotation(LFoot, 0.1745329F, 0F, 0F);
          
          RLegA = new ModelRenderer(this, 0, 27);
          RLegA.addBox(-2F, -1F, -2.5F, 4, 6, 5);
          RLegA.setRotationPoint(-4F, 5F, 4F);
          setRotation(RLegA, 0.1745329F, 0F, 0F);
          
          RLegB = new ModelRenderer(this, 18, 27);
          RLegB.addBox(-1.5F, 5F, -1.5F, 3, 4, 3);
          RLegB.setRotationPoint(-4F, 5F, 4F);
          setRotation(RLegB, 0.1745329F, 0F, 0F);
          
          RLegC = new ModelRenderer(this, 0, 38);
          RLegC.addBox(-1F, 8F, 2.5F, 2, 10, 2);
          RLegC.setRotationPoint(-4F, 5F, 4F);
          setRotation(RLegC, -0.2617994F, 0F, 0F);
          
          RFoot = new ModelRenderer(this, 32, 48);
          RFoot.addBox(-1F, 17F, -9F, 2, 1, 5);
          RFoot.setRotationPoint(-4F, 5F, 4F);
          setRotation(RFoot, 0.1745329F, 0F, 0F);
          
          Tail1 = new ModelRenderer(this, 44, 18);
          Tail1.addBox(-0.5F, -2F, -2F, 1, 4, 6);
          Tail1.setRotationPoint(0F, 4F, 15F);
          setRotation(Tail1, 0.3490659F, 0F, 0F);
          
          Tail2 = new ModelRenderer(this, 58, 18);
          Tail2.addBox(-2.6F, -2F, -2F, 1, 4, 6);
          Tail2.setRotationPoint(0F, 4F, 15F);
          setRotation(Tail2, 0.3490659F, -0.2617994F, 0F);
          
          Tail3 = new ModelRenderer(this, 30, 18);
          Tail3.addBox(1.6F, -2F, -2F, 1, 4, 6);
          Tail3.setRotationPoint(0F, 4F, 15F);
          setRotation(Tail3, 0.3490659F, 0.2617994F, 0F);
          
          LWingB = new ModelRenderer(this, 68, 46);
          LWingB.addBox(-0.5F, -3F, 0F, 1, 4, 14);
          LWingB.setRotationPoint(4F, 4F, -3F);
          setRotation(LWingB, 0.0872665F, 0.0872665F, 0F);
          
          LWingC = new ModelRenderer(this, 98, 46);
          LWingC.addBox(-1F, 0F, 0F, 1, 4, 14);
          LWingC.setRotationPoint(4F, 4F, -3F);
          setRotation(LWingC, 0F, 0.0872665F, 0F);
          
          LWingD = new ModelRenderer(this, 26, 84);
          LWingD.addBox(0F, -1F, -1F, 15, 2, 2);
          LWingD.setRotationPoint(4F, 3F, -3F);
          setRotation(LWingD, 0F, 0F, -0.3490659F);
          
          LWingE = new ModelRenderer(this, 0, 103);
          LWingE.addBox(0F, 0F, 1F, 15, 0, 15);
          LWingE.setRotationPoint(4F, 3F, -3F);
          setRotation(LWingE, 0F, 0F, -0.3490659F);
          
          RWingB = new ModelRenderer(this, 68, 0);
          RWingB.addBox(-0.5F, -3F, 0F, 1, 4, 14);
          RWingB.setRotationPoint(-4F, 4F, -3F);
          setRotation(RWingB, 0.0872665F, -0.0872665F, 0F);
          
          RWingC = new ModelRenderer(this, 98, 0);
          RWingC.addBox(0F, 0F, 0F, 1, 4, 14);
          RWingC.setRotationPoint(-4F, 4F, -3F);
          setRotation(RWingC, 0F, -0.0872665F, 0F);
          
          RWingD = new ModelRenderer(this, 26, 80);
          RWingD.addBox(-15F, -1F, -1F, 15, 2, 2);
          RWingD.setRotationPoint(-4F, 3F, -3F);
          setRotation(RWingD, 0F, 0F, 0.3490659F);
          
          RWingE = new ModelRenderer(this, 0, 88);
          RWingE.addBox(-15F, 0F, 1F, 15, 0, 15);
          RWingE.setRotationPoint(-4F, 3F, -3F);
          setRotation(RWingE, 0F, 0F, 0.3490659F);
          
          SaddleA = new ModelRenderer(this, 72, 18);
          SaddleA.addBox(-4F, 0.5F, -3F, 8, 1, 8);
          SaddleA.setRotationPoint(0F, 0F, 0F);
          SaddleB = new ModelRenderer(this, 72, 27);
          
          SaddleB.addBox(-1.5F, 0F, -3F, 3, 1, 2);
          SaddleB.setRotationPoint(0F, 0F, 0F);
          
          SaddleL = new ModelRenderer(this, 72, 30);
          SaddleL.addBox(-0.5F, 0F, -0.5F, 1, 6, 1);
          SaddleL.setRotationPoint(4F, 1F, 0F);
          
          SaddleR = new ModelRenderer(this, 84, 30);
          SaddleR.addBox(-0.5F, 0F, -0.5F, 1, 6, 1);
          SaddleR.setRotationPoint(-4F, 1F, 0F);
          
          SaddleL2 = new ModelRenderer(this, 76, 30);
          SaddleL2.addBox(-0.5F, 6F, -1F, 1, 2, 2);
          SaddleL2.setRotationPoint(4F, 1F, 0F);
          
          SaddleR2 = new ModelRenderer(this, 88, 30);
          SaddleR2.addBox(-0.5F, 6F, -1F, 1, 2, 2);
          SaddleR2.setRotationPoint(-4F, 1F, 0F);
          
          SaddleC = new ModelRenderer(this, 84, 27);
          SaddleC.addBox(-4F, 0F, 3F, 8, 1, 2);
          SaddleC.setRotationPoint(0F, 0F, 0F);
          
          NeckLFeather = new ModelRenderer(this, 8, 73);
          NeckLFeather.addBox(0F, -8F, -0.5F, 0, 7, 4);
          NeckLFeather.setRotationPoint(0F, 3F, -6F);
          setRotation(NeckLFeather, 0.2007129F, 0F, 0F);
          
          NeckUFeather = new ModelRenderer(this, 0, 73);
          NeckUFeather.addBox(0F, -16F, -2F, 0, 9, 4);
          NeckUFeather.setRotationPoint(0F, 3F, -6F);
          
          NeckD = new ModelRenderer(this, 0, 16);
          NeckD.addBox(-1.5F, -4F, -2F, 3, 8, 3);
          NeckD.setRotationPoint(0F, 3F, -6F);
          setRotation(NeckD, 0.4363323F, 0F, 0F);
          
          Saddlebag = new ModelRenderer(this, 32, 7);
          Saddlebag.addBox(-4.5F, -3F, 5F, 9, 4, 7);
          Saddlebag.setRotationPoint(0F, 0F, 0F);
          setRotation(Saddlebag, -0.2602503F, 0F, 0F);
          
          Flagpole = new ModelRenderer(this, 28, 0);
          Flagpole.addBox(-0.5F, -15F, -0.5F, 1, 17, 1);
          Flagpole.setRotationPoint(0F, 0F, 5F);
          setRotation(Flagpole, -0.2602503F, 0F, 0F);
          
          FlagBlack = new ModelRenderer(this, 108, 8);
          FlagBlack.addBox(0F, -2.1F, 0F, 0, 4, 10);
          FlagBlack.setRotationPoint(0F, -12F, 8F);
          setRotation(FlagBlack, -0.2602503F, 0F, 0F);
          
          FlagDarkGrey = new ModelRenderer(this, 108, 12);
          FlagDarkGrey.addBox(0F, -2.1F, 0F, 0, 4, 10);
          FlagDarkGrey.setRotationPoint(0F, -12F, 8F);
          setRotation(FlagDarkGrey, -0.2602503F, 0F, 0F);
          
          FlagYellow = new ModelRenderer(this, 48, 46);
          FlagYellow.addBox(0F, -2.1F, 0F, 0, 4, 10);
          FlagYellow.setRotationPoint(0F, -12F, 8F);
          setRotation(FlagYellow, -0.2602503F, 0F, 0F);
          
          FlagBrown = new ModelRenderer(this, 48, 42);
          FlagBrown.addBox(0F, -2.1F, 0F, 0, 4, 10);
          FlagBrown.setRotationPoint(0F, -12F, 8F);
          setRotation(FlagBrown, -0.2602503F, 0F, 0F);
          
          FlagGreen = new ModelRenderer(this, 48, 38);
          FlagGreen.addBox(0F, -2.1F, 0F, 0, 4, 10);
          FlagGreen.setRotationPoint(0F, -12F, 8F);
          setRotation(FlagGreen, -0.2602503F, 0F, 0F);
          
          /*FlagCyan = new ModelRenderer(this, 48, 50);
          FlagCyan.addBox(0F, -16.5F, 5F, 0, 4, 10);
          FlagCyan.setRotationPoint(0F, 0F, 0F);
          setRotation(FlagCyan, -0.2602503F, 0F, 0F);*/
          
          FlagCyan = new ModelRenderer(this, 48, 50);
          FlagCyan.addBox(0F, -2.1F, 0F, 0, 4, 10);
          FlagCyan.setRotationPoint(0F, -12F, 8F);
          setRotation(FlagCyan, -0.2602503F, 0F, 0F);
          
          FlagLightBlue = new ModelRenderer(this, 68, 32);
          FlagLightBlue.addBox(0F, -2.1F, 0F, 0, 4, 10);
          FlagLightBlue.setRotationPoint(0F, -12F, 8F);
          setRotation(FlagLightBlue, -0.2602503F, 0F, 0F);
          
          FlagDarkBlue = new ModelRenderer(this, 68, 28);
          FlagDarkBlue.addBox(0F, -2.1F, 0F, 0, 4, 10);
          FlagDarkBlue.setRotationPoint(0F, -12F, 8F);
          setRotation(FlagDarkBlue, -0.2602503F, 0F, 0F);
          
          FlagPurple = new ModelRenderer(this, 88, 32);
          FlagPurple.addBox(0F, -2.1F, 0F, 0, 4, 10);
          FlagPurple.setRotationPoint(0F, -12F, 8F);
          setRotation(FlagPurple, -0.2602503F, 0F, 0F);
          
          FlagDarkPurple = new ModelRenderer(this, 88, 28);
          FlagDarkPurple.addBox(0F, -2.1F, 0F, 0, 4, 10);
          FlagDarkPurple.setRotationPoint(0F, -12F, 8F);
          setRotation(FlagDarkPurple, -0.2602503F, 0F, 0F);
          
          FlagDarkGreen = new ModelRenderer(this, 108, 32);
          FlagDarkGreen.addBox(0F, -2.1F, 0F, 0, 4, 10);
          FlagDarkGreen.setRotationPoint(0F, -12F, 8F);
          setRotation(FlagDarkGreen, -0.2602503F, 0F, 0F);
          
          FlagLightRed = new ModelRenderer(this, 108, 28);
          FlagLightRed.addBox(0F, -2.1F, 0F, 0, 4, 10);
          FlagLightRed.setRotationPoint(0F, -12F, 8F);
          setRotation(FlagLightRed, -0.2602503F, 0F, 0F);
          
          FlagRed = new ModelRenderer(this, 108, 24);
          FlagRed.addBox(0F, -2.1F, 0F, 0, 4, 10);
          FlagRed.setRotationPoint(0F, -12F, 8F);
          setRotation(FlagRed, -0.2602503F, 0F, 0F);
          
          FlagWhite = new ModelRenderer(this, 108, 20);
          FlagWhite.addBox(0F, -2.1F, 0F, 0, 4, 10);
          FlagWhite.setRotationPoint(0F, -12F, 8F);
          setRotation(FlagWhite, -0.2602503F, 0F, 0F);
          
          FlagGrey = new ModelRenderer(this, 108, 16);
          FlagGrey.addBox(0F, -2.1F, 0F, 0, 4, 10);
          FlagGrey.setRotationPoint(0F, -12F, 8F);
          setRotation(FlagGrey, -0.2602503F, 0F, 0F);
          
          FlagOrange = new ModelRenderer(this, 88, 24);
          FlagOrange.addBox(0F, -2.1F, 0F, 0, 4, 10);
          FlagOrange.setRotationPoint(0F, -12F, 8F);
          setRotation(FlagOrange, -0.2602503F, 0F, 0F);
          
          NeckU = new ModelRenderer(this, 20, 0);
          NeckU.addBox(-1F, -12F, -4F, 2, 5, 2);
          NeckU.setRotationPoint(0F, 3F, -6F);
          
          NeckL = new ModelRenderer(this, 20, 7);
          NeckL.addBox(-1F, -8F, -2.5F, 2, 5, 2);
          NeckL.setRotationPoint(0F, 3F, -6F);
          setRotation(NeckL, 0.2007129F, 0F, 0F);
          
          NeckHarness = new ModelRenderer(this, 0, 11);
          NeckHarness.addBox(-2F, -3F, -2.5F, 4, 1, 4);
          NeckHarness.setRotationPoint(0F, 3F, -6F);
          setRotation(NeckHarness, 0.4363323F, 0F, 0F);
          
          NeckHarness2 = new ModelRenderer(this, 84, 55);
          NeckHarness2.addBox(-3F, -2.5F, -2F, 6, 1, 1);
          NeckHarness2.setRotationPoint(0F, 3F, -6F);
          
          NeckHarnessRight = new ModelRenderer(this, 84, 45);
          NeckHarnessRight.addBox(-2.3F, -3.5F, -0.5F, 0, 3, 12);
          NeckHarnessRight.setRotationPoint(0F, 3F, -6F);
          setRotation(NeckHarnessRight, 0.8983798F, 0F, 0F);
          
          NeckHarnessLeft = new ModelRenderer(this, 84, 45);
          NeckHarnessLeft.addBox(2.3F, -3.5F, -0.5F, 0, 3, 12);
          NeckHarnessLeft.setRotationPoint(0F, 3F, -6F);
          setRotation(NeckHarnessLeft, 0.8983798F, 0F, 0F);
          
          Head = new ModelRenderer(this, 0, 0);
          Head.addBox(-1.5F, -16F, -4.5F, 3, 4, 3);
          Head.setRotationPoint(0F, 3F, -6F);
          
          UniHorn = new ModelRenderer(this, 0, 8);
          UniHorn.addBox(-0.5F, -21F, 0.5F, 1, 6, 1);
          UniHorn.setRotationPoint(0F, 3F, -6F);
          setRotation(UniHorn, 0.3171542F, 0F, 0F);
          
          HelmetLeather = new ModelRenderer(this, 66, 0);
          HelmetLeather.addBox(-2F, -16.5F, -5F, 4, 5, 4);
          HelmetLeather.setRotationPoint(0F, 3F, -6F);
          
          HelmetIron = new ModelRenderer(this, 84, 46);
          HelmetIron.addBox(-2F, -16.5F, -5F, 4, 5, 4);
          HelmetIron.setRotationPoint(0F, 3F, -6F);
          
          HelmetGold = new ModelRenderer(this, 112, 64);
          HelmetGold.addBox(-2F, -16.5F, -5F, 4, 5, 4);
          HelmetGold.setRotationPoint(0F, 3F, -6F);
          
          HelmetDiamond = new ModelRenderer(this, 96, 64);
          HelmetDiamond.addBox(-2F, -16.5F, -5F, 4, 5, 4);
          HelmetDiamond.setRotationPoint(0F, 3F, -6F);
          
          HelmetHide = new ModelRenderer(this, 96, 5);
          HelmetHide.addBox(-2F, -16.5F, -5F, 4, 5, 4);
          HelmetHide.setRotationPoint(0F, 3F, -6F);
          
          HelmetNeckHide = new ModelRenderer(this, 58, 0);
          HelmetNeckHide.addBox(-1.5F, -12F, -4.5F, 3, 1, 3);
          HelmetNeckHide.setRotationPoint(0F, 3F, -6F);
          
          HelmetHideEar1 = new ModelRenderer(this, 84, 9);
          HelmetHideEar1.addBox(-2.5F, -18F, -3F, 2, 2, 1);
          HelmetHideEar1.setRotationPoint(0F, 3F, -6F);
          //setRotation(HelmetHideEar1, 0F, 0F, 0.4363323F);
          
          HelmetHideEar2 = new ModelRenderer(this, 90, 9);
          HelmetHideEar2.addBox(0.5F, -18F, -3F, 2, 2, 1);
          HelmetHideEar2.setRotationPoint(0F, 3F, -6F);
          //setRotation(HelmetHideEar2, 0F, 0F, -0.4363323F);
          
          HelmetFur = new ModelRenderer(this, 84, 0);
          HelmetFur.addBox(-2F, -16.5F, -5F, 4, 5, 4);
          HelmetFur.setRotationPoint(0F, 3F, -6F);
          
          HelmetNeckFur = new ModelRenderer(this, 96, 0);
          HelmetNeckFur.addBox(-1.5F, -12F, -4.5F, 3, 1, 3);
          HelmetNeckFur.setRotationPoint(0F, 3F, -6F);
          
          HelmetFurEar1 = new ModelRenderer(this, 66, 9);
          HelmetFurEar1.addBox(-2.5F, -18F, -3F, 2, 2, 1);
          HelmetFurEar1.setRotationPoint(0F, 3F, -6F);
          
          HelmetFurEar2 = new ModelRenderer(this, 76, 9);
          HelmetFurEar2.addBox(0.5F, -18F, -3F, 2, 2, 1);
          HelmetFurEar2.setRotationPoint(0F, 3F, -6F);
          
          HelmetReptile = new ModelRenderer(this, 64, 64);
          HelmetReptile.addBox(-2F, -16.5F, -5F, 4, 5, 4);
          HelmetReptile.setRotationPoint(0F, 3F, -6F);
          
          HelmetReptileEar2 = new ModelRenderer(this, 114, 45);
          HelmetReptileEar2.addBox(2.5F, -16.5F, -2F, 0, 5, 5);
          HelmetReptileEar2.setRotationPoint(0F, 3F, -6F);
          setRotation(HelmetReptileEar2, 0F, 0.6108652F, 0F);
          
          HelmetReptileEar1 = new ModelRenderer(this, 114, 50);
          HelmetReptileEar1.addBox(-2.5F, -16.5F, -2F, 0, 5, 5);
          HelmetReptileEar1.setRotationPoint(0F, 3F, -6F);
          setRotation(HelmetReptileEar1, 0F, -0.6108652F, 0F);
          
          HelmetGreenChitin = new ModelRenderer(this, 80, 64);
          HelmetGreenChitin.addBox(-2F, -16.5F, -5F, 4, 5, 4);
          HelmetGreenChitin.setRotationPoint(0F, 3F, -6F);
          
          HelmetYellowChitin = new ModelRenderer(this, 0, 64);
          HelmetYellowChitin.addBox(-2F, -16.5F, -5F, 4, 5, 4);
          HelmetYellowChitin.setRotationPoint(0F, 3F, -6F);
          
          HelmetBlueChitin = new ModelRenderer(this, 16, 64);
          HelmetBlueChitin.addBox(-2F, -16.5F, -5F, 4, 5, 4);
          HelmetBlueChitin.setRotationPoint(0F, 3F, -6F);
          
          HelmetBlackChitin = new ModelRenderer(this, 32, 64);
          HelmetBlackChitin.addBox(-2F, -16.5F, -5F, 4, 5, 4);
          HelmetBlackChitin.setRotationPoint(0F, 3F, -6F);
          
          HelmetRedChitin = new ModelRenderer(this, 48, 64);
          HelmetRedChitin.addBox(-2F, -16.5F, -5F, 4, 5, 4);
          HelmetRedChitin.setRotationPoint(0F, 3F, -6F);
          
          /*Tail = new ModelRenderer(this, 30, 28);
          Tail.addBox(-2.5F, 3F, 16F, 5, 5, 5);
          Tail.setRotationPoint(0F, 0F, -6F);
          
          Tailpart1 = new ModelRenderer(this, 30, 28);
          Tailpart1.addBox(-2.5F, -3.2F, 21F, 5, 5, 5);
          Tailpart1.setRotationPoint(0F, 0F, -6F);
          setRotation(Tailpart1, -0.2974289F, 0F, 0F);
          
          Tailpart2 = new ModelRenderer(this, 60, 73);
          Tailpart2.addBox(-2.5F, -8.8F, 24.6F, 5, 5, 8);
          Tailpart2.setRotationPoint(0F, 0F, -6F);
          setRotation(Tailpart2, -0.5205006F, 0F, 0F);
          
          Tailpart3 = new ModelRenderer(this, 60, 86);
          Tailpart3.addBox(-2F, 1.5F, 32.6F, 4, 4, 7);
          Tailpart3.setRotationPoint(0F, 0F, -6F);
          setRotation(Tailpart3, -0.2230717F, 0F, 0F);
          
          Tailpart4 = new ModelRenderer(this, 60, 97);
          Tailpart4.addBox(-1.5F, 13F, 36.6F, 3, 3, 7);
          Tailpart4.setRotationPoint(0F, 0F, -6F);
          setRotation(Tailpart4, 0.0743572F, 0F, 0F);
          
          Tailpart5 = new ModelRenderer(this, 60, 107);
          Tailpart5.addBox(-1F, 26.5F, 35.9F, 2, 2, 5);
          Tailpart5.setRotationPoint(0F, 0F, -6F);
          setRotation(Tailpart5, 0.4089647F, 0F, 0F);*/
          
          Tail = new ModelRenderer(this, 30, 28);
          Tail.addBox(-2.5F, -1F, 0F, 5, 5, 5);
          Tail.setRotationPoint(0F, 4F, 10F);

          Tailpart1 = new ModelRenderer(this, 30, 28);
          Tailpart1.addBox(-2.5F, -2.2F, 5F, 5, 5, 5);
          Tailpart1.setRotationPoint(0F, 4F, 10F);
          setRotation(Tailpart1, -0.2974289F, 0F, 0F);
          
          Tailpart2 = new ModelRenderer(this, 60, 73);
          Tailpart2.addBox(-2.5F, -4.3F, 9F, 5, 5, 8);
          Tailpart2.setRotationPoint(0F, 4F, 10F);
          setRotation(Tailpart2, -0.5205006F, 0F, 0F);
          
          Tailpart3 = new ModelRenderer(this, 60, 86);
          Tailpart3.addBox(-2F, 1F, 16F, 4, 4, 7);
          Tailpart3.setRotationPoint(0F, 4F, 10F);
          setRotation(Tailpart3, -0.2230717F, 0F, 0F);
          
          Tailpart4 = new ModelRenderer(this, 60, 97);
          Tailpart4.addBox(-1.5F, 8F, 20.6F, 3, 3, 7);
          Tailpart4.setRotationPoint(0F, 4F, 10F);
          setRotation(Tailpart4, 0.0743572F, 0F, 0F);
          
          Tailpart5 = new ModelRenderer(this, 60, 107);
          Tailpart5.addBox(-1F, 16.5F, 22.9F, 2, 2, 5);
          Tailpart5.setRotationPoint(0F, 4F, 10F);
          setRotation(Tailpart5, 0.4089647F, 0F, 0F);
    }

    /**
     * Used for easily adding entity-dependent animations. The second and third
     * float params here are the same second and third as in the
     * setRotationAngles method.
     */
    /*public void setLivingAnimations(EntityLiving entityliving, float par2, float par3, float par4)
    {
        super.setLivingAnimations(entityliving, par2, par3, par4);
        this.Head.rotationPointY = 6.0F + ((EntitySheep)entityliving).func_44003_c(par4) * 9.0F;
        this.field_44016_o = ((EntitySheep)entityliving).func_44002_d(par4);
    }*/

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {

        MoCEntityOstrich entityostrich = (MoCEntityOstrich) entity;
        typeI = (byte) entityostrich.getType();
        boolean openMouth = (entityostrich.mouthCounter != 0);
        boolean isSaddled = entityostrich.getIsRideable();
        boolean isHiding = entityostrich.getHiding();
        boolean wingFlap = (entityostrich.wingCounter != 0);
        boolean bagged = entityostrich.getIsChested();
        boolean rider = (entityostrich.riddenByEntity != null);
        int jumpCounter = entityostrich.jumpCounter;
        boolean floating = (entityostrich.isFlyer() && entityostrich.isOnAir());
        
        helmet = entityostrich.getHelmet();
        flagColor = entityostrich.getFlagColor();
        //super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, isHiding, wingFlap, rider, jumpCounter, floating);

        Head.render(f5);

        NeckU.render(f5);
        NeckD.render(f5);
        NeckL.render(f5);
        Body.render(f5);
        Tail.render(f5);
        LLegA.render(f5);
        LLegB.render(f5);
        LLegC.render(f5);
        LFoot.render(f5);
        RLegA.render(f5);
        RLegB.render(f5);
        RLegC.render(f5);
        RFoot.render(f5);
        
        
        
        
        if (typeI == 8)
        {
            UniHorn.render(f5);
        }
        
        if (typeI == 5 || typeI == 6) //demon and darkness ostriches
        {
            LWingD.render(f5);
            LWingE.render(f5);
            RWingD.render(f5);
            RWingE.render(f5);
            NeckUFeather.render(f5);
            NeckLFeather.render(f5);
        }
        else
        {
            LWingB.render(f5);
            LWingC.render(f5);
            RWingB.render(f5);
            RWingC.render(f5);
        }
        
        if (typeI == 6) //darkness ostrich
        {
            
            Tailpart1.render(f5);
            Tailpart2.render(f5);
            Tailpart3.render(f5);
            Tailpart4.render(f5);
            Tailpart5.render(f5);
        }
        else
        {
            Tail1.render(f5);
            Tail2.render(f5);
            Tail3.render(f5);
        }

        if (openMouth)
        {
            UBeakb.render(f5);
            UBeak2b.render(f5);
            LBeakb.render(f5);
            LBeak2b.render(f5);
        }
        else
        {
            UBeak.render(f5);
            UBeak2.render(f5);
            LBeak.render(f5);
            LBeak2.render(f5);
        }

        if (isSaddled)
        {
            SaddleA.render(f5);
            SaddleB.render(f5);
            SaddleC.render(f5);
            SaddleL.render(f5);
            SaddleR.render(f5);
            SaddleL2.render(f5);
            SaddleR2.render(f5);
            NeckHarness.render(f5);
            NeckHarness2.render(f5);
            if (rider)
            {
                NeckHarnessLeft.render(f5);
                NeckHarnessRight.render(f5);
            }
            
            
        }
        
        if (bagged)
        {
            Saddlebag.render(f5);
            Flagpole.render(f5);
            switch (flagColor)
            {
            //case 0:
            //    FlagWhite.render(f5);
            //   break;
            case 1:
                FlagOrange.render(f5);
                break;
            case 2:
                FlagPurple.render(f5);
                break;
            case 3:
                FlagLightBlue.render(f5);
                break;
            case 4:
                FlagYellow.render(f5);
                break;
            case 5:
                FlagGreen.render(f5);
                break;
            case 6:
                FlagLightRed.render(f5);
                break;
            case 7:
                FlagDarkGrey.render(f5);
                break;
            case 8:
                FlagGrey.render(f5);
                break;
            case 9:
                FlagCyan.render(f5);
                break;
            case 10:
                FlagDarkPurple.render(f5);
                break;
            case 11:
                FlagDarkBlue.render(f5);
                break;
            case 12:
                FlagBrown.render(f5);
                break;
            case 13:
                FlagDarkGreen.render(f5);
                break;
            case 14:
                FlagRed.render(f5);
                break;
            case 15:
                FlagBlack.render(f5);
                break;
            case 16:
                FlagWhite.render(f5);
                break;
            }
        }
        
        
        switch (helmet)
        {
        case 1:
            HelmetLeather.render(f5);
            break;
        case 2:
            HelmetIron.render(f5);
            break;
        case 3:
            HelmetGold.render(f5);
            break;
        case 4:
            HelmetDiamond.render(f5);
            break;
        case 5:
            HelmetHide.render(f5);
            HelmetNeckHide.render(f5);
            HelmetHideEar1.render(f5);
            HelmetHideEar2.render(f5);
            break;
        case 6:
            HelmetFur.render(f5);
            HelmetNeckFur.render(f5);
            HelmetFurEar1.render(f5);
            HelmetFurEar2.render(f5);
            break;
        case 7:
            HelmetReptile.render(f5);
            HelmetReptileEar1.render(f5);
            HelmetReptileEar2.render(f5);
            break;
        case 8:
            HelmetGreenChitin.render(f5);
            break;
        case 9:
            HelmetYellowChitin.render(f5);
            break;
        case 10:
            HelmetBlueChitin.render(f5);
            break;
        case 11:
            HelmetBlackChitin.render(f5);
            break;
        case 12:
            HelmetRedChitin.render(f5);
            break;
        
        }
        
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, boolean hiding, boolean wing, boolean rider, int jumpCounter, boolean floating)
    {
        float LLegXRot = MathHelper.cos(f * 0.4F) * 1.1F * f1;
        float RLegXRot = MathHelper.cos((f * 0.4F) + 3.141593F) * 1.1F * f1;
        
        if (hiding)
        {
            Head.rotationPointY = 9.0F;
            Head.rotateAngleX = 2.61799F;
            Head.rotateAngleY = 0.0F;

        }
        else
        {
            Head.rotationPointY = 3.0F;
            Head.rotateAngleX = (RLegXRot / 20F) + (-f4 / (180F / (float) Math.PI));
            Head.rotateAngleY = f3 / (180F / (float) Math.PI);
        }

        if (rider)
        {
            if (floating)
            {
                Head.rotateAngleX = 0F;
            }else
            {
                Head.rotateAngleX = (RLegXRot / 20F);
            }
        }
        
        UBeak.rotationPointY = Head.rotationPointY;
        UBeakb.rotationPointY = Head.rotationPointY;
        UBeak2.rotationPointY = Head.rotationPointY;
        UBeak2b.rotationPointY = Head.rotationPointY;
        LBeak.rotationPointY = Head.rotationPointY;
        LBeakb.rotationPointY = Head.rotationPointY;
        LBeak2.rotationPointY = Head.rotationPointY;
        LBeak2b.rotationPointY = Head.rotationPointY;
        NeckU.rotationPointY = Head.rotationPointY;
        NeckD.rotationPointY = Head.rotationPointY;
        NeckL.rotationPointY = Head.rotationPointY;
        
        UBeak.rotateAngleX = Head.rotateAngleX;
        UBeak.rotateAngleY = Head.rotateAngleY;
        UBeak2.rotateAngleX = Head.rotateAngleX;
        UBeak2.rotateAngleY = Head.rotateAngleY;
        LBeak.rotateAngleX = Head.rotateAngleX;
        LBeak.rotateAngleY = Head.rotateAngleY;
        LBeak2.rotateAngleX = Head.rotateAngleX;
        LBeak2.rotateAngleY = Head.rotateAngleY;
        NeckU.rotateAngleX = Head.rotateAngleX;
        NeckU.rotateAngleY = Head.rotateAngleY;
        NeckD.rotateAngleX = 0.4363323F + Head.rotateAngleX;
        NeckD.rotateAngleY = Head.rotateAngleY;
        NeckL.rotateAngleX = (11.5F/radianF) + Head.rotateAngleX;
        NeckL.rotateAngleY = Head.rotateAngleY;
        
        UBeakb.rotateAngleX = -0.0698132F + Head.rotateAngleX;
        UBeakb.rotateAngleY = Head.rotateAngleY;
        UBeak2b.rotateAngleX = -0.0698132F + Head.rotateAngleX;
        UBeak2b.rotateAngleY = Head.rotateAngleY;

        LBeakb.rotateAngleX = (7F/radianF) + Head.rotateAngleX;
        LBeakb.rotateAngleY = Head.rotateAngleY;
        LBeak2b.rotateAngleX = (7F/radianF) + Head.rotateAngleX;
        LBeak2b.rotateAngleY = Head.rotateAngleY;

        NeckUFeather.rotationPointY = Head.rotationPointY;
        NeckLFeather.rotationPointY = Head.rotationPointY;
        UniHorn.rotationPointY = Head.rotationPointY;
        
        NeckUFeather.rotateAngleX = Head.rotateAngleX;
        NeckUFeather.rotateAngleY = Head.rotateAngleY;
        NeckLFeather.rotateAngleX = (11.5F/ radianF) + Head.rotateAngleX;
        NeckLFeather.rotateAngleY = Head.rotateAngleY;
        UniHorn.rotateAngleX = (18F/ radianF) + Head.rotateAngleX;
        UniHorn.rotateAngleY = Head.rotateAngleY;
        
        NeckHarness.rotationPointY = Head.rotationPointY;
        NeckHarness2.rotationPointY = Head.rotationPointY;
        NeckHarnessLeft.rotationPointY = Head.rotationPointY;
        NeckHarnessRight.rotationPointY = Head.rotationPointY;
        
        NeckHarness.rotateAngleX = (25F/ radianF) + Head.rotateAngleX;
        NeckHarness.rotateAngleY = Head.rotateAngleY;
        NeckHarness2.rotateAngleX = Head.rotateAngleX;
        NeckHarness2.rotateAngleY = Head.rotateAngleY;
        NeckHarnessLeft.rotateAngleX = (50F/ radianF) + Head.rotateAngleX;
        NeckHarnessLeft.rotateAngleY = Head.rotateAngleY;
        NeckHarnessRight.rotateAngleX = (50F/ radianF) + Head.rotateAngleX;
        NeckHarnessRight.rotateAngleY = Head.rotateAngleY;
        
       
        
        //helmets
        
        switch (helmet)
        {
        case 1:
            
            HelmetLeather.rotationPointY = Head.rotationPointY;
            HelmetLeather.rotateAngleX = Head.rotateAngleX;
            HelmetLeather.rotateAngleY = Head.rotateAngleY;
            break;
        case 2:
            
            HelmetIron.rotationPointY = Head.rotationPointY;
            HelmetIron.rotateAngleX = Head.rotateAngleX;
            HelmetIron.rotateAngleY = Head.rotateAngleY;
            break;
        case 3:
            
            HelmetGold.rotationPointY = Head.rotationPointY;
            HelmetGold.rotateAngleX = Head.rotateAngleX;
            HelmetGold.rotateAngleY = Head.rotateAngleY;
            break;
        case 4:
            HelmetDiamond.rotationPointY = Head.rotationPointY;
            HelmetDiamond.rotateAngleX = Head.rotateAngleX;
            HelmetDiamond.rotateAngleY = Head.rotateAngleY;
            break;
        case 5:
            HelmetHide.rotationPointY = Head.rotationPointY;
            HelmetHide.rotateAngleX = Head.rotateAngleX;
            HelmetHide.rotateAngleY = Head.rotateAngleY;
            HelmetNeckHide.rotationPointY = Head.rotationPointY;
            HelmetNeckHide.rotateAngleX = Head.rotateAngleX;
            HelmetNeckHide.rotateAngleY = Head.rotateAngleY;
            HelmetHideEar1.rotationPointY = Head.rotationPointY;
            HelmetHideEar1.rotateAngleX = Head.rotateAngleX;
            HelmetHideEar1.rotateAngleY = Head.rotateAngleY;
            HelmetHideEar2.rotationPointY = Head.rotationPointY;
            HelmetHideEar2.rotateAngleX = Head.rotateAngleX;
            HelmetHideEar2.rotateAngleY = Head.rotateAngleY;
            break;
        case 6:
            HelmetFur.rotationPointY = Head.rotationPointY;
            HelmetFur.rotateAngleX = Head.rotateAngleX;
            HelmetFur.rotateAngleY = Head.rotateAngleY;
            HelmetNeckFur.rotationPointY = Head.rotationPointY;
            HelmetNeckFur.rotateAngleX = Head.rotateAngleX;
            HelmetNeckFur.rotateAngleY = Head.rotateAngleY;
            HelmetFurEar1.rotationPointY = Head.rotationPointY;
            HelmetFurEar1.rotateAngleX = Head.rotateAngleX;
            HelmetFurEar1.rotateAngleY = Head.rotateAngleY;
            HelmetFurEar2.rotationPointY = Head.rotationPointY;
            HelmetFurEar2.rotateAngleX = Head.rotateAngleX;
            HelmetFurEar2.rotateAngleY = Head.rotateAngleY;
            break;
        case 7:
            HelmetReptile.rotationPointY = Head.rotationPointY;
            HelmetReptile.rotateAngleX = Head.rotateAngleX;
            HelmetReptile.rotateAngleY = Head.rotateAngleY;
            HelmetReptileEar1.rotationPointY = Head.rotationPointY;
            HelmetReptileEar1.rotateAngleX = Head.rotateAngleX;
            HelmetReptileEar1.rotateAngleY = (-35F/radianF) + Head.rotateAngleY;
            HelmetReptileEar2.rotationPointY = Head.rotationPointY;
            HelmetReptileEar2.rotateAngleX = Head.rotateAngleX;
            HelmetReptileEar2.rotateAngleY = (35F/radianF) + Head.rotateAngleY;
            break;
        case 8:
            HelmetGreenChitin.rotationPointY = Head.rotationPointY;
            HelmetGreenChitin.rotateAngleX = Head.rotateAngleX;
            HelmetGreenChitin.rotateAngleY = Head.rotateAngleY;
            break;
        case 9:
            HelmetYellowChitin.rotationPointY = Head.rotationPointY;
            HelmetYellowChitin.rotateAngleX = Head.rotateAngleX;
            HelmetYellowChitin.rotateAngleY = Head.rotateAngleY;
            break;
        case 10:
            HelmetBlueChitin.rotationPointY = Head.rotationPointY;
            HelmetBlueChitin.rotateAngleX = Head.rotateAngleX;
            HelmetBlueChitin.rotateAngleY = Head.rotateAngleY;
            break;
        case 11:
            HelmetBlackChitin.rotationPointY = Head.rotationPointY;
            HelmetBlackChitin.rotateAngleX = Head.rotateAngleX;
            HelmetBlackChitin.rotateAngleY = Head.rotateAngleY;
            break;
        case 12:
            HelmetRedChitin.rotationPointY = Head.rotationPointY;
            HelmetRedChitin.rotateAngleX = Head.rotateAngleX;
            HelmetRedChitin.rotateAngleY = Head.rotateAngleY;
            break;
        
        }
        

        //flag
        float flagF = MathHelper.cos(f * 0.8F) * 0.1F * f1;
        
        switch (flagColor)
        {
        case 1:
            FlagOrange.rotateAngleY = flagF;
            break;
        case 2:
            FlagPurple.rotateAngleY = flagF;
            break;
        case 3:
            FlagLightBlue.rotateAngleY = flagF;
            break;
        case 4:
            FlagYellow.rotateAngleY = flagF;
            break;
        case 5:
            FlagGreen.rotateAngleY = flagF;
            break;
        case 6:
            FlagLightRed.rotateAngleY = flagF;
            break;
        case 7:
            FlagDarkGrey.rotateAngleY = flagF;
            break;
        case 8:
            FlagGrey.rotateAngleY = flagF;
            break;
        case 9:
            FlagCyan.rotateAngleY = flagF;
            break;
        case 10:
            FlagDarkPurple.rotateAngleY = flagF;
            break;
        case 11:
            FlagDarkBlue.rotateAngleY = flagF;
            break;
        case 12:
            FlagBrown.rotateAngleY = flagF;
            break;
        case 13:
            FlagDarkGreen.rotateAngleY = flagF;
            break;
        case 14:
            FlagRed.rotateAngleY = flagF;
            break;
        case 15:
            FlagBlack.rotateAngleY = flagF;
            break;
        case 16:
            FlagWhite.rotateAngleY = flagF;
            break;
        }
        
        //legs
        
        
        if ( (typeI ==5 || typeI == 6) && floating)
        {
            LLegC.rotationPointY = 8F;
            LLegC.rotationPointZ = 17F;
            RLegC.rotationPointY = 8F;
            RLegC.rotationPointZ = 17F;
            LFoot.rotationPointY = -5F;
            LFoot.rotationPointZ = -3F;
            RFoot.rotationPointY = -5F;
            RFoot.rotationPointZ = -3F;
            
            LLegA.rotateAngleX = 40F/radianF;
            LLegB.rotateAngleX = LLegA.rotateAngleX;
            LLegC.rotateAngleX = -85F/radianF;
            LFoot.rotateAngleX = 25F/radianF;

            RLegA.rotateAngleX = 40F/radianF;
            RLegB.rotateAngleX = RLegA.rotateAngleX;
            RLegC.rotateAngleX = -85F/radianF;
            RFoot.rotateAngleX = 25F/radianF;
        }
        else
        {
            
            LLegC.rotationPointY = 5F;
            LLegC.rotationPointZ = 4F;
            RLegC.rotationPointY = 5F;
            RLegC.rotationPointZ = 4F;
            LFoot.rotationPointY = 5F;
            LFoot.rotationPointZ = 4F;
            RFoot.rotationPointY = 5F;
            RFoot.rotationPointZ = 4F;
            
            LLegA.rotateAngleX = 0.1745329F + LLegXRot;
            LLegB.rotateAngleX = LLegA.rotateAngleX;
            LLegC.rotateAngleX = -0.2617994F + +LLegXRot;
            LFoot.rotateAngleX = LLegA.rotateAngleX;
            RLegA.rotateAngleX = 0.1745329F + RLegXRot;
            RLegB.rotateAngleX = RLegA.rotateAngleX;
            RLegC.rotateAngleX = -0.2617994F + +RLegXRot;
            RFoot.rotateAngleX = RLegA.rotateAngleX;
        }
        
        
       //wings
        float wingF = 0F;
        /**
         * f = distance walked f1 = speed 0 - 1 f2 = timer
         */
        if (typeI ==5 || typeI == 6)
        {
            
            if (jumpCounter!=0)
            {
                wingF = (-40F/radianF) + MathHelper.cos((float) jumpCounter * 0.3F) *  1.3F;
            }
            else
            if (rider && floating)
            {
                wingF = MathHelper.cos(f2 * 0.8F) *  0.2F;
            }
            else
            {
                wingF = MathHelper.cos(f * 0.3F) *  f1;
            }
            
            LWingD.rotateAngleZ = (-20F/radianF) - wingF;
            LWingE.rotateAngleZ = (-20F/radianF) - wingF;
            
            RWingD.rotateAngleZ = (20F/radianF) + wingF;
            RWingE.rotateAngleZ = (20F/radianF) + wingF;
            
            
        }
        else
        {
            wingF= (10F/radianF) + MathHelper.cos(f * 0.6F) * 0.2F * f1;
            if (wing)
            {
                wingF += (50 / 57.29578F);
            }
            LWingB.rotateAngleY = 0.0872665F + wingF;
            LWingC.rotateAngleY = 0.0872665F + wingF;

            RWingB.rotateAngleY = -0.0872665F - wingF;
            RWingC.rotateAngleY = -0.0872665F - wingF;

            LWingB.rotateAngleX = 0.0872665F + (RLegXRot / 10F);
            LWingC.rotateAngleX = (RLegXRot / 10F);

            RWingB.rotateAngleX = 0.0872665F + (RLegXRot / 10F);
            RWingC.rotateAngleX = (RLegXRot / 10F);
            
            
        }
        

        if (rider)
        {
            SaddleL.rotateAngleX = -60F/radianF;
            SaddleL2.rotateAngleX = SaddleL.rotateAngleX;
            SaddleR.rotateAngleX = -60F/radianF;;
            SaddleR2.rotateAngleX = SaddleR.rotateAngleX;;

            SaddleL.rotateAngleZ = -40F/radianF;
            SaddleL2.rotateAngleZ = SaddleL.rotateAngleZ;
            SaddleR.rotateAngleZ = 40F/radianF;
            SaddleR2.rotateAngleZ = SaddleR.rotateAngleZ;
        }
        else
        {
            SaddleL.rotateAngleX = RLegXRot / 3F;
            SaddleL2.rotateAngleX = RLegXRot / 3F;
            SaddleR.rotateAngleX = RLegXRot / 3F;
            SaddleR2.rotateAngleX = RLegXRot / 3F;

            SaddleL.rotateAngleZ = RLegXRot / 5F;
            SaddleL2.rotateAngleZ = RLegXRot / 5F;
            SaddleR.rotateAngleZ = -RLegXRot / 5F;
            SaddleR2.rotateAngleZ = -RLegXRot / 5F;
        }
        

        if (typeI == 6)
        {
            float f6 = 15F;
            float rotF = MathHelper.cos(f * 0.5F) * 0.3F * f1;
            Tail.rotateAngleY = rotF;
            rotF += (rotF / f6);
            Tailpart1.rotateAngleY = rotF;//MathHelper.cos(f * 0.6662F) * 0.7F * f1;
            rotF += (rotF / f6);
            Tailpart1.rotateAngleY = rotF;
            rotF += (rotF / f6);
            Tailpart2.rotateAngleY = rotF;
            rotF += (rotF / f6);
            Tailpart3.rotateAngleY = rotF;
            rotF += (rotF / f6);
            Tailpart4.rotateAngleY = rotF;
            rotF += (rotF / f6);
            Tailpart5.rotateAngleY = rotF;
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

