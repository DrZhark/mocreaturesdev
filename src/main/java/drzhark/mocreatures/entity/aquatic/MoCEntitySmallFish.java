package drzhark.mocreatures.entity.aquatic;

import com.google.common.base.Predicate;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAquatic;
import drzhark.mocreatures.entity.ai.EntityAIFleeFromEntityMoC;
import drzhark.mocreatures.entity.ai.EntityAIPanicMoC;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MoCEntitySmallFish extends MoCEntityTameableAquatic {

    public static final String fishNames[] = {"Anchovy", "Angelfish", "Angler", "Clownfish", "Goldfish", "Hippotang", "Manderin"};

    private int latMovCounter;

    public MoCEntitySmallFish(World world) {
        super(world);
        setSize(0.3F, 0.3F);
        setEdad(70 + this.rand.nextInt(30));
        this.tasks.addTask(1, new EntityAIPanicMoC(this, 1.3D));
        this.tasks.addTask(2, new EntityAIFleeFromEntityMoC(this, new Predicate() {

            public boolean apply(Entity entity) {
                return (entity.height > 0.3F || entity.width > 0.3F);
            }

            @Override
            public boolean apply(Object p_apply_1_) {
                return this.apply((Entity) p_apply_1_);
            }
        }, 2.0F, 0.6D, 1.5D));
        //this.tasks.addTask(4, new EntityAIFollowHerd(this, 0.6D, 4D, 20D, 10));
        this.tasks.addTask(5, new EntityAIWanderMoC2(this, 1.0D, 80));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
    }

    @Override
    public void selectType() {
        if (getType() == 0) {
            setType(this.rand.nextInt(7) + 1);
        }

    }

    @Override
    public ResourceLocation getTexture() {

        switch (getType()) {
            case 1:
                return MoCreatures.proxy.getTexture("smallfish_anchovy.png");
            case 2:
                return MoCreatures.proxy.getTexture("smallfish_angelfish.png");
            case 3:
                return MoCreatures.proxy.getTexture("smallfish_angler.png");
            case 4:
                return MoCreatures.proxy.getTexture("smallfish_clownfish.png");
            case 5:
                return MoCreatures.proxy.getTexture("smallfish_goldfish.png");
            case 6:
                return MoCreatures.proxy.getTexture("smallfish_hippotang.png");
            case 7:
                return MoCreatures.proxy.getTexture("smallfish_manderin.png");
            default:
                return MoCreatures.proxy.getTexture("smallfish_clownfish.png");
        }
    }

    @Override
    protected boolean canBeTrappedInNet() {
        return true;
    }

    @Override
    protected void dropFewItems(boolean flag, int x) {
        int i = this.rand.nextInt(100);
        if (i < 70) {
            entityDropItem(new ItemStack(Items.fish, 1, 0), 0.0F);
        } else {
            int j = this.rand.nextInt(2);
            for (int k = 0; k < j; k++) {
                entityDropItem(new ItemStack(MoCreatures.mocegg, 1, getType() + 79), 0.0F);
            }
        }
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if ((MoCreatures.isServer())) {

            /*if (!isNotScared() && this.rand.nextInt(5) == 0 && !getIsTamed()) {
                EntityLivingBase entityliving = getBoogey(8D);
                if (entityliving != null && entityliving.isInsideOfMaterial(Material.water)) {
                    MoCTools.runLikeHell(this, entityliving);
                }
            }*/
            if (getIsTamed() && this.rand.nextInt(100) == 0 && getHealth() < getMaxHealth()) {
                this.setHealth(getMaxHealth());
            }
        }
        if (!this.isInWater()) {
            this.prevRenderYawOffset = this.renderYawOffset = this.rotationYaw = this.prevRotationYaw;
            this.rotationPitch = this.prevRotationPitch;
        }
    }

    @Override
    public float getSizeFactor() {
        return getEdad() * 0.01F;
    }

    @Override
    public float getAdjustedYOffset() {
        if (!this.isInWater()) {
            return -0.1F;
        }
        return 0.3F;
    }

    @Override
    protected boolean isFisheable() {
        return !getIsTamed();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public float yawRotationOffset() {
        if (!this.isInWater()) {
            return 90F;
        }
        return 90F + super.yawRotationOffset();
    }

    @Override
    public float rollRotationOffset() {
        if (!this.isInWater()) {
            return -90F;
        }
        return 0F;
    }

    @Override
    public int nameYOffset() {
        return -25;
    }

    @Override
    public float getAdjustedXOffset() {
        if (!this.isInWater()) {
            return -0.6F;
        }
        return 0F;
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
    public int getMaxEdad() {
        return 120;
    }

    @Override
    public boolean isNotScared() {
        return getIsTamed();
    }
}
