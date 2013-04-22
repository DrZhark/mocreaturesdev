package drzhark.mocreatures.entity;

import net.minecraft.entity.Entity;

public interface MoCIMoCreature {

    public boolean forceUpdates();// = false;

    public void selectType();

    public String getName();

    public void setName(String name);

    public boolean getIsTamed();

    public void setTamed(boolean flag);

    public boolean getIsAdult();

    public void setAdult(boolean flag);

    public boolean checkSpawningBiome();

    public boolean getCanSpawnHere();

    /**
     * Used to synchronize animations between server and clients
     * 
     * @param i
     *            = animationType
     */
    public void performAnimation(int i);

    //public int getHealth();

    public boolean renderName();

    public int nameYOffset();

    //public int getMaxHealth();

    /**
     * Used to ajust the Yoffset when using ropes
     * 
     * @return
     */
    public double roperYOffset();

    /**
     * The entity holding the rope
     * 
     * @return
     */
    public Entity getRoper();

    public boolean updateMount();

    /**
     * method used to sync jump client/server
     */
    public void makeEntityJump();

    public void makeEntityDive();

    public float getSizeFactor();

    public float getAdjustedYOffset();

    public String getOwnerName();

    public void setOwner(String username);

    public void setArmorType(byte i);
    
    public int getType();

	public void dismountEntity();
}
