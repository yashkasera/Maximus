package com.craft404.maximus.util;

import android.text.Spannable;
import android.text.style.CharacterStyle;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SpannableStringBuilder {
    private final List<SpanSection> spanSections;
    private final StringBuilder stringBuilder;

    public SpannableStringBuilder() {
        stringBuilder = new StringBuilder();
        spanSections = new ArrayList<>();
    }

    public SpannableStringBuilder append(String text, CharacterStyle... styles) {
        if (styles != null && styles.length > 0) {
            spanSections.add(new SpanSection(text, stringBuilder.length(), styles));
        }
        stringBuilder.append(text);
        return this;
    }

    public SpannableStringBuilder appendWithSpace(String text, CharacterStyle... styles) {
        return append(text.concat(" "), styles);
    }

    public SpannableStringBuilder appendWithLineBreak(String text, CharacterStyle... styles) {
        return append(text.concat("\n"), styles);
    }

    public android.text.SpannableStringBuilder build() {
        android.text.SpannableStringBuilder ssb = new android.text.SpannableStringBuilder(stringBuilder.toString());
        for (SpanSection section : spanSections) {
            section.apply(ssb);
        }
        return ssb;
    }

    @NotNull
    @Override
    public String toString() {
        return stringBuilder.toString();
    }

    private class SpanSection {
        private final String text;
        private final int startIndex;
        private final CharacterStyle[] styles;

        private SpanSection(String text, int startIndex, CharacterStyle... styles) {
            this.styles = styles;
            this.text = text;
            this.startIndex = startIndex;
        }

        private void apply(android.text.SpannableStringBuilder spanStringBuilder) {
            if (spanStringBuilder == null) return;
            for (CharacterStyle style : styles) {
                spanStringBuilder.setSpan(style, startIndex, startIndex + text.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        }
    }
}