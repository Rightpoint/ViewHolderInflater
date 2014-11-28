package com.grosner.viewholderinflater.core.handler;

import com.grosner.viewholderinflater.core.VHManager;

import java.lang.annotation.Annotation;
import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;

/**
 * Author: andrewgrosner
 * Description:
 */
public abstract class BaseHandler implements Handler {

    private Class<? extends Annotation> mAnnotationClass;

    public BaseHandler(Class<? extends Annotation> annotationClass) {
        mAnnotationClass = annotationClass;
    }

    @Override
    public void handle(VHManager vhManager, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(mAnnotationClass);
        if(elements != null && !elements.isEmpty()) {
            for(Element element: elements) {
                onProcessElement(vhManager, roundEnvironment, element);
            }
        }
    }

    public abstract void onProcessElement(VHManager vhManager, RoundEnvironment roundEnvironment, Element element);
}
