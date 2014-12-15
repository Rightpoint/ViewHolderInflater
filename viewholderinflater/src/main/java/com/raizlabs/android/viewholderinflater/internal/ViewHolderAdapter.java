package com.raizlabs.android.viewholderinflater.internal;

import com.raizlabs.android.viewholderinflater.internal.VHInflatableDefinition;

/**
 * Author: andrewgrosner
 * Description:
 */
public abstract class ViewHolderAdapter {

    public abstract <VHClass extends VHInflatableDefinition> VHClass getVHInflatableDefinition(Class<?> clazz);

    public abstract <VHMethodClass extends VHMethodInflatableDefinition> VHMethodClass getVHMethodInflatableDefinition(Class<?> clazz);
}
