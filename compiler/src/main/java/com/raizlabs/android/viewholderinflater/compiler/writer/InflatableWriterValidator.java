package com.raizlabs.android.viewholderinflater.compiler.writer;

import com.raizlabs.android.viewholderinflater.compiler.VHManager;

/**
 * Author: andrewgrosner
 * Description: Validates a {@link com.raizlabs.android.viewholderinflater.compiler.writer.InflatableWriter} class.
 *
 * Will throw errors if data is not valid.
 */
public class InflatableWriterValidator implements Validator<InflatableWriter> {
    @Override
    public boolean validate(VHManager vhManager, InflatableWriter inflatableWriter) {
        boolean isValid = true;
        if (inflatableWriter.mViews.isEmpty()) {
            vhManager.logError("%1s must contain at least one @VHView or @VHInflatableViewHolder", inflatableWriter.elementClassName);
            isValid = false;
        }
        return isValid;
    }
}
