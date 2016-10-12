package drzhark.mocreatures.client.model;

import drzhark.mocreatures.entity.monster.MoCEntityOgre;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCModelOgre extends ModelBase {

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
    private int type;
    private int attackCounter;
    private int headMoving;
    private int armToAnimate;

    public MoCModelOgre() {
        this.textureWidth = 128;
        this.textureHeight = 128;

        this.Head = new ModelRenderer(this, 80, 0);
        this.Head.addBox(-6F, -12F, -6F, 12, 12, 12);
        this.Head.setRotationPoint(0F, -13F, 0F);

        this.Brow = new ModelRenderer(this, 68, 7);
        this.Brow.addBox(-5F, -10.5F, -8F, 10, 3, 2);
        this.Brow.setRotationPoint(0F, -13F, 0F);
        setRotation(this.Brow, -0.0872665F, 0F, 0F);

        this.NoseBridge = new ModelRenderer(this, 80, 4);
        this.NoseBridge.addBox(-1F, -7F, -8F, 2, 2, 1);
        this.NoseBridge.setRotationPoint(0F, -13F, 0F);
        setRotation(this.NoseBridge, -0.1745329F, 0F, 0F);

        this.Nose = new ModelRenderer(this, 80, 0);
        this.Nose.addBox(-2F, -7F, -7F, 4, 2, 2);
        this.Nose.setRotationPoint(0F, -13F, 0F);
        setRotation(this.Nose, 0.0872665F, 0F, 0F);

        this.RgtTusk = new ModelRenderer(this, 60, 4);
        this.RgtTusk.addBox(-3.5F, -6F, -6.5F, 1, 2, 1);
        this.RgtTusk.setRotationPoint(0F, -13F, 0F);
        setRotation(this.RgtTusk, 0.1745329F, 0F, 0F);

        this.RgtTooth = new ModelRenderer(this, 64, 4);
        this.RgtTooth.addBox(-1.5F, -5F, -6.5F, 1, 1, 1);
        this.RgtTooth.setRotationPoint(0F, -13F, 0F);
        setRotation(this.RgtTooth, 0.1745329F, 0F, 0F);

        this.LftTooth = new ModelRenderer(this, 72, 4);
        this.LftTooth.addBox(0.5F, -5F, -6.5F, 1, 1, 1);
        this.LftTooth.setRotationPoint(0F, -13F, 0F);
        setRotation(this.LftTooth, 0.1745329F, 0F, 0F);

        this.LftTusk = new ModelRenderer(this, 76, 4);
        this.LftTusk.addBox(2.5F, -6F, -6.5F, 1, 2, 1);
        this.LftTusk.setRotationPoint(0F, -13F, 0F);
        setRotation(this.LftTusk, 0.1745329F, 0F, 0F);

        this.Lip = new ModelRenderer(this, 60, 0);
        this.Lip.addBox(-4F, -4F, -7F, 8, 2, 2);
        this.Lip.setRotationPoint(0F, -13F, 0F);
        setRotation(this.Lip, 0.1745329F, 0F, 0F);

        this.RgtEar = new ModelRenderer(this, 60, 12);
        this.RgtEar.addBox(-9F, -9F, -1F, 3, 5, 2);
        this.RgtEar.setRotationPoint(0F, -13F, 0F);

        this.RgtRing = new ModelRenderer(this, 32, 58);
        this.RgtRing.addBox(-8F, -6F, -2F, 1, 4, 4);
        this.RgtRing.setRotationPoint(0F, -13F, 0F);

        this.RgtRingHole = new ModelRenderer(this, 26, 50);
        this.RgtRingHole.addBox(-8F, -5F, -1F, 1, 2, 2);
        this.RgtRingHole.setRotationPoint(0F, -13F, 0F);

        this.LftEar = new ModelRenderer(this, 70, 12);
        this.LftEar.addBox(6F, -9F, -1F, 3, 5, 2);
        this.LftEar.setRotationPoint(0F, -13F, 0F);

        this.LftRing = new ModelRenderer(this, 32, 58);
        this.LftRing.addBox(7F, -6F, -2F, 1, 4, 4);
        this.LftRing.setRotationPoint(0F, -13F, 0F);

        this.LftRingHole = new ModelRenderer(this, 26, 50);
        this.LftRingHole.addBox(7F, -5F, -1F, 1, 2, 2);
        this.LftRingHole.setRotationPoint(0F, -13F, 0F);

        this.HairRope = new ModelRenderer(this, 82, 83);
        this.HairRope.addBox(-2F, -8F, 9F, 4, 4, 4);
        this.HairRope.setRotationPoint(0F, -13F, 0F);
        setRotation(this.HairRope, 0.6108652F, 0F, 0F);

        this.Hair1 = new ModelRenderer(this, 78, 107);
        this.Hair1.addBox(-3F, -9F, 13F, 6, 8, 3);
        this.Hair1.setRotationPoint(0F, -13F, 0F);
        setRotation(this.Hair1, 0.6108652F, 0F, 0F);

        this.Hair2 = new ModelRenderer(this, 60, 107);
        this.Hair2.addBox(-3F, -6.5F, 11.6F, 6, 8, 3);
        this.Hair2.setRotationPoint(0F, -13F, 0F);
        setRotation(this.Hair2, 0.2617994F, 0F, 0F);

        this.Hair3 = new ModelRenderer(this, 42, 107);
        this.Hair3.addBox(-3F, -2.4F, 11.4F, 6, 8, 3);
        this.Hair3.setRotationPoint(0F, -13F, 0F);

        this.DiamondHorn = new ModelRenderer(this, 120, 31);
        this.DiamondHorn.addBox(-1F, -17F, -6F, 2, 6, 2);
        this.DiamondHorn.setRotationPoint(0F, -13F, 0F);
        setRotation(this.DiamondHorn, 0.0872665F, 0F, 0F);

        this.RgtHorn = new ModelRenderer(this, 46, 6);
        this.RgtHorn.addBox(-6F, -12F, -11F, 2, 2, 5);
        this.RgtHorn.setRotationPoint(0F, -13F, 0F);

        this.RgtHornTip = new ModelRenderer(this, 44, 13);
        this.RgtHornTip.addBox(-6F, -15F, -11F, 2, 3, 2);
        this.RgtHornTip.setRotationPoint(0F, -13F, 0F);

        this.LftHorn = new ModelRenderer(this, 46, 6);
        this.LftHorn.addBox(4F, -12F, -11F, 2, 2, 5);
        this.LftHorn.setRotationPoint(0F, -13F, 0F);

        this.LftHornTip = new ModelRenderer(this, 52, 13);
        this.LftHornTip.addBox(4F, -15F, -11F, 2, 3, 2);
        this.LftHornTip.setRotationPoint(0F, -13F, 0F);

        this.NeckRest = new ModelRenderer(this, 39, 20);
        this.NeckRest.addBox(-7F, -19F, -3F, 14, 3, 11);
        this.NeckRest.setRotationPoint(0F, 5F, 0F);

        this.Chest = new ModelRenderer(this, 32, 34);
        this.Chest.addBox(-9.5F, -17.8F, -7.3F, 19, 11, 13);
        this.Chest.setRotationPoint(0F, 5F, 0F);

        setRotation(this.Chest, -0.1745329F, 0F, 0F);
        this.Stomach = new ModelRenderer(this, 28, 58);
        this.Stomach.addBox(-11F, -8F, -6F, 22, 11, 14);
        this.Stomach.setRotationPoint(0F, 5F, 0F);

        this.ButtCover = new ModelRenderer(this, 32, 118);
        this.ButtCover.addBox(-4F, 0F, 0F, 8, 8, 2);
        this.ButtCover.setRotationPoint(0F, 8F, 6F);

        this.LoinCloth = new ModelRenderer(this, 32, 118);
        this.LoinCloth.addBox(-4F, 0F, -2F, 8, 8, 2);
        this.LoinCloth.setRotationPoint(0F, 8F, -4F);

        this.RgtThigh = new ModelRenderer(this, 0, 83);
        this.RgtThigh.addBox(-10F, 0F, -5F, 10, 11, 10);
        this.RgtThigh.setRotationPoint(-2F, 4F, 1F);

        this.RgtLeg = new ModelRenderer(this, 0, 104);
        this.RgtLeg.addBox(-4F, -1F, -4F, 8, 11, 8);
        this.RgtLeg.setRotationPoint(-5F, 10F, 0F);
        this.RgtThigh.addChild(this.RgtLeg);

        this.RgtKnee = new ModelRenderer(this, 0, 88);
        this.RgtKnee.addBox(-2F, -2F, -0.5F, 4, 4, 1);
        this.RgtKnee.setRotationPoint(0F, 2F, -4.25F);
        this.RgtLeg.addChild(this.RgtKnee);

        this.RgtToes = new ModelRenderer(this, 0, 123);
        this.RgtToes.addBox(-2.5F, -1F, -3F, 5, 2, 3);
        this.RgtToes.setRotationPoint(-1.5F, 9F, -3.5F);
        this.RgtLeg.addChild(this.RgtToes);

        this.RgtBigToe = new ModelRenderer(this, 20, 123);
        this.RgtBigToe.addBox(-1.5F, -1F, -3F, 3, 2, 3);
        this.RgtBigToe.setRotationPoint(2.5F, 9F, -4F);
        this.RgtLeg.addChild(this.RgtBigToe);

        this.LftThigh = new ModelRenderer(this, 88, 83);
        this.LftThigh.addBox(0F, 0F, -5F, 10, 11, 10);
        this.LftThigh.setRotationPoint(2F, 4F, 1F);

        this.LftLeg = new ModelRenderer(this, 96, 104);
        this.LftLeg.addBox(-4F, -1F, -4F, 8, 11, 8);
        this.LftLeg.setRotationPoint(5F, 10F, 0F);
        this.LftThigh.addChild(this.LftLeg);

        this.LftKnee = new ModelRenderer(this, 118, 88);
        this.LftKnee.addBox(-2F, -2F, -0.5F, 4, 4, 1);
        this.LftKnee.setRotationPoint(0F, 2F, -4.25F);
        this.LftLeg.addChild(this.LftKnee);

        this.LftToes = new ModelRenderer(this, 112, 123);
        this.LftToes.addBox(-2.5F, -1F, -3F, 5, 2, 3);
        this.LftToes.setRotationPoint(1.5F, 9F, -3.5F);
        this.LftLeg.addChild(this.LftToes);

        this.LftBigToe = new ModelRenderer(this, 96, 123);
        this.LftBigToe.addBox(-1.5F, -1F, -3F, 3, 2, 3);
        this.LftBigToe.setRotationPoint(-2.5F, 9F, -4F);
        this.LftLeg.addChild(this.LftBigToe);

        //LEFT ARM
        this.LftShoulder = new ModelRenderer(this, 96, 31);
        this.LftShoulder.addBox(0F, -3F, -4F, 8, 7, 8);
        this.LftShoulder.setRotationPoint(7F, -10F, 2F);

        this.LftArm = new ModelRenderer(this, 100, 66);
        this.LftArm.addBox(0F, 0F, -4F, 6, 9, 8);
        this.LftArm.setRotationPoint(6F, -1F, 1F);
        this.LftShoulder.addChild(this.LftArm);

        this.LftHand = new ModelRenderer(this, 96, 46);
        this.LftHand.addBox(-4F, 0F, -4F, 8, 12, 8);
        this.LftHand.setRotationPoint(3F, 8F, -1F);
        this.LftArm.addChild(this.LftHand);

        this.LftElbow = new ModelRenderer(this, 86, 64);
        this.LftElbow.addBox(-2F, -1.5F, -0.5F, 4, 3, 1);
        this.LftElbow.setRotationPoint(0F, 2.5F, 4F);
        this.LftHand.addChild(this.LftElbow);

        this.LftWeaponRoot = new ModelRenderer(this, 24, 104);
        this.LftWeaponRoot.addBox(-1.5F, -1.5F, -4F, 3, 3, 4);
        this.LftWeaponRoot.setRotationPoint(-0.5F, 8.5F, -4F);
        this.LftHand.addChild(this.LftWeaponRoot);

        this.LftWeaponEnd = new ModelRenderer(this, 74, 90);
        this.LftWeaponEnd.addBox(-1.5F, -1.5F, 0F, 3, 3, 2);
        this.LftWeaponEnd.setRotationPoint(0F, 0F, 8F);
        this.LftWeaponRoot.addChild(this.LftWeaponEnd);

        this.LftWeaponLump = new ModelRenderer(this, 30, 83);
        this.LftWeaponLump.addBox(-2.5F, -2.5F, -4F, 5, 5, 4);
        this.LftWeaponLump.setRotationPoint(0F, 0F, -4F);
        this.LftWeaponRoot.addChild(this.LftWeaponLump);

        this.LftWeaponBetween = new ModelRenderer(this, 83, 42);
        this.LftWeaponBetween.addBox(-1.5F, -1.5F, -2F, 3, 3, 2);
        this.LftWeaponBetween.setRotationPoint(0F, 0F, -4F);
        this.LftWeaponLump.addChild(this.LftWeaponBetween);

        this.LftWeaponTip = new ModelRenderer(this, 60, 118);
        this.LftWeaponTip.addBox(-2.5F, -2.5F, -5F, 5, 5, 5);
        this.LftWeaponTip.setRotationPoint(0F, 0F, -2F);
        this.LftWeaponBetween.addChild(this.LftWeaponTip);

        this.LftHammerNeck = new ModelRenderer(this, 32, 39);
        this.LftHammerNeck.addBox(-0.5F, -4F, -4F, 1, 4, 4);
        this.LftHammerNeck.setRotationPoint(0F, -2.5F, -1F);
        this.LftWeaponTip.addChild(this.LftHammerNeck);

        this.LftHammerHeadSupport = new ModelRenderer(this, 0, 0);
        this.LftHammerHeadSupport.addBox(-1F, 0F, -2F, 2, 2, 4);
        this.LftHammerHeadSupport.setRotationPoint(0F, 2.5F, -3F);
        this.LftWeaponTip.addChild(this.LftHammerHeadSupport);

        this.LftHammerHead = new ModelRenderer(this, 32, 3);
        this.LftHammerHead.addBox(-2F, 0F, -2.5F, 4, 3, 5);
        this.LftHammerHead.setRotationPoint(0F, 2F, 0F);
        this.LftHammerHeadSupport.addChild(this.LftHammerHead);

        this.LftSpike = new ModelRenderer(this, 52, 118);
        this.LftSpike.addBox(-1F, -1F, -3F, 2, 2, 3);
        this.LftSpike.setRotationPoint(0F, 0F, -5F);
        this.LftWeaponTip.addChild(this.LftSpike);

        this.LftSpike1 = new ModelRenderer(this, 52, 118);
        this.LftSpike1.addBox(-3F, -1F, -1F, 3, 2, 2);
        this.LftSpike1.setRotationPoint(-2.5F, 0F, -3F);
        this.LftWeaponTip.addChild(this.LftSpike1);

        this.LftSpike2 = new ModelRenderer(this, 52, 118);
        this.LftSpike2.addBox(3F, -1F, -1F, 3, 2, 2);
        this.LftSpike2.setRotationPoint(-0.5F, 0F, -3F);
        this.LftWeaponTip.addChild(this.LftSpike2);

        this.LftSpike3 = new ModelRenderer(this, 52, 118);
        this.LftSpike3.addBox(-1F, 0F, -1F, 2, 3, 2);
        this.LftSpike3.setRotationPoint(0F, 2.5F, -3F);
        this.LftWeaponTip.addChild(this.LftSpike3);

        this.LftSpike4 = new ModelRenderer(this, 52, 118);
        this.LftSpike4.addBox(-1F, -3F, -1F, 2, 3, 2);
        this.LftSpike4.setRotationPoint(0F, -2.5F, -3F);
        this.LftWeaponTip.addChild(this.LftSpike4);

        //RIGHT ARM
        this.RgtShoulder = new ModelRenderer(this, 0, 31);
        this.RgtShoulder.addBox(0F, -3F, -4F, 8, 7, 8);
        this.RgtShoulder.setRotationPoint(-15F, -10F, 2F);

        this.RgtArm = new ModelRenderer(this, 0, 66);
        this.RgtArm.addBox(0F, 0F, -4F, 6, 9, 8);
        this.RgtArm.setRotationPoint(-4F, -1F, 1F);
        this.RgtShoulder.addChild(this.RgtArm);

        this.RgtHand = new ModelRenderer(this, 0, 46);
        this.RgtHand.addBox(-4F, 0F, -4F, 8, 12, 8);
        this.RgtHand.setRotationPoint(3F, 8F, -1F);
        this.RgtArm.addChild(this.RgtHand);

        this.RgtElbow = new ModelRenderer(this, 86, 64);
        this.RgtElbow.addBox(-2F, -1.5F, -0.5F, 4, 3, 1);
        this.RgtElbow.setRotationPoint(0F, 2.5F, 4F);
        this.RgtHand.addChild(this.RgtElbow);

        this.RgtWeaponRoot = new ModelRenderer(this, 24, 104);
        this.RgtWeaponRoot.addBox(-1.5F, -1.5F, -4F, 3, 3, 4);
        this.RgtWeaponRoot.setRotationPoint(-0.5F, 8.5F, -4F);
        this.RgtHand.addChild(this.RgtWeaponRoot);

        this.RgtWeaponEnd = new ModelRenderer(this, 74, 90);
        this.RgtWeaponEnd.addBox(-1.5F, -1.5F, 0F, 3, 3, 2);
        this.RgtWeaponEnd.setRotationPoint(0F, 0F, 8F);
        this.RgtWeaponRoot.addChild(this.RgtWeaponEnd);

        this.RgtWeaponLump = new ModelRenderer(this, 30, 83);
        this.RgtWeaponLump.addBox(-2.5F, -2.5F, -4F, 5, 5, 4);
        this.RgtWeaponLump.setRotationPoint(0F, 0F, -4F);
        this.RgtWeaponRoot.addChild(this.RgtWeaponLump);

        this.RgtWeaponBetween = new ModelRenderer(this, 83, 42);
        this.RgtWeaponBetween.addBox(-1.5F, -1.5F, -2F, 3, 3, 2);
        this.RgtWeaponBetween.setRotationPoint(0F, 0F, -4F);
        this.RgtWeaponLump.addChild(this.RgtWeaponBetween);

        this.RgtWeaponTip = new ModelRenderer(this, 60, 118);
        this.RgtWeaponTip.addBox(-2.5F, -2.5F, -5F, 5, 5, 5);
        this.RgtWeaponTip.setRotationPoint(0F, 0F, -2F);
        this.RgtWeaponBetween.addChild(this.RgtWeaponTip);

        this.RgtHammerNeck = new ModelRenderer(this, 32, 39);
        this.RgtHammerNeck.addBox(-0.5F, -4F, -4F, 1, 4, 4);
        this.RgtHammerNeck.setRotationPoint(0F, -2.5F, -1F);
        this.RgtWeaponTip.addChild(this.RgtHammerNeck);

        this.RgtHammerHeadSupport = new ModelRenderer(this, 0, 0);
        this.RgtHammerHeadSupport.addBox(-1F, 0F, -2F, 2, 2, 4);
        this.RgtHammerHeadSupport.setRotationPoint(0F, 2.5F, -3F);
        this.RgtWeaponTip.addChild(this.RgtHammerHeadSupport);

        this.RgtHammerHead = new ModelRenderer(this, 32, 3);
        this.RgtHammerHead.addBox(-2F, 0F, -2.5F, 4, 3, 5);
        this.RgtHammerHead.setRotationPoint(0F, 2F, 0F);
        this.RgtHammerHeadSupport.addChild(this.RgtHammerHead);

        this.RgtSpike = new ModelRenderer(this, 52, 118);
        this.RgtSpike.addBox(-1F, -1F, -3F, 2, 2, 3);
        this.RgtSpike.setRotationPoint(0F, 0F, -5F);
        this.RgtWeaponTip.addChild(this.RgtSpike);

        this.RgtSpike1 = new ModelRenderer(this, 52, 118);
        this.RgtSpike1.addBox(-3F, -1F, -1F, 3, 2, 2);
        this.RgtSpike1.setRotationPoint(-2.5F, 0F, -3F);
        this.RgtWeaponTip.addChild(this.RgtSpike1);

        this.RgtSpike2 = new ModelRenderer(this, 52, 118);
        this.RgtSpike2.addBox(3F, -1F, -1F, 3, 2, 2);
        this.RgtSpike2.setRotationPoint(-0.5F, 0F, -3F);
        this.RgtWeaponTip.addChild(this.RgtSpike2);

        this.RgtSpike3 = new ModelRenderer(this, 52, 118);
        this.RgtSpike3.addBox(-1F, 0F, -1F, 2, 3, 2);
        this.RgtSpike3.setRotationPoint(0F, 2.5F, -3F);
        this.RgtWeaponTip.addChild(this.RgtSpike3);

        this.RgtSpike4 = new ModelRenderer(this, 52, 118);
        this.RgtSpike4.addBox(-1F, -3F, -1F, 2, 3, 2);
        this.RgtSpike4.setRotationPoint(0F, -2.5F, -3F);
        this.RgtWeaponTip.addChild(this.RgtSpike4);

        this.Head3RgtEar = new ModelRenderer(this, 110, 24);
        this.Head3RgtEar.addBox(-8F, -9F, -1F, 3, 5, 2);
        this.Head3RgtEar.setRotationPoint(7F, -13F, 0F);

        this.Head3LftEar = new ModelRenderer(this, 100, 24);
        this.Head3LftEar.addBox(5F, -9F, -1F, 3, 5, 2);
        this.Head3LftEar.setRotationPoint(7F, -13F, 0F);

        this.Head3Eyelid = new ModelRenderer(this, 46, 3);
        this.Head3Eyelid.addBox(-3F, -8F, -4.5F, 6, 2, 1);
        this.Head3Eyelid.setRotationPoint(7F, -13F, 0F);
        setRotation(this.Head3Eyelid, 0.2617994F, 0F, 0F);

        this.Head3Nose = new ModelRenderer(this, 60, 9);
        this.Head3Nose.addBox(-1.5F, -8.5F, -3.5F, 3, 2, 1);
        this.Head3Nose.setRotationPoint(7F, -13F, 0F);
        setRotation(this.Head3Nose, 0.4886922F, 0F, 0F);

        this.Head3 = new ModelRenderer(this, 42, 83);
        this.Head3.addBox(-5F, -12F, -6F, 10, 12, 12);
        this.Head3.setRotationPoint(7F, -13F, 0F);

        this.Head3Brow = new ModelRenderer(this, 46, 0);
        this.Head3Brow.addBox(-3F, -9F, -8.5F, 6, 2, 1);
        this.Head3Brow.setRotationPoint(7F, -13F, 0F);
        setRotation(this.Head3Brow, -0.2617994F, 0F, 0F);

        this.Head3Hair = new ModelRenderer(this, 80, 118);
        this.Head3Hair.addBox(-2F, -17F, -5F, 4, 6, 4);
        this.Head3Hair.setRotationPoint(7F, -13F, 0F);
        setRotation(this.Head3Hair, -0.6108652F, 0F, 0F);

        this.Head3Lip = new ModelRenderer(this, 22, 68);
        this.Head3Lip.addBox(-4F, -4F, -7F, 8, 2, 2);
        this.Head3Lip.setRotationPoint(7F, -13F, 0F);
        setRotation(this.Head3Lip, 0.1745329F, 0F, 0F);

        this.Head3RgtTusk = new ModelRenderer(this, 83, 34);
        this.Head3RgtTusk.addBox(-3.5F, -6F, -6.5F, 1, 2, 1);
        this.Head3RgtTusk.setRotationPoint(7F, -13F, 0F);

        setRotation(this.Head3RgtTusk, 0.1745329F, 0F, 0F);
        this.Head3RgtTooth = new ModelRenderer(this, 87, 34);
        this.Head3RgtTooth.addBox(-1.5F, -5F, -6.5F, 1, 1, 1);
        this.Head3RgtTooth.setRotationPoint(7F, -13F, 0F);
        setRotation(this.Head3RgtTooth, 0.1745329F, 0F, 0F);

        this.Head3LftTooth = new ModelRenderer(this, 96, 34);
        this.Head3LftTooth.addBox(0.5F, -5F, -6.5F, 1, 1, 1);
        this.Head3LftTooth.setRotationPoint(7F, -13F, 0F);
        setRotation(this.Head3LftTooth, 0.1745329F, 0F, 0F);

        this.Head3LftTusk = new ModelRenderer(this, 100, 34);
        this.Head3LftTusk.addBox(2.5F, -6F, -6.5F, 1, 2, 1);
        this.Head3LftTusk.setRotationPoint(7F, -13F, 0F);
        setRotation(this.Head3LftTusk, 0.1745329F, 0F, 0F);

        this.Head3RingHole = new ModelRenderer(this, 26, 50);
        this.Head3RingHole.addBox(6F, -5F, -1F, 1, 2, 2);
        this.Head3RingHole.setRotationPoint(7F, -13F, 0F);

        this.Head3Ring = new ModelRenderer(this, 32, 58);
        this.Head3Ring.addBox(6F, -6F, -2F, 1, 4, 4);
        this.Head3Ring.setRotationPoint(7F, -13F, 0F);

        this.Head2Chin = new ModelRenderer(this, 21, 24);
        this.Head2Chin.addBox(-3F, -5F, -8F, 6, 3, 3);
        this.Head2Chin.setRotationPoint(-7F, -13F, 0F);
        setRotation(this.Head2Chin, 0.2617994F, 0F, 0F);

        this.Head2 = new ModelRenderer(this, 0, 0);
        this.Head2.addBox(-5F, -12F, -6F, 10, 12, 12);
        this.Head2.setRotationPoint(-7F, -13F, 0F);

        this.Head2Lip = new ModelRenderer(this, 0, 24);
        this.Head2Lip.addBox(-4F, -5F, -8F, 8, 2, 2);
        this.Head2Lip.setRotationPoint(-7F, -13F, 0F);

        this.Head2LftTusk = new ModelRenderer(this, 46, 28);
        this.Head2LftTusk.addBox(2.5F, -8F, -6.5F, 1, 2, 1);
        this.Head2LftTusk.setRotationPoint(-7F, -13F, 0F);
        setRotation(this.Head2LftTusk, 0.1745329F, 0F, 0F);

        this.Head2RgtTusk = new ModelRenderer(this, 39, 28);
        this.Head2RgtTusk.addBox(-3.5F, -8F, -6.5F, 1, 2, 1);
        this.Head2RgtTusk.setRotationPoint(-7F, -13F, 0F);
        setRotation(this.Head2RgtTusk, 0.1745329F, 0F, 0F);

        this.Head2Nose = new ModelRenderer(this, 116, 0);
        this.Head2Nose.addBox(-2F, -7F, -7F, 4, 2, 2);
        this.Head2Nose.setRotationPoint(-7F, -13F, 0F);
        setRotation(this.Head2Nose, 0.0872665F, 0F, 0F);

        this.Head2NoseBridge = new ModelRenderer(this, 116, 4);
        this.Head2NoseBridge.addBox(-1F, -7F, -8F, 2, 2, 1);
        this.Head2NoseBridge.setRotationPoint(-7F, -13F, 0F);
        setRotation(this.Head2NoseBridge, -0.1745329F, 0F, 0F);

        this.Head2Brow = new ModelRenderer(this, 80, 24);
        this.Head2Brow.addBox(-4F, -10.5F, -8F, 8, 3, 2);
        this.Head2Brow.setRotationPoint(-7F, -13F, 0F);
        setRotation(this.Head2Brow, -0.0872665F, 0F, 0F);

        this.Head2RgtHorn = new ModelRenderer(this, 24, 30);
        this.Head2RgtHorn.addBox(-4F, -8F, -15F, 2, 2, 5);
        this.Head2RgtHorn.setRotationPoint(-7F, -13F, 0F);
        setRotation(this.Head2RgtHorn, -0.5235988F, 0F, 0F);

        this.Head2LftHorn = new ModelRenderer(this, 24, 30);
        this.Head2LftHorn.addBox(2F, -8F, -15F, 2, 2, 5);
        this.Head2LftHorn.setRotationPoint(-7F, -13F, 0F);
        setRotation(this.Head2LftHorn, -0.5235988F, 0F, 0F);

        this.Head2DiamondHorn = new ModelRenderer(this, 120, 46);
        this.Head2DiamondHorn.addBox(-1F, -17F, -6F, 2, 6, 2);
        this.Head2DiamondHorn.setRotationPoint(-7F, -13F, 0F);
        setRotation(this.Head2DiamondHorn, 0.0872665F, 0F, 0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        //super.render(entity, f, f1, f2, f3, f4, f5);
        MoCEntityOgre entityogre = (MoCEntityOgre) entity;
        this.type = entityogre.getType();
        //int leftAttack = entityogre.attackCounterLeft;
        //int rightAttack = entityogre.attackCounterRight;
        this.attackCounter = entityogre.attackCounter;
        this.headMoving = entityogre.getMovingHead();
        this.armToAnimate = entityogre.armToAnimate;

        setRotationAngles(f, f1, f2, f3, f4, f5);

        if (type == 1) {
            this.Head.render(f5);
            this.Brow.render(f5);
            this.NoseBridge.render(f5);
            this.Nose.render(f5);
            this.RgtTusk.render(f5);
            this.RgtTooth.render(f5);
            this.LftTooth.render(f5);
            this.LftTusk.render(f5);
            this.Lip.render(f5);
            this.RgtEar.render(f5);
            this.RgtRing.render(f5);
            this.RgtRingHole.render(f5);
            this.LftEar.render(f5);
            this.LftRing.render(f5);
            this.LftRingHole.render(f5);
            this.HairRope.render(f5);
            this.Hair1.render(f5);
            this.Hair2.render(f5);
            this.Hair3.render(f5);
            this.DiamondHorn.render(f5);
            this.RgtHorn.render(f5);
            this.RgtHornTip.render(f5);
            this.LftHorn.render(f5);
            this.LftHornTip.render(f5);

            this.LftWeaponRoot.isHidden = true;
        } else {
            this.Head3RgtEar.render(f5);
            this.Head3LftEar.render(f5);
            this.Head3Eyelid.render(f5);
            this.Head3Nose.render(f5);
            this.Head3.render(f5);
            this.Head3Brow.render(f5);
            this.Head3Hair.render(f5);
            this.Head3Lip.render(f5);
            this.Head3RgtTusk.render(f5);
            this.Head3RgtTooth.render(f5);
            this.Head3LftTooth.render(f5);
            this.Head3LftTusk.render(f5);
            this.Head3RingHole.render(f5);
            this.Head3Ring.render(f5);

            this.Head2Chin.render(f5);
            this.Head2.render(f5);
            this.Head2Lip.render(f5);
            this.Head2LftTusk.render(f5);
            this.Head2RgtTusk.render(f5);
            this.Head2Nose.render(f5);
            this.Head2NoseBridge.render(f5);
            this.Head2Brow.render(f5);
            this.Head2RgtHorn.render(f5);
            this.Head2LftHorn.render(f5);
            this.Head2DiamondHorn.render(f5);

            this.LftWeaponRoot.isHidden = false;
        }

        this.NeckRest.render(f5);
        this.Chest.render(f5);
        this.Stomach.render(f5);
        this.ButtCover.render(f5);
        this.LoinCloth.render(f5);
        this.RgtThigh.render(f5);
        this.LftThigh.render(f5);
        this.RgtShoulder.render(f5);
        this.LftShoulder.render(f5);

    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
        float hRotY = f3 / 57.29578F;
        float hRotX = f4 / 57.29578F;

        float RLegXRot = MathHelper.cos((f * 0.6662F) + 3.141593F) * 0.8F * f1;
        float LLegXRot = MathHelper.cos(f * 0.6662F) * 0.8F * f1;
        float ClothRot = MathHelper.cos(f * 0.9F) * 0.6F * f1;

        float RLegXRotB = RLegXRot;
        float LLegXRotB = LLegXRot;

        this.RgtThigh.rotateAngleX = RLegXRot;
        this.LftThigh.rotateAngleX = LLegXRot;

        float RLegXRot2 = MathHelper.cos(((f + 0.1F) * 0.6662F) + 3.141593F) * 0.8F * f1;
        float LLegXRot2 = MathHelper.cos((f + 0.1F) * 0.6662F) * 0.8F * f1;
        if (f1 > 0.15F) {
            if (RLegXRot > RLegXRot2) // - - >
            {
                RLegXRotB = RLegXRot + (25 / 57.29578F);

            }
            /*
             * if (RLegXRot < RLegXRot2) // < - - { RLegXRotC = RLegXRot + (15 /
             * 57.29578F); }
             */
            if (LLegXRot > LLegXRot2) // - - >
            {
                LLegXRotB = LLegXRot + (25 / 57.29578F);
            }
            /*
             * if (LLegXRot < LLegXRot2) // < - - { LLegXRotC = LLegXRot + (15 /
             * 57.29578F); }
             */
        }

        this.LftLeg.rotateAngleX = (LLegXRotB);
        this.RgtLeg.rotateAngleX = (RLegXRotB);
        this.LoinCloth.rotateAngleX = ClothRot;
        this.ButtCover.rotateAngleX = ClothRot;

        float armMov = -(MathHelper.cos((attackCounter) * 0.18F) * 3F);

        //leftArm 
        //attacking with left arm
        if (this.armToAnimate == 1 || this.armToAnimate == 3) {
            this.LftShoulder.rotateAngleX = +armMov;
            this.LftHand.rotateAngleX = (-45F / this.radianF);
        } else //normal left arm movement
        {
            this.LftShoulder.rotateAngleZ = (MathHelper.cos(f2 * 0.09F) * 0.05F) - 0.05F;
            this.LftShoulder.rotateAngleX = RLegXRot;
            this.LftHand.rotateAngleX = 0F;
        }

        //rightArm
        //attacking with right arm
        if (this.armToAnimate == 2 || this.armToAnimate == 3) {
            this.RgtShoulder.rotateAngleX = +armMov;
            this.RgtHand.rotateAngleX = (-45F / this.radianF);
        } else //normal right arm movement
        {
            this.RgtShoulder.rotateAngleZ = -(MathHelper.cos(f2 * 0.09F) * 0.05F) + 0.05F;
            this.RgtShoulder.rotateAngleX = LLegXRot;
            this.RgtHand.rotateAngleX = 0F;
        }

        if (headMoving == 2) {
            this.Head2.rotateAngleX = hRotX;
            this.Head2.rotateAngleY = hRotY;
        }

        if (headMoving == 3) {
            this.Head3.rotateAngleX = hRotX;
            this.Head3.rotateAngleY = hRotY;
        }

        if (type == 1 || type == 3 || type == 5) {
            this.Head.rotateAngleX = hRotX;
            this.Head.rotateAngleY = hRotY;

            this.Brow.rotateAngleX = this.Head.rotateAngleX;
            this.NoseBridge.rotateAngleX = this.Head.rotateAngleX;
            this.Nose.rotateAngleX = this.Head.rotateAngleX;
            this.RgtTusk.rotateAngleX = this.Head.rotateAngleX;
            this.RgtTooth.rotateAngleX = this.Head.rotateAngleX;
            this.LftTooth.rotateAngleX = this.Head.rotateAngleX;
            this.LftTusk.rotateAngleX = this.Head.rotateAngleX;
            this.Lip.rotateAngleX = this.Head.rotateAngleX;
            this.RgtEar.rotateAngleX = this.Head.rotateAngleX;
            this.RgtRing.rotateAngleX = this.Head.rotateAngleX;
            this.RgtRingHole.rotateAngleX = this.Head.rotateAngleX;
            this.LftEar.rotateAngleX = this.Head.rotateAngleX;
            this.LftRing.rotateAngleX = this.Head.rotateAngleX;
            this.LftRingHole.rotateAngleX = this.Head.rotateAngleX;
            this.HairRope.rotateAngleX = 0.6108652F + this.Head.rotateAngleX;
            this.Hair1.rotateAngleX = 0.6108652F + this.Head.rotateAngleX;
            this.Hair2.rotateAngleX = 0.2617994F + this.Head.rotateAngleX;
            this.Hair3.rotateAngleX = this.Head.rotateAngleX;
            this.DiamondHorn.rotateAngleX = 0.0872665F + this.Head.rotateAngleX;
            this.RgtHorn.rotateAngleX = this.Head.rotateAngleX;
            this.RgtHornTip.rotateAngleX = this.Head.rotateAngleX;
            this.LftHorn.rotateAngleX = this.Head.rotateAngleX;
            this.LftHornTip.rotateAngleX = this.Head.rotateAngleX;

            this.Brow.rotateAngleY = this.Head.rotateAngleY;
            this.NoseBridge.rotateAngleY = this.Head.rotateAngleY;
            this.Nose.rotateAngleY = this.Head.rotateAngleY;
            this.RgtTusk.rotateAngleY = this.Head.rotateAngleY;
            this.RgtTooth.rotateAngleY = this.Head.rotateAngleY;
            this.LftTooth.rotateAngleY = this.Head.rotateAngleY;
            this.LftTusk.rotateAngleY = this.Head.rotateAngleY;
            this.Lip.rotateAngleY = this.Head.rotateAngleY;
            this.RgtEar.rotateAngleY = this.Head.rotateAngleY;
            this.RgtRing.rotateAngleY = this.Head.rotateAngleY;
            this.RgtRingHole.rotateAngleY = this.Head.rotateAngleY;
            this.LftEar.rotateAngleY = this.Head.rotateAngleY;
            this.LftRing.rotateAngleY = this.Head.rotateAngleY;
            this.LftRingHole.rotateAngleY = this.Head.rotateAngleY;
            this.HairRope.rotateAngleY = this.Head.rotateAngleY;
            this.Hair1.rotateAngleY = this.Head.rotateAngleY;
            this.Hair2.rotateAngleY = this.Head.rotateAngleY;
            this.Hair3.rotateAngleY = this.Head.rotateAngleY;
            this.DiamondHorn.rotateAngleY = this.Head.rotateAngleY;
            this.RgtHorn.rotateAngleY = this.Head.rotateAngleY;
            this.RgtHornTip.rotateAngleY = this.Head.rotateAngleY;
            this.LftHorn.rotateAngleY = this.Head.rotateAngleY;
            this.LftHornTip.rotateAngleY = this.Head.rotateAngleY;
        } else {

            this.Head3RgtEar.rotateAngleX = this.Head3.rotateAngleX;
            this.Head3LftEar.rotateAngleX = this.Head3.rotateAngleX;
            this.Head3Eyelid.rotateAngleX = 0.2617994F + this.Head3.rotateAngleX;
            this.Head3Nose.rotateAngleX = 0.4886922F + this.Head3.rotateAngleX;
            this.Head3Brow.rotateAngleX = -0.2617994F + this.Head3.rotateAngleX;
            this.Head3Hair.rotateAngleX = -0.6108652F + this.Head3.rotateAngleX;
            this.Head3Lip.rotateAngleX = 0.1745329F + this.Head3.rotateAngleX;
            this.Head3RgtTusk.rotateAngleX = 0.1745329F + this.Head3.rotateAngleX;
            this.Head3RgtTooth.rotateAngleX = 0.1745329F + this.Head3.rotateAngleX;
            this.Head3LftTooth.rotateAngleX = 0.1745329F + this.Head3.rotateAngleX;
            this.Head3LftTusk.rotateAngleX = 0.1745329F + this.Head3.rotateAngleX;
            this.Head3RingHole.rotateAngleX = this.Head3.rotateAngleX;
            this.Head3Ring.rotateAngleX = this.Head3.rotateAngleX;

            this.Head3RgtEar.rotateAngleY = this.Head3.rotateAngleY;
            this.Head3LftEar.rotateAngleY = this.Head3.rotateAngleY;
            this.Head3Eyelid.rotateAngleY = this.Head3.rotateAngleY;
            this.Head3Nose.rotateAngleY = this.Head3.rotateAngleY;
            this.Head3Brow.rotateAngleY = this.Head3.rotateAngleY;
            this.Head3Hair.rotateAngleY = this.Head3.rotateAngleY;
            this.Head3Lip.rotateAngleY = this.Head3.rotateAngleY;
            this.Head3RgtTusk.rotateAngleY = this.Head3.rotateAngleY;
            this.Head3RgtTooth.rotateAngleY = this.Head3.rotateAngleY;
            this.Head3LftTooth.rotateAngleY = this.Head3.rotateAngleY;
            this.Head3LftTusk.rotateAngleY = this.Head3.rotateAngleY;
            this.Head3RingHole.rotateAngleY = this.Head3.rotateAngleY;
            this.Head3Ring.rotateAngleY = this.Head3.rotateAngleY;

            this.Head2Chin.rotateAngleX = 0.2617994F + this.Head2.rotateAngleX;
            this.Head2Lip.rotateAngleX = this.Head2.rotateAngleX;
            this.Head2LftTusk.rotateAngleX = 0.1745329F + this.Head2.rotateAngleX;
            this.Head2RgtTusk.rotateAngleX = 0.1745329F + this.Head2.rotateAngleX;
            this.Head2Nose.rotateAngleX = 0.0872665F + this.Head2.rotateAngleX;
            this.Head2NoseBridge.rotateAngleX = -0.1745329F + this.Head2.rotateAngleX;
            this.Head2Brow.rotateAngleX = -0.0872665F + this.Head2.rotateAngleX;
            this.Head2RgtHorn.rotateAngleX = -0.5235988F + this.Head2.rotateAngleX;
            this.Head2LftHorn.rotateAngleX = -0.5235988F + this.Head2.rotateAngleX;
            this.Head2DiamondHorn.rotateAngleX = 0.0872665F + this.Head2.rotateAngleX;

            this.Head2Chin.rotateAngleY = this.Head2.rotateAngleY;
            this.Head2Lip.rotateAngleY = this.Head2.rotateAngleY;
            this.Head2LftTusk.rotateAngleY = this.Head2.rotateAngleY;
            this.Head2RgtTusk.rotateAngleY = this.Head2.rotateAngleY;
            this.Head2Nose.rotateAngleY = this.Head2.rotateAngleY;
            this.Head2NoseBridge.rotateAngleY = this.Head2.rotateAngleY;
            this.Head2Brow.rotateAngleY = this.Head2.rotateAngleY;
            this.Head2RgtHorn.rotateAngleY = this.Head2.rotateAngleY;
            this.Head2LftHorn.rotateAngleY = this.Head2.rotateAngleY;
            this.Head2DiamondHorn.rotateAngleY = this.Head2.rotateAngleY;
        }

    }

}
