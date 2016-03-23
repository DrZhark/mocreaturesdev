package drzhark.mocreatures.network.message;

import drzhark.mocreatures.network.MoCMessageHandler;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MoCMessageInstaSpawn implements IMessage, IMessageHandler<MoCMessageInstaSpawn, IMessage> {

    public int entityId;
    public int numberToSpawn;

    public MoCMessageInstaSpawn() {
    }

    public MoCMessageInstaSpawn(int entityId, int numberToSpawn) {
        this.entityId = entityId;
        this.numberToSpawn = numberToSpawn;
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        buffer.writeInt(this.entityId);
        buffer.writeInt(this.numberToSpawn);
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        this.entityId = buffer.readInt();
    }

    @Override
    public IMessage onMessage(MoCMessageInstaSpawn message, MessageContext ctx) {
        MoCMessageHandler.handleMessage(message, ctx);
        return null;
    }

    @Override
    public String toString() {
        return String.format("MoCMessageInstaSpawn - entityId:%s, numberToSpawn:%s", this.entityId, this.numberToSpawn);
    }
}
