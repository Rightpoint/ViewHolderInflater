package com.raizlabs.android.viewholderinflater.compiler.writer;

import com.raizlabs.android.viewholderinflater.compiler.VHManager;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
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
