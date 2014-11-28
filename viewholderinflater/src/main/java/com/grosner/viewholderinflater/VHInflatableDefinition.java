package com.grosner.viewholderinflater;

import android.view.View;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description: The interface any {@link com.grosner.viewholderinflater.core.VHInflatable} will generate
 * as a full definition describing how to inflate the view.
 */
public interface VHInflatableDefinition<VHClass> {

    public void inflate(View view, VHClass inflatable);
}
