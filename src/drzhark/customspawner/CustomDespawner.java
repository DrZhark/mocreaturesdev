package drzhark.customspawner;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;
import drzhark.customspawner.utils.CMSUtils;

public class CustomDespawner {

    public static boolean debug = false;
    public static int despawnLightLevel = 7;
    public static int despawnTickRate = 111;
    public List<BiomeGenBase> biomeList = new ArrayList<BiomeGenBase>();
    private List<Class> vanillaClassList;


    //New DesPawner stuff
    public final static int entityDespawnCheck(WorldServer worldObj, EntityLiving entityliving, int despawnLightLevel)
    {
        if (entityliving instanceof EntityWolf && ((EntityWolf) entityliving).isTamed()) { return 0; }
        if (entityliving instanceof EntityOcelot && ((EntityOcelot) entityliving).isTamed()) { return 0; }
        if (!isValidDespawnLightLevel(entityliving, worldObj, despawnLightLevel)) { return 0; }

        EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(entityliving, -1D);
        if (entityplayer != null) //entityliving.canDespawn() && 
        {
            double d = ((Entity) (entityplayer)).posX - entityliving.posX;
            double d1 = ((Entity) (entityplayer)).posY - entityliving.posY;
            double d2 = ((Entity) (entityplayer)).posZ - entityliving.posZ;
            double d3 = d * d + d1 * d1 + d2 * d2;
            if (d3 > 16384D)
            {
                entityliving.setDead();
                return 1;
            }
            if (entityliving.getAge() > 600 && worldObj.rand.nextInt(800) == 0)
            {
                if (d3 < 1024D)
                {
                    //entityliving.attackEntityFrom(DamageSource.generic, 0);
                }
                else
                {
                    entityliving.setDead();
                    return 1;
                }
            }
        }
        return 0;
    }

    public final static int countEntities(Class class1, World worldObj)
    {
        int i = 0;
        for (int j = 0; j < worldObj.loadedEntityList.size(); j++)
        {
            Entity entity = (Entity) worldObj.loadedEntityList.get(j);
            if (class1.isAssignableFrom(entity.getClass()))
            {
                i++;
            }
        }

        return i;
    }

    public static boolean isValidDespawnLightLevel(Entity entity, World worldObj, int despawnLightLevel)
    {
        int x = MathHelper.floor_double(entity.posX);
        int y = MathHelper.floor_double(entity.boundingBox.minY);
        int z = MathHelper.floor_double(entity.posZ);
        int i = 0;
        if (y >= 0)
        {
            if (y >= 256)
            {
                y = 255;
            }
            i = CMSUtils.getBlockLightValue(worldObj.getChunkFromChunkCoords(x >> 4, z >> 4), x & 15, y, z & 15);
        }
        if (i > despawnLightLevel)
        {
            if (debug) CMSUtils.getEnvironment(worldObj).envLog.logger.info("Denied despawn! for vanilla " + entity.getEntityName() + ". LightLevel over threshold of " + despawnLightLevel + " in dimension " + worldObj.provider.dimensionId + " at coords " + x + ", " + y + ", " + z);
        }
        /*else {
            if (verboseConsole) CMSUtils.getEnvironment(worldObj).envLog.logger.info("Valid LightLevel " + i + " found. Proceeding to despawn vanilla " + entity.getEntityName() + " at " + x + ", " + y + ", " + z);
        }*/
        return i <= despawnLightLevel;
    }
}
