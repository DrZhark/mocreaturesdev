package drzhark.mocreatures.client;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntitySpellParticleFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.ListBox;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.model.SimpleButtonModel;
import drzhark.guiapi.GuiApiHelper;
import drzhark.guiapi.GuiModScreen;
import drzhark.guiapi.ModAction;
import drzhark.guiapi.ModSettingScreen;
import drzhark.guiapi.widget.WidgetBoolean;
import drzhark.guiapi.widget.WidgetClassicTwocolumn;
import drzhark.guiapi.widget.WidgetFloat;
import drzhark.guiapi.widget.WidgetInt;
import drzhark.guiapi.widget.WidgetList;
import drzhark.guiapi.widget.WidgetMulti;
import drzhark.guiapi.widget.WidgetSimplewindow;
import drzhark.guiapi.widget.WidgetSinglecolumn;
import drzhark.mocreatures.MoCEntityData;
import drzhark.mocreatures.MoCProxy;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.audio.MoCSounds;
import drzhark.mocreatures.client.gui.helpers.MoCGUIEntityNamer;
import drzhark.mocreatures.client.gui.helpers.MoCSettingBoolean;
import drzhark.mocreatures.client.gui.helpers.MoCSettingFloat;
import drzhark.mocreatures.client.gui.helpers.MoCSettingInt;
import drzhark.mocreatures.client.gui.helpers.MoCSettingList;
import drzhark.mocreatures.client.gui.helpers.MoCSettingMulti;
import drzhark.mocreatures.client.gui.helpers.MoCSettings;
import drzhark.mocreatures.client.model.MoCModelAnt;
import drzhark.mocreatures.client.model.MoCModelBear;
import drzhark.mocreatures.client.model.MoCModelBee;
import drzhark.mocreatures.client.model.MoCModelBigCat1;
import drzhark.mocreatures.client.model.MoCModelBigCat2;
import drzhark.mocreatures.client.model.MoCModelBird;
import drzhark.mocreatures.client.model.MoCModelBoar;
import drzhark.mocreatures.client.model.MoCModelBunny;
import drzhark.mocreatures.client.model.MoCModelButterfly;
import drzhark.mocreatures.client.model.MoCModelCrab;
import drzhark.mocreatures.client.model.MoCModelCricket;
import drzhark.mocreatures.client.model.MoCModelCrocodile;
import drzhark.mocreatures.client.model.MoCModelDeer;
import drzhark.mocreatures.client.model.MoCModelDolphin;
import drzhark.mocreatures.client.model.MoCModelDragonfly;
import drzhark.mocreatures.client.model.MoCModelDuck;
import drzhark.mocreatures.client.model.MoCModelEgg;
import drzhark.mocreatures.client.model.MoCModelElephant;
import drzhark.mocreatures.client.model.MoCModelEnt;
import drzhark.mocreatures.client.model.MoCModelFirefly;
import drzhark.mocreatures.client.model.MoCModelFishBowl;
import drzhark.mocreatures.client.model.MoCModelFishy;
import drzhark.mocreatures.client.model.MoCModelFly;
import drzhark.mocreatures.client.model.MoCModelFox;
import drzhark.mocreatures.client.model.MoCModelGoat;
import drzhark.mocreatures.client.model.MoCModelGolem;
import drzhark.mocreatures.client.model.MoCModelJellyFish;
import drzhark.mocreatures.client.model.MoCModelKitty;
import drzhark.mocreatures.client.model.MoCModelKittyBed;
import drzhark.mocreatures.client.model.MoCModelKittyBed2;
import drzhark.mocreatures.client.model.MoCModelKomodo;
import drzhark.mocreatures.client.model.MoCModelLitterBox;
import drzhark.mocreatures.client.model.MoCModelMaggot;
import drzhark.mocreatures.client.model.MoCModelMediumFish;
import drzhark.mocreatures.client.model.MoCModelMiniGolem;
import drzhark.mocreatures.client.model.MoCModelMole;
import drzhark.mocreatures.client.model.MoCModelMouse;
import drzhark.mocreatures.client.model.MoCModelNewHorse;
import drzhark.mocreatures.client.model.MoCModelNewHorseMob;
import drzhark.mocreatures.client.model.MoCModelOgre;
import drzhark.mocreatures.client.model.MoCModelOstrich;
import drzhark.mocreatures.client.model.MoCModelPetScorpion;
import drzhark.mocreatures.client.model.MoCModelRaccoon;
import drzhark.mocreatures.client.model.MoCModelRat;
import drzhark.mocreatures.client.model.MoCModelRay;
import drzhark.mocreatures.client.model.MoCModelRoach;
import drzhark.mocreatures.client.model.MoCModelScorpion;
import drzhark.mocreatures.client.model.MoCModelShark;
import drzhark.mocreatures.client.model.MoCModelSilverSkeleton;
import drzhark.mocreatures.client.model.MoCModelSmallFish;
import drzhark.mocreatures.client.model.MoCModelSnail;
import drzhark.mocreatures.client.model.MoCModelSnake;
import drzhark.mocreatures.client.model.MoCModelTurkey;
import drzhark.mocreatures.client.model.MoCModelTurtle;
import drzhark.mocreatures.client.model.MoCModelWere;
import drzhark.mocreatures.client.model.MoCModelWereHuman;
import drzhark.mocreatures.client.model.MoCModelWolf;
import drzhark.mocreatures.client.model.MoCModelWraith;
import drzhark.mocreatures.client.model.MoCModelWyvern;
import drzhark.mocreatures.client.renderer.entity.MoCRenderBear;
import drzhark.mocreatures.client.renderer.entity.MoCRenderBigCat;
import drzhark.mocreatures.client.renderer.entity.MoCRenderBird;
import drzhark.mocreatures.client.renderer.entity.MoCRenderBoar;
import drzhark.mocreatures.client.renderer.entity.MoCRenderBunny;
import drzhark.mocreatures.client.renderer.entity.MoCRenderButterfly;
import drzhark.mocreatures.client.renderer.entity.MoCRenderCricket;
import drzhark.mocreatures.client.renderer.entity.MoCRenderCrocodile;
import drzhark.mocreatures.client.renderer.entity.MoCRenderDeer;
import drzhark.mocreatures.client.renderer.entity.MoCRenderDolphin;
import drzhark.mocreatures.client.renderer.entity.MoCRenderEgg;
import drzhark.mocreatures.client.renderer.entity.MoCRenderFirefly;
import drzhark.mocreatures.client.renderer.entity.MoCRenderFishBowl;
import drzhark.mocreatures.client.renderer.entity.MoCRenderGoat;
import drzhark.mocreatures.client.renderer.entity.MoCRenderGolem;
import drzhark.mocreatures.client.renderer.entity.MoCRenderHellRat;
import drzhark.mocreatures.client.renderer.entity.MoCRenderHorseMob;
import drzhark.mocreatures.client.renderer.entity.MoCRenderInsect;
import drzhark.mocreatures.client.renderer.entity.MoCRenderKitty;
import drzhark.mocreatures.client.renderer.entity.MoCRenderKittyBed;
import drzhark.mocreatures.client.renderer.entity.MoCRenderLitterBox;
import drzhark.mocreatures.client.renderer.entity.MoCRenderMoC;
import drzhark.mocreatures.client.renderer.entity.MoCRenderMouse;
import drzhark.mocreatures.client.renderer.entity.MoCRenderNewHorse;
import drzhark.mocreatures.client.renderer.entity.MoCRenderOstrich;
import drzhark.mocreatures.client.renderer.entity.MoCRenderPetScorpion;
import drzhark.mocreatures.client.renderer.entity.MoCRenderPlatform;
import drzhark.mocreatures.client.renderer.entity.MoCRenderRat;
import drzhark.mocreatures.client.renderer.entity.MoCRenderScorpion;
import drzhark.mocreatures.client.renderer.entity.MoCRenderShark;
import drzhark.mocreatures.client.renderer.entity.MoCRenderSnake;
import drzhark.mocreatures.client.renderer.entity.MoCRenderTRock;
import drzhark.mocreatures.client.renderer.entity.MoCRenderTurtle;
import drzhark.mocreatures.client.renderer.entity.MoCRenderWWolf;
import drzhark.mocreatures.client.renderer.entity.MoCRenderWerewolf;
import drzhark.mocreatures.client.renderer.entity.MoCRenderWraith;
import drzhark.mocreatures.client.renderer.texture.MoCTextures;
import drzhark.mocreatures.configuration.MoCConfiguration;
import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.ambient.MoCEntityAnt;
import drzhark.mocreatures.entity.ambient.MoCEntityBee;
import drzhark.mocreatures.entity.ambient.MoCEntityButterfly;
import drzhark.mocreatures.entity.ambient.MoCEntityCrab;
import drzhark.mocreatures.entity.ambient.MoCEntityCricket;
import drzhark.mocreatures.entity.ambient.MoCEntityDragonfly;
import drzhark.mocreatures.entity.ambient.MoCEntityFirefly;
import drzhark.mocreatures.entity.ambient.MoCEntityFly;
import drzhark.mocreatures.entity.ambient.MoCEntityMaggot;
import drzhark.mocreatures.entity.ambient.MoCEntityRoach;
import drzhark.mocreatures.entity.ambient.MoCEntitySnail;
import drzhark.mocreatures.entity.aquatic.MoCEntityDolphin;
import drzhark.mocreatures.entity.aquatic.MoCEntityFishy;
import drzhark.mocreatures.entity.aquatic.MoCEntityJellyFish;
import drzhark.mocreatures.entity.aquatic.MoCEntityMediumFish;
import drzhark.mocreatures.entity.aquatic.MoCEntityPiranha;
import drzhark.mocreatures.entity.aquatic.MoCEntityRay;
import drzhark.mocreatures.entity.aquatic.MoCEntityShark;
import drzhark.mocreatures.entity.aquatic.MoCEntitySmallFish;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import drzhark.mocreatures.entity.item.MoCEntityFishBowl;
import drzhark.mocreatures.entity.item.MoCEntityKittyBed;
import drzhark.mocreatures.entity.item.MoCEntityLitterBox;
import drzhark.mocreatures.entity.item.MoCEntityPlatform;
import drzhark.mocreatures.entity.item.MoCEntityThrowableRock;
import drzhark.mocreatures.entity.monster.MoCEntityFlameWraith;
import drzhark.mocreatures.entity.monster.MoCEntityGolem;
import drzhark.mocreatures.entity.monster.MoCEntityHellRat;
import drzhark.mocreatures.entity.monster.MoCEntityHorseMob;
import drzhark.mocreatures.entity.monster.MoCEntityMiniGolem;
import drzhark.mocreatures.entity.monster.MoCEntityOgre;
import drzhark.mocreatures.entity.monster.MoCEntityRat;
import drzhark.mocreatures.entity.monster.MoCEntityScorpion;
import drzhark.mocreatures.entity.monster.MoCEntitySilverSkeleton;
import drzhark.mocreatures.entity.monster.MoCEntityWWolf;
import drzhark.mocreatures.entity.monster.MoCEntityWerewolf;
import drzhark.mocreatures.entity.monster.MoCEntityWraith;
import drzhark.mocreatures.entity.passive.MoCEntityBear;
import drzhark.mocreatures.entity.passive.MoCEntityBigCat;
import drzhark.mocreatures.entity.passive.MoCEntityBird;
import drzhark.mocreatures.entity.passive.MoCEntityBoar;
import drzhark.mocreatures.entity.passive.MoCEntityBunny;
import drzhark.mocreatures.entity.passive.MoCEntityCrocodile;
import drzhark.mocreatures.entity.passive.MoCEntityDeer;
import drzhark.mocreatures.entity.passive.MoCEntityDuck;
import drzhark.mocreatures.entity.passive.MoCEntityElephant;
import drzhark.mocreatures.entity.passive.MoCEntityEnt;
import drzhark.mocreatures.entity.passive.MoCEntityFox;
import drzhark.mocreatures.entity.passive.MoCEntityGoat;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.entity.passive.MoCEntityKitty;
import drzhark.mocreatures.entity.passive.MoCEntityKomodo;
import drzhark.mocreatures.entity.passive.MoCEntityMole;
import drzhark.mocreatures.entity.passive.MoCEntityMouse;
import drzhark.mocreatures.entity.passive.MoCEntityOstrich;
import drzhark.mocreatures.entity.passive.MoCEntityPetScorpion;
import drzhark.mocreatures.entity.passive.MoCEntityRaccoon;
import drzhark.mocreatures.entity.passive.MoCEntitySnake;
import drzhark.mocreatures.entity.passive.MoCEntityTurkey;
import drzhark.mocreatures.entity.passive.MoCEntityTurtle;
import drzhark.mocreatures.entity.passive.MoCEntityWyvern;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageInstaSpawn;
import drzhark.mocreatures.utils.MoCLog;

