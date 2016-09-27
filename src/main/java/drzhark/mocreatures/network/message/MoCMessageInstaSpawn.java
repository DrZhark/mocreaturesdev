package drzhark.mocreatures.network.message;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.util.MoCLog;
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
        if ((MoCreatures.proxy.getProxyMode() == 1 && MoCreatures.proxy.allowInstaSpawn) || MoCreatures.proxy.getProxyMode() == 2) { // make sure the client has admin rights on server!
            MoCTools.spawnNearPlayer(ctx.getServerHandler().playerEntity, message.entityId, message.numberToSpawn);
            if (MoCreatures.proxy.debug) {
                MoCLog.logger.info("Player " + ctx.getServerHandler().playerEntity.getName() + " used MoC instaspawner and got "
                        + message.numberToSpawn + " creatures spawned");
            }
        } else {
            if (MoCreatures.proxy.debug) {
                MoCLog.logger.info("Player " + ctx.getServerHandler().playerEntity.getName()
                        + " tried to use MoC instaspawner, but the allowInstaSpawn setting is set to " + MoCreatures.proxy.allowInstaSpawn);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format("MoCMessageInstaSpawn - entityId:%s, numberToSpawn:%s", this.entityId, this.numberToSpawn);
    }
}
