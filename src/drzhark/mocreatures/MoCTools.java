package drzhark.mocreatures;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFluid;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntityRecordPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.storage.SaveHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.server.FMLServerHandler;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import drzhark.mocreatures.entity.MoCIMoCreature;
import drzhark.mocreatures.entity.monster.MoCEntityFlameWraith;
import drzhark.mocreatures.entity.monster.MoCEntityGolem;
import drzhark.mocreatures.entity.monster.MoCEntityHorseMob;
import drzhark.mocreatures.entity.monster.MoCEntityOgre;
import drzhark.mocreatures.entity.monster.MoCEntityRat;
import drzhark.mocreatures.entity.monster.MoCEntityScorpion;
import drzhark.mocreatures.entity.monster.MoCEntityWWolf;
import drzhark.mocreatures.entity.monster.MoCEntityWerewolf;
import drzhark.mocreatures.entity.monster.MoCEntityWraith;
import drzhark.mocreatures.entity.passive.MoCEntityBear;
import drzhark.mocreatures.entity.passive.MoCEntityBee;
import drzhark.mocreatures.entity.passive.MoCEntityBigCat;
import drzhark.mocreatures.entity.passive.MoCEntityBird;
import drzhark.mocreatures.entity.passive.MoCEntityBoar;
import drzhark.mocreatures.entity.passive.MoCEntityBunny;
import drzhark.mocreatures.entity.passive.MoCEntityButterfly;
import drzhark.mocreatures.entity.passive.MoCEntityCrab;
import drzhark.mocreatures.entity.passive.MoCEntityCricket;
import drzhark.mocreatures.entity.passive.MoCEntityCrocodile;
import drzhark.mocreatures.entity.passive.MoCEntityDeer;
import drzhark.mocreatures.entity.passive.MoCEntityDolphin;
import drzhark.mocreatures.entity.passive.MoCEntityDragonfly;
import drzhark.mocreatures.entity.passive.MoCEntityDuck;
import drzhark.mocreatures.entity.passive.MoCEntityElephant;
import drzhark.mocreatures.entity.passive.MoCEntityFirefly;
import drzhark.mocreatures.entity.passive.MoCEntityFishy;
import drzhark.mocreatures.entity.passive.MoCEntityFly;
import drzhark.mocreatures.entity.passive.MoCEntityFox;
import drzhark.mocreatures.entity.passive.MoCEntityGoat;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.entity.passive.MoCEntityJellyFish;
import drzhark.mocreatures.entity.passive.MoCEntityKitty;
import drzhark.mocreatures.entity.passive.MoCEntityKomodo;
import drzhark.mocreatures.entity.passive.MoCEntityMaggot;
import drzhark.mocreatures.entity.passive.MoCEntityMouse;
import drzhark.mocreatures.entity.passive.MoCEntityOstrich;
import drzhark.mocreatures.entity.passive.MoCEntityRay;
import drzhark.mocreatures.entity.passive.MoCEntityShark;
import drzhark.mocreatures.entity.passive.MoCEntitySnail;
import drzhark.mocreatures.entity.passive.MoCEntitySnake;
import drzhark.mocreatures.entity.passive.MoCEntityTurkey;
import drzhark.mocreatures.entity.passive.MoCEntityTurtle;
import drzhark.mocreatures.entity.passive.MoCEntityWyvern;
import drzhark.mocreatures.inventory.MoCAnimalChest;
import drzhark.mocreatures.network.MoCServerPacketHandler;

public class MoCTools {
    public MoCTools()
    {

    }

    /**
     * spawns tiny slimes
     */
    public static void spawnSlimes(World worldObj, Entity entity)
    {
        if (MoCreatures.isServer())
        {
            //changed so it only spawns 0 - 1 slime, as it now spaws also big slimes
            int var2 = 1 + worldObj.rand.nextInt(1);

            for (int i = 0; i < var2; ++i)
            {
                float var4 = ((float) (i % 2) - 0.5F) * (float) 1 / 4.0F;
                float var5 = ((float) (i / 2) - 0.5F) * (float) 1 / 4.0F;
                EntitySlime var6 = new EntitySlime(worldObj);
                //var6.setSlimeSize(1);  TODO FIX
                var6.setLocationAndAngles(entity.posX + (double) var4, entity.posY + 0.5D, entity.posZ + (double) var5, worldObj.rand.nextFloat() * 360.0F, 0.0F);
                worldObj.spawnEntityInWorld(var6);
            }
        }
    }

    
    
    /**
     * Drops saddle
     */
    public static void dropSaddle(MoCEntityAnimal entity, World worldObj)
    {
        if (!entity.getIsRideable() || !MoCreatures.isServer()) { return; }
        dropCustomItem(entity, worldObj, new ItemStack(MoCreatures.horsesaddle, 1));
        /*EntityItem entityitem = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(MoCreatures.horsesaddle, 1));
        float f3 = 0.05F;
        entityitem.motionX = (float) worldObj.rand.nextGaussian() * f3;
        entityitem.motionY = ((float) worldObj.rand.nextGaussian() * f3) + 0.2F;
        entityitem.motionZ = (float) worldObj.rand.nextGaussian() * f3;
        worldObj.spawnEntityInWorld(entityitem);*/
        entity.setRideable(false);
    }

    /**
     * Drops chest block
     */
    public static void dropBags(MoCEntityAnimal entity, World worldObj)
    {
        if (!MoCreatures.isServer()) { return; }
        dropCustomItem(entity, worldObj, new ItemStack(Block.chest, 1));
/*        EntityItem entityitem = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(Block.chest, 1));
        float f3 = 0.05F;
        entityitem.motionX = (float) worldObj.rand.nextGaussian() * f3;
        entityitem.motionY = ((float) worldObj.rand.nextGaussian() * f3) + 0.2F;
        entityitem.motionZ = (float) worldObj.rand.nextGaussian() * f3;
        worldObj.spawnEntityInWorld(entityitem);*/
    }
    
