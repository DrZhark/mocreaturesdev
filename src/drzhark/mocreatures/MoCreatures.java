package drzhark.mocreatures;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityEggInfo;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.storage.MapStorage;
import net.minecraftforge.common.ConfigCategory;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Property;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.Mod.ServerAboutToStart;
import cpw.mods.fml.common.Mod.ServerStarted;
import cpw.mods.fml.common.Mod.ServerStarting;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import drzhark.customspawner.CustomSpawner;
import drzhark.mocreatures.block.MoCBlock;
import drzhark.mocreatures.block.MoCBlockDirt;
import drzhark.mocreatures.block.MoCBlockFarm;
import drzhark.mocreatures.block.MoCBlockGrass;
import drzhark.mocreatures.block.MoCBlockLeaf;
import drzhark.mocreatures.block.MoCBlockLog;
import drzhark.mocreatures.block.MoCBlockPlanks;
import drzhark.mocreatures.block.MoCBlockRock;
import drzhark.mocreatures.block.MoCBlockTallGrass;
import drzhark.mocreatures.block.MultiItemBlock;
import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.client.network.MoCClientPacketHandler;
import drzhark.mocreatures.command.CommandMoCreatures;
import drzhark.mocreatures.dimension.BiomeGenWyvernLair;
import drzhark.mocreatures.dimension.WorldProviderWyvernEnd;
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
import drzhark.mocreatures.entity.passive.MoCEntityFox;
import drzhark.mocreatures.entity.passive.MoCEntityGoat;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.entity.passive.MoCEntityKitty;
import drzhark.mocreatures.entity.passive.MoCEntityKomodo;
import drzhark.mocreatures.entity.passive.MoCEntityMouse;
import drzhark.mocreatures.entity.passive.MoCEntityOstrich;
import drzhark.mocreatures.entity.passive.MoCEntityPetScorpion;
import drzhark.mocreatures.entity.passive.MoCEntityRaccoon;
import drzhark.mocreatures.entity.passive.MoCEntitySnake;
import drzhark.mocreatures.entity.passive.MoCEntityTurkey;
import drzhark.mocreatures.entity.passive.MoCEntityTurtle;
import drzhark.mocreatures.entity.passive.MoCEntityWyvern;
import drzhark.mocreatures.item.ItemBuilderHammer;
import drzhark.mocreatures.item.ItemStaffTeleport;
import drzhark.mocreatures.item.ItemStaffPortal;
import drzhark.mocreatures.item.MoCItem;
import drzhark.mocreatures.item.MoCItemHorseAmulet;
import drzhark.mocreatures.item.MoCItemArmor;
import drzhark.mocreatures.item.MoCItemEgg;
import drzhark.mocreatures.item.MoCItemFishBowl;
import drzhark.mocreatures.item.MoCItemPetAmulet;
import drzhark.mocreatures.item.MoCItemFood;
import drzhark.mocreatures.item.MoCItemHayStack;
import drzhark.mocreatures.item.MoCItemHorseSaddle;
import drzhark.mocreatures.item.MoCItemKittyBed;
import drzhark.mocreatures.item.MoCItemLitterBox;
import drzhark.mocreatures.item.MoCItemRecord;
import drzhark.mocreatures.item.MoCItemSugarLump;
import drzhark.mocreatures.item.MoCItemTurtleSoup;
import drzhark.mocreatures.item.MoCItemWeapon;
import drzhark.mocreatures.item.MoCItemWhip;
import drzhark.mocreatures.network.MoCServerPacketHandler;

@Mod(modid = "MoCreatures", name = "DrZhark's Mo'Creatures", version = "5.2.5d1")
@NetworkMod(clientSideRequired = true, serverSideRequired = false,
clientPacketHandlerSpec = @SidedPacketHandler(channels = { "MoCreatures" }, packetHandler = MoCClientPacketHandler.class), serverPacketHandlerSpec = @SidedPacketHandler(channels = { "MoCreatures" }, packetHandler = MoCServerPacketHandler.class))
public class MoCreatures {

    @Instance("MoCreatures")
    public static MoCreatures instance;

    @SidedProxy(clientSide = "drzhark.mocreatures.client.MoCClientProxy", serverSide = "drzhark.mocreatures.MoCProxy")
    public static MoCProxy proxy;
    public static CustomSpawner myCustomSpawner;
    public static final CreativeTabs tabMoC = new CreativeTabs(CreativeTabs.creativeTabArray.length, "MoCreaturesTab");
    public MoCPetMapData mapData;

    /**
     * ITEMS
     */
    static int MoCEggID;// = 7772;
    static int MoCItemID;// = 8772;
    static int MoCEntityID = 7256; // used internally, does not need to be configured by users
    public static int WyvernLairDimensionID; //17;

    public static Block mocStone;
    public static Block mocGrass;
    public static Block mocDirt;
    public static Block mocLeaf;
    public static Block mocLog;
    public static Block mocPlank;
    public static Block mocTallGrass;

    public static ArrayList<String> multiBlockNames = new ArrayList<String>();
    public static BiomeGenBase WyvernLairBiome;
    public static Item staffPortal;
    public static Item staffTeleport;
    public static Item builderHammer;

    static EnumArmorMaterial crocARMOR = EnumHelper.addArmorMaterial("crocARMOR", 15, new int[] { 2, 6, 5, 2 }, 12);
    static EnumArmorMaterial furARMOR = EnumHelper.addArmorMaterial("furARMOR", 15, new int[] { 2, 6, 5, 2 }, 12);
    static EnumArmorMaterial hideARMOR = EnumHelper.addArmorMaterial("hideARMOR", 15, new int[] { 2, 6, 5, 2 }, 12);
    static EnumArmorMaterial scorpdARMOR = EnumHelper.addArmorMaterial("scorpdARMOR", 15, new int[] { 2, 6, 5, 2 }, 12);
    static EnumArmorMaterial scorpfARMOR = EnumHelper.addArmorMaterial("scorpfARMOR", 18, new int[] { 2, 7, 6, 2 }, 12);
    static EnumArmorMaterial scorpnARMOR = EnumHelper.addArmorMaterial("scorpnARMOR", 20, new int[] { 3, 7, 6, 3 }, 15);
    static EnumArmorMaterial scorpcARMOR = EnumHelper.addArmorMaterial("scorpcARMOR", 15, new int[] { 2, 6, 5, 2 }, 12);
    static EnumArmorMaterial silverARMOR = EnumHelper.addArmorMaterial("silverARMOR", 15, new int[] { 2, 6, 5, 2 }, 15);
    static EnumToolMaterial SILVER = EnumHelper.addToolMaterial("SILVER", 0, 250, 6.0F, 2, 15);

    public static Item horsesaddle;
    public static Item horsearmormetal;
    public static Item sharkteeth;
    public static Item haystack;
    public static Item sugarlump;
    public static Item fishyegg;
    public static Item bigcatclaw;
    public static Item whip;
    public static Item horsearmorgold;
    public static Item horsearmordiamond;
    public static Item staffunicorn;
    public static Item medallion;
    public static Item kittybed;
    public static Item litterbox;
    public static Item woolball;
    public static Item rope;
    public static Item petfood;
    public static Item staffdiamond;
    public static Item crochide;
    public static Item plateCroc;
    public static Item helmetCroc;
    public static Item legsCroc;
    public static Item bootsCroc;
    public static Item fishbowl_e;
    public static Item fishbowl_w;
    public static Item fishbowl_1;
    public static Item fishbowl_2;
    public static Item fishbowl_3;
    public static Item fishbowl_4;
    public static Item fishbowl_5;
    public static Item fishbowl_6;
    public static Item fishbowl_7;
    public static Item fishbowl_8;
    public static Item fishbowl_9;
    public static Item fishbowl_10;

    public static Item fur;
    public static Item omelet;
    public static Item turtlemeat;
    public static Item turtlesoup;

    public static Item nunchaku;
    public static Item sai;
    public static Item bo;
    public static Item katana;
    public static Item sharksword;
    public static Item swordsilver;

    public static Item key;
    public static Item vialdarkness;
    public static Item vialnightmare;
    public static Item amuletbone;
    public static Item amuletbonefull;
    public static Item amuletghost;
    public static Item amuletghostfull;
    public static Item amuletfairy;
    public static Item amuletfairyfull;
    public static Item amuletpegasus;
    public static Item amuletpegasusfull;

    public static Item vialundead;
    public static Item viallight;

    public static Item plateFur;
    public static Item helmetFur;
    public static Item legsFur;
    public static Item bootsFur;

    public static Item heartdarkness;
    public static Item heartfire;
    public static Item heartundead;
    public static Item ostrichmeat;
    public static Item ostrichcooked;
    public static Item unicorn;
    public static Item staff;
    public static Item staffender;
    public static Item horsearmorcrystal;
    public static Item recordshuffle;

    public static Item animalHide;
    public static Item rawTurkey;
    public static Item cookedTurkey;
    public static Item plateHide;
    public static Item helmetHide;
    public static Item legsHide;
    public static Item bootsHide;
    public static Item ratRaw;
    public static Item ratCooked;
    public static Item ratBurger;

    public static Item chitinCave;
    public static Item chitinFrost;
    public static Item chitinNether;
    public static Item chitinDirt;

    public static Item swordDirt;
    public static Item swordFrost;
    public static Item swordCave;
    public static Item swordNether;

    public static Item plateScorpDirt;
    public static Item helmetScorpDirt;
    public static Item legsScorpDirt;
    public static Item bootsScorpDirt;
    public static Item plateScorpFrost;
    public static Item helmetScorpFrost;
    public static Item legsScorpFrost;
    public static Item bootsScorpFrost;
    public static Item plateScorpNether;
    public static Item helmetScorpNether;
    public static Item legsScorpNether;
    public static Item bootsScorpNether;
    public static Item helmetScorpCave;
    public static Item plateScorpCave;
    public static Item legsScorpCave;
    public static Item bootsScorpCave;

    public static Item stingDirt;
    public static Item stingFrost;
    public static Item stingCave;
    public static Item stingNether;

    public static Item woodTusks;
    public static Item ironTusks;
    public static Item diamondTusks;
    public static Item chestSet;
    public static Item garment;
    public static Item harness;
    public static Item howdah;
    public static Item platform;
    
    public static Item scrollFreedom;
    public static Item scrollOfSale;
    public static Item scrollOfOwner;
    public static Item crabmeat;
    public static Item crabmeatcooked;

    public static Item fishnet;
    public static Item superAmulet;

    public static Logger log;
    public static MoCPlayerTracker tracker;

