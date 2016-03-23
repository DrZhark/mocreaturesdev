package drzhark.mocreatures.network.message;

import drzhark.mocreatures.network.MoCMessageHandler;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MoCMessageShuffle implements IMessage, IMessageHandler<MoCMessageShuffle, IMessage> {

    public int entityId;
    public boolean flag;

    public MoCMessageShuffle() {
    }

    public MoCMessageShuffle(int entityId, boolean flag) {
        this.entityId = entityId;
        this.flag = flag;
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        buffer.writeInt(this.entityId);
        buffer.writeBoolean(this.flag);
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        this.entityId = buffer.readInt();
        this.flag = buffer.readBoolean();
    }

    @Override
    public IMessage onMessage(MoCMessageShuffle message, MessageContext ctx) {
        MoCMessageHandler.handleMessage(message, ctx);
        return null;
    }

    @Override
    public String toString() {
        return String.format("MoCMessageShuffle - entityId:%s, flag:%s", this.entityId, this.flag);
    }
}
