package drzhark.mocreatures.network.message;

import java.util.List;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import drzhark.mocreatures.MoCPetData;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.client.gui.helpers.MoCGUIEntityNamer;
import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.IMoCTameable;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;


public class MoCMessageUpdatePetName implements IMessage, IMessageHandler<MoCMessageUpdatePetName, IMessage> {

    String name;
    int entityId;

    public MoCMessageUpdatePetName() {}

    public MoCMessageUpdatePetName(int entityId)
    {
        this.entityId = entityId;
    }

    public MoCMessageUpdatePetName(int entityId, String name)
    {
        this.entityId = entityId;
        this.name = name;
    }

    @Override
    public void toBytes(ByteBuf buffer)
    {
        buffer.writeInt(this.name.length());
        buffer.writeBytes(this.name.getBytes());
        buffer.writeInt(this.entityId);
    }

    @Override
    public void fromBytes(ByteBuf buffer)
    {
        int nameLength = buffer.readInt();
        this.name = new String(buffer.readBytes(nameLength).array());
        this.entityId = buffer.readInt();
    }

    @Override
    public IMessage onMessage(MoCMessageUpdatePetName message, MessageContext ctx)
    {
        Entity pet = null;
        List<Entity> entList = ctx.getServerHandler().playerEntity.worldObj.loadedEntityList;
        String ownerName = "";

        for (Entity ent : entList)
        {
            if (ent.getEntityId() == message.entityId && ent instanceof IMoCTameable)
            {
                ((IMoCEntity) ent).setName(message.name);
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
            NBTTagList tag = petData.getOwnerRootNBT().getTagList("TamedList", 10);
            for (int i = 0; i < tag.tagCount(); i++)
            {
                NBTTagCompound nbt = (NBTTagCompound)tag.getCompoundTagAt(i);
                if (nbt.getInteger("PetId") == id)
                {
                    nbt.setString("Name", message.name);
                    ((IMoCTameable)pet).setName(message.name);
                }
            }
        }
        return null;
    }

    @Override
    public String toString()
    {
        return String.format("MoCMessageUpdatePetName - entityId:%s, name:%s", this.entityId, this.name);
    }
}