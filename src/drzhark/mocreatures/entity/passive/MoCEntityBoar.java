package drzhark.mocreatures.entity.passive;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAnimal;

public class MoCEntityBoar extends MoCEntityAnimal {
    protected int force;
    protected double attackRange;

    public MoCEntityBoar(World world)
    {
        super(world);
        //texture = MoCreatures.proxy.MODEL_TEXTURE + "boara.png";
        setSize(0.9F, 0.9F);
        //health = 10;
        force = 1;
        attackRange = 1.0D;
        setEdad(50);
        if (rand.nextInt(4) == 0)
        {
            setAdult(false);

        }
        else
        {
            setAdult(true);
        }
    }

    protected void applyEntityAttributes()
    {
      super.applyEntityAttributes();
      getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
    }

    @Override
    protected void attackEntity(Entity entity, float f)
    {
        if (attackTime <= 0 && (f < 2.5D) && (entity.boundingBox.maxY > boundingBox.minY) && (entity.boundingBox.minY < boundingBox.maxY))
        {
            attackTime = 20;
            entity.attackEntityFrom(DamageSource.causeMobDamage(this), force);
            if (!(entity instanceof EntityPlayer))
            {
                MoCTools.destroyDrops(this, 3D);
            }
        }
    }

    @Override
    public ResourceLocation getTexture()
    {
        if (getIsAdult()) { return MoCreatures.proxy.getTexture("boara.png"); }
        return MoCreatures.proxy.getTexture("boarb.png");

    }

    @Override
    protected boolean canDespawn()
    {
        if (MoCreatures.proxy.forceDespawns)
            return !getIsTamed();
        else return false;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i)
    {
        if (super.attackEntityFrom(damagesource, i))
        {
            Entity entity = damagesource.getEntity();
            if ((riddenByEntity == entity) || (ridingEntity == entity)) { return true; }
            if ((entity != this) && (worldObj.difficultySetting.getDifficultyId() > 0) && getIsAdult())
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
        return getIsAdult();
    }

    @Override
    protected Entity findPlayerToAttack()
    {
        if (worldObj.difficultySetting.getDifficultyId() > 0)
        {
            EntityPlayer entityplayer = worldObj.getClosestVulnerablePlayerToEntity(this, attackRange);
            if ((entityplayer != null) && (rand.nextInt(50) == 0)) { return entityplayer; }
            if (rand.nextInt(80) == 0)
            {
                EntityLivingBase entityliving = getClosestTarget(this, 10D);
                return entityliving;
            }
        }
        return null;
    }

    

    @Override
    public void onLivingUpdate()
    {
        if (worldObj.difficultySetting.getDifficultyId() == 1)
        {
            attackRange = 2D;
            force = 1;
        }
        else if (worldObj.difficultySetting.getDifficultyId() > 1)
        {
            attackRange = 3D;
            force = 2;
        }
        super.onLivingUpdate();

        if ((MoCreatures.isServer()) && !getIsAdult() && (rand.nextInt(250) == 0))
        {
            setEdad(getEdad() + 1);
            if (getEdad() >= 100)
            {
                setAdult(true);
            }
        }
    }

    @Override
    protected Item getDropItem()
    {

        if (rand.nextInt(2) == 0) { return Items.porkchop; }

        return MoCreatures.animalHide;
    }

    @Override
    protected String getLivingSound()
    {
        return "mob.pig.say";
    }

    @Override
    protected String getHurtSound()
    {
        return "mob.pig.say";
    }

    @Override
    protected String getDeathSound()
    {
        return "mob.pig.death";
    }
}