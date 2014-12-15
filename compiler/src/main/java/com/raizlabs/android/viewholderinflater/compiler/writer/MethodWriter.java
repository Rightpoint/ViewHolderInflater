package com.raizlabs.android.viewholderinflater.compiler.writer;

import com.raizlabs.android.viewholderinflater.compiler.VHDefaultMethodList;
import com.raizlabs.android.viewholderinflater.compiler.VHManager;
import com.raizlabs.android.viewholderinflater.core.VHMethod;
import com.squareup.javawriter.JavaWriter;

import java.io.IOException;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
public class MethodWriter implements Writer {

    ExecutableElement element;

    VHManager manager;

    int resourceId;

    String viewElementName;

    String methodName;

    String methodInflatableName;

    public MethodWriter(VHManager vhManager, Element element, String methodInflatableName) {
        this.element = (ExecutableElement) element;
        this.methodInflatableName = methodInflatableName;
        manager = vhManager;

        VHMethod vhMethod = element.getAnnotation(VHMethod.class);
        if(vhMethod != null) {
            resourceId = vhMethod.value();
        }
        methodName = VHDefaultMethodList.getMethodName(element.getSimpleName().toString());
        viewElementName = VHDefaultMethodList.getResolvedMethodName(element.getSimpleName().toString());
    }

    @Override
    public void write(JavaWriter javaWriter) throws IOException {
        if(!manager.hasInflatableName(methodInflatableName, viewElementName)) {
            String instantiationStatement = "View %1sview = view.findViewById(";
            if (resourceId == 0) {
                instantiationStatement += String.format("view.getResources().getIdentifier(\"%1s\", \"id\", view.getContext().getPackageName())", viewElementName);
            } else {
                instantiationStatement += resourceId;
            }
            instantiationStatement += ")";
            javaWriter.emitStatement(instantiationStatement, viewElementName);
            manager.addInflatableName(methodInflatableName, viewElementName);
        }

        javaWriter.beginControlFlow("if (%1sview != null) ", viewElementName);

        VHDefaultMethodList.writeDefaultMethodImpl(javaWriter, viewElementName + "view",
                element, methodName);

        javaWriter.endControlFlow();
    }
}
