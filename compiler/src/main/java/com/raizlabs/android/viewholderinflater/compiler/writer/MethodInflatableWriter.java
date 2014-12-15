package com.raizlabs.android.viewholderinflater.compiler.writer;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.raizlabs.android.viewholderinflater.compiler.Classes;
import com.raizlabs.android.viewholderinflater.compiler.VHDefaultMethodList;
import com.raizlabs.android.viewholderinflater.compiler.VHManager;
import com.raizlabs.android.viewholderinflater.core.VHMethod;
import com.raizlabs.android.viewholderinflater.core.VHMethodGroup;
import com.squareup.javawriter.JavaWriter;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
public class MethodInflatableWriter extends BaseSourceWriter {

    List<MethodWriter> mMethodList = Lists.newArrayList();

    public MethodInflatableWriter(VHManager vhManager, Element element, String packageName) {
        super(vhManager, element, packageName);
        setDefinitionClassName("$MethodInflatableDefinition");


        List<? extends Element> enclosed = element.getEnclosedElements();
        MethodWriterValidator validator = new MethodWriterValidator();
        for (Element enclosedElement : enclosed) {
            if (enclosedElement.getAnnotation(VHMethod.class) != null
                    || enclosedElement.getAnnotation(VHMethodGroup.class) != null) {
                MethodWriter methodWriter = new MethodWriter(vhManager, enclosedElement, definitionClassName);
                if(validator.validate(manager, methodWriter)) {
                    mMethodList.add(methodWriter);
                }
            }
        }
    }

    @Override
    public void onWriteDefinition(JavaWriter javaWriter) throws IOException {

        javaWriter.emitAnnotation(Override.class);
        javaWriter.beginMethod("void", "connect", Sets.newHashSet(Modifier.PUBLIC, Modifier.FINAL),
                "View", "view", "final " + elementClassName, "inflatable");

        PriorityQueue<MethodWriter> methodWriters = new PriorityQueue<>(new Comparator<MethodWriter>() {
            @Override
            public int compare(MethodWriter o1, MethodWriter o2) {
                boolean is1Oncreate = o1.methodName.equals(VHDefaultMethodList.ON_CREATE);
                boolean is2Oncreate = o2.methodName.equals(VHDefaultMethodList.ON_CREATE);
                return Boolean.compare(is2Oncreate, is1Oncreate);
            }
        });

        methodWriters.addAll(mMethodList);
        for (MethodWriter methodWriter : methodWriters) {
            methodWriter.write(javaWriter);
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
