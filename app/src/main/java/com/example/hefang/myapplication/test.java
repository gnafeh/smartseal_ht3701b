package com.example.hefang.myapplication;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class test {
    private static String TAG = "hefang_test";
    String xxx = "123456789";
    String PATH_TEST_R = "/vendor/protect_f/test_result";


    public void rwProtect_f () {
        File file = new File(PATH_TEST_R);
        if(!file.exists()){
            createFile(xxx,PATH_TEST_R);
            android.util.Log.e(TAG,"protect file is not exsit!");
            return;
        }else{
            file.setReadable(true,false);
            file.setWritable(true,false);
            file.setExecutable(true,false);
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            while ((tempString = reader.readLine()) != null) {
                String[] resaultStringArray = tempString.trim().split(" ");
                //android.util.Log.e(TAG, "resaultStringArray[0]: " + resaultStringArray[0] + ", resaultStringArray[1]: " + resaultStringArray[1]);
                android.util.Log.e(TAG, "resaultStringArray[0]: " + resaultStringArray.length);
                //editor.putString(resaultStringArray[0], resaultStringArray[1]);
                //editor.commit();
                line++;
            }
            reader.close();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (reader != null){
                try {
                    reader.close();
                }catch (IOException e){

                }
            }
        }
    }

public void createFile(String id, String path) {
        File file = new File(path);
        System.out.println(TAG + " createFile() file= " + file);
    isFileExist(file);
        try {
            FileWriter a = new FileWriter(file,false);
            a.write(id);
            a.flush();
            a.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        isFileExist(file);

    }
public void isFileExist(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                System.out.println(TAG + " 文件名称：" + file.getName());
            }
        } else {
            System.out.println(TAG + " 文件不存在！");
            //file.mkdirs();
            //System.out.println(TAG + " file= " + file);
        }
    }

    public void writeToFile(String fileName, String content) {
        File file=new File(fileName);
        try {
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(file, false);
            writer.write(content);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
