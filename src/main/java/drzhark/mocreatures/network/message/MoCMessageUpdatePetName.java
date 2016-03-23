package drzhark.mocreatures.network.message;

import drzhark.mocreatures.network.MoCMessageHandler;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MoCMessageUpdatePetName implements IMessage, IMessageHandler<MoCMessageUpdatePetName, IMessage> {

    public String name;
    public int entityId;

    public MoCMessageUpdatePetName() {
    }

    public MoCMessageUpdatePetName(int entityId) {
        this.entityId = entityId;
    }

    public MoCMessageUpdatePetName(int entityId, String name) {
        this.entityId = entityId;
        this.name = name;
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        buffer.writeInt(this.name.length());
        buffer.writeBytes(this.name.getBytes());
        buffer.writeInt(this.entityId);
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        int nameLength = buffer.readInt();
        this.name = new String(buffer.readBytes(nameLength).array());
        this.entityId = buffer.readInt();
    }

    @Override
    public IMessage onMessage(MoCMessageUpdatePetName message, MessageContext ctx) {
        MoCMessageHandler.handleMessage(message, ctx);
        return null;
    }

    @Override
    public String toString() {
        return String.format("MoCMessageUpdatePetName - entityId:%s, name:%s", this.entityId, this.name);
    }
}
