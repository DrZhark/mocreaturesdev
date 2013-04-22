package drzhark.mocreatures.entity.passive;



import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import drzhark.mocreatures.network.MoCServerPacketHandler;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

// TODO
// SOUNDS: snakeslither / snakeswim / snakesnap / snakerattle /snakedying
// /snakehurt / snakehiss /snakeupset

// change walking sounds to slither sounds (done code, pending sounds)
// head follows player (Done)
// climbing animation (done)
// d/c jump (done)
// remove shadow (done)
// water floating yoffset (done)
// randomness to the slithering movement(done)
// adjust bounding size (done)
// follows player that carries snake food (mouse/birds)(done)
// change pathfinding behavior / facing behavior (avoid sudden changes facing
// when resting) (done)
// tongue in/out plus vibrating movment up/down at the end (done)
// fangs (done)
// biting (done)
// attacking animation (done)
// attacking behavior / poisoning (done)
// rattlesnake (done)
// rattle / moving tail (done)
// cobra special parts (done)
// snake textures (done)
// specific biome spawning (done)
// baby snakes - edad, growing sizes, stretching (done)
// eggs (done)
// small non aggressive snakes flee from player (done)
// different sizes/behaviors, some aggressive, some passive, some shy (done)
// you can pick tamed snakes (done)
// synchronize bite (done)
// hissing -warning prior to attacking! - avoid attack if player has mouse/bird
// (done)

// curled stance (perhaps not needed?)
// avoid curling when dying (no need)
// encrouching / eating prey? (for later)
// spitting venom snake? (for later)
// taming?? (done)

// sounds
// test

/**
 * Biome - specific Forest Desert plains Swamp Jungle Tundra Taiga Extreme Hills
 * Ocean
 * 
 * swamp: python, bright green, #1 plains: coral, cobra #1, #2, #3, #4 desert:
 * rattlesnake , #2 jungle: all except rattlesnake hills: all except python,
 * bright green, bright orange tundra-taiga: none ocean: leave alone
 * 
 */

public class MoCEntitySnake extends MoCEntityAnimal {
    private float fTongue;
    private float fMouth;
    private boolean isBiting;
    private float fRattle;
    private boolean isPissed;
    private int hissCounter;

    private int movInt;
    private boolean isNearPlayer;
    public float bodyswing;

    public static final String snakeNames[] = { "Dark", "Spotted", "Orange", "Green", "Coral", "Cobra", "Rattle", "Python" };

    public MoCEntitySnake(World world)
    {
        super(world);
        setSize(1.4F, 0.5F);
        health = 10;
        //moveSpeed = 0.6F;
        bodyswing = 2F;
        movInt = rand.nextInt(10);
        setEdad(50 + rand.nextInt(50));
        //forceUpdates = true;
    }

    //@SideOnly(Side.CLIENT)
    @Override
    public void selectType()
    {
        // snake types:
        // 1 small blackish/dark snake (passive)
        // 2 dark green /brown snake (passive)
        // 3 bright orangy snake aggressive venomous swamp, jungle, forest
        // 4 bright green snake aggressive venomous swamp, jungle, forest
        // 5 coral (aggressive - venomous) small / plains, forest
        // 6 cobra (aggressive - venomous - spitting) plains, forest
        // 7 rattlesnake (aggressive - venomous) desert
        // 8 python (aggressive - non venomous) big - swamp
        // 9 sea snake (aggressive - venomous)
        if (getType() == 0)
        {
            int k = rand.nextInt(100);
            if (k <= 12)
            {
                setType(1);
            }
            else if (k <= 25)
            {
                setType(2);
            }
            else if (k <= 37)
            {
                setType(3);
            }
            else if (k <= 50)
            {
                setType(4);
            }
            else if (k <= 62)
            {
                setType(5);
            }
            else if (k <= 75)
            {
                setType(6);
            }
            else if (k <= 87)
            {
                setType(7);
            }
            else
            {
                setType(8);
            }

        }

    }

    @Override
    public String getTexture()
    {

        switch (getType())
        {
        case 1:
            return MoCreatures.proxy.MODEL_TEXTURE + "snake1.png";
        case 2:
            return MoCreatures.proxy.MODEL_TEXTURE + "snake2.png";
        case 3:
            return MoCreatures.proxy.MODEL_TEXTURE + "snake3.png";
        case 4:
            return MoCreatures.proxy.MODEL_TEXTURE + "snake4.png";
        case 5:
            return MoCreatures.proxy.MODEL_TEXTURE + "snake5.png";
        case 6:
            return MoCreatures.proxy.MODEL_TEXTURE + "snake6.png";
        case 7:
            return MoCreatures.proxy.MODEL_TEXTURE + "snake7.png";
        case 8:
            return MoCreatures.proxy.MODEL_TEXTURE + "snake8.png";
        default:
            return MoCreatures.proxy.MODEL_TEXTURE + "snake1.png";
        }
    }

