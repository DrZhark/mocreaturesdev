package drzhark.mocreatures.entity.aquatic;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAquatic;
import drzhark.mocreatures.entity.MoCEntityTameableAquatic;

public class MoCEntitySmallFish extends MoCEntityTameableAquatic{

    public static final String fishNames[] = { "Anchovy", "Angelfish", "Angler", "Clownfish", "Goldfish", "Hippotang", "Manderin"};

    private int latMovCounter;
    
    public MoCEntitySmallFish(World world)
    {
        super(world);
        setSize(0.3F, 0.3F);
        health = getMaxHealth();
        setEdad(30 + rand.nextInt(70));
        
    }

    @Override
    public void selectType()
    {
        if (getType() == 0)
        {
            setType(rand.nextInt(7) + 1); 
        }
        
    }

    @Override
    public int getMaxHealth()
    {
        return 4;
    }

    @Override
    public String getTexture()
    {

        switch (getType())
        {
        case 1:
            return MoCreatures.proxy.MODEL_TEXTURE + "smallfish_anchovy.png";
        case 2:
            return MoCreatures.proxy.MODEL_TEXTURE + "smallfish_angelfish.png";
        case 3:
            return MoCreatures.proxy.MODEL_TEXTURE + "smallfish_angler.png";
        case 4:
            return MoCreatures.proxy.MODEL_TEXTURE + "smallfish_clownfish.png";
        case 5:
            return MoCreatures.proxy.MODEL_TEXTURE + "smallfish_goldfish.png";
        case 6:
            return MoCreatures.proxy.MODEL_TEXTURE + "smallfish_hippotang.png";
        case 7:
            return MoCreatures.proxy.MODEL_TEXTURE + "smallfish_manderin.png";
        default:
            return MoCreatures.proxy.MODEL_TEXTURE + "smallfish_clownfish.png";
        }
    }

    @Override
    protected boolean canBeTrappedInNet() 
    {
        return true;
    }

    @Override
    protected void dropFewItems(boolean flag, int x)
    {
        int i = rand.nextInt(100);
        if (i < 70)
        {
            entityDropItem(new ItemStack(Item.fishRaw.itemID, 1, 0), 0.0F);
        }
        else
        {
            int j = rand.nextInt(2);
            for (int k = 0; k < j; k++)
            {
                entityDropItem(new ItemStack(MoCreatures.fishyegg, 1, getType() + 79), 0.0F); 
            }
        }
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if ((MoCreatures.isServer()) && !getIsAdult() && (rand.nextInt(500) == 0))
        {
            setEdad(getEdad() + 1);
            if (getEdad() >= 100)
            {
                setAdult(true);
            }

            if (!isNotScared() && rand.nextInt(5) == 0 && !getIsTamed())
            {
                EntityLiving entityliving = getBoogey(8D);
                if (entityliving != null && entityliving.isInsideOfMaterial(Material.water))
                {
                   MoCTools.runLikeHell(this, entityliving);
                }
            }
            if (getIsTamed() && rand.nextInt(100) == 0 && this.health < getMaxHealth())
            {
                this.health = getMaxHealth();
            }
        }
        if (!this.isInsideOfMaterial(Material.water))
        {
            prevRenderYawOffset = renderYawOffset = rotationYaw = prevRotationYaw;
            rotationPitch = prevRotationPitch;
        }
    }

    @Override
    public float getSizeFactor() 
    {   
        return (float)getEdad() * 0.01F;
    }

    @Override
    public float getAdjustedYOffset()
    {
        if (!this.isInsideOfMaterial(Material.water))
        {
            return -0.1F;
        }
        return 0.3F;
    }

    @Override
    protected boolean isFisheable()
    {
        return !getIsTamed();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int yawRotationOffset()
    {
        if (!this.isInsideOfMaterial(Material.water))
        {
            return 90;
        }

        if (rand.nextInt(3) == 0)
        {
            if (++latMovCounter > 40) latMovCounter = 0;
        }

        int latOffset = 0;
        if (latMovCounter < 21) 
        {
            latOffset = latMovCounter;
        }
        else
        {
            latOffset = -latMovCounter + 40;
        }
         return 80 + latOffset;
    }

    @Override
    public int rollRotationOffset()
    {
        if (!this.isInsideOfMaterial(Material.water))
        {
            return -90;
        }
        return 0;
    }

    @Override
    public boolean renderName()
    {
        return getDisplayName() && (riddenByEntity == null);
    }

    @Override
    public int nameYOffset()
    {
        return -25;
    }

    @Override
    public float getAdjustedXOffset()
    {
        if (!this.isInsideOfMaterial(Material.water))
        {
            return -0.6F;
        }
        return 0F;
    }

    @Override
    public float getMoveSpeed()
    {
        return 0.3F;
    }
}