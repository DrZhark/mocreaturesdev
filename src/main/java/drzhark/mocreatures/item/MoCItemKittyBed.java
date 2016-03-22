package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.item.MoCEntityKittyBed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MoCItemKittyBed extends MoCItem {

    private int sheetType;

    public MoCItemKittyBed(String name) {
        super(name);
    }

    public MoCItemKittyBed(String name, int type) {
        this(name);
        this.sheetType = type;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        if (MoCreatures.isServer()) {
            MoCEntityKittyBed entitykittybed = new MoCEntityKittyBed(world, this.sheetType);
            entitykittybed.setPosition(entityplayer.posX, entityplayer.posY, entityplayer.posZ);
            world.spawnEntityInWorld(entitykittybed);
            entitykittybed.motionY += world.rand.nextFloat() * 0.05F;
            entitykittybed.motionX += (world.rand.nextFloat() - world.rand.nextFloat()) * 0.3F;
            entitykittybed.motionZ += (world.rand.nextFloat() - world.rand.nextFloat()) * 0.3F;
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
        }
        return itemstack;
    }
}
