package drzhark.mocreatures.entity.monster;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityMob;

public class MoCEntityHorseMob extends MoCEntityMob
{
    public int mouthCounter;
    public int textCounter;
    public int standCounter;
    public int tailCounter;
    public int eatingCounter;
    private int fCounter;
    private float transFloat = 0.2F;
    public int wingFlapCounter;

    public MoCEntityHorseMob(World world)
    {
        super(world);
        setSize(1.4F, 1.6F);
    }

    protected void applyEntityAttributes()
    {
      super.applyEntityAttributes();
      getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
    }

    @Override
    public void selectType()
    {
        if (worldObj.provider.isHellWorld)
        {
            setType(38);
            this.isImmuneToFire = true;
        }else
        {
            if (getType() == 0)
            {
                int j = rand.nextInt(100);
                if (j <= (40))
                {
                    setType(23); //undead
                } else if (j <= (80))
                {
                    setType(26); //skeleton horse
                } 
                else
                {
                    setType(32);
                }
            }
        }
    }

    /**
     * Overridden for the dynamic nightmare texture.
     * * 23 Undead 
     * 24 Undead Unicorn 
     * 25 Undead Pegasus 
     * 
     * 26 skeleton
     * 27 skeleton unicorn
     * 28 skeleton pegasus
     * 
     * 30 bug horse
     * 
     * 32 Bat Horse
     */
    @Override
    public ResourceLocation getTexture()
    {
        
        switch (getType())
        {
            case 23://undead horse
                
                if (!MoCreatures.proxy.getAnimateTextures()) 
                {
                    return MoCreatures.proxy.getTexture("horseundead.png");
                }
                String baseTex = "horseundead";
                int max = 79;

                if (rand.nextInt(3)== 0) textCounter++;
                if (textCounter < 10) textCounter = 10;
                if (textCounter > max) textCounter = 10;
                
                String iteratorTex = "" + textCounter;
                iteratorTex = iteratorTex.substring(0,1);
                String decayTex = "" + (getEdad()/100);
                decayTex = decayTex.substring(0,1);
                return MoCreatures.proxy.getTexture(baseTex + decayTex + iteratorTex + ".png");

            case 26:
                return MoCreatures.proxy.getTexture("horseskeleton.png");
            
            case 32:
                return MoCreatures.proxy.getTexture("horsebat.png");

            case 38:
                if (!MoCreatures.proxy.getAnimateTextures()) 
                {
                    return MoCreatures.proxy.getTexture("horsenightmare1.png");
                }
                textCounter++;
                if (textCounter < 10) textCounter = 10;
                if (textCounter > 59) textCounter = 10;
                String NTA = "horsenightmare";
                String NTB = "" + textCounter;
                NTB = NTB.substring(0,1);
                String NTC = ".png";

                return MoCreatures.proxy.getTexture(NTA + NTB + NTC);

            default:
                return MoCreatures.proxy.getTexture("horseundead.png");
        }
    }
    
    @Override
    protected String getDeathSound()
    {
        openMouth();
        return "mocreatures:horsedyingundead";
    }
    
    @Override
    protected String getHurtSound()
    {
        openMouth();
        stand();
        return "mocreatures:horsehurtundead";
    }

        
    @Override
    protected String getLivingSound()
    {
        openMouth();
        if (rand.nextInt(10)== 0) stand();
        return "mocreatures:horsegruntundead";
    }

    public boolean isOnAir()
    {
        return worldObj.isAirBlock(MathHelper.floor_double(posX), MathHelper.floor_double(posY - 0.2D), MathHelper.floor_double(posZ));
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if (mouthCounter > 0 && ++mouthCounter > 30)
        {
            mouthCounter = 0;
        }
        
        if (standCounter > 0 && ++standCounter > 20)
        {
            standCounter = 0;
        }
        
        if (tailCounter > 0 && ++tailCounter > 8)
        {
            tailCounter = 0;
        }
        
        if (eatingCounter > 0 && ++eatingCounter > 50)
        {
            eatingCounter = 0;
        }
        
        if (wingFlapCounter > 0 && ++wingFlapCounter > 20)
        {
            wingFlapCounter = 0;
            //TODO flap sound!
        }
    }

    /**
     * Used to flicker ghosts
     * @return
     */
    public float tFloat()
      {
        if (++this.fCounter > 60)
        {
          this.fCounter = 0;
          this.transFloat = (rand.nextFloat() * (0.6F - 0.3F) + 0.3F);
        }

        return this.transFloat;
      }

    public boolean isFlyer()
    {
        return this.getType() == 16 //pegasus
        || this.getType() == 40 // dark pegasus
        || this.getType() == 34 // fairy b
        || this.getType() == 36 // fairy p
        || this.getType() == 32 // bat horse
        || this.getType() == 21 // ghost winged
        || this.getType() == 25;// undead pegasus
    }

    /**
     * Has an unicorn? to render it and buckle entities!
     * @return
     */
    public boolean isUnicorned()
    {
        return this.getType() == 18 
        || this.getType() == 34 
        || this.getType() == 36 
        || this.getType() == 40 
        || this.getType() == 24;
    }

    /**
     * Is this a ghost horse?
     * @return
     */
    public boolean isGhost()
    {
        return this.getType() == 21 || this.getType() == 22;
    }

