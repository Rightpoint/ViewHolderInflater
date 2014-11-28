package com.grosner.viewholderinflater.app;

import android.view.View;
import android.widget.TextView;

import com.grosner.viewholderinflater.core.VHInflatable;
import com.grosner.viewholderinflater.core.VHInflatableViewHolder;
import com.grosner.viewholderinflater.core.VHView;

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