    @PreInit
    public void preInit(FMLPreInitializationEvent event)
    {
        MinecraftForge.TERRAIN_GEN_BUS.register(new MoCEventHooks()); // register our event subscriptions
        MinecraftForge.EVENT_BUS.register(new MoCEventHooks());
        log = event.getModLog();
        proxy.ConfigInit(event);
        proxy.initSounds();
        tracker = new MoCPlayerTracker();
        GameRegistry.registerPlayerTracker(tracker);
    }

    //how to check for client: if(FMLCommonHandler.instance().getSide().isClient())

    @Init
    public void load(FMLInitializationEvent event)
    {
        this.InitItems();
        this.AddNames();
        this.AddRecipes();
        this.AddEntities();
        proxy.registerRenderers();
        proxy.registerRenderInformation();
        TickRegistry.registerTickHandler(new MoCServerTickHandler(), Side.SERVER);
        DimensionManager.registerProviderType(WyvernLairDimensionID, WorldProviderWyvernEnd.class, true);
        this.WyvernLairBiome = new BiomeGenWyvernLair(MoCreatures.proxy.WyvernBiomeID);
    }

    private void AddEntities()
    {
        registerEntity(MoCEntityBunny.class, "Bunny", 12623485, 9141102, proxy.useGlobalEntityRegistration);//, 0x05600, 0x006500);
        registerEntity(MoCEntitySnake.class, "Snake", 14020607, 13749760, proxy.useGlobalEntityRegistration);//, 0x05800, 0x006800);
        registerEntity(MoCEntityTurtle.class, "Turtle", 14772545, 9320590, proxy.useGlobalEntityRegistration);//, 0x04800, 0x004500);
        registerEntity(MoCEntityBird.class, "Bird", 14020607, 14020607, proxy.useGlobalEntityRegistration);// 0x03600, 0x003500);
        registerEntity(MoCEntityMouse.class, "Mouse", 14772545, 0, proxy.useGlobalEntityRegistration);//, 0x02600, 0x002500);
        registerEntity(MoCEntityTurkey.class, "Turkey", 14020607, 16711680, proxy.useGlobalEntityRegistration);//, 0x2600, 0x052500);
        registerEntity(MoCEntityHorse.class, "Horse", 12623485, 15656192, proxy.useGlobalEntityRegistration);//, 0x2600, 0x052500);
        registerEntity(MoCEntityHorseMob.class, "HorseMob", 16711680, 9320590, false);//, 0x2600, 0x052500);
        registerEntity(MoCEntityOgre.class, "Ogre", 16711680, 65407, false);//, 0x2600, 0x052500);
        registerEntity(MoCEntityBoar.class, "Boar", 14772545, 9141102, false);//, 0x2600, 0x052500);
        registerEntity(MoCEntityBear.class, "Bear", 14772545, 1, proxy.useGlobalEntityRegistration);//, 0x2600, 0x052500);
        registerEntity(MoCEntityDuck.class, "Duck", 14772545, 15656192, false);//, 0x2600, 0x052500);
        registerEntity(MoCEntityBigCat.class, "BigCat", 12623485, 16622, proxy.useGlobalEntityRegistration);//, 0x2600, 0x052500);
        registerEntity(MoCEntityDeer.class, "Deer", 14772545, 33023, false);//, 0x2600, 0x052500);
        registerEntity(MoCEntityWWolf.class, "WWolf", 16711680, 13749760, false);//, 0x2600, 0x052500);
        registerEntity(MoCEntityWraith.class, "Wraith", 16711680, 0, false);//, 0x2600, 0x052500);
        registerEntity(MoCEntityFlameWraith.class, "FlameWraith", 16711680, 12623485, false);//, 0x2600, 0x052500);
        registerEntity(MoCEntityFox.class, "Fox", 14772545, 5253242, proxy.useGlobalEntityRegistration);//, 0x2600, 0x052500);
        registerEntity(MoCEntityWerewolf.class, "Werewolf", 16711680, 7434694, false);//, 0x2600, 0x052500);
        registerEntity(MoCEntityShark.class, "Shark", 33023, 9013643, proxy.useGlobalEntityRegistration);//, 0x2600, 0x052500);
        registerEntity(MoCEntityDolphin.class, "Dolphin", 33023, 15631086, proxy.useGlobalEntityRegistration);//, 0x2600, 0x052500);
        registerEntity(MoCEntityFishy.class, "Fishy", 33023, 65407, false);//, 0x2600, 0x052500);
        registerEntity(MoCEntityKitty.class, "Kitty", 12623485, 5253242, proxy.useGlobalEntityRegistration);//, 0x2600, 0x052500);
        registerEntity(MoCEntityKittyBed.class, "KittyBed", proxy.useGlobalEntityRegistration);
        registerEntity(MoCEntityLitterBox.class, "LitterBox", proxy.useGlobalEntityRegistration);
        registerEntity(MoCEntityRat.class, "Rat", 12623485, 9141102, false);//, 0x2600, 0x052500);
        registerEntity(MoCEntityHellRat.class, "HellRat", 16711680, 14772545, false);//, 0x2600, 0x052500);
        registerEntity(MoCEntityScorpion.class, "Scorpion", 16711680, 6053069, false);//, 0x2600, 0x052500);
        registerEntity(MoCEntityCrocodile.class, "Crocodile", 16711680, 65407, false);//, 0x2600, 0x052500);
        registerEntity(MoCEntityRay.class, "Ray", 33023, 9141102, false);//14772545, 9141102);
        registerEntity(MoCEntityJellyFish.class, "JellyFish", 33023, 14772545, false);//, 0x2600, 0x052500);
        registerEntity(MoCEntityGoat.class, "Goat", 7434694, 6053069, proxy.useGlobalEntityRegistration);//, 0x2600, 0x052500);
        registerEntity(MoCEntityEgg.class, "Egg", proxy.useGlobalEntityRegistration);//, 0x2600, 0x052500);
        registerEntity(MoCEntityFishBowl.class, "FishBowl", proxy.useGlobalEntityRegistration);//, 0x2600, 0x052500);
        registerEntity(MoCEntityOstrich.class, "Ostrich", 14020607, 9639167, proxy.useGlobalEntityRegistration);//, 0x2600, 0x052500);
        registerEntity(MoCEntityBee.class, "Bee", 65407, 15656192, false);//, 0x2600, 0x052500);
        registerEntity(MoCEntityFly.class, "Fly", 65407, 1, false);//, 0x2600, 0x052500);
        registerEntity(MoCEntityDragonfly.class, "DragonFly", 65407, 14020607, false);//, 0x2600, 0x052500);
        registerEntity(MoCEntityFirefly.class, "Firefly", 65407, 9320590, false);//, 0x2600, 0x052500);
        registerEntity(MoCEntityCricket.class, "Cricket", 65407, 16622, false);//, 0x2600, 0x052500);
        registerEntity(MoCEntitySnail.class, "Snail", 65407, 14772545, false);//, 0x2600, 0x052500);
        registerEntity(MoCEntityButterfly.class, "ButterFly", 65407, 7434694, false);//, 0x22600, 0x012500);
        registerEntity(MoCEntityThrowableRock.class, "TRock", false);
        registerEntity(MoCEntityGolem.class, "BigGolem", 16711680, 16622, false);
        registerEntity(MoCEntityPetScorpion.class, "PetScorpion", proxy.useGlobalEntityRegistration);
        registerEntity(MoCEntityPlatform.class, "MoCPlatform", false);
        registerEntity(MoCEntityElephant.class, "Elephant", 14772545, 23423, proxy.useGlobalEntityRegistration);
        registerEntity(MoCEntityKomodo.class, "KomodoDragon", 16711680, 23423, proxy.useGlobalEntityRegistration);
        registerEntity(MoCEntityWyvern.class, "Wyvern", 14772545, 65407, proxy.useGlobalEntityRegistration);
        registerEntity(MoCEntityRoach.class, "Roach", 65407, 13749760, false);
        registerEntity(MoCEntityMaggot.class, "Maggot", 65407, 9141102, false);
        registerEntity(MoCEntityCrab.class, "Crab", 65407, 13749760, false);
        registerEntity(MoCEntityRaccoon.class, "Raccoon", 14772545, 13749760, false);
        registerEntity(MoCEntityMiniGolem.class, "MiniGolem", 16711680, 13749760, false);
        registerEntity(MoCEntitySilverSkeleton.class, "SilverSkeleton", 16711680, 33023, false);
        registerEntity(MoCEntityAnt.class, "Ant", 65407, 12623485, false);
        registerEntity(MoCEntityMediumFish.class, "MediumFish", 33023, 16622, false);
        registerEntity(MoCEntitySmallFish.class, "SmallFish", 33023, 65407, false);
        registerEntity(MoCEntityPiranha.class, "Piranha", 33023, 16711680, false);
        
        /**
         * fucsia 16711680 orange curuba 14772545 gris claro 9141102 gris medio
         * 9013643 rosado 15631086 rosado claro 12623485 azul oscuro 2037680
         * azul mas oscuro 205 amarillo 15656192 marron claro 13749760
         * 
         * verde claro esmeralda 65407 azul oscuro 30091 azul oscuro 2 2372490
         * blanco azulado 14020607 azul oscuro 16622 marron claro rosado
         * 12623485 azul bse huevos acuaticos 5665535 azul brillane 33023 morado
         * fucsia 9320590 lila 7434694 morado lila 6053069
         */
    }

    @PostInit
    public void postInit(FMLPostInitializationEvent event)
    {
        DimensionManager.registerDimension(WyvernLairDimensionID, WyvernLairDimensionID);
        if (proxy.useCustomSpawner)
        {
            proxy.resetAllData();
            myCustomSpawner = new CustomSpawner();
            if (proxy.debugCMS)
            {
                myCustomSpawner.setVerboseConsole(true);
            }
            proxy.ConfigPostInit(event);
            updateSettings(); // refresh settings
        }
        else proxy.ConfigPostInit(event);
    }

    // CustomSpawner must be initialized here to avoid vanilla spawn lists being populated during world gen
    @ServerAboutToStart
    public void serverAboutToStart(FMLServerAboutToStartEvent event)
    {
        if (proxy.useCustomSpawner)
        {
            proxy.resetAllData();
            if (myCustomSpawner == null)
                myCustomSpawner = new CustomSpawner();
            else myCustomSpawner.resetCMS();
            if (proxy.debugCMS)
            {
                myCustomSpawner.setVerboseConsole(true);
            }
            proxy.initializeBiomes();
            proxy.initializeEntities();
            MoCreatures.proxy.initGUI();
            updateSettings(); // refresh settings
        }
    }

