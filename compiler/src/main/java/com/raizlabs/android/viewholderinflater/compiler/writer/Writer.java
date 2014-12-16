package com.raizlabs.android.viewholderinflater.compiler.writer;

import com.squareup.javawriter.JavaWriter;

import java.io.IOException;

/**
 * Author: andrewgrosner
 * Description: Basic interface to write to the {@link com.squareup.javawriter.JavaWriter}
 */
public interface Writer {

    /**
     * Write to the writer
     * @param javaWriter
     * @throws IOException
     */
    public void write(JavaWriter javaWriter) throws IOException;
}
