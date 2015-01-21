package drzhark.mocreatures.entity.passive;

import java.util.List;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
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
import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.item.MoCEntityKittyBed;
import drzhark.mocreatures.entity.item.MoCEntityLitterBox;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;

public class MoCEntityKitty extends MoCEntityTameableAnimal {

    private int kittytimer;
    private int madtimer;
    private boolean foundTree;
    private final int treeCoord[] = { -1, -1, -1 };
    private int displaycount;

    private boolean isSwinging;
    private boolean onTree;

    public MoCEntityKitty(World world)
    {
        super(world);
        setSize(0.7F, 0.5F);
        setAdult(true);
        setEdad(40);
        setKittyState(1);
        kittytimer = 0;
        //health = 15;
        madtimer = rand.nextInt(5);

        foundTree = false;
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15.0D);
    }

    @Override
    public void selectType()
    {
        if (getType() == 0)
        {
            setType(rand.nextInt(8)+1);
        }
    }

    @Override
    public ResourceLocation getTexture()
    {

        switch (getType())
        {
        case 1:
            return MoCreatures.proxy.getTexture("pussycata.png");
        case 2:
            return MoCreatures.proxy.getTexture("pussycatb.png");
        case 3:
            return MoCreatures.proxy.getTexture("pussycatc.png");
        case 4:
            return MoCreatures.proxy.getTexture("pussycatd.png");
        case 5:
            return MoCreatures.proxy.getTexture("pussycate.png");
        case 6:
            return MoCreatures.proxy.getTexture("pussycatf.png");
        case 7:
            return MoCreatures.proxy.getTexture("pussycatg.png");
        case 8:
            return MoCreatures.proxy.getTexture("pussycath.png");

        default:
            return MoCreatures.proxy.getTexture("pussycata.png");
        }
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(22, Integer.valueOf(0)); // kittenstate int
        dataWatcher.addObject(23, Byte.valueOf((byte) 0)); // isSitting - 0 false 1 true
        dataWatcher.addObject(24, Byte.valueOf((byte) 0)); // isHungry - 0 false 1 true
        dataWatcher.addObject(25, Byte.valueOf((byte) 0)); // isEmo - 0 false 1 true
    }

    public int getKittyState()
    {
        return dataWatcher.getWatchableObjectInt(22);
    }

    public boolean getIsSitting()
    {
        return (dataWatcher.getWatchableObjectByte(23) == 1);
    }

    public boolean getIsHungry()
    {
        return (dataWatcher.getWatchableObjectByte(24) == 1);
    }

    public boolean getIsEmo()
    {
        return (dataWatcher.getWatchableObjectByte(25) == 1);
    }

    public boolean getIsSwinging()
    {
        return isSwinging;
    }

    public boolean getOnTree()
    {
        return onTree;
    }

    public void setKittyState(int i)
    {
        dataWatcher.updateObject(22, Integer.valueOf(i));
    }

    public void setSitting(boolean flag)
    {
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(23, Byte.valueOf(input));
    }

    public void setHungry(boolean flag)
    {
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(24, Byte.valueOf(input));
    }

    public void setIsEmo(boolean flag)
    {
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(25, Byte.valueOf(input));
    }

    public void setOnTree(boolean var1)
    {
        onTree = var1;
    }

    public void setSwinging(boolean var1)
    {
        isSwinging = var1;
    }

    @Override
    protected void attackEntity(Entity entity, float f)
    {

        if ((f > 2.0F) && (f < 6F) && (rand.nextInt(30) == 0) && onGround)
        {
            double d = entity.posX - posX;
            double d1 = entity.posZ - posZ;
            float f1 = MathHelper.sqrt_double((d * d) + (d1 * d1));
            motionX = ((d / f1) * 0.5D * 0.8D) + (motionX * 0.2D);
            motionZ = ((d1 / f1) * 0.5D * 0.8D) + (motionZ * 0.2D);
            motionY = 0.4D;
        }
        if ((f < 2D) && (entity.boundingBox.maxY > boundingBox.minY) && (entity.boundingBox.minY < boundingBox.maxY))
        {
            attackTime = 20;
            if ((getKittyState() != 18) && (getKittyState() != 10))
            {
                swingArm();
            }
            if (((getKittyState() == 13) && (entity instanceof EntityPlayer)) || ((getKittyState() == 8) && (entity instanceof EntityItem)) || ((getKittyState() == 18) && (entity instanceof MoCEntityKitty)) || (getKittyState() == 10)) { return; }

            entity.attackEntityFrom(DamageSource.causeMobDamage(this), 1);
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i)
    {
        if (super.attackEntityFrom(damagesource, i))
        {
            Entity entity = damagesource.getEntity();
            if (entity != this)
            {
                if (getKittyState() == 10)
                {
                    List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(16D, 6D, 16D));
                    for (int j = 0; j < list.size(); j++)
                    {
                        Entity entity1 = (Entity) list.get(j);
                        if ((entity1 instanceof MoCEntityKitty) && (((MoCEntityKitty) entity1).getKittyState() == 21))
                        {
                            ((MoCEntityKitty) entity1).entityToAttack = entity;
                            return true;
                        }
                    }

                    return true;
                }
                if (entity instanceof EntityPlayer)
                {
                    if (getKittyState() < 2)
                    {
                        entityToAttack = entity;
                        setKittyState(-1);
                    }
                    if ((getKittyState() == 19) || (getKittyState() == 20) || (getKittyState() == 21))
                    {
                        entityToAttack = entity;
                        setSitting(false);
                        return true;
                    }
                    if ((getKittyState() > 1) && (getKittyState() != 10) && (getKittyState() != 19) && (getKittyState() != 20) && (getKittyState() != 21))
                    {
                        setKittyState(13);
                        setSitting(false);
                    }
                    return true;
                }
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
    protected boolean canDespawn()
    {
        if (MoCreatures.proxy.forceDespawns)
            return getKittyState() < 3;
        else return false;
    }

    private void changeKittyState(int i)
    {
        setKittyState(i);
        if (MoCreatures.isServer())
        {
            mountEntity(null);
        }
        setSitting(false);
        kittytimer = 0;
        setOnTree(false);
        foundTree = false;
        entityToAttack = null;
    }

    public boolean climbingTree()
    {
        return (getKittyState() == 16) && isOnLadder();
    }

    @Override
    protected void fall(float f)
    {
    }

    @Override
    protected Entity findPlayerToAttack()
    {
        if ((worldObj.difficultySetting.getDifficultyId() > 0) && (getKittyState() != 8) && (getKittyState() != 10) && (getKittyState() != 15) && (getKittyState() != 18) && (getKittyState() != 19) && !isMovementCeased() && getIsHungry())
        {
            EntityLivingBase entityliving = getClosestTarget(this, 10D);
            return entityliving;
        }
        else
        {
            return null;
        }
    }

    //TODO
    //change this so MoCAnimal getBoogey is used instead to decrease duplication of code
    public EntityLivingBase getBoogey(double d, boolean flag)
    {
        double d1 = -1D;
        EntityLivingBase entityliving = null;
        List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(d, 4D, d));
        for (int i = 0; i < list.size(); i++)
        {
            Entity entity = (Entity) list.get(i);
            if ((entity instanceof EntityLivingBase) && !(entity instanceof MoCEntityDeer) && !(entity instanceof MoCEntityHorse) && ((entity.width >= 0.5D) || (entity.height >= 0.5D)) && (flag || !(entity instanceof EntityPlayer)))
            {
                entityliving = (EntityLivingBase) entity;
            }
        }

        return entityliving;
    }

    //TODO use MoCAnimal instead
    public EntityLiving getClosestTarget(Entity entity, double d)
    {
        double d1 = -1D;
        EntityLiving entityliving = null;
        List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(d, d, d));
        for (int i = 0; i < list.size(); i++)
        {
            Entity entity1 = (Entity) list.get(i);
            if (!(entity1 instanceof EntityLiving) || (entity1 instanceof MoCEntityKitty) || (entity1 instanceof EntityPlayer) || (entity1 instanceof EntityMob) || (entity1 instanceof MoCEntityKittyBed) || (entity1 instanceof MoCEntityLitterBox) || ((entity1.width > 0.5D) && (entity1.height > 0.5D)) || ((entity instanceof IMoCEntity) && !MoCreatures.isHuntingEnabled()))
            {
                continue;
            }
            double d2 = entity1.getDistanceSq(entity.posX, entity.posY, entity.posZ);
            if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1)) && ((EntityLiving) entity1).canEntityBeSeen(entity))
            {
                d1 = d2;
                entityliving = (EntityLiving) entity1;
            }
        }

        return entityliving;
    }

    @Override
    protected String getDeathSound()
    {
        if (getKittyState() == 10)
        {
            return "mocreatures:kittendying";
        }
        else
        {
            return "mocreatures:kittydying";
        }
    }

    @Override
    protected Item getDropItem()
    {
        return null;
    }

    public ResourceLocation getEmoteIcon()
    {
        switch (getKittyState())
        {
        case -1:
            return MoCreatures.proxy.getTexture("emoticon2.png");

        case 3: // '\003'
            return MoCreatures.proxy.getTexture("emoticon3.png");

        case 4: // '\004'
            return MoCreatures.proxy.getTexture("emoticon4.png");

        case 5: // '\005'
            return MoCreatures.proxy.getTexture("emoticon5.png");

        case 7: // '\007'
            return MoCreatures.proxy.getTexture("emoticon7.png");

        case 8: // '\b'
            return MoCreatures.proxy.getTexture("emoticon8.png");

        case 9: // '\t'
            return MoCreatures.proxy.getTexture("emoticon9.png");

        case 10: // '\n'
            return MoCreatures.proxy.getTexture("emoticon10.png");

        case 11: // '\013'
            return MoCreatures.proxy.getTexture("emoticon11.png");

        case 12: // '\f'
            return MoCreatures.proxy.getTexture("emoticon12.png");

        case 13: // '\r'
            return MoCreatures.proxy.getTexture("emoticon13.png");

        case 16: // '\020'
            return MoCreatures.proxy.getTexture("emoticon16.png");

        case 17: // '\021'
            return MoCreatures.proxy.getTexture("emoticon17.png");

        case 18: // '\022'
            return MoCreatures.proxy.getTexture("emoticon9.png");

        case 19: // '\023'
            return MoCreatures.proxy.getTexture("emoticon19.png");

        case 20: // '\024'
            return MoCreatures.proxy.getTexture("emoticon19.png");

        case 21: // '\025'
            return MoCreatures.proxy.getTexture("emoticon10.png");

        case 0: // '\0'
        case 1: // '\001'
        case 2: // '\002'
        case 6: // '\006'
        case 14: // '\016'
        case 15: // '\017'
        default:
            return MoCreatures.proxy.getTexture("emoticon1.png");
        }
    }

    @Override
    protected String getHurtSound()
    {
        if (getKittyState() == 10)
        {
            return "mocreatures:kittenhurt";
        }
        else
        {
            return "mocreatures:kittyhurt";
        }
    }

    public EntityLiving getKittyStuff(Entity entity, double d, boolean flag)
    {
        double d1 = -1D;
        Object obj = null;
        List list = worldObj.getEntitiesWithinAABBExcludingEntity(entity, boundingBox.expand(d, d, d));
        for (int i = 0; i < list.size(); i++)
        {
            Entity entity1 = (Entity) list.get(i);
            if (flag)
            {
                if (!(entity1 instanceof MoCEntityLitterBox))
                {
                    continue;
                }
                MoCEntityLitterBox entitylitterbox = (MoCEntityLitterBox) entity1;
                if (entitylitterbox.getUsedLitter())
                {
                    continue;
                }
                double d2 = entity1.getDistanceSq(entity.posX, entity.posY, entity.posZ);
                if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1.0D) || (d2 < d1)) && entitylitterbox.canEntityBeSeen(entity))
                {
                    d1 = d2;
                    obj = entitylitterbox;
                }
                continue;
            }
            if (!(entity1 instanceof MoCEntityKittyBed))
            {
                continue;
            }
            MoCEntityKittyBed entitykittybed = (MoCEntityKittyBed) entity1;
            double d3 = entity1.getDistanceSq(entity.posX, entity.posY, entity.posZ);
            if (((d < 0.0D) || (d3 < (d * d))) && ((d1 == -1.0D) || (d3 < d1)) && entitykittybed.canEntityBeSeen(entity))
            {
                d1 = d3;
                obj = entitykittybed;
            }
        }

        return ((EntityLiving) (obj));
    }

    @Override
    protected String getLivingSound()
    {
        if (getKittyState() == 4)
        {
            if (ridingEntity != null)
            {
                MoCEntityKittyBed entitykittybed = (MoCEntityKittyBed) ridingEntity;
                if ((entitykittybed != null) && !entitykittybed.getHasMilk()) { return "mocreatures:kittyeatingm"; }
                if ((entitykittybed != null) && !entitykittybed.getHasFood()) { return "mocreatures:kittyeatingf"; }
            }
            return null;
        }
        if (getKittyState() == 6) { return "mocreatures:kittylitter"; }
        if (getKittyState() == 3) { return "mocreatures:kittyfood"; }
        if (getKittyState() == 10) { return "mocreatures:kittengrunt"; }
        if (getKittyState() == 13) { return "mocreatures:kittyupset"; }
        if (getKittyState() == 17) { return "mocreatures:kittytrapped"; }
        if ((getKittyState() == 18) || (getKittyState() == 12))
        {
            return "mocreatures:kittypurr";
        }
        else
        {
            return "mocreatures:kittygrunt";
        }
    }

    @Override
    public double getYOffset()
    {
        if (ridingEntity instanceof EntityPlayer && ridingEntity == MoCreatures.proxy.getPlayer() && !MoCreatures.isServer())
        {
            if (getKittyState() == 10) { return (yOffset - 1.1F); }
            if (upsideDown()) { return (yOffset - 1.7F); }
            if (onMaBack()) { return (yOffset - 1.5F); }
        }

        if ((ridingEntity instanceof EntityPlayer) && !MoCreatures.isServer())
        {
            if (getKittyState() == 10) { return (yOffset + 0.3F); }
            if (upsideDown()) { return (yOffset - 0.1F); }
            if (onMaBack()) { return (yOffset + 0.1F); }
        }

        return yOffset;
    }

    @Override
    public boolean interact(EntityPlayer entityplayer)
    {
        if (super.interact(entityplayer)) { return false; }
        //Ownership code
        //if (MoCreatures.proxy.enableOwnership && getOwnerName() != null && !getOwnerName().equals("") && !entityplayer.getCommandSenderName().equals(getOwnerName())) { return true; }

        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if ((getKittyState() == 2) && (itemstack != null) && (itemstack.getItem() == MoCreatures.medallion))
        {
            if (MoCreatures.isServer())
            {
                MoCTools.tameWithName(entityplayer, this);
            }
            if (getIsTamed() && --itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            if (getIsTamed())
            {
                changeKittyState(3);
                this.setHealth(getMaxHealth());
                return true;
            }
            return false;
        }
        if ((getKittyState() == 7) && (itemstack != null) && ((itemstack.getItem() == Items.cake) || (itemstack.getItem() == Items.fish) || (itemstack.getItem() == Items.cooked_fished)))
        {
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            worldObj.playSoundAtEntity(this, "mocreatures:kittyeatingf", 1.0F, 1.0F + ((rand.nextFloat() - rand.nextFloat()) * 0.2F));
            this.setHealth(getMaxHealth());
            changeKittyState(9);
            return true;
        }
        if ((getKittyState() == 11) && (itemstack != null) && (itemstack.getItem() == MoCreatures.woolball) && MoCreatures.isServer())
        {
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            setKittyState(8);
            EntityItem entityitem = new EntityItem(worldObj, posX, posY + 1.0D, posZ, new ItemStack(MoCreatures.woolball, 1));
            entityitem.delayBeforeCanPickup = 30;
            entityitem.age = -10000;
            worldObj.spawnEntityInWorld(entityitem);
            entityitem.motionY += worldObj.rand.nextFloat() * 0.05F;
            entityitem.motionX += (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.3F;
            entityitem.motionZ += (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.3F;
            entityToAttack = entityitem;
            return true;
        }
        if ((getKittyState() == 13) && (itemstack != null) && ((itemstack.getItem() == Items.fish) || (itemstack.getItem() == Items.cooked_fished)))
        {
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            worldObj.playSoundAtEntity(this, "mocreatures:kittyeatingf", 1.0F, 1.0F + ((rand.nextFloat() - rand.nextFloat()) * 0.2F));
            this.setHealth(getMaxHealth());
            changeKittyState(7);
            return true;
        }
        if ((itemstack != null) && (getKittyState() > 2) && ((itemstack.getItem() == MoCreatures.medallion) || (itemstack.getItem() == Items.book)))
        {
            if (MoCreatures.isServer())
            {
                MoCTools.tameWithName((EntityPlayerMP) entityplayer, this);
            }

            return true;
        }
        if ((itemstack != null) && (getKittyState() > 2) && pickable() && (itemstack.getItem() == Items.lead))
        {
            changeKittyState(14);
            if (MoCreatures.isServer())
            {
                mountEntity(entityplayer);
            }
            return true;
        }
        if ((itemstack != null) && (getKittyState() > 2) && whipeable() && (itemstack.getItem() == MoCreatures.whip))
        {
            setSitting(!getIsSitting());
            return true;
        }
        if ((itemstack == null) && (getKittyState() == 10) && (ridingEntity != null))
        {
            ridingEntity = null;
            return true;
        }
        if ((itemstack == null) && (getKittyState() > 2) && pickable())
        {
            changeKittyState(15);
            if (MoCreatures.isServer())
            {
                mountEntity(entityplayer);
            }
            return true;
        }
        if ((itemstack == null) && (getKittyState() == 15))
        {
            changeKittyState(7);
            return true;
        }
        if ((getKittyState() == 14) && ridingEntity != null)
        {
            changeKittyState(7);
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    protected boolean isMovementCeased()
    {
        return getIsSitting() || (getKittyState() == 6) || ((getKittyState() == 16) && getOnTree()) || (getKittyState() == 12) || (getKittyState() == 17) || (getKittyState() == 14) || (getKittyState() == 20) || (getKittyState() == 23);
    }

    @Override
    public boolean isOnLadder()
    {
        if (getKittyState() == 16)
        {
            return isCollidedHorizontally && getOnTree();
        }
        else
        {
            return super.isOnLadder();
        }
    }

    @Override
    public void onLivingUpdate()
    {
        if (MoCreatures.isServer())
        {
            if (!getIsAdult() && (getKittyState() != 10))
            {
                setKittyState(10);
            }
            if (getKittyState() != 12)
            {
                super.onLivingUpdate();
            }
            if (rand.nextInt(200) == 0)
            {
                setIsEmo(!getIsEmo());
            }
            if (!getIsAdult() && (rand.nextInt(200) == 0))
            {
                setEdad(getEdad() + 1);
                if (getEdad() >= 100)
                {
                    setAdult(true);
                }
            }
            if (!getIsHungry() && !getIsSitting() && (rand.nextInt(100) == 0))
            {
                setHungry(true);
            }

            label0: switch (getKittyState())
            {
            case -1:
                break;
            case 23: // '\027'
                break;

            case 1: // '\001'
                if (rand.nextInt(10) == 0)
                {
                    EntityLivingBase entityliving = getBoogey(6D, true);
                    if (entityliving != null)
                    {
                        MoCTools.runLikeHell(this, entityliving);
                    }
                    break;
                }
                if (!getIsHungry() || (rand.nextInt(10) != 0))
                {
                    break;
                }
                EntityItem entityitem = getClosestItem(this, 10D, Items.cooked_fished, Items.cooked_fished);
                if (entityitem == null)
                {
                    break;
                }
                float f = entityitem.getDistanceToEntity(this);
                if (f > 2.0F)
                {
                    getMyOwnPath(entityitem, f);
                }
                if ((f < 2.0F) && (entityitem != null) && (deathTime == 0))
                {
                    entityitem.setDead();
                    worldObj.playSoundAtEntity(this, "mocreatures:kittyeatingf", 1.0F, 1.0F + ((rand.nextFloat() - rand.nextFloat()) * 0.2F));
                    setHungry(false);
                    setKittyState(2);
                }
                break;

            case 2: // '\002'
                EntityLivingBase entityliving1 = getBoogey(6D, false);
                if (entityliving1 != null)
                {
                    MoCTools.runLikeHell(this, entityliving1);
                }
                break;

            case 3: // '\003'
                kittytimer++;
                if (kittytimer > 500)
                {
                    if (rand.nextInt(200) == 0)
                    {
                        changeKittyState(13);
                        break;
                    }
                    if (rand.nextInt(500) == 0)
                    {
                        changeKittyState(7);
                        break;
                    }
                }
                if (rand.nextInt(20) != 0)
                {
                    break;
                }
                MoCEntityKittyBed entitykittybed = (MoCEntityKittyBed) getKittyStuff(this, 18D, false);
                if ((entitykittybed == null) || (entitykittybed.riddenByEntity != null) || (!entitykittybed.getHasMilk() && !entitykittybed.getHasFood()))
                {
                    break;
                }
                float f5 = entitykittybed.getDistanceToEntity(this);
                if (f5 > 2.0F)
                {
                    getMyOwnPath(entitykittybed, f5);
                }
                if (f5 < 2.0F)
                {
                    changeKittyState(4);
                    mountEntity(entitykittybed);
                    setSitting(true);
                }
                break;

            case 4: // '\004'
                if (ridingEntity != null)
                {
                    MoCEntityKittyBed entitykittybed1 = (MoCEntityKittyBed) ridingEntity;
                    if ((entitykittybed1 != null) && !entitykittybed1.getHasMilk() && !entitykittybed1.getHasFood())
                    {
                        this.setHealth(getMaxHealth());
                        changeKittyState(5);
                    }
                }
                else
                {
                    this.setHealth(getMaxHealth());
                    changeKittyState(5);
                }
                if (rand.nextInt(2500) == 0)
                {
                    this.setHealth(getMaxHealth());
                    changeKittyState(7);
                }
                break;

            case 5: // '\005'
                kittytimer++;
                if ((kittytimer > 2000) && (rand.nextInt(1000) == 0))
                {
                    changeKittyState(13);
                    break;
                }
                if (rand.nextInt(20) != 0)
                {
                    break;
                }
                MoCEntityLitterBox entitylitterbox = (MoCEntityLitterBox) getKittyStuff(this, 18D, true);
                if ((entitylitterbox == null) || (entitylitterbox.riddenByEntity != null) || entitylitterbox.getUsedLitter())
                {
                    break;
                }
                float f6 = entitylitterbox.getDistanceToEntity(this);
                if (f6 > 2.0F)
                {
                    getMyOwnPath(entitylitterbox, f6);
                }
                if (f6 < 2.0F)
                {
                    changeKittyState(6);
                    mountEntity(entitylitterbox);
                }
                break;

            case 6: // '\006'
                kittytimer++;
                if (kittytimer <= 300)
                {
                    break;
                }
                worldObj.playSoundAtEntity(this, "mocreatures:kittypoo", 1.0F, 1.0F + ((rand.nextFloat() - rand.nextFloat()) * 0.2F));
                MoCEntityLitterBox entitylitterbox1 = (MoCEntityLitterBox) ridingEntity;
                if (entitylitterbox1 != null)
                {
                    entitylitterbox1.setUsedLitter(true);
                    entitylitterbox1.littertime = 0;
                }
                changeKittyState(7);
                break;

            case 7: // '\007'
                if (getIsSitting())
                {
                    break;
                }
                if (rand.nextInt(20) == 0)
                {
                    EntityPlayer entityplayer = worldObj.getClosestPlayerToEntity(this, 12D);
                    if (entityplayer != null)
                    {
                        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
                        if ((itemstack != null) && (itemstack.getItem() == MoCreatures.woolball))
                        {
                            changeKittyState(11);
                            break;
                        }
                    }
                }
                if (inWater && (rand.nextInt(500) == 0))
                {
                    changeKittyState(13);
                    break;
                }
                if ((rand.nextInt(500) == 0) && !worldObj.isDaytime())
                {
                    changeKittyState(12);
                    break;
                }
                if (rand.nextInt(2000) == 0)
                {
                    changeKittyState(3);
                    break;
                }
                if (rand.nextInt(4000) == 0)
                {
                    changeKittyState(16);
                }
                break;

            case 8: // '\b'
                if (inWater && rand.nextInt(200) == 0)
                {
                    changeKittyState(13);
                    break;
                }
                if ((entityToAttack != null) && (entityToAttack instanceof EntityItem))
                {
                    float f1 = getDistanceToEntity(entityToAttack);
                    if (f1 < 1.5F)
                    {
                        swingArm();
                        if (rand.nextInt(10) == 0)
                        {
                            //float force = 0.3F;
                            //if (type == 10) force = 0.2F;
                            MoCTools.bigsmack(this, entityToAttack, 0.3F);
                            //kittySmack(this, entityToAttack);
                        }
                    }
                }
                if ((entityToAttack == null) || (rand.nextInt(1000) == 0))
                {
                    changeKittyState(7);
                }
                break;

            case 9: // looking for mate
                kittytimer++;
                if (rand.nextInt(50) == 0)
                {
                    List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(16D, 6D, 16D));
                    int j = 0;
                    do
                    {
                        if (j >= list.size())
                        {
                            break;
                        }
                        Entity entity = (Entity) list.get(j);
                        if ((entity instanceof MoCEntityKitty) && (entity instanceof MoCEntityKitty) && (((MoCEntityKitty) entity).getKittyState() == 9))
                        {
                            changeKittyState(18);
                            entityToAttack = entity;
                            ((MoCEntityKitty) entity).changeKittyState(18);
                            ((MoCEntityKitty) entity).entityToAttack = this;
                            break;
                        }
                        j++;
                    } while (true);
                }
                if (kittytimer > 2000)
                {
                    changeKittyState(7);
                }
                break;

            case 10: // '\n'
                if (getIsAdult())
                {
                    changeKittyState(7);
                    break;
                }
                if (rand.nextInt(50) == 0)
                {
                    List list1 = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(16D, 6D, 16D));
                    for (int k = 0; k < list1.size(); k++)
                    {
                        Entity entity1 = (Entity) list1.get(k);
                        if (!(entity1 instanceof MoCEntityKitty) || (((MoCEntityKitty) entity1).getKittyState() != 21))
                        {
                            continue;
                        }
                        float f9 = getDistanceToEntity(entity1);
                        if (f9 > 12F)
                        {
                            entityToAttack = entity1;
                        }
                    }

                }
                if ((entityToAttack == null) && (rand.nextInt(100) == 0))
                {
                    int i = rand.nextInt(10);
                    if (i < 7)
                    {
                        entityToAttack = getClosestItem(this, 10D, null, null);
                    }
                    else
                    {
                        entityToAttack = worldObj.getClosestPlayerToEntity(this, 18D);
                    }
                }
                if ((entityToAttack != null) && (rand.nextInt(400) == 0))
                {
                    entityToAttack = null;
                }
                if ((entityToAttack != null) && (entityToAttack instanceof EntityItem))
                {
                    float f2 = getDistanceToEntity(entityToAttack);
                    if (f2 < 1.5F)
                    {
                        swingArm();
                        if (rand.nextInt(10) == 0)
                        {
                            MoCTools.bigsmack(this, entityToAttack, 0.2F);
                            //kittySmack(this, entityToAttack);
                        }
                    }
                }
                if ((entityToAttack != null) && (entityToAttack instanceof MoCEntityKitty) && (rand.nextInt(20) == 0))
                {
                    float f3 = getDistanceToEntity(entityToAttack);
                    if (f3 < 2.0F)
                    {
                        swingArm();
                        setPathToEntity(null);
                    }
                }
                if ((entityToAttack == null) || !(entityToAttack instanceof EntityPlayer))
                {
                    break;
                }
                float f4 = getDistanceToEntity(entityToAttack);
                if ((f4 < 2.0F) && (rand.nextInt(20) == 0))
                {
                    swingArm();
                }
                break;

            case 11: // '\013'
                EntityPlayer entityplayer1 = worldObj.getClosestPlayerToEntity(this, 18D);
                if ((entityplayer1 == null) || (rand.nextInt(10) != 0))
                {
                    break;
                }
                ItemStack itemstack1 = entityplayer1.inventory.getCurrentItem();
                if ((itemstack1 == null) || ((itemstack1 != null) && (itemstack1.getItem() != MoCreatures.woolball)))
                {
                    changeKittyState(7);
                    break;
                }
                float f8 = entityplayer1.getDistanceToEntity(this);
                if (f8 > 5F)
                {
                    getPathOrWalkableBlock(entityplayer1, f8);
                }
                break;

            case 12: // '\f'
                kittytimer++;
                if (worldObj.isDaytime() || ((kittytimer > 500) && (rand.nextInt(500) == 0)))
                {
                    changeKittyState(7);
                    break;
                }
                setSitting(true);
                if ((rand.nextInt(80) == 0) || !onGround)
                {
                    super.onLivingUpdate();
                }
                break;

            case 13: // '\r'
                setHungry(false);
                entityToAttack = worldObj.getClosestPlayerToEntity(this, 18D);
                if (entityToAttack != null)
                {
                    float f7 = getDistanceToEntity(entityToAttack);
                    if (f7 < 1.5F)
                    {
                        swingArm();
                        if (rand.nextInt(20) == 0)
                        {
                            madtimer--;
                            entityToAttack.attackEntityFrom(DamageSource.causeMobDamage(this), 1);
                            if (madtimer < 1)
                            {
                                changeKittyState(7);
                                madtimer = rand.nextInt(5);
                            }
                        }
                    }
                    if (rand.nextInt(500) == 0)
                    {
                        changeKittyState(7);
                    }
                }
                else
                {
                    changeKittyState(7);
                }
                break;

            case 14: // held by rope
                if (onGround)
                {
                    changeKittyState(13);
                    break;
                }
                if (rand.nextInt(50) == 0)
                {
                    swingArm();
                }
                if (ridingEntity == null)
                {
                    break;
                }
                rotationYaw = ridingEntity.rotationYaw + 90F;
                EntityPlayer entityplayer2 = (EntityPlayer) ridingEntity;
                if (entityplayer2 == null)
                {
                    changeKittyState(13);
                    break;
                }
                ItemStack itemstack2 = entityplayer2.inventory.getCurrentItem();
                if (itemstack2 == null || ((itemstack2 != null) && (itemstack2.getItem() != Items.lead)))
                {
                    changeKittyState(13);
                }
                break;

            case 15: // '\017'
                if (onGround)
                {
                    changeKittyState(7);
                }
                if (ridingEntity != null)
                {
                    rotationYaw = ridingEntity.rotationYaw + 90F;
                }
                break;

            case 16: // '\020'
                kittytimer++;
                if ((kittytimer > 500) && !getOnTree())
                {
                    changeKittyState(7);
                }
                if (!getOnTree())
                {
                    if (!foundTree && (rand.nextInt(50) == 0))
                    {
                        int ai[] = MoCTools.ReturnNearestMaterialCoord(this, Material.wood, Double.valueOf(18D), 4D);
                        if (ai[0] != -1)
                        {
                            int i1 = 0;
                            do
                            {
                                if (i1 >= 20)
                                {
                                    break;
                                }
                                Block block = worldObj.getBlock(ai[0], ai[1] + i1, ai[2]);
                                if ((block.getMaterial() == Material.leaves))
                                {
                                    foundTree = true;
                                    treeCoord[0] = ai[0];
                                    treeCoord[1] = ai[1];
                                    treeCoord[2] = ai[2];
                                    break;
                                }
                                i1++;
                            } while (true);
                        }
                    }
                    if (!foundTree || (rand.nextInt(10) != 0))
                    {
                        break;
                    }
                    PathEntity pathentity = worldObj.getEntityPathToXYZ(this, treeCoord[0], treeCoord[1], treeCoord[2], 24F, true, false, false, true);

                    if (pathentity != null)
                    {
                        setPathToEntity(pathentity);
                    }
                    Double double1 = Double.valueOf(getDistanceSq(treeCoord[0], treeCoord[1], treeCoord[2]));
                    if (double1.doubleValue() < 7D)
                    {
                        setOnTree(true);
                    }
                    break;
                }
                if (!getOnTree())
                {
                    break;
                }
                int l = treeCoord[0];
                int j1 = treeCoord[1];
                int l1 = treeCoord[2];
                faceItem(l, j1, l1, 30F);
                if ((j1 - MathHelper.floor_double(posY)) > 2)
                {
                    motionY += 0.029999999999999999D;
                }
                boolean flag = false;
                boolean flag1 = false;
                if (posX < l)
                {
                    int j2 = l - MathHelper.floor_double(posX);
                    motionX += 0.01D;
                }
                else
                {
                    int k2 = MathHelper.floor_double(posX) - l;
                    motionX -= 0.01D;
                }
                if (posZ < l1)
                {
                    int j3 = l1 - MathHelper.floor_double(posZ);
                    motionZ += 0.01D;
                }
                else
                {
                    int k3 = MathHelper.floor_double(posX) - l1;
                    motionZ -= 0.01D;
                }
                if (onGround || !isCollidedHorizontally || !isCollidedVertically)
                {
                    break;
                }
                int i4 = 0;
                do
                {
                    if (i4 >= 30)
                    {
                        break label0;
                    }
                    Block block = worldObj.getBlock(treeCoord[0], treeCoord[1] + i4, treeCoord[2]);
                    if (block == Blocks.air)
                    {
                        setLocationAndAngles(treeCoord[0], treeCoord[1] + i4, treeCoord[2], rotationYaw, rotationPitch);
                        changeKittyState(17);
                        treeCoord[0] = -1;
                        treeCoord[1] = -1;
                        treeCoord[2] = -1;
                        break label0;
                    }
                    i4++;
                } while (true);

            case 17: // '\021'
                EntityPlayer entityplayer3 = worldObj.getClosestPlayerToEntity(this, 2D);
                if (entityplayer3 != null)
                {
                    changeKittyState(7);
                }
                break;

            case 18: // '\022'
                if ((entityToAttack == null) || !(entityToAttack instanceof MoCEntityKitty))
                {
                    changeKittyState(9);
                    break;
                }
                MoCEntityKitty entitykitty = (MoCEntityKitty) entityToAttack;
                if ((entitykitty != null) && (entitykitty.getKittyState() == 18))
                {
                    if (rand.nextInt(50) == 0)
                    {
                        swingArm();
                    }
                    float f10 = getDistanceToEntity(entitykitty);
                    if (f10 < 5F)
                    {
                        kittytimer++;
                    }
                    if ((kittytimer > 500) && (rand.nextInt(50) == 0))
                    {
                        ((MoCEntityKitty) entityToAttack).changeKittyState(7);
                        changeKittyState(19);
                    }
                }
                else
                {
                    changeKittyState(9);
                }
                break;

            case 19: // '\023'
                if (rand.nextInt(20) != 0)
                {
                    break;
                }
                MoCEntityKittyBed entitykittybed2 = (MoCEntityKittyBed) getKittyStuff(this, 18D, false);
                if ((entitykittybed2 == null) || (entitykittybed2.riddenByEntity != null))
                {
                    break;
                }
                float f11 = entitykittybed2.getDistanceToEntity(this);
                if (f11 > 2.0F)
                {
                    getMyOwnPath(entitykittybed2, f11);
                }
                if (f11 < 2.0F)
                {
                    changeKittyState(20);
                    mountEntity(entitykittybed2);
                }
                break;

            case 20: // '\024'
                if (ridingEntity == null)
                {
                    changeKittyState(19);
                    break;
                }
                rotationYaw = 180F;
                kittytimer++;
                if (kittytimer <= 1000)
                {
                    break;
                }
                int i2 = rand.nextInt(3) + 1;
                for (int l2 = 0; l2 < i2; l2++)
                {
                    MoCEntityKitty entitykitty1 = new MoCEntityKitty(worldObj);
                    int babytype = this.getType();
                    if (rand.nextInt(2) == 0)
                    {
                        babytype = (rand.nextInt(8)+1);
                    }
                    entitykitty1.setType(babytype);
                    entitykitty1.setPosition(posX, posY, posZ);
                    worldObj.spawnEntityInWorld(entitykitty1);
                    worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, ((rand.nextFloat() - rand.nextFloat()) * 0.2F) + 1.0F);
                    entitykitty1.setAdult(false);
                    entitykitty1.changeKittyState(10);
                    // attackEntityFrom(DamageSource.generic, 1); blood - workaround to fix
                    // infinite births
                }

                changeKittyState(21);
                break;

            case 21: // '\025'
                kittytimer++;
                if (kittytimer > 2000)
                {
                    List list2 = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(24D, 8D, 24D));
                    int i3 = 0;
                    for (int l3 = 0; l3 < list2.size(); l3++)
                    {
                        Entity entity2 = (Entity) list2.get(l3);
                        if ((entity2 instanceof MoCEntityKitty) && (((MoCEntityKitty) entity2).getKittyState() == 10))
                        {
                            i3++;
                        }
                    }

                    if (i3 < 1)
                    {
                        changeKittyState(7);
                        break;
                    }
                    kittytimer = 1000;
                }
                if ((entityToAttack != null) && (entityToAttack instanceof EntityPlayer) && (rand.nextInt(300) == 0))
                {
                    entityToAttack = null;
                }
                break;

            case 0:
                changeKittyState(1);
                break;
            // case 22: // '\026'
            default:
                changeKittyState(7);
                break;
            }
        }
        else
        {
            super.onLivingUpdate();
        }
    }

    public boolean onMaBack()
    {
        return getKittyState() == 15;
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();
        if (getIsSwinging())
        {
            swingProgress += 0.2F;
            if (swingProgress > 2.0F)
            {
                setSwinging(false);
                swingProgress = 0.0F;
            }
        }
    }

    private boolean pickable()
    {
        return (getKittyState() != 13) && (getKittyState() != 14) && (getKittyState() != 15) && (getKittyState() != 19) && (getKittyState() != 20) && (getKittyState() != 21);
    }

    @Override
    public boolean renderName()
    {
        return getDisplayName() && (getKittyState() != 14) && (getKittyState() != 15) && (getKittyState() > 1);
    }

    @Override
    public void setDead()
    {
        if (MoCreatures.isServer() && (getKittyState() > 2) && (getHealth() > 0))
        {
            return;
        }
        else
        {
            super.setDead();
            return;
        }
    }

    public void swingArm()
    {
        //to synchronize, uses the packet handler to invoke the same method in the clients
        if (MoCreatures.isServer())
        {
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 0), new TargetPoint(this.worldObj.provider.dimensionId, this.posX, this.posY, this.posZ, 64));
        }

        if (!getIsSwinging())
        {
            setSwinging(true);
            swingProgress = 0.0F;
        }
    }

    @Override
    public void performAnimation(int i)
    {
        swingArm();
    }

    public boolean upsideDown()
    {
        return getKittyState() == 14;
    }

    public boolean whipeable()
    {
        return getKittyState() != 13;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
        setSitting(nbttagcompound.getBoolean("Sitting"));
        setKittyState(nbttagcompound.getInteger("KittyState"));
        setDisplayName(nbttagcompound.getBoolean("DisplayName"));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("Sitting", getIsSitting());
        nbttagcompound.setInteger("KittyState", getKittyState());
        nbttagcompound.setBoolean("DisplayName", getDisplayName());
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
    
    
    //drops medallion on death
    @Override
    public void onDeath(DamageSource damagesource)
    {
        if (MoCreatures.isServer())
        {
            if (getIsTamed())
            {
                MoCTools.dropCustomItem(this, this.worldObj, new ItemStack(MoCreatures.medallion, 1));
            }
        }
        super.onDeath(damagesource);
    }

    @Override
    public boolean swimmerEntity()
    {
        return true;
    }
}