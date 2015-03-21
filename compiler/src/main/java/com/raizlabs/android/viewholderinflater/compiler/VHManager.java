package com.raizlabs.android.viewholderinflater.compiler;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.raizlabs.android.viewholderinflater.compiler.handler.Handler;
import com.raizlabs.android.viewholderinflater.compiler.writer.InflatableWriter;
import com.raizlabs.android.viewholderinflater.compiler.writer.MethodInflatableWriter;
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
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

/**
 * Author: andrewgrosner
 * Description: Holds onto created definitions, writes the ViewHolderAdapter that the inflater uses,
 * and wraps around some {@link javax.annotation.processing.ProcessingEnvironment} methods.
 */
public class VHManager {

    private ProcessingEnvironment mEnvironment;

    private List<Handler> mHandlers = Lists.newArrayList();

    private Map<String, InflatableWriter> mInflatableMap = Maps.newHashMap();

    private Map<String, MethodInflatableWriter> mMethodInflatableMap = Maps.newHashMap();

    private Map<String, List<String>> mInflatableNameList = Maps.newHashMap();

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

    public void addMethodInflatableWriter(MethodInflatableWriter methodInflatableWriter) {
        mMethodInflatableMap.put(methodInflatableWriter.getSourceFileName(), methodInflatableWriter);
    }

    public void addInflatableName(String inflatableName, String viewName) {
        List<String> nameList = mInflatableNameList.get(inflatableName);
        if(nameList == null) {
            nameList = Lists.newArrayList();
            mInflatableNameList.put(inflatableName, nameList);
        }

        if(!nameList.contains(viewName)) {
            nameList.add(viewName);
        }
    }

    public boolean hasInflatableName(String inflatableName, String viewName) {
        return (mInflatableNameList.get(inflatableName) != null && mInflatableNameList.get(inflatableName).contains(viewName));
    }

    /**
     * Loops through all {@link com.raizlabs.android.viewholderinflater.compiler.handler.Handler},
     * and at the end of processing, it creates the ViewHolderAdapter$HolderAdapter to interface between
     * the ViewHolderInflater and the generated code from here.
     * @param roundEnv
     */
    public void handle(RoundEnvironment roundEnv) {
        for (Handler handler : mHandlers) {
            handler.handle(this, roundEnv);
        }

        if(roundEnv.processingOver()) {
            try {
                JavaWriter javaWriter = new JavaWriter(getFiler().createSourceFile(Classes.VH_PACKAGE_NAME + ".ViewHolderAdapter$HolderAdapter").openWriter());
                javaWriter.emitPackage(Classes.VH_PACKAGE_NAME);
                javaWriter.emitImports(Map.class.getCanonicalName(), HashMap.class.getCanonicalName(),
                        Classes.VH_ADAPTER, Classes.VH_INFLATABLE_DEFINITION, Classes.VH_METHOD_INFLATABLE_DEFINITION);

                javaWriter.beginType("ViewHolderAdapter$HolderAdapter", "class", Sets.newHashSet(Modifier.FINAL), "ViewHolderAdapter");


                javaWriter.emitField("Map<Class<?>, VHInflatableDefinition>", "mMap",
                        Sets.newHashSet(Modifier.PRIVATE, Modifier.FINAL), "new HashMap<>()");
                javaWriter.emitField("Map<Class<?>, VHMethodInflatableDefinition>", "mMethodMap",
                        Sets.newHashSet(Modifier.PRIVATE, Modifier.FINAL), "new HashMap<>()");

                javaWriter.beginConstructor(new HashSet<Modifier>());

                Set<String> writers = mInflatableMap.keySet();
                for(String writer: writers) {
                    InflatableWriter inflatableWriter = mInflatableMap.get(writer);
                    javaWriter.emitStatement("mMap.put(%1s.class, new %1s())", inflatableWriter.getFQCN(),
                            inflatableWriter.getSourceFileName());
                }

                writers = mMethodInflatableMap.keySet();
                for(String writer: writers) {
                    MethodInflatableWriter methodInflatableWriter = mMethodInflatableMap.get(writer);
                    javaWriter.emitStatement("mMethodMap.put(%1s.class, new %1s())", methodInflatableWriter.getFQCN(),
                            methodInflatableWriter.getSourceFileName());
                }

                javaWriter.endConstructor();

                javaWriter.emitAnnotation(Override.class);
                javaWriter.beginMethod("<VHClass extends VHInflatableDefinition> VHClass", "getVHInflatableDefinition" ,
                        Sets.newHashSet(Modifier.PUBLIC, Modifier.FINAL), "Class<?>", "clazz");

                javaWriter.emitStatement("return (%1s) mMap.get(clazz)", "VHClass");

                javaWriter.endMethod();

                javaWriter.emitEmptyLine().emitAnnotation(Override.class);
                javaWriter.beginMethod("<VHMethodClass extends VHMethodInflatableDefinition> VHMethodClass", "getVHMethodInflatableDefinition",
                        Sets.newHashSet(Modifier.PUBLIC, Modifier.FINAL), "Class<?>", "clazz");
                javaWriter.emitStatement("return (%1s) mMethodMap.get(clazz)", "VHMethodClass");
                javaWriter.endMethod();

                javaWriter.endType();

                javaWriter.close();
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

    public void logError(String s, Object...args) {
        mEnvironment.getMessager().printMessage(Diagnostic.Kind.ERROR, String.format(s, args));
    }

    public Elements getElements() {
        return mEnvironment.getElementUtils();
    }
}
