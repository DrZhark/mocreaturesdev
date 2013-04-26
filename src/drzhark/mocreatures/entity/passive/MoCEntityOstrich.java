// TODO
// lay eggs (done) and protect them (done)
// different behaviors depending on gender / age (done)
// increment age (done)
// change to adult (done)
// sounds (done)
// naming (done)
// rope following (done)

// scared ostriches shouldn't move


//legs when flying (done)
//flying like joust (done)
//moving tail depending on speed (done)
//different drops depending on type (done)
//drop helmet - acquire helmet (done)
//drop flag? acquire flag (done)
//drop inventory (done)
//buckle with unihorns (done)
//new ostriches (done)
//armor dmg reduction (done)
//lance? joust?
//transform fx (done)

package drzhark.mocreatures.entity.passive;

import java.util.List;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import drzhark.mocreatures.inventory.MoCAnimalChest;
import drzhark.mocreatures.network.MoCServerPacketHandler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCloth;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class MoCEntityOstrich extends MoCEntityAnimal {

    public MoCEntityOstrich(World world)
    {
        super(world);
        //texture = MoCreatures.proxy.MODEL_TEXTURE + "ostricha.png";
        setSize(1.0F, 1.6F);
        health = 20;
        //moveSpeed = 0.5F;
        setEdad(35);
        roper = null;
        //stay = false;
        //eggCounter = 0;
        this.eggCounter = this.rand.nextInt(1000) + 1000;
        this.stepHeight = 1.0F;
    }

    private int eggCounter;
    private int hidingCounter;
    public int mouthCounter;
    public int wingCounter;
    public int sprintCounter;
    public int jumpCounter;
    private int transformCounter;
    private int transformType;
    
    public MoCAnimalChest localchest;
    public ItemStack localstack;

    @Override
    protected void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(22, Byte.valueOf((byte) 0)); // isRideable - 0 false 1 true
        dataWatcher.addObject(23, Byte.valueOf((byte) 0)); // eggWatch - 0 false 1 true
        dataWatcher.addObject(24, Byte.valueOf((byte) 0)); // hiding - 0 false 1 true
        dataWatcher.addObject(25, Byte.valueOf((byte) 0)); // helmet - 0 none
        dataWatcher.addObject(26, Byte.valueOf((byte) 0)); // flagcolor - 0 white
        dataWatcher.addObject(27, Byte.valueOf((byte) 0)); // bagged - 0 false 1 true
        
    }

    public boolean getIsRideable()
    {
        return (dataWatcher.getWatchableObjectByte(22) == 1);
    }

    public void setRideable(boolean flag)
    {
        //if (worldObj.isRemote)return;
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(22, Byte.valueOf(input));
    }

    public boolean getEggWatching()
    {
        return (dataWatcher.getWatchableObjectByte(23) == 1);
    }

    public void setEggWatching(boolean flag)
    {
        //if (worldObj.isRemote)     return;
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(23, Byte.valueOf(input));
    }

    public boolean getHiding()
    {
        return (dataWatcher.getWatchableObjectByte(24) == 1);
    }

    public void setHiding(boolean flag)
    {
        if (worldObj.isRemote) { return; }
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(24, Byte.valueOf(input));
    }
    
    

   
    public byte getHelmet()
    {
        return (dataWatcher.getWatchableObjectByte(25));
    }

    public void setHelmet(byte b)
    {
        dataWatcher.updateObject(25, Byte.valueOf(b));
    }
    
    public byte getFlagColor()
    {
        return (dataWatcher.getWatchableObjectByte(26));
    }

    public void setFlagColor(byte b)
    {
        dataWatcher.updateObject(26, Byte.valueOf(b));
    }

    
    public boolean getIsChested()
    {
        return (dataWatcher.getWatchableObjectByte(27) == 1);
    }

    public void setIsChested(boolean flag)
    {
        //if (worldObj.isRemote)return;
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(27, Byte.valueOf(input));
    }
    @Override
    public boolean renderName()
    {
        return getDisplayName() && (riddenByEntity == null);
    }

    @Override
    protected boolean isMovementCeased()
    {
        return (getHiding() || riddenByEntity != null);
    }

    @Override
    public boolean isNotScared()
    {
        return (getType() == 2 && entityToAttack != null) || (getType() > 2);
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, int i)
    {
        //dmg reduction
        if (getIsTamed() && getHelmet() != 0)
        {
            int j = 0;
            switch (getHelmet())
            {
            case 1:
                j = 1;
                break;
            case 5:
            case 6:
            case 2:
                j = 2;
                break;
            case 7:
            case 3:
                j =3;
                break;
            case 4:
            case 9:
            case 10:
            case 11:
            case 12:
                j = 4;
                break;
            }
            i -= j;
            if (i <= 0) i = 1;
        }
        
        if (super.attackEntityFrom(damagesource, i))
        {
            Entity entity = damagesource.getEntity();

            if ( ((riddenByEntity != null) && (entity == riddenByEntity))  || (entity instanceof EntityPlayer && getIsTamed()) ) { return false; }

            if ((entity != this) && (worldObj.difficultySetting > 0) && getType() > 2)
            {
            	
                entityToAttack = entity;
                //setAngry(true);
                flapWings();

            }
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public void onDeath(DamageSource damagesource)
    {
        super.onDeath(damagesource);
        dropMyStuff();
    }

    @Override
    protected void attackEntity(Entity entity, float f)
    {

        /*if (attackTime <= 0 && (f < 3.0D) && (entity.boundingBox.maxY > boundingBox.minY) && (entity.boundingBox.minY < boundingBox.maxY))
        {
                
        }*/

        if (this.attackTime <= 0 && f < 2.0F && entity.boundingBox.maxY > this.boundingBox.minY && entity.boundingBox.minY < this.boundingBox.maxY)
        {
            this.attackTime = 20;
            openMouth();
            flapWings();
            entity.attackEntityFrom(DamageSource.causeMobDamage(this), 3);
            //this.attackEntityAsMob(par1Entity);
        }

    }

    @Override
    public int getMaxHealth()
    {
        switch (getType())
        {
        case 1:
            return 10;
        case 2:
            return 15;
        case 3:
            return 20;
        case 4:
            return 20;
        case 5:
            return 20;

        default:
            return 20;
        }

    }

    @Override
    public boolean canBeCollidedWith()
    {
        return riddenByEntity == null;
    }

    @Override
    public void selectType()
    {
        if (getType() == 0)
        {
            /**
             * 1 = chick 2 = female 3 = male 4 = albino male 5 = demon ostrich
             */
            int j = rand.nextInt(100);
            if (j <= (20))
            {
                setType(1);
            }
            else if (j <= (65))
            {
                setType(2);
            }
            else if (j <= (95))
            {
                setType(3);
            }
            else
            {
                setType(4);
            }

            //health = getMaxHealth();
        }

    }

    @Override
    public String getTexture()
    {

           if (transformCounter != 0 && transformType > 4)
            {
                String newText = MoCreatures.proxy.MODEL_TEXTURE + "ostricha.png";
                if (transformType == 5)
                {
                    newText = MoCreatures.proxy.MODEL_TEXTURE + "ostriche.png";
                }
                if (transformType == 6)
                {
                    newText = MoCreatures.proxy.MODEL_TEXTURE + "ostrichf.png";
                }
                if (transformType == 7)
                {
                    newText = MoCreatures.proxy.MODEL_TEXTURE + "ostrichg.png";
                }
                if (transformType == 8)
                {
                    newText = MoCreatures.proxy.MODEL_TEXTURE + "ostrichh.png";
                }
                
                if ((transformCounter % 5) == 0) { return newText; }
                if (transformCounter > 50 && (transformCounter % 3) == 0) { return newText; }

                if (transformCounter > 75 && (transformCounter % 4) == 0) { return newText; }
            }
           
        switch (getType())
        {
        case 1:
            return MoCreatures.proxy.MODEL_TEXTURE + "ostrichc.png"; //chick
        case 2:
            return MoCreatures.proxy.MODEL_TEXTURE + "ostrichb.png"; //female
        case 3:
            return MoCreatures.proxy.MODEL_TEXTURE + "ostricha.png"; //male
        case 4:
            return MoCreatures.proxy.MODEL_TEXTURE + "ostrichd.png"; //albino
        case 5:
            return MoCreatures.proxy.MODEL_TEXTURE + "ostriche.png"; //nether
        case 6:
            return MoCreatures.proxy.MODEL_TEXTURE + "ostrichf.png"; //black wyvern
        case 7:
            return MoCreatures.proxy.MODEL_TEXTURE + "ostrichg.png"; //undead
        case 8:
            return MoCreatures.proxy.MODEL_TEXTURE + "ostrichh.png"; //unicorned
            

        default:
            return MoCreatures.proxy.MODEL_TEXTURE + "ostricha.png";
        }
    }

    @Override
    public double getCustomSpeed()
    {
        //TODO for newer
        double OstrichSpeed = 0.8D;
        if (getType() == 1)
        {
            OstrichSpeed = 0.8;
        }
        else if (getType() == 2)
        {
            OstrichSpeed = 0.8D;
        }
        else if (getType() == 3)
        {
            OstrichSpeed = 1.1D;
        }
        else if (getType() == 4)
        {
            OstrichSpeed = 1.3D;
        }
        else if (getType() == 5)
        {
            OstrichSpeed = 1.4D;
            this.isImmuneToFire = true;
        }
        if (sprintCounter > 0 && sprintCounter < 200)
        {
            OstrichSpeed *= 1.5D;
        }
        if (sprintCounter > 200)
        {
            OstrichSpeed *= 0.5D;
        }
        return OstrichSpeed;
    }

    @Override
    public boolean rideableEntity()
    {
        return true;
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if (getHiding())
        {
            prevRenderYawOffset = renderYawOffset = rotationYaw = prevRotationYaw;
        }

        if (mouthCounter > 0 && ++mouthCounter > 20)
        {
            mouthCounter = 0;
        }

        if (wingCounter > 0 && ++wingCounter > 80)
        {
            wingCounter = 0;
        }
        
        if (jumpCounter > 0 && ++jumpCounter > 8)
        {
            jumpCounter = 0;
        }

        if (sprintCounter > 0 && ++sprintCounter > 300)
        {

            sprintCounter = 0;

        }
        
        if (transformCounter > 0)// && ++transformCounter > 100)
        {
            if (transformCounter == 40)
            {
                MoCTools.playCustomSound(this, "transform", worldObj);
            }

            if (++transformCounter > 100)
            {
                transformCounter = 0;
                if (transformType != 0)
                {
                    dropArmor();
                    setType(transformType);
                }
            }

        }
    }
    
    public void transform(int tType)
    {
        if (MoCreatures.isServer())
        {
            MoCServerPacketHandler.sendAnimationPacket(this.entityId, this.worldObj.provider.dimensionId, tType);
        }
        transformType = tType;
        if (this.riddenByEntity == null && transformType != 0)
        {
            dropArmor();
            transformCounter = 1;
        }
    }
    
    @Override
    public void performAnimation(int animationType)
    {
        if (animationType >= 5 && animationType < 9) //transform 5 - 8
        {
            transformType = animationType;
            transformCounter = 1;
        }
        
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        

        if (getIsTamed() && MoCreatures.isServer() && (rand.nextInt(300) == 0) && (health <= getMaxHealth()) && (deathTime == 0))
        {
            health++;
        }
        
        /*if (getType() == 5 || getType() == 6)
        {
            if (!onGround && (motionY < 0.0D))
            {
                motionY *= 0.1D;
            }
        }*/

        if (MoCreatures.isServer())
        {
            //tamed chicks will follow the player
            /*if (!getHiding() && getIsTamed() && (getType() == 1))
            {
                EntityPlayer mommy = worldObj.getClosestPlayerToEntity(this, 10D);
                if (mommy != null)
                {
                    float mdist = this.getDistanceToEntity(mommy);
                    if (mdist > 4F)
                    {
                        getPathOrWalkableBlock(mommy, mdist);
                    }
                }
            }*/

            //ostrich buckle!
            if (getType() == 8 && (sprintCounter > 0 && sprintCounter < 150) && (riddenByEntity != null))
            {
                MoCTools.buckleMobs(this, 2D, worldObj);
            }
            
            //shy ostriches will run and hide
            if (!isNotScared() && fleeingTick > 0 && fleeingTick < 2)
            {
                fleeingTick = 0;
                setHiding(true);
                setPathToEntity(null);
            }

            if (getHiding())
            {
                //wild shy ostriches will hide their heads only for a short term
                //tamed ostriches will keep their heads hidden until the whip is used again
                if (++hidingCounter > 500 && !getIsTamed())
                {
                    setHiding(false);
                    hidingCounter = 0;
                }

            }

            //to add collision detection
            //Riding();

            if (getType() == 1 && (rand.nextInt(200) == 0))
            {
                //when is chick and becomes adult, change over to different type
                setEdad(getEdad() + 1);
                if (getEdad() >= 100)
                {
                    setAdult(true);
                    setType(0);
                    selectType();
                }
            }

            //egg laying
            if ((getType() == 2) && !getEggWatching() && --this.eggCounter <= 0) //!getIsTamed() &&
            {

                //so it doesn't cause a ostrich overpopulation
                if (worldObj.countEntities(MoCEntityOstrich.class) < 20 && worldObj.countEntities(MoCEntityEgg.class) < 10)
                {
                
                    int OstrichEggType = 30;
                    MoCEntityOstrich entityOstrich = getClosestMaleOstrich(this, 8D);
                    if (this.getIsTamed() && entityOstrich != null)
                    {
                        //OstrichEggType = 31; 
                        MoCEntityEgg entityegg = new MoCEntityEgg(worldObj, OstrichEggType);
                        entityegg.setPosition(this.posX, this.posY, this.posZ);
                        worldObj.spawnEntityInWorld(entityegg);
                        entityOstrich.setEggWatching(true);
                        setEggWatching(true);
                        openMouth();
                    }

                    if (!this.getIsTamed())
                    {
                        MoCEntityEgg entityegg = new MoCEntityEgg(worldObj, OstrichEggType);
                        entityegg.setPosition(this.posX, this.posY, this.posZ);
                        worldObj.spawnEntityInWorld(entityegg);
                        setEggWatching(true);
                        if (entityOstrich != null)
                        {
                            entityOstrich.setEggWatching(true);
                        }
                        openMouth();
                    }

                    //TODO change sound
                    this.worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);

                    //finds a male and makes it eggWatch as well
                    //MoCEntityOstrich entityOstrich = (MoCEntityOstrich) getClosestSpecificEntity(this, MoCEntityOstrich.class, 12D);

                }
                this.eggCounter = this.rand.nextInt(1000) + 1000;
            }

            //egg protection
            if (getEggWatching())
            {
                //look for and protect eggs and move close
                MoCEntityEgg myEgg = (MoCEntityEgg) getBoogey(8D);
                if ((myEgg != null) && (MoCTools.getSqDistanceTo(myEgg, this.posX, this.posY, this.posZ) > 4D))
                {
                    PathEntity pathentity = worldObj.getPathEntityToEntity(this, myEgg, 16F, true, false, false, true);
                    setPathToEntity(pathentity);
                }
                if (myEgg == null) //didn't find egg
                {
                    setEggWatching(false);

                    EntityPlayer eggStealer = worldObj.getClosestPlayerToEntity(this, 10D);
                    if (eggStealer != null)
                    {
                        if (!getIsTamed() && worldObj.difficultySetting > 0)
                        {
                            entityToAttack = eggStealer;
                            //setAngry(true);
                            flapWings();
                        }
                    }

                }
            }

        }

    }

    protected MoCEntityOstrich getClosestMaleOstrich(Entity entity, double d)
    {
        double d1 = -1D;
        MoCEntityOstrich entityliving = null;
        List list = worldObj.getEntitiesWithinAABBExcludingEntity(entity, entity.boundingBox.expand(d, d, d));
        for (int i = 0; i < list.size(); i++)
        {
            Entity entity1 = (Entity) list.get(i);
            if (!(entity1 instanceof MoCEntityOstrich) || ((entity1 instanceof MoCEntityOstrich) && ((MoCEntityOstrich) entity1).getType() < 3))
            {
                continue;
            }

            double d2 = entity1.getDistanceSq(entity.posX, entity.posY, entity.posZ);
            if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1)))// && ((EntityLiving) entity1).canEntityBeSeen(entity))
            {
                d1 = d2;
                entityliving = (MoCEntityOstrich) entity1;
            }
        }

        return entityliving;
    }

    @Override
    public boolean entitiesToInclude(Entity entity)
    {
        return ((entity instanceof MoCEntityEgg) && (((MoCEntityEgg) entity).eggType == 30)

        );
    }

    @Override
    public boolean interact(EntityPlayer entityplayer)
    {
        //TODO remove
        //setTamed(true);
        
        if (super.interact(entityplayer)) { return false; }
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();

        

        if (getIsTamed() && (getType() > 1) && (itemstack != null) && !getIsRideable() && (itemstack.itemID == MoCreatures.horsesaddle.itemID || itemstack.itemID == Item.saddle.itemID))
        {
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);

            }
            worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, ((rand.nextFloat() - rand.nextFloat()) * 0.2F) + 1.0F);
            setRideable(true);
            return true;

        }

        //makes the ostrich stay by hiding their heads
        if ((itemstack != null) && (itemstack.itemID == MoCreatures.whip.itemID) && getIsTamed() && (riddenByEntity == null))
        {
            setHiding(!getHiding());
            return true;
        }

        
        
        if ((itemstack != null) && this.getIsTamed() && getType()> 1 && itemstack.itemID == MoCreatures.vialdarkness.itemID)
        {
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Item.glassBottle));
            }
            else
            {
                entityplayer.inventory.addItemStackToInventory(new ItemStack(Item.glassBottle));
            }
            if (getType() == 6)
            {
                health = getMaxHealth();
            }
            else
            {
                transform(6);
            }
            MoCTools.playCustomSound(this, "drinking", worldObj);
            return true;
        }
        
        if ((itemstack != null) && this.getIsTamed() && getType()> 1 && itemstack.itemID == MoCreatures.vialundead.itemID)
        {
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Item.glassBottle));
            }
            else
            {
                entityplayer.inventory.addItemStackToInventory(new ItemStack(Item.glassBottle));
            }
            if (getType() == 7)
            {
                health = getMaxHealth();
            }
            else
            {
                transform(7);
            }
            MoCTools.playCustomSound(this, "drinking", worldObj);
            return true;
        }
        
        if ((itemstack != null) && this.getIsTamed() && getType()> 1 && itemstack.itemID == MoCreatures.viallight.itemID)
        {
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Item.glassBottle));
            }
            else
            {
                entityplayer.inventory.addItemStackToInventory(new ItemStack(Item.glassBottle));
            }
            if (getType() == 8)
            {
                health = getMaxHealth();
            }
            else
            {
                transform(8);
            }
            MoCTools.playCustomSound(this, "drinking", worldObj);
            return true;
        }
        
        if ((itemstack != null) && this.getIsTamed() && getType()> 1 && itemstack.itemID == MoCreatures.vialnightmare.itemID)
        {
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Item.glassBottle));
            }
            else
            {
                entityplayer.inventory.addItemStackToInventory(new ItemStack(Item.glassBottle));
            }
            if (getType() == 5)
            {
                health = getMaxHealth();
            }
            else
            {
                transform(5);
            }
            MoCTools.playCustomSound(this, "drinking", worldObj);
            return true;
        }
        if (getIsTamed() && getIsChested() && (getType() > 1) && itemstack!= null && itemstack.itemID == Block.cloth.blockID)
        {
            //int colorInt = BlockCloth.getBlockFromDye(itemstack.getItemDamage());
            int colorInt = (itemstack.getItemDamage());
            if (colorInt == 0) colorInt = 16;
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                
            }
            MoCTools.playCustomSound(this, "mob.chickenplop", worldObj);
            dropFlag();
            setFlagColor((byte)colorInt);
            return true;
        }
        
        if ((itemstack != null) && (getType() > 1) && getIsTamed() && !getIsChested() && (itemstack.itemID == Block.chest.blockID))
        {
             if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }

            entityplayer.inventory.addItemStackToInventory(new ItemStack(MoCreatures.key));
            setIsChested(true);
            worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, ((rand.nextFloat() - rand.nextFloat()) * 0.2F) + 1.0F);
            return true;
        }
        
        if ((itemstack != null) && (itemstack.itemID == MoCreatures.key.itemID) && getIsChested())
        {
            // if first time opening horse chest, we must initialize it
            if (localchest == null)
            {
                localchest = new MoCAnimalChest("OstrichChest", 9);
            }
            // only open this chest on server side
            if (MoCreatures.isServer())
            {
                entityplayer.displayGUIChest(localchest);
            }
            return true;

        }

        if (getIsTamed() && (getType() > 1) && itemstack!= null)
        {
            
            Item item = itemstack.getItem();
            if (item instanceof ItemArmor)
            {
                byte helmetType = 0;
                if (itemstack.itemID == Item.helmetLeather.itemID)
                {
                    helmetType = 1;
                }
                else if (itemstack.itemID == Item.helmetIron.itemID)
                {
                    helmetType = 2;
                }
                else if (itemstack.itemID == Item.helmetGold.itemID)
                {
                    helmetType = 3;
                }
                else if (itemstack.itemID == Item.helmetDiamond.itemID)
                {
                    helmetType = 4;
                }
                else if (itemstack.itemID == MoCreatures.helmetHide.itemID)
                {
                    helmetType = 5;
                }
                else if (itemstack.itemID == MoCreatures.helmetFur.itemID)
                {
                    helmetType = 6;
                }
                else if (itemstack.itemID == MoCreatures.helmetCroc.itemID)
                {
                    helmetType = 7;
                }
                /*else if (itemstack.itemID == MoCreatures.helmetGreen.itemID)
                {
                    helmetType = 8;
                }*/
                else if (itemstack.itemID == MoCreatures.helmetScorpDirt.itemID)
                {
                    helmetType = 9;
                }
                else if (itemstack.itemID == MoCreatures.helmetScorpFrost.itemID)
                {
                    helmetType = 10;
                }
                else if (itemstack.itemID == MoCreatures.helmetScorpCave.itemID)
                {
                    helmetType = 11;
                }
                else if (itemstack.itemID == MoCreatures.helmetScorpNether.itemID)
                {
                    helmetType = 12;
                }
                
                if (helmetType != 0)// && --itemstack.stackSize == 0)
                {
                    entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                    dropArmor();
                    MoCTools.playCustomSound(this, "armoroff", worldObj);
                    setHelmet(helmetType);
                    return true;
                }
               
            }
        }
        if (getIsRideable() && getIsAdult() && (riddenByEntity == null))
        {
            entityplayer.rotationYaw = rotationYaw;
            entityplayer.rotationPitch = rotationPitch;
            setHiding(false);
            if (!this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity == entityplayer))
            {
                entityplayer.mountEntity(this);
                // setDisplayName(false);
            }

            return true;
        }

        return false;
    }

    /**
     * Drops a block of the color of the flag if carrying one
     */
    private void dropFlag() 
    {
        if (MoCreatures.isServer() && getFlagColor() != 0)
        {
            int color = getFlagColor();
            if (color == 16) color = 0;
            EntityItem entityitem = new EntityItem(worldObj, posX, posY, posZ, new ItemStack(Item.itemsList[Block.cloth.blockID], 1, color));
            entityitem.delayBeforeCanPickup = 10;
            worldObj.spawnEntityInWorld(entityitem);
            //ItemStack itemstack = new ItemStack(Item.itemsList[Block.cloth.blockID], 1, color);
            setFlagColor((byte)0);
        }
    }

    private void openMouth()
    {
        mouthCounter = 1;
    }

    private void flapWings()
    {
        wingCounter = 1;
    }

    @Override
    protected String getHurtSound()
    {
        openMouth();
        return "ostrichhurt";
    }

    @Override
    protected String getLivingSound()
    {
        openMouth();
        if (getType() == 1) { return "ostrichchick"; }

        return "ostrichgrunt";
    }

    @Override
    protected String getDeathSound()
    {
        openMouth();
        return "ostrichdying";
    }

    @Override
    protected int getDropItemId()
    {
        boolean flag = (rand.nextInt(3) == 0);
        if (flag && (this.getType() == 8)) // unicorn
        { return MoCreatures.unicorn.itemID; }
        if (this.getType() == 5 && flag) 
        { return MoCreatures.heartfire.itemID; }
        if (this.getType() == 6 && flag) // bat horse
        { return MoCreatures.heartdarkness.itemID; }
        if (this.getType() == 7 )
        {
            if (flag) { return MoCreatures.heartundead.itemID; }
            return Item.rottenFlesh.itemID;
        }
         return MoCreatures.ostrichmeat.itemID;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        setRideable(nbttagcompound.getBoolean("Saddle"));
        setEggWatching(nbttagcompound.getBoolean("EggWatch"));
        setHiding(nbttagcompound.getBoolean("Hiding"));
        setHelmet(nbttagcompound.getByte("Helmet"));
        setFlagColor(nbttagcompound.getByte("FlagColor"));
        setIsChested(nbttagcompound.getBoolean("Bagged"));
        if (getIsChested())
        {
            NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
            localchest = new MoCAnimalChest("OstrichChest", 18);
            for (int i = 0; i < nbttaglist.tagCount(); i++)
            {
                NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.tagAt(i);
                int j = nbttagcompound1.getByte("Slot") & 0xff;
                if ((j >= 0) && j < localchest.getSizeInventory())
                {
                    localchest.setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(nbttagcompound1));
                }
            }
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("Saddle", getIsRideable());
        nbttagcompound.setBoolean("EggWatch", getEggWatching());
        nbttagcompound.setBoolean("Hiding", getHiding());
        nbttagcompound.setByte("Helmet", getHelmet());
        nbttagcompound.setByte("FlagColor", getFlagColor());
        nbttagcompound.setBoolean("Bagged", getIsChested());
        
        if (getIsChested() && localchest != null)
        {
            NBTTagList nbttaglist = new NBTTagList();
            for (int i = 0; i < localchest.getSizeInventory(); i++)
            {
                localstack = localchest.getStackInSlot(i);
                if (localstack != null)
                {
                    NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                    nbttagcompound1.setByte("Slot", (byte) i);
                    localstack.writeToNBT(nbttagcompound1);
                    nbttaglist.appendTag(nbttagcompound1);
                }
            }
            nbttagcompound.setTag("Items", nbttaglist);
        }
    }

    @Override
    public boolean getCanSpawnHere()
    {
        //spawns in deserts and plains
        return (MoCreatures.proxy.entityMap.get(this.getEntityName()).frequency > 0) && getCanSpawnHereCreature() && getCanSpawnHereLiving() && (!MoCTools.isNearTorch(this));
    }

    @Override
    public int nameYOffset()
    {
        if (getType() > 1)
        {
            return -105;
        }
        else
        {
            return  (-5 - getEdad());
        }
    }

    @Override
    public double roperYOffset()
    {
        if (getType() > 1)
        {
            return 0D;
        }
        else
        {
            return (double) ((120 - getEdad()) * 0.01D);
        }

    }

    @Override
    public boolean updateMount()
    {
        return getIsTamed();
    }

    @Override
    public boolean forceUpdates()
    {
        return getIsTamed();
    }

    @Override
    public boolean isMyHealFood(ItemStack par1ItemStack)
    {
        return isItemEdible(par1ItemStack.getItem());
    }
    
    @Override
    public void dropMyStuff() 
    {
        if (MoCreatures.isServer())
        {
            dropArmor();
            MoCTools.dropSaddle(this, worldObj);
            
            if (getIsChested())
            {
               MoCTools.dropInventory(this, localchest);
               MoCTools.dropCustomItem(this, this.worldObj, new ItemStack(Block.chest, 1));
               setIsChested(false);
            }
        }
        
    }
    
   

    /**
     * Drops the helmet
     */
    @Override
    public void dropArmor() 
    {
        if (MoCreatures.isServer())
        {
            EntityItem entityitem = null;// = new EntityItem(worldObj, posX, posY, posZ, new ItemStack(Item.itemsList[Block.cloth.blockID], 1, color));
            
            switch (getHelmet())
            {
            case 0:
            case 8:
                return;
                //break;
            case 1:
                entityitem = new EntityItem(worldObj, posX, posY, posZ, new ItemStack(Item.helmetLeather, 1));
                break;
            case 2:
                entityitem = new EntityItem(worldObj, posX, posY, posZ, new ItemStack(Item.helmetIron, 1));
                break;
            case 3:
                entityitem = new EntityItem(worldObj, posX, posY, posZ, new ItemStack(Item.helmetGold, 1));
                break;
            case 4:
                entityitem = new EntityItem(worldObj, posX, posY, posZ, new ItemStack(Item.helmetDiamond, 1));
                break;
            case 5:
                entityitem = new EntityItem(worldObj, posX, posY, posZ, new ItemStack(MoCreatures.helmetHide, 1));
                break;
            case 6:
                entityitem = new EntityItem(worldObj, posX, posY, posZ, new ItemStack(MoCreatures.helmetFur, 1));
                break;
            case 7:
                entityitem = new EntityItem(worldObj, posX, posY, posZ, new ItemStack(MoCreatures.helmetCroc, 1));
                break;
            case 9:
                entityitem = new EntityItem(worldObj, posX, posY, posZ, new ItemStack(MoCreatures.helmetScorpDirt, 1));
                break;
            case 10:
                entityitem = new EntityItem(worldObj, posX, posY, posZ, new ItemStack(MoCreatures.helmetScorpFrost, 1));
                break;
            case 11:
                entityitem = new EntityItem(worldObj, posX, posY, posZ, new ItemStack(MoCreatures.helmetScorpCave, 1));
                break;
            case 12:
                entityitem = new EntityItem(worldObj, posX, posY, posZ, new ItemStack(MoCreatures.helmetScorpNether, 1));
                break;
            
            }
            
            if (entityitem != null)
            {
                entityitem.delayBeforeCanPickup = 10;
                worldObj.spawnEntityInWorld(entityitem);
            }
            setHelmet((byte)0);
            
        }
        
        
    }

   
    @Override
    public boolean isFlyer()
    {
        return (getType() == 5 || getType() == 6);
    }
    
    @Override
    protected void fall(float f)
    {
        if (isFlyer()) { return; }
    }
    
    @Override
    protected double myFallSpeed()
    {
        return 0.9D;
    }

    @Override
    protected double flyerThrust()
    {
        return 0.9D;
    }
    
    @Override
    protected float flyerFriction()
    {
        return 0.96F;
    }
    
    @Override
    protected boolean selfPropelledFlyer()
    {
        return getType() == 6;
    }
    
    @Override
    public void makeEntityJump()
    {
        if (jumpCounter > 5)
        {
            //return;
            jumpCounter = 1;
        }
        if (jumpCounter == 0)
        {
            MoCTools.playCustomSound(this, "wingflap", worldObj);
            this.jumpPending = true;
            jumpCounter = 1;
        }
        
    }
    
    
    
}
