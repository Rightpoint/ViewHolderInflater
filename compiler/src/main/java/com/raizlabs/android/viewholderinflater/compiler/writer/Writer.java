package com.raizlabs.android.viewholderinflater.compiler.writer;

import com.squareup.javawriter.JavaWriter;

import java.io.IOException;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
public interface Writer {

    public void write(JavaWriter javaWriter) throws IOException;
}
