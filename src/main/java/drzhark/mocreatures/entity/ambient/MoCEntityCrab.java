package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAmbient;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoCEntityCrab extends MoCEntityTameableAmbient

{

    public MoCEntityCrab(World world) {
        super(world);
        setSize(0.3F, 0.3F);
        setEdad(50 + this.rand.nextInt(50));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(6.0D);
    }

    @Override
    public float getMoveSpeed() {
        return 0.15F;
    }

    @Override
    public void selectType() {
        if (getType() == 0) {
            setType(this.rand.nextInt(2) + 1);
        }

    }

    @Override
    public ResourceLocation getTexture() {
        switch (getType()) {
            case 1:
                return MoCreatures.proxy.getTexture("craba.png");
            case 2:
                return MoCreatures.proxy.getTexture("crabb.png");
            default:
                return MoCreatures.proxy.getTexture("craba.png");
        }
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (MoCreatures.isServer()) {
            // TODO
            /*if (fleeingTick == 3) {
                MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 1),
                        new TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 64));
            }*/
        }
    }

    @Override
    public void fall(float f, float f1) {
    }

    @Override
    public void performAnimation(int animationType) {
        if (animationType == 1) //fleeing animation finishes
        {
            // TODO
            //this.fleeingTick = 0;
        }
    }

    @Override
    protected Item getDropItem() {
        return MoCreatures.crabraw;
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

    @Override
    public float getSizeFactor() {
        return 0.7F * getEdad() * 0.01F;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    /* TODO
    public boolean isFleeing() {
        return this.fleeingTick != 0;
    }*/

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }

    @Override
    protected boolean canBeTrappedInNet() {
        return true;
    }


    @Override
    public int nameYOffset() {
        return -20;
    }
}
