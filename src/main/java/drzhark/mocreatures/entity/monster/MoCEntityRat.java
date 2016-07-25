package drzhark.mocreatures.entity.monster;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.entity.ai.EntityAINearestAttackableTargetMoC;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class MoCEntityRat extends MoCEntityMob {

    public MoCEntityRat(World world) {
        super(world);
        setSize(0.5F, 0.5F);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, 1.0D, true));
        this.tasks.addTask(2, this.aiAvoidExplodingCreepers);
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTargetMoC(this, EntityPlayer.class, true));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.3D);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0D);
    }

    @Override
    public void selectType() {
        if (getType() == 0) {
            int i = this.rand.nextInt(100);
            if (i <= 65) {
                setType(1);
            } else if (i <= 98) {
                setType(2);
            } else {
                setType(3);
            }
        }
    }

    @Override
    protected double getAttackStrenght() {
        return 1D;
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getType()) {
            case 1:
                return MoCreatures.proxy.getTexture("ratb.png");
            case 2:
                return MoCreatures.proxy.getTexture("ratbl.png");
            case 3:
                return MoCreatures.proxy.getTexture("ratw.png");

            default:
                return MoCreatures.proxy.getTexture("ratb.png");
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        Entity entity = damagesource.getEntity();

        if (entity != null && entity instanceof EntityLivingBase) {
            setAttackTarget((EntityLivingBase) entity);
            if (MoCreatures.isServer()) {
                List list =
                        this.worldObj.getEntitiesWithinAABB(MoCEntityRat.class,
                                AxisAlignedBB.fromBounds(this.posX, this.posY, this.posZ, this.posX + 1.0D, this.posY + 1.0D, this.posZ + 1.0D)
                                        .expand(16D, 4D, 16D));
                Iterator iterator = list.iterator();
                do {
                    if (!iterator.hasNext()) {
                        break;
                    }
                    Entity entity1 = (Entity) iterator.next();
                    MoCEntityRat entityrat = (MoCEntityRat) entity1;
                    if ((entityrat != null) && (entityrat.getAttackTarget() == null)) {
                        entityrat.setAttackTarget((EntityLivingBase) entity);
                    }
                } while (true);
            }
        }
        return super.attackEntityFrom(damagesource, i);
    }

    public boolean climbing() {
        return !this.onGround && isOnLadder();
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if ((this.rand.nextInt(100) == 0) && (this.getBrightness(1.0F) > 0.5F)) {
            setAttackTarget(null);
            return;
        }
    }

    @Override
    protected String getDeathSound() {
        return "mocreatures:ratdying";
    }

    @Override
    protected Item getDropItem() {
        return MoCreatures.ratRaw;
    }

    @Override
    protected String getHurtSound() {
        return "mocreatures:rathurt";
    }

    @Override
    protected String getLivingSound() {
        return "mocreatures:ratgrunt";
    }

    @Override
    public boolean isOnLadder() {
        return this.isCollidedHorizontally;
    }

    @Override
    public boolean shouldAttackPlayers() {
        return (this.getBrightness(1.0F) < 0.5F) && super.shouldAttackPlayers();
    }
}
