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
import drzhark.mocreatures.item.ItemBuilderHammer;
import drzhark.mocreatures.item.ItemStaffTeleport;
import drzhark.mocreatures.item.ItemStaffPortal;
import drzhark.mocreatures.item.MoCItem;
import drzhark.mocreatures.item.MoCItemAmulet;
import drzhark.mocreatures.item.MoCItemArmor;
import drzhark.mocreatures.item.MoCItemEgg;
import drzhark.mocreatures.item.MoCItemFishBowl;
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

@Mod(modid = "MoCreatures", name = "DrZhark's Mo'Creatures", version = "5.1.5")
@NetworkMod(clientSideRequired = true, serverSideRequired = false,
clientPacketHandlerSpec = @SidedPacketHandler(channels = { "MoCreatures" }, packetHandler = MoCClientPacketHandler.class), serverPacketHandlerSpec = @SidedPacketHandler(channels = { "MoCreatures" }, packetHandler = MoCServerPacketHandler.class))
public class MoCreatures {

    @Instance("MoCreatures")
    public static MoCreatures instance;

    @SidedProxy(clientSide = "drzhark.mocreatures.client.MoCClientProxy", serverSide = "drzhark.mocreatures.MoCProxy")
    public static MoCProxy proxy;
    public static CustomSpawner myCustomSpawner;
    public static final CreativeTabs tabMoC = new CreativeTabs(CreativeTabs.creativeTabArray.length, "MoCreaturesTab");
    
    /**
     * ITEMS
     */
    static int MoCItemID;// = 8772;
    public static int WyvernLairDimensionID; //17;
    //public static int MoCBlockID;//
    //public static Block wyvernlairportal;
    public static Block mocStone;
    public static Block mocGrass;
    public static Block mocDirt;
    public static Block mocLeaf;
    public static Block mocLog;
    public static Block mocPlank;
    public static Block mocTallGrass;
    //public static Block mocBlockFarm;
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
    //recordshuffle.ID will be: 4098 (MoCItemID - 256)
    //public static Item                creaturepedia;
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

    public static Logger log;

    @PreInit
    public void preInit(FMLPreInitializationEvent event)
    {
        MinecraftForge.TERRAIN_GEN_BUS.register(new MoCEventHooks()); // register our event subscriptions
        log = event.getModLog();
        // check if config exists, if not then copy default
        File mocConfigFile = new File(event.getSuggestedConfigurationFile().getParent() + File.separator + "MoCProperties.cfg");

        if (!mocConfigFile.exists())
        {
            log.info("could not find MoCProperties.cfg, copying defaults...");
            try {
                InputStream is = getClass().getResourceAsStream("/drzhark/mocreatures/resources/MoCProperties.cfg");
                OutputStream out = new FileOutputStream(mocConfigFile);
                int read = 0;
                byte[] bytes = new byte[1024];

                while ((read = is.read(bytes)) != -1) {
                out.write(bytes, 0, read);
                }

                is.close();
                out.flush();
                out.close();

                log.info("MoCPropeties.cfg has been created successfully!");
            } catch (IOException e) {
                log.warning("unable to create MoCProperties.cfg!!");
                e.printStackTrace();
            }
        }
        proxy.ConfigInit(event);
        proxy.initSounds();
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
        
        DimensionManager.registerProviderType(WyvernLairDimensionID, WorldProviderWyvernEnd.class, false);
    }

    private void AddEntities()
    {
        registerEntity(MoCEntityBunny.class, "Bunny", 12623485, 9141102);//, 0x05600, 0x006500);
        registerEntity(MoCEntitySnake.class, "Snake", 14020607, 13749760);//, 0x05800, 0x006800);
        registerEntity(MoCEntityTurtle.class, "Turtle", 14772545, 9320590);//, 0x04800, 0x004500);
        registerEntity(MoCEntityBird.class, "Bird", 14020607, 14020607);// 0x03600, 0x003500);
        registerEntity(MoCEntityMouse.class, "Mouse", 14772545, 0);//, 0x02600, 0x002500);
        registerEntity(MoCEntityTurkey.class, "Turkey", 14020607, 16711680);//, 0x2600, 0x052500);
        registerEntity(MoCEntityHorse.class, "Horse", 12623485, 15656192);//, 0x2600, 0x052500);
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
        //registerEntity(MoCEntityOgre.class, "OgreNew", 16711680, 65407);
        registerEntity(MoCEntityRoach.class, "Roach", 65407, 13749760);
        registerEntity(MoCEntityMaggot.class, "Maggot", 65407, 9141102);
        registerEntity(MoCEntityCrab.class, "Crab", 65407, 13749760);
        
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
        proxy.ConfigPostInit(event);
        DimensionManager.registerDimension(WyvernLairDimensionID, WyvernLairDimensionID);
    }

 // CustomSpawner must be initialized here to avoid vanilla spawn lists being populated during world gen
    @ServerAboutToStart
    public void serverAboutToStart(FMLServerAboutToStartEvent event)
    {
        // initialized here to support all custom biomes
        if (proxy.useCustomSpawner)
        {
            myCustomSpawner = new CustomSpawner();
        }
        // Handle any biomes that weren't loaded
        proxy.initializeBiomes();
        proxy.initializeEntities();
        updateSettings(); // refresh settings
    }

