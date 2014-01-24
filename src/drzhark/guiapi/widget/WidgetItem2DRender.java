package drzhark.guiapi.widget;

import java.lang.reflect.Field;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.Widget;
import drzhark.guiapi.GuiWidgetScreen;
import drzhark.guiapi.ModSettings;

/**
 * This is a widget designed to render Minecraft objects. It has a MINIMUM size
 * of 16X16 by default. It also (Again, by default) renders a background and
 * border as per the theme for progressbars. This can be changed via setTheme,
 * see mod_GuiApiTWLExamples.SetUpColouringWindow comments for details.
 * 
 * @author ShaRose
 * 
 */
public class WidgetItem2DRender extends Widget {

    private static Field isDrawingField;

    private static RenderItem itemRenderer = new RenderItem();

    static
    {
        try
        {
            WidgetItem2DRender.isDrawingField = Tessellator.class.getDeclaredField("z");
            WidgetItem2DRender.isDrawingField.setAccessible(true);
        }
        catch(Throwable e)
        {
            try
            {
                WidgetItem2DRender.isDrawingField = Tessellator.class.getDeclaredField("isDrawing");
                WidgetItem2DRender.isDrawingField.setAccessible(true);
            }
            catch(Throwable e2)
            {
                System.out.println("GuiAPI Warning: Unable to get Tessellator.isDrawing field! There will be a chance of crashes if you attempt to render a mod item!");
            }

        }

    }

    private ItemStack renderStack;

    private int scaleType = 0;

    /**
     * This sets up the Widget to render no object (Air, specifically).
     */
    public WidgetItem2DRender() {
        this(0);
    }

    /**
     * This makes the widget render the Item that is in the slot dictated by
     * renderID. Note, if that ID slot is empty it will render as if you pass 0,
     * and that the damage will be 0.
     * 
     * @param renderID
     */
    public WidgetItem2DRender(int renderID) {
        this(new ItemStack(Item.getItemById(renderID), 0, 0));
    }

    /**
     * This makes the widget render the ItemStack passed.
     * 
     * @param renderStack
     */
    public WidgetItem2DRender(ItemStack renderStack) {
        setMinSize(16, 16);
        setTheme("/progressbar");
        //setRenderStack(renderStack);
    }

    /**
     * This gets the current ID this Widget is supposed to render.
     * 
     * @return The current ID to render.
     */
    public int getRenderID() {
        return renderStack == null ? 0 : Item.getIdFromItem(renderStack.getItem());
    }

    /**
     * This gets the ItemStack this Widget is supposed to render.
     * 
     * @return The current ID to render.
     */
    public ItemStack getRenderStack() {
        return renderStack;
    }

    /**
     * This returns an integer that specifies what kind of Scale type it is.
     * 
     * @return The scale type.
     */
    public int getScaleType() {
        return scaleType;
    }

    private boolean isDrawing(Tessellator tesselator)
    {
        if(WidgetItem2DRender.isDrawingField == null)
        {
            return false;
        }
        try
        {
            WidgetItem2DRender.isDrawingField.getBoolean(tesselator);
        }
        catch(Throwable e)
        {

        }
        return false;
    }

