package drzhark.mocreatures.client.model;

import drzhark.mocreatures.entity.passive.MoCEntityElephant;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCModelElephant extends ModelBase {

    ModelRenderer Head;
    ModelRenderer Neck;
    ModelRenderer HeadBump;
    ModelRenderer Chin;
    ModelRenderer LowerLip;
    ModelRenderer Back;
    ModelRenderer LeftSmallEar;
    ModelRenderer LeftBigEar;
    ModelRenderer RightSmallEar;
    ModelRenderer RightBigEar;
    ModelRenderer Hump;
    ModelRenderer Body;
    ModelRenderer Skirt;
    ModelRenderer RightTuskA;
    ModelRenderer RightTuskB;
    ModelRenderer RightTuskC;
    ModelRenderer RightTuskD;
    ModelRenderer LeftTuskA;
    ModelRenderer LeftTuskB;
    ModelRenderer LeftTuskC;
    ModelRenderer LeftTuskD;
    ModelRenderer TrunkA;
    ModelRenderer TrunkB;
    ModelRenderer TrunkC;
    ModelRenderer TrunkD;
    ModelRenderer TrunkE;
    ModelRenderer FrontRightUpperLeg;
    ModelRenderer FrontRightLowerLeg;
    ModelRenderer FrontLeftUpperLeg;
    ModelRenderer FrontLeftLowerLeg;
    ModelRenderer BackRightUpperLeg;
    ModelRenderer BackRightLowerLeg;
    ModelRenderer BackLeftUpperLeg;
    ModelRenderer BackLeftLowerLeg;
    ModelRenderer TailRoot;
    ModelRenderer Tail;
    ModelRenderer TailPlush;
    ModelRenderer StorageRightBedroll;
    ModelRenderer StorageLeftBedroll;
    ModelRenderer StorageFrontRightChest;
    ModelRenderer StorageBackRightChest;
    ModelRenderer StorageFrontLeftChest;
    ModelRenderer StorageBackLeftChest;
    ModelRenderer StorageRightBlankets;
    ModelRenderer StorageLeftBlankets;
    ModelRenderer HarnessBlanket;
    ModelRenderer HarnessUpperBelt;
    ModelRenderer HarnessLowerBelt;
    ModelRenderer CabinPillow;
    ModelRenderer CabinLeftRail;
    ModelRenderer Cabin;
    ModelRenderer CabinRightRail;
    ModelRenderer CabinBackRail;
    ModelRenderer CabinRoof;
    ModelRenderer FortNeckBeam;
    ModelRenderer FortBackBeam;
    ModelRenderer TuskLD1;
    ModelRenderer TuskLD2;
    ModelRenderer TuskLD3;
    ModelRenderer TuskLD4;
    ModelRenderer TuskLD5;
    ModelRenderer TuskRD1;
    ModelRenderer TuskRD2;
    ModelRenderer TuskRD3;
    ModelRenderer TuskRD4;
    ModelRenderer TuskRD5;
    ModelRenderer TuskLI1;
    ModelRenderer TuskLI2;
    ModelRenderer TuskLI3;
    ModelRenderer TuskLI4;
    ModelRenderer TuskLI5;
    ModelRenderer TuskRI1;
    ModelRenderer TuskRI2;
    ModelRenderer TuskRI3;
    ModelRenderer TuskRI4;
    ModelRenderer TuskRI5;
    ModelRenderer TuskLW1;
    ModelRenderer TuskLW2;
    ModelRenderer TuskLW3;
    ModelRenderer TuskLW4;
    ModelRenderer TuskLW5;
    ModelRenderer TuskRW1;
    ModelRenderer TuskRW2;
    ModelRenderer TuskRW3;
    ModelRenderer TuskRW4;
    ModelRenderer TuskRW5;

    ModelRenderer FortFloor1;
    ModelRenderer FortFloor2;
    ModelRenderer FortFloor3;
    ModelRenderer FortBackWall;
    ModelRenderer FortBackLeftWall;
    ModelRenderer FortBackRightWall;
    ModelRenderer StorageUpLeft;
    ModelRenderer StorageUpRight;

    float radianF = 57.29578F;

    private boolean isSitting;
    private int tailCounter;
    private int earCounter;
    private int trunkCounter;
    int tusks;

    public MoCModelElephant() {
        this.textureWidth = 128;
        this.textureHeight = 256;

        this.Head = new ModelRenderer(this, 60, 0);
        this.Head.addBox(-5.5F, -6F, -8F, 11, 15, 10);
        this.Head.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.Head, -0.1745329F, 0F, 0F);

        this.Neck = new ModelRenderer(this, 46, 48);
        this.Neck.addBox(-4.95F, -6F, -8F, 10, 14, 8);
        this.Neck.setRotationPoint(0F, -8F, -10F);
        setRotation(this.Neck, -0.2617994F, 0F, 0F);

        this.HeadBump = new ModelRenderer(this, 104, 41);
        this.HeadBump.addBox(-3F, -9F, -6F, 6, 3, 6);
        this.HeadBump.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.HeadBump, -0.1745329F, 0F, 0F);

        /*
         * Chin = new ModelRenderer(this, 86, 56); Chin.addBox(-1.5F, -1F, -4F,
         * 3, 5, 4); Chin.setRotationPoint(0F, -2F, -17.46667F);
         * setRotation(Chin, 2.054118F, 0F, 0F); LowerLip = new
         * ModelRenderer(this, 80, 65); LowerLip.addBox(-2F, -1F, -6F, 4, 2, 6);
         * LowerLip.setRotationPoint(0F, -2F, -17.46667F); setRotation(LowerLip,
         * 1.570796F, 0F, 0F);
         */

        this.Chin = new ModelRenderer(this, 86, 56);
        this.Chin.addBox(-1.5F, -6F, -10.7F, 3, 5, 4);
        this.Chin.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.Chin, 2.054118F, 0F, 0F);

        this.LowerLip = new ModelRenderer(this, 80, 65);
        this.LowerLip.addBox(-2F, -2F, -14F, 4, 2, 6);
        this.LowerLip.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.LowerLip, 1.570796F, 0F, 0F);

        this.Back = new ModelRenderer(this, 0, 48);
        this.Back.addBox(-5F, -10F, -10F, 10, 2, 26);
        this.Back.setRotationPoint(0F, -4F, -3F);

        this.LeftSmallEar = new ModelRenderer(this, 102, 0);
        this.LeftSmallEar.addBox(2F, -8F, -5F, 8, 10, 1);
        this.LeftSmallEar.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.LeftSmallEar, -0.1745329F, -0.5235988F, 0.5235988F);
        this.LeftBigEar = new ModelRenderer(this, 102, 0);
        this.LeftBigEar.addBox(2F, -8F, -5F, 12, 14, 1);
        this.LeftBigEar.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.LeftBigEar, -0.1745329F, -0.5235988F, 0.5235988F);
        this.RightSmallEar = new ModelRenderer(this, 106, 15);
        this.RightSmallEar.addBox(-10F, -8F, -5F, 8, 10, 1);
        this.RightSmallEar.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.RightSmallEar, -0.1745329F, 0.5235988F, -0.5235988F);
        this.RightBigEar = new ModelRenderer(this, 102, 15);
        this.RightBigEar.addBox(-14F, -8F, -5F, 12, 14, 1);
        this.RightBigEar.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.RightBigEar, -0.1745329F, 0.5235988F, -0.5235988F);

        this.Hump = new ModelRenderer(this, 88, 30);
        this.Hump.addBox(-6F, -2F, -3F, 12, 3, 8);
        this.Hump.setRotationPoint(0F, -13F, -5.5F);

        this.Body = new ModelRenderer(this, 0, 0);
        this.Body.addBox(-8F, -10F, -10F, 16, 20, 28);
        this.Body.setRotationPoint(0F, -2F, -3F);

        this.Skirt = new ModelRenderer(this, 28, 94);
        this.Skirt.addBox(-8F, -10F, -6F, 16, 28, 6);
        this.Skirt.setRotationPoint(0F, 8F, -3F);
        setRotation(this.Skirt, 1.570796F, 0F, 0F);

        this.RightTuskA = new ModelRenderer(this, 2, 60);
        this.RightTuskA.addBox(-3.8F, -3.5F, -19F, 2, 2, 10);
        this.RightTuskA.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.RightTuskA, 1.22173F, 0F, 0.1745329F);

        this.RightTuskB = new ModelRenderer(this, 0, 0);
        this.RightTuskB.addBox(-3.8F, 6.2F, -24.2F, 2, 2, 7);
        this.RightTuskB.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.RightTuskB, 0.6981317F, 0F, 0.1745329F);

        this.RightTuskC = new ModelRenderer(this, 0, 18);
        this.RightTuskC.addBox(-3.8F, 17.1F, -21.9F, 2, 2, 5);
        this.RightTuskC.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.RightTuskC, 0.1745329F, 0F, 0.1745329F);

        this.RightTuskD = new ModelRenderer(this, 14, 18);
        this.RightTuskD.addBox(-3.8F, 25.5F, -14.5F, 2, 2, 5);
        this.RightTuskD.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.RightTuskD, -0.3490659F, 0F, 0.1745329F);

        this.LeftTuskA = new ModelRenderer(this, 2, 48);
        this.LeftTuskA.addBox(1.8F, -3.5F, -19F, 2, 2, 10);
        this.LeftTuskA.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.LeftTuskA, 1.22173F, 0F, -0.1745329F);

        this.LeftTuskB = new ModelRenderer(this, 0, 9);
        this.LeftTuskB.addBox(1.8F, 6.2F, -24.2F, 2, 2, 7);
        this.LeftTuskB.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.LeftTuskB, 0.6981317F, 0F, -0.1745329F);

        this.LeftTuskC = new ModelRenderer(this, 0, 18);
        this.LeftTuskC.addBox(1.8F, 17.1F, -21.9F, 2, 2, 5);
        this.LeftTuskC.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.LeftTuskC, 0.1745329F, 0F, -0.1745329F);

        this.LeftTuskD = new ModelRenderer(this, 14, 18);
        this.LeftTuskD.addBox(1.8F, 25.5F, -14.5F, 2, 2, 5);
        this.LeftTuskD.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.LeftTuskD, -0.3490659F, 0F, -0.1745329F);

        //original
        this.TrunkA = new ModelRenderer(this, 0, 76);
        this.TrunkA.addBox(-4F, -2.5F, -18F, 8, 7, 10);
        this.TrunkA.setRotationPoint(0F, -3F, -22.46667F);
        setRotation(this.TrunkA, 1.570796F, 0F, 0F);

        this.TrunkB = new ModelRenderer(this, 0, 93);
        this.TrunkB.addBox(-3F, -2.5F, -7F, 6, 5, 7);
        this.TrunkB.setRotationPoint(0F, 6.5F, -22.5F);
        setRotation(this.TrunkB, 1.658063F, 0F, 0F);

        this.TrunkC = new ModelRenderer(this, 0, 105);
        this.TrunkC.addBox(-2.5F, -2F, -4F, 5, 4, 5);
        this.TrunkC.setRotationPoint(0F, 13F, -22.0F);
        setRotation(this.TrunkC, 1.919862F, 0F, 0F);

        this.TrunkD = new ModelRenderer(this, 0, 114);
        this.TrunkD.addBox(-2F, -1.5F, -5F, 4, 3, 5);
        this.TrunkD.setRotationPoint(0F, 16F, -21.5F);
        setRotation(this.TrunkD, 2.216568F, 0F, 0F);

        this.TrunkE = new ModelRenderer(this, 0, 122);
        this.TrunkE.addBox(-1.5F, -1F, -4F, 3, 2, 4);
        this.TrunkE.setRotationPoint(0F, 19.5F, -19F);
        setRotation(this.TrunkE, 2.530727F, 0F, 0F);

        this.FrontRightUpperLeg = new ModelRenderer(this, 100, 109);
        this.FrontRightUpperLeg.addBox(-3.5F, 0F, -3.5F, 7, 12, 7);
        this.FrontRightUpperLeg.setRotationPoint(-4.6F, 4F, -9.6F);

        this.FrontRightLowerLeg = new ModelRenderer(this, 100, 73);
        this.FrontRightLowerLeg.addBox(-3.5F, 0F, -3.5F, 7, 10, 7);
        this.FrontRightLowerLeg.setRotationPoint(-4.6F, 14F, -9.6F);

        this.FrontLeftUpperLeg = new ModelRenderer(this, 100, 90);
        this.FrontLeftUpperLeg.addBox(-3.5F, 0F, -3.5F, 7, 12, 7);
        this.FrontLeftUpperLeg.setRotationPoint(4.6F, 4F, -9.6F);

        this.FrontLeftLowerLeg = new ModelRenderer(this, 72, 73);
        this.FrontLeftLowerLeg.addBox(-3.5F, 0F, -3.5F, 7, 10, 7);
        this.FrontLeftLowerLeg.setRotationPoint(4.6F, 14F, -9.6F);

        this.BackRightUpperLeg = new ModelRenderer(this, 72, 109);
        this.BackRightUpperLeg.addBox(-3.5F, 0F, -3.5F, 7, 12, 7);
        this.BackRightUpperLeg.setRotationPoint(-4.6F, 4F, 11.6F);

        this.BackRightLowerLeg = new ModelRenderer(this, 100, 56);
        this.BackRightLowerLeg.addBox(-3.5F, 0F, -3.5F, 7, 10, 7);
        this.BackRightLowerLeg.setRotationPoint(-4.6F, 14F, 11.6F);

        this.BackLeftUpperLeg = new ModelRenderer(this, 72, 90);
        this.BackLeftUpperLeg.addBox(-3.5F, 0F, -3.5F, 7, 12, 7);
        this.BackLeftUpperLeg.setRotationPoint(4.6F, 4F, 11.6F);

        this.BackLeftLowerLeg = new ModelRenderer(this, 44, 77);
        this.BackLeftLowerLeg.addBox(-3.5F, 0F, -3.5F, 7, 10, 7);
        this.BackLeftLowerLeg.setRotationPoint(4.6F, 14F, 11.6F);

        this.TailRoot = new ModelRenderer(this, 20, 105);
        this.TailRoot.addBox(-1F, 0F, -2F, 2, 10, 2);
        this.TailRoot.setRotationPoint(0F, -8F, 15F);
        setRotation(this.TailRoot, 0.296706F, 0F, 0F);

        this.Tail = new ModelRenderer(this, 20, 117);
        this.Tail.addBox(-1F, 9.7F, -0.2F, 2, 6, 2);
        this.Tail.setRotationPoint(0F, -8F, 15F);
        setRotation(this.Tail, 0.1134464F, 0F, 0F);

        this.TailPlush = new ModelRenderer(this, 26, 76);
        this.TailPlush.addBox(-1.5F, 15.5F, -0.7F, 3, 6, 3);
        this.TailPlush.setRotationPoint(0F, -8F, 15F);
        setRotation(this.TailPlush, 0.1134464F, 0F, 0F);

        this.StorageRightBedroll = new ModelRenderer(this, 90, 231);
        this.StorageRightBedroll.addBox(-2.5F, 8F, -8F, 3, 3, 16);
        this.StorageRightBedroll.setRotationPoint(-9F, -10.2F, 1F);
        setRotation(this.StorageRightBedroll, 0F, 0F, 0.418879F);

        this.StorageLeftBedroll = new ModelRenderer(this, 90, 231);
        this.StorageLeftBedroll.addBox(-0.5F, 8F, -8F, 3, 3, 16);
        this.StorageLeftBedroll.setRotationPoint(9F, -10.2F, 1F);
        setRotation(this.StorageLeftBedroll, 0F, 0F, -0.418879F);

        this.StorageFrontRightChest = new ModelRenderer(this, 76, 208);
        this.StorageFrontRightChest.addBox(-3.5F, 0F, -5F, 5, 8, 10);
        this.StorageFrontRightChest.setRotationPoint(-11F, -1.2F, -4.5F);
        setRotation(this.StorageFrontRightChest, 0F, 0F, -0.2617994F);

        this.StorageBackRightChest = new ModelRenderer(this, 76, 208);
        this.StorageBackRightChest.addBox(-3.5F, 0F, -5F, 5, 8, 10);
        this.StorageBackRightChest.setRotationPoint(-11F, -1.2F, 6.5F);
        setRotation(this.StorageBackRightChest, 0F, 0F, -0.2617994F);

        this.StorageFrontLeftChest = new ModelRenderer(this, 76, 226);
        this.StorageFrontLeftChest.addBox(-1.5F, 0F, -5F, 5, 8, 10);
        this.StorageFrontLeftChest.setRotationPoint(11F, -1.2F, -4.5F);
        setRotation(this.StorageFrontLeftChest, 0F, 0F, 0.2617994F);

        this.StorageBackLeftChest = new ModelRenderer(this, 76, 226);
        this.StorageBackLeftChest.addBox(-1.5F, 0F, -5F, 5, 8, 10);
        this.StorageBackLeftChest.setRotationPoint(11F, -1.2F, 6.5F);
        setRotation(this.StorageBackLeftChest, 0F, 0F, 0.2617994F);

        this.StorageRightBlankets = new ModelRenderer(this, 0, 228);
        this.StorageRightBlankets.addBox(-4.5F, -1F, -7F, 5, 10, 14);
        this.StorageRightBlankets.setRotationPoint(-9F, -10.2F, 1F);

        this.StorageLeftBlankets = new ModelRenderer(this, 38, 228);
        this.StorageLeftBlankets.addBox(-0.5F, -1F, -7F, 5, 10, 14);
        this.StorageLeftBlankets.setRotationPoint(9F, -10.2F, 1F);

        this.HarnessBlanket = new ModelRenderer(this, 0, 196);
        this.HarnessBlanket.addBox(-8.5F, -2F, -3F, 17, 14, 18);
        this.HarnessBlanket.setRotationPoint(0F, -13.2F, -3.5F);

        this.HarnessUpperBelt = new ModelRenderer(this, 70, 196);
        this.HarnessUpperBelt.addBox(-8.5F, 0.5F, -2F, 17, 10, 2);
        this.HarnessUpperBelt.setRotationPoint(0F, -2F, -2.5F);

        this.HarnessLowerBelt = new ModelRenderer(this, 70, 196);
        this.HarnessLowerBelt.addBox(-8.5F, 0.5F, -2.5F, 17, 10, 2);
        this.HarnessLowerBelt.setRotationPoint(0F, -2F, 7F);

        this.CabinPillow = new ModelRenderer(this, 76, 146);
        this.CabinPillow.addBox(-6.5F, 0F, -6.5F, 13, 4, 13);
        this.CabinPillow.setRotationPoint(0F, -16F, 2F);

        this.CabinLeftRail = new ModelRenderer(this, 56, 147);
        this.CabinLeftRail.addBox(-7F, 0F, 7F, 14, 1, 1);
        this.CabinLeftRail.setRotationPoint(0F, -23F, 1.5F);
        setRotation(this.CabinLeftRail, 0F, 1.570796F, 0F);

        this.Cabin = new ModelRenderer(this, 0, 128);
        this.Cabin.addBox(-7F, 0F, -7F, 14, 20, 14);
        this.Cabin.setRotationPoint(0F, -35F, 2F);

        this.CabinRightRail = new ModelRenderer(this, 56, 147);
        this.CabinRightRail.addBox(-7F, 0F, 7F, 14, 1, 1);
        this.CabinRightRail.setRotationPoint(0F, -23F, 1.5F);
        setRotation(this.CabinRightRail, 0F, -1.570796F, 0F);

        this.CabinBackRail = new ModelRenderer(this, 56, 147);
        this.CabinBackRail.addBox(-7F, 0F, 7F, 14, 1, 1);
        this.CabinBackRail.setRotationPoint(0F, -23F, 1.5F);

        this.CabinRoof = new ModelRenderer(this, 56, 128);
        this.CabinRoof.addBox(-7.5F, 0F, -7.5F, 15, 4, 15);
        this.CabinRoof.setRotationPoint(0F, -34F, 2F);

        this.FortNeckBeam = new ModelRenderer(this, 26, 180);
        this.FortNeckBeam.addBox(-12F, 0F, -20.5F, 24, 4, 4);
        this.FortNeckBeam.setRotationPoint(0F, -16F, 10F);

        this.FortBackBeam = new ModelRenderer(this, 26, 180);
        this.FortBackBeam.addBox(-12F, 0F, 0F, 24, 4, 4);
        this.FortBackBeam.setRotationPoint(0F, -16F, 10F);

        this.TuskLD1 = new ModelRenderer(this, 108, 207);
        this.TuskLD1.addBox(1.3F, 5.5F, -24.2F, 3, 3, 7);
        this.TuskLD1.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.TuskLD1, 0.6981317F, 0F, -0.1745329F);

        this.TuskLD2 = new ModelRenderer(this, 112, 199);
        this.TuskLD2.addBox(1.29F, 16.5F, -21.9F, 3, 3, 5);
        this.TuskLD2.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.TuskLD2, 0.1745329F, 0F, -0.1745329F);

        this.TuskLD3 = new ModelRenderer(this, 110, 190);
        this.TuskLD3.addBox(1.3F, 24.9F, -15.5F, 3, 3, 6);
        this.TuskLD3.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.TuskLD3, -0.3490659F, 0F, -0.1745329F);

        this.TuskLD4 = new ModelRenderer(this, 86, 175);
        this.TuskLD4.addBox(2.7F, 14.5F, -21.9F, 0, 7, 5);
        this.TuskLD4.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.TuskLD4, 0.1745329F, 0F, -0.1745329F);

        this.TuskLD5 = new ModelRenderer(this, 112, 225);
        this.TuskLD5.addBox(2.7F, 22.9F, -17.5F, 0, 7, 8);
        this.TuskLD5.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.TuskLD5, -0.3490659F, 0F, -0.1745329F);

        this.TuskRD1 = new ModelRenderer(this, 108, 207);
        this.TuskRD1.addBox(-4.3F, 5.5F, -24.2F, 3, 3, 7);
        this.TuskRD1.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.TuskRD1, 0.6981317F, 0F, 0.1745329F);

        this.TuskRD2 = new ModelRenderer(this, 112, 199);
        this.TuskRD2.addBox(-4.29F, 16.5F, -21.9F, 3, 3, 5);
        this.TuskRD2.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.TuskRD2, 0.1745329F, 0F, 0.1745329F);

        this.TuskRD3 = new ModelRenderer(this, 110, 190);
        this.TuskRD3.addBox(-4.3F, 24.9F, -15.5F, 3, 3, 6);
        this.TuskRD3.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.TuskRD3, -0.3490659F, 0F, 0.1745329F);

        this.TuskRD4 = new ModelRenderer(this, 86, 163);
        this.TuskRD4.addBox(-2.8F, 14.5F, -21.9F, 0, 7, 5);
        this.TuskRD4.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.TuskRD4, 0.1745329F, 0F, 0.1745329F);

        this.TuskRD5 = new ModelRenderer(this, 112, 232);
        this.TuskRD5.addBox(-2.8F, 22.9F, -17.5F, 0, 7, 8);
        this.TuskRD5.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.TuskRD5, -0.3490659F, 0F, 0.1745329F);

        this.TuskLI1 = new ModelRenderer(this, 108, 180);
        this.TuskLI1.addBox(1.3F, 5.5F, -24.2F, 3, 3, 7);
        this.TuskLI1.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.TuskLI1, 0.6981317F, 0F, -0.1745329F);

        this.TuskLI2 = new ModelRenderer(this, 112, 172);
        this.TuskLI2.addBox(1.29F, 16.5F, -21.9F, 3, 3, 5);
        this.TuskLI2.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.TuskLI2, 0.1745329F, 0F, -0.1745329F);

        this.TuskLI3 = new ModelRenderer(this, 110, 163);
        this.TuskLI3.addBox(1.3F, 24.9F, -15.5F, 3, 3, 6);
        this.TuskLI3.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.TuskLI3, -0.3490659F, 0F, -0.1745329F);

        this.TuskLI4 = new ModelRenderer(this, 96, 175);
        this.TuskLI4.addBox(2.7F, 14.5F, -21.9F, 0, 7, 5);
        this.TuskLI4.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.TuskLI4, 0.1745329F, 0F, -0.1745329F);

        this.TuskLI5 = new ModelRenderer(this, 112, 209);
        this.TuskLI5.addBox(2.7F, 22.9F, -17.5F, 0, 7, 8);
        this.TuskLI5.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.TuskLI5, -0.3490659F, 0F, -0.1745329F);

        this.TuskRI1 = new ModelRenderer(this, 108, 180);
        this.TuskRI1.addBox(-4.3F, 5.5F, -24.2F, 3, 3, 7);
        this.TuskRI1.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.TuskRI1, 0.6981317F, 0F, 0.1745329F);

        this.TuskRI2 = new ModelRenderer(this, 112, 172);
        this.TuskRI2.addBox(-4.29F, 16.5F, -21.9F, 3, 3, 5);
        this.TuskRI2.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.TuskRI2, 0.1745329F, 0F, 0.1745329F);

        this.TuskRI3 = new ModelRenderer(this, 110, 163);
        this.TuskRI3.addBox(-4.3F, 24.9F, -15.5F, 3, 3, 6);
        this.TuskRI3.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.TuskRI3, -0.3490659F, 0F, 0.1745329F);

        this.TuskRI4 = new ModelRenderer(this, 96, 163);
        this.TuskRI4.addBox(-2.8F, 14.5F, -21.9F, 0, 7, 5);
        this.TuskRI4.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.TuskRI4, 0.1745329F, 0F, 0.1745329F);

        this.TuskRI5 = new ModelRenderer(this, 112, 216);
        this.TuskRI5.addBox(-2.8F, 22.9F, -17.5F, 0, 7, 8);
        this.TuskRI5.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.TuskRI5, -0.3490659F, 0F, 0.1745329F);

        this.TuskLW1 = new ModelRenderer(this, 56, 166);
        this.TuskLW1.addBox(1.3F, 5.5F, -24.2F, 3, 3, 7);
        this.TuskLW1.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.TuskLW1, 0.6981317F, 0F, -0.1745329F);

        this.TuskLW2 = new ModelRenderer(this, 60, 158);
        this.TuskLW2.addBox(1.29F, 16.5F, -21.9F, 3, 3, 5);
        this.TuskLW2.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.TuskLW2, 0.1745329F, 0F, -0.1745329F);

        this.TuskLW3 = new ModelRenderer(this, 58, 149);
        this.TuskLW3.addBox(1.3F, 24.9F, -15.5F, 3, 3, 6);
        this.TuskLW3.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.TuskLW3, -0.3490659F, 0F, -0.1745329F);

        this.TuskLW4 = new ModelRenderer(this, 46, 164);
        this.TuskLW4.addBox(2.7F, 14.5F, -21.9F, 0, 7, 5);
        this.TuskLW4.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.TuskLW4, 0.1745329F, 0F, -0.1745329F);

        this.TuskLW5 = new ModelRenderer(this, 52, 192);
        this.TuskLW5.addBox(2.7F, 22.9F, -17.5F, 0, 7, 8);
        this.TuskLW5.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.TuskLW5, -0.3490659F, 0F, -0.1745329F);

        this.TuskRW1 = new ModelRenderer(this, 56, 166);
        this.TuskRW1.addBox(-4.3F, 5.5F, -24.2F, 3, 3, 7);
        this.TuskRW1.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.TuskRW1, 0.6981317F, 0F, 0.1745329F);

        this.TuskRW2 = new ModelRenderer(this, 60, 158);
        this.TuskRW2.addBox(-4.29F, 16.5F, -21.9F, 3, 3, 5);
        this.TuskRW2.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.TuskRW2, 0.1745329F, 0F, 0.1745329F);

        this.TuskRW3 = new ModelRenderer(this, 58, 149);
        this.TuskRW3.addBox(-4.3F, 24.9F, -15.5F, 3, 3, 6);
        this.TuskRW3.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.TuskRW3, -0.3490659F, 0F, 0.1745329F);

        this.TuskRW4 = new ModelRenderer(this, 46, 157);
        this.TuskRW4.addBox(-2.8F, 14.5F, -21.9F, 0, 7, 5);
        this.TuskRW4.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.TuskRW4, 0.1745329F, 0F, 0.1745329F);

        this.TuskRW5 = new ModelRenderer(this, 52, 199);
        this.TuskRW5.addBox(-2.8F, 22.9F, -17.5F, 0, 7, 8);
        this.TuskRW5.setRotationPoint(0F, -10F, -16.5F);
        setRotation(this.TuskRW5, -0.3490659F, 0F, 0.1745329F);

        this.FortFloor1 = new ModelRenderer(this, 0, 176);
        this.FortFloor1.addBox(-0.5F, -20F, -6F, 1, 8, 12);
        this.FortFloor1.setRotationPoint(0F, -16F, 10F);
        setRotation(this.FortFloor1, 1.570796F, 0F, 1.570796F);

        this.FortFloor2 = new ModelRenderer(this, 0, 176);
        this.FortFloor2.addBox(-0.5F, -12F, -6F, 1, 8, 12);
        this.FortFloor2.setRotationPoint(0F, -16F, 10F);
        setRotation(this.FortFloor2, 1.570796F, 0F, 1.570796F);

        this.FortFloor3 = new ModelRenderer(this, 0, 176);
        this.FortFloor3.addBox(-0.5F, -4F, -6F, 1, 8, 12);
        this.FortFloor3.setRotationPoint(0F, -16F, 10F);
        setRotation(this.FortFloor3, 1.570796F, 0F, 1.570796F);

        this.FortBackWall = new ModelRenderer(this, 0, 176);
        this.FortBackWall.addBox(-5F, -6.2F, -6F, 1, 8, 12);
        this.FortBackWall.setRotationPoint(0F, -16F, 10F);
        setRotation(this.FortBackWall, 0F, 1.570796F, 0F);

        this.FortBackLeftWall = new ModelRenderer(this, 0, 176);
        this.FortBackLeftWall.addBox(6F, -6F, -7F, 1, 8, 12);
        this.FortBackLeftWall.setRotationPoint(0F, -16F, 10F);

        this.FortBackRightWall = new ModelRenderer(this, 0, 176);
        this.FortBackRightWall.addBox(-7F, -6F, -7F, 1, 8, 12);
        this.FortBackRightWall.setRotationPoint(0F, -16F, 10F);

        this.StorageUpLeft = new ModelRenderer(this, 76, 226);
        this.StorageUpLeft.addBox(6.5F, 1F, -14F, 5, 8, 10);
        this.StorageUpLeft.setRotationPoint(0F, -16F, 10F);
        setRotation(this.StorageUpLeft, 0F, 0F, -0.3839724F);

        this.StorageUpRight = new ModelRenderer(this, 76, 208);
        this.StorageUpRight.addBox(-11.5F, 1F, -14F, 5, 8, 10);
        this.StorageUpRight.setRotationPoint(0F, -16F, 10F);
        setRotation(this.StorageUpRight, 0F, 0F, 0.3839724F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        //super.render(entity, f, f1, f2, f3, f4, f5);
        MoCEntityElephant elephant = (MoCEntityElephant) entity;
        this.tusks = elephant.getTusks();
        int type = elephant.getType();
        this.tailCounter = elephant.tailCounter;
        this.earCounter = elephant.earCounter;
        this.trunkCounter = elephant.trunkCounter;
        int harness = elephant.getArmorType();
        int storage = elephant.getStorage();
        this.isSitting = (elephant.sitCounter != 0);
        //boolean moveTail = (elephant.tailCounter != 0);

        setRotationAngles(f, f1, f2, f3, f4, f5);

        if (tusks == 0) {
            this.LeftTuskB.render(f5);
            this.RightTuskB.render(f5);
            if (elephant.getIsAdult() || elephant.getEdad() > 70) {
                this.LeftTuskC.render(f5);
                this.RightTuskC.render(f5);
            }
            if (elephant.getIsAdult() || elephant.getEdad() > 90) {
                this.LeftTuskD.render(f5);
                this.RightTuskD.render(f5);
            }
        } else if (tusks == 1) {
            this.TuskLW1.render(f5);
            this.TuskLW2.render(f5);
            this.TuskLW3.render(f5);
            this.TuskLW4.render(f5);
            this.TuskLW5.render(f5);
            this.TuskRW1.render(f5);
            this.TuskRW2.render(f5);
            this.TuskRW3.render(f5);
            this.TuskRW4.render(f5);
            this.TuskRW5.render(f5);
        } else if (tusks == 2) {
            this.TuskLI1.render(f5);
            this.TuskLI2.render(f5);
            this.TuskLI3.render(f5);
            this.TuskLI4.render(f5);
            this.TuskLI5.render(f5);
            this.TuskRI1.render(f5);
            this.TuskRI2.render(f5);
            this.TuskRI3.render(f5);
            this.TuskRI4.render(f5);
            this.TuskRI5.render(f5);
        } else if (tusks == 3) {
            this.TuskLD1.render(f5);
            this.TuskLD2.render(f5);
            this.TuskLD3.render(f5);
            this.TuskLD4.render(f5);
            this.TuskLD5.render(f5);
            this.TuskRD1.render(f5);
            this.TuskRD2.render(f5);
            this.TuskRD3.render(f5);
            this.TuskRD4.render(f5);
            this.TuskRD5.render(f5);
        }

        if (type == 1) //african
        {
            this.LeftBigEar.render(f5);
            this.RightBigEar.render(f5);
        } else {
            this.LeftSmallEar.render(f5);
            this.RightSmallEar.render(f5);
        }

        if (type == 3 || type == 4) //mammoths
        {
            this.HeadBump.render(f5);
            this.Skirt.render(f5);
        }

        if (harness >= 1) {
            this.HarnessBlanket.render(f5);
            this.HarnessUpperBelt.render(f5);
            this.HarnessLowerBelt.render(f5);
            if (type == 5) {
                this.Skirt.render(f5);
            }
        }

        if (harness == 3) {
            if (type == 5) {
                this.CabinPillow.render(f5);
                this.CabinLeftRail.render(f5);
                this.Cabin.render(f5);
                this.CabinRightRail.render(f5);
                this.CabinBackRail.render(f5);
                this.CabinRoof.render(f5);
            }

            if (type == 4) {

                this.FortBackRightWall.render(f5);
                this.FortBackLeftWall.render(f5);
                this.FortBackWall.render(f5);
                this.FortFloor1.render(f5);
                this.FortFloor2.render(f5);
                this.FortFloor3.render(f5);
                this.FortNeckBeam.render(f5);
                this.FortBackBeam.render(f5);

            }

        }

        if (storage >= 1) {
            this.StorageRightBedroll.render(f5);
            this.StorageFrontRightChest.render(f5);
            this.StorageBackRightChest.render(f5);
            this.StorageRightBlankets.render(f5);

        }
        if (storage >= 2) {
            this.StorageLeftBlankets.render(f5);
            this.StorageLeftBedroll.render(f5);
            this.StorageFrontLeftChest.render(f5);
            this.StorageBackLeftChest.render(f5);

        }
        if (storage >= 3) {
            this.StorageUpLeft.render(f5);
        }

        if (storage >= 4) {
            this.StorageUpRight.render(f5);
        }

        this.Head.render(f5);
        this.Neck.render(f5);
        this.Chin.render(f5);
        this.LowerLip.render(f5);
        this.Back.render(f5);

        this.Hump.render(f5);
        this.Body.render(f5);

        this.RightTuskA.render(f5);
        this.LeftTuskA.render(f5);

        this.TrunkA.render(f5);
        this.TrunkB.render(f5);
        this.TrunkC.render(f5);
        this.TrunkD.render(f5);
        this.TrunkE.render(f5);
        this.FrontRightUpperLeg.render(f5);
        this.FrontRightLowerLeg.render(f5);
        this.FrontLeftUpperLeg.render(f5);
        this.FrontLeftLowerLeg.render(f5);
        this.BackRightUpperLeg.render(f5);
        this.BackRightLowerLeg.render(f5);
        this.BackLeftUpperLeg.render(f5);
        this.BackLeftLowerLeg.render(f5);
        this.TailRoot.render(f5);
        this.Tail.render(f5);
        this.TailPlush.render(f5);

    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    /**
     * Used to adjust the Y offset of the model cubes
     */
    private void AdjustY(float f) {
        float yOff = f;
        this.Head.rotationPointY = yOff + -10F;
        this.Neck.rotationPointY = yOff + -8F;
        this.HeadBump.rotationPointY = yOff + -10F;
        this.Chin.rotationPointY = yOff + -10F;
        this.LowerLip.rotationPointY = yOff + -10F;
        this.Back.rotationPointY = yOff + -4F;
        this.LeftSmallEar.rotationPointY = yOff + -10F;
        this.LeftBigEar.rotationPointY = yOff + -10F;
        this.RightSmallEar.rotationPointY = yOff + -10F;
        this.RightBigEar.rotationPointY = yOff + -10F;
        this.Hump.rotationPointY = yOff + -13F;
        this.Body.rotationPointY = yOff + -2F;
        this.Skirt.rotationPointY = yOff + 8F;
        this.RightTuskA.rotationPointY = yOff + -10F;
        this.RightTuskB.rotationPointY = yOff + -10F;
        this.RightTuskC.rotationPointY = yOff + -10F;
        this.RightTuskD.rotationPointY = yOff + -10F;
        this.LeftTuskA.rotationPointY = yOff + -10F;
        this.LeftTuskB.rotationPointY = yOff + -10F;
        this.LeftTuskC.rotationPointY = yOff + -10F;
        this.LeftTuskD.rotationPointY = yOff + -10F;
        this.TrunkA.rotationPointY = yOff + -3F;
        this.TrunkB.rotationPointY = yOff + 5.5F;
        this.TrunkC.rotationPointY = yOff + 13F;
        this.TrunkD.rotationPointY = yOff + 16F;
        this.TrunkE.rotationPointY = yOff + 19.5F;
        this.FrontRightUpperLeg.rotationPointY = yOff + 4F;
        this.FrontRightLowerLeg.rotationPointY = yOff + 14F;
        this.FrontLeftUpperLeg.rotationPointY = yOff + 4F;
        this.FrontLeftLowerLeg.rotationPointY = yOff + 14F;
        this.BackRightUpperLeg.rotationPointY = yOff + 4F;
        this.BackRightLowerLeg.rotationPointY = yOff + 14F;
        this.BackLeftUpperLeg.rotationPointY = yOff + 4F;
        this.BackLeftLowerLeg.rotationPointY = yOff + 14F;
        this.TailRoot.rotationPointY = yOff + -8F;
        this.Tail.rotationPointY = yOff + -8F;
        this.TailPlush.rotationPointY = yOff + -8F;
        this.StorageRightBedroll.rotationPointY = yOff + -10.2F;
        this.StorageLeftBedroll.rotationPointY = yOff + -10.2F;
        this.StorageFrontRightChest.rotationPointY = yOff + -1.2F;
        this.StorageBackRightChest.rotationPointY = yOff + -1.2F;
        this.StorageFrontLeftChest.rotationPointY = yOff + -1.2F;
        this.StorageBackLeftChest.rotationPointY = yOff + -1.2F;
        this.StorageRightBlankets.rotationPointY = yOff + -10.2F;
        this.StorageLeftBlankets.rotationPointY = yOff + -10.2F;
        this.HarnessBlanket.rotationPointY = yOff + -13.2F;
        this.HarnessUpperBelt.rotationPointY = yOff + -2F;
        this.HarnessLowerBelt.rotationPointY = yOff + -2F;
        this.CabinPillow.rotationPointY = yOff + -16F;
        this.CabinLeftRail.rotationPointY = yOff + -23F;
        this.Cabin.rotationPointY = yOff + -35F;
        this.CabinRightRail.rotationPointY = yOff + -23F;
        this.CabinBackRail.rotationPointY = yOff + -23F;
        this.CabinRoof.rotationPointY = yOff + -34F;
        this.FortBackRightWall.rotationPointY = yOff + -16F;
        this.FortBackLeftWall.rotationPointY = yOff + -16F;
        this.FortBackWall.rotationPointY = yOff + -16F;
        this.FortNeckBeam.rotationPointY = yOff + -16F;
        this.FortBackBeam.rotationPointY = yOff + -16F;
        this.FortFloor1.rotationPointY = yOff + -16F;
        this.FortFloor2.rotationPointY = yOff + -16F;
        this.FortFloor3.rotationPointY = yOff + -16F;

        this.StorageUpLeft.rotationPointY = yOff + -16F;
        this.StorageUpRight.rotationPointY = yOff + -16F;

        this.TuskLD1.rotationPointY = yOff + -10F;
        this.TuskLD2.rotationPointY = yOff + -10F;
        this.TuskLD3.rotationPointY = yOff + -10F;
        this.TuskLD4.rotationPointY = yOff + -10F;
        this.TuskLD5.rotationPointY = yOff + -10F;
        this.TuskRD1.rotationPointY = yOff + -10F;
        this.TuskRD2.rotationPointY = yOff + -10F;
        this.TuskRD3.rotationPointY = yOff + -10F;
        this.TuskRD4.rotationPointY = yOff + -10F;
        this.TuskRD5.rotationPointY = yOff + -10F;
        this.TuskLI1.rotationPointY = yOff + -10F;
        this.TuskLI2.rotationPointY = yOff + -10F;
        this.TuskLI3.rotationPointY = yOff + -10F;
        this.TuskLI4.rotationPointY = yOff + -10F;
        this.TuskLI5.rotationPointY = yOff + -10F;
        this.TuskRI1.rotationPointY = yOff + -10F;
        this.TuskRI2.rotationPointY = yOff + -10F;
        this.TuskRI3.rotationPointY = yOff + -10F;
        this.TuskRI4.rotationPointY = yOff + -10F;
        this.TuskRI5.rotationPointY = yOff + -10F;
        this.TuskLW1.rotationPointY = yOff + -10F;
        this.TuskLW2.rotationPointY = yOff + -10F;
        this.TuskLW3.rotationPointY = yOff + -10F;
        this.TuskLW4.rotationPointY = yOff + -10F;
        this.TuskLW5.rotationPointY = yOff + -10F;
        this.TuskRW1.rotationPointY = yOff + -10F;
        this.TuskRW2.rotationPointY = yOff + -10F;
        this.TuskRW3.rotationPointY = yOff + -10F;
        this.TuskRW4.rotationPointY = yOff + -10F;
        this.TuskRW5.rotationPointY = yOff + -10F;

    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {//, byte tusks, boolean sitting, boolean tail) {

        float RLegXRot = MathHelper.cos((f * 0.6662F) + 3.141593F) * 0.8F * f1;
        float LLegXRot = MathHelper.cos(f * 0.6662F) * 0.8F * f1;

        if (f4 < 0) {
            f4 = 0;
        }

        float HeadXRot = (f4 / 57.29578F);
        if (f3 > 20F) {
            f3 = 20F;
        }
        if (f3 < -20F) {
            f3 = -20F;
        }
        float HeadYRot = (f3 / 57.29578F);

        float f10 = 0F;
        if (isSitting) {
            f10 = 8F;
        }
        AdjustY(f10);

        /**
         * Random Trunk animation
         */
        float TrunkXRot = 0F;

        if (trunkCounter != 0) {
            HeadXRot = 0F;
            TrunkXRot = MathHelper.cos(this.trunkCounter * 0.2F) * 12F;
        }
        if (isSitting) {
            HeadXRot = 0F;
            TrunkXRot = 25F;
        }
        this.Head.rotateAngleX = (-10F / this.radianF) + HeadXRot;

        this.Head.rotateAngleY = HeadYRot;
        this.HeadBump.rotateAngleY = this.Head.rotateAngleY;
        this.HeadBump.rotateAngleX = this.Head.rotateAngleX;

        this.RightTuskA.rotateAngleY = HeadYRot;
        this.LeftTuskA.rotateAngleY = HeadYRot;
        this.RightTuskA.rotateAngleX = (70F / this.radianF) + HeadXRot;
        this.LeftTuskA.rotateAngleX = (70F / this.radianF) + HeadXRot;

        this.Chin.rotateAngleY = HeadYRot;
        this.Chin.rotateAngleX = (113F / this.radianF) + HeadXRot;
        this.LowerLip.rotateAngleY = HeadYRot;
        this.LowerLip.rotateAngleX = (85F / this.radianF) + HeadXRot;

        //ears
        /**
         * f = distance walked f1 = speed 0 - 1 f2 = timer
         */
        /**
         * Ear random animation
         */
        float EarF = 0F;

        /*float f2a = f2 % 100F;
        if (f2a > 60 & f2a < 90) {
            EarF = MathHelper.cos(f2 * 0.5F) * 0.35F;
        }*/

        if (this.earCounter != 0) {
            EarF = MathHelper.cos(this.earCounter * 0.5F) * 0.35F;
        }

        this.RightBigEar.rotateAngleY = (30F / this.radianF) + HeadYRot + EarF;
        this.RightSmallEar.rotateAngleY = (30F / this.radianF) + HeadYRot + EarF;
        this.LeftBigEar.rotateAngleY = (-30F / this.radianF) + HeadYRot - EarF;
        this.LeftSmallEar.rotateAngleY = (-30F / this.radianF) + HeadYRot - EarF;

        this.RightBigEar.rotateAngleX = (-10F / this.radianF) + HeadXRot;
        this.RightSmallEar.rotateAngleX = (-10F / this.radianF) + HeadXRot;
        this.LeftBigEar.rotateAngleX = (-10F / this.radianF) + HeadXRot;
        this.LeftSmallEar.rotateAngleX = (-10F / this.radianF) + HeadXRot;

        //TrunkA.rotateAngleX = -50F / radianF;

        //TrunkA.rotationPointY = -3F;
        this.TrunkA.rotationPointZ = -22.50F;
        adjustAllRotationPoints(this.TrunkA, this.Head);

        this.TrunkA.rotateAngleY = HeadYRot;
        float TrunkARotX = (90F - TrunkXRot);
        if (TrunkARotX < 85) {
            TrunkARotX = 85;
        }
        this.TrunkA.rotateAngleX = ((TrunkARotX) / this.radianF) + HeadXRot;

        //TrunkB.rotationPointY = 5.5F;
        this.TrunkB.rotationPointZ = -22.5F;
        //TrunkB.setRotationPoint(0F, 6.5F, -22.5F);
        adjustAllRotationPoints(this.TrunkB, this.TrunkA);
        this.TrunkB.rotateAngleY = HeadYRot;
        this.TrunkB.rotateAngleX = ((95F - TrunkXRot * 1.5F) / this.radianF) + HeadXRot;

        //TrunkC.setRotationPoint(0F, 13F, -22.0F);
        //TrunkC.rotationPointY = 13F;
        this.TrunkC.rotationPointZ = -22.5F;
        adjustAllRotationPoints(this.TrunkC, this.TrunkB);
        this.TrunkC.rotateAngleY = HeadYRot;
        this.TrunkC.rotateAngleX = ((110F - TrunkXRot * 3F) / this.radianF) + HeadXRot;

        //TrunkD.rotationPointY = 16F;
        this.TrunkD.rotationPointZ = -21.5F;
        adjustAllRotationPoints(this.TrunkD, this.TrunkC);
        this.TrunkD.rotateAngleY = HeadYRot;
        this.TrunkD.rotateAngleX = ((127F - TrunkXRot * 4.5F) / this.radianF) + HeadXRot;

        //TrunkE.rotationPointY = 19.5F;
        this.TrunkE.rotationPointZ = -19F;
        adjustAllRotationPoints(this.TrunkE, this.TrunkD);
        this.TrunkE.rotateAngleY = HeadYRot;
        this.TrunkE.rotateAngleX = ((145F - TrunkXRot * 6F) / this.radianF) + HeadXRot;

        //legs
        if (isSitting) {
            this.FrontRightUpperLeg.rotateAngleX = -30F / this.radianF;
            this.FrontLeftUpperLeg.rotateAngleX = -30F / this.radianF;
            this.BackLeftUpperLeg.rotateAngleX = -30F / this.radianF;
            this.BackRightUpperLeg.rotateAngleX = -30F / this.radianF;
        } else {
            this.FrontRightUpperLeg.rotateAngleX = RLegXRot;
            this.FrontLeftUpperLeg.rotateAngleX = LLegXRot;
            this.BackLeftUpperLeg.rotateAngleX = RLegXRot;
            this.BackRightUpperLeg.rotateAngleX = LLegXRot;
        }

        adjustXRotationPoints(this.FrontRightLowerLeg, this.FrontRightUpperLeg);
        adjustXRotationPoints(this.BackRightLowerLeg, this.BackRightUpperLeg);
        adjustXRotationPoints(this.FrontLeftLowerLeg, this.FrontLeftUpperLeg);
        adjustXRotationPoints(this.BackLeftLowerLeg, this.BackLeftUpperLeg);

        //To convert from degrees to radians, multiply by ((PI)/180o).
        //To convert from radians to degrees, multiply by (180o/(PI)).
        if (isSitting) {
            this.FrontLeftLowerLeg.rotateAngleX = 90F / this.radianF;
            this.FrontRightLowerLeg.rotateAngleX = 90F / this.radianF;
            this.BackLeftLowerLeg.rotateAngleX = 90F / this.radianF;
            this.BackRightLowerLeg.rotateAngleX = 90F / this.radianF;
        } else {
            float LLegXRotD = (LLegXRot * (float) (180 / Math.PI));
            float RLegXRotD = (RLegXRot * (float) (180 / Math.PI));

            if (LLegXRotD > 0F) {
                LLegXRotD *= 2F;
            }
            if (RLegXRotD > 0F) {
                RLegXRotD *= 2F;
            }

            this.FrontLeftLowerLeg.rotateAngleX = LLegXRotD / this.radianF;
            this.FrontRightLowerLeg.rotateAngleX = RLegXRotD / this.radianF;
            this.BackLeftLowerLeg.rotateAngleX = RLegXRotD / this.radianF;
            this.BackRightLowerLeg.rotateAngleX = LLegXRotD / this.radianF;
        }

        if (tusks == 0) {
            this.LeftTuskB.rotateAngleY = HeadYRot;
            this.LeftTuskC.rotateAngleY = HeadYRot;
            this.LeftTuskD.rotateAngleY = HeadYRot;
            this.RightTuskB.rotateAngleY = HeadYRot;
            this.RightTuskC.rotateAngleY = HeadYRot;
            this.RightTuskD.rotateAngleY = HeadYRot;

            this.LeftTuskB.rotateAngleX = (40F / this.radianF) + HeadXRot;
            this.LeftTuskC.rotateAngleX = (10F / this.radianF) + HeadXRot;
            this.LeftTuskD.rotateAngleX = (-20F / this.radianF) + HeadXRot;
            this.RightTuskB.rotateAngleX = (40F / this.radianF) + HeadXRot;
            this.RightTuskC.rotateAngleX = (10F / this.radianF) + HeadXRot;
            this.RightTuskD.rotateAngleX = (-20F / this.radianF) + HeadXRot;
        } else if (tusks == 1) {
            this.TuskLW1.rotateAngleY = HeadYRot;
            this.TuskLW2.rotateAngleY = HeadYRot;
            this.TuskLW3.rotateAngleY = HeadYRot;
            this.TuskLW4.rotateAngleY = HeadYRot;
            this.TuskLW5.rotateAngleY = HeadYRot;
            this.TuskRW1.rotateAngleY = HeadYRot;
            this.TuskRW2.rotateAngleY = HeadYRot;
            this.TuskRW3.rotateAngleY = HeadYRot;
            this.TuskRW4.rotateAngleY = HeadYRot;
            this.TuskRW5.rotateAngleY = HeadYRot;

            this.TuskLW1.rotateAngleX = (40F / this.radianF) + HeadXRot;
            this.TuskLW2.rotateAngleX = (10F / this.radianF) + HeadXRot;
            this.TuskLW3.rotateAngleX = (-20F / this.radianF) + HeadXRot;
            this.TuskLW4.rotateAngleX = (10F / this.radianF) + HeadXRot;
            this.TuskLW5.rotateAngleX = (-20F / this.radianF) + HeadXRot;
            this.TuskRW1.rotateAngleX = (40F / this.radianF) + HeadXRot;
            this.TuskRW2.rotateAngleX = (10F / this.radianF) + HeadXRot;
            this.TuskRW3.rotateAngleX = (-20F / this.radianF) + HeadXRot;
            this.TuskRW4.rotateAngleX = (10F / this.radianF) + HeadXRot;
            this.TuskRW5.rotateAngleX = (-20F / this.radianF) + HeadXRot;
        } else if (tusks == 2) {
            this.TuskLI1.rotateAngleY = HeadYRot;
            this.TuskLI2.rotateAngleY = HeadYRot;
            this.TuskLI3.rotateAngleY = HeadYRot;
            this.TuskLI4.rotateAngleY = HeadYRot;
            this.TuskLI5.rotateAngleY = HeadYRot;
            this.TuskRI1.rotateAngleY = HeadYRot;
            this.TuskRI2.rotateAngleY = HeadYRot;
            this.TuskRI3.rotateAngleY = HeadYRot;
            this.TuskRI4.rotateAngleY = HeadYRot;
            this.TuskRI5.rotateAngleY = HeadYRot;

            this.TuskLI1.rotateAngleX = (40F / this.radianF) + HeadXRot;
            this.TuskLI2.rotateAngleX = (10F / this.radianF) + HeadXRot;
            this.TuskLI3.rotateAngleX = (-20F / this.radianF) + HeadXRot;
            this.TuskLI4.rotateAngleX = (10F / this.radianF) + HeadXRot;
            this.TuskLI5.rotateAngleX = (-20F / this.radianF) + HeadXRot;
            this.TuskRI1.rotateAngleX = (40F / this.radianF) + HeadXRot;
            this.TuskRI2.rotateAngleX = (10F / this.radianF) + HeadXRot;
            this.TuskRI3.rotateAngleX = (-20F / this.radianF) + HeadXRot;
            this.TuskRI4.rotateAngleX = (10F / this.radianF) + HeadXRot;
            this.TuskRI5.rotateAngleX = (-20F / this.radianF) + HeadXRot;
        } else if (tusks == 3) {
            this.TuskLD1.rotateAngleY = HeadYRot;
            this.TuskLD2.rotateAngleY = HeadYRot;
            this.TuskLD3.rotateAngleY = HeadYRot;
            this.TuskLD4.rotateAngleY = HeadYRot;
            this.TuskLD5.rotateAngleY = HeadYRot;
            this.TuskRD1.rotateAngleY = HeadYRot;
            this.TuskRD2.rotateAngleY = HeadYRot;
            this.TuskRD3.rotateAngleY = HeadYRot;
            this.TuskRD4.rotateAngleY = HeadYRot;
            this.TuskRD5.rotateAngleY = HeadYRot;

            this.TuskLD1.rotateAngleX = (40F / this.radianF) + HeadXRot;
            this.TuskLD2.rotateAngleX = (10F / this.radianF) + HeadXRot;
            this.TuskLD3.rotateAngleX = (-20F / this.radianF) + HeadXRot;
            this.TuskLD4.rotateAngleX = (10F / this.radianF) + HeadXRot;
            this.TuskLD5.rotateAngleX = (-20F / this.radianF) + HeadXRot;
            this.TuskRD1.rotateAngleX = (40F / this.radianF) + HeadXRot;
            this.TuskRD2.rotateAngleX = (10F / this.radianF) + HeadXRot;
            this.TuskRD3.rotateAngleX = (-20F / this.radianF) + HeadXRot;
            this.TuskRD4.rotateAngleX = (10F / this.radianF) + HeadXRot;
            this.TuskRD5.rotateAngleX = (-20F / this.radianF) + HeadXRot;
        }

        //chest anim
        this.StorageLeftBedroll.rotateAngleX = LLegXRot / 10F;
        this.StorageFrontLeftChest.rotateAngleX = LLegXRot / 5F;
        this.StorageBackLeftChest.rotateAngleX = LLegXRot / 5F;

        this.StorageRightBedroll.rotateAngleX = RLegXRot / 10F;
        this.StorageFrontRightChest.rotateAngleX = RLegXRot / 5F;
        this.StorageBackRightChest.rotateAngleX = RLegXRot / 5F;

        this.FortNeckBeam.rotateAngleZ = LLegXRot / 50F;
        this.FortBackBeam.rotateAngleZ = LLegXRot / 50F;

        this.FortBackRightWall.rotateAngleZ = (LLegXRot / 50F);
        this.FortBackLeftWall.rotateAngleZ = (LLegXRot / 50F);
        this.FortBackWall.rotateAngleX = 0F - (LLegXRot / 50F);

        //f1 = movement speed!
        //f2 = timer!
        //tail
        float tailMov = (f1 * 0.9F);
        if (tailMov < 0) {
            tailMov = 0;
        }

        if (this.tailCounter != 0) {
            this.TailRoot.rotateAngleY = MathHelper.cos(f2 * 0.4F) * 1.3F;
            tailMov = 30F / this.radianF;
        } else {
            this.TailRoot.rotateAngleY = 0F;
        }

        this.TailRoot.rotateAngleX = (17F / this.radianF) + tailMov;
        this.TailPlush.rotateAngleX = this.Tail.rotateAngleX = (6.5F / this.radianF) + tailMov;
        this.TailPlush.rotateAngleY = this.TailRoot.rotateAngleY;
        this.Tail.rotateAngleY = this.TailPlush.rotateAngleY;

    }

    /**
     * Used for leg adjustment - anteroposterior movement
     *
     * @param target
     * @param attached
     */
    private void adjustXRotationPoints(ModelRenderer target, ModelRenderer origin) {
        //rotation point Z and Y adjusted for legs =
        //Z rotation point = attached rotation point Z + sin(attached.rotateangleX) * distance
        //Y rotation point = attached rotation point Y + cos(attached.rotateangleX) * distance
        float distance = target.rotationPointY - origin.rotationPointY;
        if (distance < 0F) {
            distance *= -1F;
        }
        target.rotationPointZ = origin.rotationPointZ + (MathHelper.sin(origin.rotateAngleX) * distance);
        target.rotationPointY = origin.rotationPointY + (MathHelper.cos(origin.rotateAngleX) * distance);

    }

    /**
     * Used for trunk adjustment - lateral movement
     *
     * @param target : target model
     * @param origin : origin model
     */
    @SuppressWarnings("unused")
    private void adjustYRotationPoints(ModelRenderer target, ModelRenderer origin) {
        //rotation point Z and X adjusted for head =
        //Z rotation point = attached rotation point Z - cos(attached.rotateangleX) * distance
        //Y rotation point = attached rotation point Y - sin(attached.rotateangleX) * distance
        float distanceZ = 0F;
        if (target.rotationPointZ > origin.rotationPointZ) {
            distanceZ = target.rotationPointZ - origin.rotationPointZ;
        } else {
            distanceZ = origin.rotationPointZ - target.rotationPointZ;
        }

        target.rotationPointZ = origin.rotationPointZ - (MathHelper.cos(origin.rotateAngleY) * distanceZ);
        target.rotationPointX = origin.rotationPointX - (MathHelper.sin(origin.rotateAngleY) * distanceZ);
    }

    @SuppressWarnings("unused")
    private void adjustAllRotationPoints(ModelRenderer target, ModelRenderer origin) {

        float distanceY = 0F;
        if (target.rotationPointY > origin.rotationPointY) {
            distanceY = target.rotationPointY - origin.rotationPointY;
        } else {
            distanceY = origin.rotationPointY - target.rotationPointY;
        }

        float distanceZ = 0F;
        if (target.rotationPointZ > origin.rotationPointZ) {
            distanceZ = target.rotationPointZ - origin.rotationPointZ;
        } else {
            distanceZ = origin.rotationPointZ - target.rotationPointZ;
        }

        target.rotationPointY = origin.rotationPointY + MathHelper.sin(origin.rotateAngleX) * distanceY;
        target.rotationPointZ = origin.rotationPointZ - MathHelper.cos(origin.rotateAngleY) * (MathHelper.cos(origin.rotateAngleX) * distanceY);
        target.rotationPointX = origin.rotationPointX - MathHelper.sin(origin.rotateAngleY) * (MathHelper.cos(origin.rotateAngleX) * distanceY);

    }

}
