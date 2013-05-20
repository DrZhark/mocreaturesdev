package drzhark.mocreatures;

import static drzhark.mocreatures.MoCProperty.Type.INTEGER;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.storage.SaveHandler;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import drzhark.mocreatures.MoCProperty.Type;
import drzhark.mocreatures.entity.MoCEntityAmbient;
import drzhark.mocreatures.entity.MoCIMoCreature;
import drzhark.mocreatures.entity.ambient.MoCEntityAnt;
import drzhark.mocreatures.entity.ambient.MoCEntityBee;
import drzhark.mocreatures.entity.ambient.MoCEntityButterfly;
import drzhark.mocreatures.entity.ambient.MoCEntityCrab;
import drzhark.mocreatures.entity.ambient.MoCEntityCricket;
import drzhark.mocreatures.entity.ambient.MoCEntityDragonfly;
import drzhark.mocreatures.entity.ambient.MoCEntityFirefly;
import drzhark.mocreatures.entity.ambient.MoCEntityFly;
import drzhark.mocreatures.entity.ambient.MoCEntityMaggot;
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
import drzhark.mocreatures.entity.passive.MoCEntityFox;
import drzhark.mocreatures.entity.passive.MoCEntityGoat;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.entity.passive.MoCEntityKitty;
import drzhark.mocreatures.entity.passive.MoCEntityKomodo;
import drzhark.mocreatures.entity.passive.MoCEntityMouse;
import drzhark.mocreatures.entity.passive.MoCEntityOstrich;
import drzhark.mocreatures.entity.passive.MoCEntityRaccoon;
import drzhark.mocreatures.entity.passive.MoCEntitySnake;
import drzhark.mocreatures.entity.passive.MoCEntityTurkey;
import drzhark.mocreatures.entity.passive.MoCEntityTurtle;
import drzhark.mocreatures.entity.passive.MoCEntityWyvern;


public class MoCProxy implements IGuiHandler {

    public static String ARMOR_TEXTURE = "/mods/mocreatures/textures/armor/";
    public static String MODEL_TEXTURE = "/mods/mocreatures/textures/models/";
    public static String GUI_TEXTURE = "/mods/mocreatures/textures/gui/";
    public static String MISC_TEXTURE = "/mods/mocreatures/textures/misc/";

    //CONFIG VARIABLES
    public boolean useDefaultModEntityMappings;
    public boolean useDefaultModBiomeMappings;
    // Client Only
    public boolean displayPetHealth;
    public boolean displayPetName;
    public boolean displayPetIcons;
    public boolean animateTextures;

    public boolean attackDolphins;
    public boolean attackWolves;
    public boolean attackHorses;
    public boolean staticBed;
    public boolean staticLitter;

    public boolean easyBreeding;
    public boolean modifyVanillaSpawns;
    public boolean despawnVanilla;
    public boolean destroyDrops;
    public boolean enableOwnership;
    public boolean enableResetOwnership;
    public boolean elephantBulldozer;

    // griefing options
    public boolean golemDestroyBlocks;

    public int itemID;
    //new blocks IDs
    public int blockDirtID;
    public int blockGrassID;
    public int blockStoneID;
    public int blockLeafID;
    public int blockLogID;
    public int blockTallGrassID;
    public int blockPlanksID;
    public int WyvernDimension;
    public int WyvernBiomeID;

    public boolean worldGenCreatureSpawning;
    public boolean spawnCreatures;
    public boolean spawnMonsters;
    public boolean spawnAmbients;
    public boolean spawnWaterCreatures;
    public boolean debugCMS;
    public boolean checkAmbientLightLevel;
    public boolean disallowMonsterSpawningDuringDay;
    public int despawnLightLevel = 7;
    public int lightLevel = 7;
    public int maxMonsters;
    public int maxCreatures;
    public int maxAmbients;
    public int maxWaterCreatures;
    public int creatureSpawnTickRate;
    public int monsterSpawnTickRate;
    public int ambientSpawnTickRate;
    public int waterSpawnTickRate;
    public int despawnTickRate;
    public int monsterSpawnRange;
    public int maxTamed;
    public int maxOPTamed;
    public int zebraChance;
    public int particleFX;
    // defaults
    public int frequency = 6;
    public int minGroup = 1;
    public int maxGroup = 2;
    public int maxSpawnInChunk = 3;
    public float strength = 1;

    // ogre settings
    public float ogreStrength;
    public float caveOgreStrength;
    public float fireOgreStrength;
    public short ogreAttackRange;
    public short fireOgreChance;
    public short caveOgreChance;

    public boolean debugLogging;
    public boolean allowInstaSpawn;
    public boolean useCustomSpawner;
    public boolean needsUpdate = false;
    public boolean useDefaultBiomeGroups;
    public int activeScreen = -1;

    public MoCConfiguration mocGlobalConfig;
    public MoCConfiguration mocBiomeConfig;
    public MoCConfiguration mocStructureConfig;
    private File configFile;

    private static final String MOC_BEAR_NAME = "Horse";
    private static final String MOC_BEE_NAME = "Bee";
    private static final String MOC_BIGCAT_NAME = "BigCat";
    private static final String MOC_BIRD_NAME = "Bird";
    private static final String MOC_BOAR_NAME = "Boar";
    private static final String MOC_BUNNY_NAME = "Bunny";
    private static final String MOC_BUTTERFLY_NAME = "Butterfly";
    private static final String MOC_CAVEOGRE_NAME = "CaveOgre";
    private static final String MOC_CRICKET_NAME = "Cricket";
    private static final String MOC_CROCODILE_NAME = "Crocodile";
    private static final String MOC_DEER_NAME = "Deer";
    private static final String MOC_DOLPHIN_NAME = "Dolphin";
    private static final String MOC_DRAGONFLY_NAME = "Dragonfly";
    private static final String MOC_DUCK_NAME = "Duck";
    private static final String MOC_ELEPHANT_NAME = "Elephant";
    private static final String MOC_FIREFLY_NAME = "Firefly";
    private static final String MOC_FIREOGRE_NAME = "FireOgre";
    private static final String MOC_FISHY_NAME = "Fishy";
    private static final String MOC_FLAMEWRAITH_NAME = "FlameWraith";
    private static final String MOC_FLY_NAME = "Fly";
    private static final String MOC_FOX_NAME = "Fox";
    private static final String MOC_GOAT_NAME = "Goat";
    private static final String MOC_GOLEM_NAME = "BigGolem";
    private static final String MOC_HELLRAT_NAME = "Hellrat";
    private static final String MOC_HORSE_NAME = "Horse";
    private static final String MOC_HORSEMOB_NAME = "HorseMob";
    private static final String MOC_JELLYFISH_NAME = "Jellyfish";
    private static final String MOC_KITTY_NAME = "Kitty";
    private static final String MOC_KOMODO_NAME = "KomodoDragon";
    private static final String MOC_MOUSE_NAME = "Mouse";
    private static final String MOC_OGRE_NAME = "Ogre";
    private static final String MOC_OSTRICH_NAME = "Ostrich";
    private static final String MOC_PETSCORPION_NAME = "PetScorpion";
    private static final String MOC_RAT_NAME = "Rat";
    private static final String MOC_RAY_NAME = "Ray";
    private static final String MOC_SCORPION_NAME = "Scorpion";
    private static final String MOC_SHARK_NAME = "Shark";
    private static final String MOC_SNAIL_NAME = "Snail";
    private static final String MOC_SNAKE_NAME = "Snake";
    private static final String MOC_TURKEY_NAME = "Turkey";
    private static final String MOC_TURTLE_NAME = "Turtle";
    private static final String MOC_WEREWOLF_NAME = "Werewolf";
    private static final String MOC_WRAITH_NAME = "Wraith";
    private static final String MOC_WWOLF_NAME = "WWolf";

    private static final String MOD_CREATURES_FILE_ROOT = File.separator + "Creatures" + File.separator;
    private static final String MOD_BIOME_FILE_ROOT = File.separator + "Biomes" + File.separator;

    protected static final String CATEGORY_MOC_GENERAL_SETTINGS = "global-settings";
    protected static final String CATEGORY_MOC_CREATURE_GENERAL_SETTINGS = "creature-general-settings";
    protected static final String CATEGORY_MOC_MONSTER_GENERAL_SETTINGS = "monster-general-settings";
    protected static final String CATEGORY_MOC_WATER_CREATURE_GENERAL_SETTINGS = "water-mob-general-settings";
    protected static final String CATEGORY_MOC_AMBIENT_GENERAL_SETTINGS = "ambient-general-settings";
    protected static final String CATEGORY_MOC_UNDEFINED_FREQUENCIES = "undefined-frequencies";
    protected static final String CATEGORY_CUSTOMSPAWNER_SETTINGS = "customspawner-settings";
    protected static final String CATEGORY_ENTITY_BIOME_SETTINGS = "entity-biome-settings";
    protected static final String CATEGORY_MOC_ID_SETTINGS = "custom-id-settings";
    private static final String CATEGORY_MOD_MAPPINGS = "mod-mappings";
    private static final String CATEGORY_VANILLA_CREATURE_FREQUENCIES = "vanilla-creature-frequencies";
    private static final String CATEGORY_CREATURES = "Creatures";
    private static final String CATEGORY_ENTITY_CANSPAWN_SETTINGS = "entity-canspawn-settings";
    private static final String CATEGORY_OWNERSHIP_SETTINGS = "ownership-settings";
    private static final String CATEGORY_ENTITY_SPAWN_TYPES = "entity-spawn-types";
    private static final String CATEGORY_WORLD_SETTINGS = "world-settings";
    public static final String CATEGORY_ENTITY_SPAWN_SETTINGS = "entity-spawn-settings";

    private static final String BIOME_UNDEFINED_GROUP = "UNDEFINED";
    private static final String BIOME_ARCTIC_GROUP = "ARCTIC";
    private static final String BIOME_NORMAL_GROUP = "NORMAL";
    private static final String BIOME_FOREST_GROUP = "FOREST";
    private static final String BIOME_JUNGLE_GROUP = "JUNGLE";
    private static final String BIOME_MOUNTAIN_GROUP = "MOUNTAIN";
    private static final String BIOME_SWAMP_GROUP = "SWAMP";
    private static final String BIOME_DESERT_GROUP = "DESERT";
    private static final String BIOME_OCEAN_GROUP = "OCEAN";
    private static final String BIOME_RIVER_GROUP = "RIVER";
    private static final String BIOME_BEACHES_GROUP = "BEACHES";
    private static final String BIOME_MUSHROOM_GROUP = "MUSHROOM";
    private static final String BIOME_NETHER_GROUP = "NETHER";
    private static final String BIOME_THEEND_GROUP = "THEEND";
    private static final String BIOME_WYVERN_GROUP = "WYVERNLAIR";
    private static final String BIOME_MOBS_GROUP = "MOBS";
    private static final String BIOME_TWILIGHT_GROUP = "TWILIGHTFOREST";
    private static final String BIOME_VOLCANO_GROUP = "VOLCANO";
    private static final String BIOME_LAKE_GROUP = "LAKE";
    private static final String BIOME_GROUP_SETTINGS = "biome-group-settings";

    private static final String MOD_KEY_BIOMESOPLENTY = "biomesop";
    private static final String MOD_KEY_EXTRABIOMESXL = "extrabiomes";
    private static final String MOD_KEY_VANILLA = "vanilla";
    private static final String MOD_KEY_TWILIGHT = "twilightforest";
    public static MoCStructureData structureData = new MoCStructureData();

 // biome groups
    private static List defaultBiomeGroupForest = new ArrayList();
    private static List defaultBiomeGroupNormal = new ArrayList();
    private static List defaultBiomeGroupSwamp = new ArrayList();
    private static List defaultBiomeGroupDesert = new ArrayList();
    private static List defaultBiomeGroupArctic = new ArrayList();
    private static List defaultBiomeGroupJungle = new ArrayList();
    private static List defaultBiomeGroupMountain = new ArrayList();
    private static List defaultBiomeGroupNether = new ArrayList();
    private static List defaultBiomeGroupTheEnd = new ArrayList();
    private static List defaultBiomeGroupWyvern = new ArrayList();
    private static List defaultBiomeGroupMobs = new ArrayList();
    private static List defaultBiomeGroupOcean = new ArrayList();
    private static List defaultBiomeGroupBeaches = new ArrayList();
    private static List defaultBiomeGroupRivers = new ArrayList();
    private static List defaultBiomeGroupMushroom = new ArrayList();
    private static List defaultBiomeGroupTwilight = new ArrayList();
    private static List defaultBiomeGroupVolcano = new ArrayList();
    private static List defaultBiomeGroupLake = new ArrayList();

