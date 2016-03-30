package drzhark.mocreatures.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public interface IMoCEntity {

    public void selectType();

    public String getPetName();

    public void setPetName(String name);

    public boolean getIsTamed();

    public void setTamed(boolean flag);

    public boolean getIsAdult();

    public void setAdult(boolean flag);

    public boolean checkSpawningBiome();

    public boolean getCanSpawnHere();

    public void performAnimation(int i);

    public boolean renderName();

    public int nameYOffset();

    public void makeEntityJump();

    public void makeEntityDive();

    public float getSizeFactor();

    public float getAdjustedYOffset();

    public String getOwnerName();

    public void setOwner(String username);

    public void setArmorType(byte i);

    public int getType();

    public void setType(int i);

    public void dismountEntity();

    public float rollRotationOffset();

    public float pitchRotationOffset();

    public void setEdad(int i);

    public int getEdad();

    public float yawRotationOffset();

    public float getAdjustedZOffset();

    public float getAdjustedXOffset();

    public ResourceLocation getTexture();

    public boolean canAttackTarget(EntityLivingBase entity);

    boolean getIsSitting(); // is the entity sitting, for animations and AI

    boolean isNotScared(); //relentless creature that attacks others used for AI

    boolean isMovementCeased(); //to deactivate path / wander behavior AI

    boolean shouldAttackPlayers();

    public double getDivingDepth();

    public boolean isDiving();

    public void forceEntityJump();

    public int maxFlyingHeight();

    public int minFlyingHeight();

    public boolean isFlyer();

    public boolean getIsFlying();
}
