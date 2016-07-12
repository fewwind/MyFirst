package incarmedia.com.mvpdemo.model;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import incarmedia.com.mvpdemo.bean.User;

/**
 * Created by fewwind on 2016/1/27.
 */
public class GetUserInfo implements IGetUser {

    private List<User> userList;


    @Override
    public void getUserInfo(final int id, final IUserInfoListener listener) {


        new Thread() {
            @Override
            public void run() {


                if (id == 1) {
                    userList = new ArrayList<User>();
                    for (int i = 0; i < 8; i++) {
//                        userList.add(new User("fewwind", i + "", "male", 20 + i + ""));
                    }
                    userList = getAllFiles(Environment.getExternalStorageDirectory());
                    listener.getUserInfoSuccess(new User("少峰", "1", "男", "29"), userList);
                } else {
                    listener.getUserInfoFail();
                }

            }
        }.start();

    }


    public List<User> getMediaFile(String path) {
        List<User> lists = new ArrayList<>();
        File root = new File(path);
        File[] files = root.listFiles();
        if (files==null) return null;

        for (File file : files) {
            if (file.isFile()){
                if(file.getName().endsWith("mp3")) lists.add(new User(file.getName(),"","",""));
            } else{

            }
        }

        return lists;
    }

    List<User> lists = new ArrayList<>();
    // 遍历接收一个文件路径，然后把文件子目录中的所有文件遍历并输出来
    private  List<User> getAllFiles(File root){

        File files[] = root.listFiles();
        if(files != null){
            for (File file  : files){
                if(file.isDirectory()){
                    getAllFiles(file);
                }else{
                    if(file.getName().endsWith("mp3")||file.getName().endsWith("m4a")||file.getName().endsWith("mp4")) lists.add(new User(file.getName(),"","",""));
                }
            }

            return lists;
        }

        return null;
    }
}
