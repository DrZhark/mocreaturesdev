package drzhark.guiapi;

import java.util.AbstractMap;
import java.util.ArrayList;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.TextArea;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.model.SimpleButtonModel;
import de.matthiasmann.twl.textarea.HTMLTextAreaModel;
import de.matthiasmann.twl.textarea.SimpleTextAreaModel;
import de.matthiasmann.twl.textarea.TextAreaModel;
import drzhark.guiapi.widget.WidgetSimplewindow;
import drzhark.guiapi.widget.WidgetSinglecolumn;

/**
 * This is just a class for helping ease common and somewhat long operations
 * with GuiAPI.
 * 
 * @author ShaRose
 */
public class GuiApiHelper {
    /**
     * This is a static ModAction to go back to the previous menu.
     */
    public final static ModAction backModAction;
    /**
     * This is a static ModAction to play the 'click' sound you usually hear
     * when pressing a button in minecraft.
     */
    public final static ModAction clickModAction;

    static {
        backModAction = new ModAction(GuiModScreen.class, "back");
        GuiApiHelper.backModAction.setTag("Helper Back ModAction");
        clickModAction = new ModAction(GuiModScreen.class, "clicksound");
        GuiApiHelper.clickModAction.setTag("Helper ClickSound ModAction");
    }

    /**
     * This method is one of the overloads to create a choice menu, so the user
     * is presented a textbox and user configurable buttons beneath it. This
     * overload is the most advanced option, though uses more code. Call this
     * method, use the returned GuiApiHelper instance to add the buttons you
     * want, then generate the widget.
     * 
     * @param displayText
     *            The text to be displayed to the user.
     * @return An instance of GuiApiHelper. Use the addButton methods to add
     *         buttons to the menu, and then when you are done use genWidget to
     *         create the choice menu.
     */
    public static GuiApiHelper createChoiceMenu(String displayText) {
        return new GuiApiHelper(displayText);
    }

    /**
     * This method is one of the overloads to create a choice menu, so the user
     * is presented a textbox and user configurable buttons beneath it. This
     * overload uses variable arguments to choose it.
     * 
     * @param displayText
     *            The text to display.
     * @param showBackButton
     *            Whether to automatically show a 'back' button or not.
     * @param autoBack
     *            Whether to automatically merge a 'back' ModAction with the
     *            buttons.
     * @param args
     *            The button information. Enter it in the form of String (Name
     *            on the button), ModAction (The ModAction to call when the
     *            button is pressed).
     * @return The generated widget. Use GuiModScreen.show to display it.
     */
    public static Widget createChoiceMenu(String displayText,
            Boolean showBackButton, Boolean autoBack, Object... args) {
        if ((args.length % 2) == 1) {
            throw new IllegalArgumentException(
                    "Arguments not in correct format. You need to have an even number of arguments, in the form of String, ModAction for each button.");
        }
        GuiApiHelper helper = new GuiApiHelper(displayText);
        try {
            for (int i = 0; i < args.length; i += 2) {
                helper.addButton((String) args[i], (ModAction) args[i + 1],
                        autoBack);
            }
        } catch (Throwable e) {
            throw new IllegalArgumentException(
                    "Arguments not in correct format. You need to have an even number of arguments, in the form of String, ModAction for each button.",
                    e);
        }
        return helper.genWidget(showBackButton);
    }

    /**
     * This method is one of the overloads to create a choice menu, so the user
     * is presented a textbox and user configurable buttons beneath it. This
     * overload uses two tables that match up to create the buttons.
     * 
     * @param displayText
     *            The text to display.
     * @param showBackButton
     *            Whether to automatically show a 'back' button or not.
     * @param autoBack
     *            Whether to automatically merge a 'back' ModAction with the
     *            buttons.
     * @param buttonTexts
     *            The text for the buttons you want to show.
     * @param buttonActions
     *            The corresponding ModActions for the buttons.
     * @return The generated widget. Use GuiModScreen.show to display it.
     */
    public static Widget createChoiceMenu(String displayText,
            Boolean showBackButton, Boolean autoBack, String[] buttonTexts,
            ModAction[] buttonActions) {
        if (buttonTexts.length != buttonActions.length) {
            throw new IllegalArgumentException(
                    "Arguments not in correct format. buttonTexts needs to be the same size as buttonActions.");
        }
        GuiApiHelper helper = new GuiApiHelper(displayText);
        for (int i = 0; i < buttonTexts.length; i += 2) {
            helper.addButton(buttonTexts[i], buttonActions[i], autoBack);
        }
        return helper.genWidget(showBackButton);
    }

