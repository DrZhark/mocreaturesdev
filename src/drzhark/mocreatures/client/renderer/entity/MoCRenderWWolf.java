package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.monster.MoCEntityWerewolf;

@SideOnly(Side.CLIENT)
public class MoCRenderWWolf extends RenderLiving {

    private static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "wolfwild.png");
    private static final ResourceLocation TEXTURE_BLACK = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "wolfblack.png");
    private static final ResourceLocation TEXTURE_TIMBER = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "wolftimber.png");
    private static final ResourceLocation TEXTURE_DARK = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "wolfdark.png");
    private static final ResourceLocation TEXTURE_BRIGHT = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "wolfbright.png");

    public MoCRenderWWolf(ModelBase modelbase, float f)
    {
        super(modelbase, f);
    }

    protected ResourceLocation func_110775_a(Entity par1Entity) {
        return this.getTexture((MoCEntityWerewolf)par1Entity);
    }

    protected ResourceLocation getTexture(MoCEntityWerewolf werewolf)
    {
        switch (werewolf.getType())
        {
            case 1:
                return TEXTURE_BLACK;
            case 2:
                return TEXTURE_DEFAULT;
            case 3:
                return TEXTURE_TIMBER; //snow wolf
            case 4:
                return TEXTURE_DARK;
            case 5:
                return TEXTURE_BRIGHT;
    
            default:
                return TEXTURE_DEFAULT;
        }
    }
}
