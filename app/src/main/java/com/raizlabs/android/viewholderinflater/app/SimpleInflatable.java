package com.raizlabs.android.viewholderinflater.app;

import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import com.raizlabs.android.viewholderinflater.core.VHInflatable;
import com.raizlabs.android.viewholderinflater.core.VHView;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
@VHInflatable
public class SimpleInflatable {

    @VHView(value = R.id.textView, required = true)
    TextView textView;

    @VHView
    CheckBox checkBox;

    @VHView
    ListView listView;

    @VHView
    ExpandableListView expandableListView;
}
