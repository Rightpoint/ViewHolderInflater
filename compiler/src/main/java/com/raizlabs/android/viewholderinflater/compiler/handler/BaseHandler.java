package com.raizlabs.android.viewholderinflater.compiler.handler;

import com.raizlabs.android.viewholderinflater.compiler.VHManager;
import com.raizlabs.android.viewholderinflater.compiler.writer.Validator;

import java.lang.annotation.Annotation;
import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;

/**
 * Author: andrewgrosner
 * Description: Base implementation that loops through annotations from the compiler and enables
 * validation on the {@link ValidatorClass}.
 */
public abstract class BaseHandler<ValidatorClass> implements Handler, Validator<ValidatorClass> {

    private Class<? extends Annotation> mAnnotationClass;

    private Validator<ValidatorClass> mValidator;

    /**
     * @return A new instance of the validator to use
     */
    protected abstract Validator<ValidatorClass> newValidator();

    public BaseHandler(Class<? extends Annotation> annotationClass) {
        mAnnotationClass = annotationClass;
    }

    @Override
    public boolean validate(VHManager vhManager, ValidatorClass validatorClass) {
        if (mValidator == null) {
            mValidator = newValidator();
        }
        return mValidator.validate(vhManager, validatorClass);
    }

    @Override
    public void handle(VHManager vhManager, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(mAnnotationClass);
        if (elements != null && !elements.isEmpty()) {
            for (Element element : elements) {
                onProcessElement(vhManager, roundEnvironment, element);
            }
        }
    }

    /**
     * Called when looping through the annotated elements. Create a {@link com.raizlabs.android.viewholderinflater.compiler.writer.Writer}
     * here and process it.
     *
     * @param vhManager        The manager
     * @param roundEnvironment The environment to retrieve other information or perform certain actions
     * @param element          The element to process
     */
    public abstract void onProcessElement(VHManager vhManager, RoundEnvironment roundEnvironment, Element element);
}
