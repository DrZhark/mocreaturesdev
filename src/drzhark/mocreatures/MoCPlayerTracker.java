package drzhark.mocreatures;

import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.IPlayerTracker;
import drzhark.mocreatures.entity.MoCEntityAnimal;

public class MoCPlayerTracker implements IPlayerTracker
{
    @Override
    public void onPlayerLogin(EntityPlayer player) {
    }

    @Override
    public void onPlayerLogout(EntityPlayer player) {
        System.out.println("onPlayerLogout " + player);
        if (player.ridingEntity != null && (player.ridingEntity instanceof MoCEntityAnimal))
        {
            MoCEntityAnimal mocAnimal = (MoCEntityAnimal)player.ridingEntity;
            mocAnimal.riderIsDisconnecting = true;
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