package incarmedia.com.mvpdemo.view;

import java.util.List;

import incarmedia.com.mvpdemo.bean.User;

/**
 * Created by fewwind on 2016/1/27.
 */
public interface IShowUserView {
    void showLoading();
    void hideLoading();

    void toMainActivity(User user,List<User> users);
    void onFail();
}
