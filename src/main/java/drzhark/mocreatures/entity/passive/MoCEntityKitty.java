package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.EntityAIFollowAdult;
import drzhark.mocreatures.entity.ai.EntityAIPanicMoC;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.item.MoCEntityKittyBed;
import drzhark.mocreatures.entity.item.MoCEntityLitterBox;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

import java.util.List;

public class MoCEntityKitty extends MoCEntityTameableAnimal {

    private int kittytimer;
    private int madtimer;
    private boolean foundTree;
    private final int treeCoord[] = {-1, -1, -1};

    private boolean isSwinging;
    private boolean onTree;
    private EntityItem itemAttackTarget;

    public MoCEntityKitty(World world) {
        super(world);
        setSize(0.7F, 0.5F);
        setAdult(true);
        setEdad(40);
        setKittyState(1);
        this.kittytimer = 0;
        this.madtimer = this.rand.nextInt(5);
        this.foundTree = false;
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
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15.0D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
    }

    @Override
    public void selectType() {
        if (getType() == 0) {
            setType(this.rand.nextInt(8) + 1);
        }
    }

    @Override
    public ResourceLocation getTexture() {

        switch (getType()) {
            case 1:
                return MoCreatures.proxy.getTexture("pussycata.png");
            case 2:
                return MoCreatures.proxy.getTexture("pussycatb.png");
            case 3:
                return MoCreatures.proxy.getTexture("pussycatc.png");
            case 4:
                return MoCreatures.proxy.getTexture("pussycatd.png");
            case 5:
                return MoCreatures.proxy.getTexture("pussycate.png");
            case 6:
                return MoCreatures.proxy.getTexture("pussycatf.png");
            case 7:
                return MoCreatures.proxy.getTexture("pussycatg.png");
            case 8:
                return MoCreatures.proxy.getTexture("pussycath.png");

            default:
                return MoCreatures.proxy.getTexture("pussycata.png");
        }
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(23, Byte.valueOf((byte) 0)); // isSitting - 0 false 1 true
        this.dataWatcher.addObject(24, Byte.valueOf((byte) 0)); // isHungry - 0 false 1 true
        this.dataWatcher.addObject(25, Byte.valueOf((byte) 0)); // isEmo - 0 false 1 true
        this.dataWatcher.addObject(26, Integer.valueOf(0)); // kittenstate int
    }

    public int getKittyState() {
        return this.dataWatcher.getWatchableObjectInt(26);
    }

    @Override
    public boolean getIsSitting() {
        return (this.dataWatcher.getWatchableObjectByte(23) == 1);
    }

    public boolean getIsHungry() {
        return (this.dataWatcher.getWatchableObjectByte(24) == 1);
    }

    public boolean getIsEmo() {
        return (this.dataWatcher.getWatchableObjectByte(25) == 1);
    }

    public boolean getIsSwinging() {
        return this.isSwinging;
    }

    public boolean getOnTree() {
        return this.onTree;
    }

    public void setKittyState(int i) {
        this.dataWatcher.updateObject(26, Integer.valueOf(i));
    }

    public void setSitting(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(23, Byte.valueOf(input));
    }

    public void setHungry(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(24, Byte.valueOf(input));
    }

    public void setIsEmo(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(25, Byte.valueOf(input));
    }

    public void setOnTree(boolean var1) {
        this.onTree = var1;
    }

