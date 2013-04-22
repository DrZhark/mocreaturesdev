package drzhark.mocreatures.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ChatAllowedCharacters;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.network.MoCClientPacketHandler;
import drzhark.mocreatures.entity.MoCIMoCreature;

@SideOnly(Side.CLIENT)
public class MoCGUIEntityNamer extends GuiScreen {
    protected String screenTitle;
    private final MoCIMoCreature NamedEntity;
    private int updateCounter;
    private static String allowedCharacters;
    private String NameToSet;
    protected int xSize;
    protected int ySize;

    public MoCGUIEntityNamer(MoCIMoCreature mocanimal, String s)
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
        buttonList.clear(); //1.5
        Keyboard.enableRepeatEvents(true);
        buttonList.add(new GuiButton(0, (width / 2) - 100, (height / 4) + 120, "Done")); //1.5
    }

    static
    {
        allowedCharacters = ChatAllowedCharacters.allowedCharacters;
    }

    public void updateName()
    {
        //System.out.println("updating name of entity " + NamedEntity + "with " + NameToSet);
        NamedEntity.setName(NameToSet);
        MoCClientPacketHandler.sendNameInfo(((EntityLiving) NamedEntity).entityId, NameToSet);
        //MoCServerPacketHandler.sendNameInfo(((EntityLiving)NamedEntity).entityId, NameToSet, ((EntityLiving)NamedEntity).worldObj.provider.dimensionId);

        /*        if(!mc.theWorld.isRemote)
        {
            
        }
        else
        {
            //MoCServerPacketHandler.sendNameInfo(((EntityLiving)NamedEntity).entityId, NameToSet);
        }
        */mc.displayGuiScreen(null);
    }

    @Override
    protected void actionPerformed(GuiButton guibutton)
    {
        if (!guibutton.enabled) { return; }
        if (guibutton.id == 0)
        {
            updateName();
        }
    }

    @Override
    public void drawScreen(int i, int j, float f)
    {
        drawDefaultBackground();
        //int k = mc.renderEngine.getTexture("/mocreatures/mocname.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        //mc.renderEngine.bindTexture(k);
        this.mc.renderEngine.bindTexture(MoCreatures.proxy.GUI_TEXTURE + "mocname.png");
        int l = (width - xSize) / 2;
        int i1 = (height - (ySize + 16)) / 2;
        drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);
        drawCenteredString(fontRenderer, screenTitle, width / 2, 100, 0xffffff);
        drawCenteredString(fontRenderer, NameToSet, width / 2, 120, 0xffffff);
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
    }

    @Override
    public void updateScreen()
    {
        updateCounter++;
    }
}
