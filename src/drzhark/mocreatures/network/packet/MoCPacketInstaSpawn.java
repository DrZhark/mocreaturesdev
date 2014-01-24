package drzhark.mocreatures.network.packet;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.network.AbstractPacket;
import drzhark.mocreatures.utils.MoCLog;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

public class MoCPacketInstaSpawn extends AbstractPacket {

    private int entityId;
    private int numberToSpawn;

    public MoCPacketInstaSpawn() {}

    public MoCPacketInstaSpawn(int entityId, int numberToSpawn)
    {
        this.entityId = entityId;
        this.numberToSpawn = numberToSpawn;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        buffer.writeInt(this.entityId);
        buffer.writeInt(this.numberToSpawn);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        this.entityId = buffer.readInt();
        this.numberToSpawn = buffer.readInt();
    }

    @Override
    public void handleClientSide(EntityPlayer player)
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void handleServerSide(EntityPlayer player)
    {
        if ((MoCreatures.proxy.getProxyMode() == 1 && MoCreatures.proxy.allowInstaSpawn) || MoCreatures.proxy.getProxyMode() == 2) // make sure the client has admin rights on server!
        {
            MoCTools.spawnNearPlayer(player, entityId, numberToSpawn);
            if (MoCreatures.proxy.debug) MoCLog.logger.info("Player " + player.getCommandSenderName() + " used MoC instaspawner and got " + numberToSpawn + " creatures spawned");
        }
        else
        {
            if (MoCreatures.proxy.debug) MoCLog.logger.info("Player " + player.getCommandSenderName() + " tried to use MoC instaspawner, but the allowInstaSpawn setting is set to " + MoCreatures.proxy.allowInstaSpawn);
        }
    }

}
