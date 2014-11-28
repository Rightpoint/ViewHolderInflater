package com.grosner.viewholderinflater.core;

import com.google.auto.service.AutoService;
import com.google.common.collect.Sets;
import com.grosner.viewholderinflater.core.handler.InflatableHandler;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

@AutoService(Processor.class)
public class VHProcessor extends AbstractProcessor{

    private VHManager mManager;

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Sets.newHashSet(VHInflatable.class.getName(), VHMethod.class.getName(), VHView.class.getName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mManager = new VHManager(processingEnv);
        mManager.addHandlers(new InflatableHandler());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        mManager.handle(roundEnv);
        return false;
    }
}
