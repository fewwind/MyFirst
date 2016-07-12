package incarmedia.com.mvpdemo.model;

/**
 * Created by fewwind on 2016/1/27.
 */
public interface IGetUser {
    public void getUserInfo(int id,IUserInfoListener listener);
}
