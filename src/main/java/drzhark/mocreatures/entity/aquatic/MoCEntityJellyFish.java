package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAquatic;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class MoCEntityJellyFish extends MoCEntityTameableAquatic {

    private int poisoncounter;
    private static final DataParameter<Boolean> GLOWS = EntityDataManager.<Boolean>createKey(MoCEntityJellyFish.class, DataSerializers.BOOLEAN);
    
    public MoCEntityJellyFish(World world) {
        super(world);
        setSize(0.3F, 0.5F);
        setEdad(50 + (this.rand.nextInt(50)));
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(5, new EntityAIWanderMoC2(this, 0.5D, 120));
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.15D);
    }

    @Override
    public void selectType() {
        if (getType() == 0) {
            setType(this.rand.nextInt(5) + 1);
        }
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(GLOWS, Boolean.valueOf(false));
    }

    public void setGlowing(boolean flag) {
        this.dataManager.set(GLOWS, Boolean.valueOf(flag));
    }

    public boolean isGlowing() {
        return (((Boolean)this.dataManager.get(GLOWS)).booleanValue());
    }

    @Override
    public float getAIMoveSpeed() {
        return 0.02F;
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getType()) {
            case 1:
                return MoCreatures.proxy.getTexture("jellyfisha.png");
            case 2:
                return MoCreatures.proxy.getTexture("jellyfishb.png");
            case 3:
                return MoCreatures.proxy.getTexture("jellyfishc.png");
            case 4:
                return MoCreatures.proxy.getTexture("jellyfishd.png");
            case 5:
                return MoCreatures.proxy.getTexture("jellyfishe.png");
            case 6:
                return MoCreatures.proxy.getTexture("jellyfishf.png");
            case 7:
                return MoCreatures.proxy.getTexture("jellyfishg.png");
            case 8:
                return MoCreatures.proxy.getTexture("jellyfishh.png");
            case 9:
                return MoCreatures.proxy.getTexture("jellyfishi.png");
            case 10:
                return MoCreatures.proxy.getTexture("jellyfishj.png");
            case 11:
                return MoCreatures.proxy.getTexture("jellyfishk.png");
            case 12:
                return MoCreatures.proxy.getTexture("jellyfishl.png");

            default:
                return MoCreatures.proxy.getTexture("jellyfisha.png");
        }
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!this.world.isRemote) {

            if (this.rand.nextInt(200) == 0) {
                setGlowing(!this.world.isDaytime());
            }

            if (!getIsTamed() && ++this.poisoncounter > 250 && (this.shouldAttackPlayers()) && this.rand.nextInt(30) == 0) {
                if (MoCTools.findNearPlayerAndPoison(this, true)) {
                    this.poisoncounter = 0;
                }
            }
        }
    }

    @Override
    protected Item getDropItem() {
        boolean flag = this.rand.nextInt(2) == 0;
        if (flag) {
            return Items.SLIME_BALL;
        }
        return null;
    }

    @Override
    public float pitchRotationOffset() {
        if (!this.isInWater()) {
            return 90F;
        }
        return 0F;
    }

    @Override
    public int nameYOffset() {
        int yOff = (int) (getEdad() * -1 / 2.3);
        return yOff;
    }

    @Override
    public float getSizeFactor() {
        float myMoveSpeed = MoCTools.getMyMovementSpeed(this);
        float pulseSpeed = 0.08F;
        if (myMoveSpeed > 0F)
            pulseSpeed = 0.5F;
        float pulseSize = MathHelper.cos(this.ticksExisted * pulseSpeed) * 0.2F;
        return getEdad() * 0.01F + (pulseSize / 5);
    }

    @Override
    protected boolean canBeTrappedInNet() {
        return true;
    }

    @Override
    public int getMaxEdad() {
        return 100;
    }
}
