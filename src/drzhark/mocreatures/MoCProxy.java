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
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import drzhark.mocreatures.entity.item.MoCEntityFishBowl;
import drzhark.mocreatures.entity.item.MoCEntityKittyBed;
import drzhark.mocreatures.entity.item.MoCEntityLitterBox;
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
import drzhark.mocreatures.entity.passive.MoCEntityAnt;
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
import drzhark.mocreatures.entity.passive.MoCEntityRay;
import drzhark.mocreatures.entity.passive.MoCEntityShark;
import drzhark.mocreatures.entity.passive.MoCEntitySnail;
import drzhark.mocreatures.entity.passive.MoCEntitySnake;
import drzhark.mocreatures.entity.passive.MoCEntityTurkey;
import drzhark.mocreatures.entity.passive.MoCEntityTurtle;
import drzhark.mocreatures.entity.passive.MoCEntityWyvern;


public class MoCProxy implements IGuiHandler {

    public static String ARMOR_TEXTURE = "/mods/mocreatures/textures/armor/";
    public static String MODEL_TEXTURE = "/mods/mocreatures/textures/models/";
    public static String GUI_TEXTURE = "/mods/mocreatures/textures/gui/";
    public static String MISC_TEXTURE = "/mods/mocreatures/textures/misc/";
    

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

    //CONFIG VARIABLES
    public boolean useDefaultModEntityMappings;
    public boolean useDefaultModBiomeMappings;
    // Client Only
    public boolean displayPetHealth;
    public boolean displayPetName;
    public boolean displayPetIcons;
    public boolean animateTextures;
    //

    public boolean attackDolphins;
    public boolean attackWolves;
    public boolean attackHorses;
    public boolean spawnPiranhas;
    public boolean staticBed;
    public boolean staticLitter;

    public boolean easyBreeding;
    public boolean modifyVanillaSpawns;
    public boolean despawnVanilla;
    public boolean destroyDrops;
    public boolean enableOwnership;
    public boolean enableResetOwnership;
    public boolean elephantBulldozer;

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
    public int lightLevel = 7;
    public int maxMobs;
    public int maxAnimals;
    public int maxAmbient;
    public int maxWaterMobs;
    public int creatureSpawnTickRate;
    public int mobSpawnTickRate;
    public int ambientSpawnTickRate;
    public int waterSpawnTickRate;
    public int despawnTickRate;
    public int mobSpawnRange;
    public int maxTamed;
    public int maxOPTamed;
    public int zebraChance;
    public int particleFX;
    // defaults
    public int frequency = 8;
    public int minGroup = 1;
    public int maxGroup = 1;
    public int maxSpawnInChunk = 1;
    public float strength = 1;
    public int sharkSpawnDif = 0;

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
    public File configFile;
    public FMLPreInitializationEvent configPreEvent;
    public FMLPostInitializationEvent configPostEvent;

    public static final String MOC_BEAR_NAME = "Horse";
    public static final String MOC_BEE_NAME = "Bee";
    public static final String MOC_BIGCAT_NAME = "BigCat";
    public static final String MOC_BIRD_NAME = "Bird";
    public static final String MOC_BOAR_NAME = "Boar";
    public static final String MOC_BUNNY_NAME = "Bunny";
    public static final String MOC_BUTTERFLY_NAME = "Butterfly";
    public static final String MOC_CAVEOGRE_NAME = "CaveOgre";
    public static final String MOC_CRICKET_NAME = "Cricket";
    public static final String MOC_CROCODILE_NAME = "Crocodile";
    public static final String MOC_DEER_NAME = "Deer";
    public static final String MOC_DOLPHIN_NAME = "Dolphin";
    public static final String MOC_DRAGONFLY_NAME = "Dragonfly";
    public static final String MOC_DUCK_NAME = "Duck";
    public static final String MOC_ELEPHANT_NAME = "Elephant";
    public static final String MOC_FIREFLY_NAME = "Firefly";
    public static final String MOC_FIREOGRE_NAME = "FireOgre";
    public static final String MOC_FISHY_NAME = "Fishy";
    public static final String MOC_FLAMEWRAITH_NAME = "FlameWraith";
    public static final String MOC_FLY_NAME = "Fly";
    public static final String MOC_FOX_NAME = "Fox";
    public static final String MOC_GOAT_NAME = "Goat";
    public static final String MOC_GOLEM_NAME = "BigGolem";
    public static final String MOC_HELLRAT_NAME = "Hellrat";
    public static final String MOC_HORSE_NAME = "Horse";
    public static final String MOC_HORSEMOB_NAME = "HorseMob";
    public static final String MOC_JELLYFISH_NAME = "Jellyfish";
    public static final String MOC_KITTY_NAME = "Kitty";
    public static final String MOC_KOMODO_NAME = "KomodoDragon";
    public static final String MOC_MOUSE_NAME = "Mouse";
    public static final String MOC_OGRE_NAME = "Ogre";
    public static final String MOC_OSTRICH_NAME = "Ostrich";
    public static final String MOC_PETSCORPION_NAME = "PetScorpion";
    public static final String MOC_RAT_NAME = "Rat";
    public static final String MOC_RAY_NAME = "Ray";
    public static final String MOC_SCORPION_NAME = "Scorpion";
    public static final String MOC_SHARK_NAME = "Shark";
    public static final String MOC_SNAIL_NAME = "Snail";
    public static final String MOC_SNAKE_NAME = "Snake";
    public static final String MOC_TURKEY_NAME = "Turkey";
    public static final String MOC_TURTLE_NAME = "Turtle";
    public static final String MOC_WEREWOLF_NAME = "Werewolf";
    public static final String MOC_WRAITH_NAME = "Wraith";
    public static final String MOC_WWOLF_NAME = "WWolf";

    private static final String MOD_CREATURES_FILE_ROOT = "MoCreatures" + File.separator + "Creatures" + File.separator;
    private static final String MOD_BIOME_FILE_ROOT = "MoCreatures" + File.separator + "Biomes" + File.separator;

