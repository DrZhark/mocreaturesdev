package drzhark.mocreatures.item;


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.IArmorTextureProvider;


public class MoCItemArmor extends ItemArmor implements IArmorTextureProvider//, ITextureProvider
{
    public MoCItemArmor(int i, EnumArmorMaterial enumarmormaterial, int j, int k)
      {
        super(i, enumarmormaterial, j, k);
      }

    @Override
    public String getArmorTextureFile(ItemStack itemstack) 
    {
    	String tempArmorTexture = "croc_1.png";;
        if ((itemstack.itemID == MoCreatures.helmetCroc.itemID) 
                || (itemstack.itemID == MoCreatures.plateCroc.itemID) 
                || (itemstack.itemID == MoCreatures.bootsCroc.itemID))
        {
        	tempArmorTexture = "croc_1.png";
          //return MoCreatures.proxy.ARMOR_TEXTURE + "croc_1.png";
        }
        if (itemstack.itemID == MoCreatures.legsCroc.itemID)
        {
        	tempArmorTexture = "croc_2.png";
          //return MoCreatures.proxy.ARMOR + "croc_2.png";
        }
        
        if ((itemstack.itemID == MoCreatures.helmetFur.itemID) 
                || (itemstack.itemID == MoCreatures.plateFur.itemID) 
                || (itemstack.itemID == MoCreatures.bootsFur.itemID))
        {
        	tempArmorTexture = "fur_1.png";
        	//return MoCreatures.proxy.ARMOR + "fur_1.png";
        }
        if (itemstack.itemID == MoCreatures.legsFur.itemID)
        {
        	tempArmorTexture = "fur_2.png";
        	//return MoCreatures.proxy.ARMOR + "fur_2.png";
        }
        
        if ((itemstack.itemID == MoCreatures.helmetHide.itemID) 
                || (itemstack.itemID == MoCreatures.plateHide.itemID) 
                || (itemstack.itemID == MoCreatures.bootsHide.itemID))
        {
        	tempArmorTexture = "hide_1.png";
         // return MoCreatures.proxy.ARMOR + "hide_1.png";
        }
        if (itemstack.itemID == MoCreatures.legsHide.itemID)
        {
        	tempArmorTexture = "hide_2.png";
         // return MoCreatures.proxy.ARMOR + "hide_2.png";
        }
        
        if ((itemstack.itemID == MoCreatures.helmetScorpDirt.itemID) 
                || (itemstack.itemID == MoCreatures.plateScorpDirt.itemID) 
                || (itemstack.itemID == MoCreatures.bootsScorpDirt.itemID))
        {
        	tempArmorTexture = "scorpd_1.png";
          //return MoCreatures.proxy.ARMOR + "scorpd_1.png";
        }
        if (itemstack.itemID == MoCreatures.legsScorpDirt.itemID)
        {
        	tempArmorTexture = "scorpd_2.png";
          //return MoCreatures.proxy.ARMOR + "scorpd_2.png";
        }
        
        if ((itemstack.itemID == MoCreatures.helmetScorpFrost.itemID) 
                || (itemstack.itemID == MoCreatures.plateScorpFrost.itemID) 
                || (itemstack.itemID == MoCreatures.bootsScorpFrost.itemID))
        {
        	tempArmorTexture = "scorpf_1.png";
         // return MoCreatures.proxy.ARMOR + "scorpf_1.png";
        }
        if (itemstack.itemID == MoCreatures.legsScorpFrost.itemID)
        {
        	tempArmorTexture = "scorpf_2.png";
          //return MoCreatures.proxy.ARMOR + "scorpf_2.png";
        }
        
        if ((itemstack.itemID == MoCreatures.helmetScorpCave.itemID) 
                || (itemstack.itemID == MoCreatures.plateScorpCave.itemID) 
                || (itemstack.itemID == MoCreatures.bootsScorpCave.itemID))
        {
        	tempArmorTexture = "scorpc_1.png";
          //return MoCreatures.proxy.ARMOR + "scorpc_1.png";
        }
        if (itemstack.itemID == MoCreatures.legsScorpCave.itemID)
        {
        	tempArmorTexture = "scorpc_2.png";
          //return MoCreatures.proxy.ARMOR + "scorpc_2.png";
        }
        
        if ((itemstack.itemID == MoCreatures.helmetScorpNether.itemID) 
                || (itemstack.itemID == MoCreatures.plateScorpNether.itemID) 
                || (itemstack.itemID == MoCreatures.bootsScorpNether.itemID))
        {
        	tempArmorTexture = "scorpn_1.png";
          //return MoCreatures.proxy.ARMOR + "scorpn_1.png";
        }
        if (itemstack.itemID == MoCreatures.legsScorpNether.itemID)
        {
        	tempArmorTexture = "scorpn_2.png";
          //return MoCreatures.proxy.ARMOR + "scorpn_2.png";
        }
        
        return MoCreatures.proxy.ARMOR_TEXTURE + tempArmorTexture;
        
    }

   /* @Override
    public String getTextureFile() 
    {
        return "/mocreatures/items.png";
    }*/
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister par1IconRegister)
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
    public void onArmorTickUpdate(World world, EntityPlayer player, ItemStack itemStack)
    {
    	if(world.rand.nextInt(50)==0 && player.getCurrentArmor(3) != null)
		{
		//System.out.println("player has a helmet");
		ItemStack myStack = player.getCurrentArmor(3);
		if (myStack != null && myStack.getItem() instanceof MoCItemArmor)
		{
			MoCTools.updatePlayerArmorEffects(player);
			//System.out.println("player has equipped a MoC helmet");
		}
		}
    }
}