    /**
     * Drops item
     */
    public static void dropCustomItem(Entity entity, World worldObj, ItemStack itemstack)
    {
        if (!MoCreatures.isServer()) { return; }

        EntityItem entityitem = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, itemstack);
        float f3 = 0.05F;
        entityitem.motionX = (float) worldObj.rand.nextGaussian() * f3;
        entityitem.motionY = ((float) worldObj.rand.nextGaussian() * f3) + 0.2F;
        entityitem.motionZ = (float) worldObj.rand.nextGaussian() * f3;
        worldObj.spawnEntityInWorld(entityitem);
    }
    

    public static void bigsmack(Entity entity, Entity entity1, float force)
    {
        double d = entity.posX - entity1.posX;
        double d1 = entity.posZ - entity1.posZ;
        for (d1 = entity.posZ - entity1.posZ; ((d * d) + (d1 * d1)) < 0.0001D; d1 = (Math.random() - Math.random()) * 0.01D)
        {
            d = (Math.random() - Math.random()) * 0.01D;
        }

        float f = MathHelper.sqrt_double((d * d) + (d1 * d1));
        // float f1 = 0.25F;
        /*
         * if(getKittyState() == 10) { f1 = 0.1F; }
         */
        entity1.motionX /= 2D;
        entity1.motionY /= 2D;
        entity1.motionZ /= 2D;
        entity1.motionX -= (d / f) * force;
        entity1.motionY += force;// 0.300000005960465D;
        entity1.motionZ -= (d1 / f) * force;
        if (entity1.motionY > force)// 0.3D)
        {
            entity1.motionY = force;// 0.3D;
        }
    }

    public static void buckleMobs(EntityLiving entityattacker, Double dist, World worldObj)
    {
        List list = worldObj.getEntitiesWithinAABBExcludingEntity(entityattacker, entityattacker.boundingBox.expand(dist, 2D, dist));
        for (int i = 0; i < list.size(); i++)
        {
            Entity entitytarget = (Entity) list.get(i);
            if (!(entitytarget instanceof EntityLiving) || (entityattacker.riddenByEntity != null && entitytarget == entityattacker.riddenByEntity))
            {
                continue;
            }
            //EntityMob entitymob = (EntityMob) entity;
            entitytarget.attackEntityFrom(DamageSource.causeMobDamage(entityattacker), 2);
            bigsmack(entityattacker, entitytarget, 0.6F);
            playCustomSound(entityattacker, "tud", worldObj);
            //todo tuck sound!!
        }
    }
    
    public static void buckleMobsNotPlayers(EntityLiving entityattacker, Double dist, World worldObj)
    {
        List list = worldObj.getEntitiesWithinAABBExcludingEntity(entityattacker, entityattacker.boundingBox.expand(dist, 2D, dist));
        for (int i = 0; i < list.size(); i++)
        {
            Entity entitytarget = (Entity) list.get(i);
            if (!(entitytarget instanceof EntityLiving) || (entitytarget instanceof EntityPlayer) ||(entityattacker.riddenByEntity != null && entitytarget == entityattacker.riddenByEntity))
            {
                continue;
            }
            //EntityMob entitymob = (EntityMob) entity;
            entitytarget.attackEntityFrom(DamageSource.causeMobDamage(entityattacker), 2);
            bigsmack(entityattacker, entitytarget, 0.6F);
            playCustomSound(entityattacker, "tud", worldObj);
            //todo tuck sound!!
        }
    }

    public static void spawnNearPlayer(EntityPlayer player, int creatureName, int numberToSpawn)//, World worldObj)
    {
        //WorldServer worldObj = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(player.worldObj.provider.worldType);
        WorldServer worldObj = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(player.worldObj.provider.dimensionId);

        //WorldServer worldServer = player.getServerForPlayer(); 
        for (int i = 0; i < numberToSpawn; i++)
        {
            EntityLiving entityliving = spawnList(creatureName, worldObj);

            if (entityliving != null)// && entityliving.getCanSpawnHere())
            {
                entityliving.setLocationAndAngles(player.posX - 1, player.posY, player.posZ - 1, player.rotationYaw, player.rotationPitch);
                if (entityliving instanceof MoCIMoCreature && ((MoCIMoCreature) entityliving).checkSpawningBiome())// && ((MoCIMoCreature)entityliving).getCanSpawnHere())
                {
                    //player.worldObj.spawnEntityInWorld(entityliving);
                    //worldObj.spawnEntityInWorld(entityliving);

                    worldObj.spawnEntityInWorld(entityliving);
                    //FMLCommonHandler.instance().getMinecraftServerInstance()
                    //Minecraft.getMinecraft().theWorld.spawnEntityInWorld(entityliving);
                }
            }
        }
    }
    
    public static void spawnNearPlayerbyName(EntityPlayer player, String eName, int numberToSpawn) 
    {
    	WorldServer worldObj = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(player.worldObj.provider.dimensionId);
    	System.out.println("spawning " + numberToSpawn + " entities of type " + eName );
    	for (int i = 0; i < numberToSpawn; i++)
        {
    		EntityLiving entityToSpawn = null;
            
    		try
            {
    			Class myClass = MoCreatures.proxy.entityMap.get(eName).getEntityClass();
    			entityToSpawn = (EntityLiving) myClass.getConstructor(new Class[] { World.class }).newInstance(new Object[] { worldObj });
            }catch (Exception e) 
            { System.out.println(e);}
            
            if (entityToSpawn != null)
            {
            	entityToSpawn.initCreature();
            	entityToSpawn.setLocationAndAngles(player.posX - 1, player.posY, player.posZ - 1, player.rotationYaw, player.rotationPitch);
                worldObj.spawnEntityInWorld(entityToSpawn);
            }
        }
	}
    

    public static void playCustomSound(Entity entity, String customSound, World worldObj)
    {
        worldObj.playSoundAtEntity(entity, customSound, 1.0F, 1.0F + ((worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F));
    }

    public static void playCustomSound(Entity entity, String customSound, World worldObj, float volume)
    {
        worldObj.playSoundAtEntity(entity, customSound, volume, 1.0F + ((worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F));
    }

    /*private void procreate(EntityAnimal par1EntityAnimal)
    {
        EntityLiving entityliving = spawnLiving(par1EntityAnimal);

        if (entityliving != null)
        {
            setGrowingAge(6000);
            par1EntityAnimal.setGrowingAge(6000);
            inLove = 0;
            breeding = 0;
            entityToAttack = null;
            par1EntityAnimal.entityToAttack = null;
            par1EntityAnimal.breeding = 0;
            par1EntityAnimal.inLove = 0;
            entityanimal.setGrowingAge(-24000);
            entityanimal.setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);

            

            worldObj.spawnEntityInWorld(entityanimal);
        }
    }*/

    /**
     * Returns a new instance of MoCIMoCreature based on the creatureType 
     * @param creatureType
     * @param worldObj
     * @return
     */
    public static EntityLiving spawnList(int creatureType, World worldObj)
    {
        /**
         * Spawn list
         * 
         * Bee 0 
         * Bear 1 
         * BigCat 2 
         * Bird 3 
         * Boar 4 
         * Bunny 5 
         * Butterfly6 
         * Cat 7 
         * Cricket 8 
         * Crab 9
         * Croc 10 
         * Deer 11 
         * Dolphin 12 
         * Dragonfly13 
         * Duck 14 
         * Elephant 15
         * Firefly 16 
         * Fish 17
         * Fly 18 
         * Fox 19 
         * Goat 20 
         * Golem 21
         * Horse 22 
         * HorseMob 23 
         * JellyFish24 
         * Komodo25
         * Mice 26 
         * Ogre 27 
         * Ostrich 28 
         * Rat 29 
         * Ray 30 
         * Shark 31
         * Scorpions32 
         * Snail 33 
         * Snake 34 
         * Turkey 35 
         * Turtle 36 
         * Werewolf 37 
         * Wraith 38 
         * Wraith F 39 
         * WWolf 40
         * Wyvern 41
         */

        switch (creatureType)
        {

        case 0:
            return new MoCEntityBee(worldObj);

        case 1:
            return new MoCEntityBear(worldObj);

        case 2:
            return new MoCEntityBigCat(worldObj);

        case 3:
            return new MoCEntityBird(worldObj);

        case 4:
            return new MoCEntityBoar(worldObj);

        case 5:
            return new MoCEntityBunny(worldObj);

        case 6:
            return new MoCEntityButterfly(worldObj);

        case 7:
            return new MoCEntityKitty(worldObj);

        case 8:
            return new MoCEntityCricket(worldObj);

        case 9:
            return new MoCEntityCrab(worldObj);
            
        case 10:
            return new MoCEntityCrocodile(worldObj);

        case 11:
            return new MoCEntityDeer(worldObj);

        case 12:
            return new MoCEntityDolphin(worldObj);

        case 13:
            return new MoCEntityDragonfly(worldObj);

        case 14:
            return new MoCEntityDuck(worldObj);

        case 15:
            return new MoCEntityElephant(worldObj);
            
        case 16:
            return new MoCEntityFirefly(worldObj);

        case 17:
            return new MoCEntityFishy(worldObj);

        case 18:
            return new MoCEntityFly(worldObj);

        case 19:
            return new MoCEntityFox(worldObj);

        case 20:
            return new MoCEntityGoat(worldObj);

        case 21:
            return new MoCEntityGolem(worldObj);

        case 22:
            return new MoCEntityHorse(worldObj);

        case 23:
            return new MoCEntityHorseMob(worldObj);

        case 24:
            return new MoCEntityJellyFish(worldObj);
        
        case 25:
            return new MoCEntityKomodo(worldObj);
        
        case 26:
            return new MoCEntityMouse(worldObj);

        case 27:
            return new MoCEntityOgre(worldObj);

        case 28:
            return new MoCEntityOstrich(worldObj);

        case 29:
            return new MoCEntityRat(worldObj);

        case 30:
            return new MoCEntityRay(worldObj);

        case 31:
            return new MoCEntityShark(worldObj);

        case 32:
            return new MoCEntityScorpion(worldObj);

        case 33:
            return new MoCEntitySnail(worldObj);

        case 34:
            return new MoCEntitySnake(worldObj);

        case 35:
            return new MoCEntityTurkey(worldObj);

        case 36:
            return new MoCEntityTurtle(worldObj);

        case 37:
            return new MoCEntityWerewolf(worldObj);

        case 38:
            return new MoCEntityWraith(worldObj);

        case 39:
            return new MoCEntityFlameWraith(worldObj);

        case 40:
            return new MoCEntityWWolf(worldObj);
        case 41:
            return new MoCEntityWyvern(worldObj);

        default:
            return new MoCEntityBunny(worldObj);

        }
    }
    
    public static MoCIMoCreature spawnIMoCreatureList(int creatureType, World worldObj)
    {
    	return (MoCIMoCreature) spawnList(creatureType, worldObj);
    }

    public static MoCEntityAnimal spawnMoCAnimalList(int creatureType, World worldObj)
    {
    	return (MoCEntityAnimal) spawnList(creatureType, worldObj);
    }
    
    public static boolean NearMaterialWithDistance(Entity entity, Double double1, Material mat)
    {
        AxisAlignedBB axisalignedbb = entity.boundingBox.expand(double1.doubleValue(), double1.doubleValue(), double1.doubleValue());
        int i = MathHelper.floor_double(axisalignedbb.minX);
        int j = MathHelper.floor_double(axisalignedbb.maxX + 1.0D);
        int k = MathHelper.floor_double(axisalignedbb.minY);
        int l = MathHelper.floor_double(axisalignedbb.maxY + 1.0D);
        int i1 = MathHelper.floor_double(axisalignedbb.minZ);
        int j1 = MathHelper.floor_double(axisalignedbb.maxZ + 1.0D);
        for (int k1 = i; k1 < j; k1++)
        {
            for (int l1 = k; l1 < l; l1++)
            {
                for (int i2 = i1; i2 < j1; i2++)
                {
                    int j2 = entity.worldObj.getBlockId(k1, l1, i2);
                    if ((j2 != 0) && (Block.blocksList[j2].blockMaterial == mat)) { return true; }
                }

            }

        }

        return false;
    }

    public static boolean isNearTorch(Entity entity)
    {
        return (isNearBlockName(entity, 8D, "tile.torch")) || (isNearBlockName(entity, 8d, "tile.lightgem")) || (isNearBlockName(entity, 8d, "tile.redstoneLight")) || (isNearBlockName(entity, 8d, "tile.litpumpkin"));
    }

    public static boolean isNearTorch(Entity entity, Double dist)
    {
        return isNearBlockName(entity, dist, "tile.torch") || (isNearBlockName(entity, dist, "tile.lightgem")) || (isNearBlockName(entity, dist, "tile.redstoneLight")) || (isNearBlockName(entity, dist, "tile.litpumpkin"));

    }

    public static boolean isNearBlockName(Entity entity, Double dist, String blockName)
    {
        AxisAlignedBB axisalignedbb = entity.boundingBox.expand(dist, dist / 2D, dist);
        int i = MathHelper.floor_double(axisalignedbb.minX);
        int j = MathHelper.floor_double(axisalignedbb.maxX + 1.0D);
        int k = MathHelper.floor_double(axisalignedbb.minY);
        int l = MathHelper.floor_double(axisalignedbb.maxY + 1.0D);
        int i1 = MathHelper.floor_double(axisalignedbb.minZ);
        int j1 = MathHelper.floor_double(axisalignedbb.maxZ + 1.0D);
        for (int k1 = i; k1 < j; k1++)
        {
            for (int l1 = k; l1 < l; l1++)
            {
                for (int i2 = i1; i2 < j1; i2++)
                {
                    int j2 = entity.worldObj.getBlockId(k1, l1, i2);
                    //int j2 = entity.worldObj.getBlockId(k1, l1, i2);

                    /*if (j2 !=0) 
                    {
                    String nameToCheck = Block.blocksList[j2].getBlockName();
                    if (nameToCheck.equals(blockName))
                    {
                        return true;
                    }
                    //System.out.println(nameToCheck);
                    }*/

                    if (j2 != 0)// && (Block.blocksList[j2].getBlockName().equals(blockName)))
                    {
                        String nameToCheck = "";
                        nameToCheck = Block.blocksList[j2].getUnlocalizedName();//.getBlockName();
                        if (nameToCheck != null && nameToCheck != "")
                        {
                            if (nameToCheck.equals(blockName)) { return true; }
                        }

                    }

                }

            }

        }

        return false;
    }

    public static TileEntityRecordPlayer nearJukeBoxRecord(Entity entity, Double dist)
    {
        AxisAlignedBB axisalignedbb = entity.boundingBox.expand(dist, dist / 2D, dist);
        int i = MathHelper.floor_double(axisalignedbb.minX);
        int j = MathHelper.floor_double(axisalignedbb.maxX + 1.0D);
        int k = MathHelper.floor_double(axisalignedbb.minY);
        int l = MathHelper.floor_double(axisalignedbb.maxY + 1.0D);
        int i1 = MathHelper.floor_double(axisalignedbb.minZ);
        int j1 = MathHelper.floor_double(axisalignedbb.maxZ + 1.0D);
        for (int k1 = i; k1 < j; k1++)
        {
            for (int l1 = k; l1 < l; l1++)
            {
                for (int i2 = i1; i2 < j1; i2++)
                {
                    //int j2 = MoCreatures.mc.theWorld.getBlockId(k1, l1, i2);
                    int j2 = entity.worldObj.getBlockId(k1, l1, i2);

                    /*if (j2 !=0) 
                    {
                    String nameToCheck = Block.blocksList[j2].getBlockName();
                    if (nameToCheck.equals(blockName))
                    {
                        return true;
                    }
                    //System.out.println(nameToCheck);
                    }*/

                    if (j2 != 0)// && (Block.blocksList[j2].getBlockName().equals(blockName)))
                    {
                        String nameToCheck = "";
                        nameToCheck = Block.blocksList[j2].getUnlocalizedName();//.getBlockName();
                        if (nameToCheck != null && nameToCheck != "")
                        {
                            if (nameToCheck.equals("tile.jukebox"))
                            {
                                TileEntityRecordPlayer juky = (TileEntityRecordPlayer) entity.worldObj.getBlockTileEntity(k1, l1, i2);
                                return juky;
                                //BlockJukeBox jukebox = (BlockJukeBox) Block.blocksList[j2];
                                //TileEntity recordPlaying = jukebox.getBlockEntity();

                                //String songPlaying = recordPlaying.readFromNBT(par1NBTTagCompound)
                            }

                        }

                    }

                }

            }

        }

        return null;
    }

    public static void checkForTwistedEntities(World world)
    {
        int k = 0;
        for (int l = 0; l < world.loadedEntityList.size(); l++)
        {
            Entity entity = (Entity) world.loadedEntityList.get(l);
            if (entity instanceof EntityLiving)
            {
                EntityLiving twisted = (EntityLiving) entity;
                if (twisted.deathTime > 0 && twisted.ridingEntity == null && twisted.getHealth() > 0)
                {
                    twisted.deathTime = 0;
                }
            }
        }
    }

    public static double getSqDistanceTo(Entity entity, double i, double j, double k)
    {
        double l = entity.posX - i;
        double i1 = entity.posY - j;
        double j1 = entity.posZ - k;
        return Math.sqrt((l * l) + (i1 * i1) + (j1 * j1));

    }

    public static int[] ReturnNearestMaterialCoord(Entity entity, Material material, Double double1, Double yOff)
    {
        double shortestDistance = -1D;
        double distance = 0D;
        int x = -9999;
        int y = -1;
        int z = -1;

        AxisAlignedBB axisalignedbb = entity.boundingBox.expand(double1.doubleValue(), yOff.doubleValue(), double1.doubleValue());
        int i = MathHelper.floor_double(axisalignedbb.minX);
        int j = MathHelper.floor_double(axisalignedbb.maxX + 1.0D);
        int k = MathHelper.floor_double(axisalignedbb.minY);
        int l = MathHelper.floor_double(axisalignedbb.maxY + 1.0D);
        int i1 = MathHelper.floor_double(axisalignedbb.minZ);
        int j1 = MathHelper.floor_double(axisalignedbb.maxZ + 1.0D);
        for (int k1 = i; k1 < j; k1++)
        {
            for (int l1 = k; l1 < l; l1++)
            {
                for (int i2 = i1; i2 < j1; i2++)
                {
                    int j2 = entity.worldObj.getBlockId(k1, l1, i2);
                    if ((j2 != 0) && (Block.blocksList[j2].blockMaterial == material))
                    {

                        distance = getSqDistanceTo(entity, k1, l1, i2);
                        if (shortestDistance == -1D)
                        {
                            x = k1;
                            y = l1;
                            z = i2;
                            shortestDistance = distance;
                        }

                        if (distance < shortestDistance)
                        {

                            x = k1;
                            y = l1;
                            z = i2;
                            shortestDistance = distance;
                        }

                    }
                }

            }

        }

        if (entity.posX > x)
        {
            x -= 2;
        }
        else
        {
            x += 2;
        }
        if (entity.posZ > z)
        {
            z -= 2;
        }
        else
        {
            z += 2;
        }
        return (new int[] { x, y, z });
    }
    
    public static int[] ReturnNearestBlockCoord(Entity entity, int blockID, Double dist)
    {
        double shortestDistance = -1D;
        double distance = 0D;
        int x = -9999;
        int y = -1;
        int z = -1;

        AxisAlignedBB axisalignedbb = entity.boundingBox.expand(dist, dist, dist);
        int i = MathHelper.floor_double(axisalignedbb.minX);
        int j = MathHelper.floor_double(axisalignedbb.maxX + 1.0D);
        int k = MathHelper.floor_double(axisalignedbb.minY);
        int l = MathHelper.floor_double(axisalignedbb.maxY + 1.0D);
        int i1 = MathHelper.floor_double(axisalignedbb.minZ);
        int j1 = MathHelper.floor_double(axisalignedbb.maxZ + 1.0D);
        for (int k1 = i; k1 < j; k1++)
        {
            for (int l1 = k; l1 < l; l1++)
            {
                for (int i2 = i1; i2 < j1; i2++)
                {
                    int j2 = entity.worldObj.getBlockId(k1, l1, i2);
                    if ((j2 != 0) && (j2 == blockID))
                    {

                        distance = getSqDistanceTo(entity, k1, l1, i2);
                        if (shortestDistance == -1D)
                        {
                            x = k1;
                            y = l1;
                            z = i2;
                            shortestDistance = distance;
                        }

                        if (distance < shortestDistance)
                        {

                            x = k1;
                            y = l1;
                            z = i2;
                            shortestDistance = distance;
                        }

                    }
                }

            }

        }

        if (entity.posX > x)
        {
            x -= 2;
        }
        else
        {
            x += 2;
        }
        if (entity.posZ > z)
        {
            z -= 2;
        }
        else
        {
            z += 2;
        }
        return (new int[] { x, y, z });
    }

    public static void MoveCreatureToXYZ(EntityCreature movingEntity, int x, int y, int z, float f)
    {
        //PathEntity pathentity = MoCreatures.mc.theWorld.getEntityPathToXYZ(movingEntity, x, y, z, f);
        //pathToEntity = worldObj.getEntityPathToXYZ(this, i, j, k, 10F, true, false, false, true);
        //TODO works?
        PathEntity pathentity = movingEntity.worldObj.getEntityPathToXYZ(movingEntity, x, y, z, f, true, false, false, true);

        if (pathentity != null)
        {
            movingEntity.setPathToEntity(pathentity);

        }

    }

    public static void MoveToWater(EntityCreature entity)
    {

        int ai[] = MoCTools.ReturnNearestMaterialCoord(entity, Material.water, Double.valueOf(20D), 2D);
        if (ai[0] > -1000)
        {
            MoCTools.MoveCreatureToXYZ(entity, ai[0], ai[1], ai[2], 24F);

        }
    }

    /**
     * Gives angles in the range 0-360 i.e. 361 will be returned like 1
     * 
     * @param origAngle
     * @return
     */
    public static float realAngle(float origAngle)
    {
        return origAngle % 360F;
    }

    public static void SlideEntityToXYZ(Entity entity, int x, int y, int z)
    {

        if (entity != null)
        {
            /*int i = MathHelper.floor_double(entity.posX);
            int j = MathHelper.floor_double(entity.posY);
            int k = MathHelper.floor_double(entity.posZ);*/

            if (entity.posY < y)
            {
                entity.motionY += 0.14999999999999999D;
            }
            if (entity.posX < x)
            {
                double d = x - entity.posX;
                if (d > 0.5D)
                {
                    entity.motionX += 0.050000000000000003D;
                }
            }
            else
            {
                double d1 = entity.posX - x;
                if (d1 > 0.5D)
                {
                    entity.motionX -= 0.050000000000000003D;
                }
            }
            if (entity.posZ < z)
            {
                double d2 = z - entity.posZ;
                if (d2 > 0.5D)
                {
                    entity.motionZ += 0.050000000000000003D;
                }
            }
            else
            {
                double d3 = entity.posZ - z;
                if (d3 > 0.5D)
                {
                    entity.motionZ -= 0.050000000000000003D;
                }
            }

        }
    }

    public static float distanceToSurface(Entity entity)
    {
        //double d = entity.posY + (double)entity.getEyeHeight();
        int i = MathHelper.floor_double(entity.posX);
        int j = MathHelper.floor_double(entity.posY);
        int k = MathHelper.floor_double(entity.posZ);
        int l = entity.worldObj.getBlockId(i, j, k);
        if (l != 0 && Block.blocksList[l].blockMaterial == Material.water)
        {
            for (int x = 1; x < 64; x++)
            {
                l = entity.worldObj.getBlockId(i, j + x, k);
                if (l == 0 || Block.blocksList[l].blockMaterial != Material.water) { return (float) x; }
            }

        }
        return 0F;

    }

    public static int distanceToFloor(Entity entity)
    {
        int i = MathHelper.floor_double(entity.posX);
        int j = MathHelper.floor_double(entity.posY);
        int k = MathHelper.floor_double(entity.posZ);
        int l = entity.worldObj.getBlockId(i, j, k);
        for (int x = 0; x < 64; x++)
        {
            l = entity.worldObj.getBlockId(i, j - x, k);
            if (l != 0) { return x; }
        }

        return 0;
    }

    public boolean isInsideOfMaterial(Material material, Entity entity)
    {
        double d = entity.posY + (double) entity.getEyeHeight();
        int i = MathHelper.floor_double(entity.posX);
        int j = MathHelper.floor_float(MathHelper.floor_double(d));
        int k = MathHelper.floor_double(entity.posZ);
        int l = entity.worldObj.getBlockId(i, j, k);
        if (l != 0 && Block.blocksList[l].blockMaterial == material)
        {
            float f = BlockFluid.getFluidHeightPercent(entity.worldObj.getBlockMetadata(i, j, k)) - 0.1111111F;
            float f1 = (float) (j + 1) - f;
            return d < (double) f1;
        }
        else
        {
            return false;
        }
    }

    public static void disorientEntity(Entity entity)
    {

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

    public static void slowEntity(Entity entity)
    {

        entity.motionX *= 0.8D;
        entity.motionZ *= 0.8D;
    }

    public static int colorize(int i)
    {
        return ~i & 0xf;
    }

    public int countEntities(Class class1, World worldObj)
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

    /*public static List spawnList(EnumCreatureType enumcreaturetype, int i, int j, int k)
    {
        WorldChunkManager worldchunkmanager = entity.worldObj.getWorldChunkManager();
        if(worldchunkmanager == null)
        {
            return null;
        }
        //TODO works?
        BiomeGenBase biomegenbase = worldchunkmanager.getBiomeGenAt(i >> 4, k >> 4);
        //BiomeGenBase biomegenbase = worldchunkmanager.getBiomeGenAtChunkCoord(new ChunkCoordIntPair(i >> 4, k >> 4));
        if(biomegenbase == null)
        {
            return null;
        } else
        {
            return biomegenbase.getSpawnableList(enumcreaturetype);
        }
    }*/

    public static BiomeGenBase whatBiome(World world, int i, int j, int k)
    {
        WorldChunkManager worldchunkmanager = world.getWorldChunkManager();
        if (worldchunkmanager == null) { return null; }
        //TODO works?
        //BiomeGenBase biomegenbase = worldchunkmanager.getBiomeGenAt(i >> 4, k >> 4);
        BiomeGenBase biomegenbase = worldchunkmanager.getBiomeGenAt(i, k);
        //BiomeGenBase biomegenbase = worldchunkmanager.getBiomeGenAtChunkCoord(new ChunkCoordIntPair(i >> 4, k >> 4));
        if (biomegenbase == null)
        {
            return null;
        }
        else
        {
            return biomegenbase;
        }
    }

    public static float distToPlayer(Entity entity)
    {
        //TODO 
        return 0.0F;
    }

    public static String BiomeName(World world, int i, int j, int k)
    {
        WorldChunkManager worldchunkmanager = world.getWorldChunkManager();
        if (worldchunkmanager == null) { return null; }
        //BiomeGenBase biomegenbase = worldchunkmanager.getBiomeGenAt(i >> 4, k >> 4);
        BiomeGenBase biomegenbase = worldchunkmanager.getBiomeGenAt(i, k);
        //TODO works?

        if (biomegenbase == null)
        {
            return null;
        }
        else
        {
            return biomegenbase.biomeName;
        }
    }

    public static BiomeGenBase Biomekind(World world, int i, int j, int k)
    {
        WorldChunkManager worldchunkmanager = world.getWorldChunkManager();
        if (worldchunkmanager == null) { return null; }
        BiomeGenBase biomegenbase = worldchunkmanager.getBiomeGenAt(i, k);
        if (biomegenbase == null)
        {
            return null;
        }
        else
        {
            return biomegenbase;
        }
    }
    
    public static void destroyDrops(Entity entity, double d)
    {

        if (!MoCreatures.proxy.destroyDrops) { return; }

        //List list = MoCreatures.mc.theWorld.getEntitiesWithinAABBExcludingEntity(entity, entity.boundingBox.expand(d, d, d));
        List list = entity.worldObj.getEntitiesWithinAABBExcludingEntity(entity, entity.boundingBox.expand(d, d, d));

        for (int i = 0; i < list.size(); i++)
        {
            Entity entity1 = (Entity) list.get(i);
            if (!(entity1 instanceof EntityItem))
            {
                continue;
            }
            EntityItem entityitem = (EntityItem) entity1;
            if ((entityitem != null) && (entityitem.age < 50))
            {
                entityitem.setDead();
            }
        }

    }

    public static void repelMobs(Entity entity1, Double dist, World worldObj)
    {
        List list = worldObj.getEntitiesWithinAABBExcludingEntity(entity1, entity1.boundingBox.expand(dist, 4D, dist));
        for (int i = 0; i < list.size(); i++)
        {
            Entity entity = (Entity) list.get(i);
            if (!(entity instanceof EntityMob))
            {
                continue;
            }
            EntityMob entitymob = (EntityMob) entity;
            entitymob.setAttackTarget(null);
            //entitymob.entityToAttack = null;
            entitymob.setPathToEntity(null);
        }
    }

    /**
     * Drops the important stuff to get going fast
     * 
     * @param worldObj
     * @param entity
     */
    public static void dropGoodies(World worldObj, Entity entity)
    {
        if (!MoCreatures.isServer()) { return; }

        EntityItem entityitem = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(Block.wood, 16));
        entityitem.delayBeforeCanPickup = 10;
        worldObj.spawnEntityInWorld(entityitem);

        EntityItem entityitem2 = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(Item.diamond, 64));
        entityitem2.delayBeforeCanPickup = 10;
        worldObj.spawnEntityInWorld(entityitem2);

        EntityItem entityitem3 = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(Block.pumpkin, 6));
        entityitem3.delayBeforeCanPickup = 10;
        worldObj.spawnEntityInWorld(entityitem3);

        EntityItem entityitem4 = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(Block.cobblestone, 64));
        entityitem4.delayBeforeCanPickup = 10;
        worldObj.spawnEntityInWorld(entityitem4);

        EntityItem entityitem5 = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(Item.appleRed, 24));
        entityitem5.delayBeforeCanPickup = 10;
        worldObj.spawnEntityInWorld(entityitem5);

        EntityItem entityitem6 = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(Item.leather, 64));
        entityitem6.delayBeforeCanPickup = 10;
        worldObj.spawnEntityInWorld(entityitem6);

        EntityItem entityitem7 = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(MoCreatures.recordshuffle, 6));
        entityitem7.delayBeforeCanPickup = 10;
        worldObj.spawnEntityInWorld(entityitem7);

        EntityItem entityitem8 = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(Item.ingotIron, 64));
        entityitem8.delayBeforeCanPickup = 10;
        worldObj.spawnEntityInWorld(entityitem8);

        EntityItem entityitem9 = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(Item.ingotGold, 12));
        entityitem9.delayBeforeCanPickup = 10;
        worldObj.spawnEntityInWorld(entityitem9);

        EntityItem entityitem10 = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(Item.silk, 32));
        entityitem10.delayBeforeCanPickup = 10;
        worldObj.spawnEntityInWorld(entityitem10);

        EntityItem entityitem11 = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(MoCreatures.horsearmorgold, 12));
        entityitem11.delayBeforeCanPickup = 10;
        worldObj.spawnEntityInWorld(entityitem11);

        EntityItem entityitem12 = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(Block.plantRed, 6));
        entityitem12.delayBeforeCanPickup = 10;
        worldObj.spawnEntityInWorld(entityitem12);

        EntityItem entityitem13 = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(Item.blazeRod, 12));
        entityitem13.delayBeforeCanPickup = 10;
        worldObj.spawnEntityInWorld(entityitem13);

        EntityItem entityitem14 = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(Item.enderPearl, 12));
        entityitem14.delayBeforeCanPickup = 10;
        worldObj.spawnEntityInWorld(entityitem14);

        EntityItem entityitem15 = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(Item.ghastTear, 12));
        entityitem15.delayBeforeCanPickup = 10;
        worldObj.spawnEntityInWorld(entityitem15);

        EntityItem entityitem16 = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(Block.blockLapis, 2));
        entityitem16.delayBeforeCanPickup = 10;
        worldObj.spawnEntityInWorld(entityitem16);

        EntityItem entityitem17 = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(Item.bone, 12));
        entityitem17.delayBeforeCanPickup = 10;
        worldObj.spawnEntityInWorld(entityitem17);

        EntityItem entityitem18 = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(MoCreatures.unicorn, 16));
        entityitem18.delayBeforeCanPickup = 10;
        worldObj.spawnEntityInWorld(entityitem18);

        EntityItem entityitem19 = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(Block.fire, 32));
        entityitem19.delayBeforeCanPickup = 10;
        worldObj.spawnEntityInWorld(entityitem19);

        EntityItem entityitem20 = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(MoCreatures.vialdarkness, 6));
        entityitem20.delayBeforeCanPickup = 10;
        worldObj.spawnEntityInWorld(entityitem20);

        EntityItem entityitem21 = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(MoCreatures.vialundead, 6));
        entityitem21.delayBeforeCanPickup = 10;
        worldObj.spawnEntityInWorld(entityitem21);

        EntityItem entityitem22 = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(MoCreatures.vialnightmare, 6));
        entityitem22.delayBeforeCanPickup = 10;
        worldObj.spawnEntityInWorld(entityitem22);

        EntityItem entityitem23 = new EntityItem(worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(Item.itemsList[Block.cloth.blockID], 6, 15));
        entityitem23.delayBeforeCanPickup = 10;
        worldObj.spawnEntityInWorld(entityitem23);

    }

    public static boolean mobGriefing(World world)
    {
    	return world.getGameRules().getGameRuleBooleanValue("mobGriefing");
    }
    
    public static void DestroyBlast(Entity entity, double d, double d1, double d2, float f, boolean flag)
    {
    	entity.worldObj.playSoundEffect(d, d1, d2, "destroy", 4F, (1.0F + ((entity.worldObj.rand.nextFloat() - entity.worldObj.rand.nextFloat()) * 0.2F)) * 0.7F);
            	
    	boolean mobGriefing = mobGriefing(entity.worldObj);
    	
        HashSet hashset = new HashSet();
        float f1 = f;
        int i = 16;
        for (int j = 0; j < i; j++)
        {
            for (int l = 0; l < i; l++)
            {
                label0: for (int j1 = 0; j1 < i; j1++)
                {
                    if ((j != 0) && (j != (i - 1)) && (l != 0) && (l != (i - 1)) && (j1 != 0) && (j1 != (i - 1)))
                    {
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
                    do
                    {
                        if (f2 <= 0.0F)
                        {
                            continue label0;
                        }
                        int k5 = MathHelper.floor_double(d8);
                        int l5 = MathHelper.floor_double(d10);
                        int i6 = MathHelper.floor_double(d12);
                        int j6 = entity.worldObj.getBlockId(k5, l5, i6);
                        if (j6 > 0)
                        {
                            f4 = Block.blocksList[j6].getBlockHardness(entity.worldObj, k5, l5, i6);
                            f2 -= (Block.blocksList[j6].getExplosionResistance(entity) + 0.3F) * (f3 / 10F);
                        }
                        if ((f2 > 0.0F) && (d10 > entity.posY) && (f4 < 3F))
                        {
                            hashset.add(new ChunkPosition(k5, l5, i6));
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
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
        {
            int k = MathHelper.floor_double(d - f - 1.0D);
            int i1 = MathHelper.floor_double(d + f + 1.0D);
            int k1 = MathHelper.floor_double(d1 - f - 1.0D);
            int l1 = MathHelper.floor_double(d1 + f + 1.0D);
            int i2 = MathHelper.floor_double(d2 - f - 1.0D);
            int j2 = MathHelper.floor_double(d2 + f + 1.0D);
            List list = entity.worldObj.getEntitiesWithinAABBExcludingEntity(entity, AxisAlignedBB.getBoundingBox(k, k1, i2, i1, l1, j2));
            Vec3 vec3d = Vec3.createVectorHelper(d, d1, d2);
            for (int k2 = 0; k2 < list.size(); k2++)
            {
                Entity entity1 = (Entity) list.get(k2);
                double d7 = entity1.getDistance(d, d1, d2) / f;
                if (d7 > 1.0D)
                {
                    continue;
                }
                double d9 = entity1.posX - d;
                double d11 = entity1.posY - d1;
                double d13 = entity1.posZ - d2;
                double d15 = MathHelper.sqrt_double((d9 * d9) + (d11 * d11) + (d13 * d13));
                d9 /= d15;
                d11 /= d15;
                d13 /= d15;
                double d17 = entity.worldObj.getBlockDensity(vec3d, entity1.boundingBox);
                double d19 = (1.0D - d7) * d17;

                //attacks entities in server
                if (!(entity1 instanceof MoCEntityOgre))
                {
                    entity1.attackEntityFrom(DamageSource.generic, (int) (((((d19 * d19) + d19) / 2D) * 3D * f) + 1.0D));
                    double d21 = d19;
                    entity1.motionX += d9 * d21;
                    entity1.motionY += d11 * d21;
                    entity1.motionZ += d13 * d21;
                }
            }
        }

        f = f1;
        ArrayList arraylist = new ArrayList();
        arraylist.addAll(hashset);

        for (int l2 = arraylist.size() - 1; l2 >= 0; l2--)
        {
            ChunkPosition chunkposition = (ChunkPosition) arraylist.get(l2);
            int j3 = chunkposition.x;
            int l3 = chunkposition.y;
            int j4 = chunkposition.z;
            int l4 = entity.worldObj.getBlockId(j3, l3, j4);
            for (int j5 = 0; j5 < 5; j5++)
            {
                double d14 = j3 + entity.worldObj.rand.nextFloat();
                double d16 = l3 + entity.worldObj.rand.nextFloat();
                double d18 = j4 + entity.worldObj.rand.nextFloat();
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
                if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
                {
                    entity.worldObj.spawnParticle("explode", (d14 + (d * 1.0D)) / 2D, (d16 + (d1 * 1.0D)) / 2D, (d18 + (d2 * 1.0D)) / 2D, d20, d22, d23);
                    entity.motionX -= 0.0010000000474974511D;
                    entity.motionY -= 0.0010000000474974511D;
                }

            }

            //destroys blocks on server!
            if (mobGriefing && (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) && l4 > 0)
            {
                Block.blocksList[l4].dropBlockAsItemWithChance(entity.worldObj, j3, l3, j4, entity.worldObj.getBlockMetadata(j3, l3, j4), 0.3F, 1);
                entity.worldObj.setBlock(j3, l3, j4, 0, 0, 3);
                Block.blocksList[l4].onBlockDestroyedByExplosion(entity.worldObj, j3, l3, j4, null);
            }
        }

        //sets world on fire on server
        if (mobGriefing && (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) && flag)
        {
            for (int i3 = arraylist.size() - 1; i3 >= 0; i3--)
            {
                ChunkPosition chunkposition1 = (ChunkPosition) arraylist.get(i3);
                int k3 = chunkposition1.x;
                int i4 = chunkposition1.y;
                int k4 = chunkposition1.z;
                int i5 = entity.worldObj.getBlockId(k3, i4, k4);
                //MoCreatures.mc.theWorld.setBlockMetadata(k3, i4, k4, Block.fire.blockID);
                //MoCreatures.mc.theWorld.setBlockWithNotify(k3, i4, k4, Block.fire.blockID);
                if ((i5 == 0) && (entity.worldObj.rand.nextInt(8) == 0))
                {
                    entity.worldObj.setBlock(k3, i4, k4, Block.fire.blockID, 0, 3);//MC 1.5
                    //MoCreatures.mc.theWorld.setBlockMetadata(k3, i4, k4, Block.fire.blockIndexInTexture);
                }
            }

        }
    }

    public static int despawnVanillaAnimals(World worldObj)
    {
        return despawnVanillaAnimals(worldObj, null);
    }

    public static int despawnVanillaAnimals(World worldObj, List[] classList)
    {
        int count = 0;
        for (int j = 0; j < worldObj.loadedEntityList.size(); j++)
        {
            Entity entity = (Entity) worldObj.loadedEntityList.get(j);
            if ((entity instanceof EntityLiving) && (entity instanceof EntityCow || entity instanceof EntitySheep || entity instanceof EntityPig || entity instanceof EntityChicken || entity instanceof EntitySquid || entity instanceof EntityWolf))
            {
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
            if (d3 > 16384D)
            {
                entityliving.setDead();
                count++;
            }

            if (entityliving.getAge() > 600 && worldObj.rand.nextInt(800) == 0)
            {
                if (d3 < 1024D)
                {
                    //entityliving.entityAge = 0;
                    //TODO test!
                    entityliving.attackEntityFrom(DamageSource.causeMobDamage(null), 0);

                }
                else
                {
                    entityliving.setDead();
                    count++;
                }
            }
        }
        return count;
    }

    /*public String getFXString(int i)
    {
        String FXString = "";
        switch(i)
        {
            
            case 1: 
                FXString = "suspended";
                break;
            case 2:
                FXString = "depthsuspend";
                break;
            case 3:
                FXString = "townaura";
                break;
            case 4: 
                FXString = "crit";
                break;
            case 5: 
                FXString = "magicCrit";
                break;
            case 6: 
                FXString = "smoke";
                break;
            case 7: 
                FXString = "mobSpell";
                break;
            case 8: 
                FXString = "spell";
                break;
                
            case 9: 
                FXString = "instantSpell";
                break;
            case 10: 
                FXString = "note";
                break;
            case 11: 
                FXString = "portal";
                break;

            case 12:
                FXString = "enchantmenttable";
                break;
            case 13:
                FXString = "explode";
                break;
            case 14: 
                FXString = "flame";
                break;
            case 15: 
                FXString = "lava";
                break;
            case 16: 
                FXString = "footstep";
                break;
            case 17: 
                FXString = "splash";
                break;
            case 18: 
                FXString = "largesmoke";
                break;
            case 19: 
                FXString = "cloud";
                break;
            case 20: 
                FXString = "reddust";
                break;
            case 21: 
                FXString = "snowballpoof";
                break;

            case 22:
                FXString = "dripWater";
                break;
            case 23:
                FXString = "dripLava";
                break;
            case 24: 
                FXString = "snowshovel";
                break;
            case 25: 
                FXString = "slime";
                break;
            case 26: 
                FXString = "heart";
                break;
            case 27: 
                FXString = "bubble";
                break;
        }
        //System.out.println("String = " + FXString);
        return FXString;
    }*/

    /**
     * Forces a data sync between server/client. currently used to syncrhonize
     * mounts
     */
    public static void forceDataSync(MoCIMoCreature entityMoCreature)
    {
        if (entityMoCreature.updateMount() && ((Entity) entityMoCreature).ridingEntity != null)
        {
            //System.out.println("Sending heartbeat packets...");
            MoCServerPacketHandler.sendAttachedEntity((Entity) entityMoCreature, ((Entity) entityMoCreature).ridingEntity, ((Entity) entityMoCreature).worldObj.provider.dimensionId);
        }

    }

    public static void updatePlayerArmorEffects(EntityPlayer player)
    {
        //List<EntityPlayer> playerList = player.worldObj.playerEntities;

        ItemStack mystack[] = new ItemStack[4];
        mystack[0] = player.inventory.armorItemInSlot(0); //boots
        mystack[1] = player.inventory.armorItemInSlot(1); //legs
        mystack[2] = player.inventory.armorItemInSlot(2); //plate
        mystack[3] = player.inventory.armorItemInSlot(3); //helmet

        //full scorpion cave armor set, enable night vision
        if (mystack[0] != null && mystack[0].itemID == MoCreatures.bootsScorpCave.itemID && mystack[1] != null && mystack[1].itemID == MoCreatures.legsScorpCave.itemID && mystack[2] != null && mystack[2].itemID == MoCreatures.plateScorpCave.itemID && mystack[3] != null && mystack[3].itemID == MoCreatures.helmetScorpCave.itemID)
        {
            //System.out.println("adding effect night vision to player " + player);
            player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 300, 0));
            return;
        }

        //full scorpion nether armor set, enable fire resistance
        if (mystack[0] != null && mystack[0].itemID == MoCreatures.bootsScorpNether.itemID && mystack[1] != null && mystack[1].itemID == MoCreatures.legsScorpNether.itemID && mystack[2] != null && mystack[2].itemID == MoCreatures.plateScorpNether.itemID && mystack[3] != null && mystack[3].itemID == MoCreatures.helmetScorpNether.itemID)
        {
            //System.out.println("adding effect fire resistance to player " + player);
            player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 300, 0));
            return;
        }

        //full scorpion frost armor set, enable water breathing
        if (mystack[0] != null && mystack[0].itemID == MoCreatures.bootsScorpFrost.itemID && mystack[1] != null && mystack[1].itemID == MoCreatures.legsScorpFrost.itemID && mystack[2] != null && mystack[2].itemID == MoCreatures.plateScorpFrost.itemID && mystack[3] != null && mystack[3].itemID == MoCreatures.helmetScorpFrost.itemID)
        {
            //System.out.println("adding effect water breathing to player " + player);
            player.addPotionEffect(new PotionEffect(Potion.waterBreathing.id, 300, 0));
            return;
        }

        //full scorpion armor set, regeneration effect
        if (mystack[0] != null && mystack[0].itemID == MoCreatures.bootsScorpDirt.itemID && mystack[1] != null && mystack[1].itemID == MoCreatures.legsScorpDirt.itemID && mystack[2] != null && mystack[2].itemID == MoCreatures.plateScorpDirt.itemID && mystack[3] != null && mystack[3].itemID == MoCreatures.helmetScorpDirt.itemID)
        {
            //System.out.println("adding regeneration to player " + player);
            player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 70, 0));
            return;
        }
    }

    /**
     * Finds a random block around the entity and returns the block's ID will
     * destroy the block in the process the block will be the top one of that
     * layer, without any other block around it
     * 
     * @param entity
     *            = the Entity around which the block is searched
     * @param distance
     *            = the distance around the entity used to look for the block
     * @return
     */
    public static int destroyRandomBlock(Entity entity, double distance)
    {
    	//int d = (int)distance;
        int l = (int) (distance * distance * distance);
        for (int i = 0; i < l; i++)
        {
            int x = (int) (entity.posX + entity.worldObj.rand.nextInt((int) (distance)) - (int) (distance / 2));
            int y = (int) (entity.posY + entity.worldObj.rand.nextInt((int) (distance)) - (int) (distance / 4));
            int z = (int) (entity.posZ + entity.worldObj.rand.nextInt((int) (distance)) - (int) (distance / 2));
            int k = entity.worldObj.getBlockId(MathHelper.floor_double(x), MathHelper.floor_double(y + 1.1D), MathHelper.floor_double(z));
            int j = entity.worldObj.getBlockId(MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z));

            if (j != 0 && k == 0)
            {
            	if (mobGriefing(entity.worldObj))
            	{
            		entity.worldObj.setBlock(x, y, z, 0, 0, 3);
            	}
                return j;

            }

        }

        return 0;
    }

    /**
     * Finds a random block around the entity and returns the coordinates the
     * block will be the top one of that layer, without any other block around
     * it
     * 
     * @param entity
     *            = the Entity around which the block is searched
     * @param distance
     *            = the distance around the entity used to look for the block
     * @return
     */
    public static int[] getRandomBlockCoords(Entity entity, double distance)
    {
        int tempX = -9999;
        int tempY = -1;
        int tempZ = -1;
        //int d = (int)distance;
        int ii = (int) (distance * distance * (distance / 2));
        for (int i = 0; i < ii; i++)
        {
            int x = (int) (entity.posX + entity.worldObj.rand.nextInt((int) (distance)) - (int) (distance / 2));
            int y = (int) (entity.posY + entity.worldObj.rand.nextInt((int) (distance / 2)) - (int) (distance / 4));
            int z = (int) (entity.posZ + entity.worldObj.rand.nextInt((int) (distance)) - (int) (distance / 2));
            int k = entity.worldObj.getBlockId(MathHelper.floor_double(x), MathHelper.floor_double(y + 1.1D), MathHelper.floor_double(z));
            int j = entity.worldObj.getBlockId(MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z));
            int l = entity.worldObj.getBlockId(MathHelper.floor_double(x + 1.1D), MathHelper.floor_double(y), MathHelper.floor_double(z));
            int m = entity.worldObj.getBlockId(MathHelper.floor_double(x - 1.1D), MathHelper.floor_double(y), MathHelper.floor_double(z));
            int n = entity.worldObj.getBlockId(MathHelper.floor_double(x), MathHelper.floor_double(y - 1.1D), MathHelper.floor_double(z));
            int o = entity.worldObj.getBlockId(MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z + 1.1D));
            int p = entity.worldObj.getBlockId(MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z - 1.1D));

            //k = 0;
            float tX = (float) x - (float) entity.posX;
            float tY = (float) y - (float) entity.posY;
            float tZ = (float) z - (float) entity.posZ;
            float spawnDist = tX * tX + tY * tY + tZ * tZ;

            if (allowedBlock(j) && (k == 0 || l == 0 || m == 0 || n == 0 || o == 0 || p == 0) & spawnDist > 100F)
            {
                tempX = x;
                tempY = y;
                tempZ = z;
                break;

            }

        }

        return (new int[] { tempX, tempY, tempZ });
    }

    public static boolean allowedBlock(int ID)
    {
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
                && ID < 134//the rest
        ;

    }

    /**
     * Method called to tame an entity, it will check that the player has slots
     * for taming, increase the taming count of the player, add the
     * player.username as the owner of the entity, and name the entity.
     * 
     * @param ep
     * @param entity
     * @return
     */
    public static boolean tameWithName(EntityPlayer ep, MoCIMoCreature entity) {
        //System.out.println("number of pets for " + ep + " = " + count + "limit = " + MoCreatures.proxy.getMaxTamed());
    	int max = 0;
        if (MoCreatures.proxy.enableOwnership) 
        {
            
                max = MoCreatures.proxy.maxTamed;
                int count = MoCTools.numberTamedByPlayer(ep);
                if (isThisPlayerAnOP(ep)) 
                {
                	max = MoCreatures.proxy.maxOPTamed;
                }
                if (count >= max) 
                {
                	String message = "\2474" + ep.username + " can not tame more creatures, limit of " + max + " reached";
                	MoCServerPacketHandler.sendMsgToPlayer((EntityPlayerMP) ep, message);
                	return false;
                } else 
                {
                    if (!entity.getOwnerName().equals(ep.username)) 
                    {
                    	NBTTagCompound nbtt = ep.getEntityData();
                    	nbtt.setInteger("NumberTamed", count + 1);
                    }
                }
            
            entity.setOwner(ep.username);
        }
        if (MoCreatures.isServer()) 
        {
            MoCServerPacketHandler.sendNameGUI((EntityPlayerMP) ep, ((Entity) entity).entityId);
        }
            entity.setTamed(true);
            return true;
        }

    /**
     * returns the number of entities already tamed by the player ep
     * 
     * @param ep
     * @return
     */
    public static int numberTamedByPlayer(EntityPlayer ep)
    {
        NBTTagCompound nbtt = ep.getEntityData();
        int count = nbtt.getInteger("NumberTamed");
        return count;
    }

    /**
     * Decreases the number of entity tamed by the player by 1
     * 
     * @param ep
     */
    public static void reduceTamedByPlayer(EntityPlayer ep)
    {
        int count = MoCTools.numberTamedByPlayer(ep);
        //System.out.println("tamed entities for online player " + ep.username + " =" + count);
        NBTTagCompound nbtt = ep.getEntityData();
        count--;
        if (count < 0)
        {
            count = 0;
        }
        //System.out.println("reducing tamed count for player " + ep.username + " the count now is " + count);
        nbtt.setInteger("NumberTamed", count);
    }

    /**
     * Reduces the counter on the offline player
     * 
     * @param playername
     */
    public static void reduceTamedByOfflinePlayer(String playername)
    {
        NBTTagCompound compound = ((SaveHandler) ((WorldServer) MinecraftServer.getServer().worldServerForDimension(0)).getSaveHandler()).getPlayerData(playername);
        if (compound != null && compound.hasKey("ForgeData"))
        {
            NBTTagCompound nbtt = compound.getCompoundTag("ForgeData");
            int count = nbtt.getInteger("NumberTamed");
            //System.out.println("tamed entities for player " + playername + " =" + count);
            count--;
            if (count < 0)
            {
                count = 0;
            }
            nbtt.setInteger("NumberTamed", count);
            compound.setCompoundTag("ForgeData", nbtt);

            try
            {
                SaveHandler saveHandler = ((SaveHandler) ((WorldServer) MinecraftServer.getServer().worldServerForDimension(0)).getSaveHandler());
                // as long as we know the world folder name we can generate the path to players directory
                // still need to test how other worlds will work
                String playersDirectory = ".\\" + saveHandler.getWorldDirectoryName() + "\\players\\";
                File playerFile = new File(playersDirectory + playername + ".dat");
                File playerFileNew = new File(playersDirectory, playername + ".tmp");
                //System.out.println("playerFile = " + playerFile + ", playerFileNew = " + playerFileNew + ", playerFile parent = " + playerFile.getParent());

                CompressedStreamTools.writeCompressed(compound, new FileOutputStream(playerFileNew));

                if (playerFile.exists())
                {
                    playerFile.delete();
                }

                playerFileNew.renameTo(playerFile);

                // test to see if changes took effect
                compound = ((SaveHandler) ((WorldServer) MinecraftServer.getServer().worldServerForDimension(0)).getSaveHandler()).getPlayerData(playername);
                if (compound.hasKey("ForgeData"))
                {
                    NBTTagCompound nbttest = compound.getCompoundTag("ForgeData");
                    //System.out.println("tamed entities for player " + playername + " is now " + nbttest.getInteger("NumberTamed"));
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Destroys blocks in front of entity
     * @param entity 
     * @param distance: used to calculate the distance where the target block is located
     * @param strength: int 1 - 3.  Checked against block hardness, also used to calculate how many blocks are recovered
     * @param height:  how many rows of blocks are destroyed in front of the entity
     * @return the count of blocks destroyed
     */
    public static int destroyBlocksInFront(Entity entity, double distance, int strength, int height)
    {
        if (strength == 0) { return 0; }
        int count = 0;
        float strengthF = (float) strength;
        double newPosX = entity.posX - (distance * Math.cos((MoCTools.realAngle(entity.rotationYaw - 90F)) / 57.29578F));
        double newPosZ = entity.posZ - (distance * Math.sin((MoCTools.realAngle(entity.rotationYaw - 90F)) / 57.29578F));
        double newPosY = entity.posY;
        int x = MathHelper.floor_double(newPosX);
        int y = MathHelper.floor_double(newPosY);
        int z = MathHelper.floor_double(newPosZ);

        for (int i = 0; i < height; i++)
        {
            int j = entity.worldObj.getBlockId(x, y + i, z);
            if (j != 0)
            {
                if (Block.blocksList[j].getBlockHardness(entity.worldObj, x, y + i, z) <= strengthF)
                {
                    Block.blocksList[j].dropBlockAsItemWithChance(entity.worldObj, x, y + i, z, entity.worldObj.getBlockMetadata(x, y + i, z), 0.20F * strengthF, 1);
                    entity.worldObj.setBlock(x, y + i, z, 0, 0, 3);//MC 1.5
                    
                    if (entity.worldObj.rand.nextInt(3) == 0)
                    {
                        MoCTools.playCustomSound(entity, "golemwalk", entity.worldObj);
                        count++; //only counts recovered blocks
                    }

                }
            }
        }
        return count;

    }
    
    public static void dropInventory(Entity entity, MoCAnimalChest animalchest)
    {
        if (animalchest == null || !(MoCreatures.isServer()) ) return;
        
        int i = MathHelper.floor_double(entity.posX);
        int j = MathHelper.floor_double(entity.boundingBox.minY);
        int k = MathHelper.floor_double(entity.posZ);
        
        for (int l = 0; l < animalchest.getSizeInventory(); l++)
        {
            ItemStack itemstack = animalchest.getStackInSlot(l);
            if (itemstack == null)
            {
                continue;
            }
            float f = (entity.worldObj.rand.nextFloat() * 0.8F) + 0.1F;
            float f1 = (entity.worldObj.rand.nextFloat() * 0.8F) + 0.1F;
            float f2 = (entity.worldObj.rand.nextFloat() * 0.8F) + 0.1F;
            float f3 = 0.05F;
            
            /*while (itemstack.stackSize > 0)
            {
                int i1 = entity.worldObj.rand.nextInt(21) + 10;
                if (i1 > itemstack.stackSize)
                {
                    i1 = itemstack.stackSize;
                }
                itemstack.stackSize -= i1;
                
                EntityItem entityitem = new EntityItem(entity.worldObj, i + f, j + f1, k + f2, new ItemStack(itemstack.itemID, i1, itemstack.getItemDamage()));
                entityitem.motionX = ((float) entity.worldObj.rand.nextGaussian() * f3);
                entityitem.motionY = (((float) entity.worldObj.rand.nextGaussian() * f3) + 0.2F);
                entityitem.motionZ = ((float) entity.worldObj.rand.nextGaussian() * f3);
                entity.worldObj.spawnEntityInWorld(entityitem);
            }*/
            
            EntityItem entityitem = new EntityItem(entity.worldObj,  i + f, j + f1, k + f2, itemstack);
            entityitem.motionX = ((float) entity.worldObj.rand.nextGaussian() * f3);
            entityitem.motionY = (((float) entity.worldObj.rand.nextGaussian() * f3) + 0.2F);
            entityitem.motionZ = ((float) entity.worldObj.rand.nextGaussian() * f3);
            entity.worldObj.spawnEntityInWorld(entityitem);
            animalchest.setInventorySlotContents(l, null);
        }

    }

    /**
     * Drops an amulet with the stored information of the entity passed
     * @param entity
     */
    public static void dropAmulet(MoCEntityAnimal entity)
    {
        if (MoCreatures.isServer())
        {
               ItemStack stack = getProperAmulet(entity);
                if (stack == null) 
                {
                	return;
                }
            	if( stack.stackTagCompound == null )
        		{
                	stack.setTagCompound(new NBTTagCompound());
        		}
        		NBTTagCompound nbtt = stack.stackTagCompound;
        		
                try
                {
                	//TODO change the 21 to the list given based on the class of the creature
                	nbtt.setInteger("SpawnClass", 21); //21 is the spawnlist number for horses //TODO change to a list
                	nbtt.setInteger("Health", entity.getHealth());
            		nbtt.setInteger("Edad", entity.getEdad());
            		nbtt.setString("Name", entity.getName());
            		nbtt.setBoolean("Rideable", entity.getIsRideable());
            		nbtt.setByte("Armor", entity.getArmorType());
            		nbtt.setInteger("CreatureType", entity.getType());
            		nbtt.setBoolean("Adult", entity.getIsAdult());          
            		nbtt.setString("OwnerName", entity.getOwnerName());
                }
                catch (Exception e)
                {
                }
                EntityItem entityitem = new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, stack);

                entityitem.delayBeforeCanPickup = 20;
                entity.worldObj.spawnEntityInWorld(entityitem);
            

            

        }
    }

    /**
     * Returns the right full amulet based on the MoCEntityAnimal passed
     * @param entity
     * @return
     */
    public static ItemStack getProperAmulet(MoCEntityAnimal entity)
    {
    	if (entity instanceof MoCEntityHorse)
    	{
    		//int i = ((MoCEntityHorse)entity).getType();

    		if (entity.getType() == 26 || entity.getType() == 27 || entity.getType() == 28)
    		{
    			return new ItemStack(MoCreatures.amuletbonefull, 1, entity.getType());
    		}
    		
    		if (entity.getType() > 47 && entity.getType() < 60)
            {
                return new ItemStack(MoCreatures.amuletfairyfull, 1, entity.getType());
            }
    		
    		if (entity.getType() == 39 || entity.getType() == 40)
            {
                return new ItemStack(MoCreatures.amuletpegasusfull, 1, entity.getType());
            }
    		
    		if (entity.getType() == 21 || entity.getType() == 22)
            {
               return new ItemStack(MoCreatures.amuletghostfull, 1, entity.getType());
            }
    	}
		return null;
    }
    
    /**
     * Returns the right full empty based on the MoCEntityAnimal passed. Used when the amulet empties its contents
     * @param entity
     * @return
     */
    public static ItemStack getProperEmptyAmulet(MoCEntityAnimal entity)
    {
    	if (entity instanceof MoCEntityHorse)
    	{
    		//int i = ((MoCEntityHorse)entity).getType();

    		if (entity.getType() == 26 || entity.getType() == 27 || entity.getType() == 28)
    		{
    			return new ItemStack(MoCreatures.amuletbone, 1, entity.getType());
    		}
    		
    		if (entity.getType() > 49 && entity.getType() < 60)
            {
                return new ItemStack(MoCreatures.amuletfairy, 1, entity.getType());
            }
    		
    		if (entity.getType() == 39 || entity.getType() == 40)
            {
                return new ItemStack(MoCreatures.amuletpegasus, 1, entity.getType());
            }
    		
    		if (entity.getType() == 21 || entity.getType() == 22)
            {
               return new ItemStack(MoCreatures.amuletghost, 1, entity.getType());
            }
    	}
		return null;
    }
    
    public static int countPlayersInDimension(WorldServer worldObj, int dimension)
    {
    	int playersInDimension = 0;
    	for (int j = 0; j < worldObj.playerEntities.size(); ++j)
        {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)worldObj.playerEntities.get(j);

            if (entityplayermp.dimension == dimension)
            {
            	playersInDimension++;
            }
        }
    	return playersInDimension;
    }
    
    public static boolean isThisPlayerAnOP(EntityPlayer player)
    {
    	if (!MoCreatures.isServer()) 
    	{	
    		return false;
    	}
    	//Set playerListSet = FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().getWhiteListedPlayers();
    	//Set opSet = MinecraftServer.getServer().getConfigurationManager().getOps();
    	Set opSet = FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().getOps();
    	//fSystem.out.println("opsets = " + opSet);
    	if (opSet != null)// && opSet.contains(player.username))
    	{
        	for(Iterator i = opSet.iterator(); i.hasNext();)
            {  
                String opnames = (String)i.next();  
                if (opnames.equalsIgnoreCase(player.username))
                {  
                	return true;
                }  
            }  
    	}
    	
    	
        
    	return false;
    }

    
    /*
     * Spawns maggots
     */
	public static void spawnMaggots(World worldObj, Entity entity)
	{
		 if (MoCreatures.isServer())
	        {
	            int var2 = 1 + worldObj.rand.nextInt(4);
	            for (int i = 0; i < var2; ++i)
	            {
	                float var4 = ((float) (i % 2) - 0.5F) * (float) 1 / 4.0F;
	                float var5 = ((float) (i / 2) - 0.5F) * (float) 1 / 4.0F;
	                MoCEntityMaggot maggot = new MoCEntityMaggot(worldObj);
	               maggot.setLocationAndAngles(entity.posX + (double) var4, entity.posY + 0.5D, entity.posZ + (double) var5, worldObj.rand.nextFloat() * 360.0F, 0.0F);
	                worldObj.spawnEntityInWorld(maggot);
	            }
	        }
		
	}
}
