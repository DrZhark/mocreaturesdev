package drzhark.mocreatures.dimension;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.IRenderHandler;

public class MoCSkyRenderer extends IRenderHandler
{

	@Override
	@SideOnly(Side.CLIENT)
	public void render(float partialTicks, WorldClient world, Minecraft mc) 
	{
		//System.out.println("mySkyrenderer");
//		{
//            GL11.glDisable(GL11.GL_TEXTURE_2D);
//            Vec3 var2 = world.getSkyColor(mc.renderViewEntity, partialTicks);
//            float var3 = (float)var2.xCoord;
//            float var4 = (float)var2.yCoord;
//            float var5 = (float)var2.zCoord;
//            float var8;
//
//            if (mc.gameSettings.anaglyph)
//            {
//                float var6 = (var3 * 30.0F + var4 * 59.0F + var5 * 11.0F) / 100.0F;
//                float var7 = (var3 * 30.0F + var4 * 70.0F) / 100.0F;
//                var8 = (var3 * 30.0F + var5 * 70.0F) / 100.0F;
//                var3 = var6;
//                var4 = var7;
//                var5 = var8;
//            }
//
//            GL11.glColor3f(var3, var4, var5);
//            Tessellator var23 = Tessellator.instance;
//            GL11.glDepthMask(false);
//            GL11.glEnable(GL11.GL_FOG);
//            GL11.glColor3f(var3, var4, var5);
//            GL11.glCallList(glSkyList);
//            GL11.glDisable(GL11.GL_FOG);
//            GL11.glDisable(GL11.GL_ALPHA_TEST);
//            GL11.glEnable(GL11.GL_BLEND);
//            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//            RenderHelper.disableStandardItemLighting();
//            float[] var24 = world.provider.calcSunriseSunsetColors(world.getCelestialAngle(partialTicks), partialTicks);
//            float var9;
//            float var10;
//            float var11;
//            float var12;
//
//            if (var24 != null)
//            {
//                GL11.glDisable(GL11.GL_TEXTURE_2D);
//                GL11.glShadeModel(GL11.GL_SMOOTH);
//                GL11.glPushMatrix();
//                GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
//                GL11.glRotatef(MathHelper.sin(world.getCelestialAngleRadians(partialTicks)) < 0.0F ? 180.0F : 0.0F, 0.0F, 0.0F, 1.0F);
//                GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
//                var8 = var24[0];
//                var9 = var24[1];
//                var10 = var24[2];
//                float var13;
//
//                if (mc.gameSettings.anaglyph)
//                {
//                    var11 = (var8 * 30.0F + var9 * 59.0F + var10 * 11.0F) / 100.0F;
//                    var12 = (var8 * 30.0F + var9 * 70.0F) / 100.0F;
//                    var13 = (var8 * 30.0F + var10 * 70.0F) / 100.0F;
//                    var8 = var11;
//                    var9 = var12;
//                    var10 = var13;
//                }
//
//                var23.startDrawing(6);
//                var23.setColorRGBA_F(var8, var9, var10, var24[3]);
//                var23.addVertex(0.0D, 100.0D, 0.0D);
//                byte var26 = 16;
//                var23.setColorRGBA_F(var24[0], var24[1], var24[2], 0.0F);
//
//                for (int var27 = 0; var27 <= var26; ++var27)
//                {
//                    var13 = (float)var27 * (float)Math.PI * 2.0F / (float)var26;
//                    float var14 = MathHelper.sin(var13);
//                    float var15 = MathHelper.cos(var13);
//                    var23.addVertex((double)(var14 * 120.0F), (double)(var15 * 120.0F), (double)(-var15 * 40.0F * var24[3]));
//                }
//
//                var23.draw();
//                GL11.glPopMatrix();
//                GL11.glShadeModel(GL11.GL_FLAT);
//            }
//
//            GL11.glEnable(GL11.GL_TEXTURE_2D);
//            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
//            GL11.glPushMatrix();
//            var8 = 1.0F - world.getRainStrength(partialTicks);
//            var9 = 0.0F;
//            var10 = 0.0F;
//            var11 = 0.0F;
//            GL11.glColor4f(1.0F, 1.0F, 1.0F, var8);
//            GL11.glTranslatef(var9, var10, var11);
//            GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
//            GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
//            var12 = 30.0F;
//            GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.renderEngine.getTexture("/terrain/sun.png"));
//            var23.startDrawingQuads();
//            var23.addVertexWithUV((double)(-var12), 100.0D, (double)(-var12), 0.0D, 0.0D);
//            var23.addVertexWithUV((double)var12, 100.0D, (double)(-var12), 1.0D, 0.0D);
//            var23.addVertexWithUV((double)var12, 100.0D, (double)var12, 1.0D, 1.0D);
//            var23.addVertexWithUV((double)(-var12), 100.0D, (double)var12, 0.0D, 1.0D);
//            var23.draw();
//            var12 = 20.0F;
//            GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.renderEngine.getTexture("/terrain/moon_phases.png"));
//            int var28 = world.getMoonPhase(partialTicks);
//            int var30 = var28 % 4;
//            int var29 = var28 / 4 % 2;
//            float var16 = (float)(var30 + 0) / 4.0F;
//            float var17 = (float)(var29 + 0) / 2.0F;
//            float var18 = (float)(var30 + 1) / 4.0F;
//            float var19 = (float)(var29 + 1) / 2.0F;
//            var23.startDrawingQuads();
//            var23.addVertexWithUV((double)(-var12), -100.0D, (double)var12, (double)var18, (double)var19);
//            var23.addVertexWithUV((double)var12, -100.0D, (double)var12, (double)var16, (double)var19);
//            var23.addVertexWithUV((double)var12, -100.0D, (double)(-var12), (double)var16, (double)var17);
//            var23.addVertexWithUV((double)(-var12), -100.0D, (double)(-var12), (double)var18, (double)var17);
//            var23.draw();
//            GL11.glDisable(GL11.GL_TEXTURE_2D);
//            float var20 = world.getStarBrightness(partialTicks) * var8;
//
//            if (var20 > 0.0F)
//            {
//                GL11.glColor4f(var20, var20, var20, var20);
//                GL11.glCallList(this.starGLCallList);
//            }
//
//            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//            GL11.glDisable(GL11.GL_BLEND);
//            GL11.glEnable(GL11.GL_ALPHA_TEST);
//            GL11.glEnable(GL11.GL_FOG);
//            GL11.glPopMatrix();
//            GL11.glDisable(GL11.GL_TEXTURE_2D);
//            GL11.glColor3f(0.0F, 0.0F, 0.0F);
//            double var25 = mc.thePlayer.getPosition(par1).yCoord - world.getHorizon();
//
//            if (var25 < 0.0D)
//            {
//                GL11.glPushMatrix();
//                GL11.glTranslatef(0.0F, 12.0F, 0.0F);
//                GL11.glCallList(this.glSkyList2);
//                GL11.glPopMatrix();
//                var10 = 1.0F;
//                var11 = -((float)(var25 + 65.0D));
//                var12 = -var10;
//                var23.startDrawingQuads();
//                var23.setColorRGBA_I(0, 255);
//                var23.addVertex((double)(-var10), (double)var11, (double)var10);
//                var23.addVertex((double)var10, (double)var11, (double)var10);
//                var23.addVertex((double)var10, (double)var12, (double)var10);
//                var23.addVertex((double)(-var10), (double)var12, (double)var10);
//                var23.addVertex((double)(-var10), (double)var12, (double)(-var10));
//                var23.addVertex((double)var10, (double)var12, (double)(-var10));
//                var23.addVertex((double)var10, (double)var11, (double)(-var10));
//                var23.addVertex((double)(-var10), (double)var11, (double)(-var10));
//                var23.addVertex((double)var10, (double)var12, (double)(-var10));
//                var23.addVertex((double)var10, (double)var12, (double)var10);
//                var23.addVertex((double)var10, (double)var11, (double)var10);
//                var23.addVertex((double)var10, (double)var11, (double)(-var10));
//                var23.addVertex((double)(-var10), (double)var11, (double)(-var10));
//                var23.addVertex((double)(-var10), (double)var11, (double)var10);
//                var23.addVertex((double)(-var10), (double)var12, (double)var10);
//                var23.addVertex((double)(-var10), (double)var12, (double)(-var10));
//                var23.addVertex((double)(-var10), (double)var12, (double)(-var10));
//                var23.addVertex((double)(-var10), (double)var12, (double)var10);
//                var23.addVertex((double)var10, (double)var12, (double)var10);
//                var23.addVertex((double)var10, (double)var12, (double)(-var10));
//                var23.draw();
//            }
//
//            if (world.provider.isSkyColored())
//            {
//                GL11.glColor3f(var3 * 0.2F + 0.04F, var4 * 0.2F + 0.04F, var5 * 0.6F + 0.1F);
//            }
//            else
//            {
//                GL11.glColor3f(var3, var4, var5);
//            }
//
//            GL11.glPushMatrix();
//            GL11.glTranslatef(0.0F, -((float)(var25 - 16.0D)), 0.0F);
//            GL11.glCallList(this.glSkyList2);
//            GL11.glPopMatrix();
//            GL11.glEnable(GL11.GL_TEXTURE_2D);
//            GL11.glDepthMask(true);
		

	}