    /**
     * This method creates a button widget for you.
     * 
     * @param displayText
     *            The text to display on the button.
     * @param action
     *            The ModAction to call when clicked.
     * @param addClick
     *            Set this to true and it will automatically play the Click
     *            sound.
     * @return The new Button widget.
     */
    public static Button makeButton(String displayText, ModAction action,
            Boolean addClick) {
        SimpleButtonModel simplebuttonmodel = new SimpleButtonModel();
        if (addClick) {
            action = action.mergeAction(GuiApiHelper.clickModAction);
        }
        simplebuttonmodel.addActionCallback(action);
        Button button = new Button(simplebuttonmodel);
        button.setText(displayText);
        return button;
    }

    /**
     * This method creates a button widget for you.
     * 
     * @param displayText
     *            The text to display on the button.
     * @param methodName
     *            The name of the method to call when clicked.
     * @param me
     *            The Object or Class that has the method you want to call.
     * @param addClick
     *            Set this to true and it will automatically play the Click
     *            sound.
     * @return The new Button widget.
     */
    public static Button makeButton(String displayText, String methodName,
            Object me, Boolean addClick) {
        return GuiApiHelper.makeButton(displayText, new ModAction(me,
                methodName), addClick);
    }

    /**
     * This method creates a button widget for you.
     * 
     * @param displayText
     *            The text to display on the button.
     * @param methodName
     *            The name of the method to call when clicked.
     * @param me
     *            The Object or Class that has the method you want to call.
     * @param addClick
     *            Set this to true and it will automatically play the Click
     *            sound.
     * @param classes
     *            The argument classes for the method you want to call.
     * @param arguments
     *            The defaulted arguments you want to use.
     * @return The new Button widget.
     */
    @SuppressWarnings("rawtypes")
    public static Button makeButton(String displayText, String methodName,
            Object me, Boolean addClick, Class[] classes, Object... arguments) {
        return GuiApiHelper.makeButton(displayText, new ModAction(me,
                methodName, classes).setDefaultArguments(arguments), addClick);
    }

    /**
     * This is a small helper to create TextAreas, which is basically a label
     * that wraps text.
     * 
     * @param text
     *            The text to show.
     * @param htmlMode
     *            Whether to create the Textbox to render HTML, or standard
     *            text.
     * @return The TextArea widget.
     */
    public static TextArea makeTextArea(String text, Boolean htmlMode) {
        if (!htmlMode) {
            SimpleTextAreaModel model = new SimpleTextAreaModel();
            model.setText(text, false);
            return new TextArea(model);
        }
        HTMLTextAreaModel model = new HTMLTextAreaModel();
        model.setHtml(text);
        return new TextArea(model);
    }

    /**
     * This method is designed to provide an easy way to make popups or
     * information notices.
     * 
     * @param titleText
     *            This is the text for the title on top of the display. If you
     *            set this to null, it will simply not have a top bar.
     * @param displayText
     *            This is the text to be displayed below the title bar (If there
     *            is one).
     * @param buttonText
     *            This is the text you want the back button to have. Something
     *            like 'OK' or 'Back' is what you usually use.
     * @param htmlMode
     *            This is if you want the text to be rendered as if it were
     *            HTML.
     * @return The generated widget. Use GuiModScreen.show to display it.
     */
    public static Widget makeTextDisplayAndGoBack(String titleText,
            String displayText, String buttonText, Boolean htmlMode) {
        WidgetSinglecolumn widget = new WidgetSinglecolumn(new Widget[0]);
        widget.add(GuiApiHelper.makeTextArea(displayText, htmlMode));
        widget.overrideHeight = false;
        WidgetSimplewindow window = new WidgetSimplewindow(widget, titleText);
        window.backButton.setText(buttonText);
        return window;
    }

