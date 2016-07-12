package incarmedia.com.mvpdemo.loginmvp.modle;

/**
 * Created by fewwind on 2016/3/31.
 */
public interface IUser {

    void login(String name,String pass,UserImpl.OnLoginFinishListener listener);
}
