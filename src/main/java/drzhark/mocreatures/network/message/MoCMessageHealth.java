package drzhark.mocreatures.network.message;

import drzhark.mocreatures.network.MoCMessageHandler;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MoCMessageHealth implements IMessage, IMessageHandler<MoCMessageHealth, IMessage> {

    public int entityId;
    public float health;

    public MoCMessageHealth() {
    }

    public MoCMessageHealth(int entityId, float health) {
        this.entityId = entityId;
        this.health = health;
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        buffer.writeInt(this.entityId);
        buffer.writeFloat(this.health);
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        this.entityId = buffer.readInt();
        this.health = buffer.readFloat();
    }

    @Override
    public IMessage onMessage(MoCMessageHealth message, MessageContext ctx) {
        MoCMessageHandler.handleMessage(message, ctx);
        return null;
    }

    @Override
    public String toString() {
        return String.format("MoCMessageHealth - entityId:%s, health:%s", this.entityId, this.health);
    }
}