    @ServerStarting
    public void serverStarting(FMLServerStartingEvent event)
    {
        if (DimensionManager.getWorld(0) != null) // if overworld has loaded, use its mapstorage
        {
            MoCPetMapData data = (MoCPetMapData)DimensionManager.getWorld(0).mapStorage.loadData(MoCPetMapData.class, "mocreatures");
            System.out.println("data = " + data);
            if (data == null)
            {
                System.out.println("MOCPETMAPDATA IS NULL!!! creating NEW FILE");
                data = new MoCPetMapData("mocreatures");
            }
            System.out.println("LOADING MOCPETMAPDATA");
            DimensionManager.getWorld(0).mapStorage.setData("mocreatures", data);
            DimensionManager.getWorld(0).mapStorage.saveAllData();
            this.mapData = data;
            MoCreatures.proxy.worldInitDone = true;
        }
        BiomeGenBase[] allBiomes = new BiomeGenBase[proxy.biomeMap.size()];
        List<BiomeGenBase> biomeList = new ArrayList<BiomeGenBase>();
        for (int j = 0; j < BiomeGenBase.biomeList.length; j++)
        {
            if (BiomeGenBase.biomeList[j] != null)
            {
                biomeList.add(BiomeGenBase.biomeList[j]);
            }
        }
        if (biomeList.size() > 0)
        {
           // if (proxy.debugLogging) log.info("Removing entity " + entityData.getEntityClass() + " with type " + entityData.getType() + " from all biome spawnlists.");
            allBiomes = biomeList.toArray(allBiomes);
            myCustomSpawner.copyVanillaSpawnLists(allBiomes);
        }
        event.registerServerCommand(new CommandMoCreatures());
    }

    @ServerStarted
    public void serverStarted(FMLServerStartedEvent event)
    {
    }

    /**
     * For Litterbox and kittybeds
     * 
     * @param entityClass
     * @param entityName
     */
    protected void registerEntity(Class<? extends Entity> entityClass, String entityName, boolean useGlobalID)
    {
        if (useGlobalID)
        {
            LanguageRegistry.instance().addStringLocalization("entity." + entityName + ".name", "en_US", entityName);
            int entityID = EntityRegistry.findGlobalUniqueEntityId();
            if (proxy.debugLogging) log.info("registerEntity " + entityClass + " with Global ID " + entityID);
            EntityRegistry.registerGlobalEntityID(entityClass, entityName, entityID);
            EntityRegistry.registerModEntity(entityClass, entityName, entityID, instance, 128, 1, true);
        }
        else
        {
            LanguageRegistry.instance().addStringLocalization("entity.MoCreatures." + entityName + ".name", "en_US", entityName);
            EntityRegistry.registerModEntity(entityClass, entityName, MoCEntityID, instance, 128, 1, true);
            MoCEntityID++;
        }        
    }

    private void registerEntity(Class<? extends Entity> entityClass, String entityName, int eggColor, int eggDotsColor, String visibleName, boolean useGlobalID)
    {
        if (useGlobalID)
        {
            int entityID = EntityRegistry.findGlobalUniqueEntityId();
            if (proxy.debugLogging) log.info("registerEntity " + entityClass + " with Global ID " + entityID);
            EntityRegistry.registerGlobalEntityID(entityClass, entityName, entityID, eggColor, eggDotsColor);
            LanguageRegistry.instance().addStringLocalization("entity." + entityName + ".name", "en_US", visibleName);
            EntityRegistry.registerModEntity(entityClass, entityName, entityID, instance, 128, 1, true);
        }
        else
        {
            LanguageRegistry.instance().addStringLocalization("entity.MoCreatures." + entityName + ".name", "en_US", visibleName);
            if (proxy.debugLogging) log.info("registerEntity " + entityClass + " with Mod ID " + MoCEntityID);
            EntityRegistry.registerModEntity(entityClass, entityName, MoCEntityID, instance, 128, 1, true);
            EntityList.IDtoClassMapping.put(MoCEntityID, entityClass);
            EntityList.entityEggs.put(Integer.valueOf(MoCEntityID), new EntityEggInfo(MoCEntityID, eggColor, eggDotsColor));
            MoCEntityID++;
        }
    }

    private void registerEntity(Class<? extends Entity> entityClass, String entityName, int eggColor, int eggDotsColor, boolean useGlobalID)
    {
        registerEntity(entityClass, entityName, eggColor, eggDotsColor, entityName, useGlobalID);
    }

