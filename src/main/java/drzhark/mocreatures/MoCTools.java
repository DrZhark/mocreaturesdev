package drzhark.mocreatures;

import drzhark.mocreatures.entity.IMoCTameable;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ambient.MoCEntityMaggot;
import drzhark.mocreatures.entity.item.MoCEntityThrowableRock;
import drzhark.mocreatures.entity.monster.MoCEntityOgre;
import drzhark.mocreatures.entity.monster.MoCEntitySilverSkeleton;
import drzhark.mocreatures.entity.passive.MoCEntityBigCat;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.entity.passive.MoCEntityManticorePet;
import drzhark.mocreatures.entity.passive.MoCEntityPetScorpion;
import drzhark.mocreatures.inventory.MoCAnimalChest;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageNameGUI;
import drzhark.mocreatures.utils.MoCLog;
import net.minecraft.block.Block;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.BlockJukebox.TileEntityJukebox;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MoCTools {

    /**
     * spawns tiny slimes
     */
    public static void spawnSlimes(World worldObj, Entity entity) {
        if (MoCreatures.isServer()) {
            //changed so it only spawns 0 - 1 slime, as it now spaws also big slimes
            int var2 = 1 + worldObj.rand.nextInt(1);

            for (int i = 0; i < var2; ++i) {
                float var4 = (i % 2 - 0.5F) * 1 / 4.0F;
                float var5 = (i / 2 - 0.5F) * 1 / 4.0F;
                EntitySlime var6 = new EntitySlime(worldObj);
                //var6.setSlimeSize(1);  TODO FIX
                var6.setLocationAndAngles(entity.posX + var4, entity.posY + 0.5D, entity.posZ + var5, worldObj.rand.nextFloat() * 360.0F, 0.0F);
                worldObj.spawnEntityInWorld(var6);
            }
        }
    }

    /**
     * Drops saddle
     */
    public static void dropSaddle(MoCEntityAnimal entity, World worldObj) {
        if (!entity.getIsRideable() || !MoCreatures.isServer()) {
            return;
        }
        dropCustomItem(entity, worldObj, new ItemStack(MoCreatures.horsesaddle, 1));
        entity.setRideable(false);
    }

    /**
     * Drops chest block
     */
    public static void dropBags(MoCEntityAnimal entity, World worldObj) {
        if (!MoCreatures.isServer()) {
            return;
        }
        dropCustomItem(entity, worldObj, new ItemStack(Blocks.chest, 1));
    }

    /**
     * Drops item
     */
    public static void dropCustomItem(Entity entity, World worldObj, ItemStack itemstack) {
        if (!MoCreatures.isServer()) {
            return;
        }

        EntityItem entityitem = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, itemstack);
        float f3 = 0.05F;
        entityitem.motionX = (float) worldObj.rand.nextGaussian() * f3;
        entityitem.motionY = ((float) worldObj.rand.nextGaussian() * f3) + 0.2F;
        entityitem.motionZ = (float) worldObj.rand.nextGaussian() * f3;
        worldObj.spawnEntityInWorld(entityitem);
    }

    public static void bigsmack(Entity entity, Entity entity1, float force) {
        double d = entity.posX - entity1.posX;
        double d1 = entity.posZ - entity1.posZ;
        for (d1 = entity.posZ - entity1.posZ; ((d * d) + (d1 * d1)) < 0.0001D; d1 = (Math.random() - Math.random()) * 0.01D) {
            d = (Math.random() - Math.random()) * 0.01D;
        }

        float f = MathHelper.sqrt_double((d * d) + (d1 * d1));
        entity1.motionX /= 2D;
        entity1.motionY /= 2D;
        entity1.motionZ /= 2D;
        entity1.motionX -= (d / f) * force;
        entity1.motionY += force;
        entity1.motionZ -= (d1 / f) * force;
        if (entity1.motionY > force) {
            entity1.motionY = force;
        }
    }

    public static void buckleMobs(EntityLiving entityattacker, Double dist, World worldObj) {
        List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(entityattacker, entityattacker.getEntityBoundingBox().expand(dist, 2D, dist));
        for (int i = 0; i < list.size(); i++) {
            Entity entitytarget = list.get(i);
            if (!(entitytarget instanceof EntityLiving) || (entityattacker.riddenByEntity != null && entitytarget == entityattacker.riddenByEntity)) {
                continue;
            }

            entitytarget.attackEntityFrom(DamageSource.causeMobDamage(entityattacker), 2);
            bigsmack(entityattacker, entitytarget, 0.6F);
            playCustomSound(entityattacker, "tud", worldObj);
            //todo tuck sound!!
        }
    }

    public static void buckleMobsNotPlayers(EntityLiving entityattacker, Double dist, World worldObj) {
        List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(entityattacker, entityattacker.getEntityBoundingBox().expand(dist, 2D, dist));
        for (int i = 0; i < list.size(); i++) {
            Entity entitytarget = list.get(i);
            if (!(entitytarget instanceof EntityLiving) || (entitytarget instanceof EntityPlayer)
                    || (entityattacker.riddenByEntity != null && entitytarget == entityattacker.riddenByEntity)) {
                continue;
            }

            entitytarget.attackEntityFrom(DamageSource.causeMobDamage(entityattacker), 2);
            bigsmack(entityattacker, entitytarget, 0.6F);
            playCustomSound(entityattacker, "tud", worldObj);
            //todo tuck sound!!
        }
    }

    public static void spawnNearPlayer(EntityPlayer player, int entityId, int numberToSpawn)//, World worldObj)
    {
        WorldServer worldObj =
                FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(player.worldObj.provider.getDimensionId());
        for (int i = 0; i < numberToSpawn; i++) {
            EntityLiving entityliving = null;
            try {
                Class<? extends EntityLiving> entityClass = MoCreatures.instaSpawnerMap.get(entityId);
                entityliving = (EntityLiving) entityClass.getConstructor(new Class[] {World.class}).newInstance(new Object[] {worldObj});
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (entityliving != null) {
                entityliving.setLocationAndAngles(player.posX - 1, player.posY, player.posZ - 1, player.rotationYaw, player.rotationPitch);
                worldObj.spawnEntityInWorld(entityliving);
            }
        }
    }

    public static void spawnNearPlayerbyName(EntityPlayer player, String eName, int numberToSpawn) {
        WorldServer worldObj =
                FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(player.worldObj.provider.getDimensionId());

        for (int i = 0; i < numberToSpawn; i++) {
            EntityLiving entityToSpawn = null;
            try {
                MoCEntityData entityData = MoCreatures.mocEntityMap.get(eName);
                Class<? extends EntityLiving> myClass = entityData.getEntityClass();
                entityToSpawn = (EntityLiving) myClass.getConstructor(new Class[] {World.class}).newInstance(new Object[] {worldObj});
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (entityToSpawn != null) {
                IEntityLivingData entitylivingdata = null;
                entityToSpawn.onInitialSpawn(player.worldObj.getDifficultyForLocation(new BlockPos(entityToSpawn)), entitylivingdata); // onSpawnWithEgg
                entityToSpawn.setLocationAndAngles(player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch);
                worldObj.spawnEntityInWorld(entityToSpawn);
            }
        }
    }

    public static void playCustomSound(Entity entity, String customSound, World worldObj) {
        worldObj.playSoundAtEntity(entity, "mocreatures:" + customSound, 1.0F,
                1.0F + ((worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F));
    }

    public static void playCustomSound(Entity entity, String customSound, World worldObj, float volume) {
        worldObj.playSoundAtEntity(entity, "mocreatures:" + customSound, volume,
                1.0F + ((worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F));
    }

    /**
     * Returns a new instance of EntityLiving based on the name of the class
     *
     * @param eName
     * @param worldObj
     * @return
     */
    public static EntityLiving spawnListByNameClass(String eName, World worldObj) {
        EntityLiving entityToSpawn = null;
        try {
            MoCEntityData entityData = MoCreatures.mocEntityMap.get(eName);
            Class<? extends EntityLiving> myClass = null;
            if (entityData == null && eName.contains("PetScorpion")) { // since we don't add this to our map, we need to check for it manually
                myClass = MoCEntityPetScorpion.class;
            } else if (entityData == null && eName.contains("ManticorePet")) // since we don't add this to our map, we need to check for it manually
            {
                myClass = MoCEntityManticorePet.class;
            } else if (entityData == null && eName.contains("BigCat")) // since we don't add this to our map, we need to check for it manually
            {
                myClass = MoCEntityBigCat.class;
            } else {
                myClass = entityData.getEntityClass();
            }
            entityToSpawn = (EntityLiving) myClass.getConstructor(new Class[] {World.class}).newInstance(new Object[] {worldObj});
        } catch (Exception e) {
            if (MoCreatures.proxy.debug) {
                MoCLog.logger.warn("Unable to find class for entity " + eName + ", " + e);
            }
        }
        return entityToSpawn;
    }

    public static boolean NearMaterialWithDistance(Entity entity, Double double1, Material mat) {
        AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().expand(double1.doubleValue(), double1.doubleValue(), double1.doubleValue());
        int i = MathHelper.floor_double(axisalignedbb.minX);
        int j = MathHelper.floor_double(axisalignedbb.maxX + 1.0D);
        int k = MathHelper.floor_double(axisalignedbb.minY);
        int l = MathHelper.floor_double(axisalignedbb.maxY + 1.0D);
        int i1 = MathHelper.floor_double(axisalignedbb.minZ);
        int j1 = MathHelper.floor_double(axisalignedbb.maxZ + 1.0D);
        for (int k1 = i; k1 < j; k1++) {
            for (int l1 = k; l1 < l; l1++) {
                for (int i2 = i1; i2 < j1; i2++) {
                    IBlockState blockstate = entity.worldObj.getBlockState(new BlockPos(k1, l1, i2));
                    if ((blockstate.getBlock() != Blocks.AIR) && (blockstate.getBlock().getMaterial() == mat)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isNearBlockName(Entity entity, Double dist, String blockName) {
        AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().expand(dist, dist / 2D, dist);
        int i = MathHelper.floor_double(axisalignedbb.minX);
        int j = MathHelper.floor_double(axisalignedbb.maxX + 1.0D);
        int k = MathHelper.floor_double(axisalignedbb.minY);
        int l = MathHelper.floor_double(axisalignedbb.maxY + 1.0D);
        int i1 = MathHelper.floor_double(axisalignedbb.minZ);
        int j1 = MathHelper.floor_double(axisalignedbb.maxZ + 1.0D);
        for (int k1 = i; k1 < j; k1++) {
            for (int l1 = k; l1 < l; l1++) {
                for (int i2 = i1; i2 < j1; i2++) {
                    IBlockState blockstate = entity.worldObj.getBlockState(new BlockPos(k1, l1, i2));

                    if (blockstate.getBlock() != Blocks.AIR) {
                        String nameToCheck = "";
                        nameToCheck = blockstate.getBlock().getUnlocalizedName();//.getBlockName();
                        if (nameToCheck != null && nameToCheck != "") {
                            if (nameToCheck.equals(blockName)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public static TileEntityJukebox nearJukeBoxRecord(Entity entity, Double dist) {
        AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().expand(dist, dist / 2D, dist);
        int i = MathHelper.floor_double(axisalignedbb.minX);
        int j = MathHelper.floor_double(axisalignedbb.maxX + 1.0D);
        int k = MathHelper.floor_double(axisalignedbb.minY);
        int l = MathHelper.floor_double(axisalignedbb.maxY + 1.0D);
        int i1 = MathHelper.floor_double(axisalignedbb.minZ);
        int j1 = MathHelper.floor_double(axisalignedbb.maxZ + 1.0D);
        for (int k1 = i; k1 < j; k1++) {
            for (int l1 = k; l1 < l; l1++) {
                for (int i2 = i1; i2 < j1; i2++) {
                    BlockPos pos = new BlockPos(k1, l1, i2);
                    IBlockState blockstate = entity.worldObj.getBlockState(pos);
                    if (!entity.worldObj.isAirBlock(pos)) {
                        if (blockstate.getBlock() instanceof BlockJukebox) {
                            TileEntityJukebox juky = (TileEntityJukebox) entity.worldObj.getTileEntity(pos);
                            return juky;
                        }
                    }
                }
            }
        }
        return null;
    }

    public static void checkForTwistedEntities(World world) {
        for (int l = 0; l < world.loadedEntityList.size(); l++) {
            Entity entity = (Entity) world.loadedEntityList.get(l);
            if (entity instanceof EntityLivingBase) {
                EntityLivingBase twisted = (EntityLivingBase) entity;
                if (twisted.deathTime > 0 && twisted.getRidingEntity() == null && twisted.getHealth() > 0) {
                    twisted.deathTime = 0;
                }
            }
        }
    }

    public static double getSqDistanceTo(Entity entity, double i, double j, double k) {
        double l = entity.posX - i;
        double i1 = entity.posY - j;
        double j1 = entity.posZ - k;
        return Math.sqrt((l * l) + (i1 * i1) + (j1 * j1));
    }

    public static int[] ReturnNearestMaterialCoord(Entity entity, Material material, Double double1, Double yOff) {
        double shortestDistance = -1D;
        double distance = 0D;
        int x = -9999;
        int y = -1;
        int z = -1;

        AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().expand(double1.doubleValue(), yOff.doubleValue(), double1.doubleValue());
        int i = MathHelper.floor_double(axisalignedbb.minX);
        int j = MathHelper.floor_double(axisalignedbb.maxX + 1.0D);
        int k = MathHelper.floor_double(axisalignedbb.minY);
        int l = MathHelper.floor_double(axisalignedbb.maxY + 1.0D);
        int i1 = MathHelper.floor_double(axisalignedbb.minZ);
        int j1 = MathHelper.floor_double(axisalignedbb.maxZ + 1.0D);
        for (int k1 = i; k1 < j; k1++) {
            for (int l1 = k; l1 < l; l1++) {
                for (int i2 = i1; i2 < j1; i2++) {
                    BlockPos pos = new BlockPos(k1, l1, i2);
                    IBlockState blockstate = entity.worldObj.getBlockState(pos);
                    if ((blockstate.getBlock() != Blocks.AIR) && (blockstate.getBlock().getMaterial() == material)) {
                        distance = getSqDistanceTo(entity, k1, l1, i2);
                        if (shortestDistance == -1D) {
                            x = k1;
                            y = l1;
                            z = i2;
                            shortestDistance = distance;
                        }

                        if (distance < shortestDistance) {
                            x = k1;
                            y = l1;
                            z = i2;
                            shortestDistance = distance;
                        }
                    }
                }
            }
        }

        if (entity.posX > x) {
            x -= 2;
        } else {
            x += 2;
        }
        if (entity.posZ > z) {
            z -= 2;
        } else {
            z += 2;
        }
        return (new int[] {x, y, z});
    }

    public static int[] ReturnNearestBlockCoord(Entity entity, Block block1, Double dist) {
        double shortestDistance = -1D;
        double distance = 0D;
        int x = -9999;
        int y = -1;
        int z = -1;

        AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().expand(dist, dist, dist);
        int i = MathHelper.floor_double(axisalignedbb.minX);
        int j = MathHelper.floor_double(axisalignedbb.maxX + 1.0D);
        int k = MathHelper.floor_double(axisalignedbb.minY);
        int l = MathHelper.floor_double(axisalignedbb.maxY + 1.0D);
        int i1 = MathHelper.floor_double(axisalignedbb.minZ);
        int j1 = MathHelper.floor_double(axisalignedbb.maxZ + 1.0D);
        for (int k1 = i; k1 < j; k1++) {
            for (int l1 = k; l1 < l; l1++) {
                for (int i2 = i1; i2 < j1; i2++) {
                    BlockPos pos = new BlockPos(k1, l1, i2);
                    IBlockState blockstate = entity.worldObj.getBlockState(pos);
                    if ((blockstate.getBlock() != Blocks.AIR) && (blockstate.getBlock() == block1)) {
                        distance = getSqDistanceTo(entity, k1, l1, i2);
                        if (shortestDistance == -1D) {
                            x = k1;
                            y = l1;
                            z = i2;
                            shortestDistance = distance;
                        }

                        if (distance < shortestDistance) {
                            x = k1;
                            y = l1;
                            z = i2;
                            shortestDistance = distance;
                        }
                    }
                }
            }
        }

        if (entity.posX > x) {
            x -= 2;
        } else {
            x += 2;
        }
        if (entity.posZ > z) {
            z -= 2;
        } else {
            z += 2;
        }
        return (new int[] {x, y, z});
    }

    public static void MoveCreatureToXYZ(EntityCreature movingEntity, int x, int y, int z, float f) {
        //TODO works?
        PathEntity pathentity = movingEntity.getNavigator().getPathToXYZ(x, y, z);
        if (pathentity != null) {
            movingEntity.getNavigator().setPath(pathentity, f);
        }
    }

    public static void MoveToWater(EntityCreature entity) {
        int ai[] = MoCTools.ReturnNearestMaterialCoord(entity, Material.water, Double.valueOf(20D), 2D);
        if (ai[0] > -1000) {
            MoCTools.MoveCreatureToXYZ(entity, ai[0], ai[1], ai[2], 24F);
        }
    }

    /**
     * Gives angles in the range 0-360 i.e. 361 will be returned like 1
     *
     * @param origAngle
     * @return
     */
    public static float realAngle(float origAngle) {
        return origAngle % 360F;
    }

    public static void SlideEntityToXYZ(Entity entity, int x, int y, int z) {
        if (entity != null) {
            if (entity.posY < y) {
                entity.motionY += 0.14999999999999999D;
            }
            if (entity.posX < x) {
                double d = x - entity.posX;
                if (d > 0.5D) {
                    entity.motionX += 0.050000000000000003D;
                }
            } else {
                double d1 = entity.posX - x;
                if (d1 > 0.5D) {
                    entity.motionX -= 0.050000000000000003D;
                }
            }
            if (entity.posZ < z) {
                double d2 = z - entity.posZ;
                if (d2 > 0.5D) {
                    entity.motionZ += 0.050000000000000003D;
                }
            } else {
                double d3 = entity.posZ - z;
                if (d3 > 0.5D) {
                    entity.motionZ -= 0.050000000000000003D;
                }
            }
        }
    }

    public static float distanceToSurface(Entity entity) {
        int i = MathHelper.floor_double(entity.posX);
        int j = MathHelper.floor_double(entity.posY);
        int k = MathHelper.floor_double(entity.posZ);
        Block block = entity.worldObj.getBlockState(new BlockPos(i, j, k)).getBlock();
        if (block != Blocks.AIR && block.getMaterial() == Material.water) {
            for (int x = 1; x < 64; x++) {
                block = entity.worldObj.getBlockState(new BlockPos(i, j + x, k)).getBlock();
                if (block == Blocks.AIR || block.getMaterial() != Material.water) {
                    return x;
                }
            }
        }
        return 0F;
    }

    public static double waterSurfaceAtGivenPosition(double posX, double posY, double posZ, World worldIn) {
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(posY);
        int k = MathHelper.floor_double(posZ);
        Block block = worldIn.getBlockState(new BlockPos(i, j, k)).getBlock();
        if (block != Blocks.AIR && block.getMaterial() == Material.water) {
            for (int x = 1; x < 64; x++) {
                block = worldIn.getBlockState(new BlockPos(i, j + x, k)).getBlock();
                if (block == Blocks.AIR || block.getMaterial() != Material.water) {
                    return j + x;
                }
            }
        }
        return 0F;
    }

    public static double waterSurfaceAtGivenEntity(Entity entity) {
        return waterSurfaceAtGivenPosition(entity.posX, entity.posY, entity.posZ, entity.worldObj);
    }

    public static float distanceToSurface(double posX, double posY, double posZ, World worldIn) {
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(posY);
        int k = MathHelper.floor_double(posZ);
        Block block = worldIn.getBlockState(new BlockPos(i, j, k)).getBlock();
        if (block != Blocks.AIR && block.getMaterial() == Material.water) {
            for (int x = 1; x < 64; x++) {
                block = worldIn.getBlockState(new BlockPos(i, j + x, k)).getBlock();
                if (block == Blocks.AIR || block.getMaterial() != Material.water) {
                    return x;
                }
            }
        }
        return 0F;
    }

    public static int distanceToFloor(Entity entity) {
        int i = MathHelper.floor_double(entity.posX);
        int j = MathHelper.floor_double(entity.posY);
        int k = MathHelper.floor_double(entity.posZ);
        for (int x = 0; x < 64; x++) {
            Block block = entity.worldObj.getBlockState(new BlockPos(i, j - x, k)).getBlock();
            if (block != Blocks.AIR) {
                return x;
            }
        }

        return 0;
    }

    public boolean isInsideOfMaterial(Material material, Entity entity) {
        double d = entity.posY + entity.getEyeHeight();
        int i = MathHelper.floor_double(entity.posX);
        int j = MathHelper.floor_float(MathHelper.floor_double(d));
        int k = MathHelper.floor_double(entity.posZ);
        BlockPos pos = new BlockPos(i, j, k);
        IBlockState blockstate = entity.worldObj.getBlockState(pos);
        Block block = blockstate.getBlock();
        if (block != Blocks.AIR && block.getMaterial() == material) {
            float f = BlockLiquid.getLiquidHeightPercent(blockstate.getBlock().getMetaFromState(blockstate)) - 0.1111111F;
            float f1 = j + 1 - f;
            return d < f1;
        } else {
            return false;
        }
    }

    public static void disorientEntity(Entity entity) {
        double rotD = 0;
        double motD = 0;
        double d = entity.worldObj.rand.nextGaussian();
        double d1 = 0.1D * d;
        motD = (0.2D * d1) + ((1.0D - 0.2D) * motD);
        entity.motionX += motD;
        entity.motionZ += motD;
        double d2 = 0.78D * d;
        rotD = (0.125D * d2) + ((1.0D - 0.125D) * rotD);
        entity.rotationYaw += rotD;
        entity.rotationPitch += rotD;
    }

    public static void slowEntity(Entity entity) {
        entity.motionX *= 0.8D;
        entity.motionZ *= 0.8D;
    }

    public static int colorize(int i) {
        return ~i & 0xf;
    }

    public int countEntities(Class<? extends EntityLiving> class1, World worldObj) {
        int i = 0;
        for (int j = 0; j < worldObj.loadedEntityList.size(); j++) {
            Entity entity = worldObj.loadedEntityList.get(j);
            if (class1.isAssignableFrom(entity.getClass())) {
                i++;
            }
        }

        return i;
    }

    public static Biome whatBiome(World world, BlockPos pos) {
        WorldChunkManager worldchunkmanager = world.getWorldChunkManager();
        if (worldchunkmanager == null) {
            return null;
        }
        //TODO works?
        Biome Biome = worldchunkmanager.getBiomeGenerator(pos);

        if (Biome == null) {
            return null;
        } else {
            return Biome;
        }
    }

    public static float distToPlayer(Entity entity) {
        //TODO
        return 0.0F;
    }

    public static String BiomeName(World world, BlockPos pos) {
        WorldChunkManager worldchunkmanager = world.getWorldChunkManager();
        if (worldchunkmanager == null) {
            return null;
        }

        Biome Biome = worldchunkmanager.getBiomeGenerator(pos);
        //TODO works?

        if (Biome == null) {
            return null;
        } else {
            return Biome.biomeName;
        }
    }

    public static Biome Biomekind(World world, BlockPos pos) {
        WorldChunkManager worldchunkmanager = world.getWorldChunkManager();
        if (worldchunkmanager == null) {
            return null;
        }
        Biome Biome = worldchunkmanager.getBiomeGenerator(pos);
        if (Biome == null) {
            return null;
        } else {
            return Biome;
        }
    }

    public static void destroyDrops(Entity entity, double d) {

        if (!MoCreatures.proxy.destroyDrops) {
            return;
        }

        List<Entity> list = entity.worldObj.getEntitiesWithinAABBExcludingEntity(entity, entity.getEntityBoundingBox().expand(d, d, d));

        for (int i = 0; i < list.size(); i++) {
            Entity entity1 = (Entity) list.get(i);
            if (!(entity1 instanceof EntityItem)) {
                continue;
            }
            EntityItem entityitem = (EntityItem) entity1;
            if ((entityitem != null) && (entityitem.getAge() < 50)) {
                entityitem.setDead();
            }
        }
    }

    public static void repelMobs(Entity entity1, Double dist, World worldObj) {
        List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(entity1, entity1.getEntityBoundingBox().expand(dist, 4D, dist));
        for (int i = 0; i < list.size(); i++) {
            Entity entity = (Entity) list.get(i);
            if (!(entity instanceof EntityMob)) {
                continue;
            }
            EntityMob entitymob = (EntityMob) entity;
            entitymob.setAttackTarget(null);
            //entitymob.entityLivingToAttack = null;
            entitymob.getNavigator().clearPathEntity();
        }
    }

    /**
     * Drops the important stuff to get going fast
     *
     * @param worldObj
     * @param entity
     */
    public static void dropGoodies(World worldObj, Entity entity) {
        if (!MoCreatures.isServer()) {
            return;
        }

        EntityItem entityitem = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(Blocks.log, 16));
        entityitem.setPickupDelay(10);
        worldObj.spawnEntityInWorld(entityitem);

        EntityItem entityitem2 = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(Items.diamond, 64));
        entityitem2.setPickupDelay(10);
        worldObj.spawnEntityInWorld(entityitem2);

        EntityItem entityitem3 = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(Blocks.pumpkin, 6));
        entityitem3.setPickupDelay(10);
        worldObj.spawnEntityInWorld(entityitem3);

        EntityItem entityitem4 = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(Blocks.cobblestone, 64));
        entityitem4.setPickupDelay(10);
        worldObj.spawnEntityInWorld(entityitem4);

        EntityItem entityitem5 = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(Items.APPLE, 24));
        entityitem5.setPickupDelay(10);
        worldObj.spawnEntityInWorld(entityitem5);

        EntityItem entityitem6 = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(Items.leather, 64));
        entityitem6.setPickupDelay(10);
        worldObj.spawnEntityInWorld(entityitem6);

        EntityItem entityitem7 = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(MoCreatures.recordshuffle, 6));
        entityitem7.setPickupDelay(10);
        worldObj.spawnEntityInWorld(entityitem7);

        EntityItem entityitem8 = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(Items.iron_ingot, 64));
        entityitem8.setPickupDelay(10);
        worldObj.spawnEntityInWorld(entityitem8);

        EntityItem entityitem9 = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(Items.gold_ingot, 12));
        entityitem9.setPickupDelay(10);
        worldObj.spawnEntityInWorld(entityitem9);

        EntityItem entityitem10 = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(Items.string, 32));
        entityitem10.setPickupDelay(10);
        worldObj.spawnEntityInWorld(entityitem10);

        EntityItem entityitem12 = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(Blocks.red_flower, 6));
        entityitem12.setPickupDelay(10);
        worldObj.spawnEntityInWorld(entityitem12);

        EntityItem entityitem13 = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(Items.blaze_rod, 12));
        entityitem13.setPickupDelay(10);
        worldObj.spawnEntityInWorld(entityitem13);

        EntityItem entityitem14 = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(Items.ender_pearl, 12));
        entityitem14.setPickupDelay(10);
        worldObj.spawnEntityInWorld(entityitem14);

        EntityItem entityitem15 = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(Items.ghast_tear, 12));
        entityitem15.setPickupDelay(10);
        worldObj.spawnEntityInWorld(entityitem15);

        EntityItem entityitem16 = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(Blocks.lapis_block, 2));
        entityitem16.setPickupDelay(10);
        worldObj.spawnEntityInWorld(entityitem16);

        EntityItem entityitem17 = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(Items.bone, 12));
        entityitem17.setPickupDelay(10);
        worldObj.spawnEntityInWorld(entityitem17);

        EntityItem entityitem18 = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(MoCreatures.unicornhorn, 16));
        entityitem18.setPickupDelay(10);
        worldObj.spawnEntityInWorld(entityitem18);

        EntityItem entityitem19 = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(Blocks.fire, 32));
        entityitem19.setPickupDelay(10);
        worldObj.spawnEntityInWorld(entityitem19);

        EntityItem entityitem20 = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(MoCreatures.essencedarkness, 6));
        entityitem20.setPickupDelay(10);
        worldObj.spawnEntityInWorld(entityitem20);

        EntityItem entityitem21 = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(MoCreatures.essenceundead, 6));
        entityitem21.setPickupDelay(10);
        worldObj.spawnEntityInWorld(entityitem21);

        EntityItem entityitem22 = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(MoCreatures.essencefire, 6));
        entityitem22.setPickupDelay(10);
        worldObj.spawnEntityInWorld(entityitem22);

        EntityItem entityitem23 =
                new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(Item.getItemFromBlock(Blocks.wool), 6, 15));
        entityitem23.setPickupDelay(10);
        worldObj.spawnEntityInWorld(entityitem23);

    }

    public static boolean mobGriefing(World world) {
        return world.getGameRules().getBoolean("mobGriefing");
    }

    public static void DestroyBlast(Entity entity, double d, double d1, double d2, float f, boolean flag) {
        entity.worldObj.playSoundEffect(d, d1, d2, "mocreatures:destroy", 4F,
                (1.0F + ((entity.worldObj.rand.nextFloat() - entity.worldObj.rand.nextFloat()) * 0.2F)) * 0.7F);

        boolean mobGriefing = mobGriefing(entity.worldObj);

        HashSet<BlockPos> hashset = new HashSet<BlockPos>();
        float f1 = f;
        int i = 16;
        for (int j = 0; j < i; j++) {
            for (int l = 0; l < i; l++) {
                label0: for (int j1 = 0; j1 < i; j1++) {
                    if ((j != 0) && (j != (i - 1)) && (l != 0) && (l != (i - 1)) && (j1 != 0) && (j1 != (i - 1))) {
                        continue;
                    }
                    double d3 = ((j / (i - 1.0F)) * 2.0F) - 1.0F;
                    double d4 = ((l / (i - 1.0F)) * 2.0F) - 1.0F;
                    double d5 = ((j1 / (i - 1.0F)) * 2.0F) - 1.0F;
                    double d6 = Math.sqrt((d3 * d3) + (d4 * d4) + (d5 * d5));
                    d3 /= d6;
                    d4 /= d6;
                    d5 /= d6;
                    float f2 = f * (0.7F + (entity.worldObj.rand.nextFloat() * 0.6F));
                    double d8 = d;
                    double d10 = d1;
                    double d12 = d2;
                    float f3 = 0.3F;
                    float f4 = 5F;
                    do {
                        if (f2 <= 0.0F) {
                            continue label0;
                        }
                        int k5 = MathHelper.floor_double(d8);
                        int l5 = MathHelper.floor_double(d10);
                        int i6 = MathHelper.floor_double(d12);
                        BlockPos pos = new BlockPos(k5, l5, i6);
                        IBlockState blockstate = entity.worldObj.getBlockState(pos);
                        if (blockstate.getBlock() != Blocks.AIR) {
                            f4 = blockstate.getBlock().getBlockHardness(entity.worldObj, pos);
                            f2 -= (blockstate.getBlock().getExplosionResistance(entity) + 0.3F) * (f3 / 10F);
                        }
                        if ((f2 > 0.0F) && (d10 > entity.posY) && (f4 < 3F)) {
                            hashset.add(pos);
                        }
                        d8 += d3 * f3;
                        d10 += d4 * f3;
                        d12 += d5 * f3;
                        f2 -= f3 * 0.75F;
                    } while (true);
                }

            }

        }

        f *= 2.0F;
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
            int k = MathHelper.floor_double(d - f - 1.0D);
            int i1 = MathHelper.floor_double(d + f + 1.0D);
            int k1 = MathHelper.floor_double(d1 - f - 1.0D);
            int l1 = MathHelper.floor_double(d1 + f + 1.0D);
            int i2 = MathHelper.floor_double(d2 - f - 1.0D);
            int j2 = MathHelper.floor_double(d2 + f + 1.0D);
            List<Entity> list = entity.worldObj.getEntitiesWithinAABBExcludingEntity(entity, AxisAlignedBB.fromBounds(k, k1, i2, i1, l1, j2));
            Vec3 vec3d = new Vec3(d, d1, d2);
            for (int k2 = 0; k2 < list.size(); k2++) {
                Entity entity1 = (Entity) list.get(k2);
                double d7 = entity1.getDistance(d, d1, d2) / f;
                if (d7 > 1.0D) {
                    continue;
                }
                double d9 = entity1.posX - d;
                double d11 = entity1.posY - d1;
                double d13 = entity1.posZ - d2;
                double d15 = MathHelper.sqrt_double((d9 * d9) + (d11 * d11) + (d13 * d13));
                d9 /= d15;
                d11 /= d15;
                d13 /= d15;
                double d17 = entity.worldObj.getBlockDensity(vec3d, entity1.getEntityBoundingBox());
                double d19 = (1.0D - d7) * d17;

                //attacks entities in server
                if (!(entity1 instanceof MoCEntityOgre)) {
                    entity1.attackEntityFrom(DamageSource.generic, (int) (((((d19 * d19) + d19) / 2D) * 3D * f) + 1.0D));
                    double d21 = d19;
                    entity1.motionX += d9 * d21;
                    entity1.motionY += d11 * d21;
                    entity1.motionZ += d13 * d21;
                }
            }
        }

        f = f1;
        ArrayList<BlockPos> arraylist = new ArrayList<BlockPos>();
        arraylist.addAll(hashset);

        for (int l2 = arraylist.size() - 1; l2 >= 0; l2--) {
            BlockPos chunkposition = arraylist.get(l2);
            IBlockState blockstate = entity.worldObj.getBlockState(chunkposition);
            for (int j5 = 0; j5 < 5; j5++) {
                double d14 = chunkposition.getX() + entity.worldObj.rand.nextFloat();
                double d16 = chunkposition.getY() + entity.worldObj.rand.nextFloat();
                double d18 = chunkposition.getZ() + entity.worldObj.rand.nextFloat();
                double d20 = d14 - d;
                double d22 = d16 - d1;
                double d23 = d18 - d2;
                double d24 = MathHelper.sqrt_double((d20 * d20) + (d22 * d22) + (d23 * d23));
                d20 /= d24;
                d22 /= d24;
                d23 /= d24;
                double d25 = 0.5D / ((d24 / f) + 0.10000000000000001D);
                d25 *= (entity.worldObj.rand.nextFloat() * entity.worldObj.rand.nextFloat()) + 0.3F;
                d25--;
                d20 *= d25;
                d22 *= d25 - 1.0D;
                d23 *= d25;

                /**
                 * shows explosion on clients!
                 */
                if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
                    entity.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (d14 + (d * 1.0D)) / 2D, (d16 + (d1 * 1.0D)) / 2D,
                            (d18 + (d2 * 1.0D)) / 2D, d20, d22, d23);
                    entity.motionX -= 0.0010000000474974511D;
                    entity.motionY -= 0.0010000000474974511D;
                }

            }

            //destroys blocks on server!
            if (mobGriefing && (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) && blockstate.getBlock() != Blocks.AIR) {
                BlockEvent.BreakEvent event = null;
                if (!entity.worldObj.isRemote) {
                    try {
                        event =
                                new BlockEvent.BreakEvent(entity.worldObj, chunkposition, blockstate, FakePlayerFactory.get(
                                        DimensionManager.getWorld(entity.worldObj.provider.getDimensionId()), MoCreatures.MOCFAKEPLAYER));
                    } catch (Throwable t) {
                    }
                }
                if (event != null && !event.isCanceled()) {
                    blockstate.getBlock().dropBlockAsItemWithChance(entity.worldObj, chunkposition, blockstate, 0.3F, 1);
                    entity.worldObj.setBlockToAir(chunkposition);
                    // pass explosion instance to fix BlockTNT NPE's
                    Explosion explosion =
                            new Explosion(entity.worldObj, null, chunkposition.getX(), chunkposition.getY(), chunkposition.getZ(), 3f, false, false);
                    blockstate.getBlock().onBlockDestroyedByExplosion(entity.worldObj, chunkposition, explosion);
                }
            }
        }

        //sets world on fire on server
        if (mobGriefing && (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) && flag) {
            for (int i3 = arraylist.size() - 1; i3 >= 0; i3--) {
                BlockPos chunkposition1 = arraylist.get(i3);
                IBlockState blockstate = entity.worldObj.getBlockState(chunkposition1);
                if ((blockstate.getBlock() == Blocks.AIR) && (entity.worldObj.rand.nextInt(8) == 0)) {
                    BlockEvent.BreakEvent event = null;
                    if (!entity.worldObj.isRemote) {
                        event =
                                new BlockEvent.BreakEvent(entity.worldObj, chunkposition1, blockstate, FakePlayerFactory.get(
                                        (WorldServer) entity.worldObj, MoCreatures.MOCFAKEPLAYER));
                    }
                    if (event != null && !event.isCanceled()) {
                        entity.worldObj.setBlockState(chunkposition1, Blocks.fire.getDefaultState(), 3);
                    }
                }
            }
        }
    }

    public static int despawnVanillaAnimals(World worldObj) {
        return despawnVanillaAnimals(worldObj, null);
    }

    @SuppressWarnings("rawtypes")
    public static int despawnVanillaAnimals(World worldObj, List[] classList) {
        int count = 0;
        for (int j = 0; j < worldObj.loadedEntityList.size(); j++) {
            Entity entity = (Entity) worldObj.loadedEntityList.get(j);
            if ((entity instanceof EntityLiving)
                    && (entity instanceof EntityCow || entity instanceof EntitySheep || entity instanceof EntityPig
                            || entity instanceof EntityChicken || entity instanceof EntitySquid || entity instanceof EntityWolf)) {
                count += entityDespawnCheck(worldObj, (EntityLiving) entity);
            }
        }
        return count;
    }

    protected static int entityDespawnCheck(World worldObj, EntityLiving entityliving)//to keep a copy
    {
        int count = 0;
        EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(entityliving, -1D);
        if (entityplayer != null) //entityliving.canDespawn() &&
        {
            double d = ((Entity) (entityplayer)).posX - entityliving.posX;
            double d1 = ((Entity) (entityplayer)).posY - entityliving.posY;
            double d2 = ((Entity) (entityplayer)).posZ - entityliving.posZ;
            double d3 = d * d + d1 * d1 + d2 * d2;
            if (d3 > 16384D) {
                entityliving.setDead();
                count++;
            }

            if (entityliving.getAge() > 600 && worldObj.rand.nextInt(800) == 0) {
                if (d3 < 1024D) {
                    //TODO test!
                    entityliving.attackEntityFrom(DamageSource.causeMobDamage(null), 0);
                } else {
                    entityliving.setDead();
                    count++;
                }
            }
        }
        return count;
    }

    //apparently not needed anymore since vanilla does sync mounts?
    /**
     * Forces a data sync between server/client. currently used to syncrhonize
     * mounts
     */
    /*public static void forceDataSync(IMoCEntity entityMoCreature) {
        if (entityMoCreature.updateMount() && ((Entity) entityMoCreature).getRidingEntity() != null) {
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAttachedEntity(((Entity) entityMoCreature).getEntityId(),
                    ((Entity) entityMoCreature).getRidingEntity().getEntityId()), new TargetPoint(
                    ((Entity) entityMoCreature).getRidingEntity().worldObj.provider.getDimensionId(), ((Entity) entityMoCreature).getRidingEntity().posX,
                    ((Entity) entityMoCreature).getRidingEntity().posY, ((Entity) entityMoCreature).getRidingEntity().posZ, 64));
        }
    }*/

    public static void updatePlayerArmorEffects(EntityPlayer player) {
        ItemStack mystack[] = new ItemStack[4];
        mystack[0] = player.inventory.armorItemInSlot(0); //boots
        mystack[1] = player.inventory.armorItemInSlot(1); //legs
        mystack[2] = player.inventory.armorItemInSlot(2); //plate
        mystack[3] = player.inventory.armorItemInSlot(3); //helmet

        //full scorpion cave armor set, enable night vision
        if (mystack[0] != null && mystack[0].getItem() == MoCreatures.scorpBootsCave && mystack[1] != null
                && mystack[1].getItem() == MoCreatures.scorpLegsCave && mystack[2] != null && mystack[2].getItem() == MoCreatures.scorpPlateCave
                && mystack[3] != null && mystack[3].getItem() == MoCreatures.scorpHelmetCave) {
            player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 300, 0));
            return;
        }

        //full scorpion nether armor set, enable fire resistance
        if (mystack[0] != null && mystack[0].getItem() == MoCreatures.scorpBootsNether && mystack[1] != null
                && mystack[1].getItem() == MoCreatures.scorpLegsNether && mystack[2] != null && mystack[2].getItem() == MoCreatures.scorpPlateNether
                && mystack[3] != null && mystack[3].getItem() == MoCreatures.scorpHelmetNether) {
            player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 300, 0));
            return;
        }

        //full scorpion frost armor set, enable water breathing
        if (mystack[0] != null && mystack[0].getItem() == MoCreatures.scorpBootsFrost && mystack[1] != null
                && mystack[1].getItem() == MoCreatures.scorpLegsFrost && mystack[2] != null && mystack[2].getItem() == MoCreatures.scorpPlateFrost
                && mystack[3] != null && mystack[3].getItem() == MoCreatures.scorpHelmetFrost) {
            player.addPotionEffect(new PotionEffect(Potion.waterBreathing.id, 300, 0));
            return;
        }

        //full scorpion armor set, regeneration effect
        if (mystack[0] != null && mystack[0].getItem() == MoCreatures.scorpBootsDirt && mystack[1] != null
                && mystack[1].getItem() == MoCreatures.scorpLegsDirt && mystack[2] != null && mystack[2].getItem() == MoCreatures.scorpPlateDirt
                && mystack[3] != null && mystack[3].getItem() == MoCreatures.scorpHelmetDirt) {
            player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 70, 0));
            return;
        }
    }

    /**
     * Finds a random block around the entity and returns the block's ID will
     * destroy the block in the process the block will be the top one of that
     * layer, without any other block around it
     *
     * @param entity = the Entity around which the block is searched
     * @param distance = the distance around the entity used to look for the
     *        block
     * @return
     */
    public static int destroyRandomBlock(Entity entity, double distance) {
        int l = (int) (distance * distance * distance);
        for (int i = 0; i < l; i++) {
            int x = (int) (entity.posX + entity.worldObj.rand.nextInt((int) (distance)) - (int) (distance / 2));
            int y = (int) (entity.posY + entity.worldObj.rand.nextInt((int) (distance)) - (int) (distance / 4));
            int z = (int) (entity.posZ + entity.worldObj.rand.nextInt((int) (distance)) - (int) (distance / 2));
            BlockPos pos = new BlockPos(MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z));
            IBlockState blockstate = entity.worldObj.getBlockState(pos.up());
            IBlockState blockstate1 = entity.worldObj.getBlockState(pos);

            if (blockstate.getBlock() != Blocks.AIR && blockstate1.getBlock() == Blocks.AIR) {
                if (mobGriefing(entity.worldObj)) {
                    blockstate1 = entity.worldObj.getBlockState(pos);
                    BlockEvent.BreakEvent event = null;
                    if (!entity.worldObj.isRemote) {
                        event =
                                new BlockEvent.BreakEvent(entity.worldObj, pos.up(), blockstate, FakePlayerFactory.get((WorldServer) entity.worldObj,
                                        MoCreatures.MOCFAKEPLAYER));
                    }
                    if (event != null && !event.isCanceled()) {
                        entity.worldObj.setBlockToAir(pos);
                    }
                }
                return Block.getIdFromBlock(blockstate.getBlock());
            }
        }
        return 0;
    }

    public static IBlockState destroyRandomBlockWithIBlockState(Entity entity, double distance) {
        int l = (int) (distance * distance * distance);
        for (int i = 0; i < l; i++) {
            int x = (int) (entity.posX + entity.worldObj.rand.nextInt((int) (distance)) - (int) (distance / 2));
            int y = (int) (entity.posY + entity.worldObj.rand.nextInt((int) (distance)) - (int) (distance / 2));
            int z = (int) (entity.posZ + entity.worldObj.rand.nextInt((int) (distance)) - (int) (distance / 2));
            BlockPos pos = new BlockPos(MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z));
            IBlockState stateAbove = entity.worldObj.getBlockState(pos.up());
            IBlockState stateTarget = entity.worldObj.getBlockState(pos);

            if (pos.getY() == (int) entity.posY - 1D && (pos.getX() == (int) Math.floor(entity.posX) && pos.getZ() == (int) Math.floor(entity.posZ))) {
                continue;
            }
            if (stateTarget.getBlock() != Blocks.AIR && stateTarget.getBlock() != Blocks.water && stateTarget.getBlock() != Blocks.bedrock
                    && stateAbove.getBlock() == Blocks.AIR) // ignore bedrock
            {
                if (mobGriefing(entity.worldObj)) {
                    BlockEvent.BreakEvent event = null;
                    if (!entity.worldObj.isRemote) {
                        event =
                                new BlockEvent.BreakEvent(entity.worldObj, pos, stateTarget, FakePlayerFactory.get((WorldServer) entity.worldObj,
                                        MoCreatures.MOCFAKEPLAYER));
                    }
                    if (event != null && !event.isCanceled()) {
                        entity.worldObj.setBlockToAir(pos);

                    } else {
                        stateTarget = null;
                    }
                }
                if (stateTarget != null) {
                    return stateTarget;
                }
            }
        }
        return null;
    }

    /**
     * Finds a random block around the entity and returns the coordinates the
     * block will be the top one of that layer, without any other block around
     * it
     *
     * @param entity = the Entity around which the block is searched
     * @param distance = the distance around the entity used to look for the
     *        block
     * @return
     */
    public static int[] getRandomBlockCoords(Entity entity, double distance) {
        int tempX = -9999;
        int tempY = -1;
        int tempZ = -1;
        int ii = (int) (distance * distance * (distance / 2));
        for (int i = 0; i < ii; i++) {
            int x = (int) (entity.posX + entity.worldObj.rand.nextInt((int) (distance)) - (int) (distance / 2));
            int y = (int) (entity.posY + entity.worldObj.rand.nextInt((int) (distance / 2)) - (int) (distance / 4));
            int z = (int) (entity.posZ + entity.worldObj.rand.nextInt((int) (distance)) - (int) (distance / 2));
            BlockPos pos = new BlockPos(MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z));
            IBlockState blockstate1 = entity.worldObj.getBlockState(pos.up()); // +Y
            IBlockState blockstate2 = entity.worldObj.getBlockState(pos);
            IBlockState blockstate3 = entity.worldObj.getBlockState(pos.east()); // +X
            IBlockState blockstate4 = entity.worldObj.getBlockState(pos.west()); // -X
            IBlockState blockstate5 = entity.worldObj.getBlockState(pos.down()); // -Y
            IBlockState blockstate6 = entity.worldObj.getBlockState(pos.south()); // -Z
            IBlockState blockstate7 = entity.worldObj.getBlockState(pos.north()); // +Z

            float tX = x - (float) entity.posX;
            float tY = y - (float) entity.posY;
            float tZ = z - (float) entity.posZ;
            float spawnDist = tX * tX + tY * tY + tZ * tZ;

            if (allowedBlock(Block.getIdFromBlock(blockstate1.getBlock()))
                    && (blockstate2.getBlock() == Blocks.AIR || blockstate3.getBlock() == Blocks.AIR || blockstate4.getBlock() == Blocks.AIR
                            || blockstate5.getBlock() == Blocks.AIR || blockstate6.getBlock() == Blocks.AIR || blockstate7.getBlock() == Blocks.AIR)
                    & spawnDist > 100F) {
                tempX = x;
                tempY = y;
                tempZ = z;
                break;
            }
        }
        return (new int[] {tempX, tempY, tempZ});
    }

    public static BlockPos getRandomBlockPos(Entity entity, double distance) {
        int tempX = -9999;
        int tempY = -1;
        int tempZ = -1;
        int ii = (int) (distance * distance * (distance / 2));
        for (int i = 0; i < ii; i++) {
            int x = (int) (entity.posX + entity.worldObj.rand.nextInt((int) (distance)) - (int) (distance / 2));
            int y = (int) (entity.posY + entity.worldObj.rand.nextInt((int) (distance / 2)) - (int) (distance / 4));
            int z = (int) (entity.posZ + entity.worldObj.rand.nextInt((int) (distance)) - (int) (distance / 2));
            BlockPos pos = new BlockPos(MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z));
            IBlockState blockstate1 = entity.worldObj.getBlockState(pos.up()); // +Y
            IBlockState blockstate2 = entity.worldObj.getBlockState(pos);
            IBlockState blockstate3 = entity.worldObj.getBlockState(pos.east()); // +X
            IBlockState blockstate4 = entity.worldObj.getBlockState(pos.west()); // -X
            IBlockState blockstate5 = entity.worldObj.getBlockState(pos.down()); // -Y
            IBlockState blockstate6 = entity.worldObj.getBlockState(pos.south()); // -Z
            IBlockState blockstate7 = entity.worldObj.getBlockState(pos.north()); // +Z

            float tX = x - (float) entity.posX;
            float tY = y - (float) entity.posY;
            float tZ = z - (float) entity.posZ;
            float spawnDist = tX * tX + tY * tY + tZ * tZ;

            if (allowedBlock(Block.getIdFromBlock(blockstate1.getBlock()))
                    && (blockstate2.getBlock() == Blocks.AIR || blockstate3.getBlock() == Blocks.AIR || blockstate4.getBlock() == Blocks.AIR
                            || blockstate5.getBlock() == Blocks.AIR || blockstate6.getBlock() == Blocks.AIR || blockstate7.getBlock() == Blocks.AIR)
                    & spawnDist > 100F) {
                tempX = x;
                tempY = y;
                tempZ = z;
                break;
            }
        }
        return new BlockPos(MathHelper.floor_double(tempX), MathHelper.floor_double(tempY), MathHelper.floor_double(tempZ));
    }

    public static boolean allowedBlock(int ID) {
        return ID != 0 && ID != 7 //bedrock
                && ID != 8 //water
                && ID != 9 //water
                && ID != 10//lava
                && ID != 11//lava
                && ID != 23//dispenser
                && ID != 37//plant
                && ID != 38//plant
                && ID != 50//torch
                && ID != 51//fire
                && ID != 54//chest
                && (ID < 63 || ID > 77) && ID != 95//lockedchest
                && ID != 90//portal
                && ID != 93//redstone
                && ID != 94//redstone
                && ID < 134;//the rest
    }

    /**
     * Method called to tame an entity, it will check that the player has slots
     * for taming, increase the taming count of the player, add the
     * player.getName() as the owner of the entity, and name the entity.
     *
     * @param ep
     * @param storedCreature
     * @return
     */
    public static boolean tameWithName(EntityPlayer ep, IMoCTameable storedCreature) {
        if (ep == null) {
            return false;
        }

        if (MoCreatures.proxy.enableOwnership) {
            if (storedCreature == null) {
                ep.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.RED + "ERROR:" + EnumChatFormatting.WHITE
                        + "The stored creature is NULL and could not be created. Report to admin."));
                return false;
            }
            int max = 0;
            max = MoCreatures.proxy.maxTamed;
            // only check count for new pets as owners may be changing the name
            if (!MoCreatures.instance.mapData.isExistingPet(ep.getName(), storedCreature)) {
                int count = MoCTools.numberTamedByPlayer(ep);
                if (isThisPlayerAnOP(ep)) {
                    max = MoCreatures.proxy.maxOPTamed;
                }
                if (count >= max) {
                    String message = "\2474" + ep.getName() + " can not tame more creatures, limit of " + max + " reached";
                    ep.addChatMessage(new ChatComponentTranslation(message));
                    return false;
                }
            }
        }

        storedCreature.setOwner(ep.getName()); // ALWAYS SET OWNER. Required for our new pet save system.
        MoCMessageHandler.INSTANCE.sendTo(new MoCMessageNameGUI(((Entity) storedCreature).getEntityId()), (EntityPlayerMP) ep);
        storedCreature.setTamed(true);
        // Required to update petId data for pet amulets
        if (MoCreatures.instance.mapData != null && storedCreature.getOwnerPetId() == -1) {
            MoCreatures.instance.mapData.updateOwnerPet(storedCreature);
        }
        return true;
    }

    public static int numberTamedByPlayer(EntityPlayer ep) {
        if (MoCreatures.instance.mapData != null) {
            if (MoCreatures.instance.mapData.getPetData(ep.getName()) != null) {
                return MoCreatures.instance.mapData.getPetData(ep.getName()).getTamedList().tagCount();
            }
        }
        return 0;
    }

    /**
     * Destroys blocks in front of entity
     *
     * @param entity
     * @param distance: used to calculate the distance where the target block is
     *        located
     * @param strength: int 1 - 3. Checked against block hardness, also used to
     *        calculate how many blocks are recovered
     * @param height: how many rows of blocks are destroyed in front of the
     *        entity
     * @return the count of blocks destroyed
     */
    public static int destroyBlocksInFront(Entity entity, double distance, int strength, int height) {
        if (strength == 0) {
            return 0;
        }
        int count = 0;
        float strengthF = strength;
        double newPosX = entity.posX - (distance * Math.cos((MoCTools.realAngle(entity.rotationYaw - 90F)) / 57.29578F));
        double newPosZ = entity.posZ - (distance * Math.sin((MoCTools.realAngle(entity.rotationYaw - 90F)) / 57.29578F));
        double newPosY = entity.posY;
        int x = MathHelper.floor_double(newPosX);
        int y = MathHelper.floor_double(newPosY);
        int z = MathHelper.floor_double(newPosZ);

        for (int i = 0; i < height; i++) {
            BlockPos pos = new BlockPos(x, y + i, z);
            IBlockState blockstate = entity.worldObj.getBlockState(pos);
            if (blockstate.getBlock() != Blocks.AIR) {
                if (blockstate.getBlock().getBlockHardness(entity.worldObj, pos) <= strengthF) {
                    BlockEvent.BreakEvent event = null;
                    if (!entity.worldObj.isRemote) {
                        event =
                                new BlockEvent.BreakEvent(entity.worldObj, pos, blockstate, FakePlayerFactory.get((WorldServer) entity.worldObj,
                                        MoCreatures.MOCFAKEPLAYER));
                    }
                    if (event != null && !event.isCanceled()) {
                        blockstate.getBlock().dropBlockAsItemWithChance(entity.worldObj, pos, blockstate, 0.20F * strengthF, 1);
                        entity.worldObj.setBlockToAir(pos);
                        if (entity.worldObj.rand.nextInt(3) == 0) {
                            MoCTools.playCustomSound(entity, "golemwalk", entity.worldObj);
                            count++; //only counts recovered blocks
                        }
                    }
                }
            }
        }
        return count;
    }

    public static void dropInventory(Entity entity, MoCAnimalChest animalchest) {
        if (animalchest == null || !(MoCreatures.isServer())) {
            return;
        }

        int i = MathHelper.floor_double(entity.posX);
        int j = MathHelper.floor_double(entity.getEntityBoundingBox().minY);
        int k = MathHelper.floor_double(entity.posZ);

        for (int l = 0; l < animalchest.getSizeInventory(); l++) {
            ItemStack itemstack = animalchest.getStackInSlot(l);
            if (itemstack == null) {
                continue;
            }
            float f = (entity.worldObj.rand.nextFloat() * 0.8F) + 0.1F;
            float f1 = (entity.worldObj.rand.nextFloat() * 0.8F) + 0.1F;
            float f2 = (entity.worldObj.rand.nextFloat() * 0.8F) + 0.1F;
            float f3 = 0.05F;

            EntityItem entityitem = new EntityItem(entity.worldObj, i + f, j + f1, k + f2, itemstack);
            entityitem.motionX = ((float) entity.worldObj.rand.nextGaussian() * f3);
            entityitem.motionY = (((float) entity.worldObj.rand.nextGaussian() * f3) + 0.2F);
            entityitem.motionZ = ((float) entity.worldObj.rand.nextGaussian() * f3);
            entity.worldObj.spawnEntityInWorld(entityitem);
            animalchest.setInventorySlotContents(l, null);
        }
    }

    /**
     * Drops an amulet with the stored information of the entity passed
     *
     * @param entity
     */
    public static void dropHorseAmulet(MoCEntityTameableAnimal entity) {
        if (MoCreatures.isServer()) {
            ItemStack stack = getProperAmulet(entity);
            if (stack == null) {
                return;
            }
            if (stack.getTagCompound() == null) {
                stack.setTagCompound(new NBTTagCompound());
            }
            NBTTagCompound nbtt = stack.getTagCompound();

            try {
                nbtt.setInteger("SpawnClass", 21);
                nbtt.setFloat("Health", entity.getHealth());
                nbtt.setInteger("Edad", entity.getEdad());
                nbtt.setString("Name", entity.getPetName());
                nbtt.setBoolean("Rideable", entity.getIsRideable());
                nbtt.setByte("Armor", entity.getArmorType());
                nbtt.setInteger("CreatureType", entity.getType());
                nbtt.setBoolean("Adult", entity.getIsAdult());
                nbtt.setString("OwnerName", entity.getOwnerName());
                nbtt.setInteger("PetId", entity.getOwnerPetId());
            } catch (Exception e) {
            }

            EntityPlayer epOwner = entity.worldObj.getPlayerEntityByName(entity.getOwnerName());

            if (epOwner != null && epOwner.inventory.getFirstEmptyStack() != -1) // don't attempt to set if player inventory is full
            {
                epOwner.inventory.addItemStackToInventory(stack);
            } else {
                EntityItem entityitem = new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, stack);
                entityitem.setPickupDelay(20);
                entity.worldObj.spawnEntityInWorld(entityitem);
            }
        }
    }

    /**
     * Drops a new amulet/fishnet with the stored information of the entity
     */
    public static void dropAmulet(IMoCTameable entity, int amuletType) {
        if (MoCreatures.isServer()) {
            ItemStack stack = new ItemStack(MoCreatures.fishnetfull, 1, 0);
            if (amuletType == 2) {
                stack = new ItemStack(MoCreatures.petamuletfull, 1, 0);
            }
            if (amuletType == 3) {
                stack = new ItemStack(MoCreatures.amuletghostfull, 1, 0);
            }
            if (stack.getTagCompound() == null) {
                stack.setTagCompound(new NBTTagCompound());
            }
            NBTTagCompound nbtt = stack.getTagCompound();

            try {
                String petClass = entity.getClass().getSimpleName().replace("MoCEntity", "");
                if (petClass.equalsIgnoreCase("Horse")) {
                    petClass = "WildHorse";
                } else if (petClass.equalsIgnoreCase("Komodo")) {
                    petClass = "KomodoDragon";
                }
                if (amuletType == 3) {
                    nbtt.setInteger("SpawnClass", 100);
                } else {
                    nbtt.setString("SpawnClass", petClass);
                }
                nbtt.setFloat("Health", ((EntityLiving) entity).getHealth());
                nbtt.setInteger("Edad", entity.getEdad());
                nbtt.setString("Name", entity.getPetName());
                nbtt.setInteger("CreatureType", entity.getType());
                nbtt.setString("OwnerName", entity.getOwnerName());
                nbtt.setBoolean("Adult", entity.getIsAdult());
                nbtt.setInteger("PetId", entity.getOwnerPetId());
            } catch (Exception e) {
                e.printStackTrace();
            }

            EntityPlayer epOwner = ((EntityLivingBase) entity).worldObj.getPlayerEntityByName(entity.getOwnerName());
            if (epOwner != null) {
                epOwner.inventory.addItemStackToInventory(stack);
            } else {
                EntityItem entityitem =
                        new EntityItem(((EntityLivingBase) entity).worldObj, ((EntityLivingBase) entity).posX, ((EntityLivingBase) entity).posY,
                                ((EntityLivingBase) entity).posZ, stack);
                entityitem.setPickupDelay(20);
                ((EntityLivingBase) entity).worldObj.spawnEntityInWorld(entityitem);
            }
        }
    }

    /**
     * Returns the right full amulet based on the MoCEntityAnimal passed
     *
     * @param entity
     * @return
     */
    public static ItemStack getProperAmulet(MoCEntityAnimal entity) {
        if (entity instanceof MoCEntityHorse) {
            if (entity.getType() == 26 || entity.getType() == 27 || entity.getType() == 28) {
                return new ItemStack(MoCreatures.amuletbonefull, 1, 0);
            }
            if (entity.getType() > 47 && entity.getType() < 60) {
                return new ItemStack(MoCreatures.amuletfairyfull, 1, 0);
            }
            if (entity.getType() == 39 || entity.getType() == 40) {
                return new ItemStack(MoCreatures.amuletpegasusfull, 1, 0);
            }
            if (entity.getType() == 21 || entity.getType() == 22) {
                return new ItemStack(MoCreatures.amuletghostfull, 1, 0);
            }
        }
        return null;
    }

    /**
     * Returns the right full empty based on the MoCEntityAnimal passed. Used
     * when the amulet empties its contents
     *
     * @param entity
     * @return
     */
    public static ItemStack getProperEmptyAmulet(MoCEntityAnimal entity) {
        if (entity instanceof MoCEntityHorse) {
            if (entity.getType() == 26 || entity.getType() == 27 || entity.getType() == 28) {
                return new ItemStack(MoCreatures.amuletbone, 1, 0);
            }
            if (entity.getType() > 49 && entity.getType() < 60) {
                return new ItemStack(MoCreatures.amuletfairy, 1, 0);
            }
            if (entity.getType() == 39 || entity.getType() == 40) {
                return new ItemStack(MoCreatures.amuletpegasus, 1, 0);
            }
            if (entity.getType() == 21 || entity.getType() == 22) {
                return new ItemStack(MoCreatures.amuletghost, 1, 0);
            }
        }
        return null;
    }

    public static int countPlayersInDimension(WorldServer worldObj, int dimension) {
        int playersInDimension = 0;
        for (int j = 0; j < worldObj.playerEntities.size(); ++j) {
            EntityPlayerMP entityplayermp = (EntityPlayerMP) worldObj.playerEntities.get(j);

            if (entityplayermp.dimension == dimension) {
                playersInDimension++;
            }
        }
        return playersInDimension;
    }

    public static boolean isThisPlayerAnOP(EntityPlayer player) {
        if (!MoCreatures.isServer()) {
            return false;
        }

        return FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().canSendCommands(player.getGameProfile());
    }

    public static void spawnMaggots(World worldObj, Entity entity) {
        if (MoCreatures.isServer()) {
            int var2 = 1 + worldObj.rand.nextInt(4);
            for (int i = 0; i < var2; ++i) {
                float var4 = (i % 2 - 0.5F) * 1 / 4.0F;
                float var5 = (i / 2 - 0.5F) * 1 / 4.0F;
                MoCEntityMaggot maggot = new MoCEntityMaggot(worldObj);
                maggot.setLocationAndAngles(entity.posX + var4, entity.posY + 0.5D, entity.posZ + var5, worldObj.rand.nextFloat() * 360.0F, 0.0F);
                worldObj.spawnEntityInWorld(maggot);
            }
        }
    }

    public static void getPathToEntity(EntityLiving creatureToMove, Entity entityTarget, float f) {
        PathEntity pathentity = creatureToMove.getNavigator().getPathToEntityLiving(entityTarget);
        if (pathentity != null && f < 12F) {
            creatureToMove.getNavigator().setPath(pathentity, 1.0D); //TODO check if 1.0D is proper speed.
        }
    }

    public static void runLikeHell(EntityLiving runningEntity, Entity boogey) {
        double d = runningEntity.posX - boogey.posX;
        double d1 = runningEntity.posZ - boogey.posZ;
        double d2 = Math.atan2(d, d1);
        d2 += (runningEntity.worldObj.rand.nextFloat() - runningEntity.worldObj.rand.nextFloat()) * 0.75D;
        double d3 = runningEntity.posX + (Math.sin(d2) * 8D);
        double d4 = runningEntity.posZ + (Math.cos(d2) * 8D);
        int i = MathHelper.floor_double(d3);
        int j = MathHelper.floor_double(runningEntity.getEntityBoundingBox().minY);
        int k = MathHelper.floor_double(d4);
        int l = 0;
        do {
            if (l >= 16) {
                break;
            }
            int i1 = (i + runningEntity.worldObj.rand.nextInt(4)) - runningEntity.worldObj.rand.nextInt(4);
            int j1 = (j + runningEntity.worldObj.rand.nextInt(3)) - runningEntity.worldObj.rand.nextInt(3);
            int k1 = (k + runningEntity.worldObj.rand.nextInt(4)) - runningEntity.worldObj.rand.nextInt(4);
            BlockPos pos = new BlockPos(i1, j1, k1);
            if ((j1 > 4)
                    && ((runningEntity.worldObj.getBlockState(pos).getBlock() == Blocks.AIR) || (runningEntity.worldObj.getBlockState(pos).getBlock() == Blocks.snow))
                    && (runningEntity.worldObj.getBlockState(pos.down()).getBlock() != Blocks.AIR)) {
                runningEntity.getNavigator().tryMoveToXYZ(i1, j1, k1, 1.0D);//TODO check if 1D speed is ok
                break;
            }
            l++;
        } while (true);
    }

    /**
     * Finds a near vulnerable player and poisons it if the player is in the
     * water and not riding anything
     *
     * @param poisoner
     * @param needsToBeInWater: the target needs to be in water for poison to be
     *        successful?
     * @return true if was able to poison the player
     */
    public static boolean findNearPlayerAndPoison(Entity poisoner, boolean needsToBeInWater) {
        EntityPlayer entityplayertarget = poisoner.worldObj.getClosestPlayerToEntity(poisoner, 2D);
        if (entityplayertarget != null && ((needsToBeInWater && entityplayertarget.isInWater()) || !needsToBeInWater)
                && poisoner.getDistanceToEntity(entityplayertarget) < 2.0F) {
            if (entityplayertarget.getRidingEntity() != null && entityplayertarget.getRidingEntity() instanceof EntityBoat) {
                //don't poison players on boats
            } else {
                MoCreatures.poisonPlayer(entityplayertarget);
                entityplayertarget.addPotionEffect(new PotionEffect(Potion.poison.id, 120, 0));
                return true;
            }
        }
        return false;
    }

    public static boolean isTamed(Entity entity) {
        if (entity instanceof EntityTameable) {
            if (((EntityTameable) entity).isTamed()) {
                return true;
            }
        }
        NBTTagCompound nbt = new NBTTagCompound();
        entity.writeToNBT(nbt);
        if (nbt != null) {
            if (nbt.hasKey("Owner") && !nbt.getString("Owner").equals("")) {
                return true; // ignore
            }
            if (nbt.hasKey("Tamed") && nbt.getBoolean("Tamed") == true) {
                return true; // ignore
            }
        }
        return false;
    }

    /**
     * Throws stone at entity
     *
     * @param targetEntity
     * @param rocktype
     * @param metadata
     */
    public static void ThrowStone(Entity throwerEntity, Entity targetEntity, IBlockState state, double speedMod, double height) {
        ThrowStone(throwerEntity, (int) targetEntity.posX, (int) targetEntity.posY, (int) targetEntity.posZ, state, speedMod, height);
    }

    /**
     * Throws stone at X,Y,Z coordinates
     *
     * @param X
     * @param Y
     * @param Z
     * @param rocktype
     * @param metadata
     */
    public static void ThrowStone(Entity throwerEntity, int X, int Y, int Z, IBlockState state) {
        ThrowStone(throwerEntity, X, Y, Z, state, 10D, 0.25D);
        /*MoCEntityThrowableRock etrock = new MoCEntityThrowableRock(throwerEntity.worldObj, throwerEntity, throwerEntity.posX, throwerEntity.posY + 0.5D, throwerEntity.posZ);//, false, false);
        throwerEntity.worldObj.spawnEntityInWorld(etrock);
        etrock.setState(state);
        etrock.setBehavior(0);
        etrock.motionX = ((X - throwerEntity.posX) / 10.0D);
        etrock.motionY = ((Y - throwerEntity.posY) / 10.0D + 0.25D);
        etrock.motionZ = ((Z - throwerEntity.posZ) / 10.0D);*/
    }

    public static void ThrowStone(Entity throwerEntity, int X, int Y, int Z, IBlockState state, double speedMod, double height) {
        MoCEntityThrowableRock etrock =
                new MoCEntityThrowableRock(throwerEntity.worldObj, throwerEntity, throwerEntity.posX, throwerEntity.posY + 0.5D, throwerEntity.posZ);//, false, false);
        throwerEntity.worldObj.spawnEntityInWorld(etrock);
        etrock.setState(state);
        etrock.setBehavior(0);
        etrock.motionX = ((X - throwerEntity.posX) / speedMod);
        etrock.motionY = ((Y - throwerEntity.posY) / speedMod + height);
        etrock.motionZ = ((Z - throwerEntity.posZ) / speedMod);
    }

    /**
     * Calculates the moving speed of the entity
     * @param entity
     * @return
     */
    public static float getMyMovementSpeed(Entity entity) {
        return MathHelper.sqrt_double((entity.motionX * entity.motionX) + (entity.motionZ * entity.motionZ));
    }

    public static EntityItem getClosestFood(Entity entity, double d) {
        double d1 = -1D;
        EntityItem entityitem = null;
        List<Entity> list = entity.worldObj.getEntitiesWithinAABBExcludingEntity(entity, entity.getEntityBoundingBox().expand(d, d, d));
        for (int k = 0; k < list.size(); k++) {
            Entity entity1 = (Entity) list.get(k);
            if (!(entity1 instanceof EntityItem)) {
                continue;
            }
            EntityItem entityitem1 = (EntityItem) entity1;
            if (!isItemEdible(entityitem1.getEntityItem().getItem())) {
                continue;
            }
            double d2 = entityitem1.getDistanceSq(entity.posX, entity.posY, entity.posZ);
            if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1))) {
                d1 = d2;
                entityitem = entityitem1;
            }
        }

        return entityitem;
    }

    /**
     * List of edible foods
     *
     * @param item1
     * @return
     */
    public static boolean isItemEdible(Item item1) {
        return (item1 instanceof ItemFood) || (item1 instanceof ItemSeeds) || item1 == Items.wheat || item1 == Items.sugar || item1 == Items.cake
                || item1 == Items.egg;
    }

    public static NBTTagCompound getEntityData(Entity entity) {
        if (!entity.getEntityData().hasKey(MoCConstants.MOD_ID)) {
            entity.getEntityData().setTag(MoCConstants.MOD_ID, new NBTTagCompound());
        }

        return entity.getEntityData().getCompoundTag(MoCConstants.MOD_ID);
    }

    public static void findMobRider(Entity mountEntity) {
        List list = mountEntity.worldObj.getEntitiesWithinAABBExcludingEntity(mountEntity, mountEntity.getEntityBoundingBox().expand(4D, 2D, 4D));
        for (int i = 0; i < list.size(); i++) {
            Entity entity = (Entity) list.get(i);
            if (!(entity instanceof EntityMob)) {
                continue;
            }
            EntityMob entitymob = (EntityMob) entity;
            if (entitymob.getRidingEntity() == null
                    && (entitymob instanceof EntitySkeleton || entitymob instanceof EntityZombie || entitymob instanceof MoCEntitySilverSkeleton)) {
                entitymob.mountEntity(mountEntity);
                break;
            }
        }
    }
}
