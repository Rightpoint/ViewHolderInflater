package com.raizlabs.android.viewholderinflater.compiler.writer;

import com.raizlabs.android.viewholderinflater.compiler.VHDefaultMethodList;
import com.raizlabs.android.viewholderinflater.compiler.VHManager;
import com.raizlabs.android.viewholderinflater.core.VHMethod;
import com.raizlabs.android.viewholderinflater.core.VHMethodGroup;
import com.squareup.javawriter.JavaWriter;

import java.io.IOException;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;

/**
 * Author: andrewgrosner
 * Description: Writes the Method definition for a {@link com.raizlabs.android.viewholderinflater.core.VHMethod}
 * or {@link com.raizlabs.android.viewholderinflater.core.VHMethodGroup}
 */
public class MethodWriter {

    /**
     * The method itself
     */
    ExecutableElement element;

    /**
     * The manager to reference
     */
    VHManager manager;

    /**
     * Resource id contained in the annotation
     */
    int resourceId;

    /**
     * Resource ids contained in the {@link com.raizlabs.android.viewholderinflater.core.VHMethodGroup}
     */
    int[] resourceIds;

    /**
     * Name of the postfix of the method that it corresponds to (without the method prefix).
     */
    String viewElementName;

    /**
     * The name of the method from the {@link com.raizlabs.android.viewholderinflater.compiler.VHDefaultMethodList}
     * that this corresponds to.
     */
    String methodName;

    /**
     * Used to reference the file this definition belongs to
     */
    String methodInflatableName;

    /**
     * from the {@link com.raizlabs.android.viewholderinflater.core.VHMethod#required()}
     */
    boolean required;

    /**
     * If this element is a {@link com.raizlabs.android.viewholderinflater.core.VHMethodGroup} or not
     */
    boolean isMethodGroup = false;

    /**
     * Constructs new instance
     *
     * @param vhManager            The manager
     * @param element              The element of this writer to reference
     * @param methodInflatableName The name of the file that this definition gets written in
     */
    public MethodWriter(VHManager vhManager, Element element, String methodInflatableName) {
        this.element = (ExecutableElement) element;
        this.methodInflatableName = methodInflatableName;
        manager = vhManager;

        VHMethod vhMethod = element.getAnnotation(VHMethod.class);
        if (vhMethod != null) {
            resourceId = vhMethod.value();
            required = vhMethod.required();
        } else {
            VHMethodGroup vhMethodGroup = element.getAnnotation(VHMethodGroup.class);
            if (vhMethodGroup != null) {
                required = vhMethodGroup.required();
                resourceIds = vhMethodGroup.value();
                isMethodGroup = true;
            }
        }
        viewElementName = VHDefaultMethodList.getResolvedMethodName(element.getSimpleName().toString());
        methodName = VHDefaultMethodList.getMethodName(element.getSimpleName().toString());
    }

    public void write(JavaWriter javaWriter, boolean beginControl, boolean endControl) throws IOException {
        if (!isMethodGroup) {
            if (!manager.hasInflatableName(methodInflatableName, viewElementName)) {
                javaWriter.emitStatement("View %1sview = %1s", viewElementName, getInstantiationStatement(viewElementName, resourceId));
                manager.addInflatableName(methodInflatableName, viewElementName);

            }


            if(beginControl) {
                javaWriter.beginControlFlow("if (%1sview != null) ", viewElementName);
            }

            VHDefaultMethodList.writeDefaultMethodImpl(javaWriter, viewElementName + "view",
                    element, methodName);

            if(endControl) {
                javaWriter.endControlFlow();
            }
        } else {
            // method group
            javaWriter.emitEmptyLine().emitSingleLineComment("Begin MethodGroup for %1s", viewElementName);
            javaWriter.emitStatement("View %1sview", viewElementName);
            if (!methodName.equals(VHDefaultMethodList.ON_CREATE)) {
                javaWriter.emitStatement("%1s %1s%1s = %1s", VHDefaultMethodList.getMethodCreation(methodName),
                        viewElementName, methodName, VHDefaultMethodList.getMethodImpl(viewElementName, element, methodName));
            }
            for (int resourceId : resourceIds) {
                javaWriter.emitStatement("%1sview = %1s", viewElementName, getInstantiationStatement(viewElementName, resourceId));
                javaWriter.beginControlFlow("if (%1sview != null) ", viewElementName);


                VHDefaultMethodList.writeSetterMethod(javaWriter, viewElementName + "view", element, methodName,
                        viewElementName + methodName);

                javaWriter.endControlFlow();
            }
            javaWriter.emitEmptyLine();
        }
    }

    /**
     * Returns the statement to find the view before writing its method connecting statement.
     *
     * @param viewElementName The name of element. Used to either identifier if the resourceId is 0
     * @param resourceId      The id of the resource this method points to. If 0, we use the viewElementName to find the
     *                        resource id.
     * @return The statement string on finding the view by id based on the parameters passed.
     */
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
