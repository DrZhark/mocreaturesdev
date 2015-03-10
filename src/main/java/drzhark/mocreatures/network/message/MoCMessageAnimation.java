package drzhark.mocreatures.network.message;

import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.entity.IMoCEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.List;

public class MoCMessageAnimation implements IMessage, IMessageHandler<MoCMessageAnimation, IMessage> {

    private int entityId;
    private int animationType;

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
        List<Entity> entList = MoCClientProxy.mc.thePlayer.worldObj.loadedEntityList;
        for (Entity ent : entList) {
            if (ent.getEntityId() == message.entityId && ent instanceof IMoCEntity) {
                ((IMoCEntity) ent).performAnimation(message.animationType);
                break;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format("MoCMessageAnimation - entityId:%s, animationType:%s", this.entityId, this.animationType);
    }
}
