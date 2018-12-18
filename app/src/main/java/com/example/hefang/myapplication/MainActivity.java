package com.example.hefang.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.BatteryManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mediatek.customgetdevvalue.CustomGetDevValue;
import com.example.hefang.util.*;
import com.mediatek.customgetdevvalue.UUIDS;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "hefang_MActivity";
    CustomGetDevValue cgdv = new CustomGetDevValue();

    private EditText et_sn_num;
    private RadioGroup radioGroupSmartSealStatus;
    private RadioButton radioButtonSmartSealStatusOn;
    private RadioButton radioButtonSmartSealStatusOff;
    private String sn_num;
    private byte devID[] = new byte[16];
    int sn_len = 0;

    UUIDS ids = new UUIDS(MainActivity.this);

    public TextView TV;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_sn_num = (EditText) findViewById(R.id.edittext_sn_num_id);
        radioGroupSmartSealStatus = (RadioGroup) findViewById(R.id.radioGroup_id);
        radioButtonSmartSealStatusOn = (RadioButton) findViewById(R.id.radioButtonOn_id);
        radioButtonSmartSealStatusOff = (RadioButton) findViewById(R.id.radioButtonOff_id);
        radioGroupSmartSealStatus.check(radioButtonSmartSealStatusOff.getId());

        TV = (TextView) findViewById(R.id.textView);

        //ids.createFile("ttttt",PATH_SN_VALUE2);
        ids.check();
        //ids.rwProtect_f();





        //通过RadioGroup的setOnCheckedChangeListener（）来监听选中哪一个单选按钮
        radioGroupSmartSealStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectRadioButton();
            }
        });

        //activity long light
        LongLightUtils.keepScreenLongLight(MainActivity.this);

    }

    public void doClick(View view) throws Exception {
        test test = new test();
        String msg = null;
        int result = -1;
        switch (view.getId()) {
            //get sn
            case R.id.button00:
                //msg = cgdv.getDeviceSN();
                //msg = cgdv.getSNFromPath(test.PATH_TEST_R);
                msg = ids.readFromPath();
                /*
                File file = new File(test.PATH_TEST_R);
                test.isFileExist(file);
                test.createFile(TAG,test.PATH_TEST_R);
                */
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                break;
            //set sn
            case R.id.button01:
                msg = cgdv.writeDeviceSN(inputToByteArray(et_sn_num));
                //test.writeToFile(test.PATH_TEST_R,msg);
                //ids.writeToFile(ids.FILE_PROTECT_F_SN,msg);


                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                break;
            //set seal status
            case R.id.button02:
                Toast.makeText(MainActivity.this, sn_num, Toast.LENGTH_SHORT).show();
                break;
            //get seal status
            case R.id.button03:
                Toast.makeText(MainActivity.this, sn_num, Toast.LENGTH_SHORT).show();
                break;
            //get volt
            case R.id.button04:
                result = cgdv.getVolt();
                Toast.makeText(MainActivity.this, "volt= " + result, Toast.LENGTH_SHORT).show();
                break;
            //get bat percentage
            case R.id.button05:
                result = cgdv.getBatteryPercent(MainActivity.this);
                Toast.makeText(MainActivity.this, "bat percentage= " + result, Toast.LENGTH_SHORT).show();
                break;
            //delete finger print
            case R.id.button06:
                result = cgdv.delFinger((short) 123);
                Toast.makeText(MainActivity.this, "delete finger print = " + result, Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(MainActivity.this, "Not find button id!", Toast.LENGTH_SHORT).show();
                break;

        }

    }

    private void selectRadioButton() {
//通过radioGroup.getCheckedRadioButtonId()来得到选中的RadioButton的ID，从而得到RadioButton进而获取选中值

        RadioButton rb = (RadioButton) MainActivity.this.findViewById(radioGroupSmartSealStatus.getCheckedRadioButtonId());
        sn_num = (rb.getText()).toString();
    }


    public boolean isPkgInstalled(Context context, String packageName) {

        if (packageName == null || "".equals(packageName))
            return false;
        android.content.pm.ApplicationInfo info = null;
        try {
            info = context.getPackageManager().getApplicationInfo(packageName, 0);
            return info != null;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public byte[] inputToByteArray(EditText input_sn) {
        byte[] ascii = input_sn.getText().toString().getBytes();
        //System.out.println("hefangtest platform's default character encoding : " + System.getProperty("file.encoding"));
        //System.out.println("hefangtest length of byte array in default encoding : " + ascii.length);
        //System.out.println("hefangtest contents of byte array in default encoding: " + Arrays.toString(ascii));
        //String str= new String (ascii);
        //System.out.println("hefangtest ascii string: " + str);
        BatteryManager batteryManager = (BatteryManager) getSystemService(BATTERY_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            int battery = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        }
        return ascii;
    }

    public void getDeviceIdTest() {


        //ids.createFile("111" ,PATH_SN_VALUE2);
        //String PATH_SN_VALUE = "/system/third/hefangtest";
        //String PATH_SN_VALUE2 = "/storage/emulated/0/hefang/device_id";

        String path0 = "/storage/emulated/0/Android/system_device_id";
        String path1 = "/storage/emulated/0/keep_folder/system_device_id";
        String path2 = "/system/third/hefangtest";
        String path3 = "/sys/class/leds/lcd-backlight/brightness";

        /*
        File file = new File(path);
        createFile("ttt",path);
*/
        /*
        method2(path0, "android write succeed!");
        method2(path1, "keep write succeed!");
        method2(path2, "hftest write");
        method2(path3, "hftest write");
*/


    }










}
