package drzhark.customspawner;

import drzhark.customspawner.utils.CMSUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.List;

public class CustomDespawner {

    public static boolean debug = false;
    public static int despawnLightLevel = 7;
    public static int despawnTickRate = 111;
    public List<Biome> biomeList = new ArrayList<Biome>();

    //New DesPawner stuff
    public final static int entityDespawnCheck(WorldServer world, EntityLiving entityliving, int minDespawnLightLevel, int maxDespawnLightLevel) {
        if (entityliving instanceof EntityWolf && ((EntityWolf) entityliving).isTamed()) {
            return 0;
        }
        if (entityliving instanceof EntityOcelot && ((EntityOcelot) entityliving).isTamed()) {
            return 0;
        }
        if (!isValidDespawnLightLevel(entityliving, world, minDespawnLightLevel, maxDespawnLightLevel)) {
            return 0;
        }

        EntityPlayer entityplayer = world.getClosestPlayerToEntity(entityliving, -1D);
        if (entityplayer != null) //entityliving.canDespawn() &&
        {
            double d = ((Entity) (entityplayer)).posX - entityliving.posX;
            double d1 = ((Entity) (entityplayer)).posY - entityliving.posY;
            double d2 = ((Entity) (entityplayer)).posZ - entityliving.posZ;
            double d3 = d * d + d1 * d1 + d2 * d2;
            if (d3 > 16384D) {
                entityliving.setDead();
                return 1;
            }
            if (entityliving.getIdleTime() > 600 && world.rand.nextInt(800) == 0) {
                if (d3 < 1024D) {
                    //entityliving.attackEntityFrom(DamageSource.generic, 0);
                } else {
                    entityliving.setDead();
                    return 1;
                }
            }
        }
        return 0;
    }

    public final static int countEntities(Class<? extends Entity> class1, World world) {
        int i = 0;
        for (int j = 0; j < world.loadedEntityList.size(); j++) {
            Entity entity = world.loadedEntityList.get(j);
            if (class1.isAssignableFrom(entity.getClass())) {
                i++;
            }
        }

        return i;
    }

    public static boolean isValidDespawnLightLevel(Entity entity, World world, int minDespawnLightLevel, int maxDespawnLightLevel) {
        int x = MathHelper.floor(entity.posX);
        int y = MathHelper.floor(entity.getEntityBoundingBox().minY);
        int z = MathHelper.floor(entity.posZ);
        int blockLightLevel = 0;
        if (y >= 0) {
            if (y >= 256) {
                y = 255;
            }
            blockLightLevel = CMSUtils.getLightFromNeighbors(world.getChunkFromChunkCoords(x >> 4, z >> 4), x & 15, y, z & 15);
        }
        if (blockLightLevel < minDespawnLightLevel && maxDespawnLightLevel != -1) {
            //if (debug) CMSUtils.getEnvironment(world).envLog.logger.info("Denied spawn! for " + entity.getName() + blockLightLevel + " under minimum threshold of " + minDespawnLightLevel + " in dimension " + world.provider.getDimensionType().getId() + " at coords " + x + ", " + y + ", " + z);
            return false;
        } else if (blockLightLevel > maxDespawnLightLevel && maxDespawnLightLevel != -1) {
            //if (debug) CMSUtils.getEnvironment(world).envLog.logger.info("Denied spawn! for " + entity.getName() + blockLightLevel + " over maximum threshold of " + maxDespawnLightLevel + " in dimension " + world.provider.getDimensionType().getId() + " at coords " + x + ", " + y + ", " + z);
            return false;
        }
        return true;
    }
}
