package com.raizlabs.android.viewholderinflater.compiler.writer;

import com.raizlabs.android.viewholderinflater.compiler.VHDefaultMethodList;
import com.raizlabs.android.viewholderinflater.compiler.VHManager;
import com.raizlabs.android.viewholderinflater.compiler.VHUtils;
import com.raizlabs.android.viewholderinflater.core.VHMethod;
import com.raizlabs.android.viewholderinflater.core.VHMethodGroup;
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

    int[] resourceIds;

    String viewElementName;

    String methodName;

    String methodInflatableName;

    boolean required;

    boolean isMethodGroup = false;

    public MethodWriter(VHManager vhManager, Element element, String methodInflatableName) {
        this.element = (ExecutableElement) element;
        this.methodInflatableName = methodInflatableName;
        manager = vhManager;

        VHMethod vhMethod = element.getAnnotation(VHMethod.class);
        if(vhMethod != null) {
            resourceId = vhMethod.value();
            required = vhMethod.required();
        } else {
            VHMethodGroup vhMethodGroup = element.getAnnotation(VHMethodGroup.class);
            if(vhMethodGroup != null) {
                required = vhMethodGroup.required();
                resourceIds = vhMethodGroup.value();
                isMethodGroup = true;
            }
        }
        viewElementName = VHDefaultMethodList.getResolvedMethodName(element.getSimpleName().toString());
        methodName = VHDefaultMethodList.getMethodName(element.getSimpleName().toString());
    }

    @Override
    public void write(JavaWriter javaWriter) throws IOException {
        if(!isMethodGroup) {
            if (!manager.hasInflatableName(methodInflatableName, viewElementName)) {
                javaWriter.emitStatement( "View %1sview = %1s", viewElementName, getInstantiationStatement(viewElementName, resourceId));
                manager.addInflatableName(methodInflatableName, viewElementName);
            }

            javaWriter.beginControlFlow("if (%1sview != null) ", viewElementName);

            VHDefaultMethodList.writeDefaultMethodImpl(javaWriter, viewElementName + "view",
                    element, methodName);

            javaWriter.endControlFlow();
        } else {
            // method group
            javaWriter.emitEmptyLine().emitSingleLineComment("Begin MethodGroup for %1s", viewElementName);
            javaWriter.emitStatement("View %1sview", viewElementName);
            if(!methodName.equals(VHDefaultMethodList.ON_CREATE)) {
                javaWriter.emitStatement("%1s %1s%1s = %1s", VHDefaultMethodList.getMethodCreation(methodName),
                        viewElementName, methodName, VHDefaultMethodList.getMethodImpl(viewElementName, element, methodName));
            }
            for(int resourceId: resourceIds) {
                javaWriter.emitStatement("%1sview = %1s", viewElementName, getInstantiationStatement(viewElementName, resourceId));
                javaWriter.beginControlFlow("if (%1sview != null) ", viewElementName);


                VHDefaultMethodList.writeSetterMethod(javaWriter, viewElementName + "view", element, methodName,
                        viewElementName + methodName);

                // TODO: connect each to method
                //VHDefaultMethodList.writeDefaultMethodImpl(javaWriter, viewElementName + "view",
                        //element, methodName);

                javaWriter.endControlFlow();
            }
            javaWriter.emitEmptyLine();
        }
    }

    protected String getInstantiationStatement(String viewElementName, int resourceId) {
        String instantiationStatement = "VHUtils.findViewById(view, ";
        if (resourceId == 0) {
            instantiationStatement += String.format("view.getResources().getIdentifier(\"%1s\", \"id\", view.getContext().getPackageName())", viewElementName);
        } else {
            instantiationStatement += resourceId;
        }
        instantiationStatement += ",%1s)";
        return String.format(instantiationStatement, String.valueOf(required));
    }
}