    protected static Map<String, MoCEntityData> entityMap = new HashMap<String, MoCEntityData>();
    private static final Map<String, MoCEntityData> mocEntityMap = new TreeMap<String, MoCEntityData>();
    private static final Map<String, MoCEntityData> vanillaEntityMap = new TreeMap<String, MoCEntityData>();
    private static Map<String, EnumCreatureType> entityTypes = new HashMap<String, EnumCreatureType>();
    private static Map<String, List<String>> modKeyMap = new HashMap<String, List<String>>();
    public static Map<String, MoCBiomeData> biomeMap = new TreeMap<String, MoCBiomeData>();
    public static Map<String, MoCBiomeGroupData> biomeGroupMap = new TreeMap<String, MoCBiomeGroupData>();
    public static Map<String, MoCBiomeGroupData> defaultBiomeGroupMap = new TreeMap<String, MoCBiomeGroupData>(); // used only for default biome groups
    public static Map<String, MoCEntityModData> entityModMap = new TreeMap<String, MoCEntityModData>();
    public static Map<String, MoCBiomeModData> biomeModMap = new TreeMap<String, MoCBiomeModData>();
    public static Map<String, MoCBiomeModData> tagConfigMap = new HashMap<String, MoCBiomeModData>();
    public static Map<String, String> entityModKeyMap = new HashMap<String, String>();
    public static Map<Integer, Class<? extends EntityLiving>> instaSpawnerMap = new HashMap<Integer, Class<? extends EntityLiving>>();
    public static Map<Class<? extends EntityLiving>, MoCEntityData> classToEntityMapping = new HashMap<Class<? extends EntityLiving>, MoCEntityData>();

    public void resetAllData()
    {
        entityModMap.clear();
        tagConfigMap.clear();
        biomeModMap.clear();
        mocEntityMap.clear();
        entityModKeyMap.clear();
        biomeGroupMap.clear();
        biomeModMap.clear();
        modKeyMap.clear();
        instaSpawnerMap.clear();
        classToEntityMapping.clear();
        genModConfiguration();
        this.readConfigValues();
    }

    //----------------CONFIG INITIALIZATION
    public void ConfigInit(FMLPreInitializationEvent event) 
    {
        mocGlobalConfig = new MoCConfiguration(new File(event.getSuggestedConfigurationFile().getParent(), "MoCreatures" + File.separator + "MoCGlobal.cfg"));
        mocBiomeConfig = new MoCConfiguration(new File(event.getSuggestedConfigurationFile().getParent(), "MoCreatures" + File.separator + "MoCBiomeGroups.cfg"));
        mocStructureConfig = new MoCConfiguration(new File(event.getSuggestedConfigurationFile().getParent(), "MoCreatures" + File.separator + "MoCStructures.cfg"));
        configFile = event.getSuggestedConfigurationFile();
        mocGlobalConfig.load();
        mocBiomeConfig.load();
        mocStructureConfig.load();
        genModConfiguration();
        this.readConfigValues();
        if (debugLogging) MoCreatures.log.info("Initializing MoCreatures Server Config File at " + event.getSuggestedConfigurationFile().getParent() + "MoCProperties.cfg");
    }

