package drzhark.mocreatures.datafixer;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.datafix.IDataFixer;
import net.minecraft.util.datafix.IDataWalker;

public class EntityDataWalker implements IDataWalker
{
    @Override
    public NBTTagCompound process(IDataFixer fixer, NBTTagCompound compound, int version) {
        final String entityId = compound.getString("id");
        if (entityId.contains("mocreatures.")) {
            String entityName = entityId.replace("mocreatures.", "").toLowerCase();
            if (entityName.equalsIgnoreCase("polarbear")) {
                entityName = "wildpolarbear";
            }
            final String fixedEntityId = "mocreatures:" + entityName;
            compound.setString("id", fixedEntityId);
        }
        return compound;
    }
}