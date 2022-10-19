package com.tkm.retrofitlite_dynamicproxy.util.retrofitlite;

import android.os.Build;
import android.text.TextUtils;

import com.tkm.retrofitlite_dynamicproxy.util.retrofitlite.annotations.Field;
import com.tkm.retrofitlite_dynamicproxy.util.retrofitlite.annotations.Get;
import com.tkm.retrofitlite_dynamicproxy.util.retrofitlite.annotations.Post;
import com.tkm.retrofitlite_dynamicproxy.util.retrofitlite.annotations.Query;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class ServiceMethod {
    private Method method;
    private String baseUrl;
    private String relativeUrl;
    private Map<String, Object> postParams = new HashMap<>();
    private boolean isPost;

    public ServiceMethod(String baseUrl, Method method) {
        this.method = method;
        this.baseUrl = baseUrl;
        this.relativeUrl = parseRelativeUrl(method);
        this.isPost = parsePost(method);
    }

    public Object invoke(Object[] args) {
        if (isPost) {
            //  Post请求
            String finalUrl = baseUrl + relativeUrl;
            postParams = parsePostParams(method.getParameterAnnotations(), args);
            OkHttpClient client = new OkHttpClient.Builder()
                    .build();

            FormBody.Builder bodyBuilder = new FormBody.Builder();
            for (String key : postParams.keySet()) {
                bodyBuilder.add(key, postParams.get(key).toString());
            }

            RequestBody body = bodyBuilder.build();

            Request request = new Request.Builder()
                    .url(finalUrl)
                    .post(body)
                    .build();

            return client.newCall(request);
        }
        //  Get请求
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        String relativeUrl = parseGetRelativeUrl(this.relativeUrl, parameterAnnotations, args);
        String finalUrl = baseUrl + relativeUrl;

        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        Request request = new Request.Builder()
                .url(finalUrl)
                .get()
                .build();

        return client.newCall(request);
    }

    private String parseRelativeUrl(Method method) {
        Post post = method.getAnnotation(Post.class);
        if (post != null) {
            return post.value();
        }

        Get get = method.getAnnotation(Get.class);
        if (get != null) {
            return get.value();
        }

        throw new IllegalArgumentException("只支持Get、Post");
    }

    private boolean parsePost(Method method) {
        Post post = method.getAnnotation(Post.class);
        return post != null;
    }

    private Map<String, Object> parsePostParams(Annotation[][] annotations, Object[] args) {

        Map<String, Object> result = new HashMap<>();
        int paramIndex = 0;
        for (Annotation[] annotation : annotations) {
            for (Annotation paramAnnotation : annotation) {
                if (paramAnnotation.annotationType() == Field.class) {
                    Field field = (Field) paramAnnotation;
                    String key = field.value();
                    String value = args[paramIndex].toString();
                    result.put(key, value);
                    ++paramIndex;
                }
            }
        }
        return result;
    }

    private String parseGetRelativeUrl(String url, Annotation[][] annotations, Object[] args) {
        //  https://www.wanandroid.com/article/list/0/json
        //  @Get("article/list/@{page}/json")
        StringBuilder sb = new StringBuilder();
        char[] chars = url.toCharArray();
        int paramIndex = 0;
        for (int i = 1; i < chars.length; i++) {
            char c = chars[i - 1];
            char nextC = chars[i];
            if (c == '@' && nextC == '{') {
                Annotation[] paramAnnotations = annotations[paramIndex];
                for (Annotation paramAnnotation : paramAnnotations) {
                    if (paramAnnotation.annotationType() == Query.class) {
                        Query query = (Query) paramAnnotation;
                        //  取到替换的值
                        String param = query.value();
                        String paramValue = args[paramIndex].toString();
                        String subUrl = url.substring(i);
                        int suffixIndex = subUrl.indexOf('}');
                        if (suffixIndex != -1) {
                            sb.append(paramValue);
                            i += param.length() + 2;
                            ++paramIndex;
                        }
                    }
                }
            } else {
                sb.append(c);
            }

            if (i == chars.length - 1) {
                sb.append(nextC);
            }
        }
        return sb.toString();
    }
}