    public void genModConfiguration()
    {
        mocEntityMap.put("Ant", new MoCEntityData(MoCEntityAnt.class, "Ant", EnumCreatureType.ambient, 7, 1, 4, 4, new ArrayList(Arrays.asList("FOREST", "JUNGLE", "NORMAL"))));
        mocEntityMap.put("Bear", new MoCEntityData(MoCEntityBear.class, "Bear", EnumCreatureType.creature, 6, 1, 2, 2, new ArrayList(Arrays.asList("ARCTIC", "FOREST", "MOUNTAIN", "NORMAL"))));
        mocEntityMap.put("Bee", new MoCEntityData(MoCEntityBee.class, "Bee", EnumCreatureType.ambient, 7, 1, 4, 3, new ArrayList(Arrays.asList("FOREST", "JUNGLE", "NORMAL"))));
        mocEntityMap.put("BigCat", new MoCEntityData(MoCEntityBigCat.class, "BigCat", EnumCreatureType.creature,  6, 1, 2, 2, new ArrayList(Arrays.asList("ARCTIC", "FOREST", "JUNGLE", "MOUNTAIN", "NORMAL"))));
        mocEntityMap.put("BigGolem", new MoCEntityData(MoCEntityGolem.class, "BigGolem", EnumCreatureType.monster, 3, 1, 1, 1, new ArrayList(Arrays.asList("MOBS"))));
        mocEntityMap.put("Bird", new MoCEntityData(MoCEntityBird.class, "Bird", EnumCreatureType.creature, 15, 2, 3, 4, new ArrayList(Arrays.asList("FOREST", "JUNGLE", "MOUNTAIN", "NORMAL"))));
        mocEntityMap.put("Boar", new MoCEntityData(MoCEntityBoar.class, "Boar", EnumCreatureType.creature, 8, 2, 2, 3, new ArrayList(Arrays.asList("FOREST", "JUNGLE", "NORMAL"))));
        mocEntityMap.put("Bunny", new MoCEntityData(MoCEntityBunny.class, "Bunny", EnumCreatureType.creature, 10, 2, 3, 4, new ArrayList(Arrays.asList("ARCTIC", "FOREST", "JUNGLE", "NORMAL", "WYVERNLAIR"))));
        mocEntityMap.put("ButterFly", new MoCEntityData(MoCEntityButterfly.class, "ButterFly", EnumCreatureType.ambient, 8, 1, 3, 3, new ArrayList(Arrays.asList("FOREST", "JUNGLE", "MOUNTAIN", "NORMAL"))));
        mocEntityMap.put("Crab", new MoCEntityData(MoCEntityCrab.class, "Crab", EnumCreatureType.ambient, 8, 1, 2, 2, new ArrayList(Arrays.asList("BEACHES", "RIVER"))));
        mocEntityMap.put("Cricket", new MoCEntityData(MoCEntityCricket.class, "Cricket", EnumCreatureType.ambient, 8, 1, 2, 2, new ArrayList(Arrays.asList("FOREST", "JUNGLE", "MOUNTAIN", "NORMAL"))));
        mocEntityMap.put("Crocodile", new MoCEntityData(MoCEntityCrocodile.class, "Crocodile", EnumCreatureType.creature, 6, 1, 2, 2, new ArrayList(Arrays.asList("SWAMP"))));
        mocEntityMap.put("Deer", new MoCEntityData(MoCEntityDeer.class, "Deer", EnumCreatureType.creature, 8, 1, 2, 2, new ArrayList(Arrays.asList("FOREST", "NORMAL"))));
        mocEntityMap.put("Dolphin", new MoCEntityData(MoCEntityDolphin.class, "Dolphin", EnumCreatureType.waterCreature, 6, 1, 1, 1, new ArrayList(Arrays.asList("BEACHES", "OCEAN", "RIVER"))));
        mocEntityMap.put("DragonFly", new MoCEntityData(MoCEntityDragonfly.class, "DragonFly", EnumCreatureType.ambient, 6, 1, 2, 2, new ArrayList(Arrays.asList("FOREST", "JUNGLE", "MOUNTAIN", "NORMAL", "WYVERNLAIR"))));
        mocEntityMap.put("Duck", new MoCEntityData(MoCEntityDuck.class, "Duck", EnumCreatureType.creature, 8, 1, 2, 2, new ArrayList(Arrays.asList("FOREST", "JUNGLE", "NORMAL"))));
        mocEntityMap.put("Elephant", new MoCEntityData(MoCEntityElephant.class, "Elephant", EnumCreatureType.creature, 4, 1, 1, 1, new ArrayList(Arrays.asList("ARCTIC", "DESERT", "FOREST", "NORMAL"))));
        mocEntityMap.put("Firefly", new MoCEntityData(MoCEntityFirefly.class, "Firefly", EnumCreatureType.ambient, 8, 1, 2, 2, new ArrayList(Arrays.asList("FOREST", "NORMAL", "JUNGLE", "MOUNTAIN"))));
        mocEntityMap.put("Fishy", new MoCEntityData(MoCEntityFishy.class, "Fishy", EnumCreatureType.waterCreature, 12, 1, 6, 6, new ArrayList(Arrays.asList("BEACHES", "OCEAN", "RIVER", "SWAMP", "LAKE"))));
        mocEntityMap.put("FlameWraith", new MoCEntityData(MoCEntityFlameWraith.class, "FlameWraith", EnumCreatureType.monster, 5, 1, 1, 1, new ArrayList(Arrays.asList("MOBS", "NETHER"))));
        mocEntityMap.put("Fly", new MoCEntityData(MoCEntityFly.class, "Fly", EnumCreatureType.ambient, 8, 1, 2, 2, new ArrayList(Arrays.asList("FOREST", "JUNGLE", "MOUNTAIN", "NORMAL"))));
        mocEntityMap.put("Fox", new MoCEntityData(MoCEntityFox.class, "Fox", EnumCreatureType.creature, 8, 1, 1, 1, new ArrayList(Arrays.asList("ARCTIC", "FOREST", "NORMAL", "JUNGLE"))));
        mocEntityMap.put("Goat", new MoCEntityData(MoCEntityGoat.class, "Goat", EnumCreatureType.creature, 8, 1, 3, 3, new ArrayList(Arrays.asList("FOREST", "NORMAL", "MOUNTAIN"))));
        mocEntityMap.put("HellRat", new MoCEntityData(MoCEntityHellRat.class, "HellRat", EnumCreatureType.monster, 6, 1, 4, 4, new ArrayList(Arrays.asList("NETHER"))));
        mocEntityMap.put("Horse", new MoCEntityData(MoCEntityHorse.class, "Horse", EnumCreatureType.creature, 10, 1, 3, 4, new ArrayList(Arrays.asList("FOREST", "NORMAL", "MOUNTAIN"))));
        mocEntityMap.put("HorseMob", new MoCEntityData(MoCEntityHorseMob.class, "HorseMob", EnumCreatureType.monster, 8, 1, 3, 3, new ArrayList(Arrays.asList("MOBS", "NETHER"))));
        mocEntityMap.put("JellyFish", new MoCEntityData(MoCEntityJellyFish.class, "JellyFish", EnumCreatureType.waterCreature, 8, 1, 4, 4, new ArrayList(Arrays.asList("OCEAN", "RIVER"))));
        mocEntityMap.put("Kitty", new MoCEntityData(MoCEntityKitty.class, "Kitty", EnumCreatureType.creature, 8, 1, 2, 2, new ArrayList(Arrays.asList("NORMAL"))));
        mocEntityMap.put("KomodoDragon", new MoCEntityData(MoCEntityKomodo.class, "KomodoDragon", EnumCreatureType.creature, 8, 1, 2, 2, new ArrayList(Arrays.asList("SWAMP"))));
        mocEntityMap.put("Maggot", new MoCEntityData(MoCEntityMaggot.class, "Maggot", EnumCreatureType.ambient, 8, 1, 2, 2, new ArrayList(Arrays.asList("FOREST", "JUNGLE", "MOUNTAIN", "NORMAL"))));
        mocEntityMap.put("MediumFish", new MoCEntityData(MoCEntityMediumFish.class, "MediumFish", EnumCreatureType.waterCreature, 10, 1, 4, 4, new ArrayList(Arrays.asList("BEACHES", "OCEAN", "RIVER", "SWAMP", "LAKE"))));
        mocEntityMap.put("MiniGolem", new MoCEntityData(MoCEntityMiniGolem.class, "MiniGolem", EnumCreatureType.monster, 6, 1, 1, 1, new ArrayList(Arrays.asList("MOBS"))));
        mocEntityMap.put("Mouse", new MoCEntityData(MoCEntityMouse.class, "Mouse", EnumCreatureType.creature, 7, 1, 2, 2, new ArrayList(Arrays.asList("FOREST", "JUNGLE", "MOUNTAIN", "NORMAL"))));
        mocEntityMap.put("Ogre", new MoCEntityData(MoCEntityOgre.class, "Ogre", EnumCreatureType.monster, 8, 1, 1, 1, new ArrayList(Arrays.asList("MOBS", "NETHER"))));
        mocEntityMap.put("Ostrich", new MoCEntityData(MoCEntityOstrich.class, "Ostrich", EnumCreatureType.creature, 4, 1, 1, 1, new ArrayList(Arrays.asList("DESERT", "NORMAL"))));
        mocEntityMap.put("Piranha", new MoCEntityData(MoCEntityPiranha.class, "Piranha", EnumCreatureType.waterCreature, 4, 1, 3, 3, new ArrayList(Arrays.asList("BEACHES", "RIVER", "SWAMP"))));
        mocEntityMap.put("Raccoon", new MoCEntityData(MoCEntityRaccoon.class, "Raccoon", EnumCreatureType.creature, 8, 1, 2, 2, new ArrayList(Arrays.asList("FOREST", "NORMAL", "MOUNTAIN"))));
        mocEntityMap.put("Rat", new MoCEntityData(MoCEntityRat.class, "Rat", EnumCreatureType.monster, 7, 1, 2, 2, new ArrayList(Arrays.asList("MOBS"))));
        mocEntityMap.put("Ray", new MoCEntityData(MoCEntityRay.class, "Ray", EnumCreatureType.waterCreature, 10, 1, 2, 3, new ArrayList(Arrays.asList("OCEAN", "RIVER", "SWAMP"))));
        mocEntityMap.put("Scorpion", new MoCEntityData(MoCEntityScorpion.class, "Scorpion", EnumCreatureType.monster, 6, 1, 1, 1, new ArrayList(Arrays.asList("MOBS", "NETHER"))));
        mocEntityMap.put("Shark", new MoCEntityData(MoCEntityShark.class, "Shark", EnumCreatureType.waterCreature, 6, 1, 1, 1, new ArrayList(Arrays.asList("OCEAN"))));
        mocEntityMap.put("SilverSkeleton", new MoCEntityData(MoCEntitySilverSkeleton.class, "SilverSkeleton", EnumCreatureType.monster, 6, 1, 4, 4, new ArrayList(Arrays.asList("MOBS"))));
        mocEntityMap.put("Snail", new MoCEntityData(MoCEntitySnail.class, "Snail", EnumCreatureType.ambient, 7, 1, 2, 2, new ArrayList(Arrays.asList("FOREST", "JUNGLE", "NORMAL"))));
        mocEntityMap.put("Snake", new MoCEntityData(MoCEntitySnake.class, "Snake", EnumCreatureType.creature, 8, 1, 2, 2, new ArrayList(Arrays.asList("DESERT", "FOREST", "JUNGLE", "MOUNTAIN", "NORMAL", "WYVERNLAIR"))));
        mocEntityMap.put("SmallFish", new MoCEntityData(MoCEntitySmallFish.class, "SmallFish", EnumCreatureType.waterCreature, 12, 1, 6, 6, new ArrayList(Arrays.asList("BEACHES", "OCEAN", "RIVER", "SWAMP", "LAKE"))));
        mocEntityMap.put("Turkey", new MoCEntityData(MoCEntityTurkey.class, "Turkey", EnumCreatureType.creature, 8, 1, 2, 2, new ArrayList(Arrays.asList("NORMAL"))));
        mocEntityMap.put("Turtle", new MoCEntityData(MoCEntityTurtle.class, "Turtle", EnumCreatureType.creature, 6, 1, 2, 2, new ArrayList(Arrays.asList("SWAMP", "JUNGLE"))));
        mocEntityMap.put("Werewolf", new MoCEntityData(MoCEntityWerewolf.class, "Werewolf", EnumCreatureType.monster, 8, 1, 4, 4, new ArrayList(Arrays.asList("MOBS"))));
        mocEntityMap.put("Wraith", new MoCEntityData(MoCEntityWraith.class, "Wraith", EnumCreatureType.monster, 6, 1, 1, 1, new ArrayList(Arrays.asList("MOBS"))));
        mocEntityMap.put("WWolf", new MoCEntityData(MoCEntityWWolf.class, "WWolf", EnumCreatureType.monster, 8, 1, 3, 3, new ArrayList(Arrays.asList("MOBS"))));
        mocEntityMap.put("Wyvern", new MoCEntityData(MoCEntityWyvern.class, "Wyvern", EnumCreatureType.creature, 6, 1, 3, 3, new ArrayList(Arrays.asList("WYVERNLAIR"))));

        // vanilla
        vanillaEntityMap.put("Bat", new MoCEntityData(EntityBat.class, "Bat", EnumCreatureType.ambient, 10, 1, 2, 2, new ArrayList(Arrays.asList("FOREST", "JUNGLE", "NORMAL"))));
        vanillaEntityMap.put("Blaze", new MoCEntityData(EntityBlaze.class, "Blaze", EnumCreatureType.monster, 8, 1, 2, 4, new ArrayList(Arrays.asList(""))));
        vanillaEntityMap.put("CaveSpider", new MoCEntityData(EntityCaveSpider.class, "CaveSpider", EnumCreatureType.monster, 8, 1, 2, 2, new ArrayList(Arrays.asList("MOBS"))));
        vanillaEntityMap.put("Chicken", new MoCEntityData(EntityChicken.class, "Chicken", EnumCreatureType.creature, 8, 1, 2, 2, new ArrayList(Arrays.asList("FOREST", "MOUNTAIN", "NORMAL"))));
        vanillaEntityMap.put("Cow", new MoCEntityData(EntityCow.class, "Cow", EnumCreatureType.creature, 8, 1, 2, 2, new ArrayList(Arrays.asList("FOREST", "NORMAL"))));
        vanillaEntityMap.put("Creeper", new MoCEntityData(EntityCreeper.class, "Creeper", EnumCreatureType.monster, 8, 1, 4, 4, new ArrayList(Arrays.asList("MOBS"))));
        vanillaEntityMap.put("Enderman", new MoCEntityData(EntityEnderman.class, "Enderman", EnumCreatureType.monster, 1, 1, 4, 4, new ArrayList(Arrays.asList("MOBS", "THEEND"))));
        vanillaEntityMap.put("Ghast", new MoCEntityData(EntityGhast.class, "Ghast", EnumCreatureType.monster, 6, 1, 4, 4, new ArrayList(Arrays.asList("NETHER"))));
        vanillaEntityMap.put("LavaSlime", new MoCEntityData(EntityMagmaCube.class, "LavaSlime", EnumCreatureType.monster, 1, 1, 4, 4, new ArrayList(Arrays.asList("NETHER"))));
        vanillaEntityMap.put("MushroomCow", new MoCEntityData(EntityMooshroom.class, "MushroomCow", EnumCreatureType.creature, 8, 1, 4, 4, new ArrayList(Arrays.asList("MUSHROOM"))));
        vanillaEntityMap.put("Ozelot", new MoCEntityData(EntityOcelot.class, "Ozelot", EnumCreatureType.creature, 2, 1, 1, 3, new ArrayList(Arrays.asList("JUNGLE"))));
        vanillaEntityMap.put("Pig", new MoCEntityData(EntityPig.class, "Pig", EnumCreatureType.creature, 8, 1, 2, 2, new ArrayList(Arrays.asList("FOREST", "JUNGLE", "MOUNTAIN", "NORMAL"))));
        vanillaEntityMap.put("PigZombie", new MoCEntityData(EntityPigZombie.class, "PigZombie", EnumCreatureType.monster, 15, 4, 4, 4, new ArrayList(Arrays.asList("NETHER"))));
        vanillaEntityMap.put("Sheep", new MoCEntityData(EntitySheep.class, "Sheep", EnumCreatureType.creature, 12, 1, 2, 2, new ArrayList(Arrays.asList("FOREST", "MOUNTAIN", "NORMAL"))));
        vanillaEntityMap.put("Silverfish", new MoCEntityData(EntitySilverfish.class, "Silverfish", EnumCreatureType.monster, 0, 1, 4, 4, new ArrayList(Arrays.asList(""))));
        vanillaEntityMap.put("Skeleton", new MoCEntityData(EntitySkeleton.class, "Skeleton", EnumCreatureType.monster, 8, 1, 4, 4, new ArrayList(Arrays.asList("MOBS"))));
        vanillaEntityMap.put("Slime", new MoCEntityData(EntitySlime.class, "Slime", EnumCreatureType.monster, 6, 1, 1, 4, new ArrayList(Arrays.asList("MOBS"))));
        vanillaEntityMap.put("SnowMan", new MoCEntityData(EntitySnowman.class, "SnowMan", EnumCreatureType.monster, 0, 1, 1, 1, new ArrayList(Arrays.asList(""))));
        vanillaEntityMap.put("Spider", new MoCEntityData(EntitySpider.class, "Spider", EnumCreatureType.monster, 8, 1, 2, 2, new ArrayList(Arrays.asList("MOBS"))));
        vanillaEntityMap.put("Squid", new MoCEntityData(EntitySquid.class, "Squid", EnumCreatureType.waterCreature, 8, 1, 2, 2, new ArrayList(Arrays.asList("OCEAN", "RIVER"))));
        vanillaEntityMap.put("Witch", new MoCEntityData(EntityWitch.class, "Witch", EnumCreatureType.monster, 8, 1, 4, 4, new ArrayList(Arrays.asList(""))));
        vanillaEntityMap.put("Wolf", new MoCEntityData(EntityWolf.class, "Wolf", EnumCreatureType.creature, 8, 1, 2, 2, new ArrayList(Arrays.asList("ARCTIC", "FOREST"))));
        vanillaEntityMap.put("Zombie", new MoCEntityData(EntityZombie.class, "Zombie", EnumCreatureType.monster, 8, 1, 4, 4, new ArrayList(Arrays.asList("MOBS"))));

        defaultBiomeGroupMap.put(BIOME_FOREST_GROUP, new MoCBiomeGroupData(BIOME_FOREST_GROUP, defaultBiomeGroupForest));
        defaultBiomeGroupMap.put(BIOME_ARCTIC_GROUP, new MoCBiomeGroupData(BIOME_ARCTIC_GROUP, defaultBiomeGroupArctic));
        defaultBiomeGroupMap.put(BIOME_NORMAL_GROUP, new MoCBiomeGroupData(BIOME_NORMAL_GROUP, defaultBiomeGroupNormal));
        defaultBiomeGroupMap.put(BIOME_MOUNTAIN_GROUP, new MoCBiomeGroupData(BIOME_MOUNTAIN_GROUP, defaultBiomeGroupMountain));
        defaultBiomeGroupMap.put(BIOME_JUNGLE_GROUP, new MoCBiomeGroupData(BIOME_JUNGLE_GROUP, defaultBiomeGroupJungle));
        defaultBiomeGroupMap.put(BIOME_SWAMP_GROUP, new MoCBiomeGroupData(BIOME_SWAMP_GROUP, defaultBiomeGroupSwamp));
        defaultBiomeGroupMap.put(BIOME_DESERT_GROUP, new MoCBiomeGroupData(BIOME_DESERT_GROUP, defaultBiomeGroupDesert));
        defaultBiomeGroupMap.put(BIOME_RIVER_GROUP, new MoCBiomeGroupData(BIOME_RIVER_GROUP, defaultBiomeGroupRivers));
        defaultBiomeGroupMap.put(BIOME_OCEAN_GROUP, new MoCBiomeGroupData(BIOME_OCEAN_GROUP, defaultBiomeGroupOcean));
        defaultBiomeGroupMap.put(BIOME_BEACHES_GROUP, new MoCBiomeGroupData(BIOME_BEACHES_GROUP, defaultBiomeGroupBeaches));
        defaultBiomeGroupMap.put(BIOME_MUSHROOM_GROUP, new MoCBiomeGroupData(BIOME_MUSHROOM_GROUP, defaultBiomeGroupMushroom));
        defaultBiomeGroupMap.put(BIOME_MOBS_GROUP, new MoCBiomeGroupData(BIOME_MOBS_GROUP, defaultBiomeGroupMobs));
        defaultBiomeGroupMap.put(BIOME_NETHER_GROUP, new MoCBiomeGroupData(BIOME_NETHER_GROUP, defaultBiomeGroupNether));
        defaultBiomeGroupMap.put(BIOME_THEEND_GROUP, new MoCBiomeGroupData(BIOME_THEEND_GROUP, defaultBiomeGroupTheEnd));
        defaultBiomeGroupMap.put(BIOME_WYVERN_GROUP, new MoCBiomeGroupData(BIOME_WYVERN_GROUP, defaultBiomeGroupWyvern));
        defaultBiomeGroupMap.put(BIOME_TWILIGHT_GROUP,  new MoCBiomeGroupData(BIOME_TWILIGHT_GROUP, defaultBiomeGroupTwilight));
        defaultBiomeGroupMap.put(BIOME_VOLCANO_GROUP, new MoCBiomeGroupData(BIOME_VOLCANO_GROUP, defaultBiomeGroupVolcano));
        defaultBiomeGroupMap.put(BIOME_LAKE_GROUP, new MoCBiomeGroupData(BIOME_LAKE_GROUP, defaultBiomeGroupLake));

        // sort default lists
        Collections.sort(defaultBiomeGroupForest);
        Collections.sort(defaultBiomeGroupArctic);
        Collections.sort(defaultBiomeGroupNormal);
        Collections.sort(defaultBiomeGroupLake);
        Collections.sort(defaultBiomeGroupMountain);
        Collections.sort(defaultBiomeGroupJungle);
        Collections.sort(defaultBiomeGroupSwamp);
        Collections.sort(defaultBiomeGroupDesert);
        Collections.sort(defaultBiomeGroupRivers);
        Collections.sort(defaultBiomeGroupOcean);
        Collections.sort(defaultBiomeGroupMushroom);
        Collections.sort(defaultBiomeGroupMobs);
        Collections.sort(defaultBiomeGroupNether);
        Collections.sort(defaultBiomeGroupTheEnd);
        Collections.sort(defaultBiomeGroupWyvern);
        Collections.sort(defaultBiomeGroupTwilight);

        entityTypes.put("UNDEFINED", null);
        entityTypes.put("CREATURE", EnumCreatureType.creature);
        entityTypes.put("MONSTER", EnumCreatureType.monster);
        entityTypes.put("WATERCREATURE", EnumCreatureType.waterCreature);
        entityTypes.put("AMBIENT", EnumCreatureType.ambient);

        modKeyMap.put("drzhark", new ArrayList(Arrays.asList("MOC", "MoCreatures.cfg")));
        modKeyMap.put("biomesop", new ArrayList(Arrays.asList("BOP", "BiomesOPlenty.cfg")));
        modKeyMap.put("extrabiomes", new ArrayList(Arrays.asList("XL", "ExtraBiomesXL.cfg")));
        modKeyMap.put("twilightforest", new ArrayList(Arrays.asList("TL", "TwilightForest.cfg")));
        modKeyMap.put("gaia", new ArrayList(Arrays.asList("GAI", "GrimoireOfGaia.cfg")));
        modKeyMap.put("atomicstryker", new ArrayList(Arrays.asList("ATOM", "InfernalMobs.cfg")));
        modKeyMap.put("arsmagica", new ArrayList(Arrays.asList("ARS", "ArsMagica.cfg")));
        modKeyMap.put("projectzulu", new ArrayList(Arrays.asList("ZULU", "ProjectZulu.cfg")));
        modKeyMap.put("thaumcraft", new ArrayList(Arrays.asList("TC", "Thaumcraft.cfg")));
        modKeyMap.put("vanilla", new ArrayList(Arrays.asList("MC", "Vanilla.cfg")));
        modKeyMap.put("undefined", new ArrayList(Arrays.asList("U", "Undefined.cfg")));

        entityModKeyMap.put("drzhark", "MoCreatures.cfg");
        entityModKeyMap.put("biomesop", "BiomesOPlenty.cfg");
        entityModKeyMap.put("extrabiomes", "ExtraBiomesXL.cfg");
        entityModKeyMap.put("twilightforest", "TwilightForest.cfg");
        entityModKeyMap.put("gaia", "GrimoireOfGaia.cfg");
        entityModKeyMap.put("atomicstryker", "InfernalMobs.cfg");
        entityModKeyMap.put("arsmagica", "ArsMagica.cfg");
        entityModKeyMap.put("projectzulu", "ProjectZulu.cfg");
        entityModKeyMap.put("thaumcraft", "Thaumcraft.cfg");
        entityModKeyMap.put("tinker", "TinkerConstruct.cfg");
        entityModKeyMap.put("atum", "Atum.cfg");
        entityModKeyMap.put("advancedglowstone", "AngryCreatures.cfg");
        entityModKeyMap.put("minefantasy", "MineFantasy.cfg");
        entityModKeyMap.put("primitivemobs", "PrimitiveMobs.cfg");
        entityModKeyMap.put("atmosmobs", "AtmosMobs.cfg");
        entityModKeyMap.put("farlanders", "Farlanders.cfg");
        entityModKeyMap.put("vanilla", "Vanilla.cfg");
        entityModKeyMap.put("undefined", "Undefined.cfg");

        MoCConfigCategory modMapCat = mocGlobalConfig.getCategory(CATEGORY_MOD_MAPPINGS);

        for (Map.Entry<String, List<String>> keyEntry : modKeyMap.entrySet())
        {
            List<String> mapValues = keyEntry.getValue();
            if (mapValues != null && mapValues.size() == 2)
            {
                String tag = keyEntry.getValue().get(0);
                String configName = keyEntry.getValue().get(1);
                tag = mapValues.get(0);
                configName = mapValues.get(1);
                // check if key already exists
                MoCProperty prop = modMapCat.get(keyEntry.getKey());
                if (prop != null && prop.valueList != null && prop.valueList.size() == 2)
                {
                    // assign changed tag or configname
                    tag = prop.valueList.get(0);
                    configName = prop.valueList.get(1);
                }
                List<String> values = new ArrayList(Arrays.asList(tag, configName));
                modMapCat.put(keyEntry.getKey(), new MoCProperty(keyEntry.getKey(), values, Type.STRING));
                biomeModMap.put(keyEntry.getKey(), new MoCBiomeModData(keyEntry.getKey(), tag, new MoCConfiguration(new File(mocGlobalConfig.file.getParent(), MOD_BIOME_FILE_ROOT + configName))));
                entityModMap.put(keyEntry.getKey(), new MoCEntityModData(keyEntry.getKey(), tag, new MoCConfiguration(new File(mocGlobalConfig.file.getParent(), MOD_CREATURES_FILE_ROOT + configName))));
                if (debugLogging) MoCreatures.log.info("Added Mod Entity Mapping " + keyEntry.getKey() + " to file " + configName);
                tagConfigMap.put(tag, biomeModMap.get(keyEntry.getKey()));
                if (debugLogging) MoCreatures.log.info("Added Mod Biome Mapping " + keyEntry.getKey() + " with tag " + tag + " to file " + configName);
            }
        }

        // handle custom tag to config mappings
        for (Map.Entry<String, MoCProperty> propEntry : modMapCat.entrySet())
        {
            MoCProperty prop = propEntry.getValue();
            if (prop != null && !biomeModMap.containsKey(propEntry.getKey()))
            {
                if (prop.valueList.size() == 2)
                {
                    biomeModMap.put(propEntry.getKey(), new MoCBiomeModData(propEntry.getKey(), prop.valueList.get(0), new MoCConfiguration(new File(mocGlobalConfig.file.getParent(), MOD_BIOME_FILE_ROOT + prop.valueList.get(1)))));
                    if (debugLogging) MoCreatures.log.info("Added Custom Mod Biome Mapping " + propEntry.getKey() + " with tag " + prop.valueList.get(0) + " to file " + prop.valueList.get(1));
                    entityModMap.put(propEntry.getKey(), new MoCEntityModData(propEntry.getKey(), prop.valueList.get(0), new MoCConfiguration(new File(mocGlobalConfig.file.getParent(), MOD_CREATURES_FILE_ROOT + prop.valueList.get(1)))));
                    if (debugLogging) MoCreatures.log.info("Added Custom Mod Entity Mapping " + propEntry.getKey() + " with tag " + prop.valueList.get(0) + " to file " + prop.valueList.get(1));
                }
            }
        }
        // gen mocreatures entity defautls
        MoCConfiguration mocEntityConfig = entityModMap.get("drzhark").getModConfig();
        MoCConfiguration vanillaEntityConfig = entityModMap.get("vanilla").getModConfig();

        for (Map.Entry<String, MoCEntityData> mocEntry : mocEntityMap.entrySet())
        {
            mocEntityConfig.get(CATEGORY_ENTITY_BIOME_SETTINGS, mocEntry.getKey(), mocEntry.getValue().getBiomeGroups());
        }

        for (Map.Entry<String, MoCEntityData> vanillaEntry : vanillaEntityMap.entrySet())
        {
            vanillaEntityConfig.get(CATEGORY_ENTITY_BIOME_SETTINGS, vanillaEntry.getKey(), vanillaEntry.getValue().getBiomeGroups());
        }
        // gen mocreatures entity defautls

        for (Map.Entry<String, MoCEntityData> mocEntry : mocEntityMap.entrySet())
        {
            mocEntityConfig.get(CATEGORY_ENTITY_SPAWN_SETTINGS, mocEntry.getKey(), new ArrayList(Arrays.asList(mocEntry.getValue().getType().toString().toUpperCase(), Integer.toString(mocEntry.getValue().getFrequency()), Integer.toString(mocEntry.getValue().getMinSpawn()), Integer.toString(mocEntry.getValue().getMaxSpawn()), Integer.toString(mocEntry.getValue().getMaxInChunk()))));
            mocEntry.getValue().setEntityConfig(mocEntityConfig);
        }

        for (Map.Entry<String, MoCEntityData> vanillaEntry : vanillaEntityMap.entrySet())
        {
            vanillaEntityConfig.get(CATEGORY_ENTITY_SPAWN_SETTINGS, vanillaEntry.getKey(), new ArrayList(Arrays.asList(vanillaEntry.getValue().getType().toString().toUpperCase(), Integer.toString(vanillaEntry.getValue().getFrequency()), Integer.toString(vanillaEntry.getValue().getMinSpawn()), Integer.toString(vanillaEntry.getValue().getMaxSpawn()), Integer.toString(vanillaEntry.getValue().getMaxInChunk()))));
            vanillaEntry.getValue().setEntityConfig(vanillaEntityConfig);
        }
        // populate default vanilla/mocreature mod data
        for (Map.Entry<String, MoCEntityData> entityEntry : mocEntityMap.entrySet())
        {
            entityModMap.get("drzhark").addCreature(entityEntry.getValue());
        }
        for (Map.Entry<String, MoCEntityData> entityEntry : vanillaEntityMap.entrySet())
        {
            entityModMap.get("vanilla").addCreature(entityEntry.getValue());
        }
        mocEntityConfig.save();
        vanillaEntityConfig.save();
        mocGlobalConfig.save();
    }