    @Override
    public int getMaxHealth()
    {
        return 10;
    }

    @Override
    public float getMoveSpeed()
    {
        return 0.6F;
    }

    @Override
    protected void fall(float f)
    {
    }

    @Override
    public boolean isOnLadder()
    {
        return isCollidedHorizontally;
    }

    @Override
    // snakes can't jump
    protected void jump()
    {
    }

    @Override
    protected boolean canDespawn()
    {
        return !getIsTamed();
    }

    public boolean pickedUp()
    {
        return (this.ridingEntity != null);
    }

    @Override
    public boolean interact(EntityPlayer entityplayer)
    {
        //TODO
        //this doesn't work yet, make the player feed the mouse to this snake
        /*if (entityplayer.riddenByEntity != null && entityplayer.riddenByEntity instanceof MoCEntityMouse)
        {
            //System.out.println("player has a mouse");
            
        }*/
        if (super.interact(entityplayer)) { return false; }
        if (!getIsTamed()) { return false; }

        ItemStack itemstack = entityplayer.inventory.getCurrentItem();

        //changes name
        /*if ((itemstack != null) && getIsTamed() //&& MoCreatures.isServer()
                && ((itemstack.itemID == MoCreatures.medallion.shiftedIndex) || (itemstack.itemID == Item.book.shiftedIndex)))
        {
            if (!MoCreatures.isServer())
            {
                MoCreatures.proxy.setName(entityplayer, this);
            }
            return true;
        }*/

        rotationYaw = entityplayer.rotationYaw;
        if (this.ridingEntity == null)
        {
            if (MoCreatures.isServer())
            {
                mountEntity(entityplayer);
            }
        }
        else
        {
            worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, ((rand.nextFloat() - rand.nextFloat()) * 0.2F) + 1.0F);
            if (MoCreatures.isServer())
            {
                this.mountEntity(null);
            }
        }
        motionX = entityplayer.motionX * 5D;
        motionY = (entityplayer.motionY / 2D) + 0.5D;
        motionZ = entityplayer.motionZ * 5D;

