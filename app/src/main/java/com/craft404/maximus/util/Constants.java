package com.craft404.maximus.util;

import android.os.Environment;

import java.io.File;

public class Constants {
    public static final File STATUS_DIRECTORY = new File(Environment.getExternalStorageDirectory()
            + File.separator + "Android/media/com.whatsapp/WhatsApp/Media/.Statuses");
    public static final String APP_DIR = Environment.getExternalStorageDirectory() + File.separator + "Maximus";
    public static final File DELETED_MEDIA_DIR = new File(APP_DIR + File.separator + "Whatsapp Deleted Images");


    public static final int IMAGE = 1;
    public static final int DOCUMENT = 2;
    public static final int VIDEO = 3;
    public static final int AUDIO = 4;
    public static final int GIF = 5;
    public static final int WALLPAPER = 6;
    public static final int VOICE = 7;
    public static final int STATUS = 8;
    public static final int STICKERS = 9;
    public static final int FILE = 10;
    public static final int THUMB_SIZE = 128;
    private static final String externalStorageDirectory = Environment.getExternalStorageDirectory().toString();
    public static final String IMAGES_RECEIVED_PATH = externalStorageDirectory + "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Images";
    public static final String IMAGES_SENT_PATH = externalStorageDirectory + "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Images/Sent";
    public static final String DOCUMENTS_RECEIVED_PATH = externalStorageDirectory + "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Documents";
    public static final String DOCUMENTS_SENT_PATH = externalStorageDirectory + "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Documents/Sent";
    public static final String VIDEOS_RECEIVED_PATH = externalStorageDirectory + "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Video";
    public static final String VIDEOS_SENT_PATH = externalStorageDirectory + "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Video/Sent";
    public static final String AUDIO_RECEIVED_PATH = externalStorageDirectory + "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Audio";
    public static final String AUDIO__SENT_PATH = externalStorageDirectory + "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Audio/Sent";
    public static final String GIF_RECEIVED_PATH = externalStorageDirectory + "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Animated Gifs";
    public static final String GIF__SENT_PATH = externalStorageDirectory + "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Animated Gifs/Sent";
    public static final String WALLPAPER_RECEIVED_PATH = externalStorageDirectory + "/Android/media/com.whatsapp/WhatsApp/Media/WallPaper";
    public static final String WALLPAPER__SENT_PATH = externalStorageDirectory + "/Android/media/com.whatsapp/WhatsApp/Media/WallPaper/Sent";
    public static final String VOICE_RECEIVED_PATH = externalStorageDirectory + "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Voice Notes";
    public static final String VOICE__SENT_PATH = externalStorageDirectory + "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Voice Notes/Sent";
    public static final String STATUS_CACHE = externalStorageDirectory + "/Android/media/com.whatsapp/WhatsApp/Media/.Statuses";
    public static final String STATUS_DOWNLOAD = externalStorageDirectory + "/Android/media/com.whatsapp/WhatsApp/Media/.Status Download";
    public static final String STICKERS_PATH = externalStorageDirectory + "/Android/media/com.whatsapp/WhatsApp/Media/WhatsApp Stickers";
    public final String WHATSAPP = "WhatsApp";
    public final String WHATSAPP_WEB = "WhatsApp Web";
    public final String FINISHED_BACKUP = "Finished backup";
    public final String BACKUP_PROGRESS = "Backup in progress";
    public final String BACKUP_PAUSED = "Backup paused";
    public final String YOU = "You";
    public final String RESTORE_MEDIA = "Restoring media";
    public final String CHECKING_NEW_MESSAGE = "Checking for new messages";
    public final String CHECKING_WEB_LOGIN = "WhatsApp Web is currently active";
    public final String BACKUP_INFO = "Tap for more info";
    public final String WAITING_FOR_WIFI = "Waiting for Wi-Fi";
    public final String MESSAGE_DELETED = "This message was deleted";

}
