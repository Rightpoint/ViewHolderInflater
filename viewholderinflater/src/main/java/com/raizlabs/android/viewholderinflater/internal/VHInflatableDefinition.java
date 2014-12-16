package com.raizlabs.android.viewholderinflater.internal;

import android.view.View;

/**
 * Author: andrewgrosner
 * Description: The base implementation any {@link com.raizlabs.android.viewholderinflater.core.VHInflatable} will generate
 * as a full definition describing how to inflate the view.
 */
public abstract class VHInflatableDefinition<VHClass> {

    /**
     * Defines how to inflate the view into the {@link VHClass}. All fields of the {@link VHClass}
     * must be public or package private.
     *
     * @param view       The parent view to retrieve views from.
     * @param inflatable The non-null view holder of the views.
     */
    public abstract void inflate(View view, VHClass inflatable);

    /**
     * @return a new instance of the view holder if there is a default constructor available.
     * It will not be overridden if there is no default constructor available. This is used to return
     * a View Holder without needing to create one in {@link com.raizlabs.android.viewholderinflater.ViewHolderInflater#inflate(android.content.Context, Class, int)}
     */
    public VHClass newInstance() {
        return null;
    }
}
