package com.grosner.viewholderinflater.app;

import android.widget.CheckBox;
import android.widget.TextView;

import com.grosner.viewholderinflater.core.VHInflatable;
import com.grosner.viewholderinflater.core.VHView;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
@VHInflatable
public class SimpleInflatable {

    @VHView(R.id.textView)
    TextView textView;

    @VHView
    CheckBox checkBox;
}
