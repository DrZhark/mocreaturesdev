package drzhark.mocreatures.entity.aquatic;

import com.google.common.base.Predicate;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAquatic;
import drzhark.mocreatures.entity.ai.EntityAIFleeFromEntityMoC;
import drzhark.mocreatures.entity.ai.EntityAIPanicMoC;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageHeart;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

import java.util.List;

public class MoCEntityFishy extends MoCEntityTameableAquatic {

    public int gestationtime;

    public static final String fishNames[] = {"Blue", "Orange", "Cyan", "Greeny", "Green", "Purple", "Yellow", "Striped", "Yellowy", "Red"};

    public MoCEntityFishy(World world) {
        super(world);
        setSize(0.3F, 0.3F);
        setEdad(50 + this.rand.nextInt(50));
        this.tasks.addTask(2, new EntityAIPanicMoC(this, 1.3D));
        this.tasks.addTask(3, new EntityAIFleeFromEntityMoC(this, new Predicate() {

            public boolean apply(Entity entity) {
                return (entity.height > 0.3F || entity.width > 0.3F);
            }

            @Override
            public boolean apply(Object p_apply_1_) {
                return this.apply((Entity) p_apply_1_);
            }
        }, 2.0F, 0.6D, 1.5D));
        this.tasks.addTask(5, new EntityAIWanderMoC2(this, 1.0D, 80));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(6.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
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
        this.dataWatcher.addObject(23, Byte.valueOf((byte) 0)); // byte hasEaten 0 = false 1 = true
    }

    public boolean getHasEaten() {
        return (this.dataWatcher.getWatchableObjectByte(23) == 1);
    }

    public void setHasEaten(boolean flag) {
        if (this.worldObj.isRemote) {
            return;
        }
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(23, Byte.valueOf(input));
    }

    @Override
    protected void dropFewItems(boolean flag, int x) {
        int i = this.rand.nextInt(100);
        if (i < 70) {
            entityDropItem(new ItemStack(Items.fish, 1, 0), 0.0F);
        } else {
            int j = this.rand.nextInt(2);
            for (int k = 0; k < j; k++) {
                entityDropItem(new ItemStack(MoCreatures.mocegg, 1, getType()), 0.0F);
            }

        }
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (!this.isInsideOfMaterial(Material.water)) {
            this.prevRenderYawOffset = this.renderYawOffset = this.rotationYaw = this.prevRotationYaw;
            this.rotationPitch = this.prevRotationPitch;
        }

        if (MoCreatures.isServer()) {

            if (getIsTamed() && this.rand.nextInt(100) == 0 && getHealth() < getMaxHealth()) {
                this.setHealth(getMaxHealth());
            }

            if (!ReadyforParenting(this)) {
                return;
            }
            int i = 0;
            List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(4D, 3D, 4D));
            for (int j = 0; j < list.size(); j++) {
                Entity entity = (Entity) list.get(j);
                if (entity instanceof MoCEntityFishy) {
                    i++;
                }
            }

            if (i > 1) {
                return;
            }
            List list1 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(4D, 2D, 4D));
            for (int k = 0; k < list.size(); k++) {
                Entity entity1 = (Entity) list1.get(k);
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
                            new TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 64));
                }
                if (this.gestationtime <= 50) {
                    continue;
                }
                int l = this.rand.nextInt(3) + 1;
                for (int i1 = 0; i1 < l; i1++) {
                    MoCEntityFishy entityfishy1 = new MoCEntityFishy(this.worldObj);
                    entityfishy1.setPosition(this.posX, this.posY, this.posZ);
                    this.worldObj.spawnEntityInWorld(entityfishy1);
                    this.worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F) + 1.0F);
                    setHasEaten(false);
                    entityfishy.setHasEaten(false);
                    this.gestationtime = 0;
                    entityfishy.gestationtime = 0;

                    EntityPlayer entityplayer = this.worldObj.getClosestPlayerToEntity(this, 24D);
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
        if (!this.isInsideOfMaterial(Material.water)) {
            return -90F;
        }
        return 0F;
    }

    @Override
    public float getAdjustedYOffset() {
        if (!this.isInsideOfMaterial(Material.water)) {
            return -0.1F;
        }
        return 0.0F;
    }

    @Override
    public float getAdjustedXOffset() {
        if (!this.isInsideOfMaterial(Material.water)) {
            return -0.2F;
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
}
