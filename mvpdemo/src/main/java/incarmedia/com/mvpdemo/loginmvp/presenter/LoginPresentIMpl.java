package incarmedia.com.mvpdemo.loginmvp.presenter;

import incarmedia.com.mvpdemo.loginmvp.modle.IUser;
import incarmedia.com.mvpdemo.loginmvp.modle.UserImpl;
import incarmedia.com.mvpdemo.loginmvp.view.ILoginView;

/**
 * Created by fewwind on 2016/3/31.
 */
public class LoginPresentIMpl implements ILoginPresenter {

    ILoginView mView;
    IUser mUser;

    public LoginPresentIMpl( ILoginView view) {
        this.mView = view;
        mUser = new UserImpl();
    }

    @Override
    public void checkResult(String name, String psw) {
        mView.LoadingVis();
        mUser.login(name, psw, new UserImpl.OnLoginFinishListener() {
            @Override
            public void OnSucces() {
                mView.isSuccess(true);
                mView.LoadingGone();
            }

            @Override
            public void OnFail() {
                mView.LoadingGone();
                mView.isSuccess(false);
            }
        });
    }
}
