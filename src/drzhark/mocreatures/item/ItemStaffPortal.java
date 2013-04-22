package drzhark.mocreatures.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet70GameEvent;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.dimension.MoCDirectTeleporter;
import drzhark.mocreatures.dimension.WorldGenTower;


public class ItemStaffPortal extends MoCItem
{

	public ItemStaffPortal(int i)
	{
		super(i);
		maxStackSize = 1;
		setMaxDamage(3);
		this.setCreativeTab(CreativeTabs.tabTools);
		
	}
	
	private int portalPosX;
	private int portalPosY;
	private int portalPosZ;
	private int portalDimension;


	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int l, float f1, float f2, float f3)
	{
		 
		
		if(!MoCreatures.isServer())
		{
			return false;
		}
		
		if( itemstack.stackTagCompound == null )
		 {
			 itemstack.setTagCompound(new NBTTagCompound());
		 }
		 
		NBTTagCompound nbtcompound = itemstack.stackTagCompound;
		
		EntityPlayerMP thePlayer = (EntityPlayerMP) entityplayer;
		if (thePlayer.ridingEntity != null || thePlayer.riddenByEntity != null)
		{
			
			return false;
		}
		else
		{
			//System.out.println("using portal staff");
			
			if (thePlayer.dimension != MoCreatures.WyvernLairDimensionID)
		    {
				portalDimension = thePlayer.dimension;
				portalPosX = (int) thePlayer.posX;
				portalPosY = (int) thePlayer.posY;
				portalPosZ = (int) thePlayer.posZ;
				//System.out.println("saving posX = " + portalPosX + ", posY = " + portalPosY + ", posZ = " + portalPosZ);
				writeToNBT(nbtcompound);
				
				ChunkCoordinates var2 = thePlayer.mcServer.worldServerForDimension(MoCreatures.WyvernLairDimensionID).getEntrancePortalLocation();

                if (var2 != null)
                {
                	thePlayer.playerNetServerHandler.setPlayerLocation((double)var2.posX, (double)var2.posY, (double)var2.posZ, 0.0F, 0.0F);
                }
				//System.out.println("porting to the wyvern lair");
		     thePlayer.mcServer.getConfigurationManager().transferPlayerToDimension(thePlayer, MoCreatures.WyvernLairDimensionID, new MoCDirectTeleporter(thePlayer.mcServer.worldServerForDimension(MoCreatures.WyvernLairDimensionID)));
		     
		     itemstack.damageItem(1, entityplayer);
		     //thePlayer.travelToDimension(MoCreatures.WyvernLairID);
		     return true;
		    }
		    else
		    {
		    	//on the WyvernLair!
		    	if ( (thePlayer.posX > 1.5D || thePlayer.posX < -1.5D) || (thePlayer.posZ > 2.5D || thePlayer.posZ < -2.5D))
		    	{
		    		//System.out.println("using portal staff in WivernLair at wrong location");
		    		//tower generation to remove before release
		    		/*if (MoCreatures.isServer())
		    		{
		    			WorldGenTower myTower = new WorldGenTower(Block.cobblestoneMossy.blockID, Block.netherBrick.blockID, Block.glass.blockID);
			    		myTower.generate(entityplayer.worldObj, entityplayer.worldObj.rand, MathHelper.floor_double(entityplayer.posX), MathHelper.floor_double(entityplayer.posY), MathHelper.floor_double(entityplayer.posZ));
			    		//System.out.println("using portal staff in WivernLair to generate tower");
			    		
		    		}*/
		    		//return false;
		    		return false;
		    	}
		    	readFromNBT(nbtcompound);
		    	//System.out.println("reading posX = " + portalPosX + ", posY = " + portalPosY + ", posZ = " + portalPosZ);
		    	boolean foundSpawn = false;
		    	if (portalPosX == 0 && portalPosY == 0 && portalPosZ == 0) //dummy staff
		    	{
		    		ChunkCoordinates var2 = thePlayer.mcServer.worldServerForDimension(0).getSpawnPoint();
			    	
	                if (var2 != null)
	                {
	                	//innerLabel:
	                	for (int i1 = 0; i1 < 60; i1++)
	                	{
	                		int j1 = thePlayer.mcServer.worldServerForDimension(0).getBlockId(var2.posX, var2.posY + i1, var2.posZ);
	                		int j2 = thePlayer.mcServer.worldServerForDimension(0).getBlockId(var2.posX, var2.posY + i1 + 1, var2.posZ);
	                        if (j1 == 0 && j2 == 0)
	                        {
	                        	thePlayer.playerNetServerHandler.setPlayerLocation((double)var2.posX, (double)var2.posY+i1+1, (double)var2.posZ, 0.0F, 0.0F);
	                        	if (MoCreatures.proxy.debugLogging) {System.out.println("MoC Staff teleporter found location at spawn");}
	                        	foundSpawn = true;
	                        	break;
	                        }
	                        
	                	}
	                	
	                	if (!foundSpawn)
	                	{
	                		if (MoCreatures.proxy.debugLogging) {System.out.println("MoC Staff teleporter couldn't find an adequate teleport location at spawn");}
		                    return false;
	                	}
	                	
	                }
	                
	                else
	                {
	                	if (MoCreatures.proxy.debugLogging) System.out.println("MoC Staff teleporter couldn't find spawn point");
                        return false;
	                }
		    	}else
		    	{
		    		thePlayer.playerNetServerHandler.setPlayerLocation((double)portalPosX, ((double)portalPosY) + 1D, (double)portalPosZ, 0.0F, 0.0F);
			    	
		    	}
		    	
		    	
		    	/*ChunkCoordinates var2 = thePlayer.mcServer.worldServerForDimension(0).getSpawnPoint();
		    	
                if (var2 != null)
                {
                	thePlayer.playerNetServerHandler.setPlayerLocation((double)var2.posX, (double)var2.posY+7, (double)var2.posZ, 0.0F, 0.0F);
                	
                }*/
		    	
		     itemstack.damageItem(1, entityplayer);
		     thePlayer.mcServer.getConfigurationManager().transferPlayerToDimension(thePlayer, portalDimension, new MoCDirectTeleporter(thePlayer.mcServer.worldServerForDimension(0)));
		     //thePlayer.travelToDimension(0);
		     //System.out.println("porting to the overworld");
		     return true;
		    }
		}
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
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		return par1ItemStack;
	}
	
	
	public void readFromNBT(NBTTagCompound nbt)
	{
	    this.portalPosX = nbt.getInteger("portalPosX");
	    this.portalPosY = nbt.getInteger("portalPosY");
	    this.portalPosZ = nbt.getInteger("portalPosZ");
	    this.portalDimension = nbt.getInteger("portalDimension");
	}
	
	public void writeToNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("portalPosX", this.portalPosX);
		nbt.setInteger("portalPosY", this.portalPosY);
		nbt.setInteger("portalPosZ", this.portalPosZ);
		nbt.setInteger("portalDimension", this.portalDimension);
	}
}


