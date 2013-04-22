package drzhark.mocreatures.entity.passive;

import java.util.List;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAnimal;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class MoCEntityBunny extends MoCEntityAnimal {
    public boolean pickedUp;
    public int bunnyReproduceTickerA;
    public int bunnyReproduceTickerB;
    public int bunnyLimit = 12;

    // TODO check that bunnyLimit??

    // public float edad;

    public MoCEntityBunny(World world)
    {
        super(world);
        // a = false;
        setAdult(true);
        setTamed(false);
        setEdad(50);
        //moveSpeed = 1.5F;
        //yOffset = -0.16F;
        setSize(0.4F, 0.4F);
        health = 4;
        bunnyReproduceTickerA = rand.nextInt(64);
        bunnyReproduceTickerB = 0;
        //forceUpdates = true;
        // unused_flag = true;
    }

    /*@Override
    protected boolean canDespawn()
    {
        return !getIsTamed();
    }*/

    @Override
    public float getMoveSpeed()
    {
        return 1.5F;
    }

    @Override
    public void selectType()
    {
        if (getType() == 0)
        {
            int k = rand.nextInt(100);
            if (k <= 20)
            {
                setType(1);
            }
            else if (k <= 40)
            {
                setType(2);
            }
            else if (k <= 60)
            {
                setType(3);
            }
            else if (k <= 80)
            {
                setType(4);
            }
            else
            {
                setType(5);
            }
        }

    }

    @Override
    public String getTexture()
    {

        switch (getType())
        {
        case 1:
            return MoCreatures.proxy.MODEL_TEXTURE + "bunny.png";
        case 2:
            return MoCreatures.proxy.MODEL_TEXTURE + "bunnyb.png";
        case 3:
            return MoCreatures.proxy.MODEL_TEXTURE + "bunnyc.png";
        case 4:
            return MoCreatures.proxy.MODEL_TEXTURE + "bunnyd.png";
        case 5:
            return MoCreatures.proxy.MODEL_TEXTURE + "bunnye.png";

        default:
            return MoCreatures.proxy.MODEL_TEXTURE + "bunny.png";
        }
    }

    @Override
    protected void fall(float f)
    {
    }

    @Override
    public boolean getCanSpawnHere()
    {
        return (MoCreatures.proxy.getFrequency(this.getEntityName()) > 0) && super.getCanSpawnHere();

    }

    @Override
    protected String getDeathSound()
    {
        return "rabbitdeath";
    }

    @Override
    protected String getHurtSound()
    {
        return "rabbithurt";
    }

    @Override
    protected String getLivingSound()
    {
        return null;
    }

    @Override
    public double getYOffset()
    {
        if (ridingEntity instanceof EntityPlayer && ridingEntity == MoCreatures.proxy.getPlayer() && !MoCreatures.isServer()) { return (yOffset - 1.3F); }

        if ((ridingEntity instanceof EntityPlayer) && !MoCreatures.isServer())
        {
            return (yOffset + 0.3F);
        }
        else
        {
            return yOffset;
        }
    }

    @Override
    public boolean interact(EntityPlayer entityplayer)
    {
        if (super.interact(entityplayer)) { return false; }

        ItemStack itemstack = entityplayer.inventory.getCurrentItem();

        rotationYaw = entityplayer.rotationYaw;
        if (this.ridingEntity == null)
        {
            // This is required since the server will send a Packet39AttachEntity which informs the client to mount
            if (MoCreatures.isServer())
            {
                mountEntity(entityplayer);
            }
            pickedUp = true;
            //System.out.println("My entity ID = " + this.entityId + "My name = " + this.getName() + " my owner = " + getOwnerName());
            if (!getIsTamed() && MoCreatures.isServer())
            {
                MoCTools.tameWithName((EntityPlayerMP) entityplayer, this);

            }
        }
        else
        {
            worldObj.playSoundAtEntity(this, "rabbitlift", 1.0F, ((rand.nextFloat() - rand.nextFloat()) * 0.2F) + 1.0F);
            if (MoCreatures.isServer())
            {
                this.mountEntity(null);
            }
            pickedUp = false;
        }

        motionX = entityplayer.motionX * 5D;
        motionY = (entityplayer.motionY / 2D) + 0.5D;
        motionZ = entityplayer.motionZ * 5D;

        return true;
    }

    @Override
    public void onLivingUpdate()
    {

        if (MoCreatures.isServer() && !getIsAdult() && (rand.nextInt(200) == 0))
        {
            setEdad(getEdad() + 1);
            if (getEdad() >= 100)
            {
                setAdult(true);
            }
        }
        super.onLivingUpdate();
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();
        if (MoCreatures.isServer())
        {

            if (!getIsTamed() || !getIsAdult() || (ridingEntity != null) || (worldObj.countEntities(this.getClass()) > bunnyLimit)) { return; }
            if (bunnyReproduceTickerA < 1023)
            {
                bunnyReproduceTickerA++;
            }
            else if (bunnyReproduceTickerB < 127)
            {
                bunnyReproduceTickerB++;
            }
            else
            {
                int k = worldObj.countEntities(this.getClass());
                if (k > bunnyLimit)
                {
                    proceed();
                    return;
                }
                /*
                 * List list =
                 * worldObj.getEntitiesWithinAABBExcludingEntity(this,
                 * boundingBox.expand(16D, 16D, 16D)); for(int l = 0; l <
                 * list.size(); l++) { Entity entity = (Entity) list.get(l);
                 * if(entity instanceof MoCEntityBunny) { k++; } }
                 */

                List list1 = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(4.0D, 4.0D, 4.0D));
                boolean flag = false;
                for (int i1 = 0; i1 < list1.size(); i1++)
                {
                    Entity entity1 = (Entity) list1.get(i1);
                    if (!(entity1 instanceof MoCEntityBunny) || (entity1 == this))
                    {
                        continue;
                    }
                    MoCEntityBunny entitybunny = (MoCEntityBunny) entity1;
                    if ((entitybunny.ridingEntity != null) || (entitybunny.bunnyReproduceTickerA < 1023) || !entitybunny.getIsAdult())
                    {
                        continue;
                    }
                    MoCEntityBunny entitybunny1 = new MoCEntityBunny(worldObj);
                    entitybunny1.setPosition(posX, posY, posZ);
                    entitybunny1.setAdult(false);
                    worldObj.spawnEntityInWorld(entitybunny1);
                    worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, ((rand.nextFloat() - rand.nextFloat()) * 0.2F) + 1.0F);
                    proceed();
                    entitybunny.proceed();
                    flag = true;
                    break;
                }
                /*
                 * if(!flag) { k = rand.nextInt(16); }
                 */

            }
        }
    }

    public void proceed()
    {
        bunnyReproduceTickerB = 0;
        bunnyReproduceTickerA = rand.nextInt(64);
    }

    @Override
    protected void updateEntityActionState()
    {
        if (onGround && ((motionX > 0.05D) || (motionZ > 0.05D) || (motionX < -0.05D) || (motionZ < -0.05D)))
        {
            motionY = 0.45000000000000001D;
        }
        if (!pickedUp)
        {
            super.updateEntityActionState();
        }
        else if (onGround && this.ridingEntity == null)
        {
            pickedUp = false;
            //System.out.println("pickedOff");
            worldObj.playSoundAtEntity(this, "rabbitland", 1.0F, ((rand.nextFloat() - rand.nextFloat()) * 0.2F) + 1.0F);
            List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(12D, 12D, 12D));
            for (int k = 0; k < list.size(); k++)
            {
                Entity entity = (Entity) list.get(k);
                if (entity instanceof EntityMob)
                {
                    EntityMob entitymob = (EntityMob) entity;
                    entitymob.setAttackTarget(this);

                }
            }

        }
    }

    @Override
    public boolean isBreedingItem(ItemStack par1ItemStack)
    {
        return false;//par1ItemStack != null && par1ItemStack.itemID == Item.wheat.itemID;
    }

    @Override
    public boolean updateMount()
    {
        return true;
    }

    @Override
    public boolean forceUpdates()
    {
        return true;
    }

    @Override
    public int nameYOffset()
    {

        return -40;

    }

    @Override
    public int getMaxHealth()
    {
        return 4;
    }

    @Override
    public boolean isMyHealFood(ItemStack par1ItemStack)
    {
        return par1ItemStack != null && par1ItemStack.itemID == Item.carrot.itemID;
    }

    @Override
    public double roperYOffset()
    {
        return 0.9D;
    }
    
    /**
     * So bunny-hats don't suffer damage
     */
    @Override
    public boolean attackEntityFrom(DamageSource damagesource, int i)
    {
        if (this.ridingEntity != null) 
        {
            return false;
        }
        return super.attackEntityFrom(damagesource, i);
    }
}