	/**
     * Renders the sky with the partial tick time. Args: partialTickTime
     */
//    public void renderSky(float par1)
//    {
//        IRenderHandler skyProvider = null;
//        if ((skyProvider = this.mc.theWorld.provider.getSkyRenderer()) != null)
//        {
//            skyProvider.render(par1, this.theWorld, mc);
//            return;
//        }
//        if (this.mc.theWorld.provider.dimensionId == 1)
//        {
//            GL11.glDisable(GL11.GL_FOG);
//            GL11.glDisable(GL11.GL_ALPHA_TEST);
//            GL11.glEnable(GL11.GL_BLEND);
//            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//            RenderHelper.disableStandardItemLighting();
//            GL11.glDepthMask(false);
//            this.renderEngine.bindTexture(this.renderEngine.getTexture("/misc/tunnel.png"));
//            Tessellator var21 = Tessellator.instance;
//
//            for (int var22 = 0; var22 < 6; ++var22)
//            {
//                GL11.glPushMatrix();
//
//                if (var22 == 1)
//                {
//                    GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
//                }
//
//                if (var22 == 2)
//                {
//                    GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
//                }
//
//                if (var22 == 3)
//                {
//                    GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
//                }
//
//                if (var22 == 4)
//                {
//                    GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
//                }
//
//                if (var22 == 5)
//                {
//                    GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
//                }
//
//                var21.startDrawingQuads();
//                var21.setColorOpaque_I(2631720);
//                var21.addVertexWithUV(-100.0D, -100.0D, -100.0D, 0.0D, 0.0D);
//                var21.addVertexWithUV(-100.0D, -100.0D, 100.0D, 0.0D, 16.0D);
//                var21.addVertexWithUV(100.0D, -100.0D, 100.0D, 16.0D, 16.0D);
//                var21.addVertexWithUV(100.0D, -100.0D, -100.0D, 16.0D, 0.0D);
//                var21.draw();
//                GL11.glPopMatrix();
//            }
//
//            GL11.glDepthMask(true);
//            GL11.glEnable(GL11.GL_TEXTURE_2D);
//            GL11.glEnable(GL11.GL_ALPHA_TEST);
//        }
//        else if (this.mc.theWorld.provider.isSurfaceWorld())
//        {
//            GL11.glDisable(GL11.GL_TEXTURE_2D);
//            Vec3 var2 = this.theWorld.getSkyColor(this.mc.renderViewEntity, par1);
//            float var3 = (float)var2.xCoord;
//            float var4 = (float)var2.yCoord;
//            float var5 = (float)var2.zCoord;
//            float var8;
//
//            if (this.mc.gameSettings.anaglyph)
//            {
//                float var6 = (var3 * 30.0F + var4 * 59.0F + var5 * 11.0F) / 100.0F;
//                float var7 = (var3 * 30.0F + var4 * 70.0F) / 100.0F;
//                var8 = (var3 * 30.0F + var5 * 70.0F) / 100.0F;
//                var3 = var6;
//                var4 = var7;
//                var5 = var8;
//            }
//
//            GL11.glColor3f(var3, var4, var5);
//            Tessellator var23 = Tessellator.instance;
//            GL11.glDepthMask(false);
//            GL11.glEnable(GL11.GL_FOG);
//            GL11.glColor3f(var3, var4, var5);
//            GL11.glCallList(this.glSkyList);
//            GL11.glDisable(GL11.GL_FOG);
//            GL11.glDisable(GL11.GL_ALPHA_TEST);
//            GL11.glEnable(GL11.GL_BLEND);
//            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//            RenderHelper.disableStandardItemLighting();
//            float[] var24 = this.theWorld.provider.calcSunriseSunsetColors(this.theWorld.getCelestialAngle(par1), par1);
//            float var9;
//            float var10;
//            float var11;
//            float var12;
//
//            if (var24 != null)
//            {
//                GL11.glDisable(GL11.GL_TEXTURE_2D);
//                GL11.glShadeModel(GL11.GL_SMOOTH);
//                GL11.glPushMatrix();
//                GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
//                GL11.glRotatef(MathHelper.sin(this.theWorld.getCelestialAngleRadians(par1)) < 0.0F ? 180.0F : 0.0F, 0.0F, 0.0F, 1.0F);
//                GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
//                var8 = var24[0];
//                var9 = var24[1];
//                var10 = var24[2];
//                float var13;
//
//                if (this.mc.gameSettings.anaglyph)
//                {
//                    var11 = (var8 * 30.0F + var9 * 59.0F + var10 * 11.0F) / 100.0F;
//                    var12 = (var8 * 30.0F + var9 * 70.0F) / 100.0F;
//                    var13 = (var8 * 30.0F + var10 * 70.0F) / 100.0F;
//                    var8 = var11;
//                    var9 = var12;
//                    var10 = var13;
//                }
//
//                var23.startDrawing(6);
//                var23.setColorRGBA_F(var8, var9, var10, var24[3]);
//                var23.addVertex(0.0D, 100.0D, 0.0D);
//                byte var26 = 16;
//                var23.setColorRGBA_F(var24[0], var24[1], var24[2], 0.0F);
//
//                for (int var27 = 0; var27 <= var26; ++var27)
//                {
//                    var13 = (float)var27 * (float)Math.PI * 2.0F / (float)var26;
//                    float var14 = MathHelper.sin(var13);
//                    float var15 = MathHelper.cos(var13);
//                    var23.addVertex((double)(var14 * 120.0F), (double)(var15 * 120.0F), (double)(-var15 * 40.0F * var24[3]));
//                }
//
//                var23.draw();
//                GL11.glPopMatrix();
//                GL11.glShadeModel(GL11.GL_FLAT);
//            }
//
//            GL11.glEnable(GL11.GL_TEXTURE_2D);
//            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
//            GL11.glPushMatrix();
//            var8 = 1.0F - this.theWorld.getRainStrength(par1);
//            var9 = 0.0F;
//            var10 = 0.0F;
//            var11 = 0.0F;
//            GL11.glColor4f(1.0F, 1.0F, 1.0F, var8);
//            GL11.glTranslatef(var9, var10, var11);
//            GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
//            GL11.glRotatef(this.theWorld.getCelestialAngle(par1) * 360.0F, 1.0F, 0.0F, 0.0F);
//            var12 = 30.0F;
//            GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.renderEngine.getTexture("/terrain/sun.png"));
//            var23.startDrawingQuads();
//            var23.addVertexWithUV((double)(-var12), 100.0D, (double)(-var12), 0.0D, 0.0D);
//            var23.addVertexWithUV((double)var12, 100.0D, (double)(-var12), 1.0D, 0.0D);
//            var23.addVertexWithUV((double)var12, 100.0D, (double)var12, 1.0D, 1.0D);
//            var23.addVertexWithUV((double)(-var12), 100.0D, (double)var12, 0.0D, 1.0D);
//            var23.draw();
//            var12 = 20.0F;
//            GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.renderEngine.getTexture("/terrain/moon_phases.png"));
//            int var28 = this.theWorld.getMoonPhase(par1);
//            int var30 = var28 % 4;
//            int var29 = var28 / 4 % 2;
//            float var16 = (float)(var30 + 0) / 4.0F;
//            float var17 = (float)(var29 + 0) / 2.0F;
//            float var18 = (float)(var30 + 1) / 4.0F;
//            float var19 = (float)(var29 + 1) / 2.0F;
//            var23.startDrawingQuads();
//            var23.addVertexWithUV((double)(-var12), -100.0D, (double)var12, (double)var18, (double)var19);
//            var23.addVertexWithUV((double)var12, -100.0D, (double)var12, (double)var16, (double)var19);
//            var23.addVertexWithUV((double)var12, -100.0D, (double)(-var12), (double)var16, (double)var17);
//            var23.addVertexWithUV((double)(-var12), -100.0D, (double)(-var12), (double)var18, (double)var17);
//            var23.draw();
//            GL11.glDisable(GL11.GL_TEXTURE_2D);
//            float var20 = this.theWorld.getStarBrightness(par1) * var8;
//
//            if (var20 > 0.0F)
//            {
//                GL11.glColor4f(var20, var20, var20, var20);
//                GL11.glCallList(this.starGLCallList);
//            }
//
//            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//            GL11.glDisable(GL11.GL_BLEND);
//            GL11.glEnable(GL11.GL_ALPHA_TEST);
//            GL11.glEnable(GL11.GL_FOG);
//            GL11.glPopMatrix();
//            GL11.glDisable(GL11.GL_TEXTURE_2D);
//            GL11.glColor3f(0.0F, 0.0F, 0.0F);
//            double var25 = this.mc.thePlayer.getPosition(par1).yCoord - this.theWorld.getHorizon();
//
//            if (var25 < 0.0D)
//            {
//                GL11.glPushMatrix();
//                GL11.glTranslatef(0.0F, 12.0F, 0.0F);
//                GL11.glCallList(this.glSkyList2);
//                GL11.glPopMatrix();
//                var10 = 1.0F;
//                var11 = -((float)(var25 + 65.0D));
//                var12 = -var10;
//                var23.startDrawingQuads();
//                var23.setColorRGBA_I(0, 255);
//                var23.addVertex((double)(-var10), (double)var11, (double)var10);
//                var23.addVertex((double)var10, (double)var11, (double)var10);
//                var23.addVertex((double)var10, (double)var12, (double)var10);
//                var23.addVertex((double)(-var10), (double)var12, (double)var10);
//                var23.addVertex((double)(-var10), (double)var12, (double)(-var10));
//                var23.addVertex((double)var10, (double)var12, (double)(-var10));
//                var23.addVertex((double)var10, (double)var11, (double)(-var10));
//                var23.addVertex((double)(-var10), (double)var11, (double)(-var10));
//                var23.addVertex((double)var10, (double)var12, (double)(-var10));
//                var23.addVertex((double)var10, (double)var12, (double)var10);
//                var23.addVertex((double)var10, (double)var11, (double)var10);
//                var23.addVertex((double)var10, (double)var11, (double)(-var10));
//                var23.addVertex((double)(-var10), (double)var11, (double)(-var10));
//                var23.addVertex((double)(-var10), (double)var11, (double)var10);
//                var23.addVertex((double)(-var10), (double)var12, (double)var10);
//                var23.addVertex((double)(-var10), (double)var12, (double)(-var10));
//                var23.addVertex((double)(-var10), (double)var12, (double)(-var10));
//                var23.addVertex((double)(-var10), (double)var12, (double)var10);
//                var23.addVertex((double)var10, (double)var12, (double)var10);
//                var23.addVertex((double)var10, (double)var12, (double)(-var10));
//                var23.draw();
//            }
//
//            if (this.theWorld.provider.isSkyColored())
//            {
//                GL11.glColor3f(var3 * 0.2F + 0.04F, var4 * 0.2F + 0.04F, var5 * 0.6F + 0.1F);
//            }
//            else
//            {
//                GL11.glColor3f(var3, var4, var5);
//            }
//
//            GL11.glPushMatrix();
//            GL11.glTranslatef(0.0F, -((float)(var25 - 16.0D)), 0.0F);
//            GL11.glCallList(this.glSkyList2);
//            GL11.glPopMatrix();
//            GL11.glEnable(GL11.GL_TEXTURE_2D);
//            GL11.glDepthMask(true);
//        }
//    }

//    public void renderClouds(float par1)
//    {
//        IRenderHandler renderer = null;
//        if ((renderer = theWorld.provider.getCloudRenderer()) != null)
//        {
//            renderer.render(par1, theWorld, mc);
//            return;
//        }
//
//        if (this.mc.theWorld.provider.isSurfaceWorld())
//        {
//            if (this.mc.gameSettings.fancyGraphics)
//            {
//                this.renderCloudsFancy(par1);
//            }
//            else
//            {
//                GL11.glDisable(GL11.GL_CULL_FACE);
//                float var2 = (float)(this.mc.renderViewEntity.lastTickPosY + (this.mc.renderViewEntity.posY - this.mc.renderViewEntity.lastTickPosY) * (double)par1);
//                byte var3 = 32;
//                int var4 = 256 / var3;
//                Tessellator var5 = Tessellator.instance;
//                GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.renderEngine.getTexture("/environment/clouds.png"));
//                GL11.glEnable(GL11.GL_BLEND);
//                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//                Vec3 var6 = this.theWorld.drawClouds(par1);
//                float var7 = (float)var6.xCoord;
//                float var8 = (float)var6.yCoord;
//                float var9 = (float)var6.zCoord;
//                float var10;
//
//                if (this.mc.gameSettings.anaglyph)
//                {
//                    var10 = (var7 * 30.0F + var8 * 59.0F + var9 * 11.0F) / 100.0F;
//                    float var11 = (var7 * 30.0F + var8 * 70.0F) / 100.0F;
//                    float var12 = (var7 * 30.0F + var9 * 70.0F) / 100.0F;
//                    var7 = var10;
//                    var8 = var11;
//                    var9 = var12;
//                }
//
//                var10 = 4.8828125E-4F;
//                double var24 = (double)((float)this.cloudTickCounter + par1);
//                double var13 = this.mc.renderViewEntity.prevPosX + (this.mc.renderViewEntity.posX - this.mc.renderViewEntity.prevPosX) * (double)par1 + var24 * 0.029999999329447746D;
//                double var15 = this.mc.renderViewEntity.prevPosZ + (this.mc.renderViewEntity.posZ - this.mc.renderViewEntity.prevPosZ) * (double)par1;
//                int var17 = MathHelper.floor_double(var13 / 2048.0D);
//                int var18 = MathHelper.floor_double(var15 / 2048.0D);
//                var13 -= (double)(var17 * 2048);
//                var15 -= (double)(var18 * 2048);
//                float var19 = this.theWorld.provider.getCloudHeight() - var2 + 0.33F;
//                float var20 = (float)(var13 * (double)var10);
//                float var21 = (float)(var15 * (double)var10);
//                var5.startDrawingQuads();
//                var5.setColorRGBA_F(var7, var8, var9, 0.8F);
//
//                for (int var22 = -var3 * var4; var22 < var3 * var4; var22 += var3)
//                {
//                    for (int var23 = -var3 * var4; var23 < var3 * var4; var23 += var3)
//                    {
//                        var5.addVertexWithUV((double)(var22 + 0), (double)var19, (double)(var23 + var3), (double)((float)(var22 + 0) * var10 + var20), (double)((float)(var23 + var3) * var10 + var21));
//                        var5.addVertexWithUV((double)(var22 + var3), (double)var19, (double)(var23 + var3), (double)((float)(var22 + var3) * var10 + var20), (double)((float)(var23 + var3) * var10 + var21));
//                        var5.addVertexWithUV((double)(var22 + var3), (double)var19, (double)(var23 + 0), (double)((float)(var22 + var3) * var10 + var20), (double)((float)(var23 + 0) * var10 + var21));
//                        var5.addVertexWithUV((double)(var22 + 0), (double)var19, (double)(var23 + 0), (double)((float)(var22 + 0) * var10 + var20), (double)((float)(var23 + 0) * var10 + var21));
//                    }
//                }
//
//                var5.draw();
//                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//                GL11.glDisable(GL11.GL_BLEND);
//                GL11.glEnable(GL11.GL_CULL_FACE);
//            }
//        }
//    }
}




