package com.craft404.maximus.util

import android.content.Context

const val WHITELISTED_KEYWORDS = "whitelisted_keywords"
const val BLACKLISTED_KEYWORDS = "blacklisted_keywords"
const val MIN_WHITELISTED_KEYWORDS = "min_whitelisted_count"
const val KEYWORDS_ENABLED = "keywords_enabled"
const val EMAIL_ENABLED = "email_enabled"
const val PHONE_ENABLED = "phone_enabled"
const val LENGTHY_ENABLED = "lengthy_filter"
const val RED_DOT_ENABLED = "red_dot_enabled"
const val DATE_ENABLED = "date_enabled"
const val IMPORTANT_LENGTH = "important_length"
const val LINK_FILTER = "link_filter"
const val FILTER_ALL_LINKS = 0
const val FILTER_ALL_EXCEPT_BLACKLISTED = 1
const val FILTER_ONLY_WHITELISTED_LINKS = 2
const val FILTER_NO_LINKS = 3
const val WHITELISTED_LINKS = "whitelisted_links"
const val BLACKLISTED_LINKS = "blacklisted_links"

object PrefManager {
    private val sharedPreferences = AppObjectController.maximusApplication.getSharedPreferences(
        AppObjectController.maximusApplication.packageName,
        Context.MODE_PRIVATE
    )

    fun getString(key: String): String =
        sharedPreferences.getString(key, "") ?: ""

    fun getBoolean(key: String, defValue: Boolean = false): Boolean =
        sharedPreferences.getBoolean(key, defValue)

    fun getInt(key: String, defValue: Int = 0): Int =
        sharedPreferences.getInt(key, defValue)

    fun getLong(key: String): Long =
        sharedPreferences.getLong(key, 0)

    fun getStringSet(key: String): Set<String> =
        sharedPreferences.getStringSet(key, emptySet<String>())?.toSet() ?: emptySet()

    fun put(key: String, value: String) {
        with(sharedPreferences.edit()) {
            putString(key, value)
            apply()
        }
    }

    fun put(key: String, value: Int) {
        with(sharedPreferences.edit()) {
            putInt(key, value)
            apply()
        }
    }

    fun put(key: String, value: Boolean) {
        with(sharedPreferences.edit()) {
            putBoolean(key, value)
            apply()
        }
    }

    fun put(key: String, value: Long) {
        with(sharedPreferences.edit()) {
            putLong(key, value)
            apply()
        }
    }

    fun put(key: String, value: Set<String>) {
        with(sharedPreferences.edit()) {
            putStringSet(key, value)
            apply()
        }
    }

    fun remove(key: String) {
        with(sharedPreferences.edit()) {
            remove(key)
            apply()
        }
    }

    fun clear() {
        with(sharedPreferences.edit()) {
            clear()
            apply()
        }
    }
}