package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoCEntityMole extends MoCEntityTameableAnimal {

    public MoCEntityMole(World world) {
        super(world);
        setSize(1F, 0.5F);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIWanderMoC2(this, 1.0D));
        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2D);
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getTexture("mole.png");
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(23, Byte.valueOf((byte) 0)); // state - 0 outside / 1 digging / 2 underground / 3 pick-a-boo

    }

    public boolean isOnDirt() {
        Block block =
                this.worldObj.getBlockState(
                        new BlockPos(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.getEntityBoundingBox().minY - 0.5D), MathHelper
                                .floor_double(this.posZ))).getBlock();
        return isDiggableBlock(Block.getIdFromBlock(block));//(j == 2 | j == 3 | j == 12);
    }

    private boolean isDiggableBlock(int i) {
        return i == 2 | i == 3 | i == 12;
    }

    /**
     * Moves entity forward underground
     */
    @SuppressWarnings("unused")
    private void digForward() {
        double coordY = this.posY;
        double coordZ = this.posZ;
        double coordX = this.posX;
        int x = 1;
        double newPosY = coordY - Math.cos((this.rotationPitch - 90F) / 57.29578F) * x;
        double newPosX =
                coordX + Math.cos((MoCTools.realAngle(this.rotationYaw - 90F) / 57.29578F)) * (Math.sin((this.rotationPitch - 90F) / 57.29578F) * x);
        double newPosZ =
                coordZ + Math.sin((MoCTools.realAngle(this.rotationYaw - 90F) / 57.29578F)) * (Math.sin((this.rotationPitch - 90F) / 57.29578F) * x);
        Block block =
                this.worldObj.getBlockState(
                        new BlockPos(MathHelper.floor_double(newPosX), MathHelper.floor_double(newPosY), MathHelper.floor_double(newPosZ)))
                        .getBlock();
        if (isDiggableBlock(Block.getIdFromBlock(block))) {
            this.setPosition(newPosX, newPosY, newPosZ);
        }
    }

    /**
     * obtains State
     *
     * @return 0 outside / 1 digging / 2 underground / 3 pick-a-boo
     */
    public byte getState() {
        return this.dataWatcher.getWatchableObjectByte(23);
    }

    /**
     * Changes the state
     *
     * @param b 0 outside / 1 digging / 2 underground / 3 pick-a-boo
     */
    public void setState(byte b) {
        this.dataWatcher.updateObject(23, Byte.valueOf(b));
    }

    @Override
    public float pitchRotationOffset() {

        int i = getState();
        switch (i) {
            case 0:
                return 0F;
            case 1:
                return -45F;
            case 2:
                return 0F;
            case 3:
                return 60F;
            default:
                return 0F;
        }
    }

    @Override
    public float getAdjustedYOffset() {
        int i = getState();
        switch (i) {
            case 0:
                return 0F;
            case 1:
                return 0.3F;
            case 2:
                return 1F;
            case 3:
                return 0.1F;
            default:
                return 0F;
        }
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (MoCreatures.isServer()) {
            if (this.rand.nextInt(10) == 0 && getState() == 1) {
                setState((byte) 2);
            }

            if (getState() != 2 && getState() != 1 && isOnDirt()) {
                EntityLivingBase entityliving = getBoogey(4D);
                if ((entityliving != null) && canEntityBeSeen(entityliving)) {
                    setState((byte) 1);
                    this.getNavigator().clearPathEntity();
                }
            }

            //if underground and no enemies: pick a boo
            if (this.rand.nextInt(20) == 0 && getState() == 2 && (getBoogey(4D) == null)) {
                setState((byte) 3);
                this.getNavigator().clearPathEntity();
            }

            //if not on dirt, get out!
            if (getState() != 0 && !isOnDirt()) {
                setState((byte) 0);
            }

            if (this.rand.nextInt(30) == 0 && getState() == 3) {
                setState((byte) 2);
            }

            /*
             * if (getState() == 2) { if (rand.nextInt(50) == 0) digForward(); }
             */

            //digging fx
            if ((getState() == 1 || getState() == 2)) {
                setSprinting(true);
            } else {
                setSprinting(false);
            }
        }
    }

    @Override
    public boolean isMovementCeased() {
        return getState() == 1 || getState() == 3;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (getState() != 2) {
            return super.attackEntityFrom(damagesource, i);
        }
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return (getState() != 2);
    }

    @Override
    public boolean canBePushed() {
        return (getState() != 2);
    }

    @Override
    protected void collideWithEntity(Entity par1Entity) {
        if (getState() != 2) {
            super.collideWithEntity(par1Entity);
            //            par1Entity.applyEntityCollision(this);
        }
    }

    @Override
    public boolean isEntityInsideOpaqueBlock() {
        if (getState() == 2) {
            return false;
        }
        return super.isEntityInsideOpaqueBlock();
    }

    @Override
    public void onDeath(DamageSource damagesource) {
        //System.out.println(this + " is dying with health of " + this.func_110143_aJ() + " and State of " + getState());
        super.onDeath(damagesource);
    }

    @Override
    public boolean isEntityInvulnerable(DamageSource source) {
        if (getState() == 2) {
            return true;
        }
        return super.isEntityInvulnerable(source);
    }

    @Override
    protected Item getDropItem() {
        return MoCreatures.fur;
    }

    @Override
    protected String getDeathSound() {
        return "mocreatures:rabbitdeath";
    }

    @Override
    protected String getHurtSound() {
        return "mocreatures:rabbithurt";
    }

    @Override
    protected String getLivingSound() {
        return null;
    }
}
