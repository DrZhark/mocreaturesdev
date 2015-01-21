package drzhark.mocreatures.entity.passive;

import net.minecraft.block.Block;
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
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;

public class MoCEntityBird extends MoCEntityTameableAnimal {
    private boolean fleeing;
    public float wingb;
    public float wingc;
    public float wingd;
    public float winge;
    public float wingh;
    public boolean textureSet;
    private boolean isPicked;

    public static final String birdNames[] = { "Dove", "Crow", "Parrot", "Blue", "Canary", "Red" };

    public MoCEntityBird(World world)
    {
        super(world);
        setSize(0.4F, 0.3F);
        isCollidedVertically = true;
        wingb = 0.0F;
        wingc = 0.0F;
        wingh = 1.0F;
        fleeing = false;
        textureSet = false;
        setTamed(false);
    }

    protected void applyEntityAttributes()
    {
      super.applyEntityAttributes();
      getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
    }

    @Override
    public void selectType()
    {
        if (getType() == 0)
        {
            setType(rand.nextInt(6)+1);
        }
    }

    @Override
    public ResourceLocation getTexture()
    {

        switch (getType())
        {
        case 1:
            return MoCreatures.proxy.getTexture("birdwhite.png");
        case 2:
            return MoCreatures.proxy.getTexture("birdblack.png");
        case 3:
            return MoCreatures.proxy.getTexture("birdgreen.png");
        case 4:
            return MoCreatures.proxy.getTexture("birdblue.png");
        case 5:
            return MoCreatures.proxy.getTexture("birdyellow.png");
        case 6:
            return MoCreatures.proxy.getTexture("birdred.png");

        default:
            return MoCreatures.proxy.getTexture("birdblue.png");
        }
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(22, Byte.valueOf((byte) 0)); // preTamed - 0 false 1 true
    }

    public boolean getPreTamed()
    {
        return (dataWatcher.getWatchableObjectByte(22) == 1);
    }
    
    public void setPreTamed(boolean flag)
    {
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(22, Byte.valueOf(input));
    }
    
    
    @Override
    protected void fall(float f)
    {
    }

    private int[] FindTreeTop(int i, int j, int k)
    {
        int l = i - 5;
        int i1 = k - 5;
        int j1 = i + 5;
        int k1 = j + 7;
        int l1 = k + 5;
        for (int i2 = l; i2 < j1; i2++)
        {
            label0: for (int j2 = i1; j2 < l1; j2++)
            {
                Block block = worldObj.getBlock(i2, j, j2);
                if ((block.isAir(worldObj, i2, j, j2)) || (block.getMaterial() != Material.wood))
                {
                    continue;
                }
                int l2 = j;
                do
                {
                    if (l2 >= k1)
                    {
                        continue label0;
                    }
                    Block block1 = worldObj.getBlock(i2, l2, j2);
                    if (block1.isAir(worldObj, i2, l2, j2)) { return (new int[] { i2, l2 + 2, j2 }); }
                    l2++;
                } while (true);
            }

        }

        return (new int[] { 0, 0, 0 });
    }