    public void ConfigPostInit(FMLPostInitializationEvent event) {
        initializeBiomes();
        initializeEntities();
    }

    public void initializeEntities()
    {
        entityMap.clear();
        classToEntityMapping.clear();
        instaSpawnerMap.clear();
        for (Object obj : EntityList.classToStringMapping.entrySet())
        {
            Map.Entry entry = (Map.Entry)obj;
            Class clazz = null;
            String entityName = "";
            clazz = (Class)entry.getKey();
            EntityLiving entityliving = null;
            try
            {
                if (debugLogging) MoCreatures.log.info("Attempting to construct EntityLiving instance from class " + clazz + "...");
                entityliving = (EntityLiving) clazz.getConstructor(new Class[] { World.class }).newInstance(new Object[] { DimensionManager.getWorld(0) });
            }
            catch (Exception exception)
            {
                if (debugLogging) MoCreatures.log.info("Failed! " + clazz + " is not a valid EntityLiving, Skipping...");
                continue;
            } 

            entityName = (String)entry.getValue();
            if (debugLogging) MoCreatures.log.info("Success! Discovered entity "  + entityName + " with class " + clazz);
            if (entityName.contains("."))
            {
                if ((entityName.indexOf(".") + 1) < entityName.length())
                    entityName = entityName.substring(entityName.indexOf(".") + 1, entityName.length());
            }
            entityName = entityName.replaceAll("[^A-Za-z0-9]", ""); // remove all non-digits/alphanumeric
                 
            if (clazz != null && EntityLiving.class.isAssignableFrom(clazz))
            {
                if (debugLogging) MoCreatures.log.info("Detected " + clazz + " in EntityList, checking if valid...");
                EnumCreatureType creatureType = null;
                MoCEntityData entityData = null;

                if ((EntityAnimal.class.isAssignableFrom(clazz) || entityliving.isCreatureType(EnumCreatureType.creature, true)) && !(MoCEntityAmbient.class.isAssignableFrom(clazz)))
                {
                    creatureType = EnumCreatureType.creature;
                    entityData = new MoCEntityData(clazz, entityliving.entityId, creatureType, entityName);
                    if (!spawnCreatures || maxCreatures == 0)
                    {
                        entityData.setCanSpawn(false);
                    }
                }
                else if (((IMob.class.isAssignableFrom(clazz) || IRangedAttackMob.class.isAssignableFrom(clazz)) && (clazz != EntityMob.class)) || entityliving.isCreatureType(EnumCreatureType.monster, true))
                {
                    creatureType = EnumCreatureType.monster;
                    entityData = new MoCEntityData(clazz, entityliving.entityId, creatureType, entityName);
                    if (!spawnMonsters || maxMonsters == 0)
                    {
                        entityData.setCanSpawn(false);
                    }
                }
                else if (EntityAmbientCreature.class.isAssignableFrom(clazz) || entityliving.isCreatureType(EnumCreatureType.ambient, true)) //|| MoCEntityAmbient.class.isAssignableFrom(clazz))
                {
                    creatureType = EnumCreatureType.ambient;
                    entityData = new MoCEntityData(clazz, entityliving.entityId, creatureType, entityName);
                    if (!spawnAmbients || maxAmbients == 0)
                    {
                        entityData.setCanSpawn(false);
                    }
                }
                else if (EntityWaterMob.class.isAssignableFrom(clazz) || entityliving.isCreatureType(EnumCreatureType.waterCreature, true))
                {
                    creatureType = EnumCreatureType.waterCreature;
                    entityData = new MoCEntityData(clazz, entityliving.entityId, creatureType, entityName);
                    if (!spawnWaterCreatures || maxWaterCreatures == 0)
                    {
                        entityData.setCanSpawn(false);
                    }
                }
                else if (clazz != EntityLiving.class && clazz != EntityMob.class && clazz != MoCEntityFishBowl.class && clazz != MoCEntityLitterBox.class && clazz != MoCEntityEgg.class && clazz != MoCEntityKittyBed.class)
                {
                    entityData = new MoCEntityData(clazz, entityliving.entityId, creatureType, entityName);
                    entityData.setCanSpawn(false); // dont allow undefined types to spawn
                }
                else if (entityData == null)
                {
                    if (debugLogging) MoCreatures.log.info("Entity " + clazz + " is not valid, skipping...");
                    continue;
                }
                entityData.setEntityName(entityName);
                entityData.setEntityID(entityliving.entityId);
                if (debugLogging) MoCreatures.log.info("Added " + ((creatureType == null) ? "UNDEFINED" : creatureType.toString().toUpperCase()) + " " + entry.getKey() + " with name " + entityName);
                // handle frequencies
                // assign config for type of mod
                String entityClass = clazz.getName().toLowerCase();
                MoCConfiguration entityConfig = entityModMap.get("undefined").getModConfig();

                boolean undefined = true;
                if (debugLogging) MoCreatures.log.info("Searching for mod " + entityClass + " belongs to...");

                if (entityClass.contains("net.minecraft.entity")|| entityClass.toString().length() <= 3) // vanilla
                {
                    MoCEntityModData modData = entityModMap.get("vanilla");
                    if (debugLogging) MoCreatures.log.info("Matched mod " + "Vanilla to " + entityClass);
                    if (!modData.addCreature(entityData))
                    {
                        entityData = modData.getCreature(entityName);
                        entityData.setEntityID(entityliving.entityId);
                        entityData.setEntityMod(modData);
                    }
                    entityData.setEntityMod(modData);
                    entityConfig = modData.getModConfig();
                    entityData.setEntityConfig(entityConfig);
                    undefined = false;
                }
                else { // custom
                    for (Map.Entry<String, MoCEntityModData> modEntry : entityModMap.entrySet())
                    {
                        if (entityClass.contains(modEntry.getKey()))
                        {
                            // Found match, use config
                            MoCEntityModData modData = modEntry.getValue();
                            if (debugLogging) MoCreatures.log.info("Matched mod " + modEntry.getKey() + " to " + entityClass);
                            if (!modData.addCreature(entityData))
                            {
                                entityData = modData.getCreature(entityName);
                                entityData.setEntityID(entityliving.entityId);
                                entityData.setEntityMod(modData);
                            }
                            entityData.setEntityMod(modData);
                            entityConfig = modData.getModConfig();
                            entityData.setEntityConfig(entityConfig);
                            undefined = false;
                            break;
                        }
                    }
                }
                if (undefined)
                {
                    if (debugLogging) MoCreatures.log.info("Detected Undefined Entity Class " + entityClass + ". You must add a mapping for this class in MoCGlobal.cfg.");
                    String tokens[] = entityClass.split("\\.");

                    // no mapping for class in config so lets generate one
                    if (tokens.length >= 2)
                    {
                        String modKey = tokens[0];
                        if (modKey.equalsIgnoreCase("mod") || modKey.equalsIgnoreCase("mods") || modKey.equalsIgnoreCase("mob") || modKey.equalsIgnoreCase("mobs") || modKey.equalsIgnoreCase("com") || modKey.equalsIgnoreCase("coms"))
                            modKey = tokens[1];
                        else if (entityClass.contains("net.minecraft") && tokens.length > 2)
                            modKey = tokens[2];
                        String configName = modKey + ".cfg";

                        biomeModMap.put(modKey, new MoCBiomeModData(modKey, modKey, new MoCConfiguration(new File(mocGlobalConfig.file.getParent(), MOD_BIOME_FILE_ROOT + configName))));
                        if (debugLogging) MoCreatures.log.info("AddedAutomatic Mod Biome Mapping " + modKey + " with tag " + modKey + " to file " + configName);
                        entityModMap.put(modKey, new MoCEntityModData(modKey, modKey, new MoCConfiguration(new File(mocGlobalConfig.file.getParent(), MOD_CREATURES_FILE_ROOT + configName))));
                        if (debugLogging) MoCreatures.log.info("Added Automatic Mod Entity Mapping " + modKey + " to file " + configName);
                        tagConfigMap.put(modKey, biomeModMap.get(modKey));
                        MoCConfigCategory modMapCat = mocGlobalConfig.getCategory(CATEGORY_MOD_MAPPINGS);
                        modMapCat.put(modKey, new MoCProperty(modKey, new ArrayList(Arrays.asList(modKey.toUpperCase(), configName)), Type.STRING, "automatically generated"));

                        MoCEntityModData modData = entityModMap.get(modKey);
                        if (debugLogging) MoCreatures.log.info("Matched mod " + modKey + " to " + entityClass);
                        if (!modData.addCreature(entityData))
                        {
                            entityData = modData.getCreature(entityName);
                            entityData.setEntityID(entityliving.entityId);
                            entityData.setEntityMod(modData);
                        }
                        entityData.setEntityMod(modData);
                        entityConfig = modData.getModConfig();
                        entityData.setEntityConfig(entityConfig);
                    }
                    else 
                    {
                        if (debugLogging) MoCreatures.log.info("Could not generate an automatic mod mapping for entity " + entityClass);
                        continue;
                    }
                }
                classToEntityMapping.put(clazz, entityData); // store for structure use
                entityMap.put(entityData.getEntityMod().getModTag() + "|" + entityData.getEntityName(), entityData); // used for fast lookups
                instaSpawnerMap.put(entityliving.entityId, entityliving.getClass()); // used for instaspawner
                if (!entityConfig.getCategory(CATEGORY_ENTITY_SPAWN_SETTINGS).containsKey(entityName))
                {
                    entityConfig.get(CATEGORY_ENTITY_SPAWN_SETTINGS, entityName, new ArrayList(Arrays.asList( ((creatureType == null) ? "UNDEFINED" : creatureType.toString().toUpperCase()), new Integer(frequency).toString(), new Integer(minGroup).toString(), new Integer(maxGroup).toString(), new Integer(maxSpawnInChunk).toString())));
                }
                else 
                {
                    for (Map.Entry<String, MoCProperty> propEntry : entityConfig.getCategory(CATEGORY_ENTITY_SPAWN_SETTINGS).getValues().entrySet())
                    {
                        if (propEntry.getKey().equalsIgnoreCase(entityName))
                        {
                            // handle entity config
                            MoCProperty property = propEntry.getValue();
                            if (property.valueList != null && property.valueList.size() == 5)
                            {
                                entityData.setType(entityTypes.get(property.valueList.get(0)));
                                entityData.setFrequency(Integer.parseInt(property.valueList.get(1)));
                                entityData.setMinSpawn(Integer.parseInt(property.valueList.get(2)));
                                entityData.setMaxSpawn(Integer.parseInt(property.valueList.get(3)));
                                entityData.setMaxInChunk(Integer.parseInt(property.valueList.get(4)));
                                if (entityData.getType() != null && entityData.getFrequency() > 0 && entityData.getMinSpawn() > 0 && entityData.getMaxSpawn() > 0 && entityData.getMaxInChunk() > 0)
                                    entityData.setCanSpawn(true);
                                if (entityData.getType() == EnumCreatureType.creature && (!spawnCreatures || maxCreatures == 0))
                                    entityData.setCanSpawn(false);
                                if (entityData.getType() == EnumCreatureType.waterCreature && (!spawnWaterCreatures || maxWaterCreatures == 0))
                                    entityData.setCanSpawn(false);
                                if (entityData.getType() == EnumCreatureType.monster && (!spawnMonsters || maxMonsters == 0))
                                    entityData.setCanSpawn(false);
                                if (entityData.getType() == EnumCreatureType.ambient && (!spawnAmbients || maxAmbients == 0))
                                    entityData.setCanSpawn(false);
                                break;
                            }
                            else if (debugLogging)
                            {
                                MoCreatures.log.severe("Detected invalid frequency value for entity " + entityName);
                            }
                            entityData.setEntityName(entityName);
                        }
                    }
                }
                mocGlobalConfig.save();
                MoCConfigCategory entityBiomeCategory = entityConfig.getCategory(CATEGORY_ENTITY_BIOME_SETTINGS);
                // Add spawnable biomes for each entity
                if (entityBiomeCategory.get(entityName) != null && entityBiomeCategory.get(entityName).valueList != null)
                {
                    List<String> biomeList = entityBiomeCategory.get(entityName).valueList;
                    for (int i = 0; i < biomeList.size(); i++)
                    {
                        String biomeGroupName = biomeList.get(i);
                        List<BiomeGenBase> spawnBiomes = new ArrayList<BiomeGenBase>();
                        if (mocBiomeConfig.hasCategory(biomeGroupName.toLowerCase()))
                        {
                            MoCProperty biomeProps = mocBiomeConfig.getCategory(biomeGroupName).get(biomeGroupName);
                            for (int j = 0; j < biomeProps.valueList.size(); j++)
                            {
                                List<String> biomeParts = parseName(biomeProps.valueList.get(j));
                                if (biomeParts.size() == 2) // valid
                                {
                                    String tag = biomeParts.get(0);
                                    String biomeName = biomeParts.get(1);
                                    if (tagConfigMap.get(tag).getBiome(biomeProps.valueList.get(j)) != null)
                                    {
                                        spawnBiomes.add(tagConfigMap.get(tag).getBiome(biomeProps.valueList.get(j)));
                                        entityData.addSpawnBiome(tagConfigMap.get(tag).getBiome(biomeProps.valueList.get(j)));
                                        if (debugLogging) MoCreatures.log.info("Added " + tagConfigMap.get(tag).getBiome(biomeProps.valueList.get(j)) + " to entity " + entityName + " spawnBiomes");
                                    }
                                    else if (debugLogging) MoCreatures.log.info("Invalid Biome " + biomeProps.valueList.get(j) + " was found in config category " + biomeGroupName + " in MoCBiomeGroups.cfg. Note: This may also mean you do not have the Biome Mod installed.");
                                }
                            }
                            entityData.addBiomeGroupSpawnMap(biomeGroupName, spawnBiomes);
                        }
                    }
                    entityData.setBiomeGroups(biomeList);
                }
                else // populate empty list so GUI can add to it
                {
                    MoCProperty prop = new MoCProperty(entityName, new ArrayList(Arrays.asList()), Type.STRING);
                    entityBiomeCategory.put(entityName, prop);
                    entityData.setBiomeGroups(prop.valueList);
                }
                entityConfig.save();
            }
        }
        mocGlobalConfig.save();
    }

