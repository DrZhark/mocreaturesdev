package drzhark.mocreatures.network.packet;

import java.util.List;

import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.network.AbstractPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;

public class MoCPacketHealth extends AbstractPacket {

    private int entityId;
    private float health;

    public MoCPacketHealth() {}

    public MoCPacketHealth(int entityId, float health)
    {
        this.entityId = entityId;
        this.health = health;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        buffer.writeInt(this.entityId);
        buffer.writeFloat(health);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        this.entityId = buffer.readInt();
        this.health = buffer.readFloat();
    }

    @Override
    public void handleClientSide(EntityPlayer player)
    {
        List<Entity> entList = MoCClientProxy.mc.thePlayer.worldObj.loadedEntityList;
        for (Entity ent : entList)
        {
            if (ent.getEntityId() == this.entityId && ent instanceof EntityLiving)
            {
                ((EntityLiving) ent).setHealth(this.health);
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
