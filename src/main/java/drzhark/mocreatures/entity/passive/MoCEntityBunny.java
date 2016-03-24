package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.EntityAIFleeFromPlayer;
import drzhark.mocreatures.entity.ai.EntityAIFollowAdult;
import drzhark.mocreatures.entity.ai.EntityAIFollowOwnerPlayer;
import drzhark.mocreatures.entity.ai.EntityAIPanicMoC;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import java.util.List;

public class MoCEntityBunny extends MoCEntityTameableAnimal {

    private int bunnyReproduceTickerA;
    private int bunnyReproduceTickerB;
    private int jumpTimer;

    public MoCEntityBunny(World world) {
        super(world);
        setAdult(true);
        setTamed(false);
        setEdad(50 + this.rand.nextInt(15));
        if (this.rand.nextInt(4) == 0) {
            setAdult(false);
        }
        setSize(0.5F, 0.5F);

        this.bunnyReproduceTickerA = this.rand.nextInt(64);
        this.bunnyReproduceTickerB = 0;

        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIFollowOwnerPlayer(this, 0.8D, 6F, 5F));
        this.tasks.addTask(2, new EntityAIPanicMoC(this, 1.0D));
        this.tasks.addTask(3, new EntityAIFleeFromPlayer(this, 1.0D, 4D));
        this.tasks.addTask(4, new EntityAIFollowAdult(this, 1.0D));
        this.tasks.addTask(5, new EntityAIWanderMoC2(this, 0.8D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.35D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(23, Byte.valueOf((byte) 0)); // hasEaten - 0 false 1 true
    }

    public boolean getHasEaten() {
        return (this.dataWatcher.getWatchableObjectByte(23) == 1);
    }

    public void setHasEaten(boolean flag) {
        byte input = (byte) (flag ? 1 : 0);
        this.dataWatcher.updateObject(23, Byte.valueOf(input));
    }

    @Override
    public void selectType() {
        checkSpawningBiome();

        if (getType() == 0) {
            setType(this.rand.nextInt(5) + 1);
        }

    }

    @Override
    public boolean checkSpawningBiome() {
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(getEntityBoundingBox().minY);
        int k = MathHelper.floor_double(this.posZ);
        BlockPos pos = new BlockPos(i, j, k);

        BiomeGenBase currentbiome = MoCTools.Biomekind(this.worldObj, pos);
        try {
            if (BiomeDictionary.isBiomeOfType(currentbiome, Type.SNOWY)) {
                setType(3); //snow white bunnies!
                return true;
            }
        } catch (Exception e) {
        }
        return true;
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getType()) {
            case 1:
                return MoCreatures.proxy.getTexture("bunny.png");
            case 2:
                return MoCreatures.proxy.getTexture("bunnyb.png");
            case 3:
                return MoCreatures.proxy.getTexture("bunnyc.png");
            case 4:
                return MoCreatures.proxy.getTexture("bunnyd.png");
            case 5:
                return MoCreatures.proxy.getTexture("bunnye.png");

            default:
                return MoCreatures.proxy.getTexture("bunny.png");
        }
    }

    @Override
    public void fall(float f, float f1) {
    }

    @Override
    protected String getDeathSound() {
        return "mocreatures:rabbitdeath";
    }

    @Override
    protected String getHurtSound() {
        return "mocreatures:rabbithurt";
    }

    @Override
    protected String getLivingSound() {
        return null;
    }

    @Override
    public boolean interact(EntityPlayer entityplayer) {
        if (super.interact(entityplayer)) {
            return false;
        }

        ItemStack itemstack = entityplayer.inventory.getCurrentItem();

        if ((itemstack != null) && (itemstack.getItem() == Items.golden_carrot) && !getHasEaten()) {
            if (--itemstack.stackSize == 0) {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }
            setHasEaten(true);
            MoCTools.playCustomSound(this, "mocreatures:eating", this.worldObj);
            return true;
        }

        /*
         * if (itemstack != null && this.getIsTamed() && itemstack.getItem() ==
         * Items.golden_carrot) { if (--itemstack.stackSize == 0) {
         * entityplayer.
         * inventory.setInventorySlotContents(entityplayer.inventory
         * .currentItem, null); } setHasEaten(true);
         * MoCTools.playCustomSound(this, "eating", worldObj); if
         * (MoCreatures.isServer()) { MoCEntityBunny baby = new
         * MoCEntityBunny(worldObj); baby.setPosition(posX, posY, posZ);
         * worldObj.spawnEntityInWorld(baby); worldObj.playSoundAtEntity(this,
         * "mob.chickenplop", 1.0F, ((rand.nextFloat() - rand.nextFloat()) *
         * 0.2F) + 1.0F); baby.setAdult(false); baby.setType(this.getType());
         * baby.setOwner(this.getOwnerName()); baby.setTamed(true);
         * baby.setAdult(false); MoCTools.tameWithName(entityplayer, baby); }
         * return true; }
         */

        this.rotationYaw = entityplayer.rotationYaw;
        if (this.ridingEntity == null) {
            // This is required since the server will send a Packet39AttachEntity which informs the client to mount
            if (MoCreatures.isServer()) {
                mountEntity(entityplayer);
            }

            if (MoCreatures.isServer() && !getIsTamed()) {
                MoCTools.tameWithName(entityplayer, this);
            }
        } else {
            this.worldObj.playSoundAtEntity(this, "mocreatures:rabbitlift", 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F) + 1.0F);
            if (MoCreatures.isServer()) {
                this.mountEntity(null);
            }
        }

