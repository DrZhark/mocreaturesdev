package drzhark.mocreatures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.mojang.authlib.GameProfile;

import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityList.EntityEggInfo;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import drzhark.mocreatures.block.MoCBlockDirt;
import drzhark.mocreatures.block.MoCBlockGrass;
import drzhark.mocreatures.block.MoCBlockLeaf;
import drzhark.mocreatures.block.MoCBlockLog;
import drzhark.mocreatures.block.MoCBlockPlanks;
import drzhark.mocreatures.block.MoCBlockRock;
import drzhark.mocreatures.block.MoCBlockTallGrass;
import drzhark.mocreatures.block.MultiItemBlock;
import drzhark.mocreatures.client.MoCClientTickHandler;
import drzhark.mocreatures.client.MoCCreativeTabs;
import drzhark.mocreatures.client.handlers.MoCKeyHandler;
import drzhark.mocreatures.command.CommandMoCPets;
import drzhark.mocreatures.command.CommandMoCTP;
import drzhark.mocreatures.command.CommandMoCreatures;
import drzhark.mocreatures.configuration.MoCProperty;
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
import drzhark.mocreatures.item.ItemBuilderHammer;
import drzhark.mocreatures.item.ItemStaffPortal;
import drzhark.mocreatures.item.ItemStaffTeleport;
import drzhark.mocreatures.item.MoCItem;
import drzhark.mocreatures.item.MoCItemArmor;
import drzhark.mocreatures.item.MoCItemEgg;
import drzhark.mocreatures.item.MoCItemFishBowl;
import drzhark.mocreatures.item.MoCItemFood;
import drzhark.mocreatures.item.MoCItemHayStack;
import drzhark.mocreatures.item.MoCItemHorseAmulet;
import drzhark.mocreatures.item.MoCItemHorseSaddle;
import drzhark.mocreatures.item.MoCItemKittyBed;
import drzhark.mocreatures.item.MoCItemLitterBox;
import drzhark.mocreatures.item.MoCItemPetAmulet;
import drzhark.mocreatures.item.MoCItemRecord;
import drzhark.mocreatures.item.MoCItemSugarLump;
import drzhark.mocreatures.item.MoCItemTurtleSoup;
import drzhark.mocreatures.item.MoCItemWeapon;
import drzhark.mocreatures.item.MoCItemWhip;
import drzhark.mocreatures.network.MoCPacketPipeline;
import drzhark.mocreatures.network.packet.MoCPacketAnimation;
import drzhark.mocreatures.network.packet.MoCPacketAppear;
import drzhark.mocreatures.network.packet.MoCPacketAttachedEntity;
import drzhark.mocreatures.network.packet.MoCPacketEntityDive;
import drzhark.mocreatures.network.packet.MoCPacketEntityJump;
import drzhark.mocreatures.network.packet.MoCPacketExplode;
import drzhark.mocreatures.network.packet.MoCPacketHealth;
import drzhark.mocreatures.network.packet.MoCPacketHeart;
import drzhark.mocreatures.network.packet.MoCPacketInstaSpawn;
import drzhark.mocreatures.network.packet.MoCPacketNameGUI;
import drzhark.mocreatures.network.packet.MoCPacketShuffle;
import drzhark.mocreatures.network.packet.MoCPacketTwoBytes;
import drzhark.mocreatures.network.packet.MoCPacketVanish;
import drzhark.mocreatures.utils.MoCLog;

@Mod(modid = "MoCreatures", name = "DrZhark's Mo'Creatures", version = "7.0.0.dev.R1")
public class MoCreatures {

    @Instance("MoCreatures")
    public static MoCreatures instance;

    @SidedProxy(clientSide = "drzhark.mocreatures.client.MoCClientProxy", serverSide = "drzhark.mocreatures.MoCProxy")
    public static MoCProxy proxy;
    //public static final CreativeTabs tabMoC = new MoCCreativeTabs(CreativeTabs.creativeTabArray.length, "MoCreaturesTab");
    public MoCPetMapData mapData;
    public static boolean isCustomSpawnerLoaded = false;
    public static GameProfile MOCFAKEPLAYER = new GameProfile("", "[MoCreatures]");

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

    static ArmorMaterial crocARMOR = EnumHelper.addArmorMaterial("crocARMOR", 15, new int[] { 2, 6, 5, 2 }, 12);
    static ArmorMaterial furARMOR = EnumHelper.addArmorMaterial("furARMOR", 15, new int[] { 2, 6, 5, 2 }, 12);
    static ArmorMaterial hideARMOR = EnumHelper.addArmorMaterial("hideARMOR", 15, new int[] { 2, 6, 5, 2 }, 12);
    static ArmorMaterial scorpdARMOR = EnumHelper.addArmorMaterial("scorpdARMOR", 15, new int[] { 2, 6, 5, 2 }, 12);
    static ArmorMaterial scorpfARMOR = EnumHelper.addArmorMaterial("scorpfARMOR", 18, new int[] { 2, 7, 6, 2 }, 12);
    static ArmorMaterial scorpnARMOR = EnumHelper.addArmorMaterial("scorpnARMOR", 20, new int[] { 3, 7, 6, 3 }, 15);
    static ArmorMaterial scorpcARMOR = EnumHelper.addArmorMaterial("scorpcARMOR", 15, new int[] { 2, 6, 5, 2 }, 12);
    static ArmorMaterial silverARMOR = EnumHelper.addArmorMaterial("silverARMOR", 15, new int[] { 2, 6, 5, 2 }, 15);
    static ToolMaterial SILVER = EnumHelper.addToolMaterial("SILVER", 0, 250, 6.0F, 4, 15);

    public static Item horsesaddle;
    //public static Item horsearmormetal;
    public static Item sharkteeth;
    public static Item haystack;
    public static Item sugarlump;
    public static Item fishyegg;
    public static Item bigcatclaw;
    public static Item whip;
    //public static Item horsearmorgold;
    //public static Item horsearmordiamond;
    public static Item staffunicorn;
    public static Item medallion;
    public static Item kittybed;
    public static Item litterbox;
    public static Item woolball;
    //public static Item rope;
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

