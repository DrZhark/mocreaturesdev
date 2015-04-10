package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import drzhark.mocreatures.entity.ai.EntityAIFleeFromPlayer;
import drzhark.mocreatures.entity.ai.EntityAIFollowAdult;
import drzhark.mocreatures.entity.ai.EntityAIHunt;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoCEntityBoar extends MoCEntityAnimal {

    private boolean isHungry;
    private int hungryCounter;

    public MoCEntityBoar(World world) {
        super(world);
        setSize(0.9F, 0.8F);
        setEdad(this.rand.nextInt(15) + 45);
        if (this.rand.nextInt(4) == 0) {
            setAdult(false);

        } else {
            setAdult(true);
        }
        ((PathNavigateGround) this.getNavigator()).setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIFleeFromPlayer(this, 1.0D, 4D));
        this.tasks.addTask(3, new EntityAIFollowAdult(this, 1.0D));
        this.tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0D, true));
        this.tasks.addTask(7, new EntityAIWanderMoC2(this, 1.0D));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.targetTasks.addTask(1, new EntityAIHunt(this, EntityAnimal.class, true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
    }

    @Override
    public ResourceLocation getTexture() {
        if (getIsAdult()) {
            return MoCreatures.proxy.getTexture("boara.png");
        }
        return MoCreatures.proxy.getTexture("boarb.png");

    }

    @Override
    protected boolean canDespawn() {
        if (MoCreatures.proxy.forceDespawns) {
            return !getIsTamed();
        } else {
            return false;
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (super.attackEntityFrom(damagesource, i)) {
            Entity entity = damagesource.getEntity();
            if ((this.riddenByEntity == entity) || (this.ridingEntity == entity)) {
                return true;
            }
            if (entity != this && entity instanceof EntityLivingBase && super.shouldAttackPlayers() && getIsAdult()) {
                setAttackTarget((EntityLivingBase) entity);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isNotScared() {
        return getIsAdult();
    }

    @Override
    protected Item getDropItem() {

        if (this.rand.nextInt(2) == 0) {
            return Items.porkchop;
        }

        return MoCreatures.animalHide;
    }

    @Override
    protected String getLivingSound() {
        return "mob.pig.say";
    }

    @Override
    protected String getHurtSound() {
        return "mob.pig.say";
    }

    @Override
    protected String getDeathSound() {
        return "mob.pig.death";
    }

    @Override
    public boolean canAttackTarget(EntityLivingBase entity) {
        return !(entity instanceof MoCEntityBoar) && super.canAttackTarget(entity);
    }

    @Override
    public boolean isReadyToHunt() {
        return this.getIsAdult() && !this.isMovementCeased();
    }

    @Override
    public float getSizeFactor() {
        if (getIsAdult()) {
            return 1F;
        }
        return getEdad() * 0.01F;
    }

}
