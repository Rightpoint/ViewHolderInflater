package com.raizlabs.android.viewholderinflater.compiler;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description:
 */
public class Classes {

    public static final String VH_PACKAGE_NAME = "com.raizlabs.android.viewholderinflater";

    public static final String INTERNAL_PACKAGE_NAME = VH_PACKAGE_NAME + ".internal";

    public static final String VIEW = "android.view.View";

    public static final String VH_INFLATABLE_DEFINITION = INTERNAL_PACKAGE_NAME + ".VHInflatableDefinition";

    public static final String VH_INFLATER = VH_PACKAGE_NAME + ".ViewHolderInflater";

    public static final String VH_METHOD_INFLATABLE_DEFINITION = INTERNAL_PACKAGE_NAME + ".VHMethodInflatableDefinition";

    public static final String VH_ADAPTER = INTERNAL_PACKAGE_NAME + ".ViewHolderAdapter";

    public static final String VH_UTILS = INTERNAL_PACKAGE_NAME + ".VHUtils";

    public static final String ON_CLICK_LISTENER = VIEW + ".OnClickListener";

    public static final String ADAPTER_VIEW = "android.widget.AdapterView";

    public static final String ON_ITEM_CLICK_LISTENER = ADAPTER_VIEW + ".OnItemClickListener";

    public static final String COMPOUND_BUTTON = "android.widget.CompoundButton";

    public static final String ON_CHECKED_CHANGE_LISTENER = COMPOUND_BUTTON + ".OnCheckedChangeListener";

    public static final String ON_TOUCH_LISTENER = VIEW + ".OnTouchListener";

    public static final String MOTION_EVENT = "android.view.MotionEvent";

    public static final String ON_LONG_CLICK_LISTENER = VIEW + ".OnLongClickListener";
}
