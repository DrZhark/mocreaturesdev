package drzhark.mocreatures;

import com.mojang.authlib.GameProfile;
import drzhark.mocreatures.client.MoCClientTickHandler;
import drzhark.mocreatures.client.MoCCreativeTabs;
import drzhark.mocreatures.client.handlers.MoCKeyHandler;
import drzhark.mocreatures.command.CommandMoCPets;
import drzhark.mocreatures.command.CommandMoCSpawn;
import drzhark.mocreatures.command.CommandMoCTP;
import drzhark.mocreatures.command.CommandMoCreatures;
import drzhark.mocreatures.datafixer.EntityDataWalker;
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
import drzhark.mocreatures.util.MoCLog;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

@Mod(modid = MoCConstants.MOD_ID, name = MoCConstants.MOD_NAME, version = MoCConstants.MOD_VERSION)
public class MoCreatures {

    @Instance(MoCConstants.MOD_ID)
    public static MoCreatures instance;

    @SidedProxy(clientSide = "drzhark.mocreatures.client.MoCClientProxy", serverSide = "drzhark.mocreatures.MoCProxy")
    public static MoCProxy proxy;
    public static final CreativeTabs tabMoC = new MoCCreativeTabs(CreativeTabs.CREATIVE_TAB_ARRAY.length, "MoCreaturesTab");
    public MoCPetMapData mapData;
    public static boolean isCustomSpawnerLoaded = false;
    public static GameProfile MOCFAKEPLAYER = new GameProfile(UUID.fromString("6E379B45-1111-2222-3333-2FE1A88BCD66"), "[MoCreatures]");
    public static DimensionType WYVERN_LAIR;

    /**
     * ITEMS
     */
    static int MoCEggID;// = 7772;
    static int MoCEntityID = 7256; // used internally, does not need to be configured by users
    public static int WyvernLairDimensionID; //17;

