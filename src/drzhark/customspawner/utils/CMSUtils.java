package drzhark.customspawner.utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.nbt.NBTTagCompound;

public class CMSUtils {

    public static boolean isTamed(Entity entity)
    {
        if (entity instanceof EntityTameable)
        {
            if (((EntityTameable)entity).isTamed())
            {
                return true;
            }
        }
        NBTTagCompound nbt = new NBTTagCompound();
        entity.writeToNBT(nbt);
        if (nbt != null)
        {
            if (nbt.hasKey("Owner") && !nbt.getString("Owner").equals(""))
            {
                return true; // ignore
            }
            if (nbt.hasKey("Tamed") && nbt.getBoolean("Tamed") == true)
            {
                return true; // ignore
            }
        }
        return false;
    }
}
