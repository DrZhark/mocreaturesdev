package drzhark.mocreatures.client.model;

import drzhark.mocreatures.entity.monster.MoCEntityManticore;
import net.minecraft.entity.Entity;

public class MoCModelManticore extends MoCModelNewBigCat {

    @Override
    public void updateAnimationModifiers(Entity entity) {
        MoCEntityManticore bigcat = (MoCEntityManticore) entity;
        this.isFlyer = true;
        this.isSaddled = bigcat.getIsRideable();
        this.flapwings = (bigcat.wingFlapCounter != 0);
        this.floating = (this.isFlyer && bigcat.isOnAir());
        this.poisoning = bigcat.swingingTail();
        this.isRidden = (bigcat.isBeingRidden());
        this.hasMane = true;
        this.hasSaberTeeth = true;
        this.onAir = (bigcat.isOnAir());
        this.hasStinger = true;
        this.isMovingVertically = bigcat.motionY != 0;
        this.hasChest = false;
        this.isTamed = false;
        this.hasChest = false;

    }
}
