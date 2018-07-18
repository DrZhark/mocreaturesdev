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
import drzhark.mocreatures.init.MoCItems;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class MoCEntityFox extends MoCEntityTameableAnimal {

    public MoCEntityFox(World world) {
        super(world);
        setSize(0.6F, 0.7F);
        setEdad(this.rand.nextInt(15) + 50);
        if (this.rand.nextInt(3) == 0) {
            setAdult(false);

        } else {
            setAdult(true);
        }
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIPanicMoC(this, 1.0D));
        this.tasks.addTask(3, new EntityAIFleeFromPlayer(this, 1.0D, 4D));
        this.tasks.addTask(3, new EntityAIFollowOwnerPlayer(this, 0.8D, 2F, 10F));
        this.tasks.addTask(4, new EntityAIFollowAdult(this, 1.0D));
        this.tasks.addTask(5, new EntityAIAttackMelee(this, 1.0D, true));
        this.tasks.addTask(6, new EntityAIWanderMoC2(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.targetTasks.addTask(1, new EntityAIHunt(this, EntityAnimal.class, true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15.0D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
    }

    @Override
    public void selectType() {
        checkSpawningBiome();

        if (getType() == 0) {
            setType(1);
        }
    }

    @Override
    public ResourceLocation getTexture() {

        if (!getIsAdult()) {
            if (getType() == 2) {
                return MoCreatures.proxy.getTexture("foxsnow.png");
            }
            return MoCreatures.proxy.getTexture("foxcub.png");
        }
        switch (getType()) {
            case 1:
                return MoCreatures.proxy.getTexture("fox.png");
            case 2:
                return MoCreatures.proxy.getTexture("foxsnow.png");

            default:
                return MoCreatures.proxy.getTexture("fox.png");
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (super.attackEntityFrom(damagesource, i)) {
            Entity entity = damagesource.getTrueSource();
            if (this.isRidingOrBeingRiddenBy(entity)) {
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
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        final Boolean tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        final ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && ((stack.getItem() == MoCItems.rawTurkey))) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }

            if (!this.world.isRemote) {
                MoCTools.tameWithName(player, this);
            }
            this.setHealth(getMaxHealth());

            if (!this.world.isRemote && !getIsAdult() && (getEdad() < 100)) {
                setEdad(getEdad() + 1);
            }

            return true;
        }

        return super.processInteract(player, hand);
    }

    @Override
    public boolean isNotScared() {
        return getIsAdult();
    }

    @Override
    public boolean checkSpawningBiome() {
        BlockPos pos =
                new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(getEntityBoundingBox().minY),
                        MathHelper.floor(this.posZ));
        Biome currentbiome = MoCTools.Biomekind(this.world, pos);
        try {
            if (BiomeDictionary.hasType(currentbiome, Type.SNOWY)) {
                setType(2);
            }
        } catch (Exception e) {
        }
        return true;
    }

    @Override
    protected Item getDropItem() {
        return MoCItems.fur;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_FOX_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_FOX_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return MoCSoundEvents.ENTITY_FOX_AMBIENT;
    }

    @Override
    protected float getSoundVolume() {
        return 0.3F;
    }

    @Override
    public boolean isMyHealFood(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() == MoCItems.ratRaw;
    }

    @Override
    public int nameYOffset() {
        return -50;
    }

    @Override
    public boolean canAttackTarget(EntityLivingBase entity) {
        return !(entity instanceof MoCEntityFox) && entity.height <= 0.7D && entity.width <= 0.7D;
    }

    @Override
    public boolean isReadyToHunt() {
        return this.getIsAdult() && !this.isMovementCeased();
    }

    @Override
    public float getSizeFactor() {
        if (getIsAdult()) {
            return 0.9F;
        }
        return 0.9F * getEdad() * 0.01F;
    }
}
