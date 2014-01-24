package drzhark.mocreatures.network.packet;

import java.util.List;

import drzhark.mocreatures.MoCPetData;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.client.gui.helpers.MoCGUIEntityNamer;
import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.IMoCTameable;
import drzhark.mocreatures.network.AbstractPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;


/**
 * MoCPacketNameGUI class.
 * @author blood
 */
public class MoCPacketNameGUI extends AbstractPacket {

    String name = "";
    int entityId;

    public MoCPacketNameGUI() {}

    public MoCPacketNameGUI(int entityId)
    {
        this.entityId = entityId;
    }

    public MoCPacketNameGUI(int entityId, String name)
    {
        this.entityId = entityId;
        this.name = name;
    }

    /**
     * Encode the packet data into the ByteBuf stream. Complex data sets may need specific data handlers (See @link{cpw.mods.fml.common.network.ByteBuffUtils})
     *
     * @param ctx    channel context
     * @param buffer the buffer to encode into
     */
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        buffer.writeShort(this.name.length());
        for (char c : this.name.toCharArray())
        {
            buffer.writeChar(c);
        }
        buffer.writeInt(this.entityId);
    }

    /**
     * Decode the packet data from the ByteBuf stream. Complex data sets may need specific data handlers (See @link{cpw.mods.fml.common.network.ByteBuffUtils})
     *
     * @param ctx    channel context
     * @param buffer the buffer to decode from
     */
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        short len = buffer.readShort();
        char[] chars = new char[len];
        for (int i = 0; i < len; i++)
        {
            chars[i] = buffer.readChar();
        }
        this.name = String.valueOf(chars);
        entityId = buffer.readInt();
    }

    /**
     * Handle a packet on the client side. Note this occurs after decoding has completed.
     *
     * @param player the player reference
     */
    public void handleClientSide(EntityPlayer player)
    {
        List<Entity> entList = MoCClientProxy.mc.thePlayer.worldObj.loadedEntityList;
        for (Entity ent : entList)
        {
            if (ent.getEntityId() == this.entityId && ent instanceof IMoCEntity)
            {
                MoCClientProxy.mc.displayGuiScreen(new MoCGUIEntityNamer(((IMoCEntity) ent), ((IMoCEntity) ent).getName()));
                break;
            }
        }
    }

    /**
     * Handle a packet on the server side. Note this occurs after decoding has completed.
     *
     * @param player the player reference
     */
    public void handleServerSide(EntityPlayer player)
    {
        System.out.println("handleServerSide MoCPacketNameGUI name = " + name);
        Entity pet = null;
        List<Entity> entList = player.worldObj.loadedEntityList;
        String ownerName = "";

        for (Entity ent : entList)
        {
            if (ent.getEntityId() == entityId && ent instanceof IMoCTameable)
            {
                ((IMoCEntity) ent).setName(this.name);
                ownerName = ((IMoCEntity) ent).getOwnerName();
                pet = ent;
                break;
            }
        }
        // update petdata
        MoCPetData petData = MoCreatures.instance.mapData.getPetData(ownerName);
        if (petData != null && pet != null && ((IMoCTameable)pet).getOwnerPetId() != -1)
        {
            int id = ((IMoCTameable)pet).getOwnerPetId();
            NBTTagList tag = petData.getPetData().getTagList("TamedList", 10);
            for (int i = 0; i < tag.tagCount(); i++)
            {
                NBTTagCompound nbt = (NBTTagCompound)tag.getCompoundTagAt(i);
                if (nbt.getInteger("PetId") == id)
                {
                    nbt.setString("Name", name);
                    ((IMoCTameable)pet).setName(name);
                }
            }
        }
    }
}