package drzhark.mocreatures.client;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.ListBox;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.model.SimpleButtonModel;
import drzhark.guiapi.GuiAPI;
import drzhark.guiapi.GuiApiHelper;
import drzhark.guiapi.GuiModScreen;
import drzhark.guiapi.ModAction;
import drzhark.guiapi.ModSettingScreen;
import drzhark.guiapi.widget.WidgetBoolean;
import drzhark.guiapi.widget.WidgetClassicTwocolumn;
import drzhark.guiapi.widget.WidgetFloat;
import drzhark.guiapi.widget.WidgetInt;
import drzhark.guiapi.widget.WidgetList;
import drzhark.guiapi.widget.WidgetSimplewindow;
import drzhark.guiapi.widget.WidgetSinglecolumn;
import drzhark.mocreatures.MoCEntityData;
import drzhark.mocreatures.MoCProxy;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.gui.helpers.MoCGUIEntityNamer;
import drzhark.mocreatures.client.gui.helpers.MoCSettingBoolean;
import drzhark.mocreatures.client.gui.helpers.MoCSettingFloat;
import drzhark.mocreatures.client.gui.helpers.MoCSettingInt;
import drzhark.mocreatures.client.gui.helpers.MoCSettingList;
import drzhark.mocreatures.client.gui.helpers.MoCSettings;
import drzhark.mocreatures.client.model.MoCModelAnt;
import drzhark.mocreatures.client.model.MoCModelBear;
import drzhark.mocreatures.client.model.MoCModelBee;
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
import drzhark.mocreatures.client.model.MoCModelManticore;
import drzhark.mocreatures.client.model.MoCModelMediumFish;
import drzhark.mocreatures.client.model.MoCModelMiniGolem;
import drzhark.mocreatures.client.model.MoCModelMole;
import drzhark.mocreatures.client.model.MoCModelMouse;
import drzhark.mocreatures.client.model.MoCModelNewBigCat;
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
import drzhark.mocreatures.client.renderer.entity.MoCRenderBird;
import drzhark.mocreatures.client.renderer.entity.MoCRenderBunny;
import drzhark.mocreatures.client.renderer.entity.MoCRenderButterfly;
import drzhark.mocreatures.client.renderer.entity.MoCRenderCricket;
import drzhark.mocreatures.client.renderer.entity.MoCRenderCrocodile;
import drzhark.mocreatures.client.renderer.entity.MoCRenderDolphin;
import drzhark.mocreatures.client.renderer.entity.MoCRenderEgg;
import drzhark.mocreatures.client.renderer.entity.MoCRenderFirefly;
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
import drzhark.mocreatures.entity.aquatic.MoCEntityAnchovy;
import drzhark.mocreatures.entity.aquatic.MoCEntityAngelFish;
import drzhark.mocreatures.entity.aquatic.MoCEntityAngler;
import drzhark.mocreatures.entity.aquatic.MoCEntityBass;
import drzhark.mocreatures.entity.aquatic.MoCEntityClownFish;
import drzhark.mocreatures.entity.aquatic.MoCEntityCod;
import drzhark.mocreatures.entity.aquatic.MoCEntityDolphin;
import drzhark.mocreatures.entity.aquatic.MoCEntityFishy;
import drzhark.mocreatures.entity.aquatic.MoCEntityGoldFish;
import drzhark.mocreatures.entity.aquatic.MoCEntityHippoTang;
import drzhark.mocreatures.entity.aquatic.MoCEntityJellyFish;
import drzhark.mocreatures.entity.aquatic.MoCEntityManderin;
import drzhark.mocreatures.entity.aquatic.MoCEntityMantaRay;
import drzhark.mocreatures.entity.aquatic.MoCEntityPiranha;
import drzhark.mocreatures.entity.aquatic.MoCEntitySalmon;
import drzhark.mocreatures.entity.aquatic.MoCEntityShark;
import drzhark.mocreatures.entity.aquatic.MoCEntityStingRay;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import drzhark.mocreatures.entity.item.MoCEntityKittyBed;
import drzhark.mocreatures.entity.item.MoCEntityLitterBox;
import drzhark.mocreatures.entity.item.MoCEntityThrowableRock;
import drzhark.mocreatures.entity.monster.MoCEntityCaveOgre;
import drzhark.mocreatures.entity.monster.MoCEntityFireOgre;
import drzhark.mocreatures.entity.monster.MoCEntityFlameWraith;
import drzhark.mocreatures.entity.monster.MoCEntityGolem;
import drzhark.mocreatures.entity.monster.MoCEntityGreenOgre;
import drzhark.mocreatures.entity.monster.MoCEntityHellRat;
import drzhark.mocreatures.entity.monster.MoCEntityHorseMob;
import drzhark.mocreatures.entity.monster.MoCEntityManticore;
import drzhark.mocreatures.entity.monster.MoCEntityMiniGolem;
import drzhark.mocreatures.entity.monster.MoCEntityRat;
import drzhark.mocreatures.entity.monster.MoCEntityScorpion;
import drzhark.mocreatures.entity.monster.MoCEntitySilverSkeleton;
import drzhark.mocreatures.entity.monster.MoCEntityWWolf;
import drzhark.mocreatures.entity.monster.MoCEntityWerewolf;
import drzhark.mocreatures.entity.monster.MoCEntityWraith;
import drzhark.mocreatures.entity.passive.MoCEntityBird;
import drzhark.mocreatures.entity.passive.MoCEntityBlackBear;
import drzhark.mocreatures.entity.passive.MoCEntityBoar;
import drzhark.mocreatures.entity.passive.MoCEntityBunny;
import drzhark.mocreatures.entity.passive.MoCEntityCrocodile;
import drzhark.mocreatures.entity.passive.MoCEntityDeer;
import drzhark.mocreatures.entity.passive.MoCEntityDuck;
import drzhark.mocreatures.entity.passive.MoCEntityElephant;
import drzhark.mocreatures.entity.passive.MoCEntityEnt;
import drzhark.mocreatures.entity.passive.MoCEntityFox;
import drzhark.mocreatures.entity.passive.MoCEntityGoat;
import drzhark.mocreatures.entity.passive.MoCEntityGrizzlyBear;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.entity.passive.MoCEntityKitty;
import drzhark.mocreatures.entity.passive.MoCEntityKomodo;
import drzhark.mocreatures.entity.passive.MoCEntityLeoger;
import drzhark.mocreatures.entity.passive.MoCEntityLeopard;
import drzhark.mocreatures.entity.passive.MoCEntityLiard;
import drzhark.mocreatures.entity.passive.MoCEntityLiger;
import drzhark.mocreatures.entity.passive.MoCEntityLion;
import drzhark.mocreatures.entity.passive.MoCEntityLither;
import drzhark.mocreatures.entity.passive.MoCEntityManticorePet;
import drzhark.mocreatures.entity.passive.MoCEntityMole;
import drzhark.mocreatures.entity.passive.MoCEntityMouse;
import drzhark.mocreatures.entity.passive.MoCEntityOstrich;
import drzhark.mocreatures.entity.passive.MoCEntityPandaBear;
import drzhark.mocreatures.entity.passive.MoCEntityPanthard;
import drzhark.mocreatures.entity.passive.MoCEntityPanther;
import drzhark.mocreatures.entity.passive.MoCEntityPanthger;
import drzhark.mocreatures.entity.passive.MoCEntityPetScorpion;
import drzhark.mocreatures.entity.passive.MoCEntityPolarBear;
import drzhark.mocreatures.entity.passive.MoCEntityRaccoon;
import drzhark.mocreatures.entity.passive.MoCEntitySnake;
import drzhark.mocreatures.entity.passive.MoCEntityTiger;
import drzhark.mocreatures.entity.passive.MoCEntityTurkey;
import drzhark.mocreatures.entity.passive.MoCEntityTurtle;
import drzhark.mocreatures.entity.passive.MoCEntityWyvern;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageInstaSpawn;
import drzhark.mocreatures.util.MoCLog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MoCClientProxy extends MoCProxy {

    public static Minecraft mc = Minecraft.getMinecraft();
    public static MoCClientProxy instance;
    public static MoCTextures mocTextures = new MoCTextures();

    public MoCClientProxy() {
        instance = this;
    }

    @Override
    public void registerRenderers() {
    }

    @Override
    public void initTextures() {
        mocTextures.loadTextures();
    }

    @Override
    public ResourceLocation getTexture(String texture) {
        return mocTextures.getTexture(texture);
    }

    @SuppressWarnings({"deprecation", "unchecked", "rawtypes"})
    @Override
    public void registerRenderInformation() {
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
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityBoar.class, new MoCRenderMoC(new MoCModelBoar(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityBlackBear.class, new MoCRenderMoC(new MoCModelBear(), 0.7F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityGrizzlyBear.class, new MoCRenderMoC(new MoCModelBear(), 0.7F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityPandaBear.class, new MoCRenderMoC(new MoCModelBear(), 0.7F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityPolarBear.class, new MoCRenderMoC(new MoCModelBear(), 0.7F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityDuck.class, new MoCRenderMoC(new MoCModelDuck(), 0.3F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityDeer.class, new MoCRenderMoC(new MoCModelDeer(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityWWolf.class, new MoCRenderWWolf(new MoCModelWolf(), 0.7F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityWraith.class, new MoCRenderWraith(new MoCModelWraith(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityFlameWraith.class, new MoCRenderWraith(new MoCModelWraith(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityWerewolf.class, new MoCRenderWerewolf(new MoCModelWereHuman(), new MoCModelWere(),
                0.7F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityFox.class, new MoCRenderMoC(new MoCModelFox(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityShark.class, new MoCRenderShark(new MoCModelShark(), 0.6F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityDolphin.class, new MoCRenderDolphin(new MoCModelDolphin(), 0.6F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityFishy.class, new MoCRenderMoC(new MoCModelFishy(), 0.1F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityEgg.class, new MoCRenderEgg(new MoCModelEgg(), 0.0F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityKitty.class, new MoCRenderKitty(new MoCModelKitty(0.0F, 15F), 0.4F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityKittyBed.class, new MoCRenderKittyBed(new MoCModelKittyBed(),
                new MoCModelKittyBed2(), 0.3F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityLitterBox.class, new MoCRenderLitterBox(new MoCModelLitterBox(), 0.3F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityRat.class, new MoCRenderRat(new MoCModelRat(), 0.2F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityHellRat.class, new MoCRenderHellRat(new MoCModelRat(), 0.4F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityScorpion.class, new MoCRenderScorpion(new MoCModelScorpion(), 0.4F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityCrocodile.class, new MoCRenderCrocodile(new MoCModelCrocodile(), 0.5F));
        //RenderingRegistry.registerEntityRenderingHandler(MoCEntityRay.class, new MoCRenderMoC(new MoCModelRay(), 0.4F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityMantaRay.class, new MoCRenderMoC(new MoCModelRay(), 0.4F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityStingRay.class, new MoCRenderMoC(new MoCModelRay(), 0.4F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityJellyFish.class, new MoCRenderMoC(new MoCModelJellyFish(), 0.1F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityGoat.class, new MoCRenderGoat(new MoCModelGoat(), 0.3F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityOstrich.class, new MoCRenderOstrich(new MoCModelOstrich(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityBee.class, new MoCRenderInsect(new MoCModelBee()));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityFly.class, new MoCRenderInsect(new MoCModelFly()));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityDragonfly.class, new MoCRenderInsect(new MoCModelDragonfly()));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityFirefly.class, new MoCRenderFirefly(new MoCModelFirefly()));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityCricket.class, new MoCRenderCricket(new MoCModelCricket()));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntitySnail.class, new MoCRenderMoC(new MoCModelSnail(), 0.0F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityGolem.class, new MoCRenderGolem(new MoCModelGolem(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityThrowableRock.class, new MoCRenderTRock());
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityPetScorpion.class, new MoCRenderPetScorpion(new MoCModelPetScorpion(), 0.4F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityElephant.class, new MoCRenderMoC(new MoCModelElephant(), 0.7F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityKomodo.class, new MoCRenderMoC(new MoCModelKomodo(), 0.3F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityWyvern.class, new MoCRenderMoC(new MoCModelWyvern(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityGreenOgre.class, new MoCRenderMoC(new MoCModelOgre(), 0.6F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityCaveOgre.class, new MoCRenderMoC(new MoCModelOgre(), 0.6F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityFireOgre.class, new MoCRenderMoC(new MoCModelOgre(), 0.6F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityRoach.class, new MoCRenderInsect(new MoCModelRoach()));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityMaggot.class, new MoCRenderMoC(new MoCModelMaggot(), 0F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityCrab.class, new MoCRenderMoC(new MoCModelCrab(), 0.2F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityRaccoon.class, new MoCRenderMoC(new MoCModelRaccoon(), 0.4F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityMiniGolem.class, new MoCRenderMoC(new MoCModelMiniGolem(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntitySilverSkeleton.class, new MoCRenderMoC(new MoCModelSilverSkeleton(), 0.6F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityAnt.class, new MoCRenderInsect(new MoCModelAnt()));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityCod.class, new MoCRenderMoC(new MoCModelMediumFish(), 0.2F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntitySalmon.class, new MoCRenderMoC(new MoCModelMediumFish(), 0.2F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityBass.class, new MoCRenderMoC(new MoCModelMediumFish(), 0.2F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityAnchovy.class, new MoCRenderMoC(new MoCModelSmallFish(), 0.1F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityAngelFish.class, new MoCRenderMoC(new MoCModelSmallFish(), 0.1F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityAngler.class, new MoCRenderMoC(new MoCModelSmallFish(), 0.1F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityClownFish.class, new MoCRenderMoC(new MoCModelSmallFish(), 0.1F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityGoldFish.class, new MoCRenderMoC(new MoCModelSmallFish(), 0.1F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityHippoTang.class, new MoCRenderMoC(new MoCModelSmallFish(), 0.1F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityManderin.class, new MoCRenderMoC(new MoCModelSmallFish(), 0.1F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityPiranha.class, new MoCRenderMoC(new MoCModelSmallFish(), 0.1F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityEnt.class, new MoCRenderMoC(new MoCModelEnt(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityMole.class, new MoCRenderMoC(new MoCModelMole(), 0F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityManticore.class, new MoCRenderMoC(new MoCModelManticore(), 0.7F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityLion.class, new MoCRenderMoC(new MoCModelNewBigCat(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityTiger.class, new MoCRenderMoC(new MoCModelNewBigCat(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityPanther.class, new MoCRenderMoC(new MoCModelNewBigCat(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityLeopard.class, new MoCRenderMoC(new MoCModelNewBigCat(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityManticorePet.class, new MoCRenderMoC(new MoCModelNewBigCat(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityLiger.class, new MoCRenderMoC(new MoCModelNewBigCat(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityLeoger.class, new MoCRenderMoC(new MoCModelNewBigCat(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityPanthger.class, new MoCRenderMoC(new MoCModelNewBigCat(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityPanthard.class, new MoCRenderMoC(new MoCModelNewBigCat(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityLither.class, new MoCRenderMoC(new MoCModelNewBigCat(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntityLiard.class, new MoCRenderMoC(new MoCModelNewBigCat(), 0.5F));
    }

    @Override
    public EntityPlayer getPlayer() {
        return MoCClientProxy.mc.player;
    }

    /**
     * Sets the name client side. Name is synchronized with datawatchers
     *
     * @param player
     * @param mocanimal
     */
    @Override
    public void setName(EntityPlayer player, IMoCEntity mocanimal) {
        mc.displayGuiScreen(new MoCGUIEntityNamer(mocanimal, mocanimal.getPetName()));

    }

    @Override
    public void UndeadFX(Entity entity) {
        //if (!((Boolean) MoCreatures.particleFX.get()).booleanValue()) return;
        int densityInt = (MoCreatures.proxy.getParticleFX());
        if (densityInt == 0) {
            return;
        }

        int i = (densityInt / 2) * (entity.world.rand.nextInt(2) + 1);
        if (i == 0) {
            i = 1;
        }
        if (i > 10) {
            i = 10;
        }
        for (int x = 0; x < i; x++) {
            MoCEntityFXUndead FXUndead =
                    new MoCEntityFXUndead(entity.world, entity.posX, entity.posY + entity.world.rand.nextFloat() * entity.height, entity.posZ);
            mc.effectRenderer.addEffect(FXUndead);

        }
    }

    @Override
    public void StarFX(MoCEntityHorse entity) {
        int densityInt = MoCreatures.proxy.getParticleFX();
        if (densityInt == 0) {
            return;
        }

        if ((entity.getType() >= 50 && entity.getType() < 60) || entity.getType() == 36) {

            float fRed = entity.colorFX(1, entity.getType());
            float fGreen = entity.colorFX(2, entity.getType());
            float fBlue = entity.colorFX(3, entity.getType());

            int i = densityInt * entity.world.rand.nextInt(2);// + 2;
            for (int x = 0; x < i; x++) {
                MoCEntityFXStar FXStar =
                        new MoCEntityFXStar(mc.world, entity.posX, entity.posY + entity.world.rand.nextFloat() * entity.height, entity.posZ,
                                fRed, fGreen, fBlue);
                mc.effectRenderer.addEffect(FXStar);

            }

        }
    }

    @Override
    public void LavaFX(Entity entity) {
        int densityInt = (MoCreatures.proxy.getParticleFX());
        if (densityInt == 0) {
            return;
        }
        double var2 = entity.world.rand.nextGaussian() * 0.02D;
        double var4 = entity.world.rand.nextGaussian() * 0.02D;
        double var6 = entity.world.rand.nextGaussian() * 0.02D;
        mc.world.spawnParticle(EnumParticleTypes.LAVA, entity.posX + entity.world.rand.nextFloat() * entity.width - entity.width, entity.posY
                + 0.5D + entity.world.rand.nextFloat() * entity.height, entity.posZ + entity.world.rand.nextFloat() * entity.width
                - entity.width, var2, var4, var6);

    }

    @Override
    public void VanishFX(MoCEntityHorse entity) {
        int densityInt = (MoCreatures.proxy.getParticleFX());
        if (densityInt == 0) {
            return;
        }

        for (int var6 = 0; var6 < densityInt * 8; ++var6) {
            double newPosX = ((float) entity.posX + entity.world.rand.nextFloat());
            double newPosY = 0.7D + ((float) entity.posY + entity.world.rand.nextFloat());
            double newPosZ = ((float) entity.posZ + entity.world.rand.nextFloat());
            int var19 = entity.world.rand.nextInt(2) * 2 - 1;
            double speedY = (entity.world.rand.nextFloat() - 0.5D) * 0.5D;
            double speedX = entity.world.rand.nextFloat() * 2.0F * var19;
            double speedZ = entity.world.rand.nextFloat() * 2.0F * var19;

            MoCEntityFXVanish FXVanish =
                    new MoCEntityFXVanish(entity.world, newPosX, newPosY, newPosZ, speedX, speedY, speedZ, entity.colorFX(1, entity.getType()),
                            entity.colorFX(2, entity.getType()), entity.colorFX(3, entity.getType()), false);
            mc.effectRenderer.addEffect(FXVanish);
        }
    }

    @Override
    public void MaterializeFX(MoCEntityHorse entity) {
        int densityInt = (MoCreatures.proxy.getParticleFX());
        if (densityInt == 0) {
            return;
        }

        for (int var6 = 0; var6 < (densityInt * 50); ++var6) {
            double newPosX = ((float) entity.posX + entity.world.rand.nextFloat());
            double newPosY = 0.7D + ((float) entity.posY + entity.world.rand.nextFloat());
            double newPosZ = ((float) entity.posZ + entity.world.rand.nextFloat());
            int var19 = entity.world.rand.nextInt(2) * 2 - 1;
            double speedY = (entity.world.rand.nextFloat() - 0.5D) * 0.5D;
            double speedX = entity.world.rand.nextFloat() * 2.0F * var19;
            double speedZ = entity.world.rand.nextFloat() * 2.0F * var19;

            MoCEntityFXVanish FXVanish =
                    new MoCEntityFXVanish(mc.world, newPosX, newPosY, newPosZ, speedX, speedY, speedZ, entity.colorFX(1, entity.getType()),
                            entity.colorFX(2, entity.getType()), entity.colorFX(3, entity.getType()), true);
            mc.effectRenderer.addEffect(FXVanish);
        }

    }

    @Override
    public void VacuumFX(MoCEntityGolem entity) {
        int densityInt = (MoCreatures.proxy.getParticleFX());
        if (densityInt == 0) {
            return;
        }

        for (int var1 = 0; var1 < 2; ++var1) {
            double newPosX = entity.posX - (1.5 * Math.cos((MoCTools.realAngle(entity.rotationYaw - 90F)) / 57.29578F));
            double newPosZ = entity.posZ - (1.5 * Math.sin((MoCTools.realAngle(entity.rotationYaw - 90F)) / 57.29578F));
            double newPosY = entity.posY + (entity.height - 0.8D - entity.getAdjustedYOffset() * 1.8);// + (entity.world.rand.nextDouble() * ((double) entity.height - (double) entity.getAdjustedYOffset() * 2));
            //adjustedYOffset from 0 (tallest) to 1.45 (on the ground)
            //height = 4F

            double speedX = (entity.world.rand.nextDouble() - 0.5D) * 4.0D;
            double speedY = -entity.world.rand.nextDouble();
            double speedZ = (entity.world.rand.nextDouble() - 0.5D) * 4.0D;
            MoCEntityFXVacuum FXVacuum =
                    new MoCEntityFXVacuum(mc.world, newPosX, newPosY, newPosZ, speedX, speedY, speedZ, entity.colorFX(1), entity.colorFX(2),
                            entity.colorFX(3), 146);
            mc.effectRenderer.addEffect(FXVacuum);
        }
    }

    @SuppressWarnings("unused")
    @Override
    public void hammerFX(EntityPlayer entity) {
        int densityInt = (MoCreatures.proxy.getParticleFX());
        if (densityInt == 0) {
            return;
        }

        for (int var6 = 0; var6 < (densityInt * 10); ++var6) {
            double newPosX = ((float) entity.posX + entity.world.rand.nextFloat());
            double newPosY = 0.3D + ((float) entity.posY + entity.world.rand.nextFloat());
            double newPosZ = ((float) entity.posZ + entity.world.rand.nextFloat());
            int var19 = entity.world.rand.nextInt(2) * 2 - 1;
            double speedY = (entity.world.rand.nextFloat() - 0.5D) * 0.5D;
            double speedX = entity.world.rand.nextFloat() * 2.0F * var19;
            double speedZ = entity.world.rand.nextFloat() * 2.0F * var19;

            // TODO - fix particle fx
            /*EntitySpellParticleFX hammerFX = new EntitySpellParticleFX(mc.world, newPosX, newPosY, newPosZ, speedX, speedY, speedZ);
            hammerFX.setBaseSpellTextureIndex(144);
            ((EntityFX) hammerFX).setRBGColorF(74F / 256F, 145F / 256F, 71F / 256F);
            mc.effectRenderer.addEffect(hammerFX);*/
        }

    }

    @Override
    public void teleportFX(EntityPlayer entity) {
        int densityInt = (MoCreatures.proxy.getParticleFX());
        if (densityInt == 0) {
            return;
        }

        for (int var6 = 0; var6 < (densityInt * 50); ++var6) {
            double newPosX = ((float) entity.posX + entity.world.rand.nextFloat());
            double newPosY = 0.7D + ((float) entity.posY + entity.world.rand.nextFloat());
            double newPosZ = ((float) entity.posZ + entity.world.rand.nextFloat());
            int var19 = entity.world.rand.nextInt(2) * 2 - 1;
            double speedY = (entity.world.rand.nextFloat() - 0.5D) * 0.5D;
            double speedX = entity.world.rand.nextFloat() * 2.0F * var19;
            double speedZ = entity.world.rand.nextFloat() * 2.0F * var19;

            MoCEntityFXVanish hammerFX =
                    new MoCEntityFXVanish(mc.world, newPosX, newPosY, newPosZ, speedX, speedY, speedZ, 189F / 256F, 110F / 256F, 229F / 256F, true);
            mc.effectRenderer.addEffect(hammerFX);
        }

    }

    @Override
    public int getProxyMode() {
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
    @SuppressWarnings("unused")
    private WidgetClassicTwocolumn widgetAmbientSettingsColumns;
    private WidgetClassicTwocolumn widgetAmbientSpawnSettingsColumns;
    private WidgetClassicTwocolumn widgetGeneralSettingsColumns;
    private WidgetClassicTwocolumn widgetIDSettingsColumns;
    private WidgetClassicTwocolumn defaultChoices;
    private WidgetSinglecolumn widgetInstaSpawnerColumn;
    private WidgetClassicTwocolumn creatureOptions;
    private WidgetClassicTwocolumn mobOptions;
    private WidgetClassicTwocolumn waterOptions;
    private WidgetClassicTwocolumn ambientOptions;

    private static final String BUTTON_GENERAL_SETTINGS = "General Settings";
    private static final String BUTTON_ID_SETTINGS = "ID Settings";
    private static final String BUTTON_CREATURE_GENERAL_SETTINGS = "Creature General Settings";
    private static final String BUTTON_CREATURE_SPAWN_SETTINGS = "Creature Spawn Settings";
    private static final String BUTTON_MONSTER_GENERAL_SETTINGS = "Monster General Settings";
    private static final String BUTTON_MONSTER_SPAWN_SETTINGS = "Monster Spawn Settings";
    private static final String BUTTON_WATERMOB_GENERAL_SETTINGS = "Water Mob General Settings";
    private static final String BUTTON_WATERMOB_SPAWN_SETTINGS = "Water Mob Spawn Settings";
    private static final String BUTTON_AMBIENT_SPAWN_SETTINGS = "Ambient Spawn Settings";
    @SuppressWarnings("unused")
    private static final String BUTTON_OWNERSHIP_SETTINGS = "Ownership Settings";
    @SuppressWarnings("unused")
    private static final String BUTTON_DEFAULTS = "Reset to Defaults";
    private static final String MOC_SCREEN_TITLE = "DrZhark's Mo'Creatures";

    public static final List<String> entityTypes = Arrays.asList("CREATURE", "MONSTER", "WATERCREATURE", "AMBIENT");

    public MoCEntityData currentSelectedEntity;

    public GuiAPI gui = new GuiAPI();

    @Override
    public void ConfigInit(FMLPreInitializationEvent event) {
        super.ConfigInit(event);
        try {
            Field[] fields = GuiScreen.class.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                if (fields[i].getType() == List.class) {
                    this.gui.controlListField = fields[i];
                    this.gui.controlListField.setAccessible(true);
                    break;
                }
            }
            if (this.gui.controlListField == null) {
                throw new Exception("No fields found on GuiScreen (" + GuiScreen.class.getSimpleName() + ") of type List! This should never happen!");
            }
        } catch (Throwable e) {
            throw new RuntimeException("Unable to get Field reference for GuiScreen.controlList!", e);
        }
        MinecraftForge.EVENT_BUS.register(this.gui);
    }

    @SuppressWarnings("unused")
    @Override
    public void initGUI() {
        MoCLog.logger.info("Initializing MoCreatures GUI API");
        // GUI API settings
        this.guiapiSettings = new MoCSettings("MoCreatures");
        this.MoCScreen = new ModSettingScreen("DrZhark's Mo'Creatures");

        //******************** Main Menu ********************//
        ModAction initCreatureWindow = new ModAction(this, "showCreatureOptions", new Class[0]);
        ModAction initMobWindow = new ModAction(this, "showMobOptions", new Class[0]);
        ModAction initWaterMobWindow = new ModAction(this, "showWaterMobOptions", new Class[0]);
        ModAction initAmbientWindow = new ModAction(this, "showAmbientOptions", new Class[0]);
        ModAction initGeneralSettingsWindow = new ModAction(this, "showGeneralSettings", new Class[0]);
        ModAction initIDSettingsWindow = new ModAction(this, "showIDSettings", new Class[0]);
        ModAction initDefaultsWindow = new ModAction(this, "showDefaults", new Class[0]);
        ModAction showInstaSpawner = new ModAction(this, "showInstaSpawner", new Class[0]);
        this.MoCScreen.append(GuiApiHelper.makeButton("General Settings", initGeneralSettingsWindow, true));
        this.MoCScreen.append(GuiApiHelper.makeButton("ID Settings", initIDSettingsWindow, true));
        this.MoCScreen.append(GuiApiHelper.makeButton("Insta-Spawner", showInstaSpawner, true));
        this.MoCScreen.append(GuiApiHelper.makeButton("Creatures", initCreatureWindow, true));
        this.MoCScreen.append(GuiApiHelper.makeButton("Mobs", initMobWindow, true));
        this.MoCScreen.append(GuiApiHelper.makeButton("Water Mobs", initWaterMobWindow, true));
        this.MoCScreen.append(GuiApiHelper.makeButton("Ambient", initAmbientWindow, true));
        //MoCScreen.append(GuiApiHelper.makeButton("Reset to Defaults", initDefaultsWindow, true));
        //**********************************************************//

        //******************** Creatures ********************//
        this.creatureOptions = new WidgetClassicTwocolumn(new Widget[0]);
        // create buttons
        if (!MoCreatures.isCustomSpawnerLoaded) {
            this.creatureOptions.add(GuiApiHelper.makeButton("Spawn Settings", new ModAction(this, "showCreatureSpawnSettings", new Class[0]), true));
        }
        this.creatureOptions.add(GuiApiHelper.makeButton("General Settings", new ModAction(this, "showCreatureSettings", new Class[0]), true));
        //**********************************************************//

        //******************** Mobs ********************//
        this.mobOptions = new WidgetClassicTwocolumn(new Widget[0]);
        if (!MoCreatures.isCustomSpawnerLoaded) {
            this.mobOptions.add(GuiApiHelper.makeButton("Spawn Settings", new ModAction(this, "showMobSpawnSettings", new Class[0]), true));
        }
        this.mobOptions.add(GuiApiHelper.makeButton("General Settings", new ModAction(this, "showMobSettings", new Class[0]), true));
        //**********************************************************//

        //******************** Water Mobs ********************//
        this.waterOptions = new WidgetClassicTwocolumn(new Widget[0]);
        if (!MoCreatures.isCustomSpawnerLoaded) {
            this.waterOptions.add(GuiApiHelper.makeButton("Spawn Settings", new ModAction(this, "showWaterSpawnSettings", new Class[0]), true));
        }
        this.waterOptions.add(GuiApiHelper.makeButton("General Settings", new ModAction(this, "showWaterSettings", new Class[0]), true));
        //**********************************************************//

        //******************** Ambient ********************//
        this.ambientOptions = new WidgetClassicTwocolumn(new Widget[0]);
        if (!MoCreatures.isCustomSpawnerLoaded) {
            this.ambientOptions.add(GuiApiHelper.makeButton("Spawn Settings", new ModAction(this, "showAmbientSpawnSettings", new Class[0]), true));
        }
        //**********************************************************//

        //******************** Reset All ********************//
        /*
         * SimpleButtonModel simplebuttonmodel8 = new SimpleButtonModel();
         * simplebuttonmodel8.addActionCallback(new ModAction(guiapiSettings,
         * "resetAll", new Class[0])); Button button8 = new
         * Button(simplebuttonmodel8); button8.setText("Reset to defaults");
         */
        //**********************************************************//

        this.guiapiSettings.load();
    }

    //*********************** GUI API MENU CALLS ************************//

    // Creatures
    public void showCreatureOptions() {
        if (this.creatureOptionsWindow == null) {
            this.creatureOptionsWindow = new WidgetSimplewindow(this.creatureOptions, "Creature Options");
        }

        GuiModScreen.show(this.creatureOptionsWindow);
    }

    public void showCreatureSettings() {
        if (this.creatureSettingsWindow == null) {
            this.widgetCreatureSettingsColumns = new WidgetClassicTwocolumn(new Widget[0]);
            this.guiapiSettings.append(easybreedingB =
                    new MoCSettingBoolean(this.mocSettingsConfig, CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "EasyBreeding", this.easyBreeding));
            easybreedingW = new WidgetBoolean(easybreedingB, "Easy Horse Breed", "Yes", "No");
            this.widgetCreatureSettingsColumns.add(easybreedingW);
            this.guiapiSettings.append(pegasusChanceS =
                    new MoCSettingInt(this.mocSettingsConfig, CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "ZebraChance", this.zebraChance, 1, 1, 10));
            pegasusChanceW = new WidgetInt(pegasusChanceS, "Zebra chance");
            this.widgetCreatureSettingsColumns.add(pegasusChanceW);
            this.guiapiSettings.append(ostrichEggDropChanceS =
                    new MoCSettingInt(this.mocSettingsConfig, CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "OstrichEggDropChance",
                            this.ostrichEggDropChance, 1, 1, 3));
            ostrichEggDropChanceW = new WidgetInt(ostrichEggDropChanceS, "Ostrich Egg Drop Chance");
            this.widgetCreatureSettingsColumns.add(ostrichEggDropChanceW);
            this.guiapiSettings.append(rareItemDropChanceS =
                    new MoCSettingInt(this.mocSettingsConfig, CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "RareItemDropChance", this.rareItemDropChance,
                            1, 1, 25));
            rareItemDropChanceW = new WidgetInt(rareItemDropChanceS, "Rare Item Drop Chance");
            this.widgetCreatureSettingsColumns.add(rareItemDropChanceW);
            this.guiapiSettings.append(wyvernEggDropChanceS =
                    new MoCSettingInt(this.mocSettingsConfig, CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "WyvernEggDropChance",
                            this.wyvernEggDropChance, 1, 1, 10));
            wyvernEggDropChanceW = new WidgetInt(wyvernEggDropChanceS, "Wyvern Egg Drop Chance");
            this.widgetCreatureSettingsColumns.add(wyvernEggDropChanceW);
            this.guiapiSettings.append(motherWyvernEggDropChanceS =
                    new MoCSettingInt(this.mocSettingsConfig, CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "MotherWyvernEggDropChance",
                            this.motherWyvernEggDropChance, 1, 1, 33));
            motherWyvernEggDropChanceW = new WidgetInt(motherWyvernEggDropChanceS, "M. Wyvern Egg Drop Chance");
            this.widgetCreatureSettingsColumns.add(motherWyvernEggDropChanceW);
            this.guiapiSettings.append(attackhorses =
                    new MoCSettingBoolean(this.mocSettingsConfig, CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "AttackHorses", this.attackHorses));
            attackhorsesW = new WidgetBoolean(attackhorses, "Target horses?", "Yes", "No");
            this.widgetCreatureSettingsColumns.add(attackhorsesW);
            this.guiapiSettings.append(attackwolvesB =
                    new MoCSettingBoolean(this.mocSettingsConfig, CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "AttackWolves", this.attackWolves));
            attackwolvesW = new WidgetBoolean(attackwolvesB, "Target dogs?", "Yes", "No");
            this.widgetCreatureSettingsColumns.add(attackwolvesW);
            this.guiapiSettings.append(destroyitemsB =
                    new MoCSettingBoolean(this.mocSettingsConfig, CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "DestroyDrops", this.destroyDrops));
            destroyitemsW = new WidgetBoolean(destroyitemsB, "Destroy drops?", "Yes", "No");
            this.widgetCreatureSettingsColumns.add(destroyitemsW);
            this.guiapiSettings.append(killallVillagersB =
                    new MoCSettingBoolean(this.mocSettingsConfig, CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "KillAllVillagers", this.killallVillagers));
            killallVillagersW = new WidgetBoolean(killallVillagersB, "Killall Villagers?", "Yes", "No");
            this.widgetCreatureSettingsColumns.add(killallVillagersW);
            this.creatureSettingsWindow = new WidgetSimplewindow(this.widgetCreatureSettingsColumns, BUTTON_CREATURE_GENERAL_SETTINGS);
        }
        GuiModScreen.show(this.creatureSettingsWindow);
    }

    public void showCreatureSpawnSettings() {
        if (this.creatureSpawnSettingsWindow == null) {
            this.widgetCreatureSpawnSettingsColumns = new WidgetClassicTwocolumn(new Widget[0]);
            // create entity button for each creature
            for (Map.Entry<String, MoCEntityData> entityEntry : MoCreatures.mocEntityMap.entrySet()) {
                if (entityEntry.getValue().getType() == EnumCreatureType.CREATURE) {
                    this.widgetCreatureSpawnSettingsColumns.add(GuiApiHelper.makeButton(entityEntry.getKey(), new ModAction(this,
                            "showEntitySettings", MoCEntityData.class).setDefaultArguments(entityEntry.getValue()), true));
                }
            }
            this.creatureSpawnSettingsWindow = new WidgetSimplewindow(this.widgetCreatureSpawnSettingsColumns, BUTTON_CREATURE_SPAWN_SETTINGS);
        }
        GuiModScreen.show(this.creatureSpawnSettingsWindow);
    }

    // Mobs
    public void showMobOptions() {
        if (this.mobOptionsWindow == null) {
            this.mobOptionsWindow = new WidgetSimplewindow(this.mobOptions, "Mob Options");
        }

        GuiModScreen.show(this.mobOptionsWindow);
    }

    public void showMobSpawnSettings() {
        if (this.mobSpawnSettingsWindow == null) {
            this.widgetMobSpawnSettingsColumns = new WidgetClassicTwocolumn(new Widget[0]);
            // create entity button for each mob
            for (Map.Entry<String, MoCEntityData> entityEntry : MoCreatures.mocEntityMap.entrySet()) {
                if (entityEntry.getValue().getType() == EnumCreatureType.MONSTER) {
                    this.widgetMobSpawnSettingsColumns.add(GuiApiHelper.makeButton(entityEntry.getKey(), new ModAction(this, "showEntitySettings",
                            MoCEntityData.class).setDefaultArguments(entityEntry.getValue()), true));
                }
            }
            this.mobSpawnSettingsWindow = new WidgetSimplewindow(this.widgetMobSpawnSettingsColumns, BUTTON_MONSTER_SPAWN_SETTINGS);
        }

        GuiModScreen.show(this.mobSpawnSettingsWindow);
    }

    public void showMobSettings() {
        if (this.mobSettingsWindow == null) {
            this.widgetMobSettingsColumns = new WidgetClassicTwocolumn(new Widget[0]);
            this.guiapiSettings.append(ogreStrengthS =
                    new MoCSettingFloat(this.mocSettingsConfig, CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "OgreStrength", this.ogreStrength, 0.1F, 0.1F,
                            5F));
            ogreStrengthW = new WidgetFloat(ogreStrengthS, "Ogre Strength");
            this.widgetMobSettingsColumns.add(ogreStrengthW);
            this.guiapiSettings.append(fireOgreStrengthS =
                    new MoCSettingFloat(this.mocSettingsConfig, CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "FireOgreStrength", this.fireOgreStrength,
                            0.1F, 0.1F, 5F));
            fireOgreStrengthW = new WidgetFloat(fireOgreStrengthS, "Fire O. Strength");
            this.widgetMobSettingsColumns.add(fireOgreStrengthW);
            this.guiapiSettings.append(caveOgreStrengthS =
                    new MoCSettingFloat(this.mocSettingsConfig, CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "CaveOgreStrength", this.caveOgreStrength,
                            0.1F, 0.1F, 5F));
            caveOgreStrengthW = new WidgetFloat(caveOgreStrengthS, "Cave O. Strength");
            this.widgetMobSettingsColumns.add(caveOgreStrengthW);
            this.guiapiSettings.append(ogreAttackRangeS =
                    new MoCSettingInt(this.mocSettingsConfig, CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "OgreAttackRange", this.ogreAttackRange, 1, 1,
                            24));
            ogreAttackRangeW = new WidgetInt(ogreAttackRangeS, "Ogre Attack Range");
            this.widgetMobSettingsColumns.add(ogreAttackRangeW);
            this.guiapiSettings
                    .append(caveOgreChanceS =
                            new MoCSettingInt(this.mocSettingsConfig, CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "caveOgreChance", this.caveOgreChance,
                                    0, 1, 100));
            caveOgreChanceW = new WidgetInt(caveOgreChanceS, "Cave Ogre Chance");
            this.widgetMobSettingsColumns.add(caveOgreChanceW);
            this.guiapiSettings
                    .append(fireOgreChanceS =
                            new MoCSettingInt(this.mocSettingsConfig, CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "fireOgreChance", this.fireOgreChance,
                                    0, 1, 100));
            fireOgreChanceW = new WidgetInt(fireOgreChanceS, "Fire Ogre Chance");
            this.widgetMobSettingsColumns.add(fireOgreChanceW);
            this.guiapiSettings.append(golemDestroyBlocksB =
                    new MoCSettingBoolean(this.mocSettingsConfig, CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "golemDestroyBlocks",
                            this.golemDestroyBlocks));
            golemDestroyBlocksW = new WidgetBoolean(golemDestroyBlocksB, "Golem Destroy Blocks?");
            this.widgetMobSettingsColumns.add(golemDestroyBlocksW);
            this.mobSettingsWindow = new WidgetSimplewindow(this.widgetMobSettingsColumns, BUTTON_MONSTER_GENERAL_SETTINGS);
        }

        GuiModScreen.show(this.mobSettingsWindow);
    }

    // Water Mobs
    public void showWaterMobOptions() {
        if (this.waterMobOptionsWindow == null) {
            this.waterMobOptionsWindow = new WidgetSimplewindow(this.waterOptions, "Water Mob Options");
        }

        GuiModScreen.show(this.waterMobOptionsWindow);
    }

    public void showWaterSpawnSettings() {
        if (this.waterMobSpawnSettingsWindow == null) {
            this.widgetWaterMobSpawnSettingsColumns = new WidgetClassicTwocolumn(new Widget[0]);
            // create entity button for each water creature
            for (Map.Entry<String, MoCEntityData> entityEntry : MoCreatures.mocEntityMap.entrySet()) {
                if (entityEntry.getValue().getType() == EnumCreatureType.WATER_CREATURE) {
                    this.widgetWaterMobSpawnSettingsColumns.add(GuiApiHelper.makeButton(entityEntry.getKey(), new ModAction(this,
                            "showEntitySettings", MoCEntityData.class).setDefaultArguments(entityEntry.getValue()), true));
                }
            }
            this.waterMobSpawnSettingsWindow = new WidgetSimplewindow(this.widgetWaterMobSpawnSettingsColumns, BUTTON_WATERMOB_SPAWN_SETTINGS);
        }

        GuiModScreen.show(this.waterMobSpawnSettingsWindow);
    }

    public void showWaterSettings() {
        if (this.waterMobSettingsWindow == null) {
            this.widgetWaterMobSettingsColumns = new WidgetClassicTwocolumn(new Widget[0]);
            this.guiapiSettings
                    .append(attackdolphinsB =
                            new MoCSettingBoolean(this.mocSettingsConfig, CATEGORY_MOC_WATER_CREATURE_GENERAL_SETTINGS, "AttackDolphins",
                                    this.attackDolphins));
            attackdolphinsW = new WidgetBoolean(attackdolphinsB, "Aggresive Dolphins?", "Yes", "No");
            this.widgetWaterMobSettingsColumns.add(attackdolphinsW);
            this.waterMobSettingsWindow = new WidgetSimplewindow(this.widgetWaterMobSettingsColumns, BUTTON_WATERMOB_GENERAL_SETTINGS);
        }

        GuiModScreen.show(this.waterMobSettingsWindow);
    }

    // Ambient
    public void showAmbientOptions() {
        if (this.ambientOptionsWindow == null) {
            this.ambientOptionsWindow = new WidgetSimplewindow(this.ambientOptions, "Ambient Options");
        }

        GuiModScreen.show(this.ambientOptionsWindow);
    }

    public void showAmbientSpawnSettings() {
        if (this.ambientSpawnSettingsWindow == null) {
            this.widgetAmbientSpawnSettingsColumns = new WidgetClassicTwocolumn(new Widget[0]);
            // create entity button for each ambient
            for (Map.Entry<String, MoCEntityData> entityEntry : MoCreatures.mocEntityMap.entrySet()) {
                if (entityEntry.getValue().getType() == EnumCreatureType.AMBIENT) {
                    this.widgetAmbientSpawnSettingsColumns.add(GuiApiHelper.makeButton(entityEntry.getKey(), new ModAction(this,
                            "showEntitySettings", MoCEntityData.class).setDefaultArguments(entityEntry.getValue()), true));
                }
            }
            this.ambientSpawnSettingsWindow = new WidgetSimplewindow(this.widgetAmbientSpawnSettingsColumns, BUTTON_AMBIENT_SPAWN_SETTINGS);
        }
        GuiModScreen.show(this.ambientSpawnSettingsWindow);
    }

    public void showGeneralSpawnerOptions() {
        if (this.instaSpawnerWindow == null) {
            this.instaSpawnerWindow = new WidgetSimplewindow(this.widgetInstaSpawnerColumn, "Select the number and creature to spawn");
        }
        GuiModScreen.show(this.instaSpawnerWindow);
    }

    public void showGeneralSettings() {
        if (this.generalSettingsWindow == null) {
            this.widgetGeneralSettingsColumns = new WidgetClassicTwocolumn(new Widget[0]);
            this.guiapiSettings.append(debugB = new MoCSettingBoolean(this.mocSettingsConfig, CATEGORY_MOC_GENERAL_SETTINGS, "debug", this.debug));
            debugW = new WidgetBoolean(debugB, "Show Debug Logging?", "Yes", "No");
            this.widgetGeneralSettingsColumns.add(debugW);
            this.guiapiSettings.append(displaynameB =
                    new MoCSettingBoolean(this.mocSettingsConfig, CATEGORY_MOC_GENERAL_SETTINGS, "displayPetName", this.displayPetName));
            displaynameW = new WidgetBoolean(displaynameB, "Show Pet Name?", "Yes", "No");
            this.widgetGeneralSettingsColumns.add(displaynameW);
            this.guiapiSettings.append(displayhealthB =
                    new MoCSettingBoolean(this.mocSettingsConfig, CATEGORY_MOC_GENERAL_SETTINGS, "displayPetHealth", this.displayPetHealth));
            displayhealthW = new WidgetBoolean(displayhealthB, "Show Pet Health?", "Yes", "No");
            this.widgetGeneralSettingsColumns.add(displayhealthW);
            this.guiapiSettings.append(displayemoB =
                    new MoCSettingBoolean(this.mocSettingsConfig, CATEGORY_MOC_GENERAL_SETTINGS, "displayPetIcons", this.displayPetIcons));
            displayemoW = new WidgetBoolean(displayemoB, "Show Pet Icons?", "Yes", "No");
            this.widgetGeneralSettingsColumns.add(displayemoW);
            this.guiapiSettings.append(staticbedB =
                    new MoCSettingBoolean(this.mocSettingsConfig, CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "StaticBed", this.staticBed));
            staticbedW = new WidgetBoolean(staticbedB, "Static K.Bed?", "Yes", "No");
            this.widgetGeneralSettingsColumns.add(staticbedW);
            this.guiapiSettings.append(staticlitterB =
                    new MoCSettingBoolean(this.mocSettingsConfig, CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "StaticLitter", this.staticLitter));
            staticlitterW = new WidgetBoolean(staticlitterB, "Static Litter?", "Yes", "No");
            this.widgetGeneralSettingsColumns.add(staticlitterW);
            this.guiapiSettings.append(particleFXS =
                    new MoCSettingInt(this.mocSettingsConfig, CATEGORY_MOC_GENERAL_SETTINGS, "particleFX", this.particleFX, 0, 1, 10));
            particleFXW = new WidgetInt(particleFXS, "FX Particle density");
            this.widgetGeneralSettingsColumns.add(particleFXW);
            this.guiapiSettings.append(animateTexturesB =
                    new MoCSettingBoolean(this.mocSettingsConfig, CATEGORY_MOC_GENERAL_SETTINGS, "AnimateTextures", this.animateTextures));
            animateTexturesW = new WidgetBoolean(animateTexturesB, "Animate Textures", "Yes", "No");
            this.widgetGeneralSettingsColumns.add(animateTexturesW);
            this.generalSettingsWindow = new WidgetSimplewindow(this.widgetGeneralSettingsColumns, BUTTON_GENERAL_SETTINGS);
        }

        GuiModScreen.show(this.generalSettingsWindow);
    }

    public void showIDSettings() {
        if (this.IDSettingsWindow == null) {
            this.widgetIDSettingsColumns = new WidgetClassicTwocolumn(new Widget[0]);
            this.guiapiSettings.append(mocitemidA =
                    new MoCSettingInt(this.mocSettingsConfig, CATEGORY_MOC_ID_SETTINGS, "ItemID", this.itemID, 4096, 1, 60000));
            mocitemidW = new WidgetInt(mocitemidA, "Item ID");
            this.widgetIDSettingsColumns.add(mocitemidW);
            this.guiapiSettings.append(blockDirtIdS =
                    new MoCSettingInt(this.mocSettingsConfig, CATEGORY_MOC_ID_SETTINGS, "DirtBlockID", this.blockDirtID, 1, 1, 255));
            blockDirtIdW = new WidgetInt(blockDirtIdS, "DirtBlock ID");
            this.widgetIDSettingsColumns.add(blockDirtIdW);
            this.guiapiSettings.append(blockGrassIdS =
                    new MoCSettingInt(this.mocSettingsConfig, CATEGORY_MOC_ID_SETTINGS, "GrassBlockID", this.blockGrassID, 1, 1, 255));
            blockGrassIdW = new WidgetInt(blockGrassIdS, "GrassBlock ID");
            this.widgetIDSettingsColumns.add(blockGrassIdW);
            this.guiapiSettings.append(blockStoneIdS =
                    new MoCSettingInt(this.mocSettingsConfig, CATEGORY_MOC_ID_SETTINGS, "StoneBlockID", this.blockStoneID, 256, 1, 60000));
            blockStoneIdW = new WidgetInt(blockStoneIdS, "StoneBlock ID");
            this.widgetIDSettingsColumns.add(blockStoneIdW);
            this.guiapiSettings.append(blockLeafIdS =
                    new MoCSettingInt(this.mocSettingsConfig, CATEGORY_MOC_ID_SETTINGS, "LeafBlockID", this.blockLeafID, 256, 1, 60000));
            blockLeafIdW = new WidgetInt(blockLeafIdS, "LeafBlock ID");
            this.widgetIDSettingsColumns.add(blockLeafIdW);
            this.guiapiSettings.append(blockLogIdS =
                    new MoCSettingInt(this.mocSettingsConfig, CATEGORY_MOC_ID_SETTINGS, "LogBlockID", this.blockLogID, 256, 1, 60000));
            blockLogIdW = new WidgetInt(blockLogIdS, "LogBlock ID");
            this.widgetIDSettingsColumns.add(blockLogIdW);
            this.guiapiSettings.append(blockTallGrassIdS =
                    new MoCSettingInt(this.mocSettingsConfig, CATEGORY_MOC_ID_SETTINGS, "TallGrassBlockID", this.blockTallGrassID, 256, 1, 60000));
            blockTallGrassIdW = new WidgetInt(blockTallGrassIdS, "TallG.Block ID");
            this.widgetIDSettingsColumns.add(blockTallGrassIdW);
            this.guiapiSettings.append(blockPlanksIdS =
                    new MoCSettingInt(this.mocSettingsConfig, CATEGORY_MOC_ID_SETTINGS, "PlanksBlockID", this.blockPlanksID, 256, 1, 60000));
            blockPlanksIdW = new WidgetInt(blockPlanksIdS, "PlanksBlock ID");
            this.widgetIDSettingsColumns.add(blockPlanksIdW);
            this.guiapiSettings.append(wyvernBiomeIdS =
                    new MoCSettingInt(this.mocSettingsConfig, CATEGORY_MOC_ID_SETTINGS, "WyvernLairBiomeID", this.WyvernBiomeID, 22, 1, 255));
            wyvernBiomeIdW = new WidgetInt(wyvernBiomeIdS, "WyvernBiome ID");
            this.widgetIDSettingsColumns.add(wyvernBiomeIdW);
            this.guiapiSettings.append(wyvernDimensionIdS =
                    new MoCSettingInt(this.mocSettingsConfig, CATEGORY_MOC_ID_SETTINGS, "WyvernLairDimensionID", this.WyvernDimension, -1000, 1,
                            60000));
            wyvernDimensionIdW = new WidgetInt(wyvernDimensionIdS, "Wyv. Dimension");
            this.widgetIDSettingsColumns.add(wyvernDimensionIdW);
            this.IDSettingsWindow = new WidgetSimplewindow(this.widgetIDSettingsColumns, BUTTON_ID_SETTINGS);
        }

        GuiModScreen.show(this.IDSettingsWindow);
    }

    public void showDefaults() {
        if (this.defaultsWindow == null) {
            this.defaultChoices = new WidgetClassicTwocolumn(new Widget[0]);
            SimpleButtonModel defaultChoiceButtonModel = new SimpleButtonModel();
            defaultChoiceButtonModel.addActionCallback(new ModAction(this, "resetToDefaults", new Class[0]));
            Button defaultChoiceButton = new Button(defaultChoiceButtonModel);
            defaultChoiceButton.setText("Yes");
            this.defaultChoices.add(defaultChoiceButton);
            SimpleButtonModel defaultChoiceButtonModel2 = new SimpleButtonModel();
            defaultChoiceButtonModel2.addActionCallback(new ModAction(this, "cancelReset", new Class[0]));
            Button defaultChoiceButton2 = new Button(defaultChoiceButtonModel2);
            defaultChoiceButton2.setText("No");
            this.defaultChoices.add(defaultChoiceButton2);
            this.defaultsWindow = new WidgetSimplewindow(this.defaultChoices, "Are you sure ? All settings will be erased.");
        }
        GuiModScreen.show(this.defaultsWindow);
    }

    public void showEntitySettings(MoCEntityData entityData) {
        if (entityData != null) {
            if (entityData.getEntityWindow() == null) {
                WidgetSinglecolumn widgetEntitySettingColumn = new WidgetSinglecolumn(new Widget[0]);
                //MoCSettingMulti settingType = new MoCSettingMulti(mocEntityConfig, entityData.getEntityName(), entityData.getEntityName() + " type", entityData.getType() != null ? entityTypes.indexOf(entityData.getType().name().toUpperCase()) : 0, "UNDEFINED", "CREATURE", "MONSTER", "WATERCREATURE", "AMBIENT");
                //guiapiSettings.append(settingType);
                //widgetEntitySettingColumn.add(new WidgetMulti(settingType, "Type"));
                MoCSettingInt settingFrequency =
                        new MoCSettingInt(this.mocEntityConfig, entityData.getEntityName(), entityData.getEntityName() + " frequency",
                                entityData.getFrequency(), 0, 1, 20);
                this.guiapiSettings.append(settingFrequency);
                widgetEntitySettingColumn.add(new WidgetInt(settingFrequency, "Frequency"));
                MoCSettingInt settingMinGroup =
                        new MoCSettingInt(this.mocEntityConfig, entityData.getEntityName(), entityData.getEntityName() + " minspawn",
                                entityData.getMinSpawn(), 1, 1, 20);
                this.guiapiSettings.append(settingMinGroup);
                widgetEntitySettingColumn.add(new WidgetInt(settingMinGroup, "MinSpawn"));
                MoCSettingInt settingMaxGroup =
                        new MoCSettingInt(this.mocEntityConfig, entityData.getEntityName(), entityData.getEntityName() + " maxspawn",
                                entityData.getMaxSpawn(), 1, 1, 20);
                this.guiapiSettings.append(settingMaxGroup);
                widgetEntitySettingColumn.add(new WidgetInt(settingMaxGroup, "MaxSpawn"));
                MoCSettingInt settingChunkGroup =
                        new MoCSettingInt(this.mocEntityConfig, entityData.getEntityName(), entityData.getEntityName() + " chunkspawn",
                                entityData.getMaxInChunk(), 1, 1, 20);
                this.guiapiSettings.append(settingChunkGroup);
                widgetEntitySettingColumn.add(new WidgetInt(settingChunkGroup, "MaxChunk"));
                MoCSettingBoolean settingCanSpawn =
                        new MoCSettingBoolean(this.mocEntityConfig, entityData.getEntityName(), entityData.getEntityName() + " canspawn",
                                entityData.getCanSpawn());
                this.guiapiSettings.append(settingCanSpawn);
                widgetEntitySettingColumn.add(new WidgetBoolean(settingCanSpawn, "CanSpawn"));
                entityData.setEntityWindow(new WidgetSimplewindow(widgetEntitySettingColumn, entityData.getEntityName()));
            }
            this.currentSelectedEntity = entityData;
            GuiModScreen.show(entityData.getEntityWindow());
        }
    }

    public void showInstaSpawner() {
        this.widgetInstaSpawnerColumn = new WidgetSinglecolumn(new Widget[0]);

        ArrayList<String> moCreaturesList = new ArrayList<String>();
        for (Map.Entry<String, MoCEntityData> entityEntry : MoCreatures.mocEntityMap.entrySet()) {
            moCreaturesList.add(entityEntry.getValue().getEntityName());
        }
        Collections.sort(moCreaturesList);
        this.entityList =
                this.guiapiSettings.addSetting(this.widgetInstaSpawnerColumn, "Creature Type:", "SpawnEntityList", moCreaturesList,
                        this.mocSettingsConfig, "");
        ((WidgetList) this.entityList.displayWidget).listBox.setTheme("/listbox");
        this.widgetInstaSpawnerColumn.heightOverrideExceptions.put(this.entityList.displayWidget, 130);
        this.settingNumberToSpawn = this.guiapiSettings.addSetting(this.widgetInstaSpawnerColumn, "Number to Spawn", "spawnN", 3, 1, 10);
        this.widgetInstaSpawnerColumn.add(GuiApiHelper.makeButton("Perform Spawn", new ModAction(this, "instaSpawn", MoCSettingList.class,
                ArrayList.class).setDefaultArguments(this.entityList, moCreaturesList), true));
        this.instaSpawnerWindow = new WidgetSimplewindow(this.widgetInstaSpawnerColumn, "Select the Creature to Spawn");
        GuiModScreen.show(this.instaSpawnerWindow);
    }

    public void instaSpawn(MoCSettingList setting, ArrayList<String> aList) {
        ListBox<String> listbox = ((WidgetList) setting.displayWidget).listBox;
        int selected = listbox.getSelected();
        int numberToSpawn = this.settingNumberToSpawn.get();
        String entityName = aList.get(selected);
        for (Map.Entry<String, MoCEntityData> entityEntry : MoCreatures.mocEntityMap.entrySet()) {
            if (entityEntry.getValue().getEntityName().equalsIgnoreCase(entityName)) {
                try {
                    MoCMessageHandler.INSTANCE.sendToServer(new MoCMessageInstaSpawn(entityEntry.getValue().getEntityID(), numberToSpawn));
                } catch (Exception ex) {

                }
            }
        }
    }

    @Override
    public void resetAllData() {
        this.instaSpawnerWindow = null;
        this.settingNumberToSpawn = null;
        this.creatureOptionsWindow = null;
        this.creatureSettingsWindow = null;
        this.creatureSpawnSettingsWindow = null;
        this.waterMobOptionsWindow = null;
        this.waterMobSettingsWindow = null;
        this.waterMobSpawnSettingsWindow = null;
        this.mobOptionsWindow = null;
        this.mobSettingsWindow = null;
        this.mobSpawnSettingsWindow = null;
        this.ambientOptionsWindow = null;
        this.ambientSpawnSettingsWindow = null;
        this.generalSettingsWindow = null;
        this.IDSettingsWindow = null;
        this.vanillamobwindow = null;
        this.defaultsWindow = null;

        for (int i = 0; i < ModSettingScreen.modScreens.size(); i++) {
            if (ModSettingScreen.modScreens.get(i).niceName.equalsIgnoreCase(MOC_SCREEN_TITLE)) {
                ModSettingScreen.modScreens.remove(i);
            }
        }

        this.MoCScreen = null;
        this.guiapiSettings = null;
        super.resetAllData();
    }

    public void resetToDefaults() {
        if (this.mocSettingsConfig.getFile().exists()) {
            String parentDir = this.configFile.getParent();
            if (!this.mocSettingsConfig.getFile().renameTo(new File(parentDir, "MoCreatures" + File.separator + "MoCGlobal.cfg.bak"))) {
                File oldFile = new File(parentDir, "MoCreatures" + File.separator + "MoCGlobal.cfg.bak");
                oldFile.delete();
                this.mocSettingsConfig.getFile().renameTo(new File(parentDir, "MoCreatures" + File.separator + "MoCGlobal.cfg.bak"));
            }
            this.mocSettingsConfig = new MoCConfiguration(new File(parentDir, "MoCreatures" + File.separator + "MoCGlobal.cfg"));
            File mocreaturesFile = new File(parentDir, "MoCreatures.cfg");
            if (mocreaturesFile.exists()) {
                if (!mocreaturesFile.renameTo(new File(parentDir, "MoCreatures.cfg.bak"))) {
                    File oldFile = new File(parentDir, "MoCreatures.cfg.bak");
                    oldFile.delete();
                    mocreaturesFile.renameTo(new File(parentDir, "MoCreatures.cfg.bak"));
                }
            }
        }
        resetAllData();
        MoCreatures.proxy.initGUI();
        GuiModScreen.show(this.MoCScreen.theWidget);
    }

    public void cancelReset() {
        GuiModScreen.show(MoCClientProxy.instance.MoCScreen.theWidget);
    }

    @Override
    public int getParticleFX() {
        return this.particleFX;
    }

    @Override
    public boolean getDisplayPetName() {
        return this.displayPetName;
    }

    @Override
    public boolean getDisplayPetIcons() {
        return this.displayPetIcons;
    }

    @Override
    public boolean getDisplayPetHealth() {
        return this.displayPetHealth;
    }

    @Override
    public boolean getAnimateTextures() {
        return this.animateTextures;
    }

    @Override
    public void printMessageToPlayer(String msg) {
        Minecraft.getMinecraft().player.sendMessage(new TextComponentTranslation(msg));
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
