package com.example.wakeup.model

import android.graphics.drawable.Drawable

data class UsageStats(
    var appName: kotlin.String,
    var appIcon: Drawable,
    var useTime: Int
) {
    fun getTime(): Int {
        return useTime
    }
}