    public static Map<String, MoCEntityData> mocEntityMap = new TreeMap<String, MoCEntityData>(String.CASE_INSENSITIVE_ORDER);
    public static Map<Class<? extends EntityLiving>, MoCEntityData> entityMap = new HashMap<Class<? extends EntityLiving>, MoCEntityData>();
    public static Map<Integer, Class<? extends EntityLiving>> instaSpawnerMap = new HashMap<Integer, Class<? extends EntityLiving>>();
    public static final String CATEGORY_ITEM_IDS = "item-ids";

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        if (isServer()) {
            FMLCommonHandler.instance().getMinecraftServerInstance().getDataFixer().registerWalker(FixTypes.ENTITY, new EntityDataWalker());
        }
        MoCMessageHandler.init();
        MinecraftForge.EVENT_BUS.register(new MoCEventHooks());
        proxy.ConfigInit(event);
        proxy.initTextures();
        if (!isServer()) {
            MinecraftForge.EVENT_BUS.register(new MoCClientTickHandler());
            MinecraftForge.EVENT_BUS.register(new MoCKeyHandler());
        }
        registerEntities();
    }

    //how to check for client: if(FMLCommonHandler.instance().getSide().isRemote())

    @EventHandler
    public void load(FMLInitializationEvent event) {
        WyvernLairDimensionID = proxy.WyvernDimension;
        proxy.mocSettingsConfig.save();
        proxy.registerRenderers();
        proxy.registerRenderInformation();
        WYVERN_LAIR = DimensionType.register("Wyvern Lair", "_wyvern_lair", WyvernLairDimensionID, WorldProviderWyvernEnd.class, false);
        DimensionManager.registerDimension(WyvernLairDimensionID, WYVERN_LAIR);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        isCustomSpawnerLoaded = Loader.isModLoaded("CustomSpawner");
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.initGUI();
        event.registerServerCommand(new CommandMoCreatures());
        event.registerServerCommand(new CommandMoCTP());
        event.registerServerCommand(new CommandMoCPets());
        if (isServer()) {
            if (FMLCommonHandler.instance().getMinecraftServerInstance().isDedicatedServer()) {
                event.registerServerCommand(new CommandMoCSpawn());
            }
        }
    }

    @EventHandler
    public void serverStarted(FMLServerStartedEvent event) {
    }

    public void registerEntities() {
        /**
         * Passive
         */
        registerEntity(MoCEntityBird.class, "Bird", 14020607, 14020607);// 0x03600, 0x003500);
        registerEntity(MoCEntityBlackBear.class, "BlackBear", 10, 1);//, 0x2600, 0x052500);
        registerEntity(MoCEntityBoar.class, "Boar", 14772545, 9141102);//, 0x2600, 0x052500);
        registerEntity(MoCEntityBunny.class, "Bunny", 12623485, 9141102);//, 0x05600, 0x006500);
        registerEntity(MoCEntityCrocodile.class, "Crocodile", 16711680, 65407);//, 0x2600, 0x052500);
        registerEntity(MoCEntityDuck.class, "Duck", 14021607, 15656192);//, 0x2600, 0x052500);
        registerEntity(MoCEntityDeer.class, "Deer", 14021607, 33023);//, 0x2600, 0x052500);
        registerEntity(MoCEntityElephant.class, "Elephant", 14772545, 23423);
        registerEntity(MoCEntityEnt.class, "Ent", 12623485, 16711680);
        registerEntity(MoCEntityFox.class, "Fox", 14772545, 5253242);//, 0x2600, 0x052500);
        registerEntity(MoCEntityGoat.class, "Goat", 7434694, 6053069);//, 0x2600, 0x052500);
        registerEntity(MoCEntityGrizzlyBear.class, "GrizzlyBear", 14772545, 1);//, 0x2600, 0x052500);
        registerEntity(MoCEntityKitty.class, "Kitty", 12623485, 5253242);//, 0x2600, 0x052500);
        registerEntity(MoCEntityKomodo.class, "KomodoDragon", 16711680, 23423);
        registerEntity(MoCEntityLeoger.class, "Leoger", 14772545, 14772545);
        registerEntity(MoCEntityLeopard.class, "Leopard", 13749760, 10);
        registerEntity(MoCEntityLiard.class, "Liard", 15313474, 13749760);
        registerEntity(MoCEntityLion.class, "Lion", 15313474, 13749760);
        registerEntity(MoCEntityLiger.class, "Liger", 15313474, 12623485);
        registerEntity(MoCEntityLither.class, "Lither", 15313474, 14772545);
        registerEntity(MoCEntityManticorePet.class, "ManticorePet");
        registerEntity(MoCEntityMole.class, "Mole", 14020607, 16711680);
        registerEntity(MoCEntityMouse.class, "Mouse", 14772545, 0);//, 0x02600, 0x002500);
        registerEntity(MoCEntityOstrich.class, "Ostrich", 14020607, 9639167);//, 0x2600, 0x052500);
        registerEntity(MoCEntityPandaBear.class, "PandaBear", 10, 9141102);//, 0x2600, 0x052500);
        registerEntity(MoCEntityPanthard.class, "Panthard", 10, 13749760);
        registerEntity(MoCEntityPanther.class, "Panther", 10, 205);
        registerEntity(MoCEntityPanthger.class, "Panthger", 10, 14772545);
        registerEntity(MoCEntityPetScorpion.class, "PetScorpion");
        registerEntity(MoCEntityPolarBear.class, "WildPolarBear", 14020607, 9141102);//, 0x2600, 0x052500);
        registerEntity(MoCEntityRaccoon.class, "Raccoon", 14772545, 13749760);
        registerEntity(MoCEntitySnake.class, "Snake", 14020607, 13749760);//, 0x05800, 0x006800);
        registerEntity(MoCEntityTiger.class, "Tiger", 14772545, 0);
        registerEntity(MoCEntityTurtle.class, "Turtle", 14772545, 9320590);//, 0x04800, 0x004500);
        registerEntity(MoCEntityTurkey.class, "Turkey", 14020607, 16711680);//, 0x2600, 0x052500);
        registerEntity(MoCEntityHorse.class, "WildHorse", 12623485, 15656192);//, 0x2600, 0x052500);
        registerEntity(MoCEntityWyvern.class, "Wyvern", 14772545, 65407);
        
        /**
         * Mobs
         */
        registerEntity(MoCEntityCaveOgre.class, "CaveOgre", 16711680, 33023);//, 0x2600, 0x052500);
        registerEntity(MoCEntityFlameWraith.class, "FlameWraith", 16711680, 12623485);//, 0x2600, 0x052500);
        registerEntity(MoCEntityFireOgre.class, "FireOgre", 16711680, 9320595);//, 0x2600, 0x052500);
        registerEntity(MoCEntityGreenOgre.class, "GreenOgre", 16711680, 65407);//, 0x2600, 0x052500);
        registerEntity(MoCEntityGolem.class, "BigGolem", 16711680, 16622);
        registerEntity(MoCEntityHorseMob.class, "HorseMob", 16711680, 9320590);//, 0x2600, 0x052500);
        registerEntity(MoCEntityHellRat.class, "HellRat", 16711680, 14772545);//, 0x2600, 0x052500);
        registerEntity(MoCEntityManticore.class, "Manticore", 16711680, 0);
        registerEntity(MoCEntityMiniGolem.class, "MiniGolem", 16711680, 13749760);
        registerEntity(MoCEntityRat.class, "Rat", 16711680, 9141102);//, 0x2600, 0x052500);
        registerEntity(MoCEntitySilverSkeleton.class, "SilverSkeleton", 16711680, 33023);
        registerEntity(MoCEntityScorpion.class, "Scorpion", 16711680, 6053069);//, 0x2600, 0x052500);
        registerEntity(MoCEntityWerewolf.class, "Werewolf", 16711680, 7434694);//, 0x2600, 0x052500);
        registerEntity(MoCEntityWraith.class, "Wraith", 16711680, 0);//, 0x2600, 0x052500);
        registerEntity(MoCEntityWWolf.class, "WWolf", 16711680, 13749760);//, 0x2600, 0x052500);
        
        /**
         * Aquatic
         */
        registerEntity(MoCEntityAnchovy.class, "Anchovy", 5665535, 205);
        registerEntity(MoCEntityAngelFish.class, "AngelFish", 5665535, 7434694);
        registerEntity(MoCEntityAngler.class, "Angler", 5665535, 10);
        registerEntity(MoCEntityBass.class, "Bass", 33023, 2372490);
        registerEntity(MoCEntityClownFish.class, "ClownFish", 5665535, 14772545);
        registerEntity(MoCEntityCod.class, "Cod", 33023, 16622);
        registerEntity(MoCEntityDolphin.class, "Dolphin", 33023, 15631086);//, 0x2600, 0x052500);
        registerEntity(MoCEntityFishy.class, "Fishy", 5665535, 65407);//, 0x2600, 0x052500);
        registerEntity(MoCEntityGoldFish.class, "GoldFish", 5665535, 15656192);
        registerEntity(MoCEntityHippoTang.class, "HippoTang", 5665535, 2037680);
        registerEntity(MoCEntityJellyFish.class, "JellyFish", 33023, 14772545);//, 0x2600, 0x052500);
        registerEntity(MoCEntityManderin.class, "Manderin", 5665535, 12623485);
        registerEntity(MoCEntityPiranha.class, "Piranha", 33023, 16711680);
        registerEntity(MoCEntitySalmon.class, "Salmon", 33023, 12623485);
        registerEntity(MoCEntityMantaRay.class, "MantaRay", 33023, 9141102);//14772545, 9141102);
        registerEntity(MoCEntityShark.class, "Shark", 33023, 9013643);//, 0x2600, 0x052500);
        registerEntity(MoCEntityStingRay.class, "StingRay", 33023, 6053069);//14772545, 9141102);
        
        /**
         * Ambients
         */
        registerEntity(MoCEntityAnt.class, "Ant", 65407, 12623485);
        registerEntity(MoCEntityBee.class, "Bee", 65407, 15656192);//, 0x2600, 0x052500);
        registerEntity(MoCEntityButterfly.class, "ButterFly", 65407, 7434694);//, 0x22600, 0x012500);
        registerEntity(MoCEntityCrab.class, "Crab", 65407, 13749760);
        registerEntity(MoCEntityCricket.class, "Cricket", 65407, 16622);//, 0x2600, 0x052500);
        registerEntity(MoCEntityDragonfly.class, "DragonFly", 65407, 14020607);//, 0x2600, 0x052500);
        registerEntity(MoCEntityFirefly.class, "Firefly", 65407, 9320590);//, 0x2600, 0x052500);
        registerEntity(MoCEntityFly.class, "Fly", 65407, 1);//, 0x2600, 0x052500);
        registerEntity(MoCEntityMaggot.class, "Maggot", 65407, 9141102);
        registerEntity(MoCEntitySnail.class, "Snail", 65407, 14772545);//, 0x2600, 0x052500);
        registerEntity(MoCEntityRoach.class, "Roach", 65407, 13749760);
        
        /**
         * Others
         */
        registerEntity(MoCEntityEgg.class, "Egg");//, 0x2600, 0x052500);
        registerEntity(MoCEntityKittyBed.class, "KittyBed");
        registerEntity(MoCEntityLitterBox.class, "LitterBox");
        registerEntity(MoCEntityThrowableRock.class, "TRock");
        
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
        mocEntityMap.put("Ant", new MoCEntityData("Ant", 4, EnumCreatureType.AMBIENT, new SpawnListEntry(MoCEntityAnt.class, 7, 1, 4), new ArrayList<Type>(
                Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP, Type.WASTELAND))));
        mocEntityMap.put("Bee", new MoCEntityData("Bee", 3, EnumCreatureType.AMBIENT, new SpawnListEntry(MoCEntityBee.class, 6, 1, 2), new ArrayList<Type>(
                Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MESA, Type.MOUNTAIN, Type.PLAINS))));
        mocEntityMap.put("ButterFly", new MoCEntityData("ButterFly", 3, EnumCreatureType.AMBIENT, new SpawnListEntry(MoCEntityButterfly.class, 8, 1,
                3), new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MESA, Type.MOUNTAIN, Type.PLAINS))));
        mocEntityMap.put("Crab", new MoCEntityData("Crab", 2, EnumCreatureType.AMBIENT, new SpawnListEntry(MoCEntityCrab.class, 8, 1, 2),
                new ArrayList<Type>(Arrays.asList(Type.BEACH, Type.WATER))));
        mocEntityMap.put("Cricket", new MoCEntityData("Cricket", 2, EnumCreatureType.AMBIENT, new SpawnListEntry(MoCEntityCricket.class, 8, 1, 2),
                new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP))));
        mocEntityMap.put("DragonFly", new MoCEntityData("DragonFly", 2, EnumCreatureType.AMBIENT, new SpawnListEntry(MoCEntityDragonfly.class, 6, 1,
                2), new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP, Type.BEACH))));
        mocEntityMap.put("Firefly", new MoCEntityData("Firefly", 3, EnumCreatureType.AMBIENT, new SpawnListEntry(MoCEntityFirefly.class, 8, 1, 2),
                new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP))));
        mocEntityMap.put("Fly", new MoCEntityData("Fly", 2, EnumCreatureType.AMBIENT, new SpawnListEntry(MoCEntityFly.class, 8, 1, 2), new ArrayList<Type>(
                Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP, Type.WASTELAND))));
        mocEntityMap.put("Maggot", new MoCEntityData("Maggot", 2, EnumCreatureType.AMBIENT, new SpawnListEntry(MoCEntityMaggot.class, 8, 1, 2),
                new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.PLAINS, Type.SWAMP))));
        mocEntityMap.put("Snail", new MoCEntityData("Snail", 2, EnumCreatureType.AMBIENT, new SpawnListEntry(MoCEntitySnail.class, 7, 1, 2),
                new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.PLAINS))));
        mocEntityMap.put("Roach", new MoCEntityData("Roach", 2, EnumCreatureType.AMBIENT, new SpawnListEntry(MoCEntityRoach.class, 8, 1, 2),
                new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.PLAINS, Type.SWAMP))));

        // creatures
        mocEntityMap.put("BlackBear", new MoCEntityData("BlackBear", 4, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityBlackBear.class, 6, 1, 3),
                new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.PLAINS))));
        mocEntityMap.put("GrizzlyBear", new MoCEntityData("GrizzlyBear", 4, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityGrizzlyBear.class, 6, 1, 2),
                new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.PLAINS))));
        mocEntityMap.put("WildPolarBear", new MoCEntityData("WildPolarBear", 4, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityPolarBear.class, 6, 1, 2),
                new ArrayList<Type>(Arrays.asList(Type.SNOWY))));
        mocEntityMap.put("PandaBear", new MoCEntityData("PandaBear", 4, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityPandaBear.class, 6, 1, 3),
                new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.HILLS, Type.MESA, Type.PLAINS))));
        mocEntityMap.put("Bird", new MoCEntityData("Bird", 4, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityBird.class, 15, 2, 3),
                new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MESA, Type.MOUNTAIN, Type.PLAINS))));
        mocEntityMap.put("Boar", new MoCEntityData("Boar", 3, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityBoar.class, 8, 2, 2),
                new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.PLAINS))));
        mocEntityMap.put("Bunny", new MoCEntityData("Bunny", 4, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityBunny.class, 8, 2, 3),
                new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.PLAINS, Type.SNOWY))));
        mocEntityMap.put("Crocodile", new MoCEntityData("Crocodile", 2, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityCrocodile.class, 6, 1,
                2), new ArrayList<Type>(Arrays.asList(Type.SWAMP))));
        mocEntityMap.put("Deer", new MoCEntityData("Deer", 2, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityDeer.class, 8, 1, 2),
                new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.PLAINS))));
        mocEntityMap.put("Duck", new MoCEntityData("Duck", 3, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityDuck.class, 8, 1, 2),
                new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.PLAINS))));
        mocEntityMap.put("Elephant", new MoCEntityData("Elephant", 3, EnumCreatureType.CREATURE,
                new SpawnListEntry(MoCEntityElephant.class, 4, 1, 1),
                new ArrayList<Type>(Arrays.asList(Type.SANDY, Type.FOREST, Type.PLAINS, Type.SNOWY))));
        mocEntityMap.put("Ent", new MoCEntityData("Ent", 3, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityEnt.class, 4, 1, 2),
                new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.PLAINS))));
        mocEntityMap.put("Fox", new MoCEntityData("Fox", 2, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityFox.class, 8, 1, 1),
                new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.PLAINS, Type.SNOWY))));
        mocEntityMap.put("Goat", new MoCEntityData("Goat", 2, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityGoat.class, 8, 1, 3),
                new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MESA, Type.MOUNTAIN, Type.PLAINS))));
        mocEntityMap.put("Kitty", new MoCEntityData("Kitty", 3, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityKitty.class, 8, 1, 2),
                new ArrayList<Type>(Arrays.asList(Type.PLAINS))));
        mocEntityMap.put("KomodoDragon", new MoCEntityData("KomodoDragon", 2, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityKomodo.class, 8,
                1, 2), new ArrayList<Type>(Arrays.asList(Type.SWAMP))));
        mocEntityMap.put("Leopard", new MoCEntityData("Leopard", 4, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityLeopard.class, 6, 1, 2),
                new ArrayList<Type>(Arrays.asList(Type.HILLS, Type.JUNGLE, Type.MOUNTAIN, Type.SNOWY))));
        mocEntityMap.put("Lion", new MoCEntityData("Lion", 4, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityLion.class, 6, 1, 2),
                new ArrayList<Type>(Arrays.asList(Type.JUNGLE, Type.PLAINS))));
        mocEntityMap.put("Mole", new MoCEntityData("Mole", 3, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityMole.class, 7, 1, 2),
                new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.PLAINS))));
        mocEntityMap.put("Mouse", new MoCEntityData("Mouse", 2, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityMouse.class, 7, 1, 2),
                new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MESA, Type.MOUNTAIN, Type.PLAINS))));
        mocEntityMap.put("Ostrich", new MoCEntityData("Ostrich", 3, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityOstrich.class, 4, 1, 1),
                new ArrayList<Type>(Arrays.asList(Type.SANDY, Type.PLAINS))));
        mocEntityMap.put("Panther", new MoCEntityData("Panther", 4, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityPanther.class, 6, 1, 2),
                new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MOUNTAIN))));
        mocEntityMap.put("Raccoon", new MoCEntityData("Raccoon", 2, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityRaccoon.class, 8, 1, 2),
                new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.PLAINS))));
        mocEntityMap.put(
                "Snake",
                new MoCEntityData("Snake", 3, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntitySnake.class, 8, 1, 2), new ArrayList<Type>(Arrays
                        .asList(Type.SANDY, Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP, Type.WASTELAND))));
        mocEntityMap.put("Tiger", new MoCEntityData("Tiger", 4, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityTiger.class, 6, 1, 2),
                new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.PLAINS))));
        mocEntityMap.put("Turkey", new MoCEntityData("Turkey", 2, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityTurkey.class, 8, 1, 2),
                new ArrayList<Type>(Arrays.asList(Type.PLAINS))));
        mocEntityMap.put("Turtle", new MoCEntityData("Turtle", 3, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityTurtle.class, 6, 1, 2),
                new ArrayList<Type>(Arrays.asList(Type.JUNGLE, Type.SWAMP))));
        mocEntityMap.put("WildHorse", new MoCEntityData("WildHorse", 4, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityHorse.class, 8, 1, 4),
                new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.PLAINS))));
        mocEntityMap.put("Wyvern", new MoCEntityData("Wyvern", 3, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityWyvern.class, 8, 1, 3),
                new ArrayList<Type>()));
        // water creatures
        mocEntityMap.put("Bass", new MoCEntityData("Bass", 4, EnumCreatureType.WATER_CREATURE, new SpawnListEntry(
                MoCEntityBass.class, 10, 1, 4), new ArrayList<Type>(Arrays.asList(Type.BEACH, Type.SWAMP, Type.WATER, Type.OCEAN, Type.RIVER))));
        mocEntityMap.put("Cod", new MoCEntityData("Cod", 4, EnumCreatureType.WATER_CREATURE, new SpawnListEntry(
                MoCEntityCod.class, 10, 1, 4), new ArrayList<Type>(Arrays.asList(Type.BEACH, Type.SWAMP, Type.WATER, Type.OCEAN, Type.RIVER))));
        mocEntityMap.put("Dolphin", new MoCEntityData("Dolphin", 3, EnumCreatureType.WATER_CREATURE, new SpawnListEntry(MoCEntityDolphin.class, 6, 2,
                4), new ArrayList<Type>(Arrays.asList(Type.BEACH, Type.WATER, Type.OCEAN, Type.RIVER))));
        mocEntityMap.put("Fishy", new MoCEntityData("Fishy", 6, EnumCreatureType.WATER_CREATURE, new SpawnListEntry(MoCEntityFishy.class, 12, 1, 6),
                new ArrayList<Type>(Arrays.asList(Type.BEACH, Type.SWAMP, Type.WATER, Type.OCEAN, Type.RIVER))));
        mocEntityMap.put("JellyFish", new MoCEntityData("JellyFish", 4, EnumCreatureType.WATER_CREATURE, new SpawnListEntry(MoCEntityJellyFish.class,
                8, 1, 4), new ArrayList<Type>(Arrays.asList(Type.WATER, Type.OCEAN, Type.RIVER))));
        mocEntityMap.put("Salmon", new MoCEntityData("Salmon", 4, EnumCreatureType.WATER_CREATURE, new SpawnListEntry(
                MoCEntitySalmon.class, 10, 1, 4), new ArrayList<Type>(Arrays.asList(Type.BEACH, Type.SWAMP, Type.WATER, Type.OCEAN, Type.RIVER))));
        mocEntityMap.put("Piranha", new MoCEntityData("Piranha", 4, EnumCreatureType.WATER_CREATURE, new SpawnListEntry(MoCEntityPiranha.class, 4, 1,
                3), new ArrayList<Type>(Arrays.asList(Type.BEACH, Type.SWAMP, Type.WATER, Type.OCEAN, Type.RIVER))));
        mocEntityMap.put("MantaRay", new MoCEntityData("MantaRay", 3, EnumCreatureType.WATER_CREATURE, new SpawnListEntry(MoCEntityMantaRay.class,
                10, 1, 2), new ArrayList<Type>(Arrays.asList(Type.OCEAN))));
        mocEntityMap.put("StingRay", new MoCEntityData("StingRay", 3, EnumCreatureType.WATER_CREATURE, new SpawnListEntry(MoCEntityStingRay.class,
                10, 1, 2), new ArrayList<Type>(Arrays.asList(Type.SWAMP, Type.WATER, Type.RIVER))));
        mocEntityMap.put("Shark", new MoCEntityData("Shark", 3, EnumCreatureType.WATER_CREATURE, new SpawnListEntry(MoCEntityShark.class, 6, 1, 2),
                new ArrayList<Type>(Arrays.asList(Type.WATER, Type.OCEAN, Type.RIVER))));
        mocEntityMap.put("Anchovy", new MoCEntityData("Anchovy", 6, EnumCreatureType.WATER_CREATURE, new SpawnListEntry(MoCEntityAnchovy.class,
                12, 1, 6), new ArrayList<Type>(Arrays.asList(Type.BEACH, Type.SWAMP, Type.WATER, Type.OCEAN, Type.RIVER))));
        mocEntityMap.put("AngelFish", new MoCEntityData("AngelFish", 6, EnumCreatureType.WATER_CREATURE, new SpawnListEntry(MoCEntityAngelFish.class,
                12, 1, 6), new ArrayList<Type>(Arrays.asList(Type.BEACH, Type.SWAMP, Type.WATER, Type.OCEAN, Type.RIVER))));
        mocEntityMap.put("Angler", new MoCEntityData("Angler", 6, EnumCreatureType.WATER_CREATURE, new SpawnListEntry(MoCEntityAngler.class,
                12, 1, 6), new ArrayList<Type>(Arrays.asList(Type.BEACH, Type.SWAMP, Type.WATER, Type.OCEAN))));
        mocEntityMap.put("ClownFish", new MoCEntityData("ClownFish", 6, EnumCreatureType.WATER_CREATURE, new SpawnListEntry(MoCEntityClownFish.class,
                12, 1, 6), new ArrayList<Type>(Arrays.asList(Type.BEACH, Type.SWAMP, Type.WATER, Type.OCEAN))));
        mocEntityMap.put("GoldFish", new MoCEntityData("GoldFish", 6, EnumCreatureType.WATER_CREATURE, new SpawnListEntry(MoCEntityGoldFish.class,
                12, 1, 6), new ArrayList<Type>(Arrays.asList(Type.BEACH, Type.SWAMP, Type.WATER, Type.RIVER))));
        mocEntityMap.put("HippoTang", new MoCEntityData("HippoTang", 6, EnumCreatureType.WATER_CREATURE, new SpawnListEntry(MoCEntityHippoTang.class,
                12, 1, 6), new ArrayList<Type>(Arrays.asList(Type.BEACH, Type.SWAMP, Type.WATER, Type.OCEAN))));
        mocEntityMap.put("Manderin", new MoCEntityData("Manderin", 6, EnumCreatureType.WATER_CREATURE, new SpawnListEntry(MoCEntityManderin.class,
                12, 1, 6), new ArrayList<Type>(Arrays.asList(Type.BEACH, Type.SWAMP, Type.WATER, Type.OCEAN))));
        
        // monsters
        mocEntityMap.put(
                "BigGolem",
                new MoCEntityData("BigGolem", 1, EnumCreatureType.MONSTER, new SpawnListEntry(MoCEntityGolem.class, 3, 1, 1), new ArrayList<Type>(Arrays
                        .asList(Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP,
                                Type.WASTELAND))));
        mocEntityMap.put(
                "FlameWraith",
                new MoCEntityData("FlameWraith", 3, EnumCreatureType.MONSTER, new SpawnListEntry(MoCEntityFlameWraith.class, 5, 1, 2), new ArrayList<Type>(
                        Arrays.asList(Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.NETHER, Type.PLAINS,
                                Type.SWAMP, Type.WASTELAND, Type.DEAD, Type.SPOOKY))));
        mocEntityMap.put("HellRat", new MoCEntityData("HellRat", 4, EnumCreatureType.MONSTER, new SpawnListEntry(MoCEntityHellRat.class, 6, 1, 4),
                new ArrayList<Type>(Arrays.asList(Type.NETHER, Type.DEAD, Type.SPOOKY))));
        mocEntityMap.put(
                "HorseMob",
                new MoCEntityData("HorseMob", 3, EnumCreatureType.MONSTER, new SpawnListEntry(MoCEntityHorseMob.class, 8, 1, 3), new ArrayList<Type>(Arrays
                        .asList(Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.NETHER, Type.PLAINS, Type.SWAMP,
                                Type.WASTELAND, Type.DEAD, Type.SPOOKY))));
        mocEntityMap.put(
                "MiniGolem",
                new MoCEntityData("MiniGolem", 2, EnumCreatureType.MONSTER, new SpawnListEntry(MoCEntityMiniGolem.class, 6, 1, 3), new ArrayList<Type>(
                        Arrays.asList(Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP,
                                Type.WASTELAND))));
        mocEntityMap.put(
                "GreenOgre",
                new MoCEntityData("GreenOgre", 3, EnumCreatureType.MONSTER, new SpawnListEntry(MoCEntityGreenOgre.class, 8, 1, 2), new ArrayList<Type>(Arrays.asList(
                        Type.SANDY, Type.FOREST, Type.JUNGLE, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP,
                        Type.WASTELAND, Type.DEAD, Type.SPOOKY))));
        mocEntityMap.put(
                "FireOgre",
                new MoCEntityData("FireOgre", 3, EnumCreatureType.MONSTER, new SpawnListEntry(MoCEntityFireOgre.class, 6, 1, 2), new ArrayList<Type>(Arrays.asList(
                        Type.SANDY, Type.FOREST, Type.JUNGLE, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.NETHER, Type.PLAINS, Type.SWAMP,
                        Type.WASTELAND, Type.DEAD, Type.SPOOKY))));
        mocEntityMap.put(
                "CaveOgre",
                new MoCEntityData("CaveOgre", 3, EnumCreatureType.MONSTER, new SpawnListEntry(MoCEntityCaveOgre.class, 5, 1, 2), new ArrayList<Type>(Arrays.asList(
                        Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP,
                        Type.WASTELAND, Type.DEAD, Type.SPOOKY))));
        mocEntityMap.put(
                "Rat",
                new MoCEntityData("Rat", 2, EnumCreatureType.MONSTER, new SpawnListEntry(MoCEntityRat.class, 7, 1, 2), new ArrayList<Type>(Arrays.asList(
                        Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP, Type.WASTELAND))));
        mocEntityMap.put(
                "Scorpion",
                new MoCEntityData("Scorpion", 3, EnumCreatureType.MONSTER, new SpawnListEntry(MoCEntityScorpion.class, 6, 1, 3), new ArrayList<Type>(Arrays
                        .asList(Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.NETHER, Type.PLAINS, Type.SWAMP,
                                Type.WASTELAND))));
        mocEntityMap.put(
                "SilverSkeleton",
                new MoCEntityData("SilverSkeleton", 4, EnumCreatureType.MONSTER, new SpawnListEntry(MoCEntitySilverSkeleton.class, 6, 1, 4),
                        new ArrayList<Type>(Arrays.asList(Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.PLAINS,
                                Type.SWAMP, Type.WASTELAND, Type.DEAD, Type.SPOOKY))));
        mocEntityMap.put(
                "Werewolf",
                new MoCEntityData("Werewolf", 3, EnumCreatureType.MONSTER, new SpawnListEntry(MoCEntityWerewolf.class, 8, 1, 4), new ArrayList<Type>(Arrays
                        .asList(Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP,
                                Type.WASTELAND))));
        mocEntityMap.put(
                "Wraith",
                new MoCEntityData("Wraith", 3, EnumCreatureType.MONSTER, new SpawnListEntry(MoCEntityWraith.class, 6, 1, 4), new ArrayList<Type>(Arrays
                        .asList(Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP,
                                Type.WASTELAND, Type.DEAD, Type.SPOOKY))));
        mocEntityMap.put(
                "WWolf",
                new MoCEntityData("WWolf", 3, EnumCreatureType.MONSTER, new SpawnListEntry(MoCEntityWWolf.class, 8, 1, 3), new ArrayList<Type>(Arrays
                        .asList(Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP,
                                Type.WASTELAND))));
        mocEntityMap.put(
                "Manticore",
                new MoCEntityData("Manticore", 3, EnumCreatureType.MONSTER, new SpawnListEntry(MoCEntityManticore.class, 8, 1, 3), new ArrayList<Type>(
                        Arrays.asList(Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP,
                                Type.WASTELAND, Type.NETHER, Type.DEAD, Type.SPOOKY))));

        for (MoCEntityData entityData : mocEntityMap.values()) {
            if (entityData.getEntityName().equals("Wyvern")) {
                continue;
            }
            SpawnListEntry spawnEntry = entityData.getSpawnListEntry();
            for (BiomeDictionary.Type type : entityData.getBiomeTypes()) {
                for (Biome biome : BiomeDictionary.getBiomes(type)) {
                    if (!biome.getSpawnableList(entityData.getType()).contains(spawnEntry)) {
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
    protected void registerEntity(Class<? extends Entity> entityClass, String entityName) {
        if (proxy.debug) {
            MoCLog.logger.info("registerEntity " + entityClass + " with Mod ID " + MoCEntityID);
        }
        EntityRegistry.registerModEntity(new ResourceLocation("mocreatures:" + entityName.toLowerCase()), entityClass, entityName, MoCEntityID, instance, 128, 1, true);
        MoCEntityID += 1;
    }

    private void registerEntity(Class<? extends Entity> entityClass, String entityName, int eggColor, int eggDotsColor) {
        if (proxy.debug) {
            MoCLog.logger.info("registerEntity " + entityClass + " with Mod ID " + MoCEntityID);
        }
        EntityRegistry.registerModEntity(new ResourceLocation("mocreatures:" + entityName.toLowerCase()), entityClass, entityName, MoCEntityID, instance, 128, 1, true, eggColor, eggDotsColor);
        MoCEntityID += 1;
    }

    @SuppressWarnings("unused")
    private int getItemId(String name, int defaultId) {
        return proxy.mocSettingsConfig.get(CATEGORY_ITEM_IDS, "item_" + name, defaultId).getInt();
    }

    public static void burnPlayer(EntityPlayer player) {
        //TODO 4FIX
        //if (!mc.world.isRemote)
        //{
        //    inst.burned = true;
        //}
    }

    public static void freezePlayer(EntityPlayer player) {
        //TODO 4FIX
        //if (!mc.world.isRemote)
        //{
        //    inst.freezed = true;
        //    inst.freezedcounter = 0;
        //}
    }

    public static void poisonPlayer(EntityPlayer player) {
        //TODO 4FIX
        //if (!mc.world.isRemote)
        //{
        //    inst.poisoned = true;
        //    inst.poisoncounter = 0;
        //}
    }

    public static void showCreaturePedia(EntityPlayer player, String s) {
        //TODO 4FIX        mc.displayGuiScreen(new MoCGUIPedia(s, 256, 256));
    }

    public static void showCreaturePedia(String s) {
        //TODO 4FIX        EntityPlayer entityplayer = mc.player;
        //showCreaturePedia(entityplayer, s);
    }

    public static void updateSettings() {
        proxy.readGlobalConfigValues();
    }

    public static boolean isServer() {
        return (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER);
    }
}
