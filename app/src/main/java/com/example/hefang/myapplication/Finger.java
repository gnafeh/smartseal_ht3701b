package com.example.hefang.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.hefang.util.ToastUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

public class Finger extends Activity {
    private static final String TAG = "hefang_Finger";
    private FingerprintManager mFingerprintManager;
    Context mContext;
    KeyguardManager mKeyManager;
    Button buttonRecord;
    Button buttonRead;
    Method method;
    Object obj;

    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finger_layout);
        buttonRecord = (findViewById(R.id.button_record));
        buttonRead = (findViewById(R.id.button_read));

        mFingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
        mKeyManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        ;

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        new Thread(runnable).start();

        if (judgeFingerprintIsCorrect()) {
            /*
            mFingerprintManager.authenticate();
            mFingerprintManager.hasEnrolledFingerprints();
            mFingerprintManager.isHardwareDetected();
            */
            // onAuthenticationSucceeded();
            //mManager.authenticate(null, mCancellationSignal, 0, mSelfCancelled, null);
            Toast.makeText(this, "check success!", Toast.LENGTH_SHORT).show();
        }
        buttonRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mintent;
                mintent = new Intent(Intent.ACTION_MAIN, null);
                //mintent.addCategory(Intent.CATEGORY_LAUNCHER);
                mintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mintent.setComponent(new ComponentName("com.android.settings", "com.android.settings.fingerprint.FingerprintSettings"));
                startActivity(mintent);

                //ToastUtils.showToast(Finger.this, "record",3000);
            }
        });
        buttonRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //handler.sendEmptyMessage(1);
                //handler.sendEmptyMessageDelayed(1, 10 * 1000);

            }
        });

        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("value");
            Log.i(TAG, "请求结果000:" + val);

            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("checkmysbh", "123456789012");
            hm.put("name", "123");
            String temp_checkmysbh = hm.get("checkmysbh");
            String temp_name = hm.get("name");
            Log.i(TAG, "check:" + temp_checkmysbh + ",name= " + temp_name);
            HttpUtils.main(hm);
        }
    };

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // TODO: http request.
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putString("value", "请求结果111");
            msg.setData(data);
            handler.sendMessage(msg);
        }
    };


    @TargetApi(23)
    public boolean judgeFingerprintIsCorrect() {
        //判断硬件是否支持指纹识别
        if (!mFingerprintManager.isHardwareDetected()) {
            ToastUtils.showToast(Finger.this, "没有指纹识别模块", 3000);
            return false;
        }
        //判断是否开启锁屏密码
        if (!mKeyManager.isKeyguardSecure()) {
            ToastUtils.showToast(Finger.this, "没有开启锁屏密码", 3000);
            return false;
        }
        //判断是否有指纹录入
        if (!mFingerprintManager.hasEnrolledFingerprints()) {
            ToastUtils.showToast(Finger.this, "没有指纹权没有指纹录入限", 3000);
            return false;
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) !=
                PackageManager.PERMISSION_GRANTED) {
            mFingerprintManager.hasEnrolledFingerprints();
            ToastUtils.showToast(Finger.this, "没有指纹权限", 3000);
            return false;
        }
        return true;
    }


    FingerprintManager.AuthenticationCallback mSelfCancelled = new FingerprintManager.AuthenticationCallback() {
        @Override
        public void onAuthenticationError(int errorCode, CharSequence errString) {
            //多次指纹密码验证错误后，进入此方法；并且，不可再验（短时间）
            //errorCode是失败的次数
            ToastUtils.showToast(Finger.this, "尝试次数过多，请稍后重试", 3000);
        }

        @Override
        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
            //指纹验证失败，可再验，可能手指过脏，或者移动过快等原因。
        }

        @Override
        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
            //指纹密码验证成功
        }

        @Override
        public void onAuthenticationFailed() {
            //指纹验证失败，指纹识别失败，可再验，错误原因为：该指纹不是系统录入的指纹。
        }
    };

    public class MyCallBack extends FingerprintManagerCompat.AuthenticationCallback {
        private static final String TAG = "MyCallBack";

        // 当出现错误的时候回调此函数，比如多次尝试都失败了的时候，errString是错误信息
        @Override
        public void onAuthenticationError(int errMsgId, CharSequence errString) {
            Log.d(TAG, "onAuthenticationError: " + errString);
        }

        // 当指纹验证失败的时候会回调此函数，失败之后允许多次尝试，失败次数过多会停止响应一段时间然后再停止sensor的工作
        @Override
        public void onAuthenticationFailed() {
            Log.d(TAG, "onAuthenticationFailed: " + "验证失败");
        }

        @Override
        public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
            Log.d(TAG, "onAuthenticationHelp: " + helpString);
        }

        // 当验证的指纹成功时会回调此函数，然后不再监听指纹sensor
        @Override
        public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult
                                                      result) {
            Log.d(TAG, "onAuthenticationSucceeded: " + "验证成功");
        }
    }

    public void onAuthenticationSucceeded() {
        try {
            /*
            Field field = result.getClass().getDeclaredField("mFingerprint");
            field.setAccessible(true);
            Object fingerPrint = field.get(result);
            */

            Class<?> clzz = Class.forName("android.hardware.fingerprint.Fingerprint");
            Method getName = clzz.getDeclaredMethod("getName");
            Method getFingerId = clzz.getDeclaredMethod("getFingerId");
            Method getGroupId = clzz.getDeclaredMethod("getGroupId");
            Method getDeviceId = clzz.getDeclaredMethod("getDeviceId");

            for (int i = 0; i < ((List) obj).size(); i++) {
                Object item = ((List) obj).get(i);
                if (null == item) {
                    continue;
                }

                CharSequence name = (CharSequence) getName.invoke(item);
                int fingerId = (int) getFingerId.invoke(item);
                int grouoId = (int) getGroupId.invoke(item);
                long deviceId = (long) getDeviceId.invoke(item);
/*
                Log.d(TAG, "name: " + name);
                Log.d(TAG, "fingerId: " + fingerId);
                Log.d(TAG, "groupId: " + grouoId);
                Log.d(TAG, "deviceId: " + deviceId);
                */
                Log.e(TAG,
                        "指纹name:" + getName.invoke(item) +
                                " 指纹库ID:" + getGroupId.invoke(item) +
                                "指纹ID:" + getFingerId.invoke(item) +
                                "设备Id:" + getDeviceId.invoke(item));
            }


        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
