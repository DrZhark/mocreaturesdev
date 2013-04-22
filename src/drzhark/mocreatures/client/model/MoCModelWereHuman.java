package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBiped;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MoCModelWereHuman extends ModelBiped {

    public MoCModelWereHuman()
    {
        //TODO 4.1 FIX
        super(0.0F, 0.0F, 64, 32);
    }
}
