package com.grosner.viewholderinflater.core;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.grosner.viewholderinflater.core.handler.Handler;
import com.grosner.viewholderinflater.core.writer.InflatableWriter;
import com.squareup.javawriter.JavaWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
public class VHManager {

    private ProcessingEnvironment mEnvironment;

    private List<Handler> mHandlers = Lists.newArrayList();

    private Map<String, InflatableWriter> mInflatableMap = Maps.newHashMap();

    public VHManager(ProcessingEnvironment processingEnvironment) {
        mEnvironment = processingEnvironment;
    }

    public void addHandlers(Handler... handlers) {
        for (Handler handler : handlers) {
            mHandlers.add(handler);
        }
    }

    public void addInflatableWriter(InflatableWriter inflatableWriter) {
        mInflatableMap.put(inflatableWriter.getSourceFileName(), inflatableWriter);
    }

    public void handle(RoundEnvironment roundEnv) {
        for (Handler handler : mHandlers) {
            handler.handle(this, roundEnv);
        }

        if(roundEnv.processingOver()) {
            try {
                JavaWriter javaWriter = new JavaWriter(getFiler().createSourceFile("com.grosner.viewholderinflater.ViewHolderAdapter$HolderAdapter").openWriter());
                javaWriter.emitPackage("com.grosner.viewholderinflater");
                javaWriter.emitImports(Map.class.getCanonicalName(), HashMap.class.getCanonicalName());

                javaWriter.beginType("ViewHolderAdapter$HolderAdapter", "class", Sets.newHashSet(Modifier.FINAL), "ViewHolderAdapter");


                javaWriter.emitField("Map<Class<?>, VHInflatableDefinition>", "mMap",
                        Sets.newHashSet(Modifier.PRIVATE, Modifier.FINAL), "new HashMap<>()");

                javaWriter.beginConstructor(new HashSet<Modifier>());

                Set<String> writers = mInflatableMap.keySet();
                for(String writer: writers) {
                    InflatableWriter inflatableWriter = mInflatableMap.get(writer);
                    javaWriter.emitStatement("mMap.put(%1s.class, new %1s())", inflatableWriter.getFQCN(),
                            inflatableWriter.getSourceFileName());
                }

                javaWriter.endConstructor();

                javaWriter.emitAnnotation(Override.class);
                javaWriter.beginMethod("<VHClass extends VHInflatableDefinition> VHClass", "getVHInflatableDefinition" ,
                        Sets.newHashSet(Modifier.PUBLIC, Modifier.FINAL), "Class<?>", "clazz");

                javaWriter.emitStatement("return (%1s) mMap.get(clazz)", "VHClass");

                javaWriter.endMethod();

                javaWriter.endType();

                javaWriter.close();
                ;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String getPackageName(Element element) {
        return mEnvironment.getElementUtils().getPackageOf(element).toString();
    }

    public Filer getFiler() {
        return mEnvironment.getFiler();
    }
}
