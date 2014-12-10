package com.raizlabs.android.viewholderinflater.compiler.writer;

import com.raizlabs.android.viewholderinflater.compiler.VHDefaultMethodList;
import com.raizlabs.android.viewholderinflater.compiler.VHManager;
import com.raizlabs.android.viewholderinflater.core.VHMethod;
import com.squareup.javawriter.JavaWriter;

import java.io.IOException;

import javax.lang.model.element.Element;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
public class MethodWriter implements Writer {

    Element element;

    VHManager manager;

    int resourceId;

    String viewElementName;

    String methodName;

    public MethodWriter(VHManager vhManager, Element element) {
        this.element = element;
        manager = vhManager;

        VHMethod vhMethod = element.getAnnotation(VHMethod.class);
        if(vhMethod != null) {
            resourceId = vhMethod.value();
        }
        methodName = VHDefaultMethodList.getResolvedMethodName(element.getSimpleName().toString());
        viewElementName = VHDefaultMethodList.getResolvedMethodName(element.getSimpleName().toString());
    }

    @Override
    public void write(JavaWriter javaWriter) throws IOException {
        String instantiationStatement = "View %1sview = view.findViewById(";
        if (resourceId == 0) {
            instantiationStatement += String.format("view.getResources().getIdentifier(\"%1s\", \"id\", view.getContext().getPackageName())", viewElementName);
        } else {
            instantiationStatement += resourceId;
        }
        instantiationStatement+=")";
        javaWriter.emitStatement(instantiationStatement, viewElementName);

        javaWriter.beginControlFlow("if (%1sview != null) ", viewElementName);

        VHDefaultMethodList.writeDefaultMethodImpl(javaWriter, viewElementName + "view",
                element.getSimpleName().toString(), methodName);

        javaWriter.endControlFlow();
    }
}
