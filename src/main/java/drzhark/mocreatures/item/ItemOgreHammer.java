package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemOgreHammer extends MoCItem {

    public ItemOgreHammer(String name) {
        super(name);
        this.maxStackSize = 1;
        setMaxDamage(2048);
    }

    /**
     * Returns True is the item is renderer in full 3D when hold.
     */
    @Override
    public boolean isFull3D() {
        return true;
    }

    /**
     * returns the action that specifies what animation to play when the items
     * is being used
     */
    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        return EnumAction.BLOCK;
    }

    /**
     * How long it takes to use or consume an item
     */
    @Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return 72000;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is
     * pressed. Args: itemStack, world, entityPlayer
     */
    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer, EnumHand hand) {
        double coordY = entityplayer.posY + entityplayer.getEyeHeight();
        double coordZ = entityplayer.posZ;
        double coordX = entityplayer.posX;

        for (int x = 3; x < 128; x++) {
            double newPosY = coordY - Math.cos((entityplayer.rotationPitch - 90F) / 57.29578F) * x;
            double newPosX =
                    coordX + Math.cos((MoCTools.realAngle(entityplayer.rotationYaw - 90F) / 57.29578F))
                            * (Math.sin((entityplayer.rotationPitch - 90F) / 57.29578F) * x);
            double newPosZ =
                    coordZ + Math.sin((MoCTools.realAngle(entityplayer.rotationYaw - 90F) / 57.29578F))
                            * (Math.sin((entityplayer.rotationPitch - 90F) / 57.29578F) * x);
            BlockPos pos = new BlockPos(MathHelper.floor_double(newPosX), MathHelper.floor_double(newPosY), MathHelper.floor_double(newPosZ));
            IBlockState blockstate = entityplayer.worldObj.getBlockState(pos);

            if (blockstate.getBlock() != Blocks.AIR) {
                newPosY = coordY - Math.cos((entityplayer.rotationPitch - 90F) / 57.29578F) * (x - 1);
                newPosX =
                        coordX + Math.cos((MoCTools.realAngle(entityplayer.rotationYaw - 90F) / 57.29578F))
                                * (Math.sin((entityplayer.rotationPitch - 90F) / 57.29578F) * (x - 1));
                newPosZ =
                        coordZ + Math.sin((MoCTools.realAngle(entityplayer.rotationYaw - 90F) / 57.29578F))
                                * (Math.sin((entityplayer.rotationPitch - 90F) / 57.29578F) * (x - 1));
                pos = new BlockPos(MathHelper.floor_double(newPosX), MathHelper.floor_double(newPosY), MathHelper.floor_double(newPosZ));
                if (entityplayer.worldObj.getBlockState(pos).getBlock() != Blocks.AIR) {
                    return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
                }

                int blockInfo[] = obtainBlockAndMetadataFromBelt(entityplayer, true);
                if (blockInfo[0] != 0) {
                    if (MoCreatures.isServer()) {
                        Block block = Block.getBlockById(blockInfo[0]);
                        entityplayer.worldObj.setBlockState(pos, block.getDefaultState(), 3);
                        entityplayer.worldObj.playSound(entityplayer, (float) newPosX + 0.5F, (float) newPosY + 0.5F, (float) newPosZ + 0.5F,
                                block.getSoundType().getPlaceSound(), SoundCategory.BLOCKS, (block.getSoundType().getVolume() + 1.0F) / 2.0F, block.getSoundType().getPitch() * 0.8F);
                    }
                    MoCreatures.proxy.hammerFX(entityplayer);
                    //entityplayer.setItemInUse(itemstack, 200);
                }
                return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
            }
        }
        return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
    }

    /**
     * Finds a block from the belt inventory of player, passes the block ID and
     * Metadata and reduces the stack by 1 if not on Creative mode
     *
     * @param entityplayer
     * @return
     */
    private int[] obtainBlockAndMetadataFromBelt(EntityPlayer entityplayer, boolean remove) {
        for (int y = 0; y < 9; y++) {
            ItemStack slotStack = entityplayer.inventory.getStackInSlot(y);
            if (slotStack == null) {
                continue;
            }
            Item itemTemp = slotStack.getItem();
            int metadata = slotStack.getItemDamage();
            if (itemTemp instanceof ItemBlock) {
                if (remove && !entityplayer.capabilities.isCreativeMode) {
                    if (--slotStack.stackSize <= 0) {
                        entityplayer.inventory.setInventorySlotContents(y, null);
                    } else {
                        entityplayer.inventory.setInventorySlotContents(y, slotStack);
                    }
                }
                return new int[] {Item.getIdFromItem(itemTemp), metadata};
            }
        }
        return new int[] {0, 0};
    }

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        return EnumActionResult.FAIL;
    }
}
