package drzhark.mocreatures.client.renderer.entity;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.client.model.MoCModelKitty;
import drzhark.mocreatures.entity.passive.MoCEntityKitty;

@SideOnly(Side.CLIENT)
public class MoCRenderKitty extends RenderBiped {

    public MoCModelKitty pussy1;
    private static TextureManager textureManager = MoCClientProxy.mc.func_110434_K();
    private static final ResourceLocation TEXTURE_DEFAULT = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "pussycata.png");
    private static final ResourceLocation TEXTURE_KITTY2 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "pussycatb.png");
    private static final ResourceLocation TEXTURE_KITTY3 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "pussycatc.png");
    private static final ResourceLocation TEXTURE_KITTY4 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "pussycatd.png");
    private static final ResourceLocation TEXTURE_KITTY5 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "pussycate.png");
    private static final ResourceLocation TEXTURE_KITTY6 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "pussycatf.png");
    private static final ResourceLocation TEXTURE_KITTY7 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "pussycatg.png");
    private static final ResourceLocation TEXTURE_KITTY8 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "pussycath.png");
    // emotes
    private static final ResourceLocation TEXTURE_EMOTE_DEFAULT = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "emoteicon1.png");
    private static final ResourceLocation TEXTURE_EMOTE_2 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "emoteicon2.png");
    private static final ResourceLocation TEXTURE_EMOTE_3 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "emoteicon3.png");
    private static final ResourceLocation TEXTURE_EMOTE_4 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "emoteicon4.png");
    private static final ResourceLocation TEXTURE_EMOTE_5 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "emoteicon5.png");
    private static final ResourceLocation TEXTURE_EMOTE_6 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "emoteicon6.png");
    private static final ResourceLocation TEXTURE_EMOTE_7 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "emoteicon7.png");
    private static final ResourceLocation TEXTURE_EMOTE_8 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "emoteicon8.png");
    private static final ResourceLocation TEXTURE_EMOTE_9 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "emoteicon9.png");
    private static final ResourceLocation TEXTURE_EMOTE_10 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "emoteicon10.png");
    private static final ResourceLocation TEXTURE_EMOTE_11 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "emoteicon11.png");
    private static final ResourceLocation TEXTURE_EMOTE_12 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "emoteicon12.png");
    private static final ResourceLocation TEXTURE_EMOTE_13 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "emoteicon13.png");
    private static final ResourceLocation TEXTURE_EMOTE_16 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "emoteicon16.png");
    private static final ResourceLocation TEXTURE_EMOTE_17 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "emoteicon17.png");
    private static final ResourceLocation TEXTURE_EMOTE_19 = new ResourceLocation("mocreatures", MoCreatures.proxy.MODEL_TEXTURE + "emoteicon19.png");

    protected ResourceLocation func_110775_a(Entity par1Entity) {
        return this.getTexture((MoCEntityKitty)par1Entity);
    }

    protected ResourceLocation getTexture(MoCEntityKitty kitty)
    {
        switch (kitty.getType())
        {
        case 2:
            return TEXTURE_KITTY2;
        case 3:
            return TEXTURE_KITTY3;
        case 4:
            return TEXTURE_KITTY4;
        case 5:
            return TEXTURE_KITTY5;
        case 6:
            return TEXTURE_KITTY6;
        case 7:
            return TEXTURE_KITTY7;
        case 8:
            return TEXTURE_KITTY8;

        default:
            return TEXTURE_DEFAULT;
        }
    }

    public ResourceLocation getEmoteIcon(MoCEntityKitty kitty)
    {
        switch (kitty.getKittyState())
        {
        case -1:
            return TEXTURE_EMOTE_2;

        case 3: // '\003'
            return TEXTURE_EMOTE_3;

        case 4: // '\004'
            return TEXTURE_EMOTE_4;

        case 5: // '\005'
            return TEXTURE_EMOTE_5;

        case 7: // '\007'
            return TEXTURE_EMOTE_7;

        case 8: // '\b'
            return TEXTURE_EMOTE_8;

        case 9: // '\t'
            return TEXTURE_EMOTE_9;

        case 10: // '\n'
            return TEXTURE_EMOTE_10;

        case 11: // '\013'
            return TEXTURE_EMOTE_11;

        case 12: // '\f'
            return TEXTURE_EMOTE_12;

        case 13: // '\r'
            return TEXTURE_EMOTE_13;

        case 16: // '\020'
            return TEXTURE_EMOTE_16;

        case 17: // '\021'
            return TEXTURE_EMOTE_17;

        case 18: // '\022'
            return TEXTURE_EMOTE_9;

        case 19: // '\023'
            return TEXTURE_EMOTE_19;

        case 20: // '\024'
            return TEXTURE_EMOTE_19;

        case 21: // '\025'
            return TEXTURE_EMOTE_10;

        case 0: // '\0'
        case 1: // '\001'
        case 2: // '\002'
        case 6: // '\006'
        case 14: // '\016'
        case 15: // '\017'
        default:
            return TEXTURE_EMOTE_DEFAULT;
        }
    }

    public MoCRenderKitty(MoCModelKitty modelkitty, float f)
    {
        super(modelkitty, f);
        pussy1 = modelkitty;
    }

    @Override
    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2, float f, float f1)
    {
        MoCEntityKitty entitykitty = (MoCEntityKitty) entityliving;
        super.doRenderLiving(entitykitty, d, d1, d2, f, f1);
        boolean flag = MoCreatures.proxy.getDisplayPetName() && !(entitykitty.getName()).isEmpty();
        boolean flag1 = MoCreatures.proxy.getDisplayPetHealth();
        boolean flag2 = MoCreatures.proxy.getDisplayPetIcons();
        if (entitykitty.renderName())
        {
            float f2 = 1.6F;
            float f3 = 0.01666667F * f2;
            float f4 = entityliving.getDistanceToEntity(renderManager.livingPlayer);
            String s = "";
            s = (new StringBuilder()).append(s).append(entitykitty.getName()).toString();
            if (f4 < 12F)
            {
                float f5 = 0.2F;
                if (pussy1.isSitting)
                {
                    f5 = 0.4F;
                }
                FontRenderer fontrenderer = getFontRendererFromRenderManager();
                GL11.glPushMatrix();
                GL11.glTranslatef((float) d + 0.0F, (float) d1 - f5, (float) d2);
                GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                GL11.glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(-f3, -f3, f3);
                GL11.glDisable(2896 /* GL_LIGHTING */);
                Tessellator tessellator = Tessellator.instance;
                byte byte0 = -48;
                if (flag2 && ((MoCEntityKitty) entityliving).getIsEmo())
                {
                    //if(MoCClientProxy.mc.isMultiplayerWorld() && (((MoCEntityKitty) entityliving).getEmoticon() != null))
                    //if (this.getEmoteIcon(((MoCEntityKitty) entityliving)) != null)
                    //{
                        //GL11.glBindTexture(3553 /* GL_TEXTURE_2D */, MoCClientProxy.mc.renderEngine.getTexture(((MoCEntityKitty) entityliving).getEmoticon()));
                    textureManager.func_110577_a(this.getEmoteIcon((MoCEntityKitty) entityliving));
                    //}
                    //else
                    //{
                      //  GL11.glBindTexture(3553 /* GL_TEXTURE_2D */, MoCClientProxy.mc.renderEngine.getTexture(((MoCEntityKitty) entityliving).getEmoticon()));
                    //}
                    int i = -83;
                    if (!flag)
                    {
                        i += 8;
                    }
                    if (!flag1)
                    {
                        i += 8;
                    }
                    int k = 32;
                    int l = (k / 2) * -1;
                    float f9 = 0.0F;
                    float f11 = 1.0F / k;
                    float f12 = 1.0F / k;
                    tessellator.startDrawingQuads();
                    tessellator.addVertexWithUV(l, i + k, f9, 0.0D, k * f12);
                    tessellator.addVertexWithUV(l + k, i + k, f9, k * f11, k * f12);
                    tessellator.addVertexWithUV(l + k, i, f9, k * f11, 0.0D);
                    tessellator.addVertexWithUV(l, i, f9, 0.0D, 0.0D);
                    tessellator.draw();
                }
                if (flag1)
                {
                    if (!flag)
                    {
                        byte0 += 8;
                    }
                    GL11.glDisable(3553 /* GL_TEXTURE_2D */);
                    tessellator.startDrawingQuads();
                    float f6 = entitykitty.func_110143_aJ();
                    float f7 = entitykitty.getMaxHealth();
                    float f8 = f6 / f7;
                    float f10 = 30F * f8;
                    tessellator.setColorRGBA_F(0.7F, 0.0F, 0.0F, 1.0F);
                    tessellator.addVertex(-15F + f10, -8 + byte0, 0.0D);
                    tessellator.addVertex(-15F + f10, -4 + byte0, 0.0D);
                    tessellator.addVertex(15D, -4 + byte0, 0.0D);
                    tessellator.addVertex(15D, -8 + byte0, 0.0D);
                    tessellator.setColorRGBA_F(0.0F, 0.7F, 0.0F, 1.0F);
                    tessellator.addVertex(-15D, -8 + byte0, 0.0D);
                    tessellator.addVertex(-15D, -4 + byte0, 0.0D);
                    tessellator.addVertex(f10 - 15F, -4 + byte0, 0.0D);
                    tessellator.addVertex(f10 - 15F, -8 + byte0, 0.0D);
                    tessellator.draw();
                    GL11.glEnable(3553 /* GL_TEXTURE_2D */);
                }
                if (flag)
                {
                    GL11.glDepthMask(false);
                    GL11.glDisable(2929 /* GL_DEPTH_TEST */);
                    GL11.glEnable(3042 /* GL_BLEND */);
                    GL11.glBlendFunc(770, 771);
                    GL11.glDisable(3553 /* GL_TEXTURE_2D */);
                    tessellator.startDrawingQuads();
                    int j = fontrenderer.getStringWidth(s) / 2;
                    tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
                    tessellator.addVertex(-j - 1, -1 + byte0, 0.0D);
                    tessellator.addVertex(-j - 1, 8 + byte0, 0.0D);
                    tessellator.addVertex(j + 1, 8 + byte0, 0.0D);
                    tessellator.addVertex(j + 1, -1 + byte0, 0.0D);
                    tessellator.draw();
                    GL11.glEnable(3553 /* GL_TEXTURE_2D */);
                    fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, byte0, 0x20ffffff);
                    GL11.glEnable(2929 /* GL_DEPTH_TEST */);
                    GL11.glDepthMask(true);
                    fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, byte0, -1);
                    GL11.glDisable(3042 /* GL_BLEND */);
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                }
                GL11.glEnable(2896 /* GL_LIGHTING */);
                GL11.glPopMatrix();
            }
        }
    }

    public void doRenderLiving2(EntityLiving entityliving, double d, double d1, double d2, float f, float f1)
    {
        MoCEntityKitty entitykitty = (MoCEntityKitty) entityliving;
        super.doRenderLiving(entitykitty, d, d1, d2, f, f1);
    }

    @Override
    protected float handleRotationFloat(EntityLivingBase entityliving, float f)
    {
        MoCEntityKitty entitykitty = (MoCEntityKitty) entityliving;
        if (!entitykitty.getIsAdult())
        {
            stretch(entitykitty);
        }
        return entityliving.ticksExisted + f;
    }

    protected void onMaBack(EntityLivingBase entityliving)
    {
        GL11.glRotatef(90F, 0.0F, 0.0F, -1F);

        if (!entityliving.worldObj.isRemote && (entityliving.ridingEntity != null))
        {
            GL11.glTranslatef(-1.5F, 0.2F, -0.2F);
        }
        else
        {
            GL11.glTranslatef(0.1F, 0.2F, -0.2F);
        }

    }

    protected void onTheSide(EntityLivingBase entityliving)
    {
        GL11.glRotatef(90F, 0.0F, 0.0F, -1F);
        GL11.glTranslatef(0.2F, 0.0F, -0.2F);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entityliving, float f)
    {
        MoCEntityKitty entitykitty = (MoCEntityKitty) entityliving;
        pussy1.isSitting = entitykitty.getIsSitting();
        pussy1.isSwinging = entitykitty.getIsSwinging();
        pussy1.swingProgress = entitykitty.swingProgress;
        pussy1.kittystate = entitykitty.getKittyState();
        if (entitykitty.getKittyState() == 20)
        {
            onTheSide(entityliving);
        }
        if (entitykitty.climbingTree())
        {
            rotateAnimal(entityliving);
        }
        if (entitykitty.upsideDown())
        {
            upsideDown(entityliving);
        }
        if (entitykitty.onMaBack())
        {
            onMaBack(entityliving);
        }
    }

    protected void rotateAnimal(EntityLivingBase entityliving)
    {
        if (!entityliving.onGround)
        {
            GL11.glRotatef(90F, -1F, 0.0F, 0.0F);
        }
    }

    protected void stretch(MoCEntityKitty entitykitty)
    {
        GL11.glScalef(entitykitty.getEdad() * 0.01F, entitykitty.getEdad() * 0.01F, entitykitty.getEdad() * 0.01F);
    }

    protected void upsideDown(EntityLivingBase entityliving)
    {
        GL11.glRotatef(180F, 0.0F, 0.0F, -1F);
        GL11.glTranslatef(-0.35F, 0F, -0.55F);
    }
}
