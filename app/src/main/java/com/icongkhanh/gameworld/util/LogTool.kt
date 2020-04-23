package com.icongkhanh.gameworld.util

import android.util.Log
import com.icongkhanh.gameworld.BuildConfig

object LogTool {

    fun d(tag: String?, message: String?) {
        if (!BuildConfig.DEBUG) return
        if (message == null) return
        Log.d(tag, message)
    }

    fun e(tag: String?, message: String?) {
        if (!BuildConfig.DEBUG) return
        if (message == null) return
        Log.e(tag, message)
    }
}