    public static final String CATEGORY_MOC_GENERAL_SETTINGS = "global-settings";
    public static final String CATEGORY_MOD_ENTITY_MAPPINGS = "mod-entity-mappings";
    public static final String CATEGORY_MOD_BIOME_MAPPINGS = "mod-biome-mappings";
    public static final String CATEGORY_VANILLA_CREATURE_FREQUENCIES = "vanilla-creature-frequencies";
    //public static final String CATEGORY_VANILLA_MONSTER_FREQUENCIES = "vanilla-monster-frequencies";
    //public static final String CATEGORY_VANILLA_WATER_CREATURE_FREQUENCIES = "vanilla-watercreature-frequencies";
    public static final String CATEGORY_CREATURES = "Creatures";
    public static final String CATEGORY_ENTITY_SPAWN_SETTINGS = "entity-spawn-settings";
    public static final String CATEGORY_ENTITY_CANSPAWN_SETTINGS = "entity-canspawn-settings";
    public static final String CATEGORY_MOC_CREATURE_GENERAL_SETTINGS = "creature-general-settings";
    //public static final String CATEGORY_MOC_CREATURE_CHUNK_SETTINGS = "creature-chunk-settings";
    //public static final String CATEGORY_MOC_MONSTER_FREQUENCIES = "monster-frequencies";
    public static final String CATEGORY_MOC_MONSTER_GENERAL_SETTINGS = "monster-general-settings";
   // public static final String CATEGORY_MOC_MONSTER_CHUNK_SETTINGS = "monster-chunk-settings";
    //public static final String CATEGORY_MOC_WATER_CREATURE_FREQUENCIES = "water-mob-frequencies";
    public static final String CATEGORY_MOC_WATER_CREATURE_GENERAL_SETTINGS = "water-mob-general-settings";
    //public static final String CATEGORY_MOC_WATER_CREATURE_CHUNK_SETTINGS = "water-mob-chunk-settings";
    //public static final String CATEGORY_MOC_AMBIENT_FREQUENCIES = "ambient-frequencies";
    public static final String CATEGORY_MOC_AMBIENT_GENERAL_SETTINGS = "ambient-general-settings";
   // public static final String CATEGORY_MOC_AMBIENT_CHUNK_SETTINGS = "ambient-chunk-settings";
    public static final String CATEGORY_MOC_UNDEFINED_FREQUENCIES = "undefined-frequencies";
    public static final String CATEGORY_CUSTOMSPAWNER_SETTINGS = "customspawner-settings";
    public static final String CATEGORY_OWNERSHIP_SETTINGS = "ownership-settings";
    public static final String CATEGORY_ENTITY_BIOME_SETTINGS = "entity-biome-settings";
    public static final String CATEGORY_MOC_BIOME_SETTINGS = "biome-settings";
    public static final String CATEGORY_MOC_ID_SETTINGS = "custom-id-settings";
    public static final String CATEGORY_ENTITY_SPAWN_TYPES = "entity-spawn-types";

    public static final String BIOME_UNDEFINED_GROUP = "UNDEFINED";
    public static final String BIOME_ARCTIC_GROUP = "ARCTIC";
    public static final String BIOME_NORMAL_GROUP = "NORMAL";
    public static final String BIOME_FOREST_GROUP = "FOREST";
    public static final String BIOME_JUNGLE_GROUP = "JUNGLE";
    public static final String BIOME_MOUNTAIN_GROUP = "MOUNTAIN";
    public static final String BIOME_SWAMP_GROUP = "SWAMP";
    public static final String BIOME_DESERT_GROUP = "DESERT";
    public static final String BIOME_OCEAN_GROUP = "OCEAN";
    public static final String BIOME_RIVER_GROUP = "RIVER";
    public static final String BIOME_BEACHES_GROUP = "BEACHES";
    public static final String BIOME_MUSHROOM_GROUP = "MUSHROOM";
    public static final String BIOME_NETHER_GROUP = "NETHER";
    public static final String BIOME_THEEND_GROUP = "THEEND";
    public static final String BIOME_WYVERN_GROUP = "WYVERNLAIR";
    public static final String BIOME_MOBS_GROUP = "MOBS";
    public static final String BIOME_TWILIGHT_GROUP = "TWILIGHTFOREST";
    public static final String BIOME_VOLCANO_GROUP = "VOLCANO";
    public static final String BIOME_GROUP_SETTINGS = "biome-group-settings";

    private static final String MOD_KEY_BIOMESOPLENTY = "biomesop";
    private static final String MOD_KEY_EXTRABIOMESXL = "extrabiomes";
    private static final String MOD_KEY_VANILLA = "vanilla";
    private static final String MOD_KEY_TWILIGHT = "twilightforest";
    public static MoCStructureData structureData = new MoCStructureData();
    public static Map<String, MoCBiomeData> biomeMap = new TreeMap<String, MoCBiomeData>();

 // biome groups
    //public static List defaultBiomeGroupUndefined = new ArrayList();
    public static List defaultBiomeGroupForest = new ArrayList();
    public static List defaultBiomeGroupNormal = new ArrayList();
    public static List defaultBiomeGroupSwamp = new ArrayList();
    public static List defaultBiomeGroupDesert = new ArrayList();
    public static List defaultBiomeGroupArctic = new ArrayList();
    public static List defaultBiomeGroupJungle = new ArrayList();
    public static List defaultBiomeGroupMountain = new ArrayList();
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