    public void setSwinging(boolean var1) {
        this.isSwinging = var1;
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        if ((getKittyState() != 18) && (getKittyState() != 10)) {
            swingArm();
        }
        if (((getKittyState() == 13) && (entityIn instanceof EntityPlayer)) || ((getKittyState() == 8) && (entityIn instanceof EntityItem))
                || ((getKittyState() == 18) && (entityIn instanceof MoCEntityKitty)) || (getKittyState() == 10)) {
            return false;
        }
        return super.attackEntityAsMob(entityIn);
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (super.attackEntityFrom(damagesource, i)) {
            Entity entity = damagesource.getEntity();
            if (entity != this && entity instanceof EntityLivingBase) {
                EntityLivingBase entityliving = (EntityLivingBase) entity;
                if (getKittyState() == 10) {
                    List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(16D, 6D, 16D));
                    for (int j = 0; j < list.size(); j++) {
                        Entity entity1 = list.get(j);
                        if ((entity1 instanceof MoCEntityKitty) && (((MoCEntityKitty) entity1).getKittyState() == 21)) {
                            ((MoCEntityKitty) entity1).setAttackTarget(entityliving);
                            return true;
                        }
                    }

                    return true;
                }
                if (entityliving instanceof EntityPlayer && super.shouldAttackPlayers()) {
                    if (getKittyState() < 2) {
                        setAttackTarget(entityliving);
                        setKittyState(-1);
                    }
                    if ((getKittyState() == 19) || (getKittyState() == 20) || (getKittyState() == 21)) {
                        setAttackTarget(entityliving);
                        setSitting(false);
                        return true;
                    }
                    if ((getKittyState() > 1) && (getKittyState() != 10) && (getKittyState() != 19) && (getKittyState() != 20)
                            && (getKittyState() != 21)) {
                        setKittyState(13);
                        setSitting(false);
                    }
                    return true;
                }
                setAttackTarget(entityliving);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected boolean canDespawn() {
        if (MoCreatures.proxy.forceDespawns) {
            return getKittyState() < 3;
        } else {
            return false;
        }
    }

    private void changeKittyState(int i) {
        setKittyState(i);
        if (MoCreatures.isServer()) {
            mountEntity(null);
        }
        setSitting(false);
        this.kittytimer = 0;
        setOnTree(false);
        this.foundTree = false;
        setAttackTarget(null);
        this.itemAttackTarget = null;
    }

    public boolean climbingTree() {
        return (getKittyState() == 16) && isOnLadder();
    }

    @Override
    public void fall(float f, float f1) {
    }

    @Override
    protected Entity findPlayerToAttack() {
        if ((this.worldObj.getDifficulty().getDifficultyId() > 0) && (getKittyState() != 8) && (getKittyState() != 10) && (getKittyState() != 15)
                && (getKittyState() != 18) && (getKittyState() != 19) && !isMovementCeased() && getIsHungry()) {
            EntityLivingBase entityliving = getClosestTarget(this, 10D);
            return entityliving;
        } else {
            return null;
        }
    }

    //TODO
    //change this so MoCAnimal getBoogey is used instead to decrease duplication of code
    public EntityLivingBase getBoogey(double d, boolean flag) {
        EntityLivingBase entityliving = null;
        List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(d, 4D, d));
        for (int i = 0; i < list.size(); i++) {
            Entity entity = list.get(i);
            if ((entity instanceof EntityLivingBase) && !(entity instanceof MoCEntityDeer) && !(entity instanceof MoCEntityHorse)
                    && ((entity.width >= 0.5D) || (entity.height >= 0.5D)) && (flag || !(entity instanceof EntityPlayer))) {
                entityliving = (EntityLivingBase) entity;
            }
        }

        return entityliving;
    }

    //TODO use MoCAnimal instead
    @Override
    public EntityLiving getClosestTarget(Entity entity, double d) {
        double d1 = -1D;
        EntityLiving entityliving = null;
        List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(d, d, d));
        for (int i = 0; i < list.size(); i++) {
            Entity entity1 = list.get(i);
            if (!(entity1 instanceof EntityLiving) || (entity1 instanceof MoCEntityKitty) || (entity1 instanceof EntityPlayer)
                    || (entity1 instanceof EntityMob) || (entity1 instanceof MoCEntityKittyBed) || (entity1 instanceof MoCEntityLitterBox)
                    || ((entity1.width > 0.5D) && (entity1.height > 0.5D)) || ((entity instanceof IMoCEntity) && !MoCreatures.proxy.enableHunters)) {
                continue;
            }
            double d2 = entity1.getDistanceSq(entity.posX, entity.posY, entity.posZ);
            if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1)) && ((EntityLiving) entity1).canEntityBeSeen(entity)) {
                d1 = d2;
                entityliving = (EntityLiving) entity1;
            }
        }

        return entityliving;
    }

    @Override
    protected String getDeathSound() {
        if (getKittyState() == 10) {
            return "mocreatures:kittendying";
        } else {
            return "mocreatures:kittydying";
        }
    }

    @Override
    protected Item getDropItem() {
        return null;
    }

    public ResourceLocation getEmoteIcon() {
        switch (getKittyState()) {
            case -1:
                return MoCreatures.proxy.getTexture("emoticon2.png");

            case 3: // '\003'
                return MoCreatures.proxy.getTexture("emoticon3.png");

            case 4: // '\004'
                return MoCreatures.proxy.getTexture("emoticon4.png");

            case 5: // '\005'
                return MoCreatures.proxy.getTexture("emoticon5.png");

            case 7: // '\007'
                return MoCreatures.proxy.getTexture("emoticon7.png");

            case 8: // '\b'
                return MoCreatures.proxy.getTexture("emoticon8.png");

            case 9: // '\t'
                return MoCreatures.proxy.getTexture("emoticon9.png");

            case 10: // '\n'
                return MoCreatures.proxy.getTexture("emoticon10.png");

            case 11: // '\013'
                return MoCreatures.proxy.getTexture("emoticon11.png");

            case 12: // '\f'
                return MoCreatures.proxy.getTexture("emoticon12.png");

            case 13: // '\r'
                return MoCreatures.proxy.getTexture("emoticon13.png");

            case 16: // '\020'
                return MoCreatures.proxy.getTexture("emoticon16.png");

            case 17: // '\021'
                return MoCreatures.proxy.getTexture("emoticon17.png");

            case 18: // '\022'
                return MoCreatures.proxy.getTexture("emoticon9.png");

            case 19: // '\023'
                return MoCreatures.proxy.getTexture("emoticon19.png");

            case 20: // '\024'
                return MoCreatures.proxy.getTexture("emoticon19.png");

            case 21: // '\025'
                return MoCreatures.proxy.getTexture("emoticon10.png");

            case 0: // '\0'
            case 1: // '\001'
            case 2: // '\002'
            case 6: // '\006'
            case 14: // '\016'
            case 15: // '\017'
            default:
                return MoCreatures.proxy.getTexture("emoticon1.png");
        }
    }

    @Override
    protected String getHurtSound() {
        if (getKittyState() == 10) {
            return "mocreatures:kittenhurt";
        } else {
            return "mocreatures:kittyhurt";
        }
    }

    public EntityLiving getKittyStuff(Entity entity, double d, boolean flag) {
        double d1 = -1D;
        Object obj = null;
        List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(entity, getEntityBoundingBox().expand(d, d, d));
        for (int i = 0; i < list.size(); i++) {
            Entity entity1 = list.get(i);
            if (flag) {
                if (!(entity1 instanceof MoCEntityLitterBox)) {
                    continue;
                }
                MoCEntityLitterBox entitylitterbox = (MoCEntityLitterBox) entity1;
                if (entitylitterbox.getUsedLitter()) {
                    continue;
                }
                double d2 = entity1.getDistanceSq(entity.posX, entity.posY, entity.posZ);
                if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1.0D) || (d2 < d1)) && entitylitterbox.canEntityBeSeen(entity)) {
                    d1 = d2;
                    obj = entitylitterbox;
                }
                continue;
            }
            if (!(entity1 instanceof MoCEntityKittyBed)) {
                continue;
            }
            MoCEntityKittyBed entitykittybed = (MoCEntityKittyBed) entity1;
            double d3 = entity1.getDistanceSq(entity.posX, entity.posY, entity.posZ);
            if (((d < 0.0D) || (d3 < (d * d))) && ((d1 == -1.0D) || (d3 < d1)) && entitykittybed.canEntityBeSeen(entity)) {
                d1 = d3;
                obj = entitykittybed;
            }
        }

        return ((EntityLiving) (obj));
    }

    @Override
    protected String getLivingSound() {
        if (getKittyState() == 4) {
            if (this.getRidingEntity() != null) {
                MoCEntityKittyBed entitykittybed = (MoCEntityKittyBed) this.getRidingEntity();
                if ((entitykittybed != null) && !entitykittybed.getHasMilk()) {
                    return "mocreatures:kittyeatingm";
                }
                if ((entitykittybed != null) && !entitykittybed.getHasFood()) {
                    return "mocreatures:kittyeatingf";
                }
            }
            return null;
        }
        if (getKittyState() == 6) {
            return "mocreatures:kittylitter";
        }
        if (getKittyState() == 3) {
            return "mocreatures:kittyfood";
        }
        if (getKittyState() == 10) {
            return "mocreatures:kittengrunt";
        }
        if (getKittyState() == 13) {
            return "mocreatures:kittyupset";
        }
        if (getKittyState() == 17) {
            return "mocreatures:kittytrapped";
        }
        if ((getKittyState() == 18) || (getKittyState() == 12)) {
            return "mocreatures:kittypurr";
        } else {
            return "mocreatures:kittygrunt";
        }
    }

    @Override
    public double getYOffset() {
        if (this.getRidingEntity() instanceof EntityPlayer && this.getRidingEntity() == MoCreatures.proxy.getPlayer() && !MoCreatures.isServer()) {
            if (getKittyState() == 10) {
                return (super.getYOffset() - 1.1F);
            }
            if (upsideDown()) {
                return (super.getYOffset() - 1.7F);
            }
            if (onMaBack()) {
                return (super.getYOffset() - 1.5F);
            }
        }

        if ((this.getRidingEntity() instanceof EntityPlayer) && !MoCreatures.isServer()) {
            if (getKittyState() == 10) {
                return (super.getYOffset() + 0.3F);
            }
            if (upsideDown()) {
                return (super.getYOffset() - 0.1F);
            }
            if (onMaBack()) {
                return (super.getYOffset() + 0.1F);
            }
        }

        return super.getYOffset();
    }

    @Override
    public boolean interact(EntityPlayer entityplayer) {
        if (super.interact(entityplayer)) {
            return false;
        }
        //Ownership code
        //if (MoCreatures.proxy.enableOwnership && getOwnerName() != null && !getOwnerName().equals("") && !entityplayer.getName().equals(getOwnerName())) { return true; }

        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if ((getKittyState() == 2) && (itemstack != null) && (itemstack.getItem() == MoCreatures.medallion)) {
            if (MoCreatures.isServer()) {
                MoCTools.tameWithName(entityplayer, this);
            }
            if (getIsTamed() && --itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            if (getIsTamed()) {
                changeKittyState(3);
                this.setHealth(getMaxHealth());
                return true;
            }
            return false;
        }
        if ((getKittyState() == 7) && (itemstack != null)
                && ((itemstack.getItem() == Items.cake) || (itemstack.getItem() == Items.fish) || (itemstack.getItem() == Items.cooked_fish))) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            this.worldObj.playSoundAtEntity(this, "mocreatures:kittyeatingf", 1.0F, 1.0F + ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F));
            this.setHealth(getMaxHealth());
            changeKittyState(9);
            return true;
        }
        if ((getKittyState() == 11) && (itemstack != null) && (itemstack.getItem() == MoCreatures.woolball) && MoCreatures.isServer()) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            setKittyState(8);
            EntityItem entityitem = new EntityItem(this.worldObj, this.posX, this.posY + 1.0D, this.posZ, new ItemStack(MoCreatures.woolball, 1));
            entityitem.setPickupDelay(30);
            entityitem.setNoDespawn();
            this.worldObj.spawnEntityInWorld(entityitem);
            entityitem.motionY += this.worldObj.rand.nextFloat() * 0.05F;
            entityitem.motionX += (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.3F;
            entityitem.motionZ += (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.3F;
            this.itemAttackTarget = entityitem;
            return true;
        }
        if ((getKittyState() == 13) && (itemstack != null) && ((itemstack.getItem() == Items.fish) || (itemstack.getItem() == Items.cooked_fish))) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            this.worldObj.playSoundAtEntity(this, "mocreatures:kittyeatingf", 1.0F, 1.0F + ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F));
            this.setHealth(getMaxHealth());
            changeKittyState(7);
            return true;
        }
        if ((itemstack != null) && (getKittyState() > 2) && ((itemstack.getItem() == MoCreatures.medallion) || (itemstack.getItem() == Items.book))) {
            if (MoCreatures.isServer()) {
                MoCTools.tameWithName(entityplayer, this);
            }

            return true;
        }
        if ((itemstack != null) && (getKittyState() > 2) && pickable() && (itemstack.getItem() == Items.lead)) {
            changeKittyState(14);
            if (MoCreatures.isServer()) {
                mountEntity(entityplayer);
            }
            return true;
        }
        if ((itemstack != null) && (getKittyState() > 2) && whipeable() && (itemstack.getItem() == MoCreatures.whip)) {
            setSitting(!getIsSitting());
            return true;
        }
        if ((itemstack == null) && (getKittyState() == 10) && (this.getRidingEntity() != null)) {
            this.getRidingEntity() = null;
            return true;
        }
        if ((itemstack == null) && (getKittyState() > 2) && pickable()) {
            changeKittyState(15);
            if (MoCreatures.isServer()) {
                mountEntity(entityplayer);
            }
            return true;
        }
        if ((itemstack == null) && (getKittyState() == 15)) {
            changeKittyState(7);
            return true;
        }
        if ((getKittyState() == 14) && this.getRidingEntity() != null) {
            changeKittyState(7);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isMovementCeased() {
        return getIsSitting() || (getKittyState() == 6) || ((getKittyState() == 16) && getOnTree()) || (getKittyState() == 12)
                || (getKittyState() == 17) || (getKittyState() == 14) || (getKittyState() == 20) || (getKittyState() == 23);
    }

    @Override
    public boolean isOnLadder() {
        if (getKittyState() == 16) {
            return this.isCollidedHorizontally && getOnTree();
        } else {
            return super.isOnLadder();
        }
    }

    @Override
    public void onLivingUpdate() {
        if (MoCreatures.isServer()) {
            if (!getIsAdult() && (getKittyState() != 10)) {
                setKittyState(10);
            }
            if (getKittyState() != 12) {
                super.onLivingUpdate();
            }
            if (this.rand.nextInt(200) == 0) {
                setIsEmo(!getIsEmo());
            }
            if (!getIsAdult() && (this.rand.nextInt(200) == 0)) {
                setEdad(getEdad() + 1);
                if (getEdad() >= 100) {
                    setAdult(true);
                }
            }
            if (!getIsHungry() && !getIsSitting() && (this.rand.nextInt(100) == 0)) {
                setHungry(true);
            }

            label0: switch (getKittyState()) {
                case -1:
                    break;
                case 23: // '\027'
                    break;

                case 1: // '\001'
                    if (this.rand.nextInt(10) == 0) {
                        EntityLivingBase entityliving = getBoogey(6D, true);
                        if (entityliving != null) {
                            MoCTools.runLikeHell(this, entityliving);
                        }
                        break;
                    }
                    if (!getIsHungry() || (this.rand.nextInt(10) != 0)) {
                        break;
                    }
                    EntityItem entityitem = getClosestItem(this, 10D, Items.cooked_fish, Items.cooked_fish);
                    if (entityitem == null) {
                        break;
                    }
                    float f = entityitem.getDistanceToEntity(this);
                    if (f > 2.0F) {
                        getMyOwnPath(entityitem, f);
                    }
                    if ((f < 2.0F) && (entityitem != null) && (this.deathTime == 0)) {
                        entityitem.setDead();
                        this.worldObj.playSoundAtEntity(this, "mocreatures:kittyeatingf", 1.0F,
                                1.0F + ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F));
                        setHungry(false);
                        setKittyState(2);
                    }
                    break;

                case 2: // '\002'
                    EntityLivingBase entityliving1 = getBoogey(6D, false);
                    if (entityliving1 != null) {
                        MoCTools.runLikeHell(this, entityliving1);
                    }
                    break;

                case 3: // '\003'
                    this.kittytimer++;
                    if (this.kittytimer > 500) {
                        if (this.rand.nextInt(200) == 0) {
                            changeKittyState(13);
                            break;
                        }
                        if (this.rand.nextInt(500) == 0) {
                            changeKittyState(7);
                            break;
                        }
                    }
                    if (this.rand.nextInt(20) != 0) {
                        break;
                    }
                    MoCEntityKittyBed entitykittybed = (MoCEntityKittyBed) getKittyStuff(this, 18D, false);
                    if ((entitykittybed == null) || (entitykittybed.riddenByEntity != null)
                            || (!entitykittybed.getHasMilk() && !entitykittybed.getHasFood())) {
                        break;
                    }
                    float f5 = entitykittybed.getDistanceToEntity(this);
                    if (f5 > 2.0F) {
                        getMyOwnPath(entitykittybed, f5);
                    }
                    if (f5 < 2.0F) {
                        changeKittyState(4);
                        mountEntity(entitykittybed);
                        setSitting(true);
                    }
                    break;

                case 4: // '\004'
                    if (this.getRidingEntity() != null) {
                        MoCEntityKittyBed entitykittybed1 = (MoCEntityKittyBed) this.getRidingEntity();
                        if ((entitykittybed1 != null) && !entitykittybed1.getHasMilk() && !entitykittybed1.getHasFood()) {
                            this.setHealth(getMaxHealth());
                            changeKittyState(5);
                        }
                    } else {
                        this.setHealth(getMaxHealth());
                        changeKittyState(5);
                    }
                    if (this.rand.nextInt(2500) == 0) {
                        this.setHealth(getMaxHealth());
                        changeKittyState(7);
                    }
                    break;

                case 5: // '\005'
                    this.kittytimer++;
                    if ((this.kittytimer > 2000) && (this.rand.nextInt(1000) == 0)) {
                        changeKittyState(13);
                        break;
                    }
                    if (this.rand.nextInt(20) != 0) {
                        break;
                    }
                    MoCEntityLitterBox entitylitterbox = (MoCEntityLitterBox) getKittyStuff(this, 18D, true);
                    if ((entitylitterbox == null) || (entitylitterbox.riddenByEntity != null) || entitylitterbox.getUsedLitter()) {
                        break;
                    }
                    float f6 = entitylitterbox.getDistanceToEntity(this);
                    if (f6 > 2.0F) {
                        getMyOwnPath(entitylitterbox, f6);
                    }
                    if (f6 < 2.0F) {
                        changeKittyState(6);
                        mountEntity(entitylitterbox);
                    }
                    break;

                case 6: // '\006'
                    this.kittytimer++;
                    if (this.kittytimer <= 300) {
                        break;
                    }
                    this.worldObj.playSoundAtEntity(this, "mocreatures:kittypoo", 1.0F,
                            1.0F + ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F));
                    MoCEntityLitterBox entitylitterbox1 = (MoCEntityLitterBox) this.getRidingEntity();
                    if (entitylitterbox1 != null) {
                        entitylitterbox1.setUsedLitter(true);
                        entitylitterbox1.littertime = 0;
                    }
                    changeKittyState(7);
                    break;

                case 7: // '\007'
                    if (getIsSitting()) {
                        break;
                    }
                    if (this.rand.nextInt(20) == 0) {
                        EntityPlayer entityplayer = this.worldObj.getClosestPlayerToEntity(this, 12D);
                        if (entityplayer != null) {
                            ItemStack itemstack = entityplayer.inventory.getCurrentItem();
                            if ((itemstack != null) && (itemstack.getItem() == MoCreatures.woolball)) {
                                changeKittyState(11);
                                break;
                            }
                        }
                    }
                    if (this.inWater && (this.rand.nextInt(500) == 0)) {
                        changeKittyState(13);
                        break;
                    }
                    if ((this.rand.nextInt(500) == 0) && !this.worldObj.isDaytime()) {
                        changeKittyState(12);
                        break;
                    }
                    if (this.rand.nextInt(2000) == 0) {
                        changeKittyState(3);
                        break;
                    }
                    if (this.rand.nextInt(4000) == 0) {
                        changeKittyState(16);
                    }
                    break;

                case 8: // '\b'
                    if (this.inWater && this.rand.nextInt(200) == 0) {
                        changeKittyState(13);
                        break;
                    }

                    if (this.itemAttackTarget != null && this.itemAttackTarget instanceof EntityItem) {
                        if (getAttackTarget() != null) {
                            float f1 = getDistanceToEntity(getAttackTarget());
                            if (f1 < 1.5F) {
                                swingArm();
                                if (this.rand.nextInt(10) == 0) {
                                    //float force = 0.3F;
                                    //if (type == 10) force = 0.2F;
                                    MoCTools.bigsmack(this, this.itemAttackTarget, 0.3F);
                                    //kittySmack(this, entityLivingToAttack);
                                }
                            }
                        }
                    }
                    if (getAttackTarget() == null || this.rand.nextInt(1000) == 0) {
                        changeKittyState(7);
                    }
                    break;

                case 9: // looking for mate
                    this.kittytimer++;
                    if (this.rand.nextInt(50) == 0) {
                        List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(16D, 6D, 16D));
                        int j = 0;
                        do {
                            if (j >= list.size()) {
                                break;
                            }
                            Entity entity = list.get(j);
                            if (entity instanceof MoCEntityKitty && ((MoCEntityKitty) entity).getKittyState() == 9) {
                                changeKittyState(18);
                                setAttackTarget((EntityLivingBase) entity);
                                ((MoCEntityKitty) entity).changeKittyState(18);
                                ((MoCEntityKitty) entity).setAttackTarget(this);
                                break;
                            }
                            j++;
                        } while (true);
                    }
                    if (this.kittytimer > 2000) {
                        changeKittyState(7);
                    }
                    break;

                case 10: // '\n'
                    if (getIsAdult()) {
                        changeKittyState(7);
                        break;
                    }
                    if (this.rand.nextInt(50) == 0) {
                        List<Entity> list1 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(16D, 6D, 16D));
                        for (int k = 0; k < list1.size(); k++) {
                            Entity entity1 = list1.get(k);
                            if (!(entity1 instanceof MoCEntityKitty) || (((MoCEntityKitty) entity1).getKittyState() != 21)) {
                                continue;
                            }
                            float f9 = getDistanceToEntity(entity1);
                            if (f9 > 12F) {
                                setAttackTarget((EntityLivingBase) entity1);
                            }
                        }

                    }
                    if ((this.itemAttackTarget == null || getAttackTarget() == null) && (this.rand.nextInt(100) == 0)) {
                        int i = this.rand.nextInt(10);
                        if (i < 7) {
                            this.itemAttackTarget = getClosestItem(this, 10D, null, null);
                        } else {
                            this.setAttackTarget(this.worldObj.getClosestPlayerToEntity(this, 18D));
                        }
                    }
                    if ((this.getAttackTarget() != null || this.itemAttackTarget != null) && (this.rand.nextInt(400) == 0)) {
                        setAttackTarget(null);
                        this.itemAttackTarget = null;
                    }
                    if ((this.itemAttackTarget != null) && (this.itemAttackTarget instanceof EntityItem)) {
                        float f2 = getDistanceToEntity(this.itemAttackTarget);
                        if (f2 < 1.5F) {
                            swingArm();
                            if (this.rand.nextInt(10) == 0) {
                                MoCTools.bigsmack(this, this.itemAttackTarget, 0.2F);
                                //kittySmack(this, entityLivingToAttack);
                            }
                        }
                    }
                    if (getAttackTarget() != null && (getAttackTarget() instanceof MoCEntityKitty) && (this.rand.nextInt(20) == 0)) {
                        float f3 = getDistanceToEntity(getAttackTarget());
                        if (f3 < 2.0F) {
                            swingArm();
                            this.getNavigator().clearPathEntity();
                        }
                    }
                    if ((getAttackTarget() == null) || !(getAttackTarget() instanceof EntityPlayer)) {
                        break;
                    }
                    float f4 = getDistanceToEntity(getAttackTarget());
                    if ((f4 < 2.0F) && (this.rand.nextInt(20) == 0)) {
                        swingArm();
                    }
                    break;

                case 11: // '\013'
                    EntityPlayer entityplayer1 = this.worldObj.getClosestPlayerToEntity(this, 18D);
                    if ((entityplayer1 == null) || (this.rand.nextInt(10) != 0)) {
                        break;
                    }
                    ItemStack itemstack1 = entityplayer1.inventory.getCurrentItem();
                    if ((itemstack1 == null) || ((itemstack1 != null) && (itemstack1.getItem() != MoCreatures.woolball))) {
                        changeKittyState(7);
                        break;
                    }
                    float f8 = entityplayer1.getDistanceToEntity(this);
                    if (f8 > 5F) {
                        getPathOrWalkableBlock(entityplayer1, f8);
                    }
                    break;

                case 12: // '\f'
                    this.kittytimer++;
                    if (this.worldObj.isDaytime() || ((this.kittytimer > 500) && (this.rand.nextInt(500) == 0))) {
                        changeKittyState(7);
                        break;
                    }
                    setSitting(true);
                    if ((this.rand.nextInt(80) == 0) || !this.onGround) {
                        super.onLivingUpdate();
                    }
                    break;

                case 13: // '\r'
                    setHungry(false);
                    setAttackTarget(this.worldObj.getClosestPlayerToEntity(this, 18D));
                    if (getAttackTarget() != null) {
                        float f7 = getDistanceToEntity(getAttackTarget());
                        if (f7 < 1.5F) {
                            swingArm();
                            if (this.rand.nextInt(20) == 0) {
                                this.madtimer--;
                                getAttackTarget().attackEntityFrom(DamageSource.causeMobDamage(this), 1);
                                if (this.madtimer < 1) {
                                    changeKittyState(7);
                                    this.madtimer = this.rand.nextInt(5);
                                }
                            }
                        }
                        if (this.rand.nextInt(500) == 0) {
                            changeKittyState(7);
                        }
                    } else {
                        changeKittyState(7);
                    }
                    break;

                case 14: // held by rope
                    if (this.onGround) {
                        changeKittyState(13);
                        break;
                    }
                    if (this.rand.nextInt(50) == 0) {
                        swingArm();
                    }
                    if (this.getRidingEntity() == null) {
                        break;
                    }
                    this.rotationYaw = this.getRidingEntity().rotationYaw + 90F;
                    EntityPlayer entityplayer2 = (EntityPlayer) this.getRidingEntity();
                    if (entityplayer2 == null) {
                        changeKittyState(13);
                        break;
                    }
                    ItemStack itemstack2 = entityplayer2.inventory.getCurrentItem();
                    if (itemstack2 == null || ((itemstack2 != null) && (itemstack2.getItem() != Items.lead))) {
                        changeKittyState(13);
                    }
                    break;

                case 15: // '\017'
                    if (this.onGround) {
                        changeKittyState(7);
                    }
                    if (this.getRidingEntity() != null) {
                        this.rotationYaw = this.getRidingEntity().rotationYaw + 90F;
                    }
                    break;

                case 16: // '\020'
                    this.kittytimer++;
                    if ((this.kittytimer > 500) && !getOnTree()) {
                        changeKittyState(7);
                    }
                    if (!getOnTree()) {
                        if (!this.foundTree && (this.rand.nextInt(50) == 0)) {
                            int ai[] = MoCTools.ReturnNearestMaterialCoord(this, Material.wood, Double.valueOf(18D), 4D);
                            if (ai[0] != -1) {
                                int i1 = 0;
                                do {
                                    if (i1 >= 20) {
                                        break;
                                    }
                                    Block block = this.worldObj.getBlockState(new BlockPos(ai[0], ai[1] + i1, ai[2])).getBlock();
                                    if ((block.getMaterial() == Material.leaves)) {
                                        this.foundTree = true;
                                        this.treeCoord[0] = ai[0];
                                        this.treeCoord[1] = ai[1];
                                        this.treeCoord[2] = ai[2];
                                        break;
                                    }
                                    i1++;
                                } while (true);
                            }
                        }
                        if (!this.foundTree || (this.rand.nextInt(10) != 0)) {
                            break;
                        }
                        PathEntity pathentity = this.navigator.getPathToXYZ(this.treeCoord[0], this.treeCoord[1], this.treeCoord[2]);

                        if (pathentity != null) {
                            this.navigator.setPath(pathentity, 24F);
                        }
                        Double double1 = Double.valueOf(getDistanceSq(this.treeCoord[0], this.treeCoord[1], this.treeCoord[2]));
                        if (double1.doubleValue() < 7D) {
                            setOnTree(true);
                        }
                        break;
                    }
                    if (!getOnTree()) {
                        break;
                    }
                    int l = this.treeCoord[0];
                    int j1 = this.treeCoord[1];
                    int l1 = this.treeCoord[2];
                    faceItem(l, j1, l1, 30F);
                    if ((j1 - MathHelper.floor_double(this.posY)) > 2) {
                        this.motionY += 0.029999999999999999D;
                    }

                    if (this.posX < l) {
                        this.motionX += 0.01D;
                    } else {
                        this.motionX -= 0.01D;
                    }
                    if (this.posZ < l1) {
                        this.motionZ += 0.01D;
                    } else {
                        this.motionZ -= 0.01D;
                    }
                    if (this.onGround || !this.isCollidedHorizontally || !this.isCollidedVertically) {
                        break;
                    }
                    int i4 = 0;
                    do {
                        if (i4 >= 30) {
                            break label0;
                        }
                        BlockPos pos = new BlockPos(this.treeCoord[0], this.treeCoord[1] + i4, this.treeCoord[2]);
                        Block block = this.worldObj.getBlockState(pos).getBlock();
                        if (block == Blocks.AIR) {
                            setLocationAndAngles(this.treeCoord[0], this.treeCoord[1] + i4, this.treeCoord[2], this.rotationYaw, this.rotationPitch);
                            changeKittyState(17);
                            this.treeCoord[0] = -1;
                            this.treeCoord[1] = -1;
                            this.treeCoord[2] = -1;
                            break label0;
                        }
                        i4++;
                    } while (true);

                case 17: // '\021'
                    EntityPlayer entityplayer3 = this.worldObj.getClosestPlayerToEntity(this, 2D);
                    if (entityplayer3 != null) {
                        changeKittyState(7);
                    }
                    break;

                case 18: // '\022'
                    if ((getAttackTarget() == null) || !(getAttackTarget() instanceof MoCEntityKitty)) {
                        changeKittyState(9);
                        break;
                    }
                    MoCEntityKitty entitykitty = (MoCEntityKitty) getAttackTarget();
                    if ((entitykitty != null) && (entitykitty.getKittyState() == 18)) {
                        if (this.rand.nextInt(50) == 0) {
                            swingArm();
                        }
                        float f10 = getDistanceToEntity(entitykitty);
                        if (f10 < 5F) {
                            this.kittytimer++;
                        }
                        if ((this.kittytimer > 500) && (this.rand.nextInt(50) == 0)) {
                            ((MoCEntityKitty) getAttackTarget()).changeKittyState(7);
                            changeKittyState(19);
                        }
                    } else {
                        changeKittyState(9);
                    }
                    break;

                case 19: // '\023'
                    if (this.rand.nextInt(20) != 0) {
                        break;
                    }
                    MoCEntityKittyBed entitykittybed2 = (MoCEntityKittyBed) getKittyStuff(this, 18D, false);
                    if ((entitykittybed2 == null) || (entitykittybed2.riddenByEntity != null)) {
                        break;
                    }
                    float f11 = entitykittybed2.getDistanceToEntity(this);
                    if (f11 > 2.0F) {
                        getMyOwnPath(entitykittybed2, f11);
                    }
                    if (f11 < 2.0F) {
                        changeKittyState(20);
                        mountEntity(entitykittybed2);
                    }
                    break;

                case 20: // '\024'
                    if (this.getRidingEntity() == null) {
                        changeKittyState(19);
                        break;
                    }
                    this.rotationYaw = 180F;
                    this.kittytimer++;
                    if (this.kittytimer <= 1000) {
                        break;
                    }
                    int i2 = this.rand.nextInt(3) + 1;
                    for (int l2 = 0; l2 < i2; l2++) {
                        MoCEntityKitty entitykitty1 = new MoCEntityKitty(this.worldObj);
                        int babytype = this.getType();
                        if (this.rand.nextInt(2) == 0) {
                            babytype = (this.rand.nextInt(8) + 1);
                        }
                        entitykitty1.setType(babytype);
                        entitykitty1.setPosition(this.posX, this.posY, this.posZ);
                        this.worldObj.spawnEntityInWorld(entitykitty1);
                        this.worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F,
                                ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F) + 1.0F);
                        entitykitty1.setAdult(false);
                        entitykitty1.changeKittyState(10);
                        // attackEntityFrom(DamageSource.generic, 1); blood - workaround to fix
                        // infinite births
                    }

                    changeKittyState(21);
                    break;

                case 21: // '\025'
                    this.kittytimer++;
                    if (this.kittytimer > 2000) {
                        List<Entity> list2 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(24D, 8D, 24D));
                        int i3 = 0;
                        for (int l3 = 0; l3 < list2.size(); l3++) {
                            Entity entity2 = list2.get(l3);
                            if ((entity2 instanceof MoCEntityKitty) && (((MoCEntityKitty) entity2).getKittyState() == 10)) {
                                i3++;
                            }
                        }

                        if (i3 < 1) {
                            changeKittyState(7);
                            break;
                        }
                        this.kittytimer = 1000;
                    }
                    if ((getAttackTarget() != null) && (getAttackTarget() instanceof EntityPlayer) && (this.rand.nextInt(300) == 0)) {
                        setAttackTarget(null);
                    }
                    break;

                case 0:
                    changeKittyState(1);
                    break;
                // case 22: // '\026'
                default:
                    changeKittyState(7);
                    break;
            }
        } else {
            super.onLivingUpdate();
        }
    }

    public boolean onMaBack() {
        return getKittyState() == 15;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (getIsSwinging()) {
            this.swingProgress += 0.2F;
            if (this.swingProgress > 2.0F) {
                setSwinging(false);
                this.swingProgress = 0.0F;
            }
        }
    }

    private boolean pickable() {
        return (getKittyState() != 13) && (getKittyState() != 14) && (getKittyState() != 15) && (getKittyState() != 19) && (getKittyState() != 20)
                && (getKittyState() != 21);
    }

    @Override
    public boolean renderName() {
        return (getKittyState() != 14) && (getKittyState() != 15) && (getKittyState() > 1) && super.renderName();
    }

    @Override
    public void setDead() {
        if (MoCreatures.isServer() && (getKittyState() > 2) && (getHealth() > 0)) {
            return;
        } else {
            super.setDead();
            return;
        }
    }

    public void swingArm() {
        //to synchronize, uses the packet handler to invoke the same method in the clients
        if (MoCreatures.isServer()) {
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 0),
                    new TargetPoint(this.worldObj.provider.getDimensionId(), this.posX, this.posY, this.posZ, 64));
        }

        if (!getIsSwinging()) {
            setSwinging(true);
            this.swingProgress = 0.0F;
        }
    }

    @Override
    public void performAnimation(int i) {
        swingArm();
    }

    public boolean upsideDown() {
        return getKittyState() == 14;
    }

    public boolean whipeable() {
        return getKittyState() != 13;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        setSitting(nbttagcompound.getBoolean("Sitting"));
        setKittyState(nbttagcompound.getInteger("KittyState"));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        nbttagcompound.setBoolean("Sitting", getIsSitting());
        nbttagcompound.setInteger("KittyState", getKittyState());
    }

    /*@Override
    public boolean updateMount() {
        return true;
    }*/

    /*@Override
    public boolean forceUpdates() {
        return true;
    }*/

    //drops medallion on death
    @Override
    public void onDeath(DamageSource damagesource) {
        if (MoCreatures.isServer()) {
            if (getIsTamed()) {
                MoCTools.dropCustomItem(this, this.worldObj, new ItemStack(MoCreatures.medallion, 1));
            }
        }
        super.onDeath(damagesource);
    }

    @Override
    public boolean swimmerEntity() {
        return true;
    }

    @Override
    public int nameYOffset() {
        if (this.getIsSitting())
            return -30;
        return -40;
    }
}