    public void syncConfigSettings()
    {
        initializeBiomes();
        initializeEntities();
    }

    public void initializeBiomes()
    {
        biomeMap.clear();

        for (int i = 0; i < BiomeGenBase.biomeList.length; i++)
        {
            if (BiomeGenBase.biomeList[i] != null)
            {
                BiomeGenBase biome = BiomeGenBase.biomeList[i];
                String biomeName = BiomeGenBase.biomeList[i].biomeName;
                String biomeClass = BiomeGenBase.biomeList[i].getClass().toString();
                MoCBiomeData biomeData = new MoCBiomeData(biome);
                if (debugLogging) MoCreatures.log.info("Detected Biome " + biomeName + " with class " + biomeClass);
                boolean found = false;
                MoCBiomeModData biomeModData = null;
                for (Map.Entry<String, MoCBiomeModData> modEntry : biomeModMap.entrySet())
                {
                    if (biomeClass.contains(modEntry.getKey()))
                    {
                        // Found match, use config
                        if (debugLogging) MoCreatures.log.info("Matched mod " + modEntry.getKey() + " to " + biomeClass);
                        MoCBiomeModData modData = modEntry.getValue();
                        biomeData.setTag(modData.getModTag()); // needed for undefined
                        biomeData.setDefined((true));
                        if (!modData.getBiomes().contains(modData.getModTag() + "|" + biomeData.getBiomeName()))
                        {
                            modData.addBiome(biomeData);
                        }

                        biomeMap.put(modData.getModTag() + "|" + biomeName, biomeData);
                        found = true;
                        break;
                    }
                    else if ((biomeClass.contains("net.minecraft") || biomeClass.length() <= 3) && modEntry.getKey().equalsIgnoreCase("vanilla")) // special case for vanilla
                    {
                        if (debugLogging) MoCreatures.log.info("Matched mod " + modEntry.getKey() + " to " + biomeClass);
                        MoCBiomeModData modData = modEntry.getValue();
                        biomeData.setTag(modData.getModTag());
                        biomeData.setDefined((true));
                        if (!modData.getBiomes().contains(modData.getModTag() + "|" + biomeData.getBiomeName()))
                        {
                            modData.addBiome(biomeData);
                        }

                        biomeMap.put(modData.getModTag() + "|" + biomeName, biomeData);
                        found = true;
                        break;
                    }
                }
                if (!found) // store in undefined
                {
                    if (debugLogging) MoCreatures.log.warning("Could not match Biome " + biomeName + " with class " + biomeClass + " to a mod. Adding to Undefined.cfg");
                    MoCBiomeModData modData = biomeModMap.get("undefined");
                    biomeData.setTag(modData.getModTag());
                    biomeData.setDefined((true));
                    if (!modData.getBiomes().contains(modData.getModTag() + "|" + biomeData.getBiomeName()))
                    {
                        modData.addBiome(biomeData);
                    }
                    biomeMap.put(modData.getModTag() + "|" + biomeName, biomeData);
                    break;
                }
            }
        }
        // save configs
        for (Map.Entry<String, MoCBiomeModData> modEntry : biomeModMap.entrySet())
        {
            MoCBiomeModData modData = modEntry.getValue();
            MoCProperty prop = modData.getModConfig().get("biomes", "biomes");
            if (prop!= null && prop.valueList != null)
            {
                prop.valueList = modData.getBiomes();
            }
            else {
                modData.getModConfig().get("biomes", "biomes", modData.getBiomes());
            }
            modData.getModConfig().save();
        }
        
        mocGlobalConfig.addCustomCategoryComment("mod-mappings", "Mod Biome Mappings\n" + 
                "You may change tag values but do NOT change the default keys since they are used to generate our defaults.\n" +
                "For example, 'twilightforest=TL:TwilightForest.cfg' may be changed to 'twilightforest=TWL:TWL.cfg' but may NOT be changed to 'twilight=TWL:TWL.cfg'");
        // update tags in our defaults if necessary
        mocGlobalConfig.save();

        // generate default biomes for our default groups
        /********** FOREST **********/
        // BOP
        defaultBiomeGroupForest.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Bamboo Forest");
        defaultBiomeGroupForest.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Birch Forest");
        defaultBiomeGroupForest.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Boreal Forest");
        defaultBiomeGroupForest.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Cherry Blossom Grove");
        defaultBiomeGroupForest.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Coniferous Forest");
        defaultBiomeGroupForest.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Dead Forest");
        defaultBiomeGroupForest.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Deciduous Forest");
        defaultBiomeGroupForest.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Forest"); // BOP VANILLA
        defaultBiomeGroupForest.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Grove");
        defaultBiomeGroupForest.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Jade Cliffs");
        defaultBiomeGroupForest.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Maple Woods");
        defaultBiomeGroupForest.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Mystic Grove");
        defaultBiomeGroupForest.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Ominous Woods");
        defaultBiomeGroupForest.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Orchard");
        defaultBiomeGroupForest.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Origin Valley");
        defaultBiomeGroupForest.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Rainforest");
        defaultBiomeGroupForest.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Redwood Forest");
        defaultBiomeGroupForest.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Seasonal Forest");
        defaultBiomeGroupForest.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Spruce Woods");
        defaultBiomeGroupForest.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Temperate Rainforest");
        defaultBiomeGroupForest.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Tropical Rainforest");
        defaultBiomeGroupForest.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Tropics");
        defaultBiomeGroupForest.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Woodland");
        
        // XL
        defaultBiomeGroupForest.add(biomeModMap.get(MOD_KEY_EXTRABIOMESXL).getModTag() + "|Autumn Woods");
        defaultBiomeGroupForest.add(biomeModMap.get(MOD_KEY_EXTRABIOMESXL).getModTag() + "|Birch Forest");
        defaultBiomeGroupForest.add(biomeModMap.get(MOD_KEY_EXTRABIOMESXL).getModTag() + "|Forested Hills");
        defaultBiomeGroupForest.add(biomeModMap.get(MOD_KEY_EXTRABIOMESXL).getModTag() + "|Forested Island");
        defaultBiomeGroupForest.add(biomeModMap.get(MOD_KEY_EXTRABIOMESXL).getModTag() + "|Pine Forest");
        defaultBiomeGroupForest.add(biomeModMap.get(MOD_KEY_EXTRABIOMESXL).getModTag() + "|Redwood Forest");
        defaultBiomeGroupForest.add(biomeModMap.get(MOD_KEY_EXTRABIOMESXL).getModTag() + "|Lush Redwoods");
        defaultBiomeGroupForest.add(biomeModMap.get(MOD_KEY_EXTRABIOMESXL).getModTag() + "|Rainforest");
        defaultBiomeGroupForest.add(biomeModMap.get(MOD_KEY_EXTRABIOMESXL).getModTag() + "|Temperate Rainforest");
        defaultBiomeGroupForest.add(biomeModMap.get(MOD_KEY_EXTRABIOMESXL).getModTag() + "|Woodlands");
        // MC
        defaultBiomeGroupForest.add(biomeModMap.get(MOD_KEY_VANILLA).getModTag() + "|Forest");
        defaultBiomeGroupForest.add(biomeModMap.get(MOD_KEY_VANILLA).getModTag() + "|ForestHills");
        
        /********** NORMAL **********/
        // BoP
        defaultBiomeGroupNormal.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Chaparral");
        defaultBiomeGroupNormal.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Drylands");
        defaultBiomeGroupNormal.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Field");
        defaultBiomeGroupNormal.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Garden");
        defaultBiomeGroupNormal.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Oasis");
        defaultBiomeGroupNormal.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Heathland");
        defaultBiomeGroupNormal.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Meadow");
        defaultBiomeGroupNormal.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Outback");
        defaultBiomeGroupNormal.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Pasture");
        defaultBiomeGroupNormal.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Plains"); // BOP VANILLA
        defaultBiomeGroupNormal.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Prairie");
        defaultBiomeGroupNormal.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Scrubland");
        defaultBiomeGroupNormal.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Shield");
        defaultBiomeGroupNormal.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Shrubland");
        defaultBiomeGroupNormal.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Steppe");
        defaultBiomeGroupNormal.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Thicket");
        defaultBiomeGroupNormal.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Deadlands");
        defaultBiomeGroupNormal.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Promised Land");
        // XL
        defaultBiomeGroupNormal.add(biomeModMap.get(MOD_KEY_EXTRABIOMESXL).getModTag() + "|Meadow");
        defaultBiomeGroupNormal.add(biomeModMap.get(MOD_KEY_EXTRABIOMESXL).getModTag() + "|Savanna");
        defaultBiomeGroupNormal.add(biomeModMap.get(MOD_KEY_EXTRABIOMESXL).getModTag() + "|Shrubland");
        // Vanilla
        defaultBiomeGroupNormal.add(biomeModMap.get(MOD_KEY_VANILLA).getModTag() + "|Plains");
        

        /********** SWAMP **********/
        // BoP
        defaultBiomeGroupSwamp.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Bayou");
        defaultBiomeGroupSwamp.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Bog");
        defaultBiomeGroupSwamp.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Fen");
        defaultBiomeGroupSwamp.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Mangrove");
        defaultBiomeGroupSwamp.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Marsh");
        defaultBiomeGroupSwamp.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Lush Swamp");
        defaultBiomeGroupSwamp.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Dead Swamp");
        defaultBiomeGroupSwamp.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Grassland");
        defaultBiomeGroupSwamp.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Swampland"); // BOP VANILLA
        defaultBiomeGroupSwamp.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Swampwoods");
        defaultBiomeGroupSwamp.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Wetland");
        // XL
        
        defaultBiomeGroupSwamp.add(biomeModMap.get(MOD_KEY_EXTRABIOMESXL).getModTag() + "|Green Swamplands");
        defaultBiomeGroupSwamp.add(biomeModMap.get(MOD_KEY_EXTRABIOMESXL).getModTag() + "|Marsh");
        // Vanilla
        defaultBiomeGroupSwamp.add(biomeModMap.get(MOD_KEY_VANILLA).getModTag() + "|Swampland");

        /********** DESERT **********/
        // BoP
        defaultBiomeGroupDesert.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Badlands");
        defaultBiomeGroupDesert.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Dunes");
        defaultBiomeGroupDesert.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Desert"); // BOP VANILLA
        defaultBiomeGroupDesert.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Lush Desert");
        defaultBiomeGroupDesert.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Mesa");
        defaultBiomeGroupDesert.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Oasis");
        defaultBiomeGroupDesert.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Quagmire");
        defaultBiomeGroupDesert.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Savanna");
        defaultBiomeGroupDesert.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Wasteland");
        // XL
        defaultBiomeGroupDesert.add(biomeModMap.get(MOD_KEY_EXTRABIOMESXL).getModTag() + "|Mountainous Desert");
        defaultBiomeGroupDesert.add(biomeModMap.get(MOD_KEY_EXTRABIOMESXL).getModTag() + "|Mountain Ridge");
        defaultBiomeGroupDesert.add(biomeModMap.get(MOD_KEY_EXTRABIOMESXL).getModTag() + "|Wasteland");

        // MC
        defaultBiomeGroupDesert.add(biomeModMap.get(MOD_KEY_VANILLA).getModTag() + "|Desert");
        defaultBiomeGroupDesert.add(biomeModMap.get(MOD_KEY_VANILLA).getModTag() + "|DesertHills");

        /********** ARCTIC **********/
        // BoP
        defaultBiomeGroupArctic.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Frost Forest");
        defaultBiomeGroupArctic.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Arctic Forest");
        defaultBiomeGroupArctic.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Alps");
        defaultBiomeGroupArctic.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Arctic");
        defaultBiomeGroupArctic.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Glacier");
        defaultBiomeGroupArctic.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Ice Sheet");
        defaultBiomeGroupArctic.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Icy Hills");
        defaultBiomeGroupArctic.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Snowy Woods");
        defaultBiomeGroupArctic.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Taiga");
        defaultBiomeGroupArctic.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Tundra");

        // vanilla
        defaultBiomeGroupArctic.add(biomeModMap.get(MOD_KEY_VANILLA).getModTag() + "|Taiga");
        defaultBiomeGroupArctic.add(biomeModMap.get(MOD_KEY_VANILLA).getModTag() + "|FrozenOcean");
        defaultBiomeGroupArctic.add(biomeModMap.get(MOD_KEY_VANILLA).getModTag() + "|FrozenRiver");
        defaultBiomeGroupArctic.add(biomeModMap.get(MOD_KEY_VANILLA).getModTag() + "|Ice Plains");
        defaultBiomeGroupArctic.add(biomeModMap.get(MOD_KEY_VANILLA).getModTag() + "|Ice Mountains");
        defaultBiomeGroupArctic.add(biomeModMap.get(MOD_KEY_VANILLA).getModTag() + "|TaigaHills");

        // XL
        defaultBiomeGroupArctic.add(biomeModMap.get(MOD_KEY_EXTRABIOMESXL).getModTag() + "|Alpine");
        defaultBiomeGroupArctic.add(biomeModMap.get(MOD_KEY_EXTRABIOMESXL).getModTag() + "|Ice Wasteland");
        defaultBiomeGroupArctic.add(biomeModMap.get(MOD_KEY_EXTRABIOMESXL).getModTag() + "|Mountain Taiga");
        defaultBiomeGroupArctic.add(biomeModMap.get(MOD_KEY_EXTRABIOMESXL).getModTag() + "|Snow Forest");
        defaultBiomeGroupArctic.add(biomeModMap.get(MOD_KEY_EXTRABIOMESXL).getModTag() + "|Snowy Rainforest");
        defaultBiomeGroupArctic.add(biomeModMap.get(MOD_KEY_EXTRABIOMESXL).getModTag() + "|Tundra");

        /********** JUNGLE **********/
        // BOP
        defaultBiomeGroupJungle.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Jungle"); // BOP VANILLA
        // XL
        defaultBiomeGroupJungle.add(biomeModMap.get(MOD_KEY_EXTRABIOMESXL).getModTag() + "|Extreme Jungle");
        defaultBiomeGroupJungle.add(biomeModMap.get(MOD_KEY_EXTRABIOMESXL).getModTag() + "|Mini Jungle");
        // MC
        defaultBiomeGroupJungle.add(biomeModMap.get(MOD_KEY_VANILLA).getModTag() + "|Jungle");
        defaultBiomeGroupJungle.add(biomeModMap.get(MOD_KEY_VANILLA).getModTag() + "|JungleHills");

        /********** MOUNTAIN **********/
        // BoP
        defaultBiomeGroupMountain.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Canyon");
        defaultBiomeGroupMountain.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Crag");
        defaultBiomeGroupMountain.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Extreme Hills"); // BOP VANILLA
        defaultBiomeGroupMountain.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Highland");
        defaultBiomeGroupMountain.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Moor");
        defaultBiomeGroupMountain.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Mountain");
        defaultBiomeGroupMountain.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Sacred Springs");
        // XL
        defaultBiomeGroupMountain.add(biomeModMap.get(MOD_KEY_EXTRABIOMESXL).getModTag() + "|Green Hills");
        defaultBiomeGroupMountain.add(biomeModMap.get(MOD_KEY_EXTRABIOMESXL).getModTag() + "|Forested Hills");
        defaultBiomeGroupMountain.add(biomeModMap.get(MOD_KEY_EXTRABIOMESXL).getModTag() + "|Mountain Ridge");
        // MC
        defaultBiomeGroupMountain.add(biomeModMap.get(MOD_KEY_VANILLA).getModTag() + "|Extreme Hills");
        defaultBiomeGroupMountain.add(biomeModMap.get(MOD_KEY_VANILLA).getModTag() + "|Extreme Hills Edge");

        /********** OCEAN **********/
        // BOP
        defaultBiomeGroupOcean.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Shore");
        // MC
        defaultBiomeGroupOcean.add(biomeModMap.get(MOD_KEY_VANILLA).getModTag() + "|Ocean");
        defaultBiomeGroupOcean.add(biomeModMap.get(MOD_KEY_VANILLA).getModTag() + "|FrozenOcean");
        
        /********** RIVER **********/
        defaultBiomeGroupRivers.add(biomeModMap.get(MOD_KEY_VANILLA).getModTag() + "|River");
        defaultBiomeGroupRivers.add(biomeModMap.get(MOD_KEY_VANILLA).getModTag() + "|FrozenRiver");

        /********** BEACH **********/
        defaultBiomeGroupBeaches.add(biomeModMap.get(MOD_KEY_VANILLA).getModTag() + "|Beach");
        defaultBiomeGroupBeaches.add(biomeModMap.get(MOD_KEY_VANILLA).getModTag() + "|Shore");

        /********** LAKE ***********/ //Biomes that create lake like structures to spawn aquatics
        defaultBiomeGroupLake.add(biomeModMap.get(MOD_KEY_VANILLA).getModTag() + "|Plains"); 
        defaultBiomeGroupLake.add(biomeModMap.get(MOD_KEY_VANILLA).getModTag() + "|Extreme Hills");
        defaultBiomeGroupLake.add(biomeModMap.get(MOD_KEY_VANILLA).getModTag() + "|Desert"); 

        /********** MUSHROOM **********/
        defaultBiomeGroupMushroom.add(biomeModMap.get(MOD_KEY_VANILLA).getModTag() + "|MushroomIsland");
        defaultBiomeGroupMushroom.add(biomeModMap.get(MOD_KEY_VANILLA).getModTag() + "|MushroomIslandShore");
        defaultBiomeGroupMushroom.add(biomeModMap.get(MOD_KEY_BIOMESOPLENTY).getModTag() + "|Fungi Forest");

        /********** NETHER **********/
        defaultBiomeGroupNether.add(biomeModMap.get(MOD_KEY_VANILLA).getModTag() + "|Hell");
        
        /********** THEEND **********/
        defaultBiomeGroupTheEnd.add(biomeModMap.get(MOD_KEY_VANILLA).getModTag() + "|Sky");

        /********** WYVERNLAIR **********/
        defaultBiomeGroupWyvern.add("MOC|WyvernBiome");

        /********** VOLCANO **********/
        defaultBiomeGroupVolcano.add("BOP|Volcano");

        /********** TWILIGHT *********/
        defaultBiomeGroupTwilight.add(biomeModMap.get(MOD_KEY_TWILIGHT).getModTag() + "|Clearing Border");
        defaultBiomeGroupTwilight.add(biomeModMap.get(MOD_KEY_TWILIGHT).getModTag() + "|Dark Forest");
        defaultBiomeGroupTwilight.add(biomeModMap.get(MOD_KEY_TWILIGHT).getModTag() + "|Dense Twilight Forest");
        defaultBiomeGroupTwilight.add(biomeModMap.get(MOD_KEY_TWILIGHT).getModTag() + "|Enchanted Forest");
        defaultBiomeGroupTwilight.add(biomeModMap.get(MOD_KEY_TWILIGHT).getModTag() + "|Fire Swamp");
        defaultBiomeGroupTwilight.add(biomeModMap.get(MOD_KEY_TWILIGHT).getModTag() + "|Highlands");
        defaultBiomeGroupTwilight.add(biomeModMap.get(MOD_KEY_TWILIGHT).getModTag() + "|Lake Border");
        defaultBiomeGroupTwilight.add(biomeModMap.get(MOD_KEY_TWILIGHT).getModTag() + "|Lots of Mushrooms");
        defaultBiomeGroupTwilight.add(biomeModMap.get(MOD_KEY_TWILIGHT).getModTag() + "|Major Feature");
        defaultBiomeGroupTwilight.add(biomeModMap.get(MOD_KEY_TWILIGHT).getModTag() + "|Minor Feature");
        defaultBiomeGroupTwilight.add(biomeModMap.get(MOD_KEY_TWILIGHT).getModTag() + "|Mushrooms");
        defaultBiomeGroupTwilight.add(biomeModMap.get(MOD_KEY_TWILIGHT).getModTag() + "|Snowy Forest");
        defaultBiomeGroupTwilight.add(biomeModMap.get(MOD_KEY_TWILIGHT).getModTag() + "|Twilight Clearing");
        defaultBiomeGroupTwilight.add(biomeModMap.get(MOD_KEY_TWILIGHT).getModTag() + "|Twilight Forest");
        defaultBiomeGroupTwilight.add(biomeModMap.get(MOD_KEY_TWILIGHT).getModTag() + "|Twilight Lake");
        defaultBiomeGroupTwilight.add(biomeModMap.get(MOD_KEY_TWILIGHT).getModTag() + "|Twilight Stream");
        defaultBiomeGroupTwilight.add(biomeModMap.get(MOD_KEY_TWILIGHT).getModTag() + "|Twilight Swamp");

        /********** MOBS **********/
        //Contains a list of all of the biomes except for the Nether, End and custom ones (like wyvernLair)
        for (Map.Entry<String, MoCBiomeGroupData> biomeGroupEntry : defaultBiomeGroupMap.entrySet())
        {
            MoCBiomeGroupData biomeGroupData = biomeGroupEntry.getValue();
            if (biomeGroupData.getBiomeList() != defaultBiomeGroupNether && biomeGroupData.getBiomeList() != defaultBiomeGroupTheEnd && biomeGroupData.getBiomeList() != defaultBiomeGroupWyvern && biomeGroupData.getBiomeList() != defaultBiomeGroupMushroom && biomeGroupData.getBiomeList() != defaultBiomeGroupMobs)
            {
                defaultBiomeGroupMobs.addAll(biomeGroupData.getBiomeList());
            }
        }
        // generate default biome groups
        for (Map.Entry<String, MoCBiomeGroupData> biomeGroupEntry : defaultBiomeGroupMap.entrySet())
        {
            if (!mocBiomeConfig.hasCategory(biomeGroupEntry.getKey()))
            {
                mocBiomeConfig.get(biomeGroupEntry.getKey(), biomeGroupEntry.getKey(), biomeGroupEntry.getValue().getBiomeList());
            }
        }
        mocBiomeConfig.save();
       // }

        // read biome groups from config and populate biomeGroupMap
        if (MoCreatures.proxy.debugLogging) MoCreatures.log.info("Reading MoCBiomeGroups.cfg ....");
        for (Map.Entry<String, MoCConfigCategory> catEntry : mocBiomeConfig.categories.entrySet())
        {
            if (MoCreatures.proxy.debugLogging) MoCreatures.log.info("Found Biome Group " + catEntry.getKey().toUpperCase());

            String biomeGroupName = catEntry.getKey().toUpperCase();
            for (Map.Entry<String, MoCProperty> category : catEntry.getValue().entrySet())
            {
                List biomeList = new ArrayList();
                if (category.getValue() != null && category.getValue().valueList != null)
                {
                    for (int i = 0; i < category.getValue().valueList.size(); i++)
                    {
                        List<String> biomeParts = parseName(category.getValue().valueList.get(i));

                        if (biomeList.contains(category.getValue().valueList.get(i)))
                            continue; // ignore duplicate biomes
                        biomeList.add(category.getValue().valueList.get(i));
                    }

                    if (!biomeList.isEmpty())
                    {
                        if (biomeGroupMap.containsKey(biomeGroupName))
                            biomeGroupMap.remove(biomeGroupName);
                        biomeGroupMap.put(biomeGroupName, new MoCBiomeGroupData(biomeGroupName, biomeList));
                        Collections.sort(biomeList); // sort biome groups for GUI
                        category.getValue().valueList = biomeList; // blood - make sure to link our newly generated list to the configuration list for direct modification later
                        if (MoCreatures.proxy.debugLogging) MoCreatures.log.info("Successfully added Biome Group " + biomeGroupName);
                    }
                }
            }
        }

        // add any biomes that we may have missed to biome group UNDEFINED
        if (MoCreatures.proxy.debugLogging) MoCreatures.log.info("Scanning biomeMap for biomes not found in MoCBiomeGroups.cfg ...");
        MoCBiomeModData biomeModData = biomeModMap.get("undefined");
        for (Map.Entry<String, MoCBiomeData> biomeEntry : biomeMap.entrySet())
        {
            MoCBiomeData biomeData = biomeEntry.getValue();
            if (!biomeData.isDefined())
            {
                biomeModData.addBiome(biomeData);
                if (debugLogging) MoCreatures.log.warning("Biome " + biomeData.getBiomeName() + " was NOT DEFINED, Added biome to UNDEFINED BIOME GROUP");
            }
            else if (debugLogging) MoCreatures.log.info("Biome " + biomeData.getBiomeName() + " was DEFINED");
        }
        if (biomeModData.getBiomes().size() > 0)
        {
            MoCConfigCategory cat = biomeModData.getModConfig().getCategory("biomes");
            MoCProperty prop = cat.get("biomes");
            if (prop != null)
            {
                prop.valueList = biomeModData.getBiomes();
            }
            biomeModData.getModConfig().save();
        }
        mocBiomeConfig.save();
    }

