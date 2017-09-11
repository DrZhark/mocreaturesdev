package drzhark.mocreatures.entity.aquatic;

import com.google.common.base.Predicate;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAquatic;
import drzhark.mocreatures.entity.ai.EntityAIFleeFromEntityMoC;
import drzhark.mocreatures.entity.ai.EntityAIPanicMoC;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.init.MoCItems;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageHeart;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

import java.util.List;

public class MoCEntityFishy extends MoCEntityTameableAquatic {

    public int gestationtime;

    public static final String fishNames[] = {"Blue", "Orange", "Cyan", "Greeny", "Green", "Purple", "Yellow", "Striped", "Yellowy", "Red"};
    private static final DataParameter<Boolean> HAS_EATEN = EntityDataManager.<Boolean>createKey(MoCEntityFishy.class, DataSerializers.BOOLEAN);
    
    public MoCEntityFishy(World world) {
        super(world);
        setSize(0.3F, 0.3F);
        setEdad(50 + this.rand.nextInt(50));
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(2, new EntityAIPanicMoC(this, 1.3D));
        this.tasks.addTask(3, new EntityAIFleeFromEntityMoC(this, new Predicate<Entity>() {

            public boolean apply(Entity entity) {
                return (entity.height > 0.3F || entity.width > 0.3F);
            }
        }, 2.0F, 0.6D, 1.5D));
        this.tasks.addTask(5, new EntityAIWanderMoC2(this, 1.0D, 80));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
    }

    @Override
    public void selectType() {
        if (getType() == 0) {
            setType(this.rand.nextInt(fishNames.length) + 1);
        }
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getType()) {
            case 1:
                return MoCreatures.proxy.getTexture("fishy1.png");
            case 2:
                return MoCreatures.proxy.getTexture("fishy2.png");
            case 3:
                return MoCreatures.proxy.getTexture("fishy3.png");
            case 4:
                return MoCreatures.proxy.getTexture("fishy4.png");
            case 5:
                return MoCreatures.proxy.getTexture("fishy5.png");
            case 6:
                return MoCreatures.proxy.getTexture("fishy6.png");
            case 7:
                return MoCreatures.proxy.getTexture("fishy7.png");
            case 8:
                return MoCreatures.proxy.getTexture("fishy8.png");
            case 9:
                return MoCreatures.proxy.getTexture("fishy9.png");
            case 10:
                return MoCreatures.proxy.getTexture("fishy10.png");
            default:
                return MoCreatures.proxy.getTexture("fishy1.png");
        }
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(HAS_EATEN, Boolean.valueOf(false));
    }

    public boolean getHasEaten() {
        return ((Boolean)this.dataManager.get(HAS_EATEN)).booleanValue();
    }

    public void setHasEaten(boolean flag) {
        this.dataManager.set(HAS_EATEN, Boolean.valueOf(flag));
    }
    
    @Override
    protected void dropFewItems(boolean flag, int x) {
        int i = this.rand.nextInt(100);
        if (i < 70) {
            entityDropItem(new ItemStack(Items.FISH, 1, 0), 0.0F);
        } else {
            int j = this.rand.nextInt(2);
            for (int k = 0; k < j; k++) {
                entityDropItem(new ItemStack(MoCItems.mocegg, 1, getType()), 0.0F);
            }

        }
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (!this.isInsideOfMaterial(Material.WATER)) {
            this.prevRenderYawOffset = this.renderYawOffset = this.rotationYaw = this.prevRotationYaw;
            this.rotationPitch = this.prevRotationPitch;
        }

        if (!this.world.isRemote) {

            if (getIsTamed() && this.rand.nextInt(100) == 0 && getHealth() < getMaxHealth()) {
                this.setHealth(getMaxHealth());
            }

            if (!ReadyforParenting(this)) {
                return;
            }
            int i = 0;
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(4D, 3D, 4D));
            for (int j = 0; j < list.size(); j++) {
                Entity entity = list.get(j);
                if (entity instanceof MoCEntityFishy) {
                    i++;
                }
            }

            if (i > 1) {
                return;
            }
            List<Entity> list1 = this.world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(4D, 2D, 4D));
            for (int k = 0; k < list.size(); k++) {
                Entity entity1 = list1.get(k);
                if (!(entity1 instanceof MoCEntityFishy) || (entity1 == this)) {
                    continue;
                }
                MoCEntityFishy entityfishy = (MoCEntityFishy) entity1;
                if (!ReadyforParenting(this) || !ReadyforParenting(entityfishy) || (this.getType() != entityfishy.getType())) {
                    continue;
                }
                if (this.rand.nextInt(100) == 0) {
                    this.gestationtime++;
                }
                if (this.gestationtime % 3 == 0) {
                    MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageHeart(this.getEntityId()),
                            new TargetPoint(this.world.provider.getDimensionType().getId(), this.posX, this.posY, this.posZ, 64));
                }
                if (this.gestationtime <= 50) {
                    continue;
                }
                int l = this.rand.nextInt(3) + 1;
                for (int i1 = 0; i1 < l; i1++) {
                    MoCEntityFishy entityfishy1 = new MoCEntityFishy(this.world);
                    entityfishy1.setPosition(this.posX, this.posY, this.posZ);
                    this.world.spawnEntity(entityfishy1);
                    MoCTools.playCustomSound(this, SoundEvents.ENTITY_CHICKEN_EGG);
                    setHasEaten(false);
                    entityfishy.setHasEaten(false);
                    this.gestationtime = 0;
                    entityfishy.gestationtime = 0;

                    EntityPlayer entityplayer = this.world.getClosestPlayerToEntity(this, 24D);
                    if (entityplayer != null) {
                        MoCTools.tameWithName(entityplayer, entityfishy1);
                    }

                    entityfishy1.setEdad(20);
                    entityfishy1.setAdult(false);
                    entityfishy1.setTypeInt(getType());
                }

                break;
            }
        }

    }

    public boolean ReadyforParenting(MoCEntityFishy entityfishy) {
        return false; //TOOD pending overhaul of breeding
    }

    @Override
    protected boolean canBeTrappedInNet() {
        return true;
    }

    @Override
    public int nameYOffset() {
        return -25;
    }

    @Override
    public float rollRotationOffset() {
        if (!this.isInsideOfMaterial(Material.WATER)) {
            return -90F;
        }
        return 0F;
    }

    @Override
    protected boolean isFisheable() {
        return !getIsTamed();
    }

    @Override
    protected boolean usesNewAI() {
        return true;
    }

    @Override
    public float getAIMoveSpeed() {
        return 0.10F;
    }

    @Override
    public boolean isMovementCeased() {
        return !isInWater();
    }

    @Override
    protected double minDivingDepth() {
        return 0.2D;
    }

    @Override
    protected double maxDivingDepth() {
        return 2.0D;
    }

    @Override
    public float getSizeFactor() {
        return getEdad() * 0.01F;
    }
    
    @Override
    public float getAdjustedZOffset() {
        return 0F;
    }

    @Override
    public float getAdjustedXOffset() {
        if (!isInWater()) {
            return -0.1F;
        }
        return 0F;
    }
    
    @Override
    public float getAdjustedYOffset() {
        if (!this.isInsideOfMaterial(Material.WATER)) {
            return 0.2F;
        }
        return -0.5F;
    }
}
