package com.grosner.viewholderinflater.core.writer;

import com.google.common.collect.Sets;
import com.grosner.viewholderinflater.core.VHManager;
import com.squareup.javawriter.JavaWriter;

import java.io.IOException;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
public abstract class BaseSourceWriter implements Writer {

    protected VHManager manager;

    Element element;

    public String elementClassName;

    String packageName;

    String definitionClassName;

    public BaseSourceWriter(VHManager vhManager, Element element, String packageName) {
        manager = vhManager;

        this.element = element;
        this.packageName = packageName;
        elementClassName = element.getSimpleName().toString();
    }

    protected void setDefinitionClassName(String definitionClassName) {
        this.definitionClassName = elementClassName + definitionClassName;
    }

    public String getSourceFileName() {
        return packageName + "." + definitionClassName;
    }

    public String getFQCN() {
        return packageName + "." + elementClassName;
    }

    @Override
    public void write(JavaWriter javaWriter) throws IOException {
        javaWriter.emitPackage(packageName);
        javaWriter.emitImports(getImports());
        javaWriter.beginType(definitionClassName, "class",
                Sets.newHashSet(Modifier.PUBLIC, Modifier.FINAL), getExtendsClassName(),
                getImplementingClasses());

        onWriteDefinition(javaWriter);

        javaWriter.endType();
        javaWriter.close();
    }


    protected String[] getImports(){
        return new String[0];
    }

    public String getExtendsClassName() {
        return null;
    }

    public String[] getImplementingClasses() {
        return new String[0];
    }

    public abstract void onWriteDefinition(JavaWriter javaWriter) throws IOException;
}
