package drzhark.guiapi.widget;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.Label;
import de.matthiasmann.twl.ScrollPane;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.model.SimpleButtonModel;
import drzhark.guiapi.GuiApiHelper;

/**
 * This widget is designed to make an easy base for menus. It include a
 * ScrollPane for the main widget, a title bar on top, and a button to go back
 * to the previous menu.
 *
 * @author lahwran
 * @author ShaRose
 */
public class WidgetSimplewindow extends Widget {

    /**
     * This is a reference to the back button, if created.
     */
    public Button backButton = new Button();
    /**
     * This is a reference to the row at the bottom that contains the back
     * button.
     */
    public WidgetSingleRow buttonBar = new WidgetSingleRow(0, 0);
    /**
     * This is the padding to use on each side of the main widget.
     */
    public int hPadding = 30;
    /**
     * This is a reference to the main widget in the center.
     */
    public Widget mainWidget = new Widget();

    /**
     * This is a reference to the ScrollPane that the Main Widget is in.
     */
    public Widget scrollPane = null; //TODO: Make this ScrollPane next release.
    /**
     * This is a reference to the Label that acts as the title on top.
     */
    public Label titleWidget = new Label();
    /**
     * This is the padding on the bottom. Generally, this is to keep room
     * between the bottom of the main widget's ScrollPane and the button bar.
     */
    public int vBottomPadding = 40;
    /**
     * This is the padding on the top. Generally, this is to keep room between
     * the top of the main widget's ScrollPane and the Label.
     */
    public int vTopPadding = 30;

    /**
     * This is a basic constructor. It sets the title as an empty string and it
     * creates a new WidgetClassicTwocolumn for the main widget.
     */
    public WidgetSimplewindow() {
        this(new WidgetClassicTwocolumn(), "", true);
    }

    /**
     * This is a basic constructor. It uses the passed widget as the main widget
     * and sets the title as an empty string.
     *
     * @param w The widget to use in the center.
     */
    public WidgetSimplewindow(Widget w) {
        this(w, "", true);
    }

    /**
     * This is the most common constructor. This keeps everything default,
     * although you can pass null for the title and it will remove the title bar
     * for you.
     *
     * @param w The widget to use in the center.
     * @param s The title to show on top. If null, the title bar will not be
     *        created.
     */
    public WidgetSimplewindow(Widget w, String s) {
        this(w, s, true);
    }

    /**
     * This is the more advanced constructor. This can also skip the back button
     * creation.
     *
     * @param w The widget to use in the center.
     * @param s The title to show on top. If null, the title bar will not be
     *        created.
     * @param showbackButton
     */
    public WidgetSimplewindow(Widget w, String s, Boolean showbackButton) {
        ScrollPane scrollpane = new ScrollPane(w);
        scrollpane.setFixed(ScrollPane.Fixed.HORIZONTAL);
        this.scrollPane = scrollpane;
        this.mainWidget = w;
        setTheme("");
        init(showbackButton, s);
    }

    /**
     * Initializes the WidgetSimplewindow's widgets. This is used internally.
     *
     * @param showBack Whether or not to show the back button.
     * @param titleText What to set the label text to. If null, it will not have
     *        a label.
     */
    protected void init(Boolean showBack, String titleText) {
        if (titleText != null) {
            this.titleWidget = new Label(titleText);
            add(this.titleWidget);
        } else {
            this.vTopPadding = 10;
        }
        if (showBack) {
            this.backButton = new Button(new SimpleButtonModel());
            this.backButton.getModel().addActionCallback(GuiApiHelper.backModAction.mergeAction(GuiApiHelper.clickModAction));
            this.backButton.setText("Back");
            this.buttonBar = new WidgetSingleRow(200, 20, this.backButton);
            add(this.buttonBar);
        } else {
            this.vBottomPadding = 0;
        }
        add(this.scrollPane);
    }

    @Override
    public void layout() {
        if (this.buttonBar != null) {
            this.buttonBar.setSize(this.buttonBar.getPreferredWidth(), this.buttonBar.getPreferredHeight());
            this.buttonBar.setPosition((getWidth() / 2) - (this.buttonBar.getPreferredWidth() / 2),
                    getHeight() - (this.buttonBar.getPreferredHeight() + 4));
        }
        if (this.titleWidget != null) {
            this.titleWidget.setPosition((getWidth() / 2) - (this.titleWidget.computeTextWidth() / 2), 10);
            this.titleWidget.setSize(this.titleWidget.computeTextWidth(), this.titleWidget.computeTextHeight());
        }
        this.scrollPane.setPosition(this.hPadding, this.vTopPadding);
        this.scrollPane.setSize(getWidth() - (this.hPadding * 2), getHeight() - (this.vTopPadding + this.vBottomPadding));
    }
}
