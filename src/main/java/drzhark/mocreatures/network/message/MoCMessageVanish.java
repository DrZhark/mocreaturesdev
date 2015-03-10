package drzhark.mocreatures.network.message;

import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.List;

public class MoCMessageVanish implements IMessage, IMessageHandler<MoCMessageVanish, IMessage> {

    private int entityId;

    public MoCMessageVanish() {
    }

    public MoCMessageVanish(int entityId) {
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
    public IMessage onMessage(MoCMessageVanish message, MessageContext ctx) {
        List<Entity> entList = MoCClientProxy.mc.thePlayer.worldObj.loadedEntityList;
        for (Entity ent : entList) {
            if (ent.getEntityId() == message.entityId && ent instanceof MoCEntityHorse) {
                ((MoCEntityHorse) ent).setVanishC((byte) 1);
                break;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format("MoCMessageVanish - entityId:%s", this.entityId);
    }
}
