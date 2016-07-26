package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.EntityAIFleeFromPlayer;
import drzhark.mocreatures.entity.ai.EntityAIFollowAdult;
import drzhark.mocreatures.entity.ai.EntityAIFollowOwnerPlayer;
import drzhark.mocreatures.entity.ai.EntityAIHunt;
import drzhark.mocreatures.entity.ai.EntityAIPanicMoC;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class MoCEntityRaccoon extends MoCEntityTameableAnimal {

    public MoCEntityRaccoon(World world) {
        super(world);
        setSize(0.5F, 0.6F);
        this.texture = "raccoon.png";
        setEdad(50 + this.rand.nextInt(15));

        if (this.rand.nextInt(3) == 0) {
            setAdult(false);

        } else {
            setAdult(true);
        }
        ((PathNavigateGround) this.getNavigator()).setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIPanicMoC(this, 1.0D));
        this.tasks.addTask(3, new EntityAIFleeFromPlayer(this, 1.0D, 4D));
        this.tasks.addTask(3, new EntityAIFollowOwnerPlayer(this, 0.8D, 2F, 10F));
        this.tasks.addTask(4, new EntityAIFollowAdult(this, 1.0D));
        this.tasks.addTask(5, new EntityAIAttackOnCollide(this, 1.0D, true));
        this.tasks.addTask(6, new EntityAIWanderMoC2(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.targetTasks.addTask(1, new EntityAIHunt(this, EntityAnimal.class, true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (super.attackEntityFrom(damagesource, i)) {
            Entity entity = damagesource.getEntity();
            if ((this.riddenByEntity == entity) || (this.getRidingEntity() == entity)) {
                return true;
            }
            if (entity != this && this.isNotScared() && entity instanceof EntityLivingBase && super.shouldAttackPlayers()) {
                setAttackTarget((EntityLivingBase) entity);
                setRevengeTarget((EntityLivingBase) entity);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean interact(EntityPlayer entityplayer) {
        if (super.interact(entityplayer)) {
            return false;
        }
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if ((itemstack != null) && (MoCTools.isItemEdible(itemstack.getItem()))) //((itemstack.getItem() == MoCreatures.rawTurkey.itemID)))
        {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }

            if (MoCreatures.isServer()) {
                MoCTools.tameWithName(entityplayer, this);
            }
            this.setHealth(getMaxHealth());

            if (MoCreatures.isServer() && !getIsAdult() && (getEdad() < 100)) {
                setEdad(getEdad() + 1);
            }

            return true;
        }
        return false;
    }

    @Override
    protected String getDeathSound() {
        return "mocreatures:raccoondying";
    }

    @Override
    protected Item getDropItem() {
        return MoCreatures.fur;
    }

    @Override
    protected String getHurtSound() {
        return "mocreatures:raccoonhurt";
    }

    @Override
    protected String getLivingSound() {
        return "mocreatures:raccoongrunt";
    }

    @Override
    public int nameYOffset() {
        return -30;
    }

    @Override
    public float getSizeFactor() {
        if (getIsAdult()) {
            return 0.85F;
        }
        return 0.85F * getEdad() * 0.01F;
    }

    @Override
    public int getTalkInterval() {
        return 400;
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 2;
    }

    @Override
    public boolean isNotScared() {
        return getIsAdult();
    }

    @Override
    public boolean canAttackTarget(EntityLivingBase entity) {
        return !(entity instanceof MoCEntityRaccoon) && super.canAttackTarget(entity);
    }

    @Override
    public boolean isReadyToHunt() {
        return this.getIsAdult() && !this.isMovementCeased();
    }
}
