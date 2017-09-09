package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.PotionEffect;

public class MoCItemSword extends ItemSword {

    private int specialWeaponType = 0;
    private boolean breakable = false;

    public MoCItemSword(String name, Item.ToolMaterial material) {
        this(name, 0, material);
    }

    public MoCItemSword(String name, int meta, Item.ToolMaterial material) {
        super(material);
        this.setCreativeTab(MoCreatures.tabMoC);
        this.setRegistryName(MoCConstants.MOD_ID, name);
        this.setUnlocalizedName(name);
    }

    public MoCItemSword(String name, Item.ToolMaterial material, int damageType, boolean fragile) {
        this(name, material);
        this.specialWeaponType = damageType;
        this.breakable = fragile;
    }

    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     *  
     * @param target The Entity being hit
     * @param attacker the attacking entity
     */
    @Override
    public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase target, EntityLivingBase attacker) {
        int i = 1;
        if (this.breakable) {
            i = 10;
        }
        par1ItemStack.damageItem(i, attacker);
        int potionTime = 100;
        switch (this.specialWeaponType) {
            case 1: //poison
                target.addPotionEffect(new PotionEffect(MobEffects.POISON, potionTime, 0));
                break;
            case 2: //frost slowdown
                target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, potionTime, 0));
                break;
            case 3: //fire
                target.setFire(10);
                break;
            case 4: //confusion
                target.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, potionTime, 0));
                break;
            case 5: //blindness
                target.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, potionTime, 0));
                break;
            default:
                break;
        }

        return true;
    }
}
