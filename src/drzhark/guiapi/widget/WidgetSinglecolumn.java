package drzhark.guiapi.widget;

import de.matthiasmann.twl.Widget;

/**
 * This is a widget designed to arrange other widgets into a single column.
 * 
 * @author lahwran
 * @author ShaRose
 */
public class WidgetSinglecolumn extends WidgetClassicTwocolumn {
    /**
     * This creates the WidgetSinglecolumn with the specified Widgets. It
     * chooses a default Width of 200.
     * 
     * @param widgets
     *            The widgets to add.
     */
    public WidgetSinglecolumn(Widget... widgets) {
        super(widgets);
        childDefaultWidth = 200;
    }

    @Override
    public int getPreferredHeight() {
        int totalheight = verticalPadding;
        for (int i = 0; i < getNumChildren(); ++i) {
            Widget widget = getChild(i);
            int height = childDefaultHeight;
            if (!overrideHeight) {
                height = widget.getPreferredHeight();
            }
            if (heightOverrideExceptions.containsKey(widget)) {
                Integer heightSet = heightOverrideExceptions.get(widget);
                if (heightSet < 1) {
                    height = widget.getPreferredHeight();
                    heightSet = -heightSet;
                    if ((heightSet != 0) && (heightSet > height)) {
                        height = heightSet;
                    }
                } else {
                    height = heightSet;
                }
            }
            totalheight += height + defaultPadding;
        }
        return totalheight;
    }

    @Override
    public int getPreferredWidth() {
        // I can't see why we do a check here and not on TwoColoumn, and I don't
        // really want to loop widthOverrideExceptions, so let's just remove
        // this particular check, mmkay?
        // return Math.max(getParent().getWidth(), childWidth);
        return getParent().getWidth();
    }

    @Override
    public void layout() {
        int totalheight = verticalPadding;
        for (int i = 0; i < getNumChildren(); ++i) {
            Widget w = getChild(i);
            int height = childDefaultHeight;
            int width = childDefaultWidth;
            if (!overrideHeight) {
                height = w.getPreferredHeight();
            }
            if (heightOverrideExceptions.containsKey(w)) {
                Integer heightSet = heightOverrideExceptions.get(w);
                if (heightSet < 1) {
                    height = w.getPreferredHeight();
                    heightSet = -heightSet;
                    if ((heightSet != 0) && (heightSet > height)) {
                        height = heightSet;
                    }
                } else {
                    height = heightSet;
                }
            }
            if (widthOverrideExceptions.containsKey(w)) {
                Integer widthSet = widthOverrideExceptions.get(w);

                if (widthSet < 1) {
                    width = w.getPreferredWidth();
                    widthSet = -widthSet;
                    if ((widthSet != 0) && (widthSet > width)) {
                        width = widthSet;
                    }
                } else {
                    width = widthSet;
                }
            }
            w.setSize(width, height);
            w.setPosition((getX() + (getWidth() / 2)) - (width / 2), getY()
                    + totalheight);
            totalheight += height + defaultPadding;
        }
    }
}
