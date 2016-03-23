package drzhark.mocreatures.network.message;

import drzhark.mocreatures.network.MoCMessageHandler;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MoCMessageAnimation implements IMessage, IMessageHandler<MoCMessageAnimation, IMessage> {

    public int entityId;
    public int animationType;

    public MoCMessageAnimation() {
    }

    public MoCMessageAnimation(int entityId, int animationType) {
        this.entityId = entityId;
        this.animationType = animationType;
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        buffer.writeInt(this.entityId);
        buffer.writeInt(this.animationType);
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        this.entityId = buffer.readInt();
        this.animationType = buffer.readInt();
    }

    @Override
    public IMessage onMessage(MoCMessageAnimation message, MessageContext ctx) {
        MoCMessageHandler.handleMessage(message, ctx);
        return null;
    }

    @Override
    public String toString() {
        return String.format("MoCMessageAnimation - entityId:%s, animationType:%s", this.entityId, this.animationType);
    }
}
