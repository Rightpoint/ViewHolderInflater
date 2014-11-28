package com.grosner.viewholderinflater;

import android.view.View;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description: The interface any {@link com.grosner.viewholderinflater.core.VHInflatable} will generate
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
