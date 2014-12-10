package com.raizlabs.android.viewholderinflater;

import com.raizlabs.android.viewholderinflater.internal.VHInflatableDefinition;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
public abstract class ViewHolderAdapter {

    public abstract <VHClass extends VHInflatableDefinition> VHClass getVHInflatableDefinition(Class<?> clazz);
}
