package com.tkm.logindemo.util.loginutil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.tkm.logindemo.activity.LoginActivity;
import com.tkm.logindemo.activity.MainActivity;

public class LoginUtil {
    public static void startActivity(Context context, Class<? extends Activity> activity) {
        NeedLogin needLogin = activity.getAnnotation(NeedLogin.class);
        if (needLogin != null && !SPUtils.isLogin) {
            //  未登录
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
            return;
        }

        //  已登录
        Intent intent = new Intent(context, activity);
        context.startActivity(intent);
    }

    public static void logout(Context context) {
        SPUtils.isLogin = false;
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
}
