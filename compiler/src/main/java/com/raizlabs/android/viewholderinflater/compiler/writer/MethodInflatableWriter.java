package com.raizlabs.android.viewholderinflater.compiler.writer;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.raizlabs.android.viewholderinflater.compiler.Classes;
import com.raizlabs.android.viewholderinflater.compiler.VHManager;
import com.raizlabs.android.viewholderinflater.core.VHMethod;
import com.squareup.javawriter.JavaWriter;

import java.io.IOException;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
public class MethodInflatableWriter extends BaseSourceWriter {

    private List<MethodWriter> mMethodList = Lists.newArrayList();

    public MethodInflatableWriter(VHManager vhManager, Element element, String packageName) {
        super(vhManager, element, packageName);
        setDefinitionClassName("$MethodInflatableDefinition");


        List<? extends Element> enclosed = element.getEnclosedElements();
        for(Element enclosedElement : enclosed) {
            if(enclosedElement.getAnnotation(VHMethod.class) != null) {
                mMethodList.add(new MethodWriter(vhManager, enclosedElement));
            }
        }
    }

    @Override
    public void onWriteDefinition(JavaWriter javaWriter) throws IOException {

        javaWriter.emitAnnotation(Override.class);
        javaWriter.beginMethod("void", "connect", Sets.newHashSet(Modifier.PUBLIC, Modifier.FINAL),
                "View", "view", elementClassName, "inflatable");

        for(MethodWriter methodWriter: mMethodList) {
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
        return new String[] {
            Classes.VIEW
        };
    }
}
