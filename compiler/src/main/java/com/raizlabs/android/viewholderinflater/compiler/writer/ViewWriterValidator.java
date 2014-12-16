package com.raizlabs.android.viewholderinflater.compiler.writer;

import com.raizlabs.android.viewholderinflater.compiler.VHManager;

/**
 * Author: andrewgrosner
 * Description: Validates a {@link com.raizlabs.android.viewholderinflater.compiler.writer.ViewWriter}
 * to ensure the resource id is valid.
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
