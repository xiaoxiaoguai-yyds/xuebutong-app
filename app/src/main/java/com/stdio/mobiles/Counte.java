package com.stdio.mobiles;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class Counte {
    public String data;

    public void setphp(int type) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://mobiles.stdio.com.cn/admin/cou.php?type=" + type + "&ls=1")
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

        });
    }


}
