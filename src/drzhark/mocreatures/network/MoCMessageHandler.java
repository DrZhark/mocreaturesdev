package drzhark.mocreatures.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import drzhark.mocreatures.network.message.MoCMessageAppear;
import drzhark.mocreatures.network.message.MoCMessageAttachedEntity;
import drzhark.mocreatures.network.message.MoCMessageEntityDive;
import drzhark.mocreatures.network.message.MoCMessageEntityJump;
import drzhark.mocreatures.network.message.MoCMessageExplode;
import drzhark.mocreatures.network.message.MoCMessageHealth;
import drzhark.mocreatures.network.message.MoCMessageHeart;
import drzhark.mocreatures.network.message.MoCMessageInstaSpawn;
import drzhark.mocreatures.network.message.MoCMessageNameGUI;
import drzhark.mocreatures.network.message.MoCMessageShuffle;
import drzhark.mocreatures.network.message.MoCMessageTwoBytes;
import drzhark.mocreatures.network.message.MoCMessageUpdatePetName;
import drzhark.mocreatures.network.message.MoCMessageVanish;

public class MoCMessageHandler
{
    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("MoCreatures");

    public static void init()
    {
        INSTANCE.registerMessage(MoCMessageAnimation.class, MoCMessageAnimation.class, 0, Side.CLIENT);
        INSTANCE.registerMessage(MoCMessageAppear.class, MoCMessageAppear.class, 1, Side.CLIENT);
        INSTANCE.registerMessage(MoCMessageAttachedEntity.class, MoCMessageAttachedEntity.class, 2, Side.CLIENT);
        INSTANCE.registerMessage(MoCMessageEntityDive.class, MoCMessageEntityDive.class, 3, Side.SERVER);
        INSTANCE.registerMessage(MoCMessageEntityJump.class, MoCMessageEntityJump.class, 4, Side.SERVER);
        INSTANCE.registerMessage(MoCMessageExplode.class, MoCMessageExplode.class, 5, Side.CLIENT);
        INSTANCE.registerMessage(MoCMessageHealth.class, MoCMessageHealth.class, 6, Side.CLIENT);
        INSTANCE.registerMessage(MoCMessageHeart.class, MoCMessageHeart.class, 7, Side.CLIENT);
        INSTANCE.registerMessage(MoCMessageInstaSpawn.class, MoCMessageInstaSpawn.class, 8, Side.SERVER);
        INSTANCE.registerMessage(MoCMessageNameGUI.class, MoCMessageNameGUI.class, 9, Side.CLIENT);
        INSTANCE.registerMessage(MoCMessageUpdatePetName.class, MoCMessageUpdatePetName.class, 10, Side.SERVER);
        INSTANCE.registerMessage(MoCMessageShuffle.class, MoCMessageShuffle.class, 11, Side.CLIENT);
        INSTANCE.registerMessage(MoCMessageTwoBytes.class, MoCMessageTwoBytes.class, 12, Side.CLIENT);
        INSTANCE.registerMessage(MoCMessageVanish.class, MoCMessageVanish.class, 13, Side.CLIENT);
    }
}