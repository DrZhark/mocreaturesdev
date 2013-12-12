package drzhark.mocreatures.entity.passive;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameable;

public class MoCEntityTurkey extends MoCEntityTameable {

    public MoCEntityTurkey(World world)
    {
        super(world);
        setSize(0.8F, 1.0F);
        texture = "turkey.png";
    }

    protected void applyEntityAttributes()
    {
      super.applyEntityAttributes();
      getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(6.0D);
    }

    @Override
    protected String getDeathSound()
    {
        return "mocreatures:turkeyhurt";
    }

    @Override
    protected String getHurtSound()
    {
        return "mocreatures:turkeyhurt";
    }

    @Override
    protected String getLivingSound()
    {
        return "mocreatures:turkey";
    }

    @Override
    protected int getDropItemId()
    {
        boolean flag = (rand.nextInt(2) == 0);
        if (flag) { return MoCreatures.rawTurkey.itemID; }
        return Item.feather.itemID;
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
    
    @Override
    public int getTalkInterval()
    {
        return 400;
    }

    @Override
    public int getMaxSpawnedInChunk()
    {
        return 2;
    }
}