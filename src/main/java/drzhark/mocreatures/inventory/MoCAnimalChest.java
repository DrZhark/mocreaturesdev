package drzhark.mocreatures.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.LockCode;

public class MoCAnimalChest extends InventoryBasic implements ILockableContainer {

    private LockCode lockCode = LockCode.EMPTY_CODE;

    public MoCAnimalChest(String name, int size) {
        super(name, true, size);
    }

    public void loadInventoryFromNBT(NBTTagList par1NBTTagList) {
        int var2;

        for (var2 = 0; var2 < this.getSizeInventory(); ++var2) {
            this.setInventorySlotContents(var2, (ItemStack) null);
        }

        for (var2 = 0; var2 < par1NBTTagList.tagCount(); ++var2) {
            NBTTagCompound var3 = par1NBTTagList.getCompoundTagAt(var2);
            int var4 = var3.getByte("Slot") & 255;

            if (var4 >= 0 && var4 < this.getSizeInventory()) {
                this.setInventorySlotContents(var4, ItemStack.loadItemStackFromNBT(var3));
            }
        }
    }

    public NBTTagList saveInventoryToNBT() {
        NBTTagList var1 = new NBTTagList();

        for (int var2 = 0; var2 < this.getSizeInventory(); ++var2) {
            ItemStack var3 = this.getStackInSlot(var2);

            if (var3 != null) {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte) var2);
                var3.writeToNBT(var4);
                var1.appendTag(var4);
            }
        }

        return var1;
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerChest(playerInventory, this, playerIn);
    }

    @Override
    public String getGuiID() {
        return "";
    }

    @Override
    public boolean isLocked() {
        return this.lockCode != null && !this.lockCode.isEmpty();
    }

    @Override
    public void setLockCode(LockCode code) {
        this.lockCode = code;
    }

    @Override
    public LockCode getLockCode() {
        return this.lockCode;
    }
}