    public static Map<String, MoCBiomeGroupData> biomeGroupMap = new TreeMap<String, MoCBiomeGroupData>();
    public static Map<String, MoCBiomeGroupData> defaultBiomeGroupMap = new TreeMap<String, MoCBiomeGroupData>(); // used only for default biome groups
    public static Map<Integer, MoCEntityData> entityMap = new TreeMap<Integer, MoCEntityData>();
    public static List<String> entityConflict = new ArrayList<String>();
    public static List<String> biomeConflict = new ArrayList<String>();
    public static Map<String, MoCProperty> propertyMap = new TreeMap<String, MoCProperty>();
    public static final Map<String, MoCEntityData> mocEntityMap = new TreeMap<String, MoCEntityData>();
    public static final Map<String, MoCEntityData> vanillaEntityMap = new TreeMap<String, MoCEntityData>();
    public static final Map<String, MoCEntityModData> entityModMap = new TreeMap<String, MoCEntityModData>();
    public static final Map<String, MoCBiomeModData> biomeModMap = new TreeMap<String, MoCBiomeModData>();
    public static final Map<String, MoCBiomeModData> tagConfigMap = new HashMap<String, MoCBiomeModData>();
    private static Map<String, MoCProperty> propsToSave = new HashMap<String, MoCProperty>();
    private static Map<String, EnumCreatureType> entityTypes = new HashMap<String, EnumCreatureType>();
    public static Map<String, String> entityModKeyMap = new HashMap<String, String>();
    public static Map<String, List<String>> biomeModKeyMap = new HashMap<String, List<String>>();
    public static Map<Class<? extends EntityLiving>, MoCEntityData> classToEntityMapping = new HashMap<Class<? extends EntityLiving>, MoCEntityData>();
    static {
        // mocreatures
        mocEntityMap.put("Ant", new MoCEntityData(MoCEntityAnt.class, EnumCreatureType.ambient, 7, 1, 2, 2, new ArrayList(Arrays.asList("FOREST", "JUNGLE", "NORMAL"))));
        mocEntityMap.put("Bear", new MoCEntityData(MoCEntityBear.class, EnumCreatureType.creature, 6, 1, 2, 1, new ArrayList(Arrays.asList("ARCTIC", "FOREST", "MOUNTAIN", "NORMAL"))));
        mocEntityMap.put("Bee", new MoCEntityData(MoCEntityBee.class, EnumCreatureType.ambient, 7, 1, 2, 2, new ArrayList(Arrays.asList("FOREST", "JUNGLE", "NORMAL"))));
        mocEntityMap.put("BigCat", new MoCEntityData(MoCEntityBigCat.class, EnumCreatureType.creature,  6, 1, 2, 2, new ArrayList(Arrays.asList("FOREST", "JUNGLE", "MOUNTAIN", "NORMAL"))));
        mocEntityMap.put("BigGolem", new MoCEntityData(MoCEntityGolem.class, EnumCreatureType.monster, 3, 1, 1, 1, new ArrayList(Arrays.asList("MOBS"))));
        mocEntityMap.put("Bird", new MoCEntityData(MoCEntityBird.class, EnumCreatureType.creature, 15, 2, 2, 4, new ArrayList(Arrays.asList("FOREST", "JUNGLE", "MOUNTAIN", "NORMAL"))));
        mocEntityMap.put("Boar", new MoCEntityData(MoCEntityBoar.class, EnumCreatureType.creature, 8, 2, 2, 4, new ArrayList(Arrays.asList("FOREST", "JUNGLE", "NORMAL"))));
        mocEntityMap.put("Bunny", new MoCEntityData(MoCEntityBunny.class, EnumCreatureType.creature, 10, 2, 2, 4, new ArrayList(Arrays.asList("ARCTIC", "FOREST", "JUNGLE", "NORMAL", "WYVERNLAIR"))));
        mocEntityMap.put("ButterFly", new MoCEntityData(MoCEntityButterfly.class, EnumCreatureType.ambient, 8, 1, 1, 2, new ArrayList(Arrays.asList("FOREST", "JUNGLE", "MOUNTAIN", "NORMAL"))));
        mocEntityMap.put("Crab", new MoCEntityData(MoCEntityCrab.class, EnumCreatureType.ambient, 8, 1, 2, 2, new ArrayList(Arrays.asList("BEACHES", "RIVER"))));
        mocEntityMap.put("Cricket", new MoCEntityData(MoCEntityCricket.class, EnumCreatureType.ambient, 8, 1, 2, 2, new ArrayList(Arrays.asList("FOREST", "JUNGLE", "MOUNTAIN", "NORMAL"))));
        mocEntityMap.put("Crocodile", new MoCEntityData(MoCEntityCrocodile.class, EnumCreatureType.monster, 6, 1, 2, 2, new ArrayList(Arrays.asList("SWAMP"))));
        mocEntityMap.put("Deer", new MoCEntityData(MoCEntityDeer.class, EnumCreatureType.creature, 8, 1, 2, 2, new ArrayList(Arrays.asList("FOREST", "NORMAL"))));
        mocEntityMap.put("Dolphin", new MoCEntityData(MoCEntityDolphin.class, EnumCreatureType.waterCreature, 6, 1, 1, 1, new ArrayList(Arrays.asList("BEACHES", "OCEAN", "RIVER"))));
        mocEntityMap.put("DragonFly", new MoCEntityData(MoCEntityDragonfly.class, EnumCreatureType.ambient, 6, 1, 2, 2, new ArrayList(Arrays.asList("FOREST", "JUNGLE", "MOUNTAIN", "NORMAL", "WYVERNLAIR"))));
        mocEntityMap.put("Duck", new MoCEntityData(MoCEntityDuck.class, EnumCreatureType.creature, 10, 1, 2, 2, new ArrayList(Arrays.asList("FOREST", "JUNGLE", "NORMAL"))));
        mocEntityMap.put("Elephant", new MoCEntityData(MoCEntityElephant.class, EnumCreatureType.creature, 4, 1, 2, 2, new ArrayList(Arrays.asList("ARCTIC", "DESERT", "FOREST", "NORMAL"))));
        mocEntityMap.put("Firefly", new MoCEntityData(MoCEntityFirefly.class, EnumCreatureType.ambient, 8, 1, 2, 2, new ArrayList(Arrays.asList("FOREST", "NORMAL", "JUNGLE", "MOUNTAIN"))));
        mocEntityMap.put("Fishy", new MoCEntityData(MoCEntityFishy.class, EnumCreatureType.waterCreature, 15, 1, 3, 4, new ArrayList(Arrays.asList("BEACHES", "OCEAN", "RIVER", "SWAMP"))));
        mocEntityMap.put("FlameWraith", new MoCEntityData(MoCEntityFlameWraith.class, EnumCreatureType.monster, 8, 1, 1, 1, new ArrayList(Arrays.asList("MOBS", "NETHER"))));
        mocEntityMap.put("Fly", new MoCEntityData(MoCEntityFly.class, EnumCreatureType.ambient, 8, 1, 2, 2, new ArrayList(Arrays.asList("FOREST", "JUNGLE", "MOUNTAIN", "NORMAL"))));
        mocEntityMap.put("Fox", new MoCEntityData(MoCEntityFox.class, EnumCreatureType.creature, 8, 1, 1, 1, new ArrayList(Arrays.asList("ARCTIC", "FOREST", "NORMAL", "JUNGLE"))));
        mocEntityMap.put("Goat", new MoCEntityData(MoCEntityGoat.class, EnumCreatureType.creature, 8, 1, 1, 1, new ArrayList(Arrays.asList("FOREST", "NORMAL", "MOUNTAIN"))));
        mocEntityMap.put("HellRat", new MoCEntityData(MoCEntityHellRat.class, EnumCreatureType.monster, 8, 1, 1, 1, new ArrayList(Arrays.asList("NETHER"))));
        mocEntityMap.put("Horse", new MoCEntityData(MoCEntityHorse.class, EnumCreatureType.creature, 10, 1, 3, 4, new ArrayList(Arrays.asList("FOREST", "NORMAL", "MOUNTAIN"))));
        mocEntityMap.put("HorseMob", new MoCEntityData(MoCEntityHorseMob.class, EnumCreatureType.monster, 8, 1, 3, 3, new ArrayList(Arrays.asList("MOBS", "NETHER"))));
        mocEntityMap.put("JellyFish", new MoCEntityData(MoCEntityJellyFish.class, EnumCreatureType.waterCreature, 8, 1, 2, 2, new ArrayList(Arrays.asList("OCEAN", "RIVER"))));
        mocEntityMap.put("Kitty", new MoCEntityData(MoCEntityKitty.class, EnumCreatureType.creature, 8, 1, 2, 2, new ArrayList(Arrays.asList("NORMAL"))));
        mocEntityMap.put("KomodoDragon", new MoCEntityData(MoCEntityKomodo.class, EnumCreatureType.creature, 8, 1, 2, 2, new ArrayList(Arrays.asList("SWAMP"))));
        mocEntityMap.put("Maggot", new MoCEntityData(MoCEntityMaggot.class, EnumCreatureType.ambient, 8, 1, 2, 2, new ArrayList(Arrays.asList("FOREST", "JUNGLE", "MOUNTAIN", "NORMAL"))));
        mocEntityMap.put("Mouse", new MoCEntityData(MoCEntityMouse.class, EnumCreatureType.creature, 7, 1, 1, 1, new ArrayList(Arrays.asList("FOREST", "JUNGLE", "MOUNTAIN", "NORMAL"))));
        mocEntityMap.put("Ogre", new MoCEntityData(MoCEntityOgre.class, EnumCreatureType.monster, 8, 1, 1, 1, new ArrayList(Arrays.asList("MOBS", "NETHER"))));
        mocEntityMap.put("Ostrich", new MoCEntityData(MoCEntityOstrich.class, EnumCreatureType.creature, 8, 1, 3, 3, new ArrayList(Arrays.asList("DESERT", "NORMAL"))));
        mocEntityMap.put("Rat", new MoCEntityData(MoCEntityRat.class, EnumCreatureType.monster, 7, 1, 1, 1, new ArrayList(Arrays.asList("MOBS"))));
        mocEntityMap.put("Ray", new MoCEntityData(MoCEntityRay.class, EnumCreatureType.waterCreature, 10, 1, 1, 3, new ArrayList(Arrays.asList("OCEAN", "RIVER", "SWAMP"))));
        mocEntityMap.put("Scorpion", new MoCEntityData(MoCEntityScorpion.class, EnumCreatureType.monster, 8, 1, 3, 3, new ArrayList(Arrays.asList("MOBS", "NETHER"))));
        mocEntityMap.put("Shark", new MoCEntityData(MoCEntityShark.class, EnumCreatureType.waterCreature, 8, 1, 1, 1, new ArrayList(Arrays.asList("OCEAN"))));
        mocEntityMap.put("Snail", new MoCEntityData(MoCEntitySnail.class, EnumCreatureType.ambient, 7, 1, 2, 2, new ArrayList(Arrays.asList("FOREST", "JUNGLE", "NORMAL"))));
        mocEntityMap.put("Snake", new MoCEntityData(MoCEntitySnake.class, EnumCreatureType.creature, 8, 1, 2, 2, new ArrayList(Arrays.asList("FOREST", "JUNGLE", "MOUNTAIN", "NORMAL", "WYVERNLAIR"))));
        mocEntityMap.put("Turkey", new MoCEntityData(MoCEntityTurkey.class, EnumCreatureType.creature, 8, 1, 1, 1, new ArrayList(Arrays.asList("NORMAL"))));
        mocEntityMap.put("Turtle", new MoCEntityData(MoCEntityTurtle.class, EnumCreatureType.creature, 8, 1, 1, 1, new ArrayList(Arrays.asList("SWAMP", "JUNGLE"))));
        mocEntityMap.put("Werewolf", new MoCEntityData(MoCEntityWerewolf.class, EnumCreatureType.monster, 8, 1, 2, 2, new ArrayList(Arrays.asList("MOBS"))));
        mocEntityMap.put("Wraith", new MoCEntityData(MoCEntityWraith.class, EnumCreatureType.monster, 8, 1, 1, 1, new ArrayList(Arrays.asList("MOBS"))));
        mocEntityMap.put("WWolf", new MoCEntityData(MoCEntityWWolf.class, EnumCreatureType.monster, 8, 1, 1, 1, new ArrayList(Arrays.asList("MOBS"))));
        mocEntityMap.put("Wyvern", new MoCEntityData(MoCEntityWyvern.class, EnumCreatureType.creature, 10, 1, 3, 3, new ArrayList(Arrays.asList("WYVERNLAIR"))));

        // vanilla
        vanillaEntityMap.put("Bat", new MoCEntityData(EntityBat.class, EnumCreatureType.ambient, 10, 1, 2, 4, new ArrayList(Arrays.asList("FOREST", "JUNGLE", "NORMAL"))));
        vanillaEntityMap.put("Blaze", new MoCEntityData(EntityBlaze.class, EnumCreatureType.monster, 8, 1, 2, 4, new ArrayList(Arrays.asList("NETHER"))));
        vanillaEntityMap.put("CaveSpider", new MoCEntityData(EntityCaveSpider.class, EnumCreatureType.monster, 8, 1, 2, 4, new ArrayList(Arrays.asList("MOBS"))));
        vanillaEntityMap.put("Chicken", new MoCEntityData(EntityChicken.class, EnumCreatureType.creature, 8, 1, 4, 4, new ArrayList(Arrays.asList("FOREST", "MOUNTAIN", "NORMAL"))));
        vanillaEntityMap.put("Cow", new MoCEntityData(EntityCow.class, EnumCreatureType.creature, 8, 1, 2, 4, new ArrayList(Arrays.asList("FOREST", "NORMAL"))));
        vanillaEntityMap.put("Creeper", new MoCEntityData(EntityCreeper.class, EnumCreatureType.monster, 8, 1, 4, 4, new ArrayList(Arrays.asList("MOBS", "THEEND"))));
        vanillaEntityMap.put("Enderman", new MoCEntityData(EntityEnderman.class, EnumCreatureType.monster, 1, 1, 4, 4, new ArrayList(Arrays.asList("MOBS"))));
        vanillaEntityMap.put("Ghast", new MoCEntityData(EntityGhast.class, EnumCreatureType.monster, 6, 1, 4, 4, new ArrayList(Arrays.asList("MOBS"))));
        vanillaEntityMap.put("LavaSlime", new MoCEntityData(EntityMagmaCube.class, EnumCreatureType.monster, 1, 1, 4, 4, new ArrayList(Arrays.asList("NETHER"))));
        vanillaEntityMap.put("Mooshroom", new MoCEntityData(EntityMooshroom.class, EnumCreatureType.creature, 8, 1, 4, 4, new ArrayList(Arrays.asList("MUSHROOM"))));
        vanillaEntityMap.put("Ozelot", new MoCEntityData(EntityOcelot.class, EnumCreatureType.monster, 6, 1, 3, 4, new ArrayList(Arrays.asList("JUNGLE"))));
        vanillaEntityMap.put("Pig", new MoCEntityData(EntityPig.class, EnumCreatureType.creature, 8, 1, 3, 4, new ArrayList(Arrays.asList("FOREST", "JUNGLE", "MOUNTAIN", "NORMAL"))));
        vanillaEntityMap.put("PigZombie", new MoCEntityData(EntityPigZombie.class, EnumCreatureType.monster, 15, 4, 4, 4, new ArrayList(Arrays.asList("NETHER"))));
        vanillaEntityMap.put("Sheep", new MoCEntityData(EntitySheep.class, EnumCreatureType.creature, 12, 1, 4, 4, new ArrayList(Arrays.asList("FOREST", "MOUNTAIN", "NORMAL"))));
        vanillaEntityMap.put("Silverfish", new MoCEntityData(EntitySilverfish.class, EnumCreatureType.monster, 8, 1, 4, 4, new ArrayList(Arrays.asList("MOBS"))));
        vanillaEntityMap.put("Skeleton", new MoCEntityData(EntitySkeleton.class, EnumCreatureType.monster, 8, 1, 4, 4, new ArrayList(Arrays.asList("MOBS", "NETHER"))));
        vanillaEntityMap.put("Slime", new MoCEntityData(EntitySlime.class, EnumCreatureType.monster, 6, 1, 1, 4, new ArrayList(Arrays.asList("MOBS"))));
        vanillaEntityMap.put("SnowMan", new MoCEntityData(EntitySnowman.class, EnumCreatureType.monster, 8, 1, 4, 4, new ArrayList(Arrays.asList("ARCTIC"))));
        vanillaEntityMap.put("Spider", new MoCEntityData(EntitySpider.class, EnumCreatureType.monster, 8, 1, 2, 4, new ArrayList(Arrays.asList("MOBS"))));
        vanillaEntityMap.put("Squid", new MoCEntityData(EntitySquid.class, EnumCreatureType.waterCreature, 8, 1, 2, 4, new ArrayList(Arrays.asList("OCEAN", "RIVER"))));
        vanillaEntityMap.put("Witch", new MoCEntityData(EntityWitch.class, EnumCreatureType.monster, 8, 1, 4, 4, new ArrayList(Arrays.asList("MOBS"))));
        vanillaEntityMap.put("Wolf", new MoCEntityData(EntityWolf.class, EnumCreatureType.creature, 8, 1, 3, 4, new ArrayList(Arrays.asList("ARCTIC", "FOREST"))));
        vanillaEntityMap.put("Zombie", new MoCEntityData(EntityZombie.class, EnumCreatureType.monster, 8, 1, 4, 4, new ArrayList(Arrays.asList("MOBS"))));

        //defaultBiomeGroupMap.put(BIOME_UNDEFINED_GROUP, new MoCBiomeGroupData(BIOME_UNDEFINED_GROUP, defaultBiomeGroupUndefined));
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

        // sort default lists
       // Collections.sort(defaultBiomeGroupUndefined);
        Collections.sort(defaultBiomeGroupForest);
        Collections.sort(defaultBiomeGroupArctic);
        Collections.sort(defaultBiomeGroupNormal);
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

        biomeModKeyMap.put("drzhark", new ArrayList(Arrays.asList("MOC", "MoCreatures.cfg")));
        biomeModKeyMap.put("biomesop", new ArrayList(Arrays.asList("BOP", "BiomesOPlenty.cfg")));
        biomeModKeyMap.put("extrabiomes", new ArrayList(Arrays.asList("XL", "ExtraBiomesXL.cfg")));
        biomeModKeyMap.put("twilightforest", new ArrayList(Arrays.asList("TL", "TwilightForest.cfg")));
        biomeModKeyMap.put("gaia", new ArrayList(Arrays.asList("GAI", "GrimoireOfGaia.cfg")));
        biomeModKeyMap.put("atomicstryker", new ArrayList(Arrays.asList("ATOM", "InfernalMobs.cfg")));
        biomeModKeyMap.put("arsmagica", new ArrayList(Arrays.asList("ARS", "ArsMagica.cfg")));
        biomeModKeyMap.put("projectzulu", new ArrayList(Arrays.asList("ZULU", "ProjectZulu.cfg")));
        biomeModKeyMap.put("thaumcraft", new ArrayList(Arrays.asList("TC", "Thaumcraft.cfg")));
        biomeModKeyMap.put("vanilla", new ArrayList(Arrays.asList("MC", "Vanilla.cfg")));
        biomeModKeyMap.put("undefined", new ArrayList(Arrays.asList("U", "Undefined.cfg")));

        entityModKeyMap.put("drzhark", "MoCreatures.cfg");
        entityModKeyMap.put("biomesop", "BiomesOPlenty.cfg");
        entityModKeyMap.put("extrabiomes", "ExtraBiomesXL.cfg");
        entityModKeyMap.put("twilightforest", "TwilightForest.cfg");
        entityModKeyMap.put("gaia", "GrimoireOfGaia.cfg");
        entityModKeyMap.put("atomicstryker", "InfernalMobs.cfg");
        entityModKeyMap.put("arsmagica", "ArsMagica.cfg");
        entityModKeyMap.put("projectzulu", "ProjectZulu.cfg");
        entityModKeyMap.put("thaumcraft", "Thaumcraft.cfg");
        entityModKeyMap.put("vanilla", "Vanilla.cfg");
        entityModKeyMap.put("undefined", "Undefined.cfg");
    }

