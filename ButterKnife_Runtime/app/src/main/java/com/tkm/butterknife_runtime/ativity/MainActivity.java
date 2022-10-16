package com.tkm.butterknife_runtime.ativity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tkm.butterknife_runtime.R;
import com.tkm.butterknife_runtime.util.BindView;
import com.tkm.butterknife_runtime.util.ButterKnifeLite;
import com.tkm.butterknife_runtime.util.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.et_username)
    private EditText etUsername;

    @BindView(R.id.et_password)
    private EditText etPassword;

    @BindView(R.id.btn_login)
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnifeLite.bind(this);
    }

    @OnClick(R.id.btn_login)
    private void onLoginClicked(View v) {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        Toast.makeText(this, "用户名: " + username + ", 密码: " + password, Toast.LENGTH_LONG).show();
    }
}