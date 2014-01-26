package drzhark.mocreatures.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;

public class MoCItemArmor extends ItemArmor
{
    public MoCItemArmor(String name, ArmorMaterial enumarmormaterial, int j, int k)
    {
        super(enumarmormaterial, j, k);
        this.setCreativeTab(MoCreatures.tabMoC);
        this.setUnlocalizedName(name);
        GameRegistry.registerItem(this, name);
    }

    @Override
    public String getArmorTexture(ItemStack itemstack, Entity entity, int slot, String layer)
    {
        String tempArmorTexture = "croc_1.png";;
        if ((itemstack.getItem() == MoCreatures.helmetCroc) || (itemstack.getItem() == MoCreatures.plateCroc) || (itemstack.getItem() == MoCreatures.bootsCroc))
        {
            tempArmorTexture = "croc_1.png";
        }
        if (itemstack.getItem() == MoCreatures.legsCroc)
        {
            tempArmorTexture = "croc_2.png";
        }

        if ((itemstack.getItem() == MoCreatures.helmetFur) || (itemstack.getItem() == MoCreatures.chestFur) || (itemstack.getItem() == MoCreatures.bootsFur))
        {
            tempArmorTexture = "fur_1.png";
        }
        if (itemstack.getItem() == MoCreatures.legsFur)
        {
            tempArmorTexture = "fur_2.png";;
        }

        if ((itemstack.getItem() == MoCreatures.helmetHide) || (itemstack.getItem() == MoCreatures.chestHide) || (itemstack.getItem() == MoCreatures.bootsHide))
        {
            tempArmorTexture = "hide_1.png";
        }
        if (itemstack.getItem() == MoCreatures.legsHide)
        {
            tempArmorTexture = "hide_2.png";
        }

        if ((itemstack.getItem() == MoCreatures.scorpHelmetDirt) || (itemstack.getItem() == MoCreatures.scorpPlateDirt) || (itemstack.getItem() == MoCreatures.scorpBootsDirt))
        {
            tempArmorTexture = "scorpd_1.png";
        }
        if (itemstack.getItem() == MoCreatures.scorpLegsDirt)
        {
            tempArmorTexture = "scorpd_2.png";
        }

        if ((itemstack.getItem() == MoCreatures.scorpHelmetFrost) || (itemstack.getItem() == MoCreatures.scorpPlateFrost) || (itemstack.getItem() == MoCreatures.scorpBootsFrost))
        {
            tempArmorTexture = "scorpf_1.png";
        }
        if (itemstack.getItem() == MoCreatures.scorpLegsFrost)
        {
            tempArmorTexture = "scorpf_2.png";
        }

        if ((itemstack.getItem() == MoCreatures.scorpHelmetCave) || (itemstack.getItem() == MoCreatures.scorpPlateCave) || (itemstack.getItem() == MoCreatures.scorpBootsCave))
        {
            tempArmorTexture = "scorpc_1.png";
        }
        if (itemstack.getItem() == MoCreatures.scorpLegsCave)
        {
            tempArmorTexture = "scorpc_2.png";
        }

        if ((itemstack.getItem() == MoCreatures.scorpHelmetNether) || (itemstack.getItem() == MoCreatures.scorpPlateNether) || (itemstack.getItem() == MoCreatures.scorpBootsNether))
        {
            tempArmorTexture = "scorpn_1.png";
        }
        if (itemstack.getItem() == MoCreatures.scorpLegsNether)
        {
            tempArmorTexture = "scorpn_2.png";
        }

        return "mocreatures:" + MoCreatures.proxy.ARMOR_TEXTURE + tempArmorTexture;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("mocreatures"+ this.getUnlocalizedName().replaceFirst("item.", ":"));
    }

    /**
     * Called to tick armor in the armor slot. Override to do something
     *
     * @param world
     * @param player
     * @param itemStack
     */
    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
    {
        if(world.rand.nextInt(50)==0 && player.getCurrentArmor(3) != null)
        {
            ItemStack myStack = player.getCurrentArmor(3);
            if (myStack != null && myStack.getItem() instanceof MoCItemArmor)
            {
                MoCTools.updatePlayerArmorEffects(player);
            }
        }
    }
}