    //----------------CONFIG INITIALIZATION
    public void ConfigInit(FMLPreInitializationEvent event) 
    {
        configPreEvent = event;
        mocGlobalConfig = new MoCConfiguration(new File(event.getSuggestedConfigurationFile().getParent(), "MoCreatures" + File.separator + "MoCGlobal.cfg"));
        mocBiomeConfig = new MoCConfiguration(new File(event.getSuggestedConfigurationFile().getParent(), "MoCreatures" + File.separator + "MoCBiomeGroups.cfg"));
        mocStructureConfig = new MoCConfiguration(new File(event.getSuggestedConfigurationFile().getParent(), "MoCreatures" + File.separator + "MoCStructures.cfg"));
        /* mocBiomeConfig.addCustomCategoryComment("Biome Keys", "Mod Biome List.\n" +
                "This list contains all mod detected biomes in the form ID-BiomeName.\n" +
                "This list is informational only and is used for the purpose of making biome groups.\n" +
                "You may NOT change the ID!! It will be generated automatically based on your mod configs.\n" +
                "If you do need to change a biome ID, find your mod's config and change it there instead then this list should update.");*/
        configFile = event.getSuggestedConfigurationFile();
        mocGlobalConfig.load();
        mocBiomeConfig.load();
        mocStructureConfig.load();
        this.genModCreatureFiles(event);
        this.genModBiomeFiles(event);
        this.readConfigValues();
        if (debugLogging) MoCreatures.log.info("Initializing MoCreatures Server Config File at " + event.getSuggestedConfigurationFile().getParent() + "MoCProperties.cfg");
    }

