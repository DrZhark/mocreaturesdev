package drzhark.mocreatures.init;

import drzhark.mocreatures.MoCConstants;
import net.minecraft.init.Bootstrap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(MoCConstants.MOD_ID)
public class MoCSoundEvents {

    public static final SoundEvent ENTITY_BEAR_AMBIENT;
    public static final SoundEvent ENTITY_BEAR_DEATH;
    public static final SoundEvent ENTITY_BEAR_HURT;
    public static final SoundEvent ENTITY_BEE_AMBIENT;
    public static final SoundEvent ENTITY_BEE_UPSET;
    public static final SoundEvent ENTITY_BIRD_AMBIENT_BLACK;
    public static final SoundEvent ENTITY_BIRD_AMBIENT_BLUE;
    public static final SoundEvent ENTITY_BIRD_AMBIENT_GREEN;
    public static final SoundEvent ENTITY_BIRD_AMBIENT_RED;
    public static final SoundEvent ENTITY_BIRD_AMBIENT_WHITE;
    public static final SoundEvent ENTITY_BIRD_AMBIENT_YELLOW;
    public static final SoundEvent ENTITY_BIRD_DEATH;
    public static final SoundEvent ENTITY_BIRD_HURT;
    public static final SoundEvent ENTITY_CRICKET_AMBIENT;
    public static final SoundEvent ENTITY_CRICKET_FLY;
    public static final SoundEvent ENTITY_CROCODILE_AMBIENT;
    public static final SoundEvent ENTITY_CROCODILE_DEATH;
    public static final SoundEvent ENTITY_CROCODILE_HURT;
    public static final SoundEvent ENTITY_CROCODILE_JAWSNAP;
    public static final SoundEvent ENTITY_CROCODILE_RESTING;
    public static final SoundEvent ENTITY_CROCODILE_ROLL;
    public static final SoundEvent ENTITY_DEER_AMBIENT_BABY;
    public static final SoundEvent ENTITY_DEER_AMBIENT;
    public static final SoundEvent ENTITY_DEER_DEATH;
    public static final SoundEvent ENTITY_DEER_HURT;
    public static final SoundEvent ENTITY_DOLPHIN_DEATH;
    public static final SoundEvent ENTITY_DOLPHIN_HURT;
    public static final SoundEvent ENTITY_DOLPHIN_AMBIENT;
    public static final SoundEvent ENTITY_DOLPHIN_UPSET;
    public static final SoundEvent ENTITY_DUCK_AMBIENT;
    public static final SoundEvent ENTITY_DUCK_DEATH;
    public static final SoundEvent ENTITY_DUCK_HURT;
    public static final SoundEvent ENTITY_DRAGONFLY_AMBIENT;
    public static final SoundEvent ENTITY_ELEPHANT_AMBIENT_BABY;
    public static final SoundEvent ENTITY_ELEPHANT_AMBIENT;
    public static final SoundEvent ENTITY_ELEPHANT_DEATH;
    public static final SoundEvent ENTITY_ELEPHANT_HURT;
    public static final SoundEvent ENTITY_ENT_AMBIENT;
    public static final SoundEvent ENTITY_ENT_DEATH;
    public static final SoundEvent ENTITY_ENT_HURT;
    public static final SoundEvent ENTITY_FLY_AMBIENT;
    public static final SoundEvent ENTITY_FOX_AMBIENT;
    public static final SoundEvent ENTITY_FOX_DEATH;
    public static final SoundEvent ENTITY_FOX_HURT;
    public static final SoundEvent ENTITY_GENERIC_ARMOR_ON;
    public static final SoundEvent ENTITY_GENERIC_ARMOR_OFF;
    public static final SoundEvent ENTITY_GENERIC_DESTROY;
    public static final SoundEvent ENTITY_GENERIC_DRINKING;
    public static final SoundEvent ENTITY_GENERIC_EATING;
    public static final SoundEvent ENTITY_GENERIC_MAGIC_APPEAR;
    public static final SoundEvent ENTITY_GENERIC_ROPING;
    public static final SoundEvent ENTITY_GENERIC_TRANSFORM;
    public static final SoundEvent ENTITY_GENERIC_TUD;
    public static final SoundEvent ENTITY_GENERIC_VANISH;
    public static final SoundEvent ENTITY_GENERIC_WHIP;
    public static final SoundEvent ENTITY_GENERIC_WINGFLAP;
    public static final SoundEvent ENTITY_GOAT_AMBIENT;
    public static final SoundEvent ENTITY_GOAT_AMBIENT_BABY;
    public static final SoundEvent ENTITY_GOAT_AMBIENT_FEMALE;
    public static final SoundEvent ENTITY_GOAT_DEATH;
    public static final SoundEvent ENTITY_GOAT_DIGG;
    public static final SoundEvent ENTITY_GOAT_EATING;
    public static final SoundEvent ENTITY_GOAT_HURT;
    public static final SoundEvent ENTITY_GOAT_SMACK;
    public static final SoundEvent ENTITY_GOLEM_AMBIENT;
    public static final SoundEvent ENTITY_GOLEM_ATTACH;
    public static final SoundEvent ENTITY_GOLEM_DYING;
    public static final SoundEvent ENTITY_GOLEM_EXPLODE;
    public static final SoundEvent ENTITY_GOLEM_HURT;
    public static final SoundEvent ENTITY_GOLEM_SHOOT;
    public static final SoundEvent ENTITY_GOLEM_WALK;
    public static final SoundEvent ENTITY_HORSE_MAD;
    public static final SoundEvent ENTITY_HORSE_AMBIENT;
    //public static final SoundEvent ENTITY_HORSE_AMBIENT_DONKEY;
    public static final SoundEvent ENTITY_HORSE_AMBIENT_GHOST;
    public static final SoundEvent ENTITY_HORSE_AMBIENT_UNDEAD;
    public static final SoundEvent ENTITY_HORSE_AMBIENT_ZEBRA;
    //public static final SoundEvent ENTITY_HORSE_ANGRY;
    //public static final SoundEvent ENTITY_HORSE_ANGRY_DONKEY;
    public static final SoundEvent ENTITY_HORSE_ANGRY_GHOST;
    public static final SoundEvent ENTITY_HORSE_ANGRY_UNDEAD;
    //public static final SoundEvent ENTITY_HORSE_ANGRY_ZEBRA;
    public static final SoundEvent ENTITY_HORSE_DEATH;
    public static final SoundEvent ENTITY_HORSE_DEATH_DONKEY;
    public static final SoundEvent ENTITY_HORSE_DEATH_GHOST;
    public static final SoundEvent ENTITY_HORSE_DEATH_UNDEAD;
    public static final SoundEvent ENTITY_HORSE_HURT;
    public static final SoundEvent ENTITY_HORSE_HURT_DONKEY;
    public static final SoundEvent ENTITY_HORSE_HURT_GHOST;
    public static final SoundEvent ENTITY_HORSE_HURT_UNDEAD;
    public static final SoundEvent ENTITY_HORSE_HURT_ZEBRA;
    public static final SoundEvent ENTITY_KITTY_AMBIENT;
    public static final SoundEvent ENTITY_KITTY_AMBIENT_BABY;
    public static final SoundEvent ENTITY_KITTY_ANGRY;
    public static final SoundEvent ENTITY_KITTY_DEATH;
    public static final SoundEvent ENTITY_KITTY_DEATH_BABY;
    public static final SoundEvent ENTITY_KITTY_DRINKING;
    public static final SoundEvent ENTITY_KITTY_EATING;
    public static final SoundEvent ENTITY_KITTY_HUNGRY;
    public static final SoundEvent ENTITY_KITTY_HURT;
    public static final SoundEvent ENTITY_KITTY_HURT_BABY;
    public static final SoundEvent ENTITY_KITTY_LITTER;
    public static final SoundEvent ENTITY_KITTY_PURR;
    public static final SoundEvent ENTITY_KITTY_TRAPPED;
    public static final SoundEvent ENTITY_KITTYBED_POURINGMILK;
    public static final SoundEvent ENTITY_KITTYBED_POURINGFOOD;
    public static final SoundEvent ENTITY_LION_AMBIENT;
    public static final SoundEvent ENTITY_LION_AMBIENT_BABY;
    public static final SoundEvent ENTITY_LION_DEATH;
    public static final SoundEvent ENTITY_LION_DEATH_BABY;
    public static final SoundEvent ENTITY_LION_HURT;
    public static final SoundEvent ENTITY_LION_HURT_BABY;
    public static final SoundEvent ENTITY_MOUSE_AMBIENT;
    public static final SoundEvent ENTITY_MOUSE_DEATH;
    public static final SoundEvent ENTITY_MOUSE_HURT;
    public static final SoundEvent ENTITY_OGRE_AMBIENT;
    public static final SoundEvent ENTITY_OGRE_DEATH;
    public static final SoundEvent ENTITY_OGRE_HURT;
    public static final SoundEvent ENTITY_OSTRICH_AMBIENT;
    public static final SoundEvent ENTITY_OSTRICH_AMBIENT_BABY;
    public static final SoundEvent ENTITY_OSTRICH_DEATH;
    public static final SoundEvent ENTITY_OSTRICH_HURT;
    public static final SoundEvent ENTITY_RABBIT_DEATH;
    public static final SoundEvent ENTITY_RABBIT_HURT;
    public static final SoundEvent ENTITY_RABBIT_LIFT;
    public static final SoundEvent ENTITY_RACCOON_AMBIENT;
    public static final SoundEvent ENTITY_RACCOON_DEATH;
    public static final SoundEvent ENTITY_RACCOON_HURT;
    public static final SoundEvent ENTITY_RAT_AMBIENT;
    public static final SoundEvent ENTITY_RAT_DEATH;
    public static final SoundEvent ENTITY_RAT_HURT;
    public static final SoundEvent ENTITY_SCORPION_AMBIENT;
    public static final SoundEvent ENTITY_SCORPION_CLAW;
    public static final SoundEvent ENTITY_SCORPION_DEATH;
    public static final SoundEvent ENTITY_SCORPION_HURT;
    public static final SoundEvent ENTITY_SCORPION_STING;
    public static final SoundEvent ENTITY_SNAKE_AMBIENT;
    public static final SoundEvent ENTITY_SNAKE_ANGRY;
    public static final SoundEvent ENTITY_SNAKE_DEATH;
    public static final SoundEvent ENTITY_SNAKE_HURT;
    public static final SoundEvent ENTITY_SNAKE_RATTLE;
    public static final SoundEvent ENTITY_SNAKE_SNAP;
    public static final SoundEvent ENTITY_SNAKE_SWIM;
    public static final SoundEvent ENTITY_TURKEY_AMBIENT;
    //public static final SoundEvent ENTITY_TURKEY_DEATH;
    public static final SoundEvent ENTITY_TURKEY_HURT;
    public static final SoundEvent ENTITY_TURTLE_AMBIENT;
    public static final SoundEvent ENTITY_TURTLE_ANGRY;
    public static final SoundEvent ENTITY_TURTLE_DEATH;
    public static final SoundEvent ENTITY_TURTLE_EATING;
    public static final SoundEvent ENTITY_TURTLE_HURT;
    public static final SoundEvent ENTITY_WEREWOLF_AMBIENT;
    public static final SoundEvent ENTITY_WEREWOLF_AMBIENT_HUMAN;
    public static final SoundEvent ENTITY_WEREWOLF_DEATH;
    public static final SoundEvent ENTITY_WEREWOLF_DEATH_HUMAN;
    public static final SoundEvent ENTITY_WEREWOLF_HURT_HUMAN;
    public static final SoundEvent ENTITY_WEREWOLF_HURT;
    public static final SoundEvent ENTITY_WEREWOLF_TRANSFORM;
    public static final SoundEvent ENTITY_WRAITH_AMBIENT;
    public static final SoundEvent ENTITY_WOLF_AMBIENT;
    public static final SoundEvent ENTITY_WOLF_DEATH;
    public static final SoundEvent ENTITY_WOLF_HURT;
    public static final SoundEvent ENTITY_WRAITH_DEATH;
    public static final SoundEvent ENTITY_WRAITH_HURT;
    public static final SoundEvent ENTITY_WYVERN_AMBIENT;
    public static final SoundEvent ENTITY_WYVERN_DEATH;
    public static final SoundEvent ENTITY_WYVERN_HURT;
    public static final SoundEvent ENTITY_WYVERN_WINGFLAP;
    public static final SoundEvent ITEM_RECORD_SHUFFLING;

