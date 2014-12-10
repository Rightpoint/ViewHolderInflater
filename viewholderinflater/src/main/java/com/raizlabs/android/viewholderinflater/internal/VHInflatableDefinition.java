package com.raizlabs.android.viewholderinflater.internal;

import android.view.View;

/**
 * Author: andrewgrosner
 * Description: The base implementation any {@link com.raizlabs.android.viewholderinflater.core.VHInflatable} will generate
 * as a full definition describing how to inflate the view.
 */
public abstract class VHInflatableDefinition<VHClass> {

    public abstract void inflate(View view, VHClass inflatable);

    /**
     * @return a new instance of the view holder if there is a default constructor available.
     * It will not be overridden if there is no default constructor available.
     */
    public VHClass newInstance() {
        return null;
    }
}
