package drzhark.mocreatures.entity.passive;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAnimal;

public class MoCEntityEnt extends MoCEntityAnimal{

    public MoCEntityEnt(World world) 
    {
        super(world);
        setSize(1.4F, 7F);
        this.stepHeight = 2F;
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(40F);
    }

    @Override
    public void selectType()
    {
        if (getType() == 0)
        {
            setType(rand.nextInt(2)+1);
        }
    }
    
     @Override
        public ResourceLocation getTexture()
        {
            switch (getType())
            {
            case 1:
                return MoCreatures.proxy.getTexture("ent_oak.png");
            case 2:
                return MoCreatures.proxy.getTexture("ent_birch.png");
            default:
                return MoCreatures.proxy.getTexture("ent_oak.png");
            }
        }
     
     @Override
        public float getMoveSpeed()
        {
             return 0.5F;
        }
     

}