    /**
     * Create a {@link SoundEvent}.
     *
     * @param soundName The SoundEvent's name without the testmod3 prefix
     * @return The SoundEvent
     */
    private static SoundEvent createSoundEvent(final String soundName) {
        final ResourceLocation soundID = new ResourceLocation(MoCConstants.MOD_ID, soundName);
        return new SoundEvent(soundID).setRegistryName(soundID);
    }

    @Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID)
    public static class RegistrationHandler {
        @SubscribeEvent
        public static void registerSoundEvents(final RegistryEvent.Register<SoundEvent> event) {
            event.getRegistry().registerAll(
                    ENTITY_BEAR_AMBIENT,
                    ENTITY_BEAR_DEATH,
                    ENTITY_BEAR_HURT,
                    ENTITY_BEE_AMBIENT,
                    ENTITY_BEE_UPSET,
                    ENTITY_BIRD_AMBIENT_BLACK,
                    ENTITY_BIRD_AMBIENT_BLUE,
                    ENTITY_BIRD_AMBIENT_GREEN,
                    ENTITY_BIRD_AMBIENT_RED,
                    ENTITY_BIRD_AMBIENT_YELLOW,
                    ENTITY_BIRD_AMBIENT_WHITE,
                    ENTITY_BIRD_DEATH,
                    ENTITY_BIRD_HURT,
                    ENTITY_CRICKET_AMBIENT,
                    ENTITY_CRICKET_FLY,
                    ENTITY_CROCODILE_AMBIENT,
                    ENTITY_CROCODILE_DEATH,
                    ENTITY_CROCODILE_HURT,
                    ENTITY_CROCODILE_JAWSNAP,
                    ENTITY_CROCODILE_RESTING,
                    ENTITY_CROCODILE_ROLL,
                    ENTITY_DEER_AMBIENT_BABY,
                    ENTITY_DEER_AMBIENT,
                    ENTITY_DEER_DEATH,
                    ENTITY_DEER_HURT,
                    ENTITY_DOLPHIN_AMBIENT,
                    ENTITY_DOLPHIN_DEATH,
                    ENTITY_DOLPHIN_HURT,
                    ENTITY_DOLPHIN_UPSET,
                    ENTITY_DUCK_AMBIENT,
                    ENTITY_DUCK_DEATH,
                    ENTITY_DUCK_HURT,
                    ENTITY_DRAGONFLY_AMBIENT,
                    ENTITY_ENT_AMBIENT,
                    ENTITY_ENT_DEATH,
                    ENTITY_ENT_HURT,
                    ENTITY_ELEPHANT_AMBIENT_BABY,
                    ENTITY_ELEPHANT_AMBIENT,
                    ENTITY_ELEPHANT_DEATH,
                    ENTITY_ELEPHANT_HURT,
                    ENTITY_FLY_AMBIENT,
                    ENTITY_FOX_AMBIENT,
                    ENTITY_FOX_DEATH,
                    ENTITY_FOX_HURT,
                    ENTITY_GENERIC_ARMOR_ON,
                    ENTITY_GENERIC_ARMOR_OFF,
                    ENTITY_GENERIC_DESTROY,
                    ENTITY_GENERIC_DRINKING,
                    ENTITY_GENERIC_EATING,
                    ENTITY_GENERIC_MAGIC_APPEAR,
                    ENTITY_GENERIC_ROPING,
                    ENTITY_GENERIC_TRANSFORM,
                    ENTITY_GENERIC_TUD,
                    ENTITY_GENERIC_VANISH,
                    ENTITY_GENERIC_WHIP,
                    ENTITY_GENERIC_WINGFLAP,
                    ENTITY_GOAT_AMBIENT,
                    ENTITY_GOAT_AMBIENT_BABY,
                    ENTITY_GOAT_AMBIENT_FEMALE,
                    ENTITY_GOAT_DEATH,
                    ENTITY_GOAT_HURT,
                    ENTITY_GOAT_DIGG,
                    ENTITY_GOAT_EATING,
                    ENTITY_GOAT_SMACK,
                    ENTITY_GOLEM_AMBIENT,
                    ENTITY_GOLEM_ATTACH,
                    ENTITY_GOLEM_DYING,
                    ENTITY_GOLEM_EXPLODE,
                    ENTITY_GOLEM_HURT,
                    ENTITY_GOLEM_SHOOT,
                    ENTITY_GOLEM_WALK,
                    ENTITY_HORSE_MAD,
                    ENTITY_HORSE_AMBIENT,
                    //ENTITY_HORSE_AMBIENT_DONKEY,
                    ENTITY_HORSE_AMBIENT_GHOST,
                    ENTITY_HORSE_AMBIENT_UNDEAD,
                    ENTITY_HORSE_AMBIENT_ZEBRA,
                    //ENTITY_HORSE_ANGRY,
                    //ENTITY_HORSE_ANGRY_DONKEY,
                    ENTITY_HORSE_ANGRY_GHOST,
                    ENTITY_HORSE_ANGRY_UNDEAD,
                    //ENTITY_HORSE_ANGRY_ZEBRA,
                    ENTITY_HORSE_DEATH,
                    ENTITY_HORSE_DEATH_DONKEY,
                    ENTITY_HORSE_DEATH_GHOST,
                    ENTITY_HORSE_DEATH_UNDEAD,
                    ENTITY_HORSE_HURT,
                    ENTITY_HORSE_HURT_DONKEY,
                    ENTITY_HORSE_HURT_GHOST,
                    ENTITY_HORSE_HURT_UNDEAD,
                    ENTITY_HORSE_HURT_ZEBRA,
                    ENTITY_KITTY_AMBIENT,
                    ENTITY_KITTY_AMBIENT_BABY,
                    ENTITY_KITTY_ANGRY,
                    ENTITY_KITTY_DEATH,
                    ENTITY_KITTY_DEATH_BABY,
                    ENTITY_KITTY_DRINKING,
                    ENTITY_KITTY_EATING,
                    ENTITY_KITTY_HUNGRY,
                    ENTITY_KITTY_HURT,
                    ENTITY_KITTY_HURT_BABY,
                    ENTITY_KITTY_LITTER,
                    ENTITY_KITTY_PURR,
                    ENTITY_KITTY_TRAPPED,
                    ENTITY_KITTYBED_POURINGFOOD,
                    ENTITY_KITTYBED_POURINGMILK,
                    ENTITY_LION_AMBIENT,
                    ENTITY_LION_AMBIENT_BABY,
                    ENTITY_LION_DEATH,
                    ENTITY_LION_DEATH_BABY ,
                    ENTITY_LION_HURT,
                    ENTITY_LION_HURT_BABY,
                    ENTITY_MOUSE_AMBIENT,
                    ENTITY_MOUSE_DEATH,
                    ENTITY_MOUSE_HURT,
                    ENTITY_OGRE_AMBIENT,
                    ENTITY_OGRE_DEATH,
                    ENTITY_OGRE_HURT ,
                    ENTITY_OSTRICH_AMBIENT,
                    ENTITY_OSTRICH_AMBIENT_BABY,
                    ENTITY_OSTRICH_DEATH,
                    ENTITY_OSTRICH_HURT,
                    ENTITY_RABBIT_DEATH,
                    ENTITY_RABBIT_HURT,
                    ENTITY_RABBIT_LIFT,
                    ENTITY_RACCOON_AMBIENT,
                    ENTITY_RACCOON_DEATH,
                    ENTITY_RACCOON_HURT,
                    ENTITY_RAT_AMBIENT,
                    ENTITY_RAT_DEATH,
                    ENTITY_RAT_HURT,
                    ENTITY_SCORPION_AMBIENT,
                    ENTITY_SCORPION_CLAW,
                    ENTITY_SCORPION_DEATH,
                    ENTITY_SCORPION_HURT,
                    ENTITY_SCORPION_STING,
                    ENTITY_SNAKE_AMBIENT,
                    ENTITY_SNAKE_ANGRY,
                    ENTITY_SNAKE_DEATH,
                    ENTITY_SNAKE_HURT,
                    ENTITY_SNAKE_RATTLE,
                    ENTITY_SNAKE_SNAP,
                    ENTITY_SNAKE_SWIM,
                    ENTITY_TURKEY_AMBIENT,
                    //ENTITY_TURKEY_DEATH,
                    ENTITY_TURKEY_HURT,
                    ENTITY_TURTLE_AMBIENT,
                    ENTITY_TURTLE_ANGRY,
                    ENTITY_TURTLE_DEATH,
                    ENTITY_TURTLE_EATING,
                    ENTITY_TURTLE_HURT,
                    ENTITY_WEREWOLF_AMBIENT_HUMAN,
                    ENTITY_WEREWOLF_DEATH_HUMAN,
                    ENTITY_WEREWOLF_HURT_HUMAN,
                    ENTITY_WEREWOLF_AMBIENT,
                    ENTITY_WEREWOLF_DEATH,
                    ENTITY_WEREWOLF_HURT,
                    ENTITY_WEREWOLF_TRANSFORM,
                    ENTITY_WOLF_AMBIENT,
                    ENTITY_WOLF_DEATH,
                    ENTITY_WOLF_HURT,
                    ENTITY_WRAITH_AMBIENT,
                    ENTITY_WRAITH_DEATH,
                    ENTITY_WRAITH_HURT,
                    ENTITY_WYVERN_AMBIENT,
                    ENTITY_WYVERN_DEATH,
                    ENTITY_WYVERN_HURT,
                    ENTITY_WYVERN_WINGFLAP,
                    ITEM_RECORD_SHUFFLING
            );
        }
    }

    static {
        if (!Bootstrap.isRegistered()) {
            throw new RuntimeException("Accessed Sounds before Bootstrap!");
        } else {
            ENTITY_BEAR_AMBIENT = createSoundEvent("beargrunt");
            ENTITY_BEAR_DEATH = createSoundEvent("beardeath");
            ENTITY_BEAR_HURT = createSoundEvent("bearhurt");
            ENTITY_BEE_AMBIENT = createSoundEvent("bee");
            ENTITY_BEE_UPSET = createSoundEvent("beeupset");
            ENTITY_BIRD_AMBIENT_BLACK = createSoundEvent("birdblack");
            ENTITY_BIRD_AMBIENT_BLUE = createSoundEvent("birdblue");
            ENTITY_BIRD_AMBIENT_GREEN = createSoundEvent("birdgreen");
            ENTITY_BIRD_AMBIENT_RED = createSoundEvent("birdred");
            ENTITY_BIRD_AMBIENT_YELLOW = createSoundEvent("birdyellow");
            ENTITY_BIRD_AMBIENT_WHITE = createSoundEvent("birdwhite");
            ENTITY_BIRD_DEATH = createSoundEvent("birddying"); // TODO
            ENTITY_BIRD_HURT = createSoundEvent("birdhurt"); // TODO
            ENTITY_CRICKET_AMBIENT = createSoundEvent("cricket");
            ENTITY_CRICKET_FLY = createSoundEvent("cricketfly");
            ENTITY_CROCODILE_AMBIENT = createSoundEvent("crocgrunt");
            ENTITY_CROCODILE_DEATH = createSoundEvent("crocdying");
            ENTITY_CROCODILE_HURT = createSoundEvent("crochurt"); // TODO
            ENTITY_CROCODILE_JAWSNAP = createSoundEvent("crocjawsnap");
            ENTITY_CROCODILE_RESTING = createSoundEvent("crocresting");
            ENTITY_CROCODILE_ROLL = createSoundEvent("crocroll");
            ENTITY_DEER_AMBIENT_BABY = createSoundEvent("deerbgrunt");
            ENTITY_DEER_AMBIENT = createSoundEvent("deerfgrunt");
            ENTITY_DEER_DEATH = createSoundEvent("deerdying");
            ENTITY_DEER_HURT = createSoundEvent("deerhurt"); // TODO
            ENTITY_DOLPHIN_AMBIENT = createSoundEvent("dolphin");
            ENTITY_DOLPHIN_DEATH = createSoundEvent("dolphindying");
            ENTITY_DOLPHIN_HURT = createSoundEvent("dolphinhurt");
            ENTITY_DOLPHIN_UPSET = createSoundEvent("dolphinupset");
            ENTITY_DUCK_AMBIENT = createSoundEvent("duck");
            ENTITY_DUCK_DEATH = createSoundEvent("duckdying"); // TODO
            ENTITY_DUCK_HURT = createSoundEvent("duckhurt");
            ENTITY_DRAGONFLY_AMBIENT = createSoundEvent("dragonfly");
            ENTITY_ENT_AMBIENT = createSoundEvent("entgrunt"); // TODO
            ENTITY_ENT_DEATH = createSoundEvent("entdying"); // TODO
            ENTITY_ENT_HURT = createSoundEvent("enthurt"); // TODO
            ENTITY_ELEPHANT_AMBIENT_BABY = createSoundEvent("elephantcalf");
            ENTITY_ELEPHANT_AMBIENT = createSoundEvent("elephantgrunt");
            ENTITY_ELEPHANT_DEATH = createSoundEvent("elephantdying"); // TODO
            ENTITY_ELEPHANT_HURT = createSoundEvent("elephanthurt");
            ENTITY_FLY_AMBIENT = createSoundEvent("fly");
            ENTITY_FOX_AMBIENT = createSoundEvent("foxcall");
            ENTITY_FOX_DEATH = createSoundEvent("foxdying");
            ENTITY_FOX_HURT = createSoundEvent("foxhurt");
            ENTITY_GENERIC_ARMOR_ON = createSoundEvent("armorput");
            ENTITY_GENERIC_ARMOR_OFF = createSoundEvent("armoroff");
            ENTITY_GENERIC_DESTROY = createSoundEvent("destroy");
            ENTITY_GENERIC_DRINKING = createSoundEvent("drinking");
            ENTITY_GENERIC_EATING = createSoundEvent("eating");
            ENTITY_GENERIC_MAGIC_APPEAR = createSoundEvent("appearmagic");
            ENTITY_GENERIC_ROPING = createSoundEvent("roping");
            ENTITY_GENERIC_TRANSFORM = createSoundEvent("transform");
            ENTITY_GENERIC_TUD = createSoundEvent("tud"); // TODO
            ENTITY_GENERIC_VANISH = createSoundEvent("vanish");
            ENTITY_GENERIC_WHIP = createSoundEvent("whip");
            ENTITY_GENERIC_WINGFLAP = createSoundEvent("wingflap");
            ENTITY_GOAT_AMBIENT = createSoundEvent("goatgrunt");
            ENTITY_GOAT_AMBIENT_BABY = createSoundEvent("goatkid");
            ENTITY_GOAT_AMBIENT_FEMALE = createSoundEvent("goatfemale");
            ENTITY_GOAT_DEATH = createSoundEvent("goatdying");
            ENTITY_GOAT_HURT = createSoundEvent("goathurt");
            ENTITY_GOAT_DIGG = createSoundEvent("goatdigg");
            ENTITY_GOAT_EATING = createSoundEvent("goateating");
            ENTITY_GOAT_SMACK = createSoundEvent("goatsmack");
            ENTITY_GOLEM_AMBIENT = createSoundEvent("golemgrunt");
            ENTITY_GOLEM_ATTACH = createSoundEvent("golemattach");
            ENTITY_GOLEM_DYING = createSoundEvent("golemdying");
            ENTITY_GOLEM_EXPLODE = createSoundEvent("golemexplode");
            ENTITY_GOLEM_HURT = createSoundEvent("golemhurt");
            ENTITY_GOLEM_SHOOT = createSoundEvent("golemshoot");
            ENTITY_GOLEM_WALK = createSoundEvent("golemwalk");
            ENTITY_HORSE_MAD = createSoundEvent("horsemad");
            ENTITY_HORSE_AMBIENT = createSoundEvent("horsegrunt");
            //ENTITY_HORSE_AMBIENT_DONKEY = createSoundEvent("horsegrunt"); // TODO
            ENTITY_HORSE_AMBIENT_GHOST = createSoundEvent("horsegruntghost");
            ENTITY_HORSE_AMBIENT_UNDEAD = createSoundEvent("horsegruntundead");
            ENTITY_HORSE_AMBIENT_ZEBRA = createSoundEvent("zebragrunt");
            //ENTITY_HORSE_ANGRY = createSoundEvent("horsemad");
            //ENTITY_HORSE_ANGRY_DONKEY = createSoundEvent("donkeyhurt");
            ENTITY_HORSE_ANGRY_GHOST = createSoundEvent("horsemadghost");
            ENTITY_HORSE_ANGRY_UNDEAD = createSoundEvent("horsemadundead");
            //ENTITY_HORSE_ANGRY_ZEBRA = createSoundEvent("zebrahurt");
            ENTITY_HORSE_DEATH = createSoundEvent("horsedying");
            ENTITY_HORSE_DEATH_DONKEY = createSoundEvent("donkeydying");
            ENTITY_HORSE_DEATH_GHOST = createSoundEvent("horsedyingghost");
            ENTITY_HORSE_DEATH_UNDEAD = createSoundEvent("horsedyingundead");
            ENTITY_HORSE_HURT = createSoundEvent("horsehurt");
            ENTITY_HORSE_HURT_DONKEY = createSoundEvent("donkeyhurt");
            ENTITY_HORSE_HURT_GHOST = createSoundEvent("horsehurtghost");
            ENTITY_HORSE_HURT_UNDEAD = createSoundEvent("horsehurtundead");
            ENTITY_HORSE_HURT_ZEBRA = createSoundEvent("zebrahurt");
            ENTITY_KITTY_AMBIENT = createSoundEvent("kittygrunt");
            ENTITY_KITTY_AMBIENT_BABY = createSoundEvent("kittengrunt");
            ENTITY_KITTY_ANGRY = createSoundEvent("kittyupset");
            ENTITY_KITTY_DEATH = createSoundEvent("kittydying");
            ENTITY_KITTY_DEATH_BABY = createSoundEvent("kittendying");
            ENTITY_KITTY_DRINKING = createSoundEvent("kittyeatingm");
            ENTITY_KITTY_EATING = createSoundEvent("kittyfood");
            ENTITY_KITTY_HUNGRY = createSoundEvent("kittyeatingf");
            ENTITY_KITTY_HURT = createSoundEvent("kittyhurt");
            ENTITY_KITTY_HURT_BABY = createSoundEvent("kittenhurt");
            ENTITY_KITTY_LITTER = createSoundEvent("kittylitter"); // TODO
            ENTITY_KITTY_PURR = createSoundEvent("kittypurr");
            ENTITY_KITTY_TRAPPED = createSoundEvent("kittytrapped");
            ENTITY_KITTYBED_POURINGFOOD = createSoundEvent("pouringfood");
            ENTITY_KITTYBED_POURINGMILK = createSoundEvent("pouringmilk");
            ENTITY_LION_AMBIENT = createSoundEvent("liongrunt");
            ENTITY_LION_AMBIENT_BABY = createSoundEvent("cubgrunt");
            ENTITY_LION_DEATH = createSoundEvent("liondeath");
            ENTITY_LION_DEATH_BABY = createSoundEvent("cubdying");
            ENTITY_LION_HURT = createSoundEvent("lionhurt");
            ENTITY_LION_HURT_BABY = createSoundEvent("cubhurt");
            ENTITY_MOUSE_AMBIENT = createSoundEvent("micegrunt");
            ENTITY_MOUSE_DEATH = createSoundEvent("micedying");
            ENTITY_MOUSE_HURT = createSoundEvent("micehurt"); // TODO
            ENTITY_OGRE_AMBIENT = createSoundEvent("ogre");
            ENTITY_OGRE_DEATH = createSoundEvent("ogredying");
            ENTITY_OGRE_HURT = createSoundEvent("ogrehurt");
            ENTITY_OSTRICH_AMBIENT = createSoundEvent("ostrichgrunt");
            ENTITY_OSTRICH_AMBIENT_BABY = createSoundEvent("ostrichchick");
            ENTITY_OSTRICH_DEATH = createSoundEvent("ostrichdying");
            ENTITY_OSTRICH_HURT = createSoundEvent("ostrichhurt");
            ENTITY_RABBIT_DEATH = createSoundEvent("rabbitdeath");
            ENTITY_RABBIT_HURT = createSoundEvent("rabbithurt");
            ENTITY_RABBIT_LIFT = createSoundEvent("rabbitlift");
            ENTITY_RACCOON_AMBIENT = createSoundEvent("raccoongrunt");
            ENTITY_RACCOON_DEATH = createSoundEvent("raccoondying");
            ENTITY_RACCOON_HURT = createSoundEvent("raccoonhurt");
            ENTITY_RAT_AMBIENT = createSoundEvent("ratgrunt");
            ENTITY_RAT_DEATH = createSoundEvent("ratdying");
            ENTITY_RAT_HURT = createSoundEvent("rathurt");
            ENTITY_SCORPION_AMBIENT = createSoundEvent("scorpiongrunt");
            ENTITY_SCORPION_CLAW = createSoundEvent("scorpionclaw");
            ENTITY_SCORPION_DEATH = createSoundEvent("scorpiondying");
            ENTITY_SCORPION_HURT = createSoundEvent("scorpionhurt");
            ENTITY_SCORPION_STING = createSoundEvent("scorpionsting");
            ENTITY_SNAKE_AMBIENT = createSoundEvent("snakehiss");
            ENTITY_SNAKE_ANGRY = createSoundEvent("snakeupset");
            ENTITY_SNAKE_DEATH = createSoundEvent("snakedying");
            ENTITY_SNAKE_HURT = createSoundEvent("snakehurt");
            ENTITY_SNAKE_RATTLE = createSoundEvent("snakerattle");
            ENTITY_SNAKE_SNAP = createSoundEvent("snakesnap");
            ENTITY_SNAKE_SWIM = createSoundEvent("snakeswim");
            ENTITY_TURKEY_AMBIENT = createSoundEvent("turkey");
           // ENTITY_TURKEY_DEATH = createSoundEvent("turkeyhurt");
            ENTITY_TURKEY_HURT = createSoundEvent("turkeyhurt");
            ENTITY_TURTLE_AMBIENT = createSoundEvent("turtlegrunt"); // TODO
            ENTITY_TURTLE_ANGRY = createSoundEvent("turtlehissing");
            ENTITY_TURTLE_DEATH = createSoundEvent("turtledying");
            ENTITY_TURTLE_EATING = createSoundEvent("turtleeating");
            ENTITY_TURTLE_HURT = createSoundEvent("turtlehurt");
            ENTITY_WEREWOLF_AMBIENT_HUMAN = createSoundEvent("werehumangrunt"); // TODO
            ENTITY_WEREWOLF_DEATH_HUMAN = createSoundEvent("werehumandying");
            ENTITY_WEREWOLF_HURT_HUMAN = createSoundEvent("werehumanhurt");
            ENTITY_WEREWOLF_AMBIENT = createSoundEvent("werewolfgrunt");
            ENTITY_WEREWOLF_DEATH = createSoundEvent("werewolfdying");
            ENTITY_WEREWOLF_HURT = createSoundEvent("werewolfhurt");
            ENTITY_WEREWOLF_TRANSFORM = createSoundEvent("weretransform");
            ENTITY_WOLF_AMBIENT = createSoundEvent("wolfgrunt");
            ENTITY_WOLF_DEATH = createSoundEvent("wolfdeath");
            ENTITY_WOLF_HURT = createSoundEvent("wolfhurt");
            ENTITY_WRAITH_AMBIENT = createSoundEvent("wraith");
            ENTITY_WRAITH_DEATH = createSoundEvent("wraithdying");
            ENTITY_WRAITH_HURT = createSoundEvent("wraithhurt");
            ENTITY_WYVERN_AMBIENT = createSoundEvent("wyverngrunt");
            ENTITY_WYVERN_DEATH = createSoundEvent("wyverndying");
            ENTITY_WYVERN_HURT = createSoundEvent("wyvernhurt");
            ENTITY_WYVERN_WINGFLAP = createSoundEvent("wyvernwingflap");
            ITEM_RECORD_SHUFFLING = createSoundEvent("shuffling");
        }
    }
}