package com.raizlabs.android.viewholderinflater.app;

import android.view.View;
import android.widget.TextView;

import com.grosner.viewholderinflater.app.R;
import com.raizlabs.android.viewholderinflater.core.VHInflatable;
import com.raizlabs.android.viewholderinflater.core.VHInflatableViewHolder;
import com.raizlabs.android.viewholderinflater.core.VHView;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
@VHInflatable
public class ComplexInflatable {


    @VHView(R.id.textView)
    TextView textView;

    @VHInflatableViewHolder
    SimpleInflatable simpleInflatable;

    ComplexInflatable(View dummy) {

    }
}
