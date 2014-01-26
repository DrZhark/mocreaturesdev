package drzhark.mocreatures.network.packet;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.network.AbstractPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class MoCPacketAnimation extends AbstractPacket {

    private int entityId;
    private int animationType;

    public MoCPacketAnimation() {}

    public MoCPacketAnimation(int entityId, int animationType)
    {
        this.entityId = entityId;
        this.animationType = animationType;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        buffer.writeInt(this.entityId);
        buffer.writeInt(this.animationType);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        this.entityId = buffer.readInt();
        this.animationType = buffer.readInt();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleClientSide(EntityPlayer player)
    {
        List<Entity> entList = MoCClientProxy.mc.thePlayer.worldObj.loadedEntityList;
        for (Entity ent : entList)
        {
            if (ent.getEntityId() == this.entityId && ent instanceof IMoCEntity)
            {
                ((IMoCEntity) ent).performAnimation(this.animationType);
                break;
            }
        }
    }

    @Override
    public void handleServerSide(EntityPlayer player)
    {
        // TODO Auto-generated method stub
        
    }

}
