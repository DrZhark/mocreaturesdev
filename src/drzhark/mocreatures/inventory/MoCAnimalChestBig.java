package drzhark.mocreatures.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;

public class MoCAnimalChestBig extends InventoryLargeChest {

    private final int mySize; //either 27 or 56

    public MoCAnimalChestBig(String name, IInventory par2IInventory, IInventory par3IInventory, int size)
    {
        super(name, par2IInventory, par3IInventory);
        mySize = size;
    }

    @Override
    public int getSizeInventory()
    {
        return mySize;
    }
}
