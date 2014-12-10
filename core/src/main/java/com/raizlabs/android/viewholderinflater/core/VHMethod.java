package com.raizlabs.android.viewholderinflater.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: andrewgrosner
 * Description: Marks a method as a inflated method that corresponds to a view.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface VHMethod {

    /**
     * Specifies an optional resource ID that the method corresponds to. If omitted, the field referenced
     * will be done "R.id.fieldName"
     * @return The resource ID that this method corresponds to.
     */
    int value() default 0;
}
