package drzhark.guiapi;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Method;
import java.security.InvalidParameterException;
import java.util.ArrayList;

import de.matthiasmann.twl.CallbackWithReason;
import de.matthiasmann.twl.ListBox;
import de.matthiasmann.twl.ListBox.CallbackReason;
import de.matthiasmann.twl.TextArea;

/**
 * This class is a helper designed to make it easier to use callbacks. It
 * implements Runnable and PropertyChangeListener, and you can use it in several
 * ways.
 * 
 * @author _303
 * @author ShaRose
 */
@SuppressWarnings("rawtypes")
public class ModAction implements Runnable, PropertyChangeListener,
        TextArea.Callback, CallbackWithReason<ListBox.CallbackReason> {
    @SuppressWarnings("unchecked")
    private static Boolean checkArguments(Class[] classTypes, Object[] arguments) {
        if (classTypes.length != arguments.length) {
            return false;
        }
        for (int i = 0; i < classTypes.length; i++) {
            if (!classTypes[i].isAssignableFrom(arguments[i].getClass())) {
                return false;
            }
        }
        return true;
    }

    private Object[] defaultArguments;

    private ArrayList<ModAction> mergedActions = new ArrayList<ModAction>();
    private String methodName;
    private Class[] methodParams = new Class[0];
    private Object objectRef;
    private Object tag;

    /**
     * This is the most common ModAction constructor. You simply specify what
     * has the method you want, and the method's name and (Optionally) the
     * parameters.
     * 
     * @param o
     *            The object reference or class that contains the method you
     *            wish to call.
     * @param method
     *            The name of the method you wish to call.
     * @param params
     *            The parameters of the method you wish to call.
     */
    public ModAction(Object o, String method, Class... params) {
        setTag(method);
        methodParams = params;
        setupHandler(o, method);
    }

    /**
     * This is an overload to allow the nameRef string.
     * 
     * @param o
     *            The object reference or class that contains the method you
     *            wish to call.
     * @param method
     *            The name of the method you wish to call.
     * @param name
     *            The name of this ModAction. Something else you can use to keep
     *            track, and this is included within exceptions.
     * @param params
     *            The parameters of the method you wish to call.
     */
    public ModAction(Object o, String method, String name, Class... params) {
        this(o, method, params);
        setTag(name);
    }

    /**
     * This is a constructor that is only supposed to be used internally. It
     * sets no actual handler, only a name.
     * 
     * @param name
     *            The name to use for this ModAction.
     */
    private ModAction(String name) {
        setTag(name);
    }

    /**
     * Call this ModAction and any Merged actions with the provided arguments.
     * If the arguments do not match it will try using the default arguments, if
     * they exist. If not, it will throw an exception.
     * 
     * @param args
     *            The arguments to try and call for the ModAction (And any
     *            merged ModActions)
     * @return The return values for each ModAction.
     * @throws Exception
     *             Any exception thrown when the ModAction attempts to run.
     */
    public Object[] call(Object... args) throws Exception {
        try {
            if (mergedActions.isEmpty()) {
                return new Object[] { callInternal(args) };
            }
            Object[] returnvals = new Object[mergedActions.size()];
            for (int i = 0; i < returnvals.length; i++) {
                returnvals[i] = mergedActions.get(i).call(args);
            }
            return returnvals;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("error calling callback '" + getTag() + "'.", e);
        }
    }

    @Override
    public void callback(CallbackReason reason) {
        if ((methodParams.length != 1)
                || (methodParams[0] != CallbackReason.class)) {
            throw new RuntimeException(
                    "invalid method parameters for a CallbackWithReason<ListBox.CallbackReason> callback. Modaction is '"
                            + getTag() + "'.");
        }
        try {
            call(reason);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(
                    "Error when calling CallbackWithReason<ListBox.CallbackReason> callback. Modaction is '"
                            + getTag() + "'.", e);
        }
    }

    private Object callInternal(Object... args) throws Exception {
        if (!ModAction.checkArguments(methodParams, args)) {
            if (defaultArguments != null) {
                args = defaultArguments;
            }
        }
        try {
            Method meth = getMethodRecursively(objectRef, methodName);
            return meth.invoke(objectRef instanceof Class ? null : objectRef,
                    args);
        } catch (Exception e) {
            throw new Exception("error calling callback '" + getTag() + "'.", e);
        }
    }

    private Method getMethodRecursively(Object o, String method)
            throws Exception {
        Class<?> currentclass = (o instanceof Class ? (Class<?>) o : o
                .getClass());
        while (true) {
            if (currentclass == null) {
                throw new Exception(
                        "Unable to locate method '"
                                + method
                                + "' anywhere in the inheritance chain of object '"
                                + (o instanceof Class ? (Class<?>) o : o
                                        .getClass()).getName() + "'!");
            }
            try {
                Method returnval = currentclass.getDeclaredMethod(method,
                        methodParams);
                if (returnval != null) {
                    returnval.setAccessible(true);
                    return returnval;
                }
            } catch (Throwable x) {
            }
            currentclass = currentclass.getSuperclass();
        }
    }

    /**
     * @return The tag of this ModAction.
     */
    public Object getTag() {
        return tag;
    }

    @Override
    public void handleLinkClicked(String link) {
        if ((methodParams.length != 1) || (methodParams[0] != String.class)) {
            throw new RuntimeException(
                    "invalid method parameters for a TextArea.Callback callback. Modaction is '"
                            + getTag() + "'.");
        }
        try {
            call(link);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(
                    "Error when calling TextArea.Callback callback. Modaction is '"
                            + getTag() + "'.", e);
        }
    }

    /**
     * Merge[s] newAction[s] with this action.
     * 
     * @param newActions
     *            The new Action[s] to merge with.
     * @return The Merged ModAction.
     */
    public ModAction mergeAction(ModAction... newActions) {
        if (mergedActions.isEmpty()) {
            ModAction merged = new ModAction("Merged ModAction");
            merged.mergedActions.add(this);
            for (ModAction modAction : newActions) {
                merged.mergedActions.add(modAction);
            }

            return merged;
        }
        for (ModAction modAction : newActions) {
            mergedActions.add(modAction);
        }
        return this;
    }

    @Override
    public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent) {
        if ((methodParams.length != 1)
                || (methodParams[0] != PropertyChangeEvent.class)) {
            throw new RuntimeException(
                    "invalid method parameters for a PropertyChangeListener callback. Modaction is '"
                            + getTag() + "'.");
        }
        try {
            call(paramPropertyChangeEvent);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(
                    "Error when calling PropertyChangeListener callback. Modaction is '"
                            + getTag() + "'.", e);
        }
    }

    @Override
    public void run() {
        try {
            call();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(
                    "Error when calling Runnable callback. Modaction is '"
                            + getTag() + "'.", e);
        }
    }

    /**
     * Set the arguments to use if no or invalid arguments are provided. Throws
     * InvalidParameterException if the arguments provided do not match the
     * method parameters, or are not assignable to those types.
     * 
     * @param Arguments
     *            The arguments to try and call.
     * @return this
     */
    public ModAction setDefaultArguments(Object... Arguments) {
        if (!ModAction.checkArguments(methodParams, Arguments)) {
            throw new InvalidParameterException(
                    "Arguments do not match the parameters.");
        }
        defaultArguments = Arguments;
        return this;
    }

    /**
     * Sets the tag of this ModAction. Used for tracking, and is included with
     * exceptions.
     * 
     * @param tag
     *            The tag to assign to this ModAction.
     */
    public void setTag(Object tag) {
        this.tag = tag;
    }

    private void setupHandler(Object o, String method) {
        try {
            getMethodRecursively(o, method);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(
                    "Could not locate Method with included information.", e);
        }
        methodName = method;
        objectRef = o;
    }

    @Override
    public String toString() {
        return "ModAction [methodName=" + methodName + ", tag=" + tag + "]";
    }
}