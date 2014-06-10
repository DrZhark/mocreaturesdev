package drzhark.mocreatures.network.message;

import java.util.List;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import drzhark.mocreatures.client.MoCClientProxy;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;

public class MoCMessageHealth implements IMessage, IMessageHandler<MoCMessageHealth, IMessage> {

    private int entityId;
    private float health;

    public MoCMessageHealth() {}

    public MoCMessageHealth(int entityId, float health)
    {
        this.entityId = entityId;
        this.health = health;
    }

    @Override
    public void toBytes(ByteBuf buffer)
    {
        buffer.writeInt(this.entityId);
        buffer.writeFloat(health);
    }

    @Override
    public void fromBytes(ByteBuf buffer)
    {
        this.entityId = buffer.readInt();
        this.health = buffer.readFloat();
    }

    @Override
    public IMessage onMessage(MoCMessageHealth message, MessageContext ctx)
    {
        List<Entity> entList = MoCClientProxy.mc.thePlayer.worldObj.loadedEntityList;
        for (Entity ent : entList)
        {
            if (ent.getEntityId() == message.entityId && ent instanceof EntityLiving)
            {
                ((EntityLiving) ent).setHealth(message.health);
                break;
            }
        }
        return null;
    }

    @Override
    public String toString()
    {
        return String.format("MoCMessageHealth - entityId:%s, health:%s", this.entityId, this.health);
    }
}