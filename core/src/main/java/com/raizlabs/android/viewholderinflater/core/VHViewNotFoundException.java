package com.raizlabs.android.viewholderinflater.core;

/**
 * Author: andrewgrosner
 * Description: Called when a required {@link com.raizlabs.android.viewholderinflater.core.VHView} is not found
 */
public class VHViewNotFoundException extends RuntimeException {

    public VHViewNotFoundException(String message) {
        super(message);
    }

}
