package com.raizlabs.android.viewholderinflater.compiler.writer;

import com.raizlabs.android.viewholderinflater.compiler.VHManager;

/**
 * Author: andrewgrosner
 * Description: Ensures that the {@link com.raizlabs.android.viewholderinflater.compiler.writer.MethodInflatableWriter}
 * contains at least 1 method.
 */
public class MethodInflatableWriterValidator implements Validator<MethodInflatableWriter> {
    @Override
    public boolean validate(VHManager vhManager, MethodInflatableWriter methodInflatableWriter) {
        boolean valid = true;

        if(methodInflatableWriter.mMethodList.isEmpty()) {
            vhManager.logError("%1s must contain at least one @VHMethod", methodInflatableWriter.elementClassName);
            valid = false;
        }

        return valid;
    }
}
