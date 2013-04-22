package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class MoCEntityGoat extends MoCEntityAnimal {
    private boolean hungry;
    private boolean swingLeg;
    private boolean swingEar;
    private boolean swingTail;
    private boolean bleat;
    private boolean eating;
    private int bleatcount;
    private int attacking;
    public int movecount;
    private int chargecount;
    private int tailcount; // 90 to -45
    private int earcount; // 20 to 40 default = 30
    private int eatcount;
    public EntityLiving roper;

    // TODO
    /*
     * sounds poo
     */
    public MoCEntityGoat(World world)
    {
        super(world);
        setSize(1.4F, 0.9F);
        health = 12;

        setEdad(70);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(22, Byte.valueOf((byte) 0)); // isUpset - 0 false 1 true
        dataWatcher.addObject(23, Byte.valueOf((byte) 0)); // isCharging - 0 false 1 true
    }

    public boolean getUpset()
    {
        return (dataWatcher.getWatchableObjectByte(22) == 1);
    }

    public boolean getCharging()
    {
        return (dataWatcher.getWatchableObjectByte(23) == 1);
    }

    public void setUpset(boolean flag)
    {
        if (!MoCreatures.isServer()) { return; }
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(22, Byte.valueOf(input));
    }

    public void setCharging(boolean flag)
    {
        if (!MoCreatures.isServer()) { return; }
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(23, Byte.valueOf(input));
    }

    @Override
    public void selectType()
    {
        /*
         * type 1 = baby type 2 = female type 3 = female 2 type 4 = female 3
         * type 5 = male 1 type 6 = male 2 type 7 = male 3
         */
        if (getType() == 0)
        {
            int i = rand.nextInt(100);
            if (i <= 15)
            {
                setType(1);
                setEdad(50);
            }
            else if (i <= 30)
            {
                setType(2);
                setEdad(70);
            }
            else if (i <= 45)
            {
                setType(3);
                setEdad(70);
            }
            else if (i <= 60)
            {
                setType(4);
                setEdad(70);
            }
            else if (i <= 75)
            {
                setType(5);
                setEdad(90);
            }
            else if (i <= 90)
            {
                setType(6);
                setEdad(90);
            }
            else
            {
                setType(7);
                setEdad(90);
            }
        }

    }

    @Override
    public int getMaxHealth()
    {
        return 12;
    }

    @Override
    public String getTexture()
    {

        switch (getType())
        {
        case 1:
            return MoCreatures.proxy.MODEL_TEXTURE + "goat1.png";
        case 2:
            return MoCreatures.proxy.MODEL_TEXTURE + "goat2.png";
        case 3:
            return MoCreatures.proxy.MODEL_TEXTURE + "goat3.png";
        case 4:
            return MoCreatures.proxy.MODEL_TEXTURE + "goat4.png";
        case 5:
            return MoCreatures.proxy.MODEL_TEXTURE + "goat5.png";
        case 6:
            return MoCreatures.proxy.MODEL_TEXTURE + "goat6.png";
        case 7:
            return MoCreatures.proxy.MODEL_TEXTURE + "goat1.png";

        default:
            return MoCreatures.proxy.MODEL_TEXTURE + "goat1.png";
        }
    }

    public void calm()
    {
        entityToAttack = null;
        setUpset(false);
        setCharging(false);
        moveSpeed = 0.7F;
        attacking = 0;
        chargecount = 0;
    }

    @Override
    protected void jump()
    {
        if (getType() == 1)
        {
            motionY = 0.41D;
        }
        else if (getType() < 5)
        {
            motionY = 0.45D;
        }
        else
        {
            motionY = 0.5D;
        }

        if (isPotionActive(Potion.jump))
        {
            motionY += (float) (getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F;
        }
        if (isSprinting())
        {
            float f = rotationYaw * 0.01745329F;
            motionX -= MathHelper.sin(f) * 0.2F;
            motionZ += MathHelper.cos(f) * 0.2F;
        }
        isAirBorne = true;
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (rand.nextInt(100) == 0)
        {
            setSwingEar(true);
        }

        if (rand.nextInt(80) == 0)
        {
            setSwingTail(true);
        }

        if (rand.nextInt(50) == 0)
        {
            setEating(true);
        }

        if ((hungry) && (rand.nextInt(20) == 0))
        {
            hungry = false;
        }
        if (getBleating())
        {
            bleatcount++;
            if (bleatcount > 15)
            {
                bleatcount = 0;
                setBleating(false);
            }

        }
        if (MoCreatures.isServer() && (getEdad() < 90 || getType() > 4 && getEdad() < 100) && rand.nextInt(500) == 0)
        {
            setEdad(getEdad() + 1);
            if (getType() == 1 && getEdad() > 70)
            {
                int i = rand.nextInt(6) + 2;
                setType(i);

            }
        }

        if (getUpset())
        {
            attacking += (rand.nextInt(4)) + 2;
            if (attacking > 75)
            {
                attacking = 75;
            }

            if (rand.nextInt(500) == 0 || entityToAttack == null)
            {
                calm();
                // setUpset(false);
                // attacking = 0;
            }

            if (!getCharging() && rand.nextInt(35) == 0)
            {
                swingLeg();
            }

            if (!getCharging())
            {
                setPathToEntity(null);
            }

            if (entityToAttack != null)// && rand.nextInt(100)==0)
            {
                faceEntity(entityToAttack, 10F, 10F);
                if (rand.nextInt(80) == 0)
                {
                    setCharging(true);
                }
            }

        }

        if (getCharging())
        {
            chargecount++;
            if (chargecount > 120)
            {
                chargecount = 0;
                moveSpeed = 0.7F;
            }
            else
            {
                moveSpeed = 1.0F;
            }

            if (entityToAttack == null)
            {
                calm();
                // chargecount = 0;
                // moveSpeed = 0.7F;
            }

        }

        if (!getUpset() && !getCharging())
        {
            // roped, follow the player
            /*if (getIsTamed() && (roper != null))
            {
                float f = roper.getDistanceToEntity(this);
                if ((f > 5F))
                {
                    getPathOrWalkableBlock(roper, f);
                    return;
                }

            }*/

            EntityPlayer entityplayer1 = worldObj.getClosestPlayerToEntity(this, 24D);
            if (entityplayer1 != null)
            {// Behaviour that happens only close to player :)

                // is there food around? only check with player near
                EntityItem entityitem = getClosestEntityItem(this, 10D);
                if (entityitem != null)
                {
                    float f = entityitem.getDistanceToEntity(this);
                    if (f > 2.0F)
                    {
                        int i = MathHelper.floor_double(entityitem.posX);
                        int j = MathHelper.floor_double(entityitem.posY);
                        int k = MathHelper.floor_double(entityitem.posZ);
                        faceLocation(i, j, k, 30F);

                        getMyOwnPath(entityitem, f);
                        return;
                    }
                    if ((f < 2.0F) && (entityitem != null) && (deathTime == 0) && rand.nextInt(50) == 0)
                    {
                        worldObj.playSoundAtEntity(this, "goateating", 1.0F, 1.0F + ((rand.nextFloat() - rand.nextFloat()) * 0.2F));
                        setEating(true);

                        entityitem.setDead();
                        return;
                    }
                }

                // player nearby does it have foods in hand?

                /*ItemStack itemstack1 = entityplayer1.inventory.getCurrentItem();
                Item item1 = null;
                if (itemstack1 != null)    item1 = itemstack1.getItem();
                if (item1 != null && isItemEdible(item1))
                {
                    PathEntity pathentity = worldObj.getPathEntityToEntity(this, entityplayer1, 16F, true, false, false, true);
                    setPathToEntity(pathentity);
                    hungry = true;
                    return;

                }
                */

                // find other goat to play!
                if (getType() > 4 && rand.nextInt(200) == 0)
                {
                    MoCEntityGoat entitytarget = (MoCEntityGoat) getClosestEntityLiving(this, 14D);
                    if (entitytarget != null)
                    {
                        setUpset(true);
                        entityToAttack = entitytarget;
                        entitytarget.setUpset(true);
                        entitytarget.entityToAttack = this;
                    }
                }

            }// end of close to player behavior
        }// end of !upset !charging
    }

    @Override
    public boolean isMyFavoriteFood(ItemStack par1ItemStack)
    {
        Item item1 = null;
        if (par1ItemStack != null)
        {
            item1 = par1ItemStack.getItem();
        }
        return (item1 != null && isItemEdible(item1));
    }

    @Override
    public int getTalkInterval()
    {
        if (hungry) { return 20; }

        return 120;
    }

    @Override
    public boolean entitiesToIgnore(Entity entity)
    {
        return ((!(entity instanceof MoCEntityGoat)) || ((((MoCEntityGoat) entity).getType() < 5)) || (((MoCEntityGoat) entity).roper != null));
    }

    @Override
    protected boolean isMovementCeased()
    {
        return getUpset() && !getCharging();
    }

    @Override
    protected void attackEntity(Entity entity, float f)
    {

        if (attackTime <= 0 && (f < 3.0D) && (entity.boundingBox.maxY > boundingBox.minY) && (entity.boundingBox.minY < boundingBox.maxY) && attacking > 70)
        {
            attackTime = 30;

            attacking = 30;

            worldObj.playSoundAtEntity(this, "goatsmack", 1.0F, 1.0F + ((rand.nextFloat() - rand.nextFloat()) * 0.2F));
            if (entity instanceof MoCEntityGoat)
            {
                MoCTools.bigsmack(this, entity, 0.4F);
                if (rand.nextInt(10) == 0)
                {
                    calm();
                    ((MoCEntityGoat) entity).calm();
                }

            }
            else
            {
                entity.attackEntityFrom(DamageSource.causeMobDamage(this), 3);
                MoCTools.bigsmack(this, entity, 0.8F);
                if (rand.nextInt(3) == 0)
                {
                    calm();
                }
            }
        }
    }

    @Override
    public boolean isNotScared()
    {
        return getType() > 4;
    }

    private void swingLeg()
    {
        if (!getSwingLeg())
        {
            setSwingLeg(true);
            movecount = 0;
        }
    }

    public boolean getSwingLeg()
    {
        return swingLeg;
    }

    public void setSwingLeg(boolean flag)
    {
        swingLeg = flag;
    }

    public boolean getSwingEar()
    {
        return swingEar;
    }

    public void setSwingEar(boolean flag)
    {
        swingEar = flag;
    }

    public boolean getSwingTail()
    {
        return swingTail;
    }

    public void setSwingTail(boolean flag)
    {
        swingTail = flag;
    }

    public boolean getEating()
    {
        return eating;
    }

    @Override
    public void setEating(boolean flag)
    {
        eating = flag;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, int i)
    {
        if (super.attackEntityFrom(damagesource, i))
        {
            Entity entity = damagesource.getEntity();

            if ((entity != this) && (worldObj.difficultySetting > 0) && getType() > 4)
            {
                entityToAttack = entity;
                setUpset(true);
            }
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public void onUpdate()
    {

        if (getSwingLeg())
        {
            movecount += 5;
            if (movecount == 30)
            {
                worldObj.playSoundAtEntity(this, "goatdigg", 1.0F, 1.0F + ((rand.nextFloat() - rand.nextFloat()) * 0.2F));
            }

            if (movecount > 100)
            {
                setSwingLeg(false);
                movecount = 0;
            }
        }

        if (getSwingEar())
        {
            earcount += 5;
            if (earcount > 40)
            {
                setSwingEar(false);
                earcount = 0;
            }
        }

        if (getSwingTail())
        {
            tailcount += 15;
            if (tailcount > 135)
            {
                setSwingTail(false);
                tailcount = 0;
            }
        }

        if (getEating())
        {
            eatcount += 1;
            if (eatcount == 2)
            {
                EntityPlayer entityplayer1 = worldObj.getClosestPlayerToEntity(this, 3D);
                if (entityplayer1 != null)
                {
                    worldObj.playSoundAtEntity(this, "goateating", 1.0F, 1.0F + ((rand.nextFloat() - rand.nextFloat()) * 0.2F));
                }
            }
            if (eatcount > 25)
            {
                setEating(false);
                eatcount = 0;
            }
        }

        super.onUpdate();
    }

    public int legMovement()
    {
        if (!getSwingLeg()) { return 0; }

        if (movecount < 21) { return movecount * -1; }
        if (movecount < 70) { return movecount - 40; }
        return -movecount + 100;
    }

    public int earMovement()
    {
        // 20 to 40 default = 30
        if (!getSwingEar()) { return 0; }
        if (earcount < 11) { return earcount + 30; }
        if (earcount < 31) { return -earcount + 50; }
        return earcount - 10;
    }

    public int tailMovement()
    {
        // 90 to -45
        if (!getSwingTail()) { return 90; }

        // return -tailcount + 90;
        return tailcount - 45;
    }

    public int mouthMovement()
    {
        if (!getEating()) { return 0; }
        if (eatcount < 6) { return eatcount; }
        if (eatcount < 16) { return -eatcount + 10; }
        return eatcount - 20;
    }

    @Override
    protected void fall(float f)
    {
    }

    @Override
    public boolean interact(EntityPlayer entityplayer)
    {
        //System.out.println("edad = " + getEdad());
        if (super.interact(entityplayer)) { return false; }
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if (itemstack != null && itemstack.itemID == Item.bucketEmpty.itemID)
        {
            if (getType() > 4)
            {
                setUpset(true);
                entityToAttack = entityplayer;
                return false;
            }
            if (getType() == 1) { return false; }

            entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Item.bucketMilk));
            return true;
        }

        if (getIsTamed())
        {

            /*if ((itemstack != null) //&& MoCreatures.isServer()
                    && ((itemstack.itemID == MoCreatures.medallion.itemID) || (itemstack.itemID == Item.book.itemID)))
            {
                if (!MoCreatures.isServer())
                {
                    MoCreatures.proxy.setName(entityplayer, this);
                }
                return true;
            }*/
            if ((itemstack != null) && (isItemEdible(itemstack.getItem())))
            {
                if (--itemstack.stackSize == 0)
                {
                    entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                }
                this.health = getMaxHealth();
                worldObj.playSoundAtEntity(this, "goateating", 1.0F, 1.0F + ((rand.nextFloat() - rand.nextFloat()) * 0.2F));

            }

            /*if (itemstack != null && (itemstack.itemID == Item.pickaxeDiamond.itemID || itemstack.itemID == Item.pickaxeWood.itemID || itemstack.itemID == Item.pickaxeStone.itemID || itemstack.itemID == Item.pickaxeIron.itemID || itemstack.itemID == Item.pickaxeGold.itemID))
            {
                //setDisplayName(!getDisplayName());
                return true;
            }*/

            /*if ((itemstack != null) && (riddenByEntity == null) && (roper == null) && (itemstack.itemID == MoCreatures.rope.itemID))
            {
                if (--itemstack.stackSize == 0)
                {
                    entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                }
                worldObj.playSoundAtEntity(this, "roping", 1.0F, 1.0F + ((rand.nextFloat() - rand.nextFloat()) * 0.2F));
                roper = entityplayer;
                return true;
            }
            if ((roper != null))
            {
                entityplayer.inventory.addItemStackToInventory(new ItemStack(MoCreatures.rope));
                worldObj.playSoundAtEntity(this, "roping", 1.0F, 1.0F + ((rand.nextFloat() - rand.nextFloat()) * 0.2F));
                roper = null;
                return true;
            }*/

            // return true;
        }

        if (!getIsTamed() && (itemstack != null) && isItemEdible(itemstack.getItem()))
        {
            if (MoCreatures.isServer())
            {
                MoCTools.tameWithName((EntityPlayerMP) entityplayer, this);
                //TODO NAMER
                //setTamed(true);
                //if (!MoCreatures.isServer())
                //{
                //    MoCreatures.proxy.setName(entityplayer, this);
                //}
            }
        }

        return false;

    }

    public boolean getBleating()
    {
        return bleat && (getAttacking() == 0);// && !getAttacking();
    }

    public void setBleating(boolean flag)
    {
        bleat = flag;
    }

    public int getAttacking()
    {
        return attacking;
    }

    public void setAttacking(int flag)
    {
        attacking = flag;
    }

    @Override
    public boolean renderName()
    {
        return getDisplayName();
    }

    @Override
    protected String getHurtSound()
    {
        return "goathurt";
    }

    @Override
    protected String getLivingSound()
    {
        setBleating(true);
        if (getType() == 1) { return "goatkid"; }
        if (getType() > 2 && getType() < 5) { return "goatfemale"; }

        return "goatgrunt";
    }

    @Override
    protected String getDeathSound()
    {
        return "goatdying";
    }

    @Override
    protected int getDropItemId()
    {
        return Item.leather.itemID;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        setDisplayName(nbttagcompound.getBoolean("DisplayName"));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("DisplayName", getDisplayName());
    }

    @Override
    public boolean getCanSpawnHere()
    {
        return (MoCreatures.proxy.getFrequency(this.getEntityName()) > 0) && super.getCanSpawnHere();
    }
}
