package drzhark.mocreatures.client.model;



import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.entity.passive.MoCEntityElephant;

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
    //boolean isSitting;

    public MoCModelElephant()
    {
        textureWidth = 128;
        textureHeight = 256;

        Head = new ModelRenderer(this, 60, 0);
        Head.addBox(-5.5F, -6F, -8F, 11, 15, 10);
        Head.setRotationPoint(0F, -10F, -16.5F);
        setRotation(Head, -0.1745329F, 0F, 0F);

        Neck = new ModelRenderer(this, 46, 48);
        Neck.addBox(-4.95F, -6F, -8F, 10, 14, 8);
        Neck.setRotationPoint(0F, -8F, -10F);
        setRotation(Neck, -0.2617994F, 0F, 0F);

        HeadBump = new ModelRenderer(this, 104, 41);
        HeadBump.addBox(-3F, -9F, -6F, 6, 3, 6);
        HeadBump.setRotationPoint(0F, -10F, -16.5F);
        setRotation(HeadBump, -0.1745329F, 0F, 0F);

        /*Chin = new ModelRenderer(this, 86, 56);
        Chin.addBox(-1.5F, -1F, -4F, 3, 5, 4);
        Chin.setRotationPoint(0F, -2F, -17.46667F);
        setRotation(Chin, 2.054118F, 0F, 0F);

        LowerLip = new ModelRenderer(this, 80, 65);
        LowerLip.addBox(-2F, -1F, -6F, 4, 2, 6);
        LowerLip.setRotationPoint(0F, -2F, -17.46667F);
        setRotation(LowerLip, 1.570796F, 0F, 0F);*/

        Chin = new ModelRenderer(this, 86, 56);
        Chin.addBox(-1.5F, -6F, -10.7F, 3, 5, 4);
        Chin.setRotationPoint(0F, -10F, -16.5F);
        setRotation(Chin, 2.054118F, 0F, 0F);

        LowerLip = new ModelRenderer(this, 80, 65);
        LowerLip.addBox(-2F, -2F, -14F, 4, 2, 6);
        LowerLip.setRotationPoint(0F, -10F, -16.5F);
        setRotation(LowerLip, 1.570796F, 0F, 0F);

        Back = new ModelRenderer(this, 0, 48);
        Back.addBox(-5F, -10F, -10F, 10, 2, 26);
        Back.setRotationPoint(0F, -4F, -3F);

        LeftSmallEar = new ModelRenderer(this, 102, 0);
        LeftSmallEar.addBox(2F, -8F, -5F, 8, 10, 1);
        LeftSmallEar.setRotationPoint(0F, -10F, -16.5F);
        setRotation(LeftSmallEar, -0.1745329F, -0.5235988F, 0.5235988F);
        LeftBigEar = new ModelRenderer(this, 102, 0);
        LeftBigEar.addBox(2F, -8F, -5F, 12, 14, 1);
        LeftBigEar.setRotationPoint(0F, -10F, -16.5F);
        setRotation(LeftBigEar, -0.1745329F, -0.5235988F, 0.5235988F);
        RightSmallEar = new ModelRenderer(this, 106, 15);
        RightSmallEar.addBox(-10F, -8F, -5F, 8, 10, 1);
        RightSmallEar.setRotationPoint(0F, -10F, -16.5F);
        setRotation(RightSmallEar, -0.1745329F, 0.5235988F, -0.5235988F);
        RightBigEar = new ModelRenderer(this, 102, 15);
        RightBigEar.addBox(-14F, -8F, -5F, 12, 14, 1);
        RightBigEar.setRotationPoint(0F, -10F, -16.5F);
        setRotation(RightBigEar, -0.1745329F, 0.5235988F, -0.5235988F);

        Hump = new ModelRenderer(this, 88, 30);
        Hump.addBox(-6F, -2F, -3F, 12, 3, 8);
        Hump.setRotationPoint(0F, -13F, -5.5F);

        Body = new ModelRenderer(this, 0, 0);
        Body.addBox(-8F, -10F, -10F, 16, 20, 28);
        Body.setRotationPoint(0F, -2F, -3F);

        Skirt = new ModelRenderer(this, 28, 94);
        Skirt.addBox(-8F, -10F, -6F, 16, 28, 6);
        Skirt.setRotationPoint(0F, 8F, -3F);
        setRotation(Skirt, 1.570796F, 0F, 0F);

        RightTuskA = new ModelRenderer(this, 2, 60);
        RightTuskA.addBox(-3.8F, -3.5F, -19F, 2, 2, 10);
        RightTuskA.setRotationPoint(0F, -10F, -16.5F);
        setRotation(RightTuskA, 1.22173F, 0F, 0.1745329F);

        RightTuskB = new ModelRenderer(this, 0, 0);
        RightTuskB.addBox(-3.8F, 6.2F, -24.2F, 2, 2, 7);
        RightTuskB.setRotationPoint(0F, -10F, -16.5F);
        setRotation(RightTuskB, 0.6981317F, 0F, 0.1745329F);

        RightTuskC = new ModelRenderer(this, 0, 18);
        RightTuskC.addBox(-3.8F, 17.1F, -21.9F, 2, 2, 5);
        RightTuskC.setRotationPoint(0F, -10F, -16.5F);
        setRotation(RightTuskC, 0.1745329F, 0F, 0.1745329F);

        RightTuskD = new ModelRenderer(this, 14, 18);
        RightTuskD.addBox(-3.8F, 25.5F, -14.5F, 2, 2, 5);
        RightTuskD.setRotationPoint(0F, -10F, -16.5F);
        setRotation(RightTuskD, -0.3490659F, 0F, 0.1745329F);

        LeftTuskA = new ModelRenderer(this, 2, 48);
        LeftTuskA.addBox(1.8F, -3.5F, -19F, 2, 2, 10);
        LeftTuskA.setRotationPoint(0F, -10F, -16.5F);
        setRotation(LeftTuskA, 1.22173F, 0F, -0.1745329F);

        LeftTuskB = new ModelRenderer(this, 0, 9);
        LeftTuskB.addBox(1.8F, 6.2F, -24.2F, 2, 2, 7);
        LeftTuskB.setRotationPoint(0F, -10F, -16.5F);
        setRotation(LeftTuskB, 0.6981317F, 0F, -0.1745329F);

        LeftTuskC = new ModelRenderer(this, 0, 18);
        LeftTuskC.addBox(1.8F, 17.1F, -21.9F, 2, 2, 5);
        LeftTuskC.setRotationPoint(0F, -10F, -16.5F);
        setRotation(LeftTuskC, 0.1745329F, 0F, -0.1745329F);

        LeftTuskD = new ModelRenderer(this, 14, 18);
        LeftTuskD.addBox(1.8F, 25.5F, -14.5F, 2, 2, 5);
        LeftTuskD.setRotationPoint(0F, -10F, -16.5F);
        setRotation(LeftTuskD, -0.3490659F, 0F, -0.1745329F);

        //original
        TrunkA = new ModelRenderer(this, 0, 76);
        TrunkA.addBox(-4F, -2.5F, -18F, 8, 7, 10);
        TrunkA.setRotationPoint(0F, -3F, -22.46667F);
        setRotation(TrunkA, 1.570796F, 0F, 0F);

        

        TrunkB = new ModelRenderer(this, 0, 93);
        TrunkB.addBox(-3F, -2.5F, -7F, 6, 5, 7);
        TrunkB.setRotationPoint(0F, 6.5F, -22.5F);
        setRotation(TrunkB, 1.658063F, 0F, 0F);

        

        TrunkC = new ModelRenderer(this, 0, 105);
        TrunkC.addBox(-2.5F, -2F, -4F, 5, 4, 5);
        TrunkC.setRotationPoint(0F, 13F, -22.0F);
        setRotation(TrunkC, 1.919862F, 0F, 0F);

        TrunkD = new ModelRenderer(this, 0, 114);
        TrunkD.addBox(-2F, -1.5F, -5F, 4, 3, 5);
        TrunkD.setRotationPoint(0F, 16F, -21.5F);
        setRotation(TrunkD, 2.216568F, 0F, 0F);

        TrunkE = new ModelRenderer(this, 0, 122);
        TrunkE.addBox(-1.5F, -1F, -4F, 3, 2, 4);
        TrunkE.setRotationPoint(0F, 19.5F, -19F);
        setRotation(TrunkE, 2.530727F, 0F, 0F);

        
        
        
        FrontRightUpperLeg = new ModelRenderer(this, 100, 109);
        FrontRightUpperLeg.addBox(-3.5F, 0F, -3.5F, 7, 12, 7);
        FrontRightUpperLeg.setRotationPoint(-4.6F, 4F, -9.6F);

        FrontRightLowerLeg = new ModelRenderer(this, 100, 73);
        FrontRightLowerLeg.addBox(-3.5F, 0F, -3.5F, 7, 10, 7);
        FrontRightLowerLeg.setRotationPoint(-4.6F, 14F, -9.6F);

        FrontLeftUpperLeg = new ModelRenderer(this, 100, 90);
        FrontLeftUpperLeg.addBox(-3.5F, 0F, -3.5F, 7, 12, 7);
        FrontLeftUpperLeg.setRotationPoint(4.6F, 4F, -9.6F);

        FrontLeftLowerLeg = new ModelRenderer(this, 72, 73);
        FrontLeftLowerLeg.addBox(-3.5F, 0F, -3.5F, 7, 10, 7);
        FrontLeftLowerLeg.setRotationPoint(4.6F, 14F, -9.6F);

        BackRightUpperLeg = new ModelRenderer(this, 72, 109);
        BackRightUpperLeg.addBox(-3.5F, 0F, -3.5F, 7, 12, 7);
        BackRightUpperLeg.setRotationPoint(-4.6F, 4F, 11.6F);

        BackRightLowerLeg = new ModelRenderer(this, 100, 56);
        BackRightLowerLeg.addBox(-3.5F, 0F, -3.5F, 7, 10, 7);
        BackRightLowerLeg.setRotationPoint(-4.6F, 14F, 11.6F);

        BackLeftUpperLeg = new ModelRenderer(this, 72, 90);
        BackLeftUpperLeg.addBox(-3.5F, 0F, -3.5F, 7, 12, 7);
        BackLeftUpperLeg.setRotationPoint(4.6F, 4F, 11.6F);

        BackLeftLowerLeg = new ModelRenderer(this, 44, 77);
        BackLeftLowerLeg.addBox(-3.5F, 0F, -3.5F, 7, 10, 7);
        BackLeftLowerLeg.setRotationPoint(4.6F, 14F, 11.6F);
        

        TailRoot = new ModelRenderer(this, 20, 105);
        TailRoot.addBox(-1F, 0F, -2F, 2, 10, 2);
        TailRoot.setRotationPoint(0F, -8F, 15F);
        setRotation(TailRoot, 0.296706F, 0F, 0F);

        Tail = new ModelRenderer(this, 20, 117);
        Tail.addBox(-1F, 9.7F, -0.2F, 2, 6, 2);
        Tail.setRotationPoint(0F, -8F, 15F);
        setRotation(Tail, 0.1134464F, 0F, 0F);

        TailPlush = new ModelRenderer(this, 26, 76);
        TailPlush.addBox(-1.5F, 15.5F, -0.7F, 3, 6, 3);
        TailPlush.setRotationPoint(0F, -8F, 15F);
        setRotation(TailPlush, 0.1134464F, 0F, 0F);

        StorageRightBedroll = new ModelRenderer(this, 90, 231);
        StorageRightBedroll.addBox(-2.5F, 8F, -8F, 3, 3, 16);
        StorageRightBedroll.setRotationPoint(-9F, -10.2F, 1F);
        setRotation(StorageRightBedroll, 0F, 0F, 0.418879F);

        StorageLeftBedroll = new ModelRenderer(this, 90, 231);
        StorageLeftBedroll.addBox(-0.5F, 8F, -8F, 3, 3, 16);
        StorageLeftBedroll.setRotationPoint(9F, -10.2F, 1F);
        setRotation(StorageLeftBedroll, 0F, 0F, -0.418879F);

        StorageFrontRightChest = new ModelRenderer(this, 76, 208);
        StorageFrontRightChest.addBox(-3.5F, 0F, -5F, 5, 8, 10);
        StorageFrontRightChest.setRotationPoint(-11F, -1.2F, -4.5F);
        setRotation(StorageFrontRightChest, 0F, 0F, -0.2617994F);

        StorageBackRightChest = new ModelRenderer(this, 76, 208);
        StorageBackRightChest.addBox(-3.5F, 0F, -5F, 5, 8, 10);
        StorageBackRightChest.setRotationPoint(-11F, -1.2F, 6.5F);
        setRotation(StorageBackRightChest, 0F, 0F, -0.2617994F);

        StorageFrontLeftChest = new ModelRenderer(this, 76, 226);
        StorageFrontLeftChest.addBox(-1.5F, 0F, -5F, 5, 8, 10);
        StorageFrontLeftChest.setRotationPoint(11F, -1.2F, -4.5F);
        setRotation(StorageFrontLeftChest, 0F, 0F, 0.2617994F);

        StorageBackLeftChest = new ModelRenderer(this, 76, 226);
        StorageBackLeftChest.addBox(-1.5F, 0F, -5F, 5, 8, 10);
        StorageBackLeftChest.setRotationPoint(11F, -1.2F, 6.5F);
        setRotation(StorageBackLeftChest, 0F, 0F, 0.2617994F);

        StorageRightBlankets = new ModelRenderer(this, 0, 228);
        StorageRightBlankets.addBox(-4.5F, -1F, -7F, 5, 10, 14);
        StorageRightBlankets.setRotationPoint(-9F, -10.2F, 1F);

        StorageLeftBlankets = new ModelRenderer(this, 38, 228);
        StorageLeftBlankets.addBox(-0.5F, -1F, -7F, 5, 10, 14);
        StorageLeftBlankets.setRotationPoint(9F, -10.2F, 1F);

        HarnessBlanket = new ModelRenderer(this, 0, 196);
        HarnessBlanket.addBox(-8.5F, -2F, -3F, 17, 14, 18);
        HarnessBlanket.setRotationPoint(0F, -13.2F, -3.5F);

        HarnessUpperBelt = new ModelRenderer(this, 70, 196);
        HarnessUpperBelt.addBox(-8.5F, 0.5F, -2F, 17, 10, 2);
        HarnessUpperBelt.setRotationPoint(0F, -2F, -2.5F);

        HarnessLowerBelt = new ModelRenderer(this, 70, 196);
        HarnessLowerBelt.addBox(-8.5F, 0.5F, -2.5F, 17, 10, 2);
        HarnessLowerBelt.setRotationPoint(0F, -2F, 7F);

        CabinPillow = new ModelRenderer(this, 76, 146);
        CabinPillow.addBox(-6.5F, 0F, -6.5F, 13, 4, 13);
        CabinPillow.setRotationPoint(0F, -16F, 2F);

        CabinLeftRail = new ModelRenderer(this, 56, 147);
        CabinLeftRail.addBox(-7F, 0F, 7F, 14, 1, 1);
        CabinLeftRail.setRotationPoint(0F, -23F, 1.5F);
        setRotation(CabinLeftRail, 0F, 1.570796F, 0F);

        Cabin = new ModelRenderer(this, 0, 128);
        Cabin.addBox(-7F, 0F, -7F, 14, 20, 14);
        Cabin.setRotationPoint(0F, -35F, 2F);

        CabinRightRail = new ModelRenderer(this, 56, 147);
        CabinRightRail.addBox(-7F, 0F, 7F, 14, 1, 1);
        CabinRightRail.setRotationPoint(0F, -23F, 1.5F);
        setRotation(CabinRightRail, 0F, -1.570796F, 0F);

        CabinBackRail = new ModelRenderer(this, 56, 147);
        CabinBackRail.addBox(-7F, 0F, 7F, 14, 1, 1);
        CabinBackRail.setRotationPoint(0F, -23F, 1.5F);

        CabinRoof = new ModelRenderer(this, 56, 128);
        CabinRoof.addBox(-7.5F, 0F, -7.5F, 15, 4, 15);
        CabinRoof.setRotationPoint(0F, -34F, 2F);

        
        FortNeckBeam = new ModelRenderer(this, 26, 180);
        FortNeckBeam.addBox(-12F, 0F, -20.5F, 24, 4, 4);
        FortNeckBeam.setRotationPoint(0F, -16F, 10F);

        FortBackBeam = new ModelRenderer(this, 26, 180);
        FortBackBeam.addBox(-12F, 0F, 0F, 24, 4, 4);
        FortBackBeam.setRotationPoint(0F, -16F, 10F);

        TuskLD1 = new ModelRenderer(this, 108, 207);
        TuskLD1.addBox(1.3F, 5.5F, -24.2F, 3, 3, 7);
        TuskLD1.setRotationPoint(0F, -10F, -16.5F);
        setRotation(TuskLD1, 0.6981317F, 0F, -0.1745329F);

        TuskLD2 = new ModelRenderer(this, 112, 199);
        TuskLD2.addBox(1.29F, 16.5F, -21.9F, 3, 3, 5);
        TuskLD2.setRotationPoint(0F, -10F, -16.5F);
        setRotation(TuskLD2, 0.1745329F, 0F, -0.1745329F);

        TuskLD3 = new ModelRenderer(this, 110, 190);
        TuskLD3.addBox(1.3F, 24.9F, -15.5F, 3, 3, 6);
        TuskLD3.setRotationPoint(0F, -10F, -16.5F);
        setRotation(TuskLD3, -0.3490659F, 0F, -0.1745329F);

        TuskLD4 = new ModelRenderer(this, 86, 175);
        TuskLD4.addBox(2.7F, 14.5F, -21.9F, 0, 7, 5);
        TuskLD4.setRotationPoint(0F, -10F, -16.5F);
        setRotation(TuskLD4, 0.1745329F, 0F, -0.1745329F);

        TuskLD5 = new ModelRenderer(this, 112, 225);
        TuskLD5.addBox(2.7F, 22.9F, -17.5F, 0, 7, 8);
        TuskLD5.setRotationPoint(0F, -10F, -16.5F);
        setRotation(TuskLD5, -0.3490659F, 0F, -0.1745329F);

        TuskRD1 = new ModelRenderer(this, 108, 207);
        TuskRD1.addBox(-4.3F, 5.5F, -24.2F, 3, 3, 7);
        TuskRD1.setRotationPoint(0F, -10F, -16.5F);
        setRotation(TuskRD1, 0.6981317F, 0F, 0.1745329F);

        TuskRD2 = new ModelRenderer(this, 112, 199);
        TuskRD2.addBox(-4.29F, 16.5F, -21.9F, 3, 3, 5);
        TuskRD2.setRotationPoint(0F, -10F, -16.5F);
        setRotation(TuskRD2, 0.1745329F, 0F, 0.1745329F);

        TuskRD3 = new ModelRenderer(this, 110, 190);
        TuskRD3.addBox(-4.3F, 24.9F, -15.5F, 3, 3, 6);
        TuskRD3.setRotationPoint(0F, -10F, -16.5F);
        setRotation(TuskRD3, -0.3490659F, 0F, 0.1745329F);

        TuskRD4 = new ModelRenderer(this, 86, 163);
        TuskRD4.addBox(-2.8F, 14.5F, -21.9F, 0, 7, 5);
        TuskRD4.setRotationPoint(0F, -10F, -16.5F);
        setRotation(TuskRD4, 0.1745329F, 0F, 0.1745329F);

        TuskRD5 = new ModelRenderer(this, 112, 232);
        TuskRD5.addBox(-2.8F, 22.9F, -17.5F, 0, 7, 8);
        TuskRD5.setRotationPoint(0F, -10F, -16.5F);
        setRotation(TuskRD5, -0.3490659F, 0F, 0.1745329F);

        TuskLI1 = new ModelRenderer(this, 108, 180);
        TuskLI1.addBox(1.3F, 5.5F, -24.2F, 3, 3, 7);
        TuskLI1.setRotationPoint(0F, -10F, -16.5F);
        setRotation(TuskLI1, 0.6981317F, 0F, -0.1745329F);

        TuskLI2 = new ModelRenderer(this, 112, 172);
        TuskLI2.addBox(1.29F, 16.5F, -21.9F, 3, 3, 5);
        TuskLI2.setRotationPoint(0F, -10F, -16.5F);
        setRotation(TuskLI2, 0.1745329F, 0F, -0.1745329F);

        TuskLI3 = new ModelRenderer(this, 110, 163);
        TuskLI3.addBox(1.3F, 24.9F, -15.5F, 3, 3, 6);
        TuskLI3.setRotationPoint(0F, -10F, -16.5F);
        setRotation(TuskLI3, -0.3490659F, 0F, -0.1745329F);

        TuskLI4 = new ModelRenderer(this, 96, 175);
        TuskLI4.addBox(2.7F, 14.5F, -21.9F, 0, 7, 5);
        TuskLI4.setRotationPoint(0F, -10F, -16.5F);
        setRotation(TuskLI4, 0.1745329F, 0F, -0.1745329F);

        TuskLI5 = new ModelRenderer(this, 112, 209);
        TuskLI5.addBox(2.7F, 22.9F, -17.5F, 0, 7, 8);
        TuskLI5.setRotationPoint(0F, -10F, -16.5F);
        setRotation(TuskLI5, -0.3490659F, 0F, -0.1745329F);

        TuskRI1 = new ModelRenderer(this, 108, 180);
        TuskRI1.addBox(-4.3F, 5.5F, -24.2F, 3, 3, 7);
        TuskRI1.setRotationPoint(0F, -10F, -16.5F);
        setRotation(TuskRI1, 0.6981317F, 0F, 0.1745329F);

        TuskRI2 = new ModelRenderer(this, 112, 172);
        TuskRI2.addBox(-4.29F, 16.5F, -21.9F, 3, 3, 5);
        TuskRI2.setRotationPoint(0F, -10F, -16.5F);
        setRotation(TuskRI2, 0.1745329F, 0F, 0.1745329F);

        TuskRI3 = new ModelRenderer(this, 110, 163);
        TuskRI3.addBox(-4.3F, 24.9F, -15.5F, 3, 3, 6);
        TuskRI3.setRotationPoint(0F, -10F, -16.5F);
        setRotation(TuskRI3, -0.3490659F, 0F, 0.1745329F);

        TuskRI4 = new ModelRenderer(this, 96, 163);
        TuskRI4.addBox(-2.8F, 14.5F, -21.9F, 0, 7, 5);
        TuskRI4.setRotationPoint(0F, -10F, -16.5F);
        setRotation(TuskRI4, 0.1745329F, 0F, 0.1745329F);

        TuskRI5 = new ModelRenderer(this, 112, 216);
        TuskRI5.addBox(-2.8F, 22.9F, -17.5F, 0, 7, 8);
        TuskRI5.setRotationPoint(0F, -10F, -16.5F);
        setRotation(TuskRI5, -0.3490659F, 0F, 0.1745329F);

        TuskLW1 = new ModelRenderer(this, 56, 166);
        TuskLW1.addBox(1.3F, 5.5F, -24.2F, 3, 3, 7);
        TuskLW1.setRotationPoint(0F, -10F, -16.5F);
        setRotation(TuskLW1, 0.6981317F, 0F, -0.1745329F);

        TuskLW2 = new ModelRenderer(this, 60, 158);
        TuskLW2.addBox(1.29F, 16.5F, -21.9F, 3, 3, 5);
        TuskLW2.setRotationPoint(0F, -10F, -16.5F);
        setRotation(TuskLW2, 0.1745329F, 0F, -0.1745329F);

        TuskLW3 = new ModelRenderer(this, 58, 149);
        TuskLW3.addBox(1.3F, 24.9F, -15.5F, 3, 3, 6);
        TuskLW3.setRotationPoint(0F, -10F, -16.5F);
        setRotation(TuskLW3, -0.3490659F, 0F, -0.1745329F);

        TuskLW4 = new ModelRenderer(this, 46, 164);
        TuskLW4.addBox(2.7F, 14.5F, -21.9F, 0, 7, 5);
        TuskLW4.setRotationPoint(0F, -10F, -16.5F);
        setRotation(TuskLW4, 0.1745329F, 0F, -0.1745329F);

        TuskLW5 = new ModelRenderer(this, 52, 192);
        TuskLW5.addBox(2.7F, 22.9F, -17.5F, 0, 7, 8);
        TuskLW5.setRotationPoint(0F, -10F, -16.5F);
        setRotation(TuskLW5, -0.3490659F, 0F, -0.1745329F);

        TuskRW1 = new ModelRenderer(this, 56, 166);
        TuskRW1.addBox(-4.3F, 5.5F, -24.2F, 3, 3, 7);
        TuskRW1.setRotationPoint(0F, -10F, -16.5F);
        setRotation(TuskRW1, 0.6981317F, 0F, 0.1745329F);

        TuskRW2 = new ModelRenderer(this, 60, 158);
        TuskRW2.addBox(-4.29F, 16.5F, -21.9F, 3, 3, 5);
        TuskRW2.setRotationPoint(0F, -10F, -16.5F);
        setRotation(TuskRW2, 0.1745329F, 0F, 0.1745329F);

        TuskRW3 = new ModelRenderer(this, 58, 149);
        TuskRW3.addBox(-4.3F, 24.9F, -15.5F, 3, 3, 6);
        TuskRW3.setRotationPoint(0F, -10F, -16.5F);
        setRotation(TuskRW3, -0.3490659F, 0F, 0.1745329F);

        TuskRW4 = new ModelRenderer(this, 46, 157);
        TuskRW4.addBox(-2.8F, 14.5F, -21.9F, 0, 7, 5);
        TuskRW4.setRotationPoint(0F, -10F, -16.5F);
        setRotation(TuskRW4, 0.1745329F, 0F, 0.1745329F);

        TuskRW5 = new ModelRenderer(this, 52, 199);
        TuskRW5.addBox(-2.8F, 22.9F, -17.5F, 0, 7, 8);
        TuskRW5.setRotationPoint(0F, -10F, -16.5F);
        setRotation(TuskRW5, -0.3490659F, 0F, 0.1745329F);
        
        
          FortFloor1 = new ModelRenderer(this, 0, 176);
          FortFloor1.addBox(-0.5F, -20F, -6F, 1, 8, 12);
          FortFloor1.setRotationPoint(0F, -16F, 10F);
          setRotation(FortFloor1, 1.570796F, 0F, 1.570796F);
          
          FortFloor2 = new ModelRenderer(this, 0, 176);
          FortFloor2.addBox(-0.5F, -12F, -6F, 1, 8, 12);
          FortFloor2.setRotationPoint(0F, -16F, 10F);
          setRotation(FortFloor2, 1.570796F, 0F, 1.570796F);
          
          FortFloor3 = new ModelRenderer(this, 0, 176);
          FortFloor3.addBox(-0.5F, -4F, -6F, 1, 8, 12);
          FortFloor3.setRotationPoint(0F, -16F, 10F);
          setRotation(FortFloor3, 1.570796F, 0F, 1.570796F);
          
          FortBackWall = new ModelRenderer(this, 0, 176);
          FortBackWall.addBox(-5F, -6.2F, -6F, 1, 8, 12);
          FortBackWall.setRotationPoint(0F, -16F, 10F);
          setRotation(FortBackWall, 0F, 1.570796F, 0F);
          
          FortBackLeftWall = new ModelRenderer(this, 0, 176);
          FortBackLeftWall.addBox(6F, -6F, -7F, 1, 8, 12);
          FortBackLeftWall.setRotationPoint(0F, -16F, 10F);
          
          FortBackRightWall = new ModelRenderer(this, 0, 176);
          FortBackRightWall.addBox(-7F, -6F, -7F, 1, 8, 12);
          FortBackRightWall.setRotationPoint(0F, -16F, 10F);
          
          StorageUpLeft = new ModelRenderer(this, 76, 226);
          StorageUpLeft.addBox(6.5F, 1F, -14F, 5, 8, 10);
          StorageUpLeft.setRotationPoint(0F, -16F, 10F);
          setRotation(StorageUpLeft, 0F, 0F, -0.3839724F);
          
          StorageUpRight = new ModelRenderer(this, 76, 208);
          StorageUpRight.addBox(-11.5F, 1F, -14F, 5, 8, 10);
          StorageUpRight.setRotationPoint(0F, -16F, 10F);
          setRotation(StorageUpRight, 0F, 0F, 0.3839724F);
    }

    
    
    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        //super.render(entity, f, f1, f2, f3, f4, f5);
        MoCEntityElephant elephant = (MoCEntityElephant) entity;
        byte tusks = elephant.getTusks();
        int type = elephant.getType();
        byte harness = elephant.getArmorType();
        byte storage = elephant.getStorage();
        boolean sitting = (elephant.sitCounter != 0);
        boolean moveTail = (elephant.tailCounter != 0);
        
        setRotationAngles(f, f1, f2, f3, f4, f5, tusks, sitting, moveTail);

        if (tusks == 0)
        {
            LeftTuskB.render(f5);
            RightTuskB.render(f5);
            if (elephant.getIsAdult() || elephant.getEdad() > 70)
            {
                LeftTuskC.render(f5);
                RightTuskC.render(f5);
            }
            if (elephant.getIsAdult() || elephant.getEdad() > 90)
            {
                LeftTuskD.render(f5);
                RightTuskD.render(f5);
            }
        }
        else if (tusks == 1)
        {
            TuskLW1.render(f5);
            TuskLW2.render(f5);
            TuskLW3.render(f5);
            TuskLW4.render(f5);
            TuskLW5.render(f5);
            TuskRW1.render(f5);
            TuskRW2.render(f5);
            TuskRW3.render(f5);
            TuskRW4.render(f5);
            TuskRW5.render(f5);
        }
        else if (tusks == 2)
        {
            TuskLI1.render(f5);
            TuskLI2.render(f5);
            TuskLI3.render(f5);
            TuskLI4.render(f5);
            TuskLI5.render(f5);
            TuskRI1.render(f5);
            TuskRI2.render(f5);
            TuskRI3.render(f5);
            TuskRI4.render(f5);
            TuskRI5.render(f5);
        }
        else if (tusks == 3)
        {
            TuskLD1.render(f5);
            TuskLD2.render(f5);
            TuskLD3.render(f5);
            TuskLD4.render(f5);
            TuskLD5.render(f5);
            TuskRD1.render(f5);
            TuskRD2.render(f5);
            TuskRD3.render(f5);
            TuskRD4.render(f5);
            TuskRD5.render(f5);
        }

        if (type == 1) //african
        {
            LeftBigEar.render(f5);
            RightBigEar.render(f5);
        }
        else
        {
            LeftSmallEar.render(f5);
            RightSmallEar.render(f5);
        }

        if (type == 3 || type == 4) //mammoths
        {
            HeadBump.render(f5);
            Skirt.render(f5);
        }

        if (harness >= 1)
        {
            HarnessBlanket.render(f5);
            HarnessUpperBelt.render(f5);
            HarnessLowerBelt.render(f5);
            if (type == 5)
            {
                Skirt.render(f5);
            }
        }

        if (harness == 3)
        {
            if (type == 5)
            {
                CabinPillow.render(f5);
                CabinLeftRail.render(f5);
                Cabin.render(f5);
                CabinRightRail.render(f5);
                CabinBackRail.render(f5);
                CabinRoof.render(f5);
            }

            if (type == 4)
            {
                
                FortBackRightWall.render(f5);
                FortBackLeftWall.render(f5);
                FortBackWall.render(f5);
                FortFloor1.render(f5);
                FortFloor2.render(f5);
                FortFloor3.render(f5);
                FortNeckBeam.render(f5);
                FortBackBeam.render(f5);
                
            }

        }

        if (storage >= 1)
        {
            StorageRightBedroll.render(f5);
            StorageFrontRightChest.render(f5);
            StorageBackRightChest.render(f5);
            StorageRightBlankets.render(f5);

        }
        if (storage >= 2)
        {
            StorageLeftBlankets.render(f5);
            StorageLeftBedroll.render(f5);
            StorageFrontLeftChest.render(f5);
            StorageBackLeftChest.render(f5);

        }
        if (storage >= 3)
        {
           StorageUpLeft.render(f5);
        }
        
        if (storage >= 4)
        {
            StorageUpRight.render(f5);
        }
        

        Head.render(f5);
        Neck.render(f5);
        Chin.render(f5);
        LowerLip.render(f5);
        Back.render(f5);

        Hump.render(f5);
        Body.render(f5);

        RightTuskA.render(f5);
        LeftTuskA.render(f5);

        TrunkA.render(f5);
        TrunkB.render(f5);
        TrunkC.render(f5);
        TrunkD.render(f5);
        TrunkE.render(f5);
        FrontRightUpperLeg.render(f5);
        FrontRightLowerLeg.render(f5);
        FrontLeftUpperLeg.render(f5);
        FrontLeftLowerLeg.render(f5);
        BackRightUpperLeg.render(f5);
        BackRightLowerLeg.render(f5);
        BackLeftUpperLeg.render(f5);
        BackLeftLowerLeg.render(f5);
        TailRoot.render(f5);
        Tail.render(f5);
        TailPlush.render(f5);

    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    /**
     * Used to adjust the Y offset of the model cubes
     */
    private void AdjustY(float f)
    {
        float yOff = f;
        Head.rotationPointY = yOff + -10F;
        Neck.rotationPointY = yOff + -8F;
        HeadBump.rotationPointY = yOff + -10F;
        Chin.rotationPointY = yOff + -10F;
        LowerLip.rotationPointY = yOff + -10F;
        Back.rotationPointY = yOff + -4F;
        LeftSmallEar.rotationPointY = yOff + -10F;
        LeftBigEar.rotationPointY = yOff + -10F;
        RightSmallEar.rotationPointY = yOff + -10F;
        RightBigEar.rotationPointY = yOff + -10F;
        Hump.rotationPointY = yOff + -13F;
        Body.rotationPointY = yOff + -2F;
        Skirt.rotationPointY = yOff + 8F;
        RightTuskA.rotationPointY = yOff + -10F;
        RightTuskB.rotationPointY = yOff + -10F;
        RightTuskC.rotationPointY = yOff + -10F;
        RightTuskD.rotationPointY = yOff + -10F;
        LeftTuskA.rotationPointY = yOff + -10F;
        LeftTuskB.rotationPointY = yOff + -10F;
        LeftTuskC.rotationPointY = yOff + -10F;
        LeftTuskD.rotationPointY = yOff + -10F;
        TrunkA.rotationPointY = yOff + -3F;
        TrunkB.rotationPointY = yOff + 5.5F;
        TrunkC.rotationPointY = yOff + 13F;
        TrunkD.rotationPointY = yOff + 16F;
        TrunkE.rotationPointY = yOff + 19.5F;
        FrontRightUpperLeg.rotationPointY = yOff +  4F;
        FrontRightLowerLeg.rotationPointY = yOff + 14F;
        FrontLeftUpperLeg.rotationPointY = yOff +4F;
        FrontLeftLowerLeg.rotationPointY = yOff + 14F;
        BackRightUpperLeg.rotationPointY = yOff + 4F;
        BackRightLowerLeg.rotationPointY = yOff + 14F;
        BackLeftUpperLeg.rotationPointY = yOff + 4F;
        BackLeftLowerLeg.rotationPointY = yOff + 14F;
        TailRoot.rotationPointY = yOff + -8F;
        Tail.rotationPointY = yOff + -8F;
        TailPlush.rotationPointY = yOff + -8F;
        StorageRightBedroll.rotationPointY = yOff + -10.2F;
        StorageLeftBedroll.rotationPointY = yOff + -10.2F;
        StorageFrontRightChest.rotationPointY = yOff + -1.2F;
        StorageBackRightChest.rotationPointY = yOff + -1.2F;
        StorageFrontLeftChest.rotationPointY = yOff + -1.2F;
        StorageBackLeftChest.rotationPointY = yOff + -1.2F;
        StorageRightBlankets.rotationPointY = yOff + -10.2F;
        StorageLeftBlankets.rotationPointY = yOff + -10.2F;
        HarnessBlanket.rotationPointY = yOff + -13.2F;
        HarnessUpperBelt.rotationPointY = yOff + -2F;
        HarnessLowerBelt.rotationPointY = yOff + -2F;
        CabinPillow.rotationPointY = yOff + -16F;
        CabinLeftRail.rotationPointY = yOff + -23F;
        Cabin.rotationPointY = yOff + -35F;
        CabinRightRail.rotationPointY = yOff + -23F;
        CabinBackRail.rotationPointY = yOff + -23F;
        CabinRoof.rotationPointY = yOff + -34F;
        FortBackRightWall.rotationPointY = yOff + -16F;
        FortBackLeftWall.rotationPointY = yOff + -16F;
        FortBackWall.rotationPointY = yOff + -16F;
        FortNeckBeam.rotationPointY = yOff + -16F;
        FortBackBeam.rotationPointY = yOff + -16F;
        FortFloor1.rotationPointY = yOff + -16F;
        FortFloor2.rotationPointY = yOff + -16F;
        FortFloor3.rotationPointY = yOff + -16F;
        
        StorageUpLeft.rotationPointY = yOff + -16F;
        StorageUpRight.rotationPointY = yOff + -16F;
        
        TuskLD1.rotationPointY = yOff + -10F;
        TuskLD2.rotationPointY = yOff + -10F;
        TuskLD3.rotationPointY = yOff + -10F;
        TuskLD4.rotationPointY = yOff + -10F;
        TuskLD5.rotationPointY = yOff + -10F;
        TuskRD1.rotationPointY = yOff + -10F;
        TuskRD2.rotationPointY = yOff + -10F;
        TuskRD3.rotationPointY = yOff + -10F;
        TuskRD4.rotationPointY = yOff + -10F;
        TuskRD5.rotationPointY = yOff + -10F;
        TuskLI1.rotationPointY = yOff + -10F;
        TuskLI2.rotationPointY = yOff + -10F;
        TuskLI3.rotationPointY = yOff + -10F;
        TuskLI4.rotationPointY = yOff + -10F;
        TuskLI5.rotationPointY = yOff + -10F;
        TuskRI1.rotationPointY = yOff + -10F;
        TuskRI2.rotationPointY = yOff + -10F;
        TuskRI3.rotationPointY = yOff + -10F;
        TuskRI4.rotationPointY = yOff + -10F;
        TuskRI5.rotationPointY = yOff + -10F;
        TuskLW1.rotationPointY = yOff + -10F;
        TuskLW2.rotationPointY = yOff + -10F;
        TuskLW3.rotationPointY = yOff + -10F;
        TuskLW4.rotationPointY = yOff + -10F;
        TuskLW5.rotationPointY = yOff + -10F;
        TuskRW1.rotationPointY = yOff + -10F;
        TuskRW2.rotationPointY = yOff + -10F;
        TuskRW3.rotationPointY = yOff + -10F;
        TuskRW4.rotationPointY = yOff + -10F;
        TuskRW5.rotationPointY = yOff + -10F;
        
    }
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, byte tusks, boolean sitting, boolean tail)
    {
        
        float RLegXRot = MathHelper.cos((f * 0.6662F) + 3.141593F) * 0.8F * f1;
        float LLegXRot = MathHelper.cos(f * 0.6662F) * 0.8F * f1;

        if (f4 < 0)
        {
            f4 = 0;
        }

        float HeadXRot = (f4 / 57.29578F);
        if (f3 > 20F)
        {
            f3 = 20F;
        }
        if (f3 < -20F)
        {
            f3 = -20F;
        }
        float HeadYRot = (f3 / 57.29578F);

        float f10 = 0F;
        if (sitting) f10 = 8F;
        AdjustY(f10);
        /**
         * Random Trunk animation
         */
        float TrunkXRot = f1 * 5F;//(f1 * 50F);//10F;

        if (f1 > 0.5) //walking fast
        {
            HeadXRot = 0F;
            TrunkXRot = MathHelper.cos(f2 * 0.35F) * 4F;
        }
        
        else
        {
            TrunkXRot = (f1 * 50F);//10F;
            //TrunkXRot = 40F - MathHelper.cos(f2 * 0.1F) * 10F;
            float f2b = f2 % 200F;
            if (f2b > 0 & f2b < 40)
            {
                HeadXRot = 0F;
                //TrunkXRot = (f2b * 50F);//10F;
                TrunkXRot = 15F - MathHelper.cos(f2 * 0.2F) * 15F;
            }

        }

        if (sitting)
        {
            HeadXRot = 0F;
            TrunkXRot = 25F;
        }
        Head.rotateAngleX = (-10F / radianF) + HeadXRot;

        Head.rotateAngleY = HeadYRot;
        HeadBump.rotateAngleY = Head.rotateAngleY;
        HeadBump.rotateAngleX = Head.rotateAngleX;

        RightTuskA.rotateAngleY = HeadYRot;
        LeftTuskA.rotateAngleY = HeadYRot;
        RightTuskA.rotateAngleX = (70F / radianF) + HeadXRot;
        LeftTuskA.rotateAngleX = (70F / radianF) + HeadXRot;

        Chin.rotateAngleY = HeadYRot;
        Chin.rotateAngleX = (113F / radianF) + HeadXRot;
        LowerLip.rotateAngleY = HeadYRot;
        LowerLip.rotateAngleX = (85F / radianF) + HeadXRot;

        

        //ears
        /**
         * f = distance walked f1 = speed 0 - 1 f2 = timer
         */
        /**
         * Ear random animation
         */
        float EarF = 0F;

        float f2a = f2 % 100F;
        if (f2a > 60 & f2a < 90)
        {
            EarF = MathHelper.cos(f2 * 0.5F) * 0.35F;
        }

        RightBigEar.rotateAngleY = (30F / radianF) + HeadYRot + EarF;
        RightSmallEar.rotateAngleY = (30F / radianF) + HeadYRot + EarF;
        LeftBigEar.rotateAngleY = (-30F / radianF) + HeadYRot - EarF;
        LeftSmallEar.rotateAngleY = (-30F / radianF) + HeadYRot - EarF;

        RightBigEar.rotateAngleX = (-10F / radianF) + HeadXRot;
        RightSmallEar.rotateAngleX = (-10F / radianF) + HeadXRot;
        LeftBigEar.rotateAngleX = (-10F / radianF) + HeadXRot;
        LeftSmallEar.rotateAngleX = (-10F / radianF) + HeadXRot;

        //TrunkA.rotateAngleX = -50F / radianF;

        //TrunkA.rotationPointY = -3F;
        TrunkA.rotationPointZ = -22.50F;
        adjustAllRotationPoints(TrunkA, Head);

        TrunkA.rotateAngleY = HeadYRot;
        float TrunkARotX = (90F - TrunkXRot);
        if (TrunkARotX < 85)
        {
            TrunkARotX = 85;
        }
        TrunkA.rotateAngleX = ((TrunkARotX) / radianF) + HeadXRot;

        //TrunkB.rotationPointY = 5.5F;
        TrunkB.rotationPointZ = -22.5F;
        //TrunkB.setRotationPoint(0F, 6.5F, -22.5F);
        adjustAllRotationPoints(TrunkB, TrunkA);
        TrunkB.rotateAngleY = HeadYRot;
        TrunkB.rotateAngleX = ((95F - TrunkXRot * 1.5F) / radianF) + HeadXRot;

        //TrunkC.setRotationPoint(0F, 13F, -22.0F);
        //TrunkC.rotationPointY = 13F;
        TrunkC.rotationPointZ = -22.5F;
        adjustAllRotationPoints(TrunkC, TrunkB);
        TrunkC.rotateAngleY = HeadYRot;
        TrunkC.rotateAngleX = ((110F - TrunkXRot * 3F) / radianF) + HeadXRot;

        //TrunkD.rotationPointY = 16F;
        TrunkD.rotationPointZ = -21.5F;
        adjustAllRotationPoints(TrunkD, TrunkC);
        TrunkD.rotateAngleY = HeadYRot;
        TrunkD.rotateAngleX = ((127F - TrunkXRot * 4.5F) / radianF) + HeadXRot;

        //TrunkE.rotationPointY = 19.5F;
        TrunkE.rotationPointZ = -19F;
        adjustAllRotationPoints(TrunkE, TrunkD);
        TrunkE.rotateAngleY = HeadYRot;
        TrunkE.rotateAngleX = ((145F - TrunkXRot * 6F) / radianF) + HeadXRot;
        
        
        //legs
        if (sitting)
        {
            FrontRightUpperLeg.rotateAngleX = -30F/radianF;
            FrontLeftUpperLeg.rotateAngleX = -30F/radianF;
            BackLeftUpperLeg.rotateAngleX = -30F/radianF;
            BackRightUpperLeg.rotateAngleX = -30F/radianF;
        }
        else
        {
            FrontRightUpperLeg.rotateAngleX = RLegXRot;
            FrontLeftUpperLeg.rotateAngleX = LLegXRot;
            BackLeftUpperLeg.rotateAngleX = RLegXRot;
            BackRightUpperLeg.rotateAngleX = LLegXRot;
        }
        

        adjustXRotationPoints(FrontRightLowerLeg, FrontRightUpperLeg);
        adjustXRotationPoints(BackRightLowerLeg, BackRightUpperLeg);
        adjustXRotationPoints(FrontLeftLowerLeg, FrontLeftUpperLeg);
        adjustXRotationPoints(BackLeftLowerLeg, BackLeftUpperLeg);

        //To convert from degrees to radians, multiply by ((PI)/180o).  
        //To convert from radians to degrees, multiply by (180o/(PI)). 
        if (sitting)
        {
            FrontLeftLowerLeg.rotateAngleX = 90F / radianF;
            FrontRightLowerLeg.rotateAngleX = 90F / radianF;
            BackLeftLowerLeg.rotateAngleX = 90F / radianF;
            BackRightLowerLeg.rotateAngleX = 90F / radianF;
        }
        else
        {
            float LLegXRotD = (LLegXRot * (float) (180 / Math.PI));
            float RLegXRotD = (RLegXRot * (float) (180 / Math.PI));

            if (LLegXRotD > 0F)
            {
                LLegXRotD *= 2F;
            }
            if (RLegXRotD > 0F)
            {
                RLegXRotD *= 2F;
            }

            FrontLeftLowerLeg.rotateAngleX = LLegXRotD / radianF;
            FrontRightLowerLeg.rotateAngleX = RLegXRotD / radianF;
            BackLeftLowerLeg.rotateAngleX = RLegXRotD / radianF;
            BackRightLowerLeg.rotateAngleX = LLegXRotD / radianF;
        }
        

        if (tusks == 0)
        {
            LeftTuskB.rotateAngleY = HeadYRot;
            LeftTuskC.rotateAngleY = HeadYRot;
            LeftTuskD.rotateAngleY = HeadYRot;
            RightTuskB.rotateAngleY = HeadYRot;
            RightTuskC.rotateAngleY = HeadYRot;
            RightTuskD.rotateAngleY = HeadYRot;

            LeftTuskB.rotateAngleX = (40F / radianF) + HeadXRot;
            LeftTuskC.rotateAngleX = (10F / radianF) + HeadXRot;
            LeftTuskD.rotateAngleX = (-20F / radianF) + HeadXRot;
            RightTuskB.rotateAngleX = (40F / radianF) + HeadXRot;
            RightTuskC.rotateAngleX = (10F / radianF) + HeadXRot;
            RightTuskD.rotateAngleX = (-20F / radianF) + HeadXRot;
        }
        else if (tusks == 1)
        {
            TuskLW1.rotateAngleY = HeadYRot;
            TuskLW2.rotateAngleY = HeadYRot;
            TuskLW3.rotateAngleY = HeadYRot;
            TuskLW4.rotateAngleY = HeadYRot;
            TuskLW5.rotateAngleY = HeadYRot;
            TuskRW1.rotateAngleY = HeadYRot;
            TuskRW2.rotateAngleY = HeadYRot;
            TuskRW3.rotateAngleY = HeadYRot;
            TuskRW4.rotateAngleY = HeadYRot;
            TuskRW5.rotateAngleY = HeadYRot;

            TuskLW1.rotateAngleX = (40F / radianF) + HeadXRot;
            TuskLW2.rotateAngleX = (10F / radianF) + HeadXRot;
            TuskLW3.rotateAngleX = (-20F / radianF) + HeadXRot;
            TuskLW4.rotateAngleX = (10F / radianF) + HeadXRot;
            TuskLW5.rotateAngleX = (-20F / radianF) + HeadXRot;
            TuskRW1.rotateAngleX = (40F / radianF) + HeadXRot;
            TuskRW2.rotateAngleX = (10F / radianF) + HeadXRot;
            TuskRW3.rotateAngleX = (-20F / radianF) + HeadXRot;
            TuskRW4.rotateAngleX = (10F / radianF) + HeadXRot;
            TuskRW5.rotateAngleX = (-20F / radianF) + HeadXRot;
        }
        else if (tusks == 2)
        {
            TuskLI1.rotateAngleY = HeadYRot;
            TuskLI2.rotateAngleY = HeadYRot;
            TuskLI3.rotateAngleY = HeadYRot;
            TuskLI4.rotateAngleY = HeadYRot;
            TuskLI5.rotateAngleY = HeadYRot;
            TuskRI1.rotateAngleY = HeadYRot;
            TuskRI2.rotateAngleY = HeadYRot;
            TuskRI3.rotateAngleY = HeadYRot;
            TuskRI4.rotateAngleY = HeadYRot;
            TuskRI5.rotateAngleY = HeadYRot;

            TuskLI1.rotateAngleX = (40F / radianF) + HeadXRot;
            TuskLI2.rotateAngleX = (10F / radianF) + HeadXRot;
            TuskLI3.rotateAngleX = (-20F / radianF) + HeadXRot;
            TuskLI4.rotateAngleX = (10F / radianF) + HeadXRot;
            TuskLI5.rotateAngleX = (-20F / radianF) + HeadXRot;
            TuskRI1.rotateAngleX = (40F / radianF) + HeadXRot;
            TuskRI2.rotateAngleX = (10F / radianF) + HeadXRot;
            TuskRI3.rotateAngleX = (-20F / radianF) + HeadXRot;
            TuskRI4.rotateAngleX = (10F / radianF) + HeadXRot;
            TuskRI5.rotateAngleX = (-20F / radianF) + HeadXRot;
        }
        else if (tusks == 3)
        {
            TuskLD1.rotateAngleY = HeadYRot;
            TuskLD2.rotateAngleY = HeadYRot;
            TuskLD3.rotateAngleY = HeadYRot;
            TuskLD4.rotateAngleY = HeadYRot;
            TuskLD5.rotateAngleY = HeadYRot;
            TuskRD1.rotateAngleY = HeadYRot;
            TuskRD2.rotateAngleY = HeadYRot;
            TuskRD3.rotateAngleY = HeadYRot;
            TuskRD4.rotateAngleY = HeadYRot;
            TuskRD5.rotateAngleY = HeadYRot;

            TuskLD1.rotateAngleX = (40F / radianF) + HeadXRot;
            TuskLD2.rotateAngleX = (10F / radianF) + HeadXRot;
            TuskLD3.rotateAngleX = (-20F / radianF) + HeadXRot;
            TuskLD4.rotateAngleX = (10F / radianF) + HeadXRot;
            TuskLD5.rotateAngleX = (-20F / radianF) + HeadXRot;
            TuskRD1.rotateAngleX = (40F / radianF) + HeadXRot;
            TuskRD2.rotateAngleX = (10F / radianF) + HeadXRot;
            TuskRD3.rotateAngleX = (-20F / radianF) + HeadXRot;
            TuskRD4.rotateAngleX = (10F / radianF) + HeadXRot;
            TuskRD5.rotateAngleX = (-20F / radianF) + HeadXRot;
        }

        //chest anim
        StorageLeftBedroll.rotateAngleX = LLegXRot / 10F;
        StorageFrontLeftChest.rotateAngleX = LLegXRot / 5F;
        StorageBackLeftChest.rotateAngleX = LLegXRot / 5F;

        StorageRightBedroll.rotateAngleX = RLegXRot / 10F;
        StorageFrontRightChest.rotateAngleX = RLegXRot / 5F;
        StorageBackRightChest.rotateAngleX = RLegXRot / 5F;

        FortNeckBeam.rotateAngleZ = LLegXRot / 50F;
        FortBackBeam.rotateAngleZ = LLegXRot / 50F;

        FortBackRightWall.rotateAngleZ = (LLegXRot / 50F);
        FortBackLeftWall.rotateAngleZ =  (LLegXRot / 50F);
        FortBackWall.rotateAngleX = 0F -(LLegXRot / 50F);
        
        
        //f1 = movement speed!
        //f2 = timer!
        //tail
        float tailMov = (f1 * 0.9F);
        if (tailMov < 0)
        {
            tailMov = 0;
        }

        if (tail)
        {
            TailRoot.rotateAngleY = MathHelper.cos(f2 * 0.4F) * 1.3F;
            tailMov = 30F / radianF;
        }
        else
        {
            TailRoot.rotateAngleY = 0F;
        }

        TailRoot.rotateAngleX = (17F / radianF) + tailMov;
        TailPlush.rotateAngleX = Tail.rotateAngleX = (6.5F / radianF) + tailMov;
        TailPlush.rotateAngleY = TailRoot.rotateAngleY;
        Tail.rotateAngleY = TailPlush.rotateAngleY;

    }

    /**
     * Used for leg adjustment - anteroposterior movement
     * 
     * @param target
     * @param attached
     */
    private void adjustXRotationPoints(ModelRenderer target, ModelRenderer origin)
    {
        //rotation point Z and Y adjusted for legs =
        //Z rotation point = attached rotation point Z + sin(attached.rotateangleX) * distance 
        //Y rotation point = attached rotation point Y + cos(attached.rotateangleX) * distance 
        float distance = target.rotationPointY - origin.rotationPointY;
        if (distance < 0F)
        {
            distance *= -1F;
        }
        target.rotationPointZ = origin.rotationPointZ + (MathHelper.sin(origin.rotateAngleX) * distance);
        target.rotationPointY = origin.rotationPointY + (MathHelper.cos(origin.rotateAngleX) * distance);

    }

    /**
     * Used for trunk adjustment - lateral movement
     * 
     * @param target
     *            : target model
     * @param origin
     *            : origin model
     */
    private void adjustYRotationPoints(ModelRenderer target, ModelRenderer origin)
    {
        //rotation point Z and X adjusted for head =
        //Z rotation point = attached rotation point Z - cos(attached.rotateangleX) * distance 
        //Y rotation point = attached rotation point Y - sin(attached.rotateangleX) * distance 
        float distanceZ = 0F;
        if (target.rotationPointZ > origin.rotationPointZ)
        {
            distanceZ = target.rotationPointZ - origin.rotationPointZ;
        }
        else
        {
            distanceZ = origin.rotationPointZ - target.rotationPointZ;
        }
        /*float distanceZ = target.rotationPointZ - origin.rotationPointZ;
        if (distanceZ < 0F)
        {
            distanceZ *= -1F;
        }*/
        target.rotationPointZ = origin.rotationPointZ - (MathHelper.cos(origin.rotateAngleY) * distanceZ);
        target.rotationPointX = origin.rotationPointX - (MathHelper.sin(origin.rotateAngleY) * distanceZ);

    }

    private void adjustAllRotationPoints(ModelRenderer target, ModelRenderer origin)
    {

        float distanceY = 0F;
        if (target.rotationPointY > origin.rotationPointY)
        {
            distanceY = target.rotationPointY - origin.rotationPointY;
        }
        else
        {
            distanceY = origin.rotationPointY - target.rotationPointY;
        }

        float distanceZ = 0F;
        if (target.rotationPointZ > origin.rotationPointZ)
        {
            distanceZ = target.rotationPointZ - origin.rotationPointZ;
        }
        else
        {
            distanceZ = origin.rotationPointZ - target.rotationPointZ;
        }

        target.rotationPointY = origin.rotationPointY + MathHelper.sin(origin.rotateAngleX) * distanceY;
        target.rotationPointZ = origin.rotationPointZ - MathHelper.cos(origin.rotateAngleY) * (MathHelper.cos(origin.rotateAngleX) * distanceY);
        target.rotationPointX = origin.rotationPointX - MathHelper.sin(origin.rotateAngleY) * (MathHelper.cos(origin.rotateAngleX) * distanceY);

        
    }


}

