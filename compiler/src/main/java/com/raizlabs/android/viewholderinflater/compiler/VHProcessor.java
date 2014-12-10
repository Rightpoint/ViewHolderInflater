package com.raizlabs.android.viewholderinflater.compiler;

import com.google.auto.service.AutoService;
import com.google.common.collect.Sets;
import com.raizlabs.android.viewholderinflater.core.VHInflatable;
import com.raizlabs.android.viewholderinflater.core.VHInflatableViewHolder;
import com.raizlabs.android.viewholderinflater.core.VHMethod;
import com.raizlabs.android.viewholderinflater.core.VHMethodInflatable;
import com.raizlabs.android.viewholderinflater.core.VHView;
import com.raizlabs.android.viewholderinflater.compiler.handler.InflatableHandler;
import com.raizlabs.android.viewholderinflater.compiler.handler.MethodInflatableHandler;

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
        return Sets.newHashSet(VHInflatable.class.getName(), VHMethod.class.getName(),
                VHView.class.getName(), VHMethodInflatable.class.getName(),
                VHInflatableViewHolder.class.getName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mManager = new VHManager(processingEnv);
        mManager.addHandlers(new InflatableHandler(), new MethodInflatableHandler());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        mManager.handle(roundEnv);
        return false;
    }
}