    @ServerStarting
    public void serverStarting(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new CommandMoCreatures());
    }
    
    /**
     * For Litterbox and kittybeds
     * 
     * @param entityClass
     * @param entityName
     */
    protected void registerEntity(Class<? extends Entity> entityClass, String entityName)
    {
        int entityID = EntityRegistry.findGlobalUniqueEntityId();
        LanguageRegistry.instance().addStringLocalization("entity." + entityName + ".name", "en_US", entityName);
        //private static EntityEggInfo searchEggColor(Class entityClass , int entityId)
        //EntityEggInfo eggColors = MoCEggColour.searchEggColor(entityClass, entityID);
        EntityRegistry.registerGlobalEntityID(entityClass, entityName, entityID);
        EntityRegistry.registerModEntity(entityClass, entityName, entityID, instance, 128, 1, true);
    }

    private void registerEntity(Class<? extends Entity> entityClass, String entityName, int eggColor, int eggDotsColor, String visibleName)
    {
        int entityID = EntityRegistry.findGlobalUniqueEntityId();
        LanguageRegistry.instance().addStringLocalization("entity." + entityName + ".name", "en_US", visibleName);
        EntityRegistry.registerGlobalEntityID(entityClass, entityName, entityID, eggColor, eggDotsColor);
        EntityRegistry.registerModEntity(entityClass, entityName, entityID, instance, 128, 1, true);
    }

    private void registerEntity(Class<? extends Entity> entityClass, String entityName, int eggColor, int eggDotsColor)
    {
        registerEntity(entityClass, entityName, eggColor, eggDotsColor, entityName);
    }

    protected void InitItems()
    {
        MoCItemID = proxy.itemID;//8772;//((Integer) mocitemidA.get()).intValue();

        WyvernLairDimensionID = proxy.WyvernDimension;//17
        //MoCBlockID = proxy.getBlockID();
        
        recordshuffle = (new MoCItemRecord(MoCItemID++, "shuffling")).setUnlocalizedName("recordshuffle");//setIconIndex(89).setItemName("recordshuffle");
        //recordshuffle.ID will be: (MoCItemID + 256)
        horsesaddle = (new MoCItemHorseSaddle(MoCItemID++)).setUnlocalizedName("horsesaddle");//.setIconIndex(0).setItemName("HorseSaddle");
        horsearmormetal = (new MoCItem(MoCItemID++)).setUnlocalizedName("armormetal");//.setIconIndex(73).setItemName("horsearmormetal");
        sharkteeth = (new MoCItem(MoCItemID++)).setUnlocalizedName("sharkteeth");//.setIconIndex(3).setItemName("sharkteeth");
        haystack = (new MoCItemHayStack(MoCItemID++)).setUnlocalizedName("haystack");//.setIconIndex(1).setItemName("HayStack");
        sugarlump = (new MoCItemSugarLump(MoCItemID++)).setUnlocalizedName("sugarlump");//.setIconIndex(2).setItemName("SugarLump");
        fishyegg = (new MoCItemEgg(MoCItemID++)).setUnlocalizedName("moc_egg");//.setIconIndex(16).setItemName("fishyegg");
        bigcatclaw = (new MoCItem(MoCItemID++)).setUnlocalizedName("bigcatclaw");//.setIconIndex(4).setItemName("bigcatclaw");
        whip = (new MoCItemWhip(MoCItemID++)).setUnlocalizedName("whip");//.setIconIndex(5).setItemName("whip");
        horsearmorgold = (new MoCItem(MoCItemID++)).setUnlocalizedName("armorgold");//.setIconIndex(74).setItemName("horsearmorgold");
        horsearmordiamond = (new MoCItem(MoCItemID++)).setUnlocalizedName("armordiamond");//.setIconIndex(75).setItemName("horsearmordiamond");
        staffTeleport = (new ItemStaffTeleport(MoCItemID++)).setUnlocalizedName("staffteleport");//.setIconIndex(70).setItemName("staffteleporter");
        LanguageRegistry.addName(staffTeleport, "Teleport Staff");
        
        //staffunicorn        = (new MoCItem(MoCItemID++)).setIconIndex(71).setItemName("staffunicorn");
        medallion = (new MoCItem(MoCItemID++)).setUnlocalizedName("medallion");//.setIconIndex(6).setItemName("medallion");
        kittybed = (new MoCItemKittyBed(MoCItemID++, 0)).setUnlocalizedName("catbed");//.setIconIndex(17).setItemName("kittybed");
        litterbox = (new MoCItemLitterBox(MoCItemID++)).setUnlocalizedName("catlitter");//.setIconIndex(7).setItemName("litterbox");
        woolball = (new MoCItem(MoCItemID++)).setUnlocalizedName("woolball");//.setIconIndex(8).setItemName("woolball");
        rope = (new MoCItem(MoCItemID++)).setUnlocalizedName("rope");//.setIconIndex(9).setItemName("rope");
        petfood = (new MoCItem(MoCItemID++)).setUnlocalizedName("petfood");//.setIconIndex(10).setItemName("petfood");
        builderHammer = (new ItemBuilderHammer(MoCItemID++)).setUnlocalizedName("builderhammer");//.setIconIndex(70).setItemName("staffteleporter");
        LanguageRegistry.addName(builderHammer, "Builder Hammer");
        
        //staffdiamond        = (new MoCItem(MoCItemID++)).setIconIndex(72).setItemName("staffdiamond");
        crochide = (new MoCItem(MoCItemID++)).setUnlocalizedName("reptilehide");//.setIconIndex(11).setItemName("crochide");
        plateCroc = (new MoCItemArmor(MoCItemID++, crocARMOR, 4, 1)).setUnlocalizedName("reptileplate");//.setIconIndex(12).setItemName("plateCroc");
        helmetCroc = (new MoCItemArmor(MoCItemID++, crocARMOR, 4, 0)).setUnlocalizedName("reptilehelmet");//.setIconIndex(13).setItemName("helmetCroc");
        legsCroc = (new MoCItemArmor(MoCItemID++, crocARMOR, 4, 2)).setUnlocalizedName("reptilelegs");//.setIconIndex(14).setItemName("legsCroc");
        bootsCroc = (new MoCItemArmor(MoCItemID++, crocARMOR, 4, 3)).setUnlocalizedName("reptileboots");//.setIconIndex(15).setItemName("bootsCroc");
        fishbowl_e = (new MoCItemFishBowl(MoCItemID++, 0)).setUnlocalizedName("bowlempty");//.setIconIndex(32).setItemName("fishbowle");
        fishbowl_w = (new MoCItemFishBowl(MoCItemID++, 11)).setUnlocalizedName("bowlwater");//.setIconIndex(33).setItemName("fishbowlw");
        fishbowl_1 = (new MoCItemFishBowl(MoCItemID++, 1)).setUnlocalizedName("bowlfish1");//.setIconIndex(34).setItemName("fishbowl1");
        fishbowl_2 = (new MoCItemFishBowl(MoCItemID++, 2)).setUnlocalizedName("bowlfish2");//.setIconIndex(35).setItemName("fishbowl2");
        fishbowl_3 = (new MoCItemFishBowl(MoCItemID++, 3)).setUnlocalizedName("bowlfish3");//.setIconIndex(36).setItemName("fishbowl3");
        fishbowl_4 = (new MoCItemFishBowl(MoCItemID++, 4)).setUnlocalizedName("bowlfish4");//.setIconIndex(37).setItemName("fishbowl4");
        fishbowl_5 = (new MoCItemFishBowl(MoCItemID++, 5)).setUnlocalizedName("bowlfish5");//.setIconIndex(38).setItemName("fishbowl5");
        fishbowl_6 = (new MoCItemFishBowl(MoCItemID++, 6)).setUnlocalizedName("bowlfish6");//.setIconIndex(39).setItemName("fishbowl6");
        fishbowl_7 = (new MoCItemFishBowl(MoCItemID++, 7)).setUnlocalizedName("bowlfish7");//.setIconIndex(40).setItemName("fishbowl7");
        fishbowl_8 = (new MoCItemFishBowl(MoCItemID++, 8)).setUnlocalizedName("bowlfish8");//.setIconIndex(41).setItemName("fishbowl8");
        fishbowl_9 = (new MoCItemFishBowl(MoCItemID++, 9)).setUnlocalizedName("bowlfish9");//.setIconIndex(42).setItemName("fishbowl9");
        fishbowl_10 = (new MoCItemFishBowl(MoCItemID++, 10)).setUnlocalizedName("bowlfish10");//.setIconIndex(43).setItemName("fishbowl10");

        fur = (new MoCItem(MoCItemID++)).setUnlocalizedName("fur");//.setIconIndex(18).setItemName("fur");
        omelet = (new MoCItemFood(MoCItemID++, 4, 0.6F, false)).setUnlocalizedName("omelet");//.setIconIndex(19).setItemName("omelet");
        turtlemeat = (new MoCItemFood(MoCItemID++, 2, 0.3F, false)).setUnlocalizedName("turtlemeat");//.setPotionEffect(Potion.hunger.id, 30, 0, 0.8F).setIconIndex(20).setItemName("turtlemeat");
        turtlesoup = (new MoCItemTurtleSoup(MoCItemID++, 6, 0.6F, false)).setUnlocalizedName("turtlesoup");//.setIconIndex(21).setItemName("turtlesoup");

        nunchaku = (new MoCItemWeapon(MoCItemID++, EnumToolMaterial.IRON)).setUnlocalizedName("nunchaku");//.setIconIndex(64).setItemName("nunchaku");
        sai = (new MoCItemWeapon(MoCItemID++, EnumToolMaterial.IRON)).setUnlocalizedName("sai");//.setIconIndex(65).setItemName("sai");
        bo = (new MoCItemWeapon(MoCItemID++, EnumToolMaterial.IRON)).setUnlocalizedName("bo");//.setIconIndex(66).setItemName("bo");
        katana = (new MoCItemWeapon(MoCItemID++, EnumToolMaterial.IRON)).setUnlocalizedName("katana");//.setIconIndex(67).setItemName("katana");
        sharksword = (new MoCItemWeapon(MoCItemID++, EnumToolMaterial.IRON)).setUnlocalizedName("swordshark");//.setIconIndex(68).setItemName("sharksword");

        key = (new MoCItem(MoCItemID++)).setUnlocalizedName("key");//.setIconIndex(22).setItemName("key");
        vialdarkness = (new MoCItem(MoCItemID++)).setUnlocalizedName("essencedarkness");//.setIconIndex(23).setItemName("darkness");
        vialnightmare = (new MoCItem(MoCItemID++)).setUnlocalizedName("essencefire");//.setIconIndex(24).setItemName("nightmare");
        amuletbone = (new MoCItemAmulet(MoCItemID++)).setUnlocalizedName("amuletbone");//.setIconIndex(25).setItemName("amuletbone");
        amuletbonefull = (new MoCItemAmulet(MoCItemID++)).setUnlocalizedName("amuletbonefull");//.setIconIndex(26).setItemName("amuletbonefull");
        amuletghost = (new MoCItemAmulet(MoCItemID++)).setUnlocalizedName("amuletghost");//.setIconIndex(27).setItemName("amuletghost");
        amuletghostfull = (new MoCItemAmulet(MoCItemID++)).setUnlocalizedName("amuletghostfull");//.setIconIndex(28).setItemName("amuletghostfull");
        amuletfairy = (new MoCItemAmulet(MoCItemID++)).setUnlocalizedName("amuletfairy");//.setIconIndex(85).setItemName("amuletfairy");
        amuletfairyfull = (new MoCItemAmulet(MoCItemID++)).setUnlocalizedName("amuletfairyfull");//.setIconIndex(86).setItemName("amuletfairyfull");
        amuletpegasus = (new MoCItemAmulet(MoCItemID++)).setUnlocalizedName("amuletpegasus");//.setIconIndex(87).setItemName("amuletpegasus");
        amuletpegasusfull = (new MoCItemAmulet(MoCItemID++)).setUnlocalizedName("amuletpegasusfull");//.setIconIndex(88).setItemName("amuletpegasusfull");

        vialundead = (new MoCItem(MoCItemID++)).setUnlocalizedName("essenceundead");//.setIconIndex(44).setItemName("undead");
        viallight = (new MoCItem(MoCItemID++)).setUnlocalizedName("essencelight");//.setIconIndex(31).setItemName("light");

        plateFur = (new MoCItemArmor(MoCItemID++, furARMOR, 4, 1)).setUnlocalizedName("furplate");//.setIconIndex(60).setItemName("plateFur");
        helmetFur = (new MoCItemArmor(MoCItemID++, furARMOR, 4, 0)).setUnlocalizedName("furhelmet");//.setIconIndex(61).setItemName("helmetFur");
        legsFur = (new MoCItemArmor(MoCItemID++, furARMOR, 4, 2)).setUnlocalizedName("furlegs");//.setIconIndex(62).setItemName("legsFur");
        bootsFur = (new MoCItemArmor(MoCItemID++, furARMOR, 4, 3)).setUnlocalizedName("furboots");//.setIconIndex(63).setItemName("bootsFur");

        heartdarkness = (new MoCItem(MoCItemID++)).setUnlocalizedName("heartdarkness");//.setIconIndex(80).setItemName("heartdarkness");
        heartfire = (new MoCItem(MoCItemID++)).setUnlocalizedName("heartfire");//.setIconIndex(82).setItemName("heartfire");
        heartundead = (new MoCItem(MoCItemID++)).setUnlocalizedName("heartundead");//.setIconIndex(81).setItemName("heartundead");
        ostrichmeat = (new MoCItemFood(MoCItemID++, 2, 0.3F, false)).setPotionEffect(Potion.hunger.id, 30, 0, 0.8F).setUnlocalizedName("ostrichraw");//.setPotionEffect(Potion.hunger.id, 30, 0, 0.8F).setIconIndex(83).setItemName("ostrichmeat");
        ostrichcooked = (new MoCItemFood(MoCItemID++, 6, 0.6F, false)).setUnlocalizedName("ostrichcooked");//.setIconIndex(84).setItemName("ostrichcooked");
        unicorn = (new MoCItem(MoCItemID++)).setUnlocalizedName("unicorn");//.setIconIndex(47).setItemName("unicorn");
        MoCItemID++;
        MoCItemID++;
        horsearmorcrystal = (new MoCItem(MoCItemID++)).setUnlocalizedName("armorcrystal");//.setIconIndex(76).setItemName("horsearmorcrystal");

        MoCItemID++;
        //creaturepedia     = (new MoCItemCreaturePedia(MoCItemID++)).setIconIndex(90).setItemName("creaturepedia");

        rawTurkey = (new MoCItemFood(MoCItemID++, 3, 0.3F, false)).setPotionEffect(Potion.hunger.id, 30, 0, 0.8F).setUnlocalizedName("turkeyraw");//.setIconIndex(91).setItemName("rawTurkey");
        cookedTurkey = (new MoCItemFood(MoCItemID++, 8, 0.6F, false)).setUnlocalizedName("turkeycooked");//.setIconIndex(92).setItemName("cookedTurkey");
        animalHide = (new MoCItem(MoCItemID++)).setUnlocalizedName("hide");//.setIconIndex(93).setItemName("animalHide");
        plateHide = (new MoCItemArmor(MoCItemID++, hideARMOR, 4, 1)).setUnlocalizedName("hideplate");//.setIconIndex(94).setItemName("plateHide");
        helmetHide = (new MoCItemArmor(MoCItemID++, hideARMOR, 4, 0)).setUnlocalizedName("hidehelmet");//.setIconIndex(95).setItemName("helmetHide");
        legsHide = (new MoCItemArmor(MoCItemID++, hideARMOR, 4, 2)).setUnlocalizedName("hidelegs");//.setIconIndex(96).setItemName("legsHide");
        bootsHide = (new MoCItemArmor(MoCItemID++, hideARMOR, 4, 3)).setUnlocalizedName("hideboots");//.setIconIndex(97).setItemName("bootsHide");
        ratRaw = (new MoCItemFood(MoCItemID++, 2, 0.3F, false)).setPotionEffect(Potion.hunger.id, 30, 0, 0.8F).setUnlocalizedName("ratraw");//.setIconIndex(77).setItemName("ratRaw");
        ratCooked = (new MoCItemFood(MoCItemID++, 4, 0.6F, false)).setUnlocalizedName("ratcooked");//.setIconIndex(78).setItemName("ratCooked");
        ratBurger = (new MoCItemFood(MoCItemID++, 8, 0.6F, false)).setUnlocalizedName("ratburger");//.setIconIndex(79).setItemName("ratBurger");

        chitinCave = (new MoCItem(MoCItemID++)).setUnlocalizedName("chitinblack");//.setIconIndex(98).setItemName("chitinCave");
        chitinFrost = (new MoCItem(MoCItemID++)).setUnlocalizedName("chitinfrost");//.setIconIndex(99).setItemName("chitinFrost");
        chitinNether = (new MoCItem(MoCItemID++)).setUnlocalizedName("chitinnether");//.setIconIndex(100).setItemName("chitinNether");
        chitinDirt = (new MoCItem(MoCItemID++)).setUnlocalizedName("chitin");//.setIconIndex(101).setItemName("chitinDirt");

        swordCave = (new MoCItemWeapon(MoCItemID++, EnumToolMaterial.IRON, 4, false)).setUnlocalizedName("swordscorpioncave");//.setIconIndex(102).setItemName("swordCave");
        swordFrost = (new MoCItemWeapon(MoCItemID++, EnumToolMaterial.IRON, 2, false)).setUnlocalizedName("swordscorpionfrost");//.setIconIndex(103).setItemName("swordFrost");
        swordNether = (new MoCItemWeapon(MoCItemID++, EnumToolMaterial.IRON, 3, false)).setUnlocalizedName("swordscorpionnether");//.setIconIndex(104).setItemName("swordNether");
        swordDirt = (new MoCItemWeapon(MoCItemID++, EnumToolMaterial.IRON, 1, false)).setUnlocalizedName("swordscorpion");//.setIconIndex(105).setItemName("swordDirt");

        plateScorpDirt = (new MoCItemArmor(MoCItemID++, crocARMOR, 4, 1)).setUnlocalizedName("plate");//.setIconIndex(106).setItemName("plateScorpDirt");
        helmetScorpDirt = (new MoCItemArmor(MoCItemID++, crocARMOR, 4, 0)).setUnlocalizedName("helmet");//.setIconIndex(107).setItemName("helmetScorpDirt");
        legsScorpDirt = (new MoCItemArmor(MoCItemID++, crocARMOR, 4, 2)).setUnlocalizedName("legs");//.setIconIndex(108).setItemName("legsScorpDirt");
        bootsScorpDirt = (new MoCItemArmor(MoCItemID++, crocARMOR, 4, 3)).setUnlocalizedName("boots");//.setIconIndex(109).setItemName("bootsScorpDirt");

        plateScorpFrost = (new MoCItemArmor(MoCItemID++, crocARMOR, 4, 1)).setUnlocalizedName("platefrost");//.setIconIndex(110).setItemName("plateScorpFrost");
        helmetScorpFrost = (new MoCItemArmor(MoCItemID++, crocARMOR, 4, 0)).setUnlocalizedName("helmetfrost");//.setIconIndex(111).setItemName("helmetScorpFrost");
        legsScorpFrost = (new MoCItemArmor(MoCItemID++, crocARMOR, 4, 2)).setUnlocalizedName("legsfrost");//.setIconIndex(112).setItemName("legsScorpFrost");
        bootsScorpFrost = (new MoCItemArmor(MoCItemID++, crocARMOR, 4, 3)).setUnlocalizedName("bootsfrost");//.setIconIndex(113).setItemName("bootsScorpFrost");

        plateScorpCave = (new MoCItemArmor(MoCItemID++, crocARMOR, 4, 1)).setUnlocalizedName("plateblack");//.setIconIndex(114).setItemName("plateScorpCave");
        helmetScorpCave = (new MoCItemArmor(MoCItemID++, crocARMOR, 4, 0)).setUnlocalizedName("helmetblack");//.setIconIndex(115).setItemName("helmetScorpCave");
        legsScorpCave = (new MoCItemArmor(MoCItemID++, crocARMOR, 4, 2)).setUnlocalizedName("legsblack");//.setIconIndex(116).setItemName("legsScorpCave");
        bootsScorpCave = (new MoCItemArmor(MoCItemID++, crocARMOR, 4, 3)).setUnlocalizedName("bootsblack");//.setIconIndex(117).setItemName("bootsScorpCave");

        plateScorpNether = (new MoCItemArmor(MoCItemID++, crocARMOR, 4, 1)).setUnlocalizedName("platenether");//.setIconIndex(118).setItemName("plateScorpNether");
        helmetScorpNether = (new MoCItemArmor(MoCItemID++, crocARMOR, 4, 0)).setUnlocalizedName("helmetnether");//.setIconIndex(119).setItemName("helmetScorpNether");
        legsScorpNether = (new MoCItemArmor(MoCItemID++, crocARMOR, 4, 2)).setUnlocalizedName("legsnether");//.setIconIndex(120).setItemName("legsScorpNether");
        bootsScorpNether = (new MoCItemArmor(MoCItemID++, crocARMOR, 4, 3)).setUnlocalizedName("bootsnether");//.setIconIndex(121).setItemName("bootsScorpNether");

        stingCave = (new MoCItemWeapon(MoCItemID++, EnumToolMaterial.GOLD, 4, true)).setUnlocalizedName("stingcave");//.setIconIndex(122).setItemName("stingCave");
        stingFrost = (new MoCItemWeapon(MoCItemID++, EnumToolMaterial.GOLD, 2, true)).setUnlocalizedName("stingfrost");//.setIconIndex(123).setItemName("stingFrost");
        stingNether = (new MoCItemWeapon(MoCItemID++, EnumToolMaterial.GOLD, 3, true)).setUnlocalizedName("stingnether");//.setIconIndex(124).setItemName("stingNether");
        stingDirt = (new MoCItemWeapon(MoCItemID++, EnumToolMaterial.GOLD, 1, true)).setUnlocalizedName("stingdirt");//.setIconIndex(125).setItemName("stingDirt");

        scrollFreedom = (new MoCItem(MoCItemID++)).setUnlocalizedName("scrolloffreedom");//.setIconIndex(137).setItemName("scrollFreedom");
        scrollOfSale = (new MoCItem(MoCItemID++)).setUnlocalizedName("scrollofsale");//.setIconIndex(136).setItemName("scrollSale");
        
        woodTusks = (new MoCItemWeapon(MoCItemID++, EnumToolMaterial.WOOD)).setUnlocalizedName("tuskswood");//.setIconIndex(128).setItemName("woodTusks");
        ironTusks = (new MoCItemWeapon(MoCItemID++, EnumToolMaterial.IRON)).setUnlocalizedName("tusksiron");//.setIconIndex(129).setItemName("ironTusks");
        diamondTusks = (new MoCItemWeapon(MoCItemID++, EnumToolMaterial.EMERALD)).setUnlocalizedName("tusksdiamond");//.setIconIndex(130).setItemName("diamondTusks");
        harness = (new MoCItem(MoCItemID++)).setUnlocalizedName("harness");//.setIconIndex(131).setItemName("harness");
        chestSet = (new MoCItem(MoCItemID++)).setUnlocalizedName("chestset");//.setIconIndex(132).setItemName("chestSet");
        garment = (new MoCItem(MoCItemID++)).setUnlocalizedName("garment");//.setIconIndex(133).setItemName("garment");
        howdah = (new MoCItem(MoCItemID++)).setUnlocalizedName("howdah");//.setIconIndex(134).setItemName("howdah");
        platform = (new MoCItem(MoCItemID++)).setUnlocalizedName("platform");//.setIconIndex(135).setItemName("platform");
        
        crabmeat = (new MoCItemFood(MoCItemID++, 2, 0.3F, false)).setPotionEffect(Potion.hunger.id, 30, 0, 0.8F).setUnlocalizedName("crabmeat");//.setIconIndex(77).setItemName("ratRaw");
        crabmeatcooked = (new MoCItemFood(MoCItemID++, 6, 0.6F, false)).setUnlocalizedName("crabmeatcooked");//.setIconIndex(78).setItemName("ratCooked");
        
        multiBlockNames.add ("WyvernLair");
        multiBlockNames.add("OgreLair");
        
        staffPortal = (new ItemStaffPortal(MoCItemID++)).setUnlocalizedName("staffportal");//.setIconIndex(70).setItemName("staffteleporter");
        LanguageRegistry.addName(staffPortal, "Wyvern Portal Staff");
        
        scrollOfOwner = (new MoCItem(MoCItemID++)).setUnlocalizedName("scrollofowner");//.setIconIndex(136).setItemName("scrollSale");
        
        //new blocks
        mocStone = new MoCBlockRock(proxy.blockStoneID).setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundStoneFootstep).setUnlocalizedName("MoCStone");//.setBlockName("WyvernStone");
        GameRegistry.registerBlock(mocStone, MultiItemBlock.class, "MoC_Stone");

        mocGrass = new MoCBlockGrass(proxy.blockGrassID).setHardness(0.5F).setStepSound(Block.soundGrassFootstep).setUnlocalizedName("MoCGrass");//;
        GameRegistry.registerBlock(mocGrass, MultiItemBlock.class, "MoC_Grass");
        
        mocDirt = new MoCBlockDirt(proxy.blockDirtID).setHardness(0.6F).setStepSound(Block.soundGravelFootstep).setUnlocalizedName("MoCDirt");//;
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
        //LanguageRegistry.addName(mocPlank, "WyvernLair Planks");
        
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
        
        WyvernLairBiome = (new BiomeGenWyvernLair(proxy.WyvernBiomeID)); 
        
        LanguageRegistry.instance().addStringLocalization("itemGroup.MoCreaturesTab", "en_US", "MoCreatures Misc");
        
