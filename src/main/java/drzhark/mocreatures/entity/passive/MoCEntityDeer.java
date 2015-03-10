package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.EntityAIFleeFromPlayer;
import drzhark.mocreatures.entity.ai.EntityAIFollowAdult;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoCEntityDeer extends MoCEntityTameableAnimal {

    public MoCEntityDeer(World world) {
        super(world);
        setEdad(75);
        setSize(0.9F, 1.3F);
        setAdult(true);
        setTamed(false);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIFleeFromPlayer(this, this.getMyAISpeed() * 1.2D, 10D));
        this.tasks.addTask(2, new EntityAIPanic(this, this.getMyAISpeed() * 1.2D));
        this.tasks.addTask(4, new EntityAIFollowAdult(this, getMyAISpeed()));
        this.tasks.addTask(5, new EntityAIWander(this, getMyAISpeed()));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
    }

    @Override
    protected boolean usesNewAI() {
        return true;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.35D);
    }

    @Override
    public void selectType() {
        if (getType() == 0) {
            int i = this.rand.nextInt(100);
            if (i <= 20) {
                setType(1);
            } else if (i <= 70) {
                setType(2);
            } else {
                setAdult(false);
                setType(3);
            }
        }
    }

    @Override
    public ResourceLocation getTexture() {

        switch (getType()) {
            case 1:
                return MoCreatures.proxy.getTexture("deer.png");
            case 2:
                return MoCreatures.proxy.getTexture("deerf.png");
            case 3:
                setAdult(false);
                return MoCreatures.proxy.getTexture("deerb.png");

            default:
                return MoCreatures.proxy.getTexture("deer.png");
        }
    }

    @Override
    public void fall(float f, float f1) {
    }

    @Override
    public boolean entitiesToInclude(Entity entity) {
        return !(entity instanceof MoCEntityDeer) && super.entitiesToInclude(entity);
    }

    @Override
    protected String getDeathSound() {
        return "mocreatures:deerdying";
    }

    @Override
    protected Item getDropItem() {
        return MoCreatures.fur;
    }

    @Override
    protected String getHurtSound() {
        return "mocreatures:deerhurt";
    }

    @Override
    protected String getLivingSound() {
        if (!getIsAdult()) {
            return "mocreatures:deerbgrunt";
        } else {
            return "mocreatures:deerfgrunt";
        }
    }

    public double getMyAISpeed() {
        if (getType() == 1) {
            return 1.1D;
        } else if (getType() == 2) {
            return 1.3D;
        }
        return 1.2D;
    }

    @Override
    public int getTalkInterval() {
        return 400;
    }

    @Override
    public int getMaxEdad() {
        return 130;
    }

    @Override
    public void setAdult(boolean flag) {
        if (MoCreatures.isServer()) {
            setType(this.rand.nextInt(1));
        }
        super.setAdult(flag);
    }

    @Override
    public boolean getIsAdult() {
        return this.getType() != 3 && super.getIsAdult();
    }
}
