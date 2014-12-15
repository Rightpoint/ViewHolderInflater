package com.raizlabs.android.viewholderinflater.compiler.handler;

import com.raizlabs.android.viewholderinflater.compiler.VHManager;
import com.raizlabs.android.viewholderinflater.compiler.writer.MethodInflatableWriterValidator;
import com.raizlabs.android.viewholderinflater.compiler.writer.Validator;
import com.raizlabs.android.viewholderinflater.core.VHMethodInflatable;
import com.raizlabs.android.viewholderinflater.compiler.writer.MethodInflatableWriter;
import com.squareup.javawriter.JavaWriter;

import java.io.IOException;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
public class MethodInflatableHandler extends BaseHandler<MethodInflatableWriter> {

    public MethodInflatableHandler() {
        super(VHMethodInflatable.class);
    }

    @Override
    protected Validator<MethodInflatableWriter> newValidator() {
        return new MethodInflatableWriterValidator();
    }

    @Override
    public void onProcessElement(VHManager vhManager, RoundEnvironment roundEnvironment, Element element) {
        String packageName = vhManager.getPackageName(element);
        MethodInflatableWriter methodInflatableWriter = new MethodInflatableWriter(vhManager, element, packageName);
        if(validate(vhManager, methodInflatableWriter)) {
            try {
                JavaWriter javaWriter = new JavaWriter(vhManager.getFiler().createSourceFile(methodInflatableWriter.getSourceFileName()).openWriter());

                methodInflatableWriter.write(javaWriter);
                vhManager.addMethodInflatableWriter(methodInflatableWriter);

                javaWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
