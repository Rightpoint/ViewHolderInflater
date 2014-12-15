package com.raizlabs.android.viewholderinflater.compiler.writer;

import com.raizlabs.android.viewholderinflater.compiler.VHDefaultMethodList;
import com.raizlabs.android.viewholderinflater.compiler.VHManager;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
public class MethodWriterValidator implements Validator<MethodWriter>{
    @Override
    public boolean validate(VHManager vhManager, MethodWriter methodWriter) {
        boolean valid = true;

        if(methodWriter.methodName.isEmpty()) {
            vhManager.logError("Method %1s is not a valid method name", methodWriter.viewElementName);
            valid = false;
        }

        if(VHDefaultMethodList.containsMethod(methodWriter.viewElementName)) {
            vhManager.logError("Method %1s must not be named the same as any method in VHDefaultMethodList", methodWriter.viewElementName);
            valid = false;
        }

        if (methodWriter.resourceId < 0) {
            vhManager.logError("The MethodWriter %1s must have a valid resource id", methodWriter.element.getSimpleName());
            valid = false;
        }


        return valid;
    }
}
