package com.example.fewwind.myfirst.Util;

import android.util.Log;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by fewwind on 2015/11/13.
 */
public class DownUtil {


    private String filePath;
    private String urlLoad;
    private int ThreadNum;

    DownThread[] threads ;

    private int fileSize;

    public DownUtil(String filePath,String url,int num){
        this.filePath = filePath;
        this.urlLoad = url;
        this.ThreadNum =num;
        threads = new DownThread[num];
    }

    public void downLoad() throws Exception{
        URL url = new URL(urlLoad);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        fileSize = conn.getContentLength();
        conn.disconnect();

        Log.e("tag","文件大小：："+fileSize);
        int currentPart = fileSize/ThreadNum+1;
        RandomAccessFile file = new RandomAccessFile(filePath,"rw");
        file.setLength(fileSize);
        file.close();

        for (int i=0;i<ThreadNum;i++){
            int startPos = i*currentPart;

            RandomAccessFile partfile = new RandomAccessFile(filePath,"rw");
            partfile.seek(startPos);

            threads[i] = new DownThread(startPos,currentPart,partfile);
            threads[i].start();

        }
    }


    public  double getPercent(){
        int sumSize=0;

        for (int i=0;i<ThreadNum;i++){
            sumSize+=threads[i].length;

        }
        Log.e("tag","下载的大小：："+sumSize);
        return sumSize*1.0/fileSize;
    }




    class DownThread extends Thread {
        private int startPos;
        private int currentPartSize;
        private RandomAccessFile randomFile;

        public int length=0;

        public DownThread(int pos, int currentPartSize, RandomAccessFile file) {
            this.startPos = pos;
            this.currentPartSize = currentPartSize;
            this.randomFile = file;
        }


        @Override
        public void run() {
            try {
                URL url = new URL(urlLoad);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setConnectTimeout(5000);
                conn.setRequestMethod("GET");
                conn.connect();
                InputStream inputStream = conn.getInputStream();

                inputStream.skip(this.startPos);
                byte[] buffer = new byte[1024];
                int hasRead = 0;
                while (length<currentPartSize&&(hasRead = inputStream.read(buffer))>0){
                    randomFile.write(buffer,0,hasRead);
                    length+=hasRead;
                }

                randomFile.close();
                inputStream.close();


            } catch (
                    Exception e) {
                e.printStackTrace();
            }


        }
    }
}
