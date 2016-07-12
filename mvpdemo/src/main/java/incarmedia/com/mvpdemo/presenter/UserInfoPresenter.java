package incarmedia.com.mvpdemo.presenter;

import android.os.Handler;

import java.util.List;

import incarmedia.com.mvpdemo.bean.User;
import incarmedia.com.mvpdemo.model.GetUserInfo;
import incarmedia.com.mvpdemo.model.IGetUser;
import incarmedia.com.mvpdemo.model.IUserInfoListener;
import incarmedia.com.mvpdemo.view.IShowUserView;

/**
 * Created by fewwind on 2016/1/27.
 */
public class UserInfoPresenter {

    private IGetUser iGetUser;
    private IShowUserView iShowUserView;

    private Handler mHandler = new Handler();

    public UserInfoPresenter(IShowUserView iShowView) {
        this.iShowUserView = iShowView;
        this.iGetUser = new GetUserInfo();
    }


    public void getUserInfoToShow(int id) {
        iShowUserView.showLoading();
        iGetUser.getUserInfo(id, new IUserInfoListener() {


            @Override
            public void getUserInfoSuccess(final User user, final List<User> users) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        iShowUserView.toMainActivity(user, users);
                        iShowUserView.hideLoading();
                    }
                });
            }

            @Override
            public void getUserInfoFail() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iShowUserView.onFail();
                        iShowUserView.hideLoading();
                    }
                });
            }
        });

    }

}
