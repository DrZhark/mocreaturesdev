package drzhark.mocreatures.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.item.MoCEntityEgg;

public class MoCItemEgg extends MoCItem {

    int eggType;

    public MoCItemEgg(String name)
    {
        super(name);
        maxStackSize = 16;
        setHasSubtypes(true);
    }

    public MoCItemEgg(String name, int j)
    {
        this(name);
        eggType = j;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
        itemstack.stackSize--;
        if (MoCreatures.isServer())
        {
            int i = itemstack.getItemDamage();
            if (i == 30)
            {
                i = 31; //for ostrich eggs. placed eggs become stolen eggs.
            }
            MoCEntityEgg entityegg = new MoCEntityEgg(world, i);
            entityegg.setPosition(entityplayer.posX, entityplayer.posY, entityplayer.posZ);
            entityplayer.worldObj.spawnEntityInWorld(entityegg);
            entityegg.motionY += world.rand.nextFloat() * 0.05F;
            entityegg.motionX += (world.rand.nextFloat() - world.rand.nextFloat()) * 0.3F;
            entityegg.motionZ += (world.rand.nextFloat() - world.rand.nextFloat()) * 0.3F;
        }
        return itemstack;
    }

   @Override
    public String getUnlocalizedName(ItemStack itemstack)
    {
        return (new StringBuilder()).append(super.getUnlocalizedName()).append(".").append(itemstack.getItemDamage()).toString();
    }
}