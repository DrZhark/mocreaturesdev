package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.IMoCTameable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import javax.annotation.Nullable;

public class MoCEntityLeopard extends MoCEntityBigCat {

    public MoCEntityLeopard(World world) {
        super(world);
    }

    @Override
    public void selectType() {

        if (getType() == 0) {
            checkSpawningBiome();
        }
        super.selectType();
    }

    @Override
    public boolean checkSpawningBiome() {
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(getEntityBoundingBox().minY);
        int k = MathHelper.floor_double(this.posZ);
        BlockPos pos = new BlockPos(i, j, k);

        Biome currentbiome = MoCTools.Biomekind(this.worldObj, pos);
        try {
            if (BiomeDictionary.isBiomeOfType(currentbiome, Type.SNOWY)) {
                setType(2); //snow leopard
                return true;
            }
        } catch (Exception e) {
        }
        setType(1);
        return true;
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getType()) {
            case 1:
                return MoCreatures.proxy.getTexture("BCleopard.png");
            case 2:
                return MoCreatures.proxy.getTexture("BCsnowLeopard.png");
            default:
                return MoCreatures.proxy.getTexture("BCleopard.png");
        }
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand, @Nullable ItemStack stack) {
        if (super.processInteract(player, hand, stack)) {
            return true;
        }

        if (getIsRideable() && getIsAdult() && (!this.isBeingRidden())) {
            player.rotationYaw = this.rotationYaw;
            player.rotationPitch = this.rotationPitch;
            setSitting(false);
            if (MoCreatures.isServer()) {
                player.startRiding(this);
            }
            return true;
        }
        return false;
    }

    @Override
    public String getOffspringClazz(IMoCTameable mate) {
        if (mate instanceof MoCEntityPanther && ((MoCEntityPanther) mate).getType() == 1) {
            return "Pantard";//"Panther";
        }
        if (mate instanceof MoCEntityTiger && ((MoCEntityTiger) mate).getType() == 1) {
            return "Leoger";//"Tiger";
        }
        if (mate instanceof MoCEntityLion && ((MoCEntityLion) mate).getType() == 2) {
            return "Liard";//"Lion";
        }
        return "Leopard";
    }

    @Override
    public int getOffspringTypeInt(IMoCTameable mate) {
        if (mate instanceof MoCEntityPanther && ((MoCEntityPanther) mate).getType() == 1) {
            return 1;//3; //panthard
        }
        if (mate instanceof MoCEntityTiger && ((MoCEntityTiger) mate).getType() == 1) {
            return 1;//4; //leoger
        }
        if (mate instanceof MoCEntityLion && ((MoCEntityLion) mate).getType() == 2) {
            return 1;//4; //liard
        }
        return this.getType();
    }

    @Override
    public boolean compatibleMate(Entity mate) {
        return (mate instanceof MoCEntityLeopard && ((MoCEntityLeopard) mate).getType() == this.getType())
                || (mate instanceof MoCEntityPanther && ((MoCEntityPanther) mate).getType() == 1)
                || (mate instanceof MoCEntityTiger && ((MoCEntityTiger) mate).getType() == 1)
                || (mate instanceof MoCEntityLion && ((MoCEntityLion) mate).getType() == 2);
    }

    @Override
    public float calculateMaxHealth() {
        return 25F;
    }

    @Override
    public double calculateAttackDmg() {
        return 5D;
    }

    @Override
    public double getAttackRange() {
        return 6D;
    }

    @Override
    public int getMaxEdad() {
        return 95;
    }

    @Override
    public String getClazzString() {
        return "Leopard";
    }

    @Override
    public boolean canAttackTarget(EntityLivingBase entity) {
        if (!this.getIsAdult() && (this.getEdad() < this.getMaxEdad() * 0.8)) {
            return false;
        }
        if (entity instanceof MoCEntityLeopard) {
            return false;
        }
        return entity.height < 1.3F && entity.width < 1.3F;
    }
    
    @Override
    public float getMoveSpeed() {
            return 1.6F;
    }
}
