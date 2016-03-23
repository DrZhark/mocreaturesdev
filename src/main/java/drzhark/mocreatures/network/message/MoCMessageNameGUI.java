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
        // Due to a timing issue with Forge, we now need to check for a match for a certain period of time.
        for (int count = 0; count < 100; count++) {
            Entity entity = MoCClientProxy.mc.thePlayer.worldObj.getEntityByID(message.entityId);
            if (entity != null) {
                MoCClientProxy.mc.displayGuiScreen(new MoCGUIEntityNamer(((IMoCEntity) entity), ((IMoCEntity) entity).getMoCName()));
                break;
            }
        }
    }

    @Override
    public String toString() {
        return String.format("MoCMessageNameGUI - entityId:%s", this.entityId);
    }
}
