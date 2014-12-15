package com.raizlabs.android.viewholderinflater.compiler;

import com.google.common.collect.Lists;
import com.squareup.javawriter.JavaWriter;

import java.io.IOException;
import java.util.List;

import javax.lang.model.element.ExecutableElement;

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

    public static final String ON_CREATE = "onCreate";

    private static List<String> mMethods = Lists.newArrayList(ON_CLICK, ON_ITEM_CLICK, ON_CHECKED_CHANGED,
            ON_TOUCH, ON_LONG_CLICK, ON_CREATE);

    public static String getResolvedMethodName(String methodName) {
        for(String method: mMethods) {
            if(methodName.startsWith(method)) {
                methodName = methodName.replaceFirst(method, "");
                if(methodName.isEmpty()) {
                    methodName = method;
                }
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

    public static void writeDefaultMethodImpl(JavaWriter javaWriter, String viewElementName, ExecutableElement executable, String methodName) throws IOException {
        if(methodName.equals(ON_CLICK)) {
            String methodStatement = VHUtils.getMethodStatement(executable, "v");
            javaWriter.emitStatement(viewElementName + ".setOnClickListener(" +
                    "\nnew %1s() {" +
                        "\n\tpublic void onClick(View v) { " +
                            "\n\t\t%1s.%1s;" +
                        "\n\t}" +
                    "\n})", Classes.ON_CLICK_LISTENER, "inflatable", methodStatement);
        } else if(methodName.equals(ON_ITEM_CLICK)) {
            String methodStatement = VHUtils.getMethodStatement(executable, "parent", "v", "position", "id");
            javaWriter.emitStatement("((%1s)" + viewElementName + ").setOnItemClickListener(" +
                    "\nnew %1s(){" +
                    "\n\tpublic void onItemClick(%1s<?> parent, View v, int position, long id) {" +
                        "\n\t\t%1s.%1s;" +
                    "\n\t}" +
                    "\n})", Classes.ADAPTER_VIEW, Classes.ON_ITEM_CLICK_LISTENER,
                    Classes.ADAPTER_VIEW, "inflatable", methodStatement);
        } else if(methodName.equals(ON_CHECKED_CHANGED)) {
            String methodStatement = VHUtils.getMethodStatement(executable, "buttonView", "isChecked");
            javaWriter.emitStatement("((%1s)" + viewElementName + ").setOnCheckedChangeListener(" +
                    "\nnew %1s(){" +
                    "\n\tpublic void onCheckedChanged(%1s buttonView, boolean isChecked) {" +
                        "\n\t\t%1s.%1s;" +
                    "\n\t}" +
                    "\n})", Classes.COMPOUND_BUTTON, Classes.ON_CHECKED_CHANGE_LISTENER,
                    Classes.COMPOUND_BUTTON, "inflatable", methodStatement);
        } else if(methodName.equals(ON_TOUCH)) {
            String methodStatement = VHUtils.getMethodStatement(executable, "v", "event");
            javaWriter.emitStatement(viewElementName + ".setOnTouchListener(" +
                    "\nnew %1s(){" +
                    "\n\tpublic boolean onTouch(View v, %1s event) {" +
                        "\n\t\treturn %1s.%1s;" +
                    "\n\t}" +
                    "\n})", Classes.ON_TOUCH_LISTENER, Classes.MOTION_EVENT,
                    "inflatable", methodStatement);
        } else if(methodName.equals(ON_LONG_CLICK)) {
            String methodStatement = VHUtils.getMethodStatement(executable, "v");
            javaWriter.emitStatement(viewElementName + ".setOnLongClickListener(" +
                    "\nnew %1s(){" +
                    "\n\tpublic boolean onLongClick(View v) {" +
                        "\n\t\treturn %1s.%1s;" +
                    "\n\t}" +
                    "\n})", Classes.ON_LONG_CLICK_LISTENER, "inflatable", methodStatement);
        } else if(methodName.equals(ON_CREATE)) {
            javaWriter.emitStatement("inflatable." + VHUtils.getMethodStatement(executable, viewElementName));
        }
    }
}
