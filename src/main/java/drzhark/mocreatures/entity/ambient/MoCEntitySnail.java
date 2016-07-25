package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAmbient;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoCEntitySnail extends MoCEntityAmbient {

    public MoCEntitySnail(World world) {
        super(world);
        setSize(0.2F, 0.2F);
        this.tasks.addTask(1, new EntityAIWanderMoC2(this, 0.8D));
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(23, Byte.valueOf((byte) 0));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(5.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.10D);
    }

    @Override
    public boolean isMovementCeased() {
        return (getIsHiding());
    }

    @Override
    public void selectType() {
        if (getType() == 0) {
            setType(this.rand.nextInt(6) + 1);
        }
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getType()) {
            case 1:
                return MoCreatures.proxy.getTexture("snaila.png");
            case 2:
                return MoCreatures.proxy.getTexture("snailb.png");
            case 3:
                return MoCreatures.proxy.getTexture("snailc.png");
            case 4:
                return MoCreatures.proxy.getTexture("snaild.png");
            case 5:
                return MoCreatures.proxy.getTexture("snaile.png");
            case 6:
                return MoCreatures.proxy.getTexture("snailf.png");
            default:
                return MoCreatures.proxy.getTexture("snaila.png");
        }
    }

    public boolean getIsHiding() {
        return (this.dataWatcher.getWatchableObjectByte(23) == 1);
    }

    public void setIsHiding(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(23, Byte.valueOf(input));
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (!this.worldObj.isRemote) {
            EntityLivingBase entityliving = getBoogey(3D);
            if ((entityliving != null) && entityliving.height > 0.5F && entityliving.width > 0.5F && canEntityBeSeen(entityliving)) {
                if (!getIsHiding()) {
                    setIsHiding(true);
                }
                this.getNavigator().clearPathEntity();
            } else {
                setIsHiding(false);
            }
            /**
             * snails without a shell won't hide
             */
            if (getIsHiding() && this.getType() > 4) {
                setIsHiding(false);
            }
        }
    }

    @Override
    public void fall(float f, float f1) {
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (getIsHiding()) {
            this.prevRenderYawOffset = this.renderYawOffset = this.rotationYaw = this.prevRotationYaw;
        }
    }

    @Override
    protected Item getDropItem() {
        return Items.slime_ball;
    }

    @Override
    public boolean isOnLadder() {
        return this.isCollidedHorizontally;
    }

    public boolean climbing() {
        return !this.onGround && isOnLadder();
    }

    @Override
    public void jump() {
    }
}
