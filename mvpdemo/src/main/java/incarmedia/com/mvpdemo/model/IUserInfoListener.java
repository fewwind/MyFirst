package incarmedia.com.mvpdemo.model;

import java.util.List;

import incarmedia.com.mvpdemo.bean.User;

/**
 * Created by fewwind on 2016/1/27.
 */
public interface IUserInfoListener {
    void getUserInfoSuccess(User user,List<User> users);
    void getUserInfoFail();
}
