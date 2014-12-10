package com.raizlabs.android.viewholderinflater.app;

import android.widget.CheckBox;
import android.widget.TextView;

import com.grosner.viewholderinflater.app.R;
import com.raizlabs.android.viewholderinflater.core.VHInflatable;
import com.raizlabs.android.viewholderinflater.core.VHView;

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
