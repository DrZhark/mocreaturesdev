package drzhark.mocreatures.network.packet;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.network.AbstractPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class MoCPacketShuffle extends AbstractPacket {

    private int entityId;
    private boolean flag;

    public MoCPacketShuffle() {}

    public MoCPacketShuffle(int entityId, boolean flag)
    {
        this.entityId = entityId;
        this.flag = flag;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        buffer.writeInt(this.entityId);
        buffer.writeBoolean(flag);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        this.entityId = buffer.readInt();
        this.flag = buffer.readBoolean();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleClientSide(EntityPlayer player)
    {
        List<Entity> entList = MoCClientProxy.mc.thePlayer.worldObj.loadedEntityList;
        for (Entity ent : entList)
        {
            if (ent.getEntityId() == this.entityId && ent instanceof MoCEntityHorse)
            {
                if (flag)
                {
                    ((MoCEntityHorse) ent).shuffle();
                }
                else
                {
                    ((MoCEntityHorse) ent).shuffleCounter = 0;
                }
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
