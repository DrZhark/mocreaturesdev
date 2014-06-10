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
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageUpdatePetName;

@SideOnly(Side.CLIENT)
public class MoCGUIEntityNamer extends GuiScreen {
    protected String screenTitle;
    private final IMoCEntity NamedEntity;
    private int updateCounter;
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

    public void updateName()
    {
        NamedEntity.setName(NameToSet);
        MoCMessageHandler.INSTANCE.sendToServer(new MoCMessageUpdatePetName(((EntityLiving) NamedEntity).getEntityId(), NameToSet));
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
        if (!ChatAllowedCharacters.isAllowedCharacter(c) || (NameToSet.length() >= 15))
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