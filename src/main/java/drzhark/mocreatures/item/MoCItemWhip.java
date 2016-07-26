package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import drzhark.mocreatures.entity.passive.MoCEntityElephant;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.entity.passive.MoCEntityKitty;
import drzhark.mocreatures.entity.passive.MoCEntityNewBigCat;
import drzhark.mocreatures.entity.passive.MoCEntityOstrich;
import drzhark.mocreatures.entity.passive.MoCEntityPetScorpion;
import drzhark.mocreatures.entity.passive.MoCEntityWyvern;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

import java.util.List;

public class MoCItemWhip extends MoCItem {

    public MoCItemWhip(String name) {
        super(name);
        this.maxStackSize = 1;
        setMaxDamage(24);
    }

    @Override
    public boolean isFull3D() {
        return true;
    }

    public ItemStack onItemRightClick2(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        return itemstack;
    }

    @Override
    public boolean
            onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        int i1 = 0;
        Block block = worldIn.getBlockState(pos).getBlock();
        Block block1 = worldIn.getBlockState(pos.up()).getBlock();
        if (side != EnumFacing.DOWN && (block1 == Blocks.AIR) && (block != Blocks.AIR) && (block != Blocks.standing_sign)) {
            whipFX(worldIn, pos);
            worldIn.playSoundAtEntity(playerIn, "mocreatures:whip", 0.5F, 0.4F / ((itemRand.nextFloat() * 0.4F) + 0.8F));
            stack.damageItem(1, playerIn);
            List<Entity> list = worldIn.getEntitiesWithinAABBExcludingEntity(playerIn, playerIn.getEntityBoundingBox().expand(12D, 12D, 12D));
            for (int l1 = 0; l1 < list.size(); l1++) {
                Entity entity = list.get(l1);

                if (entity instanceof MoCEntityAnimal) {
                    MoCEntityAnimal animal = (MoCEntityAnimal) entity;
                    if (MoCreatures.proxy.enableOwnership && animal.getOwnerName() != null && !animal.getOwnerName().equals("")
                            && !playerIn.getName().equals(animal.getOwnerName()) && !MoCTools.isThisPlayerAnOP(playerIn)) {
                        continue;
                    }
                }

                if (entity instanceof MoCEntityNewBigCat) {
                    MoCEntityNewBigCat entitybigcat = (MoCEntityNewBigCat) entity;
                    if (entitybigcat.getIsTamed()) {
                        entitybigcat.setSitting(!entitybigcat.getIsSitting());
                        i1++;
                    } else if ((worldIn.getDifficulty().getDifficultyId() > 0) && entitybigcat.getIsAdult()) {
                        entitybigcat.setAttackTarget(playerIn);
                    }
                }
                if (entity instanceof MoCEntityHorse) {
                    MoCEntityHorse entityhorse = (MoCEntityHorse) entity;
                    if (entityhorse.getIsTamed()) {
                        if (entityhorse.riddenByEntity == null) {
                            entityhorse.setSitting(!entityhorse.getIsSitting());
                        } else if (entityhorse.isNightmare()) {
                            entityhorse.setNightmareInt(100);
                        } else if (entityhorse.sprintCounter == 0) {
                            entityhorse.sprintCounter = 1;
                        }
                    }
                }

                if ((entity instanceof MoCEntityKitty)) {
                    MoCEntityKitty entitykitty = (MoCEntityKitty) entity;
                    if ((entitykitty.getKittyState() > 2) && entitykitty.whipeable()) {
                        entitykitty.setSitting(!entitykitty.getIsSitting());
                    }
                }

                if ((entity instanceof MoCEntityWyvern)) {
                    MoCEntityWyvern entitywyvern = (MoCEntityWyvern) entity;
                    if (entitywyvern.getIsTamed() && entitywyvern.riddenByEntity == null && !entitywyvern.isOnAir()) {
                        entitywyvern.setSitting(!entitywyvern.getIsSitting());
                    }
                }

                if ((entity instanceof MoCEntityPetScorpion)) {
                    MoCEntityPetScorpion petscorpion = (MoCEntityPetScorpion) entity;
                    if (petscorpion.getIsTamed() && petscorpion.riddenByEntity == null) {
                        petscorpion.setSitting(!petscorpion.getIsSitting());
                    }
                }

                if (entity instanceof MoCEntityOstrich) {
                    MoCEntityOstrich entityostrich = (MoCEntityOstrich) entity;

                    //makes ridden ostrich sprint
                    if (entityostrich.riddenByEntity != null && entityostrich.sprintCounter == 0) {
                        entityostrich.sprintCounter = 1;
                    }

                    //toggles hiding of tamed ostriches
                    if (entityostrich.getIsTamed() && entityostrich.riddenByEntity == null) {
                        entityostrich.setHiding(!entityostrich.getHiding());
                    }
                }
                if (entity instanceof MoCEntityElephant) {
                    MoCEntityElephant entityelephant = (MoCEntityElephant) entity;

                    //makes elephants charge
                    if (entityelephant.riddenByEntity != null && entityelephant.sprintCounter == 0) {
                        entityelephant.sprintCounter = 1;
                    }
                }
            }

            if (i1 > 6) {
                //entityplayer.triggerAchievement(MoCreatures.Indiana);
            }
            return true;
        }
        return false;
    }

    public void whipFX(World world, BlockPos pos) {
        double d = pos.getX() + 0.5F;
        double d1 = pos.getY() + 1.0F;
        double d2 = pos.getZ() + 0.5F;
        double d3 = 0.2199999988079071D;
        double d4 = 0.27000001072883606D;
        world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d - d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
        world.spawnParticle(EnumParticleTypes.FLAME, d - d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
        world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d + d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
        world.spawnParticle(EnumParticleTypes.FLAME, d + d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D);
        world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d, d1 + d3, d2 - d4, 0.0D, 0.0D, 0.0D);
        world.spawnParticle(EnumParticleTypes.FLAME, d, d1 + d3, d2 - d4, 0.0D, 0.0D, 0.0D);
        world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d, d1 + d3, d2 + d4, 0.0D, 0.0D, 0.0D);
        world.spawnParticle(EnumParticleTypes.FLAME, d, d1 + d3, d2 + d4, 0.0D, 0.0D, 0.0D);
        world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d, d1, d2, 0.0D, 0.0D, 0.0D);
        world.spawnParticle(EnumParticleTypes.FLAME, d, d1, d2, 0.0D, 0.0D, 0.0D);
    }
}
