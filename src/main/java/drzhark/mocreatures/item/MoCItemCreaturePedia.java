package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.aquatic.MoCEntityDolphin;
import drzhark.mocreatures.entity.passive.MoCEntityBunny;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.entity.passive.MoCEntityTurtle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.List;

public class MoCItemCreaturePedia extends MoCItem {

    public MoCItemCreaturePedia(String name) {
        super(name);
        this.maxStackSize = 1;
    }

    /**
     * Called when a player right clicks a entity with a item.
     */
    public void itemInteractionForEntity2(ItemStack par1ItemStack, EntityLiving entityliving) {
        if (entityliving.worldObj.isRemote) {
            return;
        }

        if (entityliving instanceof MoCEntityHorse) {
            MoCreatures.showCreaturePedia("/mocreatures/pedia/horse.png");
            return;
        }

        if (entityliving instanceof MoCEntityTurtle) {
            MoCreatures.showCreaturePedia("/mocreatures/pedia/turtle.png");
            return;
        }

        if (entityliving instanceof MoCEntityBunny) {
            MoCreatures.showCreaturePedia("/mocreatures/pedia/bunny.png");
            return;
        }

        if (entityliving instanceof MoCEntityDolphin) {
            MoCreatures.showCreaturePedia("/mocreatures/pedia/dolphin.png");
            return;
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer, EnumHand hand) {
        if (!world.isRemote) {
            double dist = 5D;
            double d1 = -1D;
            EntityLivingBase entityliving = null;
            List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(entityplayer, entityplayer.getEntityBoundingBox().expand(dist, dist, dist));
            for (int i = 0; i < list.size(); i++) {
                Entity entity1 = list.get(i);
                if (entity1 == null || !(entity1 instanceof EntityLivingBase)) {
                    continue;
                }

                if (!(entityplayer.canEntityBeSeen(entity1))) {
                    continue;
                }

                double d2 = entity1.getDistanceSq(entityplayer.posX, entityplayer.posY, entityplayer.posZ);
                if (((dist < 0.0D) || (d2 < (dist * dist))) && ((d1 == -1D) || (d2 < d1))
                        && ((EntityLivingBase) entity1).canEntityBeSeen(entityplayer)) {
                    d1 = d2;
                    entityliving = (EntityLivingBase) entity1;
                }
            }

            if (entityliving == null) {
                return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
            }

            if (entityliving instanceof MoCEntityHorse) {
                MoCreatures.showCreaturePedia(entityplayer, "/mocreatures/pedia/horse.png");
                return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
            }

            if (entityliving instanceof MoCEntityTurtle) {
                MoCreatures.showCreaturePedia(entityplayer, "/mocreatures/pedia/turtle.png");
                return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
            }

            if (entityliving instanceof MoCEntityBunny) {
                MoCreatures.showCreaturePedia(entityplayer, "/mocreatures/pedia/bunny.png");
                return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
            }
        }

        return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
    }
}
