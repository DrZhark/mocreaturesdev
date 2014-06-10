package drzhark.mocreatures.item;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.aquatic.MoCEntityFishy;
import drzhark.mocreatures.entity.item.MoCEntityFishBowl;

public class MoCItemFishBowl extends MoCItem {

    private int fishBowlType;
    private int ageCounter;

    public MoCItemFishBowl(String name)
    {
        super(name);
        maxStackSize = 16;
        setHasSubtypes(true);
        ageCounter = 0;
    }

    public MoCItemFishBowl(String name, int j)
    {
        this(name);
        fishBowlType = j;
        ageCounter = 0;
    }

    public EntityLiving getClosestFish(World worldObj, Entity entity, double d)
    {
        double d1 = -1D;
        EntityLiving entityliving = null;
        List list = worldObj.getEntitiesWithinAABBExcludingEntity(entity, entity.boundingBox.expand(d, d, d));
        for (int i = 0; i < list.size(); i++)
        {
            Entity entity1 = (Entity) list.get(i);
            if (!(entity1 instanceof MoCEntityFishy))
            {
                continue;
            }

            double d2 = entity1.getDistanceSq(entity.posX, entity.posY, entity.posZ);
            if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1)) && ((EntityLiving) entity1).canEntityBeSeen(entity))
            {
                d1 = d2;
                entityliving = (EntityLiving) entity1;
            }
        }

        return entityliving;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World worldObj, EntityPlayer entityplayer)
    {
        float var4 = 1.0F;
        double var5 = entityplayer.prevPosX + (entityplayer.posX - entityplayer.prevPosX) * (double) var4;
        double var7 = entityplayer.prevPosY + (entityplayer.posY - entityplayer.prevPosY) * (double) var4 + 1.62D - (double) entityplayer.yOffset;
        double var9 = entityplayer.prevPosZ + (entityplayer.posZ - entityplayer.prevPosZ) * (double) var4;

        MovingObjectPosition movingObjectPos = this.getMovingObjectPositionFromPlayer(worldObj, entityplayer, true);//fishBowlType == 0);

        if (movingObjectPos == null)
        {
            return itemstack;
        }
        else
        {
            if ((!worldObj.isRemote) && (fishBowlType == 11))// && movingObjectPos.entityHit instanceof MoCEntityFishy)
            {

                EntityLiving target = getClosestFish(worldObj, entityplayer, 2D);
                if (target != null)
                {
                    if (--itemstack.stackSize == 0)
                    {
                    	ItemStack fishbowl = ((MoCEntityFishBowl.toItemStack(((MoCEntityFishy) target).getType())));
                    	((MoCEntityFishy) target).setTamed(false);
                    	((MoCEntityFishy) target).setOwner("");
                    	target.setDead();
                        return fishbowl;
                    }
                    else
                    {
                        entityplayer.inventory.addItemStackToInventory(MoCEntityFishBowl.toItemStack(((MoCEntityFishy) target).getType()));
                    	((MoCEntityFishy) target).setTamed(false);
                    	((MoCEntityFishy) target).setOwner("");
                        target.setDead();
                        return itemstack;
                    }
                }
            }

            if (movingObjectPos.typeOfHit == MovingObjectType.BLOCK)
            {
                int var13 = movingObjectPos.blockX;
                int var14 = movingObjectPos.blockY;
                int var15 = movingObjectPos.blockZ;

                if (!worldObj.isRemote && fishBowlType == 0 && worldObj.getBlock(var13, var14, var15).getMaterial() == Material.water && worldObj.getBlockMetadata(var13, var14, var15) == 0)
                {

                    if (--itemstack.stackSize == 0)
                    {
                    	System.out.println("test1");
                        return new ItemStack(MoCreatures.fishbowl_w, 1, 11);
                    }
                    else
                    {
                    	System.out.println("test2");
                        entityplayer.inventory.addItemStackToInventory(new ItemStack(MoCreatures.fishbowl_w, 1, 11));

                        return itemstack;
                    }
                }

                //TODO
                //water filling stuff
                if (MoCreatures.isServer() && (fishBowlType > 0 && fishBowlType < 11) && worldObj.getBlock(var13, var14, var15).getMaterial() == Material.water && worldObj.getBlockMetadata(var13, var14, var15) == 0)
                {
                    MoCEntityFishy entityfish = new MoCEntityFishy(worldObj);
                    entityfish.setPosition(var13, var14, var15);
                    entityfish.setTypeInt(fishBowlType);
                    entityfish.selectType();
                    worldObj.spawnEntityInWorld(entityfish);
                    MoCTools.tameWithName((EntityPlayer) entityplayer, entityfish);

                    if (--itemstack.stackSize == 0)
                    {
                        return new ItemStack(MoCreatures.fishbowl_e, 1, 0);
                    }
                    else
                    {
                        entityplayer.inventory.addItemStackToInventory(new ItemStack(MoCreatures.fishbowl_e, 1, 0));
                        return itemstack;
                    }
                }

                if (MoCreatures.isServer() && worldObj.getBlock(var13, var14, var15).getMaterial().isSolid())
                {
                    MoCEntityFishBowl entityfishbowl = new MoCEntityFishBowl(worldObj, itemstack.getItemDamage());
                    entityfishbowl.setPosition(entityplayer.posX, entityplayer.posY, entityplayer.posZ);
                    worldObj.spawnEntityInWorld(entityfishbowl);
                    if (--itemstack.stackSize == 0)
                    {
                        entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                    }

                    return itemstack;
                }
            }
        }

        return itemstack;
    }
}