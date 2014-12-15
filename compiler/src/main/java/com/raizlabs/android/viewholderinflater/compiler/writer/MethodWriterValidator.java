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

        if(!methodWriter.isMethodGroup && VHDefaultMethodList.containsMethod(methodWriter.viewElementName)) {
            vhManager.logError("Method %1s must not be named the same as any method in VHDefaultMethodList unless its a @VHMethodGroup",
                    methodWriter.element.getSimpleName());
            valid = false;
        }

        if (!methodWriter.isMethodGroup && methodWriter.resourceId < 0) {
            vhManager.logError("The Method %1s must have a valid resource id", methodWriter.element.getSimpleName());
            valid = false;
        } else if(methodWriter.isMethodGroup) {
            if((methodWriter.resourceIds == null || methodWriter.resourceIds.length == 0)) {
                vhManager.logError("The Method group %1s must have at least one resource id", methodWriter.element.getSimpleName());
                valid = false;
            } else {
                for(int resourceId: methodWriter.resourceIds) {
                    if(resourceId < 0) {
                        vhManager.logError("The Method %1s must have a valid resource id. Found %1d", methodWriter.element.getSimpleName(), resourceId);
                        valid = false;
                        break;
                    }
                }
            }
        }


        return valid;
    }
}
