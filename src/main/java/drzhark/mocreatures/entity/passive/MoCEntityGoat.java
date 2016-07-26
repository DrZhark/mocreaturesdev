package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.EntityAIFollowAdult;
import drzhark.mocreatures.entity.ai.EntityAIPanicMoC;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoCEntityGoat extends MoCEntityTameableAnimal {

    private boolean hungry;
    private boolean swingLeg;
    private boolean swingEar;
    private boolean swingTail;
    private boolean bleat;
    private boolean eating;
    private int bleatcount;
    private int attacking;
    public int movecount;
    private int chargecount;
    private int tailcount; // 90 to -45
    private int earcount; // 20 to 40 default = 30
    private int eatcount;

    //private float moveSpeed;

    public MoCEntityGoat(World world) {
        super(world);
        setSize(0.8F, 1F);
        setEdad(70);
        ((PathNavigateGround) this.getNavigator()).setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIPanicMoC(this, 1.0D));
        this.tasks.addTask(4, new EntityAIFollowAdult(this, 1.0D));
        this.tasks.addTask(5, new EntityAIAttackOnCollide(this, 1.0D, true));
        this.tasks.addTask(6, new EntityAIWanderMoC2(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(12.0D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(23, Byte.valueOf((byte) 0)); // isCharging - 0 false 1 true
        this.dataWatcher.addObject(24, Byte.valueOf((byte) 0)); // isUpset - 0 false 1 true
    }

    public boolean getUpset() {
        return (this.dataWatcher.getWatchableObjectByte(24) == 1);
    }

    public boolean getCharging() {
        return (this.dataWatcher.getWatchableObjectByte(23) == 1);
    }

    public void setUpset(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(24, Byte.valueOf(input));
    }

    public void setCharging(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(23, Byte.valueOf(input));
    }

    @Override
    public void selectType() {
        /*
         * type 1 = baby type 2 = female type 3 = female 2 type 4 = female 3
         * type 5 = male 1 type 6 = male 2 type 7 = male 3
         */
        if (getType() == 0) {
            int i = this.rand.nextInt(100);
            if (i <= 15) {
                setType(1);
                setEdad(50);
            } else if (i <= 30) {
                setType(2);
                setEdad(70);
            } else if (i <= 45) {
                setType(3);
                setEdad(70);
            } else if (i <= 60) {
                setType(4);
                setEdad(70);
            } else if (i <= 75) {
                setType(5);
                setEdad(90);
            } else if (i <= 90) {
                setType(6);
                setEdad(90);
            } else {
                setType(7);
                setEdad(90);
            }
        }

    }

    @Override
    public ResourceLocation getTexture() {
        switch (getType()) {
            case 1:
                return MoCreatures.proxy.getTexture("goat1.png");
            case 2:
                return MoCreatures.proxy.getTexture("goat2.png");
            case 3:
                return MoCreatures.proxy.getTexture("goat3.png");
            case 4:
                return MoCreatures.proxy.getTexture("goat4.png");
            case 5:
                return MoCreatures.proxy.getTexture("goat5.png");
            case 6:
                return MoCreatures.proxy.getTexture("goat6.png");
            case 7:
                return MoCreatures.proxy.getTexture("goat1.png");

            default:
                return MoCreatures.proxy.getTexture("goat1.png");
        }
    }

    public void calm() {
        setAttackTarget(null);
        setUpset(false);
        setCharging(false);
        this.attacking = 0;
        this.chargecount = 0;
    }

    @Override
    protected void jump() {
        if (getType() == 1) {
            this.motionY = 0.41D;
        } else if (getType() < 5) {
            this.motionY = 0.45D;
        } else {
            this.motionY = 0.5D;
        }

        if (isPotionActive(Potion.jump)) {
            this.motionY += (getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F;
        }
        if (isSprinting()) {
            float f = this.rotationYaw * 0.01745329F;
            this.motionX -= MathHelper.sin(f) * 0.2F;
            this.motionZ += MathHelper.cos(f) * 0.2F;
        }
        this.isAirBorne = true;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!MoCreatures.isServer()) {
            if (this.rand.nextInt(100) == 0) {
                setSwingEar(true);
            }

            if (this.rand.nextInt(80) == 0) {
                setSwingTail(true);
            }

            if (this.rand.nextInt(50) == 0) {
                setEating(true);
            }
        }
        if (getBleating()) {
            this.bleatcount++;
            if (this.bleatcount > 15) {
                this.bleatcount = 0;
                setBleating(false);
            }

        }

        if ((this.hungry) && (this.rand.nextInt(20) == 0)) {
            this.hungry = false;
        }

        if (MoCreatures.isServer() && (getEdad() < 90 || getType() > 4 && getEdad() < 100) && this.rand.nextInt(500) == 0) {
            setEdad(getEdad() + 1);
            if (getType() == 1 && getEdad() > 70) {
                int i = this.rand.nextInt(6) + 2;
                setType(i);

            }
        }

        if (getUpset()) {
            this.attacking += (this.rand.nextInt(4)) + 2;
            if (this.attacking > 75) {
                this.attacking = 75;
            }

            if (this.rand.nextInt(200) == 0 || getAttackTarget() == null) {
                calm();
            }

            if (!getCharging() && this.rand.nextInt(35) == 0) {
                swingLeg();
            }

            if (!getCharging()) {
                this.getNavigator().clearPathEntity();
            }

            if (getAttackTarget() != null)// && rand.nextInt(100)==0)
            {
                faceEntity(getAttackTarget(), 10F, 10F);
                if (this.rand.nextInt(80) == 0) {
                    setCharging(true);
                }
            }
        }

        if (getCharging()) {
            this.chargecount++;
            if (this.chargecount > 120) {
                this.chargecount = 0;
            }
            if (getAttackTarget() == null) {
                calm();
            }
        }

        if (!getUpset() && !getCharging()) {
            EntityPlayer entityplayer1 = this.worldObj.getClosestPlayerToEntity(this, 24D);
            if (entityplayer1 != null) {// Behaviour that happens only close to player :)

                // is there food around? only check with player near
                EntityItem entityitem = getClosestEntityItem(this, 10D);
                if (entityitem != null) {
                    float f = entityitem.getDistanceToEntity(this);
                    if (f > 2.0F) {
                        int i = MathHelper.floor_double(entityitem.posX);
                        int j = MathHelper.floor_double(entityitem.posY);
                        int k = MathHelper.floor_double(entityitem.posZ);
                        faceLocation(i, j, k, 30F);

                        getMyOwnPath(entityitem, f);
                        return;
                    }
                    if ((f < 2.0F) && (entityitem != null) && (this.deathTime == 0) && this.rand.nextInt(50) == 0) {
                        this.worldObj.playSoundAtEntity(this, "mocreatures:goateating", 1.0F,
                                1.0F + ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F));
                        setEating(true);

                        entityitem.setDead();
                        return;
                    }
                }

                // find other goat to play!
                if (getType() > 4 && this.rand.nextInt(200) == 0) {
                    MoCEntityGoat entitytarget = (MoCEntityGoat) getClosestEntityLiving(this, 14D);
                    if (entitytarget != null) {
                        setUpset(true);
                        setAttackTarget(entitytarget);
                        entitytarget.setUpset(true);
                        entitytarget.setAttackTarget(this);
                    }
                }

            }// end of close to player behavior
        }// end of !upset !charging
    }

    @Override
    public boolean isMyFavoriteFood(ItemStack par1ItemStack) {
        Item item1 = null;
        if (par1ItemStack != null) {
            item1 = par1ItemStack.getItem();
        }
        return (item1 != null && MoCTools.isItemEdible(item1));
    }

    @Override
    public int getTalkInterval() {
        if (this.hungry) {
            return 80;
        }

        return 200;
    }

    @Override
    public boolean entitiesToIgnore(Entity entity) {
        return ((!(entity instanceof MoCEntityGoat)) || ((((MoCEntityGoat) entity).getType() < 5)));
    }

    @Override
    public boolean isMovementCeased() {
        return getUpset() && !getCharging();
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        this.attacking = 30;
        if (entityIn instanceof MoCEntityGoat) {
            MoCTools.bigsmack(this, entityIn, 0.4F);
            MoCTools.playCustomSound(this, "goatsmack", worldObj);
            if (this.rand.nextInt(3) == 0) {
                calm();
                ((MoCEntityGoat) entityIn).calm();
            }
            return false;
        }
        MoCTools.bigsmack(this, entityIn, 0.8F);
        if (this.rand.nextInt(3) == 0) {
            calm();
        }
        return super.attackEntityAsMob(entityIn);
    }

    @Override
    public boolean isNotScared() {
        return getType() > 4;
    }

    private void swingLeg() {
        if (!getSwingLeg()) {
            setSwingLeg(true);
            this.movecount = 0;
        }
    }

    public boolean getSwingLeg() {
        return this.swingLeg;
    }

    public void setSwingLeg(boolean flag) {
        this.swingLeg = flag;
    }

    public boolean getSwingEar() {
        return this.swingEar;
    }

    public void setSwingEar(boolean flag) {
        this.swingEar = flag;
    }

    public boolean getSwingTail() {
        return this.swingTail;
    }

    public void setSwingTail(boolean flag) {
        this.swingTail = flag;
    }

    public boolean getEating() {
        return this.eating;
    }

    @Override
    public void setEating(boolean flag) {
        this.eating = flag;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (super.attackEntityFrom(damagesource, i)) {
            Entity entity = damagesource.getEntity();

            if (entity != this && entity instanceof EntityLivingBase && super.shouldAttackPlayers() && getType() > 4) {
                setAttackTarget((EntityLivingBase) entity);
                setUpset(true);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onUpdate() {

        if (getSwingLeg()) {
            this.movecount += 5;
            if (this.movecount == 30) {
                this.worldObj.playSoundAtEntity(this, "mocreatures:goatdigg", 1.0F, 1.0F + ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F));
            }

            if (this.movecount > 100) {
                setSwingLeg(false);
                this.movecount = 0;
            }
        }

        if (getSwingEar()) {
            this.earcount += 5;
            if (this.earcount > 40) {
                setSwingEar(false);
                this.earcount = 0;
            }
        }

        if (getSwingTail()) {
            this.tailcount += 15;
            if (this.tailcount > 135) {
                setSwingTail(false);
                this.tailcount = 0;
            }
        }

        if (getEating()) {
            this.eatcount += 1;
            if (this.eatcount == 2) {
                EntityPlayer entityplayer1 = this.worldObj.getClosestPlayerToEntity(this, 3D);
                if (entityplayer1 != null) {
                    this.worldObj.playSoundAtEntity(this, "mocreatures:goateating", 1.0F,
                            1.0F + ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F));
                }
            }
            if (this.eatcount > 25) {
                setEating(false);
                this.eatcount = 0;
            }
        }

        super.onUpdate();
    }

    public int legMovement() {
        if (!getSwingLeg()) {
            return 0;
        }

        if (this.movecount < 21) {
            return this.movecount * -1;
        }
        if (this.movecount < 70) {
            return this.movecount - 40;
        }
        return -this.movecount + 100;
    }

    public int earMovement() {
        // 20 to 40 default = 30
        if (!getSwingEar()) {
            return 0;
        }
        if (this.earcount < 11) {
            return this.earcount + 30;
        }
        if (this.earcount < 31) {
            return -this.earcount + 50;
        }
        return this.earcount - 10;
    }

    public int tailMovement() {
        // 90 to -45
        if (!getSwingTail()) {
            return 90;
        }

        return this.tailcount - 45;
    }

    public int mouthMovement() {
        if (!getEating()) {
            return 0;
        }
        if (this.eatcount < 6) {
            return this.eatcount;
        }
        if (this.eatcount < 16) {
            return -this.eatcount + 10;
        }
        return this.eatcount - 20;
    }

    @Override
    public void fall(float f, float f1) {
    }

    @Override
    public boolean interact(EntityPlayer entityplayer) {
        if (super.interact(entityplayer)) {
            return false;
        }
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if (itemstack != null && itemstack.getItem() == Items.bucket) {
            if (getType() > 4) {
                setUpset(true);
                setAttackTarget(entityplayer);
                return false;
            }
            if (getType() == 1) {
                return false;
            }

            entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(Items.milk_bucket));
            return true;
        }

        if (getIsTamed()) {
            if ((itemstack != null) && (MoCTools.isItemEdible(itemstack.getItem()))) {
                if (--itemstack.stackSize == 0) {
                    entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
                }
                this.setHealth(getMaxHealth());
                this.worldObj
                        .playSoundAtEntity(this, "mocreatures:goateating", 1.0F, 1.0F + ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F));
                return true;
            }
        }

        if (MoCreatures.isServer() && !getIsTamed() && (itemstack != null) && MoCTools.isItemEdible(itemstack.getItem())) {
            if (MoCTools.tameWithName(entityplayer, this)) {
                return true;
            }
        }

        return false;

    }

    public boolean getBleating() {
        return this.bleat && (getAttacking() == 0);
    }

    public void setBleating(boolean flag) {
        this.bleat = flag;
    }

    public int getAttacking() {
        return this.attacking;
    }

    public void setAttacking(int flag) {
        this.attacking = flag;
    }

    @Override
    protected String getHurtSound() {
        return "mocreatures:goathurt";
    }

    @Override
    protected String getLivingSound() {
        setBleating(true);
        if (getType() == 1) {
            return "mocreatures:goatkid";
        }
        if (getType() > 2 && getType() < 5) {
            return "mocreatures:goatfemale";
        }

        return "mocreatures:goatgrunt";
    }

    @Override
    protected String getDeathSound() {
        return "mocreatures:goatdying";
    }

    @Override
    protected Item getDropItem() {
        return Items.leather;
    }

    @Override
    public int getMaxEdad() {
        return 50; //so the update is not handled on MoCEntityAnimal
    }

    @Override
    public float getAIMoveSpeed() {
        return 0.15F;
    }
}
