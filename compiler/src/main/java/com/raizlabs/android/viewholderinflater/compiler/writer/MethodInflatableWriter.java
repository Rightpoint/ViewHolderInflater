package com.raizlabs.android.viewholderinflater.compiler.writer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.raizlabs.android.viewholderinflater.compiler.Classes;
import com.raizlabs.android.viewholderinflater.compiler.VHDefaultMethodList;
import com.raizlabs.android.viewholderinflater.compiler.VHManager;
import com.raizlabs.android.viewholderinflater.core.VHMethod;
import com.raizlabs.android.viewholderinflater.core.VHMethodGroup;
import com.squareup.javawriter.JavaWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * Author: andrewgrosner
 * Description: Writes a {@link com.raizlabs.android.viewholderinflater.core.VHMethodInflatable} definition
 * file.
 */
public class MethodInflatableWriter extends BaseSourceWriter {

    Map<String, List<MethodWriter>> mMethodMap = Maps.newHashMap();

    List<MethodWriter> mOnCreateList = Lists.newArrayList();

    /**
     * Constructs new instance
     *
     * @param vhManager   The manager
     * @param element     The element that has a {@link com.raizlabs.android.viewholderinflater.core.VHInflatable}
     * @param packageName The package name to write to
     */
    public MethodInflatableWriter(VHManager vhManager, Element element, String packageName) {
        super(vhManager, element, packageName);
        setDefinitionClassName("$MethodInflatableDefinition");


        List<? extends Element> enclosed = vhManager.getElements().getAllMembers(((TypeElement) element));
        MethodWriterValidator validator = new MethodWriterValidator();
        for (Element enclosedElement : enclosed) {
            if (enclosedElement.getAnnotation(VHMethod.class) != null
                    || enclosedElement.getAnnotation(VHMethodGroup.class) != null) {
                MethodWriter methodWriter = new MethodWriter(vhManager, enclosedElement, definitionClassName);
                if (validator.validate(manager, methodWriter)) {
                    if(methodWriter.methodName.equals(VHDefaultMethodList.ON_CREATE)) {
                        mOnCreateList.add(methodWriter);
                    } else {
                        List<MethodWriter> methodList = mMethodMap.get(methodWriter.viewElementName);
                        if (methodList == null) {
                            methodList = new ArrayList<>();
                            mMethodMap.put(methodWriter.viewElementName, methodList);
                        }
                        methodList.add(methodWriter);
                    }
                }
            }
        }
    }

    @Override
    public void onWriteDefinition(JavaWriter javaWriter) throws IOException {

        javaWriter.emitAnnotation(Override.class);
        javaWriter.beginMethod("void", "connect", Sets.newHashSet(Modifier.PUBLIC, Modifier.FINAL),
                "View", "view", "final " + elementClassName, "inflatable");

        for (MethodWriter methodWriter : mOnCreateList) {
            methodWriter.write(javaWriter, true, true);
        }

        Set<String> methodSet = mMethodMap.keySet();
        for(String method: methodSet) {
            List<MethodWriter> methodWriters = mMethodMap.get(method);
            for(int i = 0; i < methodWriters.size(); i++) {
                MethodWriter methodWriter = methodWriters.get(i);
                methodWriter.write(javaWriter, i==0, i == methodWriters.size()-1);
            }
        }

        javaWriter.endMethod();
    }

    @Override
    public String getExtendsClassName() {
        return Classes.VH_METHOD_INFLATABLE_DEFINITION + "<" + elementClassName + ">";
    }

    @Override
    protected String[] getImports() {
        return new String[]{
                Classes.VIEW,
                Classes.VH_UTILS
        };
    }
}
