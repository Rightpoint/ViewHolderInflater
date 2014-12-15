package com.raizlabs.android.viewholderinflater.compiler;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
public class VHUtils {


    public static String getMethodStatement(ExecutableElement executable, String... params) {
        StringBuilder methodStatement = new StringBuilder(executable.getSimpleName().toString() + "(");
        if (executable.getParameters().size() > 0) {
            int size = executable.getParameters().size();
            for (int i = 0; i < size; i++) {
                if (i > 0) {
                    methodStatement.append(",");
                }
                VariableElement variableElement = executable.getParameters().get(i);
                methodStatement.append("(").append(variableElement.asType().toString()).append(")");
                methodStatement.append(params[i]);
            }
        }
        methodStatement.append(")");
        return methodStatement.toString();
    }
}
