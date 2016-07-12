package com.example.fewwind.myfirst.Util;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by fewwind on 2016/1/4.
 */
public class HttpUtils {

    /**
     * 通过HttpURLConnection模拟post表单提交
     *
     * @param url
     * @param params 例如"name=zhangsan&age=21"
     * @return
     * @throws Exception
     */
    public static void postReq(String url, String params) {

        HttpURLConnection conn;

        try {
            URL urlNet = new URL(url);
            conn = (HttpURLConnection) urlNet.openConnection();

            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setDoOutput(true);

            String datas = params;
            OutputStream os =
                    conn.getOutputStream();
            os.write(datas.getBytes());
            os.flush();
            os.close();
            //调用此方法就不用调用 connect链接方法啦
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                InputStream is = conn.getInputStream();
                String result = getStringReadInputStream(is);

                Log.d("tag", "postReq: "+result);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static String getStringReadInputStream(InputStream is) {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int line = -1;
        try {
            while ((line = is.read(buffer)) != -1) {
                bos.write(buffer, 0, line);
            }
            is.close();
            String result = bos.toString();
            bos.close();

            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
