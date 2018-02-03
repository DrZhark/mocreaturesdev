package drzhark.mocreatures.entity.ai;

import drzhark.mocreatures.entity.MoCEntityAnimal;
import drzhark.mocreatures.entity.monster.MoCEntityOgre;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public abstract class EntitiAITargetMoC extends EntityAIBase {

    /** The entity that this task belongs to */
    protected final EntityCreature taskOwner;
    /** If true, EntityAI targets must be able to be seen (cannot be blocked by walls) to be suitable targets. */
    protected boolean shouldCheckSight;
    /** When true, only entities that can be reached with minimal effort will be targetted. */
    private boolean nearbyOnly;
    /** When nearbyOnly is true: 0 -> No target, but OK to search; 1 -> Nearby target found; 2 -> Target too far. */
    private int targetSearchStatus;
    /** When nearbyOnly is true, this throttles target searching to avoid excessive pathfinding. */
    private int targetSearchDelay;
    /**
     * If  @shouldCheckSight is true, the number of ticks before the interuption of this AITastk when the entity does't
     * see the target
     */
    private int targetUnseenTicks;

    public EntitiAITargetMoC(EntityCreature creature, boolean checkSight, boolean onlyNearby) {
        this.taskOwner = creature;
        this.shouldCheckSight = checkSight;
        this.nearbyOnly = onlyNearby;
    }

    public EntitiAITargetMoC(EntityCreature creature, boolean checkSight) {
        this(creature, checkSight, false);
    }

    /**
     * A static method used to see if an entity is a suitable target through a number of checks.
     *
     * @param attacker entity which is attacking
     * @param target attack target
     * @param includeInvincibles should ignore {@link net.minecraft.entity.player.EntityPlayer#capabilities
     * EntityPlayer.capabilities}.{@link net.minecraft.entity.player.PlayerCapabilities#disableDamage disableDamage}
     * @param checkSight should check if attacker can see target
     */
    public static boolean isSuitableTarget(EntityLiving attacker, EntityLivingBase target, boolean includeInvincibles, boolean checkSight) {
        if (target == null) {
            return false;
        } else if (target == attacker) {
            return false;
        } else if (!target.isEntityAlive()) {
            return false;
        } else if (!attacker.canAttackClass(target.getClass())) {
            return false;
        } else if (attacker instanceof MoCEntityAnimal && !(target instanceof EntityPlayer)) {
            MoCEntityAnimal mocattacker = (MoCEntityAnimal) attacker;
            if (!mocattacker.canAttackTarget(target)) {
                return false;
            }

            //avoids attacking other tamed 
            if (mocattacker.getIsTamed() && (target instanceof MoCEntityAnimal && ((MoCEntityAnimal) target).getIsTamed())) {
                return false;
            }
        }
        Team team = attacker.getTeam();
        Team team1 = target.getTeam();

        if (team != null && team1 == team) {
            return false;
        } else {
            if (attacker instanceof IEntityOwnable && ((IEntityOwnable) attacker).getOwnerId() != null) {
                if (target instanceof IEntityOwnable && ((IEntityOwnable) attacker).getOwnerId().equals(((IEntityOwnable) target).getOwnerId())) {
                    return false;
                }

                if (target == ((IEntityOwnable) attacker).getOwner()) {
                    return false;
                }
            } else if (target instanceof EntityPlayer && includeInvincibles && ((EntityPlayer) target).capabilities.disableDamage) {
                return false;
            }

            return !checkSight || attacker.getEntitySenses().canSee(target);
        }

    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean shouldContinueExecuting() {
        EntityLivingBase entitylivingbase = this.taskOwner.getAttackTarget();

        if (entitylivingbase == null) {
            return false;
        } else if (!entitylivingbase.isEntityAlive()) {
            return false;
        } else {
            Team team = this.taskOwner.getTeam();
            Team team1 = entitylivingbase.getTeam();

            if (team != null && team1 == team) {
                return false;
            } else {
                double d0 = this.getTargetDistance();

                if (this.taskOwner.getDistanceSq(entitylivingbase) > d0 * d0) {
                    return false;
                } else {
                    if (this.shouldCheckSight) {
                        if (this.taskOwner.getEntitySenses().canSee(entitylivingbase)) {
                            this.targetUnseenTicks = 0;
                        } else if (++this.targetUnseenTicks > 60) {
                            return false;
                        }
                    }

                    return !(entitylivingbase instanceof EntityPlayer) || !((EntityPlayer) entitylivingbase).capabilities.disableDamage;
                }
            }
        }
    }

    protected double getTargetDistance() {
        if (this.taskOwner instanceof MoCEntityOgre) {
            return ((MoCEntityOgre) this.taskOwner).getAttackRange();
        }
        IAttributeInstance iattributeinstance = this.taskOwner.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE);
        return iattributeinstance == null ? 16.0D : iattributeinstance.getAttributeValue();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting() {
        this.targetSearchStatus = 0;
        this.targetSearchDelay = 0;
        this.targetUnseenTicks = 0;
    }

    /**
     * Resets the task
     */
    @Override
    public void resetTask() {
        this.taskOwner.setAttackTarget((EntityLivingBase) null);
    }

    /**
     * A method used to see if an entity is a suitable target through a number of checks. Args : entity,
     * canTargetInvinciblePlayer
     */
    protected boolean isSuitableTarget(EntityLivingBase target, boolean includeInvincibles) {
        if (!isSuitableTarget(this.taskOwner, target, includeInvincibles, this.shouldCheckSight)) {
            //System.out.println("not suitable target");
            return false;
        } else if (!this.taskOwner.isWithinHomeDistanceFromPosition(new BlockPos(target))) {
            //System.out.println("attacker away from homeposition");
            return false;
        } else {
            if (this.nearbyOnly) {
                if (--this.targetSearchDelay <= 0) {
                    this.targetSearchStatus = 0;
                }

                if (this.targetSearchStatus == 0) {
                    this.targetSearchStatus = this.canEasilyReach(target) ? 1 : 2;
                }

                if (this.targetSearchStatus == 2) {
                    return false;
                }
            }

            return true;
        }
    }

    /**
     * Checks to see if this entity can find a short path to the given target.
     */
    private boolean canEasilyReach(EntityLivingBase p_75295_1_) {
        this.targetSearchDelay = 10 + this.taskOwner.getRNG().nextInt(5);
        Path path = this.taskOwner.getNavigator().getPathToEntityLiving(p_75295_1_);

        if (path == null) {
            //System.out.println("couldn't find path");
            return false;
        } else {
            PathPoint pathpoint = path.getFinalPathPoint();

            if (pathpoint == null) {
                return false;
            } else {
                int i = pathpoint.x - MathHelper.floor(p_75295_1_.posX);
                int j = pathpoint.z - MathHelper.floor(p_75295_1_.posZ);
                return i * i + j * j <= 2.25D;
            }
        }
    }
}
/*
TARGET SMALLER CREATURE
boolean avoidTamed
-targets a creature smaller than itself

HunterBehaviour
-if tamed and sitting: don't look for prey
-if leashed don't look for prey
-if hungry look for prey
-if not hungry: don't look for prey
-if tamed: avoid attacking tamed prey (either MoC or vanilla) and if possible othe mods's tamed

Target smaller with exlusions
-targets a creature smaller than itself excluding a list
*/
