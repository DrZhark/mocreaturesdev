package drzhark.mocreatures.entity.ai;

import drzhark.mocreatures.entity.IMoCTameable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;

public class EntityAITools {

    protected static boolean IsNearPlayer(EntityLiving entityliving, double d) {
        EntityPlayer entityplayer1 = entityliving.world.getClosestPlayerToEntity(entityliving, d);
        if (entityplayer1 != null) {
            return true;
        }
        return false;
    }

    protected static EntityPlayer getIMoCTameableOwner(IMoCTameable pet) {
        if (pet.getOwnerId() == null) {
            return null;
        }

        for (int i = 0; i < ((EntityLiving) pet).world.playerEntities.size(); ++i) {
            EntityPlayer entityplayer = (EntityPlayer) ((EntityLiving) pet).world.playerEntities.get(i);

            if (pet.getOwnerId().equals(entityplayer.getUniqueID())) {
                return entityplayer;
            }
        }
        return null;
    }
}
