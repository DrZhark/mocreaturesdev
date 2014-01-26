package drzhark.mocreatures.item;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class MoCItemWeapon extends MoCItem {
    private final float weaponDamage;
    private final ToolMaterial toolMaterial;
    private int specialWeaponType = 0;
    private boolean breakable = false;

    public MoCItemWeapon(String name, ToolMaterial par2ToolMaterial)
    {
        super(name);
        this.toolMaterial = par2ToolMaterial;
        this.maxStackSize = 1;
        this.setMaxDamage(par2ToolMaterial.getMaxUses());
        this.weaponDamage = 4 + par2ToolMaterial.getDamageVsEntity();
    }

    /**
     * 
     * @param par1
     * @param par2ToolMaterial
     * @param damageType
     *            0 = default, 1 = poison, 2 = slow down, 3 = fire, 4 =
     *            confusion, 5 = blindness
     */
    public MoCItemWeapon(String name, ToolMaterial par2ToolMaterial, int damageType, boolean fragile)
    {
        this(name, par2ToolMaterial);
        this.specialWeaponType = damageType;
        this.breakable = fragile;
    }

    /**
     * Returns the strength of the stack against a given block. 1.0F base,
     * (Quality+1)*2 if correct blocktype, 1.5F if sword
     */
    @Override
    public float func_150893_a(ItemStack par1ItemStack, Block par2Block)
    {
        return par2Block == Blocks.web ? 15.0F : 1.5F;
    }

    /**
     * Current implementations of this method in child classes do not use the
     * entry argument beside ev. They just raise the damage on the stack.
     */
    @Override
    public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLiving, EntityLivingBase par3EntityLiving)
    {
        int i = 1;
        if (breakable)
        {
            i = 10;
        }
        par1ItemStack.damageItem(i, par3EntityLiving);
        int potionTime = 100;
        switch (specialWeaponType)
        {
        case 1: //poison
            par2EntityLiving.addPotionEffect(new PotionEffect(Potion.poison.id, potionTime, 0));
            break;
        case 2: //frost slowdown
            par2EntityLiving.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, potionTime, 0));
            break;
        case 3: //fire
            par2EntityLiving.setFire(10);
            break;
        case 4: //confusion
            par2EntityLiving.addPotionEffect(new PotionEffect(Potion.confusion.id, potionTime, 0));
            break;
        case 5: //blindness
            par2EntityLiving.addPotionEffect(new PotionEffect(Potion.blindness.id, potionTime, 0));
            break;
        default:
            break;
        }

        return true;
    }

    public boolean onBlockDestroyed(ItemStack par1ItemStack, int par2, int par3, int par4, int par5, EntityLiving par6EntityLiving)
    {
        par1ItemStack.damageItem(2, par6EntityLiving);
        return true;
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
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        return par1ItemStack;
    }

    /**
     * Returns if the item (tool) can harvest results from the block type.
     */
    @Override
    public boolean func_150897_b(Block par1Block)
    {
        return par1Block == Blocks.web;
    }

    /**
     * Return the enchantability factor of the item, most of the time is based
     * on material.
     */
    @Override
    public int getItemEnchantability()
    {
        return this.toolMaterial.getEnchantability();
    }
}
