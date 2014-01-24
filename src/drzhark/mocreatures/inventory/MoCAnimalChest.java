package drzhark.mocreatures.inventory;

import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class MoCAnimalChest extends InventoryBasic {

    public MoCAnimalChest(String name, int size)
    {
        super(name, true, size);
    }

    public void loadInventoryFromNBT(NBTTagList par1NBTTagList)
    {
        int var2;

        for (var2 = 0; var2 < this.getSizeInventory(); ++var2)
        {
            this.setInventorySlotContents(var2, (ItemStack) null);
        }

        for (var2 = 0; var2 < par1NBTTagList.tagCount(); ++var2)
        {
            NBTTagCompound var3 = (NBTTagCompound) par1NBTTagList.getCompoundTagAt(var2);
            int var4 = var3.getByte("Slot") & 255;

            if (var4 >= 0 && var4 < this.getSizeInventory())
            {
                this.setInventorySlotContents(var4, ItemStack.loadItemStackFromNBT(var3));
            }
        }
    }

    public NBTTagList saveInventoryToNBT()
    {
        NBTTagList var1 = new NBTTagList();

        for (int var2 = 0; var2 < this.getSizeInventory(); ++var2)
        {
            ItemStack var3 = this.getStackInSlot(var2);

            if (var3 != null)
            {
                NBTTagCompound var4 = new NBTTagCompound();
                var4.setByte("Slot", (byte) var2);
                var3.writeToNBT(var4);
                var1.appendTag(var4);
            }
        }

        return var1;
    }
}