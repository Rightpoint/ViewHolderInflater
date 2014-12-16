package com.raizlabs.android.viewholderinflater.compiler.writer;

import com.raizlabs.android.viewholderinflater.compiler.VHManager;

/**
 * Author: andrewgrosner
 * Description: Validates each annotation {@link com.raizlabs.android.viewholderinflater.compiler.writer.Writer}
 * (or anything) to ensure the classes are used properly. Also to fail where necessary to give
 * hints to the developer of how to fix the errors.
 */
public interface Validator<ValidatorClass> {

    /**
     * @param vhManager      The manager to retrieve data from
     * @param validatorClass The class to validate.
     * @return true if the validation succeeded, if false multiple errors may be thrown.
     */
    boolean validate(VHManager vhManager, ValidatorClass validatorClass);
}
