package com.raizlabs.android.viewholderinflater.compiler.handler;

import com.raizlabs.android.viewholderinflater.compiler.VHManager;

import javax.annotation.processing.RoundEnvironment;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
public interface Handler {

    public void handle(VHManager vhManager, RoundEnvironment roundEnvironment);
}
