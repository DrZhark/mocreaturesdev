package drzhark.mocreatures.network.message;

import java.util.List;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.entity.monster.MoCEntityOgre;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class MoCMessageExplode implements IMessage, IMessageHandler<MoCMessageExplode, IMessage> {

    private int entityId;

    public MoCMessageExplode() {}

    public MoCMessageExplode(int entityId)
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
    public IMessage onMessage(MoCMessageExplode message, MessageContext ctx)
    {
        List<Entity> entList = MoCClientProxy.mc.thePlayer.worldObj.loadedEntityList;
        for (Entity ent : entList)
        {
            if (ent.getEntityId() == message.entityId && ent instanceof MoCEntityOgre)
            {
                ((MoCEntityOgre) ent).DestroyingOgre();
                break;
            }
        }
        return null;
    }

    @Override
    public String toString()
    {
        return String.format("MoCMessageExplode - entityId:%s", this.entityId);
    }
}