package com.grosner.viewholderinflater.core.writer;

import com.grosner.viewholderinflater.core.VHManager;
import com.grosner.viewholderinflater.core.VHView;
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


    public ViewWriter(VHManager manager, Element element) {
        this.element = element;
        packageName = manager.getPackageName(element);
        elementClassName = element.asType().toString();

        VHView vhView = element.getAnnotation(VHView.class);
        resourceId = vhView.value();
    }

    @Override
    public void write(JavaWriter javaWriter) throws IOException {
        String elementName = element.getSimpleName().toString();

        Object resource = resourceId;
        String statement = "inflatable.%1s = (%1s) view.findViewById(";
        if(resourceId == 0) {
            resource = String.format("view.getResources().getIdentifier(\"%1s\", \"id\", view.getContext().getPackageName())", elementName);
            statement+="%1s";
        } else {
            statement+="%1d";
        }
        statement+=")";


        javaWriter.emitStatement(statement, elementName,
                elementClassName, resource);
    }
}
