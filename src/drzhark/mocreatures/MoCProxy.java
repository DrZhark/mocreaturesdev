package drzhark.mocreatures;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import drzhark.mocreatures.configuration.MoCConfiguration;
import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.monster.MoCEntityGolem;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;


public class MoCProxy implements IGuiHandler {

    public static String ARMOR_TEXTURE = "textures/armor/";
    public static String BLOCK_TEXTURE = "textures/blocks/";
    public static String ITEM_TEXTURE = "textures/items/";
    public static String MODEL_TEXTURE = "textures/models/";
    public static String GUI_TEXTURE = "textures/gui/";
    public static String MISC_TEXTURE = "textures/misc/";

    //CONFIG VARIABLES
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
    public boolean destroyDrops;
    public boolean enableOwnership;
    public boolean enableResetOwnership;
    public boolean elephantBulldozer;
    public boolean killallVillagers;

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

    public boolean debugLogging = false;
    public boolean allowInstaSpawn;
    //public boolean useGlobalEntityRegistration;
    public boolean needsUpdate = false;
    public boolean worldInitDone = false;
    public boolean useDespawner = false;
    public int activeScreen = -1;

    public MoCConfiguration mocGlobalConfig;
    protected File configFile;

    protected static final String MOD_CREATURES_FILE_ROOT = File.separator + "Creatures" + File.separator;
    private static final String MOD_BIOME_FILE_ROOT = File.separator + "Biomes" + File.separator;

    protected static final String CATEGORY_MOC_GENERAL_SETTINGS = "global-settings";
    protected static final String CATEGORY_MOC_CREATURE_GENERAL_SETTINGS = "creature-general-settings";
    protected static final String CATEGORY_MOC_MONSTER_GENERAL_SETTINGS = "monster-general-settings";
    protected static final String CATEGORY_MOC_WATER_CREATURE_GENERAL_SETTINGS = "water-mob-general-settings";
    protected static final String CATEGORY_MOC_AMBIENT_GENERAL_SETTINGS = "ambient-general-settings";
    protected static final String CATEGORY_MOC_UNDEFINED_FREQUENCIES = "undefined-frequencies";
    protected static final String CATEGORY_ENTITY_BIOME_SETTINGS = "entity-biome-settings";
    protected static final String CATEGORY_BIOMEGROUP_DEFAULTS = "biomegroup-defaults";
    protected static final String CATEGORY_MOC_ID_SETTINGS = "custom-id-settings";
    private static final String CATEGORY_MOD_MAPPINGS = "mod-mappings";
    private static final String CATEGORY_VANILLA_CREATURE_FREQUENCIES = "vanilla-creature-frequencies";
    private static final String CATEGORY_CREATURES = "Creatures";
    public static final String CATEGORY_ENTITY_CANSPAWN_SETTINGS = "entity-canspawn-settings";
    private static final String CATEGORY_OWNERSHIP_SETTINGS = "ownership-settings";
    private static final String CATEGORY_ENTITY_SPAWN_TYPES = "entity-spawn-types";
    private static final String CATEGORY_WORLD_SETTINGS = "world-settings";
    public static final String CATEGORY_ENTITY_SPAWN_SETTINGS = "entity-spawn-settings";

    private static final String BIOME_GROUP_SETTINGS = "biome-group-settings";

    private static final String MOD_KEY_BIOMESOPLENTY = "biomesop";
    private static final String MOD_KEY_EXTRABIOMESXL = "extrabiomes";
    private static final String MOD_KEY_BWG = "ted80";
    private static final String MOD_KEY_VANILLA = "vanilla";
    private static final String MOD_KEY_TWILIGHT = "twilightforest";
    public boolean isBWGinstalled = false;

    public void resetAllData()
    {
        //registerEntities();
        this.readConfigValues();
    }