        this.motionX = entityplayer.motionX * 5D;
        this.motionY = (entityplayer.motionY / 2D) + 0.5D;
        this.motionZ = entityplayer.motionZ * 5D;

        return true;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (this.ridingEntity != null) {
            this.rotationYaw = this.ridingEntity.rotationYaw;
        }
        if (MoCreatures.isServer()) {

            if (--this.jumpTimer <= 0 && this.onGround
                    && ((this.motionX > 0.05D) || (this.motionZ > 0.05D) || (this.motionX < -0.05D) || (this.motionZ < -0.05D))) {
                this.motionY = 0.3D;
                this.jumpTimer = 15;
            }

            if (!getIsTamed() || !getIsAdult() || !getHasEaten() || (this.ridingEntity != null)) {
                return;
            }
            if (this.bunnyReproduceTickerA < 1023) {
                this.bunnyReproduceTickerA++;
            } else if (this.bunnyReproduceTickerB < 127) {
                this.bunnyReproduceTickerB++;
            } else {
                List<Entity> list1 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(4.0D, 4.0D, 4.0D));
                for (int i1 = 0; i1 < list1.size(); i1++) {
                    Entity entity1 = (Entity) list1.get(i1);
                    if (!(entity1 instanceof MoCEntityBunny) || (entity1 == this)) {
                        continue;
                    }
                    MoCEntityBunny entitybunny = (MoCEntityBunny) entity1;
                    if ((entitybunny.ridingEntity != null) || (entitybunny.bunnyReproduceTickerA < 1023) || !entitybunny.getIsAdult()
                            || !entitybunny.getHasEaten()) {
                        continue;
                    }
                    MoCEntityBunny entitybunny1 = new MoCEntityBunny(this.worldObj);
                    entitybunny1.setPosition(this.posX, this.posY, this.posZ);
                    entitybunny1.setAdult(false);
                    int babytype = this.getType();
                    if (this.rand.nextInt(2) == 0) {
                        babytype = entitybunny.getType();
                    }
                    entitybunny1.setType(babytype);
                    this.worldObj.spawnEntityInWorld(entitybunny1);
                    this.worldObj.playSoundAtEntity(this, "mob.chickenplop", 1.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F) + 1.0F);
                    proceed();
                    entitybunny.proceed();
                    break;
                }
            }
        }
    }

    public void proceed() {
        setHasEaten(false);
        this.bunnyReproduceTickerB = 0;
        this.bunnyReproduceTickerA = this.rand.nextInt(64);
    }

    /*@Override
    public boolean updateMount() {
        return true;
    }*/

    /*@Override
    public boolean forceUpdates() {
        return true;
    }*/

    @Override
    public int nameYOffset() {
        return -40;
    }

    @Override
    public boolean isMyHealFood(ItemStack par1ItemStack) {
        return par1ItemStack != null && par1ItemStack.getItem() == Items.carrot;
    }

    /**
     * So bunny-hats don't suffer damage
     */
    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (this.ridingEntity != null) {
            return false;
        }
        return super.attackEntityFrom(damagesource, i);
    }

    @Override
    public boolean isNotScared() {
        return getIsTamed();
    }

    @Override
    public double getYOffset() {
        // If we are in SMP, do not alter offset on any client other than the player being mounted on
        if (this.ridingEntity instanceof EntityPlayer && this.ridingEntity == MoCreatures.proxy.getPlayer() && !MoCreatures.isServer()) {
            return ((EntityPlayer) this.ridingEntity).isSneaking() ? 0.25 : 0.5F;
        }
        if ((this.ridingEntity instanceof EntityPlayer) && !MoCreatures.isServer()) {
            return (super.getYOffset() + 0.5F);
        }
        return super.getYOffset();
    }

    @Override
    public float getAdjustedYOffset() {
        return 0.2F;
    }
}
