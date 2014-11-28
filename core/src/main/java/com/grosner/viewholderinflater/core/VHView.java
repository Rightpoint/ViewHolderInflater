package com.grosner.viewholderinflater.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: andrewgrosner
 * Description: Marks a field as one that is inflatable.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface VHView {

    /**
     * Can specify an optional ID to be used. If omitted, then the id will be used as "R.id.fieldName"
     * @return The resource ID of the view to use for the field.
     */
    int value() default 0;
}
