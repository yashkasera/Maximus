package com.craft404.maximus

import android.app.Application
import com.craft404.maximus.util.AppObjectController

class MaximusApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppObjectController.init(this)
    }
}