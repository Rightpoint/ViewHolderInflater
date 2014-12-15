package com.raizlabs.android.viewholderinflater.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: andrewgrosner
 * Description: Replaces a {@link com.raizlabs.android.viewholderinflater.core.VHMethod} when we
 * want to have multiple views associated with a method.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface VHMethodGroup {

    /**
     * @return A list of valid resource integer Ids that will be connected to the method.
     */
    int[] value();

    /**
     * @return if true, we throw an {@link com.raizlabs.android.viewholderinflater.core.VHViewNotFoundException}
     *  when any of the views are not found.
     */
    boolean required() default false;
}
