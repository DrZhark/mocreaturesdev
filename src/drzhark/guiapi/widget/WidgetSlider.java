package drzhark.guiapi.widget;

import de.matthiasmann.twl.ValueAdjusterFloat;
import de.matthiasmann.twl.model.FloatModel;

/**
 * This is a simple extension of ValueAdjusterFloat so that it always updates
 * the setting. Used internally.
 * 
 * @author lahwran
 */
public class WidgetSlider extends ValueAdjusterFloat {
    /**
     * This is the basic constructor. It just calls the ValueAdjusterFloat
     * constructor, as well as adding an option to allow editing the value with text.
     * 
     * @param f
     *            The FloatModel to use.
     */
    public WidgetSlider(FloatModel f) {
        super(f);
    }
    
    private boolean canEdit = false;;
    
    /**
     * Specifies whether or not to allow the user to click on the Slider to enter a numberic value.
     * @param value Whether or not to allow Editing.
     */
    public void setCanEdit(boolean value)
    {
        canEdit = value;
    }
    
    /**
     * @return True if the user can edit this Slider by clicking on it: False otherwise.
     */
    public boolean getCanEdit()
    {
        return canEdit;
    }

    @Override
    public void startEdit() {
        if(!getCanEdit())
        {
            cancelEdit();
        }
        else
        {
            super.startEdit();
        }
    }
    
    @Override
    protected String onEditStart()
    {
        return Float.toString(getValue());
    }
}
