package com.grosner.viewholderinflater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author: andrewgrosner
 * Description: Responsible for inflating views into types marked with {@link com.grosner.viewholderinflater.core.VHInflatable}
 * and including each {@link com.grosner.viewholderinflater.core.VHView} definition.
 */
public class ViewHolderInflater {

    private static ViewHolderAdapter viewHolderAdapter;

    public static ViewHolderAdapter getViewHolderAdapter() {
        if (viewHolderAdapter == null) {
            try {
                viewHolderAdapter = (ViewHolderAdapter) Class.forName("com.grosner.viewholderinflater.ViewHolderAdapter$HolderAdapter").newInstance();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        return viewHolderAdapter;
    }

    /**
     * Inflates the View based on the layoutResId and uses a {@link com.grosner.viewholderinflater.VHInflatableDefinition}
     * to fill in the views. If the inflatable is a {@link android.view.ViewGroup},
     * we will attach it to the root automatically.
     *
     * @param context     The context use for a {@link android.view.LayoutInflater}
     * @param inflatable  The object that contains the {@link com.grosner.viewholderinflater.core.VHInflatable}
     * @param layoutResId The resource id of the layout to inflate.
     * @return The view that was inflated.
     */
    @SuppressWarnings("unchecked")
    public static View inflate(Context context, Object inflatable, int layoutResId) {
        ViewGroup root = null;
        if (inflatable instanceof ViewGroup) {
            root = ((ViewGroup) inflatable);
        }
        View view = LayoutInflater.from(context).inflate(layoutResId, root);

        // TODO: connect the view
        getInflatableDefinition(inflatable.getClass()).inflate(view, inflatable);

        return view;
    }

    /**
     * Inflates the View based on the layoutResId. Creates and returns a new instance of the {@link com.grosner.viewholderinflater.VHInflatableDefinition}
     * to fill in the views into it.
     * <p/>
     * The VHClass must have a default constructor.
     * <p/>
     * If the inflatable is a {@link android.view.ViewGroup},
     * we will attach it to the root automatically.
     *
     * @param context         The context use for a {@link android.view.LayoutInflater}
     * @param inflatableClass The class of the {@link com.grosner.viewholderinflater.core.VHInflatable} to create.
     * @param layoutResId     The resource id of the layout to inflate.
     * @param <VHClass>       The class that contains the {@link com.grosner.viewholderinflater.core.VHInflatable} annotation.
     * @return The created instance of the ViewHolder.
     */
    @SuppressWarnings("unchecked")
    public static <VHClass> VHClass inflate(Context context, Class<VHClass> inflatableClass, int layoutResId) {
        VHClass vhClass = (VHClass) getInflatableDefinition(inflatableClass).newInstance();

        inflate(context, vhClass, layoutResId);

        return vhClass;
    }

    /**
     * Inflates the specified viewResourceId from the parentView into a newly created {@link VHClass}.
     *
     * @param inflatableClass The class of the ViewHolder to create.
     * @param parentView      The parent view of the viewResourceId
     * @param viewResourceId  The id of the resource to find and use as the parent in the viewholder
     * @param <VHClass>       The class with the {@link com.grosner.viewholderinflater.core.VHInflatable} annotation.
     * @return new instance of the {@link VHClass}
     */
    @SuppressWarnings("unchecked")
    public static <VHClass> VHClass inflate(Class<VHClass> inflatableClass, View parentView, int viewResourceId) {
        View childView = parentView.findViewById(viewResourceId);
        VHClass vhClass = null;
        if (childView != null) {
            vhClass = (VHClass) getInflatableDefinition(inflatableClass).newInstance();
            if (vhClass != null) {
                getInflatableDefinition(inflatableClass).inflate(childView, vhClass);
            }
        }

        return vhClass;
    }

    public static VHInflatableDefinition getInflatableDefinition(Class<?> inflatableClass) {
        VHInflatableDefinition vhInflatableDefinition = getViewHolderAdapter().getVHInflatableDefinition(inflatableClass);
        if (vhInflatableDefinition == null) {
            throw new RuntimeException("No VHInflatableDefinition found for: " + inflatableClass + ". Did" +
                    "you forget the @VHInflatable annotation?");
        }
        return vhInflatableDefinition;
    }

}
