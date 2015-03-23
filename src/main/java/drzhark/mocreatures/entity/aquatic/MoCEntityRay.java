package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAquatic;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenOcean;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class MoCEntityRay extends MoCEntityTameableAquatic {

    private int poisoncounter;
    private int tailCounter;

    public MoCEntityRay(World world) {
        super(world);
        setSize(1.8F, 0.5F);
        setEdad(50 + (this.rand.nextInt(40)));
        this.tasks.addTask(2, new EntityAIWanderMoC2(this, 1.0D, 80));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(getType() == 2 ? 10.0D : 20.0D);
    }

    @Override
    public void selectType() {
        checkSpawningBiome();

        if (getType() == 0) {
            int i = this.rand.nextInt(100);
            if (i <= 70) {
                setType(1);
                setEdad(80 + (this.rand.nextInt(100)));
            } else {
                setType(2);
            }
        }
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getType()) {
            case 1:
                return MoCreatures.proxy.getTexture("mantray.png");
            case 2:
                return MoCreatures.proxy.getTexture("stingray.png");

            default:
                return MoCreatures.proxy.getTexture("stingray.png");
        }
    }

    public boolean isPoisoning() {
        return this.tailCounter != 0;
    }

    @Override
    public boolean interact(EntityPlayer entityplayer) {
        if (super.interact(entityplayer)) {
            return false;
        }

        if (this.riddenByEntity == null && getType() == 1) {
            entityplayer.rotationYaw = this.rotationYaw;
            entityplayer.rotationPitch = this.rotationPitch;
            entityplayer.posY = this.posY;
            if (MoCreatures.isServer()) {
                entityplayer.mountEntity(this);
            }
            return true;
        }
        return false;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (MoCreatures.isServer()) {
            if (!getIsTamed() && getType() > 1 && ++this.poisoncounter > 250 && (this.worldObj.getDifficulty().getDifficultyId() > 0)
                    && this.rand.nextInt(30) == 0) {
                if (MoCTools.findNearPlayerAndPoison(this, true)) {
                    MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 1),
                            new TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 64));
                    this.poisoncounter = 0;
                }
            }
        } else //client stuff
        {
            if (this.tailCounter > 0 && ++this.tailCounter > 50) {
                this.tailCounter = 0;
            }
        }
    }

    @Override
    public void performAnimation(int animationType) {
        if (animationType == 1) //attacking with tail
        {
            this.tailCounter = 1;
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (super.attackEntityFrom(damagesource, i)) {
            if (getType() == 1 || (this.worldObj.getDifficulty().getDifficultyId() == 0)) {
                return true;
            }
            Entity entity = damagesource.getEntity();
            if (entity instanceof EntityLivingBase) {
                if (entity != this) {
                    setAttackTarget((EntityLivingBase) entity);
                }
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    @Override
    public boolean checkSpawningBiome() {
        BlockPos pos = new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(getEntityBoundingBox().minY), this.posZ);
        BiomeGenBase biome = MoCTools.Biomekind(this.worldObj, pos);
        if (!(biome instanceof BiomeGenOcean)) {
            setType(2);
        }
        return true;
    }

    @Override
    public float getAdjustedYOffset() {
        if (!this.isInWater()) {
            return 0.09F;
        } else if (getType() == 1) {
            return 0.15F;
        }

        return 0.15F;
    }

    @Override
    public int nameYOffset() {
        return -25;
    }

    @Override
    public boolean canBeTrappedInNet() {
        return true;
    }

    @Override
    public double getMountedYOffset() {
        return this.height * 0.15D * getSizeFactor();
    }

    @Override
    public float getSizeFactor() {
        float f = getEdad() * 0.01F;
        if (f > 1.5F) {
            f = 1.5F;
        }
        return f;
    }

    @Override
    protected boolean usesNewAI() {
        return true;
    }

    @Override
    public float getAIMoveSpeed() {
        return 0.06F;
    }

    @Override
    public boolean isMovementCeased() {
        return !isInWater();
    }

    @Override
    protected double minDivingDepth() {
        return 3D;
    }

    @Override
    protected double maxDivingDepth() {
        return 6.0D;
    }

    @Override
    public int getMaxEdad() {
        if (getType() == 1) {
            return 180;
        }
        return 90;
    }
}
