package com.raizlabs.android.viewholderinflater.compiler.writer;

import com.raizlabs.android.viewholderinflater.compiler.Classes;
import com.raizlabs.android.viewholderinflater.core.VHInflatableViewHolder;
import com.raizlabs.android.viewholderinflater.compiler.VHManager;
import com.raizlabs.android.viewholderinflater.core.VHView;
import com.squareup.javawriter.JavaWriter;

import java.io.IOException;

import javax.lang.model.element.Element;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
public class ViewWriter implements Writer {

    Element element;

    String elementClassName;

    String packageName;

    int resourceId;

    boolean isInflatableView = false;

    boolean required = false;

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
        if(!isInflatableView) {
            statement+=",";
            statement+=(required?"true" : "false");
        }
        statement += ")";


        javaWriter.emitStatement(statement, elementName,
                elementClassName);
    }
}
