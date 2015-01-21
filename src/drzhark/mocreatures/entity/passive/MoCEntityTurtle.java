package drzhark.mocreatures.entity.passive;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;

public class MoCEntityTurtle extends MoCEntityTameableAnimal {
    private boolean isSwinging;
    private boolean twistright;
    private int flopcounter;

    public MoCEntityTurtle(World world)
    {
        super(world);
        setSize(0.6F, 0.4F);
        setAdult(false);
        setEdad(110);
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15.0D);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(22, Byte.valueOf((byte) 0)); // isHiding - 0 false 1 true
        dataWatcher.addObject(23, Byte.valueOf((byte) 0)); // isUpsideDown - 0 false 1 true
    }

    /**
     * Overridden for the dynamic nightmare texture.
     */
    @Override
    public ResourceLocation getTexture()
    {
        String tempText = "turtle.png";

        if (getName().equals("Donatello") || getName().equals("donatello"))
        {
            tempText = "turtled.png";
        }

        if (getName().equals("Leonardo") || getName().equals("leonardo"))
        {
            tempText = "turtlel.png";
        }

        if (getName().equals("Rafael") || getName().equals("rafael") || getName().equals("raphael") || getName().equals("Raphael"))
        {
            tempText = "turtler.png";
        }

        if (getName().equals("Michelangelo") || getName().equals("michelangelo") || getName().equals("Michaelangelo") || getName().equals("michaelangelo"))
        {
            tempText = "turtlem.png";
        }

        return MoCreatures.proxy.getTexture(tempText);
    }

    @Override
    public float getMoveSpeed()
    {
        return 0.3F;
    }

    public boolean getIsHiding()
    {
        return (dataWatcher.getWatchableObjectByte(22) == 1);
    }

    public boolean getIsUpsideDown()
    {
        return (dataWatcher.getWatchableObjectByte(23) == 1);
    }

    public void setIsHiding(boolean flag)
    {
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(22, Byte.valueOf(input));
    }

    public void setIsUpsideDown(boolean flag)
    {
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(23, Byte.valueOf(input));
    }

    @Override
    public double getYOffset()
    {
        // If we are in SMP, do not alter offset on any client other than the player being mounted on
        if (ridingEntity instanceof EntityPlayer && ridingEntity == MoCreatures.proxy.getPlayer() && !MoCreatures.isServer()) { return (yOffset - (1F + (getEdad() * 0.01F / 6))); }
        if ((ridingEntity instanceof EntityPlayer) && !MoCreatures.isServer()) { return (yOffset + 0.3F); }
        return yOffset;
    }

    @Override
    public boolean interact(EntityPlayer entityplayer)
    {
        if (super.interact(entityplayer)) { return false; }
        if (getIsTamed())
        {
            ItemStack itemstack = entityplayer.inventory.getCurrentItem();
            if (getIsUpsideDown())
            {
                flipflop(false);
                return true;
            }

            if (ridingEntity == null)
            {
                rotationYaw = entityplayer.rotationYaw;
                // TODO change sound
                worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, ((rand.nextFloat() - rand.nextFloat()) * 0.2F) + 1.0F);
                if (MoCreatures.isServer())
                {
                    mountEntity(entityplayer);
                }
            }
            else
            {
                if (MoCreatures.isServer())
                {
                    this.mountEntity(null);
                }
                motionX = entityplayer.motionX * 5D;
                motionY = (entityplayer.motionY / 2D) + 0.2D;
                motionZ = entityplayer.motionZ * 5D;
            }
            return true;
        }
        flipflop(!getIsUpsideDown());

        return true;
    }

    @Override
    protected void jump()
    {
        if (isInsideOfMaterial(Material.water))
        {
            motionY = 0.3D;
            if (isSprinting())
            {
                float f = rotationYaw * 0.01745329F;
                motionX -= MathHelper.sin(f) * 0.2F;
                motionZ += MathHelper.cos(f) * 0.2F;
            }
            isAirBorne = true;
        }
    }

    @Override
    public boolean isNotScared()
    {
        return true;
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (worldObj.isRemote)
        {
            if (ridingEntity != null)
            {
                updateEntityActionState();
            }
        }

        if (!worldObj.isRemote)
        {
            if (!getIsUpsideDown() && !getIsTamed())
            {
                EntityLivingBase entityliving = getBoogey(4D);
                if ((entityliving != null) && canEntityBeSeen(entityliving))
                {

                    if (!getIsHiding())
                    {
                        worldObj.playSoundAtEntity(this, "mocreatures:turtlehissing", 1.0F, 1.0F + ((rand.nextFloat() - rand.nextFloat()) * 0.2F));
                        setIsHiding(true);
                    }

                    setPathToEntity(null);
                }
                else
                {

                    setIsHiding(false);
                    if (!hasPath() && rand.nextInt(50) == 0)
                    {
                        EntityItem entityitem = getClosestItem(this, 10D, Items.melon, Items.reeds);
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
                                worldObj.playSoundAtEntity(this, "mocreatures:turtleeating", 1.0F, 1.0F + ((rand.nextFloat() - rand.nextFloat()) * 0.2F));

                                EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, 24D);
                                if (entityplayer != null)
                                {
                                    MoCTools.tameWithName(entityplayer, this);
                                }
                            }
                        }
                    }
                }
            }

            if (!getIsUpsideDown() && getIsTamed() && rand.nextInt(20) == 0)
            {
                EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, 12D);
                if (entityplayer != null)
                {
                    PathEntity pathentity = worldObj.getPathEntityToEntity(this, entityplayer, 16F, true, false, false, true);
                    setPathToEntity(pathentity);
                }
            }
        }
    }

    @Override
    public boolean swimmerEntity()
    {
        return true;
    }

    @Override
    public boolean canBreatheUnderwater()
    {
        return true;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i)
    {

        if (getIsHiding())
        {
            if (rand.nextInt(10) == 0)
            {
                flipflop(true);
            }
            return false;
        }
        else
        {
            boolean flag = super.attackEntityFrom(damagesource, i);
            if (rand.nextInt(3) == 0)
            {
                flipflop(true);
            }
            return flag;
        }
    }

    public void flipflop(boolean flip)
    {
        fleeingTick = 0;
        setIsUpsideDown(flip);
        setIsHiding(false);
        setPathToEntity(null);
    }

    @Override
    protected void updateEntityActionState()
    {
        if ((ridingEntity != null) && (ridingEntity instanceof EntityPlayer))
        {
            EntityPlayer entityplayer = (EntityPlayer) ridingEntity;
            if (entityplayer != null)
            {
                rotationYaw = entityplayer.rotationYaw;
            }
        }
        else
        {
            super.updateEntityActionState();
        }
    }

    @Override
    public boolean entitiesToIgnore(Entity entity)
    {
        return (entity instanceof MoCEntityTurtle) || ((entity.height <= this.height) && (entity.width <= this.width)) || super.entitiesToIgnore(entity);
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if (getIsTamed() && getEdad() < 300 && rand.nextInt(800) == 0)
        {
            setEdad(getEdad() + 1);
        }
        if (getIsUpsideDown() && (ridingEntity == null) && rand.nextInt(20) == 0)
        {
            setSwinging(true);
            flopcounter++;
        }

        if (getIsSwinging())
        {
            swingProgress += 0.2F;

            boolean flag = (flopcounter > (rand.nextInt(3) + 8));

            if (swingProgress > 2.0F && (!flag || rand.nextInt(20) == 0))
            {
                setSwinging(false);
                swingProgress = 0.0F;
                if (rand.nextInt(2) == 0)
                {
                    twistright = !twistright;
                }

            }
            else if (swingProgress > 9.0F && flag)
            {
                setSwinging(false);
                swingProgress = 0.0F;
                // TODO
                worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, 1.0F + ((rand.nextFloat() - rand.nextFloat()) * 0.2F));
                setIsUpsideDown(false);
                flopcounter = 0;
            }
        }
    }

    public boolean getIsSwinging()
    {
        return isSwinging;
    }

    public void setSwinging(boolean flag)
    {
        isSwinging = flag;
    }

    @Override
    protected boolean isMovementCeased()
    {
        return (getIsUpsideDown() || getIsHiding());
    }

    @Override
    public boolean renderName()
    {
        return getDisplayName() && (ridingEntity == null);
    }

    public int getFlipDirection()
    {
        if (twistright) { return 1; }
        return -1;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        setIsUpsideDown(nbttagcompound.getBoolean("UpsideDown"));
        setDisplayName(nbttagcompound.getBoolean("DisplayName"));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("UpsideDown", getIsUpsideDown());
        nbttagcompound.setBoolean("DisplayName", getDisplayName());
    }

    @Override
    protected String getHurtSound()
    {
        return "mocreatures:turtlehurt";
    }

    @Override
    protected String getLivingSound()
    {
        return "mocreatures:turtlegrunt";
    }

    @Override
    protected String getDeathSound()
    {
        return "mocreatures:turtledying";
    }

    @Override
    protected Item getDropItem()
    {
        if (getName().equals("Donatello") || getName().equals("donatello")) { return MoCreatures.bo; }

        if (getName().equals("Leonardo") || getName().equals("leonardo")) { return MoCreatures.katana; }

        if (getName().equals("Rafael") || getName().equals("rafael") || getName().equals("raphael") || getName().equals("Raphael")) { return MoCreatures.sai; }

        if (getName().equals("Michelangelo") || getName().equals("michelangelo") || getName().equals("Michaelangelo") || getName().equals("michaelangelo")) { return MoCreatures.nunchaku; }
        return MoCreatures.turtleraw;
    }

    /**
     * Used to avoid rendering the top shell cube
     * 
     * @return
     */
    public boolean isTMNT()
    {
        if (getName().equals("Donatello") || getName().equals("donatello") || getName().equals("Leonardo") || getName().equals("leonardo") || getName().equals("Rafael") || getName().equals("rafael") || getName().equals("raphael") || getName().equals("Raphael") || getName().equals("Michelangelo") || getName().equals("michelangelo") || getName().equals("Michaelangelo") || getName()
                .equals("michaelangelo")) { return true; }
        return false;
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
        return par1ItemStack != null && (par1ItemStack.getItem() == Items.reeds || par1ItemStack.getItem() == Items.melon);
    }

    @Override
    public int getMaxSpawnedInChunk()
    {
        return 2;
    }
}