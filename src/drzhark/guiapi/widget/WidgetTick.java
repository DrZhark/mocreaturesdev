package drzhark.guiapi.widget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.Widget;

/**
 * This is a dummy Widget that you can use to call a Runnable object every
 * frame, or every X milliseconds. You can also use it for one-off events, such
 * as calling a method 5 seconds after a Widget is shown.
 * 
 * @author ShaRose
 */
public class WidgetTick extends Widget implements IWidgetAlwaysDraw {

    /**
     * This is a Tick that calls the passed Runnable each time a specific delay
     * is passed.
     * 
     * @author ShaRose
     */
    public class DelayTick implements iTick {

        long lastTick;
        boolean removeSelf = false;
        Runnable tickCallback;
        long timeToTick;

        /**
         * Creates a new DelayTick that calls each time a specific delay passes.
         * 
         * @param callback
         *            The Runnable it will call.
         * @param delay
         *            The delay on the tick. If 0, it will call on the first
         *            frame it is checked.
         */
        public DelayTick(Runnable callback, int delay) {
            if (callback == null) {
                throw new IllegalArgumentException("Callback cannot be null.");
            }
            if (delay < 0) {
                throw new IllegalArgumentException("Delay must be 0 or higher.");
            }
            lastTick = 0;
            timeToTick = delay;
            tickCallback = callback;
        }

        @Override
        public void checkTick() {
            long millis = System.currentTimeMillis();
            if ((lastTick + timeToTick) < millis) {
                lastTick = millis;
                tickCallback.run();
            }
        }

        /**
         * If this method is called, this tick will remove itself next frame.
         */
        public void removeSelf() {
            removeSelf = true;
        }

        @Override
        public boolean shouldRemove() {
            return removeSelf;
        }
    }

    /**
     * This is a Tick that calls the passed Runnable each frame.
     * 
     * @author ShaRose
     */
    public class FrameTick implements iTick {
        boolean removeSelf = false;

        Runnable tickCallback;

        /**
         * Creates a new FrameTick that calls each frame.
         * 
         * @param callback
         *            The Runnable it will call.
         */
        public FrameTick(Runnable callback) {
            if (callback == null) {
                throw new IllegalArgumentException("Callback cannot be null.");
            }
            tickCallback = callback;
        }

        @Override
        public void checkTick() {
            tickCallback.run();
        }

        /**
         * If this method is called, this tick will remove itself next frame.
         */
        public void removeSelf() {
            removeSelf = true;
        }

        @Override
        public boolean shouldRemove() {
            return removeSelf;
        }

        @Override
        public String toString() {
            return "FrameTick [tickCallback=" + tickCallback + "]";
        }
    }

    /**
     * This is an interface that is used by GuiAPI for ticks. You can use it as
     * well for creating custom tick patterns.
     * 
     * @author ShaRose
     */
    public interface iTick {
        /**
         * This is called once a frame if this Tick is added to a WidgetTick,
         * and said WidgetTick is drawn.
         */
        void checkTick();

        /**
         * @return Should this Tick be removed from the list?
         */
        boolean shouldRemove();
    }

    /**
     * This is a Tick that calls the passed Runnable after a configurable delay.
     * After it is called, it tries to remove itself.
     * 
     * @author ShaRose
     */
    public class SingleTick implements iTick {
        int delayBeforeRemove;

        Runnable tickCallback;
        long timeToTick;

        /**
         * Creates a new SingleTick that is only called once, with a
         * configurable delay. After this one tick it removes itself.
         * 
         * @param callback
         *            The Runnable it will call.
         * @param delay
         *            The delay on the tick. If 0, it will call on the first
         *            frame it is checked.
         */
        public SingleTick(Runnable callback, int delay) {
            if (callback == null) {
                throw new IllegalArgumentException("Callback cannot be null.");
            }
            if (delay < 0) {
                throw new IllegalArgumentException("Delay must be 0 or higher.");
            }
            timeToTick = -1;
            delayBeforeRemove = delay;
            tickCallback = callback;
        }

        @Override
        public void checkTick() {
            if (delayBeforeRemove == 0) {
                tickCallback.run();
            }
            if (timeToTick == -1) {
                timeToTick = System.currentTimeMillis() + delayBeforeRemove;
            } else {
                if (timeToTick < System.currentTimeMillis()) {
                    tickCallback.run();
                }
            }
        }

        @Override
        public boolean shouldRemove() {
            if (delayBeforeRemove == 0) {
                return true;
            }
            return timeToTick < System.currentTimeMillis();
        }

        @Override
        public String toString() {
            return "SingleTick [tickCallback=" + tickCallback + "]";
        }
    }

    protected ArrayList<iTick> ticks = new ArrayList<WidgetTick.iTick>();

    /**
     * This creates a new WidgetTick. It does nothing besides setting the max
     * size to 0,0.
     */
    public WidgetTick() {
        setMaxSize(0, 0);
    }

    /**
     * This creates and adds a new Tick that calls every frame.
     * 
     * @param callback
     *            The callback you want this to call.
     * @return {@link WidgetTick.FrameTick}
     */
    public FrameTick addCallback(Runnable callback) {
        FrameTick tick = new FrameTick(callback);
        ticks.add(tick);
        return tick;
    }

    /**
     * This creates and adds a tick that calls at a configurable delay.
     * 
     * @param callback
     *            The callback you want this to call.
     * @param timepertick
     * @return {@link WidgetTick.DelayTick}
     */
    public DelayTick addCallback(Runnable callback, int timepertick) {
        DelayTick tick = new DelayTick(callback, timepertick);
        ticks.add(tick);
        return tick;
    }

    /**
     * This adds an {@link WidgetTick.iTick} to the internal array. This is so you can
     * create your own custom ticks and add them if you want something more
     * powerful.
     * 
     * @param tick
     *            The {@link WidgetTick.iTick} to add.
     * @return true if it was able to be added, false otherwise.
     */
    public boolean addCustomTick(iTick tick) {
        return ticks.add(tick);
    }

    /**
     * This creates and adds a tick that is called only once after a delay, and
     * then removes itself.
     * 
     * @param callback
     *            The callback you want this to call.
     * @param delay
     *            The delay on the tick.
     * @return The created {@link WidgetTick.SingleTick}
     */
    public SingleTick addTimedCallback(Runnable callback, int delay) {
        SingleTick tick = new SingleTick(callback, delay);
        ticks.add(tick);
        return tick;
    }

    /**
     * Gets an unmodifiable copy of the currently registered ticks.
     * 
     * @return A unmodifiable copy of the tick list.
     */
    public List<iTick> getTickArrayCopy() {
        return Collections.unmodifiableList(ticks);
    }

    @Override
    protected void paintWidget(GUI gui) {
        iTick[] removedTicks = new iTick[ticks.size()];
        for (int i = 0; i < ticks.size(); i++) {
            iTick tick = ticks.get(i);
            try {
                tick.checkTick();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error when calling tick " + tick
                        + " at position " + i + " in WidgetTick.", e);
            }
            if (tick.shouldRemove()) {
                removedTicks[i] = tick;
            }
        }
        for (int i = 0; i < removedTicks.length; i++) {
            iTick tick = removedTicks[i];
            if (tick != null) {
                ticks.remove(tick);
            }
        }

    }
}
