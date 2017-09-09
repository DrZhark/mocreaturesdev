package drzhark.mocreatures.entity.monster;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoCEntityGreenOgre extends MoCEntityOgre{

    public MoCEntityGreenOgre(World world) {
        super(world);
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getTexture("ogregreen.png");
    }
    
    /**
     * Returns the strength of the blasting power
     * @return
     */
    @Override
    public float getDestroyForce() {
            return MoCreatures.proxy.ogreStrength;
    }
    
    @Override
    protected Item getDropItem() {
        return Item.getItemFromBlock(Blocks.OBSIDIAN);
    } 
}
