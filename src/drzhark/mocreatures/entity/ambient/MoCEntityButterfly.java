package drzhark.mocreatures.entity.ambient;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityInsect;

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

    @Override
    public ResourceLocation getTexture()
    {
        switch (getType())
        {
        case 1:
            return MoCreatures.proxy.getTexture("bfagalaisurticae.png");
        case 2:
            return MoCreatures.proxy.getTexture("bfargyreushyperbius.png");
        case 3:
            return MoCreatures.proxy.getTexture("bfathymanefte.png");
        case 4:
            return MoCreatures.proxy.getTexture("bfcatopsiliapomona.png");
        case 5:
            return MoCreatures.proxy.getTexture("bfmorphopeleides.png");
        case 6:
            return MoCreatures.proxy.getTexture("bfvanessaatalanta.png");
        case 7:
            return MoCreatures.proxy.getTexture("bfpierisrapae.png");
        case 8:
            return MoCreatures.proxy.getTexture("mothcamptogrammabilineata.png");
        case 9:
            return MoCreatures.proxy.getTexture("mothidiaaemula.png");
        case 10:
            return MoCreatures.proxy.getTexture("moththyatirabatis.png");
        default:
            return MoCreatures.proxy.getTexture("bfpierisrapae.png");
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
        return par1ItemStack != null && (par1ItemStack.getItem() == Item.getItemFromBlock(Blocks.red_flower) || par1ItemStack.getItem() == Item.getItemFromBlock(Blocks.yellow_flower));
    }
    
    @Override
    public boolean isAttractedToLight() 
    {
        return getType() > 7;
    }
}