package com.raizlabs.android.viewholderinflater.compiler.handler;

import com.raizlabs.android.viewholderinflater.compiler.writer.InflatableWriterValidator;
import com.raizlabs.android.viewholderinflater.compiler.writer.Validator;
import com.raizlabs.android.viewholderinflater.core.VHInflatable;
import com.raizlabs.android.viewholderinflater.compiler.VHManager;
import com.raizlabs.android.viewholderinflater.compiler.writer.InflatableWriter;
import com.squareup.javawriter.JavaWriter;

import java.io.IOException;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;

/**
 * Author: andrewgrosner
 * Description: Handles writing of {@link com.raizlabs.android.viewholderinflater.core.VHInflatable}
 * item definitions.
 */
public class InflatableHandler extends BaseHandler<InflatableWriter> {

    public InflatableHandler() {
        super(VHInflatable.class);
    }

    @Override
    protected Validator<InflatableWriter> newValidator() {
        return new InflatableWriterValidator();
    }

    @Override
    public void onProcessElement(VHManager vhManager, RoundEnvironment roundEnvironment, Element element) {
        String packageName = vhManager.getPackageName(element);
        InflatableWriter inflatableWriter = new InflatableWriter(vhManager, element, packageName);
        if(validate(vhManager, inflatableWriter)) {
            try {
                JavaWriter javaWriter = new JavaWriter(vhManager.getFiler()
                        .createSourceFile(inflatableWriter.getSourceFileName()).openWriter());
                inflatableWriter.write(javaWriter);

                vhManager.addInflatableWriter(inflatableWriter);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