    @Override
    protected void paintWidget(GUI gui) {

        Minecraft minecraft = ModSettings.getMcinst();

        int x = getX();
        int y = getY();
        float scalex = 1f;
        float scaley = 1f;

        int maxWidth = getInnerWidth() - 4;
        int maxHeight = getInnerHeight() - 4;

        int scale = getScaleType();

        if ((scale == -1) && ((maxWidth < 16) || (maxHeight < 16))) {
            scale = 0;
        }

        switch (scale) {
        case 0: {
            // largest square
            int size = 0;
            if (maxWidth > maxHeight) {
                size = maxHeight;
            } else {
                size = maxWidth;
            }

            x += ((maxWidth - size) / 2);
            y += ((maxHeight - size) / 2);

            scalex = size / 16f;
            scaley = scalex;
            x /= scalex;
            y /= scaley;
            break;
        }

        case -1: {
            // default size in middle
            int size = maxWidth - 16;
            x += size / 2;

            size = maxHeight - 16;
            y += size / 2;
            break;
        }

        case 1: {
            // fill / stretch
            scalex = maxWidth / 16f;
            scaley = maxHeight / 16f;
            x /= scalex;
            y /= scaley;
            break;
        }
        default:
            throw new IndexOutOfBoundsException(
                    "Scale Type is out of bounds! This should never happen!");
        }

        x += 2;
        y += 1;

        if ((minecraft == null) || (getRenderStack() == null)
                || (getRenderStack().getItem() == null)) {
            // draw black or something? Maybe NULL?
            return;
        }

        GuiWidgetScreen screen = GuiWidgetScreen.getInstance();
        screen.renderer.pauseRendering();

        screen.renderer.setClipRect();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(32826 /* GL_RESCALE_NORMAL_EXT *//* GL_RESCALE_NORMAL_EXT */);
        RenderHelper.enableStandardItemLighting();
        RenderHelper.enableGUIStandardItemLighting();

        GL11.glScalef(scalex, scaley, 1);

        ItemStack stack = getRenderStack();

        if(isDrawing(Tessellator.instance))
        {
            //Yes, this IS stuff to work around 'bad' mods :D
            setDrawing(Tessellator.instance, false);
        }
        int stackBeforeDraw = GL11.glGetInteger(GL11.GL_MODELVIEW_STACK_DEPTH);
        try {

            WidgetItem2DRender.itemRenderer
            .renderItemIntoGUI(minecraft.fontRenderer,
                    minecraft.renderEngine, stack, x, y);
            if(isDrawing(Tessellator.instance))
            {
                //Yes, this IS stuff to work around 'bad' mods :D
                setDrawing(Tessellator.instance, false);
            }
            WidgetItem2DRender.itemRenderer
            .renderItemOverlayIntoGUI(minecraft.fontRenderer,
                    minecraft.renderEngine, stack, x, y);
            if(isDrawing(Tessellator.instance))
            {
                //Yes, this IS stuff to work around 'bad' mods :D
                setDrawing(Tessellator.instance, false);
            }
        } catch (Throwable e) {
            if(isDrawing(Tessellator.instance))
            {
                //Yes, this IS stuff to work around 'bad' mods :D
                setDrawing(Tessellator.instance, false);
            }
        }

        int stackAfterDraw = GL11.glGetInteger(GL11.GL_MODELVIEW_STACK_DEPTH);

        if(stackBeforeDraw != stackAfterDraw)
        {
            //Yes, this IS stuff to work around 'bad' mods :D
            for (int i = 0; i < (stackAfterDraw - stackBeforeDraw); i++) {
                GL11.glPopMatrix();
            }
        }

        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(32826 /* GL_RESCALE_NORMAL_EXT *//* GL_RESCALE_NORMAL_EXT */);

        GL11.glPopMatrix();
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        screen.renderer.resumeRendering();
    }

    private void setDrawing(Tessellator tesselator, boolean state)
    {
        if(WidgetItem2DRender.isDrawingField == null)
        {
            return;
        }
        try
        {
            WidgetItem2DRender.isDrawingField.setBoolean(tesselator, state);
        }
        catch(Throwable e)
        {

        }
    }

    /**
     * This sets the current ID to render. This checks bounds. ItemStack damage
     * and count will stay the same.
     * 
     * @param renderID
     *            The ID you want this widget to render.
     */
   /* public void setRenderID(int renderID) {
        if ((renderID >= Item.itemsList.length) || (renderID < 0)) {
            throw new IndexOutOfBoundsException(
                    String.format(
                            "Render ID must be within the possible bounds of an Item ID! (%s - %s)",
                            0, Item.itemsList.length - 1));
        }
        if (renderStack == null) {
            renderStack = new ItemStack(Item.getItemById(renderID), 0, 0);
        }
        renderStack.itemID = renderID;
    }*/

    /**
     * This sets the ItemStack to render. This checks bounds.
     * 
     * @param stack
     *            The ItemStack you want this widget to render. Can't be null.
     */
   /* public void setRenderStack(ItemStack stack) {
        if (stack == null) {
            throw new IllegalArgumentException("stack cannot be null.");
        }
        if ((stack.itemID >= Item.itemsList.length) || (stack.itemID < 0)) {
            throw new IndexOutOfBoundsException(
                    String.format(
                            "Render ID must be within the possible bounds of an Item ID! (%s - %s)",
                            0, Item.itemsList.length - 1));
        }
        renderStack = stack;
    }*/

    /**
     * This sets what kind of scaling to use for this widget. Possible types
     * are:
     * 
     * -1: This doesn't scale it at all. It will be rendered right in the
     * middle, at 16x16 size if possible. If it needs to be smaller, it will
     * scale.
     * 
     * 0: This is the default mode. It scales to the biggest square it can, at
     * stays in the middle.
     * 
     * 1: This scales to fill all the space, whether or not that space is
     * square.
     * 
     * @param scaleType
     */
    public void setScaleType(int scaleType) {
        if (scaleType > 1) {
            scaleType = 1;
        }
        if (scaleType < -1) {
            scaleType = -1;
        }
        this.scaleType = scaleType;
    }
}