    public void genModCreatureFiles(FMLPreInitializationEvent event)
    {
        MoCConfigCategory modMapCat = mocGlobalConfig.getCategory(CATEGORY_MOD_ENTITY_MAPPINGS);
        //useDefaultModEntityMappings = mocGlobalConfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "useDefaultModEntityMappings", true, "Use Default Mod Entity Mappings").getBoolean(true);
        for (Map.Entry<String, String> keyEntry : entityModKeyMap.entrySet())
        {
            String configName = keyEntry.getValue();
            // check if key already exists
            MoCProperty prop = modMapCat.get(keyEntry.getKey());
            if (prop != null)
            {
                if (!configName.equalsIgnoreCase(configName))
                {
                    // change name
                    configName = prop.value;
                }
            }
            // assign changed tag or configname
            modMapCat.put(keyEntry.getKey(), new MoCProperty(keyEntry.getKey(), configName, Type.STRING));
            entityModMap.put(keyEntry.getKey(), new MoCEntityModData(keyEntry.getKey(), new MoCConfiguration(new File(event.getSuggestedConfigurationFile().getParent(), MOD_CREATURES_FILE_ROOT + configName))));
            if (debugLogging) MoCreatures.log.info("Added Mod Entity Mapping " + keyEntry.getKey() + " to file " + configName);
        }
        for (Map.Entry<String, MoCProperty> propEntry : modMapCat.entrySet())
        {
            MoCProperty prop = propEntry.getValue();
            if (prop != null && !entityModMap.containsKey(propEntry.getKey()) && prop.value != null)
            {
                entityModMap.put(propEntry.getKey(), new MoCEntityModData(propEntry.getKey(), new MoCConfiguration(new File(event.getSuggestedConfigurationFile().getParent(), MOD_CREATURES_FILE_ROOT + prop.value))));
                if (debugLogging) MoCreatures.log.info("Added Mod Entity Mapping " + propEntry.getKey() + " to file " + prop.value);
            }
        }
        // gen mocreatures entity defautls
        MoCConfiguration mocEntityConfig = entityModMap.get("drzhark").getModConfig();
        for (Map.Entry<String, MoCEntityData> mocEntry : mocEntityMap.entrySet())
        {
            mocEntityConfig.get(CATEGORY_ENTITY_SPAWN_SETTINGS, mocEntry.getKey(), new ArrayList(Arrays.asList(mocEntry.getValue().getType().toString().toUpperCase(), Integer.toString(mocEntry.getValue().getFrequency()), Integer.toString(mocEntry.getValue().getMinSpawn()), Integer.toString(mocEntry.getValue().getMaxSpawn()), Integer.toString(mocEntry.getValue().getMaxInChunk()))));
        }
        mocEntityConfig.save();
        MoCConfiguration vanillaEntityConfig = entityModMap.get("vanilla").getModConfig();
        for (Map.Entry<String, MoCEntityData> vanillaEntry : vanillaEntityMap.entrySet())
        {
            vanillaEntityConfig.get(CATEGORY_ENTITY_SPAWN_SETTINGS, vanillaEntry.getKey(), new ArrayList(Arrays.asList(vanillaEntry.getValue().getType().toString().toUpperCase(), Integer.toString(vanillaEntry.getValue().getFrequency()), Integer.toString(vanillaEntry.getValue().getMinSpawn()), Integer.toString(vanillaEntry.getValue().getMaxSpawn()), Integer.toString(vanillaEntry.getValue().getMaxInChunk()))));
        }
        vanillaEntityConfig.save();
        mocGlobalConfig.save();
    }

