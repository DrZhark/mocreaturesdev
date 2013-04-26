package drzhark.mocreatures.client.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.client.gui.MoCGUIEntityNamer;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import drzhark.mocreatures.entity.MoCIMoCreature;
import drzhark.mocreatures.entity.monster.MoCEntityGolem;
import drzhark.mocreatures.entity.monster.MoCEntityOgre;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;

public class MoCClientPacketHandler implements IPacketHandler {

    /**
     * 
     * Handles packets received on the client, from the server
     */
    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player playerEntity)
    {
        if (packet.channel.equals("MoCreatures"))
        {

            DataInputStream dataStream = new DataInputStream(new ByteArrayInputStream(packet.data));

            try
            {
                int packetID = dataStream.readInt();

                if (packetID == 1) // client receives status packet. first packet int: 1 then an int for the state to apply
                {
                    int applyState = dataStream.readInt();

                    //TODO 4FIX!
                    if (applyState == 1)
                    {
                        //MoCTools.burnPlayer(MoCClientProxy.mc.thePlayer);
                    }
                    else if (applyState == 2)
                    {
                        //MoCTools.freezePlayer(MoCClientProxy.mc.thePlayer);
                    }
                    else if (applyState == 3)
                    {
                        //MoCTools.poisonPlayer(MoCClientProxy.mc.thePlayer);
                    }
                    return;
                }
                if (packetID == 2) // client receives horse transform command packet. set the horse to transform to a specific type
                {
                    int entID = dataStream.readInt();
                    int tType = dataStream.readInt();

                    List<Entity> entList = MoCClientProxy.mc.thePlayer.worldObj.loadedEntityList;
                    for (Entity ent : entList)
                    {
                        if (ent.entityId == entID && ent instanceof MoCEntityHorse)
                        {
                            //((MoCEntityHorse)ent).transformType = tType;
                            ((MoCEntityHorse) ent).transform(tType);
                            break;
                        }
                    }
                    return;
                }

                if (packetID == 3) // client receives horse appear command packet. shows the fx
                {
                    int entID = dataStream.readInt();
                    List<Entity> entList = MoCClientProxy.mc.thePlayer.worldObj.loadedEntityList;
                    for (Entity ent : entList)
                    {
                        if (ent.entityId == entID && ent instanceof MoCEntityHorse)
                        {
                            ((MoCEntityHorse) ent).MaterializeFX();
                            break;
                        }
                    }
                    return;
                }
                if (packetID == 4) // client receives horse vanish command packet. starts the vanish sequence 
                {
                    int entID = dataStream.readInt();
                    List<Entity> entList = MoCClientProxy.mc.thePlayer.worldObj.loadedEntityList;
                    for (Entity ent : entList)
                    {
                        if (ent.entityId == entID && ent instanceof MoCEntityHorse)
                        {
                            //((MoCEntityHorse)ent).vanishHorse();
                            ((MoCEntityHorse) ent).setVanishC((byte) 1);
                            break;
                        }
                    }
                    return;
                }
                if (packetID == 5) // client receives ogre explode packet, starts destroying ogre sequence for Fx! 
                {
                    int entID = dataStream.readInt();
                    List<Entity> entList = MoCClientProxy.mc.thePlayer.worldObj.loadedEntityList;
                    for (Entity ent : entList)
                    {
                        if (ent.entityId == entID && ent instanceof MoCEntityOgre)
                        {
                            //((MoCEntityHorse)ent).vanishHorse();
                            ((MoCEntityOgre) ent).DestroyingOgre();
                            break;
                        }
                    }
                    return;
                }
                if (packetID == 7) // client receives GUI Namer packet command.
                {
                    int EntityID = dataStream.readInt();
                    List<Entity> entList = MoCClientProxy.mc.thePlayer.worldObj.loadedEntityList;
                    for (Entity ent : entList)
                    {
                        if (ent.entityId == EntityID && ent instanceof MoCIMoCreature)
                        {
                            MoCClientProxy.mc.displayGuiScreen(new MoCGUIEntityNamer(((MoCIMoCreature) ent), ((MoCIMoCreature) ent).getName()));
                            break;
                        }
                    }
                    return;
                    //MoCClientProxy.setNameID(EntityID);

                }
                if (packetID == 8) // client receives zebra shuffle command. invokes the .shuffle() method to restart animation
                {
                    int entID = dataStream.readInt();
                    List<Entity> entList = MoCClientProxy.mc.thePlayer.worldObj.loadedEntityList;
                    for (Entity ent : entList)
                    {
                        if (ent.entityId == entID && ent instanceof MoCEntityHorse)
                        {
                            ((MoCEntityHorse) ent).shuffle();
                            break;
                        }
                    }
                    return;
                }

                if (packetID == 9) // client receives zebra stop shuffle command. resets the shuffle counter
                {
                    int entID = dataStream.readInt();
                    List<Entity> entList = MoCClientProxy.mc.thePlayer.worldObj.loadedEntityList;
                    for (Entity ent : entList)
                    {
                        if (ent.entityId == entID && ent instanceof MoCEntityHorse)
                        {
                            ((MoCEntityHorse) ent).shuffleCounter = 0;
                            break;
                        }
                    }
                    return;
                }

                if (packetID == 10) // client receives heart animation command
                {
                    int entID = dataStream.readInt();
                    List<Entity> entList = MoCClientProxy.mc.thePlayer.worldObj.loadedEntityList;
                    for (Entity ent : entList)
                    {
                        if (ent.entityId == entID && ent instanceof MoCEntityAnimal)
                        {
                            ((MoCEntityAnimal) ent).SpawnHeart();
                            break;
                        }
                    }
                    return;
                }

                if (packetID == 11) // client receives attack animation command
                {
                    int entID = dataStream.readInt();
                    int attackType = dataStream.readInt();
                    List<Entity> entList = MoCClientProxy.mc.thePlayer.worldObj.loadedEntityList;
                    for (Entity ent : entList)
                    {
                        if (ent.entityId == entID && ent instanceof MoCIMoCreature)
                        {
                            ((MoCIMoCreature) ent).performAnimation(attackType);
                            break;
                        }
                    }
                    return;
                }

                if (packetID == 12) // client receives health update
                {
                    int entID = dataStream.readInt();
                    int health = dataStream.readInt();
                    List<Entity> entList = MoCClientProxy.mc.thePlayer.worldObj.loadedEntityList;
                    for (Entity ent : entList)
                    {
                        if (ent.entityId == entID && ent instanceof EntityLiving)
                        {
                            ((EntityLiving) ent).setEntityHealth(health);
                            break;
                        }
                    }
                    return;
                }
                if (packetID == 13) // client receives attachEntity update from forceDataSync
                {
                    int sourceId = dataStream.readInt();
                    int targetId = dataStream.readInt();
                    Object var2 = MoCClientProxy.mc.thePlayer.worldObj.getEntityByID(sourceId);
                    Entity var3 = MoCClientProxy.mc.thePlayer.worldObj.getEntityByID(targetId);

                    if (var2 != null)
                    {
                        ((Entity) var2).mountEntity(var3);
                    }
                    return;
                }

                if (packetID == 14) // client receives the sync twobytes packet - used for MoCEntityG
                {
                    int targetId = dataStream.readInt();
                    byte slot = dataStream.readByte();
                    byte value = dataStream.readByte();
                    Entity ent = MoCClientProxy.mc.thePlayer.worldObj.getEntityByID(targetId);
                    if (ent != null && ent instanceof MoCEntityGolem)
                    {
                        ((MoCEntityGolem) ent).saveGolemCube(slot, value);
                    }
                    return;
                }

                if (packetID == 15) // client receives msg to be displayed on screen
                {
                    String msg = dataStream.readUTF();
                    Minecraft.getMinecraft().thePlayer.addChatMessage(msg);
                    return;
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

    }

    /**
     * Packets generated client side
     * 
     */

    //-----------client side
    public static void sendNameInfo(int entityId, String nameToSet)
    {
        // client sending name info. int entityID and string name

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream data = new DataOutputStream(bytes);
        try
        {
            data.writeInt(Integer.valueOf(20));
            data.writeInt(entityId);
            data.writeUTF(nameToSet);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = "MoCreatures";
        packet.data = bytes.toByteArray();
        packet.length = packet.data.length;
        EntityClientPlayerMP player = MoCClientProxy.mc.thePlayer;
        player.sendQueue.addToSendQueue(packet);

        //EntityClientPlayerMP thePlayerMP = (EntityClientPlayerMP)player;
        //thePlayerMP.sendQueue.addToSendQueue(packet);

    }

    public static void sendEntityJumpPacket()
    {
        // client sending horse jump command. 

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream data = new DataOutputStream(bytes);
        try
        {
            data.writeInt(Integer.valueOf(21));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = "MoCreatures";
        packet.data = bytes.toByteArray();
        packet.length = packet.data.length;

        EntityClientPlayerMP player = MoCClientProxy.mc.thePlayer;
        //System.out.println("MoCClientPacketHandler sending packet 21 to player " + player.toString());
        player.sendQueue.addToSendQueue(packet);
    }

    public static void sendInstaSpawnPacket(int ID, int number)
    {
        // client sending insta spawn command. int ID, int number

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream data = new DataOutputStream(bytes);
        try
        {
            data.writeInt(Integer.valueOf(22));
            data.writeInt(Integer.valueOf(ID));
            data.writeInt(Integer.valueOf(number));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = "MoCreatures";
        packet.data = bytes.toByteArray();
        packet.length = packet.data.length;

        EntityClientPlayerMP player = MoCClientProxy.mc.thePlayer;
        player.sendQueue.addToSendQueue(packet);
    }
    
    public static void sendEntityDivePacket()
    {
        // client sending entity dive command. 

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream data = new DataOutputStream(bytes);
        try
        {
            data.writeInt(Integer.valueOf(24));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = "MoCreatures";
        packet.data = bytes.toByteArray();
        packet.length = packet.data.length;

        EntityClientPlayerMP player = MoCClientProxy.mc.thePlayer;
        player.sendQueue.addToSendQueue(packet);
    }

	public static void sendEntityDismountPacket() 
	{
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream data = new DataOutputStream(bytes);
        try
        {
            data.writeInt(Integer.valueOf(25));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = "MoCreatures";
        packet.data = bytes.toByteArray();
        packet.length = packet.data.length;

        EntityClientPlayerMP player = MoCClientProxy.mc.thePlayer;
        player.sendQueue.addToSendQueue(packet);
		
	}
	
	/**
	 * New instaSpawn method with (key) name of entity and number to spawn
	 * @param name
	 * @param number
	 */
	public static void sendInstaSpawnPacket(String name, int number)
    {
        // client sending insta spawn command. int ID, int number

		if (name.isEmpty() || number < 1)
		{
			return;
		}
		
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        DataOutputStream data = new DataOutputStream(bytes);
        try
        {
            data.writeInt(Integer.valueOf(26));
            data.writeUTF(name);
            data.writeInt(Integer.valueOf(number));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = "MoCreatures";
        packet.data = bytes.toByteArray();
        packet.length = packet.data.length;

        EntityClientPlayerMP player = MoCClientProxy.mc.thePlayer;
        player.sendQueue.addToSendQueue(packet);
    }
}
