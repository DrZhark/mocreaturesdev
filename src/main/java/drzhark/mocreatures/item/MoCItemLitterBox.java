package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.item.MoCEntityLitterBox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class MoCItemLitterBox extends MoCItem {

    public MoCItemLitterBox(String name) {
        super(name);
        this.maxStackSize = 16;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer, EnumHand hand) {

        if (MoCreatures.isServer()) {

            itemstack.stackSize--;
            MoCEntityLitterBox entitylitterbox = new MoCEntityLitterBox(world);
            entitylitterbox.setPosition(entityplayer.posX, entityplayer.posY, entityplayer.posZ);
            entityplayer.worldObj.spawnEntityInWorld(entitylitterbox);
            entitylitterbox.motionY += world.rand.nextFloat() * 0.05F;
            entitylitterbox.motionX += (world.rand.nextFloat() - world.rand.nextFloat()) * 0.3F;
            entitylitterbox.motionZ += (world.rand.nextFloat() - world.rand.nextFloat()) * 0.3F;
        }
        return new ActionResult(EnumActionResult.SUCCESS, itemstack);
    }
}
