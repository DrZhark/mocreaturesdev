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

        if ((itemstack.getItem() == MoCreatures.helmetFur) || (itemstack.getItem() == MoCreatures.plateFur) || (itemstack.getItem() == MoCreatures.bootsFur))
        {
            tempArmorTexture = "fur_1.png";
        }
        if (itemstack.getItem() == MoCreatures.legsFur)
        {
            tempArmorTexture = "fur_2.png";;
        }

        if ((itemstack.getItem() == MoCreatures.helmetHide) || (itemstack.getItem() == MoCreatures.plateHide) || (itemstack.getItem() == MoCreatures.bootsHide))
        {
            tempArmorTexture = "hide_1.png";
        }
        if (itemstack.getItem() == MoCreatures.legsHide)
        {
            tempArmorTexture = "hide_2.png";
        }

        if ((itemstack.getItem() == MoCreatures.helmetScorpDirt) || (itemstack.getItem() == MoCreatures.plateScorpDirt) || (itemstack.getItem() == MoCreatures.bootsScorpDirt))
        {
            tempArmorTexture = "scorpd_1.png";
        }
        if (itemstack.getItem() == MoCreatures.legsScorpDirt)
        {
            tempArmorTexture = "scorpd_2.png";
        }

        if ((itemstack.getItem() == MoCreatures.helmetScorpFrost) || (itemstack.getItem() == MoCreatures.plateScorpFrost) || (itemstack.getItem() == MoCreatures.bootsScorpFrost))
        {
            tempArmorTexture = "scorpf_1.png";
        }
        if (itemstack.getItem() == MoCreatures.legsScorpFrost)
        {
            tempArmorTexture = "scorpf_2.png";
        }

        if ((itemstack.getItem() == MoCreatures.helmetScorpCave) || (itemstack.getItem() == MoCreatures.plateScorpCave) || (itemstack.getItem() == MoCreatures.bootsScorpCave))
        {
            tempArmorTexture = "scorpc_1.png";
        }
        if (itemstack.getItem() == MoCreatures.legsScorpCave)
        {
            tempArmorTexture = "scorpc_2.png";
        }

        if ((itemstack.getItem() == MoCreatures.helmetScorpNether) || (itemstack.getItem() == MoCreatures.plateScorpNether) || (itemstack.getItem() == MoCreatures.bootsScorpNether))
        {
            tempArmorTexture = "scorpn_1.png";
        }
        if (itemstack.getItem() == MoCreatures.legsScorpNether)
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