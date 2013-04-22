package drzhark.mocreatures.block;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class MultiItemBlock extends ItemBlock {
    
    

    public MultiItemBlock(int id) 
    {
        super(id);
        setHasSubtypes(true);
        //setItemName("multiBlock"); //TODO
        this.setUnlocalizedName("multiBlock");
    }
    
    @Override
    public int getMetadata (int damageValue) {
        return damageValue;
    }
    
    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        //return getItemName() + "." + subNames[itemstack.getItemDamage()];
        return getUnlocalizedName() + "." + MoCreatures.multiBlockNames.get(itemstack.getItemDamage());
    }

}