    public void onLivingUpdate()
    {

        super.onLivingUpdate();

        if (isOnAir() && isFlyer() && rand.nextInt(5) == 0)
        {
            wingFlapCounter = 1;
        }

        if (rand.nextInt(200)==0)
        {
            moveTail();
        }

        if (!isOnAir() && (this.riddenByEntity == null) && rand.nextInt(250)==0)
        {
            stand();
        }

        if ((getType() == 38) && (rand.nextInt(50) == 0) && !MoCreatures.isServer())
        {
            LavaFX();
        }

        if ((getType() == 23) && (rand.nextInt(50) == 0) && !MoCreatures.isServer())
        {
            UndeadFX();
        }

        if (MoCreatures.isServer())
        {
            if (isFlyer() && rand.nextInt(500) == 0)
            {
                wingFlap();
            }

            if (this.worldObj.isDaytime())
            {
                float var1 = this.getBrightness(1.0F);
                if (var1 > 0.5F && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) && this.rand.nextFloat() * 30.0F < (var1 - 0.4F) * 2.0F)
                {
                    this.setFire(8);
                }
            }

            if (!isOnAir() && (this.riddenByEntity == null) && rand.nextInt(300)==0)
            {
                setEating();
            }

            if (this.riddenByEntity == null)
            {
                List list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(4D, 3D, 4D));
                for(int i = 0; i < list.size(); i++)
                {
                    Entity entity = (Entity) list.get(i);
                    if(!(entity instanceof EntityMob))
                    {
                        continue;
                    }
                    EntityMob entitymob = (EntityMob) entity;
                    if(entitymob.ridingEntity == null && (entitymob instanceof EntitySkeleton || entitymob instanceof EntityZombie))
                    {
                        entitymob.mountEntity(this);
                        break;
                    }
                }
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

    private void setEating()
    {
        eatingCounter = 1;
    }

    private void stand()
    {
        standCounter = 1;
    }

    public void wingFlap()
    {
        wingFlapCounter = 1;
    }
    
    @Override
    protected Item getDropItem()
    {
        boolean flag = (rand.nextInt(100) < MoCreatures.proxy.rareItemDropChance);
        if (this.getType() == 32 && MoCreatures.proxy.rareItemDropChance < 25)
        {
            flag = (rand.nextInt(100) < 25);
        }

        if (flag && (this.getType() == 36 || (this.getType() >=50 && this.getType() < 60))) //unicorn
        {
            return MoCreatures.unicornhorn;
        }
        if (this.getType() == 39) //pegasus
        {
            return Items.feather;
        }
        if (this.getType() == 40) //dark pegasus
        {
            return Items.feather;
        }
        if (this.getType() == 38 && flag && worldObj.provider.isHellWorld) //nightmare
        {
            return MoCreatures.heartfire;
        }
        if (this.getType() == 32 && flag) //bat horse
        {
            return MoCreatures.heartdarkness;
        }
        if (this.getType() == 26)//skely
        {
            return Items.bone;
        }
        if ((this.getType() == 23 || this.getType() == 24 || this.getType() == 25))
        {
            if (flag)
            {
                return MoCreatures.heartundead;
            }
            return Items.rotten_flesh;
        }

        if (this.getType() == 21 || this.getType() == 22)
        {
            return Items.ghast_tear;
        }

        return Items.leather;
    }

    @Override
    protected void attackEntity(Entity par1Entity, float par2)
    {
        if (this.attackTime <= 0 && par2 < 2.5F && par1Entity.boundingBox.maxY > this.boundingBox.minY && par1Entity.boundingBox.minY < this.boundingBox.maxY)
        {
            this.attackTime = 20;
            stand();
            openMouth();
            MoCTools.playCustomSound(this, "horsemad", worldObj);
            this.attackEntityAsMob(par1Entity);
        }
    }

    @Override
    public void onDeath(DamageSource damagesource)
    {
        super.onDeath(damagesource);

        if ((this.getType() == 23) || (this.getType() == 24) || (this.getType() == 25))
        {
            MoCTools.spawnSlimes(worldObj, this);
        }
        
    }

    @Override
    public double getMountedYOffset()
    {
        return (double)this.height * 0.75D - 0.5D;
    }

    @Override
    public void updateRiderPosition()
    {
        super.updateRiderPosition();
        if (riddenByEntity == null)    return;
        ((EntityLivingBase) riddenByEntity).renderYawOffset = this.rotationYaw;
        ((EntityLivingBase) riddenByEntity).prevRenderYawOffset = this.rotationYaw;
    }

    @Override
    public boolean getCanSpawnHere()
    {
        if (posY < 50D && !worldObj.provider.isHellWorld)
        {
            setType(32);
        }
        return super.getCanSpawnHere();
    }

    public void UndeadFX()
    {
        MoCreatures.proxy.UndeadFX(this);
    }

    public void LavaFX()
    {
        MoCreatures.proxy.LavaFX(this);
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public EnumCreatureAttribute getCreatureAttribute()
    {
        if (getType() == 23) 
        {
            return EnumCreatureAttribute.UNDEAD;
        }
        return super.getCreatureAttribute();
    }
}