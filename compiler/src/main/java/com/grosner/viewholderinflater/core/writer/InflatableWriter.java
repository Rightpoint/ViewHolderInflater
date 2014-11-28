package com.grosner.viewholderinflater.core.writer;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.grosner.viewholderinflater.core.Classes;
import com.grosner.viewholderinflater.core.VHManager;
import com.grosner.viewholderinflater.core.VHView;
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
public class InflatableWriter extends BaseSourceWriter {


    private List<ViewWriter> mViews = Lists.newArrayList();

    public InflatableWriter(VHManager vhManager, Element element, String packageName) {
        super(vhManager, element, packageName);
        setDefinitionClassName("$InflatableDefinition");

        List<? extends Element> elements = element.getEnclosedElements();
        for(Element innerElement: elements) {
            if(innerElement.getAnnotation(VHView.class) != null) {
                mViews.add(new ViewWriter(vhManager, innerElement));
            }
        }
    }

    @Override
    public void onWriteDefinition(JavaWriter javaWriter) throws IOException {
        javaWriter.emitAnnotation(Override.class);
        javaWriter.beginMethod("void", "inflate", Sets.newHashSet(Modifier.PUBLIC, Modifier.FINAL),
                "View", "view", elementClassName, "inflatable");
        for(ViewWriter viewWriter: mViews) {
            viewWriter.write(javaWriter);
        }

        javaWriter.endMethod();
    }

    @Override
    protected String[] getImports() {
        return new String[] {
                Classes.VIEW
        };
    }

    @Override
    public String[] getImplementingClasses() {
        return new String[]{
            Classes.VH_INFLATABLE_DEFINITION + "<" + elementClassName + ">"
        };
    }
}
