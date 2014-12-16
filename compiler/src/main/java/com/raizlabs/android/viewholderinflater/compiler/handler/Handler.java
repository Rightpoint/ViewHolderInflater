package com.raizlabs.android.viewholderinflater.compiler.handler;

import com.raizlabs.android.viewholderinflater.compiler.VHManager;

import javax.annotation.processing.RoundEnvironment;

/**
 * Author: andrewgrosner
 * Description: The interface that is registered with the {@link com.raizlabs.android.viewholderinflater.compiler.VHProcessor}
 * to perform some action during processing.
 */
public interface Handler {

    /**
     * Called when we are processing annotations
     *
     * @param vhManager        The manager
     * @param roundEnvironment The environment
     */
    public void handle(VHManager vhManager, RoundEnvironment roundEnvironment);
}
