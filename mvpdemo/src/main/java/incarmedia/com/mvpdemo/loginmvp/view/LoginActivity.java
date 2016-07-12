package incarmedia.com.mvpdemo.loginmvp.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import incarmedia.com.mvpdemo.R;
import incarmedia.com.mvpdemo.loginmvp.presenter.LoginPresentIMpl;

public class LoginActivity extends AppCompatActivity implements ILoginView {

    EditText name;
    EditText psw;
    Button login;

    LoginPresentIMpl presentIMpl;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presentIMpl = new LoginPresentIMpl(this);
        pd = new ProgressDialog(this);
        pd.setMessage("正在安全登陆中······");
        name = (EditText) findViewById(R.id.name);
        psw = (EditText) findViewById(R.id.psw);
        login = (Button) findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presentIMpl.checkResult(name.getText().toString(),psw.getText().toString());
            }
        });
    }


    @Override
    public void LoadingVis() {
        pd.show();
    }

    @Override
    public void isSuccess(boolean flag) {
            if (flag){
                Toast.makeText(LoginActivity.this,"success",Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(LoginActivity.this,"failed",Toast.LENGTH_SHORT).show();

            }
    }

    @Override
    public void LoadingGone() {
            pd.dismiss();
    }

    public static void startLoginActivity(Context context){
        Intent intent = new Intent(context,LoginActivity.class);
        context.startActivity(intent);
    }
}
