package drzhark.mocreatures.item;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.dimension.MoCDirectTeleporter;

public class ItemStaffPortal extends MoCItem
{
    public ItemStaffPortal(String name)
    {
        super(name);
        maxStackSize = 1;
        setMaxDamage(3);
    }

    private int portalPosX;
    private int portalPosY;
    private int portalPosZ;
    private int portalDimension;

    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float f1, float f2, float f3)
    {
        if(!MoCreatures.isServer())
        {
            return false;
        }
        if( itemstack.stackTagCompound == null )
        {
             itemstack.setTagCompound(new NBTTagCompound());
        }

        NBTTagCompound nbtcompound = itemstack.stackTagCompound;

        EntityPlayerMP thePlayer = (EntityPlayerMP) entityplayer;
        if (thePlayer.ridingEntity != null || thePlayer.riddenByEntity != null)
        {
            return false;
        }
        else
        {
            if (thePlayer.dimension != MoCreatures.WyvernLairDimensionID)
            {
                portalDimension = thePlayer.dimension;
                portalPosX = (int) thePlayer.posX;
                portalPosY = (int) thePlayer.posY;
                portalPosZ = (int) thePlayer.posZ;
                writeToNBT(nbtcompound);

                ChunkCoordinates var2 = thePlayer.mcServer.worldServerForDimension(MoCreatures.WyvernLairDimensionID).getEntrancePortalLocation();

                if (var2 != null)
                {
                    thePlayer.playerNetServerHandler.setPlayerLocation((double)var2.posX, (double)var2.posY, (double)var2.posZ, 0.0F, 0.0F);
                }
                thePlayer.mcServer.getConfigurationManager().transferPlayerToDimension(thePlayer, MoCreatures.WyvernLairDimensionID, new MoCDirectTeleporter(thePlayer.mcServer.worldServerForDimension(MoCreatures.WyvernLairDimensionID)));
                itemstack.damageItem(1, entityplayer);
                return true;
            }
            else
            {
                //on the WyvernLair!
                if ( (thePlayer.posX > 1.5D || thePlayer.posX < -1.5D) || (thePlayer.posZ > 2.5D || thePlayer.posZ < -2.5D))
                {
                    return false;
                }
                readFromNBT(nbtcompound);

                boolean foundSpawn = false;
                if (portalPosX == 0 && portalPosY == 0 && portalPosZ == 0) //dummy staff
                {
                    ChunkCoordinates var2 = thePlayer.mcServer.worldServerForDimension(0).getSpawnPoint();

                    if (var2 != null)
                    {
                        for (int i1 = 0; i1 < 60; i1++)
                        {
                            Block block = thePlayer.mcServer.worldServerForDimension(0).getBlock(var2.posX, var2.posY + i1, var2.posZ);
                            Block block1 = thePlayer.mcServer.worldServerForDimension(0).getBlock(var2.posX, var2.posY + i1 + 1, var2.posZ);
                            if (block == Blocks.air && block1 == Blocks.air)
                            {
                                thePlayer.playerNetServerHandler.setPlayerLocation((double)var2.posX, (double)var2.posY+i1+1, (double)var2.posZ, 0.0F, 0.0F);
                                if (MoCreatures.proxy.debug) {System.out.println("MoC Staff teleporter found location at spawn");}
                                foundSpawn = true;
                                break;
                            }
                        }

                        if (!foundSpawn)
                        {
                            if (MoCreatures.proxy.debug) {System.out.println("MoC Staff teleporter couldn't find an adequate teleport location at spawn");}
                            return false;
                        }
                    }
                    else
                    {
                        if (MoCreatures.proxy.debug) System.out.println("MoC Staff teleporter couldn't find spawn point");
                        return false;
                    }
                }
                else
                {
                    thePlayer.playerNetServerHandler.setPlayerLocation((double)portalPosX, ((double)portalPosY) + 1D, (double)portalPosZ, 0.0F, 0.0F);
                }

                itemstack.damageItem(1, entityplayer);
                thePlayer.mcServer.getConfigurationManager().transferPlayerToDimension(thePlayer, portalDimension, new MoCDirectTeleporter(thePlayer.mcServer.worldServerForDimension(0)));
                return true;
            }
        }
    }

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
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        return par1ItemStack;
    }

    public void readFromNBT(NBTTagCompound nbt)
    {
        this.portalPosX = nbt.getInteger("portalPosX");
        this.portalPosY = nbt.getInteger("portalPosY");
        this.portalPosZ = nbt.getInteger("portalPosZ");
        this.portalDimension = nbt.getInteger("portalDimension");
    }

    public void writeToNBT(NBTTagCompound nbt)
    {
        nbt.setInteger("portalPosX", this.portalPosX);
        nbt.setInteger("portalPosY", this.portalPosY);
        nbt.setInteger("portalPosZ", this.portalPosZ);
        nbt.setInteger("portalDimension", this.portalDimension);
    }
}