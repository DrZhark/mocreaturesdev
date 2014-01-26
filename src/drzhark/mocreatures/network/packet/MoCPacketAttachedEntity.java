package drzhark.mocreatures.network.packet;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.network.AbstractPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class MoCPacketAttachedEntity extends AbstractPacket {

    private int sourceEntityId;
    private int targetEntityId;

    public MoCPacketAttachedEntity() {}

    public MoCPacketAttachedEntity(int sourceEntityId, int targetEntityId)
    {
        this.sourceEntityId = sourceEntityId;
        this.targetEntityId = targetEntityId;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        buffer.writeInt(this.sourceEntityId);
        buffer.writeInt(this.targetEntityId);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        this.sourceEntityId = buffer.readInt();
        this.targetEntityId = buffer.readInt();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleClientSide(EntityPlayer player)
    {
        Object var2 = MoCClientProxy.mc.thePlayer.worldObj.getEntityByID(this.sourceEntityId);
        Entity var3 = MoCClientProxy.mc.thePlayer.worldObj.getEntityByID(this.targetEntityId);

        if (var2 != null)
        {
            ((Entity) var2).mountEntity(var3);
        }
    }

    @Override
    public void handleServerSide(EntityPlayer player)
    {
        // TODO Auto-generated method stub
        
    }

}
