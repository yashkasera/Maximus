package com.craft404.maximus.util

import java.util.*

class MessageUtils(
    private val from: String,
    private val message: String,
) {
    private val lowercaseMessage: String = message.lowercase(Locale.getDefault()).trim()
    private var isBlackListed = false

    private fun isValidMsg(): Boolean =
        when {
            from == "WhatsApp" -> false
            from == "WhatsApp Web" -> false
            message == "Incoming voice call" -> false
            message == "Missed voice call" -> false
            message == "\uD83D\uDCF9 Incoming video call" -> false
            message.contains("Incoming video call") -> false
            message.matches(Regex("^[0-9]+ missed\\scalls$")) -> false
            from.matches(Regex("^Backup\\sin\\sprogress$")) -> false
            message.matches(Regex("^Preparing\\sbackup\\s\\([0-9]+%\\)$")) -> false
            message == "This message was deleted" -> false
            message.matches(Regex("^Uploading:\\s[0-9]*\\.[0-9]+\\s[a-zA-Z]B\\sof\\s[0-9]*\\.[0-9]+ [a-zA-Z]B \\([0-9]+%\\)$")) ->
                false
            message == "\uD83D\uDC9F Sticker" -> false
            message.matches(Regex("^Tap\\sfor\\smore\\sinfo$")) -> false
            from.matches(Regex("^Finished\\sbackup$")) -> false
            else -> true
        }

    private val keywordEnabled = PrefManager.getBoolean(KEYWORDS_ENABLED, true)
    private val blacklistedKeywords = PrefManager.getStringSet(BLACKLISTED_KEYWORDS)
    private val whitelistedKeywords = PrefManager.getStringSet(WHITELISTED_KEYWORDS)
    private val minWhitelistWordCount = PrefManager.getInt(MIN_WHITELISTED_KEYWORDS, 1)
    private val isEmailEnabled = PrefManager.getBoolean(EMAIL_ENABLED, true)
    private val isPhoneEnabled = PrefManager.getBoolean(PHONE_ENABLED, true)
    private val linkFilterMode = PrefManager.getInt(LINK_FILTER, FILTER_ALL_LINKS)
    private val blacklistedLinks = PrefManager.getStringSet(BLACKLISTED_LINKS)
    private val whitelistedLinks = PrefManager.getStringSet(WHITELISTED_LINKS)
    private val isLengthyEnabled = PrefManager.getBoolean(LENGTHY_ENABLED, true)
    private val isRedDotEnabled = PrefManager.getBoolean(RED_DOT_ENABLED, true)
    private val isDateEnabled = PrefManager.getBoolean(DATE_ENABLED, true)
    private val lengthyMessageLength = PrefManager.getInt(IMPORTANT_LENGTH, -1)

    private fun containsSufficientKeywords(): Boolean {
        for (word in blacklistedKeywords) {
            if (message.contains(word.lowercase(Locale.getDefault()))) {
                isBlackListed = true
                return false
            }
        }
        var whitelistedCount = 0
        for (word in whitelistedKeywords) {
            if (message.contains(word.lowercase(Locale.getDefault())))
                whitelistedCount++
            if (whitelistedCount >= minWhitelistWordCount)
                return true
        }
        return false
    }

    private fun containsLink(): Boolean {
        if (lowercaseMessage.matches(Regex("^.*(https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|www\\.[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\\.[^\\s]{2,}|https?:\\/\\/(?:www\\.|(?!www))[a-zA-Z0-9]+\\.[^\\s]{2,}|www\\.[a-zA-Z0-9]+\\.[^\\s]{2,}).*$"))) {
            when (linkFilterMode) {
                FILTER_ALL_LINKS -> return true
                FILTER_ALL_EXCEPT_BLACKLISTED -> {
                    for (link in blacklistedLinks) {
                        if (lowercaseMessage.contains(link.lowercase(Locale.getDefault()))) {
                            isBlackListed = true
                            return false
                        }
                    }
                }
                FILTER_ONLY_WHITELISTED_LINKS -> {
                    for (link in whitelistedLinks) {
                        if (lowercaseMessage.contains(link.lowercase(Locale.getDefault()))) {
                            return true
                        }
                    }
                }
            }
        }
        return false
    }

    private fun containsPhone(): Boolean =
        message.matches(Regex("^.*([0-9]{10})+.*$")) ||
                message.matches(Regex("^.*\\+([0-9]{12})+.*$")) ||
                message.matches(Regex("^.*\\+([0-9]{12})+.*[a-zA-Z]+.*$"))

    private fun containsEmail(): Boolean =
        lowercaseMessage.matches(Regex("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$"))

    private fun isLengthy(): Boolean =
        message.length >= lengthyMessageLength

    private fun containsRedDots(): Boolean =
        message.contains("\u200B") ||
                message.contains("\uD83D\uDED1") ||
                message.contains("\uD83D\uDD34") ||
                message.contains("\uD83C\uDD98")


    private fun containsDate(): Boolean =
        message.matches(Regex("^.*[0-9]{4}(/|-)(0[1-9]|1[0-2])(/|-)(0[1-9]|[1-2][0-9]|3[0-1]).*$")) ||
                message.matches(Regex("^.*(0[1-9]|[1-2][0-9]|3[0-1])(/|-)(0[1-9]|1[0-2])(/|-)[0-9]{4}.*$")) ||
                message.matches(Regex("^.*[0-2][0-9]:[0-6][0-9]\\s?(A|P).*$"))

    fun isImportantMessage(): Boolean {
        return isValidMsg() && ((keywordEnabled) && containsSufficientKeywords()) ||
                ((linkFilterMode != FILTER_NO_LINKS) && containsLink()) ||
                ((isRedDotEnabled) && containsRedDots()) ||
                ((isLengthyEnabled) && isLengthy()) ||
                ((isEmailEnabled) && containsEmail()) ||
                ((isDateEnabled) && containsDate()) ||
                ((isPhoneEnabled) && containsPhone())
    }
}