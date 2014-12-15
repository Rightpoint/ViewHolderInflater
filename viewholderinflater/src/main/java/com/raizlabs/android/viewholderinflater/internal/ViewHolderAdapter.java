package com.raizlabs.android.viewholderinflater.internal;

import com.raizlabs.android.viewholderinflater.internal.VHInflatableDefinition;

/**
 * Author: andrewgrosner
 * Description:
 */
public abstract class ViewHolderAdapter {

    public abstract <VHClass extends VHInflatableDefinition> VHClass getVHInflatableDefinition(Class<?> clazz);
}
