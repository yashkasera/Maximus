package com.craft404.maximus.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Functions {

    public static boolean isSameDay(Date date1, Date date2) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        return fmt.format(date1).equals(fmt.format(date2));
    }

    public static void openWhatsApp(Context context, String phone, String text) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            if (text != null) {
                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + phone + "&text=" + text));
            } else {
                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + phone));
            }
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
