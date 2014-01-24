package drzhark.mocreatures.network.packet;

import java.util.List;

import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.network.AbstractPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class MoCPacketVanish extends AbstractPacket {

    private int entityId;

    public MoCPacketVanish() {}

    public MoCPacketVanish(int entityId)
    {
        this.entityId = entityId;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        buffer.writeInt(this.entityId);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        this.entityId = buffer.readInt();
    }

    @Override
    public void handleClientSide(EntityPlayer player)
    {
        List<Entity> entList = MoCClientProxy.mc.thePlayer.worldObj.loadedEntityList;
        for (Entity ent : entList)
        {
            if (ent.getEntityId() == this.entityId && ent instanceof MoCEntityHorse)
            {
                ((MoCEntityHorse) ent).setVanishC((byte) 1);
                break;
            }
        }
    }

    @Override
    public void handleServerSide(EntityPlayer player)
    {
        // TODO Auto-generated method stub
        
    }

}
