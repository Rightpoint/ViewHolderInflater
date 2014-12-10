package com.raizlabs.android.viewholderinflater.internal;

import android.view.View;

/**
 * Author: andrewgrosner
 * Description: The base implementation any {@link com.raizlabs.android.viewholderinflater.core.VHMethodInflatable}
 * will generate as a full definition describing how to connect the view to the {@link VHMethodClass}
 */
public abstract class VHMethodInflatableDefinition<VHMethodClass> {

    /**
     * Connects the methods defined in the {@link VHMethodClass} to the views.
     *
     * @param view       The parent view we retrieve child views from.
     * @param inflatable The class that contains methods to inject callbacks into
     */
    public abstract void connect(View view, VHMethodClass inflatable);
}
