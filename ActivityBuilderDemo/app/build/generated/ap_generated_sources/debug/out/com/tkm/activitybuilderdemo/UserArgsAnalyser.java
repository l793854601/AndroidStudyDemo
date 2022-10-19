package com.tkm.activitybuilderdemo;
import android.os.Bundle;
import com.tkm.activitybuilder.Args;
import java.lang.reflect.Field;
public class UserArgsAnalyser {
	public static void analyse(com.tkm.activitybuilderdemo.UserActivity userActivity) {
		Bundle bundle = userActivity.getIntent().getExtras();
		Field[] fields = userActivity.getClass().getDeclaredFields();
		for (Field field : fields) {
			Args args = field.getAnnotation(Args.class);
			if (args != null) {
				String keyName = field.getName().toUpperCase();
				Object value = bundle.get(keyName);
				try {
					field.setAccessible(true);
					field.set(userActivity, value);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
