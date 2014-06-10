package drzhark.mocreatures.network.message;

import java.util.List;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.entity.IMoCEntity;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

public class MoCMessageEntityDive implements IMessage, IMessageHandler<MoCMessageEntityDive, IMessage> {

    public MoCMessageEntityDive() {}

    @Override
    public void toBytes(ByteBuf buffer)
    {
    }

    @Override
    public void fromBytes(ByteBuf buffer)
    {
    }

    @Override
    public IMessage onMessage(MoCMessageEntityDive message, MessageContext ctx)
    {
        if (ctx.getServerHandler().playerEntity.ridingEntity != null && ctx.getServerHandler().playerEntity.ridingEntity instanceof IMoCEntity)
        {
            ((IMoCEntity) ctx.getServerHandler().playerEntity.ridingEntity).makeEntityDive();
        }
        return null;
    }
}