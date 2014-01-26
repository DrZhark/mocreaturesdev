package drzhark.mocreatures.entity.aquatic;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;

public class MoCEntityPiranha extends MoCEntitySmallFish{

    public static final String fishNames[] = { "Piranha"};

    public MoCEntityPiranha(World world)
    {
        super(world);
        setSize(0.3F, 0.3F);
        setEdad(30 + rand.nextInt(70));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(6.0D);
    }

    @Override
    public void selectType()
    {
        setType(1);
    }

    public ResourceLocation getTexture()
    {
       return MoCreatures.proxy.getTexture("smallfish_piranha.png");
    }
    
    @Override
    protected Entity findPlayerToAttack()
    {
        if ((worldObj.difficultySetting.getDifficultyId() > 0))
        {
            EntityPlayer entityplayer = worldObj.getClosestVulnerablePlayerToEntity(this, 12D);
            if ((entityplayer != null) && entityplayer.isInWater() && !getIsTamed()) { return entityplayer; }
        }
        return null;
    }
    
    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i)
    {
        if (super.attackEntityFrom(damagesource, i) && (worldObj.difficultySetting.getDifficultyId() > 0))
        {
            Entity entity = damagesource.getEntity();
            if ((riddenByEntity == entity) || (ridingEntity == entity)) { return true; }
            if (entity != this)
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

    public boolean isNotScared()
    {
        return true;
    }

    @Override
    protected void attackEntity(Entity entity, float f)
    {
        if (entity.isInWater() && (f < 0.8D))
        {
            if (entity instanceof EntityPlayer && ((EntityPlayer)entity).ridingEntity != null)
            {
                Entity playerMount = ((EntityPlayer)entity).ridingEntity;
                if (playerMount instanceof EntityBoat) 
                {
                    return;
                }
            }
            attackTime = 20;
            entity.attackEntityFrom(DamageSource.causeMobDamage(this), 2);
            if (!(entity instanceof EntityPlayer))
            {
                MoCTools.destroyDrops(this, 3D);
            }
        }
    }

    @Override
    protected void dropFewItems(boolean flag, int x)
    {
        int i = rand.nextInt(100);
        if (i < 70)
        {
            entityDropItem(new ItemStack(Items.fish, 1, 0), 0.0F);
        }
        else
        {
            int j = rand.nextInt(2);
            for (int k = 0; k < j; k++)
            {
                entityDropItem(new ItemStack(MoCreatures.mocegg, 1, 90), 0.0F); 
            }
        }
    }
}