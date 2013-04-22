package drzhark.mocreatures.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import drzhark.mocreatures.entity.passive.MoCEntityBigCat;
import drzhark.mocreatures.entity.passive.MoCEntityElephant;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.entity.passive.MoCEntityKitty;
import drzhark.mocreatures.entity.passive.MoCEntityOstrich;
import drzhark.mocreatures.entity.passive.MoCEntityWyvern;

public class MoCItemWhip extends MoCItem {

    public MoCItemWhip(int i)
    {
        super(i);
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
        int j1 = world.getBlockId(i, j, k);
        int k1 = world.getBlockId(i, j + 1, k);
        if ((l != 0) && (k1 == 0) && (j1 != 0) && (j1 != Block.signPost.blockID))
        {
            whipFX(world, i, j, k);
            world.playSoundAtEntity(entityplayer, "whip", 0.5F, 0.4F / ((itemRand.nextFloat() * 0.4F) + 0.8F));
            itemstack.damageItem(1, entityplayer);
            List list = world.getEntitiesWithinAABBExcludingEntity(entityplayer, entityplayer.boundingBox.expand(12D, 12D, 12D));
            for (int l1 = 0; l1 < list.size(); l1++)
            {
                Entity entity = (Entity) list.get(l1);
                
                if (entity instanceof MoCEntityAnimal)
                {
                    MoCEntityAnimal animal = (MoCEntityAnimal) entity;
                    if (MoCreatures.proxy.enableOwnership && animal.getOwnerName() != null && !animal.getOwnerName().equals("") && !entityplayer.username.equals(animal.getOwnerName())) 
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
                    else if ((world.difficultySetting > 0) && entitybigcat.getIsAdult())
                    {
                        //entitybigcat.entityToAttack = entityplayer;
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
                
                if (entity instanceof MoCEntityOstrich)// && entity.riddenByEntity != null)
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
                
                if (entity instanceof MoCEntityElephant)// && entity.riddenByEntity != null)
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
        if (MoCreatures.isServer() && (l != 0) && ((k1 == Block.signPost.blockID) || (j1 == Block.signPost.blockID)) && (j1 != 0))
        {
            //removed
            /*TileEntitySign tileentitysign = (TileEntitySign) world.getBlockTileEntity(i, j + 1, k);
            if (tileentitysign == null)
            {
                tileentitysign = (TileEntitySign) world.getBlockTileEntity(i, j, k);
            }
            if (tileentitysign != null)
            {
                int i2 = 0;
                //List list1 = world.getLoadedEntityList(); //client
                List list1 = world.loadedEntityList; //server
                for (int j2 = 0; j2 < list1.size(); j2++)
                {
                    Entity entity1 = (Entity) list1.get(j2);
                    if (entity1 instanceof EntityOcelot)
                    {
                        entity1.setDead();
                        //MoCEntityBunny entitybunny = (MoCEntityBunny) entity1;
                        //i2++;
                        //entitybunny.setDead();
                    }
                }

                String s = String.valueOf(i2);
                tileentitysign.signText[0] = "";
                tileentitysign.signText[1] = "R.I.P.";
                tileentitysign.signText[2] = (new StringBuilder()).append(s).append(" Ocelots").toString();
                tileentitysign.signText[3] = "";
                if (i2 > 69)
                {
                    //entityplayer.triggerAchievement(MoCreatures.BunnyKilla);
                }
                whipFX(world, i, j, k);
                world.playSoundAtEntity(entityplayer, "swhip", 0.5F, 0.4F / ((itemRand.nextFloat() * 0.4F) + 0.8F));
                itemstack.damageItem(1, entityplayer);
                return true;
            }*/
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
