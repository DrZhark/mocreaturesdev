package drzhark.mocreatures.network.message;

import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.client.gui.helpers.MoCGUIEntityNamer;
import drzhark.mocreatures.entity.IMoCEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class MoCMessageNameGUI implements IMessage, IMessageHandler<MoCMessageNameGUI, IMessage> {

    int entityId;

    public MoCMessageNameGUI() {
    }

    public MoCMessageNameGUI(int entityId) {
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
    public IMessage onMessage(MoCMessageNameGUI message, MessageContext ctx) {
        if (ctx.side == Side.CLIENT) {
            handleClientMessage(message, ctx);
        }
        return null;
    }

    @SideOnly(Side.CLIENT)
    public void handleClientMessage(MoCMessageNameGUI message, MessageContext ctx) {
        List<Entity> entList = MoCClientProxy.mc.thePlayer.worldObj.loadedEntityList;
        for (Entity ent : entList) {
            if (ent.getEntityId() == message.entityId && ent instanceof IMoCEntity) {
                MoCClientProxy.mc.displayGuiScreen(new MoCGUIEntityNamer(((IMoCEntity) ent), ((IMoCEntity) ent).getName()));
                break;
            }
        }
    }

    @Override
    public String toString() {
        return String.format("MoCMessageNameGUI - entityId:%s", this.entityId);
    }
}
