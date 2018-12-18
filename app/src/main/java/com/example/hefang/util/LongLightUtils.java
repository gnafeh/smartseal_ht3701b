package com.example.hefang.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import java.util.Locale;

import com.example.hefang.myapplication.MainActivity;
import com.mediatek.customgetdevvalue.CustomGetDevValue;

public class LongLightUtils  {

    private static final String LOG_TAG = "hefang_LongLightUtils";


    String sn = android.os.Build.SERIAL;


   /**
     * 是否使屏幕常亮
     *
    * @param activity
    */
    public static void keepScreenLongLight(MainActivity activity) {
        boolean isOpenLight = com.finddreams.screenlonglight.CommSharedUtil.getInstance(activity).getBoolean(com.finddreams.screenlonglight.CommSharedUtil.FLAG_IS_OPEN_LONG_LIGHT, true);
        Window window = activity.getWindow();
        if (isOpenLight) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

    }

}
