package com.raizlabs.android.viewholderinflater.compiler.writer;

import com.raizlabs.android.viewholderinflater.compiler.Classes;
import com.raizlabs.android.viewholderinflater.compiler.VHManager;
import com.raizlabs.android.viewholderinflater.core.VHInflatableViewHolder;
import com.raizlabs.android.viewholderinflater.core.VHView;
import com.squareup.javawriter.JavaWriter;

import java.io.IOException;

import javax.lang.model.element.Element;

/**
 * Author: andrewgrosner
 * Description: Writes the definition/instantiation statemenet
 * for a {@link com.raizlabs.android.viewholderinflater.core.VHView} or a {@link com.raizlabs.android.viewholderinflater.core.VHInflatableViewHolder}
 */
public class ViewWriter implements Writer {

    /**
     * The element of the view
     */
    Element element;

    /**
     * The name of the type that this element is
     */
    String elementClassName;

    /**
     * Package name to write the view to
     */
    String packageName;

    /**
     * The resource id of the {@link com.raizlabs.android.viewholderinflater.core.VHView#value()}
     */
    int resourceId;

    /**
     * Whether this view is a {@link com.raizlabs.android.viewholderinflater.core.VHInflatableViewHolder}
     */
    boolean isInflatableView = false;

    /**
     * If true, we will throw an exception if the view is not found.
     */
    boolean required = false;

    /**
     * Constructs new instance of the writer.
     *
     * @param manager The manager
     * @param element The element to use
     */
    public ViewWriter(VHManager manager, Element element) {
        this.element = element;
        packageName = manager.getPackageName(element);
        elementClassName = element.asType().toString();

        VHView vhView = element.getAnnotation(VHView.class);
        if (vhView != null) {
            resourceId = vhView.value();
            required = vhView.required();
        } else {
            VHInflatableViewHolder vhInflatableViewHolder = element.getAnnotation(VHInflatableViewHolder.class);
            if (vhInflatableViewHolder != null) {
                resourceId = vhInflatableViewHolder.value();
                isInflatableView = true;
            }
        }
    }

    @Override
    public void write(JavaWriter javaWriter) throws IOException {
        String elementName = element.getSimpleName().toString();

        String statement = "inflatable.%1s = ";
        if (!isInflatableView) {
            statement += "(%1s) VHUtils.findViewById(view, ";
        } else {
            statement += Classes.VH_INFLATER + ".inflate(%1s.class, view, ";
        }
        if (resourceId == 0) {
            statement += String.format("view.getResources().getIdentifier(\"%1s\", \"id\", view.getContext().getPackageName())", elementName);
        } else {
            statement += resourceId;
        }
        if (!isInflatableView) {
            statement += ",";
            statement += (required ? "true" : "false");
        }
        statement += ")";


        javaWriter.emitStatement(statement, elementName,
                elementClassName);
    }
}
