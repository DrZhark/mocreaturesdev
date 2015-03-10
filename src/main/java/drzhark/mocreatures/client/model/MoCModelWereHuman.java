package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCModelWereHuman extends ModelBiped {

    public MoCModelWereHuman() {
        //TODO 4.1 FIX
        super(0.0F, 0.0F, 64, 32);
    }
}
