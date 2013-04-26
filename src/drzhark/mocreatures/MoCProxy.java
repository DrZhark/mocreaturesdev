package drzhark.mocreatures;

import static drzhark.mocreatures.MoCProperty.Type.INTEGER;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.boss.IBossDisplayData;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import drzhark.mocreatures.entity.MoCEntityAmbient;
import drzhark.mocreatures.entity.MoCIMoCreature;
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
    public int maxMobs;
    public int maxAnimals;
    public int maxAmbient;
    public int maxWaterMobs;
    public int animalSpawnTickRate;
    public int mobSpawnTickRate;
    public int despawnTickRate;
    public int maxTamed;
    public int maxOPTamed;
    public int zebraChance;
    public int particleFX;
    // defaults
    public short frequency = 10;
    public short minGroup = 4;
    public short maxGroup = 4;
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

    public MoCConfiguration MoCconfig;
    public MoCConfiguration MoCBiomeConfig;
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

    public static final String CATEGORY_MOC_GENERAL_SETTINGS = "general-settings";
    public static final String CATEGORY_VANILLA_CREATURE_FREQUENCIES = "vanilla-creature-frequencies";
    public static final String CATEGORY_VANILLA_MONSTER_FREQUENCIES = "vanilla-monster-frequencies";
    public static final String CATEGORY_VANILLA_WATER_CREATURE_FREQUENCIES = "vanilla-watercreature-frequencies";
    public static final String CATEGORY_MOC_CREATURES = "Creatures";
    public static final String CATEGORY_MOC_CREATURE_FREQUENCIES = "creature-frequencies";
    public static final String CATEGORY_MOC_CREATURE_GENERAL_SETTINGS = "creature-general-settings";
    public static final String CATEGORY_MOC_CREATURE_CHUNK_SETTINGS = "creature-chunk-settings";
    public static final String CATEGORY_MOC_MONSTER_FREQUENCIES = "monster-frequencies";
    public static final String CATEGORY_MOC_MONSTER_GENERAL_SETTINGS = "monster-general-settings";
    public static final String CATEGORY_MOC_MONSTER_CHUNK_SETTINGS = "monster-chunk-settings";
    public static final String CATEGORY_MOC_WATER_CREATURE_FREQUENCIES = "water-mob-frequencies";
    public static final String CATEGORY_MOC_WATER_CREATURE_GENERAL_SETTINGS = "water-mob-general-settings";
    public static final String CATEGORY_MOC_WATER_CREATURE_CHUNK_SETTINGS = "water-mob-chunk-settings";
    public static final String CATEGORY_MOC_AMBIENT_FREQUENCIES = "ambient-frequencies";
    public static final String CATEGORY_MOC_AMBIENT_GENERAL_SETTINGS = "ambient-general-settings";
    public static final String CATEGORY_MOC_AMBIENT_CHUNK_SETTINGS = "ambient-chunk-settings";
    public static final String CATEGORY_CUSTOMSPAWNER_SETTINGS = "customspawner-settings";
    public static final String CATEGORY_OWNERSHIP_SETTINGS = "ownership-settings";
    public static final String CATEGORY_ENTITY_BIOME_SETTINGS = "entity-biome-settings";
    public static final String CATEGORY_MOC_BIOME_SETTINGS = "biome-settings";
    public static final String CATEGORY_MOC_ID_SETTINGS = "custom-id-settings";

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
    public static final String BIOME_GROUP_SETTINGS = "biome-group-settings";

    public Map<String, MoCBiomeData> biomeMap = new TreeMap<String, MoCBiomeData>();

 // biome groups
    public static List defaultBiomeGroupUndefined = new ArrayList();
    public static List defaultBiomeGroupForest = new ArrayList();
    public static List defaultBiomeGroupNormal = new ArrayList();
    public static List defaultBiomeGroupSwamp = new ArrayList();
    public static List defaultBiomeGroupDesert = new ArrayList();
    public static List defaultBiomeGroupArctic = new ArrayList();
    public static List defaultBiomeGroupJungle = new ArrayList();
    public static List defaultBiomeGroupMountain = new ArrayList();
    public static List defaultBiomeGroupNether = new ArrayList();
    public static List defaultBiomeGroupTheEnd = new ArrayList();
    public static List defaultBiomeGroupWyvern = new ArrayList();
    public static List defaultBiomeGroupMobs = new ArrayList();
    public static List defaultBiomeGroupOcean = new ArrayList();
    public static List defaultBiomeGroupBeaches = new ArrayList();
    public static List defaultBiomeGroupRivers = new ArrayList();
    public static List defaultBiomeGroupMushroom = new ArrayList();
    public static List defaultBiomeGroupTwilight = new ArrayList();

    public static Map<String, MoCBiomeGroupData> biomeGroupMap = new TreeMap<String, MoCBiomeGroupData>();
    public static Map<String, MoCBiomeGroupData> defaultBiomeGroupMap = new TreeMap<String, MoCBiomeGroupData>(); // used only for default biome groups
    public static Map<String, MoCEntityData> entityMap = new TreeMap<String, MoCEntityData>();
    public static Map<String, MoCProperty> propertyMap = new TreeMap<String, MoCProperty>();
    public static final Map<String, MoCEntityData> mocEntityMap = new HashMap<String, MoCEntityData>();
    static {
        // mocreatures
        mocEntityMap.put("Bunny", new MoCEntityData(MoCEntityBunny.class, EnumCreatureType.creature));
        mocEntityMap.put("Snake", new MoCEntityData(MoCEntitySnake.class, EnumCreatureType.creature)); 
        mocEntityMap.put("Turtle", new MoCEntityData(MoCEntityTurtle.class, EnumCreatureType.creature));
        mocEntityMap.put("Bird", new MoCEntityData(MoCEntityBird.class, EnumCreatureType.creature, (short)15, (short)4, (short)4));
        mocEntityMap.put("Mouse", new MoCEntityData(MoCEntityMouse.class, EnumCreatureType.creature));
        mocEntityMap.put("Turkey", new MoCEntityData(MoCEntityTurkey.class, EnumCreatureType.creature));
        mocEntityMap.put("Horse", new MoCEntityData(MoCEntityHorse.class, EnumCreatureType.creature));
        mocEntityMap.put("HorseMob", new MoCEntityData(MoCEntityHorseMob.class, EnumCreatureType.monster));
        mocEntityMap.put("Ogre", new MoCEntityData(MoCEntityOgre.class, EnumCreatureType.monster));
        mocEntityMap.put("Boar", new MoCEntityData(MoCEntityBoar.class, EnumCreatureType.creature));
        mocEntityMap.put("Bear", new MoCEntityData(MoCEntityBear.class, EnumCreatureType.creature));
        mocEntityMap.put("Duck", new MoCEntityData(MoCEntityDuck.class, EnumCreatureType.creature));
        mocEntityMap.put("BigCat", new MoCEntityData(MoCEntityBigCat.class, EnumCreatureType.creature));
        mocEntityMap.put("Deer", new MoCEntityData(MoCEntityDeer.class, EnumCreatureType.creature));
        mocEntityMap.put("WWolf", new MoCEntityData(MoCEntityWWolf.class, EnumCreatureType.monster));
        mocEntityMap.put("Wraith", new MoCEntityData(MoCEntityWraith.class, EnumCreatureType.monster));
        mocEntityMap.put("FlameWraith", new MoCEntityData(MoCEntityFlameWraith.class, EnumCreatureType.monster));
        mocEntityMap.put("Fox", new MoCEntityData(MoCEntityFox.class, EnumCreatureType.creature));
        mocEntityMap.put("Werewolf", new MoCEntityData(MoCEntityWerewolf.class, EnumCreatureType.monster));
        mocEntityMap.put("Shark", new MoCEntityData(MoCEntityShark.class, EnumCreatureType.waterCreature));
        mocEntityMap.put("Dolphin", new MoCEntityData(MoCEntityDolphin.class, EnumCreatureType.waterCreature));
        mocEntityMap.put("Fishy", new MoCEntityData(MoCEntityFishy.class, EnumCreatureType.waterCreature));
        mocEntityMap.put("Kitty", new MoCEntityData(MoCEntityKitty.class, EnumCreatureType.creature));
        mocEntityMap.put("Rat", new MoCEntityData(MoCEntityRat.class, EnumCreatureType.monster));
        mocEntityMap.put("HellRat", new MoCEntityData(MoCEntityHellRat.class, EnumCreatureType.monster));
        mocEntityMap.put("Scorpion", new MoCEntityData(MoCEntityScorpion.class, EnumCreatureType.monster));
        mocEntityMap.put("Crocodile", new MoCEntityData(MoCEntityCrocodile.class, EnumCreatureType.monster));
        mocEntityMap.put("Ray", new MoCEntityData(MoCEntityRay.class, EnumCreatureType.waterCreature));
        mocEntityMap.put("JellyFish", new MoCEntityData(MoCEntityJellyFish.class, EnumCreatureType.waterCreature));
        mocEntityMap.put("Goat", new MoCEntityData(MoCEntityGoat.class, EnumCreatureType.creature));
        mocEntityMap.put("Ostrich", new MoCEntityData(MoCEntityOstrich.class, EnumCreatureType.creature));
        mocEntityMap.put("Bee", new MoCEntityData(MoCEntityBee.class, EnumCreatureType.ambient));
        mocEntityMap.put("Fly", new MoCEntityData(MoCEntityFly.class, EnumCreatureType.ambient));
        mocEntityMap.put("DragonFly", new MoCEntityData(MoCEntityDragonfly.class, EnumCreatureType.ambient));
        mocEntityMap.put("Firefly", new MoCEntityData(MoCEntityFirefly.class, EnumCreatureType.ambient));
        mocEntityMap.put("Cricket", new MoCEntityData(MoCEntityCricket.class, EnumCreatureType.ambient));
        mocEntityMap.put("Snail", new MoCEntityData(MoCEntitySnail.class, EnumCreatureType.ambient));
        mocEntityMap.put("ButterFly", new MoCEntityData(MoCEntityButterfly.class, EnumCreatureType.ambient));
        mocEntityMap.put("BigGolem", new MoCEntityData(MoCEntityGolem.class, EnumCreatureType.monster));
        mocEntityMap.put("Elephant", new MoCEntityData(MoCEntityElephant.class, EnumCreatureType.creature));
        mocEntityMap.put("KomodoDragon", new MoCEntityData(MoCEntityKomodo.class, EnumCreatureType.creature));
        mocEntityMap.put("Wyvern", new MoCEntityData(MoCEntityWyvern.class, EnumCreatureType.creature));

        // vanilla
        /*mocEntityMap.put("Cow", new MoCEntityData(EntityCow.class, EnumCreatureType.creature, (short)8, (short)4, (short)4));
        mocEntityMap.put("Chicken", new MoCEntityData(EntityChicken.class, EnumCreatureType.creature));
        mocEntityMap.put("Pig", new MoCEntityData(EntityPig.class, EnumCreatureType.creature));
        mocEntityMap.put("Sheep", new MoCEntityData(EntitySheep.class, EnumCreatureType.creature, (short)12, (short)4, (short)4));
        mocEntityMap.put("Wolf", new MoCEntityData(EntityWolf.class, EnumCreatureType.creature));
        mocEntityMap.put("Squid", new MoCEntityData(EntitySquid.class, EnumCreatureType.waterCreature));
        mocEntityMap.put("Ocelot", new MoCEntityData(EntityOcelot.class, EnumCreatureType.creature));
        mocEntityMap.put("Bat", new MoCEntityData(EntityBat.class, EnumCreatureType.ambient));
        mocEntityMap.put("Creeper", new MoCEntityData(EntityCreeper.class, EnumCreatureType.monster));
        mocEntityMap.put("Skeleton", new MoCEntityData(EntitySkeleton.class, EnumCreatureType.monster));
        mocEntityMap.put("Zombie", new MoCEntityData(EntityZombie.class, EnumCreatureType.monster));
        mocEntityMap.put("Spider", new MoCEntityData(EntitySpider.class, EnumCreatureType.monster));
        mocEntityMap.put("Enderman", new MoCEntityData(EntityEnderman.class, EnumCreatureType.monster, (short)1, (short)1, (short)4));
        mocEntityMap.put("CaveSpider", new MoCEntityData(EntityCaveSpider.class, EnumCreatureType.monster));
        mocEntityMap.put("Slime", new MoCEntityData(EntitySlime.class, EnumCreatureType.monster));
        mocEntityMap.put("Ghast", new MoCEntityData(EntityGhast.class, EnumCreatureType.monster, (short)6, (short)4, (short)4));
        mocEntityMap.put("PigZombie", new MoCEntityData(EntityPigZombie.class, EnumCreatureType.monster, (short)15, (short)4, (short)4));
        mocEntityMap.put("LavaSlime", new MoCEntityData(EntityMagmaCube.class, EnumCreatureType.monster, (short)1, (short)4, (short)4));*/

        /********** FOREST **********/
        // BOP
        defaultBiomeGroupForest.add("Bamboo Forest");
        defaultBiomeGroupForest.add("Birch Forest");
        defaultBiomeGroupForest.add("Boreal Forest");
        defaultBiomeGroupForest.add("Cherry Blossom Grove");
        defaultBiomeGroupForest.add("Coniferous Forest");
        defaultBiomeGroupForest.add("Dead Forest");
        defaultBiomeGroupForest.add("Deciduous Forest");
        defaultBiomeGroupForest.add("Forest");
        defaultBiomeGroupForest.add("Grove");
        defaultBiomeGroupForest.add("Maple Woods");
        defaultBiomeGroupForest.add("Meadow");
        defaultBiomeGroupForest.add("Mystic Grove");
        defaultBiomeGroupForest.add("Ominous Woods");
        defaultBiomeGroupForest.add("Orchard");
        defaultBiomeGroupForest.add("Redwood Forest");
        defaultBiomeGroupForest.add("Seasonal Forest");
        defaultBiomeGroupForest.add("Woodland");
        
        // XL
        defaultBiomeGroupForest.add("Autumn Woods");
        defaultBiomeGroupForest.add("Birch Forest");
        defaultBiomeGroupForest.add("Forested Hills");
        defaultBiomeGroupForest.add("Forested Island");
        defaultBiomeGroupForest.add("Pine Forest");
        defaultBiomeGroupForest.add("Redwood Forest");
        defaultBiomeGroupForest.add("Lush Redwoods");
        defaultBiomeGroupForest.add("Snow Forest");
        defaultBiomeGroupForest.add("Snowy Rainforest");
        defaultBiomeGroupForest.add("Temperate Rainforest");
        defaultBiomeGroupForest.add("Woodlands");
        // Vanilla
        defaultBiomeGroupForest.add("ForestHills");
        
        /********** NORMAL **********/
        // BoP
        defaultBiomeGroupNormal.add("Chaparral");
        defaultBiomeGroupNormal.add("Drylands");
        defaultBiomeGroupNormal.add("Garden");
        defaultBiomeGroupNormal.add("Oasis");
        defaultBiomeGroupNormal.add("Heathland");
        defaultBiomeGroupNormal.add("Mystic Grove");
        defaultBiomeGroupNormal.add("Origin Valley");
        defaultBiomeGroupNormal.add("Pasture");
        defaultBiomeGroupNormal.add("Prairie");
        defaultBiomeGroupNormal.add("Savanna");
        defaultBiomeGroupNormal.add("Scrubland");
        defaultBiomeGroupNormal.add("Steppe");
        defaultBiomeGroupNormal.add("Thicket");
        defaultBiomeGroupNormal.add("Deadlands");
        defaultBiomeGroupNormal.add("Promised Land");
        // XL
        defaultBiomeGroupNormal.add("Meadow");
        defaultBiomeGroupNormal.add("Shrubland");
        // Vanilla
        defaultBiomeGroupNormal.add("Plains");
        

        /********** SWAMP **********/
        // BoP
        defaultBiomeGroupSwamp.add("Bayou");
        defaultBiomeGroupSwamp.add("Bog");
        defaultBiomeGroupSwamp.add("Mangrove");
        defaultBiomeGroupSwamp.add("Lush Swamp");
        defaultBiomeGroupSwamp.add("Dead Swamp");
        defaultBiomeGroupSwamp.add("Grassland");
        defaultBiomeGroupSwamp.add("Swampwoods");
        // XL
        
        defaultBiomeGroupSwamp.add("Green Swamp");
        defaultBiomeGroupSwamp.add("Marsh");
        // Vanilla
        defaultBiomeGroupSwamp.add("Swampland");

        /********** DESERT **********/
        // BoP
        defaultBiomeGroupDesert.add("Badlands");
        defaultBiomeGroupDesert.add("Desert");
        defaultBiomeGroupDesert.add("DesertHills");
        defaultBiomeGroupDesert.add("Dunes");
        defaultBiomeGroupDesert.add("Lush Desert");
        defaultBiomeGroupDesert.add("Mesa");
        defaultBiomeGroupDesert.add("Quagmire");
        defaultBiomeGroupDesert.add("Wetland");
        // XL
        defaultBiomeGroupDesert.add("Ice Wasteland");
        defaultBiomeGroupDesert.add("Mountainous Desert");
        defaultBiomeGroupDesert.add("Mountain Ridge");
        defaultBiomeGroupDesert.add("Wasteland");

        /********** ARCTIC **********/
        // BoP
        defaultBiomeGroupArctic.add("Frost Forest");
        defaultBiomeGroupArctic.add("Arctic Forest");
        defaultBiomeGroupArctic.add("Alps");
        defaultBiomeGroupArctic.add("Arctic");
        defaultBiomeGroupArctic.add("Icy Hills");

        // vanilla
        defaultBiomeGroupArctic.add("Taiga");
        defaultBiomeGroupArctic.add("FrozenOcean");
        defaultBiomeGroupArctic.add("FrozenRiver");
        defaultBiomeGroupArctic.add("Ice Plains");
        defaultBiomeGroupArctic.add("Ice Mountains");
        defaultBiomeGroupArctic.add("TaigaHills");

        // XL
        defaultBiomeGroupArctic.add("Alpine");
        defaultBiomeGroupArctic.add("Glacier");
        defaultBiomeGroupArctic.add("Ice Wasteland");
        defaultBiomeGroupArctic.add("Mountain Taiga");
        defaultBiomeGroupArctic.add("Snow Forest");
        defaultBiomeGroupArctic.add("Snowy Rainforest");
        defaultBiomeGroupArctic.add("Tundra");

        /********** JUNGLE **********/
        // XL
        defaultBiomeGroupJungle.add("Jungle");
        defaultBiomeGroupJungle.add("JungleHills");
        defaultBiomeGroupJungle.add("Extreme Jungle");
        defaultBiomeGroupJungle.add("Mini Jungle");
        defaultBiomeGroupJungle.add("Rainforest");

        /********** MOUNTAIN **********/
        // BoP
        defaultBiomeGroupMountain.add("Crag");
        defaultBiomeGroupMountain.add("Mountain");
        // XL
        defaultBiomeGroupMountain.add("Green Hills");
        defaultBiomeGroupMountain.add("Alpine");
        defaultBiomeGroupMountain.add("Extreme Hills");
        defaultBiomeGroupMountain.add("Extreme Hills Edge");
        defaultBiomeGroupMountain.add("Extreme Jungle");
        defaultBiomeGroupMountain.add("Forested Hills");
        defaultBiomeGroupMountain.add("Mountain Ridge");
        defaultBiomeGroupMountain.add("Mountain Taiga");

        /********** OCEAN **********/
        defaultBiomeGroupOcean.add("Ocean");
        defaultBiomeGroupOcean.add("FrozenOcean");
        
        /********** RIVER **********/
        defaultBiomeGroupRivers.add("River");
        defaultBiomeGroupRivers.add("FrozenRiver");

        /********** BEACH **********/
        defaultBiomeGroupBeaches.add("Beach");
        defaultBiomeGroupBeaches.add("Shore");
        
        
        /********** MUSHROOM **********/
        defaultBiomeGroupMushroom.add("MushroomIsland");
        defaultBiomeGroupMushroom.add("MushroomIslandShore");
        defaultBiomeGroupMushroom.add("Fungi Forest");

        /********** NETHER **********/
        defaultBiomeGroupNether.add("Hell");
        
        /********** THEEND **********/
        defaultBiomeGroupTheEnd.add("Sky");
        
        /********** WYVERNLAIR **********/
        defaultBiomeGroupWyvern.add("WyvernBiome");

        /********** TWILIGHT *********/
        defaultBiomeGroupTwilight.add("Clearing Border");
        defaultBiomeGroupTwilight.add("Dark Forest");
        defaultBiomeGroupTwilight.add("Dense Twilight Forest");
        defaultBiomeGroupTwilight.add("Enchanted Forest");
        defaultBiomeGroupTwilight.add("Fire Swamp");
        defaultBiomeGroupTwilight.add("Highlands");
        defaultBiomeGroupTwilight.add("Lake Border");
        defaultBiomeGroupTwilight.add("Lots of Mushrooms");
        defaultBiomeGroupTwilight.add("Major Feature");
        defaultBiomeGroupTwilight.add("Minor Feature");
        defaultBiomeGroupTwilight.add("Mushrooms");
        defaultBiomeGroupTwilight.add("Snowy Forest");
        defaultBiomeGroupTwilight.add("Twilight Clearing");
        defaultBiomeGroupTwilight.add("Twilight Forest");
        defaultBiomeGroupTwilight.add("Twilight Lake");
        defaultBiomeGroupTwilight.add("Twilight Stream");
        defaultBiomeGroupTwilight.add("Twilight Swamp");
        /********** MOBS **********/
        //Contains a list of all of the biomes except for the Nether, End and custom ones (like wyvernLair)
        for (int x = 0; x < defaultBiomeGroupForest.size(); x++)
        {
            if (!defaultBiomeGroupMobs.contains(defaultBiomeGroupForest.get(x)))
                defaultBiomeGroupMobs.add(defaultBiomeGroupForest.get(x));
        }
        for (int x = 0; x < defaultBiomeGroupNormal.size(); x++)
        {
            if (!defaultBiomeGroupMobs.contains(defaultBiomeGroupNormal.get(x)))
                defaultBiomeGroupMobs.add(defaultBiomeGroupNormal.get(x));
        }
        for (int x = 0; x < defaultBiomeGroupSwamp.size(); x++)
        {
            if (!defaultBiomeGroupMobs.contains(defaultBiomeGroupSwamp.get(x)))
                defaultBiomeGroupMobs.add(defaultBiomeGroupSwamp.get(x));
        }
        for (int x = 0; x < defaultBiomeGroupDesert.size(); x++)
        {
            if (!defaultBiomeGroupMobs.contains(defaultBiomeGroupDesert.get(x)))
                defaultBiomeGroupMobs.add(defaultBiomeGroupDesert.get(x));
        }
        for (int x = 0; x < defaultBiomeGroupArctic.size(); x++)
        {
            if (!defaultBiomeGroupMobs.contains(defaultBiomeGroupArctic.get(x)))
                defaultBiomeGroupMobs.add(defaultBiomeGroupArctic.get(x));
        }
        for (int x = 0; x < defaultBiomeGroupJungle.size(); x++)
        {
            if (!defaultBiomeGroupMobs.contains(defaultBiomeGroupJungle.get(x)))
                defaultBiomeGroupMobs.add(defaultBiomeGroupJungle.get(x));
        }
        for (int x = 0; x < defaultBiomeGroupMountain.size(); x++)
        {
            if (!defaultBiomeGroupMobs.contains(defaultBiomeGroupMountain.get(x)))
                defaultBiomeGroupMobs.add(defaultBiomeGroupMountain.get(x));
        }
        for (int x = 0; x < defaultBiomeGroupOcean.size(); x++)
        {
            if (!defaultBiomeGroupMobs.contains(defaultBiomeGroupOcean.get(x)))
                defaultBiomeGroupMobs.add(defaultBiomeGroupOcean.get(x));
        }
        for (int x = 0; x < defaultBiomeGroupRivers.size(); x++)
        {
            if (!defaultBiomeGroupMobs.contains(defaultBiomeGroupRivers.get(x)))
                defaultBiomeGroupMobs.add(defaultBiomeGroupRivers.get(x));
        }
        for (int x = 0; x < defaultBiomeGroupBeaches.size(); x++)
        {
            if (!defaultBiomeGroupMobs.contains(defaultBiomeGroupBeaches.get(x)))
                defaultBiomeGroupMobs.add(defaultBiomeGroupBeaches.get(x));
        }
        // sort default lists
        Collections.sort(defaultBiomeGroupUndefined);
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

        defaultBiomeGroupMap.put(BIOME_UNDEFINED_GROUP, new MoCBiomeGroupData(BIOME_UNDEFINED_GROUP, defaultBiomeGroupUndefined));
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
    }

    //----------------CONFIG INITIALIZATION
    public void ConfigInit(FMLPreInitializationEvent event) 
    {
        configPreEvent = event;
        MoCconfig = new MoCConfiguration(new File(event.getSuggestedConfigurationFile().getParent(), "MoCProperties.cfg"));
        MoCBiomeConfig = new MoCConfiguration(new File(event.getSuggestedConfigurationFile().getParent(), "MoCBiomeGroups.cfg"));
        configFile = event.getSuggestedConfigurationFile();
        MoCconfig.load();
        MoCBiomeConfig.load();
        this.readConfigValues();
        if (debugLogging) MoCreatures.log.info("Initializing MoCreatures Server Config File at " + event.getSuggestedConfigurationFile().getParent() + "MoCProperties.cfg");
    }

    public void ConfigPostInit(FMLPostInitializationEvent event) {
        configPostEvent = event;
        initializeBiomes();
        initializeEntities();
    }

    public void initializeEntities()
    {
        for (Object obj : EntityList.classToStringMapping.entrySet())
        {

            Map.Entry entry = (Map.Entry)obj;
            Class clazz = null;
            String entityName = "";
            try 
            {
                clazz = (Class)entry.getKey();
                if (clazz != null)
                {
                    entityName = (String)entry.getValue();
                    //if (debugLogging) MoCreatures.log.info("Found entityName "  + entityName);
                    if (entityName.contains("."))
                    {
                        if ((entityName.indexOf(".") + 1) < entityName.length())
                            entityName = entityName.substring(entityName.indexOf(".") + 1, entityName.length());
                    }
                    entityName = entityName.replaceAll("[^A-Za-z0-9]", ""); // remove all non-digits/alphanumeric
                }
            }
            catch (Exception e)
            {
                continue;
            }
            if (clazz != null && EntityLiving.class.isAssignableFrom(clazz) && !(IBossDisplayData.class.isAssignableFrom(clazz)))
            {
                if (EntityAnimal.class.isAssignableFrom(clazz) && !(MoCEntityAmbient.class.isAssignableFrom(clazz)))
                {
                    MoCEntityData entityData = new MoCEntityData(clazz, EnumCreatureType.creature);
                    entityMap.put(entityName, entityData);
                    if (debugLogging) MoCreatures.log.info("Added CREATURE " + entry.getKey() + " with name " + entityName);
                    // handle frequencies
                    MoCConfigCategory frequencyCategory = MoCconfig.getCategory(CATEGORY_MOC_CREATURE_FREQUENCIES);
                    if (!frequencyCategory.containsKey(entityName))
                    {
                        MoCconfig.get(CATEGORY_MOC_CREATURE_FREQUENCIES, entityName, new int[] {frequency, minGroup, maxGroup});
                    }
                    else 
                    {
                        for (Map.Entry<String, MoCProperty> propEntry : frequencyCategory.getValues().entrySet())
                        {
                            if (propEntry.getKey().equalsIgnoreCase(entityName))
                            {
                                // handle entity config
                                MoCProperty property = propEntry.getValue();
                                if (property.valueList != null && property.valueList.size() == 3)
                                {
                                    entityData.frequency = Short.parseShort(property.valueList.get(0));
                                    entityData.minGroup = Integer.parseInt(property.valueList.get(1));
                                    entityData.maxGroup = Integer.parseInt(property.valueList.get(2));
                                    break;
                                }
                                else if (debugLogging)
                                {
                                    MoCreatures.log.severe("Detected invalid frequency value for entity " + entityName);
                                }
                            }
                        }
                    }
                    MoCConfigCategory entityBiomeCategory = MoCconfig.getCategory(CATEGORY_ENTITY_BIOME_SETTINGS);
                    if (entityBiomeCategory.containsKey(entityName)) // populate biome groups for GUI
                    {
                        for (Map.Entry<String, MoCProperty> propEntry : entityBiomeCategory.getValues().entrySet())
                        {
                            if (propEntry.getKey().equalsIgnoreCase(entityName))
                            {
                                MoCProperty property = propEntry.getValue();
                                if (property.valueList != null)
                                {
                                    entityData.setBiomeGroups((ArrayList)property.valueList);
                                    break;
                                }
                            }
                        }
                    }
                }
                else if (IMob.class.isAssignableFrom(clazz) && (clazz != EntityMob.class))
                {
                    MoCEntityData entityData = new MoCEntityData(clazz, EnumCreatureType.monster);
                    entityMap.put(entityName, entityData);
                    if (debugLogging) MoCreatures.log.info("Added MONSTER class " + entry.getKey() + " with name " + entityName);
                    MoCConfigCategory frequencyCategory = MoCconfig.getCategory(CATEGORY_MOC_MONSTER_FREQUENCIES);
                    if (!frequencyCategory.containsKey(entityName))
                    {
                        MoCconfig.get(CATEGORY_MOC_MONSTER_FREQUENCIES, entityName, new int[] {frequency, minGroup, maxGroup});
                    }
                    else 
                    {
                        for (Map.Entry<String, MoCProperty> propEntry : frequencyCategory.getValues().entrySet())
                        {
                            if (propEntry.getKey().equalsIgnoreCase(entityName))
                            {
                                // handle entity config
                                MoCProperty property = propEntry.getValue();
                                if (property.valueList != null && property.valueList.size() == 3)
                                {
                                    entityData.frequency = Short.parseShort(property.valueList.get(0));
                                    entityData.minGroup = Integer.parseInt(property.valueList.get(1));
                                    entityData.maxGroup = Integer.parseInt(property.valueList.get(2));
                                    break;
                                }
                                else if (debugLogging)
                                {
                                    MoCreatures.log.severe("Detected invalid frequency value for entity " + entityName);
                                }
                            }
                        }
                    }
                    MoCConfigCategory entityBiomeCategory = MoCconfig.getCategory(CATEGORY_ENTITY_BIOME_SETTINGS);
                    if (entityBiomeCategory.containsKey(entityName)) // populate biome groups for GUI
                    {
                        for (Map.Entry<String, MoCProperty> propEntry : entityBiomeCategory.getValues().entrySet())
                        {
                            if (propEntry.getKey().equalsIgnoreCase(entityName))
                            {
                                MoCProperty property = propEntry.getValue();
                                if (property.valueList != null)
                                {
                                    entityData.setBiomeGroups((ArrayList)property.valueList);
                                    break;
                                }
                            }
                        }
                    }
                }
                else if (EntityAmbientCreature.class.isAssignableFrom(clazz) || MoCEntityAmbient.class.isAssignableFrom(clazz))
                {
                    MoCEntityData entityData = new MoCEntityData(clazz, EnumCreatureType.ambient);
                    entityMap.put(entityName, entityData);
                    if (debugLogging) MoCreatures.log.info("Added AMBIENT " + entry.getKey() + " with name " + entityName);
                    MoCConfigCategory frequencyCategory = MoCconfig.getCategory(CATEGORY_MOC_AMBIENT_FREQUENCIES);
                    if (!frequencyCategory.containsKey(entityName))
                    {
                        MoCconfig.get(CATEGORY_MOC_AMBIENT_FREQUENCIES, entityName, new int[] {frequency, minGroup, maxGroup});
                    }
                    else 
                    {
                        for (Map.Entry<String, MoCProperty> propEntry : frequencyCategory.getValues().entrySet())
                        {
                            if (propEntry.getKey().equalsIgnoreCase(entityName))
                            {
                                // handle entity config
                                MoCProperty property = propEntry.getValue();
                                if (property.valueList != null && property.valueList.size() == 3)
                                {
                                    entityData.frequency = Short.parseShort(property.valueList.get(0));
                                    entityData.minGroup = Integer.parseInt(property.valueList.get(1));
                                    entityData.maxGroup = Integer.parseInt(property.valueList.get(2));
                                    break;
                                }
                                else if (debugLogging)
                                {
                                    MoCreatures.log.severe("Detected invalid frequency value for entity " + entityName + ".");
                                }
                            }
                        }
                    }
                    MoCConfigCategory entityBiomeCategory = MoCconfig.getCategory(CATEGORY_ENTITY_BIOME_SETTINGS);
                    if (entityBiomeCategory.containsKey(entityName)) // populate biome groups for GUI
                    {
                        for (Map.Entry<String, MoCProperty> propEntry : entityBiomeCategory.getValues().entrySet())
                        {
                            if (propEntry.getKey().equalsIgnoreCase(entityName))
                            {
                                MoCProperty property = propEntry.getValue();
                                if (property.valueList != null)
                                {
                                    entityData.setBiomeGroups((ArrayList)property.valueList);
                                    break;
                                }
                            }
                        }
                    }
                }
                else if (EntityWaterMob.class.isAssignableFrom(clazz))
                {
                    MoCEntityData entityData = new MoCEntityData(clazz, EnumCreatureType.waterCreature);
                    entityMap.put(entityName, entityData);
                    if (debugLogging) MoCreatures.log.info("Added WATERCREATURE class " + entry.getKey() + " with name " + entityName);
                    MoCConfigCategory frequencyCategory = MoCconfig.getCategory(CATEGORY_MOC_WATER_CREATURE_FREQUENCIES);
                    if (!frequencyCategory.containsKey(entityName))
                    {
                        MoCconfig.get(CATEGORY_MOC_WATER_CREATURE_FREQUENCIES, entityName, new int[] {frequency, minGroup, maxGroup});
                    }
                    else 
                    {
                        for (Map.Entry<String, MoCProperty> propEntry : frequencyCategory.getValues().entrySet())
                        {
                            if (propEntry.getKey().equalsIgnoreCase(entityName))
                            {
                                // handle entity config
                                MoCProperty property = propEntry.getValue();
                                if (property.valueList != null && property.valueList.size() == 3)
                                {
                                    entityData.frequency = Short.parseShort(property.valueList.get(0));
                                    entityData.minGroup = Integer.parseInt(property.valueList.get(1));
                                    entityData.maxGroup = Integer.parseInt(property.valueList.get(2));
                                    break;
                                }
                                else if (debugLogging)
                                {
                                    MoCreatures.log.severe("Detected invalid frequency value for entity " + entityName + ".");
                                }
                            }
                        }
                    }
                    MoCConfigCategory entityBiomeCategory = MoCconfig.getCategory(CATEGORY_ENTITY_BIOME_SETTINGS);
                    if (entityBiomeCategory.containsKey(entityName)) // populate biome groups for GUI
                    {
                        for (Map.Entry<String, MoCProperty> propEntry : entityBiomeCategory.getValues().entrySet())
                        {
                            if (propEntry.getKey().equalsIgnoreCase(entityName))
                            {
                                MoCProperty property = propEntry.getValue();
                                if (property.valueList != null)
                                {
                                    entityData.setBiomeGroups((ArrayList)property.valueList);
                                    break;
                                }
                            }
                        }
                    }
                }
                if (entityMap.containsKey(entityName))
                    MoCconfig.get(CATEGORY_ENTITY_BIOME_SETTINGS, entityName, new ArrayList());
            }
        }
        MoCconfig.save();
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
            if (BiomeGenBase.biomeList[i] != null && !biomeMap.containsKey(BiomeGenBase.biomeList[i].biomeName))
            {
                biomeMap.put(BiomeGenBase.biomeList[i].biomeName, new MoCBiomeData(BiomeGenBase.biomeList[i].getClass(), BiomeGenBase.biomeList[i]));
                if (MoCreatures.proxy.debugLogging) MoCreatures.log.info("Added biome " + BiomeGenBase.biomeList[i].biomeName + " with biomeID " + BiomeGenBase.biomeList[i].biomeID);
            }
        }

        List biomes = null;
        if (useDefaultBiomeGroups) 
        {
            // generate default biome groups
            if (!MoCBiomeConfig.hasCategory(BIOME_ARCTIC_GROUP))
            {
                MoCBiomeConfig.get(BIOME_ARCTIC_GROUP, BIOME_ARCTIC_GROUP, defaultBiomeGroupArctic);
            }
            if (!MoCBiomeConfig.hasCategory(BIOME_DESERT_GROUP))
            {
                MoCBiomeConfig.get(BIOME_DESERT_GROUP, BIOME_DESERT_GROUP, defaultBiomeGroupDesert);
            }
            if (!MoCBiomeConfig.hasCategory(BIOME_FOREST_GROUP))
            {
                MoCBiomeConfig.get(BIOME_FOREST_GROUP, BIOME_FOREST_GROUP, defaultBiomeGroupForest);
            }
            if (!MoCBiomeConfig.hasCategory(BIOME_JUNGLE_GROUP))
            {
                MoCBiomeConfig.get(BIOME_JUNGLE_GROUP, BIOME_JUNGLE_GROUP, defaultBiomeGroupJungle);
            }
            if (!MoCBiomeConfig.hasCategory(BIOME_MOUNTAIN_GROUP))
            {
                MoCBiomeConfig.get(BIOME_MOUNTAIN_GROUP, BIOME_MOUNTAIN_GROUP, defaultBiomeGroupMountain);
            }
            if (!MoCBiomeConfig.hasCategory(BIOME_NORMAL_GROUP))
            {
                MoCBiomeConfig.get(BIOME_NORMAL_GROUP, BIOME_NORMAL_GROUP, defaultBiomeGroupNormal);
            }
            if (!MoCBiomeConfig.hasCategory(BIOME_SWAMP_GROUP))
            {
                MoCBiomeConfig.get(BIOME_SWAMP_GROUP, BIOME_SWAMP_GROUP, defaultBiomeGroupSwamp);
            }
            if (!MoCBiomeConfig.hasCategory(BIOME_MOBS_GROUP))
            {
                MoCBiomeConfig.get(BIOME_MOBS_GROUP, BIOME_MOBS_GROUP, defaultBiomeGroupMobs);
            }
            if (!MoCBiomeConfig.hasCategory(BIOME_NETHER_GROUP))
            {
                MoCBiomeConfig.get(BIOME_NETHER_GROUP, BIOME_NETHER_GROUP, defaultBiomeGroupNether);
            }
            if (!MoCBiomeConfig.hasCategory(BIOME_THEEND_GROUP))
            {
                MoCBiomeConfig.get(BIOME_THEEND_GROUP, BIOME_THEEND_GROUP, defaultBiomeGroupTheEnd);
            }
            if (!MoCBiomeConfig.hasCategory(BIOME_WYVERN_GROUP))
            {
                MoCBiomeConfig.get(BIOME_WYVERN_GROUP, BIOME_WYVERN_GROUP, defaultBiomeGroupWyvern);
            }
            if (!MoCBiomeConfig.hasCategory(BIOME_RIVER_GROUP))
            {
                MoCBiomeConfig.get(BIOME_RIVER_GROUP, BIOME_RIVER_GROUP, defaultBiomeGroupRivers);
            }
            if (!MoCBiomeConfig.hasCategory(BIOME_OCEAN_GROUP))
            {
                MoCBiomeConfig.get(BIOME_OCEAN_GROUP, BIOME_OCEAN_GROUP, defaultBiomeGroupOcean);
            }
            if (!MoCBiomeConfig.hasCategory(BIOME_BEACHES_GROUP))
            {
                MoCBiomeConfig.get(BIOME_BEACHES_GROUP, BIOME_BEACHES_GROUP, defaultBiomeGroupBeaches);
            }
            if (!MoCBiomeConfig.hasCategory(BIOME_MUSHROOM_GROUP))
            {
                MoCBiomeConfig.get(BIOME_MUSHROOM_GROUP, BIOME_MUSHROOM_GROUP, defaultBiomeGroupTwilight);
            }
            if (!MoCBiomeConfig.hasCategory(BIOME_TWILIGHT_GROUP))
            {
                MoCBiomeConfig.get(BIOME_TWILIGHT_GROUP, BIOME_TWILIGHT_GROUP, defaultBiomeGroupTwilight);
            }
            MoCBiomeConfig.save();
        }

        // read biome groups from config and populate biomeGroupMap
        if (MoCreatures.proxy.debugLogging) MoCreatures.log.info("Reading MoCBiomeGroups.cfg ....");
        for (Map.Entry<String, MoCConfigCategory> catEntry : MoCBiomeConfig.categories.entrySet())
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
                        if (MoCreatures.proxy.debugLogging) MoCreatures.log.info("Found biome " + category.getValue().valueList.get(i) + " in biome group " + biomeGroupName);
                        if (biomeList.contains(category.getValue().valueList.get(i)))
                            continue; // ignore duplicate biomes
                        biomeList.add(category.getValue().valueList.get(i));
                        MoCBiomeData biomeData = biomeMap.get(category.getValue().valueList.get(i));
                        if (biomeData != null)
                        {
                            // found valid biome in config so set defined flag to true so it doesn't get added to UNDEFINED group
                            biomeData.setDefined(true);
                        }
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
        for (Map.Entry<String, MoCBiomeData> biomeEntry : biomeMap.entrySet())
        {
            MoCBiomeData biomeData = biomeEntry.getValue();
            if (!biomeData.isDefined())
            {
                defaultBiomeGroupUndefined.add(biomeData.getBiomeName());
                if (MoCreatures.proxy.debugLogging) MoCreatures.log.info("Biome " + biomeData.getBiomeName() + " was NOT DEFINED, Added biome to UNDEFINED BIOME GROUP");
            }
            else if (MoCreatures.proxy.debugLogging) MoCreatures.log.info("Biome " + biomeData.getBiomeName() + " was DEFINED");
        }
        if (defaultBiomeGroupUndefined.size() > 0)
        {
            MoCProperty category = MoCBiomeConfig.get(BIOME_UNDEFINED_GROUP, BIOME_UNDEFINED_GROUP, defaultBiomeGroupUndefined);
            category.valueList = defaultBiomeGroupUndefined;
            biomeGroupMap.remove(BIOME_UNDEFINED_GROUP);
            biomeGroupMap.put(BIOME_UNDEFINED_GROUP, new MoCBiomeGroupData(BIOME_UNDEFINED_GROUP, defaultBiomeGroupUndefined));
        }
        MoCBiomeConfig.save();
    }

    public int getFrequency(String entityName)
    {
        if (entityMap.get(entityName) != null)
            return entityMap.get(entityName).frequency;
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
        displayPetHealth = MoCconfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "displayPetHealth", false, "Shows Pet Health").getBoolean(false);
        displayPetName = MoCconfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "displayPetName", false, "Shows Pet Name").getBoolean(false);
        displayPetIcons = MoCconfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "displayPetIcons", false, "Shows Pet Emotes").getBoolean(false);
        animateTextures = MoCconfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "animateTextures", false, "Animate Textures").getBoolean(false);
        // general
        itemID = MoCconfig.get(CATEGORY_MOC_ID_SETTINGS, "ItemID", 8772, "The starting ID used for MoCreatures items. Each item will increment this number by 1 for its ID.").getInt();
        allowInstaSpawn = MoCconfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "allowInstaSpawn", false, "Allows you to instantly spawn MoCreatures from GUI.").getBoolean(false);
        debugLogging = MoCconfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "debugLogging", false, "Turns on verbose logging").getBoolean(false);
        worldGenCreatureSpawning = MoCconfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "worldGenCreatureSpawning", true, "Allows spawns during world chunk generation.").getBoolean(true);
        maxMobs = MoCconfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "maxMobs", 70, "Max amount of mobs proportional to the amount of chunks seen by players. Formula is : amount * chunks / 256. For more info see http://www.minecraftwiki.net/wiki/Spawn.").getInt();
        maxAnimals = MoCconfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "maxAnimals", 90, "Max amount of animals proportional to the amount of chunks seen by players. Formula is : amount * chunks / 256. For more info see http://www.minecraftwiki.net/wiki/Spawn.").getInt();
        maxAmbient = MoCconfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "maxAmbient", 20, "Max amount of ambient proportional to the amount of chunks seen by players. Formula is : amount * chunks / 256. For more info see http://www.minecraftwiki.net/wiki/Spawn.").getInt();
        maxWaterMobs = MoCconfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "maxWaterMobs", 30, "Max amount of watermobs proportional to the amount of chunks seen by players. Formula is : amount * chunks / 256. For more info see http://www.minecraftwiki.net/wiki/Spawn.").getInt();
        animalSpawnTickRate = MoCconfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "animalSpawnTickRate", 100, "The amount of ticks it takes to spawn animals. A tick rate of 100 would cause Custom Spawner to spawn animals every 5 seconds. Raise this value if you want spawning to occur less. Note: 20 ticks takes about 1 second.").getInt();
        mobSpawnTickRate = MoCconfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "mobSpawnTickRate", 100, "The amount of ticks it takes to spawn mobs. A tick rate of 100 would cause Custom Spawner to spawn mobs every 5 seconds. Raise this value if you want spawning to occur less. Note: 20 ticks takes about 1 second.").getInt();
        despawnTickRate = MoCconfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "despawnTickRate", 111, "The amount of ticks it takes to despawn vanilla creatures. Requires despawnVanilla to be enabled. Note: 20 ticks takes about 1 second.").getInt();
        useCustomSpawner = MoCconfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "useCustomSpawner", true, "If enabled, Custom Spawner will be activated and process all entities in MoCProperties.cfg. Any entity not configured with a biome group will be ignored and used by Vanilla's spawner instead.").getBoolean(true);
        maxTamed = MoCconfig.get(CATEGORY_OWNERSHIP_SETTINGS, "maxTamedPerPlayer", 10, "Max tamed creatures a player can have. Requires enableOwnership to be set to true.").getInt();
        maxOPTamed = MoCconfig.get(CATEGORY_OWNERSHIP_SETTINGS, "maxTamedPerOP", 20, "Max tamed creatures an op can have. Requires enableOwnership to be set to true.").getInt();
        enableOwnership = MoCconfig.get(CATEGORY_OWNERSHIP_SETTINGS, "enableOwnership", false, "Assigns player as owner for each creature they tame. Only the owner can interact with the tamed creature.").getBoolean(false);
        enableResetOwnership = MoCconfig.get(CATEGORY_OWNERSHIP_SETTINGS, "enableResetOwnerScroll", false, "Allows players to remove a tamed creatures owner essentially untaming it.").getBoolean(false);
        easyBreeding = MoCconfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "EasyBreeding", true, "Makes horse breeding simpler.").getBoolean(true);
        elephantBulldozer = MoCconfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "ElephantBulldozer", true).getBoolean(true);
        zebraChance = MoCconfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "ZebraChance", 2, "The percent for breeding a zebra from horse.").getInt();
        staticBed = MoCconfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "StaticBed", true).getBoolean(true);
        staticLitter = MoCconfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "StaticLitter", true).getBoolean(true);
        particleFX = MoCconfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "particleFX", 3).getInt();
        attackDolphins = MoCconfig.get(CATEGORY_MOC_WATER_CREATURE_GENERAL_SETTINGS, "AttackDolphins", false, "Allows water creatures to attack dolphins.").getBoolean(false);
        attackHorses = MoCconfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "AttackHorses", false, "Allows creatures to attack horses.").getBoolean(false);
        attackWolves = MoCconfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "AttackWolves", false, "Allows creatures to attack wolves.").getBoolean(false);
        destroyDrops = MoCconfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "DestroyDrops", false).getBoolean(false);
        spawnPiranhas = MoCconfig.get(CATEGORY_MOC_WATER_CREATURE_GENERAL_SETTINGS, "SpawnPiranhas", true, "Spawns fish that attack players.").getBoolean(true);
        modifyVanillaSpawns = MoCconfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "ModifyVanillaSpawns", true, "Forces Custom Spawner to handle vanilla spawns otherwise the default vanilla spawner is used.").getBoolean(true);
        despawnVanilla = MoCconfig.get(CATEGORY_CUSTOMSPAWNER_SETTINGS, "DespawnVanilla", true, "Allows Custom Spawner to despawn vanilla every despawnTickRate. This helps prevent vanilla from overwhelming custom spawns.").getBoolean(true);
        useDefaultBiomeGroups = MoCconfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "useDefaultBiomeGroups", true).getBoolean(true);
        ogreStrength = Float.parseFloat(MoCconfig.get(CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "OgreStrength", 2.5F, "The block destruction radius of green Ogres").value);
        caveOgreStrength = Float.parseFloat(MoCconfig.get(CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "CaveOgreStrength", 3.0F, "The block destruction radius of Cave Ogres").value);
        fireOgreStrength = Float.parseFloat(MoCconfig.get(CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "FireOgreStrength", 2.0F, "The block destruction radius of Fire Ogres").value);
        ogreAttackRange = (short) MoCconfig.get(CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "OgreAttackRange", 12, "The block radius where ogres 'smell' players").getInt();
        fireOgreChance = (short) MoCconfig.get(CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "FireOgreChance", 25, "The chance percentage of spawning Fire ogres in the Overworld").getInt();
        caveOgreChance = (short) MoCconfig.get(CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "CaveOgreChance", 75, "The chance percentage of spawning Cave ogres at depth of 50 in the Overworld").getInt();

        //blocks
        blockDirtID = MoCconfig.getTerrainBlock(CATEGORY_MOC_ID_SETTINGS, "DirtBlockID", 200, "Basic block for terrain generation, needs to be less than 256").getInt();
        blockGrassID = MoCconfig.getTerrainBlock(CATEGORY_MOC_ID_SETTINGS, "GrassBlockID", 201, "Basic block for terrain generation, needs to be less than 256").getInt();
        blockLeafID = MoCconfig.getBlock(CATEGORY_MOC_ID_SETTINGS, "LeafBlockID", 700).getInt();
        blockLogID = MoCconfig.getBlock(CATEGORY_MOC_ID_SETTINGS, "LogBlockID", 701).getInt();
        blockTallGrassID = MoCconfig.getBlock(CATEGORY_MOC_ID_SETTINGS, "TallGrassBlockID", 702).getInt();
        blockPlanksID = MoCconfig.getBlock(CATEGORY_MOC_ID_SETTINGS, "PlanksBlockID", 703).getInt();
        blockStoneID = MoCconfig.getTerrainBlock(CATEGORY_MOC_ID_SETTINGS, "StoneBlockID", 202, "Basic block for terrain generation, needs to be less than 256").getInt();
        WyvernDimension = MoCconfig.get(CATEGORY_MOC_ID_SETTINGS, "WyvernLairDimensionID", -17).getInt();
        WyvernBiomeID = MoCconfig.get(CATEGORY_MOC_ID_SETTINGS, "WyvernLairBiomeID", 207).getInt();
        MoCconfig.save();
    }
}