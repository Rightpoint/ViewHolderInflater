package com.raizlabs.android.viewholderinflater.compiler.writer;

import com.raizlabs.android.viewholderinflater.compiler.VHManager;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
public class ViewWriterValidator implements Validator<ViewWriter> {
    @Override
    public boolean validate(VHManager vhManager, ViewWriter viewWriter) {
        boolean valid = true;

        if (viewWriter.resourceId < 0) {
            vhManager.logError("The ViewWriter %1s must have a valid resource id", viewWriter.element.getSimpleName());
            valid = false;
        }

        return valid;
    }
}
