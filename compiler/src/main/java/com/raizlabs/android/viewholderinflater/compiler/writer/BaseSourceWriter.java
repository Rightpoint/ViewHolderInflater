package com.raizlabs.android.viewholderinflater.compiler.writer;

import com.google.common.collect.Sets;
import com.raizlabs.android.viewholderinflater.compiler.VHManager;
import com.squareup.javawriter.JavaWriter;

import java.io.IOException;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

/**
 * Author: andrewgrosner
 * Description: Base implementation that aids in writing source files.
 */
public abstract class BaseSourceWriter implements Writer {

    /**
     * Manager to help and store data
     */
    protected VHManager manager;

    /**
     * The element of this writer
     */
    Element element;

    /**
     * The simple name of the element
     */
    public String elementClassName;

    /**
     * Package that the element resides in
     */
    String packageName;

    /**
     * The full name of the file = elementClassName + $ + definitionClassName
     */
    String definitionClassName;

    /**
     * Constructs new instance
     *
     * @param vhManager   The manager
     * @param element     Element to process and write from
     * @param packageName The package that this file is written to
     */
    public BaseSourceWriter(VHManager vhManager, Element element, String packageName) {
        manager = vhManager;

        this.element = element;
        this.packageName = packageName;
        elementClassName = element.getSimpleName().toString();
    }

    /**
     * Appends the definitionclass name to the elementClassName to make this file unique
     * @param definitionClassName
     */
    protected void setDefinitionClassName(String definitionClassName) {
        this.definitionClassName = elementClassName + definitionClassName;
    }

    /**
     * @return the fully qualified definitionClassName
     */
    public String getSourceFileName() {
        return packageName + "." + definitionClassName;
    }

    /**
     * @return The fully qualified elementClassName
     */
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

    /**
     * @return the Import FQCNs
     */
    protected String[] getImports() {
        return new String[0];
    }

    /**
     * @return The FQCN of what the file extends
     */
    public String getExtendsClassName() {
        return null;
    }

    /**
     * @return the FQCN of the classes this file implements
     */
    public String[] getImplementingClasses() {
        return new String[0];
    }

    /**
     * Called when to write definition file. The package, imports, type instantiation, type ending, and
     * closing of the {@link com.squareup.javawriter.JavaWriter} are handled by this class already.
     * Just write what you need to here.
     * @param javaWriter
     * @throws IOException
     */
    public abstract void onWriteDefinition(JavaWriter javaWriter) throws IOException;
}