    private boolean FlyToNextEntity(Entity entity)
    {
        if (entity != null)
        {
            int i = MathHelper.floor_double(entity.posX);
            int j = MathHelper.floor_double(entity.posY);
            int k = MathHelper.floor_double(entity.posZ);
            faceLocation(i, j, k, 30F);
            if (MathHelper.floor_double(posY) < j)
            {
                motionY += 0.14999999999999999D;
            }
            if (posX < entity.posX)
            {
                double d = entity.posX - posX;
                if (d > 0.5D)
                {
                    motionX += 0.050000000000000003D;
                }
            }
            else
            {
                double d1 = posX - entity.posX;
                if (d1 > 0.5D)
                {
                    motionX -= 0.050000000000000003D;
                }
            }
            if (posZ < entity.posZ)
            {
                double d2 = entity.posZ - posZ;
                if (d2 > 0.5D)
                {
                    motionZ += 0.050000000000000003D;
                }
            }
            else
            {
                double d3 = posZ - entity.posZ;
                if (d3 > 0.5D)
                {
                    motionZ -= 0.050000000000000003D;
                }
            }
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean FlyToNextTree()
    {
        int ai[] = ReturnNearestMaterialCoord(this, Material.leaves, Double.valueOf(20D));
        int ai1[] = FindTreeTop(ai[0], ai[1], ai[2]);
        if (ai1[1] != 0)
        {
            int i = ai1[0];
            int j = ai1[1];
            int k = ai1[2];
            faceLocation(i, j, k, 30F);
            if ((j - MathHelper.floor_double(posY)) > 2)
            {
                motionY += 0.14999999999999999D;
            }
            int l = 0;
            int i1 = 0;
            if (posX < i)
            {
                l = i - MathHelper.floor_double(posX);
                motionX += 0.050000000000000003D;
            }
            else
            {
                l = MathHelper.floor_double(posX) - i;
                motionX -= 0.050000000000000003D;
            }
            if (posZ < k)
            {
                i1 = k - MathHelper.floor_double(posZ);
                motionZ += 0.050000000000000003D;
            }
            else
            {
                i1 = MathHelper.floor_double(posX) - k;
                motionZ -= 0.050000000000000003D;
            }
            double d = l + i1;
            if (d < 3D) { return true; }
        }
        return false;
    }

    @Override
    public boolean entitiesToIgnore(Entity entity)
    {
        return (entity instanceof MoCEntityBird) || ((entity.height <= this.height) && (entity.width <= this.width)) || super.entitiesToIgnore(entity);
    }

    @Override
    protected String getDeathSound()
    {
        return "mocreatures:birddying";
    }

    @Override
    protected Item getDropItem()
    {
        return Items.feather;
    }

    @Override
    protected String getHurtSound()
    {
        return "mocreatures:birdhurt";
    }

    @Override
    protected String getLivingSound()
    {
        if (getType() == 1) { return "mocreatures:birdwhite"; }
        if (getType() == 2) { return "mocreatures:birdblack"; }
        if (getType() == 3) { return "mocreatures:birdgreen"; }
        if (getType() == 4) { return "mocreatures:birdblue"; }
        if (getType() == 5)
        {
            return "mocreatures:birdyellow";
        }
        else
        {
            return "mocreatures:birdred";
        }
    }

    public boolean getPicked()
    {
        return isPicked;
    }

    @Override
    public double getYOffset()
    {
        if (ridingEntity instanceof EntityPlayer && ridingEntity == MoCreatures.proxy.getPlayer() && !MoCreatures.isServer()) { return (yOffset - 1.15F); }

        if ((ridingEntity instanceof EntityPlayer) && !MoCreatures.isServer())
        {
            return (yOffset + 0.45F);
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

        if (itemstack != null && getPreTamed() && !getIsTamed() && itemstack.getItem() == Items.wheat_seeds)
        {
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            if (MoCreatures.isServer())
            {
                MoCTools.tameWithName(entityplayer, this);
            }
            return true;
        }

        if (!getIsTamed()) { return false; }
        
        rotationYaw = entityplayer.rotationYaw;
        if (this.ridingEntity == null)
        {
            if (MoCreatures.isServer())
            {
                mountEntity(entityplayer);
            }
            setPicked(true);
        }
        else
        {
            worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, ((rand.nextFloat() - rand.nextFloat()) * 0.2F) + 1.0F);
            if (MoCreatures.isServer())
            {
                this.mountEntity(null);
            }
        }
        motionX = entityplayer.motionX * 5D;
        motionY = (entityplayer.motionY / 2D) + 0.5D;
        motionZ = entityplayer.motionZ * 5D;
        return true;
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        // fixes glide issue in SMP
        if (worldObj.isRemote)
        {
            if (ridingEntity != null)
            {
                updateEntityActionState();
            }
            else
            {
                //return; 
                //commenting this fixes the wing movement bug
            }
        }

        winge = wingb;
        wingd = wingc;
        wingc = (float) (wingc + ((onGround ? -1 : 4) * 0.29999999999999999D));
        if (wingc < 0.0F)
        {
            wingc = 0.0F;
        }
        if (wingc > 1.0F)
        {
            wingc = 1.0F;
        }
        if (!onGround && (wingh < 1.0F))
        {
            wingh = 1.0F;
        }
        wingh = (float) (wingh * 0.90000000000000002D);
        if (!onGround && (motionY < 0.0D))
        {
            motionY *= 0.80000000000000004D;
        }
        wingb += wingh * 2.0F;

        //check added to avoid duplicating behavior on client / server
        if (MoCreatures.isServer())
        {
            EntityLivingBase entityliving = getBoogey(5D);
            if (rand.nextInt(10) == 0 && (entityliving != null) && !getIsTamed() && !getPreTamed() && canEntityBeSeen(entityliving))
            {
                fleeing = true;
            }
            if (rand.nextInt(200) == 0)
            {
                fleeing = true;
            }
            if (fleeing)
            {
                if (FlyToNextTree())
                {
                    fleeing = false;
                }
                int ai[] = ReturnNearestMaterialCoord(this, Material.leaves, Double.valueOf(16D));
                if (ai[0] == -1)
                {
                    for (int i = 0; i < 2; i++)
                    {
                        WingFlap();
                    }

                    fleeing = false;
                }
                if (rand.nextInt(50) == 0)
                {
                    fleeing = false;
                }
            }
            if (!fleeing)
            {
                EntityItem entityitem = getClosestItem(this, 12D, Items.wheat_seeds, null);
                if (entityitem != null)
                {
                    FlyToNextEntity(entityitem);
                    EntityItem entityitem1 = getClosestItem(this, 1.0D, Items.wheat_seeds, null);
                    if ((rand.nextInt(50) == 0) && (entityitem1 != null))
                    {
                        entityitem1.setDead();
                        setPreTamed(true);
                    }
                }
            }
            if (rand.nextInt(10) == 0 && isInsideOfMaterial(Material.water))
            {
                WingFlap();
            }
        }
    }

    public int[] ReturnNearestMaterialCoord(Entity entity, Material material, Double double1)
    {
        AxisAlignedBB axisalignedbb = entity.boundingBox.expand(double1.doubleValue(), double1.doubleValue(), double1.doubleValue());
        int i = MathHelper.floor_double(axisalignedbb.minX);
        int j = MathHelper.floor_double(axisalignedbb.maxX + 1.0D);
        int k = MathHelper.floor_double(axisalignedbb.minY);
        int l = MathHelper.floor_double(axisalignedbb.maxY + 1.0D);
        int i1 = MathHelper.floor_double(axisalignedbb.minZ);
        int j1 = MathHelper.floor_double(axisalignedbb.maxZ + 1.0D);
        for (int k1 = i; k1 < j; k1++)
        {
            for (int l1 = k; l1 < l; l1++)
            {
                for (int i2 = i1; i2 < j1; i2++)
                {
                    Block block = worldObj.getBlock(k1, l1, i2);
                    if ((block != null && !block.isAir(worldObj, k1, l1, i2)) && (block.getMaterial() == material)) { return (new int[] { k1, l1, i2 }); }
                }

            }

        }

        return (new int[] { -1, 0, 0 });
    }

    @Override
    public void setDead()
    {
        if (MoCreatures.isServer() && getIsTamed() && (this.getHealth() > 0))
        {
            return;
        }
        else
        {
            super.setDead();
            return;
        }
    }

    public void setPicked(boolean var1)
    {
        isPicked = var1;
    }

    @Override
    protected void updateEntityActionState()
    {
        if (onGround && (rand.nextInt(10) == 0) && ((motionX > 0.050000000000000003D) || (motionZ > 0.050000000000000003D) || (motionX < -0.050000000000000003D) || (motionZ < -0.050000000000000003D)))
        {
            motionY = 0.25D;
        }
        if ((ridingEntity != null) && (ridingEntity instanceof EntityPlayer))
        {
            EntityPlayer entityplayer = (EntityPlayer) ridingEntity;
            if (entityplayer != null)
            {
                rotationYaw = entityplayer.rotationYaw;
                entityplayer.fallDistance = 0.0F;
                if (entityplayer.motionY < -0.10000000000000001D)
                {
                    entityplayer.motionY = -0.10000000000000001D;
                }
            }
        }
        if (!fleeing || !getPicked())
        {
            super.updateEntityActionState();
        }
        else if (onGround)
        {
            setPicked(false);
        }
    }

    private void WingFlap()
    {
        motionY += 0.05D;
        if (rand.nextInt(30) == 0)
        {
            motionX += 0.2D;
        }
        if (rand.nextInt(30) == 0)
        {
            motionX -= 0.2D;
        }
        if (rand.nextInt(30) == 0)
        {
            motionZ += 0.2D;
        }
        if (rand.nextInt(30) == 0)
        {
            motionZ -= 0.2D;
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
    public int nameYOffset()
    {
        return -40;

    }

    @Override
    public boolean isMyHealFood(ItemStack par1ItemStack)
    {
        return par1ItemStack != null && par1ItemStack.getItem() == Items.wheat_seeds;
    }

    @Override
    public double roperYOffset()
    {
        return 0.9D;
    }
}