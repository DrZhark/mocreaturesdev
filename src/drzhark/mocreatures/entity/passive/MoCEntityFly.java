package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityInsect;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MoCEntityFly extends MoCEntityInsect
{
    public MoCEntityFly(World world)
    {
        super(world);
        //setSize(0.2F, 0.2F);
        //health = 2;
        texture = MoCreatures.proxy.MODEL_TEXTURE + "fly.png";
    }

    private int soundCount;// = 50;

    /*@Override
    public boolean isFlyer()
    {
        return true;
    }
    
    @Override
    public boolean isFlyerInsect()
    {
        return getIsFlying();
    }*/

    //private int textCounter;

    /**
     * Overridden for the dynamic nightmare texture.
     */
    /*
    public String getTexture()
    {
            //if (rand.nextInt(2)== 0) textCounter++;
            textCounter++;
            if (textCounter < 10) textCounter = 10;
            if (textCounter > 29) textCounter = 10;
            String textNumber = "" + textCounter;
            textNumber = textNumber.substring(0,1);
            return MoCreatures.proxy.MODEL_TEXTURE + "fly" + textNumber + ".png";

    }*/

    /*@Override
    protected void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(22, Byte.valueOf((byte) 0)); // isRideable - 0 false 1 true
    }
    
    public boolean getIsFlying()
    {
        return (dataWatcher.getWatchableObjectByte(22) == 1);
    }
    
    public void setIsFlying(boolean flag)
    {
        if (worldObj.isRemote)
            return;
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(22, Byte.valueOf(input));
    }*/

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (MoCreatures.isServer())
        {

            if (getIsFlying() && rand.nextInt(200) == 0)
            {
                setIsFlying(false);
            }

            EntityPlayer ep = worldObj.getClosestPlayerToEntity(this, 5D);
            if (ep != null && getIsFlying() && --soundCount == -1)
            {
                MoCTools.playCustomSound(this, "fly", this.worldObj);
                soundCount = 55;
            }

            //TODO
            //not working this causes entities to attack it and creepers to blow!
            /*if (entityToAttack == null && worldObj.rand.nextInt(100) == 0)
            {

                List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(12D, 8D, 12D));
                for (int i = 0; i < list.size(); i++)
                {
                    Entity entity = (Entity) list.get(i);
                    if (!(entity instanceof EntityMob))
                    {
                        continue;
                    }
                    EntityMob entitymob = (EntityMob) entity;
                    entitymob.setAttackTarget(this);
                    if (entitymob instanceof EntityZombie)
                    {
                        this.entityToAttack = entitymob;
                    }
                    if (entitymob instanceof MoCEntityHorseMob)
                    {
                        if (((MoCEntityHorseMob) entitymob).getType() == 23)
                        {
                            ;
                        }
                        {
                            this.entityToAttack = entitymob;
                        }
                        //((MoCEntityOgre) entitymob).attackTime = 20;
                    }
                }

            }*/

        }
    }

    @Override
    protected float getFlyingSpeed()
    {
        return 0.7F;
    }

    @Override
    protected float getWalkingSpeed()
    {
        return 0.3F;
    }

    @Override
    public boolean getCanSpawnHere()
    {
        return (MoCreatures.proxy.getFrequency(this.getEntityName()) > 0) && super.getCanSpawnHere();
    }

    @Override
    public boolean isMyFavoriteFood(ItemStack par1ItemStack)
    {
        return par1ItemStack != null && par1ItemStack.itemID == Item.rottenFlesh.itemID;
    }
}
