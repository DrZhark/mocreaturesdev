package drzhark.mocreatures.entity.passive;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAnimal;

public class MoCEntityMouse extends MoCEntityAnimal
{

    public MoCEntityMouse(World world)
    {
        super(world);
        setSize(0.3F, 0.3F);
        //health = 4;
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0D);
    }

    public void selectType()
    {
        checkSpawningBiome();
        
        if (getType() == 0)
        {
            setType(rand.nextInt(3)+1);
        }
    }

    @Override
    public ResourceLocation getTexture()
    {
        switch (getType())
        {
            case 1:
                return MoCreatures.proxy.getTexture("miceg.png");
            case 2:
                return MoCreatures.proxy.getTexture("miceb.png");
            case 3:
                return MoCreatures.proxy.getTexture("micew.png");
            
            default:
                return MoCreatures.proxy.getTexture("miceg.png");
        }
    }

    @Override
    public boolean checkSpawningBiome()
    {
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(boundingBox.minY);
        int k = MathHelper.floor_double(posZ);
        BiomeGenBase currentbiome = MoCTools.Biomekind(worldObj, i, j, k);

        String s = MoCTools.BiomeName(worldObj, i, j, k);
        if (BiomeDictionary.isBiomeOfType(currentbiome, Type.FROZEN))
        {
            setType(3); //white mice!
        }
        return true;
    }

    @Override
    public float getMoveSpeed()
    {
        return 0.8F;
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(22, Byte.valueOf((byte) 0)); // byte IsPicked, 0 = false 1 = true
    }

    public boolean getIsPicked()
    {
        return (dataWatcher.getWatchableObjectByte(22) == 1);
    }

    public void setPicked(boolean flag)
    {
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(22, Byte.valueOf(input));
    }

    private boolean checkNearCats()
    {
        return true;
    }

    private boolean checkNearRock()
    {
        return true;
    }

    public boolean climbing()
    {
        return !onGround && isOnLadder();
    }

    public boolean entitiesToInclude(Entity entity)
    {
        return !(entity instanceof MoCEntityMouse)
        && super.entitiesToInclude(entity);
    }

    @Override
    public boolean getCanSpawnHere()
    {
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(boundingBox.minY);
        int k = MathHelper.floor_double(posZ);
        return ( 
                (MoCreatures.entityMap.get(this.getClass()).getFrequency() > 0) &&
                worldObj.checkNoEntityCollision(boundingBox) 
                && (worldObj.getCollidingBoundingBoxes(this, boundingBox).size() == 0) 
                && !worldObj.isAnyLiquid(boundingBox) 
                && ((worldObj.getBlock(i, j - 1, k) == Blocks.cobblestone) 
                || (worldObj.getBlock(i, j - 1, k) == Blocks.planks) 
                || (worldObj.getBlock(i, j - 1, k) == Blocks.dirt) 
                || (worldObj.getBlock(i, j - 1, k) == Blocks.stone) 
                || (worldObj.getBlock(i, j - 1, k) == Blocks.grass)));
    }

    @Override
    protected String getDeathSound()
    {
        return "mocreatures:micedying";
    }

    @Override
    protected Item getDropItem()
    {
        return Items.wheat_seeds;
    }

    @Override
    protected String getHurtSound()
    {
        return "mocreatures:micehurt";
    }

    @Override
    protected String getLivingSound()
    {
        return "mocreatures:micegrunt";
    }

    @Override
    protected void fall(float f)
    {
    }

    @Override
    public double getYOffset()
    {
        if (ridingEntity instanceof EntityPlayer && ridingEntity == MoCreatures.proxy.getPlayer() && !MoCreatures.isServer())
        {
            return (yOffset - 1.7F);
        }
            
        if ((ridingEntity instanceof EntityPlayer) && !MoCreatures.isServer())
        {
            return (yOffset - 0.1F);
        }
        else 
            return yOffset;
    }

    @Override
    public boolean interact(EntityPlayer entityplayer)
    {
        rotationYaw = entityplayer.rotationYaw;
        if (this.ridingEntity == null)
        {
            if (MoCreatures.isServer()) mountEntity(entityplayer);
            setPicked(true);
        }
        else
        {
            worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, ((rand.nextFloat() - rand.nextFloat()) * 0.2F) + 1.0F);
            setPicked(false);
            if (MoCreatures.isServer()) this.mountEntity(null);
        }
        motionX = entityplayer.motionX * 5D;
        motionY = (entityplayer.motionY / 2D) + 0.5D;
        motionZ = entityplayer.motionZ * 5D;

        return true;
    }

    @Override
    public boolean isOnLadder()
    {
        return isCollidedHorizontally;
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        if(!worldObj.isRemote)
        {
            if(rand.nextInt(15) == 0)
            {
                EntityLivingBase entityliving = getBoogey(6D);
                if(entityliving != null)
                {
                    MoCTools.runLikeHell(this, entityliving);

                }
            }
            if(!onGround && (ridingEntity != null))
            {
                rotationYaw = ridingEntity.rotationYaw;
            }
        }
    }

    private void reproduce()
    {
    }

    public boolean upsideDown()
    {
        return getIsPicked();
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
    public boolean swimmerEntity()
    {
        return true;
    }
}