public class MoCClientProxy extends MoCProxy {

    public static Minecraft mc = Minecraft.getMinecraft();
    public static MoCClientProxy instance;
    public static MoCTextures mocTextures = new MoCTextures();

    public MoCClientProxy()
    {
        instance = this;
    }

    @Override
    public void registerRenderers()
    {
    }

    @Override
    public void initTextures()
    {
        mocTextures.loadTextures();
    }

    @Override
    public ResourceLocation getTexture(String texture)
    {
        return mocTextures.getTexture(texture);
    }

    @Override
    public void registerRenderInformation()
    {
        // Register your custom renderers
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityBunny.class, new MoCRenderBunny(new MoCModelBunny(), 0.3F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityBird.class, new MoCRenderBird(new MoCModelBird(), 0.3F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityTurtle.class, new MoCRenderTurtle(new MoCModelTurtle(), 0.4F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityMouse.class, new MoCRenderMouse(new MoCModelMouse(), 0.1F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntitySnake.class, new MoCRenderSnake(new MoCModelSnake(), 0.0F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityTurkey.class, new MoCRenderMoC(new MoCModelTurkey(), 0.4F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityButterfly.class, new MoCRenderButterfly(new MoCModelButterfly()));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityHorse.class, new MoCRenderNewHorse(new MoCModelNewHorse()));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityHorseMob.class, new MoCRenderHorseMob(new MoCModelNewHorseMob()));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityBoar.class, new MoCRenderBoar(new MoCModelBoar(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityBear.class, new MoCRenderBear(new MoCModelBear(), 0.7F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityDuck.class, new MoCRenderMoC(new MoCModelDuck(), 0.3F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityBigCat.class, new MoCRenderBigCat(new MoCModelBigCat2(), new MoCModelBigCat1(), 0.7F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityDeer.class, new MoCRenderDeer(new MoCModelDeer(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityWWolf.class, new MoCRenderWWolf(new MoCModelWolf(), 0.7F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityWraith.class, new MoCRenderWraith(new MoCModelWraith(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityFlameWraith.class, new MoCRenderWraith(new MoCModelWraith(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityWerewolf.class, new MoCRenderWerewolf(new MoCModelWereHuman(), new MoCModelWere(), 0.7F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityFox.class, new MoCRenderMoC(new MoCModelFox(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityShark.class, new MoCRenderShark(new MoCModelShark(), 0.6F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityDolphin.class, new MoCRenderDolphin(new MoCModelDolphin(), 0.6F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityFishy.class, new MoCRenderMoC(new MoCModelFishy(), 0.1F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityEgg.class, new MoCRenderEgg(new MoCModelEgg(), 0.0F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityKitty.class, new MoCRenderKitty(new MoCModelKitty(0.0F, 15F), 0.4F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityKittyBed.class, new MoCRenderKittyBed(new MoCModelKittyBed(), new MoCModelKittyBed2(), 0.3F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityLitterBox.class, new MoCRenderLitterBox(new MoCModelLitterBox(), 0.3F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityRat.class, new MoCRenderRat(new MoCModelRat(), 0.2F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityHellRat.class, new MoCRenderHellRat(new MoCModelRat(), 0.4F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityScorpion.class, new MoCRenderScorpion(new MoCModelScorpion(), 0.6F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityCrocodile.class, new MoCRenderCrocodile(new MoCModelCrocodile(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityRay.class, new MoCRenderMoC(new MoCModelRay(), 0.4F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityJellyFish.class, new MoCRenderMoC(new MoCModelJellyFish(), 0.1F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityGoat.class, new MoCRenderGoat(new MoCModelGoat(), 0.3F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityFishBowl.class, new MoCRenderFishBowl(new MoCModelFishBowl(), 0.3F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityOstrich.class, new MoCRenderOstrich(new MoCModelOstrich(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityBee.class, new MoCRenderInsect(new MoCModelBee()));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityFly.class, new MoCRenderInsect(new MoCModelFly()));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityDragonfly.class, new MoCRenderInsect(new MoCModelDragonfly()));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityFirefly.class, new MoCRenderFirefly(new MoCModelFirefly()));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityCricket.class, new MoCRenderCricket(new MoCModelCricket()));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntitySnail.class, new MoCRenderMoC(new MoCModelSnail(), 0.0F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityGolem.class, new MoCRenderGolem(new MoCModelGolem(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityThrowableRock.class, new MoCRenderTRock());
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityPetScorpion.class, new MoCRenderPetScorpion(new MoCModelPetScorpion(), 0.6F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityPlatform.class, new MoCRenderPlatform());
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityElephant.class, new MoCRenderMoC(new MoCModelElephant(), 0.7F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityKomodo.class, new MoCRenderMoC(new MoCModelKomodo(), 0.3F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityWyvern.class, new MoCRenderMoC(new MoCModelWyvern(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityOgre.class, new MoCRenderMoC(new MoCModelOgre(), 0.6F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityRoach.class, new MoCRenderInsect(new MoCModelRoach()));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityMaggot.class, new MoCRenderMoC(new MoCModelMaggot(), 0F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityCrab.class, new MoCRenderMoC(new MoCModelCrab(), 0.2F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityRaccoon.class, new MoCRenderMoC(new MoCModelRaccoon(), 0.4F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityMiniGolem.class, new MoCRenderMoC(new MoCModelMiniGolem(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntitySilverSkeleton.class, new MoCRenderMoC(new MoCModelSilverSkeleton(), 0.6F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityAnt.class, new MoCRenderInsect(new MoCModelAnt()));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityMediumFish.class, new MoCRenderMoC(new MoCModelMediumFish(), 0.2F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntitySmallFish.class, new MoCRenderMoC(new MoCModelSmallFish(), 0.1F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityPiranha.class, new MoCRenderMoC(new MoCModelSmallFish(), 0.1F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityEnt.class, new MoCRenderMoC(new MoCModelEnt(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityMole.class, new MoCRenderMoC(new MoCModelMole(), 0.3F));
        
    }

    @Override
    //public EntityClientPlayerMP getPlayer()
    public EntityPlayer getPlayer()
    {
        return this.mc.thePlayer;
    }

    /**
     * Sets the name client side. Name is synchronized with datawatchers
     * 
     * @param player
     * @param mocanimal
     */
    @Override
    public void setName(EntityPlayer player, IMoCEntity mocanimal)
    {
        mc.displayGuiScreen(new MoCGUIEntityNamer(mocanimal, mocanimal.getName()));

    }

    

    @Override
    public void UndeadFX(Entity entity)
    {
        //if (!((Boolean) MoCreatures.particleFX.get()).booleanValue()) return;
        int densityInt = (MoCreatures.proxy.getParticleFX());
        if (densityInt == 0) { return; }

        int i = (densityInt / 2) * (entity.worldObj.rand.nextInt(2) + 1);
        if (i == 0)
        {
            i = 1;
        }
        if (i > 10)
        {
            i = 10;
        }
        for (int x = 0; x < i; x++)
        {
            MoCEntityFXUndead FXUndead = new MoCEntityFXUndead(entity.worldObj, entity.posX, entity.posY + (double) (entity.worldObj.rand.nextFloat() * entity.height), entity.posZ);
            mc.effectRenderer.addEffect(FXUndead);

        }
    }

    @Override
    public void StarFX(MoCEntityHorse entity)
    {
        int densityInt = MoCreatures.proxy.getParticleFX();
        if (densityInt == 0) { return; }

        if ((entity.getType() >= 50 && entity.getType() < 60) || entity.getType() == 36)
        {

            float fRed = entity.colorFX(1, entity.getType());
            float fGreen = entity.colorFX(2, entity.getType());
            float fBlue = entity.colorFX(3, entity.getType());

            int i = densityInt * entity.worldObj.rand.nextInt(2);// + 2;
            for (int x = 0; x < i; x++)
            {
                MoCEntityFXStar FXStar = new MoCEntityFXStar(mc.theWorld, entity.posX, entity.posY + (double) (entity.worldObj.rand.nextFloat() * entity.height), entity.posZ, fRed, fGreen, fBlue);
                mc.effectRenderer.addEffect(FXStar);

            }

        }
    }

    @Override
    public void LavaFX(Entity entity)
    {
        int densityInt = (MoCreatures.proxy.getParticleFX());
        if (densityInt == 0) { return; }
        double var2 = entity.worldObj.rand.nextGaussian() * 0.02D;
        double var4 = entity.worldObj.rand.nextGaussian() * 0.02D;
        double var6 = entity.worldObj.rand.nextGaussian() * 0.02D;
        mc.theWorld.spawnParticle("lava", entity.posX + (double) (entity.worldObj.rand.nextFloat() * entity.width) - (double) entity.width, entity.posY + 0.5D + (double) (entity.worldObj.rand.nextFloat() * entity.height), entity.posZ + (double) (entity.worldObj.rand.nextFloat() * entity.width) - (double) entity.width, var2, var4, var6);

    }

    @Override
    public void VanishFX(MoCEntityHorse entity)
    {
        int densityInt = (MoCreatures.proxy.getParticleFX());
        if (densityInt == 0) { return;
        }

        for (int var6 = 0; var6 < densityInt * 8; ++var6)
        {
            double newPosX = ((float) entity.posX + entity.worldObj.rand.nextFloat());
            double newPosY = 0.7D + ((float) entity.posY + entity.worldObj.rand.nextFloat());
            double newPosZ = ((float) entity.posZ + entity.worldObj.rand.nextFloat());
            int var19 = entity.worldObj.rand.nextInt(2) * 2 - 1;
            double speedY = ((double) entity.worldObj.rand.nextFloat() - 0.5D) * 0.5D;
            double speedX = (double) (entity.worldObj.rand.nextFloat() * 2.0F * (float) var19);
            double speedZ = (double) (entity.worldObj.rand.nextFloat() * 2.0F * (float) var19);

            MoCEntityFXVanish FXVanish = new MoCEntityFXVanish(entity.worldObj, newPosX, newPosY, newPosZ, speedX, speedY, speedZ, entity.colorFX(1, entity.getType()), entity.colorFX(2, entity.getType()), entity.colorFX(3, entity.getType()), false);
            mc.effectRenderer.addEffect(FXVanish);
        }
    }

    @Override
    public void MaterializeFX(MoCEntityHorse entity)
    {
        int densityInt = (MoCreatures.proxy.getParticleFX());
        if (densityInt == 0) { return; }

        for (int var6 = 0; var6 < (densityInt * 50); ++var6)
        {
            double newPosX = ((float) entity.posX + entity.worldObj.rand.nextFloat());
            double newPosY = 0.7D + ((float) entity.posY + entity.worldObj.rand.nextFloat());
            double newPosZ = ((float) entity.posZ + entity.worldObj.rand.nextFloat());
            int var19 = entity.worldObj.rand.nextInt(2) * 2 - 1;
            double speedY = ((double) entity.worldObj.rand.nextFloat() - 0.5D) * 0.5D;
            double speedX = (double) (entity.worldObj.rand.nextFloat() * 2.0F * (float) var19);
            double speedZ = (double) (entity.worldObj.rand.nextFloat() * 2.0F * (float) var19);

            MoCEntityFXVanish FXVanish = new MoCEntityFXVanish(mc.theWorld, newPosX, newPosY, newPosZ, speedX, speedY, speedZ, entity.colorFX(1, entity.getType()), entity.colorFX(2, entity.getType()), entity.colorFX(3, entity.getType()), true);
            mc.effectRenderer.addEffect(FXVanish);
        }
        
    }

    @Override
    public void VacuumFX(MoCEntityGolem entity)
    {
        int densityInt = (MoCreatures.proxy.getParticleFX());
        if (densityInt == 0) { return; }

        for (int var1 = 0; var1 < 2; ++var1)
        {
            double newPosX = entity.posX - (1.5 * Math.cos((MoCTools.realAngle(entity.rotationYaw - 90F)) / 57.29578F));
            double newPosZ = entity.posZ - (1.5 * Math.sin((MoCTools.realAngle(entity.rotationYaw - 90F)) / 57.29578F));
            double newPosY = entity.posY + ((double) entity.height - 0.8D - (double) entity.getAdjustedYOffset() * 1.8);// + (entity.worldObj.rand.nextDouble() * ((double) entity.height - (double) entity.getAdjustedYOffset() * 2));
            //adjustedYOffset from 0 (tallest) to 1.45 (on the ground)
            //height = 4F

            double speedX = (entity.worldObj.rand.nextDouble() - 0.5D) * 4.0D;
            double speedY = -entity.worldObj.rand.nextDouble();
            double speedZ = (entity.worldObj.rand.nextDouble() - 0.5D) * 4.0D;
            MoCEntityFXVacuum FXVacuum = new MoCEntityFXVacuum(mc.theWorld, newPosX, newPosY, newPosZ, speedX, speedY, speedZ, entity.colorFX(1), entity.colorFX(2), entity.colorFX(3), 146);
            mc.effectRenderer.addEffect(FXVacuum);
        }
    }

     
    @Override
    public void hammerFX(EntityPlayer entity)
    {
        int densityInt = (MoCreatures.proxy.getParticleFX());
        if (densityInt == 0) { return; }

        for (int var6 = 0; var6 < (densityInt * 10); ++var6)
        {
            double newPosX = ((float) entity.posX + entity.worldObj.rand.nextFloat());
            double newPosY = 0.3D + ((float) entity.posY + entity.worldObj.rand.nextFloat());
            double newPosZ = ((float) entity.posZ + entity.worldObj.rand.nextFloat());
            int var19 = entity.worldObj.rand.nextInt(2) * 2 - 1;
            double speedY = ((double) entity.worldObj.rand.nextFloat() - 0.5D) * 0.5D;
            double speedX = (double) (entity.worldObj.rand.nextFloat() * 2.0F * (float) var19);
            double speedZ = (double) (entity.worldObj.rand.nextFloat() * 2.0F * (float) var19);

            EntitySpellParticleFX hammerFX = new EntitySpellParticleFX(mc.theWorld, newPosX, newPosY, newPosZ, speedX, speedY, speedZ);
            ((EntitySpellParticleFX)hammerFX).setBaseSpellTextureIndex(144);
            ((EntityFX)hammerFX).setRBGColorF(74F/256F, 145F/256F, 71F/256F);
            mc.effectRenderer.addEffect(hammerFX);
        }
        
    }
    
    
    @Override
    public void teleportFX(EntityPlayer entity)
    {
        int densityInt = (MoCreatures.proxy.getParticleFX());
        if (densityInt == 0) { return; }

        for (int var6 = 0; var6 < (densityInt * 50); ++var6)
        {
            double newPosX = ((float) entity.posX + entity.worldObj.rand.nextFloat());
            double newPosY = 0.7D + ((float) entity.posY + entity.worldObj.rand.nextFloat());
            double newPosZ = ((float) entity.posZ + entity.worldObj.rand.nextFloat());
            int var19 = entity.worldObj.rand.nextInt(2) * 2 - 1;
            double speedY = ((double) entity.worldObj.rand.nextFloat() - 0.5D) * 0.5D;
            double speedX = (double) (entity.worldObj.rand.nextFloat() * 2.0F * (float) var19);
            double speedZ = (double) (entity.worldObj.rand.nextFloat() * 2.0F * (float) var19);

            MoCEntityFXVanish hammerFX = new MoCEntityFXVanish(mc.theWorld, newPosX, newPosY, newPosZ, speedX, speedY, speedZ, 189F/256F, 110F/256F, 229F/256F, true);
            mc.effectRenderer.addEffect(hammerFX);
        }
        
    }
    @Override
    public int getProxyMode()
    {
        return 2;
    }

    /**
     * GUI API options
     */
    public static MoCSettingInt despawnTickRateS;
    public static WidgetInt despawnTickRateW;
    public static MoCSettingInt lightLevelS;
    public static WidgetInt lightLevelW;
    public static MoCSettingInt despawnLightLevelS;
    public static WidgetInt despawnLightLevelW;
    public static MoCSettingBoolean spawnPiranhaS;
    public static MoCSettingInt particleFXS;
    public static WidgetInt particleFXW;
    
    public static MoCSettingBoolean animateTexturesB;
    public static WidgetBoolean animateTexturesW;

    public static MoCSettingBoolean displaynameB;
    public static WidgetBoolean displaynameW;
    public static MoCSettingBoolean displayhealthB;
    public static WidgetBoolean displayhealthW;
    public static MoCSettingBoolean displayemoB;
    public static WidgetBoolean displayemoW;
    public static MoCSettingBoolean staticbedB;
    public static WidgetBoolean staticbedW;
    public static MoCSettingBoolean staticlitterB;
    public static WidgetBoolean staticlitterW;
    public static MoCSettingBoolean attackdolphinsB;
    public static WidgetBoolean attackdolphinsW;
    public static MoCSettingBoolean attackhorses;
    public static WidgetBoolean attackhorsesW;
    public static MoCSettingBoolean attackwolvesB;
    public static WidgetBoolean attackwolvesW;
    public static MoCSettingBoolean destroyitemsB;
    public static WidgetBoolean destroyitemsW;
    public static MoCSettingBoolean killallVillagersB;
    public static WidgetBoolean killallVillagersW;
    public static MoCSettingBoolean killallUseLightLevelB;
    public static WidgetBoolean killallUseLightLevelW;
    public static WidgetBoolean spawnpiranhaW;
    public static MoCSettingInt pegasusChanceS;
    public static WidgetInt pegasusChanceW;
    public static MoCSettingInt ostrichEggDropChanceS;
    public static WidgetInt ostrichEggDropChanceW;
    public static MoCSettingInt rareItemDropChanceS;
    public static WidgetInt rareItemDropChanceW;
    public static MoCSettingInt wyvernEggDropChanceS;
    public static WidgetInt wyvernEggDropChanceW;
    public static MoCSettingInt motherWyvernEggDropChanceS;
    public static WidgetInt motherWyvernEggDropChanceW;
    public static MoCSettingBoolean easybreedingB;
    public static WidgetBoolean easybreedingW;
    public static MoCSettingInt ogreAttackRangeS;
    public static MoCSettingFloat ogreStrengthS;
    public static MoCSettingFloat fireOgreStrengthS;
    public static MoCSettingFloat caveOgreStrengthS;
    public static WidgetFloat ogreStrengthW;
    public static WidgetFloat fireOgreStrengthW;
    public static WidgetFloat caveOgreStrengthW;
    public static WidgetInt ogreAttackRangeW;
    public static MoCSettingInt caveOgreChanceS;
    public static MoCSettingInt fireOgreChanceS;
    public static WidgetInt caveOgreChanceW;
    public static WidgetInt fireOgreChanceW;
    public static MoCSettingBoolean golemDestroyBlocksB;
    public static WidgetBoolean golemDestroyBlocksW;
    
    public MoCSettings guiapiSettings;
    public ModSettingScreen MoCScreen;

    public MoCSettingList settingBiomeGroups;
    //public MoCSettingList entityBiomeList;
    public MoCSettingList biomesList;
    public MoCSettingList entityList;

    public static MoCSettingBoolean despawnVanillaB;
    public static WidgetBoolean despawnVanillaW;

    public static MoCSettingBoolean modifyVanillaSpawnsB;
    public static WidgetBoolean modifyVanillaSpawnsW;
    
    public static MoCSettingBoolean debugB;
    public static WidgetBoolean debugW;

    public static MoCSettingInt mocitemidA;
    public static WidgetInt mocitemidW;
    public static MoCSettingInt eggidA;
    public static WidgetInt eggidW;
    public static WidgetInt blockDirtIdW;
    public static MoCSettingInt blockDirtIdS;
    public static WidgetInt blockGrassIdW;
    public static MoCSettingInt blockGrassIdS;
    public static WidgetInt blockStoneIdW;
    public static MoCSettingInt blockStoneIdS;
    public static WidgetInt blockLeafIdW;
    public static MoCSettingInt blockLeafIdS;
    public static WidgetInt blockLogIdW;
    public static MoCSettingInt blockLogIdS;
    public static WidgetInt blockTallGrassIdW;
    public static MoCSettingInt blockTallGrassIdS;
    public static WidgetInt blockPlanksIdW;
    public static MoCSettingInt blockPlanksIdS;
    public static WidgetInt wyvernDimensionIdW;
    public static MoCSettingInt wyvernDimensionIdS;
    public static WidgetInt wyvernBiomeIdW;
    public static MoCSettingInt wyvernBiomeIdS;

    private WidgetSimplewindow instaSpawnerWindow;
    private MoCSettingInt settingNumberToSpawn;
    private MoCSettingMulti settingClassToSpawn;
    public WidgetSimplewindow creatureOptionsWindow;
    public WidgetSimplewindow creatureSettingsWindow;
    public WidgetSimplewindow creatureSpawnSettingsWindow;
    public WidgetSimplewindow waterMobOptionsWindow;
    public WidgetSimplewindow waterMobSettingsWindow;
    public WidgetSimplewindow waterMobSpawnSettingsWindow;
    public WidgetSimplewindow mobOptionsWindow;
    public WidgetSimplewindow mobSettingsWindow;
    public WidgetSimplewindow mobSpawnSettingsWindow;
    public WidgetSimplewindow ambientOptionsWindow;
    public WidgetSimplewindow ambientSpawnSettingsWindow;
    public WidgetSimplewindow generalSettingsWindow;
    public WidgetSimplewindow IDSettingsWindow;
    public WidgetSimplewindow vanillamobwindow;
    public WidgetSimplewindow defaultsWindow;

    private WidgetClassicTwocolumn widgetCreatureSettingsColumns;
    private WidgetClassicTwocolumn widgetCreatureSpawnSettingsColumns;
    private WidgetClassicTwocolumn widgetMobSettingsColumns;
    private WidgetClassicTwocolumn widgetMobSpawnSettingsColumns;
    private WidgetClassicTwocolumn widgetWaterMobSettingsColumns;
    private WidgetClassicTwocolumn widgetWaterMobSpawnSettingsColumns;
    private WidgetClassicTwocolumn widgetAmbientSettingsColumns;
    private WidgetClassicTwocolumn widgetAmbientSpawnSettingsColumns;
    private WidgetClassicTwocolumn widgetGeneralSettingsColumns;
    private WidgetClassicTwocolumn widgetIDSettingsColumns;
    private WidgetClassicTwocolumn defaultChoices;
    private WidgetSinglecolumn widgetInstaSpawnerColumn;
    private WidgetClassicTwocolumn spawnColumns;
    private WidgetClassicTwocolumn creatureOptions;
    private WidgetClassicTwocolumn mobOptions;
    private WidgetClassicTwocolumn waterOptions;
    private WidgetClassicTwocolumn ambientOptions;

    private static final String BUTTON_GENERAL_SETTINGS = "General Settings";
    private static final String BUTTON_ID_SETTINGS = "ID Settings";
    private static final String BUTTON_CREATURES = "Creatures";
    private static final String BUTTON_CREATURE_GENERAL_SETTINGS = "Creature General Settings";
    private static final String BUTTON_CREATURE_SPAWN_SETTINGS = "Creature Spawn Settings";
    private static final String BUTTON_MONSTER_GENERAL_SETTINGS = "Monster General Settings";
    private static final String BUTTON_MONSTER_SPAWN_SETTINGS = "Monster Spawn Settings";
    private static final String BUTTON_WATERMOB_GENERAL_SETTINGS = "Water Mob General Settings";
    private static final String BUTTON_WATERMOB_SPAWN_SETTINGS = "Water Mob Spawn Settings";
    private static final String BUTTON_AMBIENT_SPAWN_SETTINGS = "Ambient Spawn Settings";
    private static final String BUTTON_OWNERSHIP_SETTINGS = "Ownership Settings";
    private static final String BUTTON_DEFAULTS = "Reset to Defaults";
    private static final String MOC_SCREEN_TITLE = "DrZhark's Mo'Creatures" ;

    public static final List<String> entityTypes = Arrays.asList("CREATURE", "MONSTER", "WATERCREATURE", "AMBIENT");

    public MoCEntityData currentSelectedEntity;

    @Override
    public void ConfigInit(FMLPreInitializationEvent event) {
        super.ConfigInit(event);
    }

    public void initGUI()
    {
        MoCLog.logger.info("Initializing MoCreatures GUI API");
        // GUI API settings
        guiapiSettings = new MoCSettings("MoCreatures");
        MoCScreen = new ModSettingScreen("DrZhark's Mo'Creatures");

        //******************** Main Menu ********************//
        ModAction initCreatureWindow = new ModAction(this, "showCreatureOptions", new Class[0]);
        ModAction initMobWindow = new ModAction(this, "showMobOptions", new Class[0]);
        ModAction initWaterMobWindow = new ModAction(this, "showWaterMobOptions", new Class[0]);
        ModAction initAmbientWindow = new ModAction(this, "showAmbientOptions", new Class[0]);
        ModAction initGeneralSettingsWindow = new ModAction(this, "showGeneralSettings", new Class[0]);
        ModAction initIDSettingsWindow = new ModAction(this, "showIDSettings", new Class[0]);
        ModAction initDefaultsWindow = new ModAction(this, "showDefaults", new Class[0]);
        ModAction showInstaSpawner = new ModAction(this, "showInstaSpawner", new Class[0]);
        MoCScreen.append(GuiApiHelper.makeButton("General Settings", initGeneralSettingsWindow, true));
        MoCScreen.append(GuiApiHelper.makeButton("ID Settings", initIDSettingsWindow, true));
        MoCScreen.append(GuiApiHelper.makeButton("Insta-Spawner", showInstaSpawner, true));
        MoCScreen.append(GuiApiHelper.makeButton("Creatures", initCreatureWindow, true));
        MoCScreen.append(GuiApiHelper.makeButton("Mobs", initMobWindow, true));
        MoCScreen.append(GuiApiHelper.makeButton("Water Mobs", initWaterMobWindow, true));
        MoCScreen.append(GuiApiHelper.makeButton("Ambient", initAmbientWindow, true));
        //MoCScreen.append(GuiApiHelper.makeButton("Reset to Defaults", initDefaultsWindow, true));
        //**********************************************************//

        //******************** Creatures ********************//
        creatureOptions = new WidgetClassicTwocolumn(new Widget[0]);
        // create buttons
        if (!MoCreatures.isCustomSpawnerLoaded)
        {
            creatureOptions.add(GuiApiHelper.makeButton("Spawn Settings", new ModAction(this, "showCreatureSpawnSettings", new Class[0]), true));
        }
        creatureOptions.add(GuiApiHelper.makeButton("General Settings", new ModAction(this, "showCreatureSettings", new Class[0]), true));
        //**********************************************************//


        //******************** Mobs ********************//
        mobOptions = new WidgetClassicTwocolumn(new Widget[0]);
        if (!MoCreatures.isCustomSpawnerLoaded)
        {
            mobOptions.add(GuiApiHelper.makeButton("Spawn Settings", new ModAction(this, "showMobSpawnSettings", new Class[0]), true));
        }
        mobOptions.add(GuiApiHelper.makeButton("General Settings", new ModAction(this, "showMobSettings", new Class[0]), true));
        //**********************************************************//


        //******************** Water Mobs ********************//
        waterOptions = new WidgetClassicTwocolumn(new Widget[0]);
        if (!MoCreatures.isCustomSpawnerLoaded)
        {
            waterOptions.add(GuiApiHelper.makeButton("Spawn Settings", new ModAction(this, "showWaterSpawnSettings", new Class[0]), true));
        }
        waterOptions.add(GuiApiHelper.makeButton("General Settings", new ModAction(this, "showWaterSettings", new Class[0]), true));
        //**********************************************************//

        //******************** Ambient ********************//
        ambientOptions = new WidgetClassicTwocolumn(new Widget[0]);
        if (!MoCreatures.isCustomSpawnerLoaded)
        {
            ambientOptions.add(GuiApiHelper.makeButton("Spawn Settings", new ModAction(this, "showAmbientSpawnSettings", new Class[0]), true));
        }
        //**********************************************************//

        //******************** Reset All ********************//
        /*SimpleButtonModel simplebuttonmodel8 = new SimpleButtonModel();
        simplebuttonmodel8.addActionCallback(new ModAction(guiapiSettings, "resetAll", new Class[0]));
        Button button8 = new Button(simplebuttonmodel8);
        button8.setText("Reset to defaults");*/
        //**********************************************************//

        guiapiSettings.load();
    }
    //*********************** GUI API MENU CALLS ************************//

    // Creatures
    public void showCreatureOptions()
    {
        if (creatureOptionsWindow == null)
        {
            creatureOptionsWindow = new WidgetSimplewindow(creatureOptions, "Creature Options");
        }

        GuiModScreen.show(creatureOptionsWindow);
    }

    public void showCreatureSettings()
    {
        if (creatureSettingsWindow == null)
        {
            widgetCreatureSettingsColumns = new WidgetClassicTwocolumn(new Widget[0]);
            guiapiSettings.append(easybreedingB = new MoCSettingBoolean(mocSettingsConfig, CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "EasyBreeding", easyBreeding));
            easybreedingW = new WidgetBoolean(easybreedingB, "Easy Horse Breed", "Yes", "No");
            widgetCreatureSettingsColumns.add(easybreedingW);
            guiapiSettings.append(pegasusChanceS = new MoCSettingInt(mocSettingsConfig, CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "ZebraChance", zebraChance, 1, 1, 10));
            pegasusChanceW = new WidgetInt(pegasusChanceS, "Zebra chance");
            widgetCreatureSettingsColumns.add(pegasusChanceW);
            guiapiSettings.append(ostrichEggDropChanceS = new MoCSettingInt(mocSettingsConfig, CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "OstrichEggDropChance", ostrichEggDropChance, 1, 1, 3));
            ostrichEggDropChanceW = new WidgetInt(ostrichEggDropChanceS, "Ostrich Egg Drop Chance");
            widgetCreatureSettingsColumns.add(ostrichEggDropChanceW);
            guiapiSettings.append(rareItemDropChanceS = new MoCSettingInt(mocSettingsConfig, CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "RareItemDropChance", rareItemDropChance, 1, 1, 25));
            rareItemDropChanceW = new WidgetInt(rareItemDropChanceS, "Rare Item Drop Chance");
            widgetCreatureSettingsColumns.add(rareItemDropChanceW);
            guiapiSettings.append(wyvernEggDropChanceS = new MoCSettingInt(mocSettingsConfig, CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "WyvernEggDropChance", wyvernEggDropChance, 1, 1, 10));
            wyvernEggDropChanceW = new WidgetInt(wyvernEggDropChanceS, "Wyvern Egg Drop Chance");
            widgetCreatureSettingsColumns.add(wyvernEggDropChanceW);
            guiapiSettings.append(motherWyvernEggDropChanceS = new MoCSettingInt(mocSettingsConfig, CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "MotherWyvernEggDropChance", motherWyvernEggDropChance, 1, 1, 33));
            motherWyvernEggDropChanceW = new WidgetInt(motherWyvernEggDropChanceS, "M. Wyvern Egg Drop Chance");
            widgetCreatureSettingsColumns.add(motherWyvernEggDropChanceW);
            guiapiSettings.append(attackhorses = new MoCSettingBoolean(mocSettingsConfig, CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "AttackHorses", attackHorses));
            attackhorsesW = new WidgetBoolean(attackhorses, "Target horses?", "Yes", "No");
            widgetCreatureSettingsColumns.add(attackhorsesW);
            guiapiSettings.append(attackwolvesB = new MoCSettingBoolean(mocSettingsConfig, CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "AttackWolves", attackWolves));
            attackwolvesW = new WidgetBoolean(attackwolvesB, "Target dogs?", "Yes", "No");
            widgetCreatureSettingsColumns.add(attackwolvesW);
            guiapiSettings.append(destroyitemsB = new MoCSettingBoolean(mocSettingsConfig, CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "DestroyDrops", destroyDrops));
            destroyitemsW = new WidgetBoolean(destroyitemsB, "Destroy drops?", "Yes", "No");
            widgetCreatureSettingsColumns.add(destroyitemsW);
            guiapiSettings.append(killallVillagersB = new MoCSettingBoolean(mocSettingsConfig, CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "KillAllVillagers", killallVillagers));
            killallVillagersW = new WidgetBoolean(killallVillagersB, "Killall Villagers?", "Yes", "No");
            widgetCreatureSettingsColumns.add(killallVillagersW);
            creatureSettingsWindow = new WidgetSimplewindow(widgetCreatureSettingsColumns, BUTTON_CREATURE_GENERAL_SETTINGS);
        }
        GuiModScreen.show(creatureSettingsWindow);
    }

    public void showCreatureSpawnSettings()
    {
        if (creatureSpawnSettingsWindow == null)
        {
            widgetCreatureSpawnSettingsColumns = new WidgetClassicTwocolumn(new Widget[0]);
            // create entity button for each creature
            for (Map.Entry<String, MoCEntityData> entityEntry : MoCreatures.mocEntityMap.entrySet())
            {
                if (entityEntry.getValue().getType() == EnumCreatureType.creature)
                {
                    widgetCreatureSpawnSettingsColumns.add(GuiApiHelper.makeButton(entityEntry.getKey(), new ModAction(this, "showEntitySettings", MoCEntityData.class).setDefaultArguments(entityEntry.getValue()), true));
                }
            }
            creatureSpawnSettingsWindow = new WidgetSimplewindow(widgetCreatureSpawnSettingsColumns, BUTTON_CREATURE_SPAWN_SETTINGS);
        }
        GuiModScreen.show(creatureSpawnSettingsWindow);
    }

    // Mobs
    public void showMobOptions()
    {
        if (mobOptionsWindow == null)
        {
            mobOptionsWindow = new WidgetSimplewindow(mobOptions, "Mob Options");
        }

        GuiModScreen.show(mobOptionsWindow);
    }

    public void showMobSpawnSettings()
    {
        if (mobSpawnSettingsWindow == null)
        {
            widgetMobSpawnSettingsColumns = new WidgetClassicTwocolumn(new Widget[0]);
            // create entity button for each mob
            for (Map.Entry<String, MoCEntityData> entityEntry : MoCreatures.mocEntityMap.entrySet())
            {
                if (entityEntry.getValue().getType() == EnumCreatureType.monster)
                {
                    widgetMobSpawnSettingsColumns.add(GuiApiHelper.makeButton(entityEntry.getKey(), new ModAction(this, "showEntitySettings", MoCEntityData.class).setDefaultArguments(entityEntry.getValue()), true));
                }
            }
            mobSpawnSettingsWindow = new WidgetSimplewindow(widgetMobSpawnSettingsColumns, BUTTON_MONSTER_SPAWN_SETTINGS);
        }

        GuiModScreen.show(mobSpawnSettingsWindow);
    }

    public void showMobSettings()
    {
        if (mobSettingsWindow == null)
        {
            widgetMobSettingsColumns = new WidgetClassicTwocolumn(new Widget[0]);
            guiapiSettings.append(ogreStrengthS = new MoCSettingFloat(mocSettingsConfig, CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "OgreStrength", ogreStrength, 0.1F, 0.1F, 5F));
            ogreStrengthW = new WidgetFloat(ogreStrengthS, "Ogre Strength");
            widgetMobSettingsColumns.add(ogreStrengthW);
            guiapiSettings.append(fireOgreStrengthS = new MoCSettingFloat(mocSettingsConfig, CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "FireOgreStrength", fireOgreStrength, 0.1F, 0.1F, 5F));
            fireOgreStrengthW = new WidgetFloat(fireOgreStrengthS, "Fire O. Strength");
            widgetMobSettingsColumns.add(fireOgreStrengthW);
            guiapiSettings.append(caveOgreStrengthS = new MoCSettingFloat(mocSettingsConfig, CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "CaveOgreStrength", caveOgreStrength, 0.1F, 0.1F, 5F));
            caveOgreStrengthW = new WidgetFloat(caveOgreStrengthS, "Cave O. Strength");
            widgetMobSettingsColumns.add(caveOgreStrengthW);
            guiapiSettings.append(ogreAttackRangeS = new MoCSettingInt(mocSettingsConfig, CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "OgreAttackRange", ogreAttackRange, 1, 1, 24));
            ogreAttackRangeW = new WidgetInt(ogreAttackRangeS, "Ogre Attack Range");
            widgetMobSettingsColumns.add(ogreAttackRangeW);
            guiapiSettings.append(caveOgreChanceS = new MoCSettingInt(mocSettingsConfig, CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "caveOgreChance", caveOgreChance, 0, 1, 100));
            caveOgreChanceW = new WidgetInt(caveOgreChanceS, "Cave Ogre Chance");
            widgetMobSettingsColumns.add(caveOgreChanceW);
            guiapiSettings.append(fireOgreChanceS = new MoCSettingInt(mocSettingsConfig, CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "fireOgreChance", fireOgreChance, 0, 1, 100));
            fireOgreChanceW = new WidgetInt(fireOgreChanceS, "Fire Ogre Chance");
            widgetMobSettingsColumns.add(fireOgreChanceW);
            guiapiSettings.append(golemDestroyBlocksB = new MoCSettingBoolean(mocSettingsConfig, CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "golemDestroyBlocks", golemDestroyBlocks));
            golemDestroyBlocksW = new WidgetBoolean(golemDestroyBlocksB, "Golem Destroy Blocks?");
            widgetMobSettingsColumns.add(golemDestroyBlocksW);
            mobSettingsWindow = new WidgetSimplewindow(widgetMobSettingsColumns, BUTTON_MONSTER_GENERAL_SETTINGS);
        }

        GuiModScreen.show(mobSettingsWindow);
    }

    // Water Mobs
    public void showWaterMobOptions()
    {
        if (waterMobOptionsWindow == null)
        {
            waterMobOptionsWindow = new WidgetSimplewindow(waterOptions, "Water Mob Options");
        }

        GuiModScreen.show(waterMobOptionsWindow);
    }

    public void showWaterSpawnSettings()
    {
        if (waterMobSpawnSettingsWindow == null)
        {
            widgetWaterMobSpawnSettingsColumns = new WidgetClassicTwocolumn(new Widget[0]);
            // create entity button for each water creature
            for (Map.Entry<String, MoCEntityData> entityEntry : MoCreatures.mocEntityMap.entrySet())
            {
                if (entityEntry.getValue().getType() == EnumCreatureType.waterCreature)
                {
                    widgetWaterMobSpawnSettingsColumns.add(GuiApiHelper.makeButton(entityEntry.getKey(), new ModAction(this, "showEntitySettings", MoCEntityData.class).setDefaultArguments(entityEntry.getValue()), true));
                }
            }
            waterMobSpawnSettingsWindow = new WidgetSimplewindow(widgetWaterMobSpawnSettingsColumns, BUTTON_WATERMOB_SPAWN_SETTINGS);
        }

        GuiModScreen.show(waterMobSpawnSettingsWindow);
    }

    public void showWaterSettings()
    {
        if (waterMobSettingsWindow == null)
        {
            widgetWaterMobSettingsColumns = new WidgetClassicTwocolumn(new Widget[0]);
            guiapiSettings.append(attackdolphinsB = new MoCSettingBoolean(mocSettingsConfig, CATEGORY_MOC_WATER_CREATURE_GENERAL_SETTINGS, "AttackDolphins", attackDolphins));
            attackdolphinsW = new WidgetBoolean(attackdolphinsB, "Aggresive Dolphins?", "Yes", "No");
            widgetWaterMobSettingsColumns.add(attackdolphinsW);
            waterMobSettingsWindow = new WidgetSimplewindow(widgetWaterMobSettingsColumns, BUTTON_WATERMOB_GENERAL_SETTINGS);
        }

        GuiModScreen.show(waterMobSettingsWindow);
    }

    // Ambient
    public void showAmbientOptions()
    {
        if (ambientOptionsWindow == null)
        {
            ambientOptionsWindow = new WidgetSimplewindow(ambientOptions, "Ambient Options");
        }

        GuiModScreen.show(ambientOptionsWindow);
    }

    public void showAmbientSpawnSettings()
    {
        if (ambientSpawnSettingsWindow == null)
        {
            widgetAmbientSpawnSettingsColumns = new WidgetClassicTwocolumn(new Widget[0]);
            // create entity button for each ambient
            for (Map.Entry<String, MoCEntityData> entityEntry : MoCreatures.mocEntityMap.entrySet())
            {
                if (entityEntry.getValue().getType() == EnumCreatureType.ambient)
                {
                    widgetAmbientSpawnSettingsColumns.add(GuiApiHelper.makeButton(entityEntry.getKey(), new ModAction(this, "showEntitySettings", MoCEntityData.class).setDefaultArguments(entityEntry.getValue()), true));
                }
            }
            ambientSpawnSettingsWindow = new WidgetSimplewindow(widgetAmbientSpawnSettingsColumns, BUTTON_AMBIENT_SPAWN_SETTINGS);
        }
        GuiModScreen.show(ambientSpawnSettingsWindow);
    }

    public void showGeneralSpawnerOptions()
    {
        if (instaSpawnerWindow == null)
        {
            instaSpawnerWindow = new WidgetSimplewindow(widgetInstaSpawnerColumn, "Select the number and creature to spawn");
        }
        GuiModScreen.show(instaSpawnerWindow);
    }

    public void showGeneralSettings()
    {
        if (generalSettingsWindow == null)
        {
            widgetGeneralSettingsColumns = new WidgetClassicTwocolumn(new Widget[0]);
            guiapiSettings.append(debugB = new MoCSettingBoolean(mocSettingsConfig, CATEGORY_MOC_GENERAL_SETTINGS, "debug", debug));
            debugW = new WidgetBoolean(debugB, "Show Debug Logging?", "Yes", "No");
            widgetGeneralSettingsColumns.add(debugW);
            guiapiSettings.append(displaynameB = new MoCSettingBoolean(mocSettingsConfig, CATEGORY_MOC_GENERAL_SETTINGS, "displayPetName", displayPetName));
            displaynameW = new WidgetBoolean(displaynameB, "Show Pet Name?", "Yes", "No");
            widgetGeneralSettingsColumns.add(displaynameW);
            guiapiSettings.append(displayhealthB = new MoCSettingBoolean(mocSettingsConfig, CATEGORY_MOC_GENERAL_SETTINGS, "displayPetHealth", displayPetHealth));
            displayhealthW = new WidgetBoolean(displayhealthB, "Show Pet Health?", "Yes", "No");
            widgetGeneralSettingsColumns.add(displayhealthW);
            guiapiSettings.append(displayemoB = new MoCSettingBoolean(mocSettingsConfig, CATEGORY_MOC_GENERAL_SETTINGS, "displayPetIcons", displayPetIcons));
            displayemoW = new WidgetBoolean(displayemoB, "Show Pet Icons?", "Yes", "No");
            widgetGeneralSettingsColumns.add(displayemoW);
            guiapiSettings.append(staticbedB = new MoCSettingBoolean(mocSettingsConfig, CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "StaticBed", staticBed));
            staticbedW = new WidgetBoolean(staticbedB, "Static K.Bed?", "Yes", "No");
            widgetGeneralSettingsColumns.add(staticbedW);
            guiapiSettings.append(staticlitterB = new MoCSettingBoolean(mocSettingsConfig, CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "StaticLitter", staticLitter));
            staticlitterW = new WidgetBoolean(staticlitterB, "Static Litter?", "Yes", "No");
            widgetGeneralSettingsColumns.add(staticlitterW);
            guiapiSettings.append(particleFXS = new MoCSettingInt(mocSettingsConfig, CATEGORY_MOC_GENERAL_SETTINGS, "particleFX", particleFX, 0, 1, 10));
            particleFXW = new WidgetInt(particleFXS, "FX Particle density");
            widgetGeneralSettingsColumns.add(particleFXW);
            guiapiSettings.append(animateTexturesB = new MoCSettingBoolean(mocSettingsConfig, CATEGORY_MOC_GENERAL_SETTINGS, "AnimateTextures", animateTextures));
            animateTexturesW = new WidgetBoolean(animateTexturesB, "Animate Textures", "Yes", "No");
            widgetGeneralSettingsColumns.add(animateTexturesW);
            generalSettingsWindow = new WidgetSimplewindow(widgetGeneralSettingsColumns, BUTTON_GENERAL_SETTINGS);
        }

        GuiModScreen.show(generalSettingsWindow);
    }

    public void showIDSettings()
    {
        if (IDSettingsWindow == null)
        {
            widgetIDSettingsColumns = new WidgetClassicTwocolumn(new Widget[0]);
            guiapiSettings.append(mocitemidA = new MoCSettingInt(mocSettingsConfig, CATEGORY_MOC_ID_SETTINGS, "ItemID", itemID, 4096, 1, 60000));
            mocitemidW = new WidgetInt(mocitemidA, "Item ID");
            widgetIDSettingsColumns.add(mocitemidW);
            guiapiSettings.append(blockDirtIdS = new MoCSettingInt(mocSettingsConfig, CATEGORY_MOC_ID_SETTINGS, "DirtBlockID", blockDirtID, 1, 1, 255));
            blockDirtIdW = new WidgetInt(blockDirtIdS, "DirtBlock ID");
            widgetIDSettingsColumns.add(blockDirtIdW);
            guiapiSettings.append(blockGrassIdS = new MoCSettingInt(mocSettingsConfig, CATEGORY_MOC_ID_SETTINGS, "GrassBlockID", blockGrassID, 1, 1, 255));
            blockGrassIdW = new WidgetInt(blockGrassIdS, "GrassBlock ID");
            widgetIDSettingsColumns.add(blockGrassIdW);
            guiapiSettings.append(blockStoneIdS = new MoCSettingInt(mocSettingsConfig, CATEGORY_MOC_ID_SETTINGS, "StoneBlockID", blockStoneID, 256, 1, 60000));
            blockStoneIdW = new WidgetInt(blockStoneIdS, "StoneBlock ID");
            widgetIDSettingsColumns.add(blockStoneIdW);
            guiapiSettings.append(blockLeafIdS = new MoCSettingInt(mocSettingsConfig, CATEGORY_MOC_ID_SETTINGS, "LeafBlockID", blockLeafID, 256, 1, 60000));
            blockLeafIdW = new WidgetInt(blockLeafIdS, "LeafBlock ID");
            widgetIDSettingsColumns.add(blockLeafIdW);
            guiapiSettings.append(blockLogIdS = new MoCSettingInt(mocSettingsConfig, CATEGORY_MOC_ID_SETTINGS, "LogBlockID", blockLogID, 256, 1, 60000));
            blockLogIdW = new WidgetInt(blockLogIdS, "LogBlock ID");
            widgetIDSettingsColumns.add(blockLogIdW);
            guiapiSettings.append(blockTallGrassIdS = new MoCSettingInt(mocSettingsConfig, CATEGORY_MOC_ID_SETTINGS, "TallGrassBlockID", blockTallGrassID, 256, 1, 60000));
            blockTallGrassIdW = new WidgetInt(blockTallGrassIdS, "TallG.Block ID");
            widgetIDSettingsColumns.add(blockTallGrassIdW);
            guiapiSettings.append(blockPlanksIdS = new MoCSettingInt(mocSettingsConfig, CATEGORY_MOC_ID_SETTINGS, "PlanksBlockID", blockPlanksID, 256, 1, 60000));
            blockPlanksIdW = new WidgetInt(blockPlanksIdS, "PlanksBlock ID");
            widgetIDSettingsColumns.add(blockPlanksIdW);
            guiapiSettings.append(wyvernBiomeIdS = new MoCSettingInt(mocSettingsConfig, CATEGORY_MOC_ID_SETTINGS, "WyvernLairBiomeID", WyvernBiomeID, 22, 1, 255));
            wyvernBiomeIdW = new WidgetInt(wyvernBiomeIdS, "WyvernBiome ID");
            widgetIDSettingsColumns.add(wyvernBiomeIdW);
            guiapiSettings.append(wyvernDimensionIdS = new MoCSettingInt(mocSettingsConfig, CATEGORY_MOC_ID_SETTINGS, "WyvernLairDimensionID", WyvernDimension, -1000, 1, 60000));
            wyvernDimensionIdW = new WidgetInt(wyvernDimensionIdS, "Wyv. Dimension");
            widgetIDSettingsColumns.add(wyvernDimensionIdW);
            IDSettingsWindow = new WidgetSimplewindow(widgetIDSettingsColumns, BUTTON_ID_SETTINGS);
        }

        GuiModScreen.show(IDSettingsWindow);
    }
    
    public void showDefaults()
    {
        if (defaultsWindow == null)
        {
            defaultChoices = new WidgetClassicTwocolumn(new Widget[0]);
            SimpleButtonModel defaultChoiceButtonModel = new SimpleButtonModel();
            defaultChoiceButtonModel.addActionCallback(new ModAction(this, "resetToDefaults", new Class[0]));
            Button defaultChoiceButton = new Button(defaultChoiceButtonModel);
            defaultChoiceButton.setText("Yes");
            defaultChoices.add(defaultChoiceButton);
            SimpleButtonModel defaultChoiceButtonModel2 = new SimpleButtonModel();
            defaultChoiceButtonModel2.addActionCallback(new ModAction(this, "cancelReset", new Class[0]));
            Button defaultChoiceButton2 = new Button(defaultChoiceButtonModel2);
            defaultChoiceButton2.setText("No");
            defaultChoices.add(defaultChoiceButton2);
            defaultsWindow = new WidgetSimplewindow(defaultChoices, "Are you sure ? All settings will be erased.");
        }
        GuiModScreen.show(defaultsWindow);
    }

    public void showEntitySettings(MoCEntityData entityData)
    {
        if (entityData != null)
        {
            if (entityData.getEntityWindow() == null)
            {
                WidgetSinglecolumn widgetEntitySettingColumn = new WidgetSinglecolumn(new Widget[0]);
                //MoCSettingMulti settingType = new MoCSettingMulti(mocEntityConfig, entityData.getEntityName(), entityData.getEntityName() + " type", entityData.getType() != null ? entityTypes.indexOf(entityData.getType().name().toUpperCase()) : 0, "UNDEFINED", "CREATURE", "MONSTER", "WATERCREATURE", "AMBIENT");
                //guiapiSettings.append(settingType);
                //widgetEntitySettingColumn.add(new WidgetMulti(settingType, "Type"));
                MoCSettingInt settingFrequency = new MoCSettingInt(mocEntityConfig, entityData.getEntityName(), entityData.getEntityName() + " frequency", entityData.getFrequency(), 0, 1, 20);
                guiapiSettings.append(settingFrequency);
                widgetEntitySettingColumn.add(new WidgetInt(settingFrequency, "Frequency"));
                MoCSettingInt settingMinGroup = new MoCSettingInt(mocEntityConfig, entityData.getEntityName(), entityData.getEntityName() + " minspawn", entityData.getMinSpawn(), 1, 1, 20);
                guiapiSettings.append(settingMinGroup);
                widgetEntitySettingColumn.add(new WidgetInt(settingMinGroup, "MinSpawn"));
                MoCSettingInt settingMaxGroup = new MoCSettingInt(mocEntityConfig, entityData.getEntityName(), entityData.getEntityName() + " maxspawn", entityData.getMaxSpawn(), 1, 1, 20);
                guiapiSettings.append(settingMaxGroup);
                widgetEntitySettingColumn.add(new WidgetInt(settingMaxGroup, "MaxSpawn"));
                MoCSettingInt settingChunkGroup = new MoCSettingInt(mocEntityConfig, entityData.getEntityName(), entityData.getEntityName() + " chunkspawn", entityData.getMaxInChunk(), 1, 1, 20);
                guiapiSettings.append(settingChunkGroup);
                widgetEntitySettingColumn.add(new WidgetInt(settingChunkGroup, "MaxChunk"));
                MoCSettingBoolean settingCanSpawn = new MoCSettingBoolean(mocEntityConfig, entityData.getEntityName(), entityData.getEntityName() + " canspawn", entityData.getCanSpawn());
                guiapiSettings.append(settingCanSpawn);
                widgetEntitySettingColumn.add(new WidgetBoolean(settingCanSpawn, "CanSpawn"));
                entityData.setEntityWindow(new WidgetSimplewindow(widgetEntitySettingColumn, entityData.getEntityName()));
            }
            this.currentSelectedEntity = entityData;
            GuiModScreen.show(entityData.getEntityWindow());
        }
    }

    public void showInstaSpawner()
    {
        widgetInstaSpawnerColumn = new WidgetSinglecolumn(new Widget[0]);
        
        ArrayList<String> moCreaturesList = new ArrayList<String>();
        for (Map.Entry<String, MoCEntityData> entityEntry : MoCreatures.mocEntityMap.entrySet())
        {
            moCreaturesList.add(entityEntry.getValue().getEntityName());
        }
        Collections.sort(moCreaturesList);
        entityList = guiapiSettings.addSetting(widgetInstaSpawnerColumn, "Creature Type:", "SpawnEntityList", moCreaturesList, mocSettingsConfig, "");
        ((WidgetList) entityList.displayWidget).listBox.setTheme("/listbox");
        widgetInstaSpawnerColumn.heightOverrideExceptions.put(entityList.displayWidget, 130);
        settingNumberToSpawn = guiapiSettings.addSetting(widgetInstaSpawnerColumn, "Number to Spawn", "spawnN", 3, 1, 10);
        widgetInstaSpawnerColumn.add(GuiApiHelper.makeButton("Perform Spawn", new ModAction(this, "instaSpawn", MoCSettingList.class, ArrayList.class).setDefaultArguments(entityList, moCreaturesList), true));
        instaSpawnerWindow = new WidgetSimplewindow(widgetInstaSpawnerColumn, "Select the Creature to Spawn");
        GuiModScreen.show(instaSpawnerWindow);
    }
    
    
    @SuppressWarnings("unused")
    public void instaSpawn(MoCSettingList setting, ArrayList<String> aList)
    {
        ListBox<String> listbox = ((WidgetList) setting.displayWidget).listBox;
        int selected = listbox.getSelected();
        int numberToSpawn = settingNumberToSpawn.get();//guiapiSettings.getIntSettingValue("spawnN");
        String entityName = aList.get(selected);
        for (Map.Entry<String, MoCEntityData> entityEntry : MoCreatures.mocEntityMap.entrySet())
        {
            if (entityEntry.getValue().getEntityName().equalsIgnoreCase(entityName))
            {
                try {
                    MoCMessageHandler.INSTANCE.sendToServer(new MoCMessageInstaSpawn(entityEntry.getValue().getEntityID(), numberToSpawn));
                }
                catch (Exception ex)
                {
                    
                }
            }
        }
    }

    public void resetAllData()
    {
        instaSpawnerWindow = null;
        settingNumberToSpawn = null;
        creatureOptionsWindow = null;
        creatureSettingsWindow = null;
        creatureSpawnSettingsWindow = null;
        waterMobOptionsWindow = null;
        waterMobSettingsWindow = null;
        waterMobSpawnSettingsWindow = null;
        mobOptionsWindow = null;
        mobSettingsWindow = null;
        mobSpawnSettingsWindow = null;
        ambientOptionsWindow = null;
        ambientSpawnSettingsWindow = null;
        generalSettingsWindow = null;
        IDSettingsWindow = null;
        vanillamobwindow = null;
        defaultsWindow = null;

        for (int i = 0; i < MoCScreen.modScreens.size(); i++)
        {
            if (MoCScreen.modScreens.get(i).niceName.equalsIgnoreCase(MOC_SCREEN_TITLE))
            {
                MoCScreen.modScreens.remove(i);
            }
        }

        MoCScreen = null;
        guiapiSettings = null;
        super.resetAllData();
    }

    public void resetToDefaults()
    {
        if (mocSettingsConfig.getFile().exists())
        {
            String parentDir = configFile.getParent();
            if (!mocSettingsConfig.getFile().renameTo(new File(parentDir, "MoCreatures" + File.separator + "MoCGlobal.cfg.bak")))
            {
                File oldFile = new File(parentDir, "MoCreatures" + File.separator + "MoCGlobal.cfg.bak");
                oldFile.delete();
                mocSettingsConfig.getFile().renameTo(new File(parentDir, "MoCreatures" + File.separator + "MoCGlobal.cfg.bak"));
            }
            mocSettingsConfig = new MoCConfiguration(new File(parentDir, "MoCreatures" + File.separator + "MoCGlobal.cfg"));
            File mocreaturesFile = new File(parentDir, "MoCreatures.cfg");
            if (mocreaturesFile.exists())
            {
                if (!mocreaturesFile.renameTo(new File(parentDir, "MoCreatures.cfg.bak")))
                {
                    File oldFile = new File(parentDir, "MoCreatures.cfg.bak");
                    oldFile.delete();
                    mocreaturesFile.renameTo(new File(parentDir, "MoCreatures.cfg.bak"));
                }
            }
        }
        resetAllData();
        MoCreatures.proxy.initGUI();
        GuiModScreen.show(MoCScreen.theWidget);
    }

    public void cancelReset()
    {
        GuiModScreen.show(MoCClientProxy.instance.MoCScreen.theWidget);
    }
    @Override
    public int getParticleFX()
    {
        return particleFX;
    }

    public boolean getDisplayPetName()
    {
        return displayPetName;
    }

    public boolean getDisplayPetIcons()
    {
        return displayPetIcons;
    }

    public boolean getDisplayPetHealth()
    {
        return displayPetHealth;
    }

    @Override
    public boolean getAnimateTextures()
    {
        return animateTextures;
    }

    @Override
    public void printMessageToPlayer(String msg)
    {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentTranslation(msg));
    }

    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Entry<T, E> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}