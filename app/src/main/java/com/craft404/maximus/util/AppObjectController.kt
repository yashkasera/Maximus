package com.craft404.maximus.util

import com.craft404.maximus.MaximusApplication
import com.craft404.maximus.repository.MaximusDatabase

object AppObjectController {
    @JvmStatic
    lateinit var maximusApplication: MaximusApplication

    @JvmStatic
    lateinit var database: MaximusDatabase

    fun init(maximusApplication: MaximusApplication) {
        this.maximusApplication = maximusApplication
        this.database = MaximusDatabase.getInstance(maximusApplication)
    }
}