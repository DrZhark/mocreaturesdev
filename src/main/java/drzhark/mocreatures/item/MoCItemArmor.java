package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCProxy;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class MoCItemArmor extends ItemArmor {

    public MoCItemArmor(String name, ItemArmor.ArmorMaterial materialIn, int renderIndex, EntityEquipmentSlot equipmentSlotIn) {
        super(materialIn, renderIndex, equipmentSlotIn);
        this.setCreativeTab(MoCreatures.tabMoC);
        this.setUnlocalizedName(name);
        GameRegistry.register(this, new ResourceLocation(MoCConstants.MOD_ID, name));
        if (!MoCreatures.isServer())
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
                    .register(this, 0, new ModelResourceLocation("mocreatures:" + name, "inventory"));
    }

    @Override
    public String getArmorTexture(ItemStack itemstack, Entity entity, EntityEquipmentSlot slot, String type) {
        String tempArmorTexture = "croc_1.png";
        if ((itemstack.getItem() == MoCreatures.helmetCroc) || (itemstack.getItem() == MoCreatures.plateCroc)
                || (itemstack.getItem() == MoCreatures.bootsCroc)) {
            tempArmorTexture = "croc_1.png";
        }
        if (itemstack.getItem() == MoCreatures.legsCroc) {
            tempArmorTexture = "croc_2.png";
        }

        if ((itemstack.getItem() == MoCreatures.helmetFur) || (itemstack.getItem() == MoCreatures.chestFur)
                || (itemstack.getItem() == MoCreatures.bootsFur)) {
            tempArmorTexture = "fur_1.png";
        }
        if (itemstack.getItem() == MoCreatures.legsFur) {
            tempArmorTexture = "fur_2.png";
        }

        if ((itemstack.getItem() == MoCreatures.helmetHide) || (itemstack.getItem() == MoCreatures.chestHide)
                || (itemstack.getItem() == MoCreatures.bootsHide)) {
            tempArmorTexture = "hide_1.png";
        }
        if (itemstack.getItem() == MoCreatures.legsHide) {
            tempArmorTexture = "hide_2.png";
        }

        if ((itemstack.getItem() == MoCreatures.scorpHelmetDirt) || (itemstack.getItem() == MoCreatures.scorpPlateDirt)
                || (itemstack.getItem() == MoCreatures.scorpBootsDirt)) {
            tempArmorTexture = "scorpd_1.png";
        }
        if (itemstack.getItem() == MoCreatures.scorpLegsDirt) {
            tempArmorTexture = "scorpd_2.png";
        }

        if ((itemstack.getItem() == MoCreatures.scorpHelmetFrost) || (itemstack.getItem() == MoCreatures.scorpPlateFrost)
                || (itemstack.getItem() == MoCreatures.scorpBootsFrost)) {
            tempArmorTexture = "scorpf_1.png";
        }
        if (itemstack.getItem() == MoCreatures.scorpLegsFrost) {
            tempArmorTexture = "scorpf_2.png";
        }

        if ((itemstack.getItem() == MoCreatures.scorpHelmetCave) || (itemstack.getItem() == MoCreatures.scorpPlateCave)
                || (itemstack.getItem() == MoCreatures.scorpBootsCave)) {
            tempArmorTexture = "scorpc_1.png";
        }
        if (itemstack.getItem() == MoCreatures.scorpLegsCave) {
            tempArmorTexture = "scorpc_2.png";
        }

        if ((itemstack.getItem() == MoCreatures.scorpHelmetNether) || (itemstack.getItem() == MoCreatures.scorpPlateNether)
                || (itemstack.getItem() == MoCreatures.scorpBootsNether)) {
            tempArmorTexture = "scorpn_1.png";
        }
        if (itemstack.getItem() == MoCreatures.scorpLegsNether) {
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
            ItemStack myStack = player.getItemStackFromSlot(EntityEquipmentSlot.FEET);
            if (myStack != null && myStack.getItem() instanceof MoCItemArmor) {
                MoCTools.updatePlayerArmorEffects(player);
            }
        }
    }
}
