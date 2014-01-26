package drzhark.mocreatures.network.packet;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.entity.monster.MoCEntityGolem;
import drzhark.mocreatures.network.AbstractPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class MoCPacketTwoBytes extends AbstractPacket {

    private int entityId;
    private byte slot;
    private byte value;

    public MoCPacketTwoBytes() {}

    public MoCPacketTwoBytes(int entityId, byte slot, byte value)
    {
        this.entityId = entityId;
        this.slot = slot;
        this.value = value;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        buffer.writeInt(this.entityId);
        buffer.writeByte(this.slot);
        buffer.writeByte(this.value);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        this.entityId = buffer.readInt();
        this.slot = buffer.readByte();
        this.value = buffer.readByte();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleClientSide(EntityPlayer player)
    {
        Entity ent = MoCClientProxy.mc.thePlayer.worldObj.getEntityByID(this.entityId);
        if (ent != null && ent instanceof MoCEntityGolem)
        {
            ((MoCEntityGolem) ent).saveGolemCube(this.slot, this.value);
        }
    }

    @Override
    public void handleServerSide(EntityPlayer player)
    {
        // TODO Auto-generated method stub
        
    }

}
