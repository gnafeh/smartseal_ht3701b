package com.example.hefang.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class WsnActivity extends AppCompatActivity {

    private final static String TAG = "hefang_WsnActivity";
    public final static String PATH_TEST_R = "/vendor/protect_f/test_result";
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wsn);
        textView = (TextView)findViewById(R.id.textview_sn);
    }


    public void doClick(View view) throws Exception {
        String msg = null;
        int result = -1;
        switch (view.getId()) {
            //get sn
            case R.id.button_rsn:
                ReadCamIC();
                Toast.makeText(WsnActivity.this, "rrrrrrrr", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button_wsn:
                msg = readFromPath(PATH_TEST_R);
                Toast.makeText(WsnActivity.this, msg, Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(WsnActivity.this, "Not find button id!", Toast.LENGTH_SHORT).show();
                break;

        }

    }

    public void ReadCamIC() {

        //String file_cam_main = "/proc/driver/camera_info_main";
        String file_cam_main = "/vendor/protect_f/test_result";
        String file_cam_sub = "/proc/driver/camera_info_sub";

        byte[] buffer_main = new byte[50];
        byte[] buffer_sub = new byte[50];
        try {
            InputStream inStream = new FileInputStream(file_cam_main);
            inStream.read(buffer_main);

            if (buffer_main != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                baos.write(buffer_main);
                String mBackCameraInfo = baos.toString();
                // Log.v(TAG, "read file_cam_main = " + mBackCameraInfo);
                textView.setText(mBackCameraInfo);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String readFromPath(String path) {


        File file = new File(path);
        String str = null;
        int temp_i = 0;
        try {
            InputStream is = new FileInputStream(file);
            InputStreamReader input = new InputStreamReader(is, "UTF-8");
            BufferedReader reader = new BufferedReader(input);
            str = reader.readLine();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        System.out.println(TAG + " readFromPath() = str = " + str);
        return str;
    }



}
