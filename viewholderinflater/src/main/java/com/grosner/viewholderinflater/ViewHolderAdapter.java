package com.grosner.viewholderinflater;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
public abstract class ViewHolderAdapter {

    public abstract <VHClass extends VHInflatableDefinition> VHClass getVHInflatableDefinition(Class<?> clazz);
}
