package drzhark.mocreatures.network.message;

import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.List;

public class MoCMessageHeart implements IMessage, IMessageHandler<MoCMessageHeart, IMessage> {

    private int entityId;

    public MoCMessageHeart() {
    }

    public MoCMessageHeart(int entityId) {
        this.entityId = entityId;
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        buffer.writeInt(this.entityId);
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        this.entityId = buffer.readInt();
    }

    @Override
    public IMessage onMessage(MoCMessageHeart message, MessageContext ctx) {
        List<Entity> entList = MoCClientProxy.mc.thePlayer.worldObj.loadedEntityList;
        for (Entity ent : entList) {
            if (ent.getEntityId() == message.entityId && ent instanceof MoCEntityAnimal) {
                ((MoCEntityAnimal) ent).SpawnHeart();
                break;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format("MoCMessageHeart - entityId:%s", this.entityId);
    }
}
