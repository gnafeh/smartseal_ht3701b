package com.example.hefang.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.hefang.myapplication.MainActivity;

public class SmartsealBroadcastReceiver extends BroadcastReceiver {
    private String TAG = "hefang_SmartsealBroadcastReceiver";
    static final String ACTION_PRESS = "com.seal.press";
    static final String ACTION_RELEASE = "com.seal.release";

    @Override
    public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ACTION_PRESS)) {
                /*
                Intent mainActivityIntent = new Intent(context, MainActivity.class);  // 要启动的Activity
                mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(mainActivityIntent);
                */
                Log.d(TAG,"---------ACTION_PRESS-------");
            }else if(intent.getAction().equals(ACTION_RELEASE)){
                Log.d(TAG,"---------ACTION_RELEASE-------");
            }
    }
}
