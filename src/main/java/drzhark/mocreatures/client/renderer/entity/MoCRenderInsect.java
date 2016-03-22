package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.entity.MoCEntityInsect;
import net.minecraft.client.model.ModelBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MoCRenderInsect<T extends MoCEntityInsect> extends MoCRenderMoC<T> {

    public MoCRenderInsect(ModelBase modelbase) {
        super(modelbase, 0.0F);

    }

    @Override
    protected void preRenderCallback(T entityinsect, float par2) {
        if (entityinsect.climbing()) {
            rotateAnimal(entityinsect);
        }

        stretch(entityinsect);
    }

    protected void rotateAnimal(T entityinsect) {
        GL11.glRotatef(90F, -1F, 0.0F, 0.0F);
    }

    protected void stretch(T entityinsect) {
        float sizeFactor = entityinsect.getSizeFactor();
        GL11.glScalef(sizeFactor, sizeFactor, sizeFactor);
    }
}
