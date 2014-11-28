package com.grosner.viewholderinflater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
public class ViewHolderInflater {

    private static ViewHolderAdapter viewHolderAdapter;

    public static ViewHolderAdapter getViewHolderAdapter() {
        if(viewHolderAdapter == null) {
            try {
                viewHolderAdapter = (ViewHolderAdapter) Class.forName("com.grosner.viewholderinflater.ViewHolderAdapter$HolderAdapter").newInstance();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        return viewHolderAdapter;
    }

    public static View inflate(Context context, Object inflatable, int layoutResId) {
        ViewGroup root = null;
        if(inflatable instanceof ViewGroup) {
            root = ((ViewGroup) inflatable);
        }
        View view = LayoutInflater.from(context).inflate(layoutResId, root);

        // TODO: connect the view
        injectViews(view, inflatable);

        return view;
    }

    @SuppressWarnings("unchecked")
    protected static void injectViews(View view, Object inflatable) {
        VHInflatableDefinition vhInflatableDefinition = getViewHolderAdapter().getVHInflatableDefinition(inflatable.getClass());
        if(vhInflatableDefinition != null) {
            vhInflatableDefinition.inflate(view, inflatable);
        } else {
            throw new RuntimeException("No VHInflatableDefinition found for: " + inflatable.getClass() + ". Did" +
                    "you forget the @VHInflatable annotation?");
        }
    }
}
