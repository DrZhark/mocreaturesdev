package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityInsect;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityCreature;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class MoCEntityButterfly extends MoCEntityInsect 
{
    public MoCEntityButterfly(World world)
    {
        super(world);
    }

    private int fCounter;

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (MoCreatures.isServer())
        {
            if (getIsFlying() && rand.nextInt(200) == 0)
            {
                setIsFlying(false);
            }       
        }
    }
 
    @Override
    public void selectType()
    {
        if (getType() == 0)
        {
            setType(rand.nextInt(10)+1);
        }
    }

    /**
     * Used to flicker ghosts
     * 
     * @return
     */
    public float tFloat()
    {
        if (++this.fCounter > 1000)
        {
            this.fCounter = 0;
        }

        return MathHelper.cos((fCounter * 0.1F)) * 0.2F;
    }

    @Override
    protected float getFlyingSpeed()
    {
        return 0.5F;
    }

    @Override
    protected float getWalkingSpeed()
    {
        return 0.15F;
    }

    @Override
    public float getSizeFactor()
    {
        if (getType() < 8)
        {
            return 0.7F;
        }
        return 1.0F;
    }

    @Override
    public boolean isMyFavoriteFood(ItemStack par1ItemStack)
    {
        return par1ItemStack != null && (par1ItemStack.itemID == Block.plantRed.blockID || par1ItemStack.itemID == Block.plantYellow.blockID);
    }
    
    @Override
    public boolean isAttractedToLight() 
    {
        return getType() > 7;
    }
}