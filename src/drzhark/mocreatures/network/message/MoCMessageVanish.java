package drzhark.mocreatures.network.message;

import java.util.List;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class MoCMessageVanish implements IMessage, IMessageHandler<MoCMessageVanish, IMessage> {

    private int entityId;

    public MoCMessageVanish() {}

    public MoCMessageVanish(int entityId)
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
    public IMessage onMessage(MoCMessageVanish message, MessageContext ctx)
    {
        List<Entity> entList = MoCClientProxy.mc.thePlayer.worldObj.loadedEntityList;
        for (Entity ent : entList)
        {
            if (ent.getEntityId() == message.entityId && ent instanceof MoCEntityHorse)
            {
                ((MoCEntityHorse) ent).setVanishC((byte) 1);
                break;
            }
        }
        return null;
    }

    @Override
    public String toString()
    {
        return String.format("MoCMessageVanish - entityId:%s", this.entityId);
    }
}