        return true;
    }

    @Override
    public boolean isNotScared()
    {
        // TODO depending on size!
        if (getType() > 2 && getEdad() > 50) { return true; }
        return false;
    }

    /**
     * returns true when is climbing up
     * 
     * @return
     */
    public boolean isClimbing()
    {
        return isOnLadder() && motionY > 0.01F;
    }

    public boolean isResting()
    {
        return (!getNearPlayer() && onGround && (motionX < 0.01D && motionX > -0.01D) && (motionZ < 0.01D && motionZ > -0.01D));
        // return (onGround && (motionX < 0.01D && motionX > -0.01D) && (motionZ
        // < 0.01D && motionZ > -0.01D));
    }

    public boolean getNearPlayer()
    {
        return isNearPlayer;
    }

    public int getMovInt()
    {
        return movInt;
    }

    @Override
    public boolean swimmerEntity()
    {
        return true;
    }

    @Override
    public boolean canBreatheUnderwater()
    {
        return true;
    }

    public void setNearPlayer(boolean flag)
    {
        isNearPlayer = flag;
    }

    /*@Override
    public double getYOffset()
    {
        // If we are in SMP, do not alter offset on any client other than the player being mounted on
        if (((ridingEntity instanceof EntityPlayer) && !worldObj.isRemote) || ridingEntity == MoCreatures.proxy.getPlayer())//MoCProxy.mc().thePlayer)
        {
            return(yOffset - 1.5F);
        }
        else
        {
            return yOffset;
        }
    }*/

    @Override
    public double getYOffset()
    {
        if (ridingEntity instanceof EntityPlayer && ridingEntity == MoCreatures.proxy.getPlayer() && !MoCreatures.isServer()) { return (yOffset - 1.5F); }

        if ((ridingEntity instanceof EntityPlayer) && !MoCreatures.isServer())
        {
            return (yOffset + 0.1F);
        }
        else
        {
            return yOffset;
        }
    }

    public float getSizeF()
    {
        float factor = 1.0F;

        if (getType() == 1 || getType() == 2)// small shy snakes
        {
            factor = 0.8F;
        }
        else if (getType() == 5)// coral
        {
            factor = 0.6F;
        }
        if (getType() == 6)// cobra 1.1
        {
            factor = 1.1F;
        }
        if (getType() == 7)// rattlesnake
        {
            factor = 0.9F;
        }
        if (getType() == 8)// python
        {
            factor = 1.5F;
        }
        // float f = 1.0F*factor;
        return this.getEdad() * 0.01F * factor;// */
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if (getEdad() < 100 && rand.nextInt(500) == 0)
        {
            setEdad(getEdad() + 1);
        }

        if (pickedUp())
        {
            movInt = 0;
            // System.out.println("mov rand # = " + movInt);
        }

        if (isResting())
        {

            prevRenderYawOffset = renderYawOffset = rotationYaw = prevRotationYaw;

        }

        if (!onGround && (ridingEntity != null))
        {
            rotationYaw = ridingEntity.rotationYaw;// -90F;
        }

        if (getfTongue() != 0.0F)
        {
            setfTongue(getfTongue() + 0.2F);
            if (getfTongue() > 8.0F)
            {
                setfTongue(0.0F);
            }
        }

        /*
         * if (getNearPlayer() && !getIsTamed())//&& rand.nextInt(2)==0) {
         * setfMouth(0.3F); }else
         */

        if (worldObj.difficultySetting > 0 && getNearPlayer() && !getIsTamed() && isNotScared())
        {

            // setfMouth(0.3F);
            hissCounter++;
            //System.out.println("Hissing! " + hissCounter);

            // TODO synchronize and get sound
            // hiss
            if (hissCounter % 25 == 0)
            {
                setfMouth(0.3F);
                worldObj.playSoundAtEntity(this, "snakeupset", 1.0F, 1.0F + ((rand.nextFloat() - rand.nextFloat()) * 0.2F));
            }
            if (hissCounter % 35 == 0)
            {
                setfMouth(0.0F);
            }

            if (hissCounter > 100 && rand.nextInt(50) == 0)
            {
                // then randomly get pissed
                setPissed(true);
                hissCounter = 0;
            }
        }
        if (hissCounter > 500)
        {
            hissCounter = 0;
        }

        if (getfMouth() != 0.0F && hissCounter == 0)// && !isPissed()) //biting
        {
            setfMouth(getfMouth() + 0.1F);
            if (getfMouth() > 0.5F)
            {
                setfMouth(0.0F);
            }
        }

        if (getType() == 7 && getfRattle() != 0.0F) // rattling
        {
            setfRattle(getfRattle() + 0.2F);
            if (getfRattle() == 1.0F)
            {
                // TODO synchronize
                worldObj.playSoundAtEntity(this, "snakerattle", 1.0F, 1.0F + ((rand.nextFloat() - rand.nextFloat()) * 0.2F));
            }
            if (getfRattle() > 8.0F)
            {
                setfRattle(0.0F);
            }
        }
        // super.onUpdate();

    }

    /**
     * from 0.0 to 4.0F 0.0 = inside mouth 2.0 = completely stuck out 3.0 =
     * returning 4.0 = in.
     * 
     * @return
     */
    public float getfTongue()
    {
        return fTongue;
    }

    public void setfTongue(float fTongue)
    {
        this.fTongue = fTongue;
    }

    public float getfMouth()
    {
        return fMouth;
    }

    public void setfMouth(float fMouth)
    {
        this.fMouth = fMouth;
    }

    public float getfRattle()
    {
        return fRattle;
    }

    public void setfRattle(float fRattle)
    {
        this.fRattle = fRattle;
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        // type = 7);
        /*
         * if (!hasPath() && riddenByEntity == null && !isMovementCeased() &&
         * entityToAttack == null) { updateWanderPath(); }
         */

        // setfMouth(0.3F);
        /**
         * stick tongue
         */
        if (rand.nextInt(50) == 0 && getfTongue() == 0.0F)
        {
            setfTongue(0.1F);
        }

        /**
         * Open mouth
         */
        if (rand.nextInt(100) == 0 && getfMouth() == 0.0F)
        {
            setfMouth(0.1F);
        }

        if (getType() == 7)
        {
            int chance = 0;
            if (getNearPlayer())
            {
                chance = 30;
            }
            else
            {
                chance = 100;
            }

            if (rand.nextInt(chance) == 0)
            {
                setfRattle(0.1F);
            }
        }
        /**
         * change in movement pattern
         */
        if (!isResting() && !pickedUp() && rand.nextInt(50) == 0)
        {
            movInt = rand.nextInt(10);
        }

        // if (attackTime<=0) bodyswing -= 0.3F;

        /**
         * Biting animation
         */
        if (isBiting())
        {
            bodyswing -= 0.5F;
            setfMouth(0.3F);

            if (bodyswing < 0F)
            {
                worldObj.playSoundAtEntity(this, "snakesnap", 1.0F, 1.0F + ((rand.nextFloat() - rand.nextFloat()) * 0.2F));
                bodyswing = 2.5F;
                setfMouth(0.0F);
                setBiting(false);
            }
        }

        /**
         * this stops chasing the target randomly
         */
        if (entityToAttack != null && rand.nextInt(100) == 0)
        {
            entityToAttack = null;
        }

        /**
         * Follow player that is carrying a mice
         * 
         */
        EntityPlayer entityplayer1 = worldObj.getClosestPlayerToEntity(this, 12D);
        if (entityplayer1 != null)
        {
            double distP = MoCTools.getSqDistanceTo(entityplayer1, posX, posY, posZ);
            if (isNotScared())
            {
                if (distP < 5D)
                {
                    setNearPlayer(true);
                }
                else
                {
                    setNearPlayer(false);
                }

                if (entityplayer1.riddenByEntity != null && (entityplayer1.riddenByEntity instanceof MoCEntityMouse || entityplayer1.riddenByEntity instanceof MoCEntityBird))
                {
                    PathEntity pathentity = worldObj.getPathEntityToEntity(this, entityplayer1, 16F, true, false, false, true);
                    setPathToEntity(pathentity);
                    setPissed(false);
                    hissCounter = 0;
                }

            }
            else
            {
                setNearPlayer(false);
                if (distP < 3D && !getIsTamed())
                {
                    fleeingTick = 40;
                    // System.out.println("Fleeing!");
                }

            }

        }
        else
        {
            setNearPlayer(false);
            // setPissed(false);
        }

    }

    @Override
    protected void attackEntity(Entity entity, float f)
    {

        if ((getType() < 3 || getIsTamed()) && entity instanceof EntityPlayer)
        {
            entityToAttack = null;
            return;
        }

        // attack only after hissing/rattling!
        if (!isPissed()) { return; }

        if (attackTime <= 0 && (f < 2.5D) && (entity.boundingBox.maxY > boundingBox.minY) && (entity.boundingBox.minY < boundingBox.maxY))

        // if((f < 2.5D) && (entity.boundingBox.maxY > boundingBox.minY) &&
        // (entity.boundingBox.minY < boundingBox.maxY))
        {
            /*
             * if (attackTime == 5)//<5 && attackTime >0) { //start biting
             * setBiting(true); } else if (attackTime <=0)
             */
            {
                setBiting(true);
                attackTime = 20;

                // venom!
                if (rand.nextInt(2) == 0 && entity instanceof EntityPlayer && getType() > 2 && getType() < 8)
                {
                    MoCreatures.poisonPlayer((EntityPlayer) entity);
                    ((EntityPlayer) entity).addPotionEffect(new PotionEffect(Potion.poison.id, 120, 0));
                }

                entity.attackEntityFrom(DamageSource.causeMobDamage(this), 2);

                if (!(entity instanceof EntityPlayer))
                {
                    MoCTools.destroyDrops(this, 3D);
                }
            }

        }
    }

    @Override
    public void performAnimation(int i)
    {
        setBiting(true);
    }

    public boolean isBiting()
    {
        return isBiting;
    }

    public void setBiting(boolean flag)
    {
        if (flag && MoCreatures.isServer())
        {
            MoCServerPacketHandler.sendAnimationPacket(this.entityId, this.worldObj.provider.dimensionId, 0);
        }
        this.isBiting = flag;
    }

    public boolean isPissed()
    {
        return isPissed;
    }

    public void setPissed(boolean isPissed)
    {
        this.isPissed = isPissed;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, int i)
    {

        if (getType() < 3) { return super.attackEntityFrom(damagesource, i); }

        if (super.attackEntityFrom(damagesource, i))
        {
            Entity entity = damagesource.getEntity();

            if ((riddenByEntity == entity) || (ridingEntity == entity)) { return true; }
            if ((entity != this) && (worldObj.difficultySetting > 0))// && !getIsTamed())
            {
                setPissed(true);
                entityToAttack = entity;
            }
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    protected Entity findPlayerToAttack()
    {
        if (worldObj.difficultySetting > 0)
        {
            EntityPlayer entityplayer = worldObj.getClosestVulnerablePlayerToEntity(this, 4D);
            if (!getIsTamed() && (entityplayer != null)) // && getIsAdult() )
            {

                if (isNotScared() && isPissed()) { return entityplayer; }

            }
            if ((rand.nextInt(100) == 0))
            {
                EntityLiving entityliving = getClosestEntityLiving(this, 8D);
                return entityliving;
            }
        }
        return null;
    }

    @Override
    protected void dropFewItems(boolean flag, int x)
    {
        if (getEdad() > 60)
        {
            // int i = rand.nextInt(10);
            // if(i < 5)
            {
                int j = rand.nextInt(3);
                for (int l = 0; l < j; l++)
                {

                    entityDropItem(new ItemStack(MoCreatures.fishyegg, 1, getType() + 20), 0.0F);
                }
            }
        }
    }

    // ignores big entities, everything else is prey!
    @Override
    public boolean entitiesToIgnore(Entity entity)
    {
        return ((super.entitiesToIgnore(entity)) || (entity instanceof MoCEntitySnake) || (entity.height > 0.5D && entity.width > 0.5D)

        );
    }

    @Override
    protected void playStepSound(int par1, int par2, int par3, int par4)
    {
        if (isInsideOfMaterial(Material.water))
        {
            worldObj.playSoundAtEntity(this, "snakeswimm", 1.0F, 1.0F);
        }
        else
        {
            worldObj.playSoundAtEntity(this, "snakeslither", 1.0F, 1.0F);
        }
    }

    @Override
    protected String getDeathSound()
    {
        return "snakedying";
    }

    @Override
    protected String getHurtSound()
    {
        return "snakehurt";
    }

    @Override
    protected String getLivingSound()
    {
        return "snakehiss";
    }

    @Override
    public boolean getCanSpawnHere()
    {
        if (MoCTools.isNearTorch(this)) { return false; }

        return (checkSpawningBiome() && (MoCreatures.proxy.getFrequency(this.getEntityName()) > 0) && getCanSpawnHereCreature() && getCanSpawnHereLiving());
    }

    @Override
    public boolean checkSpawningBiome()
    {
        int i = MathHelper.floor_double(posX);
        int j = MathHelper.floor_double(boundingBox.minY);
        int k = MathHelper.floor_double(posZ);

        String s = MoCTools.BiomeName(worldObj, i, j, k);
        /**
         * swamp: python, bright green, #1 (done) plains: coral, cobra #1, #2,
         * #3, #4 (everyone but 7) desert: rattlesnake , #2 jungle: all except
         * rattlesnake forest: all except rattlesnake hills: all except python,
         * bright green, bright orange, rattlesnake tundra-taiga: none ocean:
         * leave alone
         */

        /**
         * Biome lists: Ocean Plains Desert Extreme Hills Forest Taiga Swampland
         * River Frozen Ocean Frozen River Ice Plains Ice Mountains Mushroom
         * Island Mushroom Island Shore Beach DesertHills ForestHills TaigaHills
         * Extreme Hills Edge Jungle JungleHills
         * 
         */

        int l = rand.nextInt(10);

        if (s.equals("Taiga") || s.equals("FrozenOcean") || s.equals("FrozenRiver") || s.equals("Ice Plains") || s.equals("Ice Mountains") || s.equals("TaigaHills")) { return false;

        }
        if (s.equals("Desert") || s.equals("DesertHills"))
        {
            if (l < 5)
            {
                setType(7); // rattlesnake or spotted brownish ?
            }
            else
            {
                setType(2);
            }
        }

        if (getType() == 7 && !(s.equals("Desert") || s.equals("DesertHills"))) { return false;
        // type = 0);
        // chooseType();
        }
        if (s.equals("Extreme Hills") || s.equals("ForestHills") || s.equals("Extreme Hills Edge"))
        {
            if (l < 4)
            {
                setType(1);
            }
            else if (l < 7)
            {
                setType(5);
            }
            else
            {
                setType(6);
            }
        }
        if (s.equals("Swampland"))
        {
            // python or bright green bright orange
            if (l < 4)
            {
                setType(8);
            }
            else if (l < 8)
            {
                setType(4);
            }
            else
            {
                setType(1);
            }

        }

        return true;
    }

    @Override
    public boolean updateMount()
    {
        return getIsTamed();
    }

    @Override
    public boolean forceUpdates()
    {
        return getIsTamed();
    }

    @Override
    public int nameYOffset()
    {

        return -20;

    }
    
    
    
    @Override
    public boolean isMyHealFood(ItemStack par1ItemStack)
    {
        return par1ItemStack != null && (par1ItemStack.itemID == MoCreatures.ratRaw.itemID);
    }
}