    public List<String> parseName(String biomeConfigEntry)
    {
        String tag = biomeConfigEntry.substring(0, biomeConfigEntry.indexOf('|'));
        String biomeName = biomeConfigEntry.substring(biomeConfigEntry.indexOf('|') + 1, biomeConfigEntry.length());
        List<String> biomeParts = new ArrayList();
        biomeParts.add(tag);
        biomeParts.add(biomeName);
        return biomeParts;
    }

    public int getFrequency(int entityId)//String entityName, EnumCreatureType type)
    {
        if (this.entityMap.get(entityId) != null)
            return this.entityMap.get(entityId).getFrequency();
        else return frequency;
    }

    //-----------------THE FOLLOWING ARE CLIENT SIDE ONLY, NOT TO BE USED IN SERVER AS THEY AFFECT ONLY DISPLAY / SOUNDS

    public void UndeadFX(Entity entity) {} //done client side

    public void StarFX(MoCEntityHorse moCEntityHorse) {}

    public void LavaFX(Entity entity) {}

    public void VanishFX(MoCEntityHorse entity) {}

    public void MaterializeFX(MoCEntityHorse entity) {}

    public void VacuumFX(MoCEntityGolem entity) {}
    
    public void hammerFX(EntityPlayer entityplayer) {}

    public void teleportFX(EntityPlayer entity) {}
    
