package drzhark.guiapi.widget;

import java.util.HashMap;
import java.util.Map;

import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.ScrollPane;
import de.matthiasmann.twl.Widget;

/**
 * This widget is designed to arrange widgets into two columns. The width and
 * height is enforced, but they can be configured by the programmer.
 * 
 * @author lahwran
 * @author ShaRose
 */
public class WidgetClassicTwocolumn extends Widget {
    /**
     * This is the default height to enforce for widgets.
     */
    public int childDefaultHeight = 20;
    /**
     * This is the default width to enforce for widgets.
     */
    public int childDefaultWidth = 150;
    /**
     * This is the amount of padding to use between widgets vertically.
     */
    public int defaultPadding = 4;
    
    protected void paintChildren(GUI gui) {
        
        ScrollPane pane = ScrollPane.getContainingScrollPane(this);
        boolean isScrolling = pane != null;
        
        int minY = 0;
        int maxY = 0;
        if(isScrolling)
        {
            minY = getParent().getY();
            maxY = minY + pane.getContentAreaHeight();
        }
        
            for(int i=0,n=getNumChildren() ; i<n ; i++) {
                Widget child = getChild(i);
                if(child.isVisible()) {
                    boolean draw = !isScrolling;
                    if(!draw)
                    {
                    if(child instanceof IWidgetAlwaysDraw)
                    {
                        draw = true;
                    }
                    else
                    {
                        if(child.getY() + child.getHeight() >= minY && child.getY() <= maxY)
                        {
                            draw = true;
                        }
                    }
                    }
                    if(draw)
                    {
                        paintChild(gui, child);
                    }
                }
            }
    }
    
    /**
     * This is a map to override the heights of specific widgets. It is an
     * override to overrideHeight. If you set the Integer as 0, it will use what
     * the widget wants as it's height. If it is set negative, it will keep the
     * positive part as the minimum size, but if the widget wants to grow it
     * can. If you set anything else, it will use that height. Note that with
     * TwoColumn widgets it will try and keep the height the same between two
     * widgets opposite each other, so the one with the biggest height will
     * override the other.
     */
    public Map<Widget, Integer> heightOverrideExceptions = new HashMap<Widget, Integer>();
    /**
     * This says whether it should override the height for all widgets.
     */
    public boolean overrideHeight = true;
    /**
     * This is the amount of room between the two columns.
     */
    public int splitDistance = 10;
    /**
     * This is the amount of padding to have before any widgets are positioned.
     */
    public int verticalPadding = 0;
    /**
     * This is a map to override the width of specific widgets. It is an
     * override to childWidth. If you set the Integer as 0, it will use what the
     * widget wants as it's width. If it is set negative, it will keep the
     * positive part as the minimum size, but if the widget wants to grow it
     * can. If you set anything else, it will use that width. Note that with
     * TwoColumn widgets it will try and keep the width the same between two
     * widgets opposite each other, so the one with the biggest width will
     * override the other.
     */
    public Map<Widget, Integer> widthOverrideExceptions = new HashMap<Widget, Integer>();

    /**
     * This creates the WidgetClassicTwocolumn and adds the requested widgets.
     * 
     * @param widgets
     */
    public WidgetClassicTwocolumn(Widget... widgets) {
        for (int i = 0; i < widgets.length; ++i) {
            add(widgets[i]);
        }
        setTheme("");
    }

    @Override
    public int getPreferredHeight() {
        int totalheight = verticalPadding;
        for (int i = 0; i < getNumChildren(); i += 2) {
            Widget w = getChild(i);
            Widget w2 = null;
            if ((i + 1) != getNumChildren()) {
                w2 = getChild(i + 1);
            }
            int height = childDefaultHeight;
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
            if (w2 != null) {
                int temp = height;
                if (!overrideHeight) {
                    temp = w2.getPreferredHeight();
                }
                if (heightOverrideExceptions.containsKey(w2)) {
                    Integer heightSet = heightOverrideExceptions.get(w2);
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
                if (temp > height) {
                    height = temp;
                }
            }
            totalheight += height + defaultPadding;
        }
        return totalheight;
    }

    @Override
    public int getPreferredWidth() {
        return getParent().getWidth();
    }

    @Override
    public void layout() {
        if (getParent().getTheme().equals("scrollpane-notch")) {
            verticalPadding = 10;
        }
        int totalheight = verticalPadding;
        for (int i = 0; i < getNumChildren(); i += 2) {
            Widget w = getChild(i);
            Widget w2 = null;
            try {
                w2 = getChild(i + 1);
            } catch (IndexOutOfBoundsException e) {
                // do nothing, just means it's uneven.
            }
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
            if (w2 != null) {
                int temph = height;
                int tempw = width;
                if (!overrideHeight) {
                    temph = w2.getPreferredHeight();
                }
                if (heightOverrideExceptions.containsKey(w2)) {
                    Integer heightSet = heightOverrideExceptions.get(w2);
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
                if (widthOverrideExceptions.containsKey(w2)) {
                    Integer widthSet = widthOverrideExceptions.get(w2);

                    if (widthSet < 1) {
                        width = w2.getPreferredWidth();
                        widthSet = -widthSet;
                        if ((widthSet != 0) && (widthSet > width)) {
                            width = widthSet;
                        }
                    } else {
                        width = widthSet;
                    }
                }
                if (temph > height) {
                    height = temph;
                }
                if (tempw > width) {
                    width = tempw;
                }
            }
            w.setSize(width, height);
            w.setPosition((getX() + (getWidth() / 2))
                    - (width + (splitDistance / 2)), getY() + totalheight);
            if (w2 != null) {
                w2.setSize(width, height);
                w2.setPosition(getX() + (getWidth() / 2) + (splitDistance / 2),
                        getY() + totalheight);
            }
            totalheight += height + defaultPadding;
        }
    }
}
