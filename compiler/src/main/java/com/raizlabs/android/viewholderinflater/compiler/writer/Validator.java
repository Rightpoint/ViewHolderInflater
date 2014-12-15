package com.raizlabs.android.viewholderinflater.compiler.writer;

import com.raizlabs.android.viewholderinflater.compiler.VHManager;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
public interface Validator<ValidatorClass> {

    boolean validate(VHManager vhManager, ValidatorClass validatorClass);
}
