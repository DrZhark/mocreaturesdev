package drzhark.mocreatures.entity;

import com.google.common.base.Optional;
import drzhark.mocreatures.MoCPetData;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageHealth;
import drzhark.mocreatures.util.MoCSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

import java.util.UUID;

import javax.annotation.Nullable;

public class MoCEntityTameableAmbient extends MoCEntityAmbient implements IMoCTameable {

    protected static final DataParameter<Optional<UUID>> OWNER_UNIQUE_ID = EntityDataManager.<Optional<UUID>>createKey(MoCEntityTameableAmbient.class, DataSerializers.OPTIONAL_UNIQUE_ID);
    protected static final DataParameter<Integer> PET_ID = EntityDataManager.<Integer>createKey(MoCEntityTameableAmbient.class, DataSerializers.VARINT);
    protected static final DataParameter<Boolean> TAMED = EntityDataManager.<Boolean>createKey(MoCEntityTameableAmbient.class, DataSerializers.BOOLEAN);

    public MoCEntityTameableAmbient(World world) {
        super(world);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(OWNER_UNIQUE_ID, Optional.<UUID>absent());
        this.dataManager.register(PET_ID, Integer.valueOf(-1));
        this.dataManager.register(TAMED, Boolean.valueOf(false));
    }

    @Override
    public boolean getIsTamed() {
        return ((Boolean)this.dataManager.get(TAMED)).booleanValue();
    }
    
    @Override
    public void setTamed(boolean flag) {
        this.dataManager.set(TAMED, Boolean.valueOf(flag));
    }

    @Override
    public int getOwnerPetId() {
    	return ((Integer)this.dataManager.get(PET_ID)).intValue();
    }

    @Override
    public void setOwnerPetId(int i) {
    	this.dataManager.set(PET_ID, Integer.valueOf(i));
    }

    @Nullable
    public UUID getOwnerId() {
    	return (UUID)((Optional)this.dataManager.get(OWNER_UNIQUE_ID)).orNull();
    }

    public void setOwnerId(@Nullable UUID uniqueId)
    {
        this.dataManager.set(OWNER_UNIQUE_ID, Optional.fromNullable(uniqueId));
    }

    @Nullable
    public EntityLivingBase getOwner() {
        try
        {
            UUID uuid = this.getOwnerId();
            return uuid == null ? null : this.worldObj.getPlayerEntityByUUID(uuid);
        }
        catch (IllegalArgumentException var2)
        {
            return null;
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        Entity entity = damagesource.getEntity();
        //this avoids damage done by Players to a tamed creature that is not theirs
        if (MoCreatures.proxy.enableOwnership && this.getOwnerId() != null && entity != null
                && entity instanceof EntityPlayer && !((EntityPlayer) entity).getName().equals(this.getOwnerId())
                && !MoCTools.isThisPlayerAnOP(((EntityPlayer) entity))) {
            return false;
        }

        if (MoCreatures.isServer() && getIsTamed()) {
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageHealth(this.getEntityId(), this.getHealth()), new TargetPoint(
                    this.worldObj.provider.getDimensionType().getId(), this.posX, this.posY, this.posZ, 64));
        }
        return super.attackEntityFrom(damagesource, i);
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand, @Nullable ItemStack stack) {
        if (stack == null) {
            return super.processInteract(player, hand, stack);
        }

        //before ownership check
        if (getIsTamed() && ((stack.getItem() == MoCreatures.scrollOfOwner)) && MoCreatures.proxy.enableResetOwnership
                && MoCTools.isThisPlayerAnOP(player)) {
            if (--stack.stackSize == 0) {
                player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
            }
            if (MoCreatures.isServer()) {
                if (this.getOwnerPetId() != -1) // required since getInteger will always return 0 if no key is found
                {
                    MoCreatures.instance.mapData.removeOwnerPet(this, this.getOwnerPetId());//this.getOwnerPetId());
                }
                this.setOwnerId(null);

            }
            return true;
        }
        //if the player interacting is not the owner, do nothing!
        if (MoCreatures.proxy.enableOwnership && this.getOwnerId() != null
                && !player.getName().equals(this.getOwnerId()) && !MoCTools.isThisPlayerAnOP(player)) {
            return true;
        }

        //changes name
        if (MoCreatures.isServer() && getIsTamed()
                && (stack.getItem() == MoCreatures.medallion || stack.getItem() == Items.BOOK || stack.getItem() == Items.NAME_TAG)) {
            if (MoCTools.tameWithName(player, this)) {
                return true;
            }
            return false;
        }

        //sets it free, untamed
        if (getIsTamed() && stack.getItem() == MoCreatures.scrollFreedom) {
            if (--stack.stackSize == 0) {
                player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
            }
            if (MoCreatures.isServer()) {
                if (this.getOwnerPetId() != -1) // required since getInteger will always return 0 if no key is found
                {
                    MoCreatures.instance.mapData.removeOwnerPet(this, this.getOwnerPetId());//this.getOwnerPetId());
                }
                this.setOwnerId(null);
                this.setPetName("");
                this.dropMyStuff();
                this.setTamed(false);
            }

            return true;
        }

        //removes owner, any other player can claim it by renaming it
        if (getIsTamed() && stack.getItem() == MoCreatures.scrollOfSale) {
            if (--stack.stackSize == 0) {
                player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
            }
            if (MoCreatures.isServer()) {
                if (this.getOwnerPetId() != -1) // required since getInteger will always return 0 if no key is found
                {
                    MoCreatures.instance.mapData.removeOwnerPet(this, this.getOwnerPetId());//this.getOwnerPetId());
                }
                this.setOwnerId(null);
            }
            return true;
        }

        if (getIsTamed() && isMyHealFood(stack)) {
            if (--stack.stackSize == 0) {
                player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
            }
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_EATING);
            if (!this.worldObj.isRemote) {
                this.setHealth(getMaxHealth());
            }
            return true;
        }

