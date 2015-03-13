package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAquatic;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MoCEntitySmallFish extends MoCEntityTameableAquatic {

    public static final String fishNames[] = {"Anchovy", "Angelfish", "Angler", "Clownfish", "Goldfish", "Hippotang", "Manderin"};

    private int latMovCounter;

    public MoCEntitySmallFish(World world) {
        super(world);
        setSize(0.3F, 0.3F);
        setEdad(30 + this.rand.nextInt(70));

    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0D);
    }

    @Override
    public void selectType() {
        if (getType() == 0) {
            setType(this.rand.nextInt(7) + 1);
        }

    }

    @Override
    public ResourceLocation getTexture() {

        switch (getType()) {
            case 1:
                return MoCreatures.proxy.getTexture("smallfish_anchovy.png");
            case 2:
                return MoCreatures.proxy.getTexture("smallfish_angelfish.png");
            case 3:
                return MoCreatures.proxy.getTexture("smallfish_angler.png");
            case 4:
                return MoCreatures.proxy.getTexture("smallfish_clownfish.png");
            case 5:
                return MoCreatures.proxy.getTexture("smallfish_goldfish.png");
            case 6:
                return MoCreatures.proxy.getTexture("smallfish_hippotang.png");
            case 7:
                return MoCreatures.proxy.getTexture("smallfish_manderin.png");
            default:
                return MoCreatures.proxy.getTexture("smallfish_clownfish.png");
        }
    }

    @Override
    protected boolean canBeTrappedInNet() {
        return true;
    }

    @Override
    protected void dropFewItems(boolean flag, int x) {
        int i = this.rand.nextInt(100);
        if (i < 70) {
            entityDropItem(new ItemStack(Items.fish, 1, 0), 0.0F);
        } else {
            int j = this.rand.nextInt(2);
            for (int k = 0; k < j; k++) {
                entityDropItem(new ItemStack(MoCreatures.mocegg, 1, getType() + 79), 0.0F);
            }
        }
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if ((MoCreatures.isServer()) && !getIsAdult() && (this.rand.nextInt(500) == 0)) {
            setEdad(getEdad() + 1);
            if (getEdad() >= 100) {
                setAdult(true);
            }

            if (!isNotScared() && this.rand.nextInt(5) == 0 && !getIsTamed()) {
                EntityLivingBase entityliving = getBoogey(8D);
                if (entityliving != null && entityliving.isInsideOfMaterial(Material.water)) {
                    MoCTools.runLikeHell(this, entityliving);
                }
            }
            if (getIsTamed() && this.rand.nextInt(100) == 0 && getHealth() < getMaxHealth()) {
                this.setHealth(getMaxHealth());
            }
        }
        if (!this.isInsideOfMaterial(Material.water)) {
            this.prevRenderYawOffset = this.renderYawOffset = this.rotationYaw = this.prevRotationYaw;
            this.rotationPitch = this.prevRotationPitch;
        }
    }

    @Override
    public float getSizeFactor() {
        return getEdad() * 0.01F;
    }

    @Override
    public float getAdjustedYOffset() {
        if (!this.isInsideOfMaterial(Material.water)) {
            return -0.1F;
        }
        return 0.3F;
    }

    @Override
    protected boolean isFisheable() {
        return !getIsTamed();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int yawRotationOffset() {
        if (!this.isInsideOfMaterial(Material.water)) {
            return 90;
        }

        if (this.rand.nextInt(3) == 0) {
            if (++this.latMovCounter > 40) {
                this.latMovCounter = 0;
            }
        }

        int latOffset = 0;
        if (this.latMovCounter < 21) {
            latOffset = this.latMovCounter;
        } else {
            latOffset = -this.latMovCounter + 40;
        }
        return 80 + latOffset;
    }

    @Override
    public int rollRotationOffset() {
        if (!this.isInsideOfMaterial(Material.water)) {
            return -90;
        }
        return 0;
    }

    @Override
    public int nameYOffset() {
        return -25;
    }

    @Override
    public float getAdjustedXOffset() {
        if (!this.isInsideOfMaterial(Material.water)) {
            return -0.6F;
        }
        return 0F;
    }

    @Override
    public float getMoveSpeed() {
        return 0.3F;
    }
}
