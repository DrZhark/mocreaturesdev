package drzhark.mocreatures.entity.passive;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAquatic;

public class MoCEntityMediumFish extends MoCEntityAquatic{

	public static final String fishNames[] = { "Salmon", "Cod", "Bass"};

    public MoCEntityMediumFish(World world)
    {
        super(world);
        setSize(0.6F, 0.3F);
        health = 8;
        setEdad(30 + rand.nextInt(70));
        //setEdad(100);
        // unused_flag = true;
    }
    
    
    @Override
    public void selectType()
    {
        if (getType() == 0)
        {
        	setType(rand.nextInt(3) + 1);
        }
        
    }
    
    
    @Override
    public String getTexture()
    {

        switch (getType())
        {
        case 1:
            return MoCreatures.proxy.MODEL_TEXTURE + "mediumfish_salmon.png";
        case 2:
            return MoCreatures.proxy.MODEL_TEXTURE + "mediumfish_cod.png";
        case 3:
            return MoCreatures.proxy.MODEL_TEXTURE + "mediumfish_bass.png";
        default:
            return MoCreatures.proxy.MODEL_TEXTURE + "mediumfish_salmon.png";
        }
        
    }
    
    
    @Override
    public boolean getCanSpawnHere()
    {
        return (MoCreatures.proxy.getFrequency(this.getEntityName()) > 0) && super.getCanSpawnHere();
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
                entityDropItem(new ItemStack(MoCreatures.fishyegg, 1, getType()), 0.0F);
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
            if (getEdad() >= 110)
            {
                setAdult(true);
            }
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
    	return 0.7F;
    }
}
