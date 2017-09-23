package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.dimension.MoCDirectTeleporter;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemStaffPortal extends MoCItem {

    public ItemStaffPortal(String name) {
        super(name);
        this.maxStackSize = 1;
        setMaxDamage(3);
    }

    private int portalPosX;
    private int portalPosY;
    private int portalPosZ;
    private int portalDimension;

    @Override
    public EnumActionResult
            onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        final ItemStack stack = player.getHeldItem(hand);
        if (worldIn.isRemote) {
            return EnumActionResult.FAIL;
        }
        final boolean hasMending = EnchantmentHelper.getEnchantmentLevel(Enchantments.MENDING, stack) > 0;
        final boolean hasUnbreaking = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, stack) > 0;
        if (hasMending || hasUnbreaking) {
            String enchantments = "unbreaking";
            if (hasMending && hasUnbreaking) {
                enchantments = "mending, unbreaking";
            } else if (hasMending) {
                enchantments = "mending";
            }
            player.sendMessage(new TextComponentTranslation(MoCreatures.MOC_LOGO + 
                TextFormatting.RED + " Detected illegal enchantment(s) '" + TextFormatting.GREEN + enchantments + 
                    TextFormatting.RED + "' on Staff Portal!\nThe item has been removed from your inventory."));
            player.inventory.deleteStack(stack);
            return EnumActionResult.SUCCESS;
        }

        if (stack.getTagCompound() == null) {
            stack.setTagCompound(new NBTTagCompound());
        }

        NBTTagCompound nbtcompound = stack.getTagCompound();

        EntityPlayerMP playerMP = (EntityPlayerMP) player;
        if (player.getRidingEntity() != null || player.isBeingRidden()) {
            return EnumActionResult.FAIL;
        } else {
            if (player.dimension != MoCreatures.WyvernLairDimensionID) {
                this.portalDimension = player.dimension;
                this.portalPosX = (int) player.posX;
                this.portalPosY = (int) player.posY;
                this.portalPosZ = (int) player.posZ;
                writeToNBT(nbtcompound);

                BlockPos var2 = playerMP.mcServer.getWorld(MoCreatures.WyvernLairDimensionID).getSpawnCoordinate();

                if (var2 != null) {
                    playerMP.connection.setPlayerLocation(var2.getX(), var2.getY(), var2.getZ(), 0.0F, 0.0F);
                }
                playerMP.mcServer.getPlayerList().transferPlayerToDimension(playerMP, MoCreatures.WyvernLairDimensionID,
                        new MoCDirectTeleporter(playerMP.mcServer.getWorld(MoCreatures.WyvernLairDimensionID)));
                stack.damageItem(1, player);
                return EnumActionResult.SUCCESS;
            } else {
                //on the WyvernLair!
                if ((player.posX > 1.5D || player.posX < -1.5D) || (player.posZ > 2.5D || player.posZ < -2.5D)) {
                    return EnumActionResult.FAIL;
                }
                readFromNBT(nbtcompound);

                boolean foundSpawn = false;
                if (this.portalPosX == 0 && this.portalPosY == 0 && this.portalPosZ == 0) //dummy staff
                {
                    BlockPos var2 = playerMP.mcServer.getWorld(0).getSpawnPoint();

                    if (var2 != null) {
                        for (int i1 = 0; i1 < 60; i1++) {
                            IBlockState blockstate = playerMP.mcServer.getWorld(0).getBlockState(pos.add(0, i1, 0));
                            IBlockState blockstate1 = playerMP.mcServer.getWorld(0).getBlockState(pos.add(0, i1 + 1, 0));

                            if (blockstate.getBlock() == Blocks.AIR && blockstate1.getBlock() == Blocks.AIR) {
                                playerMP.connection.setPlayerLocation(var2.getX(), (double) var2.getY() + i1 + 1, var2.getZ(), 0.0F,
                                        0.0F);
                                if (MoCreatures.proxy.debug) {
                                    System.out.println("MoC Staff teleporter found location at spawn");
                                }
                                foundSpawn = true;
                                break;
                            }
                        }

                        if (!foundSpawn) {
                            if (MoCreatures.proxy.debug) {
                                System.out.println("MoC Staff teleporter couldn't find an adequate teleport location at spawn");
                            }
                            return EnumActionResult.FAIL;
                        }
                    } else {
                        if (MoCreatures.proxy.debug) {
                            System.out.println("MoC Staff teleporter couldn't find spawn point");
                        }
                        return EnumActionResult.FAIL;
                    }
                } else {
                    playerMP.connection.setPlayerLocation(this.portalPosX, (this.portalPosY) + 1D, this.portalPosZ, 0.0F, 0.0F);
                }

                stack.damageItem(1, player);
                playerMP.mcServer.getPlayerList().transferPlayerToDimension(playerMP, this.portalDimension,
                        new MoCDirectTeleporter(playerMP.mcServer.getWorld(0)));
                return EnumActionResult.SUCCESS;
            }
        }
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

    public void readFromNBT(NBTTagCompound nbt) {
        this.portalPosX = nbt.getInteger("portalPosX");
        this.portalPosY = nbt.getInteger("portalPosY");
        this.portalPosZ = nbt.getInteger("portalPosZ");
        this.portalDimension = nbt.getInteger("portalDimension");
    }

    public void writeToNBT(NBTTagCompound nbt) {
        nbt.setInteger("portalPosX", this.portalPosX);
        nbt.setInteger("portalPosY", this.portalPosY);
        nbt.setInteger("portalPosZ", this.portalPosZ);
        nbt.setInteger("portalDimension", this.portalDimension);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return false;
    }
}
