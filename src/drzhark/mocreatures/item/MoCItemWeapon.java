package drzhark.mocreatures.item;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class MoCItemWeapon extends MoCItem {
    private final int weaponDamage;
    private final EnumToolMaterial toolMaterial;
    private int specialWeaponType = 0;
    private boolean breakable = false;

    public MoCItemWeapon(int par1, EnumToolMaterial par2EnumToolMaterial)
    {
        super(par1);
        this.toolMaterial = par2EnumToolMaterial;
        this.maxStackSize = 1;
        this.setMaxDamage(par2EnumToolMaterial.getMaxUses());
        this.setCreativeTab(CreativeTabs.tabCombat);
        this.weaponDamage = 4 + par2EnumToolMaterial.getDamageVsEntity();
    }

    /**
     * 
     * @param par1
     * @param par2EnumToolMaterial
     * @param damageType
     *            0 = default, 1 = poison, 2 = slow down, 3 = fire, 4 =
     *            confusion, 5 = blindness
     */
    public MoCItemWeapon(int par1, EnumToolMaterial par2EnumToolMaterial, int damageType, boolean fragile)
    {
        this(par1, par2EnumToolMaterial);
        this.specialWeaponType = damageType;
        this.breakable = fragile;
    }

    /**
     * Returns the strength of the stack against a given block. 1.0F base,
     * (Quality+1)*2 if correct blocktype, 1.5F if sword
     */
    @Override
    public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
    {
        return par2Block.blockID == Block.web.blockID ? 15.0F : 1.5F;
    }

    /**
     * Current implementations of this method in child classes do not use the
     * entry argument beside ev. They just raise the damage on the stack.
     */
    @Override
    public boolean hitEntity(ItemStack par1ItemStack, EntityLiving par2EntityLiving, EntityLiving par3EntityLiving)
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
     * Returns the damage against a given entity.
     */
    @Override
    public int getDamageVsEntity(Entity par1Entity)
    {
        return this.weaponDamage;
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
    public boolean canHarvestBlock(Block par1Block)
    {
        return par1Block.blockID == Block.web.blockID;
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
