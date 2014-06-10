package drzhark.mocreatures.entity.monster;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;

public class MoCEntitySilverSkeleton extends MoCEntityMob
{
    public int attackCounterLeft;
    public int attackCounterRight;

    public MoCEntitySilverSkeleton(World world)
    {
        super(world);
        texture = "silverskeleton.png";
        setSize(0.9F, 1.4F);
    }

    protected void applyEntityAttributes()
    {
      super.applyEntityAttributes();
      getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0D);
    }

    @Override
    public void onLivingUpdate()
    {
        if (MoCreatures.isServer())
        {
            if (entityToAttack == null )
            {
                setSprinting(false);
            }
            else
            {
                setSprinting(true);
            }

            if (this.worldObj.isDaytime())
            {
                float var1 = this.getBrightness(1.0F);

                if (var1 > 0.5F && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) && this.rand.nextFloat() * 30.0F < (var1 - 0.4F) * 2.0F)
                {
                    this.setFire(8);
                }
            }
        }

        if (attackCounterLeft > 0 && ++attackCounterLeft > 10)
        {
            attackCounterLeft = 0;
        }

        if (attackCounterRight > 0 && ++attackCounterRight > 10)
        {
            attackCounterRight = 0;
        }

        super.onLivingUpdate();
    }

    @Override
    protected Item getDropItem()
    {
        if (rand.nextInt(10) == 0)
        {
            return MoCreatures.silversword;
        }
        return Items.bone;

    }

     @Override
        public void performAnimation(int animationType)
        {
            
            if (animationType == 1) //left arm
            {
                attackCounterLeft = 1;
            }
            if (animationType == 2) //right arm
            {
                attackCounterRight = 1;
            }
        }

        /**
         * Starts attack counters and synchronizes animations with clients
         */
        private void startAttackAnimation() 
        {
            if (MoCreatures.isServer())
            {
                boolean leftArmW = rand.nextInt(2) == 0;
                
                if (leftArmW)
                {
                    attackCounterLeft = 1;
                    MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 1), new TargetPoint(this.worldObj.provider.dimensionId, this.posX, this.posY, this.posZ, 64));
                }else
                {
                    attackCounterRight = 1;
                    MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 2), new TargetPoint(this.worldObj.provider.dimensionId, this.posX, this.posY, this.posZ, 64));
                }
            }
        }

        protected void attackEntity(Entity par1Entity, float par2)
        {
            if (this.attackTime <= 0 && par2 < 2.0F && par1Entity.boundingBox.maxY > this.boundingBox.minY && par1Entity.boundingBox.minY < this.boundingBox.maxY)
            {
                this.attackTime = 20;
                startAttackAnimation();
                this.attackEntityAsMob(par1Entity);
            }
        }
    public float getMoveSpeed()
    {
        return 1.2F;
        /*if (isSprinting()) return 1.2F;
        return 0.8F;*/
    }

    @Override
    protected String getDeathSound()
    {
    return "mob.skeleton.death";
    }

    @Override
    protected String getHurtSound()
    {
    return "mob.skeleton.hurt";
    }

    @Override
    protected String getLivingSound()
    {
    return "mob.skeleton.say";
    }
    
    /**
     * Get this Entity's EnumCreatureAttribute
     */
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }
   
    @Override
    protected void func_145780_a(int par1, int par2, int par3, Block block)
    {
        this.playSound("mob.skeleton.step", 0.15F, 1.0F);
    }
}