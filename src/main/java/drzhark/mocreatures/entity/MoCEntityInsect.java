package drzhark.mocreatures.entity;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

import java.util.List;

public class MoCEntityInsect extends MoCEntityAmbient {

    private int climbCounter;
    protected EntityAIWanderMoC2 wander;

    public MoCEntityInsect(World world) {
        super(world);
        setSize(0.2F, 0.2F);
        this.tasks.addTask(2, this.wander = new EntityAIWanderMoC2(this, 1.0D, 80));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(22, Byte.valueOf((byte) 0)); // isFlying - 0 false 1 true
    }

    @Override
    public boolean isFlyer() {
        return false;
    }

    @Override
    public boolean isFlyingAlone() {
        return getIsFlying();
    }

    @Override
    public boolean getIsFlying() {
        return (this.dataWatcher.getWatchableObjectByte(22) == 1);
    }

    public void setIsFlying(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(22, Byte.valueOf(input));
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (MoCreatures.isServer()) {
            if (!getIsFlying() && isOnLadder() && !this.onGround) {
                MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 1),
                        new TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 64));
            }

            if (isFlyer() && !getIsFlying() && this.rand.nextInt(getFlyingFreq()) == 0) {
                List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(4D, 4D, 4D));
                for (int i = 0; i < list.size(); i++) {
                    Entity entity1 = (Entity) list.get(i);
                    if (!(entity1 instanceof EntityLivingBase)) {
                        continue;
                    }
                    if (((EntityLivingBase) entity1).width >= 0.4F && ((EntityLivingBase) entity1).height >= 0.4F && canEntityBeSeen(entity1)) {
                        setIsFlying(true);
                        this.wander.makeUpdate();
                    }
                }
            }

            if (isFlyer() && !getIsFlying() && this.rand.nextInt(200) == 0) {
                setIsFlying(true);
                this.wander.makeUpdate();
            }

            if (isAttractedToLight() && this.rand.nextInt(50) == 0) {
                int ai[] = MoCTools.ReturnNearestBlockCoord(this, Blocks.torch, 8D);
                if (ai[0] > -1000) {
                    this.getNavigator().tryMoveToXYZ(ai[0], ai[1], ai[2], 1.0D);//
                }
            }

            //this makes the flying insect move all the time in the air
            if (getIsFlying() && this.getNavigator().noPath() && !isMovementCeased() && this.getAttackTarget() == null) {
                this.wander.makeUpdate();
            }

        } else // client stuff
        {
            if (this.climbCounter > 0 && ++this.climbCounter > 8) {
                this.climbCounter = 0;
            }
        }
    }

    /**
     * Is this insect attracted to light?
     *
     * @return
     */
    public boolean isAttractedToLight() {
        return false;
    }

    @Override
    public void performAnimation(int animationType) {
        if (animationType == 1) //climbing animation
        {
            this.climbCounter = 1;
        }

    }

    @Override
    public void fall(float f, float f1) {
    }

    @Override
    public boolean getCanSpawnHere() {
        return super.getCanSpawnHereAnimal() && super.getCanSpawnHereCreature();
    }

    @Override
    public float getSizeFactor() {
        return 1.0F;
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 4;
    }

    @Override
    public boolean isOnLadder() {
        return this.isCollidedHorizontally;
    }

    public boolean climbing() {
        return (this.climbCounter != 0);
    }

    @Override
    protected void jump() {
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    protected int getFlyingFreq() {
        return 20;
    }

    @Override
    public float rollRotationOffset() {
        return 0F;
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }

    @Override
    public PathNavigate getNavigator() {
        /*if (this.isInWater() && this.isAmphibian()) {
            return this.navigatorWater;
        }
        */if (this.getIsFlying()) {
            return this.navigatorFlyer;
        }
        return this.navigator;
    }
}
