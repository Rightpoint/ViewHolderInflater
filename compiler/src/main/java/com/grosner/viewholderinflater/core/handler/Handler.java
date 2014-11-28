package com.grosner.viewholderinflater.core.handler;

import com.grosner.viewholderinflater.core.VHManager;

import javax.annotation.processing.RoundEnvironment;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
public interface Handler {

    public void handle(VHManager vhManager, RoundEnvironment roundEnvironment);
}
