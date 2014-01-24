package drzhark.mocreatures.client.gui.helpers;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.IMoCTameable;
import drzhark.mocreatures.network.packet.MoCPacketNameGUI;

@SideOnly(Side.CLIENT)
public class MoCGUIEntityNamer extends GuiScreen {
    protected String screenTitle;
    private final IMoCEntity NamedEntity;
    private int updateCounter;
    private static String allowedCharacters;
    private String NameToSet;
    protected int xSize;
    protected int ySize;
    private static TextureManager textureManager = MoCClientProxy.mc.getTextureManager();
    private static final ResourceLocation TEXTURE_MOCNAME = new ResourceLocation("mocreatures", MoCreatures.proxy.GUI_TEXTURE + "mocname.png");

    public MoCGUIEntityNamer(IMoCEntity mocanimal, String s)
    {
        xSize = 256;
        ySize = 181;
        screenTitle = "Choose your Pet's name:";
        NamedEntity = mocanimal;
        NameToSet = s;
    }

    @Override
    public void initGui()
    {
        buttonList.clear();
        Keyboard.enableRepeatEvents(true);
        buttonList.add(new GuiButton(0, (width / 2) - 100, (height / 4) + 120, "Done")); //1.5
    }

    static
    {
        allowedCharacters = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000";
    }

    public void updateName()
    {
        NamedEntity.setName(NameToSet);
        System.out.println("name = " + NameToSet);
        MoCreatures.packetPipeline.sendToServer(new MoCPacketNameGUI(((EntityLiving) NamedEntity).getEntityId(), NameToSet));
        mc.displayGuiScreen(null);
    }

    @Override
    protected void actionPerformed(GuiButton guibutton)
    {
        if (!guibutton.enabled) { return; }
        if ((guibutton.id == 0) && (this.NameToSet != null) && (!this.NameToSet.equals("")))
        {
            updateName();
        }
    }

    @Override
    public void drawScreen(int i, int j, float f)
    {
        drawDefaultBackground();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        textureManager.bindTexture(TEXTURE_MOCNAME);
        int l = (width - xSize) / 2;
        int i1 = (height - (ySize + 16)) / 2;
        drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);
        drawCenteredString(fontRendererObj, screenTitle, width / 2, 100, 0xffffff);
        drawCenteredString(fontRendererObj, NameToSet, width / 2, 120, 0xffffff);
        super.drawScreen(i, j, f);
    }

    @Override
    public void handleKeyboardInput()
    {
        if (Keyboard.getEventKeyState())
        {
            if (Keyboard.getEventKey() == 28) // Handle Enter Key
            {
                updateName();
            }
        }
        super.handleKeyboardInput();
    }

    @Override
    protected void keyTyped(char c, int i)
    {
        if ((i == 14) && (NameToSet.length() > 0))
        {
            NameToSet = NameToSet.substring(0, NameToSet.length() - 1);
        }
        if ((allowedCharacters.indexOf(c) < 0) || (NameToSet.length() >= 15))
        {
        }
        else
        {
            StringBuilder name = new StringBuilder(NameToSet);
            name.append(c);
            NameToSet = name.toString();
        }
    }

    @Override
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
        if (NamedEntity instanceof IMoCTameable)
        {
            IMoCTameable tamedEntity = (IMoCTameable)NamedEntity;
            tamedEntity.playTameEffect(true);
        }
    }

    @Override
    public void updateScreen()
    {
        updateCounter++;
    }
}