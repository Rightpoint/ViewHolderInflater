package com.raizlabs.android.viewholderinflater.compiler;

import com.google.common.collect.Lists;
import com.squareup.javawriter.JavaWriter;

import java.io.IOException;
import java.util.List;

/**
 * Author: andrewgrosner
 * Contributors: { }
 * Description: Describes the default methods to replace method name, connect views to definitions. This
 * is internal usage only.
 */
public class VHDefaultMethodList {

    public static final String ON_CLICK = "onClick";

    public static final String ON_ITEM_CLICK = "onItemClick";

    public static final String ON_CHECKED_CHANGED = "onCheckedChanged";

    public static final String ON_TOUCH = "onTouch";

    public static final String ON_LONG_CLICK = "onLongClick";

    private static List<String> mMethods = Lists.newArrayList(ON_CLICK, ON_ITEM_CLICK, ON_CHECKED_CHANGED,
            ON_TOUCH, ON_LONG_CLICK);

    public static String getResolvedMethodName(String methodName) {
        for(String method: mMethods) {
            if(methodName.startsWith(method)) {
                methodName = methodName.replaceFirst(method, "");
                String firstLetter = methodName.substring(0, 1);
                methodName = methodName.replaceFirst(firstLetter, firstLetter.toLowerCase());
                break;
            }
        }

        return methodName;
    }

    public static String getMethodName(String elementMethodName) {
        String methodName = "";
        for(String method: mMethods) {
            if(elementMethodName.startsWith(method)) {
                methodName = method;
                break;
            }
        }

        return methodName;
    }

    public static void writeDefaultMethodImpl(JavaWriter javaWriter, String viewElementName, String viewMethodName, String methodName) throws IOException {
        if(methodName.equals(ON_CLICK)) {

        } else if(methodName.equals(ON_ITEM_CLICK)) {

        } else if(methodName.equals(ON_CHECKED_CHANGED)) {

        } else if(methodName.equals(ON_TOUCH)) {

        } else if(methodName.equals(ON_LONG_CLICK)) {

        }
    }
}
