package drzhark.guiapi;

import java.util.HashMap;
import java.util.Map;

import de.matthiasmann.twl.Color;
import de.matthiasmann.twl.EditField;
import de.matthiasmann.twl.TextWidget;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.renderer.AnimationStateString;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLFont;
import drzhark.guiapi.widget.WidgetText;

/**
 * This class is designed to enable you to make clones of the GuiAPI font,
 * colour it and add options as you want, and then set that font to specific
 * kinds of widgets.
 * 
 * @author ShaRose
 * 
 */
public class GuiApiFontHelper {
    /**
     * These are the font states you can use for your settings. Most of the
     * time, the only ones you will use would be normal and hover.
     * 
     * @author ShaRose
     * 
     */
    public enum FontStates {
        disabled, error, hover, normal, textSelection, warning
    }

    private static Map<Widget, GuiApiFontHelper> customFontWidgets;
    private static Map<FontStates, AnimationStateString> stateTable;
    static {
        GuiApiFontHelper.customFontWidgets = new HashMap<Widget, GuiApiFontHelper>();
        try {
            GuiApiFontHelper.stateTable = new HashMap<GuiApiFontHelper.FontStates, AnimationStateString>();
            FontStates[] states = FontStates.values();
            for (int i = 0; i < states.length; i++) {
                GuiApiFontHelper.stateTable.put(states[i],
                        new AnimationStateString(states[i].name()));
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used internally to resync the font references. This has to
     * be used each time the theme is applied (After you set the screen,
     * specifically), otherwise TWL will automatically replace it with the
     * default font.
     */
    public static void resyncCustomFonts() {
        for (Map.Entry<Widget, GuiApiFontHelper> entry : GuiApiFontHelper.customFontWidgets
                .entrySet()) {
            // probably going to want to optimize this I think
            GuiApiFontHelper font = entry.getValue();
            Widget widget = entry.getKey();
            if (widget instanceof TextWidget) {
                font.setFont((TextWidget) widget);
            }
            if (widget instanceof EditField) {
                font.setFont((EditField) widget);
            }
            if (widget instanceof WidgetText) {
                font.setFont((WidgetText) widget);
            }
        }
    }

    private LWJGLFont myFont;

    /**
     * This creates a new GuiApiFontHelper with it's own internal font
     * reference.
     */
    public GuiApiFontHelper() {
        GuiWidgetScreen widgetScreen = GuiWidgetScreen.getInstance();
        LWJGLFont baseFont = (LWJGLFont) widgetScreen.theme.getDefaultFont();
        myFont = baseFont.clone();
    }

    /**
     * @param state
     *            The font state you want to check.
     * @return The Color for this font according to the specified state.
     */
    public Color getColor(FontStates state) {
        return myFont.evalFontState(GuiApiFontHelper.stateTable.get(state))
                .getColor();
    }

    /**
     * @param state
     *            The font state you want to check.
     * @return The LineThrough for this font according to the specified state.
     */
    public boolean getLineThrough(FontStates state) {
        return myFont.evalFontState(GuiApiFontHelper.stateTable.get(state))
                .getLineThrough();
    }

    /**
     * @param state
     *            The font state you want to check.
     * @return The OffsetX for this font according to the specified state.
     */
    public int getOffsetX(FontStates state) {
        return myFont.evalFontState(GuiApiFontHelper.stateTable.get(state))
                .getOffsetX();
    }

    /**
     * @param state
     *            The font state you want to check.
     * @return The OffsetY for this font according to the specified state.
     */
    public int getOffsetY(FontStates state) {
        return myFont.evalFontState(GuiApiFontHelper.stateTable.get(state))
                .getOffsetY();
    }

    /**
     * @param state
     *            The font state you want to check.
     * @return The Underline for this font according to the specified state.
     */
    public boolean getUnderline(FontStates state) {
        return myFont.evalFontState(GuiApiFontHelper.stateTable.get(state))
                .getUnderline();
    }

    /**
     * @param state
     *            The font state you want to check.
     * @return The UnderlineOffset for this font according to the specified
     *         state.
     */
    public int getUnderlineOffset(FontStates state) {
        return myFont.evalFontState(GuiApiFontHelper.stateTable.get(state))
                .getUnderlineOffset();
    }

    /**
     * @param state
     *            The font state you want to set.
     * @param col
     *            The Color you wish to this fontstate to have for this font.
     */
    public void setColor(FontStates state, Color col) {
        myFont.evalFontState(GuiApiFontHelper.stateTable.get(state)).setColor(
                col);
        GuiApiFontHelper.resyncCustomFonts();
    }

    /**
     * @param widget
     *            The EditField (Or derived class) you wish to set.
     */
    public void setFont(EditField widget) {
        try {
            setFont(widget.textRenderer);
            GuiApiFontHelper.customFontWidgets.put(widget, this);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * @param widget
     *            The TextWidget (Or derived class) you wish to set.
     */
    public void setFont(TextWidget widget) {
        widget.setFont(myFont);
        GuiApiFontHelper.customFontWidgets.put(widget, this);
    }

    /**
     * @param widget
     *            The WidgetText (Or derived class) you wish to set. This will
     *            set the display label (if it has one) and the edit field.
     */
    public void setFont(WidgetText widget) {
        if (widget.displayLabel != null) {
            widget.displayLabel.setFont(myFont);
            GuiApiFontHelper.customFontWidgets.put(widget, this);
        }
        setFont(widget.editField);
        GuiApiFontHelper.customFontWidgets.put(widget, this);
    }

    /**
     * @param state
     *            The font state you want to set.
     * @param val
     *            The LineThrough you wish to this fontstate to have for this
     *            font.
     */
    public void setLineThrough(FontStates state, boolean val) {
        myFont.evalFontState(GuiApiFontHelper.stateTable.get(state))
                .setLineThrough(val);
        GuiApiFontHelper.resyncCustomFonts();
    }

    /**
     * @param state
     *            The font state you want to set.
     * @param i
     *            The OffsetX you wish to this fontstate to have for this font.
     */
    public void setOffsetX(FontStates state, int i) {
        myFont.evalFontState(GuiApiFontHelper.stateTable.get(state))
                .setOffsetX(i);
        GuiApiFontHelper.resyncCustomFonts();
    }

    /**
     * @param state
     *            The font state you want to set.
     * @param i
     *            The OffsetY you wish to this fontstate to have for this font.
     */
    public void setOffsetY(FontStates state, int i) {
        myFont.evalFontState(GuiApiFontHelper.stateTable.get(state))
                .setOffsetY(i);
        GuiApiFontHelper.resyncCustomFonts();
    }

    /**
     * @param state
     *            The font state you want to set.
     * @param val
     *            The Underline you wish to this fontstate to have for this
     *            font.
     */
    public void setUnderline(FontStates state, boolean val) {
        myFont.evalFontState(GuiApiFontHelper.stateTable.get(state))
                .setUnderline(val);
        GuiApiFontHelper.resyncCustomFonts();
    }

    /**
     * @param state
     *            The font state you want to set.
     * @param i
     *            The UnderlineOffset you wish to this fontstate to have for
     *            this font.
     */
    public void setUnderlineOffset(FontStates state, int i) {
        myFont.evalFontState(GuiApiFontHelper.stateTable.get(state))
                .setUnderlineOffset(i);
        GuiApiFontHelper.resyncCustomFonts();
    }

}
