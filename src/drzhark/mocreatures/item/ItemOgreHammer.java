package drzhark.mocreatures.item;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;

public class ItemOgreHammer extends MoCItem
{
    public ItemOgreHammer(String name)
    {
        super(name);
        maxStackSize = 1;
        setMaxDamage(2048);
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
     * How long it takes to use or consume an item
     */
    @Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 72000;
    }
    
    /**
     * Called whenever this item is equipped and the right mouse button is
     * pressed. Args: itemStack, world, entityPlayer
     */
    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer entityplayer)
    {
        double coordY = entityplayer.posY + (double)entityplayer.getEyeHeight();
        double coordZ = entityplayer.posZ;
        double coordX = entityplayer.posX;
        int newWallBlockID = 0;
        
        for (int x = 3; x < 128; x++)
        {
            double newPosY = coordY - Math.cos( (entityplayer.rotationPitch- 90F) / 57.29578F) * x;
            double newPosX = coordX + Math.cos((MoCTools.realAngle(entityplayer.rotationYaw- 90F) / 57.29578F)) * (Math.sin( (entityplayer.rotationPitch- 90F) / 57.29578F) * x );
            double newPosZ = coordZ + Math.sin((MoCTools.realAngle(entityplayer.rotationYaw- 90F) / 57.29578F)) * (Math.sin( (entityplayer.rotationPitch- 90F) / 57.29578F) * x );
            Block newWallBlock = entityplayer.worldObj.getBlock( MathHelper.floor_double(newPosX),  MathHelper.floor_double(newPosY),  MathHelper.floor_double(newPosZ)); 
            
            if (newWallBlock != Blocks.air)
            {
                newPosY = coordY - Math.cos( (entityplayer.rotationPitch- 90F) / 57.29578F) * (x-1);
                newPosX = coordX + Math.cos((MoCTools.realAngle(entityplayer.rotationYaw- 90F) / 57.29578F)) * (Math.sin( (entityplayer.rotationPitch- 90F) / 57.29578F) * (x-1) );
                newPosZ = coordZ + Math.sin((MoCTools.realAngle(entityplayer.rotationYaw- 90F) / 57.29578F)) * (Math.sin( (entityplayer.rotationPitch- 90F) / 57.29578F) * (x-1) );
                if (entityplayer.worldObj.getBlock(MathHelper.floor_double(newPosX), MathHelper.floor_double(newPosY), MathHelper.floor_double(newPosZ)) != Blocks.air)  
                {
                    return par1ItemStack;
                }
                
                int blockInfo[] = obtainBlockAndMetadataFromBelt(entityplayer, true);
                if (blockInfo[0] != 0)
                {
                    if (MoCreatures.isServer())
                    {
                        Block block = Block.getBlockById(blockInfo[0]);
                        entityplayer.worldObj.setBlock(MathHelper.floor_double(newPosX),  MathHelper.floor_double(newPosY),  MathHelper.floor_double(newPosZ), block, blockInfo[1], 3);
                        entityplayer.worldObj.playSoundEffect((double)((float)newPosX + 0.5F), (double)((float)newPosY + 0.5F), (double)((float)newPosZ + 0.5F), block.stepSound.func_150496_b(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
                    }
                    MoCreatures.proxy.hammerFX(entityplayer);
                    entityplayer.setItemInUse(par1ItemStack, 200);
                }
                return par1ItemStack;
            }
        }
        return par1ItemStack;
    }

    /**
     * Finds a block from the belt inventory of player, passes the block ID and Metadata and reduces the stack by 1 if not on Creative mode
     * @param entityplayer
     * @return
     */
    private int[] obtainBlockAndMetadataFromBelt(EntityPlayer entityplayer, boolean remove) 
    {
        for (int y = 0; y < 9 ; y++)
        {
            ItemStack slotStack = entityplayer.inventory.getStackInSlot(y);
            if (slotStack == null)
            {
                continue;
            }
            Item itemTemp =  slotStack.getItem();
            int metadata = slotStack.getItemDamage();
            if (itemTemp instanceof ItemBlock)
            {
                if (remove && !entityplayer.capabilities.isCreativeMode)
                {
                    if (--slotStack.stackSize <= 0)
                    {
                        entityplayer.inventory.setInventorySlotContents(y, null);
                    }
                    else
                    {
                        entityplayer.inventory.setInventorySlotContents(y, slotStack);
                    }
                }
                return new int[] {Item.getIdFromItem(itemTemp), metadata};
            }
        }
        return new int[] {0,0};
    }

    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float f1, float f2, float f3)
    {
        return false;
    }
}