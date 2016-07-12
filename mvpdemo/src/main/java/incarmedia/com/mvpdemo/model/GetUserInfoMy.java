package incarmedia.com.mvpdemo.model;

import java.util.List;

import incarmedia.com.mvpdemo.bean.User;

/**
 * Created by fewwind on 2016/1/28.
 */
public class GetUserInfoMy implements IUserInfoListener {
    @Override
    public void getUserInfoSuccess(User user, List<User> users) {

    }

    @Override
    public void getUserInfoFail() {

    }
}
