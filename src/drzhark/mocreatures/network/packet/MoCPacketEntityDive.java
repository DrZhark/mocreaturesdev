package drzhark.mocreatures.network.packet;

import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.network.AbstractPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

public class MoCPacketEntityDive extends AbstractPacket {

    public MoCPacketEntityDive() {}

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void handleClientSide(EntityPlayer player)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void handleServerSide(EntityPlayer player)
    {
        if (player.ridingEntity != null && player.ridingEntity instanceof IMoCEntity)
        {
            ((IMoCEntity) player.ridingEntity).makeEntityDive();

        }
    }

}
