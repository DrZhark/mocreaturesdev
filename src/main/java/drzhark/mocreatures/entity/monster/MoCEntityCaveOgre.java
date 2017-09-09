package drzhark.mocreatures.entity.monster;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class MoCEntityCaveOgre extends MoCEntityOgre{

    public MoCEntityCaveOgre(World world) {
        super(world);
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getTexture("ogreblue.png");
    }
    
    /**
     * Returns the strength of the blasting power
     * @return
     */
    @Override
    public float getDestroyForce() {
            return MoCreatures.proxy.caveOgreStrength;
    }
    
    @Override
    protected boolean isHarmedByDaylight() {
        return true;
    }
    
    @Override
    public boolean getCanSpawnHere() {
        return (!this.world.canBlockSeeSky(new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.posY), MathHelper
                .floor(this.posZ)))) && (this.posY < 50D) && super.getCanSpawnHere();
    }
    
    @Override
    public float calculateMaxHealth() {
        return 50F;
    }
    
    @Override
    protected Item getDropItem() {
        return Items.DIAMOND;
    } 
    
}