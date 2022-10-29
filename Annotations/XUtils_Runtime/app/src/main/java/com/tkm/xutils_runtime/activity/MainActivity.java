package com.tkm.xutils_runtime.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tkm.xutils_runtime.R;
import com.tkm.xutils_runtime.annotation.ContentView;
import com.tkm.xutils_runtime.annotation.OnClick;
import com.tkm.xutils_runtime.annotation.OnLongClick;
import com.tkm.xutils_runtime.annotation.ViewInject;
import com.tkm.xutils_runtime.utils.XUtilsLite;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewInject(R.id.tv)
    private TextView tv;

    @ViewInject(R.id.btn1)
    private Button btn1;

    @ViewInject(R.id.btn2)
    private Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        XUtilsLite.inject(this);

        tv.setText("Hello World!");
    }

    @OnClick({R.id.btn1, R.id.btn2})
    private void onViewClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.btn1) {
            Toast.makeText(this, "按钮1 clicked", Toast.LENGTH_SHORT).show();
        } else if (viewId == R.id.btn2) {
            Toast.makeText(this, "按钮2 clicked", Toast.LENGTH_SHORT).show();
        }
    }

    @OnLongClick({R.id.btn1, R.id.btn2})
    private boolean onViewLongClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.btn1) {
            Toast.makeText(this, "按钮1 long clicked", Toast.LENGTH_SHORT).show();
        } else if (viewId == R.id.btn2) {
            Toast.makeText(this, "按钮2 long clicked", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}