    public static MoCPlayerTracker tracker;
    public static Map<String, MoCEntityData> mocEntityMap = new TreeMap<String, MoCEntityData>(String.CASE_INSENSITIVE_ORDER);
    public static Map<Integer, Class<? extends EntityLiving>> instaSpawnerMap = new HashMap<Integer, Class<? extends EntityLiving>>();
    public static List<String> defaultBiomeSupport = new ArrayList<String>();
    public static final String CATEGORY_ITEM_IDS = "item-ids";
    public static final MoCPacketPipeline packetPipeline = new MoCPacketPipeline();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new MoCEventHooks());
        proxy.ConfigInit(event);
        //proxy.initSounds();
        proxy.initTextures();
        if (!isServer())
        {
            FMLCommonHandler.instance().bus().register(new MoCClientTickHandler());
            FMLCommonHandler.instance().bus().register(new MoCKeyHandler());
        }
        FMLCommonHandler.instance().bus().register(new MoCPlayerTracker());
    }

    //how to check for client: if(FMLCommonHandler.instance().getSide().isClient())

    @EventHandler
    public void load(FMLInitializationEvent event)
    {
        this.InitItems();
        this.AddNames();
        this.AddRecipes();
        proxy.registerRenderers();
        proxy.registerRenderInformation();
        packetPipeline.initalise();
        packetPipeline.registerPacket(MoCPacketNameGUI.class);
        packetPipeline.registerPacket(MoCPacketAnimation.class);
        packetPipeline.registerPacket(MoCPacketAppear.class);
        packetPipeline.registerPacket(MoCPacketAttachedEntity.class);
        packetPipeline.registerPacket(MoCPacketEntityDive.class);
        packetPipeline.registerPacket(MoCPacketEntityJump.class);
        packetPipeline.registerPacket(MoCPacketExplode.class);
        packetPipeline.registerPacket(MoCPacketHealth.class);
        packetPipeline.registerPacket(MoCPacketHeart.class);
        packetPipeline.registerPacket(MoCPacketInstaSpawn.class);
        packetPipeline.registerPacket(MoCPacketShuffle.class);
        packetPipeline.registerPacket(MoCPacketTwoBytes.class);
        packetPipeline.registerPacket(MoCPacketVanish.class);
        DimensionManager.registerProviderType(WyvernLairDimensionID, WorldProviderWyvernEnd.class, true);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        packetPipeline.postInitialise();
        isCustomSpawnerLoaded = Loader.isModLoaded("CustomSpawner");
        //ForgeChunkManager.setForcedChunkLoadingCallback(instance, new MoCloadCallback());
        DimensionManager.registerDimension(WyvernLairDimensionID, WyvernLairDimensionID);
        // ***MUST REGISTER BIOMES AT THIS POINT TO MAKE SURE OUR ENTITIES GET ALL BIOMES FROM DICTIONARY****
        this.WyvernLairBiome = new BiomeGenWyvernLair(MoCreatures.proxy.WyvernBiomeID);
        this.defaultBiomeSupport.add("biomesop");
        this.defaultBiomeSupport.add("extrabiomes");
        this.defaultBiomeSupport.add("highlands");
        this.defaultBiomeSupport.add("ted80");
        this.defaultBiomeSupport.add("extrabiomes");
        this.defaultBiomeSupport.add("minecraft");
        registerEntities();
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event)
    {
        proxy.initGUI();
        event.registerServerCommand(new CommandMoCreatures());
        event.registerServerCommand(new CommandMoCTP());
        event.registerServerCommand(new CommandMoCPets());
    }

    @EventHandler
    public void serverStarted(FMLServerStartedEvent event)
    {
    }

    /*
    public class MoCloadCallback implements ForgeChunkManager.OrderedLoadingCallback {

        @Override
        public void ticketsLoaded(List<Ticket> tickets, World world) {
        }

        @Override
        public List<Ticket> ticketsLoaded(List<Ticket> tickets, World world, int maxTicketCount) {
            return tickets;
        }
    }*/

    public void registerEntities()
    {
        registerEntity(MoCEntityBunny.class, "Bunny", 12623485, 9141102);//, 0x05600, 0x006500);
        registerEntity(MoCEntitySnake.class, "Snake", 14020607, 13749760);//, 0x05800, 0x006800);
        registerEntity(MoCEntityTurtle.class, "Turtle", 14772545, 9320590);//, 0x04800, 0x004500);
        registerEntity(MoCEntityBird.class, "Bird", 14020607, 14020607);// 0x03600, 0x003500);
        registerEntity(MoCEntityMouse.class, "Mouse", 14772545, 0);//, 0x02600, 0x002500);
        registerEntity(MoCEntityTurkey.class, "Turkey", 14020607, 16711680);//, 0x2600, 0x052500);
        registerEntity(MoCEntityHorse.class, "WildHorse", 12623485, 15656192);//, 0x2600, 0x052500);
        registerEntity(MoCEntityHorseMob.class, "HorseMob", 16711680, 9320590);//, 0x2600, 0x052500);
        registerEntity(MoCEntityOgre.class, "Ogre", 16711680, 65407);//, 0x2600, 0x052500);
        registerEntity(MoCEntityBoar.class, "Boar", 14772545, 9141102);//, 0x2600, 0x052500);
        registerEntity(MoCEntityBear.class, "Bear", 14772545, 1);//, 0x2600, 0x052500);
        registerEntity(MoCEntityDuck.class, "Duck", 14772545, 15656192);//, 0x2600, 0x052500);
        registerEntity(MoCEntityBigCat.class, "BigCat", 12623485, 16622);//, 0x2600, 0x052500);
        registerEntity(MoCEntityDeer.class, "Deer", 14772545, 33023);//, 0x2600, 0x052500);
        registerEntity(MoCEntityWWolf.class, "WWolf", 16711680, 13749760);//, 0x2600, 0x052500);
        registerEntity(MoCEntityWraith.class, "Wraith", 16711680, 0);//, 0x2600, 0x052500);
        registerEntity(MoCEntityFlameWraith.class, "FlameWraith", 16711680, 12623485);//, 0x2600, 0x052500);
        registerEntity(MoCEntityFox.class, "Fox", 14772545, 5253242);//, 0x2600, 0x052500);
        registerEntity(MoCEntityWerewolf.class, "Werewolf", 16711680, 7434694);//, 0x2600, 0x052500);
        registerEntity(MoCEntityShark.class, "Shark", 33023, 9013643);//, 0x2600, 0x052500);
        registerEntity(MoCEntityDolphin.class, "Dolphin", 33023, 15631086);//, 0x2600, 0x052500);
        registerEntity(MoCEntityFishy.class, "Fishy", 33023, 65407);//, 0x2600, 0x052500);
        registerEntity(MoCEntityKitty.class, "Kitty", 12623485, 5253242);//, 0x2600, 0x052500);
        registerEntity(MoCEntityKittyBed.class, "KittyBed");
        registerEntity(MoCEntityLitterBox.class, "LitterBox");
        registerEntity(MoCEntityRat.class, "Rat", 12623485, 9141102);//, 0x2600, 0x052500);
        registerEntity(MoCEntityHellRat.class, "HellRat", 16711680, 14772545);//, 0x2600, 0x052500);
        registerEntity(MoCEntityScorpion.class, "Scorpion", 16711680, 6053069);//, 0x2600, 0x052500);
        registerEntity(MoCEntityCrocodile.class, "Crocodile", 16711680, 65407);//, 0x2600, 0x052500);
        registerEntity(MoCEntityRay.class, "Ray", 33023, 9141102);//14772545, 9141102);
        registerEntity(MoCEntityJellyFish.class, "JellyFish", 33023, 14772545);//, 0x2600, 0x052500);
        registerEntity(MoCEntityGoat.class, "Goat", 7434694, 6053069);//, 0x2600, 0x052500);
        registerEntity(MoCEntityEgg.class, "Egg");//, 0x2600, 0x052500);
        registerEntity(MoCEntityFishBowl.class, "FishBowl");//, 0x2600, 0x052500);
        registerEntity(MoCEntityOstrich.class, "Ostrich", 14020607, 9639167);//, 0x2600, 0x052500);
        registerEntity(MoCEntityBee.class, "Bee", 65407, 15656192);//, 0x2600, 0x052500);
        registerEntity(MoCEntityFly.class, "Fly", 65407, 1);//, 0x2600, 0x052500);
        registerEntity(MoCEntityDragonfly.class, "DragonFly", 65407, 14020607);//, 0x2600, 0x052500);
        registerEntity(MoCEntityFirefly.class, "Firefly", 65407, 9320590);//, 0x2600, 0x052500);
        registerEntity(MoCEntityCricket.class, "Cricket", 65407, 16622);//, 0x2600, 0x052500);
        registerEntity(MoCEntitySnail.class, "Snail", 65407, 14772545);//, 0x2600, 0x052500);
        registerEntity(MoCEntityButterfly.class, "ButterFly", 65407, 7434694);//, 0x22600, 0x012500);
        registerEntity(MoCEntityThrowableRock.class, "TRock");
        registerEntity(MoCEntityGolem.class, "BigGolem", 16711680, 16622);
        registerEntity(MoCEntityPetScorpion.class, "PetScorpion");
        registerEntity(MoCEntityPlatform.class, "MoCPlatform");
        registerEntity(MoCEntityElephant.class, "Elephant", 14772545, 23423);
        registerEntity(MoCEntityKomodo.class, "KomodoDragon", 16711680, 23423);
        registerEntity(MoCEntityWyvern.class, "Wyvern", 14772545, 65407);
        registerEntity(MoCEntityRoach.class, "Roach", 65407, 13749760);
        registerEntity(MoCEntityMaggot.class, "Maggot", 65407, 9141102);
        registerEntity(MoCEntityCrab.class, "Crab", 65407, 13749760);
        registerEntity(MoCEntityRaccoon.class, "Raccoon", 14772545, 13749760);
        registerEntity(MoCEntityMiniGolem.class, "MiniGolem", 16711680, 13749760);
        registerEntity(MoCEntitySilverSkeleton.class, "SilverSkeleton", 16711680, 33023);
        registerEntity(MoCEntityAnt.class, "Ant", 65407, 12623485);
        registerEntity(MoCEntityMediumFish.class, "MediumFish", 33023, 16622);
        registerEntity(MoCEntitySmallFish.class, "SmallFish", 33023, 65407);
        registerEntity(MoCEntityPiranha.class, "Piranha", 33023, 16711680);
        registerEntity(MoCEntityEnt.class, "Ent", 12623485, 16711680);
        registerEntity(MoCEntityMole.class, "Mole", 14020607, 16711680);
        
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

        // ambients
        mocEntityMap.put("Ant", new MoCEntityData("Ant", 4, EnumCreatureType.ambient, new SpawnListEntry(MoCEntityAnt.class, 7, 1, 4), new ArrayList(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP, Type.WASTELAND))));
        mocEntityMap.put("Bee", new MoCEntityData("Bee", 3, EnumCreatureType.ambient, new SpawnListEntry(MoCEntityBee.class, 6, 1, 2), new ArrayList(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MOUNTAIN, Type.PLAINS, Type.WASTELAND))));
        mocEntityMap.put("ButterFly", new MoCEntityData("ButterFly", 3, EnumCreatureType.ambient, new SpawnListEntry(MoCEntityButterfly.class, 8, 1, 3), new ArrayList(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MOUNTAIN, Type.PLAINS, Type.WASTELAND))));
        mocEntityMap.put("Crab", new MoCEntityData("Crab", 2, EnumCreatureType.ambient, new SpawnListEntry(MoCEntityCrab.class, 8, 1, 2), new ArrayList(Arrays.asList(Type.BEACH, Type.WATER))));
        mocEntityMap.put("Cricket", new MoCEntityData("Cricket", 2, EnumCreatureType.ambient, new SpawnListEntry(MoCEntityCricket.class, 8, 1, 2), new ArrayList(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP, Type.WASTELAND))));
        mocEntityMap.put("DragonFly", new MoCEntityData("DragonFly", 2, EnumCreatureType.ambient, new SpawnListEntry(MoCEntityDragonfly.class, 6, 1, 2), new ArrayList(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP, Type.WASTELAND))));
        mocEntityMap.put("Firefly", new MoCEntityData("Firefly", 3, EnumCreatureType.ambient, new SpawnListEntry(MoCEntityFirefly.class, 8, 1, 2), new ArrayList(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP, Type.WASTELAND))));
        mocEntityMap.put("Fly", new MoCEntityData("Fly", 2, EnumCreatureType.ambient, new SpawnListEntry(MoCEntityFly.class, 8, 1, 2), new ArrayList(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP, Type.WASTELAND))));
        mocEntityMap.put("Maggot", new MoCEntityData("Maggot", 2, EnumCreatureType.ambient, new SpawnListEntry(MoCEntityMaggot.class, 8, 1, 2), new ArrayList(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP, Type.WASTELAND))));
        mocEntityMap.put("Snail", new MoCEntityData("Snail", 2, EnumCreatureType.ambient, new SpawnListEntry(MoCEntitySnail.class, 7, 1, 2), new ArrayList(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP, Type.WASTELAND))));
        // creatures
        mocEntityMap.put("Bear", new MoCEntityData("Bear", 4, EnumCreatureType.creature, new SpawnListEntry(MoCEntityBear.class, 6, 1, 2), new ArrayList(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MOUNTAIN, Type.PLAINS, Type.FROZEN, Type.WASTELAND))));
        mocEntityMap.put("BigCat", new MoCEntityData("BigCat", 4, EnumCreatureType.creature, new SpawnListEntry(MoCEntityBigCat.class, 6, 1, 2), new ArrayList(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MOUNTAIN, Type.PLAINS, Type.FROZEN, Type.WASTELAND))));
        mocEntityMap.put("Bird", new MoCEntityData("Bird", 4, EnumCreatureType.creature, new SpawnListEntry(MoCEntityBird.class, 15, 2, 3), new ArrayList(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MOUNTAIN, Type.PLAINS, Type.WASTELAND))));
        mocEntityMap.put("Boar", new MoCEntityData("Boar", 3, EnumCreatureType.creature, new SpawnListEntry(MoCEntityBoar.class, 8, 2, 2), new ArrayList(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MOUNTAIN, Type.PLAINS, Type.JUNGLE, Type.WASTELAND))));
        mocEntityMap.put("Bunny", new MoCEntityData("Bunny", 4, EnumCreatureType.creature, new SpawnListEntry(MoCEntityBunny.class, 8, 2, 3), new ArrayList(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MOUNTAIN, Type.PLAINS, Type.FROZEN))));
        mocEntityMap.put("Crocodile", new MoCEntityData("Crocodile", 2, EnumCreatureType.creature, new SpawnListEntry(MoCEntityCrocodile.class, 6, 1, 2), new ArrayList(Arrays.asList(Type.SWAMP, Type.WASTELAND))));
        mocEntityMap.put("Deer", new MoCEntityData("Deer", 2, EnumCreatureType.creature, new SpawnListEntry(MoCEntityDeer.class, 8, 1, 2), new ArrayList(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MOUNTAIN, Type.PLAINS))));
        mocEntityMap.put("Duck", new MoCEntityData("Duck", 3, EnumCreatureType.creature, new SpawnListEntry(MoCEntityDuck.class, 8, 1, 2), new ArrayList(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MOUNTAIN, Type.PLAINS))));
        mocEntityMap.put("Elephant", new MoCEntityData("Elephant", 3, EnumCreatureType.creature, new SpawnListEntry(MoCEntityElephant.class, 4, 1, 1), new ArrayList(Arrays.asList(Type.DESERT, Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MOUNTAIN, Type.PLAINS, Type.FROZEN))));
        mocEntityMap.put("Ent", new MoCEntityData("Ent", 3, EnumCreatureType.creature, new SpawnListEntry(MoCEntityEnt.class, 8, 1, 2), new ArrayList(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.PLAINS))));
        mocEntityMap.put("Fox", new MoCEntityData("Fox", 2, EnumCreatureType.creature, new SpawnListEntry(MoCEntityFox.class, 8, 1, 1), new ArrayList(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MOUNTAIN, Type.PLAINS, Type.FROZEN, Type.WASTELAND))));
        mocEntityMap.put("Goat", new MoCEntityData("Goat", 2, EnumCreatureType.creature, new SpawnListEntry(MoCEntityGoat.class, 8, 1, 3), new ArrayList(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MOUNTAIN, Type.PLAINS, Type.WASTELAND))));
        mocEntityMap.put("Kitty", new MoCEntityData("Kitty", 3, EnumCreatureType.creature, new SpawnListEntry(MoCEntityKitty.class, 8, 1, 2), new ArrayList(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MOUNTAIN, Type.PLAINS))));
        mocEntityMap.put("KomodoDragon", new MoCEntityData("KomodoDragon", 2, EnumCreatureType.creature, new SpawnListEntry(MoCEntityKomodo.class, 8, 1, 2), new ArrayList(Arrays.asList(Type.SWAMP, Type.WASTELAND))));
        mocEntityMap.put("Mole", new MoCEntityData("Mole", 3, EnumCreatureType.creature, new SpawnListEntry(MoCEntityMole.class, 7, 1, 2), new ArrayList(Arrays.asList(Type.FOREST, Type.HILLS, Type.MOUNTAIN, Type.PLAINS))));
        mocEntityMap.put("Mouse", new MoCEntityData("Mouse", 2, EnumCreatureType.creature, new SpawnListEntry(MoCEntityMouse.class, 7, 1, 2), new ArrayList(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MOUNTAIN, Type.PLAINS, Type.WASTELAND))));
        mocEntityMap.put("Ostrich", new MoCEntityData("Ostrich", 3, EnumCreatureType.creature, new SpawnListEntry(MoCEntityOstrich.class, 4, 1, 1), new ArrayList(Arrays.asList(Type.BEACH, Type.DESERT, Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MOUNTAIN, Type.PLAINS, Type.WASTELAND))));
        mocEntityMap.put("Raccoon", new MoCEntityData("Raccoon", 2, EnumCreatureType.creature, new SpawnListEntry(MoCEntityRaccoon.class, 8, 1, 2), new ArrayList(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MOUNTAIN, Type.PLAINS))));
        mocEntityMap.put("Snake", new MoCEntityData("Snake", 3, EnumCreatureType.creature, new SpawnListEntry(MoCEntitySnake.class, 8, 1, 2), new ArrayList(Arrays.asList(Type.DESERT, Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP, Type.WASTELAND))));
        mocEntityMap.put("Turkey", new MoCEntityData("Turkey", 2, EnumCreatureType.creature, new SpawnListEntry(MoCEntityTurkey.class, 8, 1, 2), new ArrayList(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MOUNTAIN, Type.PLAINS, Type.WASTELAND))));
        mocEntityMap.put("Turtle", new MoCEntityData("Turtle", 3, EnumCreatureType.creature, new SpawnListEntry(MoCEntityTurtle.class, 6, 1, 2), new ArrayList(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP, Type.WASTELAND))));
        mocEntityMap.put("WildHorse", new MoCEntityData("WildHorse", 4, EnumCreatureType.creature, new SpawnListEntry(MoCEntityHorse.class, 8, 1, 4), new ArrayList(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MOUNTAIN, Type.PLAINS, Type.WASTELAND))));
        mocEntityMap.put("Wyvern", new MoCEntityData("Wyvern", 3, EnumCreatureType.creature, new SpawnListEntry(MoCEntityWyvern.class, 8, 1, 3), new ArrayList()));
        //mocEntityMap.put(new SpawnListEntry(MoCEntityWyvern.class, 8, 1, 3), new ArrayList(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.MOUNTAIN, Type.SWAMP)));
        // water creatures
        mocEntityMap.put("Dolphin", new MoCEntityData("Dolphin", 3, EnumCreatureType.waterCreature, new SpawnListEntry(MoCEntityDolphin.class, 6, 1, 1), new ArrayList(Arrays.asList(Type.BEACH, Type.WATER))));
        mocEntityMap.put("Fishy", new MoCEntityData("Fishy", 6, EnumCreatureType.waterCreature, new SpawnListEntry(MoCEntityFishy.class, 12, 1, 6), new ArrayList(Arrays.asList(Type.BEACH, Type.SWAMP, Type.WATER))));
        mocEntityMap.put("JellyFish", new MoCEntityData("JellyFish", 4, EnumCreatureType.waterCreature, new SpawnListEntry(MoCEntityJellyFish.class, 8, 1, 4), new ArrayList(Arrays.asList(Type.WATER))));
        mocEntityMap.put("MediumFish", new MoCEntityData("MediumFish", 4, EnumCreatureType.waterCreature, new SpawnListEntry(MoCEntityMediumFish.class, 10, 1, 4), new ArrayList(Arrays.asList(Type.BEACH, Type.SWAMP, Type.WATER))));
        mocEntityMap.put("Piranha", new MoCEntityData("Piranha", 4, EnumCreatureType.waterCreature, new SpawnListEntry(MoCEntityPiranha.class, 4, 1, 3), new ArrayList(Arrays.asList(Type.BEACH, Type.SWAMP, Type.WATER))));
        mocEntityMap.put("Ray", new MoCEntityData("Ray", 3, EnumCreatureType.waterCreature, new SpawnListEntry(MoCEntityRay.class, 10, 1, 2), new ArrayList(Arrays.asList(Type.SWAMP, Type.WATER))));
        mocEntityMap.put("Shark", new MoCEntityData("Shark", 3, EnumCreatureType.waterCreature, new SpawnListEntry(MoCEntityShark.class, 6, 1, 1), new ArrayList(Arrays.asList(Type.WATER))));
        mocEntityMap.put("SmallFish", new MoCEntityData("SmallFish", 6, EnumCreatureType.waterCreature, new SpawnListEntry(MoCEntitySmallFish.class, 12, 1, 6), new ArrayList(Arrays.asList(Type.BEACH, Type.SWAMP, Type.WATER))));
        // monsters
        mocEntityMap.put("BigGolem", new MoCEntityData("BigGolem", 1, EnumCreatureType.monster, new SpawnListEntry(MoCEntityGolem.class, 3, 1, 1), new ArrayList(Arrays.asList(Type.DESERT, Type.FOREST, Type.FROZEN, Type.JUNGLE, Type.HILLS, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP, Type.WASTELAND))));
        mocEntityMap.put("FlameWraith", new MoCEntityData("FlameWraith", 3, EnumCreatureType.monster, new SpawnListEntry(MoCEntityFlameWraith.class, 5, 1, 1), new ArrayList(Arrays.asList(Type.DESERT, Type.FOREST, Type.FROZEN, Type.JUNGLE, Type.HILLS, Type.MOUNTAIN, Type.NETHER, Type.PLAINS, Type.SWAMP, Type.WASTELAND))));
        mocEntityMap.put("HellRat", new MoCEntityData("HellRat", 4, EnumCreatureType.monster, new SpawnListEntry(MoCEntityHellRat.class, 6, 1, 4), new ArrayList(Arrays.asList(Type.NETHER))));
        mocEntityMap.put("HorseMob", new MoCEntityData("HorseMob", 3, EnumCreatureType.monster, new SpawnListEntry(MoCEntityHorseMob.class, 8, 1, 3), new ArrayList(Arrays.asList(Type.DESERT, Type.FOREST, Type.FROZEN, Type.JUNGLE, Type.HILLS, Type.MOUNTAIN, Type.NETHER, Type.PLAINS, Type.SWAMP, Type.WASTELAND))));
        mocEntityMap.put("MiniGolem", new MoCEntityData("MiniGolem", 2, EnumCreatureType.monster, new SpawnListEntry(MoCEntityMiniGolem.class, 6, 1, 1), new ArrayList(Arrays.asList(Type.DESERT, Type.FOREST, Type.FROZEN, Type.JUNGLE, Type.HILLS, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP, Type.WASTELAND))));
        mocEntityMap.put("Ogre", new MoCEntityData("Ogre", 3, EnumCreatureType.monster, new SpawnListEntry(MoCEntityOgre.class, 8, 1, 1), new ArrayList(Arrays.asList(Type.DESERT, Type.FOREST, Type.FROZEN, Type.JUNGLE, Type.HILLS, Type.MOUNTAIN, Type.NETHER, Type.PLAINS, Type.SWAMP, Type.WASTELAND))));
        mocEntityMap.put("Rat", new MoCEntityData("Rat", 2, EnumCreatureType.monster, new SpawnListEntry(MoCEntityRat.class, 7, 1, 2), new ArrayList(Arrays.asList(Type.DESERT, Type.FOREST, Type.FROZEN, Type.JUNGLE, Type.HILLS, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP, Type.WASTELAND))));
        mocEntityMap.put("Scorpion", new MoCEntityData("Scorpion", 3, EnumCreatureType.monster, new SpawnListEntry(MoCEntityScorpion.class, 6, 1, 1), new ArrayList(Arrays.asList(Type.DESERT, Type.FOREST, Type.FROZEN, Type.JUNGLE, Type.HILLS, Type.MOUNTAIN, Type.NETHER, Type.PLAINS, Type.SWAMP, Type.WASTELAND))));
        mocEntityMap.put("SilverSkeleton", new MoCEntityData("SilverSkeleton", 4, EnumCreatureType.monster, new SpawnListEntry(MoCEntitySilverSkeleton.class, 6, 1, 4), new ArrayList(Arrays.asList(Type.DESERT, Type.FOREST, Type.FROZEN, Type.JUNGLE, Type.HILLS, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP, Type.WASTELAND))));
        mocEntityMap.put("Werewolf", new MoCEntityData("Werewolf", 3, EnumCreatureType.monster, new SpawnListEntry(MoCEntityWerewolf.class, 8, 1, 4), new ArrayList(Arrays.asList(Type.DESERT, Type.FOREST, Type.FROZEN, Type.JUNGLE, Type.HILLS, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP, Type.WASTELAND))));
        mocEntityMap.put("Wraith", new MoCEntityData("Wraith", 3, EnumCreatureType.monster, new SpawnListEntry(MoCEntityWraith.class, 1, 1, 1), new ArrayList(Arrays.asList(Type.DESERT, Type.FOREST, Type.FROZEN, Type.JUNGLE, Type.HILLS, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP, Type.WASTELAND))));
        mocEntityMap.put("WWolf", new MoCEntityData("WWolf", 3, EnumCreatureType.monster, new SpawnListEntry(MoCEntityWWolf.class, 8, 1, 3), new ArrayList(Arrays.asList(Type.DESERT, Type.FOREST, Type.FROZEN, Type.JUNGLE, Type.HILLS, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP, Type.WASTELAND))));

        for (MoCEntityData entityData : mocEntityMap.values())
        {
            if (entityData.getCommandSenderName().equals("Wyvern")) continue;
            SpawnListEntry spawnEntry = entityData.getSpawnListEntry();
            for (BiomeDictionary.Type type : entityData.getBiomeTypes())
            {
                for (BiomeGenBase biome : BiomeDictionary.getBiomesForType(type))
                {
                    boolean match = false;
                    for (String allowedBiomeMod : defaultBiomeSupport)
                    {
                        if (biome.getClass().getName().contains(allowedBiomeMod))
                        {
                            match = true;
                            break;
                        }
                    }
                    if (!biome.getSpawnableList(entityData.getType()).contains(spawnEntry) && match)
                    {
                        biome.getSpawnableList(entityData.getType()).add(spawnEntry);
                    }
                }
            }
        }
        proxy.readMocConfigValues();
    }

    /**
     * For Litterbox and kittybeds
     * 
     * @param entityClass
     * @param entityName
     */
    protected void registerEntity(Class<? extends Entity> entityClass, String entityName)
    {
        LanguageRegistry.instance().addStringLocalization("entity.MoCreatures." + entityName + ".name", entityName);
        if (proxy.debug) 
        {
            MoCLog.logger.info("registerEntity " + entityClass + " with Mod ID " + MoCEntityID);
        }
        EntityRegistry.registerModEntity(entityClass, entityName, MoCEntityID, instance, 128, 1, true);
        MoCEntityID += 1;
    }

    private void registerEntity(Class<? extends Entity> entityClass, String entityName, int eggColor, int eggDotsColor)
    {
        LanguageRegistry.instance().addStringLocalization("entity.MoCreatures." + entityName + ".name", entityName);
        if (proxy.debug) 
        {
          MoCLog.logger.info("registerEntity " + entityClass + " with Mod ID " + MoCEntityID);
        }
        EntityRegistry.registerModEntity(entityClass, entityName, MoCEntityID, instance, 128, 1, true);
        EntityList.IDtoClassMapping.put(Integer.valueOf(MoCEntityID), entityClass);
        EntityList.entityEggs.put(Integer.valueOf(MoCEntityID), new EntityEggInfo(MoCEntityID, eggColor, eggDotsColor));
        MoCEntityID += 1;
    }

    private int getItemId(String name, int defaultId)
    {
        return proxy.mocSettingsConfig.get(CATEGORY_ITEM_IDS, "item_" + name, defaultId).getInt();
    }

    protected void InitItems()
    {
        WyvernLairDimensionID = proxy.WyvernDimension;//17

        recordshuffle = new MoCItemRecord("recordshuffle");
        horsesaddle = new MoCItemHorseSaddle("horsesaddle");

        //horsearmormetal = (new MoCItem(MoCItemID++)).setUnlocalizedName("armormetal");
        sharkteeth = new MoCItem("sharkteeth");
        haystack = new MoCItemHayStack("haystack");
        sugarlump = new MoCItemSugarLump("sugarlump");
        fishyegg = new MoCItemEgg("moc_egg");
        bigcatclaw = new MoCItem("bigcatclaw");
        whip = new MoCItemWhip("whip");

        //horsearmorgold = (new MoCItem(MoCItemID++)).setUnlocalizedName("armorgold");
        //horsearmordiamond = (new MoCItem(MoCItemID++)).setUnlocalizedName("armordiamond");
        staffTeleport = new ItemStaffTeleport("staffteleport");
        LanguageRegistry.addName(staffTeleport, "Teleport Staff");

        medallion = new MoCItem("medallion");
        kittybed = new MoCItemKittyBed("catbed");
        litterbox = new MoCItemLitterBox("catlitter");
        woolball = new MoCItem("woolball");

        //rope = (new MoCItem(MoCItemID++)).setUnlocalizedName("rope");
        petfood = new MoCItem("petfood");
        builderHammer = new ItemBuilderHammer("builderhammer");
        LanguageRegistry.addName(builderHammer, "Builder Hammer");

        crochide = new MoCItem("reptilehide");
        plateCroc = new MoCItemArmor("reptileplat", crocARMOR, 4, 1);
        helmetCroc = new MoCItemArmor("reptilehelmet", crocARMOR, 4, 0);
        legsCroc = new MoCItemArmor("reptilelegs", crocARMOR, 4, 2);
        bootsCroc = new MoCItemArmor("reptileboots", crocARMOR, 4, 3);
        fishbowl_e = new MoCItemFishBowl("bowlempty", 0);
        fishbowl_w = new MoCItemFishBowl("bowlwater", 11);
        fishbowl_1 = new MoCItemFishBowl("bowlfish1", 1);
        fishbowl_2 = new MoCItemFishBowl("bowlfish2", 2);
        fishbowl_3 = new MoCItemFishBowl("bowlfish3", 3);
        fishbowl_4 = new MoCItemFishBowl("bowlfish4", 4);
        fishbowl_5 = new MoCItemFishBowl("bowlfish5", 5);
        fishbowl_6 = new MoCItemFishBowl("bowlfish6", 6);
        fishbowl_7 = new MoCItemFishBowl("bowlfish7", 7);
        fishbowl_8 = new MoCItemFishBowl("bowlfish8", 8);
        fishbowl_9 = new MoCItemFishBowl("bowlfish9", 9);
        fishbowl_10 = new MoCItemFishBowl("bowlfish10", 10);

        fur = new MoCItem("fur");
        omelet = new MoCItemFood("omelet", 4, 0.6F, false);
        turtlemeat = new MoCItemFood("turtlemeat", 2, 0.3F, false);
        turtlesoup = new MoCItemTurtleSoup("turtlesoup", 6, 0.6F, false);

        nunchaku = new MoCItemWeapon("nunchaku", ToolMaterial.IRON);
        sai = new MoCItemWeapon("sai", ToolMaterial.IRON);
        bo = new MoCItemWeapon("bo", ToolMaterial.IRON);
        katana = new MoCItemWeapon("katana", ToolMaterial.IRON);
        sharksword = new MoCItemWeapon("swordshark", ToolMaterial.IRON);

        key = new MoCItem("key");
        vialdarkness = new MoCItem("essencedarkness");
        vialnightmare = new MoCItem("essencefire");
        amuletbone = new MoCItemHorseAmulet("amuletbone");
        amuletbonefull = new MoCItemHorseAmulet("amuletbonefull");
        amuletghost = new MoCItemHorseAmulet("amuletghost");
        amuletghostfull = new MoCItemHorseAmulet("amuletghostfull");
        amuletfairy = new MoCItemHorseAmulet("amuletfairy");
        amuletfairyfull = new MoCItemHorseAmulet("amuletfairyfull");
        amuletpegasus = new MoCItemHorseAmulet("amuletpegasus");
        amuletpegasusfull = new MoCItemHorseAmulet("amuletpegasusfull");

        vialundead = new MoCItem("essenceundead");
        viallight = new MoCItem("essencelight");

        plateFur = new MoCItemArmor("furplate", furARMOR, 4, 1);
        helmetFur = new MoCItemArmor("furhelmet", furARMOR, 4, 0);
        legsFur = new MoCItemArmor("furlegs", furARMOR, 4, 2);
        bootsFur = new MoCItemArmor("furboots", furARMOR, 4, 3);

        heartdarkness = new MoCItem("heartdarkness");
        heartfire = new MoCItem("heartfire");
        heartundead = new MoCItem("heartundead");
        ostrichmeat = new MoCItemFood("ostrichraw", 2, 0.3F, false).setPotionEffect(Potion.hunger.id, 30, 0, 0.8F);
        ostrichcooked = new MoCItemFood("ostrichcooked", 6, 0.6F, false);
        unicorn = new MoCItem("unicorn");

        fishnet = new MoCItemPetAmulet("fishnet");
        MoCItemID++;
        horsearmorcrystal = new MoCItem("armorcrystal");
        MoCItemID++;

        rawTurkey = new MoCItemFood("turkeyraw", 3, 0.3F, false).setPotionEffect(Potion.hunger.id, 30, 0, 0.8F);
        cookedTurkey = new MoCItemFood("turkeycooked", 8, 0.6F, false);
        animalHide = new MoCItem("hide");
        plateHide = new MoCItemArmor("hideplate", hideARMOR, 4, 1);
        helmetHide = new MoCItemArmor("hidehelmet", hideARMOR, 4, 0);
        legsHide = new MoCItemArmor("hidelegs", hideARMOR, 4, 2);
        bootsHide = new MoCItemArmor("hideboots", hideARMOR, 4, 3);
        ratRaw = new MoCItemFood("ratraw", 2, 0.3F, false).setPotionEffect(Potion.hunger.id, 30, 0, 0.8F);
        ratCooked = new MoCItemFood("ratcooked", 4, 0.6F, false);
        ratBurger = new MoCItemFood("ratburger", 8, 0.6F, false);

        chitinCave = new MoCItem("chitinblack");
        chitinFrost = new MoCItem("chitinfrost");
        chitinNether = new MoCItem("chitinnether");
        chitinDirt = new MoCItem("chitin");

        swordCave = new MoCItemWeapon("swordscorpioncave", ToolMaterial.IRON, 4, false);
        swordFrost = new MoCItemWeapon("swordscorpionfrost", ToolMaterial.IRON, 2, false);
        swordNether = new MoCItemWeapon("swordscorpionnether", ToolMaterial.IRON, 3, false);
        swordDirt = new MoCItemWeapon("swordscorpion", ToolMaterial.IRON, 1, false);

        plateScorpDirt = new MoCItemArmor("scorpplatedirt", crocARMOR, 4, 1);
        helmetScorpDirt = new MoCItemArmor("scorphelmetdirt", crocARMOR, 4, 0);
        legsScorpDirt = new MoCItemArmor("scorplegsdirt", crocARMOR, 4, 2);
        bootsScorpDirt = new MoCItemArmor("scorpbootsdirt", crocARMOR, 4, 3);

        plateScorpFrost = new MoCItemArmor("scorpplatefrost", crocARMOR, 4, 1);
        helmetScorpFrost = new MoCItemArmor("scorphelmetfrost", crocARMOR, 4, 0);
        legsScorpFrost = new MoCItemArmor("scorplegsfrost", crocARMOR, 4, 2);
        bootsScorpFrost = new MoCItemArmor("scorpbootsfrost", crocARMOR, 4, 3);

        plateScorpCave = new MoCItemArmor("scorpplatecave", crocARMOR, 4, 1);
        helmetScorpCave = new MoCItemArmor("scorphelmetcave", crocARMOR, 4, 0);
        legsScorpCave = new MoCItemArmor("scorplegscave", crocARMOR, 4, 2);
        bootsScorpCave = new MoCItemArmor("scorpbootscave", crocARMOR, 4, 3);

        plateScorpNether = new MoCItemArmor("scorpplatenether", crocARMOR, 4, 1);
        helmetScorpNether = new MoCItemArmor("scorphelmetnether", crocARMOR, 4, 0);
        legsScorpNether = new MoCItemArmor("scorplegsnether", crocARMOR, 4, 2);
        bootsScorpNether = new MoCItemArmor("scorpbootsnether", crocARMOR, 4, 3);

        stingCave = new MoCItemWeapon("stingcave", ToolMaterial.GOLD, 4, true);
        stingFrost = new MoCItemWeapon("stingfrost", ToolMaterial.GOLD, 2, true);
        stingNether = new MoCItemWeapon("stingnether", ToolMaterial.GOLD, 3, true);
        stingDirt = new MoCItemWeapon("stingdirt", ToolMaterial.GOLD, 1, true);

        scrollFreedom = new MoCItem("scrolloffreedom");
        scrollOfSale = new MoCItem("scrollofsale");
        
        woodTusks = new MoCItemWeapon("tuskswood", ToolMaterial.WOOD);
        ironTusks = new MoCItemWeapon("tusksiron", ToolMaterial.IRON);
        diamondTusks = new MoCItemWeapon("tusksdiamond", ToolMaterial.EMERALD);
        harness = new MoCItem("elephantharness");
        chestSet = new MoCItem("elephantchest");
        garment = new MoCItem("elephantgarment");
        howdah = new MoCItem("elephanthowdah");
        platform = new MoCItem("elephantplatform");
        
        crabmeat = new MoCItemFood("crabmeat", 2, 0.3F, false).setPotionEffect(Potion.hunger.id, 30, 0, 0.8F);
        crabmeatcooked = new MoCItemFood("crabmeatcooked", 6, 0.6F, false);
        swordsilver = new MoCItemWeapon("swordsilver", this.SILVER);
        
        multiBlockNames.add ("WyvernLair");
        multiBlockNames.add("OgreLair");
        
        staffPortal = new ItemStaffPortal("staffportal");
        LanguageRegistry.addName(staffPortal, "Wyvern Portal Staff");
        
        scrollOfOwner = new MoCItem("scrollofowner");
        
        superAmulet = new MoCItemPetAmulet("superamulet", 1);

        //new blocks
        mocStone = new MoCBlockRock("MoCStone").setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundTypeStone);
        //GameRegistry.registerBlock(mocStone, MultiItemBlock.class, "MoC_Stone");

        mocGrass = new MoCBlockGrass("MoCGrass").setHardness(0.5F).setStepSound(Block.soundTypeGrass);
        //GameRegistry.registerBlock(mocGrass, MultiItemBlock.class, "MoC_Grass");
        
        mocDirt = new MoCBlockDirt("MoCDirt").setHardness(0.6F).setStepSound(Block.soundTypeGravel);
        //GameRegistry.registerBlock(mocDirt, MultiItemBlock.class, "MoC_Dirt");
        
        //non terrain generator blocks
       
        mocLeaf = new MoCBlockLeaf("MoCLeaves").setHardness(0.2F).setLightOpacity(1).setStepSound(Block.soundTypeGrass);
        //GameRegistry.registerBlock(mocLeaf, MultiItemBlock.class, "MoC_Leaf");
        
        mocLog = new MoCBlockLog("MoCLog").setHardness(2.0F).setStepSound(Block.soundTypeWood);
        //GameRegistry.registerBlock(mocLog, MultiItemBlock.class, "MoC_Log");
        
        mocTallGrass = new MoCBlockTallGrass("MoCTallGrass", true).setHardness(0.0F).setStepSound(Block.soundTypeGrass);
        //GameRegistry.registerBlock(mocTallGrass, MultiItemBlock.class, "MoC_TallGrass");
        
        mocPlank = new MoCBlockPlanks("MoCWoodPlanks").setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood);
        //GameRegistry.registerBlock(mocPlank, MultiItemBlock.class, "MoC_Planks");

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
        mocDirt.setHarvestLevel("shovel", 0, 0); 
        mocGrass.setHarvestLevel("shovel", 0, 0); 
        mocStone.setHarvestLevel("pickaxe", 1, 0);
        
        LanguageRegistry.instance().addStringLocalization("itemGroup.MoCreaturesTab", "en_US", "MoCreatures Misc");
        proxy.mocSettingsConfig.save();
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
        //LanguageRegistry.addName(rope, "Rope");
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

        //LanguageRegistry.addName(horsearmormetal, "Iron Mount Armor");
        // LanguageRegistry.addName(horsearmorgold, "Gold Mount Armor");
        //LanguageRegistry.addName(horsearmordiamond, "Diamond Mount Armor");
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
            else if (i > 49 && i < 62)
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
        GameRegistry.addSmelting(MoCreatures.crabmeat, new ItemStack(MoCreatures.crabmeatcooked, 1), 0F);
        
        GameRegistry.addSmelting(MoCreatures.ratRaw, new ItemStack(MoCreatures.ratCooked, 1), 0F);

        GameRegistry.addSmelting(MoCreatures.ostrichmeat, new ItemStack(MoCreatures.ostrichcooked, 1), 0F);

        GameRegistry.addSmelting(MoCreatures.rawTurkey, new ItemStack(MoCreatures.cookedTurkey, 1), 0F);

        GameRegistry.addSmelting(MoCreatures.fishyegg, new ItemStack(MoCreatures.omelet, 1), 0F);

        GameRegistry.addSmelting(Items.egg, new ItemStack(MoCreatures.omelet, 1), 0F);

        GameRegistry.addShapelessRecipe(new ItemStack(scrollFreedom, 1), new Object[] { Items.paper, Items.feather, Items.redstone });
        
        GameRegistry.addShapelessRecipe(new ItemStack(scrollFreedom, 1), new Object[] { scrollOfSale, Items.redstone });
        
        GameRegistry.addShapelessRecipe(new ItemStack(scrollOfSale, 1), new Object[] { Items.paper, Items.feather });
        
        
        GameRegistry.addShapelessRecipe(new ItemStack(Items.leather, 1), new Object[] { animalHide });

        GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool, 1), new Object[] { fur });

        GameRegistry.addShapelessRecipe(new ItemStack(swordNether, 1), new Object[] { Items.diamond_sword, stingNether, stingNether, stingNether });

        GameRegistry.addShapelessRecipe(new ItemStack(swordFrost, 1), new Object[] { Items.diamond_sword, stingFrost, stingFrost, stingFrost });

        GameRegistry.addShapelessRecipe(new ItemStack(swordCave, 1), new Object[] { Items.diamond_sword, stingCave, stingCave, stingCave });

        GameRegistry.addShapelessRecipe(new ItemStack(swordDirt, 1), new Object[] { Items.diamond_sword, stingDirt, stingDirt, stingDirt });

        GameRegistry.addShapelessRecipe(new ItemStack(turtlesoup, 1), new Object[] { new ItemStack(turtlemeat, 1), new ItemStack(Items.bowl, 1) });

        GameRegistry.addShapelessRecipe(new ItemStack(viallight, 1), new Object[] { vialundead, vialnightmare, vialdarkness });

        GameRegistry.addRecipe(new ItemStack(fishnet, 1), new Object[] { " # ", "S#S", "#S#", Character.valueOf('#'), Items.string, Character.valueOf('S'), sharkteeth });
        
        GameRegistry.addRecipe(new ItemStack(woodTusks, 1), new Object[] { "X  ", "XR ", "XXX", Character.valueOf('X'), Blocks.planks, Character.valueOf('R'), Items.lead  });
        
        GameRegistry.addRecipe(new ItemStack(ironTusks, 1), new Object[] { "X  ", "XR ", "XXX", Character.valueOf('X'), Items.iron_ingot, Character.valueOf('R'), Items.lead });
        
        GameRegistry.addRecipe(new ItemStack(diamondTusks, 1), new Object[] { "X  ", "XR ", "XXX", Character.valueOf('X'), Items.diamond, Character.valueOf('R'), Items.lead });
        
        GameRegistry.addRecipe(new ItemStack(platform, 1), new Object[] { "WRW", "PPP", "WRW",  Character.valueOf('W'), Blocks.log, Character.valueOf('R'), Items.lead ,  Character.valueOf('P'), Blocks.planks});
        
        GameRegistry.addRecipe(new ItemStack(chestSet, 1), new Object[] { " W ", "CHC", " W ", Character.valueOf('H'), animalHide, Character.valueOf('W'), new ItemStack(Blocks.wool, 1, 0), Character.valueOf('C'), Blocks.chest });
        
        GameRegistry.addRecipe(new ItemStack(harness, 1), new Object[] { "HWH", "IWI", "HWH", Character.valueOf('H'), animalHide, Character.valueOf('W'), new ItemStack(Blocks.wool, 1, 0), Character.valueOf('I'), Items.iron_ingot });
        
        GameRegistry.addRecipe(new ItemStack(howdah, 1), new Object[] { "SRS", "RYR", "SRS", Character.valueOf('S'), Items.stick, Character.valueOf('R'), new ItemStack(Blocks.wool, 1, 14), Character.valueOf('Y'), new ItemStack(Blocks.wool, 1, 4) });

        GameRegistry.addRecipe(new ItemStack(garment, 1), new Object[] { "pyg", "RMR", "BYB", Character.valueOf('R'), new ItemStack(Blocks.wool, 1, 14), Character.valueOf('Y'), new ItemStack(Blocks.wool, 1, 4), 
        Character.valueOf('B'), new ItemStack(Blocks.wool, 1, 11), Character.valueOf('M'), medallion,
        Character.valueOf('p'), new ItemStack(Items.dye, 1, 9), Character.valueOf('y'), new ItemStack(Items.dye, 1, 11),
        Character.valueOf('g'), new ItemStack(Items.dye, 1, 10)
        });

        //Items.dye.itemID
        GameRegistry.addRecipe(new ItemStack(ratBurger, 1), new Object[] { "SB ", "GRG", " B ", Character.valueOf('R'), ratCooked, Character.valueOf('B'), Items.bread, Character.valueOf('S'), Items.pumpkin_seeds, Character.valueOf('G'), Items.wheat_seeds });

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

        GameRegistry.addRecipe(new ItemStack(horsearmorcrystal, 1), new Object[] { "  D", "CDC", "DCD", Character.valueOf('D'), Items.diamond, Character.valueOf('C'), Blocks.glass });

        //GameRegistry.addRecipe(new ItemStack(horsearmormetal, 1), new Object[] { "  X", "XYX", "XXX", Character.valueOf('X'), Item.ingotIron, Character.valueOf('Y'), new ItemStack(Blocks.wool, 1, 15) });

        //GameRegistry.addRecipe(new ItemStack(horsearmorgold, 1), new Object[] { "  X", "XYX", "XXX", Character.valueOf('X'), Item.ingotGold, Character.valueOf('Y'), new ItemStack(Blocks.wool, 1, 14) });

        //GameRegistry.addRecipe(new ItemStack(horsearmordiamond, 1), new Object[] { "  X", "XYX", "XXX", Character.valueOf('X'), Item.diamond, Character.valueOf('Y'), new ItemStack(Blocks.wool, 1, 11) });

        GameRegistry.addRecipe(new ItemStack(viallight, 1), new Object[] { "X", "Y", "Z", Character.valueOf('X'), vialundead, Character.valueOf('Y'), vialnightmare, Character.valueOf('Z'), vialdarkness });

        GameRegistry.addRecipe(new ItemStack(vialundead, 1), new Object[] { " X ", " Y ", " Z ", Character.valueOf('X'), Items.rotten_flesh, Character.valueOf('Y'), heartundead, Character.valueOf('Z'), Items.glass_bottle });

        GameRegistry.addRecipe(new ItemStack(vialnightmare, 1), new Object[] { " X ", " Y ", " Z ", Character.valueOf('X'), Items.blaze_powder, Character.valueOf('Y'), heartfire, Character.valueOf('Z'), Items.glass_bottle });

        GameRegistry.addRecipe(new ItemStack(vialnightmare, 1), new Object[] { " X ", " Y ", " Z ", Character.valueOf('X'), Blocks.fire, Character.valueOf('Y'), heartfire, Character.valueOf('Z'), Items.glass_bottle });

        GameRegistry.addRecipe(new ItemStack(vialdarkness, 1), new Object[] { " X ", " Y ", " Z ", Character.valueOf('X'), Items.ender_pearl, Character.valueOf('Y'), heartdarkness, Character.valueOf('Z'), Items.glass_bottle });

        GameRegistry.addRecipe(new ItemStack(plateFur, 1), new Object[] { "X X", "XXX", "XXX", Character.valueOf('X'), fur });

        GameRegistry.addRecipe(new ItemStack(helmetFur, 1), new Object[] { "XXX", "X X", Character.valueOf('X'), fur });

        GameRegistry.addRecipe(new ItemStack(legsFur, 1), new Object[] { "XXX", "X X", "X X", Character.valueOf('X'), fur });

        GameRegistry.addRecipe(new ItemStack(bootsFur, 1), new Object[] { "X X", "X X", Character.valueOf('X'), fur });

        GameRegistry.addRecipe(new ItemStack(key, 1), new Object[] { "  #", " # ", "X  ", Character.valueOf('#'), Items.stick, Character.valueOf('X'), Items.iron_ingot, });

        GameRegistry.addRecipe(new ItemStack(superAmulet, 1), new Object[] { "X X", " Z ", "X X", Character.valueOf('X'), Items.gold_nugget, Character.valueOf('Z'), Items.diamond });

        GameRegistry.addRecipe(new ItemStack(amuletbone, 1), new Object[] { "#X#", "XZX", "#X#", Character.valueOf('#'), Items.bone, Character.valueOf('X'), Items.gold_nugget, Character.valueOf('Z'), Items.ender_pearl });

        GameRegistry.addRecipe(new ItemStack(amuletghost, 1), new Object[] { "#X#", "XZX", "#X#", Character.valueOf('#'), Items.bone, Character.valueOf('X'), Items.gold_nugget, Character.valueOf('Z'), Items.ghast_tear });

        GameRegistry.addRecipe(new ItemStack(amuletfairy, 1), new Object[] { "#X#", "XZX", "#X#", Character.valueOf('#'), Blocks.fire, Character.valueOf('X'), Items.gold_nugget, Character.valueOf('Z'), unicorn });

        GameRegistry.addRecipe(new ItemStack(amuletfairy, 1), new Object[] { "#X#", "XZX", "#X#", Character.valueOf('#'), Blocks.fire, Character.valueOf('X'), Items.gold_nugget, Character.valueOf('Z'), viallight });

        GameRegistry.addRecipe(new ItemStack(amuletpegasus, 1), new Object[] { "#X#", "XZX", "#X#", Character.valueOf('#'), Blocks.fire, Character.valueOf('X'), Items.gold_nugget, Character.valueOf('Z'), Items.diamond });

        GameRegistry.addRecipe(new ItemStack(sharksword, 1), new Object[] { "#X#", "#X#", " X ", Character.valueOf('#'), sharkteeth, Character.valueOf('X'), Items.stick, });

        GameRegistry.addRecipe(new ItemStack(fishbowl_e, 1), new Object[] { "# #", "# #", "###", Character.valueOf('#'), Blocks.glass, });

        //GameRegistry.addRecipe(new ItemStack(rope, 1), new Object[] { "# #", " # ", "# #", Character.valueOf('#'), Item.silk, });

        GameRegistry.addShapelessRecipe(new ItemStack(petfood, 4), new Object[] { new ItemStack(Items.fish, 1), new ItemStack(Items.porkchop, 1) });

        GameRegistry.addRecipe(new ItemStack(woolball, 1), new Object[] { " # ", "# #", " # ", Character.valueOf('#'), Items.string, });

        GameRegistry.addRecipe(new ItemStack(litterbox, 1), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Blocks.planks, Character.valueOf('X'), Blocks.sand, });

        GameRegistry.addRecipe(new ItemStack(medallion, 1), new Object[] { "# #", "XZX", " X ", Character.valueOf('#'), Items.leather, Character.valueOf('Z'), Items.diamond, Character.valueOf('X'), Items.gold_ingot, });

        GameRegistry.addRecipe(new ItemStack(medallion, 1), new Object[] { "# #", " X ", Character.valueOf('#'), Items.leather, Character.valueOf('X'), Items.gold_ingot, });

        GameRegistry.addRecipe(new ItemStack(whip, 1), new Object[] { "#X#", "X X", "# Z", Character.valueOf('#'), bigcatclaw, Character.valueOf('X'), Items.leather, Character.valueOf('Z'), Items.iron_ingot });

        GameRegistry.addRecipe(new ItemStack(horsesaddle, 1), new Object[] { "XXX", "X#X", "# #", Character.valueOf('#'), Items.iron_ingot, Character.valueOf('X'), Items.leather });

        GameRegistry.addRecipe(new ItemStack(haystack, 1), new Object[] { "XXX", "XXX", Character.valueOf('X'), Items.wheat });

        GameRegistry.addRecipe(new ItemStack(Items.wheat, 6), new Object[] { "X", Character.valueOf('X'), haystack });

        GameRegistry.addRecipe(new ItemStack(sugarlump, 1), new Object[] { "XX", "##", Character.valueOf('X'), Items.sugar, Character.valueOf('#'), Items.sugar });

        GameRegistry.addRecipe(new ItemStack(horsesaddle, 1), new Object[] { "X", "#", Character.valueOf('X'), Items.saddle, Character.valueOf('#'), Items.iron_ingot });

        GameRegistry.addRecipe(new ItemStack(Items.chainmail_chestplate, 1), new Object[] { "X X", "XXX", "XXX", Character.valueOf('X'), sharkteeth });

        GameRegistry.addRecipe(new ItemStack(Items.chainmail_helmet, 1), new Object[] { "XXX", "X X", Character.valueOf('X'), sharkteeth });

        GameRegistry.addRecipe(new ItemStack(Items.chainmail_leggings, 1), new Object[] { "XXX", "X X", "X X", Character.valueOf('X'), sharkteeth });

        GameRegistry.addRecipe(new ItemStack(Items.chainmail_boots, 1), new Object[] { "X X", "X X", Character.valueOf('X'), sharkteeth });

        GameRegistry.addRecipe(new ItemStack(plateCroc, 1), new Object[] { "X X", "XXX", "XXX", Character.valueOf('X'), crochide });

        GameRegistry.addRecipe(new ItemStack(helmetCroc, 1), new Object[] { "XXX", "X X", Character.valueOf('X'), crochide });

        GameRegistry.addRecipe(new ItemStack(legsCroc, 1), new Object[] { "XXX", "X X", "X X", Character.valueOf('X'), crochide });

        GameRegistry.addRecipe(new ItemStack(bootsCroc, 1), new Object[] { "X X", "X X", Character.valueOf('X'), crochide });

        for (int i = 0; i < 16; i++)
        {
            GameRegistry.addShapelessRecipe(new ItemStack(kittybed, 1, i), new Object[] { new ItemStack(Items.dye, 1, i), new ItemStack(kittybed, 1) });

            GameRegistry.addRecipe(new ItemStack(kittybed, 1, i), new Object[] { "###", "#X#", "Z  ", Character.valueOf('#'), Blocks.planks, Character.valueOf('X'), new ItemStack(Blocks.wool, 1, MoCTools.colorize(i)), Character.valueOf('Z'), Items.iron_ingot, });
            String s = ItemDye.field_150923_a[i];
            s = s.substring(0, 1).toUpperCase() + s.substring(1);
            LanguageRegistry.addName(new ItemStack(kittybed, 1, i), (s + " Kitty Bed"));
        }
        
        for (int i = 0; i < multiBlockNames.size(); i++) 
        {
            GameRegistry.addShapelessRecipe(new ItemStack(mocPlank, 4, i), new Object[] { new ItemStack(mocLog, 1, i)});
        }
        
        GameRegistry.addRecipe(new ItemStack(staffPortal, 1), new Object[] { "  E", " U ", "R  ", Character.valueOf('E'), Items.ender_eye, Character.valueOf('U'), unicorn, Character.valueOf('R'), Items.blaze_rod });
        
        GameRegistry.addRecipe(new ItemStack(staffPortal, 1), new Object[] { "  E", " U ", "R  ", Character.valueOf('E'), Items.ender_eye, Character.valueOf('U'), viallight, Character.valueOf('R'), Items.blaze_rod });
        
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

    public static void updateSettings()
    {
        proxy.readGlobalConfigValues();
    }

    public static boolean isServer()
    {
        return (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER);
    }

    public static boolean isHuntingEnabled() 
    {
        return proxy.forceDespawns;
    }
}