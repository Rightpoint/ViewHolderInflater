package com.grosner.viewholderinflater.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: andrewgrosner
 * Description: Marks a field within a {@link com.grosner.viewholderinflater.core.VHInflatable}
 * as being another {@link com.grosner.viewholderinflater.core.VHInflatable}.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface VHInflatableViewHolder {

    /**
     * Specifies the id of the container this field belongs to.
     * @return If 0, the field name is used in "R.id.fieledName", else the specified id int value is used.
     */
    int value() default 0;
}
