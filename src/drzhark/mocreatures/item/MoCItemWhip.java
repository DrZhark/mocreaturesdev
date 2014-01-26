package drzhark.mocreatures.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import drzhark.mocreatures.entity.passive.MoCEntityBigCat;
import drzhark.mocreatures.entity.passive.MoCEntityElephant;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.entity.passive.MoCEntityKitty;
import drzhark.mocreatures.entity.passive.MoCEntityOstrich;
import drzhark.mocreatures.entity.passive.MoCEntityWyvern;

public class MoCItemWhip extends MoCItem {

    public MoCItemWhip(String name)
    {
        super(name);
        maxStackSize = 1;
        setMaxDamage(24);
    }

    @Override
    public boolean isFull3D()
    {
        return true;
    }

    public ItemStack onItemRightClick2(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        return itemstack;
    }

    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float f1, float f2, float f3)
    {
        int i1 = 0;
        Block block = world.getBlock(i, j, k);
        Block block1 = world.getBlock(i, j + 1, k);
        if ((l != 0) && (block == Blocks.air) && (block != Blocks.air) && (block != Blocks.standing_sign))
        {
            whipFX(world, i, j, k);
            world.playSoundAtEntity(entityplayer, "mocreatures:whip", 0.5F, 0.4F / ((itemRand.nextFloat() * 0.4F) + 0.8F));
            itemstack.damageItem(1, entityplayer);
            List list = world.getEntitiesWithinAABBExcludingEntity(entityplayer, entityplayer.boundingBox.expand(12D, 12D, 12D));
            for (int l1 = 0; l1 < list.size(); l1++)
            {
                Entity entity = (Entity) list.get(l1);
                
                if (entity instanceof MoCEntityAnimal)
                {
                    MoCEntityAnimal animal = (MoCEntityAnimal) entity;
                    if (MoCreatures.proxy.enableOwnership && animal.getOwnerName() != null && !animal.getOwnerName().equals("") && !entityplayer.getCommandSenderName().equals(animal.getOwnerName()) && !MoCTools.isThisPlayerAnOP(entityplayer)) 
                    { 
                       continue;
                    }
                }
                
                if (entity instanceof MoCEntityBigCat)
                {
                    MoCEntityBigCat entitybigcat = (MoCEntityBigCat) entity;
                    if (entitybigcat.getIsTamed())
                    {
                        entitybigcat.setSitting(!entitybigcat.getIsSitting());
                        i1++;
                    }
                    else if ((world.difficultySetting.getDifficultyId() > 0) && entitybigcat.getIsAdult())
                    {
                        entitybigcat.setTarget(entityplayer);
                    }
                }
                if (entity instanceof MoCEntityHorse)
                {
                    MoCEntityHorse entityhorse = (MoCEntityHorse) entity;
                    if (entityhorse.getIsTamed())
                    {
                        if (entityhorse.riddenByEntity == null)
                        {
                            entityhorse.setEating(!entityhorse.getEating());
                        }
                        else if (entityhorse.isNightmare())
                        {
                            entityhorse.setNightmareInt(250);
                        }
                        else if (entityhorse.sprintCounter == 0)
                        {
                            entityhorse.sprintCounter = 1;
                        }
                        //TODO reactivate the right one prior to release
                        else
                        {
                            entityhorse.setEating(!entityhorse.getEating());
                        }
                    }
                }
                
                if ((entity instanceof MoCEntityKitty))
                {
                    MoCEntityKitty entitykitty = (MoCEntityKitty) entity;
                    if ((entitykitty.getKittyState() > 2) && entitykitty.whipeable())
                    {
                        entitykitty.setSitting(!entitykitty.getIsSitting());
                    }
                }
                
                if ((entity instanceof MoCEntityWyvern))
                {
                    MoCEntityWyvern entitywyvern = (MoCEntityWyvern) entity;
                    if (entitywyvern.getIsTamed() && !entitywyvern.isOnAir())
                    {
                        entitywyvern.setSitting(!entitywyvern.getIsSitting());
                    }
                }
                
                if (entity instanceof MoCEntityOstrich)
                {
                    MoCEntityOstrich entityostrich = (MoCEntityOstrich) entity;

                    //makes ridden ostrich sprint
                    if (entityostrich.riddenByEntity != null && entityostrich.sprintCounter == 0)
                    {
                        entityostrich.sprintCounter = 1;
                    }

                    //toggles hiding of tamed ostriches
                    if (entityostrich.getIsTamed() && entityostrich.riddenByEntity == null)
                    {
                        entityostrich.setHiding(!entityostrich.getHiding());
                    }
                }
                if (entity instanceof MoCEntityElephant)
                {
                    MoCEntityElephant entityelephant = (MoCEntityElephant) entity;

                    //makes elephants charge
                    if (entityelephant.riddenByEntity != null && entityelephant.sprintCounter == 0)
                    {
                        entityelephant.sprintCounter = 1;
                    }
                }
            }

            if (i1 > 6)
            {
                //entityplayer.triggerAchievement(MoCreatures.Indiana);
            }
            return true;
        }
        return false;
    }

    public void whipFX(World world, int i, int j, int k)
    {
        double d = i + 0.5F;
        double d1 = j + 1.0F;
        double d2 = k + 0.5F;
        double d3 = 0.2199999988079071D;
        double d4 = 0.27000001072883606D;
        world.spawnParticle("smoke", d - d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
        world.spawnParticle("flame", d - d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
        world.spawnParticle("smoke", d + d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
        world.spawnParticle("flame", d + d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
        world.spawnParticle("smoke", d, d1 + d3, d2 - d4, 0.0D, 0.0D, 0.0D);
        world.spawnParticle("flame", d, d1 + d3, d2 - d4, 0.0D, 0.0D, 0.0D);
        world.spawnParticle("smoke", d, d1 + d3, d2 + d4, 0.0D, 0.0D, 0.0D);
        world.spawnParticle("flame", d, d1 + d3, d2 + d4, 0.0D, 0.0D, 0.0D);
        world.spawnParticle("smoke", d, d1, d2, 0.0D, 0.0D, 0.0D);
        world.spawnParticle("flame", d, d1, d2, 0.0D, 0.0D, 0.0D);
    }
}