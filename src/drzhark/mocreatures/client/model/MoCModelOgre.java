package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.entity.monster.MoCEntityOgre;

@SideOnly(Side.CLIENT)
public class MoCModelOgre extends ModelBase
{
    ModelRenderer Head;
    ModelRenderer Brow;
    ModelRenderer NoseBridge;
    ModelRenderer Nose;
    ModelRenderer RgtTusk;
    ModelRenderer RgtTooth;
    ModelRenderer LftTooth;
    ModelRenderer LftTusk;
    ModelRenderer Lip;
    ModelRenderer RgtEar;
    ModelRenderer RgtRing;
    ModelRenderer RgtRingHole;
    ModelRenderer LftEar;
    ModelRenderer LftRing;
    ModelRenderer LftRingHole;
    ModelRenderer HairRope;
    ModelRenderer Hair1;
    ModelRenderer Hair2;
    ModelRenderer Hair3;
    ModelRenderer DiamondHorn;
    ModelRenderer RgtHorn;
    ModelRenderer RgtHornTip;
    ModelRenderer LftHorn;
    ModelRenderer LftHornTip;
    ModelRenderer RgtShoulder;
    ModelRenderer LftShoulder;
    ModelRenderer NeckRest;
    ModelRenderer Chest;
    ModelRenderer Stomach;
    ModelRenderer ButtCover;
    ModelRenderer LoinCloth;
    ModelRenderer RgtThigh;
    ModelRenderer RgtKnee;
    ModelRenderer RgtLeg;
    ModelRenderer RgtToes;
    ModelRenderer RgtBigToe;
    ModelRenderer LftThigh;
    ModelRenderer LftKnee;
    ModelRenderer LftLeg;
    ModelRenderer LftToes;
    ModelRenderer LftBigToe;
    ModelRenderer LftArm;
    ModelRenderer LftElbow;
    ModelRenderer LftHand;
    ModelRenderer LftWeaponEnd;
    ModelRenderer LftWeaponRoot;
    ModelRenderer LftWeaponLump;
    ModelRenderer LftWeaponBetween;
    ModelRenderer LftWeaponTip;
    ModelRenderer LftSpike;
    ModelRenderer LftSpike1;
    ModelRenderer LftSpike2;
    ModelRenderer LftSpike3;
    ModelRenderer LftSpike4;
    ModelRenderer LftHammerNeck;
    ModelRenderer LftHammerHeadSupport;
    ModelRenderer LftHammerHead;
    ModelRenderer RgtArm;
    ModelRenderer RgtElbow;
    ModelRenderer RgtHand;
    ModelRenderer RgtWeaponEnd;
    ModelRenderer RgtWeaponRoot;
    ModelRenderer RgtWeaponLump;
    ModelRenderer RgtWeaponBetween;
    ModelRenderer RgtWeaponTip;
    ModelRenderer RgtSpike;
    ModelRenderer RgtSpike1;
    ModelRenderer RgtSpike2;
    ModelRenderer RgtSpike3;
    ModelRenderer RgtSpike4;
    ModelRenderer RgtHammerNeck;
    ModelRenderer RgtHammerHeadSupport;
    ModelRenderer RgtHammerHead;
    ModelRenderer Head3RgtEar;
    ModelRenderer Head3LftEar;
    ModelRenderer Head3Eyelid;
    ModelRenderer Head3Nose;
    ModelRenderer Head3;
    ModelRenderer Head3Brow;
    ModelRenderer Head3Hair;
    ModelRenderer Head3Lip;
    ModelRenderer Head3RgtTusk;
    ModelRenderer Head3RgtTooth;
    ModelRenderer Head3LftTooth;
    ModelRenderer Head3LftTusk;
    ModelRenderer Head3RingHole;
    ModelRenderer Head3Ring;
    ModelRenderer Head2Chin;
    ModelRenderer Head2;
    ModelRenderer Head2Lip;
    ModelRenderer Head2LftTusk;
    ModelRenderer Head2RgtTusk;
    ModelRenderer Head2Nose;
    ModelRenderer Head2NoseBridge;
    ModelRenderer Head2Brow;
    ModelRenderer Head2RgtHorn;
    ModelRenderer Head2LftHorn;
    ModelRenderer Head2DiamondHorn;
    
    private float radianF = 57.29578F;