    //----------------CONFIG INITIALIZATION
    public void ConfigInit(FMLPreInitializationEvent event) 
    {
        mocGlobalConfig = new MoCConfiguration(new File(event.getSuggestedConfigurationFile().getParent(), "MoCreatures" + File.separator + "MoCGlobal.cfg"));
        configFile = event.getSuggestedConfigurationFile();
        mocGlobalConfig.load();
        //registerEntities();
        this.readConfigValues();
        if (debugLogging) MoCreatures.log.info("Initializing MoCreatures Server Config File at " + event.getSuggestedConfigurationFile().getParent() + "MoCGlobal.cfg");
    }

    public int getFrequency(int entityId)//String entityName, EnumCreatureType type)
    {
        if (MoCreatures.mocEntityMap.get(entityId) != null)
            return MoCreatures.mocEntityMap.get(entityId).getFrequency();
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

    public void initTextures() {}

    public ResourceLocation getTexture(String texture) 
    {
        return null;
    }

    public EntityPlayer getPlayer()
    {
        return null;
    }

    public void printMessageToPlayer(String msg)
    {
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
        useDespawner = mocGlobalConfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "useDespawner", false, "If true, despawner will force despawns on all creatures including vanilla for a more dynamic experience while exploring world. If false, all passive mocreatures will not despawn to prevent other creatures from taking over. Note: if you experience issues with farm animals despawning, adjust despawnLightLevel.").getBoolean(false);
        maxTamed = mocGlobalConfig.get(CATEGORY_OWNERSHIP_SETTINGS, "maxTamedPerPlayer", 10, "Max tamed creatures a player can have. Requires enableOwnership to be set to true.").getInt();
        maxOPTamed = mocGlobalConfig.get(CATEGORY_OWNERSHIP_SETTINGS, "maxTamedPerOP", 20, "Max tamed creatures an op can have. Requires enableOwnership to be set to true.").getInt();
        enableOwnership = mocGlobalConfig.get(CATEGORY_OWNERSHIP_SETTINGS, "enableOwnership", false, "Assigns player as owner for each creature they tame. Only the owner can interact with the tamed creature.").getBoolean(false);
        enableResetOwnership = mocGlobalConfig.get(CATEGORY_OWNERSHIP_SETTINGS, "enableResetOwnerScroll", false, "Allows players to remove a tamed creatures owner essentially untaming it.").getBoolean(false);
        easyBreeding = mocGlobalConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "EasyBreeding", false, "Makes horse breeding simpler.").getBoolean(true);
        elephantBulldozer = mocGlobalConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "ElephantBulldozer", true).getBoolean(true);
        zebraChance = mocGlobalConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "ZebraChance", 10, "The percent for spawning a zebra.").getInt();
        staticBed = mocGlobalConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "StaticBed", true).getBoolean(true);
        staticLitter = mocGlobalConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "StaticLitter", true).getBoolean(true);
        particleFX = mocGlobalConfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "particleFX", 3).getInt();
        attackDolphins = mocGlobalConfig.get(CATEGORY_MOC_WATER_CREATURE_GENERAL_SETTINGS, "AttackDolphins", false, "Allows water creatures to attack dolphins.").getBoolean(false);
        attackHorses = mocGlobalConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "AttackHorses", false, "Allows creatures to attack horses.").getBoolean(false);
        attackWolves = mocGlobalConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "AttackWolves", false, "Allows creatures to attack wolves.").getBoolean(false);
        destroyDrops = mocGlobalConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "DestroyDrops", false).getBoolean(false);
        killallVillagers = mocGlobalConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "KillAllVillagers", false).getBoolean(false);

        ogreStrength = Float.parseFloat(mocGlobalConfig.get(CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "OgreStrength", 2.5F, "The block destruction radius of green Ogres").getString());
        caveOgreStrength = Float.parseFloat(mocGlobalConfig.get(CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "CaveOgreStrength", 3.0F, "The block destruction radius of Cave Ogres").getString());
        fireOgreStrength = Float.parseFloat(mocGlobalConfig.get(CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "FireOgreStrength", 2.0F, "The block destruction radius of Fire Ogres").getString());
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
    public void setName(EntityPlayer player, IMoCEntity mocanimal) {
        //client side only
    }

    public void initGUI() {
        // client side only
    }
}