    /**
     * This is a small helper method to set the text of a TextArea. It supposed
     * Simple and HTML TextAreas.
     * 
     * @param textArea
     *            The TextArea you wish to set the text of.
     * @param text
     *            The text to set.
     */
    public static void setTextAreaText(TextArea textArea, String text) {
        TextAreaModel model = textArea.getModel();
        if (model instanceof SimpleTextAreaModel) {
            ((SimpleTextAreaModel) model).setText(text, false);
        } else {
            ((HTMLTextAreaModel) model).setHtml(text);
        }
    }

    /** The button info. */
    private ArrayList<AbstractMap.SimpleEntry<String, ModAction>> buttonInfo_;

    /** The display text. */
    private String displayText_;

    /**
     * Instantiates a new gui api helper.
     * 
     * @param displayText
     *            the display text
     */
    private GuiApiHelper(String displayText) {
        displayText_ = displayText;
        buttonInfo_ = new ArrayList<AbstractMap.SimpleEntry<String, ModAction>>();
    }

    /**
     * This method adds a button to the choice menu. The arguments are the same
     * as the related makeButton method.
     * 
     * @param text
     *            The text for the button.
     * @param action
     *            The action to use when pressed.
     * @param mergeBack
     *            Whether or not to automatically to back after the button is
     *            pressed.
     */
    public void addButton(String text, ModAction action, Boolean mergeBack) {
        ModAction buttonAction = action;
        if (mergeBack) {
            buttonAction = buttonAction.mergeAction(GuiApiHelper.backModAction);
            buttonAction.setTag("Button '" + text + "' with back.");
        }
        buttonInfo_.add(new AbstractMap.SimpleEntry<String, ModAction>(text,
                buttonAction));
    }

    /**
     * This method adds a button to the choice menu. The arguments are the same
     * as the related makeButton method.
     * 
     * @param text
     *            The text for the button.
     * @param methodName
     *            The method to call.
     * @param me
     *            The object or class with the method you wish to call.
     * @param mergeBack
     *            Whether or not to automatically to back after the button is
     *            pressed.
     */
    public void addButton(String text, String methodName, Object me,
            Boolean mergeBack) {
        addButton(text, new ModAction(me, methodName), mergeBack);
    }

    /**
     * This method adds a button to the choice menu. The arguments are the same
     * as the related makeButton method.
     * 
     * @param text
     *            The text for the button.
     * @param methodName
     *            The method to call.
     * @param me
     *            The object or class with the method you wish to call.
     * @param types
     *            The types of the arguments required for the method.
     * @param mergeBack
     *            Whether or not to automatically to back after the button is
     *            pressed.
     * @param arguments
     *            The arguments you wish to use when this button is pressed.
     */
    @SuppressWarnings("rawtypes")
    public void addButton(String text, String methodName, Object me,
            Class[] types, Boolean mergeBack, Object... arguments) {
        addButton(text,
                new ModAction(me, methodName, types)
                        .setDefaultArguments(arguments), mergeBack);
    }

    /**
     * This creates the Choice Menu from the Display Text entered earlier and
     * the buttons you have added.
     * 
     * @param showBackButton
     *            If true, show a bar on the bottom with a button to go back to
     *            the previous menu. If false, don't.
     * @return The generated widget. Use GuiModScreen.show to display it.
     */
    public WidgetSimplewindow genWidget(Boolean showBackButton) {
        WidgetSinglecolumn widget = new WidgetSinglecolumn(new Widget[0]);
        TextArea textarea = GuiApiHelper.makeTextArea(displayText_, false);
        widget.add(textarea);
        widget.heightOverrideExceptions.put(textarea, 0);
        for (AbstractMap.SimpleEntry<String, ModAction> entry : buttonInfo_) {
            widget.add(GuiApiHelper.makeButton(entry.getKey(),
                    entry.getValue(), true));
        }
        WidgetSimplewindow window = new WidgetSimplewindow(widget, null,
                showBackButton);
        return window;
    }
}
