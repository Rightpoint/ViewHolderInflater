package com.raizlabs.android.viewholderinflater.compiler.writer;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.raizlabs.android.viewholderinflater.compiler.Classes;
import com.raizlabs.android.viewholderinflater.compiler.VHManager;
import com.raizlabs.android.viewholderinflater.core.VHInflatableViewHolder;
import com.raizlabs.android.viewholderinflater.core.VHView;
import com.squareup.javawriter.JavaWriter;

import java.io.IOException;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;

/**
 * Author: andrewgrosner
 * Description: Writes an $InflatableDefinition file that contains code that injects views into a view holder.
 */
public class InflatableWriter extends BaseSourceWriter {

    public List<ViewWriter> mViews = Lists.newArrayList();

    boolean hasDefaultConstructor = false;

    public InflatableWriter(VHManager vhManager, Element element, String packageName) {
        super(vhManager, element, packageName);
        setDefinitionClassName("$InflatableDefinition");

        List<? extends Element> elements = element.getEnclosedElements();
        ViewWriterValidator viewWriterValidator = new ViewWriterValidator();
        for (Element innerElement : elements) {
            if (innerElement.getAnnotation(VHView.class) != null
                    || innerElement.getAnnotation(VHInflatableViewHolder.class) != null) {
                ViewWriter viewWriter = new ViewWriter(vhManager, innerElement);
                if (viewWriterValidator.validate(manager, viewWriter)) {
                    mViews.add(viewWriter);
                }
            } else if (innerElement.getKind() == ElementKind.CONSTRUCTOR
                    && !innerElement.getModifiers().contains(Modifier.PRIVATE)) {
                ExecutableElement executableElement = ((ExecutableElement) innerElement);
                if (executableElement.getParameters().isEmpty()) {
                    hasDefaultConstructor = true;
                }
            }
        }

    }

    @Override
    public void onWriteDefinition(JavaWriter javaWriter) throws IOException {

        if (hasDefaultConstructor) {
            javaWriter.emitAnnotation(Override.class);
            javaWriter.beginMethod(elementClassName, "newInstance", Sets.newHashSet(Modifier.PUBLIC, Modifier.FINAL));
            javaWriter.emitStatement("return new %1s()", elementClassName);
            javaWriter.endMethod();
        }

        javaWriter.emitEmptyLine();
        javaWriter.emitAnnotation(Override.class);
        javaWriter.beginMethod("void", "inflate", Sets.newHashSet(Modifier.PUBLIC, Modifier.FINAL),
                "View", "view", elementClassName, "inflatable");
        for (ViewWriter viewWriter : mViews) {
            viewWriter.write(javaWriter);
        }

        javaWriter.endMethod();
    }

    @Override
    protected String[] getImports() {
        return new String[]{
                Classes.VIEW,
                Classes.VH_UTILS
        };
    }

    @Override
    public String getExtendsClassName() {
        return Classes.VH_INFLATABLE_DEFINITION + "<" + elementClassName + ">";
    }
}
