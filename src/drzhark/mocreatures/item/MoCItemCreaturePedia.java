package drzhark.mocreatures.item;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.aquatic.MoCEntityDolphin;
import drzhark.mocreatures.entity.passive.MoCEntityBunny;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.entity.passive.MoCEntityTurtle;

public class MoCItemCreaturePedia extends MoCItem {

    public MoCItemCreaturePedia(String name)
    {
        super(name);
        maxStackSize = 1;
    }

    /**
     * Called when a player right clicks a entity with a item.
     */
    public void itemInteractionForEntity2(ItemStack par1ItemStack, EntityLiving entityliving)
    {
        if (entityliving.worldObj.isRemote) { return; }

        if (entityliving instanceof MoCEntityHorse)
        {
            MoCreatures.showCreaturePedia("/mocreatures/pedia/horse.png");
            return;
        }

        if (entityliving instanceof MoCEntityTurtle)
        {
            MoCreatures.showCreaturePedia("/mocreatures/pedia/turtle.png");
            return;
        }

        if (entityliving instanceof MoCEntityBunny)
        {
            MoCreatures.showCreaturePedia("/mocreatures/pedia/bunny.png");
            return;
        }

        if (entityliving instanceof MoCEntityDolphin)
        {
            MoCreatures.showCreaturePedia("/mocreatures/pedia/dolphin.png");
            return;
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World worldObj, EntityPlayer entityplayer)
    {
        if (!worldObj.isRemote)
        {
            double dist = 5D;
            double newPosX = entityplayer.posX - (dist * Math.cos((MoCTools.realAngle(entityplayer.rotationYaw - 90F)) / 57.29578F));
            double newPosZ = entityplayer.posZ - (dist * Math.sin((MoCTools.realAngle(entityplayer.rotationYaw - 90F)) / 57.29578F));
            double newPosY = entityplayer.posY - 1D;

            double d1 = -1D;
            EntityLivingBase entityliving = null;
            List list = worldObj.getEntitiesWithinAABBExcludingEntity(entityplayer, entityplayer.boundingBox.expand(dist, dist, dist));
            for (int i = 0; i < list.size(); i++)
            {
                Entity entity1 = (Entity) list.get(i);
                if (entity1 == null || !(entity1 instanceof EntityLivingBase))
                {
                    continue;
                }

                if (!(entityplayer.canEntityBeSeen(entity1)))
                {
                    continue;
                }

                double d2 = entity1.getDistanceSq(entityplayer.posX, entityplayer.posY, entityplayer.posZ);
                if (((dist < 0.0D) || (d2 < (dist * dist))) && ((d1 == -1D) || (d2 < d1)) && ((EntityLivingBase) entity1).canEntityBeSeen(entityplayer))
                {
                    d1 = d2;
                    entityliving = (EntityLivingBase) entity1;
                }
            }

            if (entityliving == null) { return itemstack; }

            if (entityliving instanceof MoCEntityHorse)
            {
                MoCreatures.showCreaturePedia(entityplayer, "/mocreatures/pedia/horse.png");
                return itemstack;
            }

            if (entityliving instanceof MoCEntityTurtle)
            {
                MoCreatures.showCreaturePedia(entityplayer, "/mocreatures/pedia/turtle.png");
                return itemstack;
            }

            if (entityliving instanceof MoCEntityBunny)
            {
                MoCreatures.showCreaturePedia(entityplayer, "/mocreatures/pedia/bunny.png");
                return itemstack;
            }

            //TODO 4FIX             
            /*if (entityliving instanceof MoCEntityDolphin)
            {
                //System.out.println("showing dolphin");
                MoCreatures.showCreaturePedia(entityplayer, "/mocreatures/pedia/dolphin.png");
                return itemstack;
            }*/
        }

        return itemstack;
    }

    private int ageCounter;
}