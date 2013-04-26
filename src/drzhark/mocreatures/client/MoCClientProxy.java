package drzhark.mocreatures.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntitySpellParticleFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import sharose.mods.guiapi.GuiApiHelper;
import sharose.mods.guiapi.GuiModScreen;
import sharose.mods.guiapi.ModAction;
import sharose.mods.guiapi.ModSettingScreen;
import sharose.mods.guiapi.SettingList;
import sharose.mods.guiapi.SettingMulti;
import sharose.mods.guiapi.WidgetBoolean;
import sharose.mods.guiapi.WidgetClassicTwocolumn;
import sharose.mods.guiapi.WidgetFloat;
import sharose.mods.guiapi.WidgetInt;
import sharose.mods.guiapi.WidgetList;
import sharose.mods.guiapi.WidgetMulti;
import sharose.mods.guiapi.WidgetSimplewindow;
import sharose.mods.guiapi.WidgetSingleRow;
import sharose.mods.guiapi.WidgetSinglecolumn;
import sharose.mods.guiapi.WidgetText;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.ListBox;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.model.SimpleButtonModel;
import drzhark.mocreatures.MoCBiomeGroupData;
import drzhark.mocreatures.MoCConfigCategory;
import drzhark.mocreatures.MoCEntityData;
import drzhark.mocreatures.MoCProperty;
import drzhark.mocreatures.MoCProxy;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.audio.MoCSounds;
import drzhark.mocreatures.client.gui.MoCGUIEntityNamer;
import drzhark.mocreatures.client.gui.MoCSettingBoolean;
import drzhark.mocreatures.client.gui.MoCSettingFloat;
import drzhark.mocreatures.client.gui.MoCSettingInt;
import drzhark.mocreatures.client.gui.MoCSettingList;
import drzhark.mocreatures.client.gui.MoCSettingMulti;
import drzhark.mocreatures.client.gui.MoCSettingText;
import drzhark.mocreatures.client.gui.MoCSettings;
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
import drzhark.mocreatures.client.model.MoCModelMouse;
import drzhark.mocreatures.client.model.MoCModelNewHorse;
import drzhark.mocreatures.client.model.MoCModelNewHorseMob;
import drzhark.mocreatures.client.model.MoCModelOgre;
import drzhark.mocreatures.client.model.MoCModelOstrich;
import drzhark.mocreatures.client.model.MoCModelPetScorpion;
import drzhark.mocreatures.client.model.MoCModelRat;
import drzhark.mocreatures.client.model.MoCModelRay;
import drzhark.mocreatures.client.model.MoCModelRoach;
import drzhark.mocreatures.client.model.MoCModelScorpion;
import drzhark.mocreatures.client.model.MoCModelShark;
import drzhark.mocreatures.client.model.MoCModelSnail;
import drzhark.mocreatures.client.model.MoCModelSnake;
import drzhark.mocreatures.client.model.MoCModelTurkey;
import drzhark.mocreatures.client.model.MoCModelTurtle;
import drzhark.mocreatures.client.model.MoCModelWere;
import drzhark.mocreatures.client.model.MoCModelWereHuman;
import drzhark.mocreatures.client.model.MoCModelWolf;
import drzhark.mocreatures.client.model.MoCModelWraith;
import drzhark.mocreatures.client.model.MoCModelWyvern;
import drzhark.mocreatures.client.network.MoCClientPacketHandler;
import drzhark.mocreatures.client.renderer.entity.MoCRenderAnimal;
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
import drzhark.mocreatures.client.renderer.entity.MoCRenderFishy;
import drzhark.mocreatures.client.renderer.entity.MoCRenderGoat;
import drzhark.mocreatures.client.renderer.entity.MoCRenderGolem;
import drzhark.mocreatures.client.renderer.entity.MoCRenderHellRat;
import drzhark.mocreatures.client.renderer.entity.MoCRenderHorseMob;
import drzhark.mocreatures.client.renderer.entity.MoCRenderInsect;
import drzhark.mocreatures.client.renderer.entity.MoCRenderJellyFish;
import drzhark.mocreatures.client.renderer.entity.MoCRenderKitty;
import drzhark.mocreatures.client.renderer.entity.MoCRenderKittyBed;
import drzhark.mocreatures.client.renderer.entity.MoCRenderLitterBox;
import drzhark.mocreatures.client.renderer.entity.MoCRenderMouse;
import drzhark.mocreatures.client.renderer.entity.MoCRenderNewHorse;
import drzhark.mocreatures.client.renderer.entity.MoCRenderOstrich;
import drzhark.mocreatures.client.renderer.entity.MoCRenderPetScorpion;
import drzhark.mocreatures.client.renderer.entity.MoCRenderPlatform;
import drzhark.mocreatures.client.renderer.entity.MoCRenderRat;
import drzhark.mocreatures.client.renderer.entity.MoCRenderRay;
import drzhark.mocreatures.client.renderer.entity.MoCRenderScorpion;
import drzhark.mocreatures.client.renderer.entity.MoCRenderShark;
import drzhark.mocreatures.client.renderer.entity.MoCRenderSnake;
import drzhark.mocreatures.client.renderer.entity.MoCRenderTRock;
import drzhark.mocreatures.client.renderer.entity.MoCRenderTurtle;
import drzhark.mocreatures.client.renderer.entity.MoCRenderWWolf;
import drzhark.mocreatures.client.renderer.entity.MoCRenderWerewolf;
import drzhark.mocreatures.client.renderer.entity.MoCRenderWraith;
import drzhark.mocreatures.client.settings.MoCKeyHandler;
import drzhark.mocreatures.entity.MoCIMoCreature;
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
import drzhark.mocreatures.entity.monster.MoCEntityOgre;
import drzhark.mocreatures.entity.monster.MoCEntityRat;
import drzhark.mocreatures.entity.monster.MoCEntityScorpion;
import drzhark.mocreatures.entity.monster.MoCEntityWWolf;
import drzhark.mocreatures.entity.monster.MoCEntityWerewolf;
import drzhark.mocreatures.entity.monster.MoCEntityWraith;
import drzhark.mocreatures.entity.passive.MoCEntityBear;
import drzhark.mocreatures.entity.passive.MoCEntityBee;
import drzhark.mocreatures.entity.passive.MoCEntityBigCat;
import drzhark.mocreatures.entity.passive.MoCEntityBird;
import drzhark.mocreatures.entity.passive.MoCEntityBoar;
import drzhark.mocreatures.entity.passive.MoCEntityBunny;
import drzhark.mocreatures.entity.passive.MoCEntityButterfly;
import drzhark.mocreatures.entity.passive.MoCEntityCrab;
import drzhark.mocreatures.entity.passive.MoCEntityCricket;
import drzhark.mocreatures.entity.passive.MoCEntityCrocodile;
import drzhark.mocreatures.entity.passive.MoCEntityDeer;
import drzhark.mocreatures.entity.passive.MoCEntityDolphin;
import drzhark.mocreatures.entity.passive.MoCEntityDragonfly;
import drzhark.mocreatures.entity.passive.MoCEntityDuck;
import drzhark.mocreatures.entity.passive.MoCEntityElephant;
import drzhark.mocreatures.entity.passive.MoCEntityFirefly;
import drzhark.mocreatures.entity.passive.MoCEntityFishy;
import drzhark.mocreatures.entity.passive.MoCEntityFly;
import drzhark.mocreatures.entity.passive.MoCEntityFox;
import drzhark.mocreatures.entity.passive.MoCEntityGoat;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.entity.passive.MoCEntityJellyFish;
import drzhark.mocreatures.entity.passive.MoCEntityKitty;
import drzhark.mocreatures.entity.passive.MoCEntityKomodo;
import drzhark.mocreatures.entity.passive.MoCEntityMaggot;
import drzhark.mocreatures.entity.passive.MoCEntityMouse;
import drzhark.mocreatures.entity.passive.MoCEntityOstrich;
import drzhark.mocreatures.entity.passive.MoCEntityPetScorpion;
import drzhark.mocreatures.entity.passive.MoCEntityRay;
import drzhark.mocreatures.entity.passive.MoCEntityRoach;
import drzhark.mocreatures.entity.passive.MoCEntityShark;
import drzhark.mocreatures.entity.passive.MoCEntitySnail;
import drzhark.mocreatures.entity.passive.MoCEntitySnake;
import drzhark.mocreatures.entity.passive.MoCEntityTurkey;
import drzhark.mocreatures.entity.passive.MoCEntityTurtle;
import drzhark.mocreatures.entity.passive.MoCEntityWyvern;

