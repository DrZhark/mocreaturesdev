package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCProxy;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.init.MoCItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MoCItemArmor extends ItemArmor {

    public MoCItemArmor(String name, ItemArmor.ArmorMaterial materialIn, int renderIndex, EntityEquipmentSlot equipmentSlotIn) {
        super(materialIn, renderIndex, equipmentSlotIn);
        this.setCreativeTab(MoCreatures.tabMoC);
        this.setRegistryName(MoCConstants.MOD_ID, name);
        this.setUnlocalizedName(name);
    }

    @Override
    public String getArmorTexture(ItemStack itemstack, Entity entity, EntityEquipmentSlot slot, String type) {
        String tempArmorTexture = "croc_1.png";
        if ((itemstack.getItem() == MoCItems.helmetCroc) || (itemstack.getItem() == MoCItems.plateCroc)
                || (itemstack.getItem() == MoCItems.bootsCroc)) {
            tempArmorTexture = "croc_1.png";
        }
        if (itemstack.getItem() == MoCItems.legsCroc) {
            tempArmorTexture = "croc_2.png";
        }

        if ((itemstack.getItem() == MoCItems.helmetFur) || (itemstack.getItem() == MoCItems.chestFur)
                || (itemstack.getItem() == MoCItems.bootsFur)) {
            tempArmorTexture = "fur_1.png";
        }
        if (itemstack.getItem() == MoCItems.legsFur) {
            tempArmorTexture = "fur_2.png";
        }

        if ((itemstack.getItem() == MoCItems.helmetHide) || (itemstack.getItem() == MoCItems.chestHide)
                || (itemstack.getItem() == MoCItems.bootsHide)) {
            tempArmorTexture = "hide_1.png";
        }
        if (itemstack.getItem() == MoCItems.legsHide) {
            tempArmorTexture = "hide_2.png";
        }

        if ((itemstack.getItem() == MoCItems.scorpHelmetDirt) || (itemstack.getItem() == MoCItems.scorpPlateDirt)
                || (itemstack.getItem() == MoCItems.scorpBootsDirt)) {
            tempArmorTexture = "scorpd_1.png";
        }
        if (itemstack.getItem() == MoCItems.scorpLegsDirt) {
            tempArmorTexture = "scorpd_2.png";
        }

        if ((itemstack.getItem() == MoCItems.scorpHelmetFrost) || (itemstack.getItem() == MoCItems.scorpPlateFrost)
                || (itemstack.getItem() == MoCItems.scorpBootsFrost)) {
            tempArmorTexture = "scorpf_1.png";
        }
        if (itemstack.getItem() == MoCItems.scorpLegsFrost) {
            tempArmorTexture = "scorpf_2.png";
        }

        if ((itemstack.getItem() == MoCItems.scorpHelmetCave) || (itemstack.getItem() == MoCItems.scorpPlateCave)
                || (itemstack.getItem() == MoCItems.scorpBootsCave)) {
            tempArmorTexture = "scorpc_1.png";
        }
        if (itemstack.getItem() == MoCItems.scorpLegsCave) {
            tempArmorTexture = "scorpc_2.png";
        }

        if ((itemstack.getItem() == MoCItems.scorpHelmetNether) || (itemstack.getItem() == MoCItems.scorpPlateNether)
                || (itemstack.getItem() == MoCItems.scorpBootsNether)) {
            tempArmorTexture = "scorpn_1.png";
        }
        if (itemstack.getItem() == MoCItems.scorpLegsNether) {
            tempArmorTexture = "scorpn_2.png";
        }

        return "mocreatures:" + MoCProxy.ARMOR_TEXTURE + tempArmorTexture;
    }

    /**
     * Called to tick armor in the armor slot. Override to do something
     *
     * @param world
     * @param player
     * @param itemStack
     */
    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        if (world.rand.nextInt(50) == 0 && player.getItemStackFromSlot(EntityEquipmentSlot.FEET) != null) {
            ItemStack stack = player.getItemStackFromSlot(EntityEquipmentSlot.FEET);
            if (!stack.isEmpty() && stack.getItem() instanceof MoCItemArmor) {
                MoCTools.updatePlayerArmorEffects(player);
            }
        }
    }
}
