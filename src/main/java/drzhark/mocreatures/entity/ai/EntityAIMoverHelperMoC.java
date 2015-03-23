package drzhark.mocreatures.entity.ai;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.MoCEntityAquatic;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

public class EntityAIMoverHelperMoC extends EntityMoveHelper {

    EntityCreature theCreature;

    public EntityAIMoverHelperMoC(EntityLiving entityliving) {
        super(entityliving);
        this.theCreature = (EntityCreature) entityliving;
    }

    @Override
    public void onUpdateMoveHelper() {
        if (!this.theCreature.isInWater()) {
            super.onUpdateMoveHelper();
            return;
        }

        /*
         * Water movement code
         */
        this.floating();
        if (this.update && !this.theCreature.getNavigator().noPath()) {
            double d0 = this.posX - this.theCreature.posX;
            double d1 = this.posY - this.theCreature.posY;
            //double distanceToY = d1;
            double d2 = this.posZ - this.theCreature.posZ;
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;
            d3 = MathHelper.sqrt_double(d3);
            if (d3 < 0.5) {
                this.theCreature.getNavigator().clearPathEntity();
                return;
            }
            //System.out.println("distance to objective = " + d3 + "objective: X = " + this.posX + ", Y = " + this.posY + ", Z = " + this.posZ);
            d1 /= d3;
            float f = (float) (Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
            this.theCreature.rotationYaw = this.limitAngle(this.theCreature.rotationYaw, f, 30.0F);
            this.theCreature.renderYawOffset = this.theCreature.rotationYaw;
            float f1 = (float) (this.speed * this.theCreature.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());
            this.theCreature.setAIMoveSpeed(this.theCreature.getAIMoveSpeed() + (f1 - this.theCreature.getAIMoveSpeed()) * 0.125F);
            double d4 = Math.sin((double) (this.theCreature.ticksExisted + this.theCreature.getEntityId()) * 0.75D) * 0.01D;
            double d5 = Math.cos((double) (this.theCreature.rotationYaw * (float) Math.PI / 180.0F));
            double d6 = Math.sin((double) (this.theCreature.rotationYaw * (float) Math.PI / 180.0F));
            this.theCreature.motionX += d4 * d5;
            this.theCreature.motionZ += d4 * d6;
            //d4 = Math.sin((double)(this.theCreature.ticksExisted + this.theCreature.getEntityId()) * 0.75D) * 0.05D;
            this.theCreature.motionY += d4 * (d6 + d5) * 0.25D;
            this.theCreature.motionY += (double) this.theCreature.getAIMoveSpeed() * d1 * 1.5D;
        } else {
            //this.theCreature.setAIMoveSpeed(0.0F);
        }
    }

    /**
     * Makes creatures in the water float to the right depth
     */
    private void floating() {
        if (theCreature.riddenByEntity != null) {
            return;
        }

        double distToSurface = (MoCTools.waterSurfaceAtGivenEntity(theCreature) - theCreature.posY);
        if (distToSurface > ((IMoCEntity) theCreature).getDivingDepth()) {
            if (theCreature.motionY < 0) {
                theCreature.motionY = 0;
            }
            theCreature.motionY += 0.001D;// 0.001
            theCreature.motionY += (distToSurface * 0.01);
        }

        if (!theCreature.getNavigator().noPath() && theCreature.isCollidedHorizontally) {
            if (theCreature instanceof MoCEntityAquatic) {
                theCreature.motionY = 0.05D;
            } else {
                ((IMoCEntity) theCreature).forceEntityJump();
            }
        }

        if ((this.theCreature.getAttackTarget() != null && ((this.theCreature.getAttackTarget().posY < (this.posY - 0.5D)) && this.theCreature
                .getDistanceToEntity(this.theCreature.getAttackTarget()) < 10F))) {
            if (this.theCreature.motionY < -0.1) {
                this.theCreature.motionY = -0.1;
            }
            return;
        }
    }
}