    public void genModBiomeFiles(FMLPreInitializationEvent event)
    {
        MoCConfigCategory modMapCat = mocGlobalConfig.getCategory(CATEGORY_MOD_BIOME_MAPPINGS);
        //useDefaultModBiomeMappings = mocGlobalConfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "useDefaultModBiomeMappings", true, "Use Default Mod Biome Mappings").getBoolean(true);
        for (Map.Entry<String, List<String>> keyEntry : biomeModKeyMap.entrySet())
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
                biomeModMap.put(keyEntry.getKey(), new MoCBiomeModData(keyEntry.getKey(), tag, new MoCConfiguration(new File(event.getSuggestedConfigurationFile().getParent(), MOD_BIOME_FILE_ROOT + configName))));
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
                    biomeModMap.put(propEntry.getKey(), new MoCBiomeModData(propEntry.getKey(), prop.valueList.get(0), new MoCConfiguration(new File(event.getSuggestedConfigurationFile().getParent(), MOD_BIOME_FILE_ROOT + prop.valueList.get(1)))));
                    if (debugLogging) MoCreatures.log.info("Added Custom Mod Biome Mapping " + propEntry.getKey() + " with tag " + prop.valueList.get(0) + " to file " + prop.valueList.get(1));
                }
            }
        }
        // gen mocreatures entity defautls
        MoCConfiguration mocEntityConfig = entityModMap.get("drzhark").getModConfig();
        for (Map.Entry<String, MoCEntityData> mocEntry : mocEntityMap.entrySet())
        {
            mocEntityConfig.get(CATEGORY_ENTITY_BIOME_SETTINGS, mocEntry.getKey(), mocEntry.getValue().getBiomeGroups());
        }
        mocEntityConfig.save();
        MoCConfiguration vanillaEntityConfig = entityModMap.get("vanilla").getModConfig();
        for (Map.Entry<String, MoCEntityData> vanillaEntry : vanillaEntityMap.entrySet())
        {
            vanillaEntityConfig.get(CATEGORY_ENTITY_BIOME_SETTINGS, vanillaEntry.getKey(), vanillaEntry.getValue().getBiomeGroups());
        }
        vanillaEntityConfig.save();
        mocGlobalConfig.save();
    }

    public void ConfigPostInit(FMLPostInitializationEvent event) {
        configPostEvent = event;
        initializeBiomes();
        initializeEntities();
    }

    public void initializeEntities()
    {
        entityMap.clear();
        for (Object obj : EntityList.classToStringMapping.entrySet())
        {
            Map.Entry entry = (Map.Entry)obj;
            Class clazz = null;
            String entityName = "";
            clazz = (Class)entry.getKey();
            EntityLiving entityliving = null;
            try
            {
                entityliving = (EntityLiving) clazz.getConstructor(new Class[] { World.class }).newInstance(new Object[] { DimensionManager.getWorld(0) });
            }
            catch (Exception exception)
            {
                continue;
            } 

            entityName = (String)entry.getValue();
            //if (debugLogging) MoCreatures.log.info("Found entityName "  + entityName);
            if (entityName.contains("."))
            {
                if ((entityName.indexOf(".") + 1) < entityName.length())
                    entityName = entityName.substring(entityName.indexOf(".") + 1, entityName.length());
            }
            entityName = entityName.replaceAll("[^A-Za-z0-9]", ""); // remove all non-digits/alphanumeric
                 
            if (clazz != null && EntityLiving.class.isAssignableFrom(clazz) /*&& !(IBossDisplayData.class.isAssignableFrom(clazz)*/)
            {
                if (debugLogging) MoCreatures.log.info("Detected " + clazz + " in EntityList, checking if valid...");
                EnumCreatureType creatureType = null;
                MoCEntityData entityData = null;
                if ((EntityAnimal.class.isAssignableFrom(clazz) || entityliving.isCreatureType(EnumCreatureType.creature, true)) && !(MoCEntityAmbient.class.isAssignableFrom(clazz)))
                {
                    creatureType = EnumCreatureType.creature;
                    entityData = new MoCEntityData(clazz, entityliving.entityId, creatureType, entityName);
                    if (!spawnCreatures || maxAnimals == 0)
                    {
                        entityData.setCanSpawn(false);
                    }
                }
                else if (IMob.class.isAssignableFrom(clazz) && (clazz != EntityMob.class) || entityliving.isCreatureType(EnumCreatureType.monster, true))
                {
                    creatureType = EnumCreatureType.monster;
                    entityData = new MoCEntityData(clazz, entityliving.entityId, creatureType, entityName);
                    if (!spawnMonsters || maxMobs == 0)
                    {
                        entityData.setCanSpawn(false);
                    }
                }
                else if (EntityAmbientCreature.class.isAssignableFrom(clazz) || entityliving.isCreatureType(EnumCreatureType.ambient, true) || MoCEntityAmbient.class.isAssignableFrom(clazz))
                {
                    creatureType = EnumCreatureType.ambient;
                    entityData = new MoCEntityData(clazz, entityliving.entityId, creatureType, entityName);
                    if (!spawnAmbients || maxAmbient == 0)
                    {
                        entityData.setCanSpawn(false);
                    }
                }
                else if (EntityWaterMob.class.isAssignableFrom(clazz) || entityliving.isCreatureType(EnumCreatureType.waterCreature, true))
                {
                    creatureType = EnumCreatureType.waterCreature;
                    entityData = new MoCEntityData(clazz, entityliving.entityId, creatureType, entityName);
                    if (!spawnWaterCreatures || maxWaterMobs == 0)
                    {
                        entityData.setCanSpawn(false);
                    }
                }
                else if (clazz != EntityLiving.class && clazz != EntityMob.class && clazz != MoCEntityFishBowl.class && clazz != MoCEntityLitterBox.class && clazz != MoCEntityEgg.class && clazz != MoCEntityKittyBed.class)
                {
                    // undefined
                    entityData = new MoCEntityData(clazz, entityliving.entityId, creatureType, entityName);
                    entityData.setCanSpawn(false); // dont allow undefined types to spawn
                }
                else if (entityData == null)
                {
                    if (debugLogging) MoCreatures.log.info("Entity " + clazz + " is not valid, skipping...");
                    continue;
                }
                classToEntityMapping.put(clazz, entityData); // store for structure use
                entityData.setEntityName(entityName);
                entityData.setEntityID(entityliving.entityId);
                if (debugLogging) MoCreatures.log.info("Added " + ((creatureType == null) ? "UNDEFINED" : creatureType.toString().toUpperCase()) + " " + entry.getKey() + " with name " + entityName);
                // handle frequencies
                // assign config for type of mod
                String entityClass = clazz.toString().toLowerCase();
                MoCConfiguration entityConfig = entityModMap.get("undefined").getModConfig();

                if (debugLogging) MoCreatures.log.info("Searching for mod " + entityClass + " belongs to...");
                if (entityClass.contains("net.minecraft") || entityClass.toString().length() <= 3) // vanilla
                {
                    MoCEntityModData modData = entityModMap.get("vanilla");
                    if (debugLogging) MoCreatures.log.info("Matched mod " + "Vanilla to " + entityClass);
                    modData.addCreature(entityData);
                    entityData.setEntityMod(modData);
                    entityConfig = modData.getModConfig();
                }
                else { // custom
                    for (Map.Entry<String, MoCEntityModData> modEntry : entityModMap.entrySet())
                    {
                        if (entityClass.contains(modEntry.getKey()))
                        {
                            // Found match, use config
                            MoCEntityModData modData = modEntry.getValue();
                            if (debugLogging) MoCreatures.log.info("Matched mod " + modEntry.getKey() + " to " + entityClass);
                            modData.addCreature(entityData);
                            entityData.setEntityMod(modData);
                            entityConfig = modData.getModConfig();
                            break;
                        }
                    }
                }
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
                                if (entityData.getType() == EnumCreatureType.creature && (!spawnCreatures || maxAnimals == 0))
                                    entityData.setCanSpawn(false);
                                if (entityData.getType() == EnumCreatureType.waterCreature && (!spawnWaterCreatures || maxWaterMobs == 0))
                                    entityData.setCanSpawn(false);
                                if (entityData.getType() == EnumCreatureType.monster && (!spawnMonsters || maxMobs == 0))
                                    entityData.setCanSpawn(false);
                                if (entityData.getType() == EnumCreatureType.ambient && (!spawnAmbients || maxAmbient == 0))
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
                        if (mocBiomeConfig.hasCategory(biomeGroupName.toLowerCase()))
                        {
                            MoCProperty biomeProps = mocBiomeConfig.getCategory(biomeGroupName).get(biomeGroupName);
                            for (int j = 0; j < biomeProps.valueList.size(); j++)
                            {
                                List<String> biomeParts = parseBiome(biomeProps.valueList.get(j));
                                if (biomeParts.size() == 2) // valid
                                {
                                    String tag = biomeParts.get(0);
                                    String biomeName = biomeParts.get(1);
                                    if (tagConfigMap.get(tag).getBiome(biomeProps.valueList.get(j)) != null)
                                    {
                                        entityData.addSpawnBiome(tagConfigMap.get(tag).getBiome(biomeProps.valueList.get(j)));
                                        if (debugLogging) MoCreatures.log.info("Added " + tagConfigMap.get(tag).getBiome(biomeProps.valueList.get(j)) + " to entity " + entityName + " spawnBiomes");
                                    }
                                    else if (debugLogging) MoCreatures.log.info("Invalid Biome " + biomeProps.valueList.get(j) + " was found in config category " + biomeGroupName + " in MoCBiomeGroups.cfg");
                                }
                            }
                        }
                    }
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
                        //modData.getModConfig().get("biomes", "biomes", modData.getModTag() + "|" + biomeName);
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
                        //MoCProperty cat = modData.getModConfig().get("biomes", "biomes");
                        //modData.getModConfig().get("biomes", "biomes", modData.getModTag() + "|" + biomeName);
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
            //System.out.println("found moddata " + modEntry.getKey() + ", biome size = " + modData.getBiomes().size());
            MoCProperty prop = modData.getModConfig().get("biomes", "biomes");
            if (prop!= null && prop.valueList != null)
            {
                prop.valueList = modData.getBiomes();
            }
            else {
                modData.getModConfig().get("biomes", "biomes", modData.getBiomes());
            }
            modData.getModConfig().save();
            //mocGlobalConfig.save();
        }
        
        mocGlobalConfig.addCustomCategoryComment("mod-biome-mappings", "Mod Biome Mappings\n" + 
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
            if (biomeGroupData.getBiomeList() != defaultBiomeGroupNether && biomeGroupData.getBiomeList() != defaultBiomeGroupTheEnd && biomeGroupData.getBiomeList() != defaultBiomeGroupWyvern && biomeGroupData.getBiomeList() != defaultBiomeGroupMobs)
            {
                //System.out.println("Found biomegroupentry " + biomeGroupEntry.getKey() + " with size " + biomeGroupData.getBiomeList().size());
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
                        //if (MoCreatures.proxy.debugLogging) MoCreatures.log.info("Found biome " + category.getValue().valueList.get(i) + " in biome group " + biomeGroupName);
                        List<String> biomeParts = parseBiome(category.getValue().valueList.get(i));

                        //System.out.println("prop = " + category.getValue().valueList.get(i) + ", tag = " + biomeParts.get(0)+ ", biomename = " + biomeParts.get(1));
                        // TODO verify biome read is valid
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

    public List<String> parseBiome(String biomeConfigEntry)
    {
        String tag = biomeConfigEntry.substring(0, biomeConfigEntry.indexOf('|'));
        String biomeName = biomeConfigEntry.substring(biomeConfigEntry.indexOf('|') + 1, biomeConfigEntry.length());
        List<String> biomeParts = new ArrayList();
        biomeParts.add(tag);
        biomeParts.add(biomeName);
        return biomeParts;
    }

    public int getFrequency(String entityName)
    {
        if (entityMap.get(entityName) != null)
            return entityMap.get(entityName).getFrequency();
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

    /*public EntityClientPlayerMP getPlayer()
    {
        return null;
    }*/
    
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
        displayPetHealth = mocGlobalConfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "displayPetHealth", false, "Shows Pet Health").getBoolean(false);
        displayPetName = mocGlobalConfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "displayPetName", false, "Shows Pet Name").getBoolean(false);
        displayPetIcons = mocGlobalConfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "displayPetIcons", false, "Shows Pet Emotes").getBoolean(false);
        animateTextures = mocGlobalConfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "animateTextures", false, "Animate Textures").getBoolean(false);
        // general
        itemID = mocGlobalConfig.get(CATEGORY_MOC_ID_SETTINGS, "ItemID", 8772, "The starting ID used for MoCreatures items. Each item will increment this number by 1 for its ID.").getInt();
        allowInstaSpawn = mocGlobalConfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "allowInstaSpawn", false, "Allows you to instantly spawn MoCreatures from GUI.").getBoolean(false);
        debugLogging = mocGlobalConfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "debugLogging", false, "Turns on verbose logging").getBoolean(false);
        worldGenCreatureSpawning = mocGlobalConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "worldGenCreatureSpawning", true, "Allows spawns during world chunk generation.").getBoolean(true);
        maxMobs = mocGlobalConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "maxMobs", 70, "Max amount of mobs proportional to the amount of chunks seen by players. Formula is : amount * chunks / 256. For more info see http://www.minecraftwiki.net/wiki/Spawn.").getInt();
        maxAnimals = mocGlobalConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "maxAnimals", 90, "Max amount of animals proportional to the amount of chunks seen by players. Formula is : amount * chunks / 256. For more info see http://www.minecraftwiki.net/wiki/Spawn.").getInt();
        maxAmbient = mocGlobalConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "maxAmbient", 20, "Max amount of ambient proportional to the amount of chunks seen by players. Formula is : amount * chunks / 256. For more info see http://www.minecraftwiki.net/wiki/Spawn.").getInt();
        maxWaterMobs = mocGlobalConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "maxWaterMobs", 30, "Max amount of watermobs proportional to the amount of chunks seen by players. Formula is : amount * chunks / 256. For more info see http://www.minecraftwiki.net/wiki/Spawn.").getInt();
        creatureSpawnTickRate = mocGlobalConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "creatureSpawnTickRate", 100, "The amount of ticks it takes to spawn animals. A tick rate of 100 would cause Custom Spawner to spawn animals every 5 seconds. Raise this value if you want spawning to occur less. Note: 20 ticks takes about 1 second.").getInt();
        mobSpawnTickRate = mocGlobalConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "mobSpawnTickRate", 100, "The amount of ticks it takes to spawn mobs. A tick rate of 100 would cause Custom Spawner to spawn mobs every 5 seconds. Raise this value if you want spawning to occur less. Note: 20 ticks takes about 1 second.").getInt();
        ambientSpawnTickRate = mocGlobalConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "ambientSpawnTickRate", 100, "The amount of ticks it takes to spawn ambients. A tick rate of 100 would cause Custom Spawner to spawn ambients every 5 seconds. Raise this value if you want spawning to occur less. Note: 20 ticks takes about 1 second.").getInt();
        waterSpawnTickRate = mocGlobalConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "waterSpawnTickRate", 100, "The amount of ticks it takes to spawn water creatures. A tick rate of 100 would cause Custom Spawner to spawn water creatures every 5 seconds. Raise this value if you want spawning to occur less. Note: 20 ticks takes about 1 second.").getInt();
        despawnTickRate = mocGlobalConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "despawnTickRate", 111, "The amount of ticks it takes to despawn vanilla creatures. Requires despawnVanilla to be enabled. Note: 20 ticks takes about 1 second.").getInt();
        mobSpawnRange = mocGlobalConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "mobSpawnRange", 8, "Mob limit radius to spawn distance (chunks aren't loaded)").getInt();
        spawnCreatures = mocGlobalConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "spawnCreatures", true, "Allow creatures to spawn. Turn off to disable all creature entity types.").getBoolean(true);
        spawnMonsters = mocGlobalConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "spawnMonsters", true, "Allow monsters to spawn. Turn off to disable all monster entity types.").getBoolean(true);
        spawnWaterCreatures = mocGlobalConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "spawnWaterCreatures", true, "Allow watercreatures to spawn. Turn off to disable all watercreature entity types.").getBoolean(true);
        spawnAmbients = mocGlobalConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "spawnAmbients", true, "Allow ambients to spawn. Turn off to disable all ambient entity types.").getBoolean(true);
        lightLevel = mocGlobalConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "lightLevel", 7, "The light level threshold used to determine whether or not to spawn a creature.").getInt();
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
        spawnPiranhas = mocGlobalConfig.get(CATEGORY_MOC_WATER_CREATURE_GENERAL_SETTINGS, "SpawnPiranhas", true, "Spawns fish that attack players.").getBoolean(true);
        modifyVanillaSpawns = mocGlobalConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "ModifyVanillaSpawns", true, "Forces Custom Spawner to handle vanilla spawns otherwise the default vanilla spawner is used.").getBoolean(true);
        despawnVanilla = mocGlobalConfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "DespawnVanilla", true, "Allows Custom Spawner to despawn vanilla every despawnTickRate. This helps prevent vanilla from overwhelming custom spawns.").getBoolean(true);
        useDefaultBiomeGroups = mocGlobalConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "useDefaultBiomeGroups", true).getBoolean(true);
        ogreStrength = Float.parseFloat(mocGlobalConfig.get(CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "OgreStrength", 2.5F, "The block destruction radius of green Ogres").value);
        caveOgreStrength = Float.parseFloat(mocGlobalConfig.get(CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "CaveOgreStrength", 3.0F, "The block destruction radius of Cave Ogres").value);
        fireOgreStrength = Float.parseFloat(mocGlobalConfig.get(CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "FireOgreStrength", 2.0F, "The block destruction radius of Fire Ogres").value);
        ogreAttackRange = (short) mocGlobalConfig.get(CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "OgreAttackRange", 12, "The block radius where ogres 'smell' players").getInt();
        fireOgreChance = (short) mocGlobalConfig.get(CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "FireOgreChance", 25, "The chance percentage of spawning Fire ogres in the Overworld").getInt();
        caveOgreChance = (short) mocGlobalConfig.get(CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "CaveOgreChance", 75, "The chance percentage of spawning Cave ogres at depth of 50 in the Overworld").getInt();

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

        // read defaults
       /* for (Map.Entry<String, MoCEntityData> entityEntry : mocEntityMap.entrySet())
        {
            MoCEntityData entityData = entityEntry.getValue();
            if (entityData != null)
            {
                MoCProperty entry1 = mocGlobalConfig.get(CATEGORY_ENTITY_SPAWN_SETTINGS, entityEntry.getKey());
                MoCProperty entry2 = mocGlobalConfig.get(CATEGORY_ENTITY_SPAWN_SETTINGS, entityEntry.getKey() + "." + entityEntry.getValue().getEntityClass().getSimpleName());
                if (entry1 == null && entry2 == null)
                {
                    mocGlobalConfig.get(CATEGORY_ENTITY_SPAWN_SETTINGS, entityEntry.getKey(), new ArrayList(Arrays.asList(entityData.getType().toString().toUpperCase(), new Integer(frequency).toString(), new Integer(minGroup).toString(), new Integer(maxGroup).toString(), new Integer(maxSpawnInChunk).toString())));
                }
                entry1 = mocGlobalConfig.get(CATEGORY_ENTITY_BIOME_SETTINGS, entityEntry.getKey());
                entry2 = mocGlobalConfig.get(CATEGORY_ENTITY_BIOME_SETTINGS, entityEntry.getKey() + "." + entityData.getEntityClass().getSimpleName());
                if (entry1 == null && entry2 == null)
                {
                    mocGlobalConfig.get(CATEGORY_ENTITY_BIOME_SETTINGS, entityEntry.getKey(), entityData.getBiomeGroups());
                }
            }
        }

        if (modifyVanillaSpawns)
        {
            for (Map.Entry<String, MoCEntityData> entityEntry : vanillaEntityMap.entrySet())
            {
                MoCEntityData entityData = entityEntry.getValue();
                if (entityData != null)
                {
                    MoCProperty entry1 = mocGlobalConfig.get(CATEGORY_ENTITY_SPAWN_SETTINGS, entityEntry.getKey());
                    MoCProperty entry2 = mocGlobalConfig.get(CATEGORY_ENTITY_SPAWN_SETTINGS, entityEntry.getKey() + "." + entityEntry.getValue().getEntityClass().getSimpleName());
                    if (entry1 == null && entry2 == null)
                    {
                        mocGlobalConfig.get(CATEGORY_ENTITY_SPAWN_SETTINGS, entityEntry.getKey(), new ArrayList(Arrays.asList(entityData.getType().toString().toUpperCase(), new Integer(frequency).toString(), new Integer(minGroup).toString(), new Integer(maxGroup).toString(), new Integer(maxSpawnInChunk).toString())));
                    }
                    entry1 = mocGlobalConfig.get(CATEGORY_ENTITY_BIOME_SETTINGS, entityEntry.getKey());
                    entry2 = mocGlobalConfig.get(CATEGORY_ENTITY_BIOME_SETTINGS, entityEntry.getKey() + "." + entityData.getEntityClass().getSimpleName());
                    if (entry1 == null && entry2 == null)
                    {
                        mocGlobalConfig.get(CATEGORY_ENTITY_BIOME_SETTINGS, entityEntry.getKey(), entityData.getBiomeGroups());
                    }
                }
            }
        }*/
        mocGlobalConfig.save();
    }
}