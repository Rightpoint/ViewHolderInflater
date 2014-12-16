package com.raizlabs.android.viewholderinflater.internal;

/**
 * Author: andrewgrosner
 * Description: Holds the {@link com.raizlabs.android.viewholderinflater.internal.VHInflatableDefinition}
 * and the {@link com.raizlabs.android.viewholderinflater.internal.VHMethodInflatableDefinition} generated
 * during compile time.
 */
public abstract class ViewHolderAdapter {

    /**
     * @param clazz     Class containing the {@link com.raizlabs.android.viewholderinflater.core.VHInflatable}
     * @param <VHClass>
     * @return The generated definition. Null if not found.
     */
    public abstract <VHClass extends VHInflatableDefinition> VHClass getVHInflatableDefinition(Class<?> clazz);

    /**
     * @param clazz           Class containing the {@link com.raizlabs.android.viewholderinflater.core.VHMethodInflatable}
     * @param <VHMethodClass>
     * @return The generated definition. Null if not found
     */
    public abstract <VHMethodClass extends VHMethodInflatableDefinition> VHMethodClass getVHMethodInflatableDefinition(Class<?> clazz);
}
