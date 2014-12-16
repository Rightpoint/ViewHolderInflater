package com.raizlabs.android.viewholderinflater.app;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.raizlabs.android.viewholderinflater.ViewHolderInflater;
import com.raizlabs.android.viewholderinflater.core.VHInflatable;
import com.raizlabs.android.viewholderinflater.core.VHView;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
@VHInflatable
public class ExampleRecyclerVH extends RecyclerView.ViewHolder {

    @VHView(R.id.textView)
    TextView textView;

    @VHView(R.id.checkBox)
    CheckBox checkBox;

    public ExampleRecyclerVH(View itemView) {
        super(itemView);
        ViewHolderInflater.inflate(itemView, this);
    }
}
