package com.hyying.hynews.utils;

import android.util.Log;

/**
 * 日志输出控制 (Description)
 */
public class LogUtils {
    /**
     * 是否允许输出log，true表示可以，false表示禁掉
     */

    // isDebug=true的情况在任何情况下都不应该上传到服务器
    private static boolean isDebug = true;

    private static Exception exception;
    private static StackTraceElement traceElement;

    public static void setIsDebug(boolean debug) {
        isDebug = debug;
    }

    public static boolean isDebug() {
        return isDebug;
    }

    /**
     * 以级别为 v 的形式输出LOG
     */
    public static void v(String mTag, String msg) {
        if (isDebug) {
            Log.v(mTag, "" + msg);
        }
    }

    /**
     * 以级别为 d 的形式输出LOG
     */
    public static void d(String mTag, String msg) {
        if (isDebug) {
            Log.d(_FILE_(), getLineMethod() + " " + msg);
        }
    }

    /**
     * 默认带上文件和行数信息
     *
     * @param msg
     */
    public static void d(String msg) {
        if (isDebug) {
            Log.d(_FILE_(), getLineMethod() + " " + msg);
        }
    }

    /**
     * 以级别为 i 的形式输出LOG
     */
    public static void i(String mTag, String msg) {
        if (isDebug) {
            Log.i(mTag, "" + msg);
        }
    }

    /**
     * 以级别为 w 的形式输出LOG
     */
    public static void w(String mTag, String msg) {
        if (isDebug) {
            Log.w(mTag, "" + msg);
        }
    }

    /**
     * 以级别为 w 的形式输出Throwable
     */
    public static void w(String mTag, Throwable tr) {
        if (isDebug) {
            Log.w(mTag, "", tr);
        }
    }

    /**
     * 以级别为 w 的形式输出LOG信息和Throwable
     */
    public static void w(String mTag, String msg, Throwable tr) {
        if (isDebug) {
            Log.w(mTag, "" + msg, tr);
        }
    }

    /**
     * 以级别为 e 的形式输出LOG
     */
    public static void e(String mTag, String msg) {
        if (isDebug) {
            Log.e(mTag, "" + msg);
        }
    }

    /**
     * 以级别为 e 的形式输出Throwable
     */
    public static void e(String mTag, Throwable tr) {
        if (isDebug) {
            Log.e(mTag, "", tr);
        }
    }

    /**
     * 以级别为 e 的形式输出LOG信息和Throwable
     */
    public static void e(String mTag, String msg, Throwable tr) {
        if (isDebug) {
            Log.e(mTag, "" + msg, tr);
        }
    }

    public static String getFileLineMethod() {
        traceElement = ((exception).getStackTrace())[2];
        StringBuffer toStringBuffer = new StringBuffer("[")
                .append(traceElement.getFileName()).append(" | ")
                .append(traceElement.getLineNumber()).append(" | ")
                .append(traceElement.getMethodName()).append("]");
        return toStringBuffer.toString();
    }

    public static String getLineMethod() {
        exception = new Exception();
        traceElement = ((exception).getStackTrace())[2];
        StringBuffer toStringBuffer = new StringBuffer().append(traceElement.getMethodName())
                .append("() [Line ")
                .append(traceElement.getLineNumber())
                .append("] ");
        return toStringBuffer.toString();
    }

    public static String _FILE_() {
        exception = new Exception();
        traceElement = ((exception).getStackTrace())[2];
        String fileName = traceElement.getFileName();
        return fileName;
    }

    public static String _FUNC_() {
        traceElement = ((exception).getStackTrace())[1];
        return traceElement.getMethodName();
    }

    public static int _LINE_() {
        traceElement = ((exception).getStackTrace())[1];
        return traceElement.getLineNumber();
    }
}
