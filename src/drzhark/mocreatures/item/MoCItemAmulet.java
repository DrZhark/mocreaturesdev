package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import drzhark.mocreatures.entity.MoCIMoCreature;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.network.MoCServerPacketHandler;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class MoCItemAmulet extends MoCItem {

    public MoCItemAmulet(int i)
    {
        super(i);
        maxStackSize = 1;
        setHasSubtypes(true);
        ageCounter = 0;
    }

    
    
    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World worldObj, EntityPlayer entityplayer)
    {
        if (++ageCounter < 2) { return itemstack; }
        
        int i = itemstack.getItemDamage();
        

        
        if (i == 0)
        {
            //System.out.println("cheated amulet");
            //i = 58;
        }

        if (MoCreatures.isServer())
        {
        	
        	if( itemstack.stackTagCompound == null )
    		{
        		itemstack.setTagCompound(new NBTTagCompound());
    		}
    		NBTTagCompound nbtcompound = itemstack.stackTagCompound;
    		readFromNBT(nbtcompound);
    		/*
    		System.out.println("reading spawnclass = " + spawnClass);
    		System.out.println("reading Health = " + health);
    		System.out.println("reading Edad = " + edad);
    		System.out.println("reading Name = " + name);
    		System.out.println("reading Rideable = " + rideable);
    		System.out.println("reading Armor = " + armor);
    		System.out.println("reading CreatureType = " + creatureType);
    		*/
    		
        }
        
        if (spawnClass == 21 || spawnClass == 0) // horses or old amulets
        {
        	//dirty fix for old amulets
        	spawnClass = 22;
        	if (spawnClass == 0 || creatureType == 0)
        	{
        		creatureType = i;
        		spawnClass = 22;
        		edad = 100;
        		health = 20;
        		armor = 0;
        		name = "";
        		ownerName = "";
        		rideable = false;
        		adult = true;
        	}
        	//gives an empty amulet
        	if (creatureType == 26 || creatureType == 27 || creatureType == 28)
        	{
        		entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(MoCreatures.amuletbone, 1, 0));
        	}
        	else if (creatureType == 21 || creatureType == 22)
        	{
        		entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(MoCreatures.amuletghost, 1, 0));
        	}
        	else if ((creatureType > 47 && creatureType < 60))
        	{
        		entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(MoCreatures.amuletfairy, 1, 0));
        	}
        	else if (creatureType == 39 || creatureType == 40)
        	{
        		entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(MoCreatures.amuletpegasus, 1, 0));
        	}
        }

        if (i != 0)
        {

            double dist = 3D;
            double newPosY = entityplayer.posY;
            double newPosX = entityplayer.posX - (dist * Math.cos((MoCTools.realAngle(entityplayer.rotationYaw - 90F)) / 57.29578F));
            double newPosZ = entityplayer.posZ - (dist * Math.sin((MoCTools.realAngle(entityplayer.rotationYaw - 90F)) / 57.29578F));

            if (MoCreatures.isServer())
            {
        		try
        		{
        			MoCEntityAnimal storedCreature = MoCTools.spawnMoCAnimalList(spawnClass, worldObj);
                    storedCreature.setPosition(newPosX, newPosY, newPosZ);
            		storedCreature.setType(creatureType);
            		storedCreature.setTamed(true);
            		storedCreature.setRideable(rideable);
            		storedCreature.setEdad(edad);
            		storedCreature.setName(name);
            		storedCreature.setArmorType(armor);
            		storedCreature.setEntityHealth(health);
            		storedCreature.setAdult(adult);
            		
            		//if the player using the amulet is different than the original owner
            		if (MoCreatures.proxy.enableOwnership && ownerName != "" && !(ownerName.equals(entityplayer.username)) )
      				{
      					EntityPlayer epOwner = worldObj.getPlayerEntityByName(ownerName);
      					if (epOwner != null)
      					{
      						MoCTools.reduceTamedByPlayer(epOwner);
      					}
      					else
      					{
      						MoCTools.reduceTamedByOfflinePlayer(ownerName);
      					}
      				}
      				storedCreature.setOwner(entityplayer.username);
                    
                    entityplayer.worldObj.spawnEntityInWorld(storedCreature);
                    MoCServerPacketHandler.sendAppearPacket(storedCreature.entityId, worldObj.provider.dimensionId);
                    MoCTools.playCustomSound(storedCreature, "appearmagic", worldObj);
        		}catch (Exception ex) 
        		{
        			System.out.println("Error spawning creature from amulet " + ex);
        		}
        		
            }
            
            ageCounter = 0;
       }

        return itemstack;
    }

    private int ageCounter;
    //private String owner;
    private String name;
    private int health;
    private int edad;
    private int creatureType;
    private int spawnClass;
    private boolean rideable;
    private byte armor;
    private boolean adult;
    private String ownerName;
    
    public void readFromNBT(NBTTagCompound nbt)
	{
    	this.creatureType = nbt.getInteger("CreatureType");
	    this.health = nbt.getInteger("Health");
	    this.edad = nbt.getInteger("Edad");
	    this.name = nbt.getString("Name");
	    this.spawnClass = nbt.getInteger("SpawnClass");
	    this.rideable = nbt.getBoolean("Rideable");
	    this.armor = nbt.getByte("Armor");
	    this.adult = nbt.getBoolean("Adult");
	    this.ownerName = nbt.getString("OwnerName");
	}
	
	public void writeToNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("CreatureType", this.creatureType);
		nbt.setInteger("Health", this.health);
		nbt.setInteger("Edad", this.edad);
		nbt.setString("Name", this.name);
		nbt.setInteger("SpawnClass", this.spawnClass);
		nbt.setBoolean("Rideable", this.rideable);
		nbt.setByte("Armor", this.armor);
		nbt.setBoolean("Adult", this.adult);
		nbt.setString("OwnerName", this.ownerName);
	}
}