    public boolean getAnimateTextures() {
        return false;
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

    public int getParticleFX()
    {
        return 0;
    }

    public void initSounds() {}
    
    public EntityPlayer getPlayer()
    {
        return null;
    }

    public void printMessageToPlayer(String msg)
    {
    }

    /**
     * Reads values from file
     */
    public void readConfigValues() 
    {
        // client-side only
        displayPetHealth = mocGlobalConfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "displayPetHealth", true, "Shows Pet Health").getBoolean(true);
        displayPetName = mocGlobalConfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "displayPetName", true, "Shows Pet Name").getBoolean(true);
        displayPetIcons = mocGlobalConfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "displayPetIcons", true, "Shows Pet Emotes").getBoolean(true);
        animateTextures = mocGlobalConfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "animateTextures", true, "Animate Textures").getBoolean(true);
        // general
        itemID = mocGlobalConfig.get(CATEGORY_MOC_ID_SETTINGS, "ItemID", 8772, "The starting ID used for MoCreatures items. Each item will increment this number by 1 for its ID.").getInt();
        allowInstaSpawn = mocGlobalConfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "allowInstaSpawn", false, "Allows you to instantly spawn MoCreatures from GUI.").getBoolean(false);
        debugLogging = mocGlobalConfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "debugLogging", false, "Turns on verbose logging").getBoolean(false);
        worldGenCreatureSpawning = mocGlobalConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "worldGenCreatureSpawning", true, "Allows spawns during world chunk generation.").getBoolean(true);
        maxMonsters = mocGlobalConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "maxMonsters", 215, "Max amount of monster.").getInt();
        maxCreatures = mocGlobalConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "maxCreatures", 250, "Max amount of animals.").getInt();
        maxAmbients = mocGlobalConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "maxAmbients", 100, "Max amount of ambients.").getInt();
        maxWaterCreatures = mocGlobalConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "maxWaterCreatures", 60, "Max amount of watercreatures.").getInt();
        creatureSpawnTickRate = mocGlobalConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "creatureSpawnTickRate", 50, "The amount of ticks it takes to spawn animals. A tick rate of 100 would cause Custom Spawner to spawn animals every 5 seconds. Raise this value if you want spawning to occur less. Note: 20 ticks takes about 1 second.").getInt();
        monsterSpawnTickRate = mocGlobalConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "monsterSpawnTickRate", 50, "The amount of ticks it takes to spawn mobs. A tick rate of 100 would cause Custom Spawner to spawn mobs every 5 seconds. Raise this value if you want spawning to occur less. Note: 20 ticks takes about 1 second.").getInt();
        ambientSpawnTickRate = mocGlobalConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "ambientSpawnTickRate", 60, "The amount of ticks it takes to spawn ambients. A tick rate of 100 would cause Custom Spawner to spawn ambients every 5 seconds. Raise this value if you want spawning to occur less. Note: 20 ticks takes about 1 second.").getInt();
        waterSpawnTickRate = mocGlobalConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "waterSpawnTickRate", 60, "The amount of ticks it takes to spawn water creatures. A tick rate of 100 would cause Custom Spawner to spawn water creatures every 5 seconds. Raise this value if you want spawning to occur less. Note: 20 ticks takes about 1 second.").getInt();
        despawnTickRate = mocGlobalConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "despawnTickRate", 111, "The amount of ticks it takes to despawn vanilla creatures. Requires despawnVanilla to be enabled. Note: 20 ticks takes about 1 second.").getInt();
        monsterSpawnRange = mocGlobalConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "monsterSpawnRange", 8, "Mob limit radius to spawn distance (chunks aren't loaded)").getInt();
        spawnCreatures = mocGlobalConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "spawnCreatures", true, "Allow creatures to spawn. Turn off to disable all creature entity types.").getBoolean(true);
        spawnMonsters = mocGlobalConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "spawnMonsters", true, "Allow monsters to spawn. Turn off to disable all monster entity types.").getBoolean(true);
        spawnWaterCreatures = mocGlobalConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "spawnWaterCreatures", true, "Allow watercreatures to spawn. Turn off to disable all watercreature entity types.").getBoolean(true);
        spawnAmbients = mocGlobalConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "spawnAmbients", true, "Allow ambients to spawn. Turn off to disable all ambient entity types.").getBoolean(true);
        lightLevel = mocGlobalConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "lightLevel", 7, "The light level threshold used to determine whether or not to spawn a creature.").getInt();
        despawnLightLevel = mocGlobalConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "despawnLightLevel", 7, "The light level threshold used to determine whether or not to despawn a creature.").getInt();
        checkAmbientLightLevel = mocGlobalConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "checkAmbientLightLevel", false, "Turns on check for lightLevel for Ambient creature spawns.").getBoolean(false);
        disallowMonsterSpawningDuringDay = mocGlobalConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "disallowMonsterSpawningDuringDay", false, "Prevents monsters from spawning anywhere during the day. Note: this will affect underground spawns as well.").getBoolean(false);
        debugCMS = mocGlobalConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "debugCMS", false, "Turns on CustomMobSpawner debug logging.").getBoolean(false);
        useCustomSpawner = mocGlobalConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "useCustomSpawner", true, "If enabled, Custom Spawner will be activated and process all entities in MoCProperties.cfg. Any entity not configured with a biome group will be ignored and used by Vanilla's spawner instead.").getBoolean(true);

        maxTamed = mocGlobalConfig.get(CATEGORY_OWNERSHIP_SETTINGS, "maxTamedPerPlayer", 10, "Max tamed creatures a player can have. Requires enableOwnership to be set to true.").getInt();
        maxOPTamed = mocGlobalConfig.get(CATEGORY_OWNERSHIP_SETTINGS, "maxTamedPerOP", 20, "Max tamed creatures an op can have. Requires enableOwnership to be set to true.").getInt();
        enableOwnership = mocGlobalConfig.get(CATEGORY_OWNERSHIP_SETTINGS, "enableOwnership", false, "Assigns player as owner for each creature they tame. Only the owner can interact with the tamed creature.").getBoolean(false);
        enableResetOwnership = mocGlobalConfig.get(CATEGORY_OWNERSHIP_SETTINGS, "enableResetOwnerScroll", false, "Allows players to remove a tamed creatures owner essentially untaming it.").getBoolean(false);
        easyBreeding = mocGlobalConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "EasyBreeding", true, "Makes horse breeding simpler.").getBoolean(true);
        elephantBulldozer = mocGlobalConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "ElephantBulldozer", true).getBoolean(true);
        zebraChance = mocGlobalConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "ZebraChance", 10, "The percent for spawning a zebra.").getInt();
        staticBed = mocGlobalConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "StaticBed", true).getBoolean(true);
        staticLitter = mocGlobalConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "StaticLitter", true).getBoolean(true);
        particleFX = mocGlobalConfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "particleFX", 3).getInt();
        attackDolphins = mocGlobalConfig.get(CATEGORY_MOC_WATER_CREATURE_GENERAL_SETTINGS, "AttackDolphins", false, "Allows water creatures to attack dolphins.").getBoolean(false);
        attackHorses = mocGlobalConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "AttackHorses", false, "Allows creatures to attack horses.").getBoolean(false);
        attackWolves = mocGlobalConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "AttackWolves", false, "Allows creatures to attack wolves.").getBoolean(false);
        destroyDrops = mocGlobalConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "DestroyDrops", false).getBoolean(false);

        modifyVanillaSpawns = mocGlobalConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "ModifyVanillaSpawns", true, "Forces Custom Spawner to handle vanilla spawns otherwise the default vanilla spawner is used.").getBoolean(true);
        despawnVanilla = mocGlobalConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "DespawnVanilla", true, "Allows Custom Spawner to despawn vanilla every despawnTickRate. This helps prevent vanilla from overwhelming custom spawns.").getBoolean(true);
        useDefaultBiomeGroups = mocGlobalConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "useDefaultBiomeGroups", true).getBoolean(true);
        ogreStrength = Float.parseFloat(mocGlobalConfig.get(CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "OgreStrength", 2.5F, "The block destruction radius of green Ogres").value);
        caveOgreStrength = Float.parseFloat(mocGlobalConfig.get(CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "CaveOgreStrength", 3.0F, "The block destruction radius of Cave Ogres").value);
        fireOgreStrength = Float.parseFloat(mocGlobalConfig.get(CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "FireOgreStrength", 2.0F, "The block destruction radius of Fire Ogres").value);
        ogreAttackRange = (short) mocGlobalConfig.get(CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "OgreAttackRange", 12, "The block radius where ogres 'smell' players").getInt();
        fireOgreChance = (short) mocGlobalConfig.get(CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "FireOgreChance", 25, "The chance percentage of spawning Fire ogres in the Overworld").getInt();
        caveOgreChance = (short) mocGlobalConfig.get(CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "CaveOgreChance", 75, "The chance percentage of spawning Cave ogres at depth of 50 in the Overworld").getInt();
        golemDestroyBlocks = mocGlobalConfig.get(CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "golemDestroyBlocks", true, "Allows Big Golems to break blocks.").getBoolean(true);
        //blocks
        blockDirtID = mocGlobalConfig.getTerrainBlock(CATEGORY_MOC_ID_SETTINGS, "DirtBlockID", 200, "Basic block for terrain generation, needs to be less than 256").getInt();
        blockGrassID = mocGlobalConfig.getTerrainBlock(CATEGORY_MOC_ID_SETTINGS, "GrassBlockID", 201, "Basic block for terrain generation, needs to be less than 256").getInt();
        blockLeafID = mocGlobalConfig.getBlock(CATEGORY_MOC_ID_SETTINGS, "LeafBlockID", 700).getInt();
        blockLogID = mocGlobalConfig.getBlock(CATEGORY_MOC_ID_SETTINGS, "LogBlockID", 701).getInt();
        blockTallGrassID = mocGlobalConfig.getBlock(CATEGORY_MOC_ID_SETTINGS, "TallGrassBlockID", 702).getInt();
        blockPlanksID = mocGlobalConfig.getBlock(CATEGORY_MOC_ID_SETTINGS, "PlanksBlockID", 703).getInt();
        blockStoneID = mocGlobalConfig.getTerrainBlock(CATEGORY_MOC_ID_SETTINGS, "StoneBlockID", 202, "Basic block for terrain generation, needs to be less than 256").getInt();
        WyvernDimension = mocGlobalConfig.get(CATEGORY_MOC_ID_SETTINGS, "WyvernLairDimensionID", -17).getInt();
        WyvernBiomeID = mocGlobalConfig.get(CATEGORY_MOC_ID_SETTINGS, "WyvernLairBiomeID", 207).getInt();

        mocGlobalConfig.save();
    }

    // Client stuff
    public void registerRenderers() {
        // Nothing here as this is the server side proxy
    }

    public void registerRenderInformation() {
        //client
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        // TODO Auto-generated method stub
        return null;
    }

    /***
     * Dummy to know if is dedicated server or not
     * 
     * @return
     */
    public int getProxyMode() {
        return 1;
    }

    /**
     * Sets the name client side. Name is synchronized with datawatchers
     * 
     * @param player
     * @param mocanimal
     */
    public void setName(EntityPlayer player, MoCIMoCreature mocanimal) {
        //client side only
    }
}