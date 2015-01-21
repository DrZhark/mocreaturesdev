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
import drzhark.mocreatures.configuration.MoCConfigCategory;
import drzhark.mocreatures.configuration.MoCConfiguration;
import drzhark.mocreatures.configuration.MoCProperty;
import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.monster.MoCEntityGolem;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.utils.MoCLog;


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
    public int ostrichEggDropChance;
    public int rareItemDropChance;
    public int wyvernEggDropChance;
    public int motherWyvernEggDropChance;
    public int particleFX;
    // defaults
    public int frequency = 6;
    public int minGroup = 1;
    public int maxGroup = 2;
    public int maxSpawnInChunk = 3;
    public float strength = 1;
    public int minDespawnLightLevel = 2;
    public int maxDespawnLightLevel = 7;

    // ogre settings
    public float ogreStrength;
    public float caveOgreStrength;
    public float fireOgreStrength;
    public short ogreAttackRange;
    public short fireOgreChance;
    public short caveOgreChance;

    public boolean debug = false;
    public boolean allowInstaSpawn;
    public boolean needsUpdate = false;
    public boolean worldInitDone = false;
    public boolean forceDespawns = false;
    public boolean enableHunters = false;
    public int activeScreen = -1;

    public MoCConfiguration mocSettingsConfig;
    public MoCConfiguration mocEntityConfig;
    protected File configFile;

    protected static final String CATEGORY_MOC_GENERAL_SETTINGS = "global-settings";
    protected static final String CATEGORY_MOC_CREATURE_GENERAL_SETTINGS = "creature-general-settings";
    protected static final String CATEGORY_MOC_MONSTER_GENERAL_SETTINGS = "monster-general-settings";
    protected static final String CATEGORY_MOC_WATER_CREATURE_GENERAL_SETTINGS = "water-mob-general-settings";
    protected static final String CATEGORY_MOC_AMBIENT_GENERAL_SETTINGS = "ambient-general-settings";
    protected static final String CATEGORY_MOC_ID_SETTINGS = "custom-id-settings";
    private static final String CATEGORY_VANILLA_CREATURE_FREQUENCIES = "vanilla-creature-frequencies";
    private static final String CATEGORY_CREATURES = "Creatures";
    private static final String CATEGORY_OWNERSHIP_SETTINGS = "ownership-settings";

    public void resetAllData()
    {
        //registerEntities();
        this.readGlobalConfigValues();
    }

    //----------------CONFIG INITIALIZATION
    public void ConfigInit(FMLPreInitializationEvent event) 
    {
        mocSettingsConfig = new MoCConfiguration(new File(event.getSuggestedConfigurationFile().getParent(), "MoCreatures" + File.separator + "MoCSettings.cfg"));
        mocEntityConfig = new MoCConfiguration(new File(event.getSuggestedConfigurationFile().getParent(), "MoCreatures" + File.separator + "MoCreatures.cfg"));
        configFile = event.getSuggestedConfigurationFile();
        mocSettingsConfig.load();
        mocEntityConfig.load();
        //registerEntities();
        this.readGlobalConfigValues();
        if (debug) MoCLog.logger.info("Initializing MoCreatures Config File at " + event.getSuggestedConfigurationFile().getParent() + "MoCSettings.cfg");
    }

    public int getFrequency(String entityName)//, EnumCreatureType type)
    {
        if (MoCreatures.mocEntityMap.get(entityName) != null)
            return MoCreatures.mocEntityMap.get(entityName).getFrequency();
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

    public void readMocConfigValues()
    {
        if (MoCreatures.mocEntityMap != null && !MoCreatures.mocEntityMap.isEmpty())
        {
            for (MoCEntityData entityData : MoCreatures.mocEntityMap.values())
            {
                MoCConfigCategory cat = mocEntityConfig.getCategory(entityData.getEntityName().toLowerCase());
                if (!cat.containsKey("frequency"))
                {
                    cat.put("frequency", new MoCProperty("frequency", Integer.toString(entityData.getFrequency()), MoCProperty.Type.INTEGER));
                }
                else
                {
                    entityData.setFrequency(Integer.parseInt(cat.get("frequency").value));
                }
                if (!cat.containsKey("minspawn"))
                {
                    cat.put("minspawn", new MoCProperty("minspawn", Integer.toString(entityData.getMinSpawn()), MoCProperty.Type.INTEGER));
                }
                else
                {
                    entityData.setMinSpawn(Integer.parseInt(cat.get("minspawn").value));
                }
                if (!cat.containsKey("maxspawn"))
                {
                    cat.put("maxspawn", new MoCProperty("maxspawn", Integer.toString(entityData.getMaxSpawn()), MoCProperty.Type.INTEGER));
                }
                else
                {
                    entityData.setMaxSpawn(Integer.parseInt(cat.get("maxspawn").value));
                }
                if (!cat.containsKey("maxchunk"))
                {
                    cat.put("maxchunk", new MoCProperty("maxchunk", Integer.toString(entityData.getMaxInChunk()), MoCProperty.Type.INTEGER));
                }
                else
                {
                    entityData.setMaxInChunk(Integer.parseInt(cat.get("maxchunk").value));
                }
                if (!cat.containsKey("canspawn"))
                {
                    cat.put("canspawn", new MoCProperty("canspawn", Boolean.toString(entityData.getCanSpawn()), MoCProperty.Type.BOOLEAN));
                }
                else
                {
                    entityData.setCanSpawn(Boolean.parseBoolean(cat.get("canspawn").value));
                }
            }
        }
        mocEntityConfig.save();
    }

    /**
     * Reads values from file
     */
    public void readGlobalConfigValues() 
    {
        // client-side only
        displayPetHealth = mocSettingsConfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "displayPetHealth", true, "Shows Pet Health").getBoolean(true);
        displayPetName = mocSettingsConfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "displayPetName", true, "Shows Pet Name").getBoolean(true);
        displayPetIcons = mocSettingsConfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "displayPetIcons", true, "Shows Pet Emotes").getBoolean(true);
        animateTextures = mocSettingsConfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "animateTextures", true, "Animate Textures").getBoolean(true);
        // general
        itemID = mocSettingsConfig.get(CATEGORY_MOC_ID_SETTINGS, "ItemID", 8772, "The starting ID used for MoCreatures items. Each item will increment this number by 1 for its ID.").getInt();
        allowInstaSpawn = mocSettingsConfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "allowInstaSpawn", false, "Allows you to instantly spawn MoCreatures from GUI.").getBoolean(false);
        debug = mocSettingsConfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "debug", false, "Turns on verbose logging").getBoolean(false);
        minDespawnLightLevel = mocSettingsConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "despawnLightLevel", 2, "The minimum light level threshold used to determine whether or not to despawn a farm animal. Note: Configure this value in CMS if it is installed.").getInt();
        maxDespawnLightLevel = mocSettingsConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "despawnLightLevel", 7, "The maximum light level threshold used to determine whether or not to despawn a farm animal. Note: Configure this value in CMS if it is installed.").getInt();
        forceDespawns = mocSettingsConfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "forceDespawns", false, "If true, it will force despawns on all creatures including vanilla for a more dynamic experience while exploring world. If false, all passive mocreatures will not despawn to prevent other creatures from taking over. Note: if you experience issues with farm animals despawning, adjust despawnLightLevel. If CMS is installed, this setting must remain true if you want MoCreatures to despawn.").getBoolean(false);
        maxTamed = mocSettingsConfig.get(CATEGORY_OWNERSHIP_SETTINGS, "maxTamedPerPlayer", 10, "Max tamed creatures a player can have. Requires enableOwnership to be set to true.").getInt();
        maxOPTamed = mocSettingsConfig.get(CATEGORY_OWNERSHIP_SETTINGS, "maxTamedPerOP", 20, "Max tamed creatures an op can have. Requires enableOwnership to be set to true.").getInt();
        enableOwnership = mocSettingsConfig.get(CATEGORY_OWNERSHIP_SETTINGS, "enableOwnership", false, "Assigns player as owner for each creature they tame. Only the owner can interact with the tamed creature.").getBoolean(false);
        enableResetOwnership = mocSettingsConfig.get(CATEGORY_OWNERSHIP_SETTINGS, "enableResetOwnerScroll", false, "Allows players to remove a tamed creatures owner essentially untaming it.").getBoolean(false);
        easyBreeding = mocSettingsConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "EasyBreeding", false, "Makes horse breeding simpler.").getBoolean(true);
        elephantBulldozer = mocSettingsConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "ElephantBulldozer", true).getBoolean(true);
        zebraChance = mocSettingsConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "ZebraChance", 10, "The percent for spawning a zebra.").getInt();
        ostrichEggDropChance = mocSettingsConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "OstrichEggDropChance", 3, "A value of 3 means ostriches have a 3% chance to drop an egg.").getInt();
        staticBed = mocSettingsConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "StaticBed", true).getBoolean(true);
        staticLitter = mocSettingsConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "StaticLitter", true).getBoolean(true);
        particleFX = mocSettingsConfig.get(CATEGORY_MOC_GENERAL_SETTINGS, "particleFX", 3).getInt();
        attackDolphins = mocSettingsConfig.get(CATEGORY_MOC_WATER_CREATURE_GENERAL_SETTINGS, "AttackDolphins", false, "Allows water creatures to attack dolphins.").getBoolean(false);
        attackHorses = mocSettingsConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "AttackHorses", false, "Allows creatures to attack horses.").getBoolean(false);
        attackWolves = mocSettingsConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "AttackWolves", false, "Allows creatures to attack wolves.").getBoolean(false);
        enableHunters = mocSettingsConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "EnableHunters", false, "Allows creatures to attack other creatures. Not recommended if despawning is off.").getBoolean(false);
        destroyDrops = mocSettingsConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "DestroyDrops", false).getBoolean(false);
        killallVillagers = mocSettingsConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "KillAllVillagers", false).getBoolean(false);
        rareItemDropChance = mocSettingsConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "RareItemDropChance", 25, "A value of 25 means Horses/Ostriches/Scorpions/etc. have a 25% chance to drop a rare item such as a heart of darkness, unicorn, bone when killed. Raise the value if you want higher drop rates.").getInt();
        wyvernEggDropChance = mocSettingsConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "WyvernEggDropChance", 10, "A value of 10 means wyverns have a 10% chance to drop an egg.").getInt();
        motherWyvernEggDropChance = mocSettingsConfig.get(CATEGORY_MOC_CREATURE_GENERAL_SETTINGS, "MotherWyvernEggDropChance", 33, "A value of 33 means mother wyverns have a 33% chance to drop an egg.").getInt();

        ogreStrength = Float.parseFloat(mocSettingsConfig.get(CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "OgreStrength", 2.5F, "The block destruction radius of green Ogres").getString());
        caveOgreStrength = Float.parseFloat(mocSettingsConfig.get(CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "CaveOgreStrength", 3.0F, "The block destruction radius of Cave Ogres").getString());
        fireOgreStrength = Float.parseFloat(mocSettingsConfig.get(CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "FireOgreStrength", 2.0F, "The block destruction radius of Fire Ogres").getString());
        ogreAttackRange = (short) mocSettingsConfig.get(CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "OgreAttackRange", 12, "The block radius where ogres 'smell' players").getInt();
        fireOgreChance = (short) mocSettingsConfig.get(CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "FireOgreChance", 25, "The chance percentage of spawning Fire ogres in the Overworld").getInt();
        caveOgreChance = (short) mocSettingsConfig.get(CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "CaveOgreChance", 75, "The chance percentage of spawning Cave ogres at depth of 50 in the Overworld").getInt();
        golemDestroyBlocks = mocSettingsConfig.get(CATEGORY_MOC_MONSTER_GENERAL_SETTINGS, "golemDestroyBlocks", true, "Allows Big Golems to break blocks.").getBoolean(true);
        WyvernDimension = mocSettingsConfig.get(CATEGORY_MOC_ID_SETTINGS, "WyvernLairDimensionID", -17).getInt();
        WyvernBiomeID = mocSettingsConfig.get(CATEGORY_MOC_ID_SETTINGS, "WyvernLairBiomeID", 207).getInt();
        mocSettingsConfig.save();
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