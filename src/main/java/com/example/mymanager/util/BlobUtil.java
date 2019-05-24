package com.example.mymanager.util;

import java.io.*;

public class BlobUtil {
    public static  boolean writeNewFile(InputStream in,String savePath){
        File file=new File(savePath);
        OutputStream os=null;
        try {
            os= new BufferedOutputStream(new FileOutputStream(savePath));
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = in.read(buffer)) > 0) {
                os.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            os.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
    public static  boolean writeNewFile(byte[] data,String savePath){
        File file=new File(savePath);
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            os.write(data, 0, data.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