        //stores in fishnet
        if (stack.getItem() == MoCreatures.fishnet && stack.getItemDamage() == 0 && this.canBeTrappedInNet()) {
            player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
            if (!this.worldObj.isRemote) {
                MoCPetData petData = MoCreatures.instance.mapData.getPetData(this.getOwnerId());
                if (petData != null) {
                    petData.setInAmulet(this.getOwnerPetId(), true);
                }
                MoCTools.dropAmulet(this, 1, player);
                this.isDead = true;
            }

            return true;
        }

        //heals
        if (getIsTamed() && isMyHealFood(stack)) {
            if (--stack.stackSize == 0) {
                player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
            }
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_EATING);
            if (MoCreatures.isServer()) {
                this.setHealth(getMaxHealth());
            }
            return true;
        }

        if (getIsTamed() && stack.getItem() == Items.SHEARS) {
            if (MoCreatures.isServer()) {
                dropMyStuff();
            }

            return true;
        }

        return super.processInteract(player, hand, stack);
    }

    // Fixes despawn issue when chunks unload and duplicated mounts when disconnecting on servers
    @Override
    public void setDead() {
        if (MoCreatures.isServer() && getIsTamed() && getHealth() > 0 && !this.riderIsDisconnecting) {
            return;
        }
        super.setDead();
    }

    /**
     * Play the taming effect, will either be hearts or smoke depending on
     * status
     */
    @Override
    public void playTameEffect(boolean par1) {
        EnumParticleTypes particleType = EnumParticleTypes.HEART;

        if (!par1) {
            particleType = EnumParticleTypes.SMOKE_NORMAL;
        }

        for (int i = 0; i < 7; ++i) {
            double d0 = this.rand.nextGaussian() * 0.02D;
            double d1 = this.rand.nextGaussian() * 0.02D;
            double d2 = this.rand.nextGaussian() * 0.02D;
            this.worldObj.spawnParticle(particleType, this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY + 0.5D
                    + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, d0, d1, d2);
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("Tamed", getIsTamed());
        if (this.getOwnerId() != null) {
            nbttagcompound.setString("OwnerUUID", this.getOwnerId().toString());
        }
        if (getOwnerPetId() != -1) {
            nbttagcompound.setInteger("PetId", this.getOwnerPetId());
        }
        if (this instanceof IMoCTameable && getIsTamed() && MoCreatures.instance.mapData != null) {
            MoCreatures.instance.mapData.updateOwnerPet(this);
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        setTamed(nbttagcompound.getBoolean("Tamed"));
        String s = "";
        if (nbttagcompound.hasKey("OwnerUUID", 8))
        {
            s = nbttagcompound.getString("OwnerUUID");
        }
        if (!s.isEmpty())
        {
            this.setOwnerId(UUID.fromString(s));
        }
        if (nbttagcompound.hasKey("PetId")) {
            setOwnerPetId(nbttagcompound.getInteger("PetId"));
        }
        if (this.getIsTamed() && nbttagcompound.hasKey("PetId")) {
            MoCPetData petData = MoCreatures.instance.mapData.getPetData(this.getOwnerId());
            if (petData != null) {
                NBTTagList tag = petData.getOwnerRootNBT().getTagList("TamedList", 10);
                for (int i = 0; i < tag.tagCount(); i++) {
                    NBTTagCompound nbt = tag.getCompoundTagAt(i);
                    if (nbt.getInteger("PetId") == nbttagcompound.getInteger("PetId")) {
                        // update amulet flag
                        nbt.setBoolean("InAmulet", false);
                        // check if cloned and if so kill
                        if (nbt.hasKey("Cloned")) {
                            // entity was cloned
                            nbt.removeTag("Cloned"); // clear flag
                            this.setTamed(false);
                            this.setDead();
                        }
                    }
                }
            } else // no pet data was found, mocreatures.dat could of been deleted so reset petId to -1
            {
                this.setOwnerPetId(-1);
            }
        }
    }

    @Override
    public float getPetHealth() {
        return this.getHealth();
    }

    @Override
    public boolean isRiderDisconnecting() {
        return this.riderIsDisconnecting;
    }

    @Override
    public void setRiderDisconnecting(boolean flag) {
        this.riderIsDisconnecting = flag;
    }

    @Override
    public boolean canBeLeashedTo(EntityPlayer player) {
        return this.getIsTamed();
    }

    /**
     * Used to spawn hearts at this location
     */
    @Override
    public void spawnHeart() {
        double var2 = this.rand.nextGaussian() * 0.02D;
        double var4 = this.rand.nextGaussian() * 0.02D;
        double var6 = this.rand.nextGaussian() * 0.02D;

        this.worldObj.spawnParticle(EnumParticleTypes.HEART, this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width, this.posY + 0.5D
                + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width, var2, var4, var6);
    }

	@Override
	public boolean readytoBreed() {
		return false;
	}
}
