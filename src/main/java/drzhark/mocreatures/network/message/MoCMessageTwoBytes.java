package drzhark.mocreatures.network.message;

import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.entity.monster.MoCEntityGolem;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MoCMessageTwoBytes implements IMessage, IMessageHandler<MoCMessageTwoBytes, IMessage> {

    private int entityId;
    private byte slot;
    private byte value;

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
        Entity ent = MoCClientProxy.mc.thePlayer.worldObj.getEntityByID(message.entityId);
        if (ent != null && ent instanceof MoCEntityGolem) {
            ((MoCEntityGolem) ent).saveGolemCube(message.slot, message.value);
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format("MoCMessageTwoBytes - entityId:%s, slot:%s, value:%s", this.entityId, this.slot, this.value);
    }
}
