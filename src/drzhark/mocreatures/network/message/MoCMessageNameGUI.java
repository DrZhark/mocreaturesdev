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

public class MoCMessageNameGUI implements IMessage, IMessageHandler<MoCMessageNameGUI, IMessage> {

    int entityId;

    public MoCMessageNameGUI() {}

    public MoCMessageNameGUI(int entityId)
    {
        this.entityId = entityId;
    }

    @Override
    public void toBytes(ByteBuf buffer)
    {
        buffer.writeInt(this.entityId);
    }

    @Override
    public void fromBytes(ByteBuf buffer)
    {
        this.entityId = buffer.readInt();
    }

    @Override
    public IMessage onMessage(MoCMessageNameGUI message, MessageContext ctx)
    {
        if (ctx.side == Side.CLIENT)
        {
            handleClientMessage(message, ctx);
        }
        return null;
    }

    @SideOnly(Side.CLIENT)
    public void handleClientMessage(MoCMessageNameGUI message, MessageContext ctx)
    {
        List<Entity> entList = MoCClientProxy.mc.thePlayer.worldObj.loadedEntityList;
        for (Entity ent : entList)
        {
            if (ent.getEntityId() == message.entityId && ent instanceof IMoCEntity)
            {
                MoCClientProxy.mc.displayGuiScreen(new MoCGUIEntityNamer(((IMoCEntity) ent), ((IMoCEntity) ent).getName()));
                break;
            }
        }
    }

    @Override
    public String toString()
    {
        return String.format("MoCMessageNameGUI - entityId:%s", this.entityId);
    }
}