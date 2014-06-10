package drzhark.mocreatures.network.message;

import java.util.List;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.utils.MoCLog;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

public class MoCMessageInstaSpawn implements IMessage, IMessageHandler<MoCMessageInstaSpawn, IMessage> {

    private int entityId;
    private int numberToSpawn;

    public MoCMessageInstaSpawn() {}

    public MoCMessageInstaSpawn(int entityId, int numberToSpawn)
    {
        this.entityId = entityId;
        this.numberToSpawn = numberToSpawn;
    }

    @Override
    public void toBytes(ByteBuf buffer)
    {
        buffer.writeInt(this.entityId);
        buffer.writeInt(this.numberToSpawn);
    }

    @Override
    public void fromBytes(ByteBuf buffer)
    {
        this.entityId = buffer.readInt();
    }

    @Override
    public IMessage onMessage(MoCMessageInstaSpawn message, MessageContext ctx)
    {
        if ((MoCreatures.proxy.getProxyMode() == 1 && MoCreatures.proxy.allowInstaSpawn) || MoCreatures.proxy.getProxyMode() == 2) // make sure the client has admin rights on server!
        {
            MoCTools.spawnNearPlayer(ctx.getServerHandler().playerEntity, entityId, numberToSpawn);
            if (MoCreatures.proxy.debug) MoCLog.logger.info("Player " + ctx.getServerHandler().playerEntity.getCommandSenderName() + " used MoC instaspawner and got " + numberToSpawn + " creatures spawned");
        }
        else
        {
            if (MoCreatures.proxy.debug) MoCLog.logger.info("Player " + ctx.getServerHandler().playerEntity.getCommandSenderName() + " tried to use MoC instaspawner, but the allowInstaSpawn setting is set to " + MoCreatures.proxy.allowInstaSpawn);
        }
        return null;
    }

    @Override
    public String toString()
    {
        return String.format("MoCMessageInstaSpawn - entityId:%s, numberToSpawn:%s", this.entityId, this.numberToSpawn);
    }
}