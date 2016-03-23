package drzhark.mocreatures.network.message;

import drzhark.mocreatures.network.MoCMessageHandler;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MoCMessageNameGUI implements IMessage, IMessageHandler<MoCMessageNameGUI, IMessage> {

    public int entityId;

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
        MoCMessageHandler.handleMessage(message, ctx);
    }

    @Override
    public String toString() {
        return String.format("MoCMessageNameGUI - entityId:%s", this.entityId);
    }
}
