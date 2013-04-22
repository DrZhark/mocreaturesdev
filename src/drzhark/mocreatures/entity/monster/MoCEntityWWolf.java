package drzhark.mocreatures.entity.monster;

import java.util.List;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.entity.passive.MoCEntityBear;
import drzhark.mocreatures.entity.passive.MoCEntityBigCat;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class MoCEntityWWolf extends MoCEntityMob {
    public MoCEntityWWolf(World world)
    {
        super(world);
        //wolfboolean = false;
        texture = MoCreatures.proxy.MODEL_TEXTURE + "wolfwild.png";
        setSize(0.9F, 1.3F);
        //attackStrength = 1;
    }

    public int mouthCounter;
    //public int standCounter;
    public int tailCounter;

    /**
     * Sets the type and texture of a BigCat if not set already.
     */
    @Override
    public void selectType()
    {
        if (getType() == 0)
        {

            int i = rand.nextInt(100);
            if (i <= 25)
            {
                setType(1);
            }
            else if (i <= 50)
            {
                setType(2);
            }
            else if (i <= 75)
            {
                setType(5);
            }
            else
            {
                setType(4);
            }

            //health = getMaxHealth();
        }

        /*if (type == 1)
        {
            texture = MoCreatures.proxy.MODEL_TEXTURE + "wolfblack.png";
            
        } 
        if (type == 2)
        {
            texture = MoCreatures.proxy.MODEL_TEXTURE + "wolfwild.png";
            
        }
        if (type == 3)
        {
            texture = MoCreatures.proxy.MODEL_TEXTURE + "wolftimber.png"; //snow wolf
            
        } 
        if (type == 4)
        {
            texture = MoCreatures.proxy.MODEL_TEXTURE + "wolfdark.png";
            
        } 
        
        if (type == 5)
        {
            texture = MoCreatures.proxy.MODEL_TEXTURE + "wolfbright.png";
            
        } */
    }

    @Override
    public String getTexture()
    {

        switch (getType())
        {
        case 1:
            return MoCreatures.proxy.MODEL_TEXTURE + "wolfblack.png";
        case 2:
            return MoCreatures.proxy.MODEL_TEXTURE + "wolfwild.png";
        case 3:
            return MoCreatures.proxy.MODEL_TEXTURE + "wolftimber.png"; //snow wolf
        case 4:
            return MoCreatures.proxy.MODEL_TEXTURE + "wolfdark.png";
        case 5:
            return MoCreatures.proxy.MODEL_TEXTURE + "wolfbright.png";

        default:
            return MoCreatures.proxy.MODEL_TEXTURE + "wolfwild.png";
        }
    }

    @Override
    protected void attackEntity(Entity entity, float f)
    {
        if (attackTime <= 0 && (f < 2.5D) && (entity.boundingBox.maxY > boundingBox.minY) && (entity.boundingBox.minY < boundingBox.maxY))
        {
            openMouth();
            attackTime = 20;
            this.attackEntityAsMob(entity);
            if (!(entity instanceof EntityPlayer))
            {
                MoCTools.destroyDrops(this, 3D);
            }
        }
    }

    private void openMouth()
    {
        mouthCounter = 1;
    }

    private void moveTail()
    {
        tailCounter = 1;
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if (rand.nextInt(200) == 0)
        {
            moveTail();
        }

        if (mouthCounter > 0 && ++mouthCounter > 15)
        {
            mouthCounter = 0;
        }

        /*if (standCounter > 0 && ++standCounter > 15)
        {
            standCounter = 0;
        }*/

        if (tailCounter > 0 && ++tailCounter > 8)
        {
            tailCounter = 0;
        }

    }

    @Override
    public boolean checkSpawningBiome()
    {
        if (MoCTools.isNearTorch(this)) { return false; }

        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(boundingBox.minY);
        int k = MathHelper.floor_double(posZ);

        String s = MoCTools.BiomeName(worldObj, i, j, k);

        int l = rand.nextInt(10);

        if (s.equals("Taiga") || s.equals("Frozen Ocean") || s.equals("Frozen River") || s.equals("Ice Plains") || s.equals("Ice Mountains") || s.equals("TaigaHills"))
        {
            setType(3);
        }
        selectType();
        return true;
    }

    @Override
    protected Entity findPlayerToAttack()
    {
        float f = getBrightness(1.0F);
        if (f < 0.5F)
        {
            double d = 16D;
            return worldObj.getClosestVulnerablePlayerToEntity(this, d);
        }
        if (rand.nextInt(80) == 0)
        {
            EntityLiving entityliving = getClosestTarget(this, 10D);
            return entityliving;
        }
        else
        {
            return null;
        }
    }

    @Override
    public boolean getCanSpawnHere()
    {
        return checkSpawningBiome() && worldObj.canBlockSeeTheSky(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)) && (MoCreatures.proxy.getFrequency(this.getEntityName()) > 0) && super.getCanSpawnHere();
    }

    //TODO move this
    public EntityLiving getClosestTarget(Entity entity, double d)
    {
        double d1 = -1D;
        EntityLiving entityliving = null;
        List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(d, d, d));
        for (int i = 0; i < list.size(); i++)
        {
            Entity entity1 = (Entity) list.get(i);
            if (!(entity1 instanceof EntityLiving) || (entity1 == entity) || (entity1 == entity.riddenByEntity) || (entity1 == entity.ridingEntity) || (entity1 instanceof EntityPlayer) || (entity1 instanceof EntityMob) || (entity1 instanceof MoCEntityBigCat) || (entity1 instanceof MoCEntityBear) || (entity1 instanceof EntityCow) || ((entity1 instanceof EntityWolf) && !(MoCreatures.proxy
                    .attackWolves)) || ((entity1 instanceof MoCEntityHorse) && !(MoCreatures.proxy.attackHorses)))
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
        return "wolfdeath";
    }

    @Override
    protected int getDropItemId()
    {
        return MoCreatures.fur.itemID;
    }

    @Override
    protected String getHurtSound()
    {
        openMouth();
        return "wolfhurt";
    }

    @Override
    protected String getLivingSound()
    {
        openMouth();
        return "wolfgrunt";
    }

    @Override
    public int getMaxSpawnedInChunk()
    {
        return 6;
    }

    @Override
    public int getAttackStrength(Entity par1Entity)
    {
        if (worldObj.difficultySetting == 1) { return 2; }
        return 3;
    }
}
