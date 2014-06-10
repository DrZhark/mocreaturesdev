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

public class MoCMessageShuffle implements IMessage, IMessageHandler<MoCMessageShuffle, IMessage> {

    private int entityId;
    private boolean flag;

    public MoCMessageShuffle() {}

    public MoCMessageShuffle(int entityId, boolean flag)
    {
        this.entityId = entityId;
        this.flag = flag;
    }

    @Override
    public void toBytes(ByteBuf buffer)
    {
        buffer.writeInt(this.entityId);
        buffer.writeBoolean(flag);
    }

    @Override
    public void fromBytes(ByteBuf buffer)
    {
        this.entityId = buffer.readInt();
        this.flag = buffer.readBoolean();
    }

    @Override
    public IMessage onMessage(MoCMessageShuffle message, MessageContext ctx)
    {
        List<Entity> entList = MoCClientProxy.mc.thePlayer.worldObj.loadedEntityList;
        for (Entity ent : entList)
        {
            if (ent.getEntityId() == message.entityId && ent instanceof MoCEntityHorse)
            {
                if (flag)
                {
                    ((MoCEntityHorse) ent).shuffle();
                }
                else
                {
                    ((MoCEntityHorse) ent).shuffleCounter = 0;
                }
                break;
            }
        }
        return null;
    }

    @Override
    public String toString()
    {
        return String.format("MoCMessageShuffle - entityId:%s, flag:%s", this.entityId, this.flag);
    }
}