//        
//        mocBlockFarm = new MoCBlockFarm(MoCBlockID++).setBlockName("MoCFarmBlock").setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundWoodFootstep);
//        GameRegistry.registerBlock(mocBlockFarm, "MoC_BlockFarm");
//        LanguageRegistry.addName(mocBlockFarm, "Farm Feeder");
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
        //LanguageRegistry.addName(staff, "Empty Staff");
        //LanguageRegistry.addName(staffender, "Ender Staff");
        //LanguageRegistry.addName(staffunicorn, "Unicorn Staff");
        //LanguageRegistry.addName(staffdiamond, "Diamond Staff");

        LanguageRegistry.addName(horsearmormetal, "Iron Mount Armor");
        LanguageRegistry.addName(horsearmorgold, "Gold Mount Armor");
        LanguageRegistry.addName(horsearmordiamond, "Diamond Mount Armor");
        LanguageRegistry.addName(horsearmorcrystal, "Crystal Mount Armor");

        //LanguageRegistry.addName(creaturepedia, "Creature-Pedia");

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

        for (int i = 0; i < 55; i++)
        {
            String s;
            if (i > 0 && i < 11)
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

        /*GameRegistry.addShapelessRecipe(new ItemStack(creaturepedia, 1), new Object[] {
            new ItemStack(Item.book, 1), new ItemStack(Item.diamond, 1)
        });
        
        GameRegistry.addShapelessRecipe(new ItemStack(creaturepedia, 1), new Object[] {
            unicorn, Item.goldNugget
        });*/
        
                 
        
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

        GameRegistry.addRecipe(new ItemStack(amuletbone, 1), new Object[] { "#X#", "XZX", "#X#", Character.valueOf('#'), Item.bone, Character.valueOf('X'), Item.goldNugget, Character.valueOf('Z'), Item.enderPearl });

        GameRegistry.addRecipe(new ItemStack(amuletghost, 1), new Object[] { "#X#", "XZX", "#X#", Character.valueOf('#'), Item.bone, Character.valueOf('X'), Item.goldNugget, Character.valueOf('Z'), Item.ghastTear });

        GameRegistry.addRecipe(new ItemStack(amuletfairy, 1), new Object[] { "#X#", "XZX", "#X#", Character.valueOf('#'), Block.fire, Character.valueOf('X'), Item.goldNugget, Character.valueOf('Z'), unicorn });

        GameRegistry.addRecipe(new ItemStack(amuletfairy, 1), new Object[] { "#X#", "XZX", "#X#", Character.valueOf('#'), Block.fire, Character.valueOf('X'), Item.goldNugget, Character.valueOf('Z'), viallight });

        GameRegistry.addRecipe(new ItemStack(amuletpegasus, 1), new Object[] { "#X#", "XZX", "#X#", Character.valueOf('#'), Block.fire, Character.valueOf('X'), Item.goldNugget, Character.valueOf('Z'), Item.diamond });

        /*GameRegistry.addRecipe(new ItemStack(amuletpfairy, 1), new Object[] {
            "#X#", "XZX", "#X#", Character.valueOf('#'), Item.blazePowder, Character.valueOf('X'), Item.goldNugget, Character.valueOf('Z'), Item.spiderEye
        });
        */

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

    //TODO 4FIX
    /*private void AddAchievements()
    {
        ModLoader.addAchievementDesc(Indiana, "Indiana, Master of the BigCats", "Using a whip, command at least 7 tamed BigCats at once");
        ModLoader.addAchievementDesc(BunnyKilla, "Da Bunny Killa", "using the bunny-kill-o-matic, euthanize at least 69 promiscuous bunnies at once");
    }*/

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
        //        if (!mc.theWorld.isRemote)
        //        {
        //            inst.freezed = true;
        //            inst.freezedcounter = 0;
        //        }
    }

    public static void poisonPlayer(EntityPlayer player)
    {
        //TODO 4FIX
        //        if (!mc.theWorld.isRemote)
        //        {
        //            inst.poisoned = true;
        //            inst.poisoncounter = 0;
        //        }
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

        MoCConfigCategory entities = proxy.MoCconfig.getCategory(proxy.CATEGORY_ENTITY_BIOME_SETTINGS);
        Map<String, MoCEntityData> entityList = proxy.mocEntityMap; // add mocreatures only
        if (proxy.modifyVanillaSpawns) // if we are modifying the vanilla spawns then use the complete entity list containing all entities
        {
            entityList = proxy.entityMap;
        }
        if (proxy.debugLogging) log.info("Scanning MoCProperties.cfg for entities...");
        for (Entry<String, MoCProperty> entityEntry : entities.entrySet())
        {
            if (proxy.debugLogging) log.info("Found entity " + entityEntry.getKey());
            if (proxy.entityMap.containsKey(entityEntry.getKey()));
            {
                if (proxy.debugLogging) log.info("Entity " + entityEntry.getKey() + " exists in entityMap, proceeding...");
                MoCProperty biomeGroups = entityEntry.getValue();
                if (proxy.debugLogging) log.info("Detected " + biomeGroups.valueList.size() + " Biome Groups for entity, verifying list...");

                for (int i = 0; i < biomeGroups.valueList.size(); i++)
                {
                    if (proxy.debugLogging) log.info("Found Biome Group " + biomeGroups.valueList.get(i));
                    if (proxy.biomeGroupMap.containsKey(biomeGroups.valueList.get(i))) // if valid biome group from MoCBiomeGroups.cfg continue
                    {
                        if (proxy.debugLogging) log.info("Group is valid, scanning biomes...");
                        List biomeGroup = proxy.biomeGroupMap.get(biomeGroups.valueList.get(i)).getBiomeList();
                        List<BiomeGenBase> entitySpawnBiomes = new ArrayList<BiomeGenBase>();
                        MoCEntityData entityData = entityList.get(entityEntry.getKey());//entityClass.getValue();
                        for (int j = 0; j < biomeGroup.size(); j++)
                        {
                            if (proxy.debugLogging) log.info("Found biome " + biomeGroup.get(j));
                            if (proxy.biomeMap.get(biomeGroup.get(j)) != null)
                            {
                                entitySpawnBiomes.add(proxy.biomeMap.get(biomeGroup.get(j)).getBiome());
                                if (proxy.debugLogging) log.info("Added biome " + biomeGroup.get(j) + " for entity " + entityEntry.getKey());
                            }else
                            {
                                if (proxy.debugLogging) log.info("Skipping biome " + biomeGroup.get(j) + " for entity " + entityEntry.getKey() + " as that biome is not loaded");
                            }
                        }
                        if (entitySpawnBiomes.size() > 0 && entityData != null)
                        {
                            if (proxy.debugLogging) log.info("entitySpawnBiomes size = " + entitySpawnBiomes.size());
                            BiomeGenBase[] biomesToSpawn = new BiomeGenBase[entitySpawnBiomes.size()];
                            biomesToSpawn = entitySpawnBiomes.toArray(biomesToSpawn);
                            if (proxy.useCustomSpawner)
                            {
                            	if (entityData.frequency > 0 && entityData.minGroup > 0 && entityData.maxGroup > 0)
                            	{
                            		myCustomSpawner.AddCustomSpawn(entityData.getEntityClass(), entityData.frequency, entityData.minGroup, entityData.maxGroup, entityData.getType(), biomesToSpawn);
                                    if (proxy.debugLogging) log.info("Added " + entityData.getEntityClass() + " to CustomSpawner spawn lists");
                            	}else
                            	{
                            		//myCustomSpawner.RemoveCustomSpawn(entityData.getEntityClass(), entityData.getType(), biomesToSpawn);
                            	}
                                
                                //otherwise the Forge spawnlist remains pouplated with duplicated entries on CMS
                                EntityRegistry.removeSpawn(entityData.getEntityClass(), entityData.getType(), biomesToSpawn); 
                                if (proxy.debugLogging) log.info("Removed " + entityData.getEntityClass() + " from Vanilla spawn lists");
                            }
                            else //use Forge Spawn method instead
                            {
                            	if (entityData.frequency > 0 && entityData.minGroup > 0 && entityData.maxGroup > 0)
                            	{
                            		EntityRegistry.addSpawn(entityData.getEntityClass(), entityData.frequency, entityData.minGroup, entityData.maxGroup, entityData.getType(), biomesToSpawn);
                                    if (proxy.debugLogging) log.info("Added " + entityData.getEntityClass() + " to Vanilla spawn lists");
                            	}else
                            	{
                            	    if (proxy.debugLogging) log.info("Removed " + entityData.getEntityClass() + " from Vanilla spawn lists");
                            		EntityRegistry.removeSpawn(entityData.getEntityClass(), entityData.getType(), biomesToSpawn); 
                            	}
                                
                            }
                        }
                        
                    }
                }
            }
        }
    }

    public static void updateSettings()
    {
        proxy.readConfigValues();
        updateSpawnSettings();
    }

    public static void updateSpawnSettings()
    {
        if (proxy.modifyVanillaSpawns)
        {
            ClearVanillaSpawnLists();
            ClearVanillaMobSpawns();
        }
        if (proxy.useCustomSpawner)
        {
            if (myCustomSpawner != null) // if server has not started, don't reset custom spawner settings
            {
                myCustomSpawner.setMaxMobs(proxy.maxMobs);
                myCustomSpawner.setMaxAnimals(proxy.maxAnimals);
                myCustomSpawner.setMaxAquatic(proxy.maxWaterMobs);
                myCustomSpawner.setMaxAmbient(proxy.maxAmbient);
                myCustomSpawner.clearLists();
                populateSpawns();
            }
        }
        else populateSpawns();
    }
    
    public static void ClearVanillaSpawnLists()
    {
        for (int i = 0; i < BiomeGenBase.biomeList.length; i++)
        {
            if (BiomeGenBase.biomeList[i] != null)
            {
                EntityRegistry.removeSpawn(EntityCow.class, EnumCreatureType.creature, BiomeGenBase.biomeList[i]);
                EntityRegistry.removeSpawn(EntityPig.class, EnumCreatureType.creature, BiomeGenBase.biomeList[i]);
                EntityRegistry.removeSpawn(EntitySheep.class, EnumCreatureType.creature, BiomeGenBase.biomeList[i]);
                EntityRegistry.removeSpawn(EntityChicken.class, EnumCreatureType.creature, BiomeGenBase.biomeList[i]);
                EntityRegistry.removeSpawn(EntityWolf.class, EnumCreatureType.creature, BiomeGenBase.biomeList[i]);
                EntityRegistry.removeSpawn(EntitySquid.class, EnumCreatureType.waterCreature, BiomeGenBase.biomeList[i]);
                EntityRegistry.removeSpawn(EntityOcelot.class, EnumCreatureType.creature, BiomeGenBase.biomeList[i]);
                EntityRegistry.removeSpawn(EntityBat.class, EnumCreatureType.ambient, BiomeGenBase.biomeList[i]);
            }
        }
    }

    public static void ClearVanillaMobSpawns()
    {
        for (int i = 0; i < BiomeGenBase.biomeList.length; i++)
        {
            if (BiomeGenBase.biomeList[i] != null)
            {
                EntityRegistry.removeSpawn(EntityCreeper.class, EnumCreatureType.monster, BiomeGenBase.biomeList[i]);
                EntityRegistry.removeSpawn(EntitySkeleton.class, EnumCreatureType.monster, BiomeGenBase.biomeList[i]);
                EntityRegistry.removeSpawn(EntityZombie.class, EnumCreatureType.monster, BiomeGenBase.biomeList[i]);
                EntityRegistry.removeSpawn(EntitySpider.class, EnumCreatureType.monster, BiomeGenBase.biomeList[i]);
                EntityRegistry.removeSpawn(EntityEnderman.class, EnumCreatureType.monster, BiomeGenBase.biomeList[i]);
                EntityRegistry.removeSpawn(EntityCaveSpider.class, EnumCreatureType.monster, BiomeGenBase.biomeList[i]);
                EntityRegistry.removeSpawn(EntitySlime.class, EnumCreatureType.monster, BiomeGenBase.biomeList[i]);
                EntityRegistry.removeSpawn(EntityGhast.class, EnumCreatureType.monster, BiomeGenBase.biomeList[i]);
                EntityRegistry.removeSpawn(EntityPigZombie.class, EnumCreatureType.monster, BiomeGenBase.biomeList[i]);
                EntityRegistry.removeSpawn(EntityMagmaCube.class, EnumCreatureType.monster, BiomeGenBase.biomeList[i]);
                EntityRegistry.removeSpawn(EntityOcelot.class, EnumCreatureType.monster, BiomeGenBase.biomeList[i]);
            }
        }
    }

    public static boolean isServer()
    {
        return (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER);
    }
}