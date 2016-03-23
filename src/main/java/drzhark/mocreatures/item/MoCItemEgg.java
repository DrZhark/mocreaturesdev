package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MoCItemEgg extends MoCItem {

    public MoCItemEgg(String name) {
        super(name);
        this.maxStackSize = 16;
        setHasSubtypes(true);
        for (int i = 0; i < 91; i++) {
            if (!MoCreatures.isServer())
                Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
                        .register(this, i, new ModelResourceLocation("mocreatures:mocegg", "inventory"));
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        itemstack.stackSize--;
        if (MoCreatures.isServer() && entityplayer.onGround) {
            int i = itemstack.getItemDamage();
            if (i == 30) {
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
    public String getUnlocalizedName(ItemStack itemstack) {
        return (new StringBuilder()).append(getUnlocalizedName()).append(".").append(itemstack.getItemDamage()).toString();
    }
}
