package drzhark.mocreatures.entity.monster;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.entity.passive.MoCEntityBear;
import drzhark.mocreatures.entity.passive.MoCEntityBigCat;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;

public class MoCEntityWWolf extends MoCEntityMob {

    public int mouthCounter;
    public int tailCounter;

    public MoCEntityWWolf(World world)
    {
        super(world);
        setSize(0.9F, 1.3F);
    }

    @Override
    protected double getAttackStrenght() 
    {
        return 3D;
    }
    
    @Override
    public void selectType()
    {
        if (getType() == 0)
        {
            setType(rand.nextInt(4)+1);
        }
    }

    @Override
    public ResourceLocation getTexture()
    {
        switch (getType())
        {
        case 1:
            return MoCreatures.proxy.getTexture("wolfblack.png");
        case 2:
            return MoCreatures.proxy.getTexture("wolfwild.png");
        case 3:
            return MoCreatures.proxy.getTexture("wolftimber.png"); //snow wolf
        case 4:
            return MoCreatures.proxy.getTexture("wolfdark.png");
        case 5:
            return MoCreatures.proxy.getTexture("wolfbright.png");

        default:
            return MoCreatures.proxy.getTexture("wolfwild.png");
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

        if (tailCounter > 0 && ++tailCounter > 8)
        {
            tailCounter = 0;
        }
    }

    @Override
    public boolean checkSpawningBiome()
    {
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(boundingBox.minY);
        int k = MathHelper.floor_double(posZ);

        BiomeGenBase biome = MoCTools.Biomekind(worldObj, i, j, k);
        int l = rand.nextInt(10);

        if (BiomeDictionary.isBiomeOfType(biome, Type.FROZEN))
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
            EntityLivingBase entityliving = getClosestTarget(this, 10D);
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
        return checkSpawningBiome() && worldObj.canBlockSeeTheSky(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)) && super.getCanSpawnHere();
    }

    //TODO move this
    public EntityLivingBase getClosestTarget(Entity entity, double d)
    {
        double d1 = -1D;
        EntityLivingBase entityliving = null;
        List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(d, d, d));
        for (int i = 0; i < list.size(); i++)
        {
            Entity entity1 = (Entity) list.get(i);
            if (!(entity1 instanceof EntityLivingBase) || (entity1 == entity) || (entity1 == entity.riddenByEntity) || (entity1 == entity.ridingEntity) || (entity1 instanceof EntityPlayer) || (entity1 instanceof EntityMob) || (entity1 instanceof MoCEntityBigCat) || (entity1 instanceof MoCEntityBear) || (entity1 instanceof EntityCow) || ((entity1 instanceof EntityWolf) && !(MoCreatures.proxy
                    .attackWolves)) || ((entity1 instanceof MoCEntityHorse) && !(MoCreatures.proxy.attackHorses)))
            {
                continue;
            }
            double d2 = entity1.getDistanceSq(entity.posX, entity.posY, entity.posZ);
            if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1)) && ((EntityLivingBase) entity1).canEntityBeSeen(entity))
            {
                d1 = d2;
                entityliving = (EntityLivingBase) entity1;
            }
        }

        return entityliving;
    }

    @Override
    protected String getDeathSound()
    {
        return "mocreatures:wolfdeath";
    }

    @Override
    protected Item getDropItem()
    {
        return MoCreatures.fur;
    }

    @Override
    protected String getHurtSound()
    {
        openMouth();
        return "mocreatures:wolfhurt";
    }

    @Override
    protected String getLivingSound()
    {
        openMouth();
        return "mocreatures:wolfgrunt";
    }
}