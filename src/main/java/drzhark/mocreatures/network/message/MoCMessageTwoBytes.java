package drzhark.mocreatures.network.message;

import drzhark.mocreatures.network.MoCMessageHandler;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MoCMessageTwoBytes implements IMessage, IMessageHandler<MoCMessageTwoBytes, IMessage> {

    public int entityId;
    public byte slot;
    public byte value;

    public MoCMessageTwoBytes() {
    }

    public MoCMessageTwoBytes(int entityId, byte slot, byte value) {
        this.entityId = entityId;
        this.slot = slot;
        this.value = value;
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        buffer.writeInt(this.entityId);
        buffer.writeByte(this.slot);
        buffer.writeByte(this.value);
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        this.entityId = buffer.readInt();
        this.slot = buffer.readByte();
        this.value = buffer.readByte();
    }

    @Override
    public IMessage onMessage(MoCMessageTwoBytes message, MessageContext ctx) {
        MoCMessageHandler.handleMessage(message, ctx);
        return null;
    }

    @Override
    public String toString() {
        return String.format("MoCMessageTwoBytes - entityId:%s, slot:%s, value:%s", this.entityId, this.slot, this.value);
    }
}
