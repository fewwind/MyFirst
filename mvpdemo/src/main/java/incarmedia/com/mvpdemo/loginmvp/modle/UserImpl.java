package incarmedia.com.mvpdemo.loginmvp.modle;

import android.os.Handler;
import android.text.TextUtils;

/**
 * Created by fewwind on 2016/3/31.
 */
public class UserImpl implements IUser  {

    private Handler mHandler = new Handler();
    @Override
    public void login(final String name, final String pass, final OnLoginFinishListener listener) {
        new Thread(){
            @Override
            public void run() {
                super.run();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (TextUtils.isEmpty(name)||TextUtils.isEmpty(pass)){
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.OnFail();
                        }
                    });

                } else{

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.OnSucces();
                        }
                    });

                }

            }
        }.start();


    }

    public interface OnLoginFinishListener{
        void OnSucces();
        void OnFail();
    }
}
