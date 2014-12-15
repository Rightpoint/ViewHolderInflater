package com.raizlabs.android.viewholderinflater.internal;

import android.view.View;

import com.raizlabs.android.viewholderinflater.core.VHViewNotFoundException;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
public class VHUtils {

    public static View findViewById(View parentView, int id, boolean required) {
        View child = parentView.findViewById(id);
        if(required) {
            throw new VHViewNotFoundException("View with id: " + id + "was not found");
        }
        return child;
    }
}
