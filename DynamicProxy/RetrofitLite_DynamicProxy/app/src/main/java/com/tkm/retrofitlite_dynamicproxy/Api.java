package com.tkm.retrofitlite_dynamicproxy;

import com.tkm.retrofitlite_dynamicproxy.util.retrofitlite.annotations.Field;
import com.tkm.retrofitlite_dynamicproxy.util.retrofitlite.annotations.Get;
import com.tkm.retrofitlite_dynamicproxy.util.retrofitlite.annotations.Post;
import com.tkm.retrofitlite_dynamicproxy.util.retrofitlite.annotations.Query;

import okhttp3.Call;

public interface Api {
    //  https://www.wanandroid.com/article/list/0/json
    @Get("article/list/@{page}/json")
    Call banner(@Query("page") int page);

    //  https://www.wanandroid.com/user/login
    @Post("user/login")
    Call login(@Field("username") String username, @Field("password") String password);
}
