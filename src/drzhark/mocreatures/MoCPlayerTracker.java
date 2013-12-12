package drzhark.mocreatures;

import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.IPlayerTracker;
import drzhark.mocreatures.entity.IMoCEntity;

public class MoCPlayerTracker implements IPlayerTracker
{
    @Override
    public void onPlayerLogin(EntityPlayer player) {
    }

    @Override
    public void onPlayerLogout(EntityPlayer player) {
        //System.out.println("onPlayerLogout " + player);
        if (player.ridingEntity != null && (player.ridingEntity instanceof IMoCEntity))
        {
            IMoCEntity mocEntity = (IMoCEntity)player.ridingEntity;
            mocEntity.riderIsDisconnecting(true);// = true;
        }
    }

    @Override
    public void onPlayerChangedDimension(EntityPlayer player) {
    }

    @Override
    public void onPlayerRespawn(EntityPlayer player) 
    {
    }
}