    protected void InitItems()
    {
        MoCItemID = proxy.itemID;//8772
        WyvernLairDimensionID = proxy.WyvernDimension;//17

        recordshuffle = (new MoCItemRecord(MoCItemID++, "shuffling")).setUnlocalizedName("recordshuffle");
        horsesaddle = (new MoCItemHorseSaddle(MoCItemID++)).setUnlocalizedName("horsesaddle");
        horsearmormetal = (new MoCItem(MoCItemID++)).setUnlocalizedName("armormetal");
        sharkteeth = (new MoCItem(MoCItemID++)).setUnlocalizedName("sharkteeth");
        haystack = (new MoCItemHayStack(MoCItemID++)).setUnlocalizedName("haystack");
        sugarlump = (new MoCItemSugarLump(MoCItemID++)).setUnlocalizedName("sugarlump");
        fishyegg = (new MoCItemEgg(MoCItemID++)).setUnlocalizedName("moc_egg");
        bigcatclaw = (new MoCItem(MoCItemID++)).setUnlocalizedName("bigcatclaw");
        whip = (new MoCItemWhip(MoCItemID++)).setUnlocalizedName("whip");
        horsearmorgold = (new MoCItem(MoCItemID++)).setUnlocalizedName("armorgold");
        horsearmordiamond = (new MoCItem(MoCItemID++)).setUnlocalizedName("armordiamond");
        staffTeleport = (new ItemStaffTeleport(MoCItemID++)).setUnlocalizedName("staffteleport");
        LanguageRegistry.addName(staffTeleport, "Teleport Staff");

        medallion = (new MoCItem(MoCItemID++)).setUnlocalizedName("medallion");
        kittybed = (new MoCItemKittyBed(MoCItemID++, 0)).setUnlocalizedName("catbed");
        litterbox = (new MoCItemLitterBox(MoCItemID++)).setUnlocalizedName("catlitter");
        woolball = (new MoCItem(MoCItemID++)).setUnlocalizedName("woolball");
        rope = (new MoCItem(MoCItemID++)).setUnlocalizedName("rope");
        petfood = (new MoCItem(MoCItemID++)).setUnlocalizedName("petfood");
        builderHammer = (new ItemBuilderHammer(MoCItemID++)).setUnlocalizedName("builderhammer");
        LanguageRegistry.addName(builderHammer, "Builder Hammer");

        crochide = (new MoCItem(MoCItemID++)).setUnlocalizedName("reptilehide");
        plateCroc = (new MoCItemArmor(MoCItemID++, crocARMOR, 4, 1)).setUnlocalizedName("reptileplate");
        helmetCroc = (new MoCItemArmor(MoCItemID++, crocARMOR, 4, 0)).setUnlocalizedName("reptilehelmet");
        legsCroc = (new MoCItemArmor(MoCItemID++, crocARMOR, 4, 2)).setUnlocalizedName("reptilelegs");
        bootsCroc = (new MoCItemArmor(MoCItemID++, crocARMOR, 4, 3)).setUnlocalizedName("reptileboots");
        fishbowl_e = (new MoCItemFishBowl(MoCItemID++, 0)).setUnlocalizedName("bowlempty");
        fishbowl_w = (new MoCItemFishBowl(MoCItemID++, 11)).setUnlocalizedName("bowlwater");
        fishbowl_1 = (new MoCItemFishBowl(MoCItemID++, 1)).setUnlocalizedName("bowlfish1");
        fishbowl_2 = (new MoCItemFishBowl(MoCItemID++, 2)).setUnlocalizedName("bowlfish2");
        fishbowl_3 = (new MoCItemFishBowl(MoCItemID++, 3)).setUnlocalizedName("bowlfish3");
        fishbowl_4 = (new MoCItemFishBowl(MoCItemID++, 4)).setUnlocalizedName("bowlfish4");
        fishbowl_5 = (new MoCItemFishBowl(MoCItemID++, 5)).setUnlocalizedName("bowlfish5");
        fishbowl_6 = (new MoCItemFishBowl(MoCItemID++, 6)).setUnlocalizedName("bowlfish6");
        fishbowl_7 = (new MoCItemFishBowl(MoCItemID++, 7)).setUnlocalizedName("bowlfish7");
        fishbowl_8 = (new MoCItemFishBowl(MoCItemID++, 8)).setUnlocalizedName("bowlfish8");
        fishbowl_9 = (new MoCItemFishBowl(MoCItemID++, 9)).setUnlocalizedName("bowlfish9");
        fishbowl_10 = (new MoCItemFishBowl(MoCItemID++, 10)).setUnlocalizedName("bowlfish10");

        fur = (new MoCItem(MoCItemID++)).setUnlocalizedName("fur");
        omelet = (new MoCItemFood(MoCItemID++, 4, 0.6F, false)).setUnlocalizedName("omelet");
        turtlemeat = (new MoCItemFood(MoCItemID++, 2, 0.3F, false)).setUnlocalizedName("turtlemeat");
        turtlesoup = (new MoCItemTurtleSoup(MoCItemID++, 6, 0.6F, false)).setUnlocalizedName("turtlesoup");

        nunchaku = (new MoCItemWeapon(MoCItemID++, EnumToolMaterial.IRON)).setUnlocalizedName("nunchaku");
        sai = (new MoCItemWeapon(MoCItemID++, EnumToolMaterial.IRON)).setUnlocalizedName("sai");
        bo = (new MoCItemWeapon(MoCItemID++, EnumToolMaterial.IRON)).setUnlocalizedName("bo");
        katana = (new MoCItemWeapon(MoCItemID++, EnumToolMaterial.IRON)).setUnlocalizedName("katana");
        sharksword = (new MoCItemWeapon(MoCItemID++, EnumToolMaterial.IRON)).setUnlocalizedName("swordshark");

        key = (new MoCItem(MoCItemID++)).setUnlocalizedName("key");
        vialdarkness = (new MoCItem(MoCItemID++)).setUnlocalizedName("essencedarkness");
        vialnightmare = (new MoCItem(MoCItemID++)).setUnlocalizedName("essencefire");
        amuletbone = (new MoCItemHorseAmulet(MoCItemID++)).setUnlocalizedName("amuletbone");
        amuletbonefull = (new MoCItemHorseAmulet(MoCItemID++)).setUnlocalizedName("amuletbonefull");
        amuletghost = (new MoCItemHorseAmulet(MoCItemID++)).setUnlocalizedName("amuletghost");
        amuletghostfull = (new MoCItemHorseAmulet(MoCItemID++)).setUnlocalizedName("amuletghostfull");
        amuletfairy = (new MoCItemHorseAmulet(MoCItemID++)).setUnlocalizedName("amuletfairy");
        amuletfairyfull = (new MoCItemHorseAmulet(MoCItemID++)).setUnlocalizedName("amuletfairyfull");
        amuletpegasus = (new MoCItemHorseAmulet(MoCItemID++)).setUnlocalizedName("amuletpegasus");
        amuletpegasusfull = (new MoCItemHorseAmulet(MoCItemID++)).setUnlocalizedName("amuletpegasusfull");

        vialundead = (new MoCItem(MoCItemID++)).setUnlocalizedName("essenceundead");
        viallight = (new MoCItem(MoCItemID++)).setUnlocalizedName("essencelight");

        plateFur = (new MoCItemArmor(MoCItemID++, furARMOR, 4, 1)).setUnlocalizedName("furplate");
        helmetFur = (new MoCItemArmor(MoCItemID++, furARMOR, 4, 0)).setUnlocalizedName("furhelmet");
        legsFur = (new MoCItemArmor(MoCItemID++, furARMOR, 4, 2)).setUnlocalizedName("furlegs");
        bootsFur = (new MoCItemArmor(MoCItemID++, furARMOR, 4, 3)).setUnlocalizedName("furboots");

        heartdarkness = (new MoCItem(MoCItemID++)).setUnlocalizedName("heartdarkness");
        heartfire = (new MoCItem(MoCItemID++)).setUnlocalizedName("heartfire");
        heartundead = (new MoCItem(MoCItemID++)).setUnlocalizedName("heartundead");
        ostrichmeat = (new MoCItemFood(MoCItemID++, 2, 0.3F, false)).setPotionEffect(Potion.hunger.id, 30, 0, 0.8F).setUnlocalizedName("ostrichraw");
        ostrichcooked = (new MoCItemFood(MoCItemID++, 6, 0.6F, false)).setUnlocalizedName("ostrichcooked");
        unicorn = (new MoCItem(MoCItemID++)).setUnlocalizedName("unicorn");

        fishnet = (new MoCItemPetAmulet(MoCItemID++)).setUnlocalizedName("fishnet");
        MoCItemID++;
        horsearmorcrystal = (new MoCItem(MoCItemID++)).setUnlocalizedName("armorcrystal");
        MoCItemID++;

        rawTurkey = (new MoCItemFood(MoCItemID++, 3, 0.3F, false)).setPotionEffect(Potion.hunger.id, 30, 0, 0.8F).setUnlocalizedName("turkeyraw");
        cookedTurkey = (new MoCItemFood(MoCItemID++, 8, 0.6F, false)).setUnlocalizedName("turkeycooked");
        animalHide = (new MoCItem(MoCItemID++)).setUnlocalizedName("hide");
        plateHide = (new MoCItemArmor(MoCItemID++, hideARMOR, 4, 1)).setUnlocalizedName("hideplate");
        helmetHide = (new MoCItemArmor(MoCItemID++, hideARMOR, 4, 0)).setUnlocalizedName("hidehelmet");
        legsHide = (new MoCItemArmor(MoCItemID++, hideARMOR, 4, 2)).setUnlocalizedName("hidelegs");
        bootsHide = (new MoCItemArmor(MoCItemID++, hideARMOR, 4, 3)).setUnlocalizedName("hideboots");
        ratRaw = (new MoCItemFood(MoCItemID++, 2, 0.3F, false)).setPotionEffect(Potion.hunger.id, 30, 0, 0.8F).setUnlocalizedName("ratraw");
        ratCooked = (new MoCItemFood(MoCItemID++, 4, 0.6F, false)).setUnlocalizedName("ratcooked");
        ratBurger = (new MoCItemFood(MoCItemID++, 8, 0.6F, false)).setUnlocalizedName("ratburger");

        chitinCave = (new MoCItem(MoCItemID++)).setUnlocalizedName("chitinblack");
        chitinFrost = (new MoCItem(MoCItemID++)).setUnlocalizedName("chitinfrost");
        chitinNether = (new MoCItem(MoCItemID++)).setUnlocalizedName("chitinnether");
        chitinDirt = (new MoCItem(MoCItemID++)).setUnlocalizedName("chitin");

        swordCave = (new MoCItemWeapon(MoCItemID++, EnumToolMaterial.IRON, 4, false)).setUnlocalizedName("swordscorpioncave");
        swordFrost = (new MoCItemWeapon(MoCItemID++, EnumToolMaterial.IRON, 2, false)).setUnlocalizedName("swordscorpionfrost");
        swordNether = (new MoCItemWeapon(MoCItemID++, EnumToolMaterial.IRON, 3, false)).setUnlocalizedName("swordscorpionnether");
        swordDirt = (new MoCItemWeapon(MoCItemID++, EnumToolMaterial.IRON, 1, false)).setUnlocalizedName("swordscorpion");

        plateScorpDirt = (new MoCItemArmor(MoCItemID++, crocARMOR, 4, 1)).setUnlocalizedName("plate");
        helmetScorpDirt = (new MoCItemArmor(MoCItemID++, crocARMOR, 4, 0)).setUnlocalizedName("helmet");
        legsScorpDirt = (new MoCItemArmor(MoCItemID++, crocARMOR, 4, 2)).setUnlocalizedName("legs");
        bootsScorpDirt = (new MoCItemArmor(MoCItemID++, crocARMOR, 4, 3)).setUnlocalizedName("boots");

        plateScorpFrost = (new MoCItemArmor(MoCItemID++, crocARMOR, 4, 1)).setUnlocalizedName("platefrost");
        helmetScorpFrost = (new MoCItemArmor(MoCItemID++, crocARMOR, 4, 0)).setUnlocalizedName("helmetfrost");
        legsScorpFrost = (new MoCItemArmor(MoCItemID++, crocARMOR, 4, 2)).setUnlocalizedName("legsfrost");
        bootsScorpFrost = (new MoCItemArmor(MoCItemID++, crocARMOR, 4, 3)).setUnlocalizedName("bootsfrost");

        plateScorpCave = (new MoCItemArmor(MoCItemID++, crocARMOR, 4, 1)).setUnlocalizedName("plateblack");
        helmetScorpCave = (new MoCItemArmor(MoCItemID++, crocARMOR, 4, 0)).setUnlocalizedName("helmetblack");
        legsScorpCave = (new MoCItemArmor(MoCItemID++, crocARMOR, 4, 2)).setUnlocalizedName("legsblack");
        bootsScorpCave = (new MoCItemArmor(MoCItemID++, crocARMOR, 4, 3)).setUnlocalizedName("bootsblack");

        plateScorpNether = (new MoCItemArmor(MoCItemID++, crocARMOR, 4, 1)).setUnlocalizedName("platenether");
        helmetScorpNether = (new MoCItemArmor(MoCItemID++, crocARMOR, 4, 0)).setUnlocalizedName("helmetnether");
        legsScorpNether = (new MoCItemArmor(MoCItemID++, crocARMOR, 4, 2)).setUnlocalizedName("legsnether");
        bootsScorpNether = (new MoCItemArmor(MoCItemID++, crocARMOR, 4, 3)).setUnlocalizedName("bootsnether");

        stingCave = (new MoCItemWeapon(MoCItemID++, EnumToolMaterial.GOLD, 4, true)).setUnlocalizedName("stingcave");
        stingFrost = (new MoCItemWeapon(MoCItemID++, EnumToolMaterial.GOLD, 2, true)).setUnlocalizedName("stingfrost");
        stingNether = (new MoCItemWeapon(MoCItemID++, EnumToolMaterial.GOLD, 3, true)).setUnlocalizedName("stingnether");
        stingDirt = (new MoCItemWeapon(MoCItemID++, EnumToolMaterial.GOLD, 1, true)).setUnlocalizedName("stingdirt");

        scrollFreedom = (new MoCItem(MoCItemID++)).setUnlocalizedName("scrolloffreedom");
        scrollOfSale = (new MoCItem(MoCItemID++)).setUnlocalizedName("scrollofsale");
        
        woodTusks = (new MoCItemWeapon(MoCItemID++, EnumToolMaterial.WOOD)).setUnlocalizedName("tuskswood");
        ironTusks = (new MoCItemWeapon(MoCItemID++, EnumToolMaterial.IRON)).setUnlocalizedName("tusksiron");
        diamondTusks = (new MoCItemWeapon(MoCItemID++, EnumToolMaterial.EMERALD)).setUnlocalizedName("tusksdiamond");
        harness = (new MoCItem(MoCItemID++)).setUnlocalizedName("harness");
        chestSet = (new MoCItem(MoCItemID++)).setUnlocalizedName("chestset");
        garment = (new MoCItem(MoCItemID++)).setUnlocalizedName("garment");
        howdah = (new MoCItem(MoCItemID++)).setUnlocalizedName("howdah");
        platform = (new MoCItem(MoCItemID++)).setUnlocalizedName("platform");
        
        crabmeat = (new MoCItemFood(MoCItemID++, 2, 0.3F, false)).setPotionEffect(Potion.hunger.id, 30, 0, 0.8F).setUnlocalizedName("crabmeat");
        crabmeatcooked = (new MoCItemFood(MoCItemID++, 6, 0.6F, false)).setUnlocalizedName("crabmeatcooked");
        swordsilver = (new MoCItemWeapon(MoCItemID++, this.SILVER)).setUnlocalizedName("swordsilver");
        
        multiBlockNames.add ("WyvernLair");
        multiBlockNames.add("OgreLair");
        
        staffPortal = (new ItemStaffPortal(MoCItemID++)).setUnlocalizedName("staffportal");
        LanguageRegistry.addName(staffPortal, "Wyvern Portal Staff");
        
        scrollOfOwner = (new MoCItem(MoCItemID++)).setUnlocalizedName("scrollofowner");
        
        superAmulet = (new MoCItemPetAmulet(MoCItemID++, 1)).setUnlocalizedName("superamulet");

        //new blocks
        mocStone = new MoCBlockRock(proxy.blockStoneID).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("MoCStone");
        GameRegistry.registerBlock(mocStone, MultiItemBlock.class, "MoC_Stone");

        mocGrass = new MoCBlockGrass(proxy.blockGrassID).setHardness(0.5F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("MoCGrass");
        GameRegistry.registerBlock(mocGrass, MultiItemBlock.class, "MoC_Grass");
        
        mocDirt = new MoCBlockDirt(proxy.blockDirtID).setHardness(0.6F).setStepSound(Block.soundGravelFootstep).setUnlocalizedName("MoCDirt");
        GameRegistry.registerBlock(mocDirt, MultiItemBlock.class, "MoC_Dirt");
        
        //non terrain generator blocks
       
        mocLeaf = new MoCBlockLeaf(proxy.blockLeafID).setHardness(0.2F).setLightOpacity(1).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("MoCLeaves");
        GameRegistry.registerBlock(mocLeaf, MultiItemBlock.class, "MoC_Leaf");
        
        mocLog = new MoCBlockLog(proxy.blockLogID).setHardness(2.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("MoCLog");
        GameRegistry.registerBlock(mocLog, MultiItemBlock.class, "MoC_Log");
        
        mocTallGrass = new MoCBlockTallGrass(proxy.blockTallGrassID, true).setHardness(0.0F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("MoCTallGrass");
        GameRegistry.registerBlock(mocTallGrass, MultiItemBlock.class, "MoC_TallGrass");
        
        mocPlank = new MoCBlockPlanks(proxy.blockPlanksID).setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundWoodFootstep).setUnlocalizedName("MoCWoodPlanks");
        GameRegistry.registerBlock(mocPlank, MultiItemBlock.class, "MoC_Planks");

        for (int i = 0; i < multiBlockNames.size(); i++) 
        {
            ItemStack stoneStack = new ItemStack(mocStone, 1, i);
            LanguageRegistry.addName(stoneStack, multiBlockNames.get(i) + " Stone");
            
            ItemStack grassStack = new ItemStack(mocGrass, 1, i);
            LanguageRegistry.addName(grassStack, multiBlockNames.get(i) + " Grass");
            
            ItemStack dirtStack = new ItemStack(mocDirt, 1, i);
            LanguageRegistry.addName(dirtStack, multiBlockNames.get(i) + " Dirt");
            
            ItemStack leafStack = new ItemStack(mocLeaf, 1, i);
            LanguageRegistry.addName(leafStack, multiBlockNames.get(i) + " Leaves");
            
            ItemStack logStack = new ItemStack(mocLog, 1, i);
            LanguageRegistry.addName(logStack, multiBlockNames.get(i) + " Log");
        
            ItemStack tallgrassStack = new ItemStack(mocTallGrass, 1, i);
            LanguageRegistry.addName(tallgrassStack, multiBlockNames.get(i) + " Tall Grass");
            
            ItemStack planksStack = new ItemStack(mocPlank, 1, i);
            LanguageRegistry.addName(planksStack, multiBlockNames.get(i) + " Wood Plank");
        }
        
      //wyvern lair block harvest settings
        MinecraftForge.setBlockHarvestLevel(mocDirt, 0, "shovel", 0); 
        MinecraftForge.setBlockHarvestLevel(mocGrass, 0, "shovel", 0); 
        MinecraftForge.setBlockHarvestLevel(mocStone, 0, "pickaxe", 1);
        
        LanguageRegistry.instance().addStringLocalization("itemGroup.MoCreaturesTab", "en_US", "MoCreatures Misc");
    }

    private void AddNames()
    {
        LanguageRegistry.addName(horsesaddle, "Crafted Saddle");
        LanguageRegistry.addName(sharkteeth, "Shark Teeth");
        LanguageRegistry.addName(haystack, "Hay Stack");
        LanguageRegistry.addName(sugarlump, "Sugar Lump");
        LanguageRegistry.addName(bigcatclaw, "BigCat Claw");
        LanguageRegistry.addName(whip, "Whip");
        LanguageRegistry.addName(medallion, "Medallion");
        LanguageRegistry.addName(kittybed, "Kitty Bed");
        LanguageRegistry.addName(litterbox, "Litter Box");
        LanguageRegistry.addName(woolball, "Wool Ball");
        LanguageRegistry.addName(petfood, "Pet Food");
        LanguageRegistry.addName(rope, "Rope");
        LanguageRegistry.addName(crochide, "Reptile Hide");
        LanguageRegistry.addName(plateCroc, "Reptile Plate");
        LanguageRegistry.addName(helmetCroc, "Reptile Helmet");
        LanguageRegistry.addName(bootsCroc, "Reptile Boots");
        LanguageRegistry.addName(legsCroc, "Reptile Legs");
        LanguageRegistry.addName(fishbowl_e, "Empty FishBowl");
        LanguageRegistry.addName(fishbowl_w, "Full FishBowl");
        LanguageRegistry.addName(fishbowl_1, "FishBowl");
        LanguageRegistry.addName(fishbowl_2, "FishBowl");
        LanguageRegistry.addName(fishbowl_3, "FishBowl");
        LanguageRegistry.addName(fishbowl_4, "FishBowl");
        LanguageRegistry.addName(fishbowl_5, "FishBowl");
        LanguageRegistry.addName(fishbowl_6, "FishBowl");
        LanguageRegistry.addName(fishbowl_7, "FishBowl");
        LanguageRegistry.addName(fishbowl_8, "FishBowl");
        LanguageRegistry.addName(fishbowl_9, "FishBowl");
        LanguageRegistry.addName(fishbowl_10, "FishBowl");

        LanguageRegistry.addName(fur, "Fur");
        LanguageRegistry.addName(omelet, "Omelet");
        LanguageRegistry.addName(turtlemeat, "Turtle Meat");
        LanguageRegistry.addName(turtlesoup, "Turtle Soup");

        LanguageRegistry.addName(nunchaku, "Nunchaku");
        LanguageRegistry.addName(sai, "Sai");
        LanguageRegistry.addName(bo, "Bo");
        LanguageRegistry.addName(katana, "Katana");
        LanguageRegistry.addName(sharksword, "Shark Sword");

        LanguageRegistry.addName(key, "Key");
        LanguageRegistry.addName(vialdarkness, "Essence of Darkness");
        LanguageRegistry.addName(vialnightmare, "Essence of Fire");
        LanguageRegistry.addName(vialundead, "Essence of Undead");
        LanguageRegistry.addName(viallight, "Essence of Light");
        LanguageRegistry.addName(amuletbone, "Bone Amulet");
        LanguageRegistry.addName(amuletbonefull, "Bone Amulet");
        LanguageRegistry.addName(amuletghost, "Ghost Amulet");
        LanguageRegistry.addName(amuletghostfull, "Ghost Amulet");
        LanguageRegistry.addName(amuletfairy, "Fairy Amulet");
        LanguageRegistry.addName(amuletfairyfull, "Fairy Amulet");
        LanguageRegistry.addName(amuletpegasus, "Sky Amulet");
        LanguageRegistry.addName(amuletpegasusfull, "Sky Amulet");
        LanguageRegistry.addName(recordshuffle, "Zebra Record");
        LanguageRegistry.addName(sharksword, "Shark Sword");

        LanguageRegistry.addName(plateFur, "Fur Plate");
        LanguageRegistry.addName(helmetFur, "Fur Helmet");
        LanguageRegistry.addName(bootsFur, "Fur Boots");
        LanguageRegistry.addName(legsFur, "Fur Legs");

        LanguageRegistry.addName(heartdarkness, "Heart of Darkness");
        LanguageRegistry.addName(heartfire, "Heart of Fire");
        LanguageRegistry.addName(heartundead, "Heart of Undead");
        LanguageRegistry.addName(ostrichmeat, "Ostrich meat");
        LanguageRegistry.addName(ostrichcooked, "Ostrich cooked");
        LanguageRegistry.addName(crabmeat, "Crab meat");
        LanguageRegistry.addName(crabmeatcooked, "Crab cooked");
        LanguageRegistry.addName(unicorn, "Unicorn Horn");

        LanguageRegistry.addName(horsearmormetal, "Iron Mount Armor");
        LanguageRegistry.addName(horsearmorgold, "Gold Mount Armor");
        LanguageRegistry.addName(horsearmordiamond, "Diamond Mount Armor");
        LanguageRegistry.addName(horsearmorcrystal, "Crystal Mount Armor");

        LanguageRegistry.addName(animalHide, "Hide");
        LanguageRegistry.addName(rawTurkey, "Raw Turkey");
        LanguageRegistry.addName(cookedTurkey, "Cooked Turkey");
        LanguageRegistry.addName(plateHide, "Hide Plate");
        LanguageRegistry.addName(helmetHide, "Hide Helmet");
        LanguageRegistry.addName(bootsHide, "Hide Boots");
        LanguageRegistry.addName(legsHide, "Hide Legs");
        LanguageRegistry.addName(ratRaw, "Raw Rat");
        LanguageRegistry.addName(ratCooked, "Cooked Rat");
        LanguageRegistry.addName(ratBurger, "Rat Burger");

        LanguageRegistry.addName(chitinCave, "Black Chitin");
        LanguageRegistry.addName(chitinFrost, "Frost Chitin");
        LanguageRegistry.addName(chitinNether, "Nether Chitin");
        LanguageRegistry.addName(chitinDirt, "Chitin");
        LanguageRegistry.addName(swordDirt, "Scorpion Sword");
        LanguageRegistry.addName(swordFrost, "Frost Scorpion Sword");
        LanguageRegistry.addName(swordCave, "Cave Scorpion Sword");
        LanguageRegistry.addName(swordNether, "Nether Scorpion Sword");
        LanguageRegistry.addName(swordsilver, "Silver Skeleton Sword");

        LanguageRegistry.addName(plateScorpDirt, "Scorpion Plate");
        LanguageRegistry.addName(helmetScorpDirt, "Scorpion Helmet");
        LanguageRegistry.addName(legsScorpDirt, "Scorpion Legs");
        LanguageRegistry.addName(bootsScorpDirt, "Scorpion Boots");

        LanguageRegistry.addName(plateScorpFrost, "Frost Scorpion Plate");
        LanguageRegistry.addName(helmetScorpFrost, "Frost Scorpion Helmet");
        LanguageRegistry.addName(legsScorpFrost, "Frost Scorpion Legs");
        LanguageRegistry.addName(bootsScorpFrost, "Frost Scorpion Boots");

        LanguageRegistry.addName(plateScorpCave, "Cave Scorpion Plate");
        LanguageRegistry.addName(helmetScorpCave, "Cave Scorpion Helmet");
        LanguageRegistry.addName(legsScorpCave, "Cave Scorpion Legs");
        LanguageRegistry.addName(bootsScorpCave, "Cave Scorpion Boots");

        LanguageRegistry.addName(plateScorpNether, "Nether Scorpion Plate");
        LanguageRegistry.addName(helmetScorpNether, "Nether Scorpion Helmet");
        LanguageRegistry.addName(legsScorpNether, "Nether Scorpion Legs");
        LanguageRegistry.addName(bootsScorpNether, "Nether Scorpion Boots");

        LanguageRegistry.addName(stingCave, "Cave Scorpion Sting");
        LanguageRegistry.addName(stingFrost, "Frost Scorpion Sting");
        LanguageRegistry.addName(stingNether, "Nether Scorpion Sting");
        LanguageRegistry.addName(stingDirt, "Scorpion Sting");
        LanguageRegistry.addName(fishyegg, "Spoiled Egg");
        
        LanguageRegistry.addName(scrollFreedom, "Scroll of Freedom");
        LanguageRegistry.addName(scrollOfSale, "Scroll of Sale");
        LanguageRegistry.addName(scrollOfOwner, "Scroll of Reset Owner");

        LanguageRegistry.addName(woodTusks, "Wood Tusks");
        LanguageRegistry.addName(ironTusks, "Iron Tusks");
        LanguageRegistry.addName(diamondTusks, "Diamond Tusks");
        LanguageRegistry.addName(harness, "Elephant Harness");
        LanguageRegistry.addName(chestSet, "Chest Set");
        LanguageRegistry.addName(howdah, "Elephant Howdah");
        LanguageRegistry.addName(garment, "Elephant Garment");
        LanguageRegistry.addName(platform, "Mammoth Platform");
        LanguageRegistry.addName(fishnet, "Fish Net");
        LanguageRegistry.addName(superAmulet, "Pet Amulet");

        for (int i = 0; i < 91; i++)
        {
            String s;
            if (i > 0 && i < 10)
            {
                s = MoCEntityFishy.fishNames[i - 1];
                LanguageRegistry.addName(new ItemStack(fishyegg, 1, i), (s + " Fish Egg"));
            }
            else if (i == 11)
            {
                LanguageRegistry.addName(new ItemStack(fishyegg, 1, i), ("Shark Egg"));
            }
            else if (i > 20 && i < 29)
            {
                s = MoCEntitySnake.snakeNames[i - 21];
                LanguageRegistry.addName(new ItemStack(fishyegg, 1, i), (s + " Snake Egg"));
            }
            else if (i == 30 || i == 31)
            {
                LanguageRegistry.addName(new ItemStack(fishyegg, 1, i), ("Ostrich Egg"));
            }
            else if (i == 33)
            {
                LanguageRegistry.addName(new ItemStack(fishyegg, 1, i), "Komodo Dragon Egg");
            }
            else if (i > 40 && i < 46)
            {
                s = MoCEntityPetScorpion.scorpionNames[i - 41];
                LanguageRegistry.addName(new ItemStack(fishyegg, 1, i), (s + " Scorpion Egg"));
            }
            else if (i > 49 && i <55)
            {
                s = MoCEntityWyvern.wyvernNames[i - 50];
                LanguageRegistry.addName(new ItemStack(fishyegg, 1, i), (s + " Wyvern Egg"));
            }
            else if (i > 69 && i < (70 + MoCEntityMediumFish.fishNames.length))
            {
                s = MoCEntityMediumFish.fishNames[i - 70];
                LanguageRegistry.addName(new ItemStack(fishyegg, 1, i), (s + " Egg"));
            }
            else if (i > 79 && i < (80 + MoCEntitySmallFish.fishNames.length))
            {
                s = MoCEntitySmallFish.fishNames[i - 80];
                LanguageRegistry.addName(new ItemStack(fishyegg, 1, i), (s + " Egg"));
            }
            else if (i == 90)
            {
                LanguageRegistry.addName(new ItemStack(fishyegg, 1, i), ("Piranha Egg"));
            }
        }
    }

    private void AddRecipes()
    {
        GameRegistry.addSmelting(MoCreatures.crabmeat.itemID, new ItemStack(MoCreatures.crabmeatcooked, 1), 0F);
        
        GameRegistry.addSmelting(MoCreatures.ratRaw.itemID, new ItemStack(MoCreatures.ratCooked, 1), 0F);

        GameRegistry.addSmelting(MoCreatures.ostrichmeat.itemID, new ItemStack(MoCreatures.ostrichcooked, 1), 0F);

        GameRegistry.addSmelting(MoCreatures.rawTurkey.itemID, new ItemStack(MoCreatures.cookedTurkey, 1), 0F);

        GameRegistry.addSmelting(MoCreatures.fishyegg.itemID, new ItemStack(MoCreatures.omelet, 1), 0F);

        GameRegistry.addSmelting(Item.egg.itemID, new ItemStack(MoCreatures.omelet, 1), 0F);

        GameRegistry.addShapelessRecipe(new ItemStack(scrollFreedom, 1), new Object[] { Item.paper, Item.feather, Item.redstone });
        
        GameRegistry.addShapelessRecipe(new ItemStack(scrollFreedom, 1), new Object[] { scrollOfSale, Item.redstone });
        
        GameRegistry.addShapelessRecipe(new ItemStack(scrollOfSale, 1), new Object[] { Item.paper, Item.feather });
        
        
        GameRegistry.addShapelessRecipe(new ItemStack(Item.leather, 1), new Object[] { animalHide });

        GameRegistry.addShapelessRecipe(new ItemStack(Item.itemsList[Block.cloth.blockID], 1), new Object[] { fur });

        GameRegistry.addShapelessRecipe(new ItemStack(swordNether, 1), new Object[] { Item.swordDiamond, stingNether, stingNether, stingNether });

        GameRegistry.addShapelessRecipe(new ItemStack(swordFrost, 1), new Object[] { Item.swordDiamond, stingFrost, stingFrost, stingFrost });

        GameRegistry.addShapelessRecipe(new ItemStack(swordCave, 1), new Object[] { Item.swordDiamond, stingCave, stingCave, stingCave });

        GameRegistry.addShapelessRecipe(new ItemStack(swordDirt, 1), new Object[] { Item.swordDiamond, stingDirt, stingDirt, stingDirt });

        GameRegistry.addShapelessRecipe(new ItemStack(turtlesoup, 1), new Object[] { new ItemStack(turtlemeat, 1), new ItemStack(Item.bowlEmpty, 1) });

        GameRegistry.addShapelessRecipe(new ItemStack(viallight, 1), new Object[] { vialundead, vialnightmare, vialdarkness });

        GameRegistry.addRecipe(new ItemStack(fishnet, 1), new Object[] { " # ", "S#S", "#S#", Character.valueOf('#'), Item.silk, Character.valueOf('S'), sharkteeth });
        
        GameRegistry.addRecipe(new ItemStack(woodTusks, 1), new Object[] { "X  ", "XR ", "XXX", Character.valueOf('X'), Item.itemsList[Block.planks.blockID], Character.valueOf('R'), rope });
        
        GameRegistry.addRecipe(new ItemStack(ironTusks, 1), new Object[] { "X  ", "XR ", "XXX", Character.valueOf('X'), Item.ingotIron, Character.valueOf('R'), rope });
        
        GameRegistry.addRecipe(new ItemStack(diamondTusks, 1), new Object[] { "X  ", "XR ", "XXX", Character.valueOf('X'), Item.diamond, Character.valueOf('R'), rope });
        
        GameRegistry.addRecipe(new ItemStack(platform, 1), new Object[] { "WRW", "PPP", "WRW",  Character.valueOf('W'), Item.itemsList[Block.wood.blockID], Character.valueOf('R'), rope ,  Character.valueOf('P'), Item.itemsList[Block.planks.blockID]});
        
        GameRegistry.addRecipe(new ItemStack(chestSet, 1), new Object[] { " W ", "CHC", " W ", Character.valueOf('H'), animalHide, Character.valueOf('W'), new ItemStack(Item.itemsList[Block.cloth.blockID], 1, 0), Character.valueOf('C'), Item.itemsList[Block.chest.blockID] });
        
        GameRegistry.addRecipe(new ItemStack(harness, 1), new Object[] { "HWH", "IWI", "HWH", Character.valueOf('H'), animalHide, Character.valueOf('W'), new ItemStack(Item.itemsList[Block.cloth.blockID], 1, 0), Character.valueOf('I'), Item.ingotIron });
        
        GameRegistry.addRecipe(new ItemStack(howdah, 1), new Object[] { "SRS", "RYR", "SRS", Character.valueOf('S'), Item.stick, Character.valueOf('R'), new ItemStack(Item.itemsList[Block.cloth.blockID], 1, 14), Character.valueOf('Y'), new ItemStack(Item.itemsList[Block.cloth.blockID], 1, 4) });

        GameRegistry.addRecipe(new ItemStack(garment, 1), new Object[] { "pyg", "RMR", "BYB", Character.valueOf('R'), new ItemStack(Item.itemsList[Block.cloth.blockID], 1, 14), Character.valueOf('Y'), new ItemStack(Item.itemsList[Block.cloth.blockID], 1, 4), 
        Character.valueOf('B'), new ItemStack(Item.itemsList[Block.cloth.blockID], 1, 11), Character.valueOf('M'), medallion,
        Character.valueOf('p'), new ItemStack(Item.dyePowder, 1, 9), Character.valueOf('y'), new ItemStack(Item.dyePowder, 1, 11),
        Character.valueOf('g'), new ItemStack(Item.dyePowder, 1, 10)
        });

        //Item.dyePowder.itemID
        GameRegistry.addRecipe(new ItemStack(ratBurger, 1), new Object[] { "SB ", "GRG", " B ", Character.valueOf('R'), ratCooked, Character.valueOf('B'), Item.bread, Character.valueOf('S'), Item.pumpkinSeeds, Character.valueOf('G'), Item.seeds });

        GameRegistry.addRecipe(new ItemStack(plateScorpFrost, 1), new Object[] { "X X", "XXX", "XXX", Character.valueOf('X'), chitinFrost });

        GameRegistry.addRecipe(new ItemStack(helmetScorpFrost, 1), new Object[] { "XXX", "X X", Character.valueOf('X'), chitinFrost });

        GameRegistry.addRecipe(new ItemStack(legsScorpFrost, 1), new Object[] { "XXX", "X X", "X X", Character.valueOf('X'), chitinFrost });

        GameRegistry.addRecipe(new ItemStack(bootsScorpFrost, 1), new Object[] { "X X", "X X", Character.valueOf('X'), chitinFrost });

        GameRegistry.addRecipe(new ItemStack(plateScorpNether, 1), new Object[] { "X X", "XXX", "XXX", Character.valueOf('X'), chitinNether });

        GameRegistry.addRecipe(new ItemStack(helmetScorpNether, 1), new Object[] { "XXX", "X X", Character.valueOf('X'), chitinNether });

        GameRegistry.addRecipe(new ItemStack(legsScorpNether, 1), new Object[] { "XXX", "X X", "X X", Character.valueOf('X'), chitinNether });

        GameRegistry.addRecipe(new ItemStack(bootsScorpNether, 1), new Object[] { "X X", "X X", Character.valueOf('X'), chitinNether });

        GameRegistry.addRecipe(new ItemStack(plateScorpCave, 1), new Object[] { "X X", "XXX", "XXX", Character.valueOf('X'), chitinCave });

        GameRegistry.addRecipe(new ItemStack(helmetScorpCave, 1), new Object[] { "XXX", "X X", Character.valueOf('X'), chitinCave });

        GameRegistry.addRecipe(new ItemStack(legsScorpCave, 1), new Object[] { "XXX", "X X", "X X", Character.valueOf('X'), chitinCave });

        GameRegistry.addRecipe(new ItemStack(bootsScorpCave, 1), new Object[] { "X X", "X X", Character.valueOf('X'), chitinCave });

        GameRegistry.addRecipe(new ItemStack(plateScorpDirt, 1), new Object[] { "X X", "XXX", "XXX", Character.valueOf('X'), chitinDirt });

        GameRegistry.addRecipe(new ItemStack(helmetScorpDirt, 1), new Object[] { "XXX", "X X", Character.valueOf('X'), chitinDirt });

        GameRegistry.addRecipe(new ItemStack(legsScorpDirt, 1), new Object[] { "XXX", "X X", "X X", Character.valueOf('X'), chitinDirt });

        GameRegistry.addRecipe(new ItemStack(bootsScorpDirt, 1), new Object[] { "X X", "X X", Character.valueOf('X'), chitinDirt });

        GameRegistry.addRecipe(new ItemStack(plateHide, 1), new Object[] { "X X", "XXX", "XXX", Character.valueOf('X'), animalHide });

        GameRegistry.addRecipe(new ItemStack(helmetHide, 1), new Object[] { "XXX", "X X", Character.valueOf('X'), animalHide });

        GameRegistry.addRecipe(new ItemStack(legsHide, 1), new Object[] { "XXX", "X X", "X X", Character.valueOf('X'), animalHide });

        GameRegistry.addRecipe(new ItemStack(bootsHide, 1), new Object[] { "X X", "X X", Character.valueOf('X'), animalHide });

        GameRegistry.addRecipe(new ItemStack(horsearmorcrystal, 1), new Object[] { "  D", "CDC", "DCD", Character.valueOf('D'), Item.diamond, Character.valueOf('C'), Block.glass });

        GameRegistry.addRecipe(new ItemStack(horsearmormetal, 1), new Object[] { "  X", "XYX", "XXX", Character.valueOf('X'), Item.ingotIron, Character.valueOf('Y'), new ItemStack(Item.itemsList[Block.cloth.blockID], 1, 15) });

        GameRegistry.addRecipe(new ItemStack(horsearmorgold, 1), new Object[] { "  X", "XYX", "XXX", Character.valueOf('X'), Item.ingotGold, Character.valueOf('Y'), new ItemStack(Item.itemsList[Block.cloth.blockID], 1, 14) });

        GameRegistry.addRecipe(new ItemStack(horsearmordiamond, 1), new Object[] { "  X", "XYX", "XXX", Character.valueOf('X'), Item.diamond, Character.valueOf('Y'), new ItemStack(Item.itemsList[Block.cloth.blockID], 1, 11) });

        GameRegistry.addRecipe(new ItemStack(viallight, 1), new Object[] { "X", "Y", "Z", Character.valueOf('X'), vialundead, Character.valueOf('Y'), vialnightmare, Character.valueOf('Z'), vialdarkness });

        GameRegistry.addRecipe(new ItemStack(vialundead, 1), new Object[] { " X ", " Y ", " Z ", Character.valueOf('X'), Item.rottenFlesh, Character.valueOf('Y'), heartundead, Character.valueOf('Z'), Item.glassBottle });

        GameRegistry.addRecipe(new ItemStack(vialnightmare, 1), new Object[] { " X ", " Y ", " Z ", Character.valueOf('X'), Item.blazePowder, Character.valueOf('Y'), heartfire, Character.valueOf('Z'), Item.glassBottle });

        GameRegistry.addRecipe(new ItemStack(vialnightmare, 1), new Object[] { " X ", " Y ", " Z ", Character.valueOf('X'), Block.fire, Character.valueOf('Y'), heartfire, Character.valueOf('Z'), Item.glassBottle });

        GameRegistry.addRecipe(new ItemStack(vialdarkness, 1), new Object[] { " X ", " Y ", " Z ", Character.valueOf('X'), Item.enderPearl, Character.valueOf('Y'), heartdarkness, Character.valueOf('Z'), Item.glassBottle });

        GameRegistry.addRecipe(new ItemStack(plateFur, 1), new Object[] { "X X", "XXX", "XXX", Character.valueOf('X'), fur });

        GameRegistry.addRecipe(new ItemStack(helmetFur, 1), new Object[] { "XXX", "X X", Character.valueOf('X'), fur });

        GameRegistry.addRecipe(new ItemStack(legsFur, 1), new Object[] { "XXX", "X X", "X X", Character.valueOf('X'), fur });

        GameRegistry.addRecipe(new ItemStack(bootsFur, 1), new Object[] { "X X", "X X", Character.valueOf('X'), fur });

        GameRegistry.addRecipe(new ItemStack(key, 1), new Object[] { "  #", " # ", "X  ", Character.valueOf('#'), Item.stick, Character.valueOf('X'), Item.ingotIron, });

        GameRegistry.addRecipe(new ItemStack(superAmulet, 1), new Object[] { "X X", " Z ", "X X", Character.valueOf('X'), Item.goldNugget, Character.valueOf('Z'), Item.diamond });

        GameRegistry.addRecipe(new ItemStack(amuletbone, 1), new Object[] { "#X#", "XZX", "#X#", Character.valueOf('#'), Item.bone, Character.valueOf('X'), Item.goldNugget, Character.valueOf('Z'), Item.enderPearl });

        GameRegistry.addRecipe(new ItemStack(amuletghost, 1), new Object[] { "#X#", "XZX", "#X#", Character.valueOf('#'), Item.bone, Character.valueOf('X'), Item.goldNugget, Character.valueOf('Z'), Item.ghastTear });

        GameRegistry.addRecipe(new ItemStack(amuletfairy, 1), new Object[] { "#X#", "XZX", "#X#", Character.valueOf('#'), Block.fire, Character.valueOf('X'), Item.goldNugget, Character.valueOf('Z'), unicorn });

        GameRegistry.addRecipe(new ItemStack(amuletfairy, 1), new Object[] { "#X#", "XZX", "#X#", Character.valueOf('#'), Block.fire, Character.valueOf('X'), Item.goldNugget, Character.valueOf('Z'), viallight });

        GameRegistry.addRecipe(new ItemStack(amuletpegasus, 1), new Object[] { "#X#", "XZX", "#X#", Character.valueOf('#'), Block.fire, Character.valueOf('X'), Item.goldNugget, Character.valueOf('Z'), Item.diamond });

        GameRegistry.addRecipe(new ItemStack(sharksword, 1), new Object[] { "#X#", "#X#", " X ", Character.valueOf('#'), sharkteeth, Character.valueOf('X'), Item.stick, });

        GameRegistry.addRecipe(new ItemStack(fishbowl_e, 1), new Object[] { "# #", "# #", "###", Character.valueOf('#'), Block.glass, });

        GameRegistry.addRecipe(new ItemStack(rope, 1), new Object[] { "# #", " # ", "# #", Character.valueOf('#'), Item.silk, });

        GameRegistry.addShapelessRecipe(new ItemStack(petfood, 4), new Object[] { new ItemStack(Item.fishRaw, 1), new ItemStack(Item.porkRaw, 1) });

        GameRegistry.addRecipe(new ItemStack(woolball, 1), new Object[] { " # ", "# #", " # ", Character.valueOf('#'), Item.silk, });

        GameRegistry.addRecipe(new ItemStack(litterbox, 1), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Block.planks, Character.valueOf('X'), Block.sand, });

        GameRegistry.addRecipe(new ItemStack(medallion, 1), new Object[] { "# #", "XZX", " X ", Character.valueOf('#'), Item.leather, Character.valueOf('Z'), Item.diamond, Character.valueOf('X'), Item.ingotGold, });

        GameRegistry.addRecipe(new ItemStack(medallion, 1), new Object[] { "# #", " X ", Character.valueOf('#'), Item.leather, Character.valueOf('X'), Item.ingotGold, });

        GameRegistry.addRecipe(new ItemStack(whip, 1), new Object[] { "#X#", "X X", "# Z", Character.valueOf('#'), bigcatclaw, Character.valueOf('X'), Item.leather, Character.valueOf('Z'), Item.ingotIron });

        GameRegistry.addRecipe(new ItemStack(horsesaddle, 1), new Object[] { "XXX", "X#X", "# #", Character.valueOf('#'), Item.ingotIron, Character.valueOf('X'), Item.leather });

        GameRegistry.addRecipe(new ItemStack(haystack, 1), new Object[] { "XXX", "XXX", Character.valueOf('X'), Item.wheat });

        GameRegistry.addRecipe(new ItemStack(Item.wheat, 6), new Object[] { "X", Character.valueOf('X'), haystack });

        GameRegistry.addRecipe(new ItemStack(sugarlump, 1), new Object[] { "XX", "##", Character.valueOf('X'), Item.sugar, Character.valueOf('#'), Item.sugar });

        GameRegistry.addRecipe(new ItemStack(horsesaddle, 1), new Object[] { "X", "#", Character.valueOf('X'), Item.saddle, Character.valueOf('#'), Item.ingotIron });

        GameRegistry.addRecipe(new ItemStack(Item.plateChain, 1), new Object[] { "X X", "XXX", "XXX", Character.valueOf('X'), sharkteeth });

        GameRegistry.addRecipe(new ItemStack(Item.helmetChain, 1), new Object[] { "XXX", "X X", Character.valueOf('X'), sharkteeth });

        GameRegistry.addRecipe(new ItemStack(Item.legsChain, 1), new Object[] { "XXX", "X X", "X X", Character.valueOf('X'), sharkteeth });

        GameRegistry.addRecipe(new ItemStack(Item.bootsChain, 1), new Object[] { "X X", "X X", Character.valueOf('X'), sharkteeth });

        GameRegistry.addRecipe(new ItemStack(plateCroc, 1), new Object[] { "X X", "XXX", "XXX", Character.valueOf('X'), crochide });

        GameRegistry.addRecipe(new ItemStack(helmetCroc, 1), new Object[] { "XXX", "X X", Character.valueOf('X'), crochide });

        GameRegistry.addRecipe(new ItemStack(legsCroc, 1), new Object[] { "XXX", "X X", "X X", Character.valueOf('X'), crochide });

        GameRegistry.addRecipe(new ItemStack(bootsCroc, 1), new Object[] { "X X", "X X", Character.valueOf('X'), crochide });

        for (int i = 0; i < 16; i++)
        {
            GameRegistry.addShapelessRecipe(new ItemStack(kittybed, 1, i), new Object[] { new ItemStack(Item.dyePowder, 1, i), new ItemStack(kittybed, 1) });

            GameRegistry.addRecipe(new ItemStack(kittybed, 1, i), new Object[] { "###", "#X#", "Z  ", Character.valueOf('#'), Block.planks, Character.valueOf('X'), new ItemStack(Item.itemsList[Block.cloth.blockID], 1, MoCTools.colorize(i)), Character.valueOf('Z'), Item.ingotIron, });
            String s = ItemDye.dyeColorNames[i];
            s = s.substring(0, 1).toUpperCase() + s.substring(1);
            LanguageRegistry.addName(new ItemStack(kittybed, 1, i), (s + " Kitty Bed"));
        }
        
        for (int i = 0; i < multiBlockNames.size(); i++) 
        {
            GameRegistry.addShapelessRecipe(new ItemStack(mocPlank, 4, i), new Object[] { new ItemStack(mocLog, 1, i)});
        }
        
        GameRegistry.addRecipe(new ItemStack(staffPortal, 1), new Object[] { "  E", " U ", "R  ", Character.valueOf('E'), Item.eyeOfEnder, Character.valueOf('U'), unicorn, Character.valueOf('R'), Item.blazeRod });
        
        GameRegistry.addRecipe(new ItemStack(staffPortal, 1), new Object[] { "  E", " U ", "R  ", Character.valueOf('E'), Item.eyeOfEnder, Character.valueOf('U'), viallight, Character.valueOf('R'), Item.blazeRod });
        
    }

    public static void burnPlayer(EntityPlayer player)
    {
        //TODO 4FIX
        //if (!mc.theWorld.isRemote)
        //{
        //    inst.burned = true;
        //}
    }

    public static void freezePlayer(EntityPlayer player)
    {
        //TODO 4FIX
        //if (!mc.theWorld.isRemote)
        //{
        //    inst.freezed = true;
        //    inst.freezedcounter = 0;
        //}
    }

    public static void poisonPlayer(EntityPlayer player)
    {
        //TODO 4FIX
        //if (!mc.theWorld.isRemote)
        //{
        //    inst.poisoned = true;
        //    inst.poisoncounter = 0;
        //}
    }

    public static void showCreaturePedia(EntityPlayer player, String s)
    {
        //TODO 4FIX        mc.displayGuiScreen(new MoCGUIPedia(s, 256, 256));
    }

    public static void showCreaturePedia(String s)
    {
        //TODO 4FIX        EntityPlayer entityplayer = mc.thePlayer;
        //showCreaturePedia(entityplayer, s);
    }

    /**
     * Populates spawn lists with MoCreatures or MoCreatures plus vanilla and other custom mobs (if modifyVanillaSpawns is true)
     * if useCustomSpawner is false, it will populate the Forge/MC spawn lists
     */
    public static void populateSpawns()
    {
        if (proxy.debugLogging) log.info("Populating spawns...");

        if (proxy.useCustomSpawner)
        {
            myCustomSpawner.setMaxAmbients(proxy.maxAmbients);
            myCustomSpawner.setMaxCreatures(proxy.maxCreatures);
            myCustomSpawner.setMaxWaterCreatures(proxy.maxWaterCreatures);
            myCustomSpawner.setMaxMonsters(proxy.maxMonsters);
        }
        if (proxy.debugLogging) log.info("Scanning mod configs for entities...");
        for (Map.Entry<String, MoCEntityModData> modEntry : proxy.entityModMap.entrySet())
        {
            if (modEntry.getKey().equalsIgnoreCase("vanilla") && !MoCreatures.proxy.modifyVanillaSpawns)
                continue;
            ArrayList<Map<String, MoCEntityData>> entityList = new ArrayList();
            entityList.add(modEntry.getValue().getAmbientMap());
            entityList.add(modEntry.getValue().getCreatureMap());
            entityList.add(modEntry.getValue().getWaterCreatureMap());
            entityList.add(modEntry.getValue().getMonsterMap());
            entityList.add(modEntry.getValue().getUndefinedMap());
            for (int i = 0; i < entityList.size(); i++)
            {
                for (Map.Entry<String, MoCEntityData> entityEntry : entityList.get(i).entrySet())
                {
                    MoCEntityData entityData = entityEntry.getValue();
                   // System.out.println(entityData.getEntityName() + " biome size = " + entityData.getSpawnBiomes().size());
                    if (entityData.getSpawnBiomes() != null && entityData.getSpawnBiomes().size() > 0)
                    {
                        BiomeGenBase[] biomesToSpawn = new BiomeGenBase[entityData.getSpawnBiomes().size()];
                        biomesToSpawn = entityData.getSpawnBiomes().toArray(biomesToSpawn);
                        if (proxy.useCustomSpawner)
                        {
                            if (proxy.debugLogging) log.info(entityData.getEntityName()  +" canSpawn = " + entityData.getCanSpawn());
                            if (entityData.getCanSpawn())
                            {
                                myCustomSpawner.AddCustomSpawn(entityData.getEntityClass(), entityData.getFrequency(), entityData.getMinSpawn(), entityData.getMaxSpawn(), entityData.getType(), biomesToSpawn);
                                if (proxy.debugLogging) log.info("Added " + entityData.getEntityClass() + " to CustomSpawner spawn lists");
                            }
                            //otherwise the Forge spawnlist remains populated with duplicated entries on CMS
                            removeAllBiomeSpawns(entityData, true); // If we add a entity to CMS, we MUST remove it from ALL biomes on vanilla to avoid massive spawning in missing biomes
                            if (proxy.debugLogging) log.info("Removed " + entityData.getEntityClass() + " from Vanilla spawn lists");
                        }
                        else //use Forge Spawn method instead
                        {
                            if (entityData.getFrequency() > 0 && entityData.getMinSpawn() > 0 && entityData.getMaxSpawn() > 0)
                            {
                                EntityRegistry.addSpawn(entityData.getEntityClass(), entityData.getFrequency(), entityData.getMinSpawn(), entityData.getMaxSpawn(), entityData.getType(), biomesToSpawn);
                                if (proxy.debugLogging) log.info("Added " + entityData.getEntityClass() + " to Vanilla spawn lists");
                            }else
                            {
                                if (proxy.debugLogging) log.info("Removed " + entityData.getEntityClass() + " from Vanilla spawn lists");
                                EntityRegistry.removeSpawn(entityData.getEntityClass(), entityData.getType(), biomesToSpawn); 
                            }
                            
                        }
                    }
                    else entityData.setUseVanillaSpawner(true);
                    // handle entity removals
                    if (proxy.useCustomSpawner)
                    {
                        if (entityData != null && entityData.getFrequency() <= 0 &&
                           (entityData.getType() == null // undefined
                        || ((MoCreatures.proxy.maxAmbients == 0 || !MoCreatures.proxy.spawnAmbients) && entityData.getType() == EnumCreatureType.ambient) 
                        || ((MoCreatures.proxy.maxCreatures == 0 || !MoCreatures.proxy.spawnCreatures) && entityData.getType() == EnumCreatureType.creature)
                        || ((MoCreatures.proxy.maxMonsters == 0 || !MoCreatures.proxy.spawnMonsters) && entityData.getType() == EnumCreatureType.monster)
                        || ((MoCreatures.proxy.maxWaterCreatures == 0 || !MoCreatures.proxy.spawnWaterCreatures) && entityData.getType() == EnumCreatureType.waterCreature)))
                        {
                            // remove from all biomes
                           // System.out.println("Entity " + entityData.getEntityClass() + " is not allowed to spawn due to configuration settings. Please check your confirm your settings if this is a mistake.");
                            if (proxy.debugLogging) log.info("Entity " + entityData.getEntityClass() + " is not allowed to spawn due to configuration settings. Please check your confirm your settings if this is a mistake.");
                            removeAllBiomeSpawns(entityData, false);
                        }
                    }
                }
            }
        }
    }

    public static void removeAllBiomeSpawns(MoCEntityData entityData, boolean vanillaOnly)
    {
        BiomeGenBase[] allBiomes = new BiomeGenBase[proxy.biomeMap.size()];
        List<BiomeGenBase> biomeList = new ArrayList<BiomeGenBase>();
        for (int j = 0; j < BiomeGenBase.biomeList.length; j++)
        {
            if (BiomeGenBase.biomeList[j] != null)
            {
                biomeList.add(BiomeGenBase.biomeList[j]);
            }
        }
        if (biomeList.size() > 0)
        {
            if (proxy.debugLogging) log.info("Removing entity " + entityData.getEntityClass() + " with type " + entityData.getType() + " from all biome spawnlists.");
            allBiomes = biomeList.toArray(allBiomes);
            if (!vanillaOnly)
                myCustomSpawner.RemoveCustomSpawn(entityData.getEntityClass(), entityData.getType(), allBiomes);
            // handle undefined types
            EntityRegistry.removeSpawn(entityData.getEntityClass(), EnumCreatureType.creature, allBiomes);
            EntityRegistry.removeSpawn(entityData.getEntityClass(), EnumCreatureType.waterCreature, allBiomes);
            EntityRegistry.removeSpawn(entityData.getEntityClass(), EnumCreatureType.monster, allBiomes);
            EntityRegistry.removeSpawn(entityData.getEntityClass(), EnumCreatureType.ambient, allBiomes);
        }
    }

    public static void updateSettings()
    {
        proxy.readConfigValues();
        updateSpawnSettings();
    }

    public static void updateSpawnSettings()
    {
        if (proxy.useCustomSpawner)
        {
            if (myCustomSpawner != null) // if server has not started, don't reset custom spawner settings
            {
                myCustomSpawner.setMaxMonsters(proxy.maxMonsters);
                myCustomSpawner.setMaxCreatures(proxy.maxCreatures);
                myCustomSpawner.setMaxWaterCreatures(proxy.maxWaterCreatures);
                myCustomSpawner.setMaxAmbients(proxy.maxAmbients);
                myCustomSpawner.clearLists();
                populateSpawns();
            }
        }
        else populateSpawns();
    }

    public static boolean isServer()
    {
        return (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER);
    }
}