public class MoCClientProxy extends MoCProxy {

    public static Minecraft mc = Minecraft.getMinecraft();
    public static MoCClientProxy instance;

    public MoCClientProxy()
    {
        instance = this;
        TickRegistry.registerTickHandler(new MoCClientTickHandler(), Side.CLIENT);
        KeyBindingRegistry.registerKeyBinding(new MoCKeyHandler());
    }

    @Override
    public void registerRenderers()
    {
        //no longer needed on 1.5
        //MinecraftForgeClient.preloadTexture(ITEMS_PNG);
        //MinecraftForgeClient.preloadTexture(BLOCK_PNG);
        //MoCTexturePreload.preLoadTextures(); 
    }

    @Override
    public void initSounds()
    {
        MinecraftForge.EVENT_BUS.register(new MoCSounds());
    }

    @Override
    public void registerRenderInformation()
    {
        //RenderingRegistry.addNewArmourRendererPrefix(BLOCK_PNG);

        // Register your custom renderers
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityBunny.class, new MoCRenderBunny(new MoCModelBunny(), 0.3F));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityBird.class, new MoCRenderBird(new MoCModelBird(), 0.3F));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityTurtle.class, new MoCRenderTurtle(new MoCModelTurtle(), 0.4F));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityMouse.class, new MoCRenderMouse(new MoCModelMouse(), 0.1F));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntitySnake.class, new MoCRenderSnake(new MoCModelSnake(), 0.0F));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityTurkey.class, new MoCRenderAnimal(new MoCModelTurkey(), 0.4F));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityButterfly.class, new MoCRenderButterfly(new MoCModelButterfly()));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityHorse.class, new MoCRenderNewHorse(new MoCModelNewHorse()));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityHorseMob.class, new MoCRenderHorseMob(new MoCModelNewHorseMob()));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityBoar.class, new MoCRenderBoar(new MoCModelBoar(), 0.5F));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityBear.class, new MoCRenderBear(new MoCModelBear(), 0.7F));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityDuck.class, new MoCRenderAnimal(new MoCModelDuck(), 0.3F));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityBigCat.class, new MoCRenderBigCat(new MoCModelBigCat2(), new MoCModelBigCat1(), 0.7F));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityDeer.class, new MoCRenderDeer(new MoCModelDeer(), 0.5F));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityWWolf.class, new MoCRenderWWolf(new MoCModelWolf(), 0.7F));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityWraith.class, new MoCRenderWraith(new MoCModelWraith(), 0.5F));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityFlameWraith.class, new MoCRenderWraith(new MoCModelWraith(), 0.5F));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityWerewolf.class, new MoCRenderWerewolf(new MoCModelWereHuman(), new MoCModelWere(), 0.7F));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityFox.class, new MoCRenderAnimal(new MoCModelFox(), 0.5F));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityShark.class, new MoCRenderShark(new MoCModelShark(), 0.6F));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityDolphin.class, new MoCRenderDolphin(new MoCModelDolphin(), 0.6F));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityFishy.class, new MoCRenderFishy(new MoCModelFishy(), 0.2F));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityEgg.class, new MoCRenderEgg(new MoCModelEgg(), 0.0F));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityKitty.class, new MoCRenderKitty(new MoCModelKitty(0.0F, 15F), 0.4F));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityKittyBed.class, new MoCRenderKittyBed(new MoCModelKittyBed(), new MoCModelKittyBed2(), 0.3F));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityLitterBox.class, new MoCRenderLitterBox(new MoCModelLitterBox(), 0.3F));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityRat.class, new MoCRenderRat(new MoCModelRat(), 0.2F));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityHellRat.class, new MoCRenderHellRat(new MoCModelRat(), 0.4F));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityScorpion.class, new MoCRenderScorpion(new MoCModelScorpion(), 0.6F));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityCrocodile.class, new MoCRenderCrocodile(new MoCModelCrocodile(), 0.5F));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityRay.class, new MoCRenderRay(new MoCModelRay(), 0.4F));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityJellyFish.class, new MoCRenderJellyFish(new MoCModelJellyFish(), 0.1F));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityGoat.class, new MoCRenderGoat(new MoCModelGoat(), 0.3F));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityFishBowl.class, new MoCRenderFishBowl(new MoCModelFishBowl(), 0.3F));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityOstrich.class, new MoCRenderOstrich(new MoCModelOstrich(), 0.5F));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityBee.class, new MoCRenderInsect(new MoCModelBee()));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityFly.class, new MoCRenderInsect(new MoCModelFly()));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityDragonfly.class, new MoCRenderInsect(new MoCModelDragonfly()));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityFirefly.class, new MoCRenderFirefly(new MoCModelFirefly()));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityCricket.class, new MoCRenderCricket(new MoCModelCricket()));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntitySnail.class, new MoCRenderAnimal(new MoCModelSnail(), 0.0F));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityGolem.class, new MoCRenderGolem(new MoCModelGolem(), 0.5F));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityThrowableRock.class, new MoCRenderTRock());
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityPetScorpion.class, new MoCRenderPetScorpion(new MoCModelPetScorpion(), 0.6F));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityPlatform.class, new MoCRenderPlatform());
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityElephant.class, new MoCRenderAnimal(new MoCModelElephant(), 0.7F));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityKomodo.class, new MoCRenderAnimal(new MoCModelKomodo(), 0.3F));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityWyvern.class, new MoCRenderAnimal(new MoCModelWyvern(), 0.5F));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityOgre.class, new MoCRenderAnimal(new MoCModelOgre(), 0.6F));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityRoach.class, new MoCRenderInsect(new MoCModelRoach()));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityMaggot.class, new MoCRenderAnimal(new MoCModelMaggot(), 0F));
        RenderingRegistry.instance().registerEntityRenderingHandler(MoCEntityCrab.class, new MoCRenderAnimal(new MoCModelCrab(), 0.2F));
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
    public void setName(EntityPlayer player, MoCIMoCreature mocanimal)
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

    //-----------------GUI API STUFF
    /**
     * GUI API options
     */

    public static MoCSettingInt maxMobsS;
    public static WidgetInt maxMobsW;
    public static MoCSettingInt maxAnimalsS;
    public static WidgetInt maxAnimalsW;
    public static MoCSettingInt maxWaterMobsS;
    public static WidgetInt maxWaterMobsW;
    public static MoCSettingInt animalSpawnTickRateS;
    public static WidgetInt animalSpawnTickRateW;
    public static MoCSettingInt mobSpawnTickRateS;
    public static WidgetInt mobSpawnTickRateW;
    public static MoCSettingInt maxAmbientMobsS;
    public static WidgetInt maxAmbientMobsW;
    public static MoCSettingBoolean worldGenCreatureSpawningB;
    public static WidgetBoolean worldGenCreatureSpawningW;
    
    public static MoCSettingInt despawnTickRateS;
    public static WidgetInt despawnTickRateW;
    
    //public static MoCSettingInt instaSpawnS;
   // public static WidgetInt instaSpawnW;
    //public static MoCSettingInt instaSpawnN;
    //public static WidgetInt instaSpawnNW;

    public static MoCSettingBoolean spawnPiranhaS;
    public static MoCSettingInt particleFXS;
    public static WidgetInt particleFXW;
    
    public static MoCSettingBoolean animateTexturesB;
    public static WidgetBoolean animateTexturesW;

    public static MoCSettingBoolean useCustomSpawnerS;
    public static WidgetBoolean useCustomSpawnerW;

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
    public static WidgetBoolean spawnpiranhaW;
    public static MoCSettingInt pegasusChanceS;
    public static WidgetInt pegasusChanceW;
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
    
    public MoCSettings guiapiSettings;
    public ModSettingScreen MoCScreen;

    public MoCSettingList settingBiomeGroups;
    public MoCSettingList entityBiomeList;
    public MoCSettingList biomesList;
    public MoCSettingList entityList;
    
    public static MoCSettingBoolean despawnVanillaB;
    public static WidgetBoolean despawnVanillaW;

    public static MoCSettingBoolean modifyVanillaSpawnsB;
    public static WidgetBoolean modifyVanillaSpawnsW;
    
    public static MoCSettingBoolean debugLoggingB;
    public static WidgetBoolean debugLoggingW;

    public static MoCSettingBoolean isOldWorld;
    public static WidgetBoolean isOldWorldW;
    public static MoCSettingInt mocitemidA;
    public static WidgetInt mocitemidW;
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

    public MoCSettingList listBoxSettingTest;

    private WidgetSimplewindow instaSpawnerWindow;
    private MoCSettingInt settingNumberToSpawn;
    private MoCSettingMulti settingClassToSpawn;
    public WidgetSimplewindow biomesWindow;
    public WidgetSimplewindow biomeGroupsWindow;
    public WidgetSimplewindow customSpawnerWindow;
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
    public WidgetSimplewindow instaSpaqwnerWindow;
    private WidgetClassicTwocolumn widgetCreatureSettingsColumns;
    private WidgetClassicTwocolumn widgetCreatureSpawnSettingsColumns;
    private WidgetClassicTwocolumn widgetMobSettingsColumns;
    private WidgetClassicTwocolumn widgetMobSpawnSettingsColumns;
    private WidgetClassicTwocolumn widgetWaterMobSettingsColumns;
    private WidgetClassicTwocolumn widgetWaterMobSpawnSettingsColumns;
    private WidgetClassicTwocolumn widgetAmbientSettingsColumns;
    private WidgetClassicTwocolumn widgetAmbientSpawnSettingsColumns;
    private WidgetClassicTwocolumn widgetCustomSpawnerColumns;
    private WidgetClassicTwocolumn widgetGeneralSettingsColumns;
    private WidgetClassicTwocolumn widgetIDSettingsColumns;
    private WidgetClassicTwocolumn widgetBiomeForestGroupColumns;
    private WidgetClassicTwocolumn widgetBiomeArcticGroupColumns;
    private WidgetClassicTwocolumn widgetBiomeNormalGroupColumns;
    private WidgetClassicTwocolumn widgetBiomeMountainGroupColumns;
    private WidgetClassicTwocolumn widgetBiomeJungleGroupColumns;
    private WidgetClassicTwocolumn widgetBiomeDesertGroupColumns;
    private WidgetClassicTwocolumn widgetBiomeSwampGroupColumns;
    private WidgetClassicTwocolumn defaultChoices;
    private WidgetClassicTwocolumn biomeColumns;
    private WidgetSinglecolumn widgetInstaSpawnerColumn;
    private WidgetClassicTwocolumn creatureOptions;
    private WidgetClassicTwocolumn mobOptions;
    private WidgetClassicTwocolumn waterMobOptions;
    private WidgetClassicTwocolumn ambientOptions;
    private WidgetClassicTwocolumn biomeGroupOptions;
    private WidgetClassicTwocolumn biomeButtons;

    public boolean needsBiomeGroupUpdate = false;

    public static final String BUTTON_GENERAL_SETTINGS = "General Settings";
    public static final String BUTTON_ID_SETTINGS = "ID Settings";
    public static final String BUTTON_FREQUENCIES = "Frequencies";
    public static final String BUTTON_MINGROUP = "Min Group Spawn";
    public static final String BUTTON_MAXGROUP = "Max Group Spawn";
    public static final String BUTTON_VANILLA_CREATURE_FREQUENCIES = "Vanilla Creature Frequencies";
    public static final String BUTTON_VANILLA_MONSTER_FREQUENCIES = "Vanilla Monster Frequencies";
    public static final String BUTTON_VANILLA_WATER_CREATURE_FREQUENCIES = "Vanilla Watercreature Frequencies";
    public static final String BUTTON_CREATURES = "Creatures";
    public static final String BUTTON_CREATURE_GENERAL_SETTINGS = "Creature General Settings";
    public static final String BUTTON_CREATURE_SPAWN_SETTINGS = "Creature Spawn Settings";
    public static final String BUTTON_CREATURE_CHUNK_SETTINGS = "Creature Chunk Settings";
    public static final String BUTTON_MONSTER_GENERAL_SETTINGS = "Monster General Settings";
    public static final String BUTTON_MONSTER_SPAWN_SETTINGS = "Monster Spawn Settings";
    public static final String BUTTON_MONSTER_CHUNK_SETTINGS = "Monster Chunk Settings";
    public static final String BUTTON_WATERMOB_GENERAL_SETTINGS = "Water Mob General Settings";
    public static final String BUTTON_WATERMOB_SPAWN_SETTINGS = "Water Mob Spawn Settings";
    public static final String BUTTON_WATERMOB_CHUNK_SETTINGS = "Water Mob Chunk Settings";
    public static final String BUTTON_AMBIENT_GENERAL_SETTINGS = "Ambient General Settings";
    public static final String BUTTON_AMBIENT_SPAWN_SETTINGS = "Ambient Spawn Settings";
    public static final String BUTTON_AMBIENT_CHUNK_SETTINGS = "Ambient Chunk Settings";
    public static final String BUTTON_CUSTOMSPAWNER_SETTINGS = "Customspawner Settings";
    public static final String BUTTON_OWNERSHIP_SETTINGS = "Ownership Settings";
    public static final String BUTTON_ENTITY_SPAWN_SETTINGS = "Entity Biome Settings";
    public static final String BUTTON_DEFAULTS = "Reset to Defaults";

    private Map<String, WidgetClassicTwocolumn> widgetBiomeGroupMap = new HashMap<String, WidgetClassicTwocolumn>();

    public MoCEntityData currentSelectedEntity;

    @Override
    public void ConfigInit(FMLPreInitializationEvent event) {
        super.ConfigInit(event);
        resetGuiSettings();
    }

    public void ConfigPostInit(FMLPostInitializationEvent event) {
        super.ConfigPostInit(event);
        MoCreatures.log.info("Initializing MoCreatures GUI API");
        // GUI API settings
        guiapiSettings = new MoCSettings("MoCreatures");
        MoCScreen = new ModSettingScreen("DrZhark's Mo'Creatures");

        //******************** Main Menu ********************//
        ModAction initCreatureWindow = new ModAction(this, "showCreatureOptions", new Class[0]);
        ModAction initMobWindow = new ModAction(this, "showMobOptions", new Class[0]);
        ModAction initWaterMobWindow = new ModAction(this, "showWaterMobOptions", new Class[0]);
        ModAction initAmbientWindow = new ModAction(this, "showAmbientOptions", new Class[0]);
        ModAction initCustomSpawnerWindow = new ModAction(this, "showCustomSpawnerOptions", new Class[0]);
        ModAction initGeneralSettingsWindow = new ModAction(this, "showGeneralSettings", new Class[0]);
        ModAction initIDSettingsWindow = new ModAction(this, "showIDSettings", new Class[0]);
        ModAction initBiomeGroupsWindow = new ModAction(this, "showBiomeGroups", new Class[0]);
        ModAction initDefaultsWindow = new ModAction(this, "showDefaults", new Class[0]);
        ModAction showInstaSpawner = new ModAction(this, "showInstaSpawner", new Class[0]);
        MoCScreen.append(GuiApiHelper.makeButton("General Settings", initGeneralSettingsWindow, true));
        MoCScreen.append(GuiApiHelper.makeButton("ID Settings", initIDSettingsWindow, true));
        MoCScreen.append(GuiApiHelper.makeButton("CustomSpawner", initCustomSpawnerWindow, true));
        MoCScreen.append(GuiApiHelper.makeButton("Insta-Spawner", showInstaSpawner, true));
        MoCScreen.append(GuiApiHelper.makeButton("Creatures", initCreatureWindow, true));
        MoCScreen.append(GuiApiHelper.makeButton("Mobs", initMobWindow, true));
        MoCScreen.append(GuiApiHelper.makeButton("Water Mobs", initWaterMobWindow, true));
        MoCScreen.append(GuiApiHelper.makeButton("Ambient", initAmbientWindow, true));
        MoCScreen.append(GuiApiHelper.makeButton("Biome Groups", initBiomeGroupsWindow, true));
        MoCScreen.append(GuiApiHelper.makeButton("Reset to Defaults", initDefaultsWindow, true));
        //**********************************************************//

        //******************** Creatures ********************//
        creatureOptions = new WidgetSinglecolumn(new Widget[0]);

        SimpleButtonModel creatureSpawnButtonModel = new SimpleButtonModel();
        creatureSpawnButtonModel.addActionCallback(new ModAction(this, "showCreatureSpawnSettings", new Class[0]));
        Button creatureSpawnButton = new Button(creatureSpawnButtonModel);
        creatureSpawnButton.setText(BUTTON_CREATURE_SPAWN_SETTINGS);
        creatureOptions.add(creatureSpawnButton);
        widgetCreatureSpawnSettingsColumns = new WidgetClassicTwocolumn(new Widget[0]);
        for (Map.Entry<String, MoCEntityData> entry : entityMap.entrySet())
        {
           if (entry.getValue().getType() == EnumCreatureType.creature)
           {
               widgetCreatureSpawnSettingsColumns.add(GuiApiHelper.makeButton(entry.getKey(), new ModAction(this, "showEntitySettings", String.class).setDefaultArguments(entry.getKey()), true));
           }
        }

        SimpleButtonModel simplebuttonmodel2 = new SimpleButtonModel();
        simplebuttonmodel2.addActionCallback(new ModAction(this, "showCreatureSettings", new Class[0]));
        Button button2 = new Button(simplebuttonmodel2);
        button2.setText(BUTTON_CREATURE_GENERAL_SETTINGS);
        creatureOptions.add(button2);
        widgetCreatureSettingsColumns = new WidgetClassicTwocolumn(new Widget[0]);
        guiapiSettings.append(easybreedingB = new MoCSettingBoolean(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "EasyBreeding", easyBreeding));
        easybreedingW = new WidgetBoolean(easybreedingB, "Easy Horse Breed", "Yes", "No");
        widgetCreatureSettingsColumns.add(easybreedingW);
        guiapiSettings.append(pegasusChanceS = new MoCSettingInt(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "ZebraChance", zebraChance, 1, 1, 5));
        pegasusChanceW = new WidgetInt(pegasusChanceS, "Zebra chance");
        widgetCreatureSettingsColumns.add(pegasusChanceW);
        guiapiSettings.append(attackhorses = new MoCSettingBoolean(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "AttackHorses", attackHorses));
        attackhorsesW = new WidgetBoolean(attackhorses, "Target horses?", "Yes", "No");
        widgetCreatureSettingsColumns.add(attackhorsesW);
        guiapiSettings.append(attackwolvesB = new MoCSettingBoolean(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "AttackWolves", attackWolves));
        attackwolvesW = new WidgetBoolean(attackwolvesB, "Target dogs?", "Yes", "No");
        widgetCreatureSettingsColumns.add(attackwolvesW);
        guiapiSettings.append(destroyitemsB = new MoCSettingBoolean(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "DestroyDrops", destroyDrops));
        destroyitemsW = new WidgetBoolean(destroyitemsB, "Destroy drops?", "Yes", "No");
        widgetCreatureSettingsColumns.add(destroyitemsW);
        //**********************************************************//


        //******************** Mobs ********************//
        mobOptions = new WidgetSinglecolumn(new Widget[0]);

        SimpleButtonModel mobSpawnButtonModel = new SimpleButtonModel();
        mobSpawnButtonModel.addActionCallback(new ModAction(this, "showMobSpawnSettings", new Class[0]));
        Button mobSpawnButton = new Button(mobSpawnButtonModel);
        mobSpawnButton.setText(BUTTON_MONSTER_SPAWN_SETTINGS);
        mobOptions.add(mobSpawnButton);
        widgetMobSpawnSettingsColumns = new WidgetClassicTwocolumn(new Widget[0]);
        for (Map.Entry<String, MoCEntityData> entry : entityMap.entrySet())
        {
           if (entry.getValue().getType() == EnumCreatureType.monster)
           {
               widgetMobSpawnSettingsColumns.add(GuiApiHelper.makeButton(entry.getKey(), new ModAction(this, "showEntitySettings", String.class).setDefaultArguments(entry.getKey()), true));
           }
        }

        SimpleButtonModel simplebuttonmodel4 = new SimpleButtonModel();
        simplebuttonmodel4.addActionCallback(new ModAction(this, "showMobSettings", new Class[0]));
        Button button4 = new Button(simplebuttonmodel4);
        button4.setText(BUTTON_MONSTER_GENERAL_SETTINGS);
        mobOptions.add(button4);
        widgetMobSettingsColumns = new WidgetClassicTwocolumn(new Widget[0]);
        guiapiSettings.append(ogreStrengthS = new MoCSettingFloat(CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "OgreStrength", ogreStrength, 0.1F, 0.1F, 5F));
        ogreStrengthW = new WidgetFloat(ogreStrengthS, "Ogre Strength");
        widgetMobSettingsColumns.add(ogreStrengthW);
        guiapiSettings.append(fireOgreStrengthS = new MoCSettingFloat(CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "FireOgreStrength", fireOgreStrength, 0.1F, 0.1F, 5F));
        fireOgreStrengthW = new WidgetFloat(fireOgreStrengthS, "Fire O. Strength");
        widgetMobSettingsColumns.add(fireOgreStrengthW);
        guiapiSettings.append(caveOgreStrengthS = new MoCSettingFloat(CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "CaveOgreStrength", caveOgreStrength, 0.1F, 0.1F, 5F));
        caveOgreStrengthW = new WidgetFloat(caveOgreStrengthS, "Cave O. Strength");
        widgetMobSettingsColumns.add(caveOgreStrengthW);
        guiapiSettings.append(ogreAttackRangeS = new MoCSettingInt(CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "OgreAttackRange", ogreAttackRange, 1, 1, 24));
        ogreAttackRangeW = new WidgetInt(ogreAttackRangeS, "Ogre Attack Range");
        widgetMobSettingsColumns.add(ogreAttackRangeW);
        guiapiSettings.append(caveOgreChanceS = new MoCSettingInt(CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "caveOgreChance", caveOgreChance, 0, 1, 100));
        caveOgreChanceW = new WidgetInt(caveOgreChanceS, "Cave Ogre Chance");
        widgetMobSettingsColumns.add(caveOgreChanceW);
        guiapiSettings.append(fireOgreChanceS = new MoCSettingInt(CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "fireOgreChance", fireOgreChance, 0, 1, 100));
        fireOgreChanceW = new WidgetInt(fireOgreChanceS, "Fire Ogre Chance");
        widgetMobSettingsColumns.add(fireOgreChanceW);
        //**********************************************************//


        //******************** Water Mobs ********************//
        waterMobOptions = new WidgetSinglecolumn(new Widget[0]);

        SimpleButtonModel waterMobSpawnButtonModel = new SimpleButtonModel();
        waterMobSpawnButtonModel.addActionCallback(new ModAction(this, "showWaterMobSpawnSettings", new Class[0]));
        Button waterMobSpawnButton = new Button(waterMobSpawnButtonModel);
        waterMobSpawnButton.setText(BUTTON_WATERMOB_SPAWN_SETTINGS);
        waterMobOptions.add(waterMobSpawnButton);
        widgetWaterMobSpawnSettingsColumns = new WidgetClassicTwocolumn(new Widget[0]);
        for (Map.Entry<String, MoCEntityData> entry : entityMap.entrySet())
        {
           if (entry.getValue().getType() == EnumCreatureType.waterCreature)
           {
               widgetWaterMobSpawnSettingsColumns.add(GuiApiHelper.makeButton(entry.getKey(), new ModAction(this, "showEntitySettings", String.class).setDefaultArguments(entry.getKey()), true));
           }
        }

        SimpleButtonModel simplebuttonmodel5 = new SimpleButtonModel();
        simplebuttonmodel5.addActionCallback(new ModAction(this, "showWaterMobSettings", new Class[0]));

        Button button5 = new Button(simplebuttonmodel5);
        button5.setText(BUTTON_WATERMOB_GENERAL_SETTINGS);
        waterMobOptions.add(button5);
        widgetWaterMobSettingsColumns = new WidgetClassicTwocolumn(new Widget[0]);
        guiapiSettings.append(attackdolphinsB = new MoCSettingBoolean(CATEGORY_MOC_WATER_CREATURE_GENERAL_SETTINGS, "AttackDolphins", attackDolphins));
        attackdolphinsW = new WidgetBoolean(attackdolphinsB, "Aggresive Dolphins?", "Yes", "No");
        widgetWaterMobSettingsColumns.add(attackdolphinsW);
        guiapiSettings.append(spawnPiranhaS = new MoCSettingBoolean(CATEGORY_MOC_WATER_CREATURE_GENERAL_SETTINGS, "SpawnPiranhas", spawnPiranhas));
        spawnpiranhaW = new WidgetBoolean(spawnPiranhaS, "Spawn Piranhas?", "Yes", "No");
        widgetWaterMobSettingsColumns.add(spawnpiranhaW);
        //**********************************************************//

        //******************** Ambient ********************//
        ambientOptions = new WidgetSinglecolumn(new Widget[0]);

        SimpleButtonModel ambientSpawnButtonModel = new SimpleButtonModel();
        ambientSpawnButtonModel.addActionCallback(new ModAction(this, "showAmbientSpawnSettings", new Class[0]));
        Button ambientSpawnButton = new Button(ambientSpawnButtonModel);
        ambientSpawnButton.setText(BUTTON_AMBIENT_SPAWN_SETTINGS);
        ambientOptions.add(ambientSpawnButton);
        widgetAmbientSpawnSettingsColumns = new WidgetClassicTwocolumn(new Widget[0]);
        for (Map.Entry<String, MoCEntityData> entry : entityMap.entrySet())
        {
           if (entry.getValue().getType() == EnumCreatureType.ambient)
           {
               widgetAmbientSpawnSettingsColumns.add(GuiApiHelper.makeButton(entry.getKey(), new ModAction(this, "showEntitySettings", String.class).setDefaultArguments(entry.getKey()), true));
           }
        }

        //******************** CustomSpawner ********************//
        widgetCustomSpawnerColumns = new WidgetClassicTwocolumn(new Widget[0]);
        guiapiSettings.append(useCustomSpawnerS = new MoCSettingBoolean(CATEGORY_CUSTOMSPAWNER_SETTINGS, "useCustomSpawner", useCustomSpawner));
        useCustomSpawnerW = new WidgetBoolean(useCustomSpawnerS, "Use CustomSpawner?", "Yes", "No");
        widgetCustomSpawnerColumns.add(useCustomSpawnerW);
        guiapiSettings.append(animalSpawnTickRateS = new MoCSettingInt(CATEGORY_CUSTOMSPAWNER_SETTINGS, "animalSpawnTickRate", animalSpawnTickRate, 1, 1, 1000));
        animalSpawnTickRateW = new WidgetInt(animalSpawnTickRateS, "Anml Spawn Ticks");
        widgetCustomSpawnerColumns.add(animalSpawnTickRateW);
        guiapiSettings.append(mobSpawnTickRateS = new MoCSettingInt(CATEGORY_CUSTOMSPAWNER_SETTINGS, "mobSpawnTickRate", mobSpawnTickRate, 1, 1, 1000));
        mobSpawnTickRateW = new WidgetInt(mobSpawnTickRateS, "Mob Spawn Ticks");
        widgetCustomSpawnerColumns.add(mobSpawnTickRateW);
        guiapiSettings.append(maxMobsS = new MoCSettingInt(CATEGORY_CUSTOMSPAWNER_SETTINGS, "maxMobs", maxMobs, 1, 1, 1000));
        maxMobsW = new WidgetInt(maxMobsS, "Hostiles");
        widgetCustomSpawnerColumns.add(maxMobsW);
        guiapiSettings.append(maxAnimalsS = new MoCSettingInt(CATEGORY_CUSTOMSPAWNER_SETTINGS, "maxAnimals", maxAnimals, 1, 1, 1000));
        maxAnimalsW = new WidgetInt(maxAnimalsS, "Animals");
        widgetCustomSpawnerColumns.add(maxAnimalsW);
        guiapiSettings.append(maxWaterMobsS = new MoCSettingInt(CATEGORY_CUSTOMSPAWNER_SETTINGS, "maxWaterMobs", maxWaterMobs, 1, 1, 1000));
        maxWaterMobsW = new WidgetInt(maxWaterMobsS, "Water Mobs");
        widgetCustomSpawnerColumns.add(maxWaterMobsW);
        guiapiSettings.append(maxAmbientMobsS = new MoCSettingInt(CATEGORY_CUSTOMSPAWNER_SETTINGS, "maxAmbient", maxAmbient, 1, 1, 1000));
        maxWaterMobsW = new WidgetInt(maxAmbientMobsS, "Ambient Mobs");
        widgetCustomSpawnerColumns.add(maxWaterMobsW);
        guiapiSettings.append(despawnVanillaB = new MoCSettingBoolean(CATEGORY_CUSTOMSPAWNER_SETTINGS, "DespawnVanilla", despawnVanilla));
        despawnVanillaW = new WidgetBoolean(despawnVanillaB, "Despawn vanilla mobs?", "Yes", "No");
        widgetCustomSpawnerColumns.add(despawnVanillaW);
        guiapiSettings.append(despawnTickRateS = new MoCSettingInt(CATEGORY_CUSTOMSPAWNER_SETTINGS, "despawnTickRate", despawnTickRate, 1, 1, 1000));
        despawnTickRateW = new WidgetInt(despawnTickRateS, "Despawn Tick R.");
        widgetCustomSpawnerColumns.add(despawnTickRateW);
        guiapiSettings.append(modifyVanillaSpawnsB = new MoCSettingBoolean(CATEGORY_CUSTOMSPAWNER_SETTINGS, "ModifyVanillaSpawns", modifyVanillaSpawns));
        modifyVanillaSpawnsW = new WidgetBoolean(modifyVanillaSpawnsB, "Change vanilla spawns?", "Yes", "No");
        widgetCustomSpawnerColumns.add(modifyVanillaSpawnsW);
        guiapiSettings.append(worldGenCreatureSpawningB = new MoCSettingBoolean(CATEGORY_CUSTOMSPAWNER_SETTINGS, "worldGenCreatureSpawning", worldGenCreatureSpawning));
        worldGenCreatureSpawningW = new WidgetBoolean(worldGenCreatureSpawningB, "Chunk Gen Spawning?", "Yes", "No");
        widgetCustomSpawnerColumns.add(worldGenCreatureSpawningW);
        //**********************************************************//

       
        //******************** General Settings********************//
        widgetGeneralSettingsColumns = new WidgetClassicTwocolumn(new Widget[0]);
        guiapiSettings.append(debugLoggingB = new MoCSettingBoolean(CATEGORY_MOC_GENERAL_SETTINGS, "debugLogging", debugLogging));
        debugLoggingW = new WidgetBoolean(debugLoggingB, "Show Debug Logging?", "Yes", "No");
        widgetGeneralSettingsColumns.add(debugLoggingW);
        guiapiSettings.append(displaynameB = new MoCSettingBoolean(CATEGORY_MOC_GENERAL_SETTINGS, "displayPetName", displayPetName));
        displaynameW = new WidgetBoolean(displaynameB, "Show Pet Name?", "Yes", "No");
        widgetGeneralSettingsColumns.add(displaynameW);
        guiapiSettings.append(displayhealthB = new MoCSettingBoolean(CATEGORY_MOC_GENERAL_SETTINGS, "displayPetHealth", displayPetHealth));
        displayhealthW = new WidgetBoolean(displayhealthB, "Show Pet Health?", "Yes", "No");
        widgetGeneralSettingsColumns.add(displayhealthW);
        guiapiSettings.append(displayemoB = new MoCSettingBoolean(CATEGORY_MOC_GENERAL_SETTINGS, "displayPetIcons", displayPetIcons));
        displayemoW = new WidgetBoolean(displayemoB, "Show Pet Icons?", "Yes", "No");
        widgetGeneralSettingsColumns.add(displayemoW);
        guiapiSettings.append(staticbedB = new MoCSettingBoolean(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "StaticBed", staticBed));
        staticbedW = new WidgetBoolean(staticbedB, "Static K.Bed?", "Yes", "No");
        widgetGeneralSettingsColumns.add(staticbedW);
        guiapiSettings.append(staticlitterB = new MoCSettingBoolean(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "StaticLitter", staticLitter));
        staticlitterW = new WidgetBoolean(staticlitterB, "Static Litter?", "Yes", "No");
        widgetGeneralSettingsColumns.add(staticlitterW);
        guiapiSettings.append(particleFXS = new MoCSettingInt(CATEGORY_MOC_GENERAL_SETTINGS, "particleFX", particleFX, 0, 1, 10));
        particleFXW = new WidgetInt(particleFXS, "FX Particle density");
        widgetGeneralSettingsColumns.add(particleFXW);
        guiapiSettings.append(animateTexturesB = new MoCSettingBoolean(CATEGORY_MOC_GENERAL_SETTINGS, "AnimateTextures", animateTextures));
        animateTexturesW = new WidgetBoolean(animateTexturesB, "Animate Textures", "Yes", "No");
        widgetGeneralSettingsColumns.add(animateTexturesW);
        //**********************************************************//

        //******************** ID Settings ********************//
        widgetIDSettingsColumns = new WidgetClassicTwocolumn(new Widget[0]);
        guiapiSettings.append(mocitemidA = new MoCSettingInt(CATEGORY_MOC_ID_SETTINGS, "ItemID", itemID, 4096, 1, 60000));
        mocitemidW = new WidgetInt(mocitemidA, "Item ID");
        widgetIDSettingsColumns.add(mocitemidW);
        guiapiSettings.append(blockDirtIdS = new MoCSettingInt(CATEGORY_MOC_ID_SETTINGS, "DirtBlockID", blockDirtID, 1, 1, 255));
        blockDirtIdW = new WidgetInt(blockDirtIdS, "DirtBlock ID");
        widgetIDSettingsColumns.add(blockDirtIdW);
        guiapiSettings.append(blockGrassIdS = new MoCSettingInt(CATEGORY_MOC_ID_SETTINGS, "GrassBlockID", blockGrassID, 1, 1, 255));
        blockGrassIdW = new WidgetInt(blockGrassIdS, "GrassBlock ID");
        widgetIDSettingsColumns.add(blockGrassIdW);
        guiapiSettings.append(blockStoneIdS = new MoCSettingInt(CATEGORY_MOC_ID_SETTINGS, "StoneBlockID", blockStoneID, 256, 1, 60000));
        blockStoneIdW = new WidgetInt(blockStoneIdS, "StoneBlock ID");
        widgetIDSettingsColumns.add(blockStoneIdW);
        guiapiSettings.append(blockLeafIdS = new MoCSettingInt(CATEGORY_MOC_ID_SETTINGS, "LeafBlockID", blockLeafID, 256, 1, 60000));
        blockLeafIdW = new WidgetInt(blockLeafIdS, "LeafBlock ID");
        widgetIDSettingsColumns.add(blockLeafIdW);
        guiapiSettings.append(blockLogIdS = new MoCSettingInt(CATEGORY_MOC_ID_SETTINGS, "LogBlockID", blockLogID, 256, 1, 60000));
        blockLogIdW = new WidgetInt(blockLogIdS, "LogBlock ID");
        widgetIDSettingsColumns.add(blockLogIdW);
        guiapiSettings.append(blockTallGrassIdS = new MoCSettingInt(CATEGORY_MOC_ID_SETTINGS, "TallGrassBlockID", blockTallGrassID, 256, 1, 60000));
        blockTallGrassIdW = new WidgetInt(blockTallGrassIdS, "TallG.Block ID");
        widgetIDSettingsColumns.add(blockTallGrassIdW);
        guiapiSettings.append(blockPlanksIdS = new MoCSettingInt(CATEGORY_MOC_ID_SETTINGS, "PlanksBlockID", blockPlanksID, 256, 1, 60000));
        blockPlanksIdW = new WidgetInt(blockPlanksIdS, "PlanksBlock ID");
        widgetIDSettingsColumns.add(blockPlanksIdW);
        guiapiSettings.append(wyvernBiomeIdS = new MoCSettingInt(CATEGORY_MOC_ID_SETTINGS, "WyvernLairBiomeID", WyvernBiomeID, 22, 1, 255));
        wyvernBiomeIdW = new WidgetInt(wyvernBiomeIdS, "WyvernBiome ID");
        widgetIDSettingsColumns.add(wyvernBiomeIdW);
        guiapiSettings.append(wyvernDimensionIdS = new MoCSettingInt(CATEGORY_MOC_ID_SETTINGS, "WyvernLairDimensionID", WyvernDimension, -1000, 1, 60000));
        wyvernDimensionIdW = new WidgetInt(wyvernDimensionIdS, "Wyv. Dimension");
        widgetIDSettingsColumns.add(wyvernDimensionIdW);
        //**********************************************************//
        
        
        //******************** Reset Default Settings ********************//
        defaultChoices = new WidgetClassicTwocolumn(new Widget[0]);
        SimpleButtonModel defaultChoiceButtonModel = new SimpleButtonModel();
        defaultChoiceButtonModel.addActionCallback(new ModAction(this, "startDefaultCopy", new Class[0]));
        Button defaultChoiceButton = new Button(defaultChoiceButtonModel);
        defaultChoiceButton.setText("Yes");
        defaultChoices.add(defaultChoiceButton);
        SimpleButtonModel defaultChoiceButtonModel2 = new SimpleButtonModel();
        defaultChoiceButtonModel2.addActionCallback(new ModAction(this, "cancelDefaultCopy", new Class[0]));
        Button defaultChoiceButton2 = new Button(defaultChoiceButtonModel2);
        defaultChoiceButton2.setText("No");
        defaultChoices.add(defaultChoiceButton2);
        //**********************************************************//

        //******************** Reset All ********************//
        SimpleButtonModel simplebuttonmodel8 = new SimpleButtonModel();
        simplebuttonmodel8.addActionCallback(new ModAction(guiapiSettings, "resetAll", new Class[0]));
        Button button8 = new Button(simplebuttonmodel8);
        button8.setText("Reset to defaults");
        
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
            creatureSettingsWindow = new WidgetSimplewindow(widgetCreatureSettingsColumns, BUTTON_CREATURE_GENERAL_SETTINGS);
        }

        GuiModScreen.show(creatureSettingsWindow);
    }

    public void showCreatureSpawnSettings()
    {
        if (creatureSpawnSettingsWindow == null)
        {
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
            mobSpawnSettingsWindow = new WidgetSimplewindow(widgetMobSpawnSettingsColumns, BUTTON_MONSTER_SPAWN_SETTINGS);
        }

        GuiModScreen.show(mobSpawnSettingsWindow);
    }

    public void showMobSettings()
    {
        if (mobSettingsWindow == null)
        {
            mobSettingsWindow = new WidgetSimplewindow(widgetMobSettingsColumns, BUTTON_MONSTER_GENERAL_SETTINGS);
        }

        GuiModScreen.show(mobSettingsWindow);
    }

    // Water Mobs
    public void showWaterMobOptions()
    {
        if (waterMobOptionsWindow == null)
        {
            waterMobOptionsWindow = new WidgetSimplewindow(waterMobOptions, "Water Mob Options");
        }

        GuiModScreen.show(waterMobOptionsWindow);
    }

    public void showWaterMobSpawnSettings()
    {
        if (waterMobSpawnSettingsWindow == null)
        {
            waterMobSpawnSettingsWindow = new WidgetSimplewindow(widgetWaterMobSpawnSettingsColumns, BUTTON_WATERMOB_SPAWN_SETTINGS);
        }

        GuiModScreen.show(waterMobSpawnSettingsWindow);
    }

    public void showWaterMobSettings()
    {
        if (waterMobSettingsWindow == null)
        {
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
            ambientSpawnSettingsWindow = new WidgetSimplewindow(widgetAmbientSpawnSettingsColumns, BUTTON_AMBIENT_SPAWN_SETTINGS);
        }
        GuiModScreen.show(ambientSpawnSettingsWindow);
    }

    public void showCustomSpawnerOptions()
    {
        if (customSpawnerWindow == null)
        {
            customSpawnerWindow = new WidgetSimplewindow(widgetCustomSpawnerColumns, BUTTON_CUSTOMSPAWNER_SETTINGS);
        }

        GuiModScreen.show(customSpawnerWindow);
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
            generalSettingsWindow = new WidgetSimplewindow(widgetGeneralSettingsColumns, BUTTON_GENERAL_SETTINGS);
        }

        GuiModScreen.show(generalSettingsWindow);
    }

    public void showIDSettings()
    {
        if (IDSettingsWindow == null)
        {
            IDSettingsWindow = new WidgetSimplewindow(widgetIDSettingsColumns, BUTTON_ID_SETTINGS);
        }

        GuiModScreen.show(IDSettingsWindow);
    }
    
    public void showDefaults()
    {
        if (defaultsWindow == null)
        {
            defaultsWindow = new WidgetSimplewindow(defaultChoices, "Are you sure ? All settings will be erased.");
        }
        GuiModScreen.show(defaultsWindow);
    }

    

    

    public void showBiomeGroups()
    {
        WidgetClassicTwocolumn widgetBiomeGroupColumns = new WidgetClassicTwocolumn(new Widget[0]);
        for (Map.Entry<String, MoCBiomeGroupData> entry : biomeGroupMap.entrySet())
        {
           MoCBiomeGroupData biomeGroupData = entry.getValue();

           String biomeGroupName = entry.getKey();
           if (biomeGroupData != null)
           {
               widgetBiomeGroupColumns.add(GuiApiHelper.makeButton(biomeGroupName, new ModAction(this, "showBiomes", MoCBiomeGroupData.class).setDefaultArguments(biomeGroupData), true));
           }
        }
        biomeGroupsWindow = new WidgetSimplewindow(widgetBiomeGroupColumns);
        GuiModScreen.show(biomeGroupsWindow);
    }

    public void showBiomes(MoCBiomeGroupData biomeGroupData)
    {
        if (biomeGroupData.getBiomeGroupWindow() == null)
        {
            WidgetSinglecolumn widgetBiomesColumn = new WidgetSinglecolumn(new Widget[0]);
            if (biomeGroupData.getBiomeList() != null)
            {
                biomesList = guiapiSettings.addSetting(widgetBiomesColumn, biomeGroupData.getBiomeGroupName() + " Biomes", biomeGroupData.getBiomeGroupName() + " Biomes", (ArrayList)biomeGroupData.getBiomeList(), biomeGroupData.getBiomeGroupName().toLowerCase());
            }
            ((WidgetList) biomesList.displayWidget).listBox.setTheme("/listbox");
            widgetBiomesColumn.heightOverrideExceptions.put(biomesList.displayWidget, 140);
            WidgetSingleRow listBoxRow = new WidgetSingleRow(110, 20);
            MoCSettingText textSetting = new MoCSettingText("", "enter biome");
            listBoxRow.add(GuiApiHelper.makeButton("Add Biome", new ModAction(this,"addGroupListboxOption", MoCSettingList.class, MoCSettingText.class).setDefaultArguments(biomesList, textSetting), true));
            guiapiSettings.append(textSetting);
            listBoxRow.add(new WidgetText(textSetting, ""));
            listBoxRow.add(GuiApiHelper.makeButton("Remove Selected", new ModAction(this, "removeSelectedListboxOption", MoCSettingList.class).setDefaultArguments(biomesList), true));
            widgetBiomesColumn.add(listBoxRow);
            widgetBiomesColumn.widthOverrideExceptions.put(listBoxRow, 0);
            biomeGroupData.setBiomeGroupWindow(new WidgetSimplewindow(widgetBiomesColumn, biomeGroupData.getBiomeGroupName()));
        }
        GuiModScreen.show(biomeGroupData.getBiomeGroupWindow());
    }

    public void showEntitySettings(String entityName)
    {
        MoCEntityData entityData = entityMap.get(entityName);
        if (entityData != null)
        {
            if (entityData.getEntityWindow() == null)
            {
                WidgetSinglecolumn widgetEntitySettingColumn = new WidgetSinglecolumn(new Widget[0]);
                String category = "";
                if (entityData.getType() == EnumCreatureType.creature)
                    category = CATEGORY_MOC_CREATURE_FREQUENCIES;
                else if (entityData.getType() == EnumCreatureType.monster)
                    category = CATEGORY_MOC_MONSTER_FREQUENCIES;
                else if (entityData.getType() == EnumCreatureType.waterCreature)
                    category = CATEGORY_MOC_WATER_CREATURE_FREQUENCIES;
                else if (entityData.getType() == EnumCreatureType.ambient)
                    category = CATEGORY_MOC_AMBIENT_FREQUENCIES;
                MoCSettingInt settingFrequency = new MoCSettingInt(category, entityName + " Frequency", entityData.frequency, 0, 1, 20);
                guiapiSettings.append(settingFrequency);
                widgetEntitySettingColumn.add(new WidgetInt(settingFrequency, entityName + " Frequency"));
                MoCSettingInt settingMinGroup = new MoCSettingInt(category, entityName + " Min", entityData.minGroup, 1, 1, 20);
                guiapiSettings.append(settingMinGroup);
                widgetEntitySettingColumn.add(new WidgetInt(settingMinGroup, entityName + " Min"));
                MoCSettingInt settingMaxGroup = new MoCSettingInt(category, entityName + " Max", entityData.maxGroup, 1, 1, 20);
                guiapiSettings.append(settingMaxGroup);
                widgetEntitySettingColumn.add(new WidgetInt(settingMaxGroup, entityName + " Max"));

                if (entityData.getBiomeGroups() != null)
                {
                    entityBiomeList = guiapiSettings.addSetting(widgetEntitySettingColumn, entityName + " Biome Groups", entityName + " Biome Groups", entityData.getBiomeGroups(), CATEGORY_ENTITY_BIOME_SETTINGS);
                }

                ((WidgetList) entityBiomeList.displayWidget).listBox.setTheme("/listbox");
                widgetEntitySettingColumn.heightOverrideExceptions.put(entityBiomeList.displayWidget, 140);
                WidgetSingleRow listBoxRow = new WidgetSingleRow(110, 20);
                MoCSettingText textSetting = new MoCSettingText("", "enter biome");
                listBoxRow.add(GuiApiHelper.makeButton("Add Group", new ModAction(this,"addGroupListboxOption", MoCSettingList.class, MoCSettingText.class).setDefaultArguments(entityBiomeList, textSetting), true));
                guiapiSettings.append(textSetting);
                listBoxRow.add(new WidgetText(textSetting, ""));
                //listBoxRow.add(GuiApiHelper.makeButton("Display Selected", new ModAction(this, "showSelectedListboxOption", MoCSettingList.class).setDefaultArguments(entityBiomeList), true));
                listBoxRow.add(GuiApiHelper.makeButton("Remove Selected", new ModAction(this, "removeSelectedListboxOption", MoCSettingList.class).setDefaultArguments(entityBiomeList), true));
                widgetEntitySettingColumn.add(listBoxRow);
                widgetEntitySettingColumn.widthOverrideExceptions.put(listBoxRow, 0);
                entityData.setEntityWindow(new WidgetSimplewindow(widgetEntitySettingColumn, entityName));
            }
            this.currentSelectedEntity = entityData;
            GuiModScreen.show(entityData.getEntityWindow());
        }
    }
    
    public void showInstaSpawner()
    {
    	widgetInstaSpawnerColumn = new WidgetSinglecolumn(new Widget[0]);
    	
        ArrayList<String> moCreaturesList = new ArrayList<String>();
        for (Map.Entry<String, MoCEntityData> entityEntry : entityMap.entrySet())
        {
        	moCreaturesList.add(entityEntry.getKey()); 
        }
        entityList = guiapiSettings.addSetting(widgetInstaSpawnerColumn, "Creature Type:", "SpawnEntityList", moCreaturesList, "");
        ((WidgetList) entityList.displayWidget).listBox.setTheme("/listbox");
    	widgetInstaSpawnerColumn.heightOverrideExceptions.put(entityList.displayWidget, 130);
    	settingNumberToSpawn = guiapiSettings.addSetting(widgetInstaSpawnerColumn, "Number to Spawn", "spawnN", 3, 1, 10);
        widgetInstaSpawnerColumn.add(GuiApiHelper.makeButton("Perform Spawn", new ModAction(this, "instaSpawn", MoCSettingList.class, ArrayList.class).setDefaultArguments(entityList, moCreaturesList), true));
    	instaSpaqwnerWindow = new WidgetSimplewindow(widgetInstaSpawnerColumn, "Select the Creature to Spawn");
        GuiModScreen.show(instaSpaqwnerWindow);
    }
    
    
    @SuppressWarnings("unused")
    public void instaSpawn(MoCSettingList setting, ArrayList aList)
    {
    	ListBox<String> listbox = ((WidgetList) setting.displayWidget).listBox;
        int classID = listbox.getSelected();
        int numberToSpawn = settingNumberToSpawn.get();//guiapiSettings.getIntSettingValue("spawnN");
        String nameOfEntity = (String) aList.get(classID);
        System.out.println("Sendind command spawn " + numberToSpawn + " entities of type "  + nameOfEntity);
        try {
            MoCClientPacketHandler.sendInstaSpawnPacket(nameOfEntity, numberToSpawn);
            
        }
        catch (Exception ex)
        {
            
        }
    }
    
    public void getClassFromEntityMap(int index)
    {
    	
    }
    
    @SuppressWarnings("unused")
    private static void addGroupListboxOption(MoCSettingList setting, MoCSettingText text) {
        if (MoCreatures.proxy.debugLogging) MoCreatures.log.info("addGroupListboxOption setting backendName = " + setting.backendName + ", category = " + setting.category);
        ListBox<String> listbox = ((WidgetList) setting.displayWidget).listBox;
        int selected = listbox.getSelected();
        if (text.get() != null && text.get() != "")
        {
            String[] tokens = setting.backendName.split(" ");
            MoCProperty prop = null;
            if (setting.backendName.contains("Biome Groups"))
                prop = MoCreatures.proxy.MoCconfig.get(setting.category, tokens[0]);
            else if (setting.backendName.contains("Biomes"))
                prop = MoCreatures.proxy.MoCBiomeConfig.get(setting.category, tokens[0]);
            if (prop != null)
            {
                setting.get().add(text.get());
                prop.valueList = setting.get();
                Collections.sort(setting.get()); // sort list before displaying
                setting.displayWidget.update();
                MoCreatures.proxy.MoCBiomeConfig.save();
                MoCreatures.proxy.MoCconfig.save();
            }
        }
    }

    @SuppressWarnings("unused")
    private static void removeSelectedListboxOption(MoCSettingList setting) {
        if (MoCreatures.proxy.debugLogging) MoCreatures.log.info("removeSelectedListboxOption setting backendName = " + setting.backendName + ", category = " + setting.category);
        ListBox<String> listbox = ((WidgetList) setting.displayWidget).listBox;
        int selected = listbox.getSelected();
        if (selected == -1) {
            return;
        }

            String[] tokens = setting.backendName.split(" ");
            MoCProperty prop = null;
            if (setting.backendName.contains("Biome Groups"))
                prop = MoCreatures.proxy.MoCconfig.get(setting.category, tokens[0]);
            else if (setting.backendName.contains("Biomes"))
                prop = MoCreatures.proxy.MoCBiomeConfig.get(setting.category, tokens[0]);
            if (prop != null)
            {
                setting.get().remove(selected);
                prop.valueList = setting.get();
                setting.displayWidget.update();
                MoCreatures.proxy.MoCBiomeConfig.save();
                MoCreatures.proxy.MoCconfig.save(); // save config

                if (selected == listbox.getNumEntries()) // I'm only removing one at a
                                                            // time, so this is OK.
                {
                    selected--;
                }
                if (selected == -1) {
                    return; // This is if there aren't any entries to select left. I
                            // could also check getNumEntries to see if it's 0.
                }
        
                listbox.setSelected(selected);
            }
    }

    @SuppressWarnings("unused")
    private static void showSelectedListboxOption(MoCSettingList setting) {
        GuiModScreen.show(GuiApiHelper.makeTextDisplayAndGoBack(
                "ListBox Status", setting.displayWidget.userString(),
                "Go Back", false));
    }

    public void startDefaultCopy()
    {
        try {
        // read this file into InputStream
        InputStream is = getClass().getResourceAsStream("/drzhark/mocreatures/resources/MoCProperties.cfg");
        InputStream is2 = getClass().getResourceAsStream("/drzhark/mocreatures/resources/MoCBiomeGroups.cfg");

        // write the inputStream to a FileOutputStream
        File mocConfigFile = new File(configFile.getParent() + File.separator + "MoCProperties.cfg");
        File mocBiomeConfigFile = new File(configFile.getParent() + File.separator + "MoCBiomeGroups.cfg");
        if (mocConfigFile.exists())
        { 
            mocConfigFile.renameTo(new File(configFile.getParent() + File.separator + "MoCProperties.bkp"));
            mocConfigFile.delete();
        }
        if (mocBiomeConfigFile.exists())
        {
            mocBiomeConfigFile.renameTo(new File(configFile.getParent() + File.separator + "MoCBiomeGroups.bkp"));
            mocBiomeConfigFile.delete();
        }
        OutputStream out = new FileOutputStream(mocConfigFile);
        int read = 0;
        byte[] bytes = new byte[1024];

        while ((read = is.read(bytes)) != -1) {
           out.write(bytes, 0, read);
        }

        is.close();
        out.flush();
        out.close();
        // handle biome config
        out = new FileOutputStream(mocBiomeConfigFile);
        read = 0;
        bytes = new byte[1024];

        while ((read = is2.read(bytes)) != -1) {
           out.write(bytes, 0, read);
        }

        is2.close();
        out.flush();
        out.close();
        //System.out.println("New file created!");
        ConfigInit(configPreEvent);
        //MoCreatures.updateSettings();
        ConfigPostInit(configPostEvent);
        } catch (IOException e) {
            if (MoCreatures.proxy.debugLogging) MoCreatures.log.info(e.getMessage());
        }
        GuiModScreen.show(MoCClientProxy.instance.MoCScreen.theWidget);
    }

    public void resetGuiSettings()
    {
        instaSpawnerWindow = null;
        settingNumberToSpawn = null;
        biomesWindow = null;
        biomeGroupsWindow = null;
        customSpawnerWindow = null;
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
        MoCScreen.modScreens.clear(); // clear any existing screens
        MoCScreen = null;
        guiapiSettings = null;
    }

    public void cancelDefaultCopy()
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
        Minecraft.getMinecraft().thePlayer.addChatMessage(msg);
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