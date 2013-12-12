package drzhark.mocreatures.entity.monster;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.network.MoCServerPacketHandler;

public class MoCEntityOgre extends MoCEntityMob{

    public int frequencyA;
    public int attackCounterLeft;
    public int attackCounterRight;
    private int movingHead;
    public boolean pendingSmashAttack;

    public MoCEntityOgre(World world)
    {
        super(world);
        setSize(1.9F, 3F);
        isImmuneToFire = false;
        frequencyA = 30;
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(getType() > 4 ? 50.0D : 35.0D);
    }

    @Override
    protected double getAttackStrenght() 
    {
        return 3D;
    }

    @Override
    public void selectType()
    {
        if (worldObj.provider.isHellWorld)
        {
            setType(rand.nextInt(2)+3);
            this.setHealth(getMaxHealth());
            this.isImmuneToFire = true;

        }else
        {
            if (getType() == 0)
            {
                int fOgreChance = MoCreatures.proxy.fireOgreChance;
                int cOgreChance = MoCreatures.proxy.caveOgreChance;
                int j = rand.nextInt(100);
                
                if (canCaveOgreSpawn() && (j >= (100 - cOgreChance)))
                {    //System.out.println("can spawn cave o");
                    setType(rand.nextInt(2)+5);
                }
                else if (j >= (100 - fOgreChance))
                {
                    setType(rand.nextInt(2)+3);
                    this.isImmuneToFire = true;
                }
                else
                {
                    setType(rand.nextInt(2)+1);
                }

                this.setHealth(getMaxHealth());
            }
        }
    }

    @Override
    public ResourceLocation getTexture()
    {
        switch (getType())
        {
        case 1: 
        case 2:
            return MoCreatures.proxy.getTexture("ogregreen.png");
        case 3: 
        case 4:
            return MoCreatures.proxy.getTexture("ogrered.png");
        case 5: 
        case 6: 
            return MoCreatures.proxy.getTexture("ogreblue.png");
        default:
            return MoCreatures.proxy.getTexture("ogregreen.png");
        }
    }

    @Override
    protected void attackEntity(Entity entity, float f)
    {
        if (attackTime <= 0 && (f < 2.5F) && (entity.boundingBox.maxY > boundingBox.minY) && (entity.boundingBox.minY < boundingBox.maxY) && (worldObj.difficultySetting > 0))
        {
            attackTime = 20;
            this.attackEntityAsMob(entity);
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i)
    {
        if (super.attackEntityFrom(damagesource, i))
        {
            Entity entity = damagesource.getEntity();
            if ((riddenByEntity == entity) || (ridingEntity == entity)) { return true; }
            if ((entity != this) && (worldObj.difficultySetting > 0))
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

    public void DestroyingOgre()
    {
        if (this.deathTime > 0) { return; }
        MoCTools.DestroyBlast(this, posX, posY + 1.0D, posZ, getDestroyForce(), getOgreFire());
    }

    @Override
    protected Entity findPlayerToAttack()
    {
        float f = getBrightness(1.0F);
        if (f < 0.5F)
        {
            EntityPlayer entityplayer = worldObj.getClosestVulnerablePlayerToEntity(this, getAttackRange());
            if ((entityplayer != null) && (worldObj.difficultySetting > 0)) { return entityplayer; }
        }
        return null;
    }

    @Override
    protected String getDeathSound()
    {
        return "mocreatures:ogredying";
    }

    @Override
    protected int getDropItemId()
    {
        if (getType() < 3)
        {
        return Block.obsidian.blockID;
        }
        else if (getType() < 5)
        {
             boolean flag = (rand.nextInt(4)==0);
             if (!flag) 
                 {
                    return Block.fire.blockID;
                }
             return MoCreatures.heartfire.itemID;
        }
        return Item.diamond.itemID;
    }

    @Override
    protected String getHurtSound()
    {
        return "mocreatures:ogrehurt";
    }

    @Override
    protected String getLivingSound()
    {
        return "mocreatures:ogre";
    }

    public boolean getOgreFire()
    {
        if(getType() == 3 || getType() == 4)
        {
            this.isImmuneToFire = true;
            return true;
        }
        return false;
    }

    public float getDestroyForce()
    {
        int t = getType();
        if (t < 3) //green
        {
            return MoCreatures.proxy.ogreStrength;
        }else if (t < 5) //red
        {
            return MoCreatures.proxy.fireOgreStrength;
        }
        return MoCreatures.proxy.caveOgreStrength;
    }

    public int getAttackRange()
    {
        return MoCreatures.proxy.ogreAttackRange;
    }

    @Override
    public void onLivingUpdate()
    {
        if (MoCreatures.isServer())
        {
            
            if ((entityToAttack != null) && (rand.nextInt(frequencyA) == 0) && attackTime == 0 && attackCounterLeft == 0 && attackCounterRight == 0)
            {
                startOgreAttack();
            }
            
            if ((attackTime <= 0) && pendingSmashAttack)
            {
                pendingSmashAttack = false;
                DestroyingOgre();
                MoCServerPacketHandler.sendExplodePacket(this.entityId, (this.worldObj.provider.dimensionId));
            }

            if (getType() > 2)
            {
                
            
                if (worldObj.isDaytime())
                {
                    float f = getBrightness(1.0F);
                    if ((f > 0.5F) && worldObj.canBlockSeeTheSky(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)) && ((rand.nextFloat() * 30F) < ((f - 0.4F) * 2.0F)))
                    {
                        this.setHealth(getHealth() - 5);
                    }
                }
            }
        }

        if (attackCounterLeft > 0 && ++attackCounterLeft > 30)
        {
            attackCounterLeft = 0;
        }
        
        if (attackCounterRight > 0 && ++attackCounterRight > 30)
        {
            attackCounterRight = 0;
        }
        super.onLivingUpdate();
    }

    /**
     * Starts attack counters and synchronizes animations with clients
     */
    private void startOgreAttack() 
    {
        if (MoCreatures.isServer())
        {
            attackTime = 15;
            pendingSmashAttack = true;
            boolean leftArmW = (getType() == 2 || getType() == 4 || getType() == 6) && rand.nextInt(2) == 0;

            if (leftArmW)
            {
                attackCounterLeft = 1;
                MoCServerPacketHandler.sendAnimationPacket(this.entityId, this.worldObj.provider.dimensionId, 1);
            }
            else
            {
                attackCounterRight = 1;
                MoCServerPacketHandler.sendAnimationPacket(this.entityId, this.worldObj.provider.dimensionId, 2);
            }
        }
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

    public int getMovingHead()
    {
        if (getType() == 1 || getType() == 3 || getType() == 5) //single headed ogre
        {
            return 1;
        }

        if (rand.nextInt(100) == 0)
        {
            movingHead = rand.nextInt(2)+2;  //randomly changes the focus head, returns 2 or 3
        }
        return movingHead;
    }

    private boolean canCaveOgreSpawn()
    {
        return (!worldObj.canBlockSeeTheSky(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ))) && (posY < 50D);
    }
}