    public MoCModelOgre()
    {
        textureWidth = 128;
        textureHeight = 128;

        Head = new ModelRenderer(this, 80, 0);
        Head.addBox(-6F, -12F, -6F, 12, 12, 12);
        Head.setRotationPoint(0F, -13F, 0F);
        
        Brow = new ModelRenderer(this, 68, 7);
        Brow.addBox(-5F, -10.5F, -8F, 10, 3, 2);
        Brow.setRotationPoint(0F, -13F, 0F);
        setRotation(Brow, -0.0872665F, 0F, 0F);
        
        NoseBridge = new ModelRenderer(this, 80, 4);
        NoseBridge.addBox(-1F, -7F, -8F, 2, 2, 1);
        NoseBridge.setRotationPoint(0F, -13F, 0F);
        setRotation(NoseBridge, -0.1745329F, 0F, 0F);
        
        Nose = new ModelRenderer(this, 80, 0);
        Nose.addBox(-2F, -7F, -7F, 4, 2, 2);
        Nose.setRotationPoint(0F, -13F, 0F);
        setRotation(Nose, 0.0872665F, 0F, 0F);
        
        RgtTusk = new ModelRenderer(this, 60, 4);
        RgtTusk.addBox(-3.5F, -6F, -6.5F, 1, 2, 1);
        RgtTusk.setRotationPoint(0F, -13F, 0F);
        setRotation(RgtTusk, 0.1745329F, 0F, 0F);
        
        RgtTooth = new ModelRenderer(this, 64, 4);
        RgtTooth.addBox(-1.5F, -5F, -6.5F, 1, 1, 1);
        RgtTooth.setRotationPoint(0F, -13F, 0F);
        setRotation(RgtTooth, 0.1745329F, 0F, 0F);
        
        LftTooth = new ModelRenderer(this, 72, 4);
        LftTooth.addBox(0.5F, -5F, -6.5F, 1, 1, 1);
        LftTooth.setRotationPoint(0F, -13F, 0F);
        setRotation(LftTooth, 0.1745329F, 0F, 0F);
        
        LftTusk = new ModelRenderer(this, 76, 4);
        LftTusk.addBox(2.5F, -6F, -6.5F, 1, 2, 1);
        LftTusk.setRotationPoint(0F, -13F, 0F);
        setRotation(LftTusk, 0.1745329F, 0F, 0F);
        
        Lip = new ModelRenderer(this, 60, 0);
        Lip.addBox(-4F, -4F, -7F, 8, 2, 2);
        Lip.setRotationPoint(0F, -13F, 0F);
        setRotation(Lip, 0.1745329F, 0F, 0F);
        
        RgtEar = new ModelRenderer(this, 60, 12);
        RgtEar.addBox(-9F, -9F, -1F, 3, 5, 2);
        RgtEar.setRotationPoint(0F, -13F, 0F);
        
        RgtRing = new ModelRenderer(this, 32, 58);
        RgtRing.addBox(-8F, -6F, -2F, 1, 4, 4);
        RgtRing.setRotationPoint(0F, -13F, 0F);
        
        RgtRingHole = new ModelRenderer(this, 26, 50);
        RgtRingHole.addBox(-8F, -5F, -1F, 1, 2, 2);
        RgtRingHole.setRotationPoint(0F, -13F, 0F);
        
        LftEar = new ModelRenderer(this, 70, 12);
        LftEar.addBox(6F, -9F, -1F, 3, 5, 2);
        LftEar.setRotationPoint(0F, -13F, 0F);
        
        LftRing = new ModelRenderer(this, 32, 58);
        LftRing.addBox(7F, -6F, -2F, 1, 4, 4);
        LftRing.setRotationPoint(0F, -13F, 0F);
        
        LftRingHole = new ModelRenderer(this, 26, 50);
        LftRingHole.addBox(7F, -5F, -1F, 1, 2, 2);
        LftRingHole.setRotationPoint(0F, -13F, 0F);
        
        HairRope = new ModelRenderer(this, 82, 83);
        HairRope.addBox(-2F, -8F, 9F, 4, 4, 4);
        HairRope.setRotationPoint(0F, -13F, 0F);
        setRotation(HairRope, 0.6108652F, 0F, 0F);
        
        Hair1 = new ModelRenderer(this, 78, 107);
        Hair1.addBox(-3F, -9F, 13F, 6, 8, 3);
        Hair1.setRotationPoint(0F, -13F, 0F);
        setRotation(Hair1, 0.6108652F, 0F, 0F);
        
        Hair2 = new ModelRenderer(this, 60, 107);
        Hair2.addBox(-3F, -6.5F, 11.6F, 6, 8, 3);
        Hair2.setRotationPoint(0F, -13F, 0F);
        setRotation(Hair2, 0.2617994F, 0F, 0F);
        
        Hair3 = new ModelRenderer(this, 42, 107);
        Hair3.addBox(-3F, -2.4F, 11.4F, 6, 8, 3);
        Hair3.setRotationPoint(0F, -13F, 0F);
        
        DiamondHorn = new ModelRenderer(this, 120, 31);
        DiamondHorn.addBox(-1F, -17F, -6F, 2, 6, 2);
        DiamondHorn.setRotationPoint(0F, -13F, 0F);
        setRotation(DiamondHorn, 0.0872665F, 0F, 0F);
        
        RgtHorn = new ModelRenderer(this, 46, 6);
        RgtHorn.addBox(-6F, -12F, -11F, 2, 2, 5);
        RgtHorn.setRotationPoint(0F, -13F, 0F);
        
        RgtHornTip = new ModelRenderer(this, 44, 13);
        RgtHornTip.addBox(-6F, -15F, -11F, 2, 3, 2);
        RgtHornTip.setRotationPoint(0F, -13F, 0F);
        
        LftHorn = new ModelRenderer(this, 46, 6);
        LftHorn.addBox(4F, -12F, -11F, 2, 2, 5);
        LftHorn.setRotationPoint(0F, -13F, 0F);
        
        LftHornTip = new ModelRenderer(this, 52, 13);
        LftHornTip.addBox(4F, -15F, -11F, 2, 3, 2);
        LftHornTip.setRotationPoint(0F, -13F, 0F);
    
        NeckRest = new ModelRenderer(this, 39, 20);
        NeckRest.addBox(-7F, -19F, -3F, 14, 3, 11);
        NeckRest.setRotationPoint(0F, 5F, 0F);
        
        Chest = new ModelRenderer(this, 32, 34);
        Chest.addBox(-9.5F, -17.8F, -7.3F, 19, 11, 13);
        Chest.setRotationPoint(0F, 5F, 0F);
        
        setRotation(Chest, -0.1745329F, 0F, 0F);
        Stomach = new ModelRenderer(this, 28, 58);
        Stomach.addBox(-11F, -8F, -6F, 22, 11, 14);
        Stomach.setRotationPoint(0F, 5F, 0F);
        
        ButtCover = new ModelRenderer(this, 32, 118);
        ButtCover.addBox(-4F, 0F, 0F, 8, 8, 2);
        ButtCover.setRotationPoint(0F, 8F, 6F);
        
        LoinCloth = new ModelRenderer(this, 32, 118);
        LoinCloth.addBox(-4F, 0F, -2F, 8, 8, 2);
        LoinCloth.setRotationPoint(0F, 8F, -4F);

        RgtThigh = new ModelRenderer(this, 0, 83);
        RgtThigh.addBox(-10F, 0F, -5F, 10, 11, 10);
        RgtThigh.setRotationPoint(-2F, 4F, 1F);
        
        RgtLeg = new ModelRenderer(this, 0, 104);
        RgtLeg.addBox(-4F, -1F, -4F, 8, 11, 8);
        RgtLeg.setRotationPoint(-5F, 10F, 0F);
        RgtThigh.addChild(RgtLeg);
        
        RgtKnee = new ModelRenderer(this, 0, 88);
        RgtKnee.addBox(-2F, -2F, -0.5F, 4, 4, 1);
        RgtKnee.setRotationPoint(0F, 2F, -4.25F);
        RgtLeg.addChild(RgtKnee);
        
        RgtToes = new ModelRenderer(this, 0, 123);
        RgtToes.addBox(-2.5F, -1F, -3F, 5, 2, 3);
        RgtToes.setRotationPoint(-1.5F, 9F, -3.5F);
        RgtLeg.addChild(RgtToes);
        
        RgtBigToe = new ModelRenderer(this, 20, 123);
        RgtBigToe.addBox(-1.5F, -1F, -3F, 3, 2, 3);
        RgtBigToe.setRotationPoint(2.5F, 9F, -4F);
        RgtLeg.addChild(RgtBigToe);
        
        LftThigh = new ModelRenderer(this, 88, 83);
        LftThigh.addBox(0F, 0F, -5F, 10, 11, 10);
        LftThigh.setRotationPoint(2F, 4F, 1F);
        
        LftLeg = new ModelRenderer(this, 96, 104);
        LftLeg.addBox(-4F, -1F, -4F, 8, 11, 8);
        LftLeg.setRotationPoint(5F, 10F, 0F);
        LftThigh.addChild(LftLeg);
        
        LftKnee = new ModelRenderer(this, 118, 88);
        LftKnee.addBox(-2F, -2F, -0.5F, 4, 4, 1);
        LftKnee.setRotationPoint(0F, 2F, -4.25F);
        LftLeg.addChild(LftKnee);
        
        LftToes = new ModelRenderer(this, 112, 123);
        LftToes.addBox(-2.5F, -1F, -3F, 5, 2, 3);
        LftToes.setRotationPoint(1.5F, 9F, -3.5F);
        LftLeg.addChild(LftToes);
        
        LftBigToe = new ModelRenderer(this, 96, 123);
        LftBigToe.addBox(-1.5F, -1F, -3F, 3, 2, 3);
        LftBigToe.setRotationPoint(-2.5F, 9F, -4F);
        LftLeg.addChild(LftBigToe);
        
        //LEFT ARM
        LftShoulder = new ModelRenderer(this, 96, 31);
        LftShoulder.addBox(0F, -3F, -4F, 8, 7, 8);
        LftShoulder.setRotationPoint(7F, -10F, 2F);
        
        LftArm = new ModelRenderer(this, 100, 66);
        LftArm.addBox(0F, 0F, -4F, 6, 9, 8);
        LftArm.setRotationPoint(6F, -1F, 1F);
        LftShoulder.addChild(LftArm);
        
        LftHand = new ModelRenderer(this, 96, 46);
        LftHand.addBox(-4F, 0F, -4F, 8, 12, 8);
        LftHand.setRotationPoint(3F, 8F, -1F);
        LftArm.addChild(LftHand);
        
        LftElbow = new ModelRenderer(this, 86, 64);
        LftElbow.addBox(-2F, -1.5F, -0.5F, 4, 3, 1);
        LftElbow.setRotationPoint(0F, 2.5F, 4F);
        LftHand.addChild(LftElbow);
        
        LftWeaponRoot = new ModelRenderer(this, 24, 104);
        LftWeaponRoot.addBox(-1.5F, -1.5F, -4F, 3, 3, 4);
        LftWeaponRoot.setRotationPoint(-0.5F, 8.5F, -4F);
        LftHand.addChild(LftWeaponRoot);
        
        LftWeaponEnd = new ModelRenderer(this, 74, 90);
        LftWeaponEnd.addBox(-1.5F, -1.5F, 0F, 3, 3, 2);
        LftWeaponEnd.setRotationPoint(0F, 0F, 8F);
        LftWeaponRoot.addChild(LftWeaponEnd);
        
        LftWeaponLump = new ModelRenderer(this, 30, 83);
        LftWeaponLump.addBox(-2.5F, -2.5F, -4F, 5, 5, 4);
        LftWeaponLump.setRotationPoint(0F, 0F, -4F);
        LftWeaponRoot.addChild(LftWeaponLump);
        
        LftWeaponBetween = new ModelRenderer(this, 83, 42);
        LftWeaponBetween.addBox(-1.5F, -1.5F, -2F, 3, 3, 2);
        LftWeaponBetween.setRotationPoint(0F, 0F, -4F);
        LftWeaponLump.addChild(LftWeaponBetween);
        
        LftWeaponTip = new ModelRenderer(this, 60, 118);
        LftWeaponTip.addBox(-2.5F, -2.5F, -5F, 5, 5, 5);
        LftWeaponTip.setRotationPoint(0F, 0F, -2F);
        LftWeaponBetween.addChild(LftWeaponTip);
        
        LftHammerNeck = new ModelRenderer(this, 32, 39);
        LftHammerNeck.addBox(-0.5F, -4F, -4F, 1, 4, 4);
        LftHammerNeck.setRotationPoint(0F, -2.5F, -1F);
        LftWeaponTip.addChild(LftHammerNeck);
        
        LftHammerHeadSupport = new ModelRenderer(this, 0, 0);
        LftHammerHeadSupport.addBox(-1F, 0F, -2F, 2, 2, 4);
        LftHammerHeadSupport.setRotationPoint(0F, 2.5F, -3F);
        LftWeaponTip.addChild(LftHammerHeadSupport);
        
        LftHammerHead = new ModelRenderer(this, 32, 3);
        LftHammerHead.addBox(-2F, 0F, -2.5F, 4, 3, 5);
        LftHammerHead.setRotationPoint(0F, 2F, 0F);
        LftHammerHeadSupport.addChild(LftHammerHead);
        
        LftSpike = new ModelRenderer(this, 52, 118);
        LftSpike.addBox(-1F, -1F, -3F, 2, 2, 3);
        LftSpike.setRotationPoint(0F, 0F, -5F);
        LftWeaponTip.addChild(LftSpike);
        
        LftSpike1 = new ModelRenderer(this, 52, 118);
        LftSpike1.addBox(-3F, -1F, -1F, 3, 2, 2);
        LftSpike1.setRotationPoint(-2.5F, 0F, -3F);
        LftWeaponTip.addChild(LftSpike1);
        
        LftSpike2 = new ModelRenderer(this, 52, 118);
        LftSpike2.addBox(3F, -1F, -1F, 3, 2, 2);
        LftSpike2.setRotationPoint(-0.5F, 0F, -3F);
        LftWeaponTip.addChild(LftSpike2);
        
        LftSpike3 = new ModelRenderer(this, 52, 118);
        LftSpike3.addBox(-1F, 0F, -1F, 2, 3, 2);
        LftSpike3.setRotationPoint(0F, 2.5F, -3F);
        LftWeaponTip.addChild(LftSpike3);
        
        LftSpike4 = new ModelRenderer(this, 52, 118);
        LftSpike4.addBox(-1F, -3F, -1F, 2, 3, 2);
        LftSpike4.setRotationPoint(0F, -2.5F, -3F);
        LftWeaponTip.addChild(LftSpike4);
        
        
        //RIGHT ARM
        RgtShoulder = new ModelRenderer(this, 0, 31);
        RgtShoulder.addBox(0F, -3F, -4F, 8, 7, 8);
        RgtShoulder.setRotationPoint(-15F, -10F, 2F);
        
        RgtArm = new ModelRenderer(this, 0, 66);
        RgtArm.addBox(0F, 0F, -4F, 6, 9, 8);
        RgtArm.setRotationPoint(-4F, -1F, 1F);
        RgtShoulder.addChild(RgtArm);
        
        RgtHand = new ModelRenderer(this, 0, 46);
        RgtHand.addBox(-4F, 0F, -4F, 8, 12, 8);
        RgtHand.setRotationPoint(3F, 8F, -1F);
        RgtArm.addChild(RgtHand);
        
        RgtElbow = new ModelRenderer(this, 86, 64);
        RgtElbow.addBox(-2F, -1.5F, -0.5F, 4, 3, 1);
        RgtElbow.setRotationPoint(0F, 2.5F, 4F);
        RgtHand.addChild(RgtElbow);
        
        RgtWeaponRoot = new ModelRenderer(this, 24, 104);
        RgtWeaponRoot.addBox(-1.5F, -1.5F, -4F, 3, 3, 4);
        RgtWeaponRoot.setRotationPoint(-0.5F, 8.5F, -4F);
        RgtHand.addChild(RgtWeaponRoot);
        
        RgtWeaponEnd = new ModelRenderer(this, 74, 90);
        RgtWeaponEnd.addBox(-1.5F, -1.5F, 0F, 3, 3, 2);
        RgtWeaponEnd.setRotationPoint(0F, 0F, 8F);
        RgtWeaponRoot.addChild(RgtWeaponEnd);
        
        RgtWeaponLump = new ModelRenderer(this, 30, 83);
        RgtWeaponLump.addBox(-2.5F, -2.5F, -4F, 5, 5, 4);
        RgtWeaponLump.setRotationPoint(0F, 0F, -4F);
        RgtWeaponRoot.addChild(RgtWeaponLump);
        
        RgtWeaponBetween = new ModelRenderer(this, 83, 42);
        RgtWeaponBetween.addBox(-1.5F, -1.5F, -2F, 3, 3, 2);
        RgtWeaponBetween.setRotationPoint(0F, 0F, -4F);
        RgtWeaponLump.addChild(RgtWeaponBetween);
        
        RgtWeaponTip = new ModelRenderer(this, 60, 118);
        RgtWeaponTip.addBox(-2.5F, -2.5F, -5F, 5, 5, 5);
        RgtWeaponTip.setRotationPoint(0F, 0F, -2F);
        RgtWeaponBetween.addChild(RgtWeaponTip);
        
        RgtHammerNeck = new ModelRenderer(this, 32, 39);
        RgtHammerNeck.addBox(-0.5F, -4F, -4F, 1, 4, 4);
        RgtHammerNeck.setRotationPoint(0F, -2.5F, -1F);
        RgtWeaponTip.addChild(RgtHammerNeck);
        
        RgtHammerHeadSupport = new ModelRenderer(this, 0, 0);
        RgtHammerHeadSupport.addBox(-1F, 0F, -2F, 2, 2, 4);
        RgtHammerHeadSupport.setRotationPoint(0F, 2.5F, -3F);
        RgtWeaponTip.addChild(RgtHammerHeadSupport);
        
        RgtHammerHead = new ModelRenderer(this, 32, 3);
        RgtHammerHead.addBox(-2F, 0F, -2.5F, 4, 3, 5);
        RgtHammerHead.setRotationPoint(0F, 2F, 0F);
        RgtHammerHeadSupport.addChild(RgtHammerHead);
        
        RgtSpike = new ModelRenderer(this, 52, 118);
        RgtSpike.addBox(-1F, -1F, -3F, 2, 2, 3);
        RgtSpike.setRotationPoint(0F, 0F, -5F);
        RgtWeaponTip.addChild(RgtSpike);
        
        RgtSpike1 = new ModelRenderer(this, 52, 118);
        RgtSpike1.addBox(-3F, -1F, -1F, 3, 2, 2);
        RgtSpike1.setRotationPoint(-2.5F, 0F, -3F);
        RgtWeaponTip.addChild(RgtSpike1);
        
        RgtSpike2 = new ModelRenderer(this, 52, 118);
        RgtSpike2.addBox(3F, -1F, -1F, 3, 2, 2);
        RgtSpike2.setRotationPoint(-0.5F, 0F, -3F);
        RgtWeaponTip.addChild(RgtSpike2);
        
        RgtSpike3 = new ModelRenderer(this, 52, 118);
        RgtSpike3.addBox(-1F, 0F, -1F, 2, 3, 2);
        RgtSpike3.setRotationPoint(0F, 2.5F, -3F);
        RgtWeaponTip.addChild(RgtSpike3);
        
        RgtSpike4 = new ModelRenderer(this, 52, 118);
        RgtSpike4.addBox(-1F, -3F, -1F, 2, 3, 2);
        RgtSpike4.setRotationPoint(0F, -2.5F, -3F);
        RgtWeaponTip.addChild(RgtSpike4);
        

        
        Head3RgtEar = new ModelRenderer(this, 110, 24);
        Head3RgtEar.addBox(-8F, -9F, -1F, 3, 5, 2);
        Head3RgtEar.setRotationPoint(7F, -13F, 0F);
        
        Head3LftEar = new ModelRenderer(this, 100, 24);
        Head3LftEar.addBox(5F, -9F, -1F, 3, 5, 2);
        Head3LftEar.setRotationPoint(7F, -13F, 0F);
        
        Head3Eyelid = new ModelRenderer(this, 46, 3);
        Head3Eyelid.addBox(-3F, -8F, -4.5F, 6, 2, 1);
        Head3Eyelid.setRotationPoint(7F, -13F, 0F);
        setRotation(Head3Eyelid, 0.2617994F, 0F, 0F);
        
        Head3Nose = new ModelRenderer(this, 60, 9);
        Head3Nose.addBox(-1.5F, -8.5F, -3.5F, 3, 2, 1);
        Head3Nose.setRotationPoint(7F, -13F, 0F);
        setRotation(Head3Nose, 0.4886922F, 0F, 0F);
        
        Head3 = new ModelRenderer(this, 42, 83);
        Head3.addBox(-5F, -12F, -6F, 10, 12, 12);
        Head3.setRotationPoint(7F, -13F, 0F);
        
        Head3Brow = new ModelRenderer(this, 46, 0);
        Head3Brow.addBox(-3F, -9F, -8.5F, 6, 2, 1);
        Head3Brow.setRotationPoint(7F, -13F, 0F);
        setRotation(Head3Brow, -0.2617994F, 0F, 0F);
        
        Head3Hair = new ModelRenderer(this, 80, 118);
        Head3Hair.addBox(-2F, -17F, -5F, 4, 6, 4);
        Head3Hair.setRotationPoint(7F, -13F, 0F);
        setRotation(Head3Hair, -0.6108652F, 0F, 0F);
        
        Head3Lip = new ModelRenderer(this, 22, 68);
        Head3Lip.addBox(-4F, -4F, -7F, 8, 2, 2);
        Head3Lip.setRotationPoint(7F, -13F, 0F);
        setRotation(Head3Lip, 0.1745329F, 0F, 0F);
        
        Head3RgtTusk = new ModelRenderer(this, 83, 34);
        Head3RgtTusk.addBox(-3.5F, -6F, -6.5F, 1, 2, 1);
        Head3RgtTusk.setRotationPoint(7F, -13F, 0F);
        
        setRotation(Head3RgtTusk, 0.1745329F, 0F, 0F);
        Head3RgtTooth = new ModelRenderer(this, 87, 34);
        Head3RgtTooth.addBox(-1.5F, -5F, -6.5F, 1, 1, 1);
        Head3RgtTooth.setRotationPoint(7F, -13F, 0F);
        setRotation(Head3RgtTooth, 0.1745329F, 0F, 0F);
        
        Head3LftTooth = new ModelRenderer(this, 96, 34);
        Head3LftTooth.addBox(0.5F, -5F, -6.5F, 1, 1, 1);
        Head3LftTooth.setRotationPoint(7F, -13F, 0F);
        setRotation(Head3LftTooth, 0.1745329F, 0F, 0F);
        
        Head3LftTusk = new ModelRenderer(this, 100, 34);
        Head3LftTusk.addBox(2.5F, -6F, -6.5F, 1, 2, 1);
        Head3LftTusk.setRotationPoint(7F, -13F, 0F);
        setRotation(Head3LftTusk, 0.1745329F, 0F, 0F);
        
        Head3RingHole = new ModelRenderer(this, 26, 50);
        Head3RingHole.addBox(6F, -5F, -1F, 1, 2, 2);
        Head3RingHole.setRotationPoint(7F, -13F, 0F);

        Head3Ring = new ModelRenderer(this, 32, 58);
        Head3Ring.addBox(6F, -6F, -2F, 1, 4, 4);
        Head3Ring.setRotationPoint(7F, -13F, 0F);
        
        Head2Chin = new ModelRenderer(this, 21, 24);
        Head2Chin.addBox(-3F, -5F, -8F, 6, 3, 3);
        Head2Chin.setRotationPoint(-7F, -13F, 0F);
        setRotation(Head2Chin, 0.2617994F, 0F, 0F);
        
        Head2 = new ModelRenderer(this, 0, 0);
        Head2.addBox(-5F, -12F, -6F, 10, 12, 12);
        Head2.setRotationPoint(-7F, -13F, 0F);
        
        Head2Lip = new ModelRenderer(this, 0, 24);
        Head2Lip.addBox(-4F, -5F, -8F, 8, 2, 2);
        Head2Lip.setRotationPoint(-7F, -13F, 0F);
        
        Head2LftTusk = new ModelRenderer(this, 46, 28);
        Head2LftTusk.addBox(2.5F, -8F, -6.5F, 1, 2, 1);
        Head2LftTusk.setRotationPoint(-7F, -13F, 0F);
        setRotation(Head2LftTusk, 0.1745329F, 0F, 0F);
        
        Head2RgtTusk = new ModelRenderer(this, 39, 28);
        Head2RgtTusk.addBox(-3.5F, -8F, -6.5F, 1, 2, 1);
        Head2RgtTusk.setRotationPoint(-7F, -13F, 0F);
        setRotation(Head2RgtTusk, 0.1745329F, 0F, 0F);
        
        Head2Nose = new ModelRenderer(this, 116, 0);
        Head2Nose.addBox(-2F, -7F, -7F, 4, 2, 2);
        Head2Nose.setRotationPoint(-7F, -13F, 0F);
        setRotation(Head2Nose, 0.0872665F, 0F, 0F);
        
        Head2NoseBridge = new ModelRenderer(this, 116, 4);
        Head2NoseBridge.addBox(-1F, -7F, -8F, 2, 2, 1);
        Head2NoseBridge.setRotationPoint(-7F, -13F, 0F);
        setRotation(Head2NoseBridge, -0.1745329F, 0F, 0F);
        
        Head2Brow = new ModelRenderer(this, 80, 24);
        Head2Brow.addBox(-4F, -10.5F, -8F, 8, 3, 2);
        Head2Brow.setRotationPoint(-7F, -13F, 0F);
        setRotation(Head2Brow, -0.0872665F, 0F, 0F);
        
        Head2RgtHorn = new ModelRenderer(this, 24, 30);
        Head2RgtHorn.addBox(-4F, -8F, -15F, 2, 2, 5);
        Head2RgtHorn.setRotationPoint(-7F, -13F, 0F);
        setRotation(Head2RgtHorn, -0.5235988F, 0F, 0F);
        
        Head2LftHorn = new ModelRenderer(this, 24, 30);
        Head2LftHorn.addBox(2F, -8F, -15F, 2, 2, 5);
        Head2LftHorn.setRotationPoint(-7F, -13F, 0F);
        setRotation(Head2LftHorn, -0.5235988F, 0F, 0F);
        
        Head2DiamondHorn = new ModelRenderer(this, 120, 46);
        Head2DiamondHorn.addBox(-1F, -17F, -6F, 2, 6, 2);
        Head2DiamondHorn.setRotationPoint(-7F, -13F, 0F);
        setRotation(Head2DiamondHorn, 0.0872665F, 0F, 0F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        //super.render(entity, f, f1, f2, f3, f4, f5);
        MoCEntityOgre entityogre = (MoCEntityOgre) entity;
        int type = entityogre.getType();
        int leftAttack = entityogre.attackCounterLeft;
        int rightAttack = entityogre.attackCounterRight;
        int headMoving = entityogre.getMovingHead();
        
        setRotationAngles(f, f1, f2, f3, f4, f5, headMoving, type, leftAttack, rightAttack);
        
        if (type == 1 || type == 3 || type == 5)
        {
            Head.render(f5);
            Brow.render(f5);
            NoseBridge.render(f5);
            Nose.render(f5);
            RgtTusk.render(f5);
            RgtTooth.render(f5);
            LftTooth.render(f5);
            LftTusk.render(f5);
            Lip.render(f5);
            RgtEar.render(f5);
            RgtRing.render(f5);
            RgtRingHole.render(f5);
            LftEar.render(f5);
            LftRing.render(f5);
            LftRingHole.render(f5);
            HairRope.render(f5);
            Hair1.render(f5);
            Hair2.render(f5);
            Hair3.render(f5);
            DiamondHorn.render(f5);
            RgtHorn.render(f5);
            RgtHornTip.render(f5);
            LftHorn.render(f5);
            LftHornTip.render(f5);
            
            LftWeaponRoot.isHidden = true;
        }else
        {
            Head3RgtEar.render(f5);
            Head3LftEar.render(f5);
            Head3Eyelid.render(f5);
            Head3Nose.render(f5);
            Head3.render(f5);
            Head3Brow.render(f5);
            Head3Hair.render(f5);
            Head3Lip.render(f5);
            Head3RgtTusk.render(f5);
            Head3RgtTooth.render(f5);
            Head3LftTooth.render(f5);
            Head3LftTusk.render(f5);
            Head3RingHole.render(f5);
            Head3Ring.render(f5);
            
            Head2Chin.render(f5);
            Head2.render(f5);
            Head2Lip.render(f5);
            Head2LftTusk.render(f5);
            Head2RgtTusk.render(f5);
            Head2Nose.render(f5);
            Head2NoseBridge.render(f5);
            Head2Brow.render(f5);
            Head2RgtHorn.render(f5);
            Head2LftHorn.render(f5);
            Head2DiamondHorn.render(f5);
            
            LftWeaponRoot.isHidden = false;
        }
        
        
        NeckRest.render(f5);
        Chest.render(f5);
        Stomach.render(f5);
        ButtCover.render(f5);
        LoinCloth.render(f5);
        RgtThigh.render(f5);
        LftThigh.render(f5);
        RgtShoulder.render(f5);
        LftShoulder.render(f5);
        
        

    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, int headMoving, int type, int leftAttack, int rightAttack)
    {
        float hRotY = f3 / 57.29578F;
        float hRotX = f4 / 57.29578F;
        
        float RLegXRot = MathHelper.cos((f * 0.6662F) + 3.141593F) * 0.8F * f1;
        float LLegXRot = MathHelper.cos(f * 0.6662F) * 0.8F * f1;
        float ClothRot = MathHelper.cos(f * 0.9F) * 0.6F * f1;
        
        float RLegXRotB = RLegXRot;
        float LLegXRotB = LLegXRot;
        
        RgtThigh.rotateAngleX =  RLegXRot;
        LftThigh.rotateAngleX =  LLegXRot;
        
        float RLegXRot2 = MathHelper.cos(((f + 0.1F) * 0.6662F) + 3.141593F) * 0.8F * f1;
        float LLegXRot2 = MathHelper.cos((f + 0.1F) * 0.6662F) * 0.8F * f1;
        if (f1 > 0.15F)
        {
            if (RLegXRot > RLegXRot2) // - - >
            {
                RLegXRotB = RLegXRot + (25 / 57.29578F);
               
            }
            /*if (RLegXRot < RLegXRot2) // < - -
            {
                RLegXRotC = RLegXRot + (15 / 57.29578F);
                
            }*/
            if (LLegXRot > LLegXRot2) // - - >
            {
                LLegXRotB = LLegXRot + (25 / 57.29578F);
            }
            /*if (LLegXRot < LLegXRot2) // < - -
            {
                LLegXRotC = LLegXRot + (15 / 57.29578F);
            }*/
        }
        
        
        LftLeg.rotateAngleX = (LLegXRotB);
        RgtLeg.rotateAngleX = (RLegXRotB);
        LoinCloth.rotateAngleX = ClothRot;
        ButtCover.rotateAngleX = ClothRot;

        
        if (leftAttack == 0)
        {
            LftShoulder.rotateAngleZ = (MathHelper.cos(f2 * 0.09F) * 0.05F) - 0.05F;
            LftShoulder.rotateAngleX = RLegXRot;
            LftHand.rotateAngleX = 0F;
        }else
        {
            float armMov = (MathHelper.cos((leftAttack) * 0.18F) * 2F);
            LftShoulder.rotateAngleX =  + armMov;
                LftHand.rotateAngleX = (-45F/radianF);
        }
        
                
       
        if (rightAttack == 0) //not smashing, regular arm mov
        {
            RgtShoulder.rotateAngleZ = -(MathHelper.cos(f2 * 0.09F) * 0.05F) + 0.05F;
            RgtShoulder.rotateAngleX = LLegXRot;
            RgtHand.rotateAngleX = 0F;
        }else //smashing
        {
           float armMov = (MathHelper.cos((rightAttack) * 0.18F) * 2F);
            RgtShoulder.rotateAngleX =  + armMov;//(MathHelper.cos((float)rightAttack * 2.5F) * 2.5F);
            RgtHand.rotateAngleX = (-45F/radianF);
        }
        
        
        
        
        if (headMoving == 2)
        {
            Head2.rotateAngleX = hRotX;
            Head2.rotateAngleY = hRotY;
        }
        
        if (headMoving == 3)
        {
            Head3.rotateAngleX = hRotX;
            Head3.rotateAngleY = hRotY;
        }
        
        if (type == 1 || type == 3 || type == 5)
        {
            Head.rotateAngleX = hRotX;
            Head.rotateAngleY = hRotY;
            
            Brow.rotateAngleX = Head.rotateAngleX;
            NoseBridge.rotateAngleX = Head.rotateAngleX;
            Nose.rotateAngleX = Head.rotateAngleX;
            RgtTusk.rotateAngleX = Head.rotateAngleX;
            RgtTooth.rotateAngleX = Head.rotateAngleX;
            LftTooth.rotateAngleX = Head.rotateAngleX;
            LftTusk.rotateAngleX = Head.rotateAngleX;
            Lip.rotateAngleX = Head.rotateAngleX;
            RgtEar.rotateAngleX = Head.rotateAngleX;
            RgtRing.rotateAngleX = Head.rotateAngleX;
            RgtRingHole.rotateAngleX = Head.rotateAngleX;
            LftEar.rotateAngleX = Head.rotateAngleX;
            LftRing.rotateAngleX = Head.rotateAngleX;
            LftRingHole.rotateAngleX = Head.rotateAngleX;
            HairRope.rotateAngleX = 0.6108652F + Head.rotateAngleX;
            Hair1.rotateAngleX = 0.6108652F + Head.rotateAngleX;
            Hair2.rotateAngleX = 0.2617994F + Head.rotateAngleX;
            Hair3.rotateAngleX = Head.rotateAngleX;
            DiamondHorn.rotateAngleX = 0.0872665F + Head.rotateAngleX;
            RgtHorn.rotateAngleX = Head.rotateAngleX;
            RgtHornTip.rotateAngleX = Head.rotateAngleX;
            LftHorn.rotateAngleX = Head.rotateAngleX;
            LftHornTip.rotateAngleX = Head.rotateAngleX;
            
            Brow.rotateAngleY = Head.rotateAngleY;
            NoseBridge.rotateAngleY = Head.rotateAngleY;
            Nose.rotateAngleY = Head.rotateAngleY;
            RgtTusk.rotateAngleY = Head.rotateAngleY;
            RgtTooth.rotateAngleY = Head.rotateAngleY;
            LftTooth.rotateAngleY = Head.rotateAngleY;
            LftTusk.rotateAngleY = Head.rotateAngleY;
            Lip.rotateAngleY = Head.rotateAngleY;
            RgtEar.rotateAngleY = Head.rotateAngleY;
            RgtRing.rotateAngleY = Head.rotateAngleY;
            RgtRingHole.rotateAngleY = Head.rotateAngleY;
            LftEar.rotateAngleY = Head.rotateAngleY;
            LftRing.rotateAngleY = Head.rotateAngleY;
            LftRingHole.rotateAngleY = Head.rotateAngleY;
            HairRope.rotateAngleY = Head.rotateAngleY;
            Hair1.rotateAngleY = Head.rotateAngleY;
            Hair2.rotateAngleY = Head.rotateAngleY;
            Hair3.rotateAngleY = Head.rotateAngleY;
            DiamondHorn.rotateAngleY = Head.rotateAngleY;
            RgtHorn.rotateAngleY = Head.rotateAngleY;
            RgtHornTip.rotateAngleY = Head.rotateAngleY;
            LftHorn.rotateAngleY = Head.rotateAngleY;
            LftHornTip.rotateAngleY = Head.rotateAngleY;
        }else
        {
            
            Head3RgtEar.rotateAngleX = Head3.rotateAngleX;
            Head3LftEar.rotateAngleX = Head3.rotateAngleX;
            Head3Eyelid.rotateAngleX = 0.2617994F + Head3.rotateAngleX;
            Head3Nose.rotateAngleX = 0.4886922F + Head3.rotateAngleX;
            Head3Brow.rotateAngleX = -0.2617994F + Head3.rotateAngleX;
            Head3Hair.rotateAngleX = -0.6108652F + Head3.rotateAngleX;
            Head3Lip.rotateAngleX = 0.1745329F + Head3.rotateAngleX;
            Head3RgtTusk.rotateAngleX = 0.1745329F + Head3.rotateAngleX;
            Head3RgtTooth.rotateAngleX = 0.1745329F + Head3.rotateAngleX;
            Head3LftTooth.rotateAngleX = 0.1745329F + Head3.rotateAngleX;
            Head3LftTusk.rotateAngleX = 0.1745329F + Head3.rotateAngleX;
            Head3RingHole.rotateAngleX = Head3.rotateAngleX;
            Head3Ring.rotateAngleX = Head3.rotateAngleX;
            
            Head3RgtEar.rotateAngleY = Head3.rotateAngleY;
            Head3LftEar.rotateAngleY = Head3.rotateAngleY;
            Head3Eyelid.rotateAngleY = Head3.rotateAngleY;
            Head3Nose.rotateAngleY = Head3.rotateAngleY;
            Head3Brow.rotateAngleY = Head3.rotateAngleY;
            Head3Hair.rotateAngleY = Head3.rotateAngleY;
            Head3Lip.rotateAngleY = Head3.rotateAngleY;
            Head3RgtTusk.rotateAngleY = Head3.rotateAngleY;
            Head3RgtTooth.rotateAngleY = Head3.rotateAngleY;
            Head3LftTooth.rotateAngleY = Head3.rotateAngleY;
            Head3LftTusk.rotateAngleY = Head3.rotateAngleY;
            Head3RingHole.rotateAngleY = Head3.rotateAngleY;
            Head3Ring.rotateAngleY = Head3.rotateAngleY;
            
            Head2Chin.rotateAngleX = 0.2617994F + Head2.rotateAngleX;
            Head2Lip.rotateAngleX = Head2.rotateAngleX;
            Head2LftTusk.rotateAngleX = 0.1745329F + Head2.rotateAngleX;
            Head2RgtTusk.rotateAngleX = 0.1745329F +  Head2.rotateAngleX;
            Head2Nose.rotateAngleX = 0.0872665F + Head2.rotateAngleX;
            Head2NoseBridge.rotateAngleX = -0.1745329F + Head2.rotateAngleX;
            Head2Brow.rotateAngleX = -0.0872665F + Head2.rotateAngleX;
            Head2RgtHorn.rotateAngleX = -0.5235988F + Head2.rotateAngleX;
            Head2LftHorn.rotateAngleX = -0.5235988F + Head2.rotateAngleX;
            Head2DiamondHorn.rotateAngleX = 0.0872665F + Head2.rotateAngleX;
            
            Head2Chin.rotateAngleY = Head2.rotateAngleY;
            Head2Lip.rotateAngleY = Head2.rotateAngleY;
            Head2LftTusk.rotateAngleY = Head2.rotateAngleY;
            Head2RgtTusk.rotateAngleY = Head2.rotateAngleY;
            Head2Nose.rotateAngleY = Head2.rotateAngleY;
            Head2NoseBridge.rotateAngleY = Head2.rotateAngleY;
            Head2Brow.rotateAngleY = Head2.rotateAngleY;
            Head2RgtHorn.rotateAngleY = Head2.rotateAngleY;
            Head2LftHorn.rotateAngleY = Head2.rotateAngleY;
            Head2DiamondHorn.rotateAngleY = Head2.rotateAngleY;
        }
        
        
        
        
    }

}
