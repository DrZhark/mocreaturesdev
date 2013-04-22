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
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class MoCEntityBear extends MoCEntityAnimal {

    public int mouthCounter;
    private int attackCounter;
    private int standingCounter;

    public MoCEntityBear(World world)
    {
        super(world);
        //texture = MoCreatures.proxy.MODEL_TEXTURE + "bearbrowm.png";
        setSize(0.9F, 1.3F);
        roper = null;
        //health = 25;
        setEdad(55);
        //force = 5;
        //attackRange = 16D;
        if (rand.nextInt(4) == 0)
        {
            setAdult(false);

        }
        else
        {
            setAdult(true);
        }
    }

    /**
     * Initializes datawatchers for entity. Each datawatcher is used to sync
     * server data to client.
     */
    @Override
    protected void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(22, Integer.valueOf(0)); // int BearState 
    }

    /**
     * 0 - bear is on fours 1 - standing 2 - sitting
     * 
     * @return
     */
    public int getBearState()
    {
        return dataWatcher.getWatchableObjectInt(22);
    }

    public void setBearState(int i)
    {
        if (!MoCreatures.isServer()) { return; }
        dataWatcher.updateObject(22, Integer.valueOf(i));
    }

    @Override
    public void selectType()
    {
        if (getType() == 0)
        {

            int i = rand.nextInt(100);
            if (i <= 40)
            {
                setType(1);
            }
            else if (i <= 80)
            {
                setType(2);
            }
            else
            {
                setType(3);
            }

            health = getMaxHealth();
        }
    }

    @Override
    public String getTexture()
    {

        switch (getType())
        {
        case 1:
            return MoCreatures.proxy.MODEL_TEXTURE + "bearbrowm.png";
        case 2:
            return MoCreatures.proxy.MODEL_TEXTURE + "bearblack.png";
        case 3:
            return MoCreatures.proxy.MODEL_TEXTURE + "bearpanda.png";
        case 4:
            return MoCreatures.proxy.MODEL_TEXTURE + "bearpolar.png";

        default:
            return MoCreatures.proxy.MODEL_TEXTURE + "bearbrowm.png";
        }
    }

    /**
     * Returns the factor size for the bear, polars are bigger and pandas
     * smaller
     * 
     * @return
     */
    public float getBearSize()
    {
        switch (getType())
        {
        case 1:
            return 1.2F;
        case 2:
            return 0.9F;
        case 3:
            return 0.8F;
        case 4:
            return 1.4F;

        default:
            return 1.0F;
        }
    }

    @Override
    public int getMaxHealth()
    {
        switch (getType())
        {
        case 1:
            return 20;
        case 2:
            return 15;
        case 3:
            return 15;
        case 4:
            return 25;

        default:
            return 20;
        }
    }

    /**
     * Returns the distance at which the bear attacks prey
     * 
     * @return
     */
    public double getAttackRange()
    {
        int factor = 1;
        if (worldObj.difficultySetting > 1)
        {
            factor = 2;
        }

        switch (getType())
        {
        case 1:
            return 6D * factor;
        case 2:
            return 6D * factor;
        case 3:
            return 1D;
        case 4:
            return 8D * factor;

        default:
            return 8D;
        }
    }

    /**
     * The damage the bear does
     * 
     * @return
     */
    public int getAttackStrength()
    {
        int factor = (worldObj.difficultySetting);

        switch (getType())
        {
        case 1:
            return 2 * factor;
        case 2:
            return 1 * factor;
        case 3:
            return 1;
        case 4:
            return 3 * factor;

        default:
            return 2;
        }
    }

    @Override
    protected void attackEntity(Entity entity, float f)
    {
        if (attackTime <= 0 && (f < 2.5D) && (entity.boundingBox.maxY > boundingBox.minY) && (entity.boundingBox.minY < boundingBox.maxY))
        {
            startAttack();
            attackTime = 20;
            entity.attackEntityFrom(DamageSource.causeMobDamage(this), getAttackStrength());
            if (!(entity instanceof EntityPlayer))
            {
                MoCTools.destroyDrops(this, 3D);
            }
        }
    }

    /**
     * Checks if bigcat is sitting.
     */
    @Override
    protected boolean isMovementCeased()
    {
        return getBearState() == 2;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, int i)
    {
        if (super.attackEntityFrom(damagesource, i))
        {
            Entity entity = damagesource.getEntity();
            if ((riddenByEntity == entity) || (ridingEntity == entity)) { return true; }
            if ((entity != this) && (worldObj.difficultySetting > 0) && this.getType() != 3)
            {
                entityToAttack = entity;
            }
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public boolean isNotScared()
    {
        return getType() != 3;
    }

    @Override
    protected Entity findPlayerToAttack()
    {
        if (worldObj.difficultySetting > 0)
        {
            float f = getBrightness(1.0F);
            if (f < 0.0F && this.getType() == 1 || this.getType() == 4)
            {
                EntityPlayer entityplayer = worldObj.getClosestVulnerablePlayerToEntity(this, getAttackRange());
                //worldObj.getClosestPlayerToEntity(this, getAttackRange());
                if (entityplayer != null) { return entityplayer; }
            }
            if (rand.nextInt(80) == 0 && this.getType() != 3)
            {
                EntityLiving entityliving = getClosestEntityLiving(this, 10D);
                return entityliving;
            }
        }
        return null;
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (mouthCounter > 0 && ++mouthCounter > 30)
        {
            mouthCounter = 0;
        }

        if (attackCounter > 0 && ++attackCounter > 100)
        {
            attackCounter = 0;
        }

        if ((MoCreatures.isServer()) && !getIsAdult() && (rand.nextInt(250) == 0))
        {
            setEdad(getEdad() + 1);
            if (getEdad() >= 100)
            {
                setAdult(true);
            }
        }
        /**
         * panda bears and cubs will sit down every now and then
         */
        if ((MoCreatures.isServer()) && (getType() == 3 || (!getIsAdult() && getEdad() < 60)) && (rand.nextInt(300) == 0))
        {
            setBearState(2);
        }

        /**
         * Sitting bears will resume on fours stance every now and then
         */
        if ((MoCreatures.isServer()) && (getBearState() == 2) && (rand.nextInt(800) == 0))
        {
            setBearState(0);
        }

        /**
         * Adult non panda bears will stand on hind legs if close to player
         */

        if ((MoCreatures.isServer()) && standingCounter == 0 && getBearState() != 2 && getIsAdult() && getType() != 3 && (rand.nextInt(500) == 0))
        {
            standingCounter = 1;
            EntityPlayer entityplayer1 = worldObj.getClosestPlayerToEntity(this, 8D);
            if (entityplayer1 != null)
            {
                setBearState(1);
            }
        }

        if ((MoCreatures.isServer()) && standingCounter > 0 && ++standingCounter > 50)
        {
            standingCounter = 0;
            setBearState(0);
        }

        if (MoCreatures.isServer() && getType() == 3 && (deathTime == 0) && getBearState() != 2)
        {
            EntityItem entityitem = getClosestItem(this, 12D, Item.reed.itemID, Item.sugar.itemID);
            if (entityitem != null)
            {

                float f = entityitem.getDistanceToEntity(this);
                if (f > 2.0F)
                {
                    getMyOwnPath(entityitem, f);
                }
                if ((f < 2.0F) && (entityitem != null) && (deathTime == 0))
                {
                    entityitem.setDead();
                    worldObj.playSoundAtEntity(this, "eating", 1.0F, 1.0F + ((rand.nextFloat() - rand.nextFloat()) * 0.2F));
                    //setTamed(true);
                    health = getMaxHealth();
                }

            }
        }
    }

    @Override
    public boolean getCanSpawnHere()
    {
        if (MoCTools.isNearTorch(this)) { return false; }
        checkSpawningBiome();
        return (MoCreatures.proxy.getFrequency(this.getEntityName()) > 0) && super.getCanSpawnHere();
    }

    @Override
    public boolean interact(EntityPlayer entityplayer)
    {
        if (super.interact(entityplayer)) { return false; }
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if ((itemstack != null) && (getType() == 3) && ((itemstack.itemID == MoCreatures.sugarlump.itemID) || (itemstack.itemID == Item.reed.itemID)))
        {
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }

            if (MoCreatures.isServer())
            {
                MoCTools.tameWithName((EntityPlayerMP) entityplayer, this);
            }
            health = getMaxHealth();
            eatingAnimal();
            if (MoCreatures.isServer() && !getIsAdult() && (getEdad() < 100))
            {
                setEdad(getEdad() + 1);
            }

            return true;
        }
        return false;
    }

    @Override
    public boolean entitiesToIgnore(Entity entity)
    {
        return ((super.entitiesToIgnore(entity)) || (entity instanceof MoCEntityBear) || (entity instanceof MoCEntityBigCat) || (entity instanceof MoCEntityElephant) || (entity instanceof EntityPlayer)

        );
    }

    @Override
    protected String getDeathSound()
    {
        return "beardying";
    }

    @Override
    protected int getDropItemId()
    {
        return MoCreatures.animalHide.itemID;
    }

    @Override
    protected String getHurtSound()
    {
        openMouth();
        return "bearhurt";
    }

    @Override
    protected String getLivingSound()
    {
        openMouth();
        return "beargrunt";
    }

    @Override
    public int getMaxSpawnedInChunk()
    {
        return 2;
    }

    @Override
    public boolean checkSpawningBiome()
    {
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(boundingBox.minY);
        int k = MathHelper.floor_double(posZ);

        String s = MoCTools.BiomeName(worldObj, i, j, k);
        //System.out.println("biome = " + s);
        int l = rand.nextInt(10);

        if (s.equals("Taiga") || s.equals("Frozen Ocean") || s.equals("Frozen River") || s.equals("Ice Plains") || s.equals("Ice Mountains") || s.equals("TaigaHills"))
        {
            setType(4);//polar bear
            //System.out.println("Setting type 4");
        }
        //selectType();
        return true;
    }

    private void openMouth()
    {
        mouthCounter = 1;
    }

    public float getAttackSwing()
    {
        //TODO FIX!
        return 0;
        //if (attackCounter == 0) return 0;
        //return ( (float)(attackCounter/10F) - 10F)/3F;
    }

    private void startAttack()
    {

        if (attackCounter == 0 && getBearState() == 1)
        {
            attackCounter = 1;
        }
    }

    private void eatingAnimal()
    {
        openMouth();
        MoCTools.playCustomSound(this, "eating", worldObj);
    }

    @Override
    public double roperYOffset()
    {
        if (this.getIsAdult())
        {
            return 0D;
        }
        else
        {
            return (double) ((130 - getEdad()) * 0.01D);
        }

    }

    @Override
    public boolean isMyFavoriteFood(ItemStack par1ItemStack)
    {
        return this.getType() == 3 && par1ItemStack != null && par1ItemStack.itemID == Item.reed.itemID;
    }

    @Override
    public boolean isMyHealFood(ItemStack par1ItemStack)
    {
        return this.getType() == 3 && par1ItemStack != null && par1ItemStack.itemID == Item.reed.itemID;
    }

    @Override
    public int nameYOffset()
    {
        if (getIsAdult())
        {
            return (-55);
        }
        return (int) ((100/getEdad()) * (-40));

    }
    
    @Override
    public double getCustomSpeed()
    {
        if (getBearState() == 2) 
        {
            return 0D;
        }
        return super.getCustomSpeed();
    }
}