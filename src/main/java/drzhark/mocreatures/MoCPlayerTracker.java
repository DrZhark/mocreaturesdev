package drzhark.mocreatures;

import drzhark.mocreatures.entity.IMoCTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;

public class MoCPlayerTracker {

    @SubscribeEvent
    public void onPlayerLogout(PlayerLoggedOutEvent event) {
        EntityPlayer player = event.player;
        if (player.ridingEntity != null && (player.ridingEntity instanceof IMoCTameable)) {
            IMoCTameable mocEntity = (IMoCTameable) player.ridingEntity;
            mocEntity.setRiderDisconnecting(true);
        }
    }
}
