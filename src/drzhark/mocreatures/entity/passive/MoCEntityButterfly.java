package drzhark.mocreatures.entity.passive;

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
        //texture = MoCreatures.proxy.MODEL_TEXTURE + "butterflyb.png";
    }

    private int fCounter;

    //private float transFloat;

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
            /*int k = rand.nextInt(100);
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
                setType(5);
            }*/
        }
    }

    @Override
    public String getTexture()
    {

        switch (getType())
        {
        case 1:
            return MoCreatures.proxy.MODEL_TEXTURE + "bfagalaisurticae.png";
        case 2:
            return MoCreatures.proxy.MODEL_TEXTURE + "bfargyreushyperbius.png";
        case 3:
            return MoCreatures.proxy.MODEL_TEXTURE + "bfathymanefte.png";
        case 4:
            return MoCreatures.proxy.MODEL_TEXTURE + "bfcatopsiliapomona.png";
        case 5:
            return MoCreatures.proxy.MODEL_TEXTURE + "bfmorphopeleides.png";
        case 6:
            return MoCreatures.proxy.MODEL_TEXTURE + "bfvanessaatalanta.png";
        case 7:
            return MoCreatures.proxy.MODEL_TEXTURE + "bfpierisrapae.png";
        case 8:
            return MoCreatures.proxy.MODEL_TEXTURE + "mothcamptogrammabilineata.png";
        case 9:
            return MoCreatures.proxy.MODEL_TEXTURE + "mothidiaaemula.png";
        case 10:
            return MoCreatures.proxy.MODEL_TEXTURE + "moththyatirabatis.png";
        default:
            return MoCreatures.proxy.MODEL_TEXTURE + "bfpierisrapae.png";
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
    public boolean getCanSpawnHere()
    {
        return (MoCreatures.proxy.getFrequency(this.getEntityName()) > 0) && super.getCanSpawnHere();
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
