package com.raizlabs.android.viewholderinflater.compiler.handler;

import com.raizlabs.android.viewholderinflater.compiler.VHManager;
import com.raizlabs.android.viewholderinflater.compiler.writer.Validator;

import java.lang.annotation.Annotation;
import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;

/**
 * Author: andrewgrosner
 * Description:
 */
public abstract class BaseHandler<ValidatorClass> implements Handler, Validator<ValidatorClass> {

    private Class<? extends Annotation> mAnnotationClass;

    private Validator<ValidatorClass> mValidator;

    protected abstract Validator<ValidatorClass> newValidator();

    public BaseHandler(Class<? extends Annotation> annotationClass) {
        mAnnotationClass = annotationClass;
    }

    @Override
    public boolean validate(VHManager vhManager, ValidatorClass validatorClass) {
        if(mValidator == null) {
            mValidator = newValidator();
        }
        return mValidator.validate(vhManager, validatorClass);
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
