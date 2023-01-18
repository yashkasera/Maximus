package com.craft404.maximus.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;

import com.craft404.maximus.R;
import com.craft404.maximus.util.SharedPreferenceManager;

public class OnboardingActivity extends AppCompatActivity {
    int[] titles = {R.string.onboarding_title_1, R.string.onboarding_title_2, R.string.onboarding_title_3, R.string.onboarding_title_4};
    int[] messages = {R.string.onboarding_message_1, R.string.onboarding_message_2, R.string.onboarding_message_3, R.string.onboarding_message_4};
    int[] images = {R.drawable.onboarding1, R.drawable.onboarding2, R.drawable.onboarding3, R.drawable.onboarding4};
    int[] slideIndicator = {R.id.slide_1, R.id.slide_2, R.id.slide_3, R.id.slide_4};
    int currentIndex = 0;
    SharedPreferenceManager sharedPreferenceManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        sharedPreferenceManager = new SharedPreferenceManager(this);
        updateViews();
        findViewById(R.id.next).setOnClickListener(v -> nextSlide());
        findViewById(R.id.previous).setOnClickListener(v -> previousSlide());
    }

    private void previousSlide() {
        if (currentIndex > 0) currentIndex--;
        updateViews();
    }

    private void nextSlide() {
        currentIndex++;
        if (currentIndex < slideIndicator.length) {
            updateViews();
        } else {
            sharedPreferenceManager.setFirstTimeLaunch(false);
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    private void updateViews() {
        AppCompatImageView imageView = findViewById(R.id.imageView);
        imageView.setImageResource(images[currentIndex]);
        TextView title = findViewById(R.id.title);
        title.setText(titles[currentIndex]);
        TextView message = findViewById(R.id.message);
        message.setText(messages[currentIndex]);
        for (int i : slideIndicator) {
            findViewById(i).setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.gray)));
        }
        findViewById(slideIndicator[currentIndex]).setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorAccent)));
    }
}