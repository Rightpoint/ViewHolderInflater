package com.raizlabs.android.viewholderinflater.internal;

import android.view.View;

import com.raizlabs.android.viewholderinflater.core.VHViewNotFoundException;

/**
 * Author: andrewgrosner
 * Description: Provides some basic utility methods used internally. This allows us to make changes to
 * underlying implementation without having to modify the compiler.
 */
public class VHUtils {

    /**
     * Calls {@link android.view.View#findViewById(int)} on the parentView. If the field is required,
     * we thrown a {@link com.raizlabs.android.viewholderinflater.core.VHViewNotFoundException}
     *
     * @param parentView The parent of the view to retrieve.
     * @param id         The resource id of the view to look up.
     * @param required   if true, we throw a {@link com.raizlabs.android.viewholderinflater.core.VHViewNotFoundException}
     * @return The result of {@link android.view.View#findViewById(int)} on the parent view
     * @throws com.raizlabs.android.viewholderinflater.core.VHViewNotFoundException if the view was
     *                                                                              null when it is required.
     */
    public static View findViewById(View parentView, int id, boolean required) {
        View child = parentView.findViewById(id);
        if (required) {
            throw new VHViewNotFoundException("View with id: " + id + "was not found");
        }
        return child;
    }
}
