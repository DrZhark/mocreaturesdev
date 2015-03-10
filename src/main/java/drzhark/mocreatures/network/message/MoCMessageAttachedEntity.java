package drzhark.mocreatures.network.message;

import drzhark.mocreatures.client.MoCClientProxy;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MoCMessageAttachedEntity implements IMessage, IMessageHandler<MoCMessageAttachedEntity, IMessage> {

    private int sourceEntityId;
    private int targetEntityId;

    public MoCMessageAttachedEntity() {
    }

    public MoCMessageAttachedEntity(int sourceEntityId, int targetEntityId) {
        this.sourceEntityId = sourceEntityId;
        this.targetEntityId = targetEntityId;
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        buffer.writeInt(this.sourceEntityId);
        buffer.writeInt(this.targetEntityId);
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        this.sourceEntityId = buffer.readInt();
        this.targetEntityId = buffer.readInt();
    }

    @Override
    public IMessage onMessage(MoCMessageAttachedEntity message, MessageContext ctx) {
        Object var2 = MoCClientProxy.mc.thePlayer.worldObj.getEntityByID(message.sourceEntityId);
        Entity var3 = MoCClientProxy.mc.thePlayer.worldObj.getEntityByID(message.targetEntityId);

        if (var2 != null) {
            ((Entity) var2).mountEntity(var3);
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format("MoCMessageAttachedEntity - sourceEntityId:%s, targetEntityId:%s", this.sourceEntityId, this.targetEntityId);
    }
}
