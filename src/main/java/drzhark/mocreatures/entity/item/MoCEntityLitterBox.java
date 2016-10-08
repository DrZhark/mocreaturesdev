package drzhark.mocreatures.entity.item;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.monster.MoCEntityOgre;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.List;

import javax.annotation.Nullable;

public class MoCEntityLitterBox extends EntityLiving {

    public int littertime;
    private static final DataParameter<Boolean> PICKED_UP = EntityDataManager.<Boolean>createKey(MoCEntityLitterBox.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> USED_LITTER = EntityDataManager.<Boolean>createKey(MoCEntityLitterBox.class, DataSerializers.BOOLEAN);

    public MoCEntityLitterBox(World world) {
        super(world);
        setSize(1.0F, 0.3F);
    }

    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getTexture("litterbox.png");
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D); // setMaxHealth
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(PICKED_UP, Boolean.valueOf(false));
        this.dataManager.register(USED_LITTER, Boolean.valueOf(false));
    }

    public boolean getPickedUp() {
    	return ((Boolean)this.dataManager.get(PICKED_UP)).booleanValue();
    }

    public boolean getUsedLitter() {
    	return ((Boolean)this.dataManager.get(USED_LITTER)).booleanValue();
    }

    public void setPickedUp(boolean flag) {
    	this.dataManager.set(PICKED_UP, Boolean.valueOf(flag));
    }

    public void setUsedLitter(boolean flag) {
    	this.dataManager.set(USED_LITTER, Boolean.valueOf(flag));
    }

    public boolean attackEntityFrom(Entity entity, int i) {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }

    @Override
    public boolean canBePushed() {
        return !this.isDead;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Override
    public void fall(float f, float f1) {
    }

    @Override
    protected float getSoundVolume() {
        return 0.0F;
    }

    @Override
    public double getYOffset() {
        // If we are in SMP, do not alter offset on any client other than the player being mounted on
        if (((this.getRidingEntity() instanceof EntityPlayer) && !this.worldObj.isRemote) || this.getRidingEntity() == MoCreatures.proxy.getPlayer())//MoCProxy.mc().thePlayer)
        {
            setPickedUp(true);
            return ((EntityPlayer) this.getRidingEntity()).isSneaking() ? 0.25 : 0.5F;
        }
        return super.getYOffset();

    }

    @Override
    public void handleStatusUpdate(byte byte0) {
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand, @Nullable ItemStack stack) {
        if ((stack != null) && ((stack.getItem() == Items.STONE_PICKAXE) || (stack.getItem() == Items.WOODEN_PICKAXE)
                        || (stack.getItem() == Items.IRON_PICKAXE) || (stack.getItem() == Items.GOLDEN_PICKAXE) || (stack.getItem() == Items.DIAMOND_PICKAXE))) {
            player.inventory.addItemStackToInventory(new ItemStack(MoCreatures.litterbox));
            this.playSound(SoundEvents.ENTITY_ITEM_PICKUP, 0.2F, (((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F) + 1.0F) * 2.0F);
            setDead();
            return true;
        }

        if ((stack != null) && (stack.getItem() == Item.getItemFromBlock(Blocks.SAND))) {
            if (--stack.stackSize == 0) {
                player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
            }
            setUsedLitter(false);
            this.littertime = 0;
            return true;
        } else {
            if (this.getRidingEntity() == null) {
            	this.rotationYaw = player.rotationYaw;
                this.startRiding(player);
            }return true;
        }
    }

    @Override
    public void moveEntity(double d, double d1, double d2) {
        if ((this.getRidingEntity() != null) || !this.onGround || !MoCreatures.proxy.staticLitter) {
            if (!this.worldObj.isRemote) {
                super.moveEntity(d, d1, d2);
            }
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (this.onGround) {
            setPickedUp(false);
        }
        if (getUsedLitter() && MoCreatures.isServer()) {
            this.littertime++;
            this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
            List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(12D, 4D, 12D));
            for (int i = 0; i < list.size(); i++) {
                Entity entity = list.get(i);
                if (!(entity instanceof EntityMob)) {
                    continue;
                }
                EntityMob entitymob = (EntityMob) entity;
                entitymob.setAttackTarget(this);
                if (entitymob instanceof EntityCreeper) {
                    ((EntityCreeper) entitymob).setCreeperState(-1);
                }
                if (entitymob instanceof MoCEntityOgre) {
                    ((MoCEntityOgre) entitymob).smashCounter = 0;
                }
            }

        }
        if (this.littertime > 5000 && MoCreatures.isServer()) {
            setUsedLitter(false);
            this.littertime = 0;
        }
        
        if (this.isRiding()) MoCTools.dismountSneakingPlayer(this);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        nbttagcompound = MoCTools.getEntityData(this);
        nbttagcompound.setBoolean("UsedLitter", getUsedLitter());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        nbttagcompound = MoCTools.getEntityData(this);
        setUsedLitter(nbttagcompound.getBoolean("UsedLitter"));
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        return false;
    }
}
