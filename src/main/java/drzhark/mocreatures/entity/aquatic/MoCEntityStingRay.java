package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class MoCEntityStingRay extends MoCEntityRay {

    private int poisoncounter;
    private int tailCounter;

    public MoCEntityStingRay(World world) {
        super(world);
        setSize(1.2F, 0.5F);
        setEdad(50 + (this.rand.nextInt(40)));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10D);
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getTexture("stingray.png");
    }

    @Override
    public boolean isPoisoning() {
        return this.tailCounter != 0;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (MoCreatures.isServer()) {
            if (!getIsTamed() && ++this.poisoncounter > 250 && (this.worldObj.getDifficulty().getDifficultyId() > 0) && this.rand.nextInt(30) == 0) {
                if (MoCTools.findNearPlayerAndPoison(this, true)) {
                    MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 1),
                            new TargetPoint(this.worldObj.provider.getDimensionType().getId(), this.posX, this.posY, this.posZ, 64));
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
            if ((this.worldObj.getDifficulty().getDifficultyId() == 0)) {
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
}
