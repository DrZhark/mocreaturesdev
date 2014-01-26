package drzhark.mocreatures.item;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;

public class ItemStaffTeleport extends MoCItem
{
    public ItemStaffTeleport(String name)
    {
        super(name);
        maxStackSize = 1;
        setMaxDamage(128);
    }

    private int portalPosX;
    private int portalPosY;
    private int portalPosZ;
    private int portalDimension;

    /**
     * Returns True is the item is renderer in full 3D when hold.
     */
    @Override
    public boolean isFull3D()
    {
        return true;
    }

    /**
     * returns the action that specifies what animation to play when the items
     * is being used
     */
    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.block;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is
     * pressed. Args: itemStack, world, entityPlayer
     */
    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer entityplayer)
    {
        if (entityplayer.ridingEntity != null || entityplayer.riddenByEntity != null)
        {
            return par1ItemStack;
        }

        double coordY = entityplayer.posY + (double)entityplayer.getEyeHeight();
        double coordZ = entityplayer.posZ;
        double coordX = entityplayer.posX;
        for (int x = 4; x < 128; x++)
        {
            double newPosY = coordY - Math.cos( (entityplayer.rotationPitch- 90F) / 57.29578F) * x;
            double newPosX = coordX + Math.cos((MoCTools.realAngle(entityplayer.rotationYaw- 90F) / 57.29578F)) * (Math.sin( (entityplayer.rotationPitch- 90F) / 57.29578F) * x );
            double newPosZ = coordZ + Math.sin((MoCTools.realAngle(entityplayer.rotationYaw- 90F) / 57.29578F)) * (Math.sin( (entityplayer.rotationPitch- 90F) / 57.29578F) * x );
            Block block = entityplayer.worldObj.getBlock( MathHelper.floor_double(newPosX),  MathHelper.floor_double(newPosY),  MathHelper.floor_double(newPosZ)); 
            if (block != Blocks.air)
            {
                newPosY = coordY - Math.cos( (entityplayer.rotationPitch- 90F) / 57.29578F) * (x-1);
                newPosX = coordX + Math.cos((MoCTools.realAngle(entityplayer.rotationYaw- 90F) / 57.29578F)) * (Math.sin( (entityplayer.rotationPitch- 90F) / 57.29578F) * (x-1) );
                newPosZ = coordZ + Math.sin((MoCTools.realAngle(entityplayer.rotationYaw- 90F) / 57.29578F)) * (Math.sin( (entityplayer.rotationPitch- 90F) / 57.29578F) * (x-1) );

                if (MoCreatures.isServer())
                {
                    EntityPlayerMP thePlayer = (EntityPlayerMP) entityplayer;
                    thePlayer.playerNetServerHandler.setPlayerLocation((double)newPosX, (double)newPosY, (double)newPosZ, entityplayer.rotationYaw, entityplayer.rotationPitch);
                    MoCTools.playCustomSound(entityplayer, "appearmagic", entityplayer.worldObj);
                }
                MoCreatures.proxy.teleportFX(entityplayer);
                entityplayer.setItemInUse(par1ItemStack, 200);
                par1ItemStack.damageItem(1, entityplayer);

                return par1ItemStack;
            }
        }

        entityplayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        return par1ItemStack;
    }
}