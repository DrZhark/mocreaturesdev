package drzhark.mocreatures.network.message;

import drzhark.mocreatures.entity.IMoCEntity;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MoCMessageEntityDive implements IMessage, IMessageHandler<MoCMessageEntityDive, IMessage> {

    public MoCMessageEntityDive() {
    }

    @Override
    public void toBytes(ByteBuf buffer) {
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
    }

    @Override
    public IMessage onMessage(MoCMessageEntityDive message, MessageContext ctx) {
        if (ctx.getServerHandler().playerEntity.ridingEntity != null && ctx.getServerHandler().playerEntity.ridingEntity instanceof IMoCEntity) {
            ((IMoCEntity) ctx.getServerHandler().playerEntity.ridingEntity).makeEntityDive();
        }
        return null;
    }
}
