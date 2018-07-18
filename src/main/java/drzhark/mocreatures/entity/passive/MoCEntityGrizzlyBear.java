package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.IMoCTameable;
import drzhark.mocreatures.init.MoCItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoCEntityGrizzlyBear extends MoCEntityBear{

    public MoCEntityGrizzlyBear(World world) {
        super(world);
    }
    
    @Override
    public void selectType() {
        if (getType() == 0) {
            setType(1);
        }
        super.selectType();
    }
    
    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getTexture("bearbrowm.png");
    }

    @Override
    public float getBearSize() {
        return 1.2F;
    }
    
    @Override
    public int getMaxEdad() {
        return 120;
    }
    
    @Override
    public float calculateMaxHealth() {
        return 40;
    }
    
    public double getAttackRange() {
        int factor = 1;
        if (this.world.getDifficulty().getDifficultyId() > 1) {
            factor = 2;
        }
        return 6D * factor;
    }
    
    @Override
    public int getAttackStrength() {
        int factor = (this.world.getDifficulty().getDifficultyId());
        return 3 * factor;
    }
    
    @Override
    public boolean shouldAttackPlayers() {
        return (this.getBrightness() < 0.4F) && super.shouldAttackPlayers();
    }
    
    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        final Boolean tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        final ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && this.getEdad() < 80 && MoCTools.isItemEdibleforCarnivores(stack.getItem())) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }

            if (!getIsTamed() && !this.world.isRemote) {
                MoCTools.tameWithName(player, this);
            }

            this.setHealth(getMaxHealth());
            eatingAnimal();
            if (!this.world.isRemote && !getIsAdult() && (getEdad() < 100)) {
                setEdad(getEdad() + 1);
            }
            return true;
        }
        if (!stack.isEmpty() && getIsTamed() && (stack.getItem() == MoCItems.whip)) {
            if (getBearState() == 0) {
                setBearState(2);
            }else {
                setBearState(0);
            }
            return true;
        }
        if (this.getIsRideable() && this.getIsAdult() && (!this.getIsChested() || !player.isSneaking()) && !this.isBeingRidden()) {
            if (!this.world.isRemote && player.startRiding(this)) {
                player.rotationYaw = this.rotationYaw;
                player.rotationPitch = this.rotationPitch;
                setBearState(0);
            }
            return true;
        }

        return super.processInteract(player, hand);
    }
    
    @Override
    public String getOffspringClazz(IMoCTameable mate) {
        return "GrizzlyBear";
    }

    @Override
    public int getOffspringTypeInt(IMoCTameable mate) {
        return 1;
    }

    @Override
    public boolean compatibleMate(Entity mate) {
        return mate instanceof MoCEntityGrizzlyBear;
    }
}
