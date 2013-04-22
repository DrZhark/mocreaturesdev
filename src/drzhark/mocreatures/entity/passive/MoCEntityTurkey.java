package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MoCEntityTurkey extends MoCEntityAnimal {

    public MoCEntityTurkey(World world)
    {
        super(world);
        setSize(0.5F, 0.5F);
        health = 6;
        texture = MoCreatures.proxy.MODEL_TEXTURE + "turkey.png";
    }

    @Override
    public int getMaxHealth()
    {
        return 6;
    }

    @Override
    protected String getDeathSound()
    {
        return "turkeyhurt";
    }

    @Override
    protected String getHurtSound()
    {
        return "turkeyhurt";
    }

    @Override
    protected String getLivingSound()
    {
        return "turkey";
    }

    @Override
    protected int getDropItemId()
    {
        boolean flag = (rand.nextInt(2) == 0);
        if (flag) { return MoCreatures.rawTurkey.itemID; }
        return Item.feather.itemID;
    }

    @Override
    public boolean getCanSpawnHere()
    {
        return (MoCreatures.proxy.getFrequency(this.getEntityName()) > 0) && super.getCanSpawnHere();
    }

    @Override
    public boolean interact(EntityPlayer entityplayer)
    {
        if (super.interact(entityplayer)) { return false; }

        ItemStack itemstack = entityplayer.inventory.getCurrentItem();

        if (!getIsTamed() && (itemstack != null) && (itemstack.itemID == Item.melonSeeds.itemID))
        {
            if (MoCreatures.isServer())
            {
                MoCTools.tameWithName((EntityPlayerMP) entityplayer, this);

            }
        }

        return true;
    }

    @Override
    public boolean isMyHealFood(ItemStack par1ItemStack)
    {
        return par1ItemStack != null && par1ItemStack.itemID == Item.pumpkinSeeds.itemID;
    }

    @Override
    public int nameYOffset()
    {

        return -50;

    }

    @Override
    public double roperYOffset()
    {
        return 0.8D;

    }
}
