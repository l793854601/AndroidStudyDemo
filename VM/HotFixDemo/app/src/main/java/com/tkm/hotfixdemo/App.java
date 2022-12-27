package com.tkm.hotfixdemo;

import android.app.Application;
import android.content.Context;

import com.tkm.hotfixdemo.hotfix.HotFixLite;

import java.io.File;

public class App extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        //  仅做测试，将patch.dex放在cache文件夹中加载
        String patchPath = base.getCacheDir().getAbsolutePath() + "/patch.dex";
        HotFixLite.installDex(base